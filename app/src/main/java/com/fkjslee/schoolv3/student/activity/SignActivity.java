package com.fkjslee.schoolv3.student.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Base64;
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
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.CheckPermissionsActivity;
import com.fkjslee.schoolv3.student.function.GetSchedule;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.student.function.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.Calendar;
import java.util.Date;


/**
 * 签到界面
 * 通过classDetail界面进入
 */
public class SignActivity extends CheckPermissionsActivity implements View.OnClickListener {

    private static final int getSignState = 1;
    private static Handler handler;

    private GouldMapLocation gouldMapLocation;
    private ImageView ivRtn;
    private Button btnSubmitPhoto;
    private Button btnSubmit;
    private TextView tvShowPosition;
    private TextView tv_class;
    private TextView tv_signState;
    private ImageView ivShowPhoto;

    private Bitmap photo = null;
    private MsgClass msg;
    private Integer spinnerWeek;
    private Double longitude;
    private Double latitude;
    private String requestMsg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);

        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");

        initView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_rtn:
                finish();
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
        }
    }

    private void submit() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        photo.compress(Bitmap.CompressFormat.PNG, 100, baos);
        String cName = msg.getName().substring(msg.getName().indexOf("|") + 1);
        String requestMsg = "type=sign_in" + "&img=" + Base64.encodeToString(baos.toByteArray(), Base64.DEFAULT) +
                "&sName=" + LogActivity.logAccount + "&cName=" + cName + "&week=" + spinnerWeek +
                "&weekday=" + msg.getWeekday() + "&courseBegin=" + msg.getStartTime() +
                "&tName=" + msg.getTeacher() + "&length=" + msg.getLength();
        MyCommonFunction.sendRequestToServer(requestMsg);
        if (!checkPosition()) {
            Toast.makeText(getApplicationContext(), "位置错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!checkTime()) {
            Toast.makeText(getApplicationContext(), "时间错误", Toast.LENGTH_SHORT).show();
            return;
        }
        if (photo == null) {
            Toast.makeText(getApplicationContext(), "请先拍照", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhoto() {
        startActivityForResult(new Intent(this, TakePhoto.class), 0);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    private Boolean checkPosition() {
        if (msg.getPosition().substring(0, 2).equals("A5")) {
            Double cirLong = 106.473404;
            Double cirLat = 29.572397;
            Double edgeLong = 106.473938;
            Double edgeLat = 29.572368;
            return Math.pow(longitude - cirLong, 2) + Math.pow(latitude - cirLat, 2) <
                    Math.pow(cirLong - edgeLong, 2) + Math.pow(cirLat - edgeLat, 2);
        } else if (msg.getPosition().substring(0, 2).equals("A8")) {
            Double cirLong = 106.472108;
            Double cirLat = 29.572496;
            Double edgeLong = 106.47269;
            Double edgeLat = 29.572481;
            return Math.pow(longitude - cirLong, 2) + Math.pow(latitude - cirLat, 2) <
                    Math.pow(cirLong - edgeLong, 2) + Math.pow(cirLat - edgeLat, 2);
        } else if (msg.getPosition().substring(0, 2).equals("A主")) {
            Double cirLong = 106.477085;
            Double cirLat = 29.571716;
            Double edgeLong = 106.477716;
            Double edgeLat = 29.57137;
            return Math.pow(longitude - cirLong, 2) + Math.pow(latitude - cirLat, 2) <
                    Math.pow(cirLong - edgeLong, 2) + Math.pow(cirLat - edgeLat, 2);
        } else {
            Toast.makeText(getApplicationContext(), "暂无这节课位置具体地点信息", Toast.LENGTH_SHORT).show();
            return false;
        }
    }

    private Boolean checkTime() {
        Calendar calClassTime = GetSchedule.getTime(msg, spinnerWeek);
        Calendar calToday = Calendar.getInstance();
        Date d1 = calClassTime.getTime();
        Date d2 = calToday.getTime();
        Long disMilliSeconds = d1.getTime() - d2.getTime();
        Long disMinute = disMilliSeconds / 1000 / 60;
        return Math.abs(disMinute) <= 10;
    }

    private void showPhoto() {
        photo = TakePhoto.photo;
        TakePhoto.photo = null;
        ivShowPhoto.setImageBitmap(photo);
    }

    private boolean hasSign() {
        requestMsg = "type=sign_res" + "&sName=" + LogActivity.logAccount +
                "&cName=" + msg.getName() + "&week=" + spinnerWeek + "&weekday=" + msg.getWeekday() +
                "&courseBegin=" + msg.getStartTime() + "&length=" + msg.getLength();
        String result = MyCommonFunction.sendRequestToServer(requestMsg);
        Boolean res = Boolean.valueOf(result);
        Toast.makeText(getApplicationContext(), result, Toast.LENGTH_SHORT).show();
        return res;
    }

    private void initView() {

        gouldMapLocation = new GouldMapLocation(this.getApplicationContext());
        gouldMapLocation.startLocation();

        ivRtn = (ImageView) findViewById(R.id.iv_rtn);
        btnSubmitPhoto = (Button) findViewById(R.id.btn_submitPhoto);
        tvShowPosition = (TextView) findViewById(R.id.tv_showPosition);
        btnSubmit = (Button) findViewById(R.id.btn_submit);
        ivShowPhoto = (ImageView) findViewById(R.id.showPhoto);
        tv_class = (TextView) findViewById(R.id.tv_class);
        tv_signState = (TextView) findViewById(R.id.tv_signState);
        ivShowPhoto = (ImageView) findViewById(R.id.showPhoto);

        ivRtn.setOnClickListener(this);
        btnSubmitPhoto.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        tv_class.setText(msg.getName());

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case getSignState:
                        tv_signState.setText((String) msg.obj);
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                Message message = new Message();
                message.what = getSignState;
                message.obj = hasSign() ? "已签到" : "未签到";
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();
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
                    longitude = Utils.getLongitude(loc);
                    latitude = Utils.getLatitude(loc);
                    tvShowPosition.setText(result);
                }
            }
        };

        /**
         * 默认的定位参数
         *
         * @since 2.8.0
         */
        private AMapLocationClientOption getDefaultOption() {
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

        private void initLocation() {
            //初始化client
            locationClient = new AMapLocationClient(context);
            //设置定位参数
            locationClient.setLocationOption(getDefaultOption());
            // 设置定位监听
            locationClient.setLocationListener(locationListener);
        }

        void startLocation() {
            // 设置定位参数
            locationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            locationClient.setLocationOption(locationOption);
            // 启动定位
            locationClient.startLocation();
        }
    }

}
