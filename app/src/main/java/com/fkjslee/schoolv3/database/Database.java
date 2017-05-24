package com.fkjslee.schoolv3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.fkjslee.schoolv3.counsellor.LeaveContent;
import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fkjslee
 * @time 2017/4/29
 */

public class Database {
    private static String dataBasePath;

    public static void initDatabase(Context context) {
        dataBasePath = context.getFilesDir().getAbsolutePath().replace("files", "database");
        createDatabase();
        createTableSchedule();
        createLeaveTable("leaves");
    }

    //课表
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
        db.close();
    }

    public static List<MsgClass> querySchedule(String account) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        Cursor c = db.rawQuery("SELECT * FROM schedule WHERE sid = " + account, null);
        List<MsgClass> list = new ArrayList<>();
        while (c.moveToNext()) {
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
        db.close();
        return list;
    }

    private static void createTableSchedule() {
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
        db.close();
    }

    /*
     * 请假表, 辅导员端
     * 创建数据库表的函数，如果需要再创建其他表，直接在新建个另外的建表函数即可
     */
    public static void createLeaveTable(String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "STUDENTNAME        VARCHAR(50)     NOT NULL," +
                "STUDENTNUMBER      VARCHAR(10)     NOT NULL," +
                "REASONS            VARCHAR(500), " +
                "STARTTIME          VARCHAR(20), " +
                "ENDTIME            VARCHAR(20)," +
                "PASS               INTEGER ," +
                "DEAL               INTEGER " +
                ")";
        db.execSQL(sql);
    }

    public static long insertLeave(LeaveContent leaveContent, String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        if (leaveContent == null)
            return 0;
        ContentValues values = new ContentValues();
        values.put("STUDENTNAME", leaveContent.studentName);
        values.put("STUDENTNUMBER", leaveContent.studentNumber);
        values.put("REASONS", leaveContent.reasons);
        values.put("STARTTIME", leaveContent.startTime);
        values.put("ENDTIME", leaveContent.endTime);
        values.put("PASS", leaveContent.pass);
        values.put("DEAL", leaveContent.deal);
        return db.insert(tableName, null, values);
    }


    public static List<LeaveContent> getLeaveDatas(String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        List<LeaveContent> list = new ArrayList<>();
        Cursor cursor = db.query(true, tableName, new String[]{"STUDENTNAME", "STUDENTNUMBER",
                "REASONS", "STARTTIME", "ENDTIME", "PASS", "DEAL"}, null, null, null, null, null, null);
        for (int i = 0; i < cursor.getCount(); i++) {
            cursor.moveToNext();
            LeaveContent leaveContent = new LeaveContent();
            leaveContent.studentName = cursor.getString(0);
            leaveContent.studentNumber = cursor.getString(1);
            leaveContent.reasons = cursor.getString(2);
            leaveContent.startTime = cursor.getString(3);
            leaveContent.endTime = cursor.getString(4);
            leaveContent.pass = cursor.getInt(5);
            leaveContent.deal = cursor.getInt(6);
            list.add(leaveContent);
        }
        db.close();
        cursor.close();
        return list;
    }

    public static boolean deleteLeave(String studentNumber, String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "DELETE FROM " + tableName + " WHERE STUDENTNUMBER =\"" + studentNumber + "\"";
        db.execSQL(sql);
        return true;
    }

    public static void updateLeave(LeaveContent leave, String tableName) {
        /**
         * 仅仅更新了是否处理的信息和是否通过的信息
         */
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "UPDATE " + tableName + " SET PASS = '" + leave.pass +
                "' , DEAL = '" + leave.deal + "' WHERE STUDENTNUMBER =\""
                + leave.studentNumber + "\"";
        db.execSQL(sql);
    }

    public static void deleteLeaves(String tableName) {//批量删除已处理内容
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "DELETE FROM " + tableName + " WHERE DEAL = 1";
        db.execSQL(sql);
    }

    //请假 学生端

    public static void close() {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        db.close();
    }

    private static void createDatabase() {
        SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
    }
}
