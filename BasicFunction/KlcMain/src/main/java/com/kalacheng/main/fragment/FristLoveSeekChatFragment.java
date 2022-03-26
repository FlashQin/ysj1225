package com.kalacheng.main.fragment;

import android.Manifest;
import android.app.Dialog;
import android.os.Build;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOCall;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AdminRushToTalkConfig;
import com.kalacheng.libuser.model.ApiPushChatData;
import com.kalacheng.main.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ProcessResultUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

// 求聊
public class FristLoveSeekChatFragment extends BaseFragment {

    List<AdminRushToTalkConfig> coinList;
    int coinId = 0;
    private List<String> picList = new ArrayList();
    TextView settingPriceTv;
    RadioGroup radio;

    ProcessResultUtil mProcessResultUtil;
    Dialog dialog;

    public FristLoveSeekChatFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_fristloveseek_chat;
    }

    @Override
    protected void initView() {
        settingPriceTv = mParentView.findViewById(R.id.settingPriceTv);
        radio = mParentView.findViewById(R.id.radio);
        dialog = DialogUtil.loadingDialog(getContext());
        mProcessResultUtil = new ProcessResultUtil(getActivity());

        if (null != SpUtil.getInstance().getCoinUnit()) {
            settingPriceTv.setText("100 " + SpUtil.getInstance().getCoinUnit() + "/ 分钟");
        }

        radio.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                getData(checkedId == R.id.videoRb ? 1 : 2);
            }
        });

        settingPriceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtil.showSingleTextPickerDialog(getContext(), picList.toArray(new String[]{}), new DialogUtil.SingleTextCallback() {
                    @Override
                    public void onConfirmClick(int id, String date) {
                        coinId = id;
                        settingPriceTv.setText(date);
                    }
                });
            }
        });
        mParentView.findViewById(R.id.seekchatBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                addPushChat();
            }
        });

    }

    @Override
    protected void initData() {
        getData(1);
    }

    private void getData(int type) {
        HttpApiOOOCall.getPushChatData(type, new HttpApiCallBack<ApiPushChatData>() {
            @Override
            public void onHttpRet(int code, String msg, ApiPushChatData retModel) {
                if (code == 1 && retModel != null) {
                    coinList = retModel.coinList;
                    coinId = 0;
                    picList.clear();
                    for (AdminRushToTalkConfig config : coinList) {
                        picList.add(config.telephoneMoney + SpUtil.getInstance().getCoinUnit() + "/分钟");
                    }
                    settingPriceTv.setText(coinList.get(0).telephoneMoney + SpUtil.getInstance().getCoinUnit() + "/分钟");
                }
            }
        });
    }


    private void addPushChat() {
        if (null == coinList) {
            return;
        }
        dialog.show();
        HttpApiOOOCall.addPushChat(radio.getCheckedRadioButtonId() == R.id.videoRb ? 1 : 2, (int) coinList.get(coinId).id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, final HttpNone retModel) {
                dialog.dismiss();
                if (code == 1) {
                    final ApiUserInfo info = new ApiUserInfo();
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        mProcessResultUtil.requestPermissions(new String[]{
                                Manifest.permission.READ_EXTERNAL_STORAGE,
                                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.CAMERA,
                                Manifest.permission.RECORD_AUDIO
                        }, new Runnable() {
                            @Override
                            public void run() {
                                if (retModel != null && retModel.no_use != null) {
                                    LiveConstants.mOOOSessionID = Long.parseLong(retModel.no_use);
                                    OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(getContext(), radio.getCheckedRadioButtonId() == R.id.videoRb ? 1 : 0, info, 2);
                                }
                            }
                        });
                    }
                } else if (code == 2) {
                    ToastUtil.show("余额不足");
                } else {
                    ToastUtil.show(msg);
                }
            }
        });


    }

}
