package com.fkjslee.schoolv3.teacher.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.CompleteMsgActivity;
import com.fkjslee.schoolv3.teacher.fragment.FragmentTeacherLeave;
import com.fkjslee.schoolv3.teacher.fragment.FragmentTeacherMine;
import com.fkjslee.schoolv3.teacher.fragment.FragmentTeacherSchedule;

public class TeacherActivity extends AppCompatActivity
        implements View.OnClickListener, PopupMenu.OnMenuItemClickListener, FragmentTeacherMine.FragmentTeacherMineListener {

    private FragmentTeacherSchedule fragment_schedule = new FragmentTeacherSchedule();
    private FragmentTeacherLeave fragment_leave = new FragmentTeacherLeave();
    private FragmentTeacherMine fragmentMine = new FragmentTeacherMine();
    private LinearLayout llSchedule;
    private LinearLayout llLeave;
    private LinearLayout llMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView() {
        llSchedule = (LinearLayout)findViewById(R.id.ll_schedule);
        llLeave = (LinearLayout)findViewById(R.id.ll_leave);
        llMine = (LinearLayout)findViewById(R.id.ll_mine);

        llSchedule.setOnClickListener(this);
        llLeave.setOnClickListener(this);
        llMine.setOnClickListener(this);
        onClick(findViewById(R.id.ll_schedule));
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(v.getId()) {
            case R.id.ll_schedule:
                transaction.replace(R.id.top_layout, fragment_schedule);
                transaction.commit();
                break;
            case R.id.ll_leave:
                transaction.replace(R.id.top_layout, fragment_leave);
                transaction.commit();
                break;
            case R.id.ll_mine:
                transaction.replace(R.id.top_layout, fragmentMine);
                transaction.commit();
        }
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_completeMsg:
                startActivity(new Intent(this, CompleteMsgActivity.class));
                break;
            case R.id.item_changeAcount:
                this.finish();
                break;
        }
        return true;
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startCompleteMsg() {
        startActivity(new Intent(this, CompleteMsgActivity.class));
    }
}
