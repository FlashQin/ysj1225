package com.kalacheng.livecommon.fragment;

import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.ApiCloseLine;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.libuser.model.AppTabInfo;
import com.kalacheng.livecommon.R;
import com.kalacheng.util.utils.DrawableUtil;
import com.kalacheng.util.utils.StringUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MyGropView;
import com.klc.bean.OOOLiveHangUpBean;

import java.util.ArrayList;
import java.util.List;

/*
 * 视频结束 （用户）
 * */
public class OOOLiveUserEndDialogFragment extends BaseDialogFragment implements View.OnClickListener, DialogInterface.OnKeyListener {
    private OOOLiveHangUpBean oooLiveHangUpBean;
    private AppJoinRoomVO apiJoinRoom;
    private MyGropView UserEnd_labelList, UserEnd_Select;
    private EditText UserEnd_AddEdit;

    private long addlabelid = 10000;

    private List<AppTabInfo> mList = new ArrayList<>();
    //选中的标签
    private List<AppTabInfo> mSelectLabel = new ArrayList<>();

    //免费时长
    private String freeCallMsg;

    @Override
    protected int getLayoutId() {
        return R.layout.ooo_live_userend;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    public void getOOOLiveHangUpInformation(OOOLiveHangUpBean bean, AppJoinRoomVO apiJoinRoom, String freeCallMsg) {
        this.oooLiveHangUpBean = bean;
        this.apiJoinRoom = apiJoinRoom;
        this.freeCallMsg = freeCallMsg;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ImageView UserEnd_UserHead = mRootView.findViewById(R.id.UserEnd_UserHead);
        TextView UserEnd_UserName = mRootView.findViewById(R.id.UserEnd_UserName);
        TextView UserEnd_Time = mRootView.findViewById(R.id.UserEnd_Time);
        TextView UserEnd_FreeTime = mRootView.findViewById(R.id.UserEnd_FreeTime);
        UserEnd_AddEdit = mRootView.findViewById(R.id.UserEnd_AddEdit);
        TextView vipDiscount_money = mRootView.findViewById(R.id.vipDiscount_money);
        RelativeLayout vipDiscount = mRootView.findViewById(R.id.vipDiscount);
        TextView tvVipMsg = mRootView.findViewById(R.id.tvVipMsg);
        LinearLayout comment = mRootView.findViewById(R.id.comment);

        TextView UserEnd_Add = mRootView.findViewById(R.id.UserEnd_Add);
        UserEnd_Add.setOnClickListener(this);

        TextView UserEnd_true = mRootView.findViewById(R.id.UserEnd_true);
        UserEnd_true.setOnClickListener(this);

        ImageView close = mRootView.findViewById(R.id.close);
        close.setOnClickListener(this);

        ImageLoader.display(apiJoinRoom.anchorAvatar, UserEnd_UserHead, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        UserEnd_UserName.setText("与" + apiJoinRoom.anchorName + "的通话结束啦");
        UserEnd_Time.setText("通话时长:" + StringUtil.getDurationText2(oooLiveHangUpBean.callTime));

        if (!TextUtils.isEmpty(freeCallMsg)) {
            UserEnd_FreeTime.setText("含" + freeCallMsg);
            UserEnd_FreeTime.setVisibility(View.VISIBLE);
        } else {
            UserEnd_FreeTime.setVisibility(View.GONE);
        }

        //1v1是否评论开关0开启1关闭
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_ISCOMMENT, 0) == 1) {
            comment.setVisibility(View.GONE);
        } else {
            comment.setVisibility(View.VISIBLE);
        }

        if (oooLiveHangUpBean.vipCount == 0) {
            vipDiscount.setVisibility(View.GONE);
        } else {
            vipDiscount.setVisibility(View.VISIBLE);
            vipDiscount_money.setText(DecimalFormatUtils.isIntegerDouble(oooLiveHangUpBean.vipCount));
            tvVipMsg.setText(oooLiveHangUpBean.vipGradeMsg);
        }

        UserEnd_labelList = mRootView.findViewById(R.id.UserEnd_labelList);
        UserEnd_Select = mRootView.findViewById(R.id.UserEnd_Select);
        getLabel();

        this.getDialog().setOnKeyListener(new DialogInterface.OnKeyListener() {
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    close();
                    return true; // pretend we've processed it
                } else {
                    return false; // pass on to be processed as normal
                }
            }
        });
    }

    //获取标签
    public void getLabel() {
        HttpApiAppUser.getlabels(5, new HttpApiCallBackArr<AppTabInfo>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppTabInfo> retModel) {
                if (code == 1) {
                    if (retModel != null) {
                        mList.addAll(retModel);
                        setValue();
                    }
                }
            }
        });
    }

    public void setValue() {
        UserEnd_labelList.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            if (!"".equals(mList.get(i))) {
                View view = LayoutInflater.from(mContext).inflate(R.layout.label_itme, null, false);

                final TextView Search_Content = view.findViewById(R.id.Search_Content);

                DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
                builder.setStroke(2, Color.parseColor(mList.get(i).fontColor));
                builder.setColor(Color.parseColor("#ffffff"));
                builder.setCornerRadius(40f);
                Drawable drawable = builder.create();
                Search_Content.setBackground(drawable);
                Search_Content.setTextColor(Color.parseColor(mList.get(i).fontColor));

                Search_Content.setText(mList.get(i).name);
                Search_Content.setTag(mList.get(i).id);
                final int finalI1 = i;
                Search_Content.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (mSelectLabel.size() <= 3) {
                            if (!mSelectLabel.contains(mList.get(finalI1))) {
                                DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
                                builder.setStroke(2, Color.parseColor("#829DFF"));
                                builder.setColor(Color.parseColor("#829DFF"));
                                builder.setCornerRadius(40f);
                                Drawable drawable = builder.create();
                                Search_Content.setBackground(drawable);
                                Search_Content.setTextColor(Color.parseColor("#ffffff"));

                                selectLable(mList.get(finalI1));
                            }
                        } else {
                            ToastUtil.show("不能再添加了");
                        }

                    }
                });

                UserEnd_labelList.addView(view);
            }
        }
    }

    //选中标签
    public void selectLable(final AppTabInfo appTabInfo) {
        mSelectLabel.add(appTabInfo);
        final View view = LayoutInflater.from(mContext).inflate(R.layout.label_itme, null, false);

        TextView Search_Content = view.findViewById(R.id.Search_Content);
        ImageView Search_reduce = view.findViewById(R.id.Search_reduce);
        Search_reduce.setVisibility(View.VISIBLE);
        DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
        builder.setStroke(2, Color.parseColor("#829DFF"));
        builder.setColor(Color.parseColor("#829DFF"));
        builder.setCornerRadius(40f);
        Drawable drawable = builder.create();
        Search_Content.setBackground(drawable);
        Search_Content.setTextColor(Color.WHITE);

        Search_Content.setText(appTabInfo.name);

        Search_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < mSelectLabel.size(); i++) {
                    if (appTabInfo.id == mSelectLabel.get(i).id) {
                        mSelectLabel.remove(i);
                    }
                }

                for (int y = 0; y < mList.size(); y++) {
                    if (appTabInfo.id == mList.get(y).id) {
                        RelativeLayout relativeLayout = (RelativeLayout) UserEnd_labelList.getChildAt(y);
                        TextView textView = (TextView) relativeLayout.getChildAt(0);
                        DrawableUtil.Builder builder = DrawableUtil.getBuilder(DrawableUtil.Builder.RECTANGLE);
                        builder.setStroke(2, Color.parseColor(mList.get(y).fontColor));
                        builder.setColor(Color.parseColor("#ffffff"));
                        builder.setCornerRadius(40f);
                        Drawable drawable = builder.create();
                        textView.setBackground(drawable);
                        textView.setTextColor(Color.parseColor(mList.get(y).fontColor));
                    }
                }

                UserEnd_Select.removeView(view);
            }
        });

        UserEnd_Select.addView(view);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.UserEnd_Add) {
            if (mSelectLabel.size() <= 3) {
                if (UserEnd_AddEdit.getText().toString() != null && !UserEnd_AddEdit.getText().toString().equals("")) {
                    addlabelid = addlabelid + 1;
                    AppTabInfo appTabInfo = new AppTabInfo();
                    appTabInfo.fontColor = "#829DFF";
                    appTabInfo.name = UserEnd_AddEdit.getText().toString();
                    appTabInfo.id = addlabelid;
                    selectLable(appTabInfo);
                }
            } else {
                ToastUtil.show("不能再添加了");
            }


        } else if (view.getId() == R.id.UserEnd_true) {
            submit();
        } else if (view.getId() == R.id.close) {
            close();
        }
    }

    // 关闭
    private void close() {
        LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCloseLive, new ApiCloseLine());
        dismiss();
    }

    public void submit() {
        if ((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_ISCOMMENT, 0) == 1) {
            close();
        } else {
            String tabIdStr = "";
            for (AppTabInfo appTabInfo : mSelectLabel) {
                tabIdStr = tabIdStr + appTabInfo.fontColor + ":" + appTabInfo.name + ",";
            }
            if (tabIdStr.equals("")) {
                if (mList.size() > 0) {
                    ToastUtil.show("请选择标签");
                } else {
                    close();
                }
                return;
            }
            HttpApiAppUser.addCommentByAnchor(LiveConstants.ANCHORID, tabIdStr.substring(0, tabIdStr.length() - 1), new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OOOCloseLive, new ApiCloseLine());
                    dismiss();
                }
            });
        }
    }


    @Override
    public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            close();
            return true;
        } else {
            //这里注意当不是返回键时需将事件扩散，否则无法处理其他点击事件
            return false;
        }
    }
}
