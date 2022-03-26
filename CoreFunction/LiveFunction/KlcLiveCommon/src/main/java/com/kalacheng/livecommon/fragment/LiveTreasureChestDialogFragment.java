package com.kalacheng.livecommon.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busgame.httpApi.HttpApiGame;
import com.kalacheng.busgame.model.GameAwardsAndPriceDTO;
import com.kalacheng.busgame.model.GameAwardsDTO;
import com.kalacheng.busgame.model.GameUserLuckDrawDTO;
import com.kalacheng.commonview.utils.GetIntoRoomVerificationUtlis;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.TreasureChestAdpater;
import com.kalacheng.livecommon.adapter.TreasureChestGiftListAdpater;
import com.kalacheng.util.adapter.BasePagerAdapter;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.view.ItemDecoration;

import java.util.ArrayList;
import java.util.List;

/*
 * 百宝箱
 * */
public class LiveTreasureChestDialogFragment extends BaseDialogFragment implements View.OnClickListener {
    private RecyclerView TreasureChest_Classification;
    private ViewPager TreasureChest_viewPage;
    private LinearLayout TreasureChest_spot;
    private TextView TreasureChest_Title;
    private ViewFlipper TreasureChest_WinRecord;

    private TreasureChestAdpater adpater;

    private ProgressBar TreasureChest_ProgressBar;

    private RecyclerView TreasureChest_GiftList;

    private TextView TreasureChest_Time;

    private TreasureChestGiftListAdpater treasureChestGiftListAdpater;

    private int type = 1;//1 黄金宝箱 2幸运宝箱

    private GameAwardsAndPriceDTO gameAwardsAndPriceDTO;

    private GameUserLuckDrawDTO gameUserLuckDrawDTO;

    private List<View> mList = new ArrayList<>();

    private RelativeLayout TreasureChest_Rela;
    private View TreasureChest_View;
    private FrameLayout TreasureChest_Frame;

    private boolean isLuckDraw = false;//是否在抽奖中


    @Override
    protected int getLayoutId() {
        return R.layout.treasure_chest_dialog;
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
        window.setWindowAnimations(com.kalacheng.livecommon.R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = DpUtil.getScreenHeight() / 4 * 3;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getInitView();
        getData();
    }

    @SuppressLint("WrongConstant")
    public void getInitView() {
        TreasureChest_Classification = mRootView.findViewById(R.id.TreasureChest_Classification);
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 3);
        TreasureChest_Classification.addItemDecoration(new ItemDecoration(getActivity(), 0, 20, 0));
        TreasureChest_Classification.setLayoutManager(manager);
        adpater = new TreasureChestAdpater(getActivity());
        TreasureChest_Classification.setAdapter(adpater);

        TreasureChest_viewPage = mRootView.findViewById(R.id.TreasureChest_viewPage);
        TreasureChest_spot = mRootView.findViewById(R.id.TreasureChest_spot);

        RelativeLayout TreasureChest_choice = mRootView.findViewById(R.id.TreasureChest_choice);
        TreasureChest_choice.setOnClickListener(this);

        TreasureChest_Title = mRootView.findViewById(R.id.TreasureChest_Title);

        TreasureChest_ProgressBar = mRootView.findViewById(R.id.TreasureChest_ProgressBar);

        TreasureChest_GiftList = mRootView.findViewById(R.id.TreasureChest_GiftList);
        LinearLayoutManager giftListManager = new LinearLayoutManager(getActivity());
        giftListManager.setOrientation(OrientationHelper.HORIZONTAL);
        TreasureChest_GiftList.setLayoutManager(giftListManager);
        TreasureChest_GiftList.addItemDecoration(new ItemDecoration(getActivity(), 0, 10, 0));
        treasureChestGiftListAdpater = new TreasureChestGiftListAdpater(getActivity());
        TreasureChest_GiftList.setAdapter(treasureChestGiftListAdpater);

        TreasureChest_WinRecord = mRootView.findViewById(R.id.TreasureChest_WinRecord);

        TreasureChest_Time = mRootView.findViewById(R.id.TreasureChest_Time);

        TreasureChest_Rela = mRootView.findViewById(R.id.TreasureChest_Rela);
        TreasureChest_View = mRootView.findViewById(R.id.TreasureChest_View);
        TreasureChest_Frame = mRootView.findViewById(R.id.TreasureChest_Frame);
    }

