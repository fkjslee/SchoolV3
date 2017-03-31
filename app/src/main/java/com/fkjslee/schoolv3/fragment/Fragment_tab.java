package com.fkjslee.schoolv3.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkjslee.schoolv3.R;


/**
 * Created by fkjslee on 2017/2/18.
 * 显示"课表, 请假, 讨论"的碎片
 */

public class Fragment_tab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        return view;
    }

}