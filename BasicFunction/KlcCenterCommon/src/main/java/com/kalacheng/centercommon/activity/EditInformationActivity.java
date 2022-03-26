package com.kalacheng.centercommon.activity;

import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.alibaba.fastjson.JSONObject;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.EditInformationAdpater;
import com.kalacheng.centercommon.bean.InformationBean;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import cn.jpush.im.android.api.JMessageClient;
import cn.jpush.im.android.api.model.UserInfo;
import cn.jpush.im.api.BasicCallback;

/**
 * 编辑资料
 */
@Route(path = ARouterPath.EidtInformation)
public class EditInformationActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = "UserInfoDto")
    public ApiUserInfo retModel;

    EditInformationAdpater baseinfoAdapter;
    EditInformationAdpater personinfoAdapter;
    EditInformationAdpater contactAdapter;
    EditInformationAdpater otherAdapter;
    private RecyclerView recyclerViewBaseInfo;
    private RecyclerView recyclerViewPersonInfo;
    private RecyclerView recyclerViewContact;
    private RecyclerView recyclerViewOther;

    //联系方式
    LinearLayout EditInformation_Lin;

    String height;
    String weight;
    String constellationStr;
    ImageView imgeView1, imgeView2, imgeView3, imgeView4, imgeView5, imgeView6, pickImg, imgeViewAdd1, imgeViewAdd2, imgeViewAdd3, imgeViewAdd4, imgeViewAdd5, imgeViewAdd6, pickAddImg;
    private ProcessImageUtil mImageUtil;
    int imgId;
    Map<Integer, String> mapImg = new HashMap<>();
    List<InformationBean> baseInfoList = new ArrayList<>();
    List<InformationBean> personInfoList = new ArrayList<>();
    List<InformationBean> contactInfoList = new ArrayList<>();
    List<InformationBean> otherInfoList = new ArrayList<>();

    public static int NAME = 1000;
    public static int PERSONAL = 1001;
    public static int BIRTHDAY = 1002;
    public static int HEIGHT = 1003;
    public static int WEIGHT = 1004;
    public static int PROFESSION = 1005;
    public static int PHONE = 1006;
    public static int WX = 1007;
    public static int address = 1008;
    public static int Sign = 1009;
    private Dialog uploadDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_information);
        initView();
        initData();
        initListener();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }

    private void initView() {
        uploadDialog = DialogUtil.loadingDialog(this, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, "上传中");
        recyclerViewBaseInfo = findViewById(R.id.recyclerView_baseinfo);
        recyclerViewPersonInfo = findViewById(R.id.recyclerView_personinfo);
        recyclerViewContact = findViewById(R.id.recyclerView_contact);
        recyclerViewOther = findViewById(R.id.recyclerView_other);
        EditInformation_Lin = findViewById(R.id.EditInformation_Lin);
        TextView textView = findViewById(R.id.titleView);
        textView.setText("编辑资料");
        recyclerViewBaseInfo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewPersonInfo.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewContact.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewOther.setLayoutManager(new LinearLayoutManager(this));
        recyclerViewBaseInfo.setHasFixedSize(true);
        recyclerViewBaseInfo.setNestedScrollingEnabled(false);
        recyclerViewPersonInfo.setHasFixedSize(true);
        recyclerViewPersonInfo.setNestedScrollingEnabled(false);
        recyclerViewContact.setHasFixedSize(true);
        recyclerViewContact.setNestedScrollingEnabled(false);
        recyclerViewOther.setHasFixedSize(true);
        recyclerViewOther.setNestedScrollingEnabled(false);
        imgeView1 = findViewById(R.id.img1);
        imgeView2 = findViewById(R.id.img2);
        imgeView3 = findViewById(R.id.img3);
        imgeView4 = findViewById(R.id.img4);
        imgeView5 = findViewById(R.id.img5);
        imgeView6 = findViewById(R.id.img6);

        imgeViewAdd1 = findViewById(R.id.img1_add);
        imgeViewAdd2 = findViewById(R.id.img2_add);
        imgeViewAdd3 = findViewById(R.id.img3_add);
        imgeViewAdd4 = findViewById(R.id.img4_add);
        imgeViewAdd5 = findViewById(R.id.img5_add);
        imgeViewAdd6 = findViewById(R.id.img6_add);

        imgeView1.setOnClickListener(this);
        imgeView2.setOnClickListener(this);
        imgeView3.setOnClickListener(this);
        imgeView4.setOnClickListener(this);
        imgeView5.setOnClickListener(this);
        imgeView6.setOnClickListener(this);

        if (ConfigUtil.getBoolValue(R.bool.showContactInformation)) {
            EditInformation_Lin.setVisibility(View.VISIBLE);
            recyclerViewContact.setVisibility(View.VISIBLE);
        } else {
            EditInformation_Lin.setVisibility(View.GONE);
            recyclerViewContact.setVisibility(View.GONE);
        }

        mImageUtil = new ProcessImageUtil(this);
        mImageUtil.setImageResultCallback(new ImageResultCallback() {
            @Override
            public void beforeCamera() {

            }

            @Override
            public void onSuccess(final File file) {
                if (file != null) {
                    ImageLoader.display(file, pickImg);
                    pickAddImg.setVisibility(View.GONE);
                    if (uploadDialog != null) {
                        uploadDialog.show();
                    }

                    UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
                        @Override
                        public void onSuccess(final String imgStr) {
                            if (!TextUtils.isEmpty(imgStr)) {
                                if (imgId == R.id.img1) {
                                    HttpApiAppUser.user_update(null, imgStr, null, null, null, -1, -1, null, -1, null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                                        @Override
                                        public void onHttpRet(int code, String msg, HttpNone retModel) {
                                            if (uploadDialog != null && uploadDialog.isShowing()) {
                                                uploadDialog.dismiss();
                                            }
                                            if (!TextUtils.isEmpty(msg)) {
                                                ToastUtil.show(msg);
                                            }
                                            if (code == 1) {
                                                UserInfo userInfo = JMessageClient.getMyInfo();
                                                String avatarUrl = userInfo.getExtra("avatarUrlStr");
                                                if (!avatarUrl.equals(imgStr)) {
                                                    userInfo.setUserExtras("avatarUrlStr", imgStr);
                                                    JMessageClient.updateMyInfo(UserInfo.Field.extras, userInfo, new BasicCallback() {
                                                        @Override
                                                        public void gotResult(int i, String s) {
                                                            L.e("极光IM", "更新极光用户图像信息回调---gotResult--->code: " + i + " msg: " + s);
                                                        }
                                                    });
                                                }
                                            }
                                        }
                                    });
                                } else {
                                    mapImg.put(imgId, imgStr);
                                    updatePortrait();
                                }
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
            }

            @Override
            public void onFailure() {
            }
        });

    }

    private void initListener() {
        baseinfoAdapter.setOnItemClickListener(new OnBeanCallback<InformationBean>() {
            @Override
            public void onClick(final InformationBean bean) {
                if (bean.name.equals("昵称")) {
                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 0).withString(ARouterValueNameConfig.EDIT_USER_OTHER, baseInfoList.get(0).value).navigation(EditInformationActivity.this, NAME);
                } else if (bean.name.equals("个性签名")) {
                    ARouter.getInstance().build(ARouterPath.PersonalActivity).withString(ARouterValueNameConfig.EDIT_USER_PERSONAL, retModel.signature).navigation(EditInformationActivity.this, PERSONAL);
                }
            }
        });

        personinfoAdapter.setOnItemClickListener(new OnBeanCallback<InformationBean>() {
            @Override
            public void onClick(final InformationBean bean) {
                if (bean.name.equals("生日")) {
                    ARouter.getInstance().build(ARouterPath.BirthdayActivity).withString(ARouterValueNameConfig.EDIT_USER_BIRTH, retModel.birthday).withString(ARouterValueNameConfig.EDIT_USER_CONSTELLATION, constellationStr).navigation(EditInformationActivity.this, BIRTHDAY);
                } else if (bean.name.equals("身高")) {
                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 1).withString(ARouterValueNameConfig.EDIT_USER_OTHER, retModel.height + "").navigation(EditInformationActivity.this, HEIGHT);
                } else if (bean.name.equals("体重")) {
                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 2).withString(ARouterValueNameConfig.EDIT_USER_OTHER, retModel.weight + "").navigation(EditInformationActivity.this, WEIGHT);
                } else if (bean.name.equals("职业")) {
                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 3).withString(ARouterValueNameConfig.EDIT_USER_OTHER, retModel.vocation).navigation(EditInformationActivity.this, PROFESSION);

                }
            }
        });
        contactAdapter.setOnItemClickListener(new OnBeanCallback<InformationBean>() {
            @Override
            public void onClick(final InformationBean bean) {
                if (bean.name.equals("手机号")) {
//                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 4).navigation(EditInformationActivity.this, PHONE);
                } else if (bean.name.equals("微信号")) {
                    ARouter.getInstance().build(ARouterPath.NameActivity).withInt(ARouterValueNameConfig.TYPE, 5).withString(ARouterValueNameConfig.EDIT_USER_OTHER, "-1".equals(retModel.wechatNo) ? "" : retModel.wechatNo).navigation(EditInformationActivity.this, WX);

                }
            }
        });
    }

    private void initData() {
        if (null != retModel.constellation) {
            constellationStr = retModel.constellation;
        } else {
            constellationStr = "";
        }
        if (!TextUtils.isEmpty(retModel.avatar)) {
            ImageLoader.display(retModel.avatar, imgeView1);
            imgeViewAdd1.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(retModel.portrait)) {
            String[] imageStr = retModel.portrait.split(",");
            if (imageStr.length > 0) {
                ImageLoader.display(imageStr[0], imgeView2);
                imgeViewAdd2.setVisibility(View.GONE);
                mapImg.put(R.id.img2, imageStr[0]);
            }
            if (imageStr.length > 1) {
                ImageLoader.display(imageStr[1], imgeView3);
                imgeViewAdd3.setVisibility(View.GONE);
                mapImg.put(R.id.img3, imageStr[1]);
            }
            if (imageStr.length > 2) {
                ImageLoader.display(imageStr[2], imgeView4);
                imgeViewAdd4.setVisibility(View.GONE);
                mapImg.put(R.id.img4, imageStr[2]);
            }
            if (imageStr.length > 3) {
                ImageLoader.display(imageStr[3], imgeView5);
                imgeViewAdd5.setVisibility(View.GONE);
                mapImg.put(R.id.img5, imageStr[3]);
            }
            if (imageStr.length > 4) {
                ImageLoader.display(imageStr[4], imgeView6);
                imgeViewAdd6.setVisibility(View.GONE);
                mapImg.put(R.id.img6, imageStr[4]);
            }
        }
        baseInfoList.add(new InformationBean("昵称", retModel.username));
        baseInfoList.add(new InformationBean("个性签名", TextUtils.isEmpty(retModel.signature) ? "这家伙很懒,什么也没留下" : retModel.signature));
        baseinfoAdapter = new EditInformationAdpater(baseInfoList);

        personInfoList.add(new InformationBean("生日", retModel.birthday));
        personInfoList.add(new InformationBean("星座", constellationStr));
        personInfoList.add(new InformationBean("职业", TextUtils.isEmpty(retModel.vocation) ? "设置你的职业" : retModel.vocation));
        personInfoList.add(new InformationBean("身高", retModel.height + "cm"));
        personInfoList.add(new InformationBean("体重", retModel.weight + "KG"));
        personinfoAdapter = new EditInformationAdpater(personInfoList);

        contactInfoList.add(new InformationBean("手机号", retModel.mobile));
        contactInfoList.add(new InformationBean("微信号", "-1".equals(retModel.wechatNo) ? "未填写" : retModel.wechatNo));
        contactAdapter = new EditInformationAdpater(contactInfoList);

        recyclerViewBaseInfo.setAdapter(baseinfoAdapter);
        recyclerViewPersonInfo.setAdapter(personinfoAdapter);
        recyclerViewContact.setAdapter(contactAdapter);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (otherAdapter == null) {
            StringBuffer buffer = new StringBuffer();

            if (retModel.interestListStr != null && retModel.interestListStr.name.length() > 0) {
                JSONObject jsonObject = JSONObject.parseObject(retModel.interestListStr.name);
                Iterator iter = jsonObject.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry entry = (Map.Entry) iter.next();
                    System.out.println(entry.getKey().toString());
                    System.out.println(entry.getValue().toString());
                    buffer.append(entry.getValue().toString()).append(" ");
                }

            } else {
                buffer.append("选择你的标签");
            }


            otherInfoList.add(new InformationBean("我的标签", buffer.toString()));
//            otherInfoList.add(new InformationBean("我的地址", retModel.address));
            otherAdapter = new EditInformationAdpater(otherInfoList);
            recyclerViewOther.setAdapter(otherAdapter);
            otherAdapter.setOnItemClickListener(new OnBeanCallback<InformationBean>() {
                @Override
                public void onClick(InformationBean bean) {
                    if (bean.name.equals("我的标签"))
                        //跳转页面
                        ARouter.getInstance().build(ARouterPath.TagActivity).navigation(EditInformationActivity.this, Sign);
                    else if (bean.name.equals("我的地址")) {
                        ARouter.getInstance().build(ARouterPath.MapActivity).withInt(ARouterValueNameConfig.TYPE, 2).navigation(EditInformationActivity.this, address);
                    }
                }
            });
        } else {
//            otherInfoList.get(1).value = (String) SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "");
//            otherAdapter.notifyDataSetChanged();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @Override
    public void onClick(View view) {
        if (CheckDoubleClick.isFastDoubleClick()) {
            return;
        }
        if (view.getId() == R.id.img1) {
            pickImg = imgeView1;
            pickAddImg = imgeViewAdd1;
            imgId = R.id.img1;
            setImage();
        } else if (view.getId() == R.id.img2) {
            pickImg = imgeView2;
            pickAddImg = imgeViewAdd2;
            imgId = R.id.img2;
            setImage();
        } else if (view.getId() == R.id.img3) {
            pickImg = imgeView3;
            pickAddImg = imgeViewAdd3;
            imgId = R.id.img3;
            setImage();
        } else if (view.getId() == R.id.img4) {
            pickImg = imgeView4;
            pickAddImg = imgeViewAdd4;
            imgId = R.id.img4;
            setImage();
        } else if (view.getId() == R.id.img5) {
            pickImg = imgeView5;
            pickAddImg = imgeViewAdd5;
            imgId = R.id.img5;
            setImage();
        } else if (view.getId() == R.id.img6) {
            pickImg = imgeView6;
            pickAddImg = imgeViewAdd6;
            imgId = R.id.img6;
            setImage();
        }

    }

    /**
     * 设置头像
     */
    Integer[] str1;
    Integer[] str2;

    private void setImage() {
        if (ConfigUtil.getBoolValue(R.bool.containPhotograph)) {
            str1 = new Integer[]{R.string.camera, R.string.alumb};
            str2 = new Integer[]{R.string.camera, R.string.alumb, R.string.delete};
        } else {
            str1 = new Integer[]{R.string.alumb};
            str2 = new Integer[]{R.string.alumb, R.string.delete};
        }

        if (imgId == R.id.img1) {
            DialogUtil.showStringArrayDialog(mContext, str1, new DialogUtil.StringArrayDialogCallback() {
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
        } else {
            DialogUtil.showStringArrayDialog(mContext, str2, new DialogUtil.StringArrayDialogCallback() {
                @RequiresApi(api = Build.VERSION_CODES.M)
                @Override
                public void onItemClick(String text, int tag) {
                    if (tag == R.string.camera) {
                        mImageUtil.getImageByCamera(false);
                    } else if (tag == R.string.alumb) {
                        mImageUtil.getImageByAlbumCustom(false);
                    } else if (tag == R.string.delete) {
                        deleteImage();
                    }
                }
            });
        }
    }

    public void deleteImage() {
        if (mapImg.containsKey(imgId)) {
            DialogUtil.showSimpleDialog(EditInformationActivity.this, "提示", "是否要删除这张图片", true, new DialogUtil.SimpleCallback() {
                @Override
                public void onConfirmClick() {
                    pickImg.setImageResource(0);
                    pickAddImg.setVisibility(View.VISIBLE);
                    mapImg.remove(imgId);
                    if (uploadDialog != null) {
                        uploadDialog.show();
                    }
                    updatePortrait();
                }

                @Override
                public void onConfirmClick(java.lang.String input) {

                }

                @Override
                public void onCancelClick() {

                }
            });
        }
    }

    /**
     * 更新资料图片
     */
    private void updatePortrait() {
        String portrait = "";
        for (int key : mapImg.keySet()) {
            portrait = portrait + mapImg.get(key) + ",";
        }
        if (!TextUtils.isEmpty(portrait) && portrait.endsWith(",")) {
            portrait = portrait.substring(0, portrait.length() - 1);
        }
        if (TextUtils.isEmpty(portrait)) {
            portrait = "-1";
        }
        HttpApiAppUser.user_update(null, null, null, null, null, -1, -1, null, -1, portrait, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (uploadDialog != null && uploadDialog.isShowing()) {
                    uploadDialog.dismiss();
                }
                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == NAME) {
                String name = data.getStringExtra("name");

                UserInfo userInfo = JMessageClient.getMyInfo();
                if (userInfo != null) {
                    String nickname = userInfo.getNickname();
                    if (!nickname.equals(name)) {
                        userInfo.setNickname(name);
                        JMessageClient.updateMyInfo(UserInfo.Field.nickname, userInfo, new BasicCallback() {
                            @Override
                            public void gotResult(int i, String s) {
                                L.e("极光IM", "更新极光用户昵称信息回调---gotResult--->code: " + i + " msg: " + s);
                            }
                        });
                    }
                }
                baseInfoList.get(0).value = name;
                baseinfoAdapter.notifyDataSetChanged();
            } else if (requestCode == PERSONAL) {
                String personal = data.getStringExtra("personal");
                baseInfoList.get(1).value = personal;
                baseinfoAdapter.notifyDataSetChanged();
                retModel.signature = personal;
            } else if (requestCode == BIRTHDAY) {
                String birthday = data.getStringExtra("birthday");
                String constellation = data.getStringExtra("constellation");
                personInfoList.get(0).value = birthday;
                personInfoList.get(1).value = constellation;
                personinfoAdapter.notifyDataSetChanged();
                retModel.birthday = birthday;
                constellationStr = constellation;
                ApiUserInfo apiUserInfo = SpUtil.getInstance().<ApiUserInfo>getModel(SpUtil.USER_INFO, ApiUserInfo.class);
                apiUserInfo.birthday = birthday;
                apiUserInfo.constellation = constellation;
                SpUtil.getInstance().putModel(SpUtil.USER_INFO, apiUserInfo);
            } else if (requestCode == HEIGHT) {
                String height = data.getStringExtra("height");
                personInfoList.get(3).value = height + "cm";
                personinfoAdapter.notifyDataSetChanged();
                retModel.height = Integer.parseInt(TextUtils.isEmpty(height) ? "0" : height);
            } else if (requestCode == WEIGHT) {
                String weight = data.getStringExtra("weight");
                personInfoList.get(4).value = weight + "KG";
                personinfoAdapter.notifyDataSetChanged();
                retModel.weight = Double.parseDouble(TextUtils.isEmpty(weight) ? "0" : weight);
            } else if (requestCode == PROFESSION) {
                String profession = data.getStringExtra("profession");
                personInfoList.get(2).value = profession;
                personinfoAdapter.notifyDataSetChanged();
                retModel.vocation = profession;
            } else if (requestCode == WX) {
                String wx = data.getStringExtra("wx");
                contactInfoList.get(1).value = wx;
                contactAdapter.notifyDataSetChanged();
                retModel.wechatNo = wx;
            } else if (requestCode == address) {
//                String address = data.getStringExtra(ARouterValueNameConfig.ADDRESS);
//                otherInfoList.get(1).value = address;
//                otherAdapter.notifyDataSetChanged();
            } else if (requestCode == Sign) {
                otherInfoList.get(0).value = data.getStringExtra("Name");
                otherAdapter.notifyDataSetChanged();
            }
        }
    }
}
