package com.google.android.gms.analytics.internal;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

public class zzan implements zzp {
    public String at;
    public double fQ;
    public int fR;
    public int fS;
    public int fT;
    public int fU;
    public Map<String, String> fV;

    public zzan() {
        this.fQ = -1.0d;
        this.fR = -1;
        this.fS = -1;
        this.fT = -1;
        this.fU = -1;
        this.fV = new HashMap();
    }

    public int getSessionTimeout() {
        return this.fR;
    }

    public String getTrackingId() {
        return this.at;
    }

    public boolean zzahc() {
        return this.at != null;
    }

    public boolean zzahd() {
        return this.fQ >= 0.0d;
    }

    public double zzahe() {
        return this.fQ;
    }

    public boolean zzahf() {
        return this.fR >= 0;
    }

    public boolean zzahg() {
        return this.fS != -1;
    }

    public boolean zzahh() {
        return this.fS == 1;
    }

    public boolean zzahi() {
        return this.fT != -1;
    }

    public boolean zzahj() {
        return this.fT == 1;
    }

    public boolean zzahk() {
        return this.fU == 1;
    }

    public String zzfh(String str) {
        String str2 = (String) this.fV.get(str);
        return str2 != null ? str2 : str;
    }

    public String zzr(Activity activity) {
        return zzfh(activity.getClass().getCanonicalName());
    }
}
