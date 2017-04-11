package com.fkjslee.schoolv3.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import com.fkjslee.schoolv3.function.GetSchedule;
import com.fkjslee.schoolv3.function.Utils;
import com.fkjslee.schoolv3.network.HttpThread;

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.Date;


/**
 * 签到界面
 * 通过classDetail界面进入
 * */
public class SignActivity extends CheckPermissionsActivity implements View.OnClickListener{

    private GouldMapLocation gouldMapLocation;
    private Button btnRtn;
    private Button btnGetPosition;
    private Button btnSubmitPhoto;
    private Button btnShowPhoto;
    private Button btnSubmit;
    private TextView tvShowPosition;
    private TextView tv_class;
    private TextView tv_signState;
    private ImageView ivShowPhoto;
    private EditText etHour;
    private EditText etMinute;

    private Bitmap photo = null;
    private MsgClass msg;
    private Integer spinnerWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        gouldMapLocation = new GouldMapLocation(this.getApplicationContext());

        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer)getIntent().getSerializableExtra("spinnerWeek");

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
                //showPhoto();
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
            case 1:
                photo = GetPhoto.photo;
                showPhoto();
                GetPhoto.photo = null;
                break;
            default:
                break;
        }
    }

    private void submit() {
        if(!checkTime()) {
            Toast.makeText(getApplicationContext(), "时间错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if(!checkPosition()) {
            return;
        }
        if(photo == null) {
            Toast.makeText(getApplicationContext(), "请先拍照", Toast.LENGTH_SHORT).show();
            return;
        }
        String url = LogActivity.url;
        String param = "type=picture&msg=" + bitmapToString(photo);
        HttpThread httpThread = new HttpThread(url, param);
        new Thread(httpThread).start();
    }

    private void takePhoto() {
        Intent intent = new Intent(this, GetPhoto.class);
        startActivityForResult(intent, 1);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            clickBtnRtn();
        }
        return super.onKeyDown(keyCode, event);
    }

    private Boolean checkPosition() {
        return true;
    }

    private Boolean checkTime() {
        Calendar calClassTime = GetSchedule.getTime(msg, spinnerWeek);
        Calendar calToday = Calendar.getInstance();
        if(etMinute.getText().toString().length() != 0) {
            calToday.set(Calendar.HOUR_OF_DAY, Integer.valueOf(etHour.getText().toString()));
            calToday.set(Calendar.MINUTE, Integer.valueOf(etMinute.getText().toString()));
        }
        Date d1 = calClassTime.getTime();
        Date d2 = calToday.getTime();
        Long disMilliSeconds = d1.getTime() - d2.getTime();
        Long disMinute = disMilliSeconds / 1000 / 60;
        return Math.abs(disMinute) <= 10;
    }

    private void showPhoto() {
        if(photo == null) {
            Toast.makeText(getApplicationContext(), "请先拍照", Toast.LENGTH_SHORT).show();
            return;
        }
        String strPicture = bitmapToString(photo);
        ivShowPhoto.setImageBitmap(stringToBitmap(strPicture));
    }

    private Bitmap stringToBitmap(String string){
        //将字符串转换成Bitmap类型
        Bitmap bitmap=null;
        try {
            byte[]bitmapArray;
            bitmapArray=Base64.decode(string, Base64.DEFAULT);
            bitmap=BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bitmap;
    }

    private String bitmapToString(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytePicture = baos.toByteArray();
        return Base64.encodeToString(bytePicture, Base64.DEFAULT);
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
        ivShowPhoto = (ImageView) findViewById(R.id.showPhoto);
        tv_class = (TextView)findViewById(R.id.tv_class);
        tv_signState = (TextView)findViewById(R.id.tv_signState);
        etHour = (EditText)findViewById(R.id.et_hour);
        etMinute = (EditText)findViewById(R.id.et_minute);

        btnGetPosition.setOnClickListener(this);
        btnRtn.setOnClickListener(this);
        btnShowPhoto.setOnClickListener(this);
        btnSubmitPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        tv_class.setText(msg.getName());
        tv_signState.setText(hasSign()? "已签到" : "未签到");
    }


    public class GouldMapLocation {
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

        void startLocation(){
            // 设置定位参数
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }
    }

}
