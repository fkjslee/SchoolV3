package com.fkjslee.schoolv3.piece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.MsgHomework;


/**
 *
 * Created by fkjslee on 2017/2/25.
 * 一个作业的界面
 */
public class PieceHomework extends LinearLayout {

    private View thisView = null;

    public PieceHomework(Context context) {
        super(context);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.homework_fragment, null);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        thisView = view;
    }

    public void setHomeworkData(MsgHomework data) {
        TextView tvHomework = (TextView)thisView.findViewById(R.id.tvHomework);
        TextView tvContent = (TextView)thisView.findViewById(R.id.tvContent);
        TextView tvStartTime = (TextView)thisView.findViewById(R.id.tvStartTime);
        TextView tvEndTime = (TextView)thisView.findViewById(R.id.tvEndTime);
        TextView tvRemindTime = (TextView)thisView.findViewById(R.id.tvRemindTime);
        tvHomework.setText(data.getTitle());
        tvContent.setText(data.getContent());
        tvStartTime.setText(data.getStartTime());
        tvEndTime.setText(data.getEndTime());
        tvRemindTime.setText(data.getRemindTime());
    }
}
