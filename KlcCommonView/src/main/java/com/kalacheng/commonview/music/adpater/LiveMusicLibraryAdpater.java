package com.kalacheng.commonview.music.adpater;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppMusicDTO;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class LiveMusicLibraryAdpater extends RecyclerView.Adapter<LiveMusicLibraryAdpater.LiveMusicLibraryViewHolder> {
    private Context mContext;
    private List<AppMusicDTO> mList = new ArrayList<>();
    private LiveMusicLibraryCallBack callBack;
    private int addPoistion;

    public LiveMusicLibraryAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void getList(List<AppMusicDTO> data) {
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    //添加
    public void addMusic(int poistion) {
        mList.get(poistion).added = 1;
        notifyItemChanged(poistion);
    }

    @NonNull
    @Override
    public LiveMusicLibraryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.livemusiclibrary_itme, null, false);
        LiveMusicLibraryViewHolder holder = new LiveMusicLibraryViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LiveMusicLibraryViewHolder holder, final int position) {
        holder.LiveMusicLibrary_Info.setText(mList.get(position).author);
        holder.LiveMusicLibrary_Name.setText(mList.get(position).name);

        if (mList.get(position).added == 1) {
            holder.LiveMusicLibrary_add.setText("已添加");
            holder.LiveMusicLibrary_add.setBackgroundResource(R.drawable.gradient_grey);
        } else {
            holder.LiveMusicLibrary_add.setText("添加");
            holder.LiveMusicLibrary_add.setBackgroundResource(R.drawable.gradient_blue);
        }
        holder.LiveMusicLibrary_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                callBack.onAddClick(position);
            }
        });

        ImageLoader.display(mList.get(position).cover, holder.LiveMusicLibrary_HeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

    }

    @Override
    public int getItemCount() {
        return mList.size();

    }

    public AppMusicDTO getItem(int position) {
        if (mList.size() > position) {
            return mList.get(position);
        }
        return null;
    }

    public class LiveMusicLibraryViewHolder extends RecyclerView.ViewHolder {
        public TextView LiveMusicLibrary_Name;
        public TextView LiveMusicLibrary_Info;
        public TextView LiveMusicLibrary_add;
        public RoundedImageView LiveMusicLibrary_HeadImage;

        public LiveMusicLibraryViewHolder(@NonNull View itemView) {
            super(itemView);
            LiveMusicLibrary_Name = itemView.findViewById(R.id.LiveMusicLibrary_Name);
            LiveMusicLibrary_Info = itemView.findViewById(R.id.LiveMusicLibrary_Info);
            LiveMusicLibrary_add = itemView.findViewById(R.id.LiveMusicLibrary_add);
            LiveMusicLibrary_HeadImage = itemView.findViewById(R.id.LiveMusicLibrary_HeadImage);
        }
    }

    public void setLiveMusicLibraryCallBack(LiveMusicLibraryCallBack callBack) {
        this.callBack = callBack;
    }

    public interface LiveMusicLibraryCallBack {
        public void onAddClick(int poistion);
    }
}
