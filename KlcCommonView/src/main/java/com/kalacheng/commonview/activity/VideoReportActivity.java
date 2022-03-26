package com.kalacheng.commonview.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.adapter.ReportAdpater;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.model.AppUsersVideoReportClassify;
import com.kalacheng.util.utils.ToastUtil;

import java.util.List;

/**
 * 举报
 */
@Route(path = ARouterPath.VideoReport)
public class VideoReportActivity extends BaseActivity implements View.OnClickListener {

    private long ClasslyID = -1;
    private String mContent;

    @Autowired(name = ARouterValueNameConfig.USERID)
    public long UserID;

    private ReportAdpater adpater;
    private EditText content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.video_report);
        initData();
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }


    @SuppressLint("WrongConstant")
    public void initData() {
        ImageView btn_back = findViewById(R.id.btn_back);
        btn_back.setOnClickListener(this);

        TextView submit = findViewById(R.id.submit);
        submit.setOnClickListener(this);

        content = findViewById(R.id.content);

        RecyclerView Report_List = findViewById(R.id.Report_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        Report_List.setLayoutManager(manager);

        adpater = new ReportAdpater(mContext);
        Report_List.setAdapter(adpater);

        adpater.setReportItmeCallBack(new ReportAdpater.ReportItmeCallBack() {
            @Override
            public void onClick(int poistion) {
                adpater.setPositontslect(poistion);
                ClasslyID = adpater.getItemId(poistion);
            }
        });
        getClassly();
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.btn_back) {
            finish();
        } else if (view.getId() == R.id.submit) {
            Submit();
        }
    }

    //获取举报类型
    public void getClassly() {
        HttpApiAppUser.getUsersReportClassifyList(new HttpApiCallBackArr<AppUsersVideoReportClassify>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppUsersVideoReportClassify> retModel) {
                if (code == 1 && null != retModel) {
                    adpater.setData(retModel);
                }
            }
        });
    }

    //提交
    public void Submit() {
        if (ClasslyID == -1) {
            ToastUtil.show("请选择举报类型");
            return;
        }
        mContent = content.getText().toString();
        HttpApiAppUser.usersReport(ClasslyID, mContent, UserID, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show(msg);
                }
                finish();
            }
        });
    }
}
