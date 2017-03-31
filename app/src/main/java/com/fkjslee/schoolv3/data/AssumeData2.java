package com.fkjslee.schoolv3.data;

/**
 * Created by fkjslee on 2017/2/18.
 * 暂时代替数据库
 */
public class AssumeData2 {
//    public static MsgClass getData(int day, int number) {
//        if(day == 0) return null;
//        if(number == 0) return null;
//        if(day == 1) {
//            if(number == 3) return MsgClass.CO1;
//            if(number == 5) return MsgClass.CN1;
//            if(number == 9) return MsgClass.CO2;
//        }
//        if(day == 2) {
//            if(number == 3) return MsgClass.CAD1;
//            if(number == 5) return MsgClass.OS1;
//            if(number == 7) return MsgClass.PP1;
//        }
//        if(day == 3) {
//            if(number == 3) return MsgClass.CN2;
//            if(number == 5) return MsgClass.AA1;
//            if(number == 7) return MsgClass.BM1;
//            if(number == 9) return MsgClass.PP2;
//        }
//        if(day == 4) {
//            if(number == 3) return MsgClass.CO3;
//            if(number == 5) return MsgClass.CAD2;
//            if(number == 9) return MsgClass.CAD3;
//        }
//        if(day == 5) {
//            if(number == 3) return MsgClass.AA2;
//            if(number == 5) return MsgClass.OS2;
//            if(number == 7) return MsgClass.BM2;
//            if(number == 9) return MsgClass.PP3;
//        }
//        if(day == 6) {
//            if(number == 1) return MsgClass.CN3;
//            if(number == 3) return MsgClass.CO4;
//        }
//        if(day == 7) {
//            if(number == 3) return MsgClass.CO5;
//            if(number == 5) return MsgClass.OS3;
//            if(number == 7) return MsgClass.BM3;
//        }
//        return null;
//    }

    static public MsgHomework getHomeworkData(String classId, String teacherId, String stuId) {
        if(classId.length() == 0) return MsgHomework.h1;
        else return MsgHomework.h2;
    }

    static public NoteData getNoteData(String id) {
        if(id.length() == 0) return NoteData.note1;
        else return NoteData.note2;
    }
}
