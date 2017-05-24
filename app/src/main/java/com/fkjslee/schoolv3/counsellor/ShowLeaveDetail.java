package com.fkjslee.schoolv3.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.database.Database;

public class ShowLeaveDetail extends AppCompatActivity {

    private TextView student,reasons,startTime,endTime;
    private ImageView back;
//    private Button agree,reject;
    private LeaveContent leaveContent;//要显示的对象
    /**
     *下面是和是否已经处理请假消息有关的变量
     */
    private int deal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_leave_detail);
        init();
    }


    private void init(){

        Intent intent = getIntent();
        leaveContent = (LeaveContent) intent.getSerializableExtra("leaveContent");
        deal = intent.getIntExtra("deal",0);

        student = (TextView)findViewById(R.id.leave_detail_student);
        reasons = (TextView)findViewById(R.id.leave_detail_reasons);
        startTime = (TextView)findViewById(R.id.leave_detail_starttime);
        endTime = (TextView)findViewById(R.id.leave_detail_endtime);
        /**
         * 设置假条相关内容
         */
        String students = leaveContent.studentName+"   "+leaveContent.studentNumber;
        student.setText(students);
        reasons.setText(leaveContent.reasons);
        startTime.setText(leaveContent.startTime);
        endTime.setText(leaveContent.endTime);
        back = (ImageView)findViewById(R.id.leave_detail_backspace);

//        agree = (Button)findViewById(R.id.leave_detail_agree);
//        reject = (Button)findViewById(R.id.leave_detail_reject);

        Onclick onclick = new Onclick();
        back.setOnClickListener(onclick);
//        agree.setOnClickListener(onclick);
//        reject.setOnClickListener(onclick);

        /**
         * 根据点击的位置不同，设置agree reject是否可见,已处理的请假条不需要再设置同意和拒绝
         */
        if(deal == 1){
//            agree.setVisibility(View.INVISIBLE);
//            reject.setVisibility(View.INVISIBLE);
        }
    }


    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.leave_detail_backspace:
                    ShowLeaveDetail.this.finish();
                    break;
//                case R.id.leave_detail_agree:
//                    leaveContent.deal = 1;
//                    leaveContent.pass = 1;
//                    Database.updateLeave(leaveContent,"leaves");
//                    Database.close();
//                    ShowLeaveDetail.this.finish();
//                    break;
//                case R.id.leave_detail_reject:
//                    leaveContent.deal = 1;
//                    leaveContent.pass = 0;
//                    Database.updateLeave(leaveContent,"leaves");
//                    Database.close();
//                    ShowLeaveDetail.this.finish();
//                    break;
            }
        }
    }
}
