package com.facebook.ads.internal.p010h.p012b.p013a;

import android.text.TextUtils;
import com.facebook.ads.internal.p010h.p012b.C0602m;
import com.mp3download.zingmp3.BuildConfig;

/* renamed from: com.facebook.ads.internal.h.b.a.f */
public class C0579f implements C0573c {
    private String m1032b(String str) {
        int lastIndexOf = str.lastIndexOf(46);
        return (lastIndexOf == -1 || lastIndexOf <= str.lastIndexOf(47) || (lastIndexOf + 2) + 4 <= str.length()) ? BuildConfig.FLAVOR : str.substring(lastIndexOf + 1, str.length());
    }

    public String m1033a(String str) {
        Object b = m1032b(str);
        String d = C0602m.m1116d(str);
        return TextUtils.isEmpty(b) ? d : d + "." + b;
    }
}
