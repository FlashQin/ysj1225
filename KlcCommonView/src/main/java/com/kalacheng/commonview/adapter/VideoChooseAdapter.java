package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.commonview.R;
import com.kalacheng.util.bean.VideoChooseBean;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.List;

/**
 * Created by cxf on 2018/6/20.
 */

public class VideoChooseAdapter extends RecyclerView.Adapter<VideoChooseAdapter.Vh> {

    private List<VideoChooseBean> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private OnItemClickListener<VideoChooseBean> mOnItemClickListener;

    public VideoChooseAdapter(Context context, List<VideoChooseBean> list) {
        mList = list;
        mInflater = LayoutInflater.from(context);
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && mOnItemClickListener != null) {
                    mOnItemClickListener.onItemClick(0, (VideoChooseBean) tag);
                }
            }
        };
    }

    public void setOnItemClickListener(OnItemClickListener<VideoChooseBean> listener) {
        mOnItemClickListener = listener;
    }


    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_video_choose_local, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mCover;
        TextView mDuration;

        public Vh(View itemView) {
            super(itemView);
            mCover = (ImageView) itemView.findViewById(R.id.cover);
            mDuration = (TextView) itemView.findViewById(R.id.duration);
            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(VideoChooseBean bean) {
            itemView.setTag(bean);
            ImageLoader.displayVideoThumb(bean.getVideoPath(), mCover);
            mDuration.setText(bean.getDurationString());
        }
    }

}
