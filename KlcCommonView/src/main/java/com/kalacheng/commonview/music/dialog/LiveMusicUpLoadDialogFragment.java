package com.kalacheng.commonview.music.dialog;


import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.music.adpater.MusicChooseAdapter;
import com.kalacheng.commonview.music.bean.MusicChooseBean;
import com.kalacheng.commonview.music.listener.OnMusicChooseItemClickListener;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppMusic;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.WordUtil;
import com.kalacheng.util.view.ItemDecoration;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 上传音乐
 */
public class LiveMusicUpLoadDialogFragment extends DialogFragment {

    private static String TAG = LiveMusicUpLoadDialogFragment.class.getSimpleName();
    protected Context mContext;
    private Map musicMap = new HashMap();

    public LiveMusicUpLoadDialogFragment(Context mContext) {
        this.mContext = mContext;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(true);//设置点击外部Dialog消失
        View view = inflater.inflate(R.layout.livemusicupload, null, false);

        initData(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NORMAL, R.style.dialog2);
    }

    RecyclerView recyclerView;

    public void initData(View view) {
        try {
            String strs = (String) SpUtil.getInstance().getSharedPreference(SpUtil.MUSIC_UP_LOAD, "");
            if (!TextUtils.isEmpty(strs)){
                musicMap = new Gson().fromJson(strs, Map.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));

        ImageView btn_back = view.findViewById(R.id.btn_back);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        serchMusic();
    }


    private List<MusicChooseBean> filePath = new ArrayList<>();

    private static class InnerHandler extends Handler {
        private final WeakReference<LiveMusicUpLoadDialogFragment> mActivity;

        public InnerHandler(LiveMusicUpLoadDialogFragment activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            LiveMusicUpLoadDialogFragment activity = mActivity.get();
            if (activity != null) {
                if (msg.arg1 == 1) {
                    activity.showPictures();
                }
            }
        }
    }

    private InnerHandler handler = new InnerHandler(this);

    /**
     * 查询系统所有图片地址       
     */
    private void serchMusic() {
        filePath.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                //查询音乐
                Uri mImageUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                ContentResolver mContentResolver = mContext.getContentResolver();
                String selection = MediaStore.Audio.Media.MIME_TYPE + "=? ";
                String[] selectionArgs = new String[]{"audio/mpeg"};
                Cursor mCursor = mContentResolver.query(mImageUri, null, selection, selectionArgs, MediaStore.Audio.Media.DATE_MODIFIED);
                while (mCursor.moveToNext()) {

                    String path = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DATA));
                    int duration = mCursor.getInt(mCursor.getColumnIndex(MediaStore.Audio.Media.DURATION));
                    String name = mCursor.getString(mCursor.getColumnIndex(MediaStore.Audio.Media.DISPLAY_NAME));
                    String author = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST));
                    String tilte = mCursor.getString(mCursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE));

                    if (!TextUtils.isEmpty(name) && duration > 0) {
                        MusicChooseBean bean = new MusicChooseBean();
                        bean.setPath(path);

                        bean.setName(!TextUtils.isEmpty(tilte) ? tilte : name);
                        bean.setAuthor(author);
                        bean.setDuration(duration);
                        if (null != musicMap && musicMap.containsKey(bean.getName())){
                            bean.setTv_upload_type(1);
                        }
                        filePath.add(bean);
                    }
                }

                Log.e(TAG, "=== 本地获取音乐曲数 ===" + mCursor.getCount());

                mCursor.close();
                // 通知Handler扫描图片完成
                mCursor.close();


                Message msg = Message.obtain();
                msg.arg1 = 1;
                handler.sendMessage(msg);
            }
        }).start();
    }

    private MusicChooseAdapter adapter;

    private void showPictures() {
        if (filePath.size() > 0) {
            adapter = new MusicChooseAdapter(mContext, filePath);
            adapter.setShowSelect(true);
            recyclerView.setAdapter(adapter);
            ItemDecoration decoration = new ItemDecoration(mContext, ContextCompat.getColor(mContext, R.color.textColorE), 3, 1);
//        decoration.setOnlySetItemOffsetsButNoDraw(false);
            recyclerView.addItemDecoration(decoration);
            adapter.setOnItemClickListener(new OnMusicChooseItemClickListener<MusicChooseBean>() {
                @Override
                public void onItemClick(MusicChooseBean bean, int position) {
                    if (CheckDoubleClick.isFastDoubleClick()) return;

                    L.e(TAG, "onItemClick  position  " + position + "  " + bean.getName());
                }

                @Override
                public void onItemSelect(MusicChooseBean bean, int position) {
                    L.e(TAG, "onItemSelect  position  " + position + "  " + bean.getName() + "  " + bean.getPath());

                    upLoad(bean, position);
                }

                @Override
                public void onItemDelete(MusicChooseBean bean, int position) {
                    L.e(TAG, "onItemDelete  position  " + position + "  " + bean.getName());
                }
            });
        } else {
//            binding.noData.setVisibility(View.VISIBLE);
        }
    }

    /*--------- 上传音乐 S ------------------------------------------------------------------------------------*/

    private void changeUploadType(int type, int position) {

        adapter.setStartUpLoad(type == 0);

        adapter.getList().get(position).setTv_upload_type(type);
        adapter.notifyDataSetChanged();
    }

    private Dialog mLoading;

    private void upLoad(final MusicChooseBean bean, final int position) {
        mLoading = DialogUtil.loadingDialog(mContext, R.style.dialog2, com.kalacheng.util.R.layout.dialog_loading, false, false, WordUtil.getString(R.string.video_pub_ing));

        if (mLoading != null) {
            mLoading.show();
        }
        changeUploadType(0, position);

        UploadUtil.getInstance().uploadPicture(2, new File(bean.getPath()), new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                if (!TextUtils.isEmpty(imgStr)) {
                    //得到 上传成功的  外链地址  调用自己服务器的上传接口
                    L.e(TAG, "音乐上传成功   imgStr  " + imgStr);
                    bean.setMusic_url(imgStr);
                    uploadMusic(bean, position);
                    if (!musicMap.containsKey(bean.getName())){
                        musicMap.put(bean.getName(), position);
                        SpUtil.getInstance().putModel(SpUtil.MUSIC_UP_LOAD, musicMap);
                    }
                } else {
                    if (mLoading != null && mLoading.isShowing()) {
                        mLoading.dismiss();
                    }
                    ToastUtil.show("上传失败");
                    changeUploadType(-2, position);
                }
            }

            @Override
            public void onFailure() {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }
                ToastUtil.show("上传失败");
                changeUploadType(-2, position);
            }
        });
    }

    /**
     * 上传音乐
     *
     * @param bean
     */
    private void uploadMusic(MusicChooseBean bean, final int position) {

        HttpApiAppMusic.uploadMusic(bean.getMusicData(), new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (mLoading != null && mLoading.isShowing()) {
                    mLoading.dismiss();
                }

                if (!TextUtils.isEmpty(msg)) {
                    ToastUtil.show(msg);
                }

                if (code == 1) {
                    changeUploadType(1, position);
                } else {
                    changeUploadType(-2, position);
                }

            }
        });
    }


    /*--------- 上传音乐 E ------------------------------------------------------------------------------------*/


    @Override
    public void onStart() {
        super.onStart();

        Window window = getDialog().getWindow();
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() * 4 / 7;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);

    }
}
