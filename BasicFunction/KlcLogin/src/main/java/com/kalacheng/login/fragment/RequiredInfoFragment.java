package com.kalacheng.login.fragment;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.dialog.DatePickerDialog;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.base.event.RequiredInfoEvent;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.login.R;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import org.greenrobot.eventbus.EventBus;

import java.io.File;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author: Administrator
 * @date: 2020/9/7
 * @info:
 */
public class RequiredInfoFragment extends BaseFragment implements View.OnClickListener {

    private Context mContext;
    private ApiUserInfo apiUserInfo;

    ImageView ivHead, iv_head_min;
    EditText etName;
    TextView tvBirthday;
    private Dialog uploadDialog;
    private ProcessImageUtil mImageUtil;
    Disposable uploadImgDisposable;
    String imgUrl, userName, birthday, constellation;
    int sex = 0;
    private File imgFile;
    private RelativeLayout rl_girl_select, rl_boy_select;
    private TextView tv_girl_select, tv_boy_select, btn_next;

    public RequiredInfoFragment() {

    }

    public RequiredInfoFragment(Context mContext, ViewGroup mParentView) {
        this.mContext = mContext;
        this.mParentView = mParentView;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_required_info;
    }

    @Override
    protected void initView() {
        ivHead = mParentView.findViewById(R.id.iv_head);
        etName = mParentView.findViewById(R.id.et_name);
        tvBirthday = mParentView.findViewById(R.id.tv_birthday);
        rl_girl_select = mParentView.findViewById(R.id.rl_girl_select);
        rl_boy_select = mParentView.findViewById(R.id.rl_boy_select);
        tv_girl_select = mParentView.findViewById(R.id.tv_girl_select);
        tv_boy_select = mParentView.findViewById(R.id.tv_boy_select);
        iv_head_min = mParentView.findViewById(R.id.iv_head_min);
        btn_next = mParentView.findViewById(R.id.btn_next);

        apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);

        if (apiUserInfo != null) {
            if (!TextUtils.isEmpty(apiUserInfo.avatar)) {
                ImageLoader.display(apiUserInfo.avatar, ivHead);
                imgUrl = apiUserInfo.avatar;
                iv_head_min.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(apiUserInfo.username)) {
                etName.setText(apiUserInfo.username);
                userName = apiUserInfo.username;
            }
            if (!TextUtils.isEmpty(apiUserInfo.birthday) && !TextUtils.isEmpty(apiUserInfo.constellation)) {
                tvBirthday.setText(apiUserInfo.birthday);
                birthday = apiUserInfo.birthday;
                constellation = apiUserInfo.constellation;
            }
        }

