package com.fkjslee.schoolv3.data;

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

//    public static final MsgClass CO1 = new MsgClass("冯永", "B二211", "计算机组成原理", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//    public static final MsgClass CN1 = new MsgClass("陈自郁", "A8109", "计算机网络", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//    public static final MsgClass CO2 = new MsgClass("冯永", "B二211", "计算机组成原理", 4,
//            new Integer[]{11, 12, 13, 14});
//    public static final MsgClass CAD1 = new MsgClass("肖沙里", "A5101", "机械CAD", 2,
//            new Integer[]{1, 2, 3, 4, 6});
//    public static final MsgClass OS1 = new MsgClass("何婧媛", "A8309", "操作系统", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//    public static final MsgClass PP1 = new MsgClass("陈波", "(未填写)", "程序实践", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9});
//    public static final MsgClass CN2 = new MsgClass("陈自郁", "B二418", "计算机网络", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//    public static final MsgClass AA1 = new MsgClass("夏云霓", "A主211", "算发分析与设计", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13});
//    public static final MsgClass BM1 = new MsgClass("邓扶平", "A5307", "创业管理", 2,
//            new Integer[]{1, 2, 3, 4, 6, 7, 8, 9});
//    public static final MsgClass PP2 = new MsgClass("陈波", "A主0414", "程序实践", 4,
//            new Integer[]{13, 14, 15});
//    public static final MsgClass CO3 = new MsgClass("冯永", "B二309", "计算机组成原理", 2,
//            new Integer[]{1, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13, 14});
//    public static final MsgClass CAD2 = new MsgClass("肖沙里", "A主1118", "机械CAD", 2,
//            new Integer[]{4});
//    public static final MsgClass CAD3 = new MsgClass("肖沙里", "A主1118", "机械CAD", 2,
//            new Integer[]{6, 7, 8, 9});
//    public static final MsgClass AA2 = new MsgClass("夏云霓", "A8213", "算发分析与设计", 2,
//            new Integer[]{1, 3, 4, 6, 7, 8, 9, 10, 11, 12, 13});
//    public static final MsgClass OS2 = new MsgClass("何婧媛", "A8211", "操作系统", 2,
//            new Integer[]{4, 6, 8, 10, 12, 14});
//    public static final MsgClass BM2 = new MsgClass("邓扶平", "A5307", "创业管理", 2,
//            new Integer[]{1, 3, 4, 6, 7, 8, 9});
//    public static final MsgClass PP3 = new MsgClass("陈波", "A主0414", "程序实践", 4,
//            new Integer[]{4, 7, 8, 13, 15});
//    public static final MsgClass CN3 = new MsgClass("陈自郁", "A5111", "计算机网络", 2,
//            new Integer[]{13, 14});
//    public static final MsgClass CO4 = new MsgClass("冯永", "B二309", "计算机组成原理", 2,
//            new Integer[]{13, 14});
//    public static final MsgClass CO5 = new MsgClass("冯永", "B二309", "计算机组成原理", 2,
//            new Integer[]{5});
//    public static final MsgClass OS3 = new MsgClass("何婧媛", "A8211", "操作系统", 2,
//            new Integer[]{2});
//    public static final MsgClass BM3 = new MsgClass("邓扶平", "A5307", "创业管理", 2,
//            new Integer[]{2});
}
