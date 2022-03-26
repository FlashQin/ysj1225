package com.kalacheng.maputils;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.location.Location;
import android.os.Bundle;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.fastjson.JSON;
import com.kalacheng.base.activty.BaseActivity;
import com.kalacheng.util.listener.OnBeanCallback;
import com.kalacheng.buscommon.model.ApiUserInfo;
import com.kalacheng.buspersonalcenter.httpApi.HttpApiAppUser;
import com.kalacheng.frame.config.ARouterPath;
import com.kalacheng.frame.config.ARouterValueNameConfig;
import com.kalacheng.frame.config.HttpConstants;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.HttpNone;
import com.kalacheng.util.adapter.SimpleTextAdapter2;
import com.kalacheng.util.bean.SimpleTextBean;
import com.kalacheng.util.utils.PermissionsUtil;
import com.kalacheng.util.utils.SpUtil;
import com.kalacheng.util.utils.SystemUtils;
import com.kalacheng.util.utils.glide.ImageLoader;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.tencent.tencentmap.mapsdk.maps.CameraUpdateFactory;
import com.tencent.tencentmap.mapsdk.maps.LocationSource;
import com.tencent.tencentmap.mapsdk.maps.MapView;
import com.tencent.tencentmap.mapsdk.maps.TencentMap;
import com.tencent.tencentmap.mapsdk.maps.UiSettings;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptor;
import com.tencent.tencentmap.mapsdk.maps.model.BitmapDescriptorFactory;
import com.tencent.tencentmap.mapsdk.maps.model.CameraPosition;
import com.tencent.tencentmap.mapsdk.maps.model.LatLng;
import com.tencent.tencentmap.mapsdk.maps.model.MyLocationStyle;

import java.util.ArrayList;
import java.util.List;


