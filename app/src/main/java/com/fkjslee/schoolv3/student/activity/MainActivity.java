package com.fkjslee.schoolv3.student.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.fragment.FragmentMine;
import com.fkjslee.schoolv3.student.fragment.FragmentNews;
import com.fkjslee.schoolv3.student.fragment.Fragment_leave;
import com.fkjslee.schoolv3.student.fragment.Fragment_schedule;


public class MainActivity extends AppCompatActivity
        implements View.OnClickListener, FragmentMine.FragmentMineListener{

    private Fragment_schedule fragment_schedule = new Fragment_schedule();
    private Fragment_leave fragment_leave = new Fragment_leave();
    private FragmentNews fragmentNews = new FragmentNews();
    private FragmentMine fragmentMine = new FragmentMine();
    private PopupMenu popupMenu;
    private LinearLayout llSchedule;
    private LinearLayout llLeave;
    private LinearLayout llNews;
    private LinearLayout llMine;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        llSchedule = (LinearLayout)findViewById(R.id.ll_schedule);
        llLeave = (LinearLayout)findViewById(R.id.ll_leave);
        llNews = (LinearLayout)findViewById(R.id.ll_news);
        llMine = (LinearLayout)findViewById(R.id.ll_mine);

        llSchedule.setOnClickListener(this);
        llLeave.setOnClickListener(this);
        llNews.setOnClickListener(this);
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
            case R.id.ll_news:
                transaction.replace(R.id.top_layout, fragmentNews);
                transaction.commit();
                break;
            case R.id.ll_mine:
                transaction.replace(R.id.top_layout, fragmentMine);
                transaction.commit();
                break;
        }
    }

    @Override
    public void finishActivity() {
        finish();
    }

    @Override
    public void startCompleteMsg() {
        startActivity(new Intent(this, CompleteMsgActivity.class));
    }

    @Override
    public void quit() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }
}
