package com.fkjslee.schoolv3.counsellor;

/**
 * Created by Xiaojun on 2017/5/7.
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 这个类里面放的是一些静态函数，为别的类公用
 */
public class Helper {
    /*也许这个函数会被复用
* 把list里面的内容格式化存到listMap里面*/
    public static List<Map<String,Object>> setMapList(List<LeaveContent> list, int deal){
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(LeaveContent leaveContent : list){
            if(leaveContent.deal != deal)
                continue;
            Map<String,Object> map = new HashMap<>();
            map.put(LeaveContent.array[0],leaveContent.studentName);
            map.put(LeaveContent.array[1],leaveContent.studentNumber);
            map.put(LeaveContent.array[2],leaveContent.reasons);
            map.put(LeaveContent.array[3],leaveContent.startTime);
            map.put(LeaveContent.array[4],leaveContent.endTime);
            String str = "";
            if (leaveContent.pass == 1){
                str = "已批准";
            }else if (leaveContent.pass == 0){
                str = "已拒绝";
            }
            map.put(LeaveContent.array[4],str);
            //这个里面不需要添加是否已经处理的信息
            listMap.add(map);
        }
        return listMap;
    }

    public static List<Map<String,Object>> setMapList1(List<NoteForIns> list){
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(NoteForIns noteForIns : list){
            Map<String,Object> map = new HashMap<>();
            map.put(NoteForIns.array[0],noteForIns.getsId());
            map.put(NoteForIns.array[1],noteForIns.getsName());
            map.put(NoteForIns.array[2],noteForIns.getContent());
            map.put(NoteForIns.array[3],noteForIns.getStartTime());
            map.put(NoteForIns.array[4],noteForIns.getEndTime());
            map.put(NoteForIns.array[5],noteForIns.getApplyTime());
            map.put(NoteForIns.array[6],noteForIns.getNoteId());
            String str = "";
            if (noteForIns.getState() == 1){
                str = "已批准";
            }else if (noteForIns.getState() == -1){
                str = "已拒绝";
            }
            map.put(NoteForIns.array[7],str);
            //这个里面不需要添加是否已经处理的信息
            listMap.add(map);
        }
        return listMap;
    }

    public static NoteForIns getNoteFromMap(Map<String,Object> map){
        NoteForIns noteForIns = new NoteForIns();
        int i = -1;
        noteForIns.setsId((String) map.get(NoteForIns.array[++i]));
        noteForIns.setsName((String) map.get(NoteForIns.array[++i]));
        noteForIns.setContent((String) map.get(NoteForIns.array[++i]));
        noteForIns.setStartTime((String) map.get(NoteForIns.array[++i]));
        noteForIns.setEndTime((String) map.get(NoteForIns.array[++i]));
        noteForIns.setApplyTime((String) map.get(NoteForIns.array[++i]));
        noteForIns.setNoteId((String) map.get(NoteForIns.array[++i]));
//        noteForIns.setState((int) map.get(NoteForIns.array[++i]));  显示的时候暂时不显示
        return noteForIns;
    }

}
