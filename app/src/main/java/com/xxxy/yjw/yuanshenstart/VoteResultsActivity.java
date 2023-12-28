package com.xxxy.yjw.yuanshenstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxxy.yjw.yuanshenstart.Adapter.ProgressBarAdapter;
import com.xxxy.yjw.yuanshenstart.Adapter.VoteAdapter;
import com.xxxy.yjw.yuanshenstart.model.Count;
import com.xxxy.yjw.yuanshenstart.model.CountName;
import com.xxxy.yjw.yuanshenstart.model.NCount;
import com.xxxy.yjw.yuanshenstart.model.Polls;
import com.xxxy.yjw.yuanshenstart.model.TCount;
import com.xxxy.yjw.yuanshenstart.model.Vote_records;
import com.xxxy.yjw.yuanshenstart.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class VoteResultsActivity extends AppCompatActivity {
    //1、建立数据源
    String[] titles = new String[]{"张亚琼班", "快乐家园", "小仲", "张亚琼班", "快乐家园", "小仲", "张亚琼班", "快乐家园", "小仲"
    };

    int[] progressBars = new int[]{3, 2, 7, 3, 2, 7, 3, 2, 9
    };
    private RecyclerView vote;
    private String title = "";
    private List<Vote_records> vote_records;
    private String yTou = "";
    private String nTOu = "";
    //获取选项情况 和投票状态
    private List<TCount> tCountList;
    private Map<String, String> countMap;
    private Map<String, String> countNMap;

    private List<NCount> nCountList;
    private TextView tv_show_count1;
    private TextView tv_show_count0;
    private TextView btn_result_voted;
    private TextView btn_result_notVoted;
    private TextView btn_result_content;
    private Button backButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vote_results);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        btn_result_content = findViewById(R.id.btn_result_content);
        btn_result_content.setText(title);
        request_postlist(title);
        tv_show_count1 = findViewById(R.id.tv_show_count1);
        btn_result_voted = findViewById(R.id.btn_result_voted);
        btn_result_notVoted = findViewById(R.id.btn_result_notVoted);
        backButton = findViewById(R.id.backButton);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent1);
            }
        });


    }

    private void init() {
        vote = findViewById(R.id.recyclerView_1);
        //2.建立适配器
        ProgressBarAdapter adapter = new ProgressBarAdapter(getData(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        vote.setLayoutManager(layoutManager);
        //3.设置适配器
        vote.setAdapter(adapter);


    }

    //1.建立数据源第二步
    private List<Map<String, String>> getData() {
        List<Map<String, String>> myList = new ArrayList<Map<String, String>>();
        for (int i = 0; i < tCountList.size(); i++) {
            Map<String, String> map = new HashMap<>();
            map.put("title", tCountList.get(i).getContent_show());
            map.put("progressBar", tCountList.get(i).getCount());
            myList.add(map);
        }
        return myList;
    }


    private void request_postlist(String title) {
        String strURL = Constant.WEB_SITE + "/TCountServlet";//请求地址
        String strURL1 = Constant.WEB_SITE + "/CountServlet";//请求地址
        String strURL2 = Constant.WEB_SITE + "/UNumServlet";//请求地址
        FormBody title1 = new FormBody.Builder().add("title", title).build();
        Request request = new Request.Builder().url(strURL).post(title1).build();
        Request request1 = new Request.Builder().url(strURL1).post(title1).build();
        Request request2 = new Request.Builder().url(strURL2).post(title1).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        //请求选项内容
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
        //请求投情况
        Call call1 = okHttpClient.newCall(request1);
        call1.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e.getMessage();
                //通过handler向主线程发送json数据
                handler1.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rep = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = rep;
                handler1.sendMessage(message);
            }
        });

        Call call2 = okHttpClient.newCall(request2);
        call2.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message message = new Message();
                message.what = 2;
                message.obj = e.getMessage();
                //通过handler向主线程发送json数据
                handler2.sendMessage(message);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String rep = response.body().string();
                Message message = new Message();
                message.what = 1;
                message.obj = rep;
                handler2.sendMessage(message);
            }
        });
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String rep = (String) msg.obj;//获取json串
                Log.d("vvvvhandleMessage: ", rep.toString());
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                JSONArray pollsTitleArray = null;
                JSONArray contentShowArray = null;
                JSONArray countArray = null;
                try {
                    tCountList = new ArrayList<>();
                    jsonObject = new JSONObject(rep);
                    pollsTitleArray = jsonObject.getJSONArray("polls.polls_title");
                    contentShowArray = jsonObject.getJSONArray("content.content_show");
                    countArray = jsonObject.getJSONArray("count(*)");
                    for (int i = 0; i < pollsTitleArray.length(); i++) {
                        TCount tCount = new TCount();
                        tCount.setPolls_title(pollsTitleArray.getString(i).toString());
                        tCount.setContent_show(contentShowArray.getString(i));
                        tCount.setCount(countArray.getString(i));
                        tCountList.add(tCount);
                    }

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                init();
            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }
        }
    };

    private Handler handler1 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String rep = (String) msg.obj;//获取json串
                Log.d("ddddhandleMessage: ", rep.toString().toString());
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                String value0 = null;
                String value1 = null;
                try {
                    countMap = new HashMap<>();
                    jsonObject = new JSONObject(rep);
                    value0 = jsonObject.getString("0");
                    value1 = jsonObject.getString("1");
                    countMap.put("0", value0);
                    countMap.put("1", value1);

//                    btn_result_voted.setText(countMap.get("1").toString());
//                    btn_result_notVoted.setText(countMap.get("0").toString());

                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }

            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }
        }
    };

    private Handler handler2 = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 1) {
                String rep = (String) msg.obj;//获取json串
                Log.d("sssshandleMessage:", rep.toString());
                Gson gson = new Gson();
                JSONObject jsonObject = null;
                JSONArray array0 = null;
                JSONArray array1 = null;
                countNMap = new HashMap<>();
                try {
                    jsonObject = new JSONObject(rep);
                    array0 = jsonObject.getJSONArray("0");
                    countNMap.put("0", array0.toString());
                    btn_result_notVoted.setText(countNMap.get("0").replace("[", "").replace("]", "").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                try {
                    array1 = jsonObject.getJSONArray("1");
                    countNMap.put("1", array1.toString());
                    btn_result_voted.setText(countNMap.get("1").replace("[", "").replace("]", "").toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }
        }
    };
}