        etName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!TextUtils.isEmpty(s.toString().trim())) {
                    setNextButton();
                } else {
                    btn_next.setBackgroundResource(R.drawable.bg_gray_30);
                }
            }
        });
    }

    @Override
    protected void initData() {
        uploadDialog = DialogUtil.loadingDialog(getContext(), R.style.dialog2, R.layout.dialog_loading, false, false, "上传中");
        mImageUtil = new ProcessImageUtil(getActivity());
        mParentView.findViewById(R.id.rl_boy_select).setOnClickListener(this);
        mParentView.findViewById(R.id.rl_girl_select).setOnClickListener(this);
        mParentView.findViewById(R.id.btn_next).setOnClickListener(this);
        mParentView.findViewById(R.id.ll_birthday).setOnClickListener(this);
        ivHead.setOnClickListener(this);
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                imgFile = file;
                ImageLoader.display(imgFile, ivHead);
                iv_head_min.setVisibility(View.GONE);
                setNextButton();
            }

            @Override
            public void onFailure() {
            }
        });
    }

    // 修改性别相应的ui
    private void setSix(int six) {
        rl_boy_select.setBackgroundResource(R.drawable.shape_f5f5f5_22_bg);
        rl_girl_select.setBackgroundResource(R.drawable.shape_f5f5f5_22_bg);
        tv_girl_select.setTextColor(Color.parseColor("#333333"));
        tv_boy_select.setTextColor(Color.parseColor("#333333"));
        if (six == 1) {
            rl_boy_select.setBackgroundResource(R.drawable.shape_dbf4ff_22_bg);
            tv_boy_select.setTextColor(Color.parseColor("#4BC9FF"));
        } else {
            rl_girl_select.setBackgroundResource(R.drawable.shape_fff1f8_22_bg);
            tv_girl_select.setTextColor(Color.parseColor("#FF5EC6"));
        }
    }

    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.iv_head) {
            DialogUtil.showStringArrayDialog(getContext(), new Integer[]{
                    R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(String text, int tag) {
                    if (tag == R.string.camera) {
                        mImageUtil.getImageByCamera();
                    } else if (tag == R.string.alumb) {
                        mImageUtil.getImageByAlbumCustom();
                    }
                }
            });
        } else if (view.getId() == R.id.ll_birthday) {
            showDialog();
        } else if (view.getId() == R.id.rl_boy_select) {
            sex = 1;
            setSix(sex);
            setNextButton();
        } else if (view.getId() == R.id.rl_girl_select) {
            sex = 2;
            setSix(sex);
            setNextButton();
        } else if (view.getId() == R.id.btn_next) {
            if (TextUtils.isEmpty(imgUrl) && imgFile == null) {
                ToastUtil.show("请先上传头像");
                return;
            }
            userName = etName.getText().toString().trim();
            if (TextUtils.isEmpty(userName)) {
                ToastUtil.show("请先输入昵称");
                return;
            }
            if (TextUtils.isEmpty(birthday) || TextUtils.isEmpty(constellation)) {
                ToastUtil.show("请先选择生日");
                return;
            }
            if (sex == 0) {
                ToastUtil.show("请先选择性别");
                return;
            }
            if (uploadDialog != null) {
                uploadDialog.show();
            }
            if (imgFile != null) {
                upImage();
            } else {
                updateInfo();
            }
        }
    }

    // 检测信息是否已经全部填写完成
    private void setNextButton() {
        if (TextUtils.isEmpty(imgUrl) && null == imgFile && TextUtils.isEmpty(apiUserInfo.avatar)) {
            btn_next.setBackgroundResource(R.drawable.bg_gray_30);
            return;
        }
        userName = etName.getText().toString().trim();
        if (TextUtils.isEmpty(userName)) {
            btn_next.setBackgroundResource(R.drawable.bg_gray_30);
            return;
        }
        if (TextUtils.isEmpty(birthday) || TextUtils.isEmpty(constellation)) {
            btn_next.setBackgroundResource(R.drawable.bg_gray_30);
            return;
        }
        if (TextUtils.isEmpty(etName.getText().toString().trim())) {
            btn_next.setBackgroundResource(R.drawable.bg_gray_30);
            return;
        }
        if (sex == 0) {
            btn_next.setBackgroundResource(R.drawable.bg_gray_30);
            return;
        }
        if (sex == 1) {
            btn_next.setBackgroundResource(R.drawable.bg_gradient_blue3);
        } else if (sex == 2) {
            btn_next.setBackgroundResource(R.drawable.bg_gradient_purple3);
        }

    }

    private void showDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog();
        datePickerDialog.show(getFragmentManager(), "DatePickerDialog");
        datePickerDialog.setOnDataPickerListener(new DatePickerDialog.OnDataPickerListener() {
            @Override
            public void onConfirm(String date) {
                tvBirthday.setText(date);
                birthday = date;
                String string[] = date.split("-");
                int month = Integer.parseInt(string[1].toString());
                int dateInt = Integer.parseInt(string[2].toString());
                switch (month) {
                    case 1:
                        if (dateInt < 21)
                            constellation = "摩羯座";
                        else
                            constellation = "水瓶座";
                        break;
                    case 2:
                        if (dateInt < 20)
                            constellation = "水瓶座";
                        else
                            constellation = "双鱼座";
                        break;
                    case 3:
                        if (dateInt < 21)
                            constellation = "双鱼座";
                        else
                            constellation = "白羊座";
                        break;
                    case 4:
                        if (dateInt < 21)
                            constellation = "白羊座";
                        else
                            constellation = "金牛座";
                        break;
                    case 5:
                        if (dateInt < 22)
                            constellation = "金牛座";
                        else
                            constellation = "双子座";
                        break;
                    case 6:
                        if (dateInt < 22)
                            constellation = "双子座";
                        else
                            constellation = "巨蟹座";
                        break;
                    case 7:
                        if (dateInt < 23)
                            constellation = "巨蟹座";
                        else
                            constellation = "狮子座";
                        break;
                    case 8:
                        if (dateInt < 24)
                            constellation = "狮子座";
                        else
                            constellation = "处女座";
                        break;
                    case 9:
                        if (dateInt < 24)
                            constellation = "处女座";
                        else
                            constellation = "天秤座";
                        break;
                    case 10:
                        if (dateInt < 24)
                            constellation = "天秤座";
                        else
                            constellation = "天蝎座";
                        break;
                    case 11:
                        if (dateInt < 23)
                            constellation = "天蝎座";
                        else
                            constellation = "射手座";
                        break;
                    case 12:
                        if (dateInt < 22)
                            constellation = "射手座";
                        else
                            constellation = "摩羯座";
                        break;
                }
                setNextButton();
            }
        });
    }

    /**
     * 上传头像
     */
    private void upImage() {
        uploadImgDisposable = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(final ObservableEmitter<String> emitter) throws Exception {
                UploadUtil.getInstance().uploadPicture(1, imgFile, new PictureUploadCallback() {
                    @Override
                    public void onSuccess(String imgStr) {
                        if (!TextUtils.isEmpty(imgStr)) {
                            emitter.onNext(imgStr);
                        } else {
                            if (uploadDialog != null && uploadDialog.isShowing()) {
                                uploadDialog.dismiss();
                            }
                            ToastUtil.show("上传失败");
                        }
                    }

                    @Override
                    public void onFailure() {
                        if (uploadDialog != null && uploadDialog.isShowing()) {
                            uploadDialog.dismiss();
                        }
                        ToastUtil.show("上传失败");
                    }
                });
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String url) throws Exception {
                if (!TextUtils.isEmpty(url)) {
//                    imgFile = null;
                    imgUrl = url;
                    updateInfo();
                } else {
                    if (uploadDialog != null && uploadDialog.isShowing()) {
                        uploadDialog.dismiss();
                    }
                    ToastUtil.show("上传头像失败");
                }
            }
        });
    }

    /**
     * 更新个人信息
     */
    private void updateInfo() {
        HttpApiAppUser.user_update(null, imgUrl, birthday, null, constellation, -1, -1, null, -1, null, sex, null, userName, null, null, -1, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (uploadDialog != null && uploadDialog.isShowing()) {
                    uploadDialog.dismiss();
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.show(msg);
                }
                if (code == 1) {
                    if (apiUserInfo != null) {
                        apiUserInfo.avatar = imgUrl;
                        apiUserInfo.username = userName;
                        apiUserInfo.sex = sex;
                        apiUserInfo.birthday = birthday;
                        apiUserInfo.constellation = constellation;
                        SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
                    }
//                    JMessageClient.updateUserAvatar(imgFile, new BasicCallback() {
//                        @Override
//                        public void gotResult(int i, String s) {
//                            L.e("设置头像极光回调---gotResult--->code: " + i + " msg: " + s);
//                        }
//                    });
                    ARouter.getInstance().build(ARouterPath.TagSelectActivity).withParcelable(ARouterValueNameConfig.ApiUserInfo, apiUserInfo).navigation();
                    EventBus.getDefault().post(new RequiredInfoEvent());
                }
            }
        });
    }

    @Override
    public void onDestroy() {
        if (uploadImgDisposable != null)
            uploadImgDisposable.dispose();
        super.onDestroy();
    }

}