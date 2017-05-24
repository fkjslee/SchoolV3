package com.fkjslee.schoolv3.student.activity;

import android.annotation.TargetApi;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.data.MsgLeave;

public class LeaveDetail extends AppCompatActivity implements View.OnClickListener {

    private MsgLeave msgLeave;
    private TextView tvReason;
    private TextView tvRequestTime;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private TextView tvResult;
    private ImageView ivRtn;
    private Button btnKnow;

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
        tvResult = (TextView)findViewById(R.id.tv_result);
        ivRtn = (ImageView)findViewById(R.id.iv_rtn);
        btnKnow = (Button)findViewById(R.id.btn_know);

        ivRtn.setOnClickListener(this);
        btnKnow.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-M-d H:m:s");
        tvReason.setText(msgLeave.getReason());
        tvRequestTime.setText(simpleDateFormat.format(msgLeave.getRequestTime().getTime()));
        tvStartTime.setText(simpleDateFormat.format(msgLeave.getStartTime().getTime()));
        tvEndTime.setText(simpleDateFormat.format(msgLeave.getEndTime().getTime()));
        if(msgLeave.getResult().equals("-1")) tvResult.setText("请假被拒绝");
        else if(msgLeave.getResult().equals("0")) tvResult.setText("还未批准");
        else tvResult.setText("允许请假");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rtn:
                finish();
                break;
            case R.id.btn_know:
                finish();
                break;
        }
    }
}
