package com.kalacheng.main.fragment;

import android.Manifest;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.listener.OnItemClickCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.busooolive.httpApi.HttpApiOOOLive;
import com.kalacheng.commonview.utils.OOOLiveCallUtils;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiCfgPayCallOneVsOne;
import com.kalacheng.main.R;
import com.kalacheng.main.adapter.ManyPeopleVideoAdapter;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.view.SpacesItemDecoration;
import com.xuantongyun.livecloud.protocol.OtherLiveSetUtlis;
import com.xuantongyun.livecloud.protocol.PulicUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * 遇见多人
 */
public class MeetManyFragment extends BaseFragment implements View.OnClickListener {
    RelativeLayout rlVideo;
    public RecyclerView recyclerViewMany;
    ImageView ivUserVideo, ivUserVoice;
    boolean isShowVideo, isShowVoice;
    private SurfaceView surfaceView;
    List<ApiCfgPayCallOneVsOne> apiCfgPayCallOneVsOneList;
    private List<ApiCfgPayCallOneVsOne> newList = new ArrayList<>();
    PermissionsUtil mProcessResultUtil;
    public ManyPeopleVideoAdapter adapter;
    Disposable listDisposable;

    public MeetManyFragment() {
        super();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_many;
    }

    @Override
    public void initView() {
        recyclerViewMany = mParentView.findViewById(R.id.recyclerView_many);
        rlVideo = mParentView.findViewById(R.id.rl_video);
        ivUserVideo = mParentView.findViewById(R.id.iv_user_video);
        ivUserVoice = mParentView.findViewById(R.id.iv_user_voice);

        HashMap<String, Integer> stringIntegerHashMap = new HashMap<>();
        stringIntegerHashMap.put(SpacesItemDecoration.TOP_DECORATION, 15);//上下间距
        stringIntegerHashMap.put(SpacesItemDecoration.RIGHT_DECORATION, 25);
        recyclerViewMany.addItemDecoration(new SpacesItemDecoration(stringIntegerHashMap));
        recyclerViewMany.setLayoutManager(new GridLayoutManager(getContext(), 3));

        ivUserVideo.setOnClickListener(this);
        ivUserVoice.setOnClickListener(this);
        mParentView.findViewById(R.id.iv_refresh).setOnClickListener(this);

    }

    @Override
    public void initData() {
        mProcessResultUtil = new PermissionsUtil(getActivity());

        HttpApiOOOLive.meetUser((float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT), (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG), new HttpApiCallBackArr<ApiCfgPayCallOneVsOne>() {
            @Override
            public void onHttpRet(int code, String msg, List<ApiCfgPayCallOneVsOne> retModel) {
                if (code == 1 && null != retModel) {
                    apiCfgPayCallOneVsOneList = retModel;
                    createRandomList(apiCfgPayCallOneVsOneList, 6);

                }
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.iv_user_video) {
            if (isShowVideo) {
                removeLocalVideo();
                ivUserVideo.setImageResource(R.mipmap.bg_video_open);
            } else {
                showLocalVideo();
                ivUserVideo.setImageResource(R.mipmap.bg_video_close);
            }
            isShowVideo = !isShowVideo;
        } else if (view.getId() == R.id.iv_user_voice) {
            if (isShowVideo) {
                if (isShowVoice) {
                    PulicUtils.getInstance().muteLocalAudioStream(false);
                    ivUserVoice.setImageResource(R.mipmap.anchor_voice_open);
                } else {
                    PulicUtils.getInstance().muteLocalAudioStream(true);
                    ivUserVoice.setImageResource(R.mipmap.anchor_voice_close);

                }
            } else {
                ToastUtil.show("请打开摄像头");
            }
        } else if (view.getId() == R.id.iv_refresh) {
            createRandomList(apiCfgPayCallOneVsOneList, 6);
        }
    }

    private void showLocalVideo() {
        OtherLiveSetUtlis.getInstance().init(getContext());
        surfaceView = OtherLiveSetUtlis.getInstance().setupLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0));
//        surfaceView = tcloud.playLocalVideo((int) SpUtil.getInstance().getSharedPreference(SpUtil.CONFIG_IMAGE_QUALITY, 0), HttpClient.getUid());
        OtherLiveSetUtlis.getInstance().setClientRole(1);
        surfaceView.setZOrderMediaOverlay(false);
        rlVideo.addView(surfaceView);

    }

    private void removeLocalVideo() {
        rlVideo.removeView(surfaceView);
        surfaceView = null;
    }

    /**
     * 从list中随机抽取元素
     *
     * @param list
     * @param n
     * @return void
     * @throws
     * @Title: createRandomList
     */
    private void createRandomList(final List list, final int n) {
        listDisposable = Observable.create(new ObservableOnSubscribe<List>() {
            @Override
            public void subscribe(ObservableEmitter<List> emitter) throws Exception {
                int size;
                if (list.size() < n) {
                    size = list.size();
                } else {
                    size = n;
                }
                Map map = new HashMap();
                List listNew = new ArrayList();
                while (map.size() < size) {
                    int random = (int) (Math.random() * list.size());
                    if (!map.containsKey(random)) {
                        map.put(random, "");
                        listNew.add(list.get(random));
                    }
                }
                emitter.onNext(listNew);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Consumer<List>() {
            @Override
            public void accept(List list) throws Exception {
                if (adapter == null) {
                    newList.addAll(list);
                    adapter = new ManyPeopleVideoAdapter(newList, mProcessResultUtil);
                    recyclerViewMany.setAdapter(adapter);
                    adapter.setItemClickCallback(new OnItemClickCallback<ApiCfgPayCallOneVsOne>() {
                        @Override
                        public void onClick(View view, ApiCfgPayCallOneVsOne item) {
                            final ApiUserInfo info = new ApiUserInfo();
                            info.userId = item.userId;
                            LiveConstants.mIsOOOSend = true;
                            info.avatar = item.liveThumb;
                            info.username = item.userName;
                            mProcessResultUtil.requestPermissions(new String[]{
                                    Manifest.permission.READ_EXTERNAL_STORAGE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                    Manifest.permission.CAMERA,
                                    Manifest.permission.RECORD_AUDIO
                            }, new Runnable() {
                                @Override
                                public void run() {
                                    OOOLiveCallUtils.getInstance().OnetoOneinviteAnchorChat(getActivity(), 1, info, 1);

                                }
                            });
                        }
                    });
                } else {
                    newList.clear();
                    newList.addAll(list);
                    adapter.notifyDataSetChanged();
                }
            }
        });
//
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (listDisposable != null)
            listDisposable.dispose();
    }
}
