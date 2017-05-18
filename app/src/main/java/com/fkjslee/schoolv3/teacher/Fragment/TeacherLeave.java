package com.fkjslee.schoolv3.teacher.Fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.LeaveMutiChoose;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class TeacherLeave extends Fragment {

    private View view;
    private int currIndex = 0;//界面的下标指示
    private List<Fragment> holder;
    //toolbar内容
    private Button handledButton,unhandledButton;
    private TextView mutiChoose = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view=inflater.inflate(R.layout.fragment_teacher_leave, container, false);
        initView();
        return view;
    }

    private void initView(){

        handledButton = (Button)view.findViewById(R.id.teacher_leave_handled_bt);
        unhandledButton = (Button)view.findViewById(R.id.teacher_leave_unhandled_bt);
        handledButton.setOnClickListener(new ButtonClick(1));
        unhandledButton.setOnClickListener(new ButtonClick(0));

       /* mutiChoose = (TextView)view.findViewById(R.id.teacher_leave_muti);
        mutiChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),LeaveMutiChoose.class);
                intent.putExtra("index",currIndex);
                startActivity(intent);
            }
        });*/
        holder = new ArrayList<>();
        holder.add(new TeacherHandle());
        holder.add(new TeacherUnHandle());
        SetCurr();

    }

    private void SetCurr(){
       FragmentManager fragmentManager = getFragmentManager();
       FragmentTransaction transaction = fragmentManager.beginTransaction();
        FragmentTransaction replace;
        replace = transaction.replace(R.id.teacher_leave_body, holder.get(currIndex));
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
}
