package com.kalacheng.commonview.component;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.ViewGroup;
import android.widget.MediaController;

import com.kalacheng.base.base.BaseViewHolder;
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

/**
 * 直播间外，动图
 */
public class LiveOutGiftComponent extends BaseViewHolder implements LiveBundle.onLiveSocket {
    private SVGAImageView mSVGAImageView;
    private GifDrawable mGifDrawable;
    private GifImageView mGifImageView;
    private MediaController mMediaController;//koral--/android-gif-drawable 这个库用来播放gif动画的
    ApiGiftSender apiGiftSender;
    private Map<Long, SoftReference<SVGAVideoEntity>> mSVGAMap;
    private SVGAParser mSVGAParser;
    private ConcurrentLinkedQueue<ApiGiftSender> mQueue = new ConcurrentLinkedQueue<>();
    boolean isShowGift;
    MyHandler myHandler;

    private String groupName;
    private Context mContext;

    public LiveOutGiftComponent(Context context, ViewGroup parentView) {
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
        mSVGAImageView = (SVGAImageView) findViewById(R.id.gift_svga);
        mGifImageView = (GifImageView) findViewById(R.id.gift_gif);
        myHandler = new MyHandler(this);
    }

    /**
     * 播放豪华礼物
     */
    private void playGift(final ApiGiftSender apiGiftSender) {
        this.apiGiftSender = apiGiftSender;
        //播放礼物
        if (apiGiftSender.giftswf.endsWith(".svga") || apiGiftSender.giftswf.endsWith(".gif")) {
            String urlpath = String.valueOf(apiGiftSender.giftId);
            /*if (apiGiftSender.giftswf.endsWith(".svga")){
                urlpath = apiGiftSender.giftswf.substring(apiGiftSender.giftswf.length()-16,apiGiftSender.giftswf.length()-5);
            }else {
                urlpath = apiGiftSender.giftswf.substring(apiGiftSender.giftswf.length()-15,apiGiftSender.giftswf.length()-4);

            }*/
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
        isShowGift = true;
        if (mSVGAImageView != null) {
            SVGADrawable drawable = new SVGADrawable(svgaVideoEntity);
            mSVGAImageView.setImageDrawable(drawable);
            mSVGAImageView.setVideoItem(svgaVideoEntity);
            mSVGAImageView.startAnimation();
            myHandler.sendEmptyMessageDelayed(1, 4500);
            mSVGAImageView.setCallback(new SVGACallback() {
                @Override
                public void onPause() {

                }

                @Override
                public void onFinished() {

                }

                @Override
                public void onRepeat() {

                }

                @Override
                public void onStep(int i, double v) {

                }
            });
        }
    }

    static class MyHandler extends Handler {
        WeakReference<LiveOutGiftComponent> mActivityReference;

        MyHandler(LiveOutGiftComponent liveOutGiftComponent) {
            mActivityReference = new WeakReference<LiveOutGiftComponent>(liveOutGiftComponent);
        }

        @Override
        public void handleMessage(Message msg) {
            final LiveOutGiftComponent liveOutGiftComponent = mActivityReference.get();
            if (liveOutGiftComponent != null) {
                liveOutGiftComponent.isShowGift = false;
                ApiGiftSender apiGiftSender = liveOutGiftComponent.mQueue.poll();
                if (null != apiGiftSender) {
                    liveOutGiftComponent.playGift(apiGiftSender);
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

            }

            @Override
            public void onGiftMsgAll(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.roomId == -1) {
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
            }

            @Override
            public void onGiveGiftUser(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.roomId == -1) {
                    if (!isShowGift) {
                        playGift(apiGiftSender);
                    } else {
                        mQueue.offer(apiGiftSender);
                    }
                }
            }

            @Override
            public void onGiveGift(ApiGiftSender apiGiftSender) {
                if (apiGiftSender.roomId == -1) {
                    if (!isShowGift) {
                        playGift(apiGiftSender);
                    } else {
                        mQueue.offer(apiGiftSender);
                    }
                }
            }

            @Override
            public void onGiftPKResult(ApiPkResult apiPKResult) {

            }

            @Override
            public void onOtherMsg(Object obj) {

            }
        });
    }
}
