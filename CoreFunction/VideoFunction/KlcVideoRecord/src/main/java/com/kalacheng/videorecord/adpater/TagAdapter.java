package com.kalacheng.videorecord.adpater;

import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.model.AppHotSort;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.videorecord.R;
import com.kalacheng.videorecord.databinding.PublishTagItemBinding;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.CheckDoubleClick;

import java.util.ArrayList;
import java.util.List;

public class TagAdapter extends RecyclerView.Adapter<TagAdapter.ViewHolder> {

    private List<AppHotSort> mList = new ArrayList<>();
    public List<Long> ids = new ArrayList<>();
    public boolean isPublish;
    public Context mContext;
    private OnBeanCallback<AppHotSort> onBeanCallback;

    public TagAdapter(List<AppHotSort> data) {
        mList.clear();
        mList.addAll(data);
    }

    @NonNull
    @Override
    public TagAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        PublishTagItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.publish_tag_item, parent, false);
        ViewHolder holder = new ViewHolder(binding);
        return holder;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onBindViewHolder(@NonNull TagAdapter.ViewHolder holder, final int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
        if (!TextUtils.isEmpty(mList.get(position).name) && mList.get(position).name.length() > 5) {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
        } else {
            holder.binding.typeLable.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        }
        if (isPublish) {
            if (mList.get(position).isChecked == 0) {
                holder.binding.typeLable.setSelected(false);
            } else {
                holder.binding.typeLable.setSelected(true);
            }
            holder.binding.setCallback(new OnBeanCallback<AppHotSort>() {
                @Override
                public void onClick(AppHotSort bean) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (bean.isChecked == 0) {
                        if (ids.size() < 3) {
                            ids.add(bean.id);
                            bean.isChecked = 1;
                        } else {
                            ToastUtil.show("选择分类大于三个");
                        }
                    } else {
                        ids.remove(bean.id);
                        bean.isChecked = 0;
                    }
                    notifyItemChanged(position);
                }
            });
        } else {
            holder.binding.typeLable.setBackgroundResource(R.drawable.translucent_grey_rect2);
            holder.binding.typeLable.setTextColor(mContext.getColor(R.color.white));
            holder.binding.setCallback(new OnBeanCallback<AppHotSort>() {
                @Override
                public void onClick(AppHotSort bean) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (onBeanCallback != null) {
                        onBeanCallback.onClick(bean);
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        PublishTagItemBinding binding;

        public ViewHolder(@NonNull PublishTagItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    public void setOnBeanCallback(OnBeanCallback<AppHotSort> onBeanCallback) {
        this.onBeanCallback = onBeanCallback;
    }

    public String getSelectIds() {
        if (ids.size() > 0) {
            String strs = "";
            for (Long id : ids) {
                strs += id + ",";
            }
            strs = strs.substring(0, strs.length() - 1);
            return strs;
        }
        return "";
    }
}
