package com.fkjslee.schoolv3.teacher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.PopupMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.CompleteMsgActivity;
import com.fkjslee.schoolv3.student.fragment.Fragment_discuss;
import com.fkjslee.schoolv3.student.fragment.Fragment_leave;
import com.fkjslee.schoolv3.teacher.Fragment.FragmentTeacherSchedule;
import com.fkjslee.schoolv3.teacher.Fragment.TeacherLeave;

public class TeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    FragmentTeacherSchedule fragment_schedule = new FragmentTeacherSchedule();
    TeacherLeave fragment_leave = new TeacherLeave();
    Fragment_discuss fragment_discuss = new Fragment_discuss();
    private Button btnSetting;
    private PopupMenu popupMenu;
    private Button btnSchedule;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导航栏
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    private void initView() {
        btnSetting = (Button)findViewById(R.id.btn_setting1);
        btnSchedule = (Button)findViewById(R.id.btn_schedule);

        btnSchedule.setOnClickListener(this);
        onClick(findViewById(R.id.btn_schedule));
        //btnSetting.setOnClickListener(this);
    }
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //填充菜单
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.teacher, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    //导航栏 侧显示栏
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.btn_schedule:
                FragmentManager fragmentManager1 = getFragmentManager();
                FragmentTransaction transaction = fragmentManager1.beginTransaction();
                fragment_schedule = new FragmentTeacherSchedule();
                transaction.replace(R.id.top_layout, fragment_schedule);
                transaction.commit();
                break;
            case R.id.btn_leave:
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
                fragment_leave=new TeacherLeave();
                transaction2.replace(R.id.top_layout, fragment_leave);
                transaction2.commit();
                break;
            case R.id.btn_discuss:
                FragmentTransaction transaction3 = getFragmentManager().beginTransaction();
                fragment_discuss = new Fragment_discuss();
                transaction3.replace(R.id.top_layout, fragment_discuss);
                transaction3.commit();
                break;
        }
    }

}
