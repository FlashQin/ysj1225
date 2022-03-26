package com.kalacheng.util.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by cxf on 2018/9/17.
 * SharedPreferences 封装
 */

public class SpUtil {

    private static SpUtil sInstance;
    private SharedPreferences mSharedPreferences;
    private String coinUnit;//虚拟货币单位
    private String[] shortVideoIds;

    //config配制信息
    public static final String CONFIG_IMAGE_QUALITY = "imageQuality";//直播画质
    public static final String CONFIG_SOCKET_IP = "socketIp";
    public static final String CONFIG_SOCKET_PORT = "socketPort";
    public static final String CONFIG_LOGIN_TYPE = "loginType";//登录方式
    public static final String CONFIG_SHARE_TYPE = "shareType";//分享方式
    public static final String CONFIG_STY_KEY = "styKey";//直播云key
    public static final String CONFIG_CDN_KEY = "cdnKey";//CDN key
    public static final String CONFIG_ROOM_TYPE = "roomType";//多人直播房间类型
    public static final String CONFIG_VOICEROOM_TYPE = "voiceRoomType";//多人语音房间类型
    public static final String CONFIG_IS_REG_CODE = "isRegCode";//必填邀请码
    public static final String CONFIG_IM_KEY = "imKey";//im所需要的Key
    public static final String CONFIG_VCUNIT = "vcUnit";//虚拟货币单位
    public static final String CONFIG_BARRAGEFEE = "barrageFee";//弹幕费用
    public static final String CONFIG_BANNER_JUMP_MODE = "jumpMode";//广告跳转方式，0 app内；1 外部浏览器
    public static final String CONFIG_ISSHWCOIN = "isShowCoin";//是否显示用户余额0开启1关闭
    public static final String CONFIG_MUISC = "pushMusic";//背景音乐
    public static final String CONFIG_ISCOMMENT = "CONFIG_ISCOMMENT";//是否开启用户评论
    public static final String CONFIG_VIDEO_FEE = "isShortVideoFee";//是否开启用户评论
    public static final String CONFIG_USER_CANCEL = "configUserCancel";//注销文字信息
    public static final String CONFIG_WITHDRAWAL_RULE = "configWithdrawalRule";//提现规则
    public static final String CONFIG_VIPSTATESFEE = "VIPStatesFee";//贵宾席费用
    public static final String CONFIG_CLOUD_TYPE = "configCloudType";//存储方式
    public static final String CONFIG_WX_APP_ID = "configWxAppId";//微信支付的AppId
    public static final String CONFIG_XIEYI_RULE = "configXieyiRule";//协议提示内容
    public static final String CONFIG_BIND_PHONE = "configBindPhone";//三方登录，是否必须绑定手机号码0开启1关闭
    public static final String CONFIG_PAY_LIST = "configPayList";//支付配置
    public static final String CONFIG_VIDEO_CLIP_KEY = "configVideoClipKey";
    public static final String CONFIG_HOT_LINE = "configHotLine";//客服
    public static final String CONFIG_HOT_QQ = "QQ";//客服QQ
    public static final String CONFIG_HOT_WX = "WX";//客服WX
    public static final String CONFIG_HOT_WXCODE = "wechatCode";//客服WX
    public static final String CONFIG_DEFAULT_SIGNATURE = "defaultSignature";//默认个性签名


    public static final String ANCHOR_ID = "ANCHOR_ID";//主播ID

    public static final String CONFIG_A_TO_A = "anchorToAnchor";//主播和主播发起通话0开启1关闭
    public static final String CONFIG_U_TO_U = "userToUser";//用户和用户发起通话0开启1关闭
    public static final String CONFIF_NOBLE_CHAT_FREE = "nobleChatFree";

    //个人信息
    public static final String USER_INFO = "UserInfo";//个人信息
    public static final String ANCHORID = "anchorid";
    public static final String UID = "uid";
    public static final String TOKEN = "token";
    public static final String USER_IS_FIRST_LOGIN = "isFirstLogin";//用户是否第一次登录
    public static final String USER_IS_PID = "isPid";//用户是否需要填写邀请码
    public static final String READ_SHORT_VIDEO_NUMBER = "ReadShortVideoNumber";//免费观影私密视频次数
    public static final String READ_SHORT_VIDEO_URLS = "ReadShortVideoUrls"; // 购买过的视频地址 集合
    public static final String FIRST_VIDEO_SHOP_TIPS = "FirstVideoShopTips"; // 直播带货 发布的短视频 新用户弹出提示PopupWindow

