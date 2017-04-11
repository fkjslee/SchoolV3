package com.fkjslee.schoolv3.teacher;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.fragment.Fragment_discuss;
import com.fkjslee.schoolv3.fragment.Fragment_leave;
import com.fkjslee.schoolv3.fragment.Fragment_schedule;

public class TeacherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    Fragment_schedule fragment_schedule = new Fragment_schedule();
    Fragment_leave fragment_leave = new Fragment_leave();
    Fragment_discuss fragment_discuss = new Fragment_discuss();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        //导航栏
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
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
                fragment_schedule = new Fragment_schedule();
                transaction.replace(R.id.top_layout, fragment_schedule);
                transaction.commit();
                break;
            case R.id.btn_leave:
                fragment_leave = new Fragment_leave();
                FragmentManager fragmentManager2 = getFragmentManager();
                FragmentTransaction transaction2 = fragmentManager2.beginTransaction();
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
