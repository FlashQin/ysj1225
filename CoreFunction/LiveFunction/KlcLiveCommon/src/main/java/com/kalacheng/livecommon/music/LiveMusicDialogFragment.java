package com.kalacheng.livecommon.music;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.libuser.httpApi.HttpApiAppVideo;
import com.kalacheng.libuser.model.ApiMusic;
import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackPageArr;
import com.kalacheng.base.http.PageInfo;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.music.db.MusicDbManager;
import com.kalacheng.util.utils.download.DownloadUtil;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.FileUtil;

import java.io.File;
import java.util.List;


/**
 * Created by cxf on 2017/9/2.
 */

public class LiveMusicDialogFragment extends BaseDialogFragment implements View.OnClickListener, LiveMusicAdapter.ActionListener {

    private EditText mEditText;
    private RecyclerView mRecyclerView;
    private View mNoLocalMusic;//没有本地歌曲
    private String mLastKey;
    private LiveMusicAdapter mAdapter;
    private DownloadUtil mDownloadUtil;
    private ActionListener mActionListener;

    @Override
    protected int getLayoutId() {
        return R.layout.dialog_live_music;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog2;
    }

    @Override
    protected boolean canCancel() {
        return false;
    }

    @Override
    protected void setWindowAttributes(Window window) {
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = DpUtil.dp2px(280);
        params.height = DpUtil.dp2px(360);
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mEditText = mRootView.findViewById(R.id.edit);
        mEditText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    search();
                    return true;
                }
                return false;
            }
        });
        mEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 0) {
                    queryDownloadMusic();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        mNoLocalMusic = mRootView.findViewById(R.id.no_local_music);
        mRecyclerView = mRootView.findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        mAdapter = new LiveMusicAdapter(mContext);
        mAdapter.setActionListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRootView.findViewById(R.id.btn_close).setOnClickListener(this);
        queryDownloadMusic();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_close) {
            dismiss();
        }
    }

    /**
     * 获取已经下载的歌曲
     */
    private void queryDownloadMusic() {
        List<ApiMusic> list = MusicDbManager.getInstace().queryList();
        if (list.size() > 0) {
            if (mNoLocalMusic.getVisibility() == View.VISIBLE) {
                mNoLocalMusic.setVisibility(View.INVISIBLE);
            }
            mAdapter.setList(list);
        } else {
            if (mNoLocalMusic.getVisibility() != View.VISIBLE) {
                mNoLocalMusic.setVisibility(View.VISIBLE);
            }
        }
    }

    /**
     * 搜索歌曲
     */
    private void search() {
        String key = mEditText.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            ToastUtil.show(getString(R.string.content_empty));
            return;
        }
        if (!TextUtils.isEmpty(mLastKey) && mLastKey.equals(key)) {
            return;
        }
        mLastKey = key;
        HttpApiAppVideo.musicList(key, 1, 20, new HttpApiCallBackPageArr<ApiMusic>() {
            @Override
            public void onHttpRet(int code, String msg, PageInfo pageInfo, List<ApiMusic> retModel) {
                if (code == 1) {
                    if (mNoLocalMusic.getVisibility() == View.VISIBLE) {
                        mNoLocalMusic.setVisibility(View.INVISIBLE);
                    }
                    mAdapter.setList(retModel);
                }
            }
        });
    }

    @Override
    public void onChoose(ApiMusic bean) {
        dismiss();
        if (mActionListener != null) {
            mActionListener.onChoose(bean.id);
        }
    }

    @Override
    public void onDownLoad(final ApiMusic bean) {
//        mMusicUrlCallback.setLiveMusicBean(bean);
        //获取选中歌曲的下载地址和歌词
//        HttpUtil.getMusicUrl(bean.getId(), mMusicUrlCallback);
        HttpApiAppVideo.musicInfo(bean.id, new HttpApiCallBack<ApiMusic>() {
            @Override
            public void onHttpRet(int code, String msg, ApiMusic retModel) {
                final String musicId = bean.id;
                File dir = new File(APPProConfig.MUSIC_PATH);
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                if (!TextUtils.isEmpty(retModel.lyrics)) {
                    FileUtil.saveStringToFile(dir, retModel.lyrics, musicId + ".lrc");
                }
                DownloadUtil.getInstance().download(HttpConstants.DOWNLOAD_MUSIC, dir, musicId + ".mp3", retModel.playUrl, new DownloadUtil.Callback() {

                    @Override
                    public void onSuccess(File file) {
                        MusicDbManager.getInstace().save(bean);
                    }

                    @Override
                    public void onProgress(int progress) {
                        if (mAdapter != null) {
                            mAdapter.updateItem(musicId, progress);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
            }
        });
    }

    @Override
    public void onItemRemoved(String musicId, int listSize) {
        MusicDbManager.getInstace().delete(musicId);
        File mp3File = new File(APPProConfig.MUSIC_PATH, musicId + ".mp3");
        if (mp3File.exists()) {
            mp3File.delete();
        }
        File lrcFile = new File(APPProConfig.MUSIC_PATH, musicId + ".lrc");
        if (lrcFile.exists()) {
            lrcFile.delete();
        }
        if (listSize == 0) {
            queryDownloadMusic();
        }
    }

    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }

    public interface ActionListener {
        void onChoose(String musicId);
    }

    @Override
    public void onDestroy() {
        if (mAdapter != null) {
            mAdapter.release();
        }
        super.onDestroy();
    }

}
