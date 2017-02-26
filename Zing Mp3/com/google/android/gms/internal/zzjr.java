package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.media.AudioManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzi;
import java.util.Locale;

@zzji
public final class zzjr {
    public final float zzavd;
    public final int zzcke;
    public final int zzckf;
    public final int zzcps;
    public final boolean zzcpt;
    public final boolean zzcpu;
    public final String zzcpv;
    public final String zzcpw;
    public final boolean zzcpx;
    public final boolean zzcpy;
    public final boolean zzcpz;
    public final boolean zzcqa;
    public final String zzcqb;
    public final String zzcqc;
    public final int zzcqd;
    public final int zzcqe;
    public final int zzcqf;
    public final int zzcqg;
    public final int zzcqh;
    public final int zzcqi;
    public final double zzcqj;
    public final boolean zzcqk;
    public final boolean zzcql;
    public final int zzcqm;
    public final String zzcqn;
    public final boolean zzcqo;

    public static final class zza {
        private float zzavd;
        private int zzcke;
        private int zzckf;
        private int zzcps;
        private boolean zzcpt;
        private boolean zzcpu;
        private String zzcpv;
        private String zzcpw;
        private boolean zzcpx;
        private boolean zzcpy;
        private boolean zzcpz;
        private boolean zzcqa;
        private String zzcqb;
        private String zzcqc;
        private int zzcqd;
        private int zzcqe;
        private int zzcqf;
        private int zzcqg;
        private int zzcqh;
        private int zzcqi;
        private double zzcqj;
        private boolean zzcqk;
        private boolean zzcql;
        private int zzcqm;
        private String zzcqn;
        private boolean zzcqo;

        public zza(Context context) {
            boolean z = true;
            PackageManager packageManager = context.getPackageManager();
            zzs(context);
            zza(context, packageManager);
            zzt(context);
            Locale locale = Locale.getDefault();
            this.zzcpt = zza(packageManager, "geo:0,0?q=donuts") != null;
            if (zza(packageManager, "http://www.google.com") == null) {
                z = false;
            }
            this.zzcpu = z;
            this.zzcpw = locale.getCountry();
            this.zzcpx = zzm.zzkr().zzwp();
            this.zzcpy = zzi.zzcj(context);
            this.zzcqb = locale.getLanguage();
            this.zzcqc = zza(packageManager);
            Resources resources = context.getResources();
            if (resources != null) {
                DisplayMetrics displayMetrics = resources.getDisplayMetrics();
                if (displayMetrics != null) {
                    this.zzavd = displayMetrics.density;
                    this.zzcke = displayMetrics.widthPixels;
                    this.zzckf = displayMetrics.heightPixels;
                }
            }
        }

        public zza(Context context, zzjr com_google_android_gms_internal_zzjr) {
            PackageManager packageManager = context.getPackageManager();
            zzs(context);
            zza(context, packageManager);
            zzt(context);
            zzu(context);
            this.zzcpt = com_google_android_gms_internal_zzjr.zzcpt;
            this.zzcpu = com_google_android_gms_internal_zzjr.zzcpu;
            this.zzcpw = com_google_android_gms_internal_zzjr.zzcpw;
            this.zzcpx = com_google_android_gms_internal_zzjr.zzcpx;
            this.zzcpy = com_google_android_gms_internal_zzjr.zzcpy;
            this.zzcqb = com_google_android_gms_internal_zzjr.zzcqb;
            this.zzcqc = com_google_android_gms_internal_zzjr.zzcqc;
            this.zzavd = com_google_android_gms_internal_zzjr.zzavd;
            this.zzcke = com_google_android_gms_internal_zzjr.zzcke;
            this.zzckf = com_google_android_gms_internal_zzjr.zzckf;
        }

