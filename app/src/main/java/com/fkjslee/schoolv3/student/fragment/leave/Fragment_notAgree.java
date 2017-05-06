package com.fkjslee.schoolv3.student.fragment.leave;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkjslee.schoolv3.R;

/**
 * @author fkjslee
 * @time 2017/5/6
 */

public class Fragment_notAgree extends Fragment{
    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave_not_agree, container, false);

        return view;
    }


}