    public void getData() {
        getGamePriceAndAwardsList();

        adpater.setTreasureChestCallBack(new TreasureChestAdpater.TreasureChestCallBack() {
            @Override
            public void onClick(int poistion) {
                if (CheckDoubleClick.isFastDoubleClick()) {
                    return;
                }
                if (!isLuckDraw && gameAwardsAndPriceDTO != null) {
                    isLuckDraw = true;
                    HttpApiGame.starPlayGame(-1, gameAwardsAndPriceDTO.gamePriceList.get(poistion).id, type, new HttpApiCallBack<GameUserLuckDrawDTO>() {
                        @Override
                        public void onHttpRet(int code, String msg, GameUserLuckDrawDTO retModel) {
                            if (code == 1) {
                                gameUserLuckDrawDTO = retModel;
                                setAnimation(type);
                                handler.sendEmptyMessageDelayed(1, 2000);
                            } else if (code == -1) {
                                isLuckDraw = false;
                                GetIntoRoomVerificationUtlis.getInstance().getTipsDialog(getActivity(), 1);
                            } else {
                                isLuckDraw = false;
                                ToastUtil.show(msg);
                            }
                        }
                    });
                } else {
                    ToastUtil.show("正在开奖中");
                }


            }
        });
        TreasureChestGiftPic();
        getUserPrizeRecordList();
    }

