package com.fkjslee.schoolv3.teacher.function;

import com.fkjslee.schoolv3.counsellor.LeaveContent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by suoxin on 2017/5/12.
 */

public class StudentHelp {
    public static List<Map<String,Object>> setMapList(List<Student> list){
        List<Map<String,Object>> listMap = new ArrayList<>();
        for(Student stu : list){
            Map<String,Object> map = new HashMap<>();
            map.put(stu.array[0],stu.stuId);
            map.put(stu.array[1],stu.stuName);
            listMap.add(map);
        }
        return listMap;
    }


}
