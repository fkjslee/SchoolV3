package com.fkjslee.schoolv3.teacher;

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

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.CompleteMsgActivity;
import com.fkjslee.schoolv3.teacher.fragment.FragmentTeacherLeave;
import com.fkjslee.schoolv3.teacher.fragment.FragmentTeacherSchedule;

public class TeacherActivity extends AppCompatActivity
        implements View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private FragmentTeacherSchedule fragment_schedule = new FragmentTeacherSchedule();
    private FragmentTeacherLeave fragment_leave = new FragmentTeacherLeave();
    private Button btnSchedule;
    private Button btnLeave;
    private ImageView ivSetting;
    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_teacher);
        initView();
    }

    private void initView() {
        btnSchedule = (Button)findViewById(R.id.btn_schedule);
        btnLeave = (Button)findViewById(R.id.btn_teacher_leave);
        ivSetting = (ImageView)findViewById(R.id.iv_teacher_setting);

        btnSchedule.setOnClickListener(this);
        btnLeave.setOnClickListener(this);
        ivSetting.setOnClickListener(this);
        ivSetting.bringToFront();
        onClick(findViewById(R.id.btn_schedule));
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(v.getId()) {
            case R.id.btn_schedule:
                transaction.replace(R.id.top_layout, fragment_schedule);
                transaction.commit();
                break;
            case R.id.btn_teacher_leave:
                transaction.replace(R.id.top_layout, fragment_leave);
                transaction.commit();
                break;
            case R.id.iv_teacher_setting:
                popupMenu = new PopupMenu(this, v);
                popupMenu.getMenuInflater().inflate(R.menu.setting, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
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
}
