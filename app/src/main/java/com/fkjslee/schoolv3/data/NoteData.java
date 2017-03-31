package com.fkjslee.schoolv3.data;

/**
 * Created by fkjslee on 2017/3/8.
 */
public class NoteData {
    private String content;
    private String people;
    private String reason;
    private boolean state;
    private String startTime;

    public NoteData(String people, String reason, boolean state, String content, String startTime) {
        this.people = people;
        this.reason = reason;
        this.state = state;
        this.content = content;
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public String getPeople() {
        return people;
    }

    public String getReason() {
        return reason;
    }

    public boolean isState() {
        return state;
    }

    public String getStartTime() {
        return startTime;
    }

    public static NoteData note1 = new NoteData("张三", "看病", false, "请假看病", "2017.1.23");
    public static NoteData note2 = new NoteData("李四", "回家", true, "有事提前回家", "2017.2.22");
}