    public void TreasureChestGiftPic() {
        for (int i = 0; i < 2; i++) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.treasurechest_gift, null, false);
            if (i == 0) {
                view.findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_gold_case_close);
            } else if (i == 1) {
                view.findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_purple_case_close);
            }
            mList.add(view);
        }
        BasePagerAdapter treasureChestAdapter = new BasePagerAdapter(mList);
        TreasureChest_viewPage.setAdapter(treasureChestAdapter);
        TreasureChest_viewPage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    TreasureChest_Title.setText("黄金宝箱");
                    TreasureChest_Title.setTextColor(Color.parseColor("#FFD45E"));
                    type = 1;
                    TreasureChest_Time.setText("");
                } else {
                    TreasureChest_Title.setText("幸运宝箱");
                    TreasureChest_Title.setTextColor(Color.parseColor("#ffffff"));
                    type = 2;
                    if (gameAwardsAndPriceDTO != null) {
                        TreasureChest_Time.setText(gameAwardsAndPriceDTO.luckyStartTime + "~" + gameAwardsAndPriceDTO.luckyEndTime);
                    }
                }
                getSpot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        getSpot(0);
    }

    //设置切换点
    public void getSpot(int poistion) {
        TreasureChest_spot.removeAllViews();
        for (int i = 0; i < mList.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            if (i == poistion) {
                imageView.setBackgroundResource(R.drawable.powder_oval);
                imageView.setPadding(5, 0, 0, 0);
            } else {
                imageView.setBackgroundResource(R.drawable.ash_oval);
                imageView.setPadding(5, 0, 0, 0);
            }
            TreasureChest_spot.addView(imageView);
        }
    }

    public void ShowDialog() {

        final Dialog mDialog = new Dialog(getActivity(), R.style.dialog2);
        mDialog.setContentView(R.layout.treasurechest_choice);
        mDialog.setCancelable(true);
        mDialog.setCanceledOnTouchOutside(true);

        Window window = mDialog.getWindow();
        window.setGravity(Gravity.RIGHT | Gravity.TOP);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.x = DpUtil.dp2px(30);
        lp.y = DpUtil.dp2px(180);
        window.setAttributes(lp);

        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {

            }
        });

        //玩法说明
        TextView TreasureChest_Explain = mDialog.findViewById(R.id.TreasureChest_Explain);
        TreasureChest_Explain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreasureChestExplainDialogFragment treasureChestExplainDialogFragment = new TreasureChestExplainDialogFragment();
                treasureChestExplainDialogFragment.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "TreasureChestExplainDialogFragment");
                mDialog.dismiss();
            }
        });

        //中奖纪录
        TextView TreasureChest_Record = mDialog.findViewById(R.id.TreasureChest_Record);
        TreasureChest_Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreasureChestRecordDialogFragment treasureChestRecordDialogFragment = new TreasureChestRecordDialogFragment();
                treasureChestRecordDialogFragment.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "TreasureChestRecordDialogFragment");

                mDialog.dismiss();
            }
        });

        //我的奖品
        TextView TreasureChest_Prize = mDialog.findViewById(R.id.TreasureChest_Prize);
        TreasureChest_Prize.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TreasureChestMyPrizeDialogFragment treasureChestMyPrizeDialogFragment = new TreasureChestMyPrizeDialogFragment();
                treasureChestMyPrizeDialogFragment.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "TreasureChestMyPrizeDialogFragment");


                mDialog.dismiss();
            }
        });


        mDialog.show();
    }

    //获取游戏奖项以及收费标准
    public void getGamePriceAndAwardsList() {
        HttpApiGame.getGamePriceAndAwardsList(1, new HttpApiCallBack<GameAwardsAndPriceDTO>() {
            @Override
            public void onHttpRet(int code, String msg, GameAwardsAndPriceDTO retModel) {
                if (code == 1 && retModel != null) {
                    gameAwardsAndPriceDTO = retModel;
                    getUi(retModel);
                }
            }
        });
    }

    public void getUi(final GameAwardsAndPriceDTO gameAwardsAndPriceDTO) {
        adpater.getData(gameAwardsAndPriceDTO.gamePriceList);

        treasureChestGiftListAdpater.getData(gameAwardsAndPriceDTO.gameAwardsList);
        getProgressBar(gameAwardsAndPriceDTO.luckyPrizeNum, gameAwardsAndPriceDTO.userLuckyNum);
    }

    public void getProgressBar(final int totleNum, final int LuckyNum) {
        /*    TreasureChest_ProgressBar.setMax(totleNum);

        TreasureChest_ProgressBar.post(new Runnable() {
            @Override
            public void run() {
                TreasureChest_ProgressBar.setProgress(LuckyNum);
            }
        });*/

        int Frame_height = TreasureChest_Frame.getWidth();

        int poistion = (int) (((double) LuckyNum / (double) totleNum) * Frame_height);

        FrameLayout.LayoutParams params0 = (FrameLayout.LayoutParams) TreasureChest_Rela.getLayoutParams();
        params0.width = poistion;
        TreasureChest_Rela.setLayoutParams(params0);
    }

    //获取最新十条中奖信息
    public void getUserPrizeRecordList() {
        HttpApiGame.getUserPrizeRecordList(1, new HttpApiCallBackArr<GameAwardsDTO>() {
            @Override
            public void onHttpRet(int code, String msg, List<GameAwardsDTO> retModel) {
                if (code == 1) {
                    getMarquee(retModel);
                }
            }
        });
    }

    //礼物展示跑马灯
    public void getMarquee(List<GameAwardsDTO> retModel) {
        TreasureChest_WinRecord.removeAllViews();
        if (retModel.size() == 0) {
            TreasureChest_WinRecord.setVisibility(View.GONE);
        } else {
            TreasureChest_WinRecord.setVisibility(View.VISIBLE);
            for (int i = 0; i < retModel.size(); i++) {
                TextView textView = new TextView(getActivity());
                textView.setText("恭喜 " + retModel.get(i).userName + " 开出豪华大奖 " + retModel.get(i).prizeName + "*" + retModel.get(i).prizeNum);
                textView.setTextColor(Color.parseColor("#FFC904"));
                textView.setTextSize(12);
                textView.setGravity(Gravity.CENTER);
                TreasureChest_WinRecord.addView(textView);
            }
        }

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.TreasureChest_choice) {
            ShowDialog();

        }
    }

    //抽奖动画
    ObjectAnimator liftrotate;
    ObjectAnimator rightrotate;
    ObjectAnimator rightrotate3;
    int poistion;

    public void setAnimation(int type) {
        if (type == 1) {
            poistion = 0;
        } else {
            poistion = 1;
        }
        //顺时针旋转45°
        liftrotate = ObjectAnimator.ofFloat(mList.get(poistion).findViewById(R.id.TreasureChest_GiftImage), "rotation", 0, 30f).setDuration(300);
        liftrotate.start();

        //逆时针旋转45°
        rightrotate = ObjectAnimator.ofFloat(mList.get(poistion).findViewById(R.id.TreasureChest_GiftImage), "rotation", 30f, -30f).setDuration(300);

        rightrotate3 = ObjectAnimator.ofFloat(mList.get(poistion).findViewById(R.id.TreasureChest_GiftImage), "rotation", -30f, 30f).setDuration(300);

        liftrotate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                liftrotate.cancel();
                rightrotate.start();
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        rightrotate.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (rightrotate3 != null) {
                    rightrotate3.start();
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        rightrotate3.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (rightrotate != null) {
                    rightrotate.start();
                }

            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
    }

    //抽奖定时
    public Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    result();
                    if (liftrotate != null) {
                        liftrotate.cancel();
                        liftrotate = null;
                    }
                    if (rightrotate != null) {
                        rightrotate.cancel();
                        rightrotate = null;
                    }
                    if (rightrotate3 != null) {
                        rightrotate3.cancel();
                        rightrotate3 = null;
                    }
                    mList.get(poistion).findViewById(R.id.TreasureChest_GiftImage).animate().rotation(0).setDuration(60).start();


                    break;
            }
        }
    };

    //抽奖结果
    public void result() {
        isLuckDraw = false;
        if (gameAwardsAndPriceDTO != null) {
            getProgressBar(gameAwardsAndPriceDTO.luckyPrizeNum, gameUserLuckDrawDTO.userGameNum);
        }
        if (type == 1) {
            mList.get(0).findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_gold_case);
        } else {
            mList.get(1).findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_purple_case);
        }

        GameWinRecordDialogFragment gameWinRecordDialogFragment = new GameWinRecordDialogFragment();
        gameWinRecordDialogFragment.setGameUserLuckDrawDTO(gameUserLuckDrawDTO);
        gameWinRecordDialogFragment.show(((FragmentActivity) getActivity()).getSupportFragmentManager(), "GameWinRecordDialogFragment");

        gameWinRecordDialogFragment.setGameWinRecordCallBack(new GameWinRecordDialogFragment.GameWinRecordCallBack() {
            @Override
            public void onClick() {
                if (type == 1) {
                    mList.get(0).findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_gold_case_close);
                } else {
                    mList.get(1).findViewById(R.id.TreasureChest_GiftImage).setBackgroundResource(R.mipmap.icon_purple_case_close);
                }
            }
        });
    }

    public void clean() {
        if (handler != null) {
            handler.removeMessages(1);
        }
        if (liftrotate != null) {
            liftrotate.cancel();
            liftrotate = null;
        }
        if (rightrotate != null) {
            rightrotate.cancel();
            rightrotate = null;
        }
        if (rightrotate3 != null) {
            rightrotate3.cancel();
            rightrotate3 = null;
        }
    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        clean();
    }
}
