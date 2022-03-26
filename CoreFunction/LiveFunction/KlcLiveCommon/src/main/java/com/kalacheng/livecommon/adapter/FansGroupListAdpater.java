package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppUserAvatar;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class FansGroupListAdpater extends RecyclerView.Adapter<FansGroupListAdpater.FansGroupListViewHolder> {
    private Context mContext;
    private List<AppUserAvatar> mList = new ArrayList<>();
    public FansGroupListAdpater (Context mContext){
        this.mContext = mContext;
    }

    public void getFansGroupList(List<AppUserAvatar> data){
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public FansGroupListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.fans_group_list_itme,null,false);
        FansGroupListViewHolder holder = new FansGroupListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull FansGroupListViewHolder holder, int position) {
        ImageLoader.display(mList.get(position).avatar,holder.fansgroup_userimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class FansGroupListViewHolder extends RecyclerView.ViewHolder{
        public RoundedImageView fansgroup_userimage;
        public FansGroupListViewHolder(@NonNull View itemView) {
            super(itemView);
            fansgroup_userimage = itemView.findViewById(R.id.fansgroup_userimage);
        }
    }
}
