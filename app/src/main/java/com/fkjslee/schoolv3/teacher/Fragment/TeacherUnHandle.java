package com.fkjslee.schoolv3.teacher.Fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.Helper;
import com.fkjslee.schoolv3.counsellor.LeaveContent;
import com.fkjslee.schoolv3.counsellor.ShowLeaveDetail;
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.LeaveStuActivity;
import com.fkjslee.schoolv3.teacher.ShowStuLeave;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiaojun on 2017/5/7.
 */

public class TeacherUnHandle extends Fragment {
    private View view = null;
    private ListView listViewUnhandled = null;
    private SimpleAdapter unhandledAdapter = null;
    private List<Map<String,Object>> unhandled;
    private String LeaveNodeId;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_un_handle,container,false);
        return view;
    }

    @Override
    public void onResume() {
        if (unhandled != null){
            unhandled.clear();
            //换数据源
            unhandled.addAll(getLeaveNote());
        }else{
            unhandled = getLeaveNote();
        }
        Database.close();
        initUnhandledView();
        super.onResume();
    }

    private void initUnhandledView(){ //初始化未处理界面
        listViewUnhandled = null;
        listViewUnhandled = (ListView) view.findViewById(R.id.teacher_leave_unhandled);
        unhandledAdapter = new SimpleAdapter(view.getContext(),unhandled,R.layout.counsellor_leave_item,
                LeaveContent.array,
                new int[]{R.id.counsellor_leave_student_name,R.id.counsellor_leave_student_sn,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time});
        listViewUnhandled.setAdapter(unhandledAdapter);

        /*为未处理的请假请求添加点击事件和长按事件：点击事件跳转到完整的假条显示，长按可以编辑单个条目*/

        listViewUnhandled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(TeacherUnHandle.this.getActivity(),ShowStuLeave.class);
                /*传递假条对象*/
                LeaveContent leaveContent = new LeaveContent();
                leaveContent.studentName = (String) unhandled.get(i).get("studentName");
                leaveContent.studentNumber = (String)unhandled.get(i).get("studentNumber");
                leaveContent.reasons = (String)unhandled.get(i).get("reasons");
                leaveContent.startTime = (String)unhandled.get(i).get("startTime");
                leaveContent.endTime = (String)unhandled.get(i).get("endTime");
                leaveContent.leaveNoteId=(String)unhandled.get(i).get("leaveNoteId");
                intent.putExtra("leaveContent",leaveContent);
                intent.putExtra("deal",0);//表示这些假条未处理
                startActivity(intent);
            }
        });

        listViewUnhandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                final LeaveContent leaveTemp = new LeaveContent();
                final int index = i;
                //弹出菜单，询问是否要清空当前
                new AlertDialog.Builder(view.getContext()).setTitle("同意请假？")
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                leaveTemp.pass = 1;
                                leaveTemp.deal = 1;
                                dialog.dismiss();
                                /*把当前操作假条的内容从未处理记录中删除并添加到已处理条目中，学生信息反馈*/
                                Toast.makeText(view.getContext(),"已同意",Toast.LENGTH_SHORT).show();

                                leaveTemp.studentNumber = (String)unhandled.remove(index).get("studentNumber");
                                unhandledAdapter.notifyDataSetChanged();
                                //从数据库更改记录
                                Database.updateLeave(leaveTemp,"leaves");
                                //添加记录到已处理记录里面并刷新显示,关闭数据库

//                                HandledFragment.handled.clear();
//                                HandledFragment.handled.addAll(Helper.setMapList(openOrCreateDB.getLeaveDatas("leaves"),1));
                                Database.close();
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                leaveTemp.pass = 0;
                                leaveTemp.deal = 1;
                                Log.e("yes",""+leaveTemp.deal);
                                /*把当前操作假条的内容从未处理记录中删除并添加到已处理，学生信息反馈*/
                                Toast.makeText(view.getContext(),"已拒绝",Toast.LENGTH_SHORT).show();

                                Map<String,Object> map = unhandled.remove(index);
                                map.remove("pass");
                                map.put("pass","已拒绝");

                                leaveTemp.studentNumber = (String)map.get("studentNumber");
                                unhandledAdapter.notifyDataSetChanged();

                                //从数据库更改记录
                                Database.updateLeave(leaveTemp,"leaves");
                                //添加记录到已处理记录里面并刷新显示,关闭数据库
//                                HandledFragment.handled.clear();
//                                HandledFragment.handled.addAll(Helper.setMapList(openOrCreateDB.getLeaveDatas("leaves"),1));
                                Database.close();
                                dialog.dismiss();

                            }
                        }).show();

                return true;
            }
        });
    }

    //想服务器获取请教条数据
    private List<Map<String,Object>> getLeaveNote(){

        String param = "type=getLeave&telephone=" + "" + "&noteState= 2";
        String result = MyCommonFunction.sendRequestToServer(param);
        List<LeaveContent> list=getLeaveContent(result);
        return new Helper().setMapList(list,0);
    }
    //解析数据
    private List<LeaveContent>  getLeaveContent(String str){

        List<LeaveContent> list=new ArrayList<>();

        return list;
    }
}
