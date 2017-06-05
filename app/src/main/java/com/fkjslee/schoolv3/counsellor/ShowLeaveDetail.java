package com.fkjslee.schoolv3.counsellor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.wegit.LoadDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class ShowLeaveDetail extends AppCompatActivity {

    private TextView student,reasons,applyTime;
    private ImageView back;
    private Button agree,reject;
    private NoteForIns noteForIns;//要显示的对象
    /**
     *下面是和是否已经处理请假消息有关的变量
     */
    private int deal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_leave_detail);
        EventBus.getDefault().register(this);
        init();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataGot(HttpEvent httpEvent){    //网络请求的数据回调
        if (httpEvent.getIsError() == 1){
            Toast.makeText(this,"网络请求失败",Toast.LENGTH_LONG).show();
            LoadDialog.dismiss(this);
            return;
        }
        if(httpEvent.getType() == HttpUtil.COUNSELLOR_AGREE_LEAVE){
            LoadDialog.dismiss(this);
            Toast.makeText(this,"已同意",Toast.LENGTH_SHORT).show();
            finish();
        }else if (httpEvent.getType() == HttpUtil.COUNSELLOR_REJECT_LEAVE){
            LoadDialog.dismiss(this);
            Toast.makeText(this,"已拒绝",Toast.LENGTH_SHORT).show();
            finish();
        }

    }

    private void init(){

        Intent intent = getIntent();
        noteForIns = (NoteForIns) intent.getSerializableExtra("note");
        deal = intent.getIntExtra("deal",0);

        student = (TextView)findViewById(R.id.leave_detail_student);
        reasons = (TextView)findViewById(R.id.leave_detail_reasons);
        applyTime = (TextView)findViewById(R.id.leave_detail_starttime);
        /**
         * 设置假条相关内容
         */
        String students = noteForIns.getsName()+"   "+noteForIns.getsId();
        student.setText(students);
        reasons.setText(noteForIns.getContent() +" "+noteForIns.getStartTime()+" "+noteForIns.getEndTime());
        applyTime.setText(noteForIns.getApplyTime());
        back = (ImageView)findViewById(R.id.leave_detail_backspace);

        agree = (Button)findViewById(R.id.leave_detail_agree);
        reject = (Button)findViewById(R.id.leave_detail_reject);

        Onclick onclick = new Onclick();
        back.setOnClickListener(onclick);
        agree.setOnClickListener(onclick);
        reject.setOnClickListener(onclick);

        /**
         * 根据点击的位置不同，设置agree reject是否可见,已处理的请假条不需要再设置同意和拒绝
         */
        if(deal == 1){
            agree.setVisibility(View.INVISIBLE);
            reject.setVisibility(View.INVISIBLE);
        }
    }


    private class Onclick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.leave_detail_backspace:
                    ShowLeaveDetail.this.finish();
                    break;
                case R.id.leave_detail_agree:
                    //同意请假
                    LoadDialog.show(ShowLeaveDetail.this);
                    HttpUtil.Request(HttpUtil.COUNSELLOR_AGREE_LEAVE,Integer.parseInt(noteForIns.getNoteId()));
                    break;
                case R.id.leave_detail_reject:
                    //拒绝请假
                    LoadDialog.show(ShowLeaveDetail.this);
                    HttpUtil.Request(HttpUtil.COUNSELLOR_REJECT_LEAVE,Integer.parseInt(noteForIns.getNoteId()));
                    break;
            }
        }
    }
}
