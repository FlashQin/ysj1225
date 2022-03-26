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
import com.kalacheng.buscommon.model.GuildListVO;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.points.R;
import com.kalacheng.points.databinding.FamilyListHeadItmeBinding;
import com.kalacheng.points.databinding.FamilyListItmeBinding;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @date: 2020/11/24
 * @info:
 */
public class RankFamilyAdapter  extends RecyclerView.Adapter {

    private Context mContext;
    private static final int HEAD = 0;
    private static final int NORMAL = 1;
    private List<GuildListVO> mList = new ArrayList<>();
    private List<GuildListVO> Top = new ArrayList<>();

    public RankFamilyAdapter(Context mContext) {
        this.mContext = mContext;
    }

    //加载更多
    public void setLoadData(List<GuildListVO> data) {
        this.mList.addAll(data);
        notifyDataSetChanged();
    }

    //刷新数据
    public void setRefreshData(List<GuildListVO> data) {
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

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == HEAD) {
            FamilyListHeadItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.family_list_head_itme, parent, false);
            RankFamilyHeadViewHolder headViewHolder = new RankFamilyHeadViewHolder(binding);
            return headViewHolder;
        } else {
            FamilyListItmeBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.family_list_itme, parent, false);
            RankFamilyViewHolder holder = new RankFamilyViewHolder(binding);
            return holder;
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof RankFamilyViewHolder) {
            if (mList.size() > 0) {
                ((RankFamilyViewHolder) holder).setData(mList.get(position - 1), position - 1);
            }
        } else {
            ((RankFamilyHeadViewHolder) holder).setData();
        }
    }

    public class RankFamilyViewHolder extends RecyclerView.ViewHolder {
        FamilyListItmeBinding binding;

        public RankFamilyViewHolder(@NonNull FamilyListItmeBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void setData(final GuildListVO bean, int position) {
            if (mList.size() == 1) {
                binding.viewItemDivider.setVisibility(View.GONE);
                binding.layoutListItemBg.setBackgroundResource(R.drawable.bg_white_top_bottom);
            } else {
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
            }

            binding.setViewModel(bean);
            binding.executePendingBindings();

            binding.layoutListItemBg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, bean.guildId).navigation();
                }
            });
        }
    }

    public class RankFamilyHeadViewHolder extends RecyclerView.ViewHolder {
        FamilyListHeadItmeBinding headitmeBinding;

        public RankFamilyHeadViewHolder(FamilyListHeadItmeBinding binding) {
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

                if (Top.get(0).guildAvatar == null || Top.get(0).guildAvatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).guildAvatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }

                headitmeBinding.name1.setText(Top.get(0).guildName);
                headitmeBinding.votes1.setText((long) Top.get(0).guildTotalVotes + "");

            } else if (Top.size() == 2) {
                headitmeBinding.item1.setVisibility(View.VISIBLE);
                headitmeBinding.item2.setVisibility(View.VISIBLE);
                headitmeBinding.item3.setVisibility(View.GONE);

                if (Top.get(0).guildAvatar == null || Top.get(0).guildAvatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).guildAvatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name1.setText(Top.get(0).guildName);
                headitmeBinding.votes1.setText((long) Top.get(0).guildTotalVotes + "");

                if (Top.get(1).guildAvatar == null || Top.get(1).guildAvatar.equals("")) {
                    headitmeBinding.avatar2.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(1).guildAvatar, headitmeBinding.avatar2,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name2.setText(Top.get(1).guildName);
                headitmeBinding.votes2.setText(((int) Top.get(1).guildTotalVotes) + "");
            } else if (Top.size() == 3) {
                headitmeBinding.item1.setVisibility(View.VISIBLE);
                headitmeBinding.item2.setVisibility(View.VISIBLE);
                headitmeBinding.item3.setVisibility(View.VISIBLE);

                if (Top.get(0).guildAvatar == null || Top.get(0).guildAvatar.equals("")) {
                    headitmeBinding.avatar1.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(0).guildAvatar, headitmeBinding.avatar1,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name1.setText(Top.get(0).guildName);
                headitmeBinding.votes1.setText((long) Top.get(0).guildTotalVotes + "");

                if (Top.get(1).guildAvatar == null || Top.get(1).guildAvatar.equals("")) {
                    headitmeBinding.avatar2.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(1).guildAvatar, headitmeBinding.avatar2,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name2.setText(Top.get(1).guildName);
                headitmeBinding.votes2.setText((long) Top.get(1).guildTotalVotes + "");

                if (Top.get(2).guildAvatar == null || Top.get(2).guildAvatar.equals("")) {
                    headitmeBinding.avatar3.setImageResource(R.mipmap.ic_launcher);
                } else {
                    ImageLoader.display(Top.get(2).guildAvatar, headitmeBinding.avatar3,R.mipmap.ic_launcher,R.mipmap.ic_launcher);
                }
                headitmeBinding.name3.setText(Top.get(2).guildName);
                headitmeBinding.votes3.setText((long) Top.get(2).guildTotalVotes + "");
            }

            headitmeBinding.item1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(0).guildId).navigation();
                }
            });
            headitmeBinding.item2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(1).guildId).navigation();
                }
            });
            headitmeBinding.item3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, Top.get(2).guildId).navigation();
                }
            });
        }
    }

}