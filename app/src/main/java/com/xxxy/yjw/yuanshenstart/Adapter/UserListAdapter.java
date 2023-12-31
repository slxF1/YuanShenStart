package com.xxxy.yjw.yuanshenstart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;


import com.xxxy.yjw.yuanshenstart.Holder.UserHoder;
import com.xxxy.yjw.yuanshenstart.R;
import com.xxxy.yjw.yuanshenstart.model.Users;

import java.util.List;

public class UserListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Users> userList;

    public UserListAdapter(Context context,List<Users> userList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.userList = userList;
    }

    @Override
    public int getCount() {
        return userList.size();
    }

    @Override
    public Object getItem(int i) {

        return userList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        UserHoder userHoder = null;
        if (view==null){
            view = layoutInflater.inflate(R.layout.userlistitem,null,false);
            userHoder = new UserHoder();

            userHoder.tv_name = view.findViewById(R.id.tv_useritem_name);
            userHoder.ck_useritem = view.findViewById(R.id.ck_useritem);


            view.setTag(userHoder);
        }else {
            userHoder = (UserHoder) view.getTag();
        }

//        userHoder.ck_useritem.setChecked(false);
        userHoder.tv_name.setText(userList.get(i).getUserName());
        return view;
    }
}


