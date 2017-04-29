package com.fkjslee.schoolv3.counsellor;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.database.OpenOrCreateDB;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class LeaveMutiChoose extends AppCompatActivity {

    private ImageView backspace,pass,reject,delete;
    private ListView listView = null;
    private TextView chooseAll = null;
    private List<Map<String,Object>> leaveList = null;
    private int deal = 0 ;

//    private int images[];
    /*下面是和多选相关的变量*/
    private List<Boolean> checks;
    private MutiChooseItemAdapter leaveAdapter;
    private int chooseState = 0;//表示当前是未被多选的状态

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leave_muti_choose);
        init();
    }

    private void init(){
        chooseState = 0;//当前是全选还是已经全选
        Intent tempIntent = getIntent();
        deal = tempIntent.getIntExtra("index",0);//后面一个参数是表示默认数据
        Log.e("which",""+deal);
        //get data to show
        OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
        leaveList = CounsellorLeaveActivty.setMapList(openOrCreateDB.getLeaveDatas("leaves"),deal);
        openOrCreateDB.close();

        backspace = (ImageView)findViewById(R.id.leave_muti_backspace);
        listView = (ListView)findViewById(R.id.leave_muti_list);
        chooseAll = (TextView)findViewById(R.id.leave_muti_choose_all);
        pass = (ImageView)findViewById(R.id.leave_muti_pass);
        reject = (ImageView)findViewById(R.id.leave_muti_reject);
        delete = (ImageView)findViewById(R.id.leave_muti_delete);

        OnClick onClick = new OnClick();
        OnTouch onTouch = new OnTouch();

        backspace.setOnClickListener(onClick);
        chooseAll.setOnClickListener(onClick);
        pass.setOnClickListener(onClick);
        reject.setOnClickListener(onClick);
        delete.setOnClickListener(onClick);

        pass.setOnTouchListener(onTouch);
        reject.setOnTouchListener(onTouch);
        delete.setOnTouchListener(onTouch);

        initImageViews();

        /**
         * 添加是否选中的数据内容
         */
        checks = new ArrayList<>();
        for (int i = 0;i< leaveList.size();i++){
            checks.add(false);
        }
        leaveAdapter = new MutiChooseItemAdapter(LeaveMutiChoose.this,leaveList);
        listView.setAdapter(leaveAdapter);
    }

    private void initImageViews(){//下方选项键的初始化
        if(deal == 1){//如果是已处理信息，那么下方的  通过 拒绝 按键失效
            pass.setImageResource(R.drawable.pass0);
            reject.setImageResource(R.drawable.reject0);
            delete.setImageResource(R.drawable.delete);
        }else{
            pass.setImageResource(R.drawable.pass);
            reject.setImageResource(R.drawable.reject);
            delete.setImageResource(R.drawable.delete0);
        }
    }

    private int chooseCount(){//判断用户使用多选时是否选择了条目
        int count = 0;
        for(Boolean bool : checks){
            if(bool){
                count++;
            }
        }
        return count;
    }

    private class OnClick implements View.OnClickListener{
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.leave_muti_backspace:
                    LeaveMutiChoose.this.finish();
                    break;

                case R.id.leave_muti_choose_all:
                    //全选
                    if(chooseState == 0){
                        for(int i = 0; i < checks.size();i++){
                            checks.set(i,true);
                        }
                        chooseAll.setText("取消全选");
                        chooseState = 1;
                    }else{
                        for(int i = 0; i < checks.size();i++){
                            checks.set(i,false);
                        }
                        chooseAll.setText("全选");
                        chooseState = 0;
                    }
                    leaveAdapter.notifyDataSetChanged();
                    break;

                case R.id.leave_muti_pass:
                    if(deal == 1){
                        Toast.makeText(LeaveMutiChoose.this,"您已处理过这些假条，无需重复处理",Toast.LENGTH_SHORT).show();
                        break;
                    }else if(chooseCount() == 0){
                        Toast.makeText(LeaveMutiChoose.this,"请先选择内容",Toast.LENGTH_SHORT).show();
                        break;
                    }

                    new AlertDialog.Builder(LeaveMutiChoose.this).setTitle("全部通过").setMessage("确认通过?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //全部通过
                                    OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                    for(int i = 0;i<checks.size();i++){
                                        if(checks.get(i) == true){
                                                checks.remove(i);//从check里面删除
                                                LeaveContent leaveContent = new LeaveContent();
                                                leaveContent.studentNumber = (String)leaveList.remove(i).get("studentNumber");
                                                leaveContent.deal = 1;
                                                leaveContent.pass = 1;
                                                openOrCreateDB.updateLeave(leaveContent,"leaves");
                                            i --;//因为在上面删除了数据
                                        }
                                    }
                                    leaveAdapter.notifyDataSetChanged();
                                    openOrCreateDB.close();
                                    chooseState = 0;
                                    chooseAll.setText("全选");
                                    dialog.dismiss();
                                    Toast.makeText(LeaveMutiChoose.this,"已通过",Toast.LENGTH_SHORT).show();

                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    break;

                case R.id.leave_muti_reject:
                    if(deal == 1){
                        Toast.makeText(LeaveMutiChoose.this,"您已处理过这些假条，无需重复处理",Toast.LENGTH_SHORT).show();
                        break;
                    }else if(chooseCount() == 0){
                        Toast.makeText(LeaveMutiChoose.this,"请先选择内容",Toast.LENGTH_SHORT).show();
                        break;
                    }

                    new AlertDialog.Builder(LeaveMutiChoose.this).setTitle("全部拒绝").setMessage("确认拒绝?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //全部拒绝
                                    OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                    for(int i = 0;i<checks.size();i++){
                                        if(checks.get(i) == true){
                                                LeaveContent leaveContent = new LeaveContent();
                                                checks.remove(i);//从check里面删除
                                                leaveContent.studentNumber = (String)leaveList.remove(i).get("studentNumber");
                                                leaveContent.deal = 1;
                                                leaveContent.pass = 0;
                                                openOrCreateDB.updateLeave(leaveContent,"leaves");
                                            i --;//因为在上面删除了数据
                                        }
                                    }
                                    leaveAdapter.notifyDataSetChanged();
                                    openOrCreateDB.close();

                                    chooseState = 0;
                                    chooseAll.setText("全选");
                                    dialog.dismiss();
                                    Toast.makeText(LeaveMutiChoose.this,"已拒绝",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
                case R.id.leave_muti_delete:
                    if(deal == 0){
                        Toast.makeText(LeaveMutiChoose.this,"请您先处理这些假条",Toast.LENGTH_SHORT).show();
                        break;
                    }else if(chooseCount() == 0){
                        Toast.makeText(LeaveMutiChoose.this,"请先选择内容",Toast.LENGTH_SHORT).show();
                        break;
                    }

                    new AlertDialog.Builder(LeaveMutiChoose.this).setTitle("全部删除").setMessage("确认删除?")
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    //全部删除
                                    OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
                                    for(int i = 0;i<checks.size();i++){
                                        if(checks.get(i) == true){
                                            checks.remove(i);//从check里面删除
                                            openOrCreateDB.deleteLeave((String)leaveList.remove(i).get("studentNumber"),"leaves");
                                            i --;//因为在上面删除了数据
                                        }
                                    }
                                    leaveAdapter.notifyDataSetChanged();
                                    openOrCreateDB.close();
                                    chooseState = 0;
                                    chooseAll.setText("全选");
                                    dialog.dismiss();
                                    Toast.makeText(LeaveMutiChoose.this,"已删除",Toast.LENGTH_SHORT).show();
                                }
                            })
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    break;
            }
