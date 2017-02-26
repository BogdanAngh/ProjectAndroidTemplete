package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.google.android.gms.internal.zzafw;
import com.google.android.gms.internal.zzafz;
import com.google.android.gms.internal.zzaga;
import com.google.android.gms.internal.zzagb;
import com.google.android.gms.internal.zzai.zzj;
import com.google.android.gms.tagmanager.zzbn.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.OutputStream;

class zzct implements Runnable {
    private final String aDY;
    private volatile String aEw;
    private final String aGA;
    private zzbn<zzj> aGB;
    private volatile zzt aGC;
    private volatile String aGD;
    private final zzaga aGz;
    private final Context mContext;

    zzct(Context context, String str, zzaga com_google_android_gms_internal_zzaga, zzt com_google_android_gms_tagmanager_zzt) {
        this.mContext = context;
        this.aGz = com_google_android_gms_internal_zzaga;
        this.aDY = str;
        this.aGC = com_google_android_gms_tagmanager_zzt;
        String valueOf = String.valueOf("/r?id=");
        String valueOf2 = String.valueOf(str);
        this.aGA = valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf);
        this.aEw = this.aGA;
        this.aGD = null;
    }

    public zzct(Context context, String str, zzt com_google_android_gms_tagmanager_zzt) {
        this(context, str, new zzaga(), com_google_android_gms_tagmanager_zzt);
    }

    private boolean zzcgc() {
        NetworkInfo activeNetworkInfo = ((ConnectivityManager) this.mContext.getSystemService("connectivity")).getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            return true;
        }
        zzbo.m1699v("...no network connectivity");
        return false;
    }

    private void zzcgd() {
        String str;
        String valueOf;
        if (zzcgc()) {
            String str2;
            zzbo.m1699v("Start loading resource from network ...");
            String zzcge = zzcge();
            zzafz zzclf = this.aGz.zzclf();
            InputStream inputStream = null;
            try {
                inputStream = zzclf.zzqz(zzcge);
            } catch (FileNotFoundException e) {
                str = this.aDY;
                zzbo.zzdi(new StringBuilder((String.valueOf(zzcge).length() + 79) + String.valueOf(str).length()).append("No data is retrieved from the given url: ").append(zzcge).append(". Make sure container_id: ").append(str).append(" is correct.").toString());
                this.aGB.zza(zza.SERVER_ERROR);
                zzclf.close();
                return;
            } catch (zzagb e2) {
                str2 = "Error when loading resource for url: ";
                valueOf = String.valueOf(zzcge);
                zzbo.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                this.aGB.zza(zza.SERVER_UNAVAILABLE_ERROR);
            } catch (Throwable e3) {
                valueOf = String.valueOf(e3.getMessage());
                zzbo.zzc(new StringBuilder((String.valueOf(zzcge).length() + 40) + String.valueOf(valueOf).length()).append("Error when loading resources from url: ").append(zzcge).append(" ").append(valueOf).toString(), e3);
                this.aGB.zza(zza.IO_ERROR);
                zzclf.close();
                return;
            } catch (Throwable th) {
                zzclf.close();
            }
            try {
                OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                zzafw.zzc(inputStream, byteArrayOutputStream);
                zzj zzg = zzj.zzg(byteArrayOutputStream.toByteArray());
                str = String.valueOf(zzg);
                zzbo.m1699v(new StringBuilder(String.valueOf(str).length() + 43).append("Successfully loaded supplemented resource: ").append(str).toString());
                if (zzg.zzxv == null && zzg.zzxu.length == 0) {
                    str2 = "No change for container: ";
                    str = String.valueOf(this.aDY);
                    zzbo.m1699v(str.length() != 0 ? str2.concat(str) : new String(str2));
                }
                this.aGB.onSuccess(zzg);
                zzclf.close();
                zzbo.m1699v("Load resource from network finished.");
                return;
            } catch (Throwable e32) {
                valueOf = String.valueOf(e32.getMessage());
                zzbo.zzc(new StringBuilder((String.valueOf(zzcge).length() + 51) + String.valueOf(valueOf).length()).append("Error when parsing downloaded resources from url: ").append(zzcge).append(" ").append(valueOf).toString(), e32);
                this.aGB.zza(zza.SERVER_ERROR);
                zzclf.close();
                return;
            }
        }
        this.aGB.zza(zza.NOT_AVAILABLE);
    }

    public void run() {
        if (this.aGB == null) {
            throw new IllegalStateException("callback must be set before execute");
        }
        this.aGB.zzcei();
        zzcgd();
    }

    void zza(zzbn<zzj> com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzai_zzj) {
        this.aGB = com_google_android_gms_tagmanager_zzbn_com_google_android_gms_internal_zzai_zzj;
    }

    String zzcge() {
        String valueOf = String.valueOf(this.aGC.zzcep());
        String str = this.aEw;
        String valueOf2 = String.valueOf("&v=a65833898");
        valueOf = new StringBuilder(((String.valueOf(valueOf).length() + 0) + String.valueOf(str).length()) + String.valueOf(valueOf2).length()).append(valueOf).append(str).append(valueOf2).toString();
        if (!(this.aGD == null || this.aGD.trim().equals(BuildConfig.FLAVOR))) {
            valueOf = String.valueOf(valueOf);
            str = String.valueOf("&pv=");
            valueOf2 = this.aGD;
            valueOf = new StringBuilder(((String.valueOf(valueOf).length() + 0) + String.valueOf(str).length()) + String.valueOf(valueOf2).length()).append(valueOf).append(str).append(valueOf2).toString();
        }
        if (!zzcj.zzcfz().zzcga().equals(zza.CONTAINER_DEBUG)) {
            return valueOf;
        }
        str = String.valueOf(valueOf);
        valueOf = String.valueOf("&gtm_debug=x");
        return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }

    void zzpa(String str) {
        if (str == null) {
            this.aEw = this.aGA;
            return;
        }
        String str2 = "Setting CTFE URL path: ";
        String valueOf = String.valueOf(str);
        zzbo.zzdg(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.aEw = str;
    }

    void zzpp(String str) {
        String str2 = "Setting previous container version: ";
        String valueOf = String.valueOf(str);
        zzbo.zzdg(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        this.aGD = str;
    }
}
