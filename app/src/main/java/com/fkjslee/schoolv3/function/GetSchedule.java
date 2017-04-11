package com.fkjslee.schoolv3.function;

import android.app.Activity;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.fkjslee.schoolv3.activity.LogActivity;
import com.fkjslee.schoolv3.data.MsgClass;
import com.fkjslee.schoolv3.network.HttpThread;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
        String url = LogActivity.url;
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

    public static Calendar getTime(MsgClass msg) {

        Calendar calendar = Calendar.getInstance();
        String time = "";
        switch (msg.getStartTime()) {
            case 1 :
                time = "8:00";
                break;
            case 2 :
                time = "8:55";
                break;
            case 3 :
                time = "10:10";
                break;
            case 4 :
                time = "11:05";
                break;
            case 5 :
                time = "14:30";
                break;
            case 6 :
                time = "15:25";
                break;
            case 7 :
                time = "16:40";
                break;
            case 8 :
                time = "17:35";
                break;
            case 9 :
                time = "19:00";
                break;
            case 10 :
                time = "19:55";
                break;
            case 11 :
                time = "20:50";
                break;
            case 12 :
                time = "21:45";
                break;
        }
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        String strTime = String.valueOf(calendar.get(Calendar.YEAR)) + "-" +
                String.valueOf(calendar.get(Calendar.MONTH)) + "-" +
                String.valueOf(calendar.get(Calendar.DAY_OF_MONTH)) + time;
        try {
            Date date = simpleDateFormat.parse(time);
            calendar.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar;
    }
}
