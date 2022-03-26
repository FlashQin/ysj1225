package com.kalacheng.one2onelive.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.busooolive.model.OOOInviteRet;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.EnableInvtHost;
import com.kalacheng.one2onelive.R;
import com.kalacheng.one2onelive.adpater.SvipAnchorListAdpater;
import com.kalacheng.util.utils.DpUtil;

import java.util.ArrayList;
import java.util.List;

public class SvipAnchorListDialogFragment extends BaseDialogFragment {

    private SvipAnchorListAdpater svipAnchorListAdpater;

    private EditText svip_search;
    private ImageView svip_screen;

    private int genderType = 0;
    private String SearchContent = "";


    private List<EnableInvtHost> mList = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.svip_anchor_list_dialog;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() * 2 / 3;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView Svip_Anchor_close = mRootView.findViewById(R.id.Svip_Anchor_close);
        Svip_Anchor_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        RecyclerView Svip_Anchor_List = mRootView.findViewById(R.id.Svip_Anchor_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Svip_Anchor_List.setLayoutManager(manager);

        svip_search = mRootView.findViewById(R.id.svip_search);
        svip_screen = mRootView.findViewById(R.id.svip_screen);
        svip_screen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SvipSearchDialog.getInstance().show(mContext);

                SvipSearchDialog.getInstance().setSvipSearchCallBack(new SvipSearchDialog.SvipSearchCallBack() {
                    @Override
                    public void onClick(int type) {
                        genderType = type;
                        svip_search.setText("");
                        getData();
                    }
                });
            }
        });

        svip_search.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    //点击搜索的时候隐藏软键盘
                    hideKeyboard(svip_search);
                    // 在这里写搜索的操作,一般都是网络请求数据
                    getData();
                    return true;
                }

                return false;
            }
        });


        svipAnchorListAdpater = new SvipAnchorListAdpater(mContext);
        Svip_Anchor_List.setAdapter(svipAnchorListAdpater);

        svipAnchorListAdpater.setSvipAnchorListCallBack(new SvipAnchorListAdpater.SvipAnchorListCallBack() {
            @Override
            public void onClick(int poistion) {
                //40010用户金币不足；40011有用户在1v1通话中 40012房间人数超限
                HttpApiOTMCall.invtJoinOneVsOne(mList.get(poistion).userid, 1, LiveConstants.mOOOSessionID, new HttpApiCallBack<OOOInviteRet>() {
                    @Override
                    public void onHttpRet(int code, String msg, OOOInviteRet retModel) {
                        if (code == 1) {
                            ToastUtil.show(msg);
                        } else if (code == 40010) {
                            ToastUtil.show(msg);
                        } else if (code == 40011) {
                            ToastUtil.show(msg);
                        } else if (code == 40012) {
                            //如果直播间人数大于最大邀请人数 提示不能邀请
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOLiveSVipPeopleSatisfy, null);
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });

                dismiss();
            }

            @Override
            public void onlookinfo(int poistion) {
                LiveConstants.TOUID = mList.get(poistion).userid;
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.Information, null);
            }
        });
        getData();
    }

    public void getData() {
        if (svip_search.getText().toString() == null) {
            SearchContent = "";
        } else {
            SearchContent = svip_search.getText().toString();

        }
        HttpApiOTMCall.invitingAnchor(genderType, SearchContent, new HttpApiCallBackArr<EnableInvtHost>() {
            @Override
            public void onHttpRet(int code, String msg, List<EnableInvtHost> retModel) {
                if (code == 1) {
                    mList.clear();
                    mList = retModel;
                    svipAnchorListAdpater.setData(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

    }

    /**
     * 隐藏软键盘
     *
     * @param :上下文
     * @param view :一般为EditText
     */
    public void hideKeyboard(View view) {
        InputMethodManager manager = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        manager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

}
