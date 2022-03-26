package com.kalacheng.centercommon.fragment;

import android.annotation.SuppressLint;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.OrientationHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.kalacheng.base.base.BaseFragment;
import com.kalacheng.buscommon.model.TaskDto;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.centercommon.R;
import com.kalacheng.centercommon.adapter.GradeReListAdpater;
import com.kalacheng.centercommon.adapter.UserTaskAdapter;
import com.kalacheng.commonview.dialog.SignInDialog;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.base.http.HttpApiCallBackArr;
import com.kalacheng.libuser.model.ApiGradeReWarRe;
import com.kalacheng.libuser.model.ApiSignIn;
import com.kalacheng.libuser.model.ApiSignInDto;
import com.kalacheng.util.utils.CheckDoubleClick;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.ToastUtil;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.kalacheng.util.view.ItemDecoration;

import java.util.List;

/**
 * 用户任务
 */
public class UserTaskFragment extends BaseFragment {

    private TextView UserTask_Sign_Day;
    private ImageView UserTask_Sign_GiftImage;
    private TextView UserTask_Sign_GiftName;
    private LinearLayout UserTask_Sign_GiftInfor;
    private TextView UserTask_Sign_TaskNextGrade;
    private TextView UserTask_Sign_TaskGrade;
    //当前经验值
    private TextView UserTask_Sign_EmpiricalValue;
    //距离升级
    private TextView UserTask_Sign_NextEmpiricalValue;
    //礼包列表
    private RecyclerView UserTask_Sign_GradeReList;
    GradeReListAdpater gradeReListAdpater;

    private RelativeLayout UserTask_Sign_GradePro_Re;
    private ImageView UserTask_Sign_GradePro;

    //任务列表
    private RecyclerView UserTask_TaskList;

    private TextView UserTask_Sign;

    private ApiSignInDto apiSignInDto;

    //签到 Dialog弹窗
    private SignInDialog dialog;

