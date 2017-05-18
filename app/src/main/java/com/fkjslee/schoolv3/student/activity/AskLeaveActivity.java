package com.fkjslee.schoolv3.student.activity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

public class AskLeaveActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int askLeaveReault = 0;

    private static Handler handler;

    private Button btnStartTimeSetDate;
    private Button btnStartTimeSetTime;
    private Button btnEndTimeSetDate;
    private Button btnEndTimeSetTime;
    private Button btnSure;
    private EditText etReason;

    private Integer stYear, stMonth, stDay, stHour, stMinute;
    private Integer etYear, etMonth, etDay, etHour, etMinute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ask_leave);
        initView();
    }

    private void initView() {
        btnStartTimeSetDate = (Button) findViewById(R.id.btn_startTimeSetDate);
        btnStartTimeSetTime = (Button) findViewById(R.id.btn_startTimeSetTime);
        btnEndTimeSetDate = (Button) findViewById(R.id.btn_endTimeSetDate);
        btnEndTimeSetTime = (Button) findViewById(R.id.btn_endTimeSetTime);
        btnSure = (Button)findViewById(R.id.btn_sure);
        etReason = (EditText)findViewById(R.id.et_reason);

        btnStartTimeSetDate.setOnClickListener(this);
        btnStartTimeSetTime.setOnClickListener(this);
        btnEndTimeSetDate.setOnClickListener(this);
        btnEndTimeSetTime.setOnClickListener(this);
        btnSure.setOnClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case askLeaveReault:
                        Toast.makeText(getApplicationContext(), msg.toString(), Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) finish();
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_startTimeSetDate:
                clickBtnStartTimeSetDate();
                break;
            case R.id.btn_startTimeSetTime:
                clickBtnStartTimeSetTime();
                break;
            case R.id.btn_endTimeSetDate:
                clickBtnEndTimeSetDate();
                break;
            case R.id.btn_endTimeSetTime:
                clickBtnEndTimeSetTime();
                break;
            case R.id.btn_sure:
                clickBtnSure();
                break;
        }
    }

    private String askLeave() {
        String param = "type=askForLeave" +
                "&telephone=" + LogActivity.logAccount +
                "&content=" + etReason.getText().toString() +
                "&startTime=" + stYear.toString() + "-" + stMonth.toString() + "-" + stDay.toString() + " " +
                stHour.toString() + ":" + stMinute.toString() + ":00" +
                "&endTime=" + etYear.toString() + "-" + etMonth.toString() + "-" + etDay.toString() + " " +
                etHour.toString() + ":" + etMinute.toString() + ":00";
        return MyCommonFunction.sendRequestToServer(param);
    }

    private void clickBtnSure() {
        String set = "设置";
        if(btnStartTimeSetDate.getText().toString().substring(0, 2).equals(set) ||
                btnStartTimeSetTime.getText().toString().substring(0, 2).equals(set) ||
                btnEndTimeSetDate.getText().toString().substring(0, 2).equals(set) ||
                btnEndTimeSetTime.getText().toString().substring(0, 2).equals(set)) {
            Toast.makeText(this, "请输入正确时间", Toast.LENGTH_SHORT).show();
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Message message = new Message();
                message.what = askLeaveReault;
                message.obj = askLeave();
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();
    }

    private void clickBtnStartTimeSetDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                stYear = year;
                stMonth = monthOfYear;
                stDay = dayOfMonth;
                btnStartTimeSetDate.setText(stYear.toString() + "-" + stMonth.toString() + "-" +
                        stDay.toString());
            }
        }, 2017, 1, 1).show();
    }

    private void clickBtnStartTimeSetTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                stHour = hourOfDay;
                stMinute = minute;
                btnStartTimeSetTime.setText(stHour.toString() + "-" + stMinute.toString());
            }
        }, 0, 0, true).show();
    }

    private void clickBtnEndTimeSetDate() {
        new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etYear = year;
                etMonth = monthOfYear;
                etDay = dayOfMonth;
                btnEndTimeSetDate.setText(etYear.toString() + "-" + etMonth.toString() + "-" +
                        etDay.toString());
            }
        }, 2017, 1, 1).show();
    }

    private void clickBtnEndTimeSetTime() {
        new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                etHour = hourOfDay;
                etMinute = minute;
                btnEndTimeSetTime.setText(etHour.toString() + "-" + etMinute.toString());
            }
        }, 0, 0, true).show();
    }
}
