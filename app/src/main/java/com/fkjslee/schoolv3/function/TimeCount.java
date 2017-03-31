package com.fkjslee.schoolv3.function;

import android.os.CountDownTimer;
import android.widget.Button;

/**
 * Created by fkjslee on 2017/2/17.
 * 定义一个倒计时的类
 */
public class TimeCount extends CountDownTimer {
    private Button btn;
    private String record;
    private String front;
    private String back;
    public TimeCount(long millisInFuture, long countDownInterval, Button btn,
                     String front, String back) {
        super(millisInFuture, countDownInterval);//参数依次为总时长,和计时的时间间隔
        this.btn = btn;
        record = btn.getText().toString();
        this.front = front;
        this.back = back;
    }

    @Override
    public void onFinish() {//计时完毕时触发
        btn.setText(record);
        btn.setClickable(true);
    }

    @Override
    public void onTick(long millisUntilFinished) {//计时过程显示
        btn.setClickable(false);
        btn.setText(front + millisUntilFinished / 1000 + back);
    }
}