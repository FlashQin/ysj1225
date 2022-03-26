package com.kalacheng.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.libuser.model.AppOfficialNewsDTO;
import com.kalacheng.message.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OfficialNewsAdpater extends RecyclerView.Adapter<OfficialNewsAdpater.OfficialNewsViewHolder> {
    private Context mContext;
    private List<AppOfficialNewsDTO> mList = new ArrayList<>();

    public OfficialNewsAdpater(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<AppOfficialNewsDTO> data){
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public OfficialNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.official_news_item,null,false);
        OfficialNewsViewHolder holder = new OfficialNewsViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull OfficialNewsViewHolder holder, final int position) {
        holder.OfficialNews_Title.setText(mList.get(position).title);
        holder.OfficialNews_Content.setText(mList.get(position).introduction);
        ImageLoader.display(mList.get(position).logo,holder.OfficialNews_Image);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd hh:mm:ss");
        holder.OfficialNews_Time.setText(simpleDateFormat.format(mList.get(position).publishTime));

        holder.OfficialNews_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterPath.OfficialNewsDetailsActivity).withLong("id",mList.get(position).id).withString("content",mList.get(position).content).navigation(mContext);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class OfficialNewsViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView OfficialNews_Image;
        public TextView OfficialNews_Title;
        public TextView OfficialNews_Content;
        public TextView OfficialNews_Time;
        public RelativeLayout OfficialNews_Re;

        public OfficialNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            OfficialNews_Image = itemView.findViewById(R.id.OfficialNews_Image);
            OfficialNews_Title = itemView.findViewById(R.id.OfficialNews_Title);
            OfficialNews_Content = itemView.findViewById(R.id.OfficialNews_Content);
            OfficialNews_Time = itemView.findViewById(R.id.OfficialNews_Time);
            OfficialNews_Re = itemView.findViewById(R.id.OfficialNews_Re);
        }
    }
}
