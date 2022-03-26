package com.kalacheng.onevoicelive.component;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;

import com.kalacheng.base.base.BaseMVVMViewHolder;
import com.kalacheng.base.listener.MsgListener;
import com.kalacheng.busooolive.model.OOOVolumeRet;
import com.kalacheng.frame.config.LiveBundle;
import com.kalacheng.frame.config.LiveConstants;
import com.kalacheng.libuser.model.AppJoinRoomVO;
import com.kalacheng.onevoicelive.R;
import com.kalacheng.onevoicelive.databinding.OnevoiceViewBinding;
import com.kalacheng.onevoicelive.viewmodel.OneVoiceViewModel;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.klc.bean.live.JniObjs;

//观众和主播的视图
public class OneVoiceViewComponet extends BaseMVVMViewHolder<OnevoiceViewBinding, OneVoiceViewModel> {
    public OneVoiceViewComponet(Context context, ViewGroup parentView) {
        super(context, parentView);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.onevoice_view;
    }

    @Override
    protected void init() {


        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_CloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOCloseLive, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                clean();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.RoomInfoList, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                addToParent();
                viewModel.joinRoom.set((AppJoinRoomVO) o);
                intiview();
            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OOOLiveSVipEstoppelSpeake, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {

                OOOVolumeRet volumeRet = (OOOVolumeRet) o;
//                if (volumeRet.hostStatus == 1) {
//                    binding.VoiceLiveAnchorClose.setVisibility(View.GONE);
//                } else {
//                    binding.VoiceLiveAnchorClose.setVisibility(View.VISIBLE);
//                }
//
//                if (volumeRet.feeStatus == 1) {
//                    binding.VoiceLiveUserClose.setVisibility(View.GONE);
//                } else {
//                    binding.VoiceLiveUserClose.setVisibility(View.VISIBLE);
//                }

                //操作人为主播
                if (volumeRet.operateUid == viewModel.joinRoom.get().anchorId) {
                    if (volumeRet.operateStatus == 1) {
                        binding.VoiceLiveAnchorClose.setVisibility(View.GONE);
                    } else {
                        binding.VoiceLiveAnchorClose.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.AnchorspreadView.stopAnim();
                            }
                        }, 500);
                    }
                    //操作人为观众
                } else {
                    if (volumeRet.operateStatus == 1) {
                        binding.VoiceLiveUserClose.setVisibility(View.GONE);
                    } else {
                        binding.VoiceLiveUserClose.setVisibility(View.VISIBLE);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                binding.UserspreadView.stopAnim();
                            }
                        }, 500);
                    }
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });

        //说话的声音
        LiveBundle.getInstance().addSimpleMsgListener(LiveConstants.LM_OneVoiceVolume, new MsgListener.SimpleMsgListener() {
            @Override
            public void onMsg(Object o) {
                JniObjs mJniObjs = (JniObjs) o;
                if (mJniObjs.mUid == LiveConstants.ANCHORID) {
                    AnchorWaveImage(mJniObjs.mAudioLevel);
                } else {
                    UserWaveImage(mJniObjs.mAudioLevel);
                }

            }

            @Override
            public void onTagMsg(String tag, Object o) {

            }
        });
    }


    public void intiview() {

        ImageLoader.display(viewModel.joinRoom.get().userAvatar, binding.VoiceLiveUserHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
        ImageLoader.display(viewModel.joinRoom.get().anchorAvatar, binding.VoiceLiveAnchorHeadImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
//        binding.AnchorspreadView.startAnim();
//
//        binding.UserspreadView.startAnim();
    }

    public void clean() {
//        binding.AnchorspreadView.clean();
//        binding.UserspreadView.clean();

        removeFromParent();


    }

    //设置主播讲话是的动画博文
    public void AnchorWaveImage(int volume) {


        if (volume > 2) {
            binding.AnchorspreadView.startAnim();
          /*  binding.spreadView.setVisibility(View.VISIBLE);
            binding.spreadView.setInitialRadius(DpUtil.dp2px(27));
            binding.spreadView.setDuration(5000);
            binding.spreadView.setStyle(Paint.Style.FILL);

                if (sex == 1) {
                    binding.spreadView.setColor(Color.parseColor("#3568de"));
                } else {
                    binding.spreadView.setColor(Color.parseColor("#ff6e70"));
                }

            binding.spreadView.setInterpolator(new LinearOutSlowInInterpolator());
            binding.spreadView.setMaxRadius(DpUtil.dp2px(42));
            binding.spreadView.start();*/
        } else {
            /*binding.spreadView.setVisibility(View.GONE);
            binding.spreadView.stop();*/
            binding.AnchorspreadView.stopAnim();
        }

    }

    //设置用户讲话是的动画博文
    public void UserWaveImage(int volume) {
        if (volume > 2) {
            binding.UserspreadView.startAnim();
        } else {
            binding.UserspreadView.stopAnim();
        }

    }
}
