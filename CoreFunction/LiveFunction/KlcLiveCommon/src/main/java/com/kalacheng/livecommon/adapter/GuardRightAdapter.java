package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiGuard;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

public class GuardRightAdapter extends RecyclerView.Adapter<GuardRightAdapter.Vh> {
    private ApiGuard bean;
    private LayoutInflater mInflater;
    private int mColor1;
    private int mColor2;
    private int mColor3;

    public GuardRightAdapter(Context context, ApiGuard apiGuardEntity) {
        bean = apiGuardEntity;
        mInflater = LayoutInflater.from(context);
        mColor1 = ContextCompat.getColor(context, R.color.textColor6);
        mColor2 = ContextCompat.getColor(context, R.color.textColor9);
        mColor3 = ContextCompat.getColor(context, R.color.textColorC);
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.guard_right, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
        holder.setData(bean, position);
    }

    @Override
    public int getItemCount() {
        return bean.guardEffectList.size();
    }

    public class Vh extends RecyclerView.ViewHolder {
        ImageView mIcon;
        TextView mTitle;
        TextView mDes;

        public Vh(@NonNull View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.icon);
            mTitle = itemView.findViewById(R.id.title);
            mDes = itemView.findViewById(R.id.des);
        }

        void setData(ApiGuard bean, int i) {
            ImageLoader.display(bean.guardEffectList.get(i).img, mIcon);
            mTitle.setText(bean.guardEffectList.get(i).title);
            mDes.setText(bean.guardEffectList.get(i).des);
        }
    }
}
