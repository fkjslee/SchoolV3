package com.fkjslee.schoolv3.student.fragment.leave;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.LeaveDetail;
import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgLeave;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * @author fkjslee
 * @time 2017/5/6
 */

public class Fragment_agree extends Fragment implements AdapterView.OnItemClickListener {

    private static final int getData = 1;
    private static Handler handler;

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

    @TargetApi(Build.VERSION_CODES.N)
    private void initView() {
        lvLeaveAgree = (ListView)view.findViewById(R.id.lv_leave_agree);

        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case getData:
                        setData((String)msg.obj);
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                String param = "type=askForState&telephone=" + LogActivity.logAccount;
                Message message = new Message();
                message.what = getData;
                message.obj = MyCommonFunction.sendRequestToServer(param);
                handler.sendMessage(message);
                Looper.loop();
            }
        }).start();

    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void setData(String leaveNote) {
        List<Map<String, Object>> items = new ArrayList<>();
        Map<String, Object> item;

        JSONArray jsonLeaveNote;
        try {
            jsonLeaveNote = new JSONArray(leaveNote);
            msgLeaves.clear();
            for(int i = 0; i < jsonLeaveNote.length(); ++i) {
                JSONObject jasonObject = new JSONObject(jsonLeaveNote.getString(i));
                String content = jasonObject.getString("content");
                String startTime = jasonObject.getString("startTime");
                String endTime = jasonObject.getString("endTime");
                String requestTime = jasonObject.getString("applyTime");
                try {
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Calendar calStartTime = Calendar.getInstance();
                    calStartTime.setTime(format.parse(startTime));
                    Calendar calEndTime = Calendar.getInstance();
                    calEndTime.setTime(format.parse(endTime));
                    Calendar calRequestTime = Calendar.getInstance();
                    calRequestTime.setTime(format.parse(requestTime));
                    String state = jasonObject.getString("state");
                    if(state.equals("1"))
                        msgLeaves.add(new MsgLeave(content, calRequestTime, calStartTime, calEndTime, state));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for(Integer i = 0; i < msgLeaves.size(); ++i) {
            MsgLeave msgLeave = msgLeaves.elementAt(i);
            item = new HashMap<>();
            item.put("reason", msgLeave.getReason());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH-mm");
            item.put("time", dateFormat.format(msgLeave.getRequestTime().getTime()));
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
