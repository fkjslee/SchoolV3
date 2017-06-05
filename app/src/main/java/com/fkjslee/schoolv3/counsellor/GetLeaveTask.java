package com.fkjslee.schoolv3.counsellor;

import android.os.AsyncTask;

/**
 * Created by Xiaojun on 2017/5/24.
 */

/**
 *
 */
public class GetLeaveTask extends AsyncTask<Void,Integer,Boolean> {


    //处理前
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        //显示加载对话框
    }

    //后台任务
    @Override
    protected Boolean doInBackground(Void... params) {
        return null;
    }

    //执行完毕
    @Override
    protected void onPostExecute(Boolean aBoolean) {
        super.onPostExecute(aBoolean);
        //
    }
}
