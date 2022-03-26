package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.TimeaxisBinding;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.AppTrendsRecord;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class TimeAxisAdapter extends RecyclerView.Adapter<TimeAxisAdapter.ViewHolder> {

    private List<AppTrendsRecord> mList = new ArrayList<>();
    private OnBeanCallback<AppTrendsRecord> mCallBack;
    Context mContext;

    public TimeAxisAdapter() {

    }

    //加载更多
    public void loadData(List<AppTrendsRecord> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新
    public void RefreshData(List<AppTrendsRecord> data) {
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    public void getData(List<AppTrendsRecord> data) {
        this.mList = data;
    }

    @NonNull
    @Override
    public TimeAxisAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        TimeaxisBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.timeaxis, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.tvTime.setText(new DateUtil(mList.get(position).createTime).getDateToFormat("yyyy-MM-dd"));
        if (TextUtils.isEmpty(mList.get(position).source)) {
            holder.binding.ivAvatar.setVisibility(View.GONE);
        } else {
            holder.binding.ivAvatar.setVisibility(View.VISIBLE);
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) holder.binding.ivAvatar.getLayoutParams();
            switch (mList.get(position).type) {
                case 1://新生啦
                    holder.binding.ivAvatar.setOval(false);
                    layoutParams.width = DpUtil.dp2px(195);
                    layoutParams.height = DpUtil.dp2px(120);
                    break;

                case 2://发布第1条动态
                case 6://发布第1条短视频
                case 21://第1次发布私密动态
                    holder.binding.ivAvatar.setOval(false);
                    layoutParams.width = DpUtil.dp2px(120);
                    layoutParams.height = DpUtil.dp2px(140);
                    break;

                case 3://和TA第1次打招呼
                case 4://和TA聊天亲密值达到100
                case 5://第1次守护TA
                case 9://和TA第1次语音通话
                case 10://和TA第1次视频通话
                case 22://第1次被守护
                    holder.binding.ivAvatar.setOval(true);
                    layoutParams.width = DpUtil.dp2px(85);
                    layoutParams.height = DpUtil.dp2px(85);
                    break;

                case 7://发起第1次视频直播
                case 8://发起第1次语音直播
                case 20://第1次认证成主播
                case 23://第1次提现
                default:
                    holder.binding.ivAvatar.setOval(false);
                    layoutParams.width = DpUtil.dp2px(120);
                    layoutParams.height = DpUtil.dp2px(120);
                    break;
            }
            ImageLoader.display(mList.get(position).source, holder.binding.ivAvatar);
        }
        holder.binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (mList.get(position).type) {
                    case 3://和TA第1次打招呼
                    case 4://和TA聊天亲密值达到100
                    case 5://第1次守护TA
                    case 9://和TA第1次语音通话
                    case 10://和TA第1次视频通话
                    case 22://第1次被守护
                        if (mList.get(position).fkId != 0) {
                            ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).fkId).navigation();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        holder.binding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TimeaxisBinding binding;

        public ViewHolder(@NonNull TimeaxisBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.setCallback(mCallBack);
        }
    }


    public void setOnItemClickListener(OnBeanCallback<AppTrendsRecord> onItemClickListener) {
        mCallBack = onItemClickListener;
    }
}
