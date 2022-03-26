package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.bean.ShareDialogBean;
import com.kalacheng.util.adapter.SimpleImgTextAdapter;
import com.kalacheng.util.bean.SimpleImageUrlTextBean;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.util.utils.SpUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 分享Dialog
 */
public class ShareDialog extends BaseDialogFragment {
    public static final String ShareDialogOtherBeans = "SHARE_DIALOG_OTHER_BEANS";//第二行控件
    private ShareDialogListener mShareDialogListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_share;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.BottomDialogStyle;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        RecyclerView recyclerViewShare = mRootView.findViewById(R.id.recyclerViewShare);
        recyclerViewShare.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewShare.setHasFixedSize(true);
        recyclerViewShare.setNestedScrollingEnabled(false);
        String shareType = (String) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_SHARE_TYPE, "");
        if (!TextUtils.isEmpty(shareType)) {
            List<String> shareTypeList = Arrays.asList(shareType.split(","));
            if (shareTypeList.size() > 0) {
                List<SimpleImageUrlTextBean> shareBeans = new ArrayList<>();
                for (String string : shareTypeList) {
                    if ("1".equals(string)) {
                        SimpleImageUrlTextBean bean = new SimpleImageUrlTextBean(R.mipmap.icon_share_util_wx, "微信好友");
                        bean.id = 1;
                        shareBeans.add(bean);
                    } else if ("2".equals(string)) {
                        SimpleImageUrlTextBean bean = new SimpleImageUrlTextBean(R.mipmap.icon_share_util_wx_quan, "朋友圈");
                        bean.id = 2;
                        shareBeans.add(bean);
                    } else if ("3".equals(string)) {
                        SimpleImageUrlTextBean bean = new SimpleImageUrlTextBean(R.mipmap.icon_share_util_qq, "QQ好友");
                        bean.id = 3;
                        shareBeans.add(bean);
                    } else if ("4".equals(string)) {
                        SimpleImageUrlTextBean bean = new SimpleImageUrlTextBean(R.mipmap.icon_share_util_qq_zone, "QQ空间");
                        bean.id = 4;
                        shareBeans.add(bean);
                    }
                }
                SimpleImgTextAdapter shareAdapter = new SimpleImgTextAdapter(shareBeans);
                shareAdapter.setLayoutWidth(78);
                shareAdapter.setImgWidthHeight(38, 38);
                shareAdapter.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                shareAdapter.setPadding(0, 0, 0, 6);
                shareAdapter.setTextColor("#3A3A3A");
                shareAdapter.setTextSize(11);
                recyclerViewShare.setAdapter(shareAdapter);
                shareAdapter.setOnItemClickCallback(new OnBeanCallback<SimpleImageUrlTextBean>() {
                    @Override
                    public void onClick(SimpleImageUrlTextBean bean) {
                        if (mShareDialogListener != null) {
                            mShareDialogListener.onItemClick(bean.id);
                        }
                        dismiss();
                    }
                });
            } else {
                recyclerViewShare.setVisibility(View.GONE);
                mRootView.findViewById(R.id.viewDivider).setVisibility(View.GONE);
            }
        } else {
            recyclerViewShare.setVisibility(View.GONE);
            mRootView.findViewById(R.id.viewDivider).setVisibility(View.GONE);
        }

        RecyclerView recyclerViewOther = mRootView.findViewById(R.id.recyclerViewOther);
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        recyclerViewOther.setHasFixedSize(true);
        recyclerViewOther.setNestedScrollingEnabled(false);
        Bundle bundle = getArguments();
        if (bundle != null) {
            ArrayList<ShareDialogBean> otherBeans = bundle.getParcelableArrayList(ShareDialogOtherBeans);
            if (otherBeans != null && otherBeans.size() > 0) {
                List<SimpleImageUrlTextBean> simpleImageUrlTextBeans = new ArrayList<>();
                for (ShareDialogBean shareDialogBean : otherBeans) {
                    SimpleImageUrlTextBean simpleImageUrlTextBean = new SimpleImageUrlTextBean(shareDialogBean.src, shareDialogBean.text);
                    simpleImageUrlTextBean.id = shareDialogBean.id;
                    simpleImageUrlTextBeans.add(simpleImageUrlTextBean);
                }
                SimpleImgTextAdapter otherAdapter = new SimpleImgTextAdapter(simpleImageUrlTextBeans);
                otherAdapter.setLayoutWidth(78);
                otherAdapter.setImgWidthHeight(38, 38);
                otherAdapter.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
                otherAdapter.setPadding(0, 0, 0, 6);
                otherAdapter.setTextColor("#3A3A3A");
                otherAdapter.setTextSize(11);
                recyclerViewOther.setAdapter(otherAdapter);
                otherAdapter.setOnItemClickCallback(new OnBeanCallback<SimpleImageUrlTextBean>() {
                    @Override
                    public void onClick(SimpleImageUrlTextBean bean) {
                        if (mShareDialogListener != null) {
                            mShareDialogListener.onItemClick(bean.id);
                        }
                        dismiss();
                    }
                });
            } else {
                recyclerViewOther.setVisibility(View.GONE);
                mRootView.findViewById(R.id.viewDivider).setVisibility(View.GONE);
            }
        } else {
            recyclerViewOther.setVisibility(View.GONE);
            mRootView.findViewById(R.id.viewDivider).setVisibility(View.GONE);
        }

        mRootView.findViewById(R.id.tvCancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void setShareDialogListener(ShareDialogListener shareDialogListener) {
        mShareDialogListener = shareDialogListener;
    }

    public interface ShareDialogListener {
        void onItemClick(long id);
    }
}
