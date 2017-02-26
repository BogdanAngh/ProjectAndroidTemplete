package com.getkeepsafe.taptargetview;

import android.content.Context;
import android.content.res.Resources.Theme;
import android.os.Build.VERSION;
import android.support.annotation.ColorRes;
import android.support.annotation.DimenRes;
import android.support.v4.view.ViewCompat;
import android.util.TypedValue;

class UiUtil {
    UiUtil() {
    }

    static int dp(Context context, int val) {
        return (int) TypedValue.applyDimension(1, (float) val, context.getResources().getDisplayMetrics());
    }

    static int sp(Context context, int val) {
        return (int) TypedValue.applyDimension(2, (float) val, context.getResources().getDisplayMetrics());
    }

    static int themeIntAttr(Context context, String attr) {
        Theme theme = context.getTheme();
        if (theme == null) {
            return -1;
        }
        TypedValue value = new TypedValue();
        int id = context.getResources().getIdentifier(attr, "attr", context.getPackageName());
        if (id == 0) {
            return -1;
        }
        theme.resolveAttribute(id, value, true);
        return value.data;
    }

    static int setAlpha(int argb, float alpha) {
        if (alpha > 1.0f) {
            alpha = 1.0f;
        } else if (alpha <= 0.0f) {
            alpha = 0.0f;
        }
        return (((int) (((float) (argb >>> 24)) * alpha)) << 24) | (ViewCompat.MEASURED_SIZE_MASK & argb);
    }

    static int color(Context context, @ColorRes int id) {
        if (VERSION.SDK_INT >= 23) {
            return context.getColor(id);
        }
        return context.getResources().getColor(id);
    }

    static int dimen(Context context, @DimenRes int id) {
        return (int) context.getResources().getDimension(id);
    }
}
