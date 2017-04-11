package com.fkjslee.schoolv3.fragment;


import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.activity.ClassDetailActivity;
import com.fkjslee.schoolv3.data.MsgClass;
import com.fkjslee.schoolv3.function.GetSchedule;
import com.fkjslee.schoolv3.teacher.CourseSignActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static android.content.Context.MODE_WORLD_WRITEABLE;


/**
 * Created by fkjslee on 2017/2/18.
 * "课表"碎片
 */

@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class Fragment_schedule extends Fragment implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener, View.OnClickListener {

    private Button btnRegetSchedule;
    private Button btnSetNowWeek;
    private Spinner spinner;

    private View parentView = null;
    private Integer spinnerWeek = 1;
    private ArrayAdapter<String> weekAdapter = null;

    private MsgClass[] recordMsg = new MsgClass[120];

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_schedule, container, false);
        parentView = view;
        initView();

        return view;
    }

    /**
    * 设置学生的课表信息*/
    @TargetApi(Build.VERSION_CODES.M)
    public void setSchedulePosition() {
        Integer recordMsgLength = 0;
        View view = parentView;
        RelativeLayout layout_schedule = (RelativeLayout)view.findViewById(R.id.layout_schedule);
        layout_schedule.removeAllViews();

        int interval = 3;
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
        ClassMsgView btnLeftUp = new ClassMsgView(parentView.getContext());
        btnLeftUp.setLayoutParams(new ViewGroup.LayoutParams(blankWidth, blankHeight));
        btnLeftUp.setClickable(false);
        btnLeftUp.setBackgroundResource(R.drawable.btn_alpha);
        layout_schedule.addView(btnLeftUp);

        //左边的提示 (课数)
        //变成TextView
        for(Integer i = 0; i < 12; ++i) {
            ClassMsgView btnLeft = new ClassMsgView(view.getContext());
            btnLeft.setY(blankHeight + interval + i * (itemHeight+interval));
            btnLeft.setLayoutParams(new ViewGroup.LayoutParams(blankWidth, itemHeight));
            btnLeft.setText(String.valueOf(i+1));
            btnLeft.setBackgroundResource(R.drawable.btn_alpha);
            btnLeft.setBackgroundColor(Color.argb(0, 0, 0, 0));
            layout_schedule.addView(btnLeft);
        }

        //上边的提示 (天数)
        for(Integer i = 0; i < 7; ++i) {
            ClassMsgView btn = new ClassMsgView(view.getContext());
            btn.setX(blankWidth+interval + i * (itemWidth+interval));
            btn.setLayoutParams(new ViewGroup.LayoutParams(itemWidth, blankHeight));
            btn.setText(String.valueOf(i+1));
            btn.setBackgroundResource(R.drawable.btn_alpha);
            btn.setBackgroundColor(Color.argb(0, 0, 0, 0)); //背景透明度
            layout_schedule.addView(btn);
        }

        //真正的课表区域
        SharedPreferences read = parentView.getContext().getSharedPreferences("lock", parentView.getContext().MODE_WORLD_READABLE);
        String value = read.getString("code", "");
        if(value.length() == 0) {
            GetSchedule.getSchedule(this.getActivity());
            read = parentView.getContext().getSharedPreferences("lock", parentView.getContext().MODE_WORLD_READABLE);
            value = read.getString("code", "");
        }
        try{
            JSONArray bbs = new JSONArray(value);
            for(int i = 0;i < bbs.length(); i++) {
                String myTemp1 = bbs.getString(i);
                JSONObject jasonObject = new JSONObject(myTemp1);
                String classTeacher = jasonObject.getString("teacher");
                String classPosition = jasonObject.getString("classroom");
                String className = jasonObject.getString("name");
                Integer classWeekday = Integer.parseInt(jasonObject.getString("weekday"));
                Integer classLength = Integer.parseInt(jasonObject.getString("courseLength"));
                Integer classBeginTime = Integer.parseInt(jasonObject.getString("courseBegin"));
                String[] temp = jasonObject.getString("week").split(" ");
                Integer[] classWeeks = new Integer[temp.length];
                for(int j = 0; j < temp.length; ++j) classWeeks[j] = Integer.parseInt(temp[j]);
                recordMsg[recordMsgLength++] = new MsgClass(className, classTeacher, classPosition,
                        classLength, classBeginTime, classWeekday, classWeeks);
            }
        } catch(Exception e){
            e.printStackTrace();
        }

        ClassPositionMsg[][] posMsg = new ClassPositionMsg[8][20];
        for(Integer i = 0; i < 8; ++i) {
            posMsg[i] = new ClassPositionMsg[20];
            for(Integer j = 0; j < 20; ++j)
                posMsg[i][j] = new ClassPositionMsg();
        }

        for(Integer i = 0; i < recordMsgLength; ++i) {
            MsgClass msg = recordMsg[i];
            Integer[] weeks = msg.getWeeks();
            Integer weekDay = msg.getWeekday();

            for(Integer eachWeek : weeks) {
                //蓝色的课表信息
                if(eachWeek.equals(spinnerWeek)) {
                    Integer maxColor = 0;
                    for(int j = msg.getStartTime(), cnt = 0; cnt < msg.getLength(); ++j, ++cnt) {
                        maxColor = Math.max(maxColor, posMsg[weekDay][j].color);
                    }
                    //清除颜色
                    if(maxColor != 2) {
                        for(int j = msg.getStartTime(), cnt = 0; cnt < msg.getLength(); ++j, ++cnt) {
                            if(posMsg[weekDay][j].color == 1) {
                                for(int k = posMsg[weekDay][j].startPos, cnt2 = 0; cnt2 < posMsg[weekDay][j].length; ++k, ++cnt2) {
                                    posMsg[weekDay][k].color = 0;
                                }
                            }
                        }
                        //设置颜色
                        for(int j = msg.getStartTime(), cnt = 0; cnt < msg.getLength(); ++j, ++cnt) {
                            ClassPositionMsg temp = posMsg[msg.getWeekday()][j];
                            temp.color = 2;
                            temp.id = i;
                            temp.startPos = msg.getStartTime();
                            temp.length = msg.getLength();
                        }
                    }
                }
            }
            Integer maxColor = 0;
            for(int j = msg.getStartTime(), cnt = 0; cnt < msg.getLength(); ++j, ++cnt) {
                maxColor = Math.max(maxColor, posMsg[weekDay][j].color);
            }
            if(maxColor == 0) {
                //设置颜色
                for(int j = msg.getStartTime(), cnt = 0; cnt < msg.getLength(); ++j, ++cnt) {
                    ClassPositionMsg temp = posMsg[msg.getWeekday()][j];
                    temp.color = 1;
                    temp.id = i;
                    temp.startPos = msg.getStartTime();
                    temp.length = msg.getLength();
                }
            }
        }

        for(Integer i = 1; i < 8; ++i)
            for(Integer j = 1; j < 20; ++j) {
                if(posMsg[i][j].startPos != j || posMsg[i][j].color == 0) continue;
                ClassMsgView btn = new ClassMsgView(view.getContext());
                btn.setX(blankWidth+interval + (i-1) * (itemWidth+interval));
                btn.setY(blankHeight+interval + (j-1) * (itemHeight+interval));
                btn.setLayoutParams(new ViewGroup.LayoutParams(itemWidth,
                        itemHeight * posMsg[i][j].length));
                btn.setLines(j*3-1);
                btn.setOnClickListener(this);
                layout_schedule.addView(btn);
                btn.setBackgroundResource(0);
                if(posMsg[i][j].color == 1) btn.setBackgroundResource(R.drawable.btn_alpha);
                else btn.setBackgroundResource(R.drawable.btn_azure);
                @android.support.annotation.IdRes int id = posMsg[i][j].id;
                btn.setText(recordMsg[id].getName() + recordMsg[id].getPosition());
                btn.setTextColor(this.getResources().getColor(R.color.white));
                btn.getBackground().setAlpha(200);
                btn.setId(id);
            }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView() {
        btnRegetSchedule = (Button)parentView.findViewById(R.id.btn_reGetSchedule);
        btnSetNowWeek = (Button)parentView.findViewById(R.id.btn_setNowWeek);

        btnRegetSchedule.setOnClickListener(this);
        btnSetNowWeek.setOnClickListener(this);


        //选择周数 spinner
        List<String> list = new ArrayList<>();
        int maxWeek = getMaxWeek();
        for(int i = 1; i <= maxWeek; ++i) {
            list.add(Integer.toString(i));
        }
        weekAdapter = new ArrayAdapter<>(parentView.getContext(),
                android.R.layout.simple_spinner_item, list);
        Integer nowWeek = getSpinnerWeek();
        spinner = (Spinner)parentView.findViewById(R.id.spinner);
        spinner.setAdapter(weekAdapter);
        if(nowWeek != null) {
            spinner.setSelection(nowWeek - 1, true);
            spinnerWeek = nowWeek;
            setSchedulePosition();
        }
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownVerticalOffset(30);
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

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.btn_reGetSchedule) {
            if(true == true) {
                initView();
                return;
            }
            GetSchedule.getSchedule(getActivity());
            return;
        } else if(v.getId() == R.id.btn_setNowWeek) {
            setSpinnerWeek();
            return;
        }
       /* Intent intent = new Intent(getActivity(), ClassDetailActivity.class);*/
        Intent intent = new Intent(getActivity(), CourseSignActivity.class);
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

    public class ClassMsgView extends TextView{

        public ClassMsgView(Context context) {
            super(context);
        }
    }

    public class ClassPositionMsg {
        Integer startPos;
        Integer length;
        Integer color;
        Integer id;
        ClassPositionMsg() {
            color = 0;
        }
    }

    private Integer getSpinnerWeek() {
        SharedPreferences read = parentView.getContext().getSharedPreferences("lock", parentView.getContext().MODE_WORLD_READABLE);
        String strFirstDay = read.getString("spinnerWeek", "");
        if(strFirstDay.length() == 0) {
            return null;
        }
        Calendar calFirstDay = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = simpleDateFormat.parse(strFirstDay);
            calFirstDay.setTime(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar calToday = Calendar.getInstance();
        Integer nowWeek = (calToday.get(Calendar.DAY_OF_YEAR) - calFirstDay.get(Calendar.DAY_OF_YEAR)) / 7 + 1;
        return nowWeek;
    }

    private void setSpinnerWeek() {
        Integer weekday = getWeekday();
        Integer disToFirstDay = (spinnerWeek - 1) * 7 + (weekday - 1);
        Calendar calFirstDay = Calendar.getInstance();
        calFirstDay.add(Calendar.DAY_OF_MONTH, -disToFirstDay);
        String strFirstDay = String.valueOf(calFirstDay.get(Calendar.YEAR)) + "-" +
                String.valueOf(calFirstDay.get(Calendar.MONTH) + 1) + "-" +
                String.valueOf(calFirstDay.get(Calendar.DAY_OF_MONTH));

        SharedPreferences.Editor editor = getActivity().getSharedPreferences("lock",
                MODE_WORLD_WRITEABLE).edit();
        editor.putString("spinnerWeek",  strFirstDay);
        editor.apply();
   }

    /**
     * @return 这个时间的当前周是第几天
     * */
    private Integer getWeekday() {
        Calendar calendar = Calendar.getInstance();
        Integer weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if(calendar.getFirstDayOfWeek() == Calendar.SUNDAY) {
            weekday--;
            if(weekday == 0)
                weekday = 7;
        }
        return weekday;
    }
}
