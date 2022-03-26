package com.kalacheng.util.utils;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.res.Resources;
import android.text.Html;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.UnderlineSpan;


/**
 * Created by cxf on 2017/10/10.
 * 获取string.xml中的字
 */

public class WordUtil {

    private static Resources sResources;

    static {
        sResources = ApplicationUtil.getInstance().getResources();
    }

    public static String getString(int res) {
        return sResources.getString(res);
    }

    /**
     * 为字符串添加颜色
     *
     * @param color #FFFFFF
     */
    public static String strAddColor(String str, String color) {
        return "<font color=\"" + color + "\">" + str + "</font>";
    }

    /**
     * 将String转化为Spanned对象， 例：vTvTest.setText(strToSpanned("!!!" +
     * strAddColor("@@@", "#ff0000") + "###" + strAddColor("$$$", "#00ff00")));
     */
    public static Spanned strToSpanned(String str) {
        return Html.fromHtml(str);
    }

    /**
     * 为文字添加下划线
     */
    public static SpannableString addUnderline(String str) {
        SpannableString spannableString = new SpannableString(str);
        UnderlineSpan underlineSpan = new UnderlineSpan();
        spannableString.setSpan(underlineSpan, 0, spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        return spannableString;
    }

    /**
     * 复制链接
     */
    public static void copyLink(String link) {
        ClipboardManager mClipboardManager = (ClipboardManager) ApplicationUtil.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (mClipboardManager != null) {
            ClipData clipData = ClipData.newPlainText("text", link);
            mClipboardManager.setPrimaryClip(clipData);
            ToastUtil.show("复制成功");
        }
    }

    /**
     * 获取剪切板内容
     */
    public static String getClipboardContent() {
        ClipboardManager cm = (ClipboardManager) ApplicationUtil.getInstance().getSystemService(Context.CLIPBOARD_SERVICE);
        if (cm != null) {
            ClipData data = cm.getPrimaryClip();
            if (data != null && data.getItemCount() > 0) {
                ClipData.Item item = data.getItemAt(0);
                if (item != null) {
                    CharSequence sequence = item.coerceToText(ApplicationUtil.getInstance());
                    if (sequence != null) {
                        return sequence.toString();
                    }
                }
            }
        }
        return null;
    }
}
