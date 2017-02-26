package com.example.duy.calculator.utils;

import android.content.Context;

@Deprecated
public class GraphicUtil {
    public static float dpFromPx(Context context, float px) {
        return px / context.getResources().getDisplayMetrics().density;
    }

    public static float pxFromDp(Context context, float dp) {
        return context.getResources().getDisplayMetrics().density * dp;
    }
}
