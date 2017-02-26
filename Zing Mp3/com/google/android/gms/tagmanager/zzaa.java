package com.google.android.gms.tagmanager;

import android.content.Context;

public class zzaa implements zzat {
    private static final Object aDL;
    private static zzaa aFa;
    private zzcl aEo;
    private zzau aFb;

    static {
        aDL = new Object();
    }

    private zzaa(Context context) {
        this(zzav.zzee(context), new zzda());
    }

    zzaa(zzau com_google_android_gms_tagmanager_zzau, zzcl com_google_android_gms_tagmanager_zzcl) {
        this.aFb = com_google_android_gms_tagmanager_zzau;
        this.aEo = com_google_android_gms_tagmanager_zzcl;
    }

    public static zzat zzdx(Context context) {
        zzat com_google_android_gms_tagmanager_zzat;
        synchronized (aDL) {
            if (aFa == null) {
                aFa = new zzaa(context);
            }
            com_google_android_gms_tagmanager_zzat = aFa;
        }
        return com_google_android_gms_tagmanager_zzat;
    }

    public boolean zzpg(String str) {
        if (this.aEo.zzagf()) {
            this.aFb.zzpk(str);
            return true;
        }
        zzbo.zzdi("Too many urls sent too quickly with the TagManagerSender, rate limiting invoked.");
        return false;
    }
}
