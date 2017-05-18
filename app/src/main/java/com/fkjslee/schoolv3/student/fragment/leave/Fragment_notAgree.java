package com.fkjslee.schoolv3.student.fragment.leave;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.student.activity.AskLeaveActivity;

/**
 * @author fkjslee
 * @time 2017/5/6
 */

public class Fragment_notAgree extends Fragment implements View.OnClickListener{
    private ImageView ivAdd;
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_leave_not_agree, container, false);
        initView();
        return view;
    }

    private void initView() {
        ivAdd = (ImageView)view.findViewById(R.id.iv_add);

        ivAdd.setOnClickListener(this);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_add:
                startActivity(new Intent(view.getContext(), AskLeaveActivity.class));
        }
    }
}
