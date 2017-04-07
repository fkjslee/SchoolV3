package com.fkjslee.schoolv3.function;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fkjslee.schoolv3.activity.LogActivity;
import com.fkjslee.schoolv3.network.HttpThread;

import static android.content.Context.MODE_WORLD_WRITEABLE;

/**
 * @author fkjslee
 * @time 2017/4/6
 */

public class GetSchedule {
    public static void getSchedule(Activity activity) {
        //从服务器获得的数据
        SharedPreferences.Editor editor = activity.getSharedPreferences("lock",
                MODE_WORLD_WRITEABLE).edit();
        String url = "http://119.29.241.101:8080/MyServlet/MainServlet";
        String param = "type=class&name=" + LogActivity.logAccount + "&password=" + LogActivity.logPwd;
        //String param = "type=picture&msg=" + picture;
        HttpThread httpThread = new HttpThread(url, param);
        new Thread(httpThread).start();
        String schedule = httpThread.getResult();
        editor.putString("code", schedule);
        editor.apply();
        Toast.makeText(activity.getApplicationContext(),
                "获取课表 : " + schedule.length(), Toast.LENGTH_SHORT).show();
    }
}
