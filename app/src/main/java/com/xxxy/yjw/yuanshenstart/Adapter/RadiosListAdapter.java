package com.xxxy.yjw.yuanshenstart.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.xxxy.yjw.yuanshenstart.R;
import com.xxxy.yjw.yuanshenstart.model.Polls;

import java.util.HashMap;
import java.util.List;

public class RadiosListAdapter extends BaseAdapter {
    private LayoutInflater layoutInflater;
    private List<Polls> radioList;
    private HashMap<String, Boolean> states = new HashMap<String, Boolean>();

    public static String radioStr;

    private Context context;
    private RadioButton radioButton;
    private ImageView iv_radio;


    public RadiosListAdapter(Context context, List<Polls> radioList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.radioList = radioList;
    }


    @Override
    public int getCount() {
        return radioList.size();
    }

    @Override
    public Object getItem(int i) {
        return radioList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View view1;
        if (view == null) {
            view = layoutInflater.inflate(R.layout.radiositem, null, false);
        } else {

            view1 = view;//复用历史缓存对象
        }
        //单选按钮文字
        TextView radioText = (TextView) view.findViewById(R.id.tv_item_radios);
        //单选按钮
        radioButton = (RadioButton) view.findViewById(R.id.rb_itemButton);
        iv_radio = (ImageView)view.findViewById(R.id.iv_radio);
        radioText.setText(radioList.get(i).getContent_id().getContentshow());
        Glide.with(iv_radio.getContext()).load(radioList.get(i).getContent_id().getContentimg()).into(iv_radio);

        radioButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 重置，确保最多只有一项被选中
                for (String key : states.keySet()) {
                    states.put(key, false);
                }
                states.put(String.valueOf(i), true);
                RadiosListAdapter.this.notifyDataSetChanged();
            }
        });


        boolean res = false;
        if (states.get(String.valueOf(i)) == null || states.get(String.valueOf(i)) == false) {
            res = false;
            states.put(String.valueOf(i), false);
        } else {
            res = true;
        }

        //这里给 radioButton设置true和false
        radioButton.setChecked(res);
        if (radioButton.isChecked()) {
            TextView mtextView = view.findViewById(R.id.tv_item_radios);
            radioStr = mtextView.getText().toString();
            Toast.makeText(view.getContext(), "" + radioStr, Toast.LENGTH_SHORT).show();
        }


        return view;
    }

}
