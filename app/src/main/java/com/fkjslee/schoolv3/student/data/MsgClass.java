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
    private Integer length;
    private Integer startTime;
    private Integer weekday;

    public MsgClass(String name, String teacher, String position, Integer length, Integer startTime, Integer weekday, Integer[] weeks) {
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

    public Integer getLength() {
        return length;
    }

    public Integer getStartTime() {
        return startTime;
    }

    public Integer getWeekday() {
        return weekday;
    }

    public Integer[] getWeeks() {
        return weeks;
    }

    private Integer [] weeks;
}
