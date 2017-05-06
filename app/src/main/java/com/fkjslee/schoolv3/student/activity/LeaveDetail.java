package com.fkjslee.schoolv3.student.activity;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.data.MsgLeave;

public class LeaveDetail extends AppCompatActivity {

    private MsgLeave msgLeave;
    private TextView tvReason;
    private TextView tvRequestTime;
    private TextView tvStartTime;
    private TextView tvEndTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_detail);
        msgLeave = (MsgLeave)getIntent().getSerializableExtra("msgLeave");
        initView();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private void initView() {
        tvReason = (TextView)findViewById(R.id.tv_leave_reason);
        tvRequestTime = (TextView)findViewById(R.id.tv_request_time);
        tvStartTime = (TextView)findViewById(R.id.tv_start_time);
        tvEndTime = (TextView)findViewById(R.id.tv_end_time);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        tvReason.setText(msgLeave.getReason());
        tvRequestTime.setText(simpleDateFormat.format(msgLeave.getRequestTime().getTime()));
        tvStartTime.setText(simpleDateFormat.format(msgLeave.getStartTime().getTime()));
        tvEndTime.setText(simpleDateFormat.format(msgLeave.getEndTime().getTime()));
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }
}
