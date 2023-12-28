package com.xxxy.yjw.yuanshenstart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.xxxy.yjw.yuanshenstart.Adapter.UserListAdapter;
import com.xxxy.yjw.yuanshenstart.model.Content;
import com.xxxy.yjw.yuanshenstart.model.DataShow;
import com.xxxy.yjw.yuanshenstart.model.Users;
import com.xxxy.yjw.yuanshenstart.utils.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class InitiateVoteActivity extends AppCompatActivity {
    private LinearLayout linearLayout_info;
    private int editTextCount = 0;
    private List<String> users;
    private List<String> gradeArray;
    private ListView lv_users;
    private Spinner sq_info1;
    private Spinner sq_info2;
    private EditText initiate_title;
    private EditText initiate_person;
    private EditText initiate_num;
    private Button initiate_btn_FaBu;
    private String tpContext;//投票内容
    private String stringxh = "";
    private String stringxm = "";
    private List<DataShow> dataShowList;
    private List<Users> usersList;
    private List<Content> contentList;
    private List<String> usernameList ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiate_vote);
        //验证按钮
        Button btn_yz = findViewById(R.id.btn_yz);
        //拿到发起人信息
        initiate_person = findViewById(R.id.initiate_person);
        initiate_num = findViewById(R.id.initiate_num);

        btn_yz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取请求
                request_postlist(initiate_num.getText().toString(),initiate_person.getText().toString());
            }
        });
        //checkBox
        lv_users = findViewById(R.id.lv_users);
        //spinner设置前两个Spinner
        sq_info1 = findViewById(R.id.sq_info1);
        sq_info2 = findViewById(R.id.sq_info2);
        //遍历linearLayout
        linearLayout_info = findViewById(R.id.linearLayout_info);
        //返回按钮
        Button initiate_btn_back = findViewById(R.id.initiate_btn_back);
        initiate_btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InitiateVoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

        initiate_title = findViewById(R.id.initiate_title);
        tpContext = initiate_title.getText().toString();//拿到投票内容

        //发布按钮
        initiate_btn_FaBu = findViewById(R.id.initiate_btn_FaBu);
        final String[] name = {""};
        initiate_btn_FaBu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //遍历LinearLayout中每一个Spinner
                int LLchildCount = linearLayout_info.getChildCount();
                for (int i = 0; i < LLchildCount; i++) {
                    View childView = linearLayout_info.getChildAt(i);
                    if (childView instanceof Spinner) {
                        Spinner spinner = ((Spinner) childView);
                        //SpinnerStr.add(gradeArray[i]);
                        Log.d("1111emSelected: ", spinner.getSelectedItem().toString());
//                        SpinnerStr.add( spinner.getSelectedItem().toString());
//                        Integer selectedItem = (Integer) spinner.getSelectedItem();
                        name[0] = name[0] + " " + spinner.getSelectedItemId();
                        Log.d("ossssnClick: ", name[0]);

                    }
                }


                //获取页面所有内容
                Editable initiate_titleText = initiate_title.getText();
                stringxh = initiate_num.getText().toString();
                stringxm = initiate_person.getText().toString();
                //放入到一个Map中向后端发送json串写入数据库
                Log.d("onClick: ","title: " + initiate_titleText +
                        " 学号: "+ stringxh +
                        " 姓名: " + stringxm +
                        " 人员: " + usernameList +
                        ":" + name[0].toString());


                String [] arr = name[0].split(" ");
                String [] arr1 = new String[arr.length-1];
                for (int i = 1; i < arr.length ; i++) {
                    arr1[i-1] = arr[i];
                }
                Log.d("arrayonClick: ",arr1[0]);
                List<String> list = Arrays.asList(arr1);
                Gson gson = new Gson();
                List<Map<String,String>>  list1 = new ArrayList<>();
                for (int i = 0; i < list.size(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("fauser_id",stringxh);
                    map.put("fauser_name",stringxm);
                    map.put("polls_title",initiate_titleText.toString());
                    map.put("content_i",arr1[i]);
                    list1.add(map);
                }
                List<Map<String,String>>  list2 = new ArrayList<>();
                for (int i = 0; i < usernameList.size(); i++) {
                    HashMap<String, String> map = new HashMap<>();
                    map.put("touuser_name",usernameList.get(i));
                    map.put("polls_title",initiate_titleText.toString());
                    list2.add(map);
                }

                String gson1 = gson.toJson(list1);
                String gson2 = gson.toJson(list2);
                request_postlist1(gson1,gson2);


                //跳转主页
                Intent intent = new Intent(InitiateVoteActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });


        //点击加号使用addSpinner方法
        ImageView iv_etadd = findViewById(R.id.iv_etadd);
        iv_etadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addSpinner();
            }
        });
    }


    private void addSpinner() {
        List<String> spName = new ArrayList<>();
        for (int i = 0; i <contentList.size() ; i++) {
            spName.add(contentList.get(i).getContentshow());
        }
        //通过计数器editText每次点击增加，然后赋值相应的id
        if (editTextCount < 4) {
            Spinner newsp = new Spinner(this);
            linearLayout_info.addView(newsp); // 将Spinner添加到LinearLayout中

            //遍历linearLayout获取当前Spinner对象
            int LLchildCount = linearLayout_info.getChildCount();
            for (int i = 0; i < LLchildCount; i++) {
                View childView = linearLayout_info.getChildAt(i);
                if (childView instanceof Spinner) {
                    ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, spName);
                    ((Spinner) childView).setAdapter(gradeAdapter);
                }
            }

            // 更新已添加的Spinner数量
            editTextCount += 1;
            Toast.makeText(this, "已添加第 " + editTextCount + " 个EditText", Toast.LENGTH_SHORT).show(); // 显示提示信息

        } else {
            Toast.makeText(this, "数量上限", Toast.LENGTH_SHORT).show();
        }
    }

    private void request_postlist(String initiate_num,String initiate_person) {
        String strURL = Constant.WEB_SITE + "/UserServlet";//请求地址
        FormBody body = new FormBody.Builder()
                .add("name", initiate_person)
                .add("password",initiate_num)
                .build();
        Request request = new Request.Builder().url(strURL).post(body).build();
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
                Log.d("dddddd: ",rep.toString());
                Gson gson = new Gson();
//                dataShowList = gson.fromJson(rep, new TypeToken<List<DataShow>>() {
//                }.getType());
                JSONObject jsonObject = null;
                JSONArray usersArray = null;
                JSONArray contentArray = null;
                try {
                    jsonObject = new JSONObject(rep);
                    usersArray = jsonObject.getJSONArray("users");
                    contentArray = jsonObject.getJSONArray("content");
                    Log.d("usersArrayhandlege: ",usersArray.toString());
                    Log.d("contentArrayhandlege: ",contentArray.toString());
                    usersList = new ArrayList<>();
                    contentList = new ArrayList<>();
                    gradeArray = new ArrayList<>();
                    Log.d( "usersList: ",usersArray.length()+"");
                    for (int i = 0; i < usersArray.length(); i++) {
                        JSONObject userObject = usersArray.getJSONObject(i);
                        Users users1 = new Users();
                        users1.setUserid(userObject.getInt("userid"));
                        users1.setUseruid(userObject.getInt("useruid"));
                        users1.setUserName(userObject.getString("userName"));
                        usersList.add(users1);
                    }

                    for (int i = 0; i < contentArray.length(); i++) {
                        JSONObject contentObject = contentArray.getJSONObject(i);
                        Content content = new Content();
                        content.setContentid(contentObject.getInt("contentid"));
                        content.setContentshow(contentObject.getString("contentshow"));
                        contentList.add(content);
                        gradeArray.add(contentObject.getString("contentshow"));
                    }
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


                //需要获取投票人员的json 给投票人员赋值设置适配器
                usernameList = new ArrayList<>();
                UserListAdapter userListAdapter = new UserListAdapter(InitiateVoteActivity.this,usersList);
                lv_users.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                        CheckBox checkBox = view.findViewById(R.id.ck_useritem);
                        if (!checkBox.isChecked()) {
                            checkBox.setChecked(true);
                            TextView username = view.findViewById(R.id.tv_useritem_name);
                            usernameList.add(usersList.get(i).getUserName());
                        } else {
                            checkBox.setChecked(false);
                            usernameList.remove(usersList.get(i).getUserName());
                        }
                        Toast.makeText(getApplicationContext(), "" + usernameList.toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                lv_users.setAdapter(userListAdapter);

                //获取选项内容的Json
                ArrayAdapter<String> gradeAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_item, gradeArray);
                sq_info1.setAdapter(gradeAdapter);
                sq_info2.setAdapter(gradeAdapter);
                //设置默认选中
                sq_info1.setSelection(0);
                sq_info1.setPrompt("请选择要设置的选项");


            }
            if (msg.what == 2) {
                Log.d("222222handleMessage: ", "失败");
            }
        }
    };

    //提交表单
    private void request_postlist1(String strings,String strings1) {
        String strURL = Constant.WEB_SITE + "/FQTPServlet";//请求地址
        FormBody body = new FormBody.Builder()
                .add("ren",strings1 )
                .add("tie",strings)
                .build();
        Request request = new Request.Builder().url(strURL).post(body).build();
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