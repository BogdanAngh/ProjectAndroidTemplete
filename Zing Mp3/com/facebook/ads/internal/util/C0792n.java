package com.facebook.ads.internal.util;

import android.text.TextUtils;
import com.facebook.ads.internal.p000i.p014a.C0619d;

/* renamed from: com.facebook.ads.internal.util.n */
public class C0792n {
    private final C0619d f1452a;
    private boolean f1453b;

    public C0792n(C0619d c0619d) {
        this.f1453b = true;
        this.f1452a = c0619d;
    }

    private static long m1652a(String str, String str2) {
        long j = -1;
        Object substring = str.substring(str2.length());
        if (!TextUtils.isEmpty(substring)) {
            try {
                Long valueOf = Long.valueOf(Long.parseLong(substring));
                if (valueOf.longValue() >= 0) {
                    j = valueOf.longValue();
                }
            } catch (NumberFormatException e) {
            }
        }
        return j;
    }

    public void m1653a() {
        if (!this.f1453b) {
            return;
        }
        if (this.f1452a.canGoBack() || this.f1452a.canGoForward()) {
            this.f1453b = false;
        } else {
            this.f1452a.m1163b("void((function() {try {  if (!window.performance || !window.performance.timing || !document ||       !document.body || document.body.scrollHeight <= 0 ||       !document.body.children || document.body.children.length < 1) {    return;  }  var nvtiming__an_t = window.performance.timing;  if (nvtiming__an_t.responseEnd > 0) {    console.log('ANNavResponseEnd:'+nvtiming__an_t.responseEnd);  }  if (nvtiming__an_t.domContentLoadedEventStart > 0) {    console.log('ANNavDomContentLoaded:' + nvtiming__an_t.domContentLoadedEventStart);  }  if (nvtiming__an_t.loadEventEnd > 0) {    console.log('ANNavLoadEventEnd:' + nvtiming__an_t.loadEventEnd);  }} catch(err) {  console.log('an_navigation_timing_error:' + err.message);}})());");
        }
    }

    public void m1654a(String str) {
        if (!this.f1453b) {
            return;
        }
        if (str.startsWith("ANNavResponseEnd:")) {
            this.f1452a.m1160a(C0792n.m1652a(str, "ANNavResponseEnd:"));
        } else if (str.startsWith("ANNavDomContentLoaded:")) {
            this.f1452a.m1162b(C0792n.m1652a(str, "ANNavDomContentLoaded:"));
        } else if (str.startsWith("ANNavLoadEventEnd:")) {
            this.f1452a.m1164c(C0792n.m1652a(str, "ANNavLoadEventEnd:"));
        }
    }

    public void m1655a(boolean z) {
        this.f1453b = z;
    }
}
