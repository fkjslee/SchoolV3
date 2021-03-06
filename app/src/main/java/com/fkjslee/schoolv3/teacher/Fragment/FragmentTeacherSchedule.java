package com.fkjslee.schoolv3.teacher.fragment;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.database.Database;
import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.GetSchedule;
import com.fkjslee.schoolv3.teacher.activity.CourseSignActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class FragmentTeacherSchedule extends android.app.Fragment implements AdapterView.OnItemSelectedListener,
        AdapterView.OnItemClickListener, View.OnClickListener {
    private static final int getScheduleState = 1;
    private static Handler handler;

    private Spinner spinner;
    private View view;
    private Integer spinnerWeek = 1;
    private ArrayAdapter<String> weekAdapter = null;

    private MsgClass[] recordMsg = new MsgClass[120];

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_fragment_teacher_schedule, container, false);
        initView();

        return view;
    }

    /**
     * 设置学生的课表信息*/
    @TargetApi(Build.VERSION_CODES.M)
    public void setSchedulePosition() {
        Integer recordMsgLength = 0;
        RelativeLayout layout_schedule = (RelativeLayout)view.findViewById(R.id.layout_teacher_schedule);
        layout_schedule.removeAllViews();

        int interval = 3;
        WindowManager wm = (WindowManager)view.getContext().getSystemService(Context.WINDOW_SERVICE);
        int screenWidth = wm.getDefaultDisplay().getWidth();
        int screenHeight = (int) (wm.getDefaultDisplay().getHeight() * 1.2);
        layout_schedule.setMinimumHeight(screenHeight);
        int blankWidth = screenWidth / 14;
        screenWidth -= blankWidth;
        int blankHeight = screenHeight / 36;
        screenHeight -= blankHeight;
        int itemWidth = screenWidth / 7 - interval;
        int itemHeight = screenHeight / 12 - interval;

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
       // List<MsgClass> list = Database.querySchedule(LogActivity.logAccount); //得到课表信息
        List<MsgClass> list=getScheduleData();
        for(MsgClass msgClass : list)
            recordMsg[recordMsgLength++] = msgClass;

        ClassPositionMsg[][] posMsg = new ClassPositionMsg[8][20];
        for(Integer i = 0; i < 8; ++i) {
            posMsg[i] = new ClassPositionMsg[20];
            for(Integer j = 0; j < 20; ++j)
                posMsg[i][j] = new ClassPositionMsg();
        }

        for(Integer i = 0; i < recordMsgLength; ++i) {
            MsgClass msg = recordMsg[i];
            String[] tempWeek = msg.getWeeks().split(" ");
            Integer[] weeks = new Integer[tempWeek.length];
            for(Integer j = 0; j < tempWeek.length; ++j)
                weeks[j] = Integer.valueOf(tempWeek[j]);
            Integer weekDay = Integer.valueOf(msg.getWeekday());
            Integer startTime = Integer.valueOf(msg.getStartTime());
            Integer classLength = Integer.valueOf(msg.getLength());

            for(Integer eachWeek : weeks) {
                //蓝色的课表信息
                if(eachWeek.equals(spinnerWeek)) {
                    Integer maxColor = 0;
                    for(int j = startTime, cnt = 0; cnt < classLength; ++j, ++cnt) {
                        maxColor = Math.max(maxColor, posMsg[weekDay][j].color);
                    }
                    //清除颜色
                    if(maxColor != 2) {
                        for(int j = startTime, cnt = 0; cnt < classLength; ++j, ++cnt) {
                            if(posMsg[weekDay][j].color == 1) {
                                for(int k = posMsg[weekDay][j].startPos, cnt2 = 0; cnt2 < posMsg[weekDay][j].length; ++k, ++cnt2) {
                                    posMsg[weekDay][k].color = 0;
                                }
                            }
                        }
                        //设置颜色
                        for(int j = startTime, cnt = 0; cnt < classLength; ++j, ++cnt) {
                            ClassPositionMsg temp = posMsg[weekDay][j];
                            temp.color = 2;
                            temp.id = i;
                            temp.startPos = startTime;
                            temp.length = classLength;
                        }
                    }
                }
            }
            Integer maxColor = 0;
            for(int j = startTime, cnt = 0; cnt < classLength; ++j, ++cnt) {
                maxColor = Math.max(maxColor, posMsg[weekDay][j].color);
            }
            if(maxColor == 0) {
                //设置颜色
                for(int j = startTime, cnt = 0; cnt < classLength; ++j, ++cnt) {
                    ClassPositionMsg temp = posMsg[weekDay][j];
                    temp.color = 1;
                    temp.id = i;
                    temp.startPos = startTime;
                    temp.length = classLength;
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
        //选择周数 spinner
        List<String> list = new ArrayList<>();
        int maxWeek = getMaxWeek();
        for(int i = 1; i <= maxWeek; ++i) {
            list.add("第" + Integer.toString(i) + "周");
        }
        weekAdapter = new ArrayAdapter<>(view.getContext(),
                android.R.layout.simple_spinner_item, list);
        Integer nowWeek = getSpinnerWeek();
        spinner = (Spinner)view.findViewById(R.id.spinner);
        spinner.setAdapter(weekAdapter);
        spinner.setSelection(nowWeek - 1, true);
        spinnerWeek = nowWeek;
        spinner.setOnItemSelectedListener(this);
        spinner.setDropDownVerticalOffset(30);
        setSchedulePosition();


        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case getScheduleState:
                        setSchedulePosition();
                        break;
                }
            }
        };

        new Thread(new Runnable() {
            @Override
            public void run() {
                getScheduleData();
                Message message = new Message();
                message.what = getScheduleState;
                handler.sendMessage(message);
            }
        }).start();
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
        String temp = weekAdapter.getItem(position);
        temp = temp.substring(1, temp.indexOf("周"));
        spinnerWeek = Integer.valueOf(temp);
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
        //课程表显示签到信息的跳转
        Intent intent = new Intent(getActivity(), CourseSignActivity.class);
        MsgClass msg = recordMsg[v.getId()];
        intent.putExtra("classMsg", msg);
        intent.putExtra("spinnerWeek", spinnerWeek);
        startActivity(intent);
    }

    private List<MsgClass> getScheduleData() {
        List<MsgClass> list = Database.querySchedule(LogActivity.logAccount);
        if(list.isEmpty()) {
            try {
                JSONArray schedule = new JSONArray(GetSchedule.getSchedule(this.getActivity()));//向服务器请求课表
                if(schedule.length() < 10) Toast.makeText(view.getContext(),
                        "获取课表失败 可能是未绑定, 请在设置中完善信息", Toast.LENGTH_SHORT).show();
                for(int i = 0; i < schedule.length(); ++i) {
                    JSONObject jasonObject = new JSONObject(schedule.getString(i));
                    String classTeacher = jasonObject.getString("teacher");
                    String classPosition = jasonObject.getString("classroom");
                    String className = jasonObject.getString("name");
                    className = className.substring(className.indexOf("|") + 1);
                    String classWeekday = jasonObject.getString("weekday");
                    String classLength = jasonObject.getString("courseLength");
                    String classBeginTime = jasonObject.getString("courseBegin");
                    String classWeeks = jasonObject.getString("week");
                    list.add(new MsgClass(LogActivity.logAccount, className, classTeacher, classPosition,
                            classLength, classBeginTime, classWeekday, classWeeks));
                }
                Database.insertSchedule(list);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public class ClassMsgView extends TextView {

        public ClassMsgView(Context context) {
            super(context);
            setTextSize(12);
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
        Calendar calFirstDay = LogActivity.calFirstDay;
        Calendar calToday = Calendar.getInstance();
        return (calToday.get(Calendar.DAY_OF_YEAR) - calFirstDay.get(Calendar.DAY_OF_YEAR)) / 7 + 1;
    }
}
