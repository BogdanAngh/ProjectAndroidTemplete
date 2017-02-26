package com.google.android.gms.ads.mediation.customevent;

import com.google.ads.mediation.NetworkExtras;
import java.util.HashMap;

@Deprecated
public final class CustomEventExtras implements NetworkExtras {
    private final HashMap<String, Object> f1516X;

    public CustomEventExtras() {
        this.f1516X = new HashMap();
    }

    public Object getExtra(String str) {
        return this.f1516X.get(str);
    }

    public void setExtra(String str, Object obj) {
        this.f1516X.put(str, obj);
    }
}
