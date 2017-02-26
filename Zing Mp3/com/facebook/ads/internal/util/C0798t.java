package com.facebook.ads.internal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Base64;

/* renamed from: com.facebook.ads.internal.util.t */
public class C0798t {
    public static Bitmap m1671a(Context context, C0796r c0796r) {
        byte[] decode = Base64.decode(c0796r.m1665a(context.getResources().getDisplayMetrics().density), 0);
        return BitmapFactory.decodeByteArray(decode, 0, decode.length);
    }

    public static Drawable m1672b(Context context, C0796r c0796r) {
        return new BitmapDrawable(context.getResources(), C0798t.m1671a(context, c0796r));
    }
}
