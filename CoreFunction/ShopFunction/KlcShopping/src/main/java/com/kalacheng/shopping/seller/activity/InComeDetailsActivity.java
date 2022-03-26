package com.kalacheng.shopping.seller.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseActivity;
import com.kalacheng.shopping.databinding.ActivityIncomeDetailsBinding;
import com.kalacheng.shopping.seller.adapter.InComeDetailsAdapter;
import com.kalacheng.shopping.seller.viewmodel.InComeDetailsViewModel;
import com.kalacheng.util.view.BubblePopupWindow;

/**
 * 收入明细（废弃）
 */
public class InComeDetailsActivity extends SBaseActivity<ActivityIncomeDetailsBinding, InComeDetailsViewModel> {

    InComeDetailsAdapter adapter;

    BubblePopupWindow bubbleView;

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_income_details;
    }

    @Override
    public void initData() {
        super.initData();

        adapter = new InComeDetailsAdapter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adapter);

        binding.moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPop();
            }
        });

        if (bubbleView == null) {
            bubbleView = new BubblePopupWindow(this);
            View view = LayoutInflater.from(this).inflate(R.layout.popu_more_income, bubbleView.getBubbleView(), false);
            TextView tvAll = view.findViewById(R.id.tv_all);
            tvAll.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ShoppingCartActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvDay7 = view.findViewById(R.id.tv_day_7);
            tvDay7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvDay15 = view.findViewById(R.id.tv_day_15);
            tvDay15.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvMonth1 = view.findViewById(R.id.tv_month_1);
            tvMonth1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            TextView tvMonth3 = view.findViewById(R.id.tv_month_3);
            tvMonth3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ARouter.getInstance().build(ARouterPath.ConversationListActivity).navigation();
                    bubbleView.dismiss();
                }
            });
            bubbleView.setBubbleView(view);
        }


    }

    private void showPop() {
        bubbleView.show(binding.moreIv, Gravity.BOTTOM, 320);
    }
}
