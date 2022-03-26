package com.kalacheng.main.adapter;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAPPAnchor;
import com.kalacheng.commonview.dialog.AnchorAttestationDialogFragment;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.List;

public class ChatLocalAdpater extends RecyclerView.Adapter<ChatLocalAdpater.ChatLocalViewHolder> {
    private Context mContext;
    ProcessResultUtil mProcessResultUtil;
    private List<ApiUsersLine> mList = new ArrayList<>();

    public ChatLocalAdpater(Context context) {
        this.mContext = context;
        mProcessResultUtil = new ProcessResultUtil((FragmentActivity) context);
    }

    public void getData(List<ApiUsersLine> data) {
        this.mList.clear();
        if (data != null) {
            this.mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChatLocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.chatlocal_item, null, false);
        ChatLocalViewHolder holder = new ChatLocalViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChatLocalViewHolder holder, final int position) {
        ImageLoader.display(mList.get(position).avatar, holder.ChatLocal_headImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        holder.ChatLocal_Name.setText(mList.get(position).userName);
        holder.ChatLocal_Distance.setText(mList.get(position).distance + "km");
        ImageLoader.display(mList.get(position).userGradeImg, holder.ChatLocal_vserGrade);
        ImageLoader.display(mList.get(position).nobleGradeImg, holder.ChatLocal_vipGrade);

        if (TextUtils.isEmpty(mList.get(position).nobleGradeImg)) {
            holder.ChatLocal_vipGrade.setVisibility(View.GONE);
        } else {
            holder.ChatLocal_vipGrade.setVisibility(View.VISIBLE);
        }

        if (mList.get(position).isSayCoin == 0) {
            holder.ll_coin.setVisibility(View.VISIBLE);
            holder.tv_coin.setText(DecimalFormatUtils.isIntegerDouble(mList.get(position).coin));
        } else {
            holder.ll_coin.setVisibility(View.GONE);
        }

        holder.ChatLocal_sex.setImageResource(mList.get(position).sex == 2 ? com.kalacheng.centercommon.R.mipmap.icon_girl_white : com.kalacheng.centercommon.R.mipmap.icon_boy_white);
        holder.tv_age.setText(mList.get(position).age + "");
        holder.layoutSex.setBackgroundResource(mList.get(position).sex == 2 ? com.kalacheng.centercommon.R.drawable.bg_sex_girl : com.kalacheng.centercommon.R.drawable.bg_sex_boy);

        holder.ChatLocal_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    mProcessResultUtil.requestPermissions(new String[]{
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.CAMERA,
                            Manifest.permission.RECORD_AUDIO
                    }, new Runnable() {
                        @Override
                        public void run() {
                            HttpApiAPPAnchor.is_auth(3, new HttpApiCallBack<HttpNone>() {
                                @Override
                                public void onHttpRet(int code, String msg, HttpNone retModel) {
                                    if (code == 1 && null != retModel) {
                                        if ("0".equals(retModel.no_use)) {
                                            ApiUserInfo info = new ApiUserInfo();
                                            info.userId = mList.get(position).uid;
                                            LiveConstants.mIsOOOSend = true;
                                            info.avatar = mList.get(position).avatar;
                                            info.sex = mList.get(position).sex;
                                            info.username = mList.get(position).userName;
                                            info.role = mList.get(position).role;
                                            LookRoomUtlis.getInstance().showDialog(1, info, mProcessResultUtil, mContext, 1);
                                        } else if ("1".equals(retModel.no_use)) {
                                            AnchorAttestationDialogFragment.getInstance().show(mContext);
                                        } else {
                                            if (!TextUtils.isEmpty(msg)) {
                                                ToastUtil.show(msg);
                                            }
                                        }
                                    } else {
                                        if (!TextUtils.isEmpty(msg)) {
                                            ToastUtil.show(msg);
                                        }
                                    }
                                }
                            });
                        }
                    });
                }
            }
        });

        holder.ChatLocal_Re.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, mList.get(position).uid).navigation();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class ChatLocalViewHolder extends RecyclerView.ViewHolder {
        public RoundedImageView ChatLocal_headImage;
        public TextView ChatLocal_Name;
        public TextView ChatLocal_Distance;
        public ImageView ChatLocal_sex;
        public ImageView ChatLocal_vserGrade;
        public ImageView ChatLocal_vipGrade;
        public ImageView ChatLocal_Btn;
        public TextView tv_age, tv_coin;
        public LinearLayout layoutSex, ll_coin;
        public RelativeLayout ChatLocal_Re;

        public ChatLocalViewHolder(@NonNull View itemView) {
            super(itemView);
            ChatLocal_headImage = itemView.findViewById(R.id.ChatLocal_headImage);
            ChatLocal_Name = itemView.findViewById(R.id.ChatLocal_Name);
            ChatLocal_Distance = itemView.findViewById(R.id.ChatLocal_Distance);
            ChatLocal_sex = itemView.findViewById(R.id.ChatLocal_sex);
            ChatLocal_vserGrade = itemView.findViewById(R.id.ChatLocal_vserGrade);
            ChatLocal_vipGrade = itemView.findViewById(R.id.ChatLocal_vipGrade);
            ChatLocal_Btn = itemView.findViewById(R.id.ChatLocal_Btn);
            tv_age = itemView.findViewById(R.id.tv_age);
            layoutSex = itemView.findViewById(R.id.layoutSex);
            ChatLocal_Re = itemView.findViewById(R.id.ChatLocal_Re);
            ll_coin = itemView.findViewById(R.id.ll_coin);
            tv_coin = itemView.findViewById(R.id.tv_coin);
        }
    }
}
