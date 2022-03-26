package com.kalacheng.centercommon.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.databinding.ItemTextInvitationCodeBinding;
import com.kalacheng.libuser.model.KeyValueDto;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

public class QRCodeAdpater extends RecyclerView.Adapter<QRCodeAdpater.ViewHolder> {

    private List<KeyValueDto> mList = new ArrayList<>();

    public QRCodeAdpater(List<KeyValueDto> list) {
        mList.clear();
        mList.addAll(list);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTextInvitationCodeBinding binding = DataBindingUtil
                .inflate(LayoutInflater.from(parent.getContext()), R.layout.item_text_invitation_code,
                        parent, false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.binding.setBean(mList.get(position));
        holder.binding.executePendingBindings();
//        ImageLoader.displayFitCenter(mList.get(position).key, holder.binding.ivCode);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ItemTextInvitationCodeBinding binding;

        public ViewHolder(ItemTextInvitationCodeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
