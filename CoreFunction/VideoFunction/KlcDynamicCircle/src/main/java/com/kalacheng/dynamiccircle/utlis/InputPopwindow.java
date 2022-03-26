package com.kalacheng.dynamiccircle.utlis;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ImageSpan;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.busshortvideo.httpApi.HttpApiAppShortVideo;
import com.kalacheng.dynamiccircle.R;
import com.kalacheng.dynamiccircle.adpater.ImChatFacePagerAdapter;
import com.kalacheng.dynamiccircle.listener.OnFaceClickListener;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiUsersVideoComments;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.ToastUtil;

public class InputPopwindow extends PopupWindow implements OnFaceClickListener {
    private Context mContext;
    private Handler mHandler = new Handler();
    private int type;
    private long id;

    private RadioGroup radio_group;
    private ViewPager viewPager;
    private EditText editText;
    private LinearLayout layoutFace;
    private InputSuccessCallBack callBack;
    private boolean isVideo;

    public InputPopwindow(Context mContext) {
        this.mContext = mContext;
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vPopupWindow = inflater.inflate(R.layout.dialog_video_input, null, true);//引入弹窗布局
        setContentView(vPopupWindow);
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        setBackgroundDrawable(new ColorDrawable());
        setOutsideTouchable(true);
        setFocusable(true);

        editText = vPopupWindow.findViewById(R.id.input);
        radio_group = vPopupWindow.findViewById(R.id.radio_group);
        viewPager = vPopupWindow.findViewById(R.id.viewPager);
        layoutFace = vPopupWindow.findViewById(R.id.layoutFace);
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEND) {
                    sendComment(editText, type, id);
                    return true;
                }
                return false;
            }
        });
        editText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    if (layoutFace.getVisibility() == View.VISIBLE) {
                        layoutFace.setVisibility(View.GONE);
                    }
                }
            }
        });
        vPopupWindow.findViewById(R.id.ivCheck).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (layoutFace.getVisibility() == View.VISIBLE) {
                    layoutFace.setVisibility(View.GONE);
                    if (mHandler != null) {
                        mHandler.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                SystemUtils.openKeyboard(editText);
                            }
                        }, 200);
                    }
                } else {
                    layoutFace.setVisibility(View.VISIBLE);
                    SystemUtils.closeKeyboard(editText);
                }
            }
        });

        //表情发送
        vPopupWindow.findViewById(R.id.btn_send).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendComment(editText, type, id);
            }
        });

        initFace();
    }

    @Override
    public void dismiss() {
        SystemUtils.closeKeyboard(editText);
        super.dismiss();
    }

    public void showShadow(boolean isVideo, View input, int type, long id, boolean expression, String toUserName) {
        showShadow(isVideo, input, type, id, expression, true, toUserName);
    }

    //Expression ture表情 false 文字
    public void showShadow(boolean isVideo, View input, int type, long id, boolean expression, boolean isShowFace, String toUserName) {
        this.isVideo = isVideo;
        this.type = type;
        this.id = id;

        if (type == 2 && !TextUtils.isEmpty(toUserName)) {
            editText.setHint("回复" + toUserName + "：");
        }

        if (expression) {
            layoutFace.setVisibility(isShowFace ? View.VISIBLE : View.GONE);
            SystemUtils.closeKeyboard(editText);
        } else {
            layoutFace.setVisibility(View.GONE);
            if (mHandler != null) {
                mHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        SystemUtils.openKeyboard(editText);
                    }
                }, 200);
            }
        }

        setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);//防止软件盘遮挡 PopupWindow
        //相对于父控件的位置（例如正中央Gravity.CENTER，下方Gravity.BOTTOM等），可以设置偏移或无偏移
        showAtLocation(input, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 发表评论
     */
    public void sendComment(final EditText mInput, int type, long ID) {

        String content = mInput.getText().toString().trim();
        if (TextUtils.isEmpty(content)) {
            ToastUtil.show(R.string.content_empty);
            return;
        }

        if (isVideo) {
            HttpApiAppShortVideo.shortVideoComment(type, mInput.getText().toString(), ID, new HttpApiCallBack<ApiUsersVideoComments>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUsersVideoComments retModel) {
                    if (code == 1) {
                        mInput.setText("");
                        SystemUtils.closeKeyboard(editText);
                        dismiss();
                        ToastUtil.show(msg);
                        callBack.Success(retModel);
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        } else {
            HttpApiAppVideo.addComment(type, mInput.getText().toString(), ID, new HttpApiCallBack<ApiUsersVideoComments>() {
                @Override
                public void onHttpRet(int code, String msg, ApiUsersVideoComments retModel) {
                    if (code == 1) {
                        mInput.setText("");
                        SystemUtils.closeKeyboard(editText);
                        dismiss();
                        ToastUtil.show(msg);
                        callBack.Success(retModel);
                    } else {
                        ToastUtil.show(msg);
                    }
                }
            });
        }
    }

    /**
     * 初始化表情键盘
     */
    private void initFace() {
        viewPager.setOffscreenPageLimit(10);
        ImChatFacePagerAdapter adapter = new ImChatFacePagerAdapter(mContext, this);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                ((RadioButton) radio_group.getChildAt(position)).setChecked(true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        for (int i = 0, pageCount = adapter.getCount(); i < pageCount; i++) {
            RadioButton radioButton = (RadioButton) LayoutInflater.from(mContext).inflate(R.layout.view_chat_indicator, radio_group, false);
            radioButton.setId(i + 10000);
            if (i == 0) {
                radioButton.setChecked(true);
            }
            radio_group.addView(radioButton);
        }
    }

    @Override
    public void onFaceClick(String str, int faceImageRes) {
        if (editText != null) {
            Editable editable = editText.getText();
            editable.insert(editText.getSelectionStart(), getFaceImageSpan(str, faceImageRes));
        }
    }

    @Override
    public void onFaceDeleteClick() {
        if (editText != null) {
            int selection = editText.getSelectionStart();
            String text = editText.getText().toString();
            if (selection > 0) {
                String text2 = text.substring(selection - 1, selection);
                if ("]".equals(text2)) {
                    int start = text.lastIndexOf("[", selection);
                    if (start >= 0) {
                        editText.getText().delete(start, selection);
                    } else {
                        editText.getText().delete(selection - 1, selection);
                    }
                } else {
                    editText.getText().delete(selection - 1, selection);
                }
            }
        }
    }

    /**
     * 聊天表情
     */
    private int FACE_WIDTH;
    private int VIDEO_FACE_WIDTH;

    public CharSequence getFaceImageSpan(String content, int imgRes) {
        FACE_WIDTH = DpUtil.dp2px(20);
        VIDEO_FACE_WIDTH = DpUtil.dp2px(16);
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        Drawable faceDrawable = ContextCompat.getDrawable(BaseApplication.getInstance(), imgRes);
        faceDrawable.setBounds(0, 0, FACE_WIDTH, FACE_WIDTH);
        ImageSpan imageSpan = new ImageSpan(faceDrawable, ImageSpan.ALIGN_BOTTOM);
        builder.setSpan(imageSpan, 0, content.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        return builder;
    }

    //成功回调
    public interface InputSuccessCallBack {
        public void Success(ApiUsersVideoComments comments);
    }

    public void setInputSuccessCallBack(InputSuccessCallBack back) {
        this.callBack = back;
    }
}
