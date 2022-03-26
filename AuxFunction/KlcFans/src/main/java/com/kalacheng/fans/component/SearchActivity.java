package com.kalacheng.fans.component;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.buscommon.model.LiveBean;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.fans.R;
import com.kalacheng.fans.adapter.SearchAdpater;
import com.kalacheng.fans.databinding.SearchlBinding;
import com.kalacheng.fans.viewmodel.SearchViewModel;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

import java.util.List;

/**
 * 搜索
 */
@Route(path = ARouterPath.Search)
public class SearchActivity extends BaseMVVMActivity<SearchlBinding, SearchViewModel> {

    private SearchAdpater adpater;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.searchl;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.refreshView.setLayoutManager(manager);

        adpater = new SearchAdpater(mContext);
        binding.refreshView.setAdapter(adpater);
        binding.edit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!TextUtils.isEmpty(editable.toString())) {
                    binding.ivTxtClear.setVisibility(View.VISIBLE);
                    getSearchData(editable.toString());
                } else {
                    binding.ivTxtClear.setVisibility(View.GONE);
                }
            }
        });

        binding.ivTxtClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                binding.edit.setText("");
            }
        });
    }

    public void getSearchData(String key) {
        HttpApiAppUser.lobby(key, 0, 20, 0, new HttpApiCallBackPageArr<LiveBean>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<LiveBean> retModel) {
                if (code == 1) {
                    adpater.load(retModel);
                } else {
                    ToastUtil.show(msg);
                }

            }
        });

    }
}
