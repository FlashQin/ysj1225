package com.kalacheng.livecommon.music;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.ApiMusic;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ItemSlideHelper;
import com.kalacheng.util.view.ProgressTextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * Created by cxf on 2017/9/2.
 */

public class LiveMusicAdapter extends RecyclerView.Adapter<LiveMusicAdapter.Vh> implements ItemSlideHelper.Callback {

    private Context mContext;
    private List<ApiMusic> mList;
    private LayoutInflater mInflater;
    private RecyclerView mRecyclerView;
    private View.OnClickListener mChooseClickListener;
    private View.OnClickListener mDeleteClickListener;
    private ActionListener mActionListener;
    private HashMap<String, Integer> mMap;

    public LiveMusicAdapter(Context context) {
        mContext = context;
        mList = new ArrayList<>();
        mInflater = LayoutInflater.from(context);
        mMap = new HashMap<>();
        mChooseClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null && mActionListener != null) {
                    int position = (int) tag;
                    ApiMusic bean = mList.get(position);
                    if (bean.progress == 100) {
                        mActionListener.onChoose(bean);
                    } else if (bean.progress == 0) {
                        mMap.put(bean.id, position);
                        mActionListener.onDownLoad(bean);
                    }
                }
            }
        };
        mDeleteClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Object tag = v.getTag();
                if (tag != null) {
                    int position = (int) tag;
                    ApiMusic bean = mList.get(position);
                    mList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, mList.size());
                    if (mActionListener != null) {
                        mActionListener.onItemRemoved(bean.id, mList.size());
                    }
                }
            }
        };
    }

    @Override
    public Vh onCreateViewHolder(ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_live_music, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull Vh holder, int position) {
    }

    @Override
    public void onBindViewHolder(@NonNull Vh vh, int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        vh.setData(mList.get(position), position, payload);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void setList(List<ApiMusic> list) {
        mMap.clear();
        mList.clear();
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        mMap.clear();
        mList.clear();
        notifyDataSetChanged();
    }

    class Vh extends RecyclerView.ViewHolder {

        TextView mMusicName;
        TextView mArtist;
        ProgressTextView mProgressTextView;
        View mBtnDelete;

        public Vh(View itemView) {
            super(itemView);
            mMusicName = (TextView) itemView.findViewById(R.id.music_name);
            mArtist = (TextView) itemView.findViewById(R.id.artist);
            mProgressTextView = (ProgressTextView) itemView.findViewById(R.id.ptv);
            mProgressTextView.setOnClickListener(mChooseClickListener);
            mBtnDelete = itemView.findViewById(R.id.btn_delete);
            mBtnDelete.setOnClickListener(mDeleteClickListener);
        }

        void setData(ApiMusic bean, int position, Object payload) {
            if (payload == null) {
                mMusicName.setText(bean.name);
                mArtist.setText(bean.authors);
            }
            mProgressTextView.setTag(position);
            mBtnDelete.setTag(position);
            mProgressTextView.setProgress(bean.progress);
        }
    }

    public void updateItem(String musicId, int progress) {
        if (TextUtils.isEmpty(musicId) || mMap == null || mMap.size() == 0 || mList == null || mList.size() == 0) {
            return;
        }
        Integer position = mMap.get(musicId);
        if (position != null && position >= 0 && position < mList.size()) {
            ApiMusic bean = mList.get(position);
            if (bean != null && musicId.equals(bean.id)) {
                bean.progress = progress;
                notifyItemChanged(position);
                if (progress == 100) {
                    mMap.remove(musicId);
                }
            }
        }
    }

    public void release() {
        if (mList != null) {
            mList.clear();
        }
        if (mMap != null) {
            mMap.clear();
        }
        mContext = null;
        mActionListener = null;
        mChooseClickListener = null;
        mDeleteClickListener = null;
    }


    public void setActionListener(ActionListener listener) {
        mActionListener = listener;
    }

    public interface ActionListener {
        /**
         * 选中歌曲
         */
        void onChoose(ApiMusic bean);

        /**
         * 下载歌曲
         */
        void onDownLoad(ApiMusic bean);

        /**
         * 删除歌曲
         */
        void onItemRemoved(String musicId, int listSize);
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        mRecyclerView = recyclerView;
        mRecyclerView.addOnItemTouchListener(new ItemSlideHelper(mContext, this));
    }

    @Override
    public int getHorizontalRange(RecyclerView.ViewHolder holder) {
        return DpUtil.dp2px(60);
    }

    @Override
    public RecyclerView.ViewHolder getChildViewHolder(View childView) {
        if (mRecyclerView != null && childView != null) {
            return mRecyclerView.getChildViewHolder(childView);
        }
        return null;
    }

    @Override
    public View findTargetView(float x, float y) {
        return mRecyclerView.findChildViewUnder(x, y);
    }

    @Override
    public boolean useLeftScroll() {
        return true;
    }

    @Override
    public void onLeftScroll(RecyclerView.ViewHolder holder) {

    }


}
