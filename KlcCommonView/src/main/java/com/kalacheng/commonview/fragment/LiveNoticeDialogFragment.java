package com.kalacheng.commonview.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.RequiresApi;

import com.kalacheng.base.base.BaseDialogFragment;
import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.commonview.R;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.makeramen.roundedimageview.RoundedImageView;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

public class LiveNoticeDialogFragment extends BaseDialogFragment
        implements ImageResultCallback {

    ImageView closeIv;
    TextView okTv;
    EditText titleTv;
    TextView timeTv;
    NumberPicker numberPicker;
    TimePicker timePicker;
    RoundedImageView posterIv;


    ArrayList<String> dates;
    ArrayList<String> monthDate;
    ArrayList<Long> millis;

    long stamp;

    String monthDateStr;
    String year = "";
    String dayText = "今天";
    String hour = "";
    String minute = "";

    String picture;
    private ProcessImageUtil mImageUtil;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dialog_live_notive;
    }

    @Override
    protected int getDialogStyle() {
        return R.style.dialog;
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
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        closeIv = mRootView.findViewById(R.id.closeIv);
        okTv = mRootView.findViewById(R.id.okTv);
        titleTv = mRootView.findViewById(R.id.titleTv);
        timeTv = mRootView.findViewById(R.id.timeTv);
        numberPicker = mRootView.findViewById(R.id.numberPicker);
        timePicker = mRootView.findViewById(R.id.timePicker);
        posterIv = mRootView.findViewById(R.id.posterIv);

        mImageUtil = new ProcessImageUtil(getActivity());
        mImageUtil.setImageResultCallback(this);

        posterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });

        closeIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        getDays();
        setNumberPickerDivider(numberPicker);

        timePicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        timePicker.setIs24HourView(true);
        setTitmePickerDivider();
    }


    private void getDays() {

        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = String.valueOf(Calendar.HOUR_OF_DAY);
        minute = String.valueOf(Calendar.MINUTE);
        calendar.add(Calendar.DATE, -7);
        dates = new ArrayList<>();
        dates.add((calendar.get(Calendar.MONTH) + 1) + "月 " + calendar.get(Calendar.DAY_OF_MONTH) + "日 周" + getWorkDay(calendar.get(Calendar.DAY_OF_WEEK)));
        int value = 7;

        monthDate = new ArrayList<>();
        monthDate.add((calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH)));
        monthDateStr = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "";

        millis = new ArrayList<>();
        millis.add(calendar.getTimeInMillis());
        stamp = calendar.getTimeInMillis();

        for (int i = 1; i < 15; i++) {
            calendar.add(Calendar.DATE, 1);
            String dayStr = (calendar.get(Calendar.MONTH) + 1) + "月 " + calendar.get(Calendar.DAY_OF_MONTH) + "日 周" + getWorkDay(calendar.get(Calendar.DAY_OF_WEEK));
            if (calendar.get(Calendar.DAY_OF_MONTH) == day) {
                dayStr = "今天";
                value = i;
            }
            dates.add(dayStr);
            year = calendar.get(Calendar.YEAR) + "";
            millis.add(calendar.getTimeInMillis());
            monthDate.add((calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH)));
            monthDateStr = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "";
        }

        monthDateStr = monthDate.get(0);

        numberPicker.setDisplayedValues(dates.toArray(new String[dates.size()]));
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(dates.size());
        numberPicker.setValue(value + 1);

        numberPicker.setWrapSelectorWheel(false);
        numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayText = dates.get(newVal - 1);
                stamp = millis.get(newVal - 1);
                monthDateStr = monthDate.get(newVal - 1);
                setTimeTv();
            }
        });

        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                LiveNoticeDialogFragment.this.hour = String.valueOf(hourOfDay);
                LiveNoticeDialogFragment.this.minute = String.valueOf(minute);
                setTimeTv();
            }
        });

        okTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                addNotice();
            }
        });

    }

    private String getWorkDay(int d) {
        switch (d) {
            case 1:
                return "日";
            case 2:
                return "一";
            case 3:
                return "二";
            case 4:
                return "三";
            case 5:
                return "四";
            case 6:
                return "五";
            case 7:
                return "六";
            default:
                return "";
        }
    }


    private void setTitmePickerDivider() {
        Resources systemResources = Resources.getSystem();
        int hourNumberPickerId = systemResources.getIdentifier("hour", "id", "android");
        int minuteNumberPickerId = systemResources.getIdentifier("minute", "id", "android");

        NumberPicker hourNumberPicker = (NumberPicker) timePicker.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) timePicker.findViewById(minuteNumberPickerId);
        setNumberPickerDivider(hourNumberPicker);
        setNumberPickerDivider(minuteNumberPicker);
    }


    private void setNumberPickerDivider(NumberPicker numberPicker) {
        NumberPicker picker = numberPicker;
        Field[] pickerFields = NumberPicker.class.getDeclaredFields();
        for (Field pf : pickerFields) {
            if (pf.getName().equals("mSelectionDivider")) {  //设置颜色
                pf.setAccessible(true);
                ColorDrawable colorDrawable = new ColorDrawable(0xFFDEDEDE); //选择自己喜欢的颜色
                try {
                    pf.set(numberPicker, colorDrawable);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
            if (pf.getName().equals("mSelectionDividerHeight")) {   //设置高度
                pf.setAccessible(true);
                try {
                    int result = 1;  //要设置的高度
                    pf.set(picker, result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            }
            picker.invalidate();
        }
    }

    private void setTimeTv() {
        timeTv.setText(dayText + "  " + hour + ": " + minute);
    }


    private void showImageDialog() {
        DialogUtil.showStringArrayDialog(getContext(), new Integer[]{
                R.string.camera, R.string.alumb}, new DialogUtil.StringArrayDialogCallback() {
            @RequiresApi(api = Build.VERSION_CODES.M)
            @Override
            public void onItemClick(String text, int tag) {
                if (tag == R.string.camera) {
                    mImageUtil.getImageByCamera(false);
                } else if (tag == R.string.alumb) {
                    mImageUtil.getImageByAlbumCustom(false);
                }
            }
        });
    }

    @Override
    public void beforeCamera() {

    }

    @Override
    public void onSuccess(File file) {
        final Dialog dialog = DialogUtil.loadingDialog(getContext());
        dialog.show();
        UploadUtil.getInstance().uploadPicture(1, file, new PictureUploadCallback() {
            @Override
            public void onSuccess(String imgStr) {
                picture = imgStr;
                ImageLoader.display(imgStr, posterIv);
                dialog.dismiss();
            }

            @Override
            public void onFailure() {
                ToastUtil.show("图片上传失败");
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onFailure() {

    }

    private void addNotice() {
        if (TextUtils.isEmpty(titleTv.getText().toString().trim())) {
            ToastUtil.show("请填写标题");
            return;
        }
        if (TextUtils.isEmpty(picture)) {
            ToastUtil.show("请上传海报");
            return;
        }

        String title = titleTv.getText().toString().trim();
        String time = year + "-" + monthDateStr + " " + hour + ":" + minute;
        stamp = new DateUtil(time, "yyyy-MM-dd HH:mm").toDate().getTime();

        HttpApiShopBusiness.saveLiveAnnouncement(dayText + " " + hour + "：" + minute, picture, "", stamp + "", title, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("添加成功");
                } else {
                    ToastUtil.show("添加失败");
                }
            }
        });

    }
}
