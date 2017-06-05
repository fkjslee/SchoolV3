package com.fkjslee.schoolv3.counsellor;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.CompleteMsgActivity;
import com.fkjslee.schoolv3.teacher.activity.CompleteMsgActivityTeacher;

import java.util.ArrayList;
import java.util.List;

public class CounsellorLeaveActivty extends AppCompatActivity {

    Toolbar toolbar;
    private Fragment handledFragment,unhandledFragment;
    private int currIndex = 0;//界面的下标指示
    private List<Fragment> holder;
    //toolbar内容
    private Button handledButton,unhandledButton;
    private TextView authenticate = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counsellor_leave);
        init();
    }

    @Override
    protected void onResume() {
        //界面跳转之后的刷新操作

        super.onResume();
    }

    private void init(){
        toolbar = (Toolbar)findViewById(R.id.counsellor_toolbars);
        setSupportActionBar(toolbar);
        handledButton = (Button)findViewById(R.id.counsellor_leave_handled_bt);
        unhandledButton = (Button)findViewById(R.id.counsellor_leave_unhandled_bt);
        handledButton.setOnClickListener(new ButtonClick(1));
        unhandledButton.setOnClickListener(new ButtonClick(0));

        authenticate = (TextView)findViewById(R.id.counsellor_leave_authenticate);
        authenticate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CounsellorLeaveActivty.this, CompleteMsgActivity.class));
            }
        });

        holder = new ArrayList<>();
        holder.add(new UnhandledFragment());
        holder.add(new HandledFragment());

        SetCurr();

    }

    private void SetCurr(){
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.counsellor_leave_body,holder.get(currIndex));
        transaction.commit();
        if(currIndex == 1){
            handledButton.setBackgroundColor(Color.WHITE);
            handledButton.setTextColor(getResources().getColor(R.color.blue));
            unhandledButton.setTextColor(Color.WHITE);
            unhandledButton.setBackgroundResource(R.drawable.counsellor_leave_not);
            return;
        }
        unhandledButton.setBackgroundColor(Color.WHITE);
        unhandledButton.setTextColor(getResources().getColor(R.color.blue));
        handledButton.setTextColor(Color.WHITE);
        handledButton.setBackgroundResource(R.drawable.counsellor_leave_not);

    }

    private class ButtonClick implements View.OnClickListener{
        private int index;
        public ButtonClick(int i){
            index = i;
        }
        @Override
        public void onClick(View view) {
            currIndex = index;
            SetCurr();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.counsellor_toolbar,menu);
//        return true;
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()){
//            case R.id.muti_choose_pop://跳转到多选Activity，中间涉及到参数传递的问题
//                /*根据当前的下标，向新activity传递不同的内容*/
//                Intent intent = new Intent(CounsellorLeaveActivty.this,LeaveMutiChoose.class);
//                intent.putExtra("index",currIndex);
//                startActivity(intent);
//                break;
//            case R.id.clear_out_pop:
//                //弹出菜单，询问是否要清空当前
//                new AlertDialog.Builder(CounsellorLeaveActivty.this).setTitle("清空当前内容").setMessage("确认清空?")
//                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                                //具体清空操作
//                                if(currIndex == 0){
//                                    Toast.makeText(CounsellorLeaveActivty.this,"请您先处理请假信息",Toast.LENGTH_SHORT).show();
//                                }else{
//                                    //批量删除已处理的信息
//                                    OpenOrCreateDB openOrCreateDB = new OpenOrCreateDB();
//                                    openOrCreateDB.deleteLeaves("leaves");
////                                    HandledFragment.handled.clear();
////                                    HandledFragment.handled.addAll(Helper.setMapList(openOrCreateDB.getLeaveDatas("leaves"),1));
//                                    openOrCreateDB.close();
//                                    Toast.makeText(CounsellorLeaveActivty.this,"已清空",Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                        })
//                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();
//                break;
//        }
//        return true;
//    }

}
