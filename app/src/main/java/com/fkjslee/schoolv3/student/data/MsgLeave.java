package com.fkjslee.schoolv3.student.data;

import java.io.Serializable;
import java.util.Calendar;

/**
 * @author fkjslee
 * @time 2017/5/6
 */

public class MsgLeave implements Serializable{
    private String reason;
    private Calendar requestTime;
    private Calendar startTime;
    private Calendar endTime;

    public MsgLeave(String reason, Calendar requestTime, Calendar startTime, Calendar endTime) {
        this.reason = reason;
        this.requestTime = requestTime;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public static MsgLeave m1() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        return new MsgLeave("12岁的女儿偷了某员工的车，所以他没法来上班，因为他不想闹到警局去。", calendar, calendar, calendar);
    }

    public static MsgLeave m2() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, 1);
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        return new MsgLeave("在去旧货市场血拼之后头痛", calendar, calendar, calendar);
    }

    public String getReason() {
        return reason;
    }

    public Calendar getRequestTime() {
        return requestTime;
    }

    public Calendar getStartTime() {
        return startTime;
    }

    public Calendar getEndTime() {
        return endTime;
    }
}
