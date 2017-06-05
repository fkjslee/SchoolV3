package com.fkjslee.schoolv3.counsellor;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by Xiaojun on 2017/5/26.
 */

public class NoteForIns implements Serializable {
    private String sId;  //学生Id
    private String sName;//学生姓名
    private String content;//请假内容
    private String startTime;//开始时间
    private String endTime;//结束时间
    private String applyTime;//提交时间
    private String noteId;  //请假条id
    private int state;//状态  是否处理
    public static String[] array = {"sId","sName","content","startTime","endTime","applyTime","noteId","state"};

    public NoteForIns(){

    }

    public NoteForIns(JSONObject tem){
        try {
            sId = tem.getString("sId");
            sName = tem.getString("sName");
            content = tem.getString("content");
            startTime = tem.getString("startTime");
            endTime = tem.getString("endTime");
            applyTime = tem.getString("applyTime");
            noteId = tem.getString("note1Id");
            state = tem.getInt("state");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(String applyTime) {
        this.applyTime = applyTime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
}
