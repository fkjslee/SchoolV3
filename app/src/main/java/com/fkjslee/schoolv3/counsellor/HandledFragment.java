package com.fkjslee.schoolv3.counsellor;

import android.app.Activity;
import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.database.Database;

import java.util.List;
import java.util.Map;

/**
 * Created by Xiaojun on 2017/5/7.
 */

public class HandledFragment extends Fragment {
    private View view = null;
    private ListView listViewHandled = null;
    private SimpleAdapter handledAdapter = null;
    private List<Map<String,Object>> handled;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.counsellor_leave_handled,container,false);
        return view;
    }

    @Override
    public void onResume() {
        if (handled != null){
            handled.clear();
            handled.addAll(Helper.setMapList(Database.getLeaveDatas("leaves"),1));
        }else{
            handled = Helper.setMapList(Database.getLeaveDatas("leaves"),1);
        }
        Database.close();
        initHandledView();
        super.onResume();
    }

    private void initHandledView(){ //初始化已处理界面
        listViewHandled = null;
        listViewHandled = (ListView) view.findViewById(R.id.counsellor_leave_handled);
        handledAdapter = new SimpleAdapter(view.getContext(),handled,R.layout.counsellor_leave_item,
                LeaveContent.array,
                new int[]{R.id.counsellor_leave_student_name,R.id.counsellor_leave_student_sn,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time,R.id.counsellor_leave_pass});
        listViewHandled.setAdapter(handledAdapter);

        listViewHandled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HandledFragment.this.getActivity(),ShowLeaveDetail.class);
                /*传递假条对象*/
                LeaveContent leaveContent = new LeaveContent();
                leaveContent.studentName = (String) handled.get(i).get("studentName");
                leaveContent.studentNumber = (String)handled.get(i).get("studentNumber");
                leaveContent.reasons = (String)handled.get(i).get("reasons");
                leaveContent.time = (String)handled.get(i).get("time");
                intent.putExtra("leaveContent",leaveContent);
                intent.putExtra("deal",1);//表示这些假条未处理
                startActivity(intent);
            }
        });

        listViewHandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                new AlertDialog.Builder(view.getContext()).setTitle("删除这条记录").setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                /*删除并刷新界面*/
                                Map<String,Object> map = handled.remove(i);
                                handledAdapter.notifyDataSetChanged();
                                //从数据库删除记录
                                Database.deleteLeave((String) map.get("studentNumber"),"leaves");
                                Database.close();
                                Toast.makeText(view.getContext(),"已删除",Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;//当同时设置了点击事件和长按事件时，这里要设置为true，不然长按之后还会激发点击事件
            }
        });
    }

}
