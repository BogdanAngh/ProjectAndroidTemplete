package com.facebook.ads.internal.util;

/* renamed from: com.facebook.ads.internal.util.b */
public enum C0774b {
    BILLABLE_CLICK(0),
    CLICK_RESUME(8);
    
    int f1399c;

    private C0774b(int i) {
        this.f1399c = i;
    }

    public String m1593a(String str) {
        return str + "&action=" + this.f1399c;
    }
}
