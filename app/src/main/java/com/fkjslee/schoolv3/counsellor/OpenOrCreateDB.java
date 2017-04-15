package com.fkjslee.schoolv3.counsellor;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Xiaojun on 2017/4/13.
 */

public class OpenOrCreateDB {
    private final String databaseName = "users";//数据库的名称
    private String path = null;

    SQLiteDatabase db;

    public OpenOrCreateDB() {
        //如果路径下没有这个数据库，创建
        db = SQLiteDatabase.openOrCreateDatabase(Values.dbPath+"/"+databaseName,null);//null表示用默认的cursor工厂
    }

    /**
     * 创建数据库表的函数，如果需要再创建其他表，直接在新建个另外的建表函数即可
     */
    public void createLeaveTable(String tableName){
        String sql = "CREATE TABLE IF NOT EXISTS " +tableName + " (" +
                "STUDENTNAME       VARCHAR(50)     NOT NULL," +
                "STUDENTNUMBER     VARCHAR(10)     NOT NULL," +
                "REASONS      VARCHAR(500), "+
                "TIME       VARCHAR(20), "+
                "PASS       INTEGER ,"+
                "DEAL       INTEGER "+
                ")";
        db.execSQL(sql);
    }

    public long insertLeave(LeaveContent leaveContent,String tableName){
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

    public boolean searchLeave(LeaveContent leaveContent,String tableName){
        Cursor cursor = db.query(true,tableName,new String[]{"STUDENTNUMBER"},"STUDENTNUMBER=\""+leaveContent.studentNumber+"\"",null,null,null,null,null);
        if (cursor.getCount()>0)
            return true;
        return false;
    }

    public List<LeaveContent> getLeaveDatas(String tableName) {
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

    public boolean deleteLeave(String studentNumber,String tableName){
        String sql = "DELETE FROM "+tableName+" WHERE STUDENTNUMBER =\""+studentNumber+"\"";
        db.execSQL(sql);
        return true;
    }

    public void updateLeave(LeaveContent leave,String tableName){
        /**
         * 仅仅更新了是否处理的信息和是否通过的信息
         */
        String sql = "UPDATE "+tableName+" SET PASS = '"+leave.pass+
                "' , DEAL = '"+leave.deal+"' WHERE STUDENTNUMBER =\""
                +leave.studentNumber+"\"";
        db.execSQL(sql);
    }

    public void deleteLeaves(String tableName){//批量删除已处理内容
        String sql = "DELETE FROM "+tableName+" WHERE DEAL = 1";
        db.execSQL(sql);
    }

    public void close(){
        db.close();
    }
}
