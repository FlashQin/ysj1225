package com.kalacheng.centercommon.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.fastjson.JSONObject;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemAnchorCommentBinding;
import com.kalacheng.libuser.model.ApiUserComment;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.DrawableUtil;

import java.util.List;
import java.util.Map;

public class AnchorCommentAdpater extends RecyclerView.Adapter<AnchorCommentAdpater.ViewHolder> {

    private List<ApiUserComment> mList;
    Context mContext;

    public AnchorCommentAdpater(Context context, List<ApiUserComment> data) {
        this.mList = data;
        mContext = context;

    }

    public void setData(List<ApiUserComment> data) {
        this.mList = data;
        notifyDataSetChanged();
    }

    public void clearData() {
        mList.clear();
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public AnchorCommentAdpater.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemAnchorCommentBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_anchor_comment, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull AnchorCommentAdpater.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        holder.setLabel(mList.get(position));
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ItemAnchorCommentBinding binding;

        public ViewHolder(@NonNull ItemAnchorCommentBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setLabel(ApiUserComment apiUserComment) {
            binding.label.removeAllViews();
            Map<String, String> maps = JSONObject.parseObject(apiUserComment.labels, Map.class);
            if (maps.size() != 0) {
                for (String key : maps.keySet()) {
                    View view = LayoutInflater.from(mContext).inflate(R.layout.label_itme, null, false);
                    final TextView Search_Content = view.findViewById(R.id.Search_Content);
                    DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
//                builder.setStroke(2, Color.parseColor(mList.get(i).fontColor));
                    builder.setColor(Color.parseColor(key));
                    builder.setCornerRadius(40f);
                    Drawable drawable = builder.create();
                    Search_Content.setBackground(drawable);
                    Search_Content.setTextColor(Color.parseColor("#ffffff"));
                    Search_Content.setText(maps.get(key));
                    binding.label.addView(view);
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) view.getLayoutParams();
                    layoutParams.gravity = Gravity.CENTER_VERTICAL;
                    layoutParams.setMargins(DpUtil.dp2px(5), 0, DpUtil.dp2px(5), 0);

                }
            }
        }
    }
}
