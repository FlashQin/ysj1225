package com.kalacheng.livecommon.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busgame.model.GameUserLuckDrawDTO;
import com.kalacheng.livecommon.R;
import com.kalacheng.livecommon.adapter.GameWinRecordListAdpater;


/*
 * 中奖纪录
 * */
public class GameWinRecordDialogFragment extends BaseDialogFragment implements DialogInterface.OnDismissListener {
    private GameUserLuckDrawDTO gameUserLuckDrawDTO;

    private RecyclerView GameWinRecord_List;
    private GameWinRecordListAdpater adpater;

    public void setGameUserLuckDrawDTO(GameUserLuckDrawDTO gameUserLuckDrawDTO) {
        this.gameUserLuckDrawDTO = gameUserLuckDrawDTO;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.game_win_record;
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
        window.setWindowAnimations(R.style.bottomToTopAnim);
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView GameWinRecord_Title = mRootView.findViewById(R.id.GameWinRecord_Title);

        setTextViewStyles(GameWinRecord_Title);

        GameWinRecord_List = mRootView.findViewById(R.id.GameWinRecord_List);
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        manager.setOrientation(OrientationHelper.VERTICAL);
        GameWinRecord_List.setLayoutManager(manager);

        TextView GameWinRecord_Btn = mRootView.findViewById(R.id.GameWinRecord_Btn);
        ImageView GameWinRecord_NoWin = mRootView.findViewById(R.id.GameWinRecord_NoWin);
        TextView GameWinRecord_Result = mRootView.findViewById(R.id.GameWinRecord_Result);

        adpater = new GameWinRecordListAdpater(mContext);
        GameWinRecord_List.setAdapter(adpater);

        if (gameUserLuckDrawDTO.gameLuckDraw.isAwards == 1) {
            GameWinRecord_List.setVisibility(View.VISIBLE);
            GameWinRecord_NoWin.setVisibility(View.GONE);
            if (gameUserLuckDrawDTO.gamePrizeRecordList != null) {
                GameWinRecord_Result.setVisibility(View.VISIBLE);
                GameWinRecord_Result.setText("共获得" + gameUserLuckDrawDTO.gamePrizeRecordList.size() + "个奖品");
            } else {
                GameWinRecord_Result.setVisibility(View.GONE);
            }
            GameWinRecord_Btn.setText("继续抽奖");
            GameWinRecord_Title.setText("恭喜您，中奖啦");
        } else {
            GameWinRecord_List.setVisibility(View.GONE);
            GameWinRecord_NoWin.setVisibility(View.VISIBLE);
            GameWinRecord_Result.setVisibility(View.GONE);
            GameWinRecord_Result.setText("差一点就抽到奖品了");
            GameWinRecord_Btn.setText("再抽一次");
            GameWinRecord_Title.setText("再接再厉");
        }

        adpater.getData(gameUserLuckDrawDTO.gamePrizeRecordList);

        ImageView colse = mRootView.findViewById(R.id.colse);
        colse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        GameWinRecord_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }

    public void setTextViewStyles(TextView text) {

        LinearGradient mLinearGradient = new LinearGradient(0, 0, 0, text.getPaint().getTextSize(), Color.parseColor("#FFFFFF"), Color.parseColor("#B521E9"), Shader.TileMode.CLAMP);

        text.getPaint().setShader(mLinearGradient);

        text.invalidate();

    }

    @Override
    public void onDismiss(@NonNull DialogInterface dialog) {
        super.onDismiss(dialog);
        if (back != null) {
            back.onClick();
        }
    }

    private GameWinRecordCallBack back;

    public void setGameWinRecordCallBack(GameWinRecordCallBack callBack) {
        this.back = callBack;
    }

    public interface GameWinRecordCallBack {
        public void onClick();
    }
}
