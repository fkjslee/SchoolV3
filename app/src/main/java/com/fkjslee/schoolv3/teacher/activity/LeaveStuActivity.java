package com.fkjslee.schoolv3.teacher.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.function.Student;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class LeaveStuActivity extends AppCompatActivity {

    private static Handler handler;
    private ListView lvLeaveStudent;
    private MsgClass msg = null;
    private Integer spinnerWeek;
    Vector<Student> vLeaveStu = new Vector<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_stu);
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");
        initView();
    }

    private void initView() {
        lvLeaveStudent = (ListView) findViewById(R.id.lv_leaveStudent);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setData((String)msg.obj);
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String param = "type=getSignDetail" +
                        "&telephone=" + LogActivity.logAccount +
                        "&week=" + spinnerWeek +
                        "&weekday=" + msg.getWeekday() +
                        "&courseBegin=" + msg.getStartTime() +
                        "&length=" + msg.getLength() + "&state=2";
                Message message = new Message();
                message.what = 1;
                message.obj = MyCommonFunction.sendRequestToServer(param);
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();


    }

    private void setData(String result) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(result);
            vLeaveStu.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tem = jsonArray.getJSONObject(i);
                String stuId = tem.getString("stuId");
                String stuName = tem.getString("stuName");
                Student stu = new Student(stuId, stuName);
                vLeaveStu.add(stu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item;
        for(Integer i = 0; i < vLeaveStu.size(); ++i) {
            Student stu = vLeaveStu.elementAt(i);
            item = new HashMap<>();
            item.put("sId", stu.stuId);
            item.put("sName", stu.stuName);
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(this, items, R.layout.leave_student_item,
                new String[]{"sId", "sName"}, new int[]{R.id.leave_studentID, R.id.leave_student_name});
        lvLeaveStudent.setAdapter(adapter);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
