package com.fkjslee.schoolv3.teacher;

import java.io.Serializable;

/**
 * @author fkjslee
 * @time 2017/5/24
 */

public class TeacherLeaveContent implements Serializable{
    private String sid;
    private String sName;
    private String content;
    private String week;
    private String weekDay;
    private String courseBegin;
    private String length;
    private String noteId;
    private String state;

    public TeacherLeaveContent(String sid, String sName, String content, String week, String weekDay, String courseBegin, String length, String noteId, String state) {
        this.sid = sid;
        this.sName = sName;
        this.content = content;
        this.week = week;
        this.weekDay = weekDay;
        this.courseBegin = courseBegin;
        this.length = length;
        this.noteId = noteId;
        this.state = state;
    }

    public String getSid() {
        return sid;
    }

    public String getsName() {
        return sName;
    }

    public String getContent() {
        return content;
    }

    public String getWeek() {
        return week;
    }

    public String getWeekDay() {
        return weekDay;
    }

    public String getCourseBegin() {
        return courseBegin;
    }

    public String getLength() {
        return length;
    }

    public String getNoteId() {
        return noteId;
    }

    public String getState() {
        return state;
    }
}
