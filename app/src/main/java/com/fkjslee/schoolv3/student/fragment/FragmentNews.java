package com.fkjslee.schoolv3.student.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.NewsActivity;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.activity.TeacherLeaveDetailActivity;
//import com.fkjslee.schoolv3.student.function.News;
//import com.fkjslee.schoolv3.student.function.SchoolNews;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author fkjslee
 * @time 2017/5/24
 */

public class FragmentNews extends Fragment implements AdapterView.OnItemClickListener {

    private View view;
    private ListView lvNews;
    private Handler handler;
    private Vector<News> vNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return view;
    }

    private void initView() {
        lvNews = (ListView) view.findViewById(R.id.lv_news);
        vNews = new Vector<>();

        lvNews.setOnItemClickListener(this);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 1:
                        setData((String)msg.obj);
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String param = "type=getNews";
                Message message = new Message();
                message.what = 1;
                message.obj = MyCommonFunction.sendRequestToServer(param);
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();
    }


    private void setData(String result) {
        JSONArray jsonArray;
        try {
            jsonArray = new JSONArray(result);
            vNews.clear();
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject tem = jsonArray.getJSONObject(i);
                String time = tem.getString("time");
                String title = tem.getString("title");
                String url = tem.getString("url");
                String id = String.valueOf(i);
                News stu = new News(time, url, title, id);
                vNews.add(stu);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item;
        for(Integer i = 0; i < vNews.size(); ++i) {
            News news = vNews.elementAt(i);
            item = new HashMap<>();
            item.put("time", news.time);
            item.put("title", news.title);
            item.put("id", String.valueOf(i));
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), items, R.layout.item_news,
                new String[]{"time", "title"}, new int[]{R.id.tv_time, R.id.tv_title});
        lvNews.setAdapter(adapter);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Map<String,Object> map = (Map<String, Object>) lvNews.getItemAtPosition(i);
        Intent intent = new Intent(view.getContext(), NewsActivity.class);
        Integer id = Integer.valueOf((String)map.get("id"));
        intent.putExtra("url", vNews.elementAt(id).url);
        startActivity(intent);
    }

    private class News {
        String time;
        String url;
        String title;
        String id;

        public News(String time, String url, String title, String id) {
            this.time = time;
            this.url = url;
            this.title = title;
            this.id = id;
        }
    }
}
