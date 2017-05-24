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
    private String result;

    public MsgLeave(String reason, Calendar requestTime, Calendar startTime, Calendar endTime, String result) {
        this.reason = reason;
        this.requestTime = requestTime;
        this.startTime = startTime;
        this.endTime = endTime;
        this.result = result;
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

    public String getResult() { return result; }
}
