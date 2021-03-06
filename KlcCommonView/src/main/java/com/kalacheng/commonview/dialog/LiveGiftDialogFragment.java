package com.kalacheng.commonview.dialog;

import android.annotation.SuppressLint;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.donkingliang.labels.LabelsView;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.listener.OnItemClickListener;
import com.kalacheng.busnobility.httpApi.HttpApiNobLiveGift;
import com.kalacheng.busnobility.model.ApiNobLiveGift;
import com.kalacheng.busnobility.socketcontroller.IMApiLiveGift;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.LiveGiftCountAdapter;
import com.kalacheng.commonview.adapter.LiveGiftPagerAdapter;
import com.kalacheng.commonview.utils.GetIntoRoomVerificationUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.NobLiveGift;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.SendGiftPeopleBean;
import com.makeramen.roundedimageview.RoundedImageView;
import com.wengying666.imsocket.IMUtil;

import java.util.List;


/**
 * Created by cxf on 2018/10/12.
 * ??????????????????
 */

public class LiveGiftDialogFragment extends BaseDialogFragment implements View.OnClickListener, OnItemClickListener<String> {

    private TextView tvCoin;
    private RelativeLayout Recharge_Re;
    private ViewPager mViewPager;
    private RadioGroup mRadioGroup;
    private View mLoading;
    private View mBtnSend;
    private View mBtnSendGroup;
    private View mBtnSendLian;
    private TextView mBtnChooseCount;
    private PopupWindow mGiftCountPopupWindow;//?????????????????????popupWindow
    private LiveGiftPagerAdapter mLiveGiftPagerAdapter;
    private NobLiveGift mLiveGiftBean;
    private static final String DEFAULT_COUNT = "1";
    private String mCount = DEFAULT_COUNT;
    private String mStream;
    private Handler mHandler;
    private int mLianCountDownCount;//????????????????????????
    private TextView mLianText;
    private static final int WHAT_LIAN = 100;
    private boolean mShowLianBtn;//???????????????????????????
    private IMApiLiveGift imApiLiveGift;
    private int coin;
    private int backID = -1;
    private int mLiveType;
    private String mShowid;
    private long mRoomid;
    private long LiveRoomAnchorID;

    //0 ?????? 1????????????
    private int GiftType = 0;
    //???????????????id
    private long sendID;
    //?????????id
    private long shortVideoId;

    //???????????????
    private SendGiftPeopleBean sendGiftPeopleBean;

    private TextView send_gift_username;
    private RoundedImageView send_gift_headimage;

    private LabelsView labels;
    private TextView send_gift_mybg;
    private List<SendGiftPeopleBean> mSendList;
    private boolean mHideRoleTip;//????????????????????????

    private VoiceGifeChoiseDialog choiseDialog;
    private ImageView send_gift_upimage;
    private LinearLayout send_gift_upRe;

    private SendGiftSuccessCallBack successCallBack;

    //??????????????????
    private boolean isSend = false;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_gift;
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
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        isSend = false;

