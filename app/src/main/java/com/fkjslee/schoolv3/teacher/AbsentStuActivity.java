package com.fkjslee.schoolv3.teacher;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.HandledFragment;
import com.fkjslee.schoolv3.counsellor.LeaveContent;
import com.fkjslee.schoolv3.counsellor.ShowLeaveDetail;
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.student.activity.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.function.Student;
import com.fkjslee.schoolv3.teacher.function.StudentHelp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class AbsentStuActivity extends AppCompatActivity {

    private View view = null;
    private ListView listViewHandled = null;
    private SimpleAdapter handledAdapter = null;
    private List<Map<String,Object>> handled;
    private ImageView back;
    private String array[]={"stuID","stuName"};
    private MsgClass msg = null;
    private Integer spinnerWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_stu);
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");

        initView();
    }

    @Override
    protected void onResume() {

        super.onResume();
        try {
            getAbsentStudent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void initView(){
        try {
            getAbsentStudent();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        back=(ImageView) findViewById(R.id.absent_stu_back);
        listViewHandled = (ListView)findViewById(R.id.activity_absent_listView);
        handledAdapter = new SimpleAdapter(this,handled,R.layout.absent_student_item,
                array,
                new int[]{R.id.absent_studentID,R.id.absent_student_name});
        listViewHandled.setAdapter(handledAdapter);
        //回退上一层
       back.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                AbsentStuActivity.this.finish();
            }
        });

        listViewHandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                new AlertDialog.Builder(view.getContext()).setTitle("删除这条记录").setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                /*//*删除并刷新界面*//**/
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

    private void getAbsentStudent() throws JSONException {
        String param = "type=getSignDetail&telephone=" + LogActivity.logAccount + "&week=" + spinnerWeek
                + "&weekday=" + msg.getWeekday() + "&courseBegin=" + msg.getStartTime() +
                "&length=" + msg.getLength() + "&state=0";
        String result = MyCommonFunction.sendRequestToServer(param);
        JSONArray stuList = new JSONArray(result);
        List<Student> list = new ArrayList<Student>();
        for(int i=0;i<stuList.length();i++) {
            JSONObject tem = stuList.getJSONObject(i);
            String stuId = tem.getString("stuId");
            String stuName = tem.getString("stuName");
            Student stu = new Student(stuId, stuName);
            list.add(stu);
        }
        handled= new StudentHelp().setMapList(list);
    }

    private List<Student> getStudentList(String res){
        List<Student> list=new ArrayList<>();

        return list;
    }

}
