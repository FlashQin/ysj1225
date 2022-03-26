package com.kalacheng.commonview.pay;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;

import com.alipay.sdk.app.PayTask;
import com.kalacheng.busfinance.httpApi.HttpApiApiPay;
import com.kalacheng.base.http.HttpApiCallBack;
import com.kalacheng.libbas.model.SingleString;
import com.kalacheng.libuser.model.AppUsersCharge;
import com.kalacheng.util.utils.ToastUtil;

import java.io.UnsupportedEncodingException;
import java.lang.ref.WeakReference;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by cxf on 2017/9/21.
 */

public class AliPayBuilder {

    private Activity mActivity;
    private String mPartner;// 商户ID
    private String mSellerId; // 商户收款账号
    private String mPrivateKey; // 商户私钥，pkcs8格式
    private String mPayInfo;//支付宝订单信息 包括 商品信息，订单签名，签名类型
    private AppUsersCharge mBean;
    private String mCoinName;
    private PayHandler mPayHandler;

    public AliPayBuilder(Activity activity, String partner, String sellerId, String privateKey) {
        mActivity = new WeakReference<>(activity).get();
        mPartner = partner;
        mSellerId = sellerId;
        mPrivateKey = privateKey;
    }

    public AliPayBuilder(Activity activity) {
        mActivity = new WeakReference<>(activity).get();
    }

    public AliPayBuilder setCoinBean(AppUsersCharge bean) {
        mBean = bean;
        return this;
    }

    public AliPayBuilder setCoinName(String coinName) {
        mCoinName = coinName;
        return this;
    }


    public AliPayBuilder setPayCallback(PayCallback callback) {
        mPayHandler = new PayHandler(callback);
        return this;
    }


    //    /**
//     * 从服务器端获取订单号,即下单
//     */
//    public void pay() {
//        if (mBean == null) {
//            return;
//        }
//        HttpUtil.getAliOrder(mBean.getMoney(), mBean.getId(), mBean.getCoin(), new HttpCallback() {
//            @Override
//            public void onSuccess(int code, String msg, String[] info) {
//                if (code == 0 && info.length > 0) {
//                    JSONObject obj = JSON.parseObject(info[0]);
//                    String orderInfo = createAliOrder(obj.getString("orderid"));//商品信息
//                    String sign = getOrderSign(orderInfo);//订单签名
//                    sign = urlEncode(sign);
//                    String signType = "sign_type=\"RSA\"";//签名类型
//                    mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + signType;
//                    L.e("支付宝订单信息----->" + mPayInfo);
//                    invokeAliPay();
//                }
//            }
//
//            @Override
//            public boolean showLoadingDialog() {
//                return true;
//            }
//
//            @Override
//            public Dialog createLoadingDialog() {
//                return DialogUitl.loadingDialog(mActivity);
//            }
//
//
//        });
//    }

