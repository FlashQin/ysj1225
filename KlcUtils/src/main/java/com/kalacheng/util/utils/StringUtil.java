package com.kalacheng.util.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.net.UrlQuerySanitizer;
import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * String.format("%.2f", 数字)
 */

public class StringUtil {
    private static DecimalFormat sDecimalFormat;

    static {
        sDecimalFormat = new DecimalFormat("#.#");
        sDecimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    /**
     * 把数字转化成多少万
     */
    public static String toWan(long num) {
        if (num < 10000) {
            return String.valueOf(num);
        }
        return sDecimalFormat.format(num / 10000d) + "W";
    }

    /**
     * 人民币格式化
     */
    public static String formatRmb(double rmb) {
        if (rmb <= 0) {
            return "0.00";
        } else {
            DecimalFormat df = new DecimalFormat("###,###,##0.00");
            return df.format(rmb);
        }
    }

    /**
     * 去掉double小数点最后的0
     */
    public static String formatDoubleWithRemoveEndZero(double number) {
        DecimalFormat decimalFormat = new DecimalFormat("###################.###########");
        return decimalFormat.format(number);
    }

    /**
     * 将字符大写
     */
    public static String toUpperCase(String str) {
        return str.toUpperCase(Locale.getDefault());
    }

    /**
     * 将字符小写
     */
    public static String toLowerCase(String str) {
        return str.toLowerCase(Locale.getDefault());
    }

    /**
     * 判断是否为手机号
     */
    public static boolean isPhoneNum(String value) {
        // String regExp = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
        // Pattern p = Pattern.compile(regExp);
        // Matcher m = p.matcher(value);
        // return m.find();

        if (!TextUtils.isEmpty(value) && value.trim().startsWith("1") && value.trim().length() == 11) {
            Pattern p = Pattern.compile("[0-9]*");
            Matcher m = p.matcher(value);
            if (m.matches()) {
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 判断字符类型
     *
     * @return 1 数字； 2 字母； 3 汉字
     */
    public static int judgeStringType(String txt) {
        Pattern p = Pattern.compile("[0-9]*");
        Matcher m = p.matcher(txt);
        if (m.matches()) {
            return 1;
        }
        p = Pattern.compile("[a-zA-Z]");
        m = p.matcher(txt);
        if (m.matches()) {
            return 2;
        }
        p = Pattern.compile("[\u4e00-\u9fa5]");
        m = p.matcher(txt);
        if (m.matches()) {
            return 3;
        }
        return 0;
    }

    /**
     * url提取host
     */
    public static String getHost(String url) {
        if (url == null || url.trim().equals("")) {
            return "";
        }
        String host = "";
        Pattern p = Pattern.compile("(?<=//|)((\\w)+\\.)+\\w+");
        Matcher matcher = p.matcher(url);
        if (matcher.find()) {
            host = matcher.group();
        }
        return host;
    }

    /**
     * 获取URL中某个value值
     *
     * @param url       例：http://example.com/?name=Mark
     * @param parameter 例：name
     * @return 例：Mark
     */
    public static String getUrlValueByName(String url, String parameter) {
        UrlQuerySanitizer sanitizer = new UrlQuerySanitizer(url);
        sanitizer.setAllowUnregisteredParamaters(true);
        return sanitizer.getValue(parameter);
    }

    /**
     * 获取英文字母 A到z
     */
    public static List<String> getEnglishLetter() {
        List<String> letter = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            letter.add("" + (char) i);
        }
        for (int i = 'a'; i <= 'z'; i++) {
            letter.add("" + (char) i);
        }
        return letter;
    }

    /**
     * 将String的编码格式转换为UTF-8
     * <p>编码
     * URLEncoder.encode(text, "utf-8")
     * <p>解码
     * URLDecoder.decode("%e6%8c%87%e6%a0%87%e8%af%b4%e6%98%8e", "utf-8");
     * <p>判断字符串编码类型
     * Charset.forName("GB2312").newEncoder().canEncode("   ");
     */
    public static String toUTF8(String str) {
        String[] directoryLevels = str.split("/");
        String strUtf = "";
        for (String level : directoryLevels) {
            for (int j = 0; j < level.length(); j++) {
                String c = String.valueOf(level.charAt(j));
                if (c.equals(" ")) {
                    strUtf += "%20";
                } else {
                    try {
                        strUtf += URLEncoder.encode(c, "UTF-8");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }
                }
            }
            strUtf += "/";
        }
        return strUtf.substring(0, strUtf.length() - 1);
    }

    /**
     * 判断字符串是否为null或全为空格
     *
     * @param s 待校验字符串
     * @return {@code true}: null或全空格<br> {@code false}: 不为null且不全空格
     */
    public static boolean isEmpty(String s) {
        return (s == null || s.trim().length() == 0);
    }


    /**
     * 把一个long类型的总毫秒数转成时长
     */
    public static String getDurationText(long mms) {
        int hours = (int) (mms / (1000 * 60 * 60));
        int minutes = (int) ((mms % (1000 * 60 * 60)) / (1000 * 60));
        int seconds = (int) ((mms % (1000 * 60)) / 1000);
        String s = "";
        if (hours > 0) {
            if (hours < 10) {
                s += "0" + hours + ":";
            } else {
                s += hours + ":";
            }
        }
        if (minutes > 0) {
            if (minutes < 10) {
                s += "0" + minutes + ":";
            } else {
                s += minutes + ":";
            }
        } else {
            s += "00" + ":";
        }
        if (seconds > 0) {
            if (seconds < 10) {
                s += "0" + seconds;
            } else {
                s += seconds;
            }
        } else {
            s += "00";
        }
        return s;
    }

    /**
     * 把一个long类型的总毫秒数转成时长
     */
    public static String getDurationText2(long mms) {
        int hours = (int) (mms / (60 * 60));
        int minutes = (int) ((mms % (60 * 60)) / (60));
        int seconds = (int) ((mms % (60)));
        String s = "";
        if (hours > 0) {
            if (hours < 10) {
                s += "0" + hours + ":";
            } else {
                s += hours + ":";
            }
        }
        if (minutes > 0) {
            if (minutes < 10) {
                s += "0" + minutes + ":";
            } else {
                s += minutes + ":";
            }
        } else {
            s += "00" + ":";
        }
        if (seconds > 0) {
            if (seconds < 10) {
                s += "0" + seconds;
            } else {
                s += seconds;
            }
        } else {
            s += "00";
        }
        return s;
    }

    /**
     * 判断一个字符串是否是数字
     */
    private static Pattern sIntPattern;

    public static boolean isInt(String str) {
        sIntPattern = Pattern.compile("^[-\\+]?[\\d]*$");
        return sIntPattern.matcher(str).matches();
    }

    /**
     * 提取字符串中的 中文字符
     */
    public static String getChinese(String value) {
        String str = "";
        if (!TextUtils.isEmpty(value)) {
            String reg = "[^\u4e00-\u9fa5]";
            str = value.replaceAll(reg, "");
        }
        return str + "";
    }

    /**
     * 点击 复制到系统粘贴板
     */
    public static void CopyText(Context context, String content) {
        if (null != context && !content.isEmpty()) {
            ClipboardManager copy = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
            copy.setText(content);
            ToastUtil.show("已经复制:" + content);
        }

    }

}
