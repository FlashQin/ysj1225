package com.kalacheng.centercommon.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.centercommon.R;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpClient;
import com.kalacheng.util.utils.BitmapUtil;

/**
 * 图片分享
 */
@Route(path = ARouterPath.SaveInvitationCodeActivity)
public class SaveInvitationCodeActivity extends BaseActivity implements View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.URL)
    public String url;
    RelativeLayout rlAll;
    ImageView ivAll;
    private String imgUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_code);
        initView();
        initData();
    }

    private void initView() {
        rlAll = findViewById(R.id.rl_all);
        ivAll = findViewById(R.id.iv_all);
        findViewById(R.id.btn_back).setOnClickListener(this);
        findViewById(R.id.btn_refresh).setOnClickListener(this);
        findViewById(R.id.btn_next).setOnClickListener(this);
        findViewById(R.id.btn_copy).setOnClickListener(this);

    }

    private void initData() {
        imgUrl = HttpClient.getInstance().getUrl() + "/api/support/getShareImg?_uid_=" + HttpClient.getUid() + "&_token_=" + HttpClient.getToken();
//        ImageLoader.display(imgUrl, ivAll);
        Glide.with(this).load(imgUrl).skipMemoryCache(true).diskCacheStrategy(DiskCacheStrategy.NONE).into(ivAll);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        } else if (view.getId() == R.id.btn_refresh) {
            initData();
        } else if (view.getId() == R.id.btn_next) {
            BitmapUtil.saveViewBitmapFile(ivAll, APPProConfig.PIC_PATH);
        } else if (view.getId() == R.id.btn_copy) {
            copy(this, url);
        }
    }

    public static void copy(Context context, String s) {
        try {
            // 获取系统剪贴板
            ClipboardManager clipboard = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            // 创建一个剪贴数据集，包含一个普通文本数据条目（需要复制的数据）
            ClipData clipData = ClipData.newPlainText(null, s);
            // 把数据集设置（复制）到剪贴板
            clipboard.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

}
