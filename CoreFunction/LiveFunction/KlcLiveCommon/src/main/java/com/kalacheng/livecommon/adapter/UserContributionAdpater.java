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

import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.commonview.utils.SexUtlis;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class UserContributionAdpater extends RecyclerView.Adapter<UserContributionAdpater.UserContributionViewHolder> {
    private Context mContext;
    private List<RanksDto> mList = new ArrayList<>();
    public UserContributionAdpater(Context mContext){
        this.mContext = mContext;
    }

    public void setData(List<RanksDto> data){
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public UserContributionViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_contribution_itme,null,false);
        UserContributionViewHolder holder = new UserContributionViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull UserContributionViewHolder holder, final int position) {

        holder.contribution_number.setText(String.valueOf(mList.get(position).sort));
        holder.contribution_name.setText(mList.get(position).username);
        holder.contribution_money.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).delta));
        ImageLoader.display(mList.get(position).avatar,holder.contribution_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        ImageLoader.display(mList.get(position).icon,holder.contribution_grade);

        SexUtlis.getInstance().setSex(mContext,holder.contribution_gender,mList.get(position).sex,0);
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

    public class UserContributionViewHolder extends RecyclerView.ViewHolder {
        public TextView contribution_number;
        public RoundedImageView contribution_headimage;
        public TextView contribution_name;
        public LinearLayout contribution_gender;
        public ImageView contribution_grade;
        public TextView contribution_money;
        public UserContributionViewHolder(@NonNull View itemView) {
            super(itemView);
            contribution_number = itemView.findViewById(R.id.contribution_number);
            contribution_headimage = itemView.findViewById(R.id.contribution_headimage);
            contribution_name = itemView.findViewById(R.id.contribution_name);
            contribution_gender = itemView.findViewById(R.id.contribution_gender);
            contribution_grade = itemView.findViewById(R.id.contribution_grade);
            contribution_money = itemView.findViewById(R.id.contribution_money);
        }
    }

    private UserContributionCallBack callBack;
    public void setUserContributionCallBack(UserContributionCallBack back){
        this.callBack =back;
    }
    public interface UserContributionCallBack{
        public void onClick(int poistion);
    }
}
