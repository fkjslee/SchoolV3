package com.fkjslee.schoolv3.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.GetPhotoActivity;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

public class CompleteMsgActivityTeacher extends AppCompatActivity implements View.OnClickListener {

    private EditText etRealName;
    private EditText etAcadamy;
    private Button btnSubmit;
    private ImageView ivRtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_msg_teacher);
        initView();
    }

    private void initView() {
        etRealName = (EditText) findViewById(R.id.et_realName);
        etAcadamy = (EditText) findViewById(R.id.et_academy);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        ivRtn = (ImageView)findViewById(R.id.iv_rtn);

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
                String param = "type=bind"  +
                        "&telephone=" + LogActivity.logAccount +
                        "&sID=1" +
                        "&sPwd=1" +
                        "&userName=" + etRealName.getText().toString() +
                        "&academy=" + etAcadamy.getText().toString();
                String result = MyCommonFunction.sendRequestToServer(param);
                String show;
                switch (result) {
                    case "yes":
                        show = "绑定成功";
                        finish();
                        break;
                    case "no":
                        show = "绑定失败, 检查信息 ? 该信息已经被绑定?";
                        break;
                    default:
                        show = "错误";
                        break;
                }
                Toast.makeText(this, show, Toast.LENGTH_SHORT).show();
        }
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finish();
        return super.onKeyDown(keyCode, event);
    }
}
