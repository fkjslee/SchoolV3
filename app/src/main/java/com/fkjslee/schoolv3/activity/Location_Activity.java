package com.fkjslee.schoolv3.activity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationClientOption.AMapLocationProtocol;
import com.amap.api.location.AMapLocationListener;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.function.CheckPermissionsActivity;
import com.fkjslee.schoolv3.function.Utils;


/**
 * 高精度定位模式功能演示
 *
 * @创建时间： 2015年11月24日 下午5:22:42
 * @项目名称： AMapLocationDemo2.x
 * @author hongming.wang
 * @文件名称: Hight_Accuracy_Activity.java
 * @类型名称: Hight_Accuracy_Activity
 */
public class Location_Activity extends CheckPermissionsActivity
		implements
		OnClickListener {
	private RadioGroup rgLocationMode;
	private EditText etInterval;
	private EditText etHttpTimeout;
	private CheckBox cbOnceLocation;
	private CheckBox cbAddress;
	private CheckBox cbGpsFirst;
	private CheckBox cbCacheAble;
	private CheckBox cbOnceLastest;
	private CheckBox cbSensorAble;
	private TextView tvResult;
	private Button btLocation;

	//private GouldMapLocation gouldMapLocation;

	private AMapLocationClient locationClient = null;
	//private AMapLocationClientOption locationOption = new AMapLocationClientOption();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_location);
		setTitle("location");

		//gouldMapLocation = new GouldMapLocation(getApplicationContext());

		//initView();

		//初始化定位
		//initLocation();
	}

	//初始化控件
	private void initView(){
		rgLocationMode = (RadioGroup) findViewById(R.id.rg_locationMode);

		etInterval = (EditText) findViewById(R.id.et_interval);
		etHttpTimeout = (EditText) findViewById(R.id.et_httpTimeout);

		cbOnceLocation = (CheckBox)findViewById(R.id.cb_onceLocation);
		cbGpsFirst = (CheckBox) findViewById(R.id.cb_gpsFirst);
		cbAddress = (CheckBox) findViewById(R.id.cb_needAddress);
		cbCacheAble = (CheckBox) findViewById(R.id.cb_cacheAble);
		cbOnceLastest = (CheckBox) findViewById(R.id.cb_onceLastest);
		cbSensorAble = (CheckBox)findViewById(R.id.cb_sensorAble);

		tvResult = (TextView) findViewById(R.id.tv_result);
		btLocation = (Button) findViewById(R.id.bt_location);

		btLocation.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		//super.onDestroy();
	}


	@Override
	public void onClick(View v) {
//    if (v.getId() == R.id.bt_location) {
//       if (btLocation.getText().equals(
//             "开始定位")) {
//          btLocation.setText(
//                "停止定位");
//          tvResult.setText("正在定位...");
//          startLocation();
//       } else {
//          btLocation.setText("开始定位");
//          tvResult.setText("定位停止");
//       }
//    }
	}

	/**
	 * 初始化定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void initLocation(){
//    //初始化client
//    locationClient = new AMapLocationClient(this.getApplicationContext());
//    //设置定位参数
//    locationClient.setLocationOption(getDefaultOption());
//    // 设置定位监听
//    locationClient.setLocationListener(locationListener);
	}

	/**
	 * 默认的定位参数
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private AMapLocationClientOption getDefaultOption(){
		AMapLocationClientOption mOption = new AMapLocationClientOption();
//    mOption.setLocationMode(AMapLocationMode.Hight_Accuracy);//可选，设置定位模式，可选的模式有高精度、仅设备、仅网络。默认为高精度模式
//    mOption.setGpsFirst(false);//可选，设置是否gps优先，只在高精度模式下有效。默认关闭
//    mOption.setHttpTimeOut(30000);//可选，设置网络请求超时时间。默认为30秒。在仅设备模式下无效
//    mOption.setInterval(2000);//可选，设置定位间隔。默认为2秒
//    mOption.setNeedAddress(true);//可选，设置是否返回逆地理地址信息。默认是true
//    mOption.setOnceLocation(false);//可选，设置是否单次定位。默认是false
//    mOption.setOnceLocationLatest(false);//可选，设置是否等待wifi刷新，默认为false.如果设置为true,会自动变为单次定位，持续定位时不要使用
//    AMapLocationClientOption.setLocationProtocol(AMapLocationProtocol.HTTP);//可选， 设置网络请求的协议。可选HTTP或者HTTPS。默认为HTTP
//    mOption.setSensorEnable(false);//可选，设置是否使用传感器。默认是false
//    mOption.setWifiScan(true); //可选，设置是否开启wifi扫描。默认为true，如果设置为false会同时停止主动刷新，停止以后完全依赖于系统刷新，定位位置可能存在误差
//    mOption.setLocationCacheEnable(true); //可选，设置是否使用缓存定位，默认为true
		return mOption;
	}

	/**
	 * 定位监听
	 */
	AMapLocationListener locationListener = new AMapLocationListener() {
		@Override
		public void onLocationChanged(AMapLocation loc) {
//       if (null != loc) {
//          //解析定位结果
//          String result = Utils.getLocationStr(loc);
//          tvResult.setText(result);
//       } else {
//          tvResult.setText("定位失败，loc is null");
//       }
		}
	};


	/**
	 * 开始定位
	 *
	 * @since 2.8.0
	 * @author hongming.wang
	 *
	 */
	private void startLocation(){
//    locationOption.setLocationMode(AMapLocationMode.Battery_Saving);
//    // 设置定位参数
//    locationClient.setLocationOption(locationOption);
//    // 启动定位
//    locationClient.startLocation();

		//gouldMapLocation.startLocation();
		//String result = gouldMapLocation.getLocation();
		//Toast.makeText(getApplicationContext(), result, Toast.LENGTH_LONG).show();
	}

}

