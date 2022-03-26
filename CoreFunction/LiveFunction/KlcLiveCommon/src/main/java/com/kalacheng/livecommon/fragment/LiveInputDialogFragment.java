package com.kalacheng.livecommon.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.buslive.socketcontroller.IMApiLiveMsg;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.model.ApiBaseEntity;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.KeyBoardHeightUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.wengying666.imsocket.SocketClient;


/**
 * Created by cxf on 2017/8/21.
 * 直播间发言框
 */

public class LiveInputDialogFragment extends BaseDialogFragment implements View.OnClickListener, KeyBoardHeightUtil.KeyBoardHeightChangeListener {

    private EditText mInput;
    private CheckBox mCheckBox;
    private RadioButton mMyRadioButton;
    private String mHint1;
    private String mHint2;
    IMApiLiveMsg imApiLiveMsg;
    KeyBoardHeightUtil mKeyBoardHeightUtil;

    private long mRoomID;
    private SocketClient mSockt;
    private String name;
    private int liveType;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_chat_input;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    public void setSocket(SocketClient client, long mRoomID, String name, int gzdmPrivilege, int liveType) {
        this.name = name;
        this.mRoomID = mRoomID;
        this.liveType = liveType;
        mSockt = client;
        imApiLiveMsg = new IMApiLiveMsg();
        imApiLiveMsg.init(mSockt);
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.dp2px(50);
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (mKeyBoardHeightUtil == null) {
            mKeyBoardHeightUtil = new KeyBoardHeightUtil(mContext, ((FragmentActivity) mContext).findViewById(android.R.id.content), this);
            mKeyBoardHeightUtil.start();
        }
        mInput = (EditText) mRootView.findViewById(R.id.input);

        if (!TextUtils.isEmpty(name)) {
            mInput.setText("@" + name + ":");
            mInput.setSelection(mInput.getText().toString().length());//将光标移至文字末尾
        }
        mInput.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendMessage();
                    return true;
                }
                return false;
            }
        });
        mInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    mMyRadioButton.setChecked(false);
                } else {
                    mMyRadioButton.setChecked(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mInput.postDelayed(new Runnable() {
            @Override
            public void run() {
                //软键盘弹出
                SystemUtils.openKeyboard(mInput);
            }
        }, 200);
        mCheckBox = (CheckBox) mRootView.findViewById(R.id.danmu);
        mMyRadioButton = (RadioButton) mRootView.findViewById(R.id.btn_send);
        mMyRadioButton.setOnClickListener(this);
//        String danmuPrice = bundle.getString(Constants.LIVE_DANMU_PRICE);
//        String coinName = bundle.getString(Constants.COIN_NAME);00
        mHint2 = WordUtil.getString(R.string.live_say_something);
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.NOBLE_BARRAGE, 0) == 1) {
            mHint1 = "贵族免费发弹幕";
        } else {
            mHint1 = WordUtil.getString(R.string.live_open_alba) + String.valueOf(SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_BARRAGEFEE, 0)) + SpUtil.getInstance().getCoinUnit() + "/" + WordUtil.getString(R.string.live_tiao);
        }

        mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton button, boolean isChecked) {
                if (isChecked) {
                    mInput.setHint(mHint1);
                } else {
                    mInput.setHint(mHint2);
                }
            }
        });

    }

    @Override
    public void onClick(View v) {
        sendMessage();
    }

    private void sendMessage() {
        String content = mInput.getText().toString().trim();
        if (!TextUtils.isEmpty(content)) {
            if (mCheckBox.isChecked()) {
                HttpApiPublicLive.sendMsgRoom(LiveConstants.ANCHORID, content, liveType,  mRoomID,2, new HttpApiCallBack<ApiBaseEntity>() {
                    @Override
                    public void onHttpRet(int code, String msg, ApiBaseEntity retModel) {
                        if (code == 1 && null != retModel){
                            if (retModel.code != 1) {
                                ToastUtil.show(retModel.msg);
                            }
                        }else {
                            ToastUtil.show(msg);
                        }
                        dismiss();
                    }
                });
            } else {
                HttpApiPublicLive.sendMsgRoom(LiveConstants.ANCHORID, content, liveType, mRoomID, 1, new HttpApiCallBack<ApiBaseEntity>() {
                    @Override
                    public void onHttpRet(int code, String msg, ApiBaseEntity retModel) {
                        if (code == 1 && null != retModel){
                            if (retModel.code != 1) {
                                ToastUtil.show(retModel.msg);
                            }
                        }else {
                            ToastUtil.show(msg);
                        }
                        dismiss();
                    }
                });
            }
            mInput.setText("");
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        SystemUtils.closeKeyboard(mInput);
        super.onDismiss(dialog);
    }

    @Override
    public void onPause() {
        SystemUtils.closeKeyboard(mInput);
        super.onPause();
    }

    @Override
    public void onKeyBoardHeightChanged(int visibleHeight, int keyboardHeight) {
        if (mRootView != null) {
//            FrameLayout.LayoutParams params =  mRootView.getLayoutParams();
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, DpUtil.dp2px(50));
            params.setMargins(0, 0, 0, keyboardHeight);
            params.width = FrameLayout.LayoutParams.MATCH_PARENT;
            mRootView.setLayoutParams(params);
        }

    }

    @Override
    public boolean isSoftInputShowed() {
        if (mKeyBoardHeightUtil != null) {
            return mKeyBoardHeightUtil.isSoftInputShowed();
        }
        return false;
    }
}
