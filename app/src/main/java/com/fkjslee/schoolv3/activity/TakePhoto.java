package com.fkjslee.schoolv3.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;

import com.fkjslee.schoolv3.R;

import java.io.ByteArrayOutputStream;
import java.io.File;

public class TakePhoto extends AppCompatActivity {
    public static Bitmap photo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        takePhoto();
    }

    public void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        File out = new File(Environment.getExternalStorageDirectory(),
                "camera.jpg");
        Uri uri = Uri.fromFile(out);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 根据上面发送过去的请求吗来区别
        switch (requestCode) {
            case 0:
                String pathString = Environment.getExternalStorageDirectory()
                        .toString() + "/camera.jpg";
                photo = BitmapFactory.decodeFile(pathString);
                compressPhoto();
                finish();
                break;
            default:
                break;
        }
    }

    private void compressPhoto() {
        //图片允许最大空间   单位：KB
        Double maxSize = 1000.00;
        //将bitmap放至数组中，意在bitmap的大小（与实际读取的原文件要大）
        int size;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1)
            size = photo.getByteCount();
        else
            size = photo.getRowBytes() * photo.getHeight();
        //判断bitmap占用空间是否大于允许最大空间  如果大于则压缩 小于则不压缩
        if (size / 1024 > maxSize) {
            //获取bitmap大小 是允许最大大小的多少倍
            double i = size / 1024 / maxSize;
            //开始压缩  此处用到平方根 将宽带和高度压缩掉对应的平方根倍 （1.保持刻度和高度和原bitmap比率一致，压缩后也达到了最大大小占用空间的大小）
            photo = zoomImage(photo, photo.getWidth() / Math.sqrt(i),
                    photo.getHeight() / Math.sqrt(i));
        }
    }

    /***
     * 图片的缩放方法
     *
     * @param bgimage
     *            ：源图片资源
     * @param newWidth
     *            ：缩放后宽度
     * @param newHeight
     *            ：缩放后高度
     * @return
     */
    private static Bitmap zoomImage(Bitmap bgimage, double newWidth,
                                    double newHeight) {
        // 获取这个图片的宽和高
        float width = bgimage.getWidth();
        float height = bgimage.getHeight();
        // 创建操作图片用的matrix对象
        Matrix matrix = new Matrix();
        // 计算宽高缩放率
        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;
        // 缩放图片动作
        matrix.postScale(scaleWidth, scaleHeight);
        Bitmap bitmap = Bitmap.createBitmap(bgimage, 0, 0, (int) width,
                (int) height, matrix, true);
        return bitmap;
    }

    public static String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytePicture = baos.toByteArray();
        return Base64.encodeToString(bytePicture, Base64.DEFAULT);
    }
}
