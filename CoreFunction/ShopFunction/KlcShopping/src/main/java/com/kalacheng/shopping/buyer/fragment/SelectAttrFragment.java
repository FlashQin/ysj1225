package com.kalacheng.shopping.buyer.fragment;

import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshop.httpApi.HttpApiShopCar;
import com.kalacheng.busshop.model.ShopAttrCompose;
import com.kalacheng.busshop.model.ShopGoods;
import com.kalacheng.busshop.model.ShopGoodsAttrDTO;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.adapter.FlowAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.List;

public class SelectAttrFragment extends BaseDialogFragment {

    List<ShopGoodsAttrDTO> attrDTOList;
    List<ShopAttrCompose> composeList;
    ShopGoods shopGoods;
    int minIndex = 0;
    int num = 1;
    int viewType = 0;
    int stock = 0; //库存

    TextView attr1Tv;
    TagFlowLayout flowLayout1;

    LinearLayout attr1Ll;
    LinearLayout attr2Ll;
    TextView attr2Tv;
    TagFlowLayout flowLayout2;

    TextView reduceTv;
    TextView numTv;
    TextView addTv;

    TextView goodsPriceTv;
    TextView price2Tv;
    TextView countTv;
    TextView attrTv;

    RoundedImageView goodIv;

    TextView addCartTv;
    TextView addOrderTv;

    FlowAdapter adapter1;
    FlowAdapter adapter2;

    OnClickTagListener listener;
    OnBuyNowListener onBuyNowListener;
    OnEditListener onEditListener;
    AddCartlistener addCartlistener;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_select_attr;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        attrDTOList = getArguments().getParcelableArrayList("attrDTOList");
        composeList = getArguments().getParcelableArrayList("composeList");
        minIndex = getArguments().getInt("minIndex");
        shopGoods = getArguments().getParcelable("shopGoods");
        num = getArguments().getInt("num");
        viewType = getArguments().getInt("viewType");

        goodIv = mRootView.findViewById(R.id.goodIv);
        ImageLoader.display(shopGoods.goodsPicture.split(",")[0], goodIv, R.mipmap.ic_launcher, R.mipmap.ic_launcher);

        goodsPriceTv = mRootView.findViewById(R.id.goodsPriceTv);
        price2Tv = mRootView.findViewById(R.id.price2Tv);
        price2Tv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        countTv = mRootView.findViewById(R.id.countTv);
        attrTv = mRootView.findViewById(R.id.attrTv);

