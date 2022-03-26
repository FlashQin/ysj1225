package com.kalacheng.commonview.music.adpater;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.music.bean.MusicChooseBean;
import com.kalacheng.commonview.music.listener.OnMusicChooseItemClickListener;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

public class MusicChooseAdapter extends RecyclerView.Adapter<MusicChooseAdapter.Vh> {
    private List<MusicChooseBean> mList = new ArrayList<>();
    private LayoutInflater mInflater;
    private View.OnClickListener mOnClickListener;
    private OnMusicChooseItemClickListener<MusicChooseBean> mOnItemClickListener;
    private boolean showSelect = true;

    public void setShowSelect(boolean showSelect) {
        this.showSelect = showSelect;
    }

    /**
     * 是否 开始下载
     * true 标识 下载中
     */
    private boolean isStartUpLoad = false;

    public void setStartUpLoad(boolean isStartUpLoad) {
        this.isStartUpLoad = isStartUpLoad;
    }

    public List<MusicChooseBean> getList() {
        return this.mList;
    }

    private Context mContext;
    public MusicChooseAdapter(Context context, List<MusicChooseBean> list) {
        this.mContext = context;
        this.mList = list;
        this.mInflater = LayoutInflater.from(context);
        this.mOnClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.tvSelect) {

                    if (!isStartUpLoad) {
                        if (CheckDoubleClick.isFastDoubleClick()) return;

                        Object tag = v.getTag();
                        if (tag != null && mOnItemClickListener != null) {
                            int position = (int) v.getTag();
                            MusicChooseBean bean = mList.get(position);
                            if (showSelect) {
                                mOnItemClickListener.onItemSelect(bean, position);
                            } else {
                                if (!TextUtils.isEmpty(bean.getPath())) {
                                    mOnItemClickListener.onItemDelete(bean, position);
                                }
                            }
                        }
                    }

                } else {
                    Object tag = v.getTag();
                    if (tag != null && mOnItemClickListener != null) {
                        int position = (int) v.getTag();
                        mOnItemClickListener.onItemClick(mList.get(position), position);
                    }
                }
            }
        };
    }

    public void setOnItemClickListener(OnMusicChooseItemClickListener<MusicChooseBean> listener) {
        mOnItemClickListener = listener;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_music_choose_local, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position) {
        vh.setData(mList.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    class Vh extends RecyclerView.ViewHolder {


        TextView tvSelect;

        TextView tv_music_name;
        TextView tv_music_author;
        TextView tv_duration;

        public Vh(View itemView) {
            super(itemView);

            tvSelect = itemView.findViewById(R.id.tvSelect);
            tv_music_name = itemView.findViewById(R.id.tv_music_name);
            tv_music_author = itemView.findViewById(R.id.tv_music_author);
            tv_duration = itemView.findViewById(R.id.tv_duration);

            tvSelect.setOnClickListener(mOnClickListener);
            itemView.setOnClickListener(mOnClickListener);
        }

        void setData(MusicChooseBean bean, int position) {
            tvSelect.setTag(position);
            itemView.setTag(position);

            if (!TextUtils.isEmpty(bean.getPath())) {
                tv_music_name.setText(bean.showName());
                tv_music_author.setText(bean.showAuthorTag());
                tv_duration.setText(bean.showDuration());
            }

            if (showSelect) {
                tvSelect.setBackgroundResource(R.color.white);
                //选中
                //上传状态 的变化
                //-2 为 上传失败 / -1 为 未上传 / 0 为 上传中 / 1 为 已上传
                tvSelect.setTextColor(ContextCompat.getColor(mContext, R.color.textColor3));
                if (bean.getTv_upload_type() == -2) {
                    tvSelect.setText("上传失败");
                } else if (bean.getTv_upload_type() == -1) {
                    tvSelect.setText("未上传");
                    tvSelect.setTextColor(Color.parseColor("#FFFFFF"));
                    tvSelect.setBackgroundResource(R.drawable.gradient_blue);
                } else if (bean.getTv_upload_type() == 0) {
                    tvSelect.setText("正在上传");
                } else {
                    tvSelect.setText("已上传");
                    tvSelect.setTextColor(ContextCompat.getColor(mContext, R.color.textColor9));
                }
            }
        }
    }
}
