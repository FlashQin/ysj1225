package com.kalacheng.shopping.buyer.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopAddress;
import com.kalacheng.busshop.model_fun.ShopCar_updateShopAddress;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.activity.AddressListActivity;
import com.kalacheng.shopping.databinding.ItemAddressBinding;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import cn.we.swipe.helper.WeSwipeHelper;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.Holder> {
    private AddressListActivity activity;
    private Context context;
    private List<ShopAddress> list = new ArrayList<>();
    private long addressId;
    private OnClickSelectListenes onClickSelectListenes;

    public AddressAdapter(long addressId) {
        this.addressId = addressId;
    }

    public void setActivity(AddressListActivity activity) {
        this.activity = activity;
    }

    public void setList(List<ShopAddress> list) {
        this.list.clear();
        if (list != null) {
            this.list.addAll(list);
        }
        notifyDataSetChanged();
    }

    public void setOnClickSelectListenes(OnClickSelectListenes onClickSelectListenes) {
        this.onClickSelectListenes = onClickSelectListenes;
    }

    private void setDefault(int index) {
//        for (int i = 0; i < list.size(); i++) {
//            list.get(i).isDefault = i == index ? 1 : 0;
//        }
//        upDataAddress(list.get(index));
        if (list.size() > index) {
            this.addressId = list.get(index).id;
        }
        notifyDataSetChanged();

        if (onClickSelectListenes != null) {
            onClickSelectListenes.onClickSelectListenes(index);
        }

    }

    public ShopAddress getItem(int index) {
        if (list.size() > index) {
            return list.get(index);
        }
        return null;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        ItemAddressBinding binding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.item_address, parent, false);
        return new Holder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull final Holder holder, int position) {
        holder.binding.setAddress(list.get(holder.getBindingAdapterPosition()));
        if (list.get(position).id == addressId) {
            holder.binding.isDefaultIv.setVisibility(View.VISIBLE);
        } else {
            holder.binding.isDefaultIv.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class Holder extends RecyclerView.ViewHolder implements WeSwipeHelper.SwipeLayoutTypeCallBack {
        ItemAddressBinding binding;

        public Holder(final ItemAddressBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

            binding.item1Rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    setDefault(getBindingAdapterPosition());
                }
            });

            binding.editTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;
                    ARouter.getInstance().build(ARouterPath.AddAddressActivity)
                            .withParcelable(ARouterValueNameConfig.shopAddress, binding.getAddress())
                            .navigation(activity, 1001);
                }
            });

            binding.delTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    delAddress(binding.getAddress().id);
                    list.remove(getBindingAdapterPosition());
                    notifyItemRemoved(getBindingAdapterPosition());
                    notifyItemRangeChanged(getBindingAdapterPosition(), list.size() - 1);
                }
            });
        }

        @Override
        public float getSwipeWidth() {
            return binding.item2Ll.getWidth();
        }

        @Override
        public View needSwipeLayout() {
            return binding.itemLl;
        }

        @Override
        public View onScreenView() {
            return binding.item1Rl;
        }
    }

    private void delAddress(long id) {

        HttpApiShopCar.delShopAddress(id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("删除成功");
                }
            }
        });

    }

    private void upDataAddress(ShopAddress address) {
        ShopCar_updateShopAddress updateAddress = new ShopCar_updateShopAddress();
        updateAddress.addressId = address.id;
        updateAddress.isDefault = address.isDefault;
        HttpApiShopCar.updateShopAddress(updateAddress, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("设置默认成功");
                }
            }
        });
    }


    public interface OnClickSelectListenes {

        void onClickSelectListenes(int index);

    }


}
