package com.fkjslee.schoolv3.teacher.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.fkjslee.schoolv3.teacher.TeacherLeaveContent;
import com.fkjslee.schoolv3.teacher.activity.TeacherLeaveDetailActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

public class FragmentTeacherNotSeen extends Fragment implements AdapterView.OnItemClickListener {
    private View view = null;
    private ListView lvNotSeen = null;
    private Vector<TeacherLeaveContent> teacherLeaveContents = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_not_seen,container,false);
        initView();
        return view;
    }

    private void initView() {
        lvNotSeen = (ListView) view.findViewById(R.id.lv_notSeen);
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
                if(state.equals("0"))
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
            item.put("id", i.toString());
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), items, R.layout.teacher_leave_item,
                new String[]{"content", "time", "name"}, new int[]{R.id.tv_content, R.id.tv_time, R.id.tv_sName});

        lvNotSeen.setAdapter(adapter);
        lvNotSeen.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Map<String,Object> map = (Map<String, Object>) lvNotSeen.getItemAtPosition(i);
        Intent intent = new Intent(view.getContext(), TeacherLeaveDetailActivity.class);
        intent.putExtra("teacherLeaveContent", teacherLeaveContents.elementAt(Integer.valueOf((String)map.get("id"))));
        startActivity(intent);
    }
}
