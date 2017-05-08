package com.fkjslee.schoolv3.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.fkjslee.schoolv3.counsellor.LeaveContent;
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

    public static void initDatabase(Context context) {
        dataBasePath = context.getFilesDir().getAbsolutePath().replace("files", "database");
        createDatabase();
        createTableSchedule();
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
    }

    /**
     * 创建数据库表的函数，如果需要再创建其他表，直接在新建个另外的建表函数即可
     */
    public static void createLeaveTable(String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (" +
                "STUDENTNAME       VARCHAR(50)     NOT NULL," +
                "STUDENTNUMBER     VARCHAR(10)     NOT NULL," +
                "REASONS      VARCHAR(500), " +
                "TIME       VARCHAR(20), " +
                "PASS       INTEGER ," +
                "DEAL       INTEGER " +
                ")";
        db.execSQL(sql);
    }

    public static long insertLeave(LeaveContent leaveContent, String tableName){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        if(leaveContent==null)
            return 0;
        ContentValues values = new ContentValues();
        values.put("STUDENTNAME", leaveContent.studentName);
        values.put("STUDENTNUMBER", leaveContent.studentNumber);
        values.put("REASONS", leaveContent.reasons);
        values.put("TIME", leaveContent.time);
        values.put("PASS",leaveContent.pass);
        values.put("DEAL",leaveContent.deal);
        return db.insert(tableName, null, values);
    }

    private static void createDatabase() {
        SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
    }

    public static boolean searchLeave(LeaveContent leaveContent,String tableName){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        Cursor cursor = db.query(true,tableName,new String[]{"STUDENTNUMBER"},"STUDENTNUMBER=\""+leaveContent.studentNumber+"\"",null,null,null,null,null);
        if (cursor.getCount()>0)
            return true;
        return false;
    }

    public static List<LeaveContent> getLeaveDatas(String tableName) {
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        List<LeaveContent> list = new ArrayList<>();
        Cursor cursor = db.query(true,tableName,new String[]{"STUDENTNAME","STUDENTNUMBER",
                "REASONS","TIME","PASS","DEAL"},null,null,null,null,null,null);

        for(int i = 0;i <cursor.getCount();i++){
            cursor.moveToNext();
            LeaveContent leaveContent = new LeaveContent();
            leaveContent.studentName = cursor.getString(0);
            leaveContent.studentNumber = cursor.getString(1);
            leaveContent.reasons = cursor.getString(2);
            leaveContent.time = cursor.getString(3);
            leaveContent.pass = cursor.getInt(4);
            leaveContent.deal = cursor.getInt(5);
            list.add(leaveContent);
        }
        return list;
    }

    public static boolean deleteLeave(String studentNumber,String tableName){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "DELETE FROM "+tableName+" WHERE STUDENTNUMBER =\""+studentNumber+"\"";
        db.execSQL(sql);
        return true;
    }

    public static void updateLeave(LeaveContent leave,String tableName){
        /**
         * 仅仅更新了是否处理的信息和是否通过的信息
         */
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "UPDATE "+tableName+" SET PASS = '"+leave.pass+
                "' , DEAL = '"+leave.deal+"' WHERE STUDENTNUMBER =\""
                +leave.studentNumber+"\"";
        db.execSQL(sql);
    }

    public static void deleteLeaves(String tableName){//批量删除已处理内容
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        String sql = "DELETE FROM "+tableName+" WHERE DEAL = 1";
        db.execSQL(sql);
    }

    public static void close(){
        SQLiteDatabase db = SQLiteDatabase.openOrCreateDatabase(dataBasePath, null);
        db.close();
    }
}