        imApiLiveGift = new IMApiLiveGift();
        imApiLiveGift.init(IMUtil.getClient());
        if (null != getArguments()) {
//            mLiveType = getArguments().getInt(ARouterValueNameConfig.Livetype);
//            mShowid = getArguments().getString(ARouterValueNameConfig.ShowID);
            mSendList = getArguments().getParcelableArrayList("SendList");
            mHideRoleTip = getArguments().getBoolean("hideRoleTip", false);
//            mRoomid = getArguments().getLong(ARouterValueNameConfig.RoomID);
//            shortVideoId = getArguments().getLong(ARouterValueNameConfig.ShortVideoId);
//            LiveRoomAnchorID = getArguments().getLong(ARouterValueNameConfig.LiveRoomAnchorID);
        }
        tvCoin = (TextView) mRootView.findViewById(R.id.coin);
        Recharge_Re = mRootView.findViewById(R.id.Recharge_Re);
        mLoading = mRootView.findViewById(R.id.loading);
        mBtnSend = mRootView.findViewById(R.id.btn_send);
        mBtnSendGroup = mRootView.findViewById(R.id.btn_send_group);
        mBtnSendLian = mRootView.findViewById(R.id.btn_send_lian);
        mBtnChooseCount = (TextView) mRootView.findViewById(R.id.btn_choose);
        mLianText = (TextView) mRootView.findViewById(R.id.lian_text);
        mViewPager = (ViewPager) mRootView.findViewById(R.id.viewPager);

        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (mRadioGroup != null && mRadioGroup.getChildCount() > position) {
                    ((RadioButton) mRadioGroup.getChildAt(position)).setChecked(true);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mRadioGroup = (RadioGroup) mRootView.findViewById(R.id.radio_group);


        mBtnSend.setOnClickListener(this);
        mBtnSendLian.setOnClickListener(this);
        mBtnChooseCount.setOnClickListener(this);
        Recharge_Re.setOnClickListener(this);
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                mLianCountDownCount--;
                if (mLianCountDownCount == 0) {
                    hideLianBtn();
                } else {
                    if (mLianText != null) {
                        mLianText.setText(mLianCountDownCount + "s");
                        if (mHandler != null) {
                            mHandler.sendEmptyMessageDelayed(WHAT_LIAN, 1000);
                        }
                    }
                }
            }
        };
        loadData(-1);

        send_gift_username = mRootView.findViewById(R.id.send_gift_username);

        send_gift_headimage = mRootView.findViewById(R.id.send_gift_headimage);
        send_gift_username.setText(mSendList.get(0).name);
        ImageLoader.display(mSendList.get(0).headimage, send_gift_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        sendID = mSendList.get(0).uid;
        sendGiftPeopleBean = mSendList.get(0);

        mLiveType = mSendList.get(0).liveType;
        mShowid = mSendList.get(0).showid;
        mRoomid = mSendList.get(0).roomID;
        shortVideoId = mSendList.get(0).shortVideoId;
        LiveRoomAnchorID = mSendList.get(0).anchorID;

        send_gift_upimage = mRootView.findViewById(R.id.send_gift_upimage);
        send_gift_upRe = mRootView.findViewById(R.id.send_gift_upRe);
        RelativeLayout rela_tab = mRootView.findViewById(R.id.rela_tab);

        if (mSendList.size() > 1) {
            send_gift_upimage.setVisibility(View.VISIBLE);
            send_gift_upRe.setOnClickListener(this);
        } else {
            send_gift_upimage.setVisibility(View.GONE);
        }

        labels = mRootView.findViewById(R.id.labels);


        //????????????
        send_gift_mybg = mRootView.findViewById(R.id.send_gift_mybg);
        send_gift_mybg.setOnClickListener(this);


    }

    private List<ApiNobLiveGift> mList;

    //??????????????????
    private void loadData(final int type) {
        if (mList == null || mList.size() <= 0) {
            HttpApiNobLiveGift.getGiftList(type, new HttpApiCallBackArr<ApiNobLiveGift>() {
                @Override
                public void onHttpRet(int code, String msg, List<ApiNobLiveGift> retModel) {
                    if (retModel != null) {
                        mList = retModel;
                        setGiftType(type);
                        mLoading.setVisibility(View.GONE);
                        coin = (int) retModel.get(0).coin;
                        tvCoin.setText(DecimalFormatUtils.isIntegerDouble(coin) + "");
                    }
                }
            });
        } else {
            setGiftType(type);
            mLoading.setVisibility(View.GONE);
        }
    }

    private void showGiftList(List<NobLiveGift> list) {
        mRadioGroup.removeAllViews();
        //????????????
        mLiveGiftBean = list.get(0);
        //?????????????????????
        for (NobLiveGift gift : list) {
            gift.checked = 0;
        }
        list.get(0).checked = 1;
        mLiveGiftPagerAdapter = new LiveGiftPagerAdapter(mContext, list);
        mViewPager.setAdapter(mLiveGiftPagerAdapter);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        for (int i = 0, size = mLiveGiftPagerAdapter.getCount(); i < size; i++) {
            RadioButton radioButton = (RadioButton) inflater.inflate(R.layout.view_gift_indicator, mRadioGroup, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            mRadioGroup.addView(radioButton);
        }

        mLiveGiftPagerAdapter.setPageGiftClick(new LiveGiftPagerAdapter.PageGiftClick() {
            @Override
            public void onPageGiftClick(NobLiveGift item) {
                if (GiftType == 1) {
                    backID = item.backid;
                } else {
                    backID = -1;
                }
                mLiveGiftBean = item;
                if (item.type == 0)
                    mBtnChooseCount.setVisibility(View.VISIBLE);
                else
                    mBtnChooseCount.setVisibility(View.GONE);
                hideLianBtn();
                mBtnSend.setEnabled(true);
                if (!DEFAULT_COUNT.equals(mCount)) {
                    mCount = DEFAULT_COUNT;
                    mBtnChooseCount.setText(DEFAULT_COUNT);
                }
            }
        });
    }


    @Override
    public void onDestroy() {

        if (mGiftCountPopupWindow != null) {
            mGiftCountPopupWindow.dismiss();
        }
        if (mLiveGiftPagerAdapter != null) {
            mLiveGiftPagerAdapter.release();
        }
        if (mHandler != null) {
            mHandler.removeMessages(1);
        }

        if (choiseDialog != null) {
            choiseDialog.setDismiss();
        }
        super.onDestroy();
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.btn_send || i == R.id.btn_send_lian) {
            if (!isSend) {
                isSend = true;
                sendGift();
            }
        } else if (i == R.id.btn_choose) {
            showGiftCount();
        } else if (i == R.id.Recharge_Re) {
            forwardMyCoin();
        } else if (i == R.id.send_gift_mybg) {//????????????
            hideLianBtn();
            labels.clearAllSelect();
            if (GiftType == 0) {
                labels.removeAllViews();
//                labels.setVisibility(View.GONE);

                GiftType = 1;
                send_gift_mybg.setText("??????");
                getMyPackge();
            } else {
//                labels.setVisibility(View.VISIBLE);
                backID = -1;
                GiftType = 0;
                send_gift_mybg.setText("????????????");
                loadData(-1);
            }

        } else if (i == R.id.send_gift_upRe) {//???????????????
            choiseDialog = new VoiceGifeChoiseDialog();
            choiseDialog.ShowDialog(mContext, mSendList, mHideRoleTip);
            choiseDialog.setSendGiftCallBack(new VoiceGifeChoiseDialog.SendGiftCallBack() {
                @Override
                public void onClick(SendGiftPeopleBean bean) {
                    getGiftChoisePeople(bean);
                }
            });
        }
    }

    //?????????????????????
    public void getGiftChoisePeople(SendGiftPeopleBean data) {
        send_gift_username.setText(data.name);
        ImageLoader.display(data.headimage, send_gift_headimage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        sendID = data.uid;
        sendGiftPeopleBean = data;

        mLiveType = data.liveType;
        mShowid = data.showid;
        mRoomid = data.roomID;
        shortVideoId = data.shortVideoId;
        LiveRoomAnchorID = data.anchorID;
    }

    /**
     * ?????????????????????
     */
    private void forwardMyCoin() {
        ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
//        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveRecharge,null);

    }


    private List<NobLiveGift> mPackageGiftList;

    //??????????????????
    public void getMyPackge() {
        if (mPackageGiftList == null || mPackageGiftList.size() <= 0) {
            HttpApiNobLiveGift.getGiftList(4, new HttpApiCallBackArr<ApiNobLiveGift>() {
                @Override
                public void onHttpRet(int code, String msg, List<ApiNobLiveGift> retModel) {
                    if (code == 1 && retModel != null && retModel.size() > 0) {
                        if (mLiveGiftPagerAdapter != null) {
                            mLiveGiftPagerAdapter.release();
                            mLiveGiftBean = null;
                            mLiveGiftPagerAdapter = null;
                        }
                        mPackageGiftList = retModel.get(0).giftList;

                        mLiveGiftBean = mPackageGiftList.get(0);
                        backID = mPackageGiftList.get(0).backid;
                        showGiftList(mPackageGiftList);
                    } else {
                        mLiveGiftBean = null;
                        mPackageGiftList = null;
                        mRadioGroup.removeAllViews();
                        mViewPager.removeAllViews();
                    }
                }
            });
        } else {
            mLiveGiftBean = mPackageGiftList.get(0);
            backID = mPackageGiftList.get(0).backid;
            showGiftList(mPackageGiftList);
        }
    }

    /**
     * ??????????????????
     */
    @SuppressLint("WrongConstant")
    private void showGiftCount() {
        View v = LayoutInflater.from(mContext).inflate(R.layout.view_gift_count, null);
        RecyclerView recyclerView = (RecyclerView) v.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, true));
        LiveGiftCountAdapter adapter = new LiveGiftCountAdapter(mContext);
        adapter.setOnItemClickListener(this);
        recyclerView.setAdapter(adapter);
        mGiftCountPopupWindow = new PopupWindow(v, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mGiftCountPopupWindow.setBackgroundDrawable(new ColorDrawable());
        mGiftCountPopupWindow.setOutsideTouchable(true);
        mGiftCountPopupWindow.showAtLocation(mBtnChooseCount, Gravity.BOTTOM | Gravity.RIGHT, DpUtil.dp2px(70), DpUtil.dp2px(40));
    }

    /**
     * ??????????????????
     */
    private void hideGiftCount() {
        if (mGiftCountPopupWindow != null) {
            mGiftCountPopupWindow.dismiss();
        }
    }


    @Override
    public void onItemClick(int position, String bean) {
        mCount = bean;
        mBtnChooseCount.setText(bean);
        hideGiftCount();
    }

    /**
     * ????????????
     */
    public void sendGift() {
        if (null != mLiveGiftBean && mLiveGiftBean.checked == 1) {
            if (mLiveType == 0) {
                mLiveType = 1;
            }
            HttpApiNobLiveGift.giftSender(sendID, backID, (int) mLiveGiftBean.id, Integer.parseInt(mCount), mRoomid, shortVideoId, mShowid, mLiveType, LiveRoomAnchorID, new HttpApiCallBack<ApiGiftSender>() {
                @Override
                public void onHttpRet(int code, String msg, ApiGiftSender retModel) {
                    isSend = false;
                    if (code == 1) {
                        if (retModel.code == 1) {
                            tvCoin.setText(DecimalFormatUtils.isIntegerDouble(retModel.userCoin) + "");

                            showLianBtn();

                            //????????????????????????
                            if (successCallBack != null) {
                                successCallBack.onSuccess(mLiveGiftBean, Integer.parseInt(mCount), sendGiftPeopleBean);
                            }

                            if (backID != -1) {
                                labels.removeAllViews();
                                GiftType = 1;
                                send_gift_mybg.setText("??????");

                                if (mPackageGiftList != null && mPackageGiftList.size() > 0) {
                                    for (NobLiveGift gift : mPackageGiftList) {
                                        if (gift.backid == backID && gift.id == mLiveGiftBean.id) {
                                            if (gift.number > Integer.parseInt(mCount)) {
                                                gift.number = gift.number - Integer.parseInt(mCount);
                                                if (mLiveGiftPagerAdapter != null) {
                                                    mLiveGiftPagerAdapter.refreshNumber();
                                                }
                                            } else {
                                                mPackageGiftList.clear();
                                                getMyPackge();
                                            }
                                            break;
                                        }
                                    }
                                } else {
                                    getMyPackge();
                                }
                            }
                        } else if (retModel.code == 3) {
                            GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(mContext, 1);

//                            LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_NoMoney, (int) (mLiveGiftBean.needcoin * Integer.parseInt(mCount)));
                        } else if (retModel.code == 4) {
                            DialogUtil.showTipsButtonDialog(mContext, retModel.msg, "????????????", new DialogUtil.CurrencyCallback() {
                                @Override
                                public void onConfirmClick() {
                                    ARouter.getInstance().build(ARouterPath.MyCoinActivity).navigation();
                                }
                            });
                        } else {
                            ToastUtil.show(retModel.msg);
                        }

                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            isSend = false;
            ToastUtil.show("???????????????");
        }

    }

    /**
     * ??????????????????
     */
    private void hideLianBtn() {
        mShowLianBtn = false;
        if (mHandler != null) {
            mHandler.removeMessages(WHAT_LIAN);
        }
        if (mBtnSendLian != null && mBtnSendLian.getVisibility() == View.VISIBLE) {
            mBtnSendLian.setVisibility(View.INVISIBLE);
        }
        if (mBtnSendGroup != null && mBtnSendGroup.getVisibility() != View.VISIBLE) {
            mBtnSendGroup.setVisibility(View.VISIBLE);
        }
    }

    /**
     * ??????????????????
     */
    private void showLianBtn() {
        if (mLianText != null) {
            mLianText.setText("5s");
        }
        mLianCountDownCount = 5;
        if (mHandler != null) {
            mHandler.removeMessages(WHAT_LIAN);
            mHandler.sendEmptyMessageDelayed(WHAT_LIAN, 1000);
        }
        if (mShowLianBtn) {
            return;
        }
        mShowLianBtn = true;
        if (mBtnSendGroup != null && mBtnSendGroup.getVisibility() == View.VISIBLE) {
            mBtnSendGroup.setVisibility(View.INVISIBLE);
        }
        if (mBtnSendLian != null && mBtnSendLian.getVisibility() != View.VISIBLE) {
            mBtnSendLian.setVisibility(View.VISIBLE);
        }
    }

    public void setGiftType(final int type) {
//        ArrayList<String> label = new ArrayList<>();
//        label.add("????????????");
//        label.add("???????????????");
//        label.add("????????????");
//        label.add("????????????");
//        labels.setLabels(label); //?????????????????????????????????????????????
        labels.setLabels(mList, new LabelsView.LabelTextProvider<ApiNobLiveGift>() {
            @Override
            public CharSequence getLabelText(TextView label, int position, ApiNobLiveGift data) {
                //??????data???position??????label????????????????????????
                return data.giftTypeName;
            }
        });
//?????????????????????
        if (backID == -1) {
            labels.setSelects(0);
        } else {
            labels.clearAllSelect();
        }
        labels.setOnLabelClickListener(new LabelsView.OnLabelClickListener() {
            @Override
            public void onLabelClick(TextView label, Object data, int position) {
                //label????????????????????????data??????????????????????????????position?????????????????????
                backID = -1;
                if (mLiveGiftPagerAdapter != null) {
                    mLiveGiftPagerAdapter.release();
                    mLiveGiftBean = null;
                    mLiveGiftPagerAdapter = null;
                }

                showGiftList(mList.get(position).giftList);
//                loadData(position);
            }
        });
        showGiftList(mList.get(0).giftList);
    }

    public void setSendGiftSuccessCallBack(SendGiftSuccessCallBack back) {
        this.successCallBack = back;
    }

    //????????????????????????
    public interface SendGiftSuccessCallBack {
        public void onSuccess(NobLiveGift nobLiveGift, int giftNum, SendGiftPeopleBean bean);
    }
}
