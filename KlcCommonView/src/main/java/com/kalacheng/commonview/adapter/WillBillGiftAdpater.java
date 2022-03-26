package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class WillBillGiftAdpater extends RecyclerView.Adapter<WillBillGiftAdpater.WillBillGiftViweHolder> {

    private Context mContext;
    private List<ApiUsersLiveWish> list = new ArrayList<>();
    private WillBillGiftItmeClickBack clickBack;

    public WillBillGiftAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setGiftList(List<ApiUsersLiveWish> data) {
        this.list.clear();
        this.list = data;
        notifyDataSetChanged();
    }

    public List<ApiUsersLiveWish> getGiftList() {
        return list;
    }

    @NonNull
    @Override
    public WillBillGiftViweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.will_bill_gift_itme, null, false);
        WillBillGiftViweHolder holder = new WillBillGiftViweHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final WillBillGiftViweHolder holder, final int position) {
        ImageLoader.display(list.get(position).gifticon, holder.gift_icon);

        holder.gift_gold.setText(DecimalFormatUtils.isIntegerDouble(list.get(position).needcoin) + SpUtil.getInstance().getSharedPreference("vcUnit", ""));
        holder.gift_num.setText(String.valueOf(list.get(position).num));

        if (list.get(position).isCheck == 1) {
            holder.gift_check.setBackgroundResource(R.mipmap.selection);
        } else {
            holder.gift_check.setBackgroundResource(R.mipmap.unchecked);
        }

        //加
        holder.gift_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).num = Integer.parseInt(holder.gift_num.getText().toString()) + 1;
                list.get(position).isCheck = 1;
                clickBack.onAdd(list.get(position));
                notifyDataSetChanged();
            }
        });

        //减
        holder.gift_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Integer.parseInt(holder.gift_num.getText().toString()) > 0) {
                    list.get(position).num = Integer.parseInt(holder.gift_num.getText().toString()) - 1;
                    if (list.get(position).num == 0) {
                        list.get(position).isCheck = 0;
                    }
                    clickBack.onReduce(list.get(position));
                    notifyDataSetChanged();
                }

            }
        });

        //选中
        holder.gift_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                list.get(position).num = Integer.parseInt(holder.gift_num.getText().toString());

                if (list.get(position).isCheck == 1) {
                    list.get(position).isCheck = 0;
                    clickBack.onCheck(list.get(position));
                } else {
                    list.get(position).isCheck = 1;
                    if (list.get(position).num == 0) {
                        list.get(position).num = 1;
                    }
                    clickBack.onCheck(list.get(position));
                }

                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class WillBillGiftViweHolder extends RecyclerView.ViewHolder {
        public ImageView gift_icon;
        public TextView gift_num;
        public RelativeLayout gift_reduce;
        public RelativeLayout gift_add;
        public ImageView gift_check;
        public TextView gift_gold;

        public WillBillGiftViweHolder(@NonNull View itemView) {
            super(itemView);
            gift_icon = itemView.findViewById(R.id.gift_icon);
            gift_num = itemView.findViewById(R.id.gift_num);
            gift_reduce = itemView.findViewById(R.id.gift_reduce);
            gift_add = itemView.findViewById(R.id.gift_add);
            gift_check = itemView.findViewById(R.id.gift_check);
            gift_gold = itemView.findViewById(R.id.gift_gold);
        }
    }

    //操作回调
    public interface WillBillGiftItmeClickBack {
        public void onAdd(ApiUsersLiveWish gift);

        public void onReduce(ApiUsersLiveWish gift);

        public void onCheck(ApiUsersLiveWish gift);
    }

    public void setWillBillGiftItmeClickBack(WillBillGiftItmeClickBack back) {
        this.clickBack = back;
    }
}
