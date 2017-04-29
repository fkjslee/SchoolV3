package com.fkjslee.schoolv3.counsellor;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.database.OpenOrCreateDB;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CounsellorLeaveActivty extends AppCompatActivity {

    Toolbar toolbar;
    private ViewPager viewPager = null;
    private List<View> views = null;
    private View viewHandled,viewUnhandled;
    private ListView listViewHandled,listViewUnhandled;
    private SimpleAdapter handledAdapter,unhandledAdapter;
    private List<Map<String,Object>> handled,unhandled;
    private TextView text1,text2;//上方的两个文字项
    private int currIndex = 0;//界面的下标指示
    private ImageView cursor = null;
    private int bmpW;// 动画图片宽度
    private int offset;//每次移动的时候偏移量
    //toolbar内容
    private ImageView backspace = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_leave);

//        init();
    }

    @Override
    protected void onResume() {
        //界面跳转之后的刷新操作
        if(handled != null){
            handled.clear();
        }
        if(unhandled != null){
            unhandled.clear();
        }
        init();
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.counsellor_toolbar,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.muti_choose_pop://跳转到多选Activity，中间涉及到参数传递的问题
                /*根据当前的下标，向新activity传递不同的内容*/
                Intent intent = new Intent(CounsellorLeaveActivty.this,LeaveMutiChoose.class);
                int which = viewPager.getCurrentItem();
                intent.putExtra("index",which);
                startActivity(intent);
                Log.e("index",which+"");
                break;
            case R.id.clear_out_pop:
                //弹出菜单，询问是否要清空当前
                new AlertDialog.Builder(CounsellorLeaveActivty.this).setTitle("清空当前内容").setMessage("确认清空?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                //具体清空操作
                                if(currIndex == 0){
                                    Toast.makeText(CounsellorLeaveActivty.this,"请您先处理请假信息",Toast.LENGTH_SHORT).show();
                                }else{
                                    //批量删除已处理的信息
                                    OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                    openOrCreateDB.deleteLeaves("leaves");
                                    openOrCreateDB.close();
                                    handled.clear();
                                    //因为数据已经清空的原因，下面一句话就不用再运行了
//                                    handled = setMapList(openOrCreateDB.getLeaveDatas("leaves"),1);
                                    handledAdapter.notifyDataSetChanged();

                                    Toast.makeText(CounsellorLeaveActivty.this,"已清空",Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();
                break;
        }
        return true;
    }

    private void init(){
        //数据初始化

//        OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
//        for (int i = 0;i < 30;i++){
//            LeaveContent leaveContent = new LeaveContent();
//            leaveContent.studentName = "name"+i;
//            leaveContent.studentNumber = "2014400"+i;
//            leaveContent.reasons = "睡觉啊看到静安寺觉得睡觉啊看到静安寺觉得睡觉啊看到静安寺觉得";
//            leaveContent.time = "2014/08/02";
//            leaveContent.deal = i%2;
//            if(leaveContent.deal == 1){
//                leaveContent.pass = i%2;
//            }
//            openOrCreateDB.insertLeave(leaveContent,"leaves");
//        }
//        openOrCreateDB.close();

        toolbar = (Toolbar)findViewById(R.id.counsellor_toolbars);
        setSupportActionBar(toolbar);
        backspace = (ImageView)findViewById(R.id.counsellor_backspace);
        backspace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CounsellorLeaveActivty.this.finish();
            }
        });
        text1 = (TextView)findViewById(R.id.unhandled_leave_tip);
        text2 = (TextView)findViewById(R.id.handled_leave_tip);//已处理
        text1.setOnClickListener(new TextClick(0));
        text2.setOnClickListener(new TextClick(1));
