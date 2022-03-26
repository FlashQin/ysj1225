package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;

import java.util.ArrayList;
import java.util.List;

public class YoungPatternContentAdpater extends RecyclerView.Adapter<YoungPatternContentAdpater.YoungPatternViewHolder> {
    private List<String> mList;
    private Context mContext;
    public YoungPatternContentAdpater(Context mContext,List<String> mList){
        this.mContext = mContext;
        this.mList = mList;
    }
    @NonNull
    @Override
    public YoungPatternViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.youngpattern_content_itme,null,false);
        YoungPatternViewHolder holder = new YoungPatternViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull YoungPatternViewHolder holder, int position) {
        holder.YoungPattern_Content.setText(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class YoungPatternViewHolder  extends RecyclerView.ViewHolder{
        public TextView YoungPattern_Content;
        public YoungPatternViewHolder(@NonNull View itemView) {
            super(itemView);
            YoungPattern_Content = itemView.findViewById(R.id.YoungPattern_Content);
        }
    }
}
