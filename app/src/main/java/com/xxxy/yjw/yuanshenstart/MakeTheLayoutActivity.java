package com.xxxy.yjw.yuanshenstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxxy.yjw.yuanshenstart.Adapter.RadiosListAdapter;
import com.xxxy.yjw.yuanshenstart.Adapter.UserListAdapter;
import com.xxxy.yjw.yuanshenstart.model.Polls;
import com.xxxy.yjw.yuanshenstart.utils.Constant;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MakeTheLayoutActivity extends AppCompatActivity {
    private String title = "";
    private List<String> radios;
    private ListView lv_danxuan;
    //进行投票的学号和姓名
    private EditText btn_vote_studentId;
    private EditText btn_vote_name;
    private TextView btn_vote_content;
    private List<Polls> pollsList;
    private String js = "";
    private HashMap<String, Boolean> states = new HashMap<String, Boolean>();
    private RadiosListAdapter radiosListAdapter;
    private String xh_btn_vote_studentId ="";
    private String xm_btn_vote_name ="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_the_layout);
        Intent intent = getIntent();
        title = intent.getStringExtra("title");
        request_postlist(title);

        btn_vote_content = findViewById(R.id.btn_vote_content);
        btn_vote_content.setText(title);

        //单选
        lv_danxuan = findViewById(R.id.lv_danxuan);
        //绑定UserListAdapter
        lv_danxuan.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //拿到子项中的值
                TextView tv_item_radios = view.findViewById(R.id.tv_item_radios);
                RadioButton rb_itemButton = view.findViewById(R.id.rb_itemButton);

                if (!rb_itemButton.isChecked()) {
                    rb_itemButton.setChecked(true);
                } else {
                    rb_itemButton.setChecked(false);
                }
                js = tv_item_radios.getText().toString();
                rb_itemButton.performClick();


            }
        });


//        js = RadiosListAdapter.radioStr;
        Log.d("jsonItemClick: ", js);
        //返回投票结果
        Button btn_vote_sure = findViewById(R.id.btn_vote_sure);
        btn_vote_studentId = findViewById(R.id.btn_vote_studentId);
        btn_vote_name = findViewById(R.id.btn_vote_name);

        btn_vote_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                xh_btn_vote_studentId = btn_vote_studentId.getText().toString();
                xm_btn_vote_name = btn_vote_name.getText().toString();
//                if (xh_btn_vote_studentId!=null&&xh_btn_vote_studentId.equals("")&&xm_btn_vote_name!=null&&xm_btn_vote_name.equals("")){
//
//                }
                Log.d("jsonItemClick1: ", xh_btn_vote_studentId+""+xm_btn_vote_name);
                request_postlist1(title, xh_btn_vote_studentId, xm_btn_vote_name, js);
                Intent intent = new Intent(MakeTheLayoutActivity.this, VoteResultsActivity.class);
                intent.putExtra("title", title);
                startActivity(intent);
                finish();
            }
        });
    }

    private void request_postlist(String title) {
        String strURL = Constant.WEB_SITE + "/TPServlet";//请求地址
        FormBody title1 = new FormBody.Builder()
                .add("title", title)
                .build();
        Request request = new Request.Builder().url(strURL).post(title1).build();
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
                Log.d("make11: ",rep);
                pollsList = gson.fromJson(rep, new TypeToken<List<Polls>>() {
                }.getType());
                Log.d( "make22: ",pollsList.toString());
                radiosListAdapter = new RadiosListAdapter(MakeTheLayoutActivity.this, pollsList);
                lv_danxuan.setAdapter(radiosListAdapter);
            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }
        }
    };

    private void request_postlist1(String title, String user_uid, String user_uname, String content_name) {
        String strURL = Constant.WEB_SITE + "/TJServlet";//请求地址
        FormBody title1 = new FormBody.Builder()
                .add("title", title)
                .add("user_uid", user_uid)
                .add("user_uname", user_uname)
                .add("content_name", content_name)
                .build();
        Request request = new Request.Builder().url(strURL).post(title1).build();
        OkHttpClient okHttpClient = new OkHttpClient();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
            }
        });
    }










}