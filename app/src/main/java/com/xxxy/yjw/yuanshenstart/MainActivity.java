package com.xxxy.yjw.yuanshenstart;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.xxxy.yjw.yuanshenstart.dao.UserDao;
import com.xxxy.yjw.yuanshenstart.dao.Vote_RecordsDao;
import com.xxxy.yjw.yuanshenstart.dao.VotersDao;
import com.xxxy.yjw.yuanshenstart.model.Vote_records;
import com.xxxy.yjw.yuanshenstart.model.Voters;

import java.util.List;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new Thread(new Runnable() {
            @Override
            public void run() {
                VotersDao votersDao = new VotersDao();
                List<Voters> all = votersDao.getAll();
                Log.d("aaaaaaaaaaaaaaaaaaaaa",all.toString()+"");
                Vote_RecordsDao voteRecordsDao = new Vote_RecordsDao();
                List<Vote_records> all1 = voteRecordsDao.getAll();
                Log.d("vvvvvvvv",all1+"");


            }
        }).start();
    }
}