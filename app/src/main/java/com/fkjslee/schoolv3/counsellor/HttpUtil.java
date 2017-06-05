package com.fkjslee.schoolv3.counsellor;

import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

import com.fkjslee.schoolv3.LogActivity;

import org.greenrobot.eventbus.EventBus;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by Xiaojun on 2017/5/26.
 */

public class HttpUtil {

    public final static String url = "http://119.29.241.101:8080/MyServlet/MainServlet";
    //用于判断是那些网络操作
    public final static int COUNSELLOR_GET_LEAVE_UNHANDLED = 0;
    public final static int COUNSELLOR_GET_LEAVE_HANDLED = 1;
    public final static int COUNSELLOR_AGREE_LEAVE = 2;
    public final static int COUNSELLOR_REJECT_LEAVE = 3;
    public final static int COUNSELLOR_DELETE_LEAVE = 4;

    //请求假条
    //同意或者拒绝假条
    public static void Request(int type,int id){
        switch (type){
            case COUNSELLOR_GET_LEAVE_UNHANDLED://请求未处理假条
                String param = "type=getNotes" + "&state=0"+
                        "&telephone=" + LogActivity.logAccount;
                sendRequest(param,COUNSELLOR_GET_LEAVE_UNHANDLED);
                break;
            case COUNSELLOR_GET_LEAVE_HANDLED://请求已经处理的假条
                String param1 = "type=getNotes" + "&state=1"+
                        "&telephone=" + LogActivity.logAccount;
                sendRequest(param1,COUNSELLOR_GET_LEAVE_HANDLED);
                break;
            case COUNSELLOR_AGREE_LEAVE: // 同意请t假
                String param2 = "type=noteHandle" +
                        "&noteId="+id+"&apply=1";
                sendRequest(param2,COUNSELLOR_AGREE_LEAVE);
                break;
            case COUNSELLOR_REJECT_LEAVE ://拒绝请假
                String param3 = "type=noteHandle" +
                        "&noteId="+id+"&apply=-1";
                sendRequest(param3,COUNSELLOR_REJECT_LEAVE);
                break;

            case COUNSELLOR_DELETE_LEAVE:
                String param4 = "type=deleteNote" +
                        "&noteId="+id;
                sendRequest(param4,COUNSELLOR_DELETE_LEAVE);
                break;
        }
    }

    public static void sendRequest(final String para,final int type){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();      //一定要设置，不回调函数不可以使用
                byte[] param = para.getBytes();
                OutputStream out = null;
                BufferedReader in = null;
                String temResult = "";
                try {
                    URL realUrl = new URL(url);
                    // 打开和URL之间的连接
                    URLConnection conn = realUrl.openConnection();
                    conn.setRequestProperty("accept", "*/*");
                    conn.setRequestProperty("connection", "Keep-Alive");

                    // 发送POST请求必须设置如下两行
                    conn.setDoOutput(true);
                    conn.setDoInput(true);
                    // 获取URLConnection对象对应的输出流
                    out = conn.getOutputStream();
                    // 发送请求参数
                    out.write(param);
                    // flush输出流的缓冲
                    out.flush();
                    // 定义BufferedReader输入流来读取URL的响应
                    in = new BufferedReader(
                            new InputStreamReader(conn.getInputStream(), "UTF-8"));
                    String line;
                    while ((line = in.readLine()) != null) {
                        temResult += line;
                    }
                    EventBus.getDefault().post(new HttpEvent(temResult,0,type));//准确获得
                } catch (Exception e) {
                    e.printStackTrace();
                    EventBus.getDefault().post(new HttpEvent(temResult,1,type));//错误获得
                }

                //使用finally块来关闭输出流、输入流
                finally {
                    try {
                        if (out != null) {
                            out.close();
                        }
                        if (in != null) {
                            in.close();
                        }
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        }).start();

    }
}
