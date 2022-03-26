package com.kalacheng.message.adapter;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.message.R;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.FaceUtil;

import java.util.List;

/**
 * Created by cxf on 2018/7/11.
 * 聊天表情的RecyclerView Adapter
 */

public class ChatFaceAdapter extends RecyclerView.Adapter<ChatFaceAdapter.Vh> {

    private List<String> mList;
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;

    public ChatFaceAdapter(List<String> list, LayoutInflater inflater, final  ChatFaceAdapter.OnFaceClickListener onFaceClickListener) {
        mList = list;
        mInflater = inflater;
        mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Object tag = v.getTag();
                L.e("点击了表情"+tag.toString() + onFaceClickListener);
                if (tag != null && onFaceClickListener != null) {

                    String str = (String) v.getTag();
                    if (!TextUtils.isEmpty(str)) {
                        if ("<".equals(str)) {
                            onFaceClickListener.onClickDelete();
                        } else {
                            onFaceClickListener.onClickFace(str, v.getId());
                        }
                    }
                }
            }
        };
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.layout_face_img, parent, false));
    }

    @Override
    public void onBindViewHolder(Vh vh, int position) {
        vh.setData(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {

        ImageView mImageView;

        public Vh(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView;
            mImageView.setOnClickListener(mOnClickListener);
        }

        void setData(String str) {
            mImageView.setTag(str);
            if (!TextUtils.isEmpty(str)) {
                if ("<".equals(str)) {
                    mImageView.setImageResource(R.mipmap.icon_del);
                } else {
                    int imgRes = FaceUtil.getFaceImageRes(str);
                    mImageView.setId(imgRes);
                    mImageView.setImageResource(imgRes);
                }
            }else{
                mImageView.setClickable(false);
            }
        }
    }

    public interface OnFaceClickListener {

        void onClickFace(String str, int res);

        void onClickDelete();

    }
}
