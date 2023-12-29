package com.xxxy.yjw.yuanshenstart;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxxy.yjw.yuanshenstart.Adapter.VoteAdapter;
import com.xxxy.yjw.yuanshenstart.model.ShowIndex;
import com.xxxy.yjw.yuanshenstart.model.Vote_records;
import com.xxxy.yjw.yuanshenstart.utils.Constant;
import java.io.IOException;
import java.util.List;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.view.View;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {
    String[] vote_titles = new String[]{"游戏投票", "游戏投票", "游戏投票", "游戏投票"};
    private RecyclerView rv_Vote;
    private List<ShowIndex> showIndexList;//投票内容
    private VoteAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //请求
        request_postlist();
        //发起投票按钮
        LinearLayout manager_button = findViewById(R.id.manager_button);
        manager_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, InitiateVoteActivity.class);
                startActivity(intent);
            }
        });
        SwipeRefreshLayout sw_fresh = findViewById(R.id.sw_fresh);
//        rv_Vote.setLayoutManager(new LinearLayoutManager(this));

        sw_fresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request_postlist();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        sw_fresh.setRefreshing(false);
                    }
                },500);
            }
        });



    }

    private void init() {
        rv_Vote = findViewById(R.id.rl_votes_show);
        //2.建立适配器
        adapter = new VoteAdapter(getData(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rv_Vote.setLayoutManager(layoutManager);
        //3.设置适配器
        rv_Vote.setAdapter(adapter);

    }

    private List<Map<String, Object>> getData() {
        List<Map<String, Object>> myList = new ArrayList<Map<String, Object>>();
        for (int i = 0; i < showIndexList.size(); i++) {
            Map<String, Object> map = new HashMap<>();
            map.put("itemid", showIndexList.get(i).getPollsid());
            map.put("itemtitle", vote_titles[1]);
            map.put("iteminfo",showIndexList.get(i).getPolls_title());
            map.put("itempeople",showIndexList.get(i).getUsers_id().getUserName());
            myList.add(map);
        }
        return myList;
    }


    private void request_postlist() {
        String strURL = Constant.WEB_SITE + "/TPServlet";
        Request request = new Request.Builder().url(strURL).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e.getMessage();
                //通过handler向主线程发送json数据
                handler.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rep = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = rep;
                handler.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String rep = (String) msg.obj;//获取json串
                Gson gson = new Gson();
                showIndexList=gson.fromJson(rep, new TypeToken<List<ShowIndex>>() {
                }.getType());
                init();
            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }

        }
    };
}

