package com.fkjslee.schoolv3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.MsgClass;
import com.fkjslee.schoolv3.function.CheckPermissionsActivity;

import java.io.File;


/**
 * 签到界面
 * 通过classDetail界面进入
 * */
public class SignActivity extends CheckPermissionsActivity implements View.OnClickListener{

    GouldMapLocation gouldMapLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        gouldMapLocation = new GouldMapLocation(this.getApplicationContext());

        MsgClass msg = (MsgClass) getIntent().getSerializableExtra("classMsg");

        TextView tv_class = (TextView)findViewById(R.id.tv_class);
        TextView tv_signState = (TextView)findViewById(R.id.tv_signState);
        tv_class.setText(msg.getName());
        tv_signState.setText(hasSign()? "已签到" : "未签到");

        ((Button)findViewById(R.id.btn_getPosition)).setOnClickListener(this);

    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_getPosition:
                clickBtnGetPosition();
                break;
            case R.id.btn_rtn:
                clickBtnRtn();
                break;
            case R.id.btn_submitPhoto:
                takePhoto();
                break;
            case R.id.btn_showPhoto:
                showPhoto();
                break;
        }
    }

    private void takePhoto() {
        Log.v("takePhoto", "there");
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File out = new File(Environment.getExternalStorageDirectory(),
                "camera.jpg");
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0);
        Log.v("takePhoto", "here");
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    private void showPhoto() {
        Log.i("SignActivity", "showPhoto");
        ImageView view = (ImageView) findViewById(R.id.showPhoto);
        String pathString = Environment.getExternalStorageDirectory()
                .toString() + "/camera.jpg";
        Bitmap b = BitmapFactory.decodeFile(pathString);
        view.setImageBitmap(b);
    }

    private boolean hasSign() {
        return true;
    }

    private void clickBtnRtn() { finish(); }

    private void clickBtnGetPosition() {
        gouldMapLocation.startLocation();
        String result = gouldMapLocation.getLocation();
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
    }

}