//            leaveAdapter.notifyDataSetChanged();
        }
    }

    private class MutiChooseItemAdapter extends BaseAdapter { //多选条目适配器，可以根据状态，显示checkbox的形态

        private Context context = null;
        private List<Map<String,Object>> message = null;

        public MutiChooseItemAdapter(Context context,List<Map<String,Object>> message ){
            this.context = context;
            this.message = message;
        }
        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            final ViewHolder holder;
            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.leave_message_muti_choose,null);
                holder = new ViewHolder();
                holder.checkBox = (CheckBox)convertView.findViewById(R.id.leave_message_checkbox);
                holder.studentName = (TextView)convertView.findViewById(R.id.counsellor_leave_student_name);
                holder.studentNumber = (TextView)convertView.findViewById(R.id.counsellor_leave_student_sn);
                holder.reasons = (TextView)convertView.findViewById(R.id.counsellor_leave_reason);
                holder.time = (TextView)convertView.findViewById(R.id.counsellor_leave_time);
                holder.pass = (TextView)convertView.findViewById(R.id.counsellor_leave_pass);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder)convertView.getTag();
            }
            //具体显示方式
            holder.checkBox.setChecked(checks.get(position));
            holder.studentName.setText((String)message.get(position).get("studentName"));
            holder.studentNumber.setText((String)message.get(position).get("studentNumber"));
            holder.reasons.setText((String)message.get(position).get("reasons"));
            holder.time.setText((String)message.get(position).get("time"));
            holder.pass.setText((String)message.get(position).get("pass"));
            holder.checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean bool = holder.checkBox.isChecked();
                    checks.set(position,bool);
                }
            });
            return convertView;
        }

        @Override
        public int getCount() {
            return checks.size();
        }

        @Override
        public Object getItem(int position) {
            return checks.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        private class ViewHolder{
            public CheckBox checkBox;
            public TextView studentName,studentNumber,reasons,time,pass;
        }
    }

    /**
     * 触摸事件，主要用来产生点击动画
     */
    private class OnTouch implements View.OnTouchListener{
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ImageView imageView = (ImageView)view;
            switch (motionEvent.getAction()) {
                case MotionEvent.ACTION_DOWN:

                    switch (view.getId()){
                        case R.id.leave_muti_pass:
                            if(deal ==1){
                                break;
                            }
                            imageView.setImageResource(R.drawable.pass1);
                            break;
                        case R.id.leave_muti_delete:
                            if(deal == 0){
                                break;
                            }
                            imageView.setImageResource(R.drawable.delete1);//删除的动画效果
                            break;
                        case R.id.leave_muti_reject:
                            if(deal ==1){
                                break;
                            }
                            imageView.setImageResource(R.drawable.reject1);
                            break;
                    }
                    return false;
                case MotionEvent.ACTION_UP:
                    switch (view.getId()){
                        case R.id.leave_muti_pass:
                            if(deal == 1){
                                imageView.setImageResource(R.drawable.pass0);
                            }else{
                                imageView.setImageResource(R.drawable.pass);
                            }
                            break;

                        case R.id.leave_muti_delete:
                            if(deal == 1){
                                imageView.setImageResource(R.drawable.delete);
                            }else{
                                imageView.setImageResource(R.drawable.delete0);
                            }
                            break;

                        case R.id.leave_muti_reject:
                            if(deal == 1){
                                imageView.setImageResource(R.drawable.reject0);
                            }else{
                                imageView.setImageResource(R.drawable.reject);
                            }
                            break;
                    }
                    return false;
                default:
                    return false;
            }
        }

    }


}
