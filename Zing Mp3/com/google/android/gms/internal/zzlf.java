package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzo;
import com.google.android.gms.internal.zzli.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.io.InputStream;

@zzji
public class zzlf {
    private final Object zzako;
    private String zzcwd;
    private String zzcwe;
    private boolean zzcwf;

    /* renamed from: com.google.android.gms.internal.zzlf.1 */
    class C14521 implements zza<String> {
        final /* synthetic */ String zzall;
        final /* synthetic */ zzlf zzcwg;

        C14521(zzlf com_google_android_gms_internal_zzlf, String str) {
            this.zzcwg = com_google_android_gms_internal_zzlf;
            this.zzall = str;
        }

        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzi(inputStream);
        }

        public String zzi(InputStream inputStream) {
            String str;
            try {
                str = new String(zzo.zza(inputStream, true), C0989C.UTF8_NAME);
                String str2 = this.zzall;
                zzb.zzdg(new StringBuilder((String.valueOf(str2).length() + 49) + String.valueOf(str).length()).append("Response received from server. \nURL: ").append(str2).append("\n Response: ").append(str).toString());
                return str;
            } catch (Throwable e) {
                Throwable th = e;
                String str3 = "Error connecting to url: ";
                str = String.valueOf(this.zzall);
                zzb.zzc(str.length() != 0 ? str3.concat(str) : new String(str3), th);
                return null;
            }
        }

        public /* synthetic */ Object zzsw() {
            return zzwh();
        }

        public String zzwh() {
            String str = "Error getting a response from: ";
            String valueOf = String.valueOf(this.zzall);
            zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return null;
        }
    }

    public zzlf() {
        this.zzako = new Object();
        this.zzcwd = BuildConfig.FLAVOR;
        this.zzcwe = BuildConfig.FLAVOR;
        this.zzcwf = false;
    }

    private Uri zze(Context context, String str, String str2) {
        Builder buildUpon = Uri.parse(str).buildUpon();
        buildUpon.appendQueryParameter("linkedDeviceId", zzam(context));
        buildUpon.appendQueryParameter("adSlotPath", str2);
        return buildUpon.build();
    }

    private void zzo(Context context, String str) {
        zzu.zzgm().zza(context, zze(context, (String) zzdr.zzbky.get(), str));
    }

    public void zza(Context context, String str, String str2, String str3) {
        Builder buildUpon = zze(context, (String) zzdr.zzblb.get(), str3).buildUpon();
        buildUpon.appendQueryParameter("debugData", str2);
        zzu.zzgm().zzc(context, str, buildUpon.build().toString());
    }

    public void zzaj(boolean z) {
        synchronized (this.zzako) {
            this.zzcwf = z;
        }
    }

    public String zzam(Context context) {
        String str;
        synchronized (this.zzako) {
            if (TextUtils.isEmpty(this.zzcwd)) {
                this.zzcwd = zzu.zzgm().zzi(context, "debug_signals_id.txt");
                if (TextUtils.isEmpty(this.zzcwd)) {
                    this.zzcwd = zzu.zzgm().zzvr();
                    zzu.zzgm().zzd(context, "debug_signals_id.txt", this.zzcwd);
                }
            }
            str = this.zzcwd;
        }
        return str;
    }

    public void zzde(String str) {
        synchronized (this.zzako) {
            this.zzcwe = str;
        }
    }

    public void zzj(Context context, String str) {
        if (zzl(context, str)) {
            zzb.zzdg("Device is linked for in app preview.");
        } else {
            zzo(context, str);
        }
    }

    public void zzk(Context context, String str) {
        if (zzm(context, str)) {
            zzb.zzdg("Device is linked for debug signals.");
        } else {
            zzo(context, str);
        }
    }

    boolean zzl(Context context, String str) {
        Object zzn = zzn(context, zze(context, (String) zzdr.zzbkz.get(), str).toString());
        if (TextUtils.isEmpty(zzn)) {
            zzb.zzdg("Not linked for in app preview.");
            return false;
        }
        zzde(zzn.trim());
        return true;
    }

    boolean zzm(Context context, String str) {
        Object zzn = zzn(context, zze(context, (String) zzdr.zzbla.get(), str).toString());
        if (TextUtils.isEmpty(zzn)) {
            zzb.zzdg("Not linked for debug signals.");
            return false;
        }
        boolean parseBoolean = Boolean.parseBoolean(zzn.trim());
        zzaj(parseBoolean);
        return parseBoolean;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    protected java.lang.String zzn(android.content.Context r7, java.lang.String r8) {
        /*
        r6 = this;
        r5 = 1;
        r0 = new com.google.android.gms.internal.zzli;
        r0.<init>(r7);
        r1 = new com.google.android.gms.internal.zzlf$1;
        r1.<init>(r6, r8);
        r2 = r0.zza(r8, r1);
        r0 = com.google.android.gms.internal.zzdr.zzblc;	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = r0.get();	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = (java.lang.Integer) r0;	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = r0.intValue();	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = (long) r0;	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r3 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = r2.get(r0, r3);	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
        r0 = (java.lang.String) r0;	 Catch:{ TimeoutException -> 0x0025, InterruptedException -> 0x0045, Exception -> 0x0064 }
    L_0x0024:
        return r0;
    L_0x0025:
        r0 = move-exception;
        r1 = r0;
        r3 = "Timeout while retriving a response from: ";
        r0 = java.lang.String.valueOf(r8);
        r4 = r0.length();
        if (r4 == 0) goto L_0x003f;
    L_0x0033:
        r0 = r3.concat(r0);
    L_0x0037:
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r0, r1);
        r2.cancel(r5);
    L_0x003d:
        r0 = 0;
        goto L_0x0024;
    L_0x003f:
        r0 = new java.lang.String;
        r0.<init>(r3);
        goto L_0x0037;
    L_0x0045:
        r0 = move-exception;
        r1 = r0;
        r3 = "Interrupted while retriving a response from: ";
        r0 = java.lang.String.valueOf(r8);
        r4 = r0.length();
        if (r4 == 0) goto L_0x005e;
    L_0x0053:
        r0 = r3.concat(r0);
    L_0x0057:
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r0, r1);
        r2.cancel(r5);
        goto L_0x003d;
    L_0x005e:
        r0 = new java.lang.String;
        r0.<init>(r3);
        goto L_0x0057;
    L_0x0064:
        r0 = move-exception;
        r1 = r0;
        r2 = "Error retriving a response from: ";
        r0 = java.lang.String.valueOf(r8);
        r3 = r0.length();
        if (r3 == 0) goto L_0x007a;
    L_0x0072:
        r0 = r2.concat(r0);
    L_0x0076:
        com.google.android.gms.ads.internal.util.client.zzb.zzb(r0, r1);
        goto L_0x003d;
    L_0x007a:
        r0 = new java.lang.String;
        r0.<init>(r2);
        goto L_0x0076;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlf.zzn(android.content.Context, java.lang.String):java.lang.String");
    }

    public String zzwf() {
        String str;
        synchronized (this.zzako) {
            str = this.zzcwe;
        }
        return str;
    }

    public boolean zzwg() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcwf;
        }
        return z;
    }
}
