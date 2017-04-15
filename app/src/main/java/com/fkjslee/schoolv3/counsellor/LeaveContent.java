package com.fkjslee.schoolv3.counsellor;

import java.io.Serializable;

/**
 * Created by Xiaojun on 2017/4/13.
 */

public class LeaveContent implements Serializable {/*可以在activity之间传递这个对象*/
    public String studentName;
    public String studentNumber;
    public String reasons;
    public String time;
    public int deal = 0;//假条是否已经处理
    public int pass = -1;//是否同意请假  1 pass 0 reject -1 unhandled
    public static String[] array = {"studentName","studentNumber","reasons","time","pass"};//所有的对象公用，防止内存浪费

    public LeaveContent(){}
}