//        text1.setBackgroundColor(Color.GRAY);
        text1.setTextColor(Color.WHITE);

        InitImageView();//初始化横线指标图片

        viewPager=(ViewPager) findViewById(R.id.vPager);
        views=new ArrayList<View>();
        LayoutInflater inflater=getLayoutInflater();
        viewHandled=inflater.inflate(R.layout.counsellor_leave_handled, null);
        viewUnhandled=inflater.inflate(R.layout.counsellor_leave_unhandled, null);
        initHandledView();
        initUnhandledView();
        views.add(viewUnhandled);
        views.add(viewHandled);
        //设置viewPager
        viewPager.setAdapter(new MyViewPagerAdapter(views));
        viewPager.setCurrentItem(currIndex);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    /**
     2      * 初始化动画，这个就是页卡滑动时，下面的横线也滑动的效果，在这里需要计算一些数据
     3 */

    private void InitImageView() {
        cursor = (ImageView)findViewById(R.id.counsellor_leave_cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;// 获取分辨率宽度
        offset = (screenW / 2 - bmpW) / 2 +10;// 计算偏移量，不知道横线不是在最中心，现在是自己又加上的10px
        Log.e("offset:"+offset,"bw"+bmpW);
        Matrix matrix = new Matrix();
        matrix.preTranslate(offset, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
    }

    private void initHandledView(){ //初始化已处理界面
        listViewHandled = null;
        listViewHandled = (ListView) viewHandled.findViewById(R.id.counsellor_leave_handled);
        setHandled();
        handledAdapter = new SimpleAdapter(CounsellorLeaveActivty.this,handled,R.layout.counsellor_leave_item,
                LeaveContent.array,
                new int[]{R.id.counsellor_leave_student_name,R.id.counsellor_leave_student_sn,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time,R.id.counsellor_leave_pass});
        listViewHandled.setAdapter(handledAdapter);

        /*已同意的请假请求里面现在没有添加点击事件和长按事件*/

        listViewHandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long l) {
                new AlertDialog.Builder(CounsellorLeaveActivty.this).setTitle("删除这条记录").setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                /*删除并刷新界面*/
                                Map<String,Object> map = handled.remove(i);
                                handledAdapter.notifyDataSetChanged();
                                //从数据库删除记录
                                OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                openOrCreateDB.deleteLeave((String) map.get("studentNumber"),"leaves");
                                openOrCreateDB.close();
                                Toast.makeText(CounsellorLeaveActivty.this,"已删除",Toast.LENGTH_SHORT).show();

                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;//当同时设置了点击事件和长按事件时，这里要设置为true，不然长按之后还会激发点击事件
            }
        });
    }


    private void initUnhandledView(){ //初始化未处理界面
        listViewUnhandled = null;
        listViewUnhandled = (ListView) viewUnhandled.findViewById(R.id.counsellor_leave_unhandled);
        setUnhandled();
        unhandledAdapter = new SimpleAdapter(CounsellorLeaveActivty.this,unhandled,R.layout.counsellor_leave_item,
                LeaveContent.array,
                new int[]{R.id.counsellor_leave_student_name,R.id.counsellor_leave_student_sn,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time});
        listViewUnhandled.setAdapter(unhandledAdapter);

        /*为未处理的请假请求添加点击事件和长按事件：点击事件跳转到完整的假条显示，长按可以编辑单个条目*/

        listViewUnhandled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(CounsellorLeaveActivty.this,ShowLeaveDetail.class);
                /*传递假条对象*/
                LeaveContent leaveContent = null;
//                leaveContent.studentName = (String) unhandled.get(i).get("name");

                startActivity(intent);

            }
        });

        listViewUnhandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                final LeaveContent leaveTemp = new LeaveContent();
                final int index = i;
                //弹出菜单，询问是否要清空当前
                new AlertDialog.Builder(CounsellorLeaveActivty.this).setTitle("同意请假？")
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                leaveTemp.pass = 1;
                                leaveTemp.deal = 1;
                                dialog.dismiss();
                                /*把当前操作假条的内容从未处理记录中删除并添加到已处理条目中，学生信息反馈*/
                                Toast.makeText(CounsellorLeaveActivty.this,"已同意",Toast.LENGTH_SHORT).show();

                                leaveTemp.studentNumber = (String)unhandled.remove(index).get("studentNumber");
                                unhandledAdapter.notifyDataSetChanged();
                                //从数据库更改记录
                                OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                openOrCreateDB.updateLeave(leaveTemp,"leaves");

                                //添加记录到已处理记录里面并刷新显示,关闭数据库
                                handled.clear();
                                handled.addAll(setMapList(openOrCreateDB.getLeaveDatas("leaves"),1));
                                openOrCreateDB.close();
                                Log.e("CounsellorLeave","hanledSize"+handled.size());
                                handledAdapter.notifyDataSetChanged();

                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                leaveTemp.pass = 0;
                                leaveTemp.deal = 1;
                                Log.e("yes",""+leaveTemp.deal);
                                /*把当前操作假条的内容从未处理记录中删除并添加到已处理，学生信息反馈*/
                                Toast.makeText(CounsellorLeaveActivty.this,"已拒绝",Toast.LENGTH_SHORT).show();

                                Map<String,Object> map = unhandled.remove(index);
                                map.remove("pass");
                                map.put("pass","已拒绝");

                                leaveTemp.studentNumber = (String)map.get("studentNumber");
                                unhandledAdapter.notifyDataSetChanged();

                                //从数据库更改记录
                                OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                openOrCreateDB.updateLeave(leaveTemp,"leaves");
                                //添加记录到已处理记录里面并刷新显示,关闭数据库
                                handled.clear();
                                handled.addAll(setMapList(openOrCreateDB.getLeaveDatas("leaves"),1));
                                openOrCreateDB.close();

                                handledAdapter.notifyDataSetChanged();
                                dialog.dismiss();

                            }
                        }).show();

                return true;
            }
        });
    }

    public void setHandled(){
        /*
        *为请假条列表添加内容，添加之后再添加到对应的map列表里面
        *   现在的添加方式是固定添加，后面可以根据数据库里面的内容用另外的添加方式
        */
//        handledLeaveList = new ArrayList<>();


        OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
        handled = setMapList(openOrCreateDB.getLeaveDatas("leaves"),1);
        openOrCreateDB.close();


    }

    public void setUnhandled(){  //设置已处理列表的内容项，目前是随意添加的数据

        OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
        unhandled = setMapList(openOrCreateDB.getLeaveDatas("leaves"),0);
        openOrCreateDB.close();
    }

    /*也许这个函数会被复用
    * 把list里面的内容格式化存到listMap里面*/
    public static List<Map<String,Object>> setMapList(List<LeaveContent> list,int deal){
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(LeaveContent leaveContent : list){
            if(leaveContent.deal != deal)
                continue;
            Map<String,Object> map = new HashMap<>();
            map.put(LeaveContent.array[0],leaveContent.studentName);
            map.put(LeaveContent.array[1],leaveContent.studentNumber);
            map.put(LeaveContent.array[2],leaveContent.reasons);
            map.put(LeaveContent.array[3],leaveContent.time);
            String str = "";
            if (leaveContent.pass == 1){
                str = "已批准";
            }else if (leaveContent.pass == 0){
                str = "已拒绝";
            }
            map.put(LeaveContent.array[4],str);
            //这个里面不需要添加是否已经处理的信息
            listMap.add(map);
        }
        return listMap;
    }

    private class TextClick implements View.OnClickListener{

        private int index=0;
        public TextClick(int i){
            index=i;
        }
        @Override
        public void onClick(View view) {
            viewPager.setCurrentItem(index);
        }
    }


    public class MyViewPagerAdapter extends PagerAdapter {
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
//            handledAdapter.notifyDataSetChanged();//////////////////////////////////
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0==arg1;
        }
    }

    private class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;
        public void onPageScrollStateChanged(int arg0) {


        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {
            if (arg0 == 1){
//                text2.setBackgroundColor(Color.GRAY);
//                text1.setBackgroundColor(Color.WHITE);
                text2.setTextColor(Color.WHITE);
                text1.setTextColor(Color.BLACK);
            }else{
                text1.setTextColor(Color.WHITE);
                text2.setTextColor(Color.BLACK);
//                text2.setBackgroundColor(Color.WHITE);
//                text1.setBackgroundColor(Color.GRAY);
            }
            Animation animation = new TranslateAnimation(one*currIndex, one*arg0, 0, 0);
            currIndex = arg0;
            animation.setFillAfter(true);// True:图片停在动画结束位置
            animation.setDuration(300);//设置时间为0.3秒
            cursor.startAnimation(animation);
        }

    }


}
