package com.fkjslee.schoolv3;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.fkjslee.schoolv3.counsellor.CounsellorLeaveActivty;
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.student.activity.FgtPwdActivity;
import com.fkjslee.schoolv3.student.activity.MainActivity;
import com.fkjslee.schoolv3.student.activity.RegisterActivity;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.student.function.TimeCount;
import com.fkjslee.schoolv3.teacher.activity.TeacherActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * 1. 点击登录 成功则进入主界面, 失败3次有罚时<br>
 * 2. 点击退出, 退出应用<br>
 * 3. 点击忘记密码, 跳到忘记密码界面<br>
 * 4. 点击绑定手机, 跳到绑定手机界面
 */

public class LogActivity extends AppCompatActivity implements View.OnClickListener {

    public static Context context;
    public static String logAccount = "22222";
    public static String logPwd = "123456";
    public static String url = "http://119.29.241.101:8080/MyServlet/MainServlet";//服务器ip
    //    public static String url = "http://100.56.101.29:8080/MyServlet/MainServlet";
    public static Calendar calFirstDay;

    public static String loginIdentity = "学生";
    private int testTime = 0;
    private long mExitTime = -2005;

    private Button btnLog;
    private Button btnQuit;
    private Button btnFgtPwd;
    private Button btnBind;
    private EditText etStuId;
    private EditText etPwd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_log);
        testTime = 0;

        Database.initDatabase(getApplicationContext());
        initView();
    }

    /**
     * 点击事件:<br>1:登录<br>2:退出<br>3:忘记密码<br>4:绑定手机
     *
     * @param v 控件
     */
    public void onClick(View v) {
        Class<? extends android.app.Activity> activityClass = null;
        if (R.id.btn_fgt_pwd == v.getId()) activityClass = FgtPwdActivity.class;
        if (R.id.btn_bind == v.getId()) activityClass = RegisterActivity.class;
        if (null != activityClass) {
            startActivity(new Intent(this.getApplicationContext(), activityClass));
            return;
        }
        if (R.id.btn_log == v.getId()) clickBtnLog();
        if (R.id.btn_rtn == v.getId()) clickBtnQuit();
    }

    /**
     * 功能: 点击"登录"后的具体实现逻辑<br>
     * 正确则进入主界面, 错误给出提醒, 3次以上罚时, 罚时等于60秒
     */
    private void clickBtnLog() {
        String result = checkPWD(etStuId.getText().toString(), etPwd.getText().toString());
        if (result.equals("学生")) {
            logAccount = etStuId.getText().toString();
            logPwd = etPwd.getText().toString();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        } else if (result.equals("辅导员")) {
            logAccount = etStuId.getText().toString();
            logPwd = etPwd.getText().toString();
            startActivity(new Intent(getApplicationContext(), CounsellorLeaveActivty.class));
        } else if (result.equals("老师")) {
            logAccount = etStuId.getText().toString();
            logPwd = etPwd.getText().toString();
            startActivity(new Intent(getApplicationContext(), TeacherActivity.class));
        } else {
            Toast.makeText(getApplicationContext(), "密码错误",
                    Toast.LENGTH_SHORT).show();
            testTime++;
            if (testTime >= 3) {
                btn_log_delay((int) Math.pow(2, (testTime - 3)));
            }
        }
    }

    /**
     * 功能: 点击"退出"后的具体实现,
     * 返回值: 无
     * 参数: 无
     * 调用android的api
     */
    private void clickBtnQuit() {
        finish();
    }

    /**
     * 功能: 按钮延时
     * 返回值: 无
     * 参数: 需要延迟的时间
     * 错误输入密码后罚时登录, 使用继承与CountDownTimer的TimeCount类实现及时功能,
     * 重写了每次显示过程和及时完毕的过程
     */
    private void btn_log_delay(int waitTime) {
        TimeCount timer = new TimeCount(waitTime * 1000, 1000, (Button) findViewById(R.id.btn_log),
                "验证失败, 请等待", "秒后重新验证");
        timer.start();
    }

    /**
     * to do
     * 功能: 检查密码是否正确
     * 返回值: boolean, 密码正确
     * 参数, String, 学生的学号, String, 学生的密码
     */
    private String checkPWD(String phone, String pwd) {
        String param = "type=login&telephone=" + phone + "&pwd=" + pwd;
        String result = MyCommonFunction.sendRequestToServer(param);
        loginIdentity = result;
        Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        switch (result) {
            case "学生":
                return "学生";
            case "辅导员":
                return "辅导员";
            case "老师":
                return "老师";
            default:
                Toast.makeText(this, "账号或者密码错误", Toast.LENGTH_SHORT).show();
                return "";
        }
    }

    /**
     * 两次点击返回退出程序
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
                mExitTime = System.currentTimeMillis();
            } else {
                clickBtnQuit();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    //控件初始化
    private void initView() {
        context = getApplicationContext();
        calFirstDay = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse("2017-2-20");
            calFirstDay.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        btnLog = (Button) findViewById(R.id.btn_log);
        btnQuit = (Button) findViewById(R.id.btn_quit);
        btnFgtPwd = (Button) findViewById(R.id.btn_fgt_pwd);
        btnBind = (Button) findViewById(R.id.btn_bind);
        etStuId = (EditText) findViewById(R.id.et_stu_ID);
        etPwd = (EditText) findViewById(R.id.et_pwd);

        etStuId.setText(LogActivity.logAccount);
        etPwd.setText(LogActivity.logPwd);

        btnLog.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
        btnFgtPwd.setOnClickListener(this);
        btnBind.setOnClickListener(this);

    }
}
