package com.kalacheng.shopping.seller.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class UpImgAdapter1 extends RecyclerView.Adapter<UpImgAdapter1.Holder> {

    int maxSize = 8;

    Context mContext;
    List<String> imgs = new ArrayList<>();

    addImgClickListener listener;

    public UpImgAdapter1() {
        imgs.add("add");
    }

    public UpImgAdapter1(String type, int maxSize) {
        imgs.add(type);
        this.maxSize = maxSize;
    }

    public void setListener(addImgClickListener listener) {
        this.listener = listener;
    }

    public List<String> getImgs() {
        List<String> list = new ArrayList<>();
        for (String str : imgs) {
            if (!str.startsWith("add")) {
                list.add(str);
            }
        }
        return list;
    }

    public void addImg(String imgUrl) {
        int index = imgs.size() > 1 ? imgs.size() - 1 : 0;
        imgs.add(index, imgUrl);
        if (index == maxSize-1) {
            notifyDataSetChanged();
        } else {
            notifyItemInserted(index);
        }
    }
    public void addImgs( List<String> list) {
       imgs.addAll(0,list);
       notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        return new Holder(LayoutInflater.from(mContext).inflate(R.layout.item_up_img_1, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final String img = imgs.get(position);
        if (img.startsWith("add")) {
            holder.imageView.setImageResource(R.mipmap.icon_tianjia);
            holder.delImgTv.setVisibility(View.GONE);
        } else {
            ImageLoader.display(img, holder.imageView);
            holder.delImgTv.setVisibility(View.VISIBLE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (img.startsWith("add")) {
                    if (listener != null) {
                        listener.addImgClick(UpImgAdapter1.this);
                    }
                } else {
                    ImagePreview.getInstance().setContext(mContext).setIndex(holder.getBindingAdapterPosition()).setImageList(getImgs()).setShowDownButton(false).start();
                }

            }
        });
        holder.delImgTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int p = holder.getBindingAdapterPosition();
                imgs.remove(p);
                notifyItemRemoved(p);
            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(imgs.size(), maxSize);
    }

    static class Holder extends RecyclerView.ViewHolder {
        TextView delImgTv;
        RoundedImageView imageView;

        public Holder(@NonNull View itemView) {
            super(itemView);
            delImgTv = (TextView) itemView.findViewById(R.id.delImgTv);
            imageView = itemView.findViewById(R.id.shopPhotoIv);
        }
    }

    public interface addImgClickListener {
        void addImgClick(UpImgAdapter1 adapter);
    }
}
