package com.fkjslee.schoolv3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.fkjslee.schoolv3.student.activity.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author fkjslee
 * @time 2017/4/29
 */

public class Database {
    private static String dataBasePath;

    public Database() {
        String dataBasePath = Environment.getExternalStorageDirectory().getPath() + "/database";
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        //创建person表
        db.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
        Person person = new Person();
        person.name = "john";
        person.age = 30;
        //插入数据
        db.execSQL("INSERT INTO person VALUES (NULL, ?, ?)", new Object[]{person.name, person.age});

        Cursor c = db.rawQuery("SELECT * FROM person WHERE age >= ?", new String[]{"33"});
        while (c.moveToNext()) {
            int _id = c.getInt(c.getColumnIndex("_id"));
            String name = c.getString(c.getColumnIndex("name"));
            int age = c.getInt(c.getColumnIndex("age"));
            Log.i("db", "_id=>" + _id + ", name=>" + name + ", age=>" + age);
        }
        c.close();
        //关闭当前数据库
        db.close();
    }

    public static void initDatabase(Context context) {
        dataBasePath = context.getFilesDir().getAbsolutePath().replace("files", "database");
        createDatabase();
        createTable();
    }

    public static void insertSchedule(List<MsgClass> list) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        for (MsgClass msgClass : list) {
            String sql = "INSERT INTO schedule VALUES ('" +
                    LogActivity.logAccount + "','" +
                    msgClass.getWeekday() + "','" +
                    msgClass.getStartTime() + "','" +
                    msgClass.getLength() + "','" +
                    msgClass.getName() + "','" +
                    msgClass.getPosition() + "','" +
                    msgClass.getTeacher() + "','" +
                    msgClass.getWeeks() + "')";
            db.execSQL(sql);
        }
    }

    public static List<MsgClass> querySchedule(String account) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        Cursor c = db.rawQuery("SELECT * FROM schedule WHERE sid = " + account, null);
        List<MsgClass> list = new ArrayList<>();
        while(c.moveToNext()) {
            list.add(new MsgClass(c.getString(c.getColumnIndex("sid")),
                    c.getString(c.getColumnIndex("className")),
                    c.getString(c.getColumnIndex("classTeacher")),
                    c.getString(c.getColumnIndex("classPosition")),
                    c.getString(c.getColumnIndex("classLength")),
                    c.getString(c.getColumnIndex("startTime")),
                    c.getString(c.getColumnIndex("weekday")),
                    c.getString(c.getColumnIndex("allWeek"))));
        }
        c.close();
        return list;
    }

    private static void createTable() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "CREATE TABLE IF NOT EXISTS schedule(" +
                "sid            CHAR(20)," +
                "weekday        SMALLINT," +
                "startTime      SMALLINT," +
                "classLength    SMALLINT," +
                "className      VARCHAR(50)," +
                "classPosition  VARCHAR(50)," +
                "classTeacher   VARCHAR(20)," +
                "allWeek        VARCHAR(50))";
        db.execSQL(sql);
        db.execSQL("CREATE TABLE IF NOT EXISTS person (_id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, age SMALLINT)");
    }

    private static void createDatabase() {
        SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
    }

    private class Person {
        String name;
        int age;
    }
}
