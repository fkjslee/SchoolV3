package com.fkjslee.schoolv3.teacher;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.LeaveContent;
import com.fkjslee.schoolv3.counsellor.ShowLeaveDetail;
import com.fkjslee.schoolv3.counsellor.UnhandledFragment;
import com.fkjslee.schoolv3.student.activity.LogActivity;
import com.fkjslee.schoolv3.student.data.MsgClass;
import com.fkjslee.schoolv3.student.function.MyCommonFunction;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

public class CourseSignActivity extends AppCompatActivity {

    public static Handler handler = null;
    private ImageView imageView;
    private TextView courseName;
    private TextView classroom;
    private TextView week;
    private TextView leaveNum;
    private TextView signNum;
    private TextView absentNum;
    private String weekday;
    private String weeks;
    private String period;

    private MsgClass msg = null;
    private Integer spinnerWeek;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_sign);
        Toolbar toolbar = (Toolbar) findViewById(R.id.course_sign_toolbar);
        setSupportActionBar(toolbar);
        init();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    protected void onResume() {

        super.onResume();
        try {
            getTeacherSignData();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void init() {
        imageView = (ImageView) findViewById(R.id.course_sign_backspace);
        courseName = (TextView) findViewById(R.id.course_sign_coursename);
        classroom = (TextView) findViewById(R.id.course_sign_classroom);
        week = (TextView) findViewById(R.id.course_sign_week);
        leaveNum = (TextView) findViewById(R.id.course_sign_leavenum);
        signNum = (TextView) findViewById(R.id.course_sign_signnum);
        absentNum = (TextView) findViewById(R.id.course_sign_absentnum);
        Intent intent = getIntent();
        msg = (MsgClass) getIntent().getSerializableExtra("classMsg");
        spinnerWeek = (Integer) getIntent().getSerializableExtra("spinnerWeek");
        courseName.setText(msg.getName());
        classroom.setText(msg.getPosition());
        week.setText(msg.getWeeks());
        absentNum.setOnClickListener(new ToAbsentNum());
        leaveNum.setOnClickListener(new ToLeaveNum());
        /*
        *
        * */
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseSignActivity.this.finish();
            }
        });

    }

    private void getTeacherSignData() throws JSONException {

        String param = "type=getSignRes&telephone=" + LogActivity.logAccount + "&week=" + spinnerWeek
                + "&weekday=" + msg.getWeekday() + "&courseBegin=" + msg.getStartTime() +
                "&length=" + msg.getLength();
        String result = MyCommonFunction.sendRequestToServer(param);
        //解析数据 signNUm,absentNum,leaveNum;
        JSONObject json = new JSONObject(result);
        leaveNum.setText("请假：  "+json.getString("leave"));
        signNum.setText("签到:  "+json.getString("succeed"));
        absentNum.setText("缺席  "+json.getString("failed"));
    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("CourseSign Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }

    private class ToAbsentNum implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), AbsentStuActivity.class);
            intent.putExtra("classMsg", msg);
            intent.putExtra("spinnerWeek", spinnerWeek);
            startActivity(intent);
        }
    }

    private class ToLeaveNum implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(getApplicationContext(), LeaveStuActivity.class);
            intent.putExtra("classMsg", msg);
            intent.putExtra("spinnerWeek", spinnerWeek);
            startActivity(intent);
        }
    }
}
