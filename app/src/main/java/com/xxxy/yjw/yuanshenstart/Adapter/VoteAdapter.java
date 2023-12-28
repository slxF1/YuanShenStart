package com.xxxy.yjw.yuanshenstart.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.xxxy.yjw.yuanshenstart.MakeTheLayoutActivity;
import com.xxxy.yjw.yuanshenstart.R;
import com.xxxy.yjw.yuanshenstart.VoteResultsActivity;

import java.util.List;
import java.util.Map;

public class VoteAdapter extends RecyclerView.Adapter<ViewHolder> {
    private List<Map<String,Object>> myList;
    private Context mContext;
    private LayoutInflater mInflater;

    public VoteAdapter(List<Map<String,Object>> mylist, Context mContext) {
        this.myList = mylist;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这就是那个盘子
        View view = mInflater.inflate(R.layout.showitem, parent, false);//物理托盘和逻辑托盘对应
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        //盘子里东西
        Map<String, Object> map = myList.get(position);
        holder.itemID.setText(map.get("itemid").toString());
        holder.itemTitle.setText(map.get("itemtitle").toString());
        holder.itemInfo.setText(map.get("iteminfo").toString());
        holder.itemPeople.setText(map.get("itempeople").toString());
        //拿到item中的投票表的标题
        String title = holder.itemInfo.getText().toString();

        //开始投票
        holder.show_start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MakeTheLayoutActivity.class);
                intent.putExtra("title",title);
                mContext.startActivity(intent);
            }
        });
        //投票结果
        holder.show_result.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, VoteResultsActivity.class);
                intent.putExtra("title",title);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}

class ViewHolder extends RecyclerView.ViewHolder{
    TextView itemID,itemTitle,itemInfo,itemPeople;
    Button show_start,show_result;

    public ViewHolder(@NonNull View itemView) {
        super(itemView);
        itemID = itemView.findViewById(R.id.show_tv_id);
        itemTitle = itemView.findViewById(R.id.show_tv_title);
        itemInfo = itemView.findViewById(R.id.show_tv_info);
        itemPeople = itemView.findViewById(R.id.show_tv_person);
        show_start = itemView.findViewById(R.id.show_start);
        show_result = itemView.findViewById(R.id.show_result);
    }
}
