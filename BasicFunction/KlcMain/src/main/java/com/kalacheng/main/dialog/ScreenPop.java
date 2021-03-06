package com.kalacheng.main.dialog;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.kalacheng.main.R;
import com.kalacheng.util.utils.ConfigUtil;
import com.kalacheng.util.utils.DpUtil;

public class ScreenPop extends PopupWindow {
    RadioGroup radioGroup;
    RadioButton oneRb;
    RadioButton twoRb;
    RadioButton threeRb;
    RadioButton fourRb;
    RadioButton fiveRb;
    int i = 0;
    int type = 0;
    OnDismissListener onDismissListener;

    public ScreenPop(Context context, OnDismissListener onDismissListener) {
        this.onDismissListener = onDismissListener;
        setContentView(LayoutInflater.from(context).inflate(R.layout.dialog_screen, null));
        setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
        setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);

        radioGroup = getContentView().findViewById(R.id.radioGroup);
        oneRb = getContentView().findViewById(R.id.oneRb);
        twoRb = getContentView().findViewById(R.id.twoRb);
        threeRb = getContentView().findViewById(R.id.threeRb);
        fourRb = getContentView().findViewById(R.id.fourRb);
        fiveRb = getContentView().findViewById(R.id.fiveRb);


        getContentView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });


        oneRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (oneRb.isChecked()) {
                    i = 1;
                    radioGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 300);
                    sendData();
                }
            }
        });
        twoRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (twoRb.isChecked()) {
                    i = 2;
                    radioGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 300);
                    sendData();
                }
            }
        });
        threeRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (threeRb.isChecked()) {
                    i = 3;
                    radioGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 300);
                    sendData();
                }
            }
        });
        fourRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fourRb.isChecked()) {
                    i = 4;
                    radioGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 300);
                    sendData();
                }
            }
        });
        fiveRb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fiveRb.isChecked()) {
                    i = 5;
                    radioGroup.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            dismiss();
                        }
                    }, 300);
                    sendData();
                }
            }
        });


//        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(RadioGroup group, int checkedId) {
//                switch (checkedId) {
//                    case R.id.oneRb:
//                        i = 1;
//                        break;
//                    case R.id.twoRb:
//                        i = 2;
//                        break;
//                    case R.id.threeRb:
//                        i = 3;
//                        break;
//                    case R.id.fourRb:
//                        i = 4;
//                        break;
//                    case R.id.fiveRb:
//                        i = 5;
//                        break;
//                    default:
//                        i = 0;
//                }
//                radioGroup.postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        dismiss();
//                    }
//                }, 300);
//            }
//        });

        setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if (ScreenPop.this.onDismissListener != null) {
                    ScreenPop.this.onDismissListener.onDismissShow(type);
                }
            }
        });

        setFocusable(true);
        setOutsideTouchable(false);
        setClippingEnabled(false);
        ColorDrawable dw = new ColorDrawable(0x33000000);
        setBackgroundDrawable(dw);
    }


    /**
     * ?????????????????????  ?????? ????????????
     */
    private void sendData() {
        if (ScreenPop.this.onDismissListener != null) {
            ScreenPop.this.onDismissListener.onDismiss(type, i);
        }
    }

    /**
     * ??????????????????
     *
     * @param anchor ??????view
     * @param type   ???????????? 0?????????1????????????
     * @param index  ???????????? ?????? id
     */
    public void show(View anchor, int type, int index) {
        this.type = type;
        //??????????????????????????? true ??????
        if (ConfigUtil.getBoolValue(R.bool.sexNormal)) {
            //??????
            //??????????????????????????????????????????
            if (type == 0) {
                fourRb.setVisibility(View.GONE);
                fiveRb.setVisibility(View.GONE);
            } else {
                fourRb.setVisibility(View.VISIBLE);
                fiveRb.setVisibility(View.VISIBLE);
            }

            twoRb.setText(type == 0 ? "???" : "?????????");
            threeRb.setText(type == 0 ? "???" : "?????????");

        } else {
            //?????????
            twoRb.setText(type == 0 ? "0" : "?????????");
            threeRb.setText(type == 0 ? "0.5" : "?????????");
            fourRb.setText(type == 0 ? "1" : "??????");
            fiveRb.setText(type == 0 ? "..." : "??????");
        }

        if (type == 0) {
            index = getIndexTagSex(index);
        } else {
            //????????????
            if (index == -1) {
                index += 2;
            } else {
                index++;
            }
        }

        i = index;
        if (index == 1) {
            //??????
            oneRb.setChecked(true);
        } else if (index == 2) {
            twoRb.setChecked(true);
        } else if (index == 3) {
            threeRb.setChecked(true);
        } else if (index == 4) {
            fourRb.setChecked(true);
        } else if (index == 5) {
            fiveRb.setChecked(true);
        }

        if (Build.VERSION.SDK_INT >= 24) {
            Rect rect = new Rect();
            anchor.getGlobalVisibleRect(rect);
            int h = DpUtil.getScreenHeight() - rect.bottom;
            setHeight(h);
        }
        showAsDropDown(anchor);
    }

    /**
     * ??????id ??????????????? ???????????????
     *
     * @param id ???????????? ?????? id
     * @return ?????????????????????
     */
    private int getIndexTagSex(int id) {
        int index = 1;

        if (ConfigUtil.getBoolValue(R.bool.sexNormal)) {
            //??????
            switch (id) {
                case -1:
                    //???????????????
                    index = 1;
                    break;
                case 1:
                    //???????????????
                    index = 2;
                    break;
                case 2:
                    //0
                    index = 3;
                    break;
            }
        } else {
            switch (id) {
                case -1:
                    //???????????????
                    index = 1;
                    break;
                case 3:
                    //0
                    index = 2;
                    break;
                case 4:
                    //0.5
                    index = 3;
                    break;
                case 1:
                    //1
                    index = 4;
                    break;
                case 5:
                    //...
                    index = 5;
                    break;
            }
        }


        return index;
    }


    public interface OnDismissListener {
        void onDismiss(int type, int i);

        void onDismissShow(int type);
    }

}
