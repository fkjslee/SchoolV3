package com.fkjslee.schoolv3.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.LeaveContent;
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

public class ShowStuLeave extends AppCompatActivity {

    private TextView student,reasons,startTime,endTime,stuNum;
    private ImageView back;
    private Button sure;
    private LeaveContent leaveContent;//要显示的对象
    /**
     *下面是和是否已经处理请假消息有关的变量
     */
    private int deal = 0;
    private String leaveNoteId;
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
        student = (TextView)findViewById(R.id.stu_leave_student);
        stuNum=(TextView)findViewById(R.id.stu_leave_studentnum);
        reasons = (TextView)findViewById(R.id.stu_leave_reasons);
        startTime = (TextView)findViewById(R.id.stu_leave_starttime);
        endTime = (TextView)findViewById(R.id.stu_leave_endtime);
        leaveNoteId=leaveContent.leaveNoteId;
        /**
         * 设置假条相关内容
         */
        String students = leaveContent.studentName+"   "+leaveContent.studentNumber;
        student.setText(students);
        reasons.setText(leaveContent.reasons);
        startTime.setText(leaveContent.startTime);
        endTime.setText(leaveContent.endTime);
        back = (ImageView)findViewById(R.id.stu_leave_backspace);


        Onclick onclick = new Onclick();
        back.setOnClickListener(onclick);
        sure.setOnClickListener(onclick);

        /**
         * 根据点击的位置不同，设置agree reject是否可见,已处理的请假条不需要再设置同意和拒绝
         */
    }


    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.stu_leave_backspace:
                    ShowStuLeave.this.finish();
                    break;
                case R.id.stu_leave_sure:
                    leaveContent.deal = 1;
                    leaveContent.pass = 1;
                   /* Database.updateLeave(leaveContent,"leaves");
                    Database.close();*/
                    //添加向服务器发送数据
                    ShowStuLeave.this.finish();
                    break;
            }
        }
    }

    private void sendMsgToserver(String leaveNoteId){
        String param = "type=TeacherSeeLeaveNote&telephone=" + "" + "&LeaveNoteId"+leaveNoteId;
        String result = MyCommonFunction.sendRequestToServer(param);
    }

}
