package com.fkjslee.schoolv3.student.fragment.leave;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.LeaveDetail;
import com.fkjslee.schoolv3.student.data.MsgLeave;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author fkjslee
 * @time 2017/5/6
 */

public class Fragment_agree extends Fragment implements AdapterView.OnItemClickListener {
    private ListView lvLeaveAgree;
    private View view;
    private Vector<MsgLeave> msgLeaves = new Vector<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leave_agree, container, false);
        initView();
        return view;
    }

    private void initView() {
        lvLeaveAgree = (ListView)view.findViewById(R.id.lv_leave_agree);

        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item;

        msgLeaves.add(MsgLeave.m1());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        msgLeaves.add(MsgLeave.m2());
        for(Integer i = 0; i < msgLeaves.size(); ++i) {
            MsgLeave msgLeave = msgLeaves.elementAt(i);
            item = new HashMap<>();
            item.put("reason", msgLeave.getReason());
            item.put("time", "2");
            item.put("id", i.toString());
            items.add(item);
        }
        SimpleAdapter adapter = new SimpleAdapter(view.getContext(), items, R.layout.leave_agree,
                new String[]{"reason", "time"}, new int[]{R.id.tv_reason, R.id.tv_time});
        lvLeaveAgree.setAdapter(adapter);
        lvLeaveAgree.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Map<String,Object> map = (Map<String, Object>) lvLeaveAgree.getItemAtPosition(i);
        Intent intent = new Intent(view.getContext(), LeaveDetail.class);
        intent.putExtra("msgLeave", msgLeaves.elementAt(Integer.valueOf((String)map.get("id"))));
        startActivity(intent);
    }
}
