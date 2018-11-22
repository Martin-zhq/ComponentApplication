package cn.xxt.customsdk.adutil;

import android.content.Context;

/**
 * 工具类
 * @author zhq
 * @date 2018/11/22 上午9:48
 */
public class Utils {

    public static int dip2px(Context context, float dip) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dip * scale);
    }
















}
