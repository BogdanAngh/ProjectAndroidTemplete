package com.google.android.gms.analytics.internal;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

public class zzal implements zzp {
    public double zzME;
    public int zzMF;
    public int zzMG;
    public int zzMH;
    public int zzMI;
    public Map<String, String> zzMJ;
    public String zztd;

    public zzal() {
        this.zzME = -1.0d;
        this.zzMF = -1;
        this.zzMG = -1;
        this.zzMH = -1;
        this.zzMI = -1;
        this.zzMJ = new HashMap();
    }

    public int getSessionTimeout() {
        return this.zzMF;
    }

    public String getTrackingId() {
        return this.zztd;
    }

    public String zzbh(String str) {
        String str2 = (String) this.zzMJ.get(str);
        return str2 != null ? str2 : str;
    }

    public double zzkA() {
        return this.zzME;
    }

    public boolean zzkB() {
        return this.zzMF >= 0;
    }

    public boolean zzkC() {
        return this.zzMG != -1;
    }

    public boolean zzkD() {
        return this.zzMG == 1;
    }

    public boolean zzkE() {
        return this.zzMH != -1;
    }

    public boolean zzkF() {
        return this.zzMH == 1;
    }

    public boolean zzkG() {
        return this.zzMI == 1;
    }

    public boolean zzky() {
        return this.zztd != null;
    }

    public boolean zzkz() {
        return this.zzME >= 0.0d;
    }

    public String zzq(Activity activity) {
        return zzbh(activity.getClass().getCanonicalName());
    }
}
