package com.fkjslee.schoolv3.student.fragment;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.R;
//import com.fkjslee.schoolv3.student.function.News;
//import com.fkjslee.schoolv3.student.function.SchoolNews;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author fkjslee
 * @time 2017/5/24
 */

public class FragmentNews extends Fragment{

    private View view;
    private ListView lvNews;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        return view;
    }

    private void initView() {
//        lvNews = (ListView)view.findViewById(R.id.lv_news);
//        ArrayList<News> newsList = new SchoolNews().getNewsList();
//        List<Map<String, Object>> items = new ArrayList<>();
//        for(News news : newsList) {
//            Map<String, Object> item = new HashMap<>();
//            item.put("title", news.getTitle());
//            items.add(item);
//        }
//        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), items, R.layout.item_news,
//                new String[]{"title"}, new int[]{R.id.tv_title});
//        lvNews.setAdapter(adapter);
    }
}
