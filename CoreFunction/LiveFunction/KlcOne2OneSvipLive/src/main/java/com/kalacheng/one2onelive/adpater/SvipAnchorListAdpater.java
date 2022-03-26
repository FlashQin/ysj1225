package com.kalacheng.one2onelive.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.libuser.model.EnableInvtHost;
import com.kalacheng.one2onelive.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class SvipAnchorListAdpater extends RecyclerView.Adapter<SvipAnchorListAdpater.SvipAnchorListViewHolder> {
    private Context mContext;
    private List<EnableInvtHost> mList = new ArrayList<>();
    private SvipAnchorListCallBack callBack;
    public SvipAnchorListAdpater(Context mContext){
        this.mContext=mContext;
    }

    public void setData(List<EnableInvtHost> data){
        this.mList.clear();
        this.mList = data;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SvipAnchorListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.svip_anchor_list_itme,null,false);
        SvipAnchorListViewHolder holder = new SvipAnchorListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SvipAnchorListViewHolder holder, final int position) {
        holder.SvipAnchor_name.setText(mList.get(position).username);
        ImageLoader.display(mList.get(position).avatar,holder.SvipAnchor_headIamge,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
        holder.SvipAnchor_price.setText(String.valueOf(mList.get(position).videoCoin)+ SpUtil.getInstance().getSharedPreference("vcUnit","")+"/分钟");

        holder.SvipAnchor_invitation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });

        holder.SvipAnchor_headIamge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onlookinfo(position);
            }
        });
    }



    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class SvipAnchorListViewHolder extends RecyclerView.ViewHolder{
        public TextView SvipAnchor_invitation;
        public RoundedImageView SvipAnchor_headIamge;
        public TextView SvipAnchor_name;
        public TextView SvipAnchor_price;
        public SvipAnchorListViewHolder(@NonNull View itemView) {
            super(itemView);
            SvipAnchor_invitation = itemView.findViewById(R.id.SvipAnchor_invitation);
            SvipAnchor_headIamge = itemView.findViewById(R.id.SvipAnchor_headIamge);
            SvipAnchor_name = itemView.findViewById(R.id.SvipAnchor_name);
            SvipAnchor_price = itemView.findViewById(R.id.SvipAnchor_price);
        }
    }

    public void setSvipAnchorListCallBack(SvipAnchorListCallBack back){
        this.callBack =back;
    }
    public interface SvipAnchorListCallBack{
        public void onClick(int poistion);
        public void onlookinfo(int poistion);
    }
}