        if (composeList == null || composeList.size() == 0) {
            if (shopGoods.channelId == 1) {
                if (shopGoods.favorablePrice > 0) {
                    goodsPriceTv.setText("¥ " + shopGoods.favorablePrice);
                    price2Tv.setText("¥ " + shopGoods.price);
                } else {
                    goodsPriceTv.setText("¥ " + shopGoods.price);
                }
            } else {
                goodsPriceTv.setText("¥ " + shopGoods.price);
            }
            //countTv.setText("库存 " + composeList.stock + " 件");
            countTv.setVisibility(View.GONE);
            attrTv.setText(shopGoods.goodsName);
        } else {
            setPrice(composeList.get(minIndex));

            attr1Ll = mRootView.findViewById(R.id.attr1Ll);
            attr1Tv = mRootView.findViewById(R.id.attr1Tv);
            flowLayout1 = mRootView.findViewById(R.id.flowLayout1);
            flowLayout1.setMaxSelectCount(1);
            flowLayout1.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (adapter1 != null) {
                        for (int i = 0; i < composeList.size(); i++) {
                            if (composeList.get(i).attribute1Id == adapter1.getItem(position).id && composeList.get(i).attribute2Id == composeList.get(minIndex).attribute2Id) {
                                minIndex = i;
                                setPrice(composeList.get(i));
                                if (listener != null)
                                    listener.onClickTagListener(i, num);
                            }
                        }
                    }
                    return false;
                }
            });

            attr2Ll = mRootView.findViewById(R.id.attr2Ll);
            attr2Tv = mRootView.findViewById(R.id.attr2Tv);
            flowLayout2 = mRootView.findViewById(R.id.flowLayout2);
            flowLayout2.setMaxSelectCount(1);
            flowLayout2.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                @Override
                public boolean onTagClick(View view, int position, FlowLayout parent) {
                    if (adapter2 != null) {
                        for (int i = 0; i < composeList.size(); i++) {
                            if (composeList.get(i).attribute2Id == adapter2.getItem(position).id && composeList.get(i).attribute1Id == composeList.get(minIndex).attribute1Id) {
                                minIndex = i;
                                setPrice(composeList.get(i));
                                if (listener != null)
                                    listener.onClickTagListener(i, num);
                            }
                        }
                    }
                    return false;
                }
            });

            if (attrDTOList != null) {

                if (attrDTOList.size() > 0) {
                    attr1Ll.setVisibility(View.VISIBLE);
                    attr1Tv.setText(attrDTOList.get(0).attrName);

                    int selected = 0;
                    for (int i = 0; i < attrDTOList.get(0).attrValueList.size(); i++) {
                        if (composeList.get(minIndex).attribute1Id == attrDTOList.get(0).attrValueList.get(i).id) {
                            selected = i;
                        }
                    }
                    adapter1 = new FlowAdapter(attrDTOList.get(0).attrValueList);
                    adapter1.setSelectedList(selected);
                    flowLayout1.setAdapter(adapter1);
                }

                if (attrDTOList.size() > 1) {
                    attr2Ll.setVisibility(View.VISIBLE);
                    attr2Tv.setText(attrDTOList.get(1).attrName);

                    int selected = 0;
                    for (int i = 0; i < attrDTOList.get(1).attrValueList.size(); i++) {
                        if (composeList.get(minIndex).attribute2Id == attrDTOList.get(1).attrValueList.get(i).id) {
                            selected = i;
                        }
                    }
                    adapter2 = new FlowAdapter(attrDTOList.get(1).attrValueList);
                    adapter2.setSelectedList(selected);
                    flowLayout2.setAdapter(adapter2);
                }

            }

        }

        reduceTv = mRootView.findViewById(R.id.reduceTv);
        numTv = mRootView.findViewById(R.id.numTv);
        addTv = mRootView.findViewById(R.id.addTv);
        numTv.setText(String.valueOf(num));
        reduceTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (num > 1) {
                    num--;
                    numTv.setText(String.valueOf(num));
                    reduceTv.setTextColor(Color.parseColor("#666666"));
                    if (listener != null)
                        listener.onClickTagListener(minIndex, num);
                } else {
                    reduceTv.setTextColor(Color.parseColor("#EAEAEA"));
                }

            }
        });
        addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (num < stock){
                num++;
                numTv.setText(String.valueOf(num));
                reduceTv.setTextColor(Color.parseColor("#666666"));
                if (listener != null)
                    listener.onClickTagListener(minIndex, num);
//                }else {
//                    ToastUtil.show("库存不足了");
//                }

            }
        });

        mRootView.findViewById(R.id.closeIv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        addCartTv = mRootView.findViewById(R.id.addCartTv);
        addOrderTv = mRootView.findViewById(R.id.addOrderTv);

        if (viewType == 1) {
            addOrderTv.setVisibility(View.GONE);
        }
        if (viewType == 2) {
            addCartTv.setVisibility(View.GONE);
        }
        if (viewType == 3) {
            addCartTv.setVisibility(View.GONE);
            addOrderTv.setText("确定");
        }

        addCartTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                addCart();
            }
        });

        addOrderTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (onBuyNowListener != null) {
                    onBuyNowListener.onBuyNowListener();
                }
                if (onEditListener != null) {
                    onEditListener.onEditListener(composeList.get(minIndex), num);
                }
                dismiss();
            }
        });

    }

    private void setPrice(ShopAttrCompose compose) {
        if (compose.favorablePrice > 0) {
            goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(compose.favorablePrice));
            price2Tv.setText("¥ " + compose.price);
        } else {
            goodsPriceTv.setText("¥ " + DecimalFormatUtils.toTwo(compose.price));
            price2Tv.setText("");
        }
//        stock = compose.stock;
        countTv.setText("库存 " + compose.stock + " 件");
        attrTv.setText("已选：");
        if (!TextUtils.isEmpty(compose.name1))
            attrTv.append("“" + compose.name1 + "” ");
        if (!TextUtils.isEmpty(compose.name2))
            attrTv.append("“" + compose.name2 + "” ");
    }

    private void addCart() {
        long id = 0;
        if (composeList != null && composeList.size() > 0) {
            id = composeList.get(minIndex).id;
        }
        HttpApiShopCar.saveShopCar(id, shopGoods.id, num, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    if (addCartlistener != null) {
                        addCartlistener.addCartlistener();
                    }
                    ToastUtil.show("成功加入购物车");
                    dismiss();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });

    }

    public void setListener(OnClickTagListener listener) {
        this.listener = listener;
    }

    public void setOnBuyNowListener(OnBuyNowListener listener) {
        this.onBuyNowListener = listener;
    }

    public void setOnEditListener(OnEditListener onEditListener) {
        this.onEditListener = onEditListener;
    }

    public void setAddCartlistener(AddCartlistener addCartlistener) {
        this.addCartlistener = addCartlistener;
    }

    public interface OnClickTagListener {
        void onClickTagListener(int index, int num);
    }

    public interface OnBuyNowListener {
        void onBuyNowListener();
    }

    public interface OnEditListener {
        void onEditListener(ShopAttrCompose compose, int num);
    }

    public interface AddCartlistener {
        void addCartlistener();
    }

}
