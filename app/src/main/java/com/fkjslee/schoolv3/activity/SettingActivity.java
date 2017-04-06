package com.fkjslee.schoolv3.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.network.HttpThread;


public class SettingActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case (R.id.btn_rtn) :
                clickBtnRtn();
                break;
            case (R.id.btn_uploadPhoto) :
                clickBtnUploadPhoto();
                break;
        }
    }

    private void clickBtnGetSchedule() {
        SharedPreferences.Editor editor = getSharedPreferences("lock", MODE_WORLD_WRITEABLE).edit();

        //假定的数据
//        String schedule = "[{\"teacher\":\"\", \"name\":\"math\", \"week\":\"1 2\", \"weekday\":\"5\", " +
//                "\"periodbegin\":\"1\", \"periodlength\":\"2\", \"classroom\":\"A1208\"}, " +
//                "{\"teacher\":\"汪哈\", \"name\":\"英语\", \"week\":\"3 4\", \"weekday\":\"1\", " +
//                "\"periodbegin\":\"5\", \"periodlength\":\"2\", \"classroom\":\"A1208\"}]";


        //从服务器获得的数据
        String url = "http://119.29.241.101:8080/MyServlet/MainServlet";
        String param = "type=class&name=" + LogActivity.logAccount + "&password=" + LogActivity.logPwd;
        //String param = "type=picture&msg=" + picture;
        HttpThread httpThread = new HttpThread(url, param);
        new Thread(httpThread).start();
        String schedule = httpThread.getResult();
        editor.putString("code", schedule);
        editor.commit();
        Toast.makeText(getApplicationContext(), "获取课表 : " + schedule, Toast.LENGTH_SHORT).show();
    }

    private void clickBtnRtn() { finish(); }

    private void clickBtnUploadPhoto() {
        Intent intent = new Intent(this, GetPhotoActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        startActivity(intent);
    }

}
