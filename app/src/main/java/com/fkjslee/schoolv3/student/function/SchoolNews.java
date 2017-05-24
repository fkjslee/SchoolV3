package com.fkjslee.schoolv3.student.function;

import java.util.ArrayList;


public class SchoolNews {

    private String url="http://jwc.cqu.edu.cn/announcement";
    private DefaultHttpClient httpClient;

    public SchoolNews() {
        httpClient=new DefaultHttpClient();
    }

    public ArrayList<News> getNewsList() {

        HttpGet httpGet = new HttpGet(url);
        String html = null;
        String base = null;
        HttpResponse response;
        HttpEntity entity;
        ArrayList<News> newList = null;
        try {
            newList = new ArrayList<>();
            base = "http://jwc.cqu.edu.cn";
            response = httpClient.execute(httpGet);
            entity = response.getEntity();
            html = EntityUtils.toString(entity, "GBK");
        } catch (Exception e) {
            e.printStackTrace();
        }

        httpGet.releaseConnection();
        Document doc=Jsoup.parse(html);
        Elements ele=doc.select("body>div>div>div>div>div>div>"
                + "div>div>div>span>a");
        ArrayList<String> titleList= new ArrayList<>();
        ArrayList<String> urlList= new ArrayList<>();
        ArrayList<String> timeList= new ArrayList<>();
        for(Element e:ele){
            titleList.add(e.text());
            urlList.add(base+e.attr("href"));
        }
        Elements ele1=doc.select("body>div>div>div>div>div>div>"
                + "div>div>div[class=\"views-field views-field-changed\"]>span");
        for(Element e:ele1){
            timeList.add(e.text());
        }
        for(int i=0;i<titleList.size();i++){
            News news=new News();
            news.setTime(timeList.get(i));
            news.setTitle(titleList.get(i));
            news.setUrl(urlList.get(i));
            newList.add(news);
        }
        return newList;
    }
}