    //其它
    public static final String SYS_NOTICE_SHOWED_DATA = "sys_notice_showed_data";//系统消息显示过的信息

    public static final String VOICE_VALUE = "voice_value";//语音声音


    public static final String CONFIG = "config";
    public static final String IM_LOGIN = "jimLogin";
    public static final String PUSH_LOGIN = "jpushLogin";
    public static final String HAS_SYSTEM_MSG = "hasSystemMsg";
    public static final String LOCATION_LNG = "locationLng";
    public static final String LOCATION_LAT = "locationLat";
    public static final String LOCATION_PROVINCE = "locationProvince";
    public static final String LOCATION_CITY = "locationCity";
    public static final String LOCATION_DISTRICT = "locationDistrict";
    public static final String FIRST = "first";
    public static final String LATITUDE = "latitude";
    public static final String LONGITUDE = "longitude";
    public static final String ADDRESS = "address";
    public static final String CITY = "city";
    public static final String LIVELATITUDE = "livelatitude";
    public static final String LIVELONGITUDE = "livelongitude";
    public static final String LIVEADDRESS = "liveaddress";
    public static final String LIVE_CITY = "liveCity";
    public static final String BEAUTY = "beauty";
    public static final String BRIGHT = "bright";
    public static final String FIRST_PIC_BROWSE = "first_pic_browse";//是否第一次进入主界面
    public static final String BEAUTY_SWITCH = "beauty_switch";
    public static final String BEAUTY_KEY = "beauty_key";
    public static final String FREE_CALL = "free_call";
    public static final String FREE_CALL_two = "free_call_two";
    public static final String AUTH_IS_SEX = "auth_is_sex";
    public static final String AdminVideoConfig = "AdminVideoConfig";
    public static final String BIRTHDAY = "Birthday_Welcome"; // 生日
    public static final String NOBLE_BARRAGE = "NobleBarrage";//贵族弹幕特权
    public static final String MUSIC_UP_LOAD = "MusicUpload"; // 上传音乐集合 退出App 不清除数据

    // 1对1
    public static final String STATIC_GRAD_CHAT = "staticGrabChat"; // 抢聊，订单全局提示，按钮是否打开

    public static final String USER_ALIAS = "USER_ALIAS";//当前用户使用的别名
    private SharedPreferences.Editor editor;

    /**
     * 用户今日是否第一次登录
     * （用于今天第一次进入弹出签到弹出框）
     * 标记 存入当前日期，（当天第一次，为""）
     */
    public static final String USER_TODAY_IS_FIRST_LOGIN = "user_today_is_first_login";


    private SpUtil() {
        mSharedPreferences = ApplicationUtil.getInstance().getSharedPreferences("SharedPreferences", Context.MODE_PRIVATE);
        editor = mSharedPreferences.edit();
    }

    public static SpUtil getInstance() {
        if (sInstance == null) {
            synchronized (SpUtil.class) {
                if (sInstance == null) {
                    sInstance = new SpUtil();
                }
            }
        }
        return sInstance;
    }

    /**
     * 存储
     */
    public void put(String key, Object object) {
        if (object == null) {
            return;
        }
        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        editor.apply();
    }

