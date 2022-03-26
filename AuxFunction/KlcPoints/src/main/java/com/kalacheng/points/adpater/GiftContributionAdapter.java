package com.kalacheng.points.adpater;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.RanksDto;
import com.kalacheng.points.R;
import com.kalacheng.points.databinding.GiftcontributionItmeBinding;
import com.kalacheng.points.databinding.ProfitlistHeaditmeBinding;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class GiftContributionAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private static final int HEAD = 0;
    private static final int NORMAL = 1;
    private List<RanksDto> mList = new ArrayList<>();
    private List<RanksDto> Top = new ArrayList<>();

    public GiftContributionAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //加载更多
    public void setLoadData(List<RanksDto> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<RanksDto> data) {
        this.mList.clear();
        this.Top.clear();
        int size = data.size();
        if (size > 0) {
            Top.add(data.get(0));
        }
        if (size > 1) {
            Top.add(data.get(1));
        }
        if (size > 2) {
            Top.add(data.get(2));
        }
        if (size >= 3) {
            mList = data.subList(3, data.size());
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return HEAD;
        }
        return NORMAL;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == HEAD) {
            ProfitlistHeaditmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.profitlist_headitme, parent, false);
            GiftContributionHeadViewHolder headViewHolder = new GiftContributionHeadViewHolder(binding);
            return headViewHolder;
        } else {
            GiftcontributionItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.giftcontribution_itme, parent, false);
            GiftContributionViewHolder holder = new GiftContributionViewHolder(binding);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof GiftContributionViewHolder) {
            if (mList.size() > 0) {
                ((GiftContributionViewHolder) holder).setData(mList.get(position - 1), position - 1);
            }
        } else {
            ((GiftContributionHeadViewHolder) holder).setData();
        }

    }


    @Override
    public int getItemCount() {

        return mList.size() + 1;

    }

    public class GiftContributionViewHolder extends RecyclerView.ViewHolder {
        GiftcontributionItmeBinding binding;

        public GiftContributionViewHolder(@NonNull GiftcontributionItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final RanksDto bean, int position) {

            if (position == 0) {
                binding.viewItemDivider.setVisibility(View.GONE);
                binding.layoutListItemBg.setBackgroundResource(R.drawable.bg_white_top);
            } else if (position == (mList.size() - 1)) {
                binding.viewItemDivider.setVisibility(View.VISIBLE);
                binding.layoutListItemBg.setBackgroundResource(R.drawable.bg_white_bottom);
            } else {
                binding.viewItemDivider.setVisibility(View.VISIBLE);
                binding.layoutListItemBg.setBackgroundColor(Color.WHITE);
            }

            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.layoutListItemBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.userId).navigation();
                }
            });


        }
    }

    public class GiftContributionHeadViewHolder extends RecyclerView.ViewHolder {
        ProfitlistHeaditmeBinding headitmeBinding;

        public GiftContributionHeadViewHolder(ProfitlistHeaditmeBinding binding) {
            super(binding.getRoot());
            this.headitmeBinding = binding;
        }

        public void setData() {
            if (Top.size() == 0) {
                headitmeBinding.item1.setVisibility(View.GONE);
                headitmeBinding.item2.setVisibility(View.GONE);
                headitmeBinding.item3.setVisibility(View.GONE);
            } else if (Top.size() == 1) {
                headitmeBinding.item1.setVisibility(View.VISIBLE);
                headitmeBinding.item2.setVisibility(View.GONE);
                headitmeBinding.item3.setVisibility(View.GONE);

                if (Top.get(0).avatar == null || Top.get(0).avatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).avatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name1.setText(Top.get(0).username);
                headitmeBinding.votes1.setText((long) Top.get(0).delta + "");


            } else if (Top.size() == 2) {
                headitmeBinding.item1.setVisibility(View.VISIBLE);
                headitmeBinding.item2.setVisibility(View.VISIBLE);
                headitmeBinding.item3.setVisibility(View.GONE);

                if (Top.get(0).avatar == null || Top.get(0).avatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).avatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name1.setText(Top.get(0).username);
                headitmeBinding.votes1.setText((long) Top.get(0).delta + "");

                if (Top.get(1).avatar == null || Top.get(1).avatar.equals("")) {
                    headitmeBinding.avatar2.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(1).avatar, headitmeBinding.avatar2,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name2.setText(Top.get(1).username);
                headitmeBinding.votes2.setText((long) Top.get(1).delta + "");
            } else if (Top.size() == 3) {
                headitmeBinding.item1.setVisibility(View.VISIBLE);
                headitmeBinding.item2.setVisibility(View.VISIBLE);
                headitmeBinding.item3.setVisibility(View.VISIBLE);

                if (Top.get(0).avatar == null || Top.get(0).avatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).avatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name1.setText(Top.get(0).username);
                headitmeBinding.votes1.setText((long) Top.get(0).delta + "");

                if (Top.get(1).avatar == null || Top.get(1).avatar.equals("")) {
                    headitmeBinding.avatar2.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(1).avatar, headitmeBinding.avatar2,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name2.setText(Top.get(1).username);
                headitmeBinding.votes2.setText((long) Top.get(1).delta + "");

                if (Top.get(2).avatar == null || Top.get(2).avatar.equals("")) {
                    headitmeBinding.avatar3.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(2).avatar, headitmeBinding.avatar3,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name3.setText(Top.get(2).username);
                headitmeBinding.votes3.setText((long) Top.get(2).delta + "");
            }

            headitmeBinding.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(0).userId).navigation();
                }
            });
            headitmeBinding.item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(1).userId).navigation();
                }
            });
            headitmeBinding.item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(2).userId).navigation();
                }
            });
        }
    }
}
