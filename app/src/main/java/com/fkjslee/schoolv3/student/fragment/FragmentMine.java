package com.fkjslee.schoolv3.student.fragment;

import android.app.ActivityManager;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkjslee.schoolv3.LogActivity;
import com.fkjslee.schoolv3.R;

/**
 * @author fkjslee
 * @time 2017/5/24
 */

public class FragmentMine extends Fragment implements View.OnClickListener {

    private View view;
    private LinearLayout llChangeAccount;
    private LinearLayout llCompleteMsg;
    private FragmentMineListener fragmentMineListener;
    private Button btnQuit;
    private TextView tvName;
    private TextView tvIdentity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_mine, container, false);
        initView();
        return view;
    }

    private void initView() {
        llChangeAccount = (LinearLayout)view.findViewById(R.id.ll_changeAccount);
        llCompleteMsg = (LinearLayout)view.findViewById(R.id.ll_completeMsg);
        btnQuit = (Button)view.findViewById(R.id.btn_quit);
        tvName = (TextView)view.findViewById(R.id.tv_name);
        tvIdentity = (TextView)view.findViewById(R.id.tv_identity);

        tvName.setText("待定");
        tvIdentity.setText(LogActivity.loginIdentity);

        fragmentMineListener = (FragmentMineListener)getActivity();

        llChangeAccount.setOnClickListener(this);
        llCompleteMsg.setOnClickListener(this);
        btnQuit.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_changeAccount:
                fragmentMineListener.finishActivity();
                break;
            case R.id.ll_completeMsg:
                fragmentMineListener.startCompleteMsg();
                break;
            case R.id.btn_quit:
                fragmentMineListener.quit();
                break;
        }
    }

    public interface FragmentMineListener {
        void finishActivity();
        void startCompleteMsg();
        void quit();
    }
}
