package com.kalacheng.main.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.buscommon.AppHomeHallDTO;
import com.kalacheng.commonview.utils.LookRoomUtlis;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.libuser.model.ApiUsersLine;
import com.kalacheng.main.R;
import com.kalacheng.main.databinding.ItemNearbyBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.glide.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NearbyAdapter extends RecyclerView.Adapter<NearbyAdapter.Holder> {

    List<ApiUsersLine> list;
    Context context;

    public NearbyAdapter() {
        list = new ArrayList<>();
    }

    public void refreshList(List<ApiUsersLine> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void loadMoreList(List<ApiUsersLine> list) {
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        ItemNearbyBinding binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.item_nearby, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {

    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, final int position, @NonNull List<Object> payloads) {
        Object payload = payloads.size() > 0 ? payloads.get(0) : null;
        if (payload == null) {
            holder.binding.setModel(list.get(position));
            if (list.get(position).isLocation == 0) {
                holder.binding.distanceTv.setVisibility(View.VISIBLE);
                holder.binding.distanceTv.setText(list.get(position).distance + "km");
            } else {
                holder.binding.distanceTv.setVisibility(View.GONE);
            }

            if (list.get(position).onlineStatus == 0 && !TextUtils.isEmpty(list.get(position).offLineTime)) {
                holder.binding.distanceTv.append("·" + list.get(position).offLineTime + "在线");
            }

            if (list.get(position).onlineStatus == 0) {
                holder.binding.followTv.setVisibility(View.GONE);
            } else {
                if (list.get(position).roomId > 0) {
                    holder.binding.followTv.setVisibility(View.VISIBLE);
                } else {
                    holder.binding.followTv.setVisibility(View.GONE);
                }
            }

            holder.binding.newrbyRe.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    ARouter.getInstance().build(ARouterPath.HomePage).withLong(ARouterValueNameConfig.ANCHORID, list.get(position).uid).navigation();
                }
            });

            holder.binding.followTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) {
                        return;
                    }
                    AppHomeHallDTO bean = new AppHomeHallDTO();
                    bean.liveType = 2;
                    bean.roomId = list.get(position).roomId;
                    bean.coin = 0;
                    bean.typeVal = list.get(position).typeVal;
                    bean.showid = list.get(position).showid;
                    bean.isPay = list.get(position).isPay;
                    LookRoomUtlis.getInstance().getData(bean, context);
                }
            });

            showConstellationTv(holder, list.get(position));

//            if (list.get(position).sex ==3){
//                holder.binding.constellationTv.setText(list.get(position).constellation+"/"+list.get(position).age+"/"+"0");
//            }else if(list.get(position).sex ==4){
//                holder.binding.constellationTv.setText(list.get(position).constellation+"/"+list.get(position).age+"/"+"0.5");
//
//            }else if(list.get(position).sex ==1){
//                holder.binding.constellationTv.setText(list.get(position).constellation+"/"+list.get(position).age+"/"+"1");
//
//            }else {
//                holder.binding.constellationTv.setText(list.get(position).constellation+"/"+list.get(position).age+"/"+"...");
//
//            }

            ImageLoader.display(list.get(position).avatar, holder.binding.avatarIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
            holder.binding.statueIv.setImageResource(list.get(position).onlineStatus == 0 ? R.mipmap.icon_lixian : R.mipmap.icon_zaixian);
        }
    }

    /**
     * 设置 星座标记
     *
     * @param holder 控件
     * @param data   实体数据
     */
    private void showConstellationTv(Holder holder, ApiUsersLine data) {
        if (isSexNormal()) {
            //正常
            holder.binding.constellationTv.setText(data.constellation + "/" + data.age);
        } else {
            holder.binding.constellationTv.setText(data.constellation + "/" + data.age + "/" + addSexTag(data.sex));
        }
    }

    /**
     * 性别偏好拼接
     *
     * @param sexIndex 性别标识
     * @return 返回 性别偏好拼接
     */
    private String addSexTag(int sexIndex) {

        String sexTag = "";

        switch (sexIndex) {
            case 3:
                //0
                sexTag = "0";
                break;
            case 1:
                //1
                sexTag = "1";
                break;
            case 4:
                //0.5
                sexTag = "0.5";
                break;
            case 5:
            default:
                //...
                sexTag = "...";
                break;
        }

        return sexTag;
    }

    /**
     * 是否为正常性别展示
     *
     * @return true 正常
     */
    private boolean isSexNormal() {
        return ConfigUtil.getBoolValue(R.bool.sexNormal);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class Holder extends RecyclerView.ViewHolder {
        ItemNearbyBinding binding;

        public Holder(@NonNull ItemNearbyBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

}
