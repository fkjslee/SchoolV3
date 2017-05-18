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

}
