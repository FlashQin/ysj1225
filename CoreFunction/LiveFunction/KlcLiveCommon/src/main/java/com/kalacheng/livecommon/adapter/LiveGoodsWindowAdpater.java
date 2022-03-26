package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopGoodsDTO;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class LiveGoodsWindowAdpater extends RecyclerView.Adapter<LiveGoodsWindowAdpater.LiveGoodsWindowViewHolder> {
    private Context mContext;
    private LiveGoodsWindowCallBack callBack;
    private List<ShopGoodsDTO> mList = new ArrayList<>();

    public LiveGoodsWindowAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(List<ShopGoodsDTO> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveGoodsWindowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.livegoodswindow_itme, null, false);
        LiveGoodsWindowViewHolder holder = new LiveGoodsWindowViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveGoodsWindowViewHolder holder, final int position) {
        holder.LiveGoodsWindow_name.setText(mList.get(position).goodsName);
        if (mList.get(position).goodsPicture.length() > 0) {
            String[] pic = mList.get(position).goodsPicture.split(",");
            ImageLoader.display(pic[0], holder.LiveGoodsWindow_image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        }
        if (mList.get(position).channelId == 1) {
            if (mList.get(position).favorablePrice > 0) {
                holder.LiveGoodsWindow_Money.setText("¥ " + mList.get(position).favorablePrice);
            } else {
                holder.LiveGoodsWindow_Money.setText("¥ " + mList.get(position).price);
            }
        } else {
            holder.LiveGoodsWindow_Money.setText("¥ " + mList.get(position).price);
        }
        holder.LiveGoodsWindow_sort.setText(String.valueOf(mList.get(position).sort));
        //是否选取 0 没有选取 1 选取
        if (mList.get(position).checked == 1) {
            holder.LiveGoodsWindow_select.setBackgroundResource(R.mipmap.livegoods_select);
        } else {
            holder.LiveGoodsWindow_select.setBackgroundResource(R.mipmap.livegoods_unselect);
        }
        holder.LiveGoodsWindow_select_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onChecked(position);
            }
        });

        holder.LiveGoodsWindow_sort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onEditNumber(position);

            }
        });
        holder.LiveGoodsWindow_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callBack.onGoGoodsInfo(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LiveGoodsWindowViewHolder extends RecyclerView.ViewHolder {
        public ImageView LiveGoodsWindow_image;
        public TextView LiveGoodsWindow_name;
        public TextView LiveGoodsWindow_sort;
        public TextView LiveGoodsWindow_Money;
        public ImageView LiveGoodsWindow_select;
        public RelativeLayout LiveGoodsWindow_select_Re;
        public RelativeLayout LiveGoodsWindow_rl;

        public LiveGoodsWindowViewHolder(@NonNull View itemView) {
            super(itemView);
            LiveGoodsWindow_image = itemView.findViewById(R.id.LiveGoodsWindow_image);
            LiveGoodsWindow_name = itemView.findViewById(R.id.LiveGoodsWindow_name);
            LiveGoodsWindow_sort = itemView.findViewById(R.id.LiveGoodsWindow_sort);
            LiveGoodsWindow_Money = itemView.findViewById(R.id.LiveGoodsWindow_Money);
            LiveGoodsWindow_select = itemView.findViewById(R.id.LiveGoodsWindow_select);
            LiveGoodsWindow_select_Re = itemView.findViewById(R.id.LiveGoodsWindow_select_Re);
            LiveGoodsWindow_rl = itemView.findViewById(R.id.LiveGoodsWindow_rl);
        }
    }

    public void setLiveGoodsWindowCallBack(LiveGoodsWindowCallBack back) {
        this.callBack = back;
    }

    public interface LiveGoodsWindowCallBack {
        public void onEditNumber(int poistion);

        public void onChecked(int poistion);

        public void onGoGoodsInfo(int position);
    }
}
