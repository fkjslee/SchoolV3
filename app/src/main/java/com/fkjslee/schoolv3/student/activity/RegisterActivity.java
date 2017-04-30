package com.fkjslee.schoolv3.student.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.student.function.SendSms;
import com.fkjslee.schoolv3.student.function.TimeCount;

import java.util.ArrayList;


/**
 * 功能: 绑定手机控件
 * */

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {

    private int testTime = 5;
    private Button btnSendCode;
    private ImageView ivRtn;
    private Button btnSure;
    private EditText etPhone;
    private EditText etCode;
    private EditText etPwd;
    private EditText etRepeatPwd;
    private Spinner spinnerChooseIdentity;
    private int mobileCode;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
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
            case R.id.iv_rtn:
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
        if(etPhone.getText().toString().equals("")) {
            Toast.makeText(getApplicationContext(), "请输入手机号再点击发送验证码", Toast.LENGTH_SHORT).show();
            return;
        }
        sendCode(etPhone.getText().toString());
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
        String phone = etPhone.getText().toString();
        String code = etCode.getText().toString();
        if(!correctCode(code)) {
            Toast.makeText(getApplicationContext(), "验证码错误",
                    Toast.LENGTH_SHORT).show();
        }
        else if(!etPwd.getText().toString().equals(etRepeatPwd.getText().toString())) {
            Toast.makeText(getApplicationContext(), "两次密码不一致",
                    Toast.LENGTH_SHORT).show();
        }
        else {
            register(phone, etPwd.getText().toString(), spinnerChooseIdentity.getSelectedItem().toString());
        }
    }

    /**
    * to do
    * 向目标手机发送验证码
    * */
    private void sendCode(final String phone) {
        mobileCode = (int)((Math.random()*9+1)*100000);
        new Thread(new Runnable() {
            @Override
            public void run() {
                new SendSms().Sendsms(phone,mobileCode);
            }
        }).start() ;
    }

    /**
    * to do
    * 验证是否验证码和手机匹配
    * */
    private boolean correctCode(String code) {
        if(true == true) return true;
        return Integer.parseInt(code) == mobileCode;
    }

    /**
    * to do
    * 验证成功后绑定手机
    * */
    private void register(String phone, String pwd, String identity) {
        String param = "type=signUp&phone=" + phone + "&pwd=" + pwd + "&identity=" + identity;
        String result = MyCommonFunction.sendRequestToServer(param);
        if(result.equals("Yes")) {
            Toast.makeText(this, "注册成功", Toast.LENGTH_SHORT).show();
            finish();
        }
        else
            Toast.makeText(this, "注册失败", Toast.LENGTH_SHORT).show();
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        ivRtn = (ImageView)findViewById(R.id.iv_rtn);
        btnSure = (Button)findViewById(R.id.btn_sure);
        btnSendCode = (Button)findViewById(R.id.btn_send_code);
        etCode = (EditText)findViewById(R.id.et_code);
        etPhone = (EditText)findViewById(R.id.et_phone);
        etPwd = (EditText) findViewById(R.id.et_pwd);
        etRepeatPwd = (EditText) findViewById(R.id.et_rePwd);
        spinnerChooseIdentity = (Spinner) findViewById(R.id.spinner_choose_identity);

        ivRtn.setOnClickListener(this);
        btnSure.setOnClickListener(this);
        btnSendCode.setOnClickListener(this);


        ArrayList<String> list = new ArrayList<>();
        list.add("学生");
        list.add("老师");
        list.add("辅导员");
        ArrayAdapter<String> identity = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_item, list);
        spinnerChooseIdentity.setAdapter(identity);
        spinnerChooseIdentity.setOnItemSelectedListener(this);
        spinnerChooseIdentity.setDropDownVerticalOffset(30);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
