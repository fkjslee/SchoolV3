package com.fkjslee.schoolv3.student.data;

import java.io.Serializable;

/**
 * Created by fkjslee on 2017/2/21.
 * 每个课程的课程信息
 */
public class MsgClass implements Serializable{
    private String name;
    private String teacher;
    private String position;
    private String length;
    private String startTime;
    private String weekday;
    private String weeks;
    private String sid;

    public MsgClass(String sid, String name, String teacher, String position, String length, String startTime, String weekday, String weeks) {
        this.sid = sid;
        this.name = name;
        this.teacher = teacher;
        this.position = position;
        this.length = length;
        this.startTime = startTime;
        this.weekday = weekday;
        this.weeks = weeks;
    }

    public String getName() {
        return name;
    }

    public String getTeacher() {
        return teacher;
    }

    public String getPosition() {
        return position;
    }

    public String getLength() {
        return length;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getWeekday() {
        return weekday;
    }

    public String getWeeks() {
        return weeks;
    }

}
