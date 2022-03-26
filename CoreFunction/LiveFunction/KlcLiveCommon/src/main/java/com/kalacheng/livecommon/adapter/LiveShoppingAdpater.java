package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.busshop.model.ShopLiveGoods;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.http.HttpClient;

import java.util.ArrayList;
import java.util.List;

public class LiveShoppingAdpater extends RecyclerView.Adapter<LiveShoppingAdpater.LiveShoppingViewHolder> {
    private Context mContext;
    private List<ShopLiveGoods> mList = new ArrayList<>();
    private LiveShoppingCallBack callBack;
    private int Selected = -1;

    public LiveShoppingAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getData(List<ShopLiveGoods> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public void setSelect(int poistion) {
        this.Selected = poistion;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveShoppingViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.liveshopping_itme, null, false);
        LiveShoppingViewHolder holder = new LiveShoppingViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveShoppingViewHolder holder, final int position) {
        if (mList.get(position).goodsPicture.length() > 0) {
            String[] pic = mList.get(position).goodsPicture.split(",");
            ImageLoader.display(pic[0], holder.goods_image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        }
        if (!TextUtils.isEmpty(mList.get(position).name) && mList.get(position).name.length() > 4) {
            holder.goods_name.setText(mList.get(position).name.substring(0, 4) + ".");
        } else {
            holder.goods_name.setText(mList.get(position).name);
        }
        if (mList.get(position).channelId == 1) {
            if (mList.get(position).favorablePrice > 0) {
                holder.goods_price.setText("¥" + String.valueOf(mList.get(position).favorablePrice));
            } else {
                holder.goods_price.setText("¥" + String.valueOf(mList.get(position).goodsPrice));
            }
        } else {
            holder.goods_price.setText("¥" + String.valueOf(mList.get(position).goodsPrice));
        }


        if (HttpClient.getUid() != LiveConstants.ANCHORID) {
            holder.goods_image_state.setVisibility(View.GONE);
        } else {
            holder.goods_image_state.setVisibility(View.VISIBLE);
//            if (Selected == position) {
//                if (holder.goods_image_state.getText().toString().equals("讲解中")) {
//                    holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta);
//                    holder.goods_image_state.setText("讲TA");
//                } else {
//                    holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta2);
//                    holder.goods_image_state.setText("讲解中");
//                }
//
//            } else {
//                holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta);
//                holder.goods_image_state.setText("讲TA");
//            }

            if (mList.get(position).idExplain == 1) {
                if (holder.goods_image_state.getText().toString().equals("讲解中")) {
                    holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta);
                    holder.goods_image_state.setText("讲TA");
                } else {
                    holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta2);
                    holder.goods_image_state.setText("讲解中");
                }

            } else {
                holder.goods_image_state.setBackgroundResource(R.drawable.bg_live_speak_ta);
                holder.goods_image_state.setText("讲TA");
            }
        }


        holder.goods_image_state.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });

        holder.goods_re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onDetails(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    public class LiveShoppingViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView goods_image;
        public TextView goods_name;
        public TextView goods_price;
        public TextView goods_image_state;
        public RelativeLayout goods_re;

        public LiveShoppingViewHolder(@NonNull View itemView) {
            super(itemView);
            goods_image = itemView.findViewById(R.id.goods_image);
            goods_name = itemView.findViewById(R.id.goods_name);
            goods_price = itemView.findViewById(R.id.goods_price);
            goods_image_state = itemView.findViewById(R.id.goods_image_state);
            goods_re = itemView.findViewById(R.id.goods_re);
        }
    }

    public void setLiveShoppingCallBack(LiveShoppingCallBack back) {
        this.callBack = back;
    }

    public interface LiveShoppingCallBack {
        public void onClick(int position);

        public void onDetails(int position);
    }
}