@Route(path = ARouterPath.MapActivity)
public class MapActivity extends BaseActivity implements TencentLocationListener, LocationSource, View.OnClickListener {
    @Autowired(name = ARouterValueNameConfig.TYPE)
    public int type;//1 开播  2  编辑我的
    MapView mapview;
    //权限
    PermissionsUtil mProcessResultUtil;
    private TencentMap tencentMap;
    TencentLocationManager locationManager;
    private TencentLocationRequest locationRequest;
    OnLocationChangedListener locationChangedListener;
    private MyLocationStyle locationStyle;
    boolean isFirst = true, llSearchVisibility, isPOISearch;
    RecyclerView recyclerView;
    double latitude, longitude;
    private String city;
    EditText etAddress;
    private SimpleTextAdapter2 simpleTextAdapter2;
    ImageView ivAvatar;
    TextView tvTitle;
    private TencentSearch tencentSearch;
    List<SimpleTextBean> simpleTextBeanList = new ArrayList<>();
    private SearchPOIBean searchPOIBean;
    LinearLayout llSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        initView();
    }

    /**
     * 定位的一些初始化设置
     */
    private void initLocation() {

        tencentSearch = new TencentSearch(MapActivity.this);

        //用于访问腾讯定位服务的类, 周期性向客户端提供位置更新
        locationManager = TencentLocationManager.getInstance(this);
        //设置坐标系
        locationManager.setCoordinateType(TencentLocationManager.COORDINATE_TYPE_GCJ02);
        //创建定位请求
        locationRequest = TencentLocationRequest.create();
        //设置定位周期（位置监听器回调周期）为3s
//        locationRequest.setInterval(3000);

        //地图上设置定位数据源
        tencentMap.setLocationSource(this);
        //设置当前位置可见
        tencentMap.setMyLocationEnabled(true);
        UiSettings mapUiSettings = tencentMap.getUiSettings();
        /**
         * 当通过TencentMap.setLocationSource(locationSource)设置好地图的定位源后，
         * 点击此按钮可以在地图上标注一个蓝点指示用户的当前位置。
         * 可以通过UiSettings.setMyLocationButtonEnabled()接口设置此控件的显示和隐藏。
         */
        mapUiSettings.setMyLocationButtonEnabled(true);
        //设置定位图标样式
        setLocMarkerStyle();
        locationStyle = locationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW_NO_CENTER);
        tencentMap.setMyLocationStyle(locationStyle);
        tencentMap.setOnCameraChangeListener(new TencentMap.OnCameraChangeListener() {
            @Override
            public void onCameraChange(CameraPosition cameraPosition) {

            }

            @Override
            public void onCameraChangeFinished(CameraPosition cameraPosition) {
                //获取当前地图视图信息
//                info = "经纬度："+cameraPosition.target.latitude+","+cameraPosition.target.longitude+";zoom："+cameraPosition.zoom;
//                textView.setText(info);
                //还可以传入其他坐标系的坐标，不过需要用coord_type()指明所用类型
                //这里设置返回周边poi列表，可以在一定程度上满足用户获取指定坐标周边poi的需求
                latitude = cameraPosition.target.latitude;
                longitude = cameraPosition.target.longitude;
                Geo2AddressParam geo2AddressParam = new Geo2AddressParam(new LatLng(cameraPosition.target.latitude, cameraPosition.target.longitude)).getPoi(true)
                        .setPoiOptions(new Geo2AddressParam.PoiOptions()
                                .setRadius(50)
                                .setPolicy(Geo2AddressParam.PoiOptions.POLICY_DEFAULT));
                tencentSearch.geo2address(geo2AddressParam, new HttpResponseListener<BaseObject>() {

                    @Override
                    public void onSuccess(int arg0, BaseObject arg1) {
                        if (arg1 == null) {
                            return;
                        }
                        Geo2AddressResultObject obj = (Geo2AddressResultObject) arg1;
//                        StringBuilder sb = new StringBuilder();
//                        sb.append("逆地址解析");
//                        sb.append("\n地址：" + obj.result.address);
//                        sb.append("\npois:");
                        if (null != obj.result.pois && !obj.result.pois.isEmpty() && !isPOISearch) {
                            if (!TextUtils.isEmpty(obj.result.pois.get(0).title)) {
                                tvTitle.setText(obj.result.pois.get(0).title);
                            }
                            city = obj.result.ad_info.city;
                        }
                        if (isPOISearch) {
                            isPOISearch = !isPOISearch;
                        }

                        //printResult(sb.toString());
                    }

                    @Override
                    public void onFailure(int arg0, String arg1, Throwable arg2) {
                        // TODO Auto-generated method stub
//                        printResult(arg1);
                    }
                });
            }
        });

    }

    /**
     * 设置定位图标样式
     */
    private void setLocMarkerStyle() {
        locationStyle = new MyLocationStyle();
        //创建图标
        BitmapDescriptor bitmapDescriptor = BitmapDescriptorFactory.fromBitmap(getBitMap(R.mipmap.icon_maker_location));
        locationStyle.icon(bitmapDescriptor);
        //设置定位圆形区域的边框宽度
        locationStyle.strokeWidth(3);
        //设置圆区域的颜色
        locationStyle.fillColor(R.color.pk_blue);

        //tencentMap.setMyLocationStyle(locationStyle);
    }

    /**
     * 实现位置监听
     *
     * @param tencentLocation
     * @param i
     * @param s
     */
    @Override
    public void onLocationChanged(final TencentLocation tencentLocation, int i, String s) {

        if (i == TencentLocation.ERROR_OK && locationChangedListener != null) {
            Location location = new Location(tencentLocation.getProvider());
            //设置经纬度以及精度
            location.setLatitude(tencentLocation.getLatitude());
            location.setLongitude(tencentLocation.getLongitude());
            location.setAccuracy(tencentLocation.getAccuracy());
            locationChangedListener.onLocationChanged(location);
            latitude = tencentLocation.getLatitude();
            longitude = tencentLocation.getLongitude();
            //显示回调的实时位置信息
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //打印tencentLocation的json字符串
                    if (isFirst) {
//                        SpUtil.getInstance().getSharedPreference(SpUtil.ADDRESS, "");
                        float latitude = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LATITUDE, HttpConstants.LAT);
                        float longitude = (float) SpUtil.getInstance().getSharedPreference(SpUtil.LONGITUDE, HttpConstants.LNG);
                        tencentMap.animateCamera(CameraUpdateFactory.newLatLng(new LatLng((double) latitude, (double) longitude)));
                        isFirst = !isFirst;
                    }

                }
            });
        }
    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {

    }

    private void initView() {
        mapview = findViewById(R.id.mapview);
        recyclerView = findViewById(R.id.recyclerView);
        ivAvatar = findViewById(R.id.iv_avatar);
        etAddress = (EditText) findViewById(R.id.et_address);
        tvTitle = (TextView) findViewById(R.id.tv_title);
        llSearch = (LinearLayout) findViewById(R.id.ll_search);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tencentMap = mapview.getMap();
        etAddress.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchPoi(editable.toString());
            }
        });
        mProcessResultUtil = new PermissionsUtil(this);
        ApiUserInfo userBean = SpUtil.getInstance().<ApiUserInfo>getModel("UserInfo", ApiUserInfo.class);
        if (null != userBean.avatar && !TextUtils.isEmpty(userBean.avatar)) {
            ImageLoader.display(userBean.avatar, ivAvatar);
        }
        tvTitle.setOnClickListener(this);
        findViewById(R.id.iv_back).setOnClickListener(this);

    }

    @Override
    protected void onDestroy() {
        mapview.onDestroy();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapview.onPause();
        super.onPause();
    }

    @Override
    protected void onResume() {
        mapview.onResume();
        mProcessResultUtil.requestPermissions(new String[]{
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
        }, new Runnable() {
            @Override
            public void run() {
                initLocation();

            }
        });
        super.onResume();
    }

    @Override
    protected void onStop() {
        mapview.onStop();
        super.onStop();
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        locationChangedListener = onLocationChangedListener;
        int err = locationManager.requestLocationUpdates(locationRequest, this, Looper.myLooper());
        switch (err) {
            case 1:
                Toast.makeText(this, "设备缺少使用腾讯定位服务需要的基本条件", Toast.LENGTH_SHORT).show();
                break;
            case 2:
                Toast.makeText(this, "manifest 中配置的 key 不正确", Toast.LENGTH_SHORT).show();
                break;
            case 3:
                Toast.makeText(this, "自动加载libtencentloc.so失败", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
    }

    @Override
    public void deactivate() {
        locationManager.removeUpdates(this);
        locationManager = null;
        locationRequest = null;
        locationChangedListener = null;
    }

    /**
     * poi检索
     */
    protected void searchPoi(String keyWord) {

        String url = "https://apis.map.qq.com/ws/place/v1/search?boundary=nearby(" + latitude + "," + longitude + ",1000)&keyword=" + keyWord + "&page_size=20&page_index=1&orderby=_distance&key=MBZBZ-QDYCO-FW6WX-S52JI-CRTQ5-IMFCG";
//        String url = "https://apis.map.qq.com/ws/place/v1/suggestion/?keyword=" + keyWord + "&page_size=20&page_index=1&key=MBZBZ-QDYCO-FW6WX-S52JI-CRTQ5-IMFCG";
        OkGo.<String>get(url).tag(this).execute(new StringCallback() {

            @Override
            public void onSuccess(Response<String> response) {
                String responseStr = response.body().toString();
                if (null != responseStr) {
                    simpleTextBeanList.clear();
                    searchPOIBean = JSON.parseObject(responseStr, SearchPOIBean.class);
                    if (null != searchPOIBean.data && !searchPOIBean.data.isEmpty()) {
                        for (SearchPOIBean.SearchResultData searchResultData : searchPOIBean.data) {
                            SimpleTextBean simpleTextBean = new SimpleTextBean(searchResultData.id
                                    , searchResultData.title, String.format("%.2f", searchResultData._distance / 1000) + "km | " + searchResultData.ad_info.district);
                            simpleTextBeanList.add(simpleTextBean);
                            if (null == simpleTextAdapter2) {
                                simpleTextAdapter2 = new SimpleTextAdapter2(simpleTextBeanList);
                                simpleTextAdapter2.setTextWidthHight(0, 25);
                                recyclerView.setAdapter(simpleTextAdapter2);
                                simpleTextAdapter2.setOnItemClickCallback(new OnBeanCallback<SimpleTextBean>() {
                                    @Override
                                    public void onClick(SimpleTextBean bean) {
                                        isPOISearch = true;
                                        SystemUtils.closeKeyboard(etAddress);
                                        for (int i = 0; i < searchPOIBean.data.size(); i++) {
                                            if (bean.IdStr.equals(searchPOIBean.data.get(i).id)) {
                                                tvTitle.setText(searchPOIBean.data.get(i).title);
                                                if (!TextUtils.isEmpty(searchPOIBean.data.get(i).ad_info.city)) {
                                                    city = searchPOIBean.data.get(i).ad_info.city;
                                                }
                                                tencentMap.animateCamera(CameraUpdateFactory.newLatLng(
                                                        new LatLng(searchPOIBean.data.get(i).location.lat, searchPOIBean.data.get(i).location.lng)));
                                                llSearch.setVisibility(View.GONE);
                                            }
                                        }
                                    }
                                });
                            } else {
                                simpleTextAdapter2.setData(simpleTextBeanList);
                            }

                        }
                    }
                }
            }
        });
    }

    private Bitmap getBitMap(int resourceId) {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), resourceId);
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int newWidth = 55;
        int newHeight = 55;
        float widthScale = ((float) newWidth) / width;
        float heightScale = ((float) newHeight) / height;
        Matrix matrix = new Matrix();
        matrix.postScale(widthScale, heightScale);
        bitmap = Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
        return bitmap;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tv_title) {
            if (llSearchVisibility) {
                llSearch.setVisibility(View.GONE);
            } else {
                llSearch.setVisibility(View.VISIBLE);
                searchPoi(tvTitle.getText().toString().trim());
            }
            llSearchVisibility = !llSearchVisibility;

        } else if (view.getId() == R.id.iv_back) {
            onBackPressed();
        }
    }

    @Override
    public void onBackPressed() {
        if (type == 1) {
            if (!TextUtils.isEmpty(city) && city.endsWith("市")) {
                city = city.substring(0, city.length() - 1);
            }
            SpUtil.getInstance().put(SpUtil.LIVE_CITY, city);
            SpUtil.getInstance().put(SpUtil.LIVEADDRESS, tvTitle.getText().toString().trim());
            SpUtil.getInstance().put(SpUtil.LIVELATITUDE, (float) latitude);
            SpUtil.getInstance().put(SpUtil.LIVELONGITUDE, (float) longitude);
            super.onBackPressed();
        } else if (type == 2) {
            if (!TextUtils.isEmpty(city) && city.endsWith("市")) {
                city = city.substring(0, city.length() - 1);
            }
            HttpApiAppUser.user_update(tvTitle.getText().toString().trim(), null, null, city, null, -1, latitude, null, longitude, null, -1, null, null, null, null, -1, new HttpApiCallBack<HttpNone>() {
                @Override
                public void onHttpRet(int code, String msg, HttpNone retModel) {
                    if (code == 1) {
                        SpUtil.getInstance().put(SpUtil.CITY, city);
                        SpUtil.getInstance().put(SpUtil.ADDRESS, tvTitle.getText().toString().trim());
                        SpUtil.getInstance().put(SpUtil.LATITUDE, (float) latitude);
                        SpUtil.getInstance().put(SpUtil.LONGITUDE, (float) longitude);

                        Intent intent = new Intent();
                        intent.putExtra(ARouterValueNameConfig.CITY, city);
                        intent.putExtra(ARouterValueNameConfig.ADDRESS, tvTitle.getText().toString().trim());
                        intent.putExtra(ARouterValueNameConfig.LATITUDE, latitude);
                        intent.putExtra(ARouterValueNameConfig.LONGITUDE, longitude);
                        setResult(RESULT_OK, intent);
                        MapActivity.super.onBackPressed();
                    }
                }
            });
        } else {
            Intent intent = new Intent();
            if (!TextUtils.isEmpty(city) && city.endsWith("市")) {
                city = city.substring(0, city.length() - 1);
            }
            intent.putExtra(ARouterValueNameConfig.CITY, city);
            intent.putExtra(ARouterValueNameConfig.ADDRESS, tvTitle.getText().toString().trim());
            intent.putExtra(ARouterValueNameConfig.LATITUDE, latitude);
            intent.putExtra(ARouterValueNameConfig.LONGITUDE, longitude);
            setResult(RESULT_OK, intent);
            super.onBackPressed();
        }
    }
}
