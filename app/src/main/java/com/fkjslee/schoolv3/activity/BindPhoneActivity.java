package com.fkjslee.schoolv3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.function.TimeCount;


/**
 * 功能: 绑定手机控件
 * */

public class BindPhoneActivity extends AppCompatActivity implements View.OnClickListener {

    private int testTime = 5;
    private Button btnSendCode;
    private Button btnRtn;
    private Button btnSure;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_bind);
        testTime = 0;
        initView();
    }

    /**
    * 点击事件实现:
    * 1. 点击发送验证码
    * 2. 点击返回
    * 3. 点击确定
    * */
    public void onClick(View v) {
        switch (v.getId()) {
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
        sendCode(((EditText)findViewById(R.id.et_phone)).getText().toString());
        TimeCount tc = new TimeCount(60 * 1000, 1000, (Button)findViewById(R.id.btn_send_code),
                "已发送 ", "");//发送验证码之后按钮等待60秒才能再发送
        tc.start();
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
    * 如果验证码和手机匹配, 那么绑定手机
    * 如果不匹配, 提示验证码错误, 错误3次以上罚时
    * */
    private void clickBtnSure() {
        String stu_id = ((EditText)findViewById(R.id.et_stu_ID)).getText().toString();
        String phone = ((EditText)findViewById(R.id.et_phone)).getText().toString();
        String code = ((EditText)findViewById(R.id.et_code)).getText().toString();
        if(correctCode(phone, code)) bindPhone(stu_id, phone);
        else {
            Toast.makeText(getApplicationContext(), "验证码错误",
                    Toast.LENGTH_SHORT).show();
            testTime++;
            if(testTime >= 3) {
                btn_sure_delay((int)Math.pow(2, (testTime-3)));
            }
        }
    }

    /**
    * to do
    * 向目标手机发送验证码
    * */
    private void sendCode(String phone) {

    }

    /**
    * to do
    * 验证是否验证码和手机匹配
    * */
    private boolean correctCode(String stu_ID, String code) {
        return stu_ID.length() == 0 && code.length() == 0;
    }

    /**
    * to do
    * 验证成功后绑定手机
    * */
    private void bindPhone(String stu_id, String phone) {

    }

    /**
    * 多次点击确定失败后罚时
    * */
    private void btn_sure_delay(int waitTime) {
        TimeCount timer = new TimeCount(waitTime * 1000, 1000, (Button)findViewById(R.id.btn_sure),
                "验证失败, 请等待", "秒后重新验证");
        timer.start();
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
        btnRtn = (Button)findViewById(R.id.btn_rtn);
        btnSure = (Button)findViewById(R.id.btn_sure);
        btnSendCode = (Button)findViewById(R.id.btn_send_code);

        btnRtn.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);
    }
}
