package com.fkjslee.schoolv3.teacher;

import android.content.DialogInterface;
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
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.function.Student;
import com.fkjslee.schoolv3.teacher.function.StudentHelp;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaveStuActivity extends AppCompatActivity {

    private View view = null;
    private ListView listViewHandled = null;
    private SimpleAdapter handledAdapter = null;
    private List<Map<String, Object>> handled;
    private ImageView back;
    private String array[] = {"stuID", "stuName"};

    private MsgClass msg = null;
    private Integer spinnerWeek;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_stu);
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");
        initView();
    }
    @Override
    protected void onResume() {
        super.onResume();
        getLeaveStudent();
    }

    private void initView() {
        getLeaveStudent();
        back = (ImageView) findViewById(R.id.leave_stu_back);
        listViewHandled = (ListView) findViewById(R.id.activity_leave_listView);
        handledAdapter = new SimpleAdapter(this, handled, R.layout.leave_student_item,
                array,
                new int[]{R.id.leave_studentID, R.id.leave_student_name});
        listViewHandled.setAdapter(handledAdapter);
        //回退上一层
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LeaveStuActivity.this.finish();
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
                                Map<String, Object> map = handled.remove(i);
                                handledAdapter.notifyDataSetChanged();
                                //从数据库删除记录
                                Database.deleteLeave((String) map.get("studentNumber"), "leaves");
                                Database.close();
                                Toast.makeText(view.getContext(), "已删除", Toast.LENGTH_SHORT).show();
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

    private void getLeaveStudent() {

        String param = "type=getSignDetail&telephone=" + LogActivity.logAccount + "&week=" + spinnerWeek
                + "&weekday=1"  + "&courseBegin=" + msg.getStartTime() +
                "&length=" + msg.getLength() + "&state=2";
        String result = MyCommonFunction.sendRequestToServer(param);
        JSONArray stuList = null;
        List<Student> list = new ArrayList<Student>();
        try {
            stuList = new JSONArray(result);
            for(int i=0;i<stuList.length();i++){
                JSONObject tem = stuList.getJSONObject(i);
                String stuId = tem.getString("stuId");
                String stuName = tem.getString("stuName");
                Student stu = new Student(stuId, stuName);
                System.out.println(stuName);
                list.add(stu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
       handled= new StudentHelp().setMapList(list);
    }

    private List<Student> getStudentList(String res) {
        List<Student> list = new ArrayList<>();

        return list;
    }
}
