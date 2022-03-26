package com.kalacheng.commonview.activity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import com.kalacheng.base.activty.BaseMVVMActivity;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.ActivityPictureChooseBinding;
import com.kalacheng.commonview.viewmodel.PictureChooseViewModel;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.commonview.adapter.PictureChooseAdapter;
import com.kalacheng.commonview.bean.PictureChooseBean;
import com.kalacheng.commonview.listener.OnPictureChooseItemClickListener;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cc.shinichi.library.ImagePreview;

public class PictureChooseActivity extends BaseMVVMActivity<ActivityPictureChooseBinding, PictureChooseViewModel> {
    public static final String PICTURE_CHOOSE_NUM = "PICTURE_CHOOSE_NUM";
    private int mMaxChooseNum;

    private ArrayList<PictureChooseBean> mChooseList = new ArrayList<>();

    private List<PictureChooseBean> filePath = new ArrayList<>();

    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_picture_choose;
    }

    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        mMaxChooseNum = getIntent().getIntExtra(PICTURE_CHOOSE_NUM, 0);

        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new GridLayoutManager(mContext, 4, GridLayoutManager.VERTICAL, false));
        ItemDecoration decoration = new ItemDecoration(mContext, 0x00000000, 3, 3);
        decoration.setOnlySetItemOffsetsButNoDraw(true);
        binding.recyclerView.addItemDecoration(decoration);

        initListeners();

        serchPhoto();
    }

    /**
     * 查询系统所有图片地址       
     */
    private void serchPhoto() {
        filePath.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                Uri mImageUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = getContentResolver();
                // 只查询jpeg和png的图片
                Cursor mCursor = mContentResolver.query(mImageUri, null,
                        MediaStore.Images.Media.MIME_TYPE + "=? or "
                                + MediaStore.Images.Media.MIME_TYPE + "=?"
                                + " or " + MediaStore.Images.Media.MIME_TYPE
                                + "=?", new String[]{"image/jpeg",
                                "image/png", "image/jpg"},
                        MediaStore.Images.Media.DATE_MODIFIED + " desc");

                Log.e("TAG", mCursor.getCount() + "===" + filePath.toString());
                while (mCursor.moveToNext()) {
                    // 获取图片的路径
                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Images.Media.DATA));
                    PictureChooseBean bean = new PictureChooseBean();
                    bean.setPath(path);
                    filePath.add(bean);
                }
                mCursor.close();
                Message msg = Message.obtain();
                msg.arg1 = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private static class InnerHandler extends Handler {
        private final WeakReference<PictureChooseActivity> mActivity;

        public InnerHandler(PictureChooseActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            PictureChooseActivity activity = mActivity.get();
            if (activity != null) {
                if (msg.arg1 == 1) {
                    activity.showPictures();
                }
            }
        }
    }

    private InnerHandler handler = new InnerHandler(this);

    private void showPictures() {
        if (filePath.size() > 0) {
            final PictureChooseAdapter adapter = new PictureChooseAdapter(mContext, filePath);
            binding.recyclerView.setAdapter(adapter);
            adapter.setOnItemClickListener(new OnPictureChooseItemClickListener<PictureChooseBean>() {
                @Override
                public void onItemClick(PictureChooseBean bean, int position) {
                    ImagePreview.getInstance().setContext(mContext).setImage(bean.getPath()).setShowDownButton(false).start();
                }

                @Override
                public void onItemSelect(PictureChooseBean bean, int position) {
                    if (bean.getNum() == 0) {
                        if (mChooseList.size() < mMaxChooseNum) {
                            bean.setNum(mChooseList.size() + 1);
                            mChooseList.add(bean);
                            adapter.notifyDataSetChanged();
                            binding.tvPictureChooseConfirm.setText(WordUtil.getString(R.string.picture_choose_confirm) + "(" + mChooseList.size() + ")");
                            binding.tvPictureChooseConfirm.setEnabled(true);
                        } else {
                            ToastUtil.show("最多选择" + mMaxChooseNum + "张图片");
                        }
                    } else {
                        bean.setNum(0);
                        mChooseList.remove(bean);
                        for (int i = 0; i < mChooseList.size(); i++) {
                            mChooseList.get(i).setNum(i + 1);
                        }
                        adapter.notifyDataSetChanged();
                        binding.tvPictureChooseConfirm.setText(WordUtil.getString(R.string.picture_choose_confirm) + "(" + mChooseList.size() + ")");
                        if (mChooseList.size() == 0) {
                            binding.tvPictureChooseConfirm.setEnabled(false);
                        }
                    }
                }

                @Override
                public void onItemDelete(PictureChooseBean bean, int position) {

                }
            });
        } else {
            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    private void initListeners() {
        binding.tvOther.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        binding.tvPictureChooseConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putParcelableArrayListExtra(ARouterValueNameConfig.PICTURE_LIST, mChooseList);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    protected boolean isStatusBarWhite() {
        return false;
    }
}
