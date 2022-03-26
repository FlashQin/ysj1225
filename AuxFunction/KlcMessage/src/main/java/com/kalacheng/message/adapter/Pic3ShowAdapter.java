package com.kalacheng.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.Arrays;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class Pic3ShowAdapter extends RecyclerView.Adapter<Pic3ShowAdapter.Holder> {

    String[] strs;
    Context context;

    public void setStrs(String[] strs) {
        this.strs = strs;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new Holder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pic3_show, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            ImageLoader.display(strs[position], holder.ivPic);
            holder.ivPic.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ImagePreview.getInstance().setContext(context).setIndex(position).setImageList(Arrays.asList(strs)).setShowDownButton(false).start();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (strs == null) return 0;
        return strs.length > 3 ? 3 : strs.length;
    }

    static class Holder extends RecyclerView.ViewHolder {
        ImageView ivPic;

        public Holder(@NonNull View itemView) {
            super(itemView);
            ivPic = itemView.findViewById(R.id.ivPic);
        }
    }


}
