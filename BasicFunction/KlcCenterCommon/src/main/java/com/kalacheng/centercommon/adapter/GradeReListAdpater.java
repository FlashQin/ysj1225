package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.centercommon.R;
import com.kalacheng.libuser.model.ApiGradeReList;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.List;

public class GradeReListAdpater extends RecyclerView.Adapter<GradeReListAdpater.GradeReListViewHolder> {
    private Context mContext;
    private List<ApiGradeReList> mList = new ArrayList<>();
    public GradeReListAdpater(Context mContext){
        this.mContext =mContext;
    }

    public void getData(List<ApiGradeReList> data){
        this.mList =data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GradeReListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.grade_relist_itme,null,false);
        GradeReListViewHolder holder = new GradeReListViewHolder(view);
        return holder;
    }



    @Override
    public void onBindViewHolder(@NonNull GradeReListViewHolder holder, int position) {
        if (mList.get(position).type ==1){
            ImageLoader.display(R.mipmap.icon_money_big,holder.Grade_ReList_Image);
            holder.Grade_ReList_Name.setText(SpUtil.getInstance().getCoinUnit());

        }else {
            ImageLoader.display(mList.get(position).img,holder.Grade_ReList_Image,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            holder.Grade_ReList_Name.setText(mList.get(position).title);

        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class GradeReListViewHolder extends RecyclerView.ViewHolder{
        public ImageView Grade_ReList_Image;
        private TextView Grade_ReList_Name;
        public GradeReListViewHolder(@NonNull View itemView) {
            super(itemView);
            Grade_ReList_Image = itemView.findViewById(R.id.Grade_ReList_Image);
            Grade_ReList_Name = itemView.findViewById(R.id.Grade_ReList_Name);

        }
    }
}
