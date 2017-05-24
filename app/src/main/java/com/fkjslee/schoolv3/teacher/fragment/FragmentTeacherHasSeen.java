package com.fkjslee.schoolv3.teacher.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.TeacherLeaveContent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FragmentTeacherHasSeen extends android.app.Fragment {
    private View view = null;
    private ListView lvHandled = null;
    private Vector<TeacherLeaveContent> teacherLeaveContents = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_has_seen, container, false);
        initView();
        return view;
    }

    private void initView() {
        lvHandled = (ListView) view.findViewById(R.id.lv_hasSeen);

        try {
            String param = "type=teacherGetNotes" +
                "&telephone=" + LogActivity.logAccount;
            JSONArray jsonArray = new JSONArray(MyCommonFunction.sendRequestToServer(param));
            teacherLeaveContents.clear();
            for (int i = 0; i < jsonArray.length(); ++i) {
                JSONObject jasonObject = new JSONObject(jsonArray.getString(i));
                String sId = jasonObject.getString("sId");
                String sName = jasonObject.getString("sName");
                String content = jasonObject.getString("content");
                String week = jasonObject.getString("week");
                String weekday = jasonObject.getString("weekday");
                String courseBegin = jasonObject.getString("courseBegin");
                String length = jasonObject.getString("length");
                String noteId = jasonObject.getString("note2Id");
                String state = jasonObject.getString("state");
                if(state.equals("1"))
                    teacherLeaveContents.add(new TeacherLeaveContent(sId, sName, content, week, weekday, courseBegin, length, noteId, state));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        List<Map<String, Object>> items = new ArrayList<>();
        for(Integer i = 0; i < teacherLeaveContents.size(); ++i) {
            Map<String, Object> item = new HashMap<>();
            TeacherLeaveContent temp = teacherLeaveContents.elementAt(i);
            item.put("content", temp.getContent());
            item.put("time", "第" + temp.getWeek() + "周  " + "周" + temp.getWeekDay() + "  " + "第" + temp.getCourseBegin() + "节课");
            item.put("name", temp.getsName());
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), items, R.layout.teacher_leave_item,
                new String[]{"content", "time", "name"}, new int[]{R.id.tv_content, R.id.tv_time, R.id.tv_sName});

        lvHandled.setAdapter(adapter);
    }
}
