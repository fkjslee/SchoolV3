package com.fkjslee.schoolv3.student.function;

import android.graphics.Bitmap;
import android.widget.Toast;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.network.HttpThread;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author fkjslee
 * @time 2017/4/15
 */

public class MyCommonFunction {

    public static String sendRequestToServer(String param) {
        String url = LogActivity.url;
        HttpThread httpThread = new HttpThread(url, param);
        new Thread(httpThread).start();
        if(httpThread.getResult().equals("请输入type")) {
//            Toast.makeText(LogActivity.context, "参数错误 param = " + param, Toast.LENGTH_LONG).show();
        }
        return httpThread.getResult();
    }

}
