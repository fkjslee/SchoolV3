//package com.fkjslee.schoolv3.student.function;
//
//import org.apache.commons.httpclient.HttpClient;
//import org.apache.commons.httpclient.methods.PostMethod;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.jsoup.select.Elements;
//
//import java.util.ArrayList;
//
//
//public class SchoolNews {
//
//    private String url="http://jwc.cqu.edu.cn/announcement";
//    private HttpClient httpclient;
//
//    public SchoolNews() {
//        httpclient = new HttpClient();
//    }
//
//    public ArrayList<News> getNewsList() {
//        PostMethod postmethond = new PostMethod(url);
//        String html = null;
//        String base = null;
//        ArrayList<News> newList = null;
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        }).run();
//        try {
//            newList = new ArrayList<>();
//            base = "http://jwc.cqu.edu.cn";
//            httpclient.executeMethod(postmethond);
//            html = postmethond.getResponseBodyAsString();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//        postmethond.releaseConnection();
//        Document doc= Jsoup.parse(html);
//        Elements ele=doc.select("body>div>div>div>div>div>div>"
//                + "div>div>div>span>a");
//        ArrayList<String> titleList= new ArrayList<>();
//        ArrayList<String> urlList= new ArrayList<>();
//        ArrayList<String> timeList= new ArrayList<>();
//        for(Element e:ele){
//            titleList.add(e.text());
//            urlList.add(base+e.attr("href"));
//        }
//        Elements ele1=doc.select("body>div>div>div>div>div>div>"
//                + "div>div>div[class=\"views-field views-field-changed\"]>span");
//        for(Element e:ele1){
//            timeList.add(e.text());
//        }
//        for(int i=0;i<titleList.size();i++){
//            News news=new News();
//            news.setTime(timeList.get(i));
//            news.setTitle(titleList.get(i));
//            news.setUrl(urlList.get(i));
//            newList.add(news);
//        }
//        return newList;
//    }
//}
