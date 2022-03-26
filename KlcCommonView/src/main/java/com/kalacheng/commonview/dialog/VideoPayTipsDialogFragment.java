package com.kalacheng.commonview.dialog;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.commonview.R;
import com.kalacheng.libuser.model.ApiShortVideoDto;
import com.kalacheng.util.utils.DecimalFormatUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.MaskImageView;

/**
 * @author: Administrator
 * @date: 2020/11/13
 * @info: 短视频 付费查看Dialog
 */
public class VideoPayTipsDialogFragment extends BaseDialogFragment {

    private VideoPayTipsChoiceListener listener;
    private ApiShortVideoDto apiShortVideoDto;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_video_pay_tips_layout;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return true;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        apiShortVideoDto = getArguments().getParcelable("ApiShortVideoDto");
        init();
    }

    private void init(){

        ImageView ivPic = mRootView.findViewById(R.id.ivPic);
        TextView tvRoomPayTips = mRootView.findViewById(R.id.tvRoomPayTips);
        TextView tvRoomPayMoney = mRootView.findViewById(R.id.tvRoomPayMoney);
        TextView tvOpenVip = mRootView.findViewById(R.id.tvOpenVip);
        ImageView ivPlay = mRootView.findViewById(R.id.ivPlay);

        if (null != apiShortVideoDto){
            ImageLoader.displayBlur(!TextUtils.isEmpty(apiShortVideoDto.thumb) ? apiShortVideoDto.thumb : apiShortVideoDto.avatar, ivPic);
            tvRoomPayTips.setText("花费 " + DecimalFormatUtils.isIntegerDouble(apiShortVideoDto.coin));
            if (apiShortVideoDto.type == 1){
                tvRoomPayMoney.setText("查看此视频");
            }else if (apiShortVideoDto.type == 2){
                tvRoomPayMoney.setText("查看此图片");
                ivPlay.setVisibility(View.GONE);
            }
            if (!TextUtils.isEmpty(apiShortVideoDto.privilegesLowestName)){
                tvOpenVip.setText("开通" + apiShortVideoDto.privilegesLowestName + "免费看");
            }else {
                tvOpenVip.setVisibility(View.GONE);
            }
        }

        // 关闭
        mRootView.findViewById(R.id.ivClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 开通贵族免费看
        mRootView.findViewById(R.id.tvOpenVip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.openVip();
                }
            }
        });

        // 付费观看
        mRootView.findViewById(R.id.tvVideoPay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener){
                    listener.pey();
                }
            }
        });
    }

    public void setListener(VideoPayTipsChoiceListener listener){
        this.listener = listener;
    }

    public interface VideoPayTipsChoiceListener{
        void openVip();
        void pey();
    }

}