package com.fkjslee.schoolv3.data;

/**
 * Created by fkjslee on 2017/2/26.
 * 作业有哪些属性, 比如"内容", "起始时间", "结束时间"
 */
public class MsgHomework {
    private String title;
    private String content;
    private String startTime;
    private String endTime;
    private String remindTime;

    public MsgHomework(String title, String content, String startTime, String endTime, String remindTime) {
        this.title = title;
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.remindTime = remindTime;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getRemindTime() {
        return remindTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public String getStartTime() {
        return startTime;
    }


    static public MsgHomework h1 = new MsgHomework("计算机组成原理", "计算机组成原理", "2.19", "3.1", "1");
    static public MsgHomework h2 = new MsgHomework("计算机网络", "计算机网络", "2.17", "3.5", "2");
}
