package com.kalacheng.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.MainfollowItmeBinding;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

public class MainFollowAdpater extends RecyclerView.Adapter {
    private Context mContext;
    private List<LiveBean> mList = new ArrayList<>();
    long mLastClickTime;

    public MainFollowAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void loadData(List<LiveBean> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    public void RefreshData(List<LiveBean> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        MainfollowItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.mainfollow_itme, null, false);
        MainFollowViewHolder holder = new MainFollowViewHolder(binding);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        int with = DpUtil.getScreenWidth() / 2 - 25;
        RelativeLayout.LayoutParams linearParams = (RelativeLayout.LayoutParams) ((MainFollowViewHolder) holder).binding.cover.getLayoutParams();
        linearParams.width = with;
        linearParams.height = with;
        ((MainFollowViewHolder) holder).binding.cover.setLayoutParams(linearParams);
        ((MainFollowViewHolder) holder).setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class MainFollowViewHolder extends RecyclerView.ViewHolder {


        MainfollowItmeBinding binding;

        public MainFollowViewHolder(MainfollowItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final LiveBean bean) {

            if (bean.type == 0) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_0);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype1));
            } else if (bean.type == 1) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_1);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype2));
            } else if (bean.type == 2) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_2);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype3));
            } else if (bean.type == 3) {
                binding.roomtype.setBackgroundResource(R.mipmap.icon_main_live_type_3);
                binding.roomtype.setText(mContext.getResources().getString(R.string.roomtype4));
            }
            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.ReleFollow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (bean.islive == 1) {
//                        LookRoomUtlis.getInstance().getData(bean, mContext);
                    } else {
                        ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.uid).navigation();
                    }

                }
            });
        }

    }
}