    /**
     * 选择充值规则进行充值,返回value为支付宝/微信支付等需要的参数
     *
     * @param tag    支付渠道ID
     * @param ruleId 支付参数，请使用JSON字符串传递参数, 1金币充值可传参数：{ruleId:1,terminal:'ios'}
     *               //     * @param type   业务类型：1：金币充值  2：待定
     */
    public void pay(long tag, long ruleId) {
//        String params = "{ruleId:" + ruleId + ",terminal:'android'}";

        HttpApiApiPay.startPay(tag, ruleId, 1, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    mPayInfo = retModel.value;
//                    String sign = getOrderSign(orderInfo);//订单签名
//                    sign = urlEncode(sign);
//                    String signType = "sign_type=\"RSA\"";//签名类型
//                    mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + signType;
                    invokeAliPay();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 商品支付接口
     *
     * @param tag     支付渠道ID
     * @param orderId 订单Id
     */
    public void shopPay(long tag, long orderId) {
//        String params = "{orderId:" + orderId + "}";
        HttpApiApiPay.startPay(tag, orderId, 2, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    mPayInfo = retModel.value;
//                    String sign = getOrderSign(orderInfo);//订单签名
//                    sign = urlEncode(sign);
//                    String signType = "sign_type=\"RSA\"";//签名类型
//                    mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + signType;
                    invokeAliPay();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 购买VIP
     *
     * @param channelId 渠道ID
     * @param vipId     订单ID
     */
    public void buyVip(long channelId, long vipId) {
//        String params = "{vipId:" + vipId + ",terminal:'android'}";
        HttpApiApiPay.startPay(channelId, vipId, 4, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    mPayInfo = retModel.value;
//                    String sign = getOrderSign(orderInfo);//订单签名
//                    sign = urlEncode(sign);
//                    String signType = "sign_type=\"RSA\"";//签名类型
//                    mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + signType;
                    invokeAliPay();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 灵活使用 （RMB购买 SVIP）
     *
     * @param tag 支付渠道ID
     * @param id  支付参数，请使用JSON字符串传递参数, 1金币充值可传参数：{ruleId:1,terminal:'ios'}
     *            //     * @param type   业务类型：1：金币充值  2：待定
     */
    public void startPay(long tag, long id, int type) {
//        String params = "{" + jsonKey + ":" + id + ",terminal:'android'}";
        HttpApiApiPay.startPay(tag, id, type, new HttpApiCallBack<SingleString>() {
            @Override
            public void onHttpRet(int code, String msg, SingleString retModel) {
                if (code == 1 && null != retModel) {
                    mPayInfo = retModel.value;
//                    String sign = getOrderSign(orderInfo);//订单签名
//                    sign = urlEncode(sign);
//                    String signType = "sign_type=\"RSA\"";//签名类型
//                    mPayInfo = orderInfo + "&sign=\"" + sign + "\"&" + signType;
                    invokeAliPay();
                } else {
                    ToastUtil.show(msg);
                }
            }
        });
    }

    /**
     * 根据订单号和商品信息生成支付宝格式的订单信息
     *
     * @param orderId 服务器返回的订单号
     * @return
     */
    private String createAliOrder(String orderId) {
        // 合作者身份ID
        String orderInfo = "partner=" + "\"" + mPartner + "\"";

        // 卖家支付宝账号
        orderInfo += "&seller_id=" + "\"" + mSellerId + "\"";

        // 商户网站唯一订单号
        orderInfo += "&out_trade_no=" + "\"" + orderId + "\"";

//        // 商品名称
//        if (AppConfig.aliPayShowAppName()) {
//            orderInfo += "&subject=" + "\"" + WordUtil.getString(R.string.app_name_live) + "\"";
//        } else {
//            orderInfo += "&subject=" + "\"" + mBean.getCoin() + mCoinName + "\"";
//        }

//        // 商品详情
//        orderInfo += "&body=" + "\"" + mBean.getCoin() + mCoinName + "\"";
//
//        // 商品金额
//        orderInfo += "&total_fee=" + "\"" + mBean.getMoney() + "\"";
//
//        // 服务器异步通知页面路径 //服务器异步通知页面路径  参数 notify_url，如果商户没设定，则不会进行该操作
//        orderInfo += "&notify_url=" + "\"" + HtmlConfig.ALI_PAY_NOTIFY_URL + "\"";

        // 接口名称， 固定值
        orderInfo += "&service=\"mobile.securitypay.pay\"";

        // 支付类型， 固定值
        orderInfo += "&payment_type=\"1\"";

        // 参数编码， 固定值
        orderInfo += "&_input_charset=\"utf-8\"";

        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m〜15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        orderInfo += "&it_b_pay=\"30m\"";

        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        orderInfo += "&return_url=\"m.alipay.com\"";

        // 调用银行卡支付，需配置此参数，参与签名， 固定值
        // orderInfo += "&paymethod=\"expressGateway\"";

        return orderInfo;
    }

    /**
     * 根据订单信息生成订单的签名
     *
     * @param orderInfo 订单信息
     * @return
     */
//    private String getOrderSign(String orderInfo) {
//        return SignUtils.sign(orderInfo, mPrivateKey);
//    }

    /**
     * 对订单签名进行urlencode转码
     *
     * @param sign 签名
     * @return
     */
    private String urlEncode(String sign) {
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }

    /**
     * 调用支付宝sdk
     */
    private void invokeAliPay() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask(mActivity);
                //执行支付，这是一个耗时操作，最后返回支付的结果，用handler发送到主线程
                Map<String, String> result = alipay.payV2(mPayInfo, true);
                if (mPayHandler != null) {
                    Message msg = Message.obtain();
                    msg.obj = result;
                    mPayHandler.sendMessage(msg);
                }
            }
        }).start();
    }


    private static class PayHandler extends Handler {

        private PayCallback mPayCallback;

        public PayHandler(PayCallback payCallback) {
            mPayCallback = new WeakReference<>(payCallback).get();
        }

        @Override
        public void handleMessage(Message msg) {
            if (mPayCallback != null) {
                @SuppressWarnings("unchecked")
                Map<String, String> result = (Map<String, String>) msg.obj;
                if ("9000".equals(result.get("resultStatus"))) {
                    mPayCallback.onSuccess();
                } else {
                    mPayCallback.onFailed();
                }
            }
            mPayCallback = null;
        }
    }

}
