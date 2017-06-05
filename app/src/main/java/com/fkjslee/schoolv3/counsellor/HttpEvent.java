package com.fkjslee.schoolv3.counsellor;

/**
 * Created by Xiaojun on 2017/5/27.
 */

public class HttpEvent {
    private String msg;
    private int isError;
    private int type;

    public HttpEvent(String msg,int isError,int type){
        this.msg = msg;
        this.isError = isError;
        this.type = type;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getIsError() {
        return isError;
    }

    public void setIsError(int isError) {
        this.isError = isError;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
