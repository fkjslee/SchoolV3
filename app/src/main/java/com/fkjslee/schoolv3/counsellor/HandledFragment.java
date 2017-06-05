package com.fkjslee.schoolv3.counsellor;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.fkjslee.schoolv3.R;
import com.fkjslee.schoolv3.counsellor.wegit.LoadDialog;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Xiaojun on 2017/5/7.
 */

public class HandledFragment extends Fragment {
    private View view = null;
    private ListView listViewHandled = null;
    private SimpleAdapter handledAdapter = null;
    private List<Map<String,Object>> handled;
    private List<NoteForIns> noteList;
    private JSONArray jsonArray;
    private int chooseIndex;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.counsellor_leave_handled,container,false);
        return view;
    }

    @Override
    public void onResume() {
        getDataFromServer();
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getDataFromServer(){
        LoadDialog.show(getActivity());
        HttpUtil.Request(HttpUtil.COUNSELLOR_GET_LEAVE_HANDLED,1);//在请求假条数据的时候，第二个int参数实际并没有使用
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataGot(HttpEvent httpEvent){    //网络请求的数据回调
        if (httpEvent.getIsError() == 1){
            Toast.makeText(getActivity(),"网络请求失败",Toast.LENGTH_LONG).show();
            LoadDialog.dismiss(getActivity());
            return;
        }
        if (httpEvent.getType() == HttpUtil.COUNSELLOR_GET_LEAVE_HANDLED){
            try {
                jsonArray = new JSONArray(httpEvent.getMsg());
                if (jsonArray == null) {  //有的时候会获取json数组失败，不知道原因
                    Toast.makeText(getActivity(), "获取数据失败，请重试", Toast.LENGTH_LONG).show();
                } else {
                    noteList = new ArrayList<NoteForIns>();
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        NoteForIns tem = new NoteForIns(obj);
                        noteList.add(tem);
                    }
                    if (handled == null) {
                        handled = Helper.setMapList1(noteList);
                    } else {
                        handled.clear();
                        handled.addAll(Helper.setMapList1(noteList));
                    }
                    initHandledView();
                }
            } catch(JSONException e){
                e.printStackTrace();
            }
            LoadDialog.dismiss(getActivity());
        }else if (httpEvent.getType() == HttpUtil.COUNSELLOR_DELETE_LEAVE){
            LoadDialog.dismiss(getActivity());
            Toast.makeText(getActivity(),"已删除",Toast.LENGTH_SHORT).show();
            handled.remove(chooseIndex);
            handledAdapter.notifyDataSetChanged();
        }

    }

    private void initHandledView(){ //初始化已处理界面
        listViewHandled = null;
        listViewHandled = (ListView) view.findViewById(R.id.counsellor_leave_handled);
        handledAdapter = new SimpleAdapter(view.getContext(),handled,R.layout.counsellor_leave_item,
                new String[]{"sId","sName","content","applyTime","state"},
                new int[]{R.id.counsellor_leave_student_sn,R.id.counsellor_leave_student_name,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time,R.id.counsellor_leave_pass});
        listViewHandled.setAdapter(handledAdapter);

        listViewHandled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(HandledFragment.this.getActivity(),ShowLeaveDetail.class);
                /*传递假条对象*/
                NoteForIns noteForIns =Helper.getNoteFromMap(handled.get(i));
                intent.putExtra("note",noteForIns);
                intent.putExtra("deal",1);//表示这些假条未处理
                startActivity(intent);
            }
        });

        listViewHandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, final int i, long l) {
                chooseIndex = i;
                new AlertDialog.Builder(view.getContext()).setTitle("删除这条记录").setMessage("确认删除?")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoadDialog.show(getActivity());
                                HttpUtil.Request(HttpUtil.COUNSELLOR_DELETE_LEAVE,Integer.parseInt((String)handled.get(chooseIndex).get("noteId")));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).show();

                return true;//当同时设置了点击事件和长按事件时，这里要设置为true，不然长按之后还会激发点击事件
            }
        });

    }

}



