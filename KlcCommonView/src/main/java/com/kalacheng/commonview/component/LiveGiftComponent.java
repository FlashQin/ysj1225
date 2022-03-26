package com.kalacheng.commonview.component;


import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.kalacheng.base.base.BaseViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.busnobility.model.ApiPkResult;
import com.kalacheng.busnobility.socketmsg.IMRcvLiveGiftSend;
import com.kalacheng.commonview.R;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.ApiGiftSender;
import com.kalacheng.libuser.model.ApiSimpleMsgRoom;
import com.kalacheng.util.utils.CommonCallback;
import com.kalacheng.commonview.utils.GifCacheUtil;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;
import com.wengying666.imsocket.IMUtil;
import com.wengying666.imsocket.SocketClient;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageView;

public class LiveGiftComponent extends BaseViewHolder implements LiveBundle.onLiveSocket {

    private SVGAImageView mSVGAImageView;
    private GifDrawable mGifDrawable;
    private GifImageView mGifImageView;
    private MediaController mMediaController;//koral--/android-gif-drawable 这个库用来播放gif动画的
    private ApiGiftSender apiGiftSender;
    private Map<Long, SoftReference<SVGAVideoEntity>> mSVGAMap;
    private SVGAParser mSVGAParser;
    private ConcurrentLinkedQueue<ApiGiftSender> mQueue = new ConcurrentLinkedQueue<>();
    private boolean isShowGift;
    private MyHandler myHandler;

    private String groupName;
    private Context mContext;

    public LiveGiftComponent(Context context, ViewGroup parentView) {
        super(context, parentView);
        mContext = context;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.view_gif_play;
    }

