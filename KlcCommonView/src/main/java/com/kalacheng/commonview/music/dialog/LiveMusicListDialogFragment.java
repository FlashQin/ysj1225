package com.kalacheng.commonview.music.dialog;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;

import com.kalacheng.commonview.R;
import com.kalacheng.commonview.databinding.LivemusiclistBinding;
import com.kalacheng.commonview.music.MusicDbManager1;
import com.kalacheng.commonview.music.adpater.LiveMusicListAdpater;
import com.kalacheng.commonview.utils.LiveMusicView;
import com.kalacheng.commonview.viewmodel.LiveMusicListViewModel;
import com.kalacheng.frame.config.APPProConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.libuser.httpApi.HttpApiAppMusic;
import com.kalacheng.libuser.model.AppUserMusicDTO;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.download.DownloadUtil;
import com.kalacheng.base.base.BaseMVVMFragment;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;

import java.io.File;
import java.util.List;

/**
 * 音乐播放列表
 */
public class LiveMusicListDialogFragment extends BaseMVVMFragment<LivemusiclistBinding, LiveMusicListViewModel> {
    private LiveMusicListAdpater liveMusicListAdpater;

    //    private List<AppUserMusicDTO> mList;
    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.livemusiclist;
    }


    @SuppressLint("WrongConstant")
    @Override
    public void initData() {
//        EventBus.getDefault().register(this);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.VERTICAL);
        binding.LiveMusicListRe.setLayoutManager(manager);

        liveMusicListAdpater = new LiveMusicListAdpater(getActivity());
        binding.LiveMusicListRe.setAdapter(liveMusicListAdpater);
//        liveMusicListAdpater.setData(MusicDbManager1.getInstace().queryList());
        liveMusicListAdpater.setLiveMusicListCallBack(new LiveMusicListAdpater.LiveMusicListCallBack() {
            @Override
            public void onClick(int poisition) {
//                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_LiveMusicChooice, poisition);
                if (LiveConstants.isPalyMusic) {
                    LiveMusicView.getInstance().play(poisition);
                } else {
                    LiveMusicView.getInstance().show(getActivity());
                    LiveMusicView.getInstance().play(poisition);
                }

            }

            @Override
            public void onDelete(int position) {
                setDetele(position);
            }
        });

        getData();
    }

    @Override
    public void setShowed(boolean showed) {
        super.setShowed(showed);
        if (showed) {
//            if (liveMusicListAdpater != null) {
//                liveMusicListAdpater.setData(MusicDbManager1.getInstace().queryList());
//            }
            getData();
        }/*else {
            EventBus.getDefault().unregister(this);
        }*/

    }

    public void getData() {
        HttpApiAppMusic.queryMyMusicList("", 0, 1000, new HttpApiCallBackArr<AppUserMusicDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<AppUserMusicDTO> retModel) {
                if (code == 1 && retModel != null && retModel.size() > 0) {
                    binding.tvCollectionNum.setText("共" + retModel.size() + "首");
                    for (int i = 0; i < retModel.size(); i++) {
                        File dir = new File(APPProConfig.MUSIC_PATH);
                        if (!dir.exists()) {
                            dir.mkdirs();
                        }

                        DownloadUtil.getInstance().download(HttpConstants.DOWNLOAD_MUSIC, dir, retModel.get(i).id + ".mp3", retModel.get(i).musicUrl, new DownloadUtil.Callback() {

                            @Override
                            public void onSuccess(File file) {

                            }

                            @Override
                            public void onProgress(int progress) {
                                /*if (mAdapter != null) {
                                    mAdapter.updateItem(musicId, progress);
                                }*/
                            }

                            @Override
                            public void onError(Throwable e) {

                            }
                        });
                        MusicDbManager1.getInstace().save(retModel.get(i));
                    }
                    if (liveMusicListAdpater != null) {
                        liveMusicListAdpater.setData(MusicDbManager1.getInstace().queryList());
                    }
                } else {
                    binding.tvCollectionNum.setText("共0首");
                }
            }
        });
    }

    //删除列表音乐
    public void setDetele(final int poistion) {
        HttpApiAppMusic.setMusic(MusicDbManager1.getInstace().queryList().get(poistion).musicId, 2, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    MusicDbManager1.getInstace().delete(MusicDbManager1.getInstace().queryList().get(poistion).id);
                    if (liveMusicListAdpater != null) {
                        liveMusicListAdpater.setData(MusicDbManager1.getInstace().queryList());
                        binding.tvCollectionNum.setText("共" + liveMusicListAdpater.getItemCount() + "首");
                    }
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

   /* @Subscribe(threadMode = ThreadMode.MAIN)
    public void onPlayMusic(PlayMusicEvent event){
        if (liveMusicListAdpater != null) {
            liveMusicListAdpater.setMusic(event.id);
        }
    }*/
}
