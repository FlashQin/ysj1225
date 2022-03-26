package com.kalacheng.shopping.seller.fragment;

import android.app.Dialog;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.NumberPicker;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.kalacheng.busshop.httpApi.HttpApiShopBusiness;
import com.kalacheng.busshop.model.ShopBusinessLiveAnnouncementDTO;
import com.kalacheng.commonview.utils.ProcessImageUtil;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.shopping.R;
import com.kalacheng.shopping.base.SBaseFragment;
import com.kalacheng.shopping.databinding.FragmentLiveNoticeBinding;
import com.kalacheng.shopping.seller.adapter.LiveNoticeAdadpter;
import com.kalacheng.shopping.seller.viewmodel.LiveNoticeViewModel;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.DateUtil;
import com.kalacheng.util.utils.DialogUtil;
import com.kalacheng.util.utils.ImageResultCallback;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.xuantongyun.storagecloud.upload.UploadUtil;
import com.xuantongyun.storagecloud.upload.base.PictureUploadCallback;

import java.io.File;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;

import cn.we.swipe.helper.WeSwipe;

//  直播预告
public class LiveNoticeFragment extends SBaseFragment<FragmentLiveNoticeBinding, LiveNoticeViewModel>
        implements ImageResultCallback {

    ArrayList<String> dates;
    ArrayList<String> monthDate;
    ArrayList<Long> millis;


    long stamp;
    String monthDateStr = "";
    String year = "";
    String dayText = "";
    String hour = "";
    String minute = "";

    String picture;
    private ProcessImageUtil mImageUtil;

    LiveNoticeAdadpter adadpter;

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_live_notice;
    }

    @Override
    public void initData() {

        getDays();
        setNumberPickerDivider(binding.numberPicker);


        binding.timepicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        binding.timepicker.setIs24HourView(true);
        setTitmePickerDivider();

        adadpter = new LiveNoticeAdadpter();
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setAdapter(adadpter);
        WeSwipe.attach(binding.recyclerView);

        adadpter.setClickDeleteListenes(new LiveNoticeAdadpter.OnClickDeleteListenes() {
            @Override
            public void onClickDeleteListenes(long id) {
                //请求接口
                deleteNotice(id);
            }
        });

        mImageUtil = new ProcessImageUtil(getActivity());
        mImageUtil.setImageResultCallback(this);

        binding.addTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                addNotice();
//                LiveNoticeDialogFragment fragment = new LiveNoticeDialogFragment();
//                fragment.show(getChildFragmentManager(),"LiveNoticeDialogFragment");
            }
        });

        binding.posterIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showImageDialog();
            }
        });

        getNoticeList();

    }

    // 获取预告列表
    private void getNoticeList() {
        HttpApiShopBusiness.getBusinessLiveAnnouncementList(new HttpApiCallBack<ShopBusinessLiveAnnouncementDTO>() {
            @Override
            public void onHttpRet(int code, String msg, ShopBusinessLiveAnnouncementDTO retModel) {
                if (code == 1) {
                    adadpter.setList(retModel.shopLiveAnnouncementList);

                    binding.line.setVisibility(retModel.shopLiveAnnouncementList.size() > 0 ? View.VISIBLE : View.GONE);
                    binding.tvTitle.setVisibility(retModel.shopLiveAnnouncementList.size() > 0 ? View.VISIBLE : View.GONE);

//                    if (retModel.shopLiveAnnouncementList.size()>0){
//                        binding.tvTitle.setVisibility(View.VISIBLE);
//                        binding.line.setVisibility(View.VISIBLE);
//                    }else {
//                        binding.tvTitle.setVisibility(View.GONE);
//                        binding.line.setVisibility(View.GONE);
//                    }
                } else {
                    binding.line.setVisibility(View.GONE);
                    binding.tvTitle.setVisibility(View.GONE);
                }
            }
        });
    }

    // 删除列表item
    private void deleteNotice(long id) {
        HttpApiShopBusiness.delLiveAnnouncement(id, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    getNoticeList();
                }
            }
        });
    }

    // 添加预告
    private void addNotice(String liveDate, String posterStickers, String shopCategory, String startTime, String title) {
        HttpApiShopBusiness.saveLiveAnnouncement(liveDate, posterStickers, shopCategory, startTime, title, new HttpApiCallBack<HttpNone>() {
            @Override
            public void onHttpRet(int code, String msg, HttpNone retModel) {
                if (code == 1) {
                    ToastUtil.show("添加成功");
                    getActivity().finish();
                } else {
                    ToastUtil.show("添加失败");
                }
            }
        });
    }

    private void getDays() {

        Calendar calendar = Calendar.getInstance();
        hour = String.valueOf(calendar.get(Calendar.HOUR_OF_DAY));
        minute = String.valueOf(calendar.get(Calendar.MINUTE));

        dates = new ArrayList<>();
        monthDate = new ArrayList<>();
        dates.add("今天");
        millis = new ArrayList<>();
        millis.add(calendar.getTimeInMillis());

        dayText = "今天";
        stamp = calendar.getTimeInMillis();

        monthDate.add((calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH)));
        monthDateStr = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "";

        for (int i = 1; i < 6; i++) {
            calendar.add(Calendar.DATE, 1);
            String dayStr = (calendar.get(Calendar.MONTH) + 1) + "月 " + calendar.get(Calendar.DAY_OF_MONTH) + "日 周" + getWorkDay(calendar.get(Calendar.DAY_OF_WEEK));
            dates.add(dayStr);
            millis.add(calendar.getTimeInMillis());
            year = calendar.get(Calendar.YEAR) + "";
            monthDate.add((calendar.get(Calendar.MONTH) + 1) + "-" + (calendar.get(Calendar.DAY_OF_MONTH)));
            monthDateStr = (calendar.get(Calendar.MONTH) + 1) + "-" + calendar.get(Calendar.DAY_OF_MONTH) + "";
        }
        monthDateStr = monthDate.get(0);

        binding.numberPicker.setDisplayedValues(dates.toArray(new String[dates.size()]));
        binding.numberPicker.setMinValue(1);
        binding.numberPicker.setMaxValue(dates.size());
        binding.numberPicker.setValue(1);

        binding.numberPicker.setWrapSelectorWheel(false);
        binding.numberPicker.setDescendantFocusability(NumberPicker.FOCUS_BLOCK_DESCENDANTS);
        binding.numberPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                dayText = dates.get(newVal - 1);
                stamp = millis.get(newVal - 1);
                monthDateStr = monthDate.get(newVal - 1);
                setTimeTv();
            }
        });

        binding.timepicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int hourOfDay, int minute) {
                LiveNoticeFragment.this.hour = String.valueOf(hourOfDay);
                LiveNoticeFragment.this.minute = String.valueOf(minute);
                setTimeTv();
            }
        });

        setTimeTv();

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

        NumberPicker hourNumberPicker = (NumberPicker) binding.timepicker.findViewById(hourNumberPickerId);
        NumberPicker minuteNumberPicker = (NumberPicker) binding.timepicker.findViewById(minuteNumberPickerId);
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
        binding.timeTv.setText(dayText + "  " + hour + ": " + minute);
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
                ImageLoader.display(imgStr, binding.posterIv);
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
        String title = binding.titleTv.getText().toString().trim();

        if (TextUtils.isEmpty(title)) {
            ToastUtil.show("请填写标题");
            return;
        }

        if (TextUtils.isEmpty(picture)) {
            ToastUtil.show("请上传海报");
            return;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(stamp);
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));

        String time = year + "-" + monthDateStr + " " + hour + ":" + minute;
        stamp = new DateUtil(time, "yyyy-MM-dd HH:mm").toDate().getTime();

        //ToastUtil.show(year + "-" + monthDateStr + " " +hour + ":" + minute);
        addNotice(dayText + " " + hour + "：" + minute, picture, "", stamp + "", title);

    }

}
