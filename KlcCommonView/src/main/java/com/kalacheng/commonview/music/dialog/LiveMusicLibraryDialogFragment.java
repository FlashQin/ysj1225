package com.kalacheng.commonview.music.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.LivemusiclibraryBinding;
import com.kalacheng.commonview.music.adpater.LiveMusicLibraryAdpater;
import com.kalacheng.commonview.viewmodel.LiveMusicLibraryViewModel;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppMusic;
import com.kalacheng.libuser.model.AppMusicDTO;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.util.utils.ToastUtil;

import java.util.List;

/*
 * 音乐库
 * */
public class LiveMusicLibraryDialogFragment extends BaseMVVMFragment<LivemusiclibraryBinding, LiveMusicLibraryViewModel> {
    private LiveMusicLibraryAdpater liveMusicLibraryAdpater;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.livemusiclibrary;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.LiveMusicLibraryRc.setLayoutManager(manager);

        liveMusicLibraryAdpater = new LiveMusicLibraryAdpater(getActivity());
        binding.LiveMusicLibraryRc.setAdapter(liveMusicLibraryAdpater);
        liveMusicLibraryAdpater.setLiveMusicLibraryCallBack(new LiveMusicLibraryAdpater.LiveMusicLibraryCallBack() {
            @Override
            public void onAddClick(final int poistion) {
                addMusic(poistion);

            }
        });

        search();

        getData();
    }

    public void getData() {
        HttpApiAppMusic.queryList("", 0, 1000, new HttpApiCallBackArr<AppMusicDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppMusicDTO> retModel) {
                if (code == 1) {
                    if (retModel != null && retModel.size() != 0) {
                        liveMusicLibraryAdpater.getList(retModel);
                    }

                }
            }
        });
    }

    //添加音乐
    public void addMusic(final int poistion) {
        AppMusicDTO appMusicDTO = liveMusicLibraryAdpater.getItem(poistion);
        if (appMusicDTO != null) {
            if (appMusicDTO.added == 0) {
                HttpApiAppMusic.setMusic(appMusicDTO.id, 1, new HttpApiCallBack<HttpNone>() {
                    @Override
                    public void onHttpRet(int code, String msg, HttpNone retModel) {
                        if (code == 1) {
                            liveMusicLibraryAdpater.addMusic(poistion);
                            ToastUtil.show(msg);
                        } else {
                            ToastUtil.show(msg);
                        }

                    }
                });
            }
        }
    }

    /**
     * 搜索歌曲
     */
    private String mLastKey;

    private void search() {
        binding.LiveMusicLibrarySearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                String key;
                if (editable == null) {
                    key = "";
                } else {
                    key = editable.toString();
                }
                HttpApiAppMusic.queryList(key, 0, HttpConstants.PAGE_SIZE, new HttpApiCallBackArr<AppMusicDTO>() {
                    @Override
                    public void onHttpRet(int code, String msg, List<AppMusicDTO> retModel) {
                        if (code == 1) {
                            if (retModel != null && retModel.size() != 0) {
                                liveMusicLibraryAdpater.getList(retModel);
                            }

                        }
                    }
                });
            }
        });
        /*String key = LiveMusicLibrary_Search.getText().toString().trim();
        if (TextUtils.isEmpty(key)) {
            ToastUtil.show(getString(R.string.content_empty));
            return;
        }
        if (!TextUtils.isEmpty(mLastKey) && mLastKey.equals(key)) {
            return;
        }
        mLastKey = key;*/

    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (showed) {
            getData();
        }
    }
}
