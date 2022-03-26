package com.kalacheng.shopping.seller.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.kalacheng.busshop.model.ShopGoodsChannel;
import com.kalacheng.shopping.R;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;

import java.util.List;

public class ShopChannelSelectDialog {

    public static void showStringArrayDialog(Context context, List<ShopGoodsChannel> array, final DialogUtil.ChannelDialogCallback callback) {
        final Dialog dialog = new Dialog(context, com.kalacheng.util.R.style.dialog);
        dialog.setContentView(R.layout.dialog_shop_channel_select);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setWindowAnimations(com.kalacheng.util.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        LinearLayout container = (LinearLayout) dialog.findViewById(com.kalacheng.util.R.id.container);
        View.OnClickListener itemListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView textView = (TextView) v;
                if (callback != null) {
                    long id = (long) v.getTag();
                    callback.onItemClick(id, textView.getText().toString());
                }
                dialog.dismiss();
            }
        };
        for (int i = 0, length = array.size(); i < length; i++) {
            TextView textView = new TextView(context);
            textView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(54)));
            textView.setTextColor(0xff323232);
            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            textView.setGravity(Gravity.CENTER);
            textView.setText(array.get(i).goodsChannel);
            textView.setTag(array.get(i).id);
            textView.setOnClickListener(itemListener);
            container.addView(textView);
            if (i != length - 1) {
                View v = new View(context);
                v.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DpUtil.dp2px(1)));
                v.setBackgroundColor(0xfff5f5f5);
                container.addView(v);
            }
        }
        dialog.findViewById(com.kalacheng.util.R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
//        if (listener != null) {
//            dialog.setOnDismissListener(listener);
//        }
        dialog.show();
    }
}
