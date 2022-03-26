package com.kalacheng.live.component.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.live.R;
import com.klc.bean.LiveRoomTypeBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by cxf on 2018/10/8.
 */

public class LiveRoomTypeAdapter extends RecyclerView.Adapter<LiveRoomTypeAdapter.Vh> {
    private Context mContext;
    private List<LiveRoomTypeBean> mList;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;


    public LiveRoomTypeAdapter(Context context, int checkedId) {
        mContext = context;
        mList = new ArrayList<>();
        String roomTypeStr = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_ROOM_TYPE, "");
        if (!TextUtils.isEmpty(roomTypeStr)) {
            String[] roomtype = roomTypeStr.split(",");
            List<String> listid = Arrays.asList(roomtype);
            //普通房间
            List arrList = new ArrayList(listid);
            arrList.add(0, "0");

            for (int i = 0; i < arrList.size(); i++) {
                LiveRoomTypeBean bean = new LiveRoomTypeBean();
                bean.id = Integer.parseInt(arrList.get(i).toString());
                if (bean.id == 1) {
                    //房间类型1是私密直播，2是收费直播，3是计时直播
                    bean.name = "私密直播";
                } else if (bean.id == 2) {
                    bean.name = "收费直播";
                } else if (bean.id == 3) {
                    bean.name = "计时直播";
                } else {
                    bean.name = "普通房间";
                }
                if (bean.id == checkedId) {
                    bean.isChecked = true;
                } else {
                    bean.isChecked = false;
                }
                mList.add(bean);
            }
        }
        mInflater = LayoutInflater.from(context);

    }

    public void setClick(int postion) {
        Log.i("aaa", postion + "");

        for (int i = 0; i < mList.size(); i++) {
            if (postion == i) {
                mList.get(i).setChecked(true);
            } else {
                mList.get(i).setChecked(false);
            }
        }

        notifyDataSetChanged();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public Vh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new Vh(mInflater.inflate(R.layout.item_live_type, parent, false));
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

        ImageView mIcon;
        TextView mName;
        LinearLayout room_lin;

        public Vh(View itemView) {
            super(itemView);
            mIcon = itemView.findViewById(R.id.icon);
            mName = itemView.findViewById(R.id.name);
            room_lin = itemView.findViewById(R.id.room_lin);
        }

        void setData(final LiveRoomTypeBean bean, final int position) {
            itemView.setTag(position);
            mName.setText(bean.getName());
            if (bean.isChecked()) {
                mName.setTextColor(mContext.getResources().getColor(R.color.color_live_room_type_checked));
            } else {
                mName.setTextColor(0xffffffff);
            }

            if (bean.id == 0) {
                if (bean.isChecked) {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_normal_1);
                } else {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_normal_2);
                }
            } else if (bean.id == 1) {
                if (bean.isChecked) {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_pwd_1);
                } else {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_pwd_2);
                }
            } else if (bean.id == 2) {

                if (bean.isChecked) {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_pay_1);
                } else {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_pay_2);
                }
            } else if (bean.id == 3) {
                if (bean.isChecked) {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_time_1);
                } else {
                    mIcon.setBackgroundResource(R.mipmap.icon_live_type_time_2);
                }
            }
            room_lin.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mOnItemClickListener.onClcik(bean, position);
                }
            });
        }

    }

    public interface OnItemClickListener {
        public void onClcik(LiveRoomTypeBean bean, int poistion);
    }
}
