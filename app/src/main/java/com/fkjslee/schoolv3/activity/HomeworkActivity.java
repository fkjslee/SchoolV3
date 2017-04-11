package com.fkjslee.schoolv3.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.AssumeData2;
import com.fkjslee.schoolv3.piece.PieceHomework;


/**
 * 作业页面
 * 通过在课程详情里面点击"作业"进入
 * */

public class HomeworkActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        initView();

        LinearLayout ll_homework = (LinearLayout)findViewById(R.id.ll_homework);
        PieceHomework hf1 = new PieceHomework(getBaseContext());
        hf1.setHomeworkData(AssumeData2.getHomeworkData("", "", ""));
        ll_homework.addView(hf1);
        PieceHomework hf2 = new PieceHomework(getBaseContext());
        hf2.setHomeworkData(AssumeData2.getHomeworkData("1", "", ""));
        ll_homework.addView(hf2);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_rtn:
                clickBtnRtn();
                break;
        }
    }

    private void clickBtnRtn() {
        finish();
    }

    private void initView() {
        btnRtn = (Button)findViewById(R.id.btn_rtn);

        btnRtn.setOnClickListener(this);
    }
}