    public UserTaskFragment() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.user_task;
    }

    @SuppressLint("WrongConstant")
    @Override
    protected void initView() {
        UserTask_Sign_Day = mParentView.findViewById(R.id.UserTask_Sign_Day);
        UserTask_Sign_GiftImage = mParentView.findViewById(R.id.UserTask_Sign_GiftImage);
        UserTask_Sign_GiftName = mParentView.findViewById(R.id.UserTask_Sign_GiftName);
        UserTask_Sign_GiftInfor = mParentView.findViewById(R.id.UserTask_Sign_GiftInfor);
        UserTask_Sign_TaskNextGrade = mParentView.findViewById(R.id.UserTask_Sign_TaskNextGrade);
        UserTask_Sign_TaskGrade = mParentView.findViewById(R.id.UserTask_Sign_TaskGrade);
        UserTask_Sign_EmpiricalValue = mParentView.findViewById(R.id.UserTask_Sign_EmpiricalValue);
        UserTask_Sign_NextEmpiricalValue = mParentView.findViewById(R.id.UserTask_Sign_NextEmpiricalValue);
        UserTask_Sign_GradePro_Re = mParentView.findViewById(R.id.UserTask_Sign_GradePro_Re);
        UserTask_Sign_GradePro = mParentView.findViewById(R.id.UserTask_Sign_GradePro);

        UserTask_Sign_GradeReList = mParentView.findViewById(R.id.UserTask_Sign_GradeReList);
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(OrientationHelper.HORIZONTAL);
        UserTask_Sign_GradeReList.setLayoutManager(manager);
        UserTask_Sign_GradeReList.addItemDecoration(new ItemDecoration(getActivity(), 0, 10, 0));
        gradeReListAdpater = new GradeReListAdpater(getActivity());
        UserTask_Sign_GradeReList.setAdapter(gradeReListAdpater);

        UserTask_TaskList = mParentView.findViewById(R.id.UserTask_TaskList);
        LinearLayoutManager mTaskListManager = new LinearLayoutManager(getActivity());
        mTaskListManager.setOrientation(OrientationHelper.VERTICAL);
        UserTask_TaskList.setLayoutManager(mTaskListManager);

        UserTask_Sign = mParentView.findViewById(R.id.UserTask_Sign);
        UserTask_Sign.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CheckDoubleClick.isFastDoubleClick()) return;
                if (apiSignInDto == null) return;
                if (apiSignInDto.isSign == 0) {
                    dialog = new SignInDialog(getActivity(), apiSignInDto, new SignInDialog.SignInListener() {
                        @Override
                        public void signIn() {
                            getSign();
                        }
                    });
                    dialog.show();
                } else {
                    ToastUtil.show("今日已签到");
                }
            }
        });

    }

    @Override
    protected void initData() {
        getSign();
        getInformation();
        getMyTask();
    }

    //查询签到
    public void getSign() {
        HttpApiAppUser.getSignInfo(new HttpApiCallBack<ApiSignInDto>() {
            @Override
            public void onHttpRet(int code, String msg, ApiSignInDto retModel) {
                if (code == 1 && retModel != null) {
                    apiSignInDto = retModel;
                    getSignUI(retModel);
                }
            }
        });

    }

    //任务等级
    public void getSignUI(ApiSignInDto apiSignInDto) {
        if (apiSignInDto.isSign == 0) {
            UserTask_Sign.setText("立即签到");
        } else {
            UserTask_Sign.setText("已签到");
            UserTask_Sign.setBackgroundResource(R.drawable.bg_user_task_btn_grey);
        }
        UserTask_Sign_Day.setText("每日签到（第" + String.valueOf(apiSignInDto.signDay == 0 ? 1 : apiSignInDto.signDay) + "天）");
        if (apiSignInDto.signList != null && apiSignInDto.signList.size() >= apiSignInDto.signDay) {
            UserTask_Sign_GiftInfor.setVisibility(View.VISIBLE);
            ApiSignIn apiSignIn = apiSignInDto.signList.get((apiSignInDto.signDay == 0 ? 1 : apiSignInDto.signDay) - 1);
            if (apiSignIn.type == 1) {//金币
                ImageLoader.display(R.mipmap.icon_money_big, UserTask_Sign_GiftImage);
                UserTask_Sign_GiftName.setText(SpUtil.getInstance().getCoinUnit() + "*" + apiSignIn.typeVal);
            } else {
                ImageLoader.display(apiSignIn.image, UserTask_Sign_GiftImage, R.mipmap.ic_launcher, R.mipmap.ic_launcher);
                UserTask_Sign_GiftName.setText(apiSignIn.name);
            }
        } else {
            UserTask_Sign_GiftInfor.setVisibility(View.GONE);
        }

    }

    //查询用户信息
    public void getInformation() {
        HttpApiAppUser.userLevelInfo(1, new HttpApiCallBack<ApiGradeReWarRe>() {
            @Override
            public void onHttpRet(int code, String msg, ApiGradeReWarRe retModel) {
                if (code == 1) {
                    getgetInformationUI(retModel);
                }
            }
        });
    }

    public void getgetInformationUI(ApiGradeReWarRe levelPackList) {
        UserTask_Sign_TaskNextGrade.setText("完成任务获得LV" + levelPackList.nextLevel + "级大礼包");
        UserTask_Sign_TaskGrade.setText("当前：LV" + levelPackList.currLevel);
        UserTask_Sign_EmpiricalValue.setText("经验值：" + (levelPackList.nextLevelTotalEmpirical - levelPackList.nextLevelEmpirical));
        UserTask_Sign_NextEmpiricalValue.setText("距离升级：" + levelPackList.nextLevelEmpirical);
        if (levelPackList.apiGradeReList != null && levelPackList.apiGradeReList.size() != 0) {
            gradeReListAdpater.getData(levelPackList.apiGradeReList);
        } else {
            UserTask_Sign_TaskNextGrade.setVisibility(View.GONE);
            UserTask_Sign_GradeReList.setVisibility(View.GONE);
        }
//        getUserGradePro(levelPackList.nextLevelTotalEmpirical,(levelPackList.nextLevelTotalEmpirical- levelPackList.nextLevelEmpirical));
    }

    //获取用户任务
    private void getMyTask() {
        HttpApiAppUser.userTaskList(new HttpApiCallBackArr<TaskDto>() {
            @Override
            public void onHttpRet(int code, String msg, List<TaskDto> retModel) {
                if (code == 1 && null != retModel) {
                    if (retModel.size() > 0) {
                        UserTaskAdapter userTaskAdapter = new UserTaskAdapter();
                        userTaskAdapter.setData(retModel);
                        UserTask_TaskList.setAdapter(userTaskAdapter);
                    }
                }
            }
        });
    }

//    //签到
//    public void getSignIn(){
//        HttpApiAppUser.signIn(new HttpApiCallBack<HttpNone>() {
//            @Override
//            public void onHttpRet(int code, String msg, HttpNone retModel) {
//                if (code ==1){
//                    getSign();
//                    getInformation();
//                    getMyTask();
//                    ToastUtil.show("签到成功");
//                }else {
//                    ToastUtil.show(msg);
//                }
//                if (null != dialog && dialog.isShowing()){
//                    dialog.dismiss();
//                }
//            }
//        });
//    }

    //设置等级升级进度条
    public void getUserGradePro(int total, int nextGrade) {
        int Frame_height = UserTask_Sign_GradePro_Re.getWidth();

        int poistion = (int) (((double) nextGrade / (double) total) * Frame_height);

        RelativeLayout.LayoutParams params0 = (RelativeLayout.LayoutParams) UserTask_Sign_GradePro.getLayoutParams();
        params0.width = poistion;
        UserTask_Sign_GradePro.setLayoutParams(params0);
    }
}
