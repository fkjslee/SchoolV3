package com.fkjslee.schoolv3.piece;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.NoteData;


/**
 * Created by fkjslee on 2017/3/8.
 * 一个假条的信息
 */
public class NotePiece extends LinearLayout {

    private View thisView = null;

    public NotePiece(Context context) {
        super(context);
        LayoutInflater mInflater = LayoutInflater.from(context);
        View view = mInflater.inflate(R.layout.note, null);
        view.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        addView(view);
        thisView = view;
    }

    public void setNoteData(NoteData note) {
        TextView tvContent = (TextView) thisView.findViewById(R.id.tvContent);
        CheckBox cbState = (CheckBox) thisView.findViewById(R.id.cbState);
        TextView tvStartTime = (TextView) thisView.findViewById(R.id.tvStartTime);
        tvContent.setText(note.getContent());
        cbState.setChecked(note.isState());
        tvStartTime.setText(note.getStartTime());
    }

}
