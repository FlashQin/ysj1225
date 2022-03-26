package com.kalacheng.commonview.dialog;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOTMCall;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.OpenSvipMoneyAdpater;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppSvipDto;
import com.kalacheng.libuser.model.AppSvipPackage;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;

public class OpenSVipDialogFragment extends BaseDialogFragment {
    private OpenSvipMoneyAdpater adpater;
    private long payID;
    private AppSvipPackage appSvipPackage;

    @Override
    protected int getLayoutId() {
        return R.layout.open_svip_dialog;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.getScreenWidth() - DpUtil.dp2px(80);
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        openSvipInformation();
    }

    public void openSvipInformation() {
        HttpApiOTMCall.getAppSvip(1, new HttpApiCallBack<AppSvipDto>() {
            @Override
            public void onHttpRet(int code, String msg, AppSvipDto retModel) {
                if (code == 1) {
                    getIntiView(retModel);
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @SuppressLint("WrongConstant")
    public void getIntiView(final AppSvipDto info) {
        TextView OpenSvip_name = mRootView.findViewById(R.id.OpenSvip_name);
        OpenSvip_name.setText(info.name);
        ImageView OpenSvip_image = mRootView.findViewById(R.id.OpenSvip_image);
        ImageLoader.display(info.picture, OpenSvip_image);

        RecyclerView OpenSivp_MoneyList = mRootView.findViewById(R.id.OpenSivp_MoneyList);
        GridLayoutManager manager = new GridLayoutManager(mContext, 3);
        OpenSivp_MoneyList.addItemDecoration(new ItemDecoration(mContext, 0, 10, 0));
        OpenSivp_MoneyList.setLayoutManager(manager);

        adpater = new OpenSvipMoneyAdpater(mContext);
        OpenSivp_MoneyList.setAdapter(adpater);
        adpater.setData(info.svipPackages);


        adpater.setOpenSvipMoneyCallBack(new OpenSvipMoneyAdpater.OpenSvipMoneyCallBack() {
            @Override
            public void onClick(int poistion) {
                adpater.setClick(poistion);
                payID = info.svipPackages.get(poistion).pid;
                appSvipPackage = info.svipPackages.get(poistion);
            }
        });
        if (info.svipPackages != null && info.svipPackages.size() > 0) {
            appSvipPackage = info.svipPackages.get(0);
            payID = info.svipPackages.get(0).pid;
        }
        TextView OpenSvip_openText = mRootView.findViewById(R.id.OpenSvip_openText);
        if (info.leftDays == 0) {
            OpenSvip_openText.setText("立即开通");
            OpenSvip_openText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    if (appSvipPackage != null && appSvipPackage.coinType == 1) {
                        goPayMethodSelectDialog(payID, appSvipPackage);
                    } else {
                        HttpApiOTMCall.openMember(payID, 1, new HttpApiCallBack<HttpNone>() {
                            @Override
                            public void onHttpRet(int code, String msg, HttpNone retModel) {
                                if (code == 1) {
                                    ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                                    apiUserInfo.isSvip = 1;
                                    SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
                                    ToastUtil.show(msg);
                                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_OpenSvipSusser, null);
                                    dismiss();
                                } else {
                                    ToastUtil.show(msg);
                                }
                            }
                        });
                    }
                }
            });
        } else {
            OpenSvip_openText.setText("还有" + info.leftDays + "天到期");
        }


        ImageView close = mRootView.findViewById(R.id.close);
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    /**
     * 进入支付方式选择弹框
     */
    private void goPayMethodSelectDialog(long id, AppSvipPackage bean) {
        PayMethodSelectDialog fragment = new PayMethodSelectDialog();
        Bundle bundle = new Bundle();
        bundle.putInt("shop", 2);
        bundle.putLong("orderId", id);
        bundle.putParcelable("appSvipPackage", bean);
        fragment.setArguments(bundle);
        fragment.show(getActivity().getSupportFragmentManager(), "PayMethodSelectDialog");
        fragment.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                dismiss();
            }
        });
    }
}
