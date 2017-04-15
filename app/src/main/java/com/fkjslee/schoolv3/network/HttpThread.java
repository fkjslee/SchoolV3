package com.fkjslee.schoolv3.network;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by wang xin on 2017/3/19.
 * 从服务器请求数据
 */


public class HttpThread implements Runnable{
    private String url;
    private byte [] param;
    private String result;

    public HttpThread(String url, String param){
        this.url = url;
        this.param = param.getBytes();
        this.result = "";
    }

    public void run(){
        OutputStream out = null;
        BufferedReader in = null;
        String temResult = "";
        try{
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
        } catch (Exception e) {
            e.printStackTrace();
        }

        //使用finally块来关闭输出流、输入流
        finally{
            try{
                if(out!=null){
                    out.close();
                }
                if(in!=null){
                    in.close();
                }
            }
            catch(IOException ex){
                ex.printStackTrace();
            }
        }
        this.result = temResult;
    }

    public String getResult(){
        try{
            Thread.sleep(5000);
            int exception = 1/this.result.length();
        } catch(ArithmeticException e){
            return "得到结果长度为0";
        } catch (InterruptedException e) {
            return "打断异常";
        }
        return this.result;
    }
}
