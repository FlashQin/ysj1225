package com.kalacheng.commonview.music.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppUserMusicDTO;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class LiveMusicListAdpater extends RecyclerView.Adapter<LiveMusicListAdpater.LiveMusicListViewHolder> {
    private Context mContext;
    List<AppUserMusicDTO> list = new ArrayList<>();
    private long palyID = -1;
    private LiveMusicListCallBack liveMusicListCallBack;

    public LiveMusicListAdpater(Context mContext) {
        this.mContext = mContext;

    }

    public void setData(List<AppUserMusicDTO> data) {
        this.list.clear();
        if (data != null) {
            this.list.addAll(data);
        }
        notifyDataSetChanged();
    }

    //删除
    public void detele(int poistion) {
        list.remove(poistion);
        notifyDataSetChanged();
    }

    //正在播放的音乐
    public void setMusic(long id) {
        palyID = id;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public LiveMusicListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.livemusiclist_itme, null, false);
        LiveMusicListViewHolder holder = new LiveMusicListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMusicListViewHolder holder, final int position) {
        holder.LiveMusicList_Info.setText(list.get(position).author);
        holder.LiveMusicList_Name.setText(list.get(position).name);
        ImageLoader.display(list.get(position).cover, holder.LiveMusicList_Image, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        holder.LiveMusicList_sort.setText(String.valueOf((position + 1)));
        holder.LiveMusicList_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveMusicListCallBack.onClick(position);
            }
        });
        holder.LiveMusicList_Detile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                liveMusicListCallBack.onDelete(position);
            }
        });

        if (palyID == list.get(position).id) {
            holder.LiveMusicList_State.setVisibility(View.VISIBLE);
        } else {
            holder.LiveMusicList_State.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class LiveMusicListViewHolder extends RecyclerView.ViewHolder {
        private TextView LiveMusicList_Info;
        private TextView LiveMusicList_Name;
        private RelativeLayout LiveMusicList_Re;
        private ImageView LiveMusicList_Detile;
        private RoundedImageView LiveMusicList_Image;
        private TextView LiveMusicList_sort;
        private TextView LiveMusicList_State;

        public LiveMusicListViewHolder(@NonNull View itemView) {
            super(itemView);
            LiveMusicList_Name = itemView.findViewById(R.id.LiveMusicList_Name);
            LiveMusicList_Info = itemView.findViewById(R.id.LiveMusicList_Info);
            LiveMusicList_Re = itemView.findViewById(R.id.LiveMusicList_Re);
            LiveMusicList_Detile = itemView.findViewById(R.id.LiveMusicList_Detile);
            LiveMusicList_Image = itemView.findViewById(R.id.LiveMusicList_Image);
            LiveMusicList_sort = itemView.findViewById(R.id.LiveMusicList_sort);
            LiveMusicList_State = itemView.findViewById(R.id.LiveMusicList_State);
        }
    }

    public void setLiveMusicListCallBack(LiveMusicListCallBack callBack) {
        this.liveMusicListCallBack = callBack;
    }

    public interface LiveMusicListCallBack {
        public void onClick(int poisition);

        public void onDelete(int position);
    }
}
