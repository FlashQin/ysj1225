package com.kalacheng.shopping.buyer.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.shopping.R;
import com.kalacheng.shopping.buyer.adapter.CityStringAdapter;
import com.kalacheng.shopping.buyer.adapter.CityStringAdapter1;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.util.bean.AddressBean;

import java.util.ArrayList;
import java.util.List;

public class CityAreaFragment extends BaseDialogFragment implements CityStringAdapter.OnItemClickListenes
        ,CityStringAdapter1.OnItemClickListenes {

    ImageView closeIv;
    RecyclerView recyclerView;
    RecyclerView recyclerView1;

    CityStringAdapter adapter;
    CityStringAdapter1 adapter1;
    OnDismissListenes onDismissListenes;

    ArrayList<AddressBean> list;


    @Override
    protected int getLayoutId() {
        return R.layout.fragment_city_area;
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

        closeIv = mRootView.findViewById(R.id.closeIv);
        recyclerView = mRootView.findViewById(R.id.recyclerView);
        recyclerView1 = mRootView.findViewById(R.id.recyclerView1);
        list = new ArrayList<>();
        ArrayList<AddressBean> addressBeans = getArguments().getParcelableArrayList("addressBeans");
        if (addressBeans != null){
            createObj(addressBeans);
        }

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        adapter1 = new CityStringAdapter1();
        adapter1.setOnItemClickListenes(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter1);

        recyclerView1.setLayoutManager(new LinearLayoutManager(mContext));
        recyclerView1.setHasFixedSize(true);
        adapter = new CityStringAdapter(list);
        adapter.setOnItemClickListenes(this);
        recyclerView1.setAdapter(adapter);

        int level = list != null && list.size() > 0 ? 2 : 0;
        adapter1.setList(adapter.getList(), level);

    }

    @Override
    public void onItemClickListenes(int level) {
        adapter1.setList(adapter.getList(), level);
        if (level == 3) {
            closeIv.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (onDismissListenes != null) {
                        onDismissListenes.onDismiss(adapter.getList());
                    }
                    dismiss();
                }
            }, 500);
        }

    }

    @Override
    public void onItemClickListenes1(int level) {
        adapter.setLevel(level);
    }


    public void setOnDismissListenes(OnDismissListenes onDismissListenes) {
        this.onDismissListenes = onDismissListenes;
    }

    public interface OnDismissListenes {
        void onDismiss(List<AddressBean> list);
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (list!= null)list.clear();
    }

    private void createObj(ArrayList<AddressBean> addressBeans){
        for (AddressBean bean:addressBeans){
            AddressBean bean1 = null;
            try {
                bean1 = (AddressBean) bean.clone();
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
            list .add(bean1);
        }

    }
}
