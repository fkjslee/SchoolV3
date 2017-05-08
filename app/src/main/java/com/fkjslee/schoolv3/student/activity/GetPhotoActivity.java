package com.fkjslee.schoolv3.student.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

import java.io.File;


public class GetPhotoActivity extends AppCompatActivity implements View.OnClickListener{

    private Button btnRtn;
    private Button btnSubmit;
    private Button btnSubmitPhoto;
    private ImageView ivShowPhoto;
    private Bitmap photo = null;

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
            case R.id.btn_submit:
                submit();
                break;
        }
    }

    // 回调方法，从第二个页面回来的时候会执行这个方法
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0: //得到照片后
                showPhoto();
                break;
            default:
                break;
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File out = new File(Environment.getExternalStorageDirectory(),
                "firstSignPicture.jpg");
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0);
    }

    private void showPhoto() {
        String pathString = Environment.getExternalStorageDirectory()
                .toString() + "firstSignPicture.jpg";
        photo = BitmapFactory.decodeFile(pathString);
        ivShowPhoto.setImageURI(Uri.fromFile(new File(pathString)));
    }

    private void submit() {
        String picturePath = Environment.getExternalStorageDirectory()
                .toString() + "firstSignPicture.jpg";
        MyCommonFunction.compressAndGenImage(photo, picturePath, 1024);
        byte[] bytes = MyCommonFunction.getBytesFromFile(new File(picturePath));
        String requestMsg = "type=picture" + "&sName=" + LogActivity.logAccount + "&img=" +
                Base64.encodeToString(bytes, Base64.DEFAULT);
        MyCommonFunction.sendRequestToServer(requestMsg);
    }

    private void clickBtnRtn() { finish(); }

    private void initView() {
        btnRtn = (Button)findViewById(R.id.btn_rtn);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        ivShowPhoto = (ImageView)findViewById(R.id.iv_showPhoto);
        btnSubmitPhoto = (Button)findViewById(R.id.btn_submitPhoto);

        btnSubmitPhoto.setOnClickListener(this);
        btnRtn.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }
}
