package com.facebook.ads.internal.util;

import android.content.Context;
import android.support.annotation.NonNull;
import com.facebook.ads.internal.util.ag.C0725a;
import com.facebook.ads.internal.util.ag.C0773b;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;

/* renamed from: com.facebook.ads.internal.util.v */
public class C0802v extends ag {
    @NonNull
    private final String f1484a;
    @NonNull
    private final String f1485b;

    /* renamed from: com.facebook.ads.internal.util.v.1 */
    static /* synthetic */ class C08011 {
        static final /* synthetic */ int[] f1483a;

        static {
            f1483a = new int[C0773b.values().length];
            try {
                f1483a[C0773b.MRC.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                f1483a[C0773b.PLAY.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                f1483a[C0773b.TIME.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    public C0802v(@NonNull Context context, @NonNull C0725a c0725a, @NonNull String str, @NonNull String str2) {
        super(context, new C0783h(), c0725a, BuildConfig.FLAVOR);
        this.f1484a = str;
        this.f1485b = str2;
    }

    protected String m1677a(C0773b c0773b) {
        switch (C08011.f1483a[c0773b.ordinal()]) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return this.f1485b;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return this.f1484a;
            default:
                return null;
        }
    }
}
