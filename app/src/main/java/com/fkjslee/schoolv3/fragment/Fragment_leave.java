package com.fkjslee.schoolv3.fragment;

import android.annotation.TargetApi;
import android.app.Fragment;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.data.AssumeData2;
import com.fkjslee.schoolv3.data.NoteData;
import com.fkjslee.schoolv3.piece.NotePiece;


/**
 * Created by fkjslee on 2017/2/18.
 * "请假" 碎片
 */

public class Fragment_leave extends Fragment {

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_leave, container, false);

        LinearLayout top_layout = (LinearLayout)view.findViewById(R.id.top_layout);
        NoteData note1 = AssumeData2.getNoteData("");
        NoteData note2 = AssumeData2.getNoteData("2");
        NotePiece notePiece1 = new NotePiece(view.getContext());
        NotePiece notePiece2 = new NotePiece(view.getContext());
        notePiece1.setNoteData(note1);
        notePiece2.setNoteData(note2);
        top_layout.addView(notePiece1);
        top_layout.addView(notePiece2);
        return view;
    }

}