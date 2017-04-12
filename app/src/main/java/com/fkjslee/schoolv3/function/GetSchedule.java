package com.fkjslee.schoolv3.function;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fkjslee.schoolv3.activity.LogActivity;
import com.fkjslee.schoolv3.data.MsgClass;
import com.fkjslee.schoolv3.network.HttpThread;

import java.util.Calendar;

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
    public static Calendar getTime(MsgClass msg, Integer spinnerWeek) {

        Calendar calendar = Calendar.getInstance();
        Integer hour = 0;
        Integer minute = 0;
        switch (msg.getStartTime()) {
            case 1 :
                hour = 8;
                minute = 0;
                break;
            case 2 :
                hour = 8;
                minute = 55;
                break;
            case 3 :
                hour = 10;
                minute = 10;
                break;
            case 4 :
                hour = 11;
                minute = 5;
                break;
            case 5 :
                hour = 14;
                minute = 30;
                break;
            case 6 :
                hour = 15;
                minute = 25;
                break;
            case 7 :
                hour = 16;
                minute = 40;
                break;
            case 8 :
                hour = 17;
                minute = 35;
                break;
            case 9 :
                hour = 19;
                minute = 0;
                break;
            case 10 :
                hour = 19;
                minute = 55;
                break;
            case 11 :
                hour = 20;
                minute = 50;
                break;
            case 12 :
                hour = 21;
                minute = 45;
                break;
        }
        calendar.set(Calendar.DAY_OF_WEEK, msg.getWeekday()+1);
        calendar.set(Calendar.WEEK_OF_YEAR, LogActivity.calFirstDay.get(Calendar.WEEK_OF_YEAR) +
                spinnerWeek - 1);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        return calendar;
    }
}
