package com.fkjslee.schoolv3.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.activity.ClassDetailActivity;
import com.fkjslee.schoolv3.data.MsgClass;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by fkjslee on 2017/2/18.
 * "课表"碎片
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment_schedule extends Fragment implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private View parentView = null;
    private Integer spinnerWeek = 1;
    private ArrayAdapter<String> weekAdapter = null;

    private MsgClass[] recordMsg = new MsgClass[120];
    private Integer recordMsgLength = 0;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        //选择周数 spinner
        List<String> list = new ArrayList<>();
        int maxWeek = getMaxWeek();
        for(int i = 1; i <= maxWeek; ++i) {
            list.add(Integer.toString(i));
        }
        parentView = view;
        weekAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, list);
        Spinner spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setAdapter(weekAdapter);
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownVerticalOffset(30);

        setSchedulePosition();

        return view;
    }

    /**
    * 设置学生的课表信息*/
    @TargetApi(Build.VERSION_CODES.M)
    public void setSchedulePosition() {
        View view = parentView;
        RelativeLayout layout_schedule = (RelativeLayout)view.findViewById(R.id.layout_schedule);

        int interval = 5;
        WindowManager wm = (WindowManager)view.getContext().getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = (int) (wm.getDefaultDisplay().getHeight() * 1.5);
        layout_schedule.setMinimumHeight(screenHeight);
        int blankWidth = screenWidth / 14;
        screenWidth -= blankWidth;
        int blankHeight = screenHeight / 36;
        screenHeight -= blankHeight;
        int itemWidth = screenWidth / 7 - interval;
        int itemHeight = screenHeight / 12 - interval;

        //左上的空白
        ClassMsgButton btnLeftUp = new ClassMsgButton(parentView.getContext());
        btnLeftUp.setLayoutParams(new ViewGroup.LayoutParams(blankWidth, blankHeight));
        btnLeftUp.setClickable(false);
        btnLeftUp.setBackgroundResource(R.drawable.btn_alpha);
        layout_schedule.addView(btnLeftUp);

        //左边的提示 (课数)
        for(Integer i = 0; i < 12; ++i) {
            ClassMsgButton btnLeft = new ClassMsgButton(view.getContext());
            btnLeft.setY(blankHeight + interval + i * (itemHeight+interval));
            btnLeft.setLayoutParams(new ViewGroup.LayoutParams(blankWidth, itemHeight));
            btnLeft.setText(String.valueOf(i+1));
            btnLeft.setBackgroundResource(R.drawable.btn_alpha);
            layout_schedule.addView(btnLeft);
        }

        //上边的提示 (天数)
        for(Integer i = 0; i < 7; ++i) {
            ClassMsgButton btn = new ClassMsgButton(view.getContext());
            btn.setX(blankWidth+interval + i * (itemWidth+interval));
            btn.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, blankHeight));
            btn.setText(String.valueOf(i+1));
            btn.setBackgroundResource(R.drawable.btn_alpha);
            layout_schedule.addView(btn);
        }

        //真正的课表区域
        SharedPreferences read = parentView.getContext().getSharedPreferences("lock", parentView.getContext().MODE_WORLD_READABLE);
        String value = read.getString("code", "");
        try{
            JSONArray bbs = new JSONArray(value);
            for(int i=0;i<bbs.length();i++) {
                String myTemp1 = bbs.getString(i);
                JSONObject jasonObject = new JSONObject(myTemp1);
                String classTeacher = jasonObject.getString("teacher");
                String classPosition = jasonObject.getString("classroom");
                String className = jasonObject.getString("name");
                Integer classWeekday = Integer.parseInt(jasonObject.getString("weekday"));
                Integer classLength = Integer.parseInt(jasonObject.getString("periodlength"));
                Integer classBeginTime = Integer.parseInt(jasonObject.getString("periodbegin"));
                String[] temp = jasonObject.getString("week").split(" ");
                Integer[] classWeeks = new Integer[temp.length];
                for(int fkjslee_i = 0; fkjslee_i < temp.length; ++fkjslee_i) classWeeks[fkjslee_i] = Integer.parseInt(temp[fkjslee_i]);
                recordMsg[recordMsgLength++] = new MsgClass(className, classTeacher, classPosition,
                        classLength, classBeginTime, classWeekday, classWeeks);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        for(Integer i = 0; i < recordMsgLength; ++i) {
            MsgClass msg = recordMsg[i];
            ClassMsgButton btn = new ClassMsgButton(view.getContext());
            btn.setX(blankWidth+interval + (msg.getWeekday()-1) * (itemWidth+interval));
            btn.setY(blankHeight+interval + (msg.getStartTime()-1) * (itemHeight+interval));
            btn.setLayoutParams(new ViewGroup.LayoutParams(itemWidth,
                    itemHeight * msg.getLength()));
            btn.setText(msg.getName() + msg.getPosition());
            btn.setLines(msg.getLength()*3-1);
            @android.support.annotation.IdRes int id = i;
            btn.setId(id);
            btn.setOnClickListener(this);
            layout_schedule.addView(btn);
            Integer [] weeks = msg.getWeeks();
            boolean hasClass = false;
            for(Integer tempClass : weeks) if(tempClass.equals(spinnerWeek)) hasClass = true;
            if(hasClass) btn.setBackgroundResource(R.drawable.btn_azure);
            else btn.setBackgroundResource(R.drawable.btn_alpha);
        }
    }

    /**
     * 功能: 得到一个学生有多少周有课
    * */
    private int getMaxWeek() {
        return 18;
    }

    /**
     * 功能: 选择spinner的某个值后重新绘制课表
     * */
    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinnerWeek = Integer.valueOf(weekAdapter.getItem(position));
        setSchedulePosition();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) { }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_getSchedule) {
            Log.i("here", "here192");
            return;
        }
        Intent intent = new Intent(getActivity(), ClassDetailActivity.class);
        MsgClass msg = recordMsg[v.getId()];
        intent.putExtra("classMsg", msg);
        startActivity(intent);
    }

    public class ClassMsgButton extends Button {

        public ClassMsgButton(Context context) {
            super(context);
            setEllipsize(TextUtils.TruncateAt.valueOf("END"));
        }
    }
}
