package com.fkjslee.schoolv3.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.fragment.Fragment_discuss;
import com.fkjslee.schoolv3.fragment.Fragment_leave;
import com.fkjslee.schoolv3.fragment.Fragment_schedule;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private Spinner spinnerMenu;

    Fragment_schedule fragment_schedule = new Fragment_schedule();
    Fragment_leave fragment_leave = new Fragment_leave();
    Fragment_discuss fragment_discuss = new Fragment_discuss();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        initView();
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

    /**
    * 点击返回后返回到登录界面
    * */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void initView() {
        spinnerMenu = (Spinner)findViewById(R.id.spinner_menu);
//
//        List<String> list = new ArrayList<>();
//        for(int i = 1; i <= maxWeek; ++i) {
//            list.add(Integer.toString(i));
//        }
//        weekAdapter = new ArrayAdapter<>(parentView.getContext(),
//                android.R.layout.simple_spinner_item, list);
//        Integer nowWeek = getSpinnerWeek();
//        spinnerMenu = (Spinner)parentView.findViewById(R.id.spinner);
//        spinnerMenu.setAdapter(weekAdapter);
//        if(nowWeek != null) {
//            spinner.setSelection(nowWeek - 1, true);
//            spinnerWeek = nowWeek;
//            setSchedulePosition();
//        }
//        spinner.setOnItemSelectedListener(this);
//        spinner.setDropDownVerticalOffset(30);
    }

    private void clickBtnRtn() {
        finish();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        /*toDetailActivity(position);*/
    }
}
