package com.fkjslee.schoolv3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;

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

    private void clickBtnRtn() { finish(); }

    private void clickBtnUploadPhoto() {
        Intent intent = new Intent(this, GetPhoto.class);
        startActivityForResult(intent, 1);
    }


    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 1:
                Bitmap photo = GetPhoto.photo;

                String url = LogActivity.url;
//                String param = "type=class&name=" + LogActivity.logAccount + "&password=" + LogActivity.logPwd;
                String param = "type=img&msg=" + GetPhoto.bitmapToString(photo);
                HttpThread httpThread = new HttpThread(url, param);
                new Thread(httpThread).start();

                GetPhoto.photo = null;
                break;
            default:
                break;
        }
    }
}
