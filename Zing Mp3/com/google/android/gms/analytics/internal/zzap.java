package com.google.android.gms.analytics.internal;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import com.facebook.internal.NativeProtocol;

public class zzap extends zzd {
    protected boolean aK;
    protected String bN;
    protected String bO;
    protected int ee;
    protected boolean fX;
    protected boolean fY;
    protected int fg;

    public zzap(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    private static int zzfo(String str) {
        String toLowerCase = str.toLowerCase();
        return "verbose".equals(toLowerCase) ? 0 : "info".equals(toLowerCase) ? 1 : "warning".equals(toLowerCase) ? 2 : NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE.equals(toLowerCase) ? 3 : -1;
    }

    public int getLogLevel() {
        zzacj();
        return this.ee;
    }

    void zza(zzaa com_google_android_gms_analytics_internal_zzaa) {
        int zzfo;
        zzes("Loading global XML config values");
        if (com_google_android_gms_analytics_internal_zzaa.zzafq()) {
            String zzaae = com_google_android_gms_analytics_internal_zzaa.zzaae();
            this.bN = zzaae;
            zzb("XML config - app name", zzaae);
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzafr()) {
            zzaae = com_google_android_gms_analytics_internal_zzaa.zzaaf();
            this.bO = zzaae;
            zzb("XML config - app version", zzaae);
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzafs()) {
            zzfo = zzfo(com_google_android_gms_analytics_internal_zzaa.zzaft());
            if (zzfo >= 0) {
                this.ee = zzfo;
                zza("XML config - log level", Integer.valueOf(zzfo));
            }
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzafu()) {
            zzfo = com_google_android_gms_analytics_internal_zzaa.zzafv();
            this.fg = zzfo;
            this.fX = true;
            zzb("XML config - dispatch period (sec)", Integer.valueOf(zzfo));
        }
        if (com_google_android_gms_analytics_internal_zzaa.zzafw()) {
            boolean zzafx = com_google_android_gms_analytics_internal_zzaa.zzafx();
            this.aK = zzafx;
            this.fY = true;
            zzb("XML config - dry run", Boolean.valueOf(zzafx));
        }
    }

    public String zzaae() {
        zzacj();
        return this.bN;
    }

    public String zzaaf() {
        zzacj();
        return this.bO;
    }

    public boolean zzafs() {
        zzacj();
        return false;
    }

    public boolean zzafu() {
        zzacj();
        return this.fX;
    }

    public boolean zzafw() {
        zzacj();
        return this.fY;
    }

    public boolean zzafx() {
        zzacj();
        return this.aK;
    }

    public int zzahl() {
        zzacj();
        return this.fg;
    }

    protected void zzahm() {
        ApplicationInfo applicationInfo;
        Context context = getContext();
        try {
            applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 129);
        } catch (NameNotFoundException e) {
            zzd("PackageManager doesn't know about the app package", e);
            applicationInfo = null;
        }
        if (applicationInfo == null) {
            zzev("Couldn't get ApplicationInfo to load global config");
            return;
        }
        Bundle bundle = applicationInfo.metaData;
        if (bundle != null) {
            int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
            if (i > 0) {
                zzaa com_google_android_gms_analytics_internal_zzaa = (zzaa) new zzz(zzabx()).zzcg(i);
                if (com_google_android_gms_analytics_internal_zzaa != null) {
                    zza(com_google_android_gms_analytics_internal_zzaa);
                }
            }
        }
    }

    protected void zzzy() {
        zzahm();
    }
}
