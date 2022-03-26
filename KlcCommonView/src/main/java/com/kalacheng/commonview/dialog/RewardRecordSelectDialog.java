package com.kalacheng.commonview.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.kalacheng.commonview.R;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;

/**
 * 佣金明细筛选弹窗
 */
public class RewardRecordSelectDialog extends DialogFragment {
    protected Context mContext;
    protected View mRootView;
    private RewardRecordSelectListener rewardRecordSelectListener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        mRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_reward_record_select, null);

        Dialog dialog = new Dialog(mContext, R.style.dialog2);
        dialog.setContentView(mRootView);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.END | Gravity.TOP;
        params.x = DpUtil.dp2px(10);
        params.y = DpUtil.dp2px(40);
        window.setAttributes(params);

        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (ConfigUtil.getIntValue(R.integer.commissionSelectType) == 1) {
            //1 注册、充值
            mRootView.findViewById(R.id.tvSelectType4).setVisibility(View.GONE);
            mRootView.findViewById(R.id.tvSelectType5).setVisibility(View.GONE);
        }else if(ConfigUtil.getIntValue(R.integer.commissionSelectType) == 2) {
            //2 充值、签到
            mRootView.findViewById(R.id.tvSelectType1).setVisibility(View.GONE);
            mRootView.findViewById(R.id.tvSelectType4).setVisibility(View.GONE);
            mRootView.findViewById(R.id.tvSelectType5).setVisibility(View.GONE);

            mRootView.findViewById(R.id.tvSelectType6).setVisibility(View.VISIBLE);
        }

        mRootView.findViewById(R.id.tvSelectType0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(0, "全部");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(50, "注册佣金");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(51, "充值佣金");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(52, "用户消费佣金");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(54, "主播认证佣金");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(53, "主播收益佣金");
                }
                dismiss();
            }
        });
        mRootView.findViewById(R.id.tvSelectType6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (rewardRecordSelectListener != null) {
                    rewardRecordSelectListener.onSelect(55, "签到佣金");
                }
                dismiss();
            }
        });
    }

    public void setRewardRecordSelectListener(RewardRecordSelectListener rewardRecordSelectListener) {
        this.rewardRecordSelectListener = rewardRecordSelectListener;
    }

    public interface RewardRecordSelectListener {
        void onSelect(int type, String name);
    }
}
