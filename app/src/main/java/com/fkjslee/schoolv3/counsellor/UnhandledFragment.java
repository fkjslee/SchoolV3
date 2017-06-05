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
import android.widget.TextView;
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

/**
 * eventBus使用测试
 */
public class UnhandledFragment extends Fragment{
    private View view = null;
    private ListView listViewUnhandled = null;
    private SimpleAdapter unhandledAdapter = null;
    private List<Map<String,Object>> unhandled;
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
        view = inflater.inflate(R.layout.counsellor_leave_unhandled,container,false);
        return view;
    }

    @Override
    public void onResume() {
        getDataFromServer();//保证每次打开这个界面数据都会刷新
        super.onResume();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    private void getDataFromServer(){
        LoadDialog.show(getActivity());
        HttpUtil.Request(HttpUtil.COUNSELLOR_GET_LEAVE_UNHANDLED,0);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onDataGot(HttpEvent httpEvent){    //网络请求的数据回调
        if (httpEvent.getIsError() == 1){
            Toast.makeText(getActivity(),"网络请求失败",Toast.LENGTH_LONG).show();
            LoadDialog.dismiss(getActivity());
            return;
        }
        if (httpEvent.getType() == HttpUtil.COUNSELLOR_GET_LEAVE_UNHANDLED){
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
                    if (unhandled == null) {
                        unhandled = Helper.setMapList1(noteList);
                    } else {
                        unhandled.clear();
                        unhandled.addAll(Helper.setMapList1(noteList));
                    }
                    initUnhandledView();
                }
            } catch(JSONException e){
                e.printStackTrace();
            }
            LoadDialog.dismiss(getActivity());
        }else if(httpEvent.getType() == HttpUtil.COUNSELLOR_AGREE_LEAVE){
            LoadDialog.dismiss(getActivity());
            Toast.makeText(getActivity(),"已同意",Toast.LENGTH_SHORT).show();
            unhandled.remove(chooseIndex);
            unhandledAdapter.notifyDataSetChanged();
        }else if (httpEvent.getType() == HttpUtil.COUNSELLOR_REJECT_LEAVE){
            LoadDialog.dismiss(getActivity());
            Toast.makeText(getActivity(),"已拒绝",Toast.LENGTH_SHORT).show();
            unhandled.remove(chooseIndex);
            unhandledAdapter.notifyDataSetChanged();
        }

    }

    //初始化未处理界面
    private void initUnhandledView(){
        listViewUnhandled = null;
        listViewUnhandled = (ListView) view.findViewById(R.id.counsellor_leave_unhandled);
        unhandledAdapter = new SimpleAdapter(view.getContext(),unhandled,R.layout.counsellor_leave_item,
                new String[]{"sId","sName","content","applyTime"},
                new int[]{R.id.counsellor_leave_student_sn,R.id.counsellor_leave_student_name,
                        R.id.counsellor_leave_reason,R.id.counsellor_leave_time});
        listViewUnhandled.setAdapter(unhandledAdapter);

        /*为未处理的请假请求添加点击事件和长按事件：点击事件跳转到完整的假条显示，长按可以编辑单个条目*/

        listViewUnhandled.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(UnhandledFragment.this.getActivity(),ShowLeaveDetail.class);
                /*传递假条对象*/
                NoteForIns noteForIns = Helper.getNoteFromMap(unhandled.get(i));
                intent.putExtra("note",noteForIns);
                intent.putExtra("deal",0);//表示这些假条未处理
                startActivity(intent);
            }
        });


        //同意或者拒绝请假只需要提交nodeId和处理结果
        listViewUnhandled.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, final View view, int i, long l) {
                chooseIndex = i;
                //弹出菜单，询问是否要清空当前
                new AlertDialog.Builder(view.getContext()).setTitle("同意请假？")
                        .setPositiveButton("同意", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoadDialog.show(getActivity());
                                HttpUtil.Request(HttpUtil.COUNSELLOR_REJECT_LEAVE,Integer.parseInt((String) unhandled.get(chooseIndex).get("noteId")));
                                dialog.dismiss();
                            }
                        })
                        .setNegativeButton("拒绝", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                LoadDialog.show(getActivity());
                                HttpUtil.Request(HttpUtil.COUNSELLOR_REJECT_LEAVE,Integer.parseInt((String) unhandled.get(chooseIndex).get("noteId")));
                                dialog.dismiss();
                            }
                        }).show();

                return true;
            }
        });
    }
}
