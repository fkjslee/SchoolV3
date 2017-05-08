package com.fkjslee.schoolv3.student.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.CounsellorLeaveActivty;
import com.fkjslee.schoolv3.student.fragment.Fragment_discuss;
import com.fkjslee.schoolv3.student.fragment.Fragment_leave;
import com.fkjslee.schoolv3.student.fragment.Fragment_schedule;


public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, View.OnClickListener, PopupMenu.OnMenuItemClickListener {

    private Fragment_schedule fragment_schedule = new Fragment_schedule();
    private Fragment_leave fragment_leave = new Fragment_leave();
    private Fragment_discuss fragment_discuss = new Fragment_discuss();
    private Button btnSetting;
    private PopupMenu popupMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);
        initView();
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
            case R.id.btn_leave:
                transaction.replace(R.id.top_layout, fragment_leave);
                transaction.commit();
                break;
            case R.id.btn_discuss:
                transaction.replace(R.id.top_layout, fragment_discuss);
                transaction.commit();
                break;
            case R.id.btn_setting:
                popupMenu = new PopupMenu(this, v);
                popupMenu.getMenuInflater().inflate(R.menu.setting, popupMenu.getMenu());
                popupMenu.setOnMenuItemClickListener(this);
                popupMenu.show();
        }
    }

    private void initView() {
        btnSetting = (Button)findViewById(R.id.btn_setting);

        btnSetting.setOnClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
