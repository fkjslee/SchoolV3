package com.fkjslee.schoolv3.activity;

import android.content.Context;
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

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.MsgClass;
import com.fkjslee.schoolv3.function.CheckPermissionsActivity;
import com.fkjslee.schoolv3.function.Utils;

import java.io.File;


/**
 * 签到界面
 * 通过classDetail界面进入
 * */
public class SignActivity extends CheckPermissionsActivity implements View.OnClickListener{

    GouldMapLocation gouldMapLocation;
    private Button btnRtn;
    private Button btnGetPosition;
    private Button btnSubmitPhoto;
    private Button btnShowPhoto;
    private Button btnSubmit;
    private TextView tvShowPosition;

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

        initView();
    }

    @Override
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
    }

    private void initView() {
        btnGetPosition = (Button) findViewById(R.id.btn_getPosition);
        btnRtn = (Button) findViewById(R.id.btn_rtn);
        btnShowPhoto = (Button) findViewById(R.id.btn_showPhoto);
        btnSubmitPhoto = (Button) findViewById(R.id.btn_submitPhoto);
        tvShowPosition = (TextView) findViewById(R.id.tv_showPosition);
        btnSubmit = (Button) findViewById(R.id.btn_submit);

        btnGetPosition.setOnClickListener(this);
        btnRtn.setOnClickListener(this);
        btnShowPhoto.setOnClickListener(this);
        btnSubmitPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    class GouldMapLocation {
        private AMapLocationClient locationClient = null;
        private AMapLocationClientOption locationOption = new AMapLocationClientOption();
        private String result;
        private Context context;

        GouldMapLocation(Context context) {
            this.context = context;
            initLocation();
        }

        private AMapLocationListener locationListener = new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation loc) {
                if (null != loc) {
                    //解析定位结果
                    result = Utils.getLocationStr(loc);
                    tvShowPosition.setText(result);
                }
            }
        };

        /**
         * 默认的定位参数
         * @since 2.8.0
         *
         */
        private AMapLocationClientOption getDefaultOption(){
            AMapLocationClientOption mOption = new AMapLocationClientOption();
            mOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
            mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
            mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
            mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
            mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
            mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
            mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
            AMapLocationClientOption.setLocationProtocol(AMapLocationClientOption.AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
            mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
            mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
            mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
            return mOption;
        }

        private void initLocation(){
            //初始化client
            locationClient = new AMapLocationClient(context);
            //设置定位参数
            locationClient.setLocationOption(getDefaultOption());
            // 设置定位监听
            locationClient.setLocationListener(locationListener);
        }

        public void startLocation(){
            // 设置定位参数
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }
    }


}
