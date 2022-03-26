package com.kalacheng.login.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.login.R;

import java.util.List;

public class LoginTypeAdapter extends RecyclerView.Adapter<LoginTypeAdapter.LoginTypeViewHolder> {

    private Context mContext;
    private List<String> mList;

    private LoginTypeOnItemCallBack callBack;

    public LoginTypeAdapter(Context mContext, List<String> mList) {
        this.mContext = mContext;
        this.mList = mList;

    }

    @NonNull
    @Override
    public LoginTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_login_type, null, false);
        LoginTypeViewHolder holder = new LoginTypeViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoginTypeViewHolder holder, final int position) {
        if (mList.get(position).equals("1")) {
            holder.image_login_type.setBackgroundResource(R.mipmap.icon_login_qq);
        } else if (mList.get(position).equals("2")) {
            holder.image_login_type.setBackgroundResource(R.mipmap.icon_login_weixin);
        }
        holder.image_login_type.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callBack.onClick(position);
            }
        });
    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class LoginTypeViewHolder extends RecyclerView.ViewHolder {
        public ImageView image_login_type;

        public LoginTypeViewHolder(@NonNull View itemView) {
            super(itemView);
            image_login_type = itemView.findViewById(R.id.image_login_type);
        }
    }

    public void setLoginTypeOnItemCallBack(LoginTypeOnItemCallBack back) {
        this.callBack = back;
    }

    public interface LoginTypeOnItemCallBack {
        public void onClick(int position);
    }
}
