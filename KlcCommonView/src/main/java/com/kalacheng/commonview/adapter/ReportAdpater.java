package com.kalacheng.commonview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.AppUsersVideoReportClassify;

import java.util.ArrayList;
import java.util.List;

public class ReportAdpater extends RecyclerView.Adapter<ReportAdpater.ReportViewHolder> {
    private Context mContext;
    private int positontslect = -1;
    private ReportItmeCallBack callBack;
    private List<AppUsersVideoReportClassify> mList = new ArrayList<>();

    public ReportAdpater(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<AppUsersVideoReportClassify> data) {
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void setPositontslect(int poistion) {
        this.positontslect = poistion;
        notifyDataSetChanged();
    }

    public long getItemId(int position) {
        if (mList.size() > position) {
            return mList.get(position).id;
        }
        return -1;
    }

    @NonNull
    @Override
    public ReportViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.report_itme, null, false);
        ReportViewHolder holder = new ReportViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ReportViewHolder holder, final int position) {
        if (positontslect == position) {
            holder.Select.setBackgroundResource(R.mipmap.selection);
        } else {
            holder.Select.setBackgroundResource(R.mipmap.unchecked);
        }

        holder.ReportClassly_Name.setText(mList.get(position).name);

        holder.ReportClassly_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return null != mList && mList.size() > 0 ? mList.size() : 0;
    }

    public class ReportViewHolder extends RecyclerView.ViewHolder {
        public ImageView Select;
        public TextView ReportClassly_Name;
        public LinearLayout ReportClassly_Re;

        public ReportViewHolder(@NonNull View itemView) {
            super(itemView);
            ReportClassly_Name = itemView.findViewById(R.id.ReportClassly_Name);
            Select = itemView.findViewById(R.id.Select);
            ReportClassly_Re = itemView.findViewById(R.id.ReportClassly_Re);
        }
    }

    public void setReportItmeCallBack(ReportItmeCallBack back) {
        this.callBack = back;
    }

    public interface ReportItmeCallBack {
        public void onClick(int poistion);
    }
}