        private static ResolveInfo zza(PackageManager packageManager, String str) {
            return packageManager.resolveActivity(new Intent("android.intent.action.VIEW", Uri.parse(str)), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        }

        private static String zza(PackageManager packageManager) {
            String str = null;
            ResolveInfo zza = zza(packageManager, "market://details?id=com.google.android.gms.ads");
            if (zza != null) {
                ActivityInfo activityInfo = zza.activityInfo;
                if (activityInfo != null) {
                    try {
                        PackageInfo packageInfo = packageManager.getPackageInfo(activityInfo.packageName, 0);
                        if (packageInfo != null) {
                            int i = packageInfo.versionCode;
                            String valueOf = String.valueOf(activityInfo.packageName);
                            str = new StringBuilder(String.valueOf(valueOf).length() + 12).append(i).append(".").append(valueOf).toString();
                        }
                    } catch (NameNotFoundException e) {
                    }
                }
            }
            return str;
        }

        @TargetApi(16)
        private void zza(Context context, PackageManager packageManager) {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService("phone");
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
            this.zzcpv = telephonyManager.getNetworkOperator();
            this.zzcqf = telephonyManager.getNetworkType();
            this.zzcqg = telephonyManager.getPhoneType();
            this.zzcqe = -2;
            this.zzcql = false;
            this.zzcqm = -1;
            if (zzu.zzgm().zza(packageManager, context.getPackageName(), "android.permission.ACCESS_NETWORK_STATE")) {
                NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
                if (activeNetworkInfo != null) {
                    this.zzcqe = activeNetworkInfo.getType();
                    this.zzcqm = activeNetworkInfo.getDetailedState().ordinal();
                } else {
                    this.zzcqe = -1;
                }
                if (VERSION.SDK_INT >= 16) {
                    this.zzcql = connectivityManager.isActiveNetworkMetered();
                }
            }
        }

        private void zzs(Context context) {
            AudioManager zzag = zzu.zzgm().zzag(context);
            if (zzag != null) {
                try {
                    this.zzcps = zzag.getMode();
                    this.zzcpz = zzag.isMusicActive();
                    this.zzcqa = zzag.isSpeakerphoneOn();
                    this.zzcqd = zzag.getStreamVolume(3);
                    this.zzcqh = zzag.getRingerMode();
                    this.zzcqi = zzag.getStreamVolume(2);
                    return;
                } catch (Throwable th) {
                    zzu.zzgq().zza(th, "DeviceInfo.gatherAudioInfo");
                }
            }
            this.zzcps = -2;
            this.zzcpz = false;
            this.zzcqa = false;
            this.zzcqd = 0;
            this.zzcqh = 0;
            this.zzcqi = 0;
        }

        private void zzt(Context context) {
            boolean z = false;
            Intent registerReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (registerReceiver != null) {
                int intExtra = registerReceiver.getIntExtra(AnalyticsEvents.PARAMETER_SHARE_DIALOG_CONTENT_STATUS, -1);
                this.zzcqj = (double) (((float) registerReceiver.getIntExtra("level", -1)) / ((float) registerReceiver.getIntExtra("scale", -1)));
                if (intExtra == 2 || intExtra == 5) {
                    z = true;
                }
                this.zzcqk = z;
                return;
            }
            this.zzcqj = -1.0d;
            this.zzcqk = false;
        }

        private void zzu(Context context) {
            this.zzcqn = Build.FINGERPRINT;
            this.zzcqo = zzef.zzn(context);
        }

        public zzjr zztr() {
            return new zzjr(this.zzcps, this.zzcpt, this.zzcpu, this.zzcpv, this.zzcpw, this.zzcpx, this.zzcpy, this.zzcpz, this.zzcqa, this.zzcqb, this.zzcqc, this.zzcqd, this.zzcqe, this.zzcqf, this.zzcqg, this.zzcqh, this.zzcqi, this.zzavd, this.zzcke, this.zzckf, this.zzcqj, this.zzcqk, this.zzcql, this.zzcqm, this.zzcqn, this.zzcqo);
        }
    }

    zzjr(int i, boolean z, boolean z2, String str, String str2, boolean z3, boolean z4, boolean z5, boolean z6, String str3, String str4, int i2, int i3, int i4, int i5, int i6, int i7, float f, int i8, int i9, double d, boolean z7, boolean z8, int i10, String str5, boolean z9) {
        this.zzcps = i;
        this.zzcpt = z;
        this.zzcpu = z2;
        this.zzcpv = str;
        this.zzcpw = str2;
        this.zzcpx = z3;
        this.zzcpy = z4;
        this.zzcpz = z5;
        this.zzcqa = z6;
        this.zzcqb = str3;
        this.zzcqc = str4;
        this.zzcqd = i2;
        this.zzcqe = i3;
        this.zzcqf = i4;
        this.zzcqg = i5;
        this.zzcqh = i6;
        this.zzcqi = i7;
        this.zzavd = f;
        this.zzcke = i8;
        this.zzckf = i9;
        this.zzcqj = d;
        this.zzcqk = z7;
        this.zzcql = z8;
        this.zzcqm = i10;
        this.zzcqn = str5;
        this.zzcqo = z9;
    }
}
