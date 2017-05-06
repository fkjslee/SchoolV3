package com.fkjslee.schoolv3.student.data;

/**
 * Created by fkjslee on 2017/2/18.
 * 暂时代替数据库
 */
public class AssumeData2 {

    static public MsgHomework getHomeworkData(String classId, String teacherId, String stuId) {
        if(classId.length() == 0) return MsgHomework.h1;
        else return MsgHomework.h2;
    }
}
