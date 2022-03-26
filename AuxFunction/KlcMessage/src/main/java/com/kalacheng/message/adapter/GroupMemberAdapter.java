package com.kalacheng.message.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.message.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.SendGiftPeopleBean;

import java.util.ArrayList;
import java.util.List;

public class GroupMemberAdapter extends RecyclerView.Adapter<GroupMemberAdapter.Holder> {
    ArrayList<SendGiftPeopleBean> list = new ArrayList<>();
    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_group_member,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() >0 ? payloads.get(0):null;
        final SendGiftPeopleBean bean = list.get(position);
        if (payload == null) {
            holder.nameTv.setText(bean.name);
            ImageLoader.display(bean.headimage,holder.avatarIv,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID,bean.uid).navigation();
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void setList( ArrayList<SendGiftPeopleBean> list){
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    class Holder extends  RecyclerView.ViewHolder{
        RoundedImageView avatarIv;
        TextView nameTv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            avatarIv = itemView.findViewById(R.id.avatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
