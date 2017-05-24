package com.fkjslee.schoolv3.teacher.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fkjslee.schoolv3.R;

/**
 * @author fkjslee
 * @time 2017/5/24
 */

public class FragmentTeacherTab extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.teacher_tab, container, false);
        return view;
    }

}