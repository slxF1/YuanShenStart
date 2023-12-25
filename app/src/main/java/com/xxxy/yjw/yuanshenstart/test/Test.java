package com.xxxy.yjw.yuanshenstart.test;

import android.util.Log;

import com.xxxy.yjw.yuanshenstart.dao.Vote_RecordsDao;
import com.xxxy.yjw.yuanshenstart.dao.VotersDao;
import com.xxxy.yjw.yuanshenstart.model.Vote_records;
import com.xxxy.yjw.yuanshenstart.model.Voters;


import java.util.List;

public class Test {
    public static void main(String[] args) {
        Test1();
        Test2();
    }
    public static void Test1(){
        List<Voters> all = new VotersDao().getAll();
        System.out.println(all);
    }
    public static void Test2(){
        List<Vote_records> all = new Vote_RecordsDao().getAll();
        System.out.println(all);
    }


}
