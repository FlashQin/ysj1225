package com.kalacheng.livecommon.fragment;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.baseLive.httpApi.HttpApiPublicLive;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.livecommon.R;

public class ModifyRoomNoticeDialogFragment extends BaseDialogFragment {
    private AppJoinRoomVO apiJoinRoom;
    public EditText Live_NoticeContent;
    private TextView Live_NoticeNumber;

    @Override
    protected int getLayoutId() {
        return R.layout.modify_room_notice;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiJoinRoom = getArguments().getParcelable("ApiJoinRoom");

        Live_NoticeContent = mRootView.findViewById(R.id.Live_NoticeContent);
        Live_NoticeNumber = mRootView.findViewById(R.id.Live_NoticeNumber);

        TextView Live_NoticeSubmit = mRootView.findViewById(R.id.Live_NoticeSubmit);
        Live_NoticeSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HttpApiPublicLive.updateLiveNotice(apiJoinRoom.anchorId, Live_NoticeContent.getText().toString(), apiJoinRoom.liveType, apiJoinRoom.roomId, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            ToastUtil.show(msg);
                            dismiss();
                        } else {
                            ToastUtil.show(msg);
                        }
                    }
                });
            }
        });

        Live_NoticeContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Live_NoticeNumber.setText(s.toString().length() + "/1000");
            }
        });
        lookNotice();

        ImageView Live_NoticeClose = mRootView.findViewById(R.id.Live_NoticeClose);
        Live_NoticeClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    public void lookNotice() {
        HttpApiPublicLive.getLiveNotice(apiJoinRoom.anchorId, apiJoinRoom.liveType, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    Live_NoticeContent.setText(retModel.no_use);
                    Live_NoticeNumber.setText(retModel.no_use.length() + "/1000");
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }
}
