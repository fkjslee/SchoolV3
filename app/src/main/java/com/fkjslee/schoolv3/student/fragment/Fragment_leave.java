package com.fkjslee.schoolv3.student.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.AskLeaveActivity;
import com.fkjslee.schoolv3.student.fragment.leave.Fragment_agree;
import com.fkjslee.schoolv3.student.fragment.leave.Fragment_notAgree;


/**
 * Created by fkjslee on 2017/2/18.
 * "请假" 碎片
 */

public class Fragment_leave extends Fragment implements View.OnClickListener{
    private Fragment_agree fragment_agree = new Fragment_agree();
    private Fragment_notAgree fragment_notAgree = new Fragment_notAgree();
    private Button btnAgree;
    private Button btnNotAgree;
    private View view;

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leave, container, false);
        initView();
        return view;
    }

    private void initView() {
        btnAgree = (Button)view.findViewById(R.id.btn_agree);
        btnNotAgree = (Button)view.findViewById(R.id.btn_notAgree);

        btnAgree.setOnClickListener(this);
        btnNotAgree.setOnClickListener(this);
        onClick(view.findViewById(R.id.btn_notAgree));
    }

    @Override
    public void onClick(View v) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch(v.getId()) {
            case R.id.btn_agree:
                btnNotAgree.setBackgroundResource(R.color.blue);
                btnNotAgree.setTextColor(Color.WHITE);
                btnAgree.setBackgroundResource(R.color.white);
                btnAgree.setTextColor(Color.BLUE);
                transaction.replace(R.id.leave_top_layout, fragment_agree);
                transaction.commit();
                break;
            case R.id.btn_notAgree:
                btnAgree.setBackgroundResource(R.color.blue);
                btnAgree.setTextColor(Color.WHITE);
                btnNotAgree.setBackgroundResource(R.color.white);
                btnNotAgree.setTextColor(Color.BLUE);
                transaction.replace(R.id.leave_top_layout, fragment_notAgree);
                transaction.commit();
                break;
            case R.id.iv_add:
                startActivity(new Intent(view.getContext(), AskLeaveActivity.class));
                break;
        }
    }
}