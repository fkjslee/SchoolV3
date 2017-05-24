package com.fkjslee.schoolv3.teacher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;

public class TeacherLeaveDetailActivity extends AppCompatActivity {

    private TeacherLeaveContent teacherLeaveContent;
    private TextView tvSId;
    private TextView tvSName;
    private TextView tvContent;
    private TextView tvTime;
    private TextView tvState;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave_detail);
        teacherLeaveContent = (TeacherLeaveContent)getIntent().getSerializableExtra("msgLeave");
        initView();
    }

    private void initView() {
//        tvSid = (TextView)findViewById(R.id.tv_sId);
    }
}
