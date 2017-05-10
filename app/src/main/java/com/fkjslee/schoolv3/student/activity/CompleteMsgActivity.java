package com.fkjslee.schoolv3.student.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

public class CompleteMsgActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText etNickname;
    private EditText etSid;
    private EditText etPwd;
    private Button btnUploadPhoto;
    private Button btnSubmit;
    private ImageView ivRtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_msg);
        initView();
    }

    private void initView() {
        etNickname = (EditText)findViewById(R.id.et_nickName);
        etSid = (EditText)findViewById(R.id.et_stu_ID);
        etPwd = (EditText)findViewById(R.id.et_pwd);
        btnUploadPhoto = (Button) findViewById(R.id.btn_uploadPhoto);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        ivRtn = (ImageView)findViewById(R.id.iv_rtn);

        btnUploadPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
        ivRtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_rtn:
                finish();
                break;
            case R.id.btn_uploadPhoto:
                startActivity(new Intent(this, GetPhotoActivity.class));
                break;
            case R.id.btn_submit:
                String param = "type=bind&telephone=" + LogActivity.logAccount +
                        "&sName=" + etSid.getText().toString() +
                        "&sPwd=" + etPwd.getText().toString();
                String result = MyCommonFunction.sendRequestToServer(param);
                Toast.makeText(this, "上传成功 ? " + result, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finish();
        return super.onKeyDown(keyCode, event);
    }
}
