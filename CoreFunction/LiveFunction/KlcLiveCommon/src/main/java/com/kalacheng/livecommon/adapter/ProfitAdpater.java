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

import com.kalacheng.libuser.model.ApiUsersVoterecord;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.commonview.utils.SexUtlis;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class ProfitAdpater extends RecyclerView.Adapter<ProfitAdpater.ProfitViewHolder> {
    private Context mContext;
    private List<ApiUsersVoterecord> mList = new ArrayList<>();
    public ProfitAdpater(Context mContext){
        this.mContext = mContext;
    }
    public void setData(List<ApiUsersVoterecord> data){
        this.mList.clear();
        this.mList.addAll(data);
        notifyDataSetChanged();
    }
    public void loadMoreList(List<ApiUsersVoterecord> list) {
        this.mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<ApiUsersVoterecord> getList() {
        return this.mList;
    }

    @NonNull
    @Override
    public ProfitViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.profit_itme,null,false);
        ProfitViewHolder holder = new ProfitViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProfitViewHolder holder, final int position) {
        holder.ProfitNum_number.setText(String.valueOf(mList.get(position).number));
        holder.ProfitNum_name.setText(mList.get(position).username);
        holder.ProfitNum_money.setText(DecimalFormatUtils.toTwo(mList.get(position).totalvotes));
        ImageLoader.display(mList.get(position).avatar,holder.ProfitNum_headimage,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        ImageLoader.display(mList.get(position).anchorGradeImg,holder.ProfitNum_grade,R.mipmap.ic_launcher,R.mipmap.ic_launcher);

        SexUtlis.getInstance().setSex(mContext,holder.ProfitNum_gender,mList.get(position).sex,0);

        holder.ProfitNum_headimage.setOnClickListener(new View.OnClickListener() {
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

    public class ProfitViewHolder extends RecyclerView.ViewHolder{
        public TextView ProfitNum_number;
        public RoundedImageView ProfitNum_headimage;
        public TextView ProfitNum_name;
        public LinearLayout ProfitNum_gender;
        public ImageView ProfitNum_grade;
        public TextView ProfitNum_money;
        public ProfitViewHolder(@NonNull View itemView) {
            super(itemView);
            ProfitNum_number = itemView.findViewById(R.id.ProfitNum_number);
            ProfitNum_headimage = itemView.findViewById(R.id.ProfitNum_headimage);
            ProfitNum_name = itemView.findViewById(R.id.ProfitNum_name);
            ProfitNum_gender = itemView.findViewById(R.id.ProfitNum_gender);
            ProfitNum_grade = itemView.findViewById(R.id.ProfitNum_grade);
            ProfitNum_money = itemView.findViewById(R.id.ProfitNum_money);
        }
    }

    private ProfitCallBack callBack;
    public void setProfitCallBack(ProfitCallBack back){
        this.callBack = back;
    }
    public interface ProfitCallBack{
        public void onClick(int poistion);
    }
}
