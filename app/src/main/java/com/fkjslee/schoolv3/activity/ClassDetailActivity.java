package com.fkjslee.schoolv3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.MsgClass;


/**
 * 课程详情界面
 * 通过在课表中点击对应的课程进入
 * */

public class ClassDetailActivity extends AppCompatActivity implements View.OnClickListener{

    MsgClass msg = null;

    private Button btnRtn;
    private Button btnHonework;
    private Button btnSign;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_detail);

        initView();
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        TextView tvName = (TextView)findViewById(R.id.tvName);
        tvName.setText(msg.getName());
        TextView tvPosition = (TextView)findViewById(R.id.tvPosition);
        tvPosition.setText(msg.getPosition());
        TextView tvTeacher = (TextView)findViewById(R.id.tvTeacher);
        tvTeacher.setText(msg.getTeacher());
        TextView tvNum = (TextView)findViewById(R.id.tvNumber);
        tvNum.setText(String.valueOf(msg.getLength()));
        TextView tvWeeks = (TextView)findViewById(R.id.tvWeeks);
        String temp = "";
        for(int i : msg.getWeeks()) temp += String.valueOf(i) + " ";
        tvWeeks.setText(String.valueOf(temp));
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rtn:
                clickBtnRtn();
                break;
            case R.id.btn_homework:
                toHomeworkActivity();
                break;
            case R.id.btn_sign:
                Sign();
                break;
        }
    }

    private void Sign() {
        toSignActivity();
    }

    private void toSignActivity() {
        Intent intent = new Intent(ClassDetailActivity.this, SignActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        intent.putExtra("classMsg", msg);
        startActivity(intent);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void clickBtnRtn() { finish(); }

    private void toHomeworkActivity() {
        Intent intent = new Intent(this, HomeworkActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

    private void initView() {
        btnHonework = (Button) findViewById(R.id.btn_homework);
        btnRtn = (Button) findViewById(R.id.btn_rtn);
        btnSign = (Button) findViewById(R.id.btn_sign);

        btnHonework.setOnClickListener(this);
        btnRtn.setOnClickListener(this);
        btnSign.setOnClickListener(this);
    }
}
