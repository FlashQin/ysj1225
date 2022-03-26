package com.kalacheng.dynamiccircle.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.view.TrendImageItemLayout;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

import cc.shinichi.library.ImagePreview;

public class MyDynamicListImageAdpater extends RecyclerView.Adapter {
    private Context mContext;
    private List<String> mList ;

    public MyDynamicListImageAdpater(Context mContext, List<String> mList){
        this.mContext=mContext;
        this.mList=mList;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_main_trend_image,null,false);
        MyDynamicListImageViewHolder holder = new MyDynamicListImageViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyDynamicListImageViewHolder)holder).setData(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }
    public class MyDynamicListImageViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImage;
        public TrendImageItemLayout layoutImage;

        public MyDynamicListImageViewHolder(@NonNull View itemView) {
            super(itemView);
            ivImage =itemView.findViewById(R.id.ivImage);
            layoutImage =itemView.findViewById(R.id.layoutImage);
        }

        public void setData(String url, final int postion){
            ImageLoader.display(url, ivImage);

            layoutImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ImagePreview.getInstance().setContext(mContext).setIndex(postion).setImageList(mList).setShowDownButton(false).start();

                }
            });
        }
    }
}
