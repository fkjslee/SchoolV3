package com.fkjslee.schoolv3.teacher.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

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

    private MsgClass msg = null;
    private Integer spinnerWeek;

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

        tvRoom.setText(msg.getPosition());
        tvTime.setText(msg.getWeeks());

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
        }
    }
}