    @Override
    protected void init() {
        addToParent();
        LiveBundle.getInstance().addLiveSocketListener(this);
        mSVGAImageView = (SVGAImageView) findViewById(R.id.gift_svga);
        mGifImageView = (GifImageView) findViewById(R.id.gift_gif);
        myHandler = new MyHandler(this);
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_ExitRoom, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_HttpCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clear();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });


    }

    /**
     * 播放豪华礼物
     */
    private void playGift(final ApiGiftSender apiGiftSender) {
        if (null == apiGiftSender || TextUtils.isEmpty(apiGiftSender.giftswf)) {
            return;
        }
        LiveGiftComponent.this.apiGiftSender = apiGiftSender;
        //播放礼物
        if (apiGiftSender.giftswf.endsWith(".svga") || apiGiftSender.giftswf.endsWith(".gif")) {
            String urlpath = String.valueOf(apiGiftSender.giftId);
            /*if (apiGiftSender.giftswf.endsWith(".svga")){
                urlpath = apiGiftSender.giftswf.substring(apiGiftSender.giftswf.length()-16,apiGiftSender.giftswf.length()-5);
            }else {
                urlpath = apiGiftSender.giftswf.substring(apiGiftSender.giftswf.length()-15,apiGiftSender.giftswf.length()-4);

            }*/
            try {
                GifCacheUtil.getFile(urlpath, apiGiftSender.giftswf, new CommonCallback<File>() {
                    @Override
                    public void callback(File file) {
                        if (apiGiftSender.giftswf.endsWith(".gif")) {
                            playGif(file);
                        } else {
                            SVGAVideoEntity svgaVideoEntity = null;
                            if (mSVGAMap != null) {
                                SoftReference<SVGAVideoEntity> reference = mSVGAMap.get(apiGiftSender.giftId);
                                if (reference != null) {
                                    svgaVideoEntity = reference.get();
                                }
                            }
                            if (svgaVideoEntity != null) {
                                playSVGA(svgaVideoEntity);
                            } else {
                                decodeSvga(file);
                            }
                        }
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 播放gif
     */
    private void playGif(File file) {
        try {
            mGifDrawable = new GifDrawable(file);
            if (mGifDrawable.getError().getErrorCode() != 0) {
                file.delete();
//                GifCacheUtil.getFile(Constants.GIF_GIFT_PREFIX + apiGiftSender.giftId, apiGiftSender.giftswf, new CommonCallback<File>() {
//                    @Override
//                    public void callback(File bean) {
//                        playGift(bean);
//                    }
//                });
            } else {
                isShowGift = true;
                if (mMediaController == null) {
                    mMediaController = new MediaController(mContext);
                    mMediaController.setVisibility(View.GONE);
                }
                mGifDrawable.setLoopCount(1);
                mGifImageView.setImageDrawable(mGifDrawable);
                mMediaController.setMediaPlayer((GifDrawable) mGifImageView.getDrawable());
                mMediaController.setAnchorView(mGifImageView);

                myHandler.sendEmptyMessageDelayed(1, mGifDrawable.getDuration() + 1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 解析svga
     */
    private void decodeSvga(final File file) {
        try {
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));
            if (mSVGAParser == null) {
                mSVGAParser = new SVGAParser(mContext);
            }
            SVGAParser.ParseCompletion mParseCompletionCallback = new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(SVGAVideoEntity svgaVideoEntity) {
                    if (mSVGAMap == null) {
                        mSVGAMap = new HashMap<>();
                    }
                    if (apiGiftSender != null) {
                        mSVGAMap.put(apiGiftSender.giftId, new SoftReference<>(svgaVideoEntity));
                    }
                    playSVGA(svgaVideoEntity);
                }

                @Override
                public void onError() {
                    // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
                    if (file.exists() && file.isFile()) {
                        file.delete();
                    }
//                    GifCacheUtil.getFile(Constants.GIF_GIFT_PREFIX + apiGiftSender.giftId, apiGiftSender.giftswf, new CommonCallback<File>() {
//                        @Override
//                        public void callback(File bean) {
//                            playGift(bean);
//                        }
//                    });
                }
            };

            mSVGAParser.decodeFromInputStream(bis, file.getAbsolutePath(), mParseCompletionCallback, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 播放svga
     */
    private void playSVGA(SVGAVideoEntity svgaVideoEntity) {
        try {
            if (mSVGAImageView != null && null != svgaVideoEntity) {
                isShowGift = true;
                SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
                mSVGAImageView.setImageDrawable(drawable);
                mSVGAImageView.setVideoItem(svgaVideoEntity);
                mSVGAImageView.startAnimation();
                mSVGAImageView.setCallback(new SVGACallback() {
                    @Override
                    public void onPause() {
                    }

                    @Override
                    public void onFinished() {
                        myHandler.sendEmptyMessage(1);
                    }

                    @Override
                    public void onRepeat() {

                    }

                    @Override
                    public void onStep(int i, double v) {

                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static class MyHandler extends Handler {
        WeakReference<LiveGiftComponent> mActivityReference;

        MyHandler(LiveGiftComponent liveGiftComponent) {
            mActivityReference = new WeakReference<LiveGiftComponent>(liveGiftComponent);
        }

        @Override
        public void handleMessage(Message msg) {
            final LiveGiftComponent liveGiftComponent = mActivityReference.get();
            if (liveGiftComponent != null) {
                liveGiftComponent.isShowGift = false;
                if (liveGiftComponent.mGifImageView != null) {
                    liveGiftComponent.mGifImageView.setImageDrawable(null);
                }
                ApiGiftSender apiGiftSender = liveGiftComponent.mQueue.poll();
                if (null != apiGiftSender) {
                    liveGiftComponent.playGift(apiGiftSender);
                }
            }
        }
    }

    public void clear() {
        if (mMediaController != null) {
            mMediaController.hide();
            mMediaController.setAnchorView(null);
        }
        if (mGifImageView != null) {
            mGifImageView.setImageDrawable(null);
        }
        if (mGifDrawable != null && !mGifDrawable.isRecycled()) {
            mGifDrawable.stop();
            mGifDrawable.recycle();
            mGifDrawable = null;
        }
        if (mSVGAImageView != null) {
            mSVGAImageView.stopAnimation(true);
        }
        if (mSVGAMap != null) {
            mSVGAMap.clear();
        }
        if (mQueue != null) {
            mQueue.clear();
        }
//        IMUtil.removeReceiver(groupName);
    }

    @Override
    public void init(String groupName, SocketClient socketClient) {
        //ggm  进行修改
        this.groupName = groupName;
        IMUtil.addReceiver(groupName, new IMRcvLiveGiftSend() {
            @Override
            public void onSimpleGiftMsgRoom(ApiSimpleMsgRoom apiSimpleMsgRoom) {
                LiveBundle.getInstance().sendTagMsg(LiveConstants.LM_GiftMsg, LiveGiftComponent.this.groupName, apiSimpleMsgRoom);
            }

            @Override
            public void onGiftMsgAll(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.anchorId == LiveConstants.ANCHORID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_MessageForGift, apiGiftSender);
                }
                if (apiGiftSender.liveType == 7 || apiGiftSender.liveType == 8) {
                    if (!isShowGift) {
                        playGift(apiGiftSender);
                    } else {
                        mQueue.offer(apiGiftSender);
                    }
                } else {
                    if (apiGiftSender.roomId == LiveConstants.ROOMID) {
                        if (!isShowGift) {
                            playGift(apiGiftSender);
                        } else {
                            mQueue.offer(apiGiftSender);
                        }
                    }
                }
            }

            @Override
            public void onGiveGiftUser(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.anchorId == LiveConstants.ANCHORID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_MessageForGift, apiGiftSender);
                }
                if (!isShowGift) {
                    playGift(apiGiftSender);
                } else {
                    mQueue.offer(apiGiftSender);
                }
            }

            @Override
            public void onGiveGift(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.anchorId == LiveConstants.ANCHORID) {
                    LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_MessageForGift, apiGiftSender);
                }
                if (!isShowGift) {
                    playGift(apiGiftSender);
                } else {
                    mQueue.offer(apiGiftSender);
                }
            }

            @Override
            public void onGiftPKResult(ApiPkResult apiPKResult) {
                LiveBundle.getInstance().sendSimpleMsg(LiveConstants.LM_ChangePKValue, apiPKResult);
            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });
    }
}
