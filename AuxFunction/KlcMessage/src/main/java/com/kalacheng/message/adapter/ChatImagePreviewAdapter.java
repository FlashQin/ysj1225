package com.kalacheng.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.PagerSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.commonview.jguangIm.ImMessageBean;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

import cn.jpush.im.android.api.content.CustomContent;

public class ChatImagePreviewAdapter extends RecyclerView.Adapter<ChatImagePreviewAdapter.Vh> {

    private List<ImMessageBean> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private ActionListener mActionListener;

    public ChatImagePreviewAdapter(Context context, List<ImMessageBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if (mActionListener != null) {
                mActionListener.onImageClick();
            }
            }
        };
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        return new Vh(mInflater.inflate(R.layout.item_im_chat_img, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }


    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        PagerSnapHelper pagerSnapHelper = new PagerSnapHelper();
        pagerSnapHelper.attachToRecyclerView(recyclerView);
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public Vh(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
            mImageView.setOnClickListener(mOnClickListener);
        }

        void setData(ImMessageBean bean) {
//            ImMessageUtil.getInstance().displayImageFile(bean, mImageView);
            CustomContent customContent = (CustomContent) bean.getRawMessage().getContent();
            String url = customContent.getStringValue("picUrlStr");
            ImageLoader.display(url,mImageView);
        }
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onImageClick();
    }
}
