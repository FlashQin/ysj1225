package com.kalacheng.maputils;

import android.text.TextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.libbas.model.HttpNone;

import com.kalacheng.util.utils.AppUtil;
import com.kalacheng.util.utils.CommonCallback;
import com.kalacheng.util.utils.L;
import com.kalacheng.util.utils.SpUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.kalacheng.base.activty.BaseApplication;
import com.kalacheng.base.http.HttpApiCallBack;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;

import java.util.List;

public class LocationUtil {
    private static final String TAG = "定位";
    private static LocationUtil sInstance;
    private TencentLocationManager mLocationManager;
    private boolean mLocationStarted;
    private boolean mNeedPostLocationEvent;//是否需要发送定位成功事件

    private LocationUtil() {
        mLocationManager = TencentLocationManager.getInstance(BaseApplication.getInstance());
    }

    public static LocationUtil getInstance() {
        if (sInstance == null) {
            synchronized (LocationUtil.class) {
                if (sInstance == null) {
                    sInstance = new LocationUtil();
                }
            }
        }
        return sInstance;
    }


    private TencentLocationListener mLocationListener = new TencentLocationListener() {
        @Override
        public void onLocationChanged(TencentLocation location, int code, String reason) {
            if (code == TencentLocation.ERROR_OK) {
                double lng = location.getLongitude();//经度
                double lat = location.getLatitude();//纬度
                L.e(TAG, "获取经纬度成功------>经度：" + lng + "，纬度：" + lat);
                getAddressInfoByTxLocaitonSdk(lng, lat, 0, 1, mCallback);
                if (mNeedPostLocationEvent) {
//                    EventBus.getDefault().post(new LocationEvent(lng, lat));
                }
            }
        }

        @Override
        public void onStatusUpdate(String s, int i, String s1) {

        }
    };

    private CommonCallback<TxLocationBean> mCallback = new CommonCallback<TxLocationBean>() {
        @Override
        public void callback(TxLocationBean bean) {
            L.e(TAG, "获取位置信息成功---当前地址--->" + bean.getAddress());
            String city = bean.getCity();
            if (!TextUtils.isEmpty(city) && city.endsWith("市")) {
                city = city.substring(0, city.length() - 1);
            }
            SpUtil.getInstance().put(SpUtil.CITY, city);
            SpUtil.getInstance().put(SpUtil.ADDRESS, bean.getLandmark());
            SpUtil.getInstance().put(SpUtil.LATITUDE, (float) bean.getLat());
            SpUtil.getInstance().put(SpUtil.LONGITUDE, (float) bean.getLng());

            HttpApiAppUser.user_update(bean.getLandmark(), null, null, city, null, -1, (float) bean.getLat(), null, (float) bean.getLng(), null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {

                    }
                }
            });
        }
    };


    //启动定位
    public void startLocation() {
        if (!mLocationStarted && mLocationManager != null) {
            mLocationStarted = true;
            L.e(TAG, "开启定位");
            TencentLocationRequest request = TencentLocationRequest
                    .create()
                    .setRequestLevel(TencentLocationRequest.REQUEST_LEVEL_GEO)
                    .setInterval(60 * 60 * 1000);//1小时定一次位

            //当定位周期大于0时, 不论是否有得到新的定位结果, 位置监听器都会按定位周期定时被回调;
            // 当定位周期等于0时, 仅当有新的定位结果时, 位置监听器才会被回调(即, 回调时机存在不确定性).
            // 如果需要周期性回调, 建议将 定位周期 设置为 5000-10000ms
            mLocationManager.requestLocationUpdates(request, mLocationListener);
        }
    }

    //停止定位
    public void stopLocation() {
        if (mLocationStarted && mLocationManager != null) {
            L.e(TAG, "关闭定位");
            mLocationManager.removeUpdates(mLocationListener);
            mLocationStarted = false;
        }
    }

    public void setNeedPostLocationEvent(boolean needPostLocationEvent) {
        mNeedPostLocationEvent = needPostLocationEvent;
    }

    /**
     * 使用腾讯定位sdk获取 位置信息
     *
     * @param lng 经度
     * @param lat 纬度
     * @param poi 是否要查询POI
     */
    public static void getAddressInfoByTxLocaitonSdk(final double lng, final double lat, final int poi, int pageIndex, final CommonCallback<TxLocationBean> commonCallback) {
        OkGo.<String>get("https://apis.map.qq.com/ws/geocoder/v1/")
                .params("location", lat + "," + lng)
                .params("get_poi", poi)
                .params("poi_options", "address_format=short;radius=1000;page_size=20;page_index=" + pageIndex + ";policy=5")
                .params("key", AppUtil.getMetaDataString("TencentMapSDK"))
                .tag("getInfoByTxSdk")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject obj = JSON.parseObject(response.body());
                        if (obj.getIntValue("status") == 0) {
                            JSONObject result = obj.getJSONObject("result");
                            if (result != null) {
                                TxLocationBean bean = new TxLocationBean();
                                bean.setLng(lng);
                                bean.setLat(lat);
                                bean.setAddress(result.getString("address"));
                                JSONObject addressComponent = result.getJSONObject("address_component");
                                if (addressComponent != null) {
                                    bean.setNation(addressComponent.getString("nation"));
                                    bean.setProvince(addressComponent.getString("province"));
                                    bean.setCity(addressComponent.getString("city"));
                                    bean.setDistrict(addressComponent.getString("district"));
                                    bean.setStreet(addressComponent.getString("street"));
                                }
                                JSONObject addressReference = result.getJSONObject("address_reference");
                                if (addressReference != null) {
                                    JSONObject landmark = addressReference.getJSONObject("landmark_l2");
                                    if (landmark != null) {
                                        bean.setLandmark(landmark.getString("title"));
                                    }
                                }

                                if (poi == 1) {
                                    List<TxLocationPoiBean> poiList = JSON.parseArray(result.getString("pois"), TxLocationPoiBean.class);
                                    bean.setPoiList(poiList);
                                }
                                if (commonCallback != null) {
                                    commonCallback.callback(bean);
                                }
                            }
                        }
                    }
                });
    }

    /**
     * 使用腾讯地图API进行搜索
     *
     * @param lng 经度
     * @param lat 纬度
     */
    public static void searchAddressInfoByTxLocaitonSdk(final double lng, final double lat, String keyword, int pageIndex, final CommonCallback<List<TxLocationPoiBean>> commonCallback) {
        OkGo.<String>get("https://apis.map.qq.com/ws/place/v1/search?")
                .params("keyword", keyword)
                .params("boundary", "nearby(" + lat + "," + lng + ",1000)&orderby=_distance&page_size=20&page_index=" + pageIndex)
                .params("key", AppUtil.getMetaDataString("TencentMapSDK"))
                .tag("searchInfoByTxSdk")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        JSONObject obj = JSON.parseObject(response.body());
                        if (obj.getIntValue("status") == 0) {
                            List<TxLocationPoiBean> poiList = JSON.parseArray(obj.getString("data"), TxLocationPoiBean.class);
                            if (commonCallback != null) {
                                commonCallback.callback(poiList);
                            }
                        }
                    }
                });
    }
}
