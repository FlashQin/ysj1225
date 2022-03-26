package com.kalacheng.util.utils;

import java.text.DecimalFormat;

public class DecimalFormatUtils {

    public static String toTwo(double d){
        DecimalFormat format = new DecimalFormat("#0.00");
        return format.format(d);
    }

    /**
     * 过滤一次 判断是否为整数  若是整数返回整数  若小数返回小数
     */
    public static String isIntegerDouble(double obj) {
        String result = "";
        if (isIntegerForDouble(obj)) {
            result = (int) obj + "";
        } else {
            result = obj + "";
        }
        return result;
    }

    /**
     * 判断double是否是整数
     *
     * @param obj
     * @return
     */
    public static boolean isIntegerForDouble(double obj) {
        double eps = 1e-10;  // 精度范围
        return obj - Math.floor(obj) < eps;
    }

}
