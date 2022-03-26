package com.kalacheng.util.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Selection;
import android.text.Spannable;
import android.text.TextUtils;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.kalacheng.util.R;


/**
 * Created by hgy on 2019/9/18.
 */

public class DialogUtil {
    public static final int INPUT_TYPE_TEXT = 0;
    public static final int INPUT_TYPE_NUMBER = 1;
    public static final int INPUT_TYPE_NUMBER_PASSWORD = 2;
    public static final int INPUT_TYPE_TEXT_PASSWORD = 3;

    public static Dialog getBaseDialog(Context context, int style, int layout, boolean cancelable, boolean canceledOnTouchOutside) {
        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.show();
        return dialog;
    }

    // 系统弹窗
    public static Dialog getBaseSystemDialog(Context context, int style, int layout, boolean cancelable, boolean canceledOnTouchOutside) {
        Dialog dialog = new Dialog(context, style);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY);
        } else {
            dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(true);
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.show();
        return dialog;
    }

    public static Dialog show2BtnDialog(Context context, int style, int layout, boolean cancelable, boolean canceledOnTouchOutside, final SimpleCallback callback) {
        final Dialog dialog = new Dialog(context, style);
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                        callback.onCancelClick();
                    }
                }
        );
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
                callback.onConfirmClick();
            }
        });

        if (!((Activity) context).isFinishing()) {
            dialog.show();
        }
        return dialog;
    }

    /**
     * 用于网络请求等耗时操作的LoadingDialog
     */
    public static Dialog loadingDialog(Context context, int style, int layout, boolean cancelable, boolean canceledOnTouchOutside, String text) {
        Dialog dialog = new Dialog(context, style);
        dialog.setContentView(layout);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(canceledOnTouchOutside);
        if (!TextUtils.isEmpty(text)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.text);
            if (titleView != null) {
                titleView.setText(text);
            }
        }
        return dialog;
    }


    public static Dialog loadingDialog(Context context) {
        return loadingDialog(context, R.style.dialog2, R.layout.dialog_loading, false, false, "");
    }

    public static void showSimpleTipDialog(Context context, String content) {
        showSimpleTipDialog(context, null, content);
    }

    public static void showSimpleTipDialog(Context context, String title, String content) {
        final Dialog dialog = new Dialog(context, R.style.dialog2);
        dialog.setContentView(R.layout.dialog_simple_tip);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        if (!TextUtils.isEmpty(title)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            titleView.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            TextView contentTextView = (TextView) dialog.findViewById(R.id.content);
            contentTextView.setText(content);
        }
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showSimpleTipDialog(Context context, String title, String content, boolean cancelable, final SimpleCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog2);
        dialog.setContentView(R.layout.dialog_simple_tip);
        dialog.setCancelable(cancelable);
        dialog.setCanceledOnTouchOutside(cancelable);
        if (!TextUtils.isEmpty(title)) {
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            titleView.setText(title);
        }
        if (!TextUtils.isEmpty(content)) {
            TextView contentTextView = (TextView) dialog.findViewById(R.id.content);
            contentTextView.setText(content);
        }
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirmClick();
            }
        });
        dialog.show();
    }

    public static void showSimpleDialog(Context context, String title, String content, boolean cancelable, SimpleCallback callback) {
        new Builder(context)
                .setTitle(title)
                .setContent(content)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleDialog(Context context, String title, String content, String confirmStr, boolean cancelable, SimpleCallback callback) {
        new Builder(context)
                .setTitle(title)
                .setContent(content)
                .setConfrimString(confirmStr)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }

    // 设置背景
    public static void showSimpleDialog(Context context, String title, String content, String confirmStr, boolean bg, boolean cancelable, SimpleCallback callback) {
        new Builder(context)
                .setTitle(title)
                .setContent(content)
                .setConfrimString(confirmStr)
                .setBackgroundDimEnabled(bg)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }

    //字体颜色
    public static void showSimpleDialog(Context context, String title, String content, String confirmStr, int color, boolean cancelable, SimpleCallback callback) {
        new Builder(context)
                .setTitle(title)
                .setContent(content)
                .setConfrimString(confirmStr)
                .setCancelTextColor(color)
                .setCancelable(cancelable)
                .setClickCallback(callback)
                .build()
                .show();
    }

    public static void showSimpleInputDialog(Context context, String title, String content, String hint, boolean cancelable, int inputType, int length, int cancelTextColor, SimpleCallback callback) {
        new Builder(context).setTitle(title)
                .setContent(content)
                .setCancelable(cancelable)
                .setInput(true)
                .setHint(hint)
                .setInputType(inputType)
                .setLength(length)
                .setCancelTextColor(cancelTextColor)
                .setClickCallback(callback)
                .build()
                .show();
    }

    //  背景白色带阴影  黑色取消  粉色确定的 通用Dialog
    public static void showPublicTips(Context context, String title, String content, final CurrencyCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_public_layout);
        TextView tvTitle = dialog.findViewById(R.id.title);
        TextView tvContent = dialog.findViewById(R.id.content);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        tvTitle.setText(title);
        tvContent.setText(content);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirmClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    public static void showStringArrayDialog(Context context, SparseArray<String> array, final StringArrayDialogCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_string_array);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        LinearLayout container = (LinearLayout) dialog.findViewById(R.id.container);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (callback != null) {
                    callback.onItemClick(textView.getText().toString(), (int) v.getTag());
                }
                dialog.dismiss();
            }
        };
        for (int i = 0, length = array.size(); i < length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(54)));
            textView.setTextColor(0xff323232);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);
            textView.setText(array.valueAt(i));
            textView.setTag(array.keyAt(i));
            textView.setOnClickListener(itemListener);
            container.addView(textView);
            if (i != length - 1) {
                View v = new View(context);
                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(1)));
                v.setBackgroundColor(0xfff5f5f5);
                container.addView(v);
            }
        }
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // 进入App  弹出的获取免费通话时长的Dialog   恭喜你!   你获得2次时长为2分钟的免费通话, 快去聊天吧!
    public static void showFreeCallsDialog(Context context, String chance, String time) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_free_call_layout);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView title = dialog.findViewById(R.id.tv_time);
        title.setText("你获得" + chance + "次时长为" + time + "分钟的免费通话，快去聊天吧！");
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //提现账户删除
    public static void showAccountDeteleDialog(Context context, final CashAccountDeleteCallBack callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.accountdeteledialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirmClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    //提现账户删除
    public static void showAccountBuyDialog(Context context, String text, final CashAccountDeleteCallBack callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.accountdeteledialog);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        TextView textView = dialog.findViewById(R.id.title);
        textView.setText(text);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onConfirmClick();
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    /*
     * 语音、视频接听收费设置设置
     * */
    public static void showCallChargesDialog(Context mContext, String titles, final InviteCodeCallBack codeCallBack) {
        final Dialog dialog = new Dialog(mContext, R.style.dialog);
        dialog.setContentView(R.layout.dialog_call_charges);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);

        TextView title = dialog.findViewById(R.id.title);
        title.setText(titles);

        TextView coinTime = dialog.findViewById(R.id.tv_coin_time);
        coinTime.setText(SpUtil.getInstance().getCoinUnit() + "/分钟");

        final EditText content = dialog.findViewById(R.id.content);

        dialog.findViewById(R.id.btn_confirm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(content.getText().toString().trim())) {
                    codeCallBack.onConfirmClick(dialog, content.getText().toString());
                    dialog.dismiss();
                } else {
                    ToastUtil.show("请输入修改金额");
                }
            }
        });
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();

    }

    public static void showKnowDialog(Context context, String content, final SimpleCallback simpleCallback) {
        final Dialog dialog = DialogUtil.getBaseDialog(context, R.style.dialog2, R.layout.dialog_no_disturb, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(90);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);
        ((TextView) dialog.findViewById(R.id.name)).setText(content);
        dialog.findViewById(R.id.btn_sure).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != simpleCallback)
                    simpleCallback.onConfirmClick();
                dialog.dismiss();
            }
        });

    }

    public static void showStringArrayDialog(Context context, Integer[] array, final StringArrayDialogCallback callback) {
        showStringArrayDialog(context, array, null, callback);
    }

    public static void showStringArrayDialog(Context context, Integer[] array, DialogInterface.OnDismissListener listener, final StringArrayDialogCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_string_array);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        LinearLayout container = (LinearLayout) dialog.findViewById(R.id.container);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (callback != null) {
                    callback.onItemClick(textView.getText().toString(), (int) v.getTag());
                }
                dialog.dismiss();
            }
        };
        for (int i = 0, length = array.length; i < length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(54)));
            textView.setTextColor(0xff323232);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);
            textView.setText(array[i]);
            textView.setTag(array[i]);
            textView.setOnClickListener(itemListener);
            container.addView(textView);
            if (i != length - 1) {
                View v = new View(context);
                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(1)));
                v.setBackgroundColor(0xfff5f5f5);
                container.addView(v);
            }
        }
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        if (listener != null) {
            dialog.setOnDismissListener(listener);
        }
        dialog.show();
    }

    // 白色 list 列表选择从下方滑出
    public static void showStringArrayDialog2(Context context, String[] array, final ChannelDialogCallback callback) {
        final Dialog dialog = new Dialog(context, R.style.dialog);
        dialog.setContentView(R.layout.dialog_string_array_1);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        LinearLayout container = (LinearLayout) dialog.findViewById(R.id.container);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (callback != null) {
                    callback.onItemClick(0, textView.getText().toString());
                }
                dialog.dismiss();
            }
        };
        for (int i = 0, length = array.length; i < length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(54)));
            textView.setTextColor(0xff323232);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);
            textView.setText(array[i]);
            textView.setTag(array[i]);
            textView.setOnClickListener(itemListener);
            container.addView(textView);
            if (i != length - 1) {
                View v = new View(context);
                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(1)));
                v.setBackgroundColor(0xfff5f5f5);
                container.addView(v);
            }
        }
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        if (listener != null) {
//            dialog.setOnDismissListener(listener);
//        }
        dialog.show();
    }

    public static void showSingleTextPickerDialog(Context context, final String[] list, final SingleTextCallback callback) {

        final Dialog dialog = new Dialog(context, R.style.BottomDialogStyle);
        Window window = dialog.getWindow();
        window.getDecorView().setPadding(0, 0, 0, 0);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        dialog.setContentView(R.layout.dialog_single_text_picker);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        final NumberPicker numberPicker = (NumberPicker) dialog.findViewById(R.id.numberPicker);
        numberPicker.setDisplayedValues(list);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(list.length);
        numberPicker.setValue(2);
        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//                L.e(newVal+"");
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (v.getId() == R.id.btn_confirm) {
                    if (callback != null) {
                        callback.onConfirmClick(numberPicker.getValue() - 1, list[numberPicker.getValue() - 1]);
                        dialog.dismiss();
                    }
                }
            }
        };
        dialog.findViewById(R.id.btn_confirm).setOnClickListener(listener);
        dialog.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

    // 半透明 蓝底 （多用于 余额不足弹窗）
    public static void showTipsButtonDialog(Context mContext, String tips, String buttonText, final CurrencyCallback callback) {
        final Dialog dialog = DialogUtil.getBaseDialog(mContext, R.style.dialog2, R.layout.verification_tips_dialog, true, true);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        window.setAttributes(params);

        ImageView iv_close = dialog.findViewById(R.id.iv_close);
        TextView title = ((TextView) dialog.findViewById(R.id.title));
        TextView title2 = ((TextView) dialog.findViewById(R.id.title2));
        TextView tv_sure = dialog.findViewById(R.id.tv_sure);
        title2.setVisibility(View.GONE);

        if (!tips.isEmpty()) {
            title.setText(tips);
        }
        tv_sure.setText(buttonText);

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (null != callback) {
                    callback.onConfirmClick();
                    dialog.dismiss();
                }
            }
        });
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }


    public static class Builder {

        private Context mContext;
        private String mTitle;
        private String mContent;
        private String mConfrimString;
        private String mCancelString;
        private boolean mCancelable;
        private boolean mBackgroundDimEnabled;//显示区域以外是否使用黑色半透明背景
        private boolean mInput;//是否是输入框的
        private String mHint;
        private int mInputType;
        private int mLength;
        private SimpleCallback mClickCallback;
        private InviteCodeCallBack inviteCodeCallBack;
        private int mCancelTextColor;
        private boolean mHideCancel;//隐藏取消按钮

        private boolean isVersion = false;//是否是更新


        public Builder(Context context) {
            mContext = context;
        }

        public Builder isUpVersion(boolean isVersion) {
            this.isVersion = isVersion;
            return this;
        }

        public Builder hideCancel(boolean hideCancel) {
            mHideCancel = hideCancel;
            return this;
        }

        public Builder setTitle(String title) {
            mTitle = title;
            return this;
        }

        public Builder setContent(String content) {
            mContent = content;
            return this;
        }

        public Builder setConfrimString(String confrimString) {
            mConfrimString = confrimString;
            return this;
        }

        public Builder setCancelString(String cancelString) {
            mCancelString = cancelString;
            return this;
        }

        public Builder setCancelable(boolean cancelable) {
            mCancelable = cancelable;
            return this;
        }

        public Builder setCancelTextColor(int cancelTextColor) {
            mCancelTextColor = cancelTextColor;
            return this;
        }

        public Builder setBackgroundDimEnabled(boolean backgroundDimEnabled) {
            mBackgroundDimEnabled = backgroundDimEnabled;
            return this;
        }

        public Builder setInput(boolean input) {
            mInput = input;
            return this;
        }

        public Builder setHint(String hint) {
            mHint = hint;
            return this;
        }

        public Builder setInputType(int inputType) {
            mInputType = inputType;
            return this;
        }

        public Builder setLength(int length) {
            mLength = length;
            return this;
        }

        public Builder setClickCallback(SimpleCallback clickCallback) {
            mClickCallback = clickCallback;
            return this;
        }

        public Builder setInviteCodeCallBack(InviteCodeCallBack inviteCodeCallBack) {
            this.inviteCodeCallBack = inviteCodeCallBack;
            return this;
        }

        public Dialog build() {
            final Dialog dialog = new Dialog(mContext, mBackgroundDimEnabled ? R.style.dialog : R.style.dialog2);
            dialog.setContentView(mInput ? R.layout.dialog_input_util : R.layout.dialog_simple);
            dialog.setCancelable(mCancelable);
            dialog.setCanceledOnTouchOutside(mCancelable);
            TextView titleView = (TextView) dialog.findViewById(R.id.title);
            if (!TextUtils.isEmpty(mTitle)) {
                titleView.setText(mTitle);
            }
            final TextView content = (TextView) dialog.findViewById(R.id.content);
            if (!TextUtils.isEmpty(mHint)) {
                content.setHint(mHint);
            }
            if (!TextUtils.isEmpty(mContent)) {
                content.setText(mContent);
                CharSequence text = content.getText();
                if (text instanceof Spannable) {
                    Spannable spanText = (Spannable) text;
                    Selection.setSelection(spanText, text.length());
                }
            }
            if (mInputType == INPUT_TYPE_NUMBER) {
                content.setInputType(InputType.TYPE_CLASS_NUMBER);
            } else if (mInputType == INPUT_TYPE_NUMBER_PASSWORD) {
                content.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
            } else if (mInputType == INPUT_TYPE_TEXT_PASSWORD) {
                content.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
            }
            if (mLength > 0 && content instanceof EditText) {
                content.setFilters(new InputFilter[]{new InputFilter.LengthFilter(mLength)});
            }
            TextView btnConfirm = (TextView) dialog.findViewById(R.id.btn_confirm);
            if (!TextUtils.isEmpty(mConfrimString)) {
                btnConfirm.setText(mConfrimString);
            }
            TextView btnCancel = (TextView) dialog.findViewById(R.id.btn_cancel);
            if (mCancelTextColor != 0) {
                btnCancel.setTextColor(mCancelTextColor);
            }
            if (!TextUtils.isEmpty(mCancelString)) {
                btnCancel.setText(mCancelString);
            }
            if (mHideCancel) {
                btnCancel.setVisibility(View.GONE);
                if (!isVersion) {
                    dialog.findViewById(R.id.viewDialogCancelDivider).setVisibility(View.GONE);
                }
            }
            View.OnClickListener listener = new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (v.getId() == R.id.btn_confirm) {
                        if (mClickCallback != null) {
                            if (mInput) {
                                EditText editText = dialog.findViewById(R.id.content);
                                mClickCallback.onConfirmClick(editText.getText().toString().trim());
                                dialog.dismiss();
                            } else {
                                mClickCallback.onConfirmClick();
                                dialog.dismiss();
                            }
                        } else {
                            dialog.dismiss();
                        }
                    } else {
                        mClickCallback.onCancelClick();
                        dialog.dismiss();

                    }
                }
            };
            btnConfirm.setOnClickListener(listener);
            btnCancel.setOnClickListener(listener);
            return dialog;
        }

    }

    public interface SingleTextCallback {
        void onConfirmClick(int id, String date);
    }

    public interface StringArrayDialogCallback {
        void onItemClick(String text, int tag);
    }

    public interface ChannelDialogCallback {
        void onItemClick(long id, String text);
    }

    public interface SimpleCallback {
        void onConfirmClick();

        void onConfirmClick(String input);

        void onCancelClick();

    }

    public interface SexPickerCallback {
        void onConfirmClick(int sex);
    }

    //添加提现账户
    public interface CashAccountCallBack {
        void onConfirmClick(int cashType, String accountNum, String name, String cardID);
    }

    //删除提现账户
    public interface CashAccountDeleteCallBack {
        void onConfirmClick();
    }

    //邀请码回调
    public interface InviteCodeCallBack {
        void onConfirmClick(Dialog dialog, String content);
    }

    // 单个确定按钮 （通用）
    public interface CurrencyCallback {
        void onConfirmClick();
    }

}
