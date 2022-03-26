package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;

import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiWishUser;
import com.kalacheng.live.R;
import com.kalacheng.live.databinding.LiveWishBillUserListBinding;

import java.util.List;

public class LiveWishBillUserListAdpater extends RecyclerView.Adapter<LiveWishBillUserListAdpater.LiveWishBillUserViewHolder> {

    private Context mContext;
    private LiveWishBillUserItmeOnClick billUserItmeOnClick;
    private List<ApiWishUser> mList ;
    public LiveWishBillUserListAdpater(Context mContext,List<ApiWishUser> data){
        this.mContext=mContext;
        this.mList=data;
    }

    @NonNull
    @Override
    public LiveWishBillUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LiveWishBillUserListBinding bind = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.live_wish_bill_user_list,null,false);
        LiveWishBillUserViewHolder holder = new LiveWishBillUserViewHolder(bind);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveWishBillUserViewHolder holder, int position) {
        holder.getData(mList.get(position),position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LiveWishBillUserViewHolder extends RecyclerView.ViewHolder {
        LiveWishBillUserListBinding bind ;

        public LiveWishBillUserViewHolder(LiveWishBillUserListBinding bind) {
            super(bind.getRoot());
            this.bind =bind;
        }

        public void getData(ApiWishUser bean, final int position){

            bind.LiveWishBillUserListUserName.setText((position+1)+"");
            bind.LiveWishBillUserListUserImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).uid).navigation();

                }
            });
            bind.setViewModel(bean);
            bind.executePendingBindings();
        }
    }
    public void setLiveWishBillUserItmeOnClick(LiveWishBillUserItmeOnClick click){
        this.billUserItmeOnClick =click;
    }

    public interface LiveWishBillUserItmeOnClick{
        public void onClick(int position);
    }
}
