package com.fkjslee.schoolv3.teacher.fragment;


import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.fkjslee.schoolv3.R;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentTeacherLeave extends Fragment implements View.OnClickListener {
    private FragmentTeacherHasSeen teacherHasSeen = new FragmentTeacherHasSeen();
    private FragmentTeacherNotSeen teacherNotSeen = new FragmentTeacherNotSeen();

    private View view;

    private Button btnHasSeen;
    private Button btnNotSeen;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_teacher_leave, container, false);
        initView();
        return view;
    }

    private void initView() {
        btnHasSeen = (Button) view.findViewById(R.id.btn_hasSeen);
        btnNotSeen = (Button) view.findViewById(R.id.btn_notSeen);

        btnHasSeen.setOnClickListener(this);
        btnNotSeen.setOnClickListener(this);
        onClick(view.findViewById(R.id.btn_notSeen));
    }

    @Override
    public void onClick(View view) {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        switch (view.getId()) {
            case R.id.btn_hasSeen:
                btnHasSeen.setBackgroundColor(Color.WHITE);
                btnHasSeen.setTextColor(getResources().getColor(R.color.blue));
                btnNotSeen.setBackgroundResource(R.color.blue);
                btnNotSeen.setTextColor(getResources().getColor(R.color.white));
                transaction.replace(R.id.teacher_leave_body, teacherHasSeen);
                transaction.commit();
                break;
            case R.id.btn_notSeen:
                btnNotSeen.setBackgroundColor(Color.WHITE);
                btnNotSeen.setTextColor(getResources().getColor(R.color.blue));
                btnHasSeen.setBackgroundResource(R.color.blue);
                btnHasSeen.setTextColor(getResources().getColor(R.color.white));
                transaction.replace(R.id.teacher_leave_body, teacherNotSeen);
                transaction.commit();
                break;
        }
    }
}
