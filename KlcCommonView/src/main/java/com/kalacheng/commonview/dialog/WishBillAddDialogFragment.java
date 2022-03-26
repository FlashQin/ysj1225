package com.kalacheng.commonview.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAnchorWishList;
import com.kalacheng.libuser.model.ApiUsersLiveWish;
import com.kalacheng.commonview.adapter.WillBillGiftAdpater;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.jmessage.support.google.gson.Gson;

public class WishBillAddDialogFragment extends BaseDialogFragment {

    private WillBillGiftAdpater adapter;
    private RecyclerView recyclerView;
    //选中礼物列表
    private List<ApiUsersLiveWish> liveWishList = new ArrayList<>();

    public WishBillAddDialogFragment() {
    }

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_wish_bill_add;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    private long roomId;

    private long touid;

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        roomId = getArguments().getLong("RoomID");
        touid = getArguments().getLong("UserID");

        TextView tvAdd = mRootView.findViewById(R.id.tvAdd);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        linearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(linearLayoutManager);
//        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        adapter = new WillBillGiftAdpater(mContext);
//        adapter.setOnWishBillItemClickListener(this);
        recyclerView.setAdapter(adapter);

        adapter.setWillBillGiftItmeClickBack(new WillBillGiftAdpater.WillBillGiftItmeClickBack() {

            @Override
            public void onAdd(ApiUsersLiveWish gift) {
                if (gift.isCheck == 1) {
                    if (liveWishList.size() > 0) {
                        boolean addSuccess = false;
                        for (int i = 0; i < liveWishList.size(); i++) {
                            if (liveWishList.get(i).giftid == gift.giftid) {
                                liveWishList.get(i).num = gift.num;
                                addSuccess = true;
                                break;
                            }
                        }
                        if (!addSuccess) {
                            ApiUsersLiveWish liveWish = new ApiUsersLiveWish();
                            liveWish.giftid = (int) gift.giftid;
                            liveWish.num = gift.num;
                            liveWish.giftname = gift.giftname;
                            liveWishList.add(liveWish);
                        }
                    } else {
                        ApiUsersLiveWish liveWish = new ApiUsersLiveWish();
                        liveWish.giftid = (int) gift.giftid;
                        liveWish.num = gift.num;
                        liveWish.giftname = gift.giftname;
                        liveWishList.add(liveWish);
                    }
                }
            }

            @Override
            public void onReduce(ApiUsersLiveWish gift) {
                if (gift.isCheck == 1) {
                    for (int i = 0; i < liveWishList.size(); i++) {
                        if (liveWishList.get(i).giftid == gift.giftid) {
                            liveWishList.get(i).num = gift.num;
                        }
                    }
                } else {
                    for (int i = 0; i < liveWishList.size(); i++) {
                        if (liveWishList.get(i).giftid == gift.giftid) {
                            liveWishList.remove(i);
                        }
                    }
                }
            }

            @Override
            public void onCheck(ApiUsersLiveWish gift) {
                if (gift.isCheck == 1) {
                    ApiUsersLiveWish liveWish = new ApiUsersLiveWish();
                    liveWish.giftid = (int) gift.giftid;
                    liveWish.num = gift.num;
                    liveWish.giftname = gift.giftname;
                    liveWishList.add(liveWish);
                } else {
                    if (liveWishList.size() > 0) {
                        for (int i = 0; i < liveWishList.size(); i++) {
                            if (liveWishList.get(i).giftid == gift.giftid) {
                                liveWishList.remove(i);
                            }
                        }
                    }
                }
            }
        });
        tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < liveWishList.size(); i++) {
                    if (liveWishList.get(i).num == 0) {
                        ToastUtil.show(liveWishList.get(i).giftname + "数量为0");
                        return;
                    }
                }
                Collections.reverse(liveWishList);
                Log.e("cjh>>>", "" + new Gson().toJson(liveWishList));
                HttpApiAnchorWishList.setWish(liveWishList, roomId, touid, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show(WordUtil.getString(R.string.wish_generate_success));
                            dismiss();
                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_AddWishListSuccess, liveWishList);
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });
        ImageView btn_back = mRootView.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        loadData();
    }


    //获取心愿单礼物列表
    private void loadData() {
        HttpApiAnchorWishList.getWishGiftList(HttpClient.getUid(), new HttpApiCallBackArr<ApiUsersLiveWish>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiUsersLiveWish> retModel) {
                if (code == 1) {
                    if (retModel.size() > 0) {
                        for (int i = 0; i < retModel.size(); i++) {
                            if (retModel.get(i).isCheck == 1) {
                                liveWishList.add(retModel.get(i));
                            }
                        }
                    }
                    adapter.setGiftList(retModel);
                }
            }
        });
    }
}
