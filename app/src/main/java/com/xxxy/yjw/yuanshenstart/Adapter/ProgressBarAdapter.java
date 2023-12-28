package com.xxxy.yjw.yuanshenstart.Adapter;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.ColorStateList;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.xxxy.yjw.yuanshenstart.R;

import java.util.List;
import java.util.Map;
//定义适配器
public class ProgressBarAdapter extends RecyclerView.Adapter<ProgressBarHolder> {
    private List<Map<String,String>> myList;
    private Context mContext;
    private LayoutInflater mInflater;


    public ProgressBarAdapter(List<Map<String, String>> myList, Context mContext) {
        this.myList = myList;
        this.mContext = mContext;
        mInflater = LayoutInflater.from(mContext);
    }

    @NonNull
    @Override
    public ProgressBarHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //这就是那个盘子
        View view = mInflater.inflate(R.layout.vote_item, parent, false);//物理托盘和逻辑托盘对应
        ProgressBarHolder holder = new ProgressBarHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProgressBarHolder holder, @SuppressLint("RecyclerView") int position) {
        //盘子里东西
        Map<String, String> map = myList.get(position);
        holder.itemTitle.setText(map.get("title").toString());

        holder.itemProgressBar.setSecondaryProgress(Integer.parseInt(map.get("progressBar")));
        holder.textViewProgressBar.setText(String.valueOf(holder.itemProgressBar.getSecondaryProgress()));


//        int progressValue = (int) map.get("progressBar");
//        holder.textViewProgress.setText(String.valueOf(progressValue));


        holder.itemProgressBar.setSecondaryProgressTintList(ColorStateList.valueOf(ContextCompat.getColor(mContext, R.color.zll)));



        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,myList.get(position).get("title").toString(),Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return myList.size();
    }
}
class ProgressBarHolder extends RecyclerView.ViewHolder {
    TextView itemTitle;
    ProgressBar itemProgressBar;
    TextView textViewProgressBar;
    public ProgressBarHolder(@NonNull View itemView) {
        super(itemView);
        itemTitle = itemView.findViewById(R.id.textView_2);
        itemProgressBar = itemView.findViewById(R.id.progressBar_2);
        textViewProgressBar = itemView.findViewById(R.id.textView_progress);

    }

}