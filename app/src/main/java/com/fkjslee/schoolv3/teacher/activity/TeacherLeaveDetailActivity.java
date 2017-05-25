package com.fkjslee.schoolv3.teacher.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.TeacherLeaveContent;

public class TeacherLeaveDetailActivity extends AppCompatActivity implements View.OnClickListener {

    private TeacherLeaveContent buffer;
    private TextView tvSId;
    private TextView tvSName;
    private TextView tvContent;
    private TextView tvTime;
    private Button btnHasSeen;
    private ImageView ivRtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_leave_detail);
        buffer = (TeacherLeaveContent)getIntent().getSerializableExtra("teacherLeaveContent");
        initView();
    }

    private void initView() {
        tvSId = (TextView)findViewById(R.id.tv_sId);
        tvSName = (TextView)findViewById(R.id.tv_sName);
        tvContent = (TextView)findViewById(R.id.tv_content);
        tvTime = (TextView)findViewById(R.id.tv_time);
        btnHasSeen = (Button)findViewById(R.id.btn_hasSeen);
        ivRtn = (ImageView)findViewById(R.id.iv_rtn);

        btnHasSeen.setOnClickListener(this);
        ivRtn.setOnClickListener(this);

        tvSId.setText(buffer.getSid());
        tvSName.setText(buffer.getsName());
        tvContent.setText(buffer.getContent());
        tvTime.setText("第" + buffer.getWeek() + "  周" + buffer.getWeekDay() + "  " + "第" +
                buffer.getCourseBegin() + "节课-第" + String.valueOf(
                Integer.valueOf(buffer.getCourseBegin()) + Integer.valueOf(buffer.getLength())) +
                "节课");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_hasSeen:
                String param = "type=teacherNoteHandle" +
                        "&note2Id=" + buffer.getNoteId();
                MyCommonFunction.sendRequestToServer(param);
                Toast.makeText(this, "此消息已转移至\"已阅\"中", Toast.LENGTH_SHORT).show();
                finish();
                break;
            case R.id.iv_rtn:
                finish();
                break;
        }
    }
}
