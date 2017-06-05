package com.fkjslee.schoolv3.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

import org.json.JSONException;
import org.json.JSONObject;

public class CourseSignActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView tvRoom;
    private TextView tvTime;
    private TextView tvAskLeavePerson;
    private TextView tvSignedPerson;
    private TextView tvAbsentPerson;
    private Button btnSign;
    private Button btnAskLeave;
    private Button btnAbsent;

    private MsgClass msg = null;
    private Integer spinnerWeek;
    public Boolean canSign = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_sign);
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");
        initView();
    }

    private void initView() {
        tvRoom = (TextView)findViewById(R.id.tv_room);
        tvTime = (TextView)findViewById(R.id.tv_time);
        tvAskLeavePerson = (TextView)findViewById(R.id.tv_askLeave);
        tvSignedPerson = (TextView)findViewById(R.id.tv_signed);
        tvAbsentPerson = (TextView)findViewById(R.id.tv_absent);
        btnSign = (Button) findViewById(R.id.btn_sign);
        btnAskLeave = (Button) findViewById(R.id.btn_askLeave);
        btnAbsent = (Button) findViewById(R.id.btn_absent);

        tvRoom.setText(msg.getPosition());
        tvTime.setText(msg.getWeeks());

        btnSign.setOnClickListener(this);
        btnAskLeave.setOnClickListener(this);
        btnAbsent.setOnClickListener(this);
        tvAskLeavePerson.setOnClickListener(this);
        tvAbsentPerson.setOnClickListener(this);

        String param = "type=getSignRes" +
                "&telephone=" + LogActivity.logAccount +
                "&week=" + spinnerWeek +
                "&weekday=" + msg.getWeekday() +
                "&courseBegin=" + msg.getStartTime() +
                "&length=" + msg.getLength();
        String result = MyCommonFunction.sendRequestToServer(param);
        JSONObject json;
        try {
            json = new JSONObject(result);
            tvAskLeavePerson.setText(json.getString("leave"));
            tvSignedPerson.setText(json.getString("succeed"));
            tvAbsentPerson.setText(json.getString("failed"));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        intent.putExtra("classMsg", msg);
        intent.putExtra("spinnerWeek", spinnerWeek);
        switch (view.getId()) {
            case R.id.tv_askLeave:
                intent.setClass(this, LeaveStuActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_absent:
                intent.setClass(this, AbsentStuActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_askLeave:
                intent.setClass(this, LeaveStuActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_absent:
                intent.setClass(this, AbsentStuActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_sign:
                if(btnSign.getText().toString().equals("开始签到")) {
                    btnSign.setText("结束签到");
                    canSign = true;
                } else {
                    btnSign.setText("开始签到");
                    canSign = false;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MyCommonFunction.sendRequestToServer("type=attendanceControl" +
                                "&tName=" + msg.getTeacher() +
                                "&week=" + spinnerWeek +
                                "&weekday=" + msg.getWeekday() +
                                "&courseBegin=" + msg.getStartTime() +
                                "&length=" + msg.getLength() +
                                "&state=" + (canSign?"1":"-1"));
                    }
                }).start();
                break;
        }
    }
}