    /**
     * 获取保存的数据
     */
    public Object getSharedPreference(String key, Object defaultObject) {
        if (defaultObject instanceof String) {
            try {
                return mSharedPreferences.getString(key, (String) defaultObject);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        } else if (defaultObject instanceof Integer) {
            try {
                return mSharedPreferences.getInt(key, (Integer) defaultObject);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        } else if (defaultObject instanceof Boolean) {
            try {
                return mSharedPreferences.getBoolean(key, (Boolean) defaultObject);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        } else if (defaultObject instanceof Float) {
            try {
                return mSharedPreferences.getFloat(key, (Float) defaultObject);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        } else if (defaultObject instanceof Long) {
            try {
                return mSharedPreferences.getLong(key, (Long) defaultObject);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        } else {
            try {
                return mSharedPreferences.getString(key, null);
            } catch (Exception e) {
                e.printStackTrace();
                remove(key);
                return defaultObject;
            }
        }
    }

    public void putModel(String key, Object model) {
        if (model != null) {
            String modelStr = JSON.toJSONString(model);
            put(key, modelStr);
        }
    }

    public <T> T getModel(String key, Class<T> clazz) {
        String jsonStr = (String) getSharedPreference(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            T model = JSON.parseObject(jsonStr, clazz);
            return model;
        }
        return null;
    }

    public <T> List<T> getModelList(String key, Class<T> clazz) {
        String jsonStr = (String) getSharedPreference(key, "");
        if (!TextUtils.isEmpty(jsonStr)) {
            List<T> model = JSON.parseArray(jsonStr, clazz);
            return model;
        }
        return null;
    }

    public void clearLoginInfo() {
//        editor.clear().apply();

        //清除config配制信息外所有数据
        Map<String, ?> map = mSharedPreferences.getAll();
        Set<String> keys = map.keySet();
        List configKeys = Arrays.asList(CONFIG_IMAGE_QUALITY, CONFIG_LOGIN_TYPE, CONFIG_SHARE_TYPE, CONFIG_STY_KEY, CONFIG_CDN_KEY, CONFIG_ROOM_TYPE, CONFIG_IS_REG_CODE,
                CONFIG_IM_KEY, CONFIG_VCUNIT, CONFIG_BARRAGEFEE, FIRST, CONFIG_BANNER_JUMP_MODE, SYS_NOTICE_SHOWED_DATA, BEAUTY_SWITCH, BEAUTY_KEY, FIRST_PIC_BROWSE, AUTH_IS_SEX, AdminVideoConfig, CONFIG_VOICEROOM_TYPE,
                CONFIG_MUISC, CONFIG_ISCOMMENT, CONFIG_ISSHWCOIN, CONFIG_VIDEO_FEE, CONFIG_USER_CANCEL, CONFIG_WITHDRAWAL_RULE, CONFIG_SOCKET_IP, CONFIG_SOCKET_PORT, CONFIG_VIPSTATESFEE,
                CONFIG_CLOUD_TYPE, CONFIG_WX_APP_ID, CONFIG_XIEYI_RULE, CONFIG_BIND_PHONE, CONFIG_PAY_LIST, CONFIG_VIDEO_CLIP_KEY, CONFIG_HOT_LINE, VOICE_VALUE, BIRTHDAY, CONFIG_HOT_QQ, CONFIG_HOT_WX, CONFIG_HOT_WXCODE,
                CONFIG_DEFAULT_SIGNATURE, USER_TODAY_IS_FIRST_LOGIN, MUSIC_UP_LOAD);
        for (String key : keys) {
            if (!configKeys.contains(key)) {
                L.e("清空  key  " + key);
                remove(key);
            }
        }


    }

    /**
     * 移除某个key值已经对应的值
     *
     * @param key
     */
    public void remove(String key) {
        editor.remove(key);
        editor.apply();
    }

    public void removeValue(String... keys) {
        for (String key : keys) {
            editor.remove(key);
        }
        editor.apply();
    }

    /**
     * 清空全部缓存
     */
    public void clear() {
        editor.clear();
        editor.apply();
    }

    /**
     * 获取虚拟货币单位
     */
    public String getCoinUnit() {
        if (TextUtils.isEmpty(coinUnit)) {
            coinUnit = (String) getSharedPreference(CONFIG_VCUNIT, "");
        }
        return coinUnit;
    }

    // 获取免费视频id集合
    public String getShortVideoIds() {
        String videoIds;
        videoIds = (String) getSharedPreference(SpUtil.READ_SHORT_VIDEO_URLS, "");
        if (TextUtils.isEmpty(videoIds)) {
            videoIds = "";
        }
        return videoIds;
    }

    // 将免费视频 加入集合  shortVideoId: 免费视频ID       number：免费视频条数 （从后台获取）
    // 是否免费 （如果是付费 需要高斯模糊 并锁住视频，让其付费） (如果包含这一条id是免费视频 返回true 让用户观看，  如果不包含这一条id 返回false 锁住视频让用户购买)
    public boolean isFree(String shortVideoId, int number) {
        if (number > 0 && number > getShortVideoIds().split(",").length && !getShortVideoIds().contains(shortVideoId)) {
            put(SpUtil.READ_SHORT_VIDEO_URLS, shortVideoId + "," + getShortVideoIds());
            return true;
        } else if (getShortVideoIds().contains(shortVideoId)) {
            return true;
        } else {
            return false;
        }
    }

}
