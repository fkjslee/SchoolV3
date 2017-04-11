package com.fkjslee.schoolv3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.function.TimeCount;


/**
* 功能: 忘记密码控件
* */

public class FgtPwdActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnSendCode;
    private Button btnRtn;
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_fgt_pwd);

        initView();
    }

    /**
    * 点击事件
    * 1. 发送验证码
    * 2. 返回
    * 3. 确定
    * */
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_send_code:
                clickBtnSendCode();
                break;
            case R.id.btn_rtn:
                clickBtnRtn();
                break;
            case R.id.btn_sure:
                clickBtnSure();
                break;
        }
    }

    /**
    * 功能: 发送验证码
    * 返回值: 无
    * 参数: 无
    * 点击后需要等待60s才能再次点击
    * */
    private void clickBtnSendCode() {
        Log.i("routerecord", "clickBtnSendCode1");
        String temp = getPhone( ((EditText)findViewById(R.id.et_stu_ID)).getText().toString() );
        sendCode(temp);
        Log.i("routerecord", "clickBtnSendCode");
        TimeCount tc = new TimeCount(60 * 1000, 1000, (Button)findViewById(R.id.btn_send_code),
                "已发送 ", "");//发送验证码之后按钮等待60秒再响应
        tc.start();
    }

    /**
    * to do
    * 向目标手机发送验证码
    * */
    private void sendCode(String phone) {

    }

    private String getPhone(String stu_ID) {
        return stu_ID;
    }

    /**
    * 功能: 返回到登录界面
    * 返回值: 无
    * 参数: 无
    * */
    private void clickBtnRtn() {
        finish();
    }

    /**
    * 点击确定按钮
    * 1. 如果验证码和学生的ID对应的手机不合, 显示"验证码错误"
    * 2. 如果两次密码不同, 显示"密码不同"
    * 3. 否则修改该学生的密码
    * */
    private void clickBtnSure() {
        EditText et_stu_ID = (EditText)findViewById(R.id.et_stu_ID);
        EditText et_code = (EditText)findViewById(R.id.et_code);
        EditText et_pwd = (EditText)findViewById(R.id.et_pwd);
        EditText et_new_pwd = (EditText)findViewById(R.id.et_new_pwd);
        if(!checkCode(et_stu_ID.getText().toString(), et_code.getText().toString())) {
            Toast.makeText(this, "验证码错误", Toast.LENGTH_SHORT).show();
        } else if(!et_pwd.getText().toString().equals(et_new_pwd.getText().toString())) {
            Toast.makeText(this, "两次输入密码不同", Toast.LENGTH_SHORT).show();
        } else {
            changePwd(et_stu_ID.getText().toString(), et_pwd.getText().toString());
        }
    }

    /**
    * to do
    * 返回该学生的ID和验证码是否相符
    * */
    private boolean checkCode(String stu_ID, String code) {
        return stu_ID.length() == 0 && code.length() == 0;
    }

    /**
    * to do
    * 修改学生的密码
    * */
    private void changePwd(String stu_ID, String code) {

    }

    /**
    * 点击返回后返回到登录界面
    * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        btnRtn = (Button) findViewById(R.id.btn_rtn);
        btnSendCode = (Button) findViewById(R.id.btn_send_code);
        btnSure = (Button) findViewById(R.id.bt_sure);

        btnRtn.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);
        btnSure.setOnClickListener(this);
    }
}
