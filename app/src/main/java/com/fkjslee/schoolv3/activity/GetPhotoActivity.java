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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fkjslee.schoolv3.R;

import java.io.File;


public class GetPhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRtn;
    private Button btnSubmitPhoto;
    private Button btnShowPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_photo);

        initView();
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case(R.id.btn_rtn) :
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

    private void showPhoto() {
        Log.i("SignActivity", "showPhoto");
        ImageView view = (ImageView) findViewById(R.id.showPhoto);
        String pathString = Environment.getExternalStorageDirectory()
                .toString() + "/camera.jpg";
        Log.v("", "pathString = " + pathString);
        Bitmap b = BitmapFactory.decodeFile(pathString);
        view.setImageBitmap(b);
    }

    private void clickBtnRtn() { finish(); }

    private void initView() {
        btnRtn = (Button)findViewById(R.id.btn_rtn);
        btnSubmitPhoto = (Button)findViewById(R.id.btn_submitPhoto);
        btnShowPhoto = (Button)findViewById(R.id.btn_showPhoto);

        btnRtn.setOnClickListener(this);
        btnSubmitPhoto.setOnClickListener(this);
        btnShowPhoto.setOnClickListener(this);
    }
}
