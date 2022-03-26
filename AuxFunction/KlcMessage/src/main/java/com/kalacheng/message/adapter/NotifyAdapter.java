package com.kalacheng.message.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiChatRoom;
import com.kalacheng.libuser.model.AppSystemNotice;
import com.kalacheng.message.R;
import com.makeramen.roundedimageview.RoundedImageView;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NotifyAdapter extends RecyclerView.Adapter<NotifyAdapter.NotifyViewHolder> {

    List<AppSystemNotice> list;
    OnClick onClick;
    ReadAndDelete readAndDelete;
    Context mContext;

    public NotifyAdapter() {
        list = new ArrayList<>();
    }

    public void setList(List<AppSystemNotice> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void setOnClick(OnClick onClick) {
        this.onClick = onClick;
    }

    public ReadAndDelete getReadAndDelete() {
        return readAndDelete;
    }

    public void setReadAndDelete(ReadAndDelete readAndDelete) {
        this.readAndDelete = readAndDelete;
    }

    @NonNull
    @Override
    public NotifyAdapter.NotifyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_notify, parent, false);
        return new NotifyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotifyAdapter.NotifyViewHolder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull NotifyViewHolder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        final AppSystemNotice noticeUser = list.get(position);
        if (payload == null) {
            ImageLoader.display(noticeUser.logo, holder.singAvatarIv);
            holder.nameTv.setText(noticeUser.title);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClick != null) {
                        onClick.onClick(noticeUser);
                        list.get(position).noReadNumber = 0;
                        notifyItemChanged(position);
                    }
                }
            });

            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    showDialog(v, noticeUser.id, position);
                    return false;
                }
            });
        }
        holder.contentTv.setText(noticeUser.content);
        holder.timeTv.setText(noticeUser.addtime);
        int count = noticeUser.noReadNumber;
        if (count > 0) {
            holder.unReadCountTv.setVisibility(View.VISIBLE);
            holder.unReadCountTv.setText(String.valueOf(count));
        } else {
            holder.unReadCountTv.setVisibility(View.INVISIBLE);
        }
    }

    @SuppressLint("ResourceType")
    private void showDialog(final View view, final long id, final int position) {
        view.setAlpha(0.3f);
        DialogUtil.showStringArrayDialog(mContext, new Integer[]{R.string.msg_read,
                R.string.msg_delete}, new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                view.setAlpha(1f);
            }
        }, new DialogUtil.StringArrayDialogCallback() {
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.msg_read) {
                    HttpApiChatRoom.clearNoticeMsg((int) id, 3, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (readAndDelete != null) {
                                readAndDelete.onRead();
                            }
                            list.get(position).noReadNumber = 0;
                            notifyItemChanged(position);
                        }
                    });

                } else if (tag == R.string.msg_delete) {
                    HttpApiChatRoom.delNoticeMsg((int) id, new HttpApiCallBack<HttpNone>() {
                        @Override
                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                            if (readAndDelete != null) {
                                readAndDelete.onDelte();
                            }
                            list.remove(position);
                            notifyItemRemoved(position);
                        }
                    });

                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return list.size();
    }

    static class NotifyViewHolder extends RecyclerView.ViewHolder {
        RoundedImageView singAvatarIv;
        TextView nameTv;
        TextView contentTv;
        TextView timeTv;
        TextView unReadCountTv;

        NotifyViewHolder(@NonNull View itemView) {
            super(itemView);
            singAvatarIv = itemView.findViewById(R.id.singAvatarIv);
            nameTv = itemView.findViewById(R.id.nameTv);
            contentTv = itemView.findViewById(R.id.contentTv);
            timeTv = itemView.findViewById(R.id.timeTv);
            unReadCountTv = itemView.findViewById(R.id.unReadCountTv);
        }
    }

    public interface OnClick {

        void onClick(AppSystemNotice noticeUser);

    }

    public interface ReadAndDelete {

        void onRead();

        void onDelte();
    }

}
