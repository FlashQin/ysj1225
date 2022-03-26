package com.kalacheng.live.component.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiUsableAnchorResp;
import com.kalacheng.live.component.adapter.LinkMicListAdapter;
import com.kalacheng.live.component.adapter.LiveLinkMicPkSearchDialog;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.live.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.List;

public class LiveLinkMicListDialogFragment extends BaseDialogFragment implements View.OnClickListener, LiveLinkMicPkSearchDialog.ActionListener {
    LinkMicListAdapter linkMicListAdapter = new LinkMicListAdapter();
    String keyWord = "";
    private EditText mEditText;
    private Handler mHandler = new Handler();
    private View mSearchView;
    private LiveLinkMicPkSearchDialog mLivePkSearchDialog;
    RefreshLayout refreshLayout;

    private List<ApiUsableAnchorResp> mList;
    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_pk;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.dp2px(300);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mRootView.findViewById(R.id.btn_search).setOnClickListener(this);
        RecyclerView recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(linkMicListAdapter);
        getUsableAnchor(keyWord);

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_LaunchInteraction, new MsgListener.SimpleMsgListener<ApiUsableAnchorResp>() {
            @Override
            public void onMsg(ApiUsableAnchorResp apiUsableAnchorResp) {
                dismissAllowingStateLoss();
            }

            @Override
            public void onTagMsg(String tag, ApiUsableAnchorResp apiUsableAnchorResp) {

            }
        });
        refreshLayout = (RefreshLayout) mRootView.findViewById(R.id.refreshLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                keyWord = "";
                getUsableAnchor(keyWord);
            }
        });
        ImageView btn_close = mRootView.findViewById(R.id.btn_close);
        btn_close.setOnClickListener(this);
    }

    private void getUsableAnchor(String keyWord) {
        HttpApiPublicLive.getUsableAnchor(keyWord, new HttpApiCallBackArr<ApiUsableAnchorResp>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsableAnchorResp> retModel) {
                refreshLayout.finishRefresh();
                if (null != retModel) {
                    mList = retModel;
                    linkMicListAdapter.setList(retModel);
                }
            }
        });
    }

    private View initSearchView() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_live_pk_search, null);
        mEditText = (EditText) v.findViewById(R.id.edit);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    keyWord = v.getText().toString().trim();
                    if (!TextUtils.isEmpty(keyWord)) {
                        getUsableAnchor(keyWord);
                        SystemUtils.closeKeyboard(mEditText);
                        hideSearchDialog();
                    } else
                        ToastUtil.show(R.string.content_empty);
                    return true;
                }
                return false;
            }
        });
        v.findViewById(R.id.btn_back).setOnClickListener(this);
        return v;
    }

    private void showSearchDialog() {
        if (mSearchView == null) {
            mSearchView = initSearchView();
        }
        mLivePkSearchDialog = new LiveLinkMicPkSearchDialog(mRootView, mSearchView, this);
        mLivePkSearchDialog.show();
        if (mHandler == null) {
            mHandler = new Handler();
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SystemUtils.openKeyboard(mEditText);
            }
        }, 300);

    }

    @Override
    public void onSearchDialogDismiss() {
        if (mHandler != null) {
            mHandler.removeCallbacksAndMessages(null);
            mHandler = null;
        }
        if (mEditText != null) {
            mEditText.setText("");
        }
        if (linkMicListAdapter != null) {
            if (mList!=null){
                linkMicListAdapter.setList(mList);
            }
        }
    }

    /**
     * 隐藏搜索弹窗
     */
    private void hideSearchDialog() {
        if (mLivePkSearchDialog != null) {
            mLivePkSearchDialog.dismiss();
        }

        if (linkMicListAdapter != null) {
            if (mList!=null){
                linkMicListAdapter.setList(mList);
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_close) {
            dismiss();
        } else if (i == R.id.btn_search) {
            showSearchDialog();
        } else if (i == R.id.btn_back) {
            hideSearchDialog();
        }
    }
}
