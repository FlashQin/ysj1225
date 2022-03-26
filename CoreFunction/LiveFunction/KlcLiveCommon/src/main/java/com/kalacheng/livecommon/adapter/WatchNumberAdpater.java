package com.kalacheng.livecommon.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.buscommon.model.ApiUserBasicInfo;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.commonview.utils.SexUtlis;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class WatchNumberAdpater extends RecyclerView.Adapter<WatchNumberAdpater.WatchNumberViewHolder> {
    private List<ApiUserBasicInfo> mList;
    private Context mContext;

    public WatchNumberAdpater(Context mContext, List<ApiUserBasicInfo> data) {
        this.mContext = mContext;
        this.mList = data;
    }

    @NonNull
    @Override
    public WatchNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.watchnumber_item, null, false);
        WatchNumberViewHolder holder = new WatchNumberViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull WatchNumberViewHolder holder, final int position) {
        holder.contribution_number.setText(String.valueOf(position + 1));
        holder.contribution_name.setText(mList.get(position).username);
        holder.contribution_money.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).currContValue));
        ImageLoader.display(mList.get(position).avatar, holder.contribution_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        ImageLoader.display(mList.get(position).,holder.contribution_grade);
        SexUtlis.getInstance().setSex(mContext, holder.contribution_gender, mList.get(position).sex, 0);

        holder.contribution_headimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class WatchNumberViewHolder extends RecyclerView.ViewHolder {
        public TextView contribution_number;
        public RoundedImageView contribution_headimage;
        public TextView contribution_name;
        public LinearLayout contribution_gender;
        public ImageView contribution_grade;
        public TextView contribution_money;

        public WatchNumberViewHolder(@NonNull View itemView) {
            super(itemView);
            contribution_number = itemView.findViewById(R.id.contribution_number);
            contribution_headimage = itemView.findViewById(R.id.contribution_headimage);
            contribution_name = itemView.findViewById(R.id.contribution_name);
            contribution_gender = itemView.findViewById(R.id.contribution_gender);
            contribution_grade = itemView.findViewById(R.id.contribution_grade);
            contribution_money = itemView.findViewById(R.id.contribution_money);
        }
    }

    private WatchNumberCallBack callBack;

    public void setWatchNumberCallBack(WatchNumberCallBack back) {
        this.callBack = back;
    }

    public interface WatchNumberCallBack {
        public void onClick(int position);
    }
}
