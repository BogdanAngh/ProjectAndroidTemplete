package com.google.android.gms.analytics.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.mp3download.zingmp3.BuildConfig;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Locale;

public class zza extends zzd {
    public static boolean cC;
    private Info cD;
    private final zzal cE;
    private String cF;
    private boolean cG;
    private Object cH;

    zza(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
        this.cG = false;
        this.cH = new Object();
        this.cE = new zzal(com_google_android_gms_analytics_internal_zzf.zzabz());
    }

    private boolean zza(Info info, Info info2) {
        Object obj = null;
        CharSequence id = info2 == null ? null : info2.getId();
        if (TextUtils.isEmpty(id)) {
            return true;
        }
        String zzady = zzacf().zzady();
        synchronized (this.cH) {
            String valueOf;
            String valueOf2;
            if (!this.cG) {
                this.cF = zzabq();
                this.cG = true;
            } else if (TextUtils.isEmpty(this.cF)) {
                if (info != null) {
                    obj = info.getId();
                }
                if (obj == null) {
                    valueOf = String.valueOf(id);
                    String valueOf3 = String.valueOf(zzady);
                    boolean zzer = zzer(valueOf3.length() != 0 ? valueOf.concat(valueOf3) : new String(valueOf));
                    return zzer;
                }
                valueOf2 = String.valueOf(obj);
                valueOf = String.valueOf(zzady);
                this.cF = zzeq(valueOf.length() != 0 ? valueOf2.concat(valueOf) : new String(valueOf2));
            }
            valueOf2 = String.valueOf(id);
            valueOf = String.valueOf(zzady);
            obj = zzeq(valueOf.length() != 0 ? valueOf2.concat(valueOf) : new String(valueOf2));
            if (TextUtils.isEmpty(obj)) {
                return false;
            } else if (obj.equals(this.cF)) {
                return true;
            } else {
                if (TextUtils.isEmpty(this.cF)) {
                    valueOf = zzady;
                } else {
                    zzes("Resetting the client id because Advertising Id changed.");
                    obj = zzacf().zzadz();
                    zza("New client Id", obj);
                }
                String valueOf4 = String.valueOf(id);
                valueOf3 = String.valueOf(obj);
                zzer = zzer(valueOf3.length() != 0 ? valueOf4.concat(valueOf3) : new String(valueOf4));
                return zzer;
            }
        }
    }

    private synchronized Info zzabo() {
        if (this.cE.zzz(1000)) {
            this.cE.start();
            Info zzabp = zzabp();
            if (zza(this.cD, zzabp)) {
                this.cD = zzabp;
            } else {
                zzew("Failed to reset client id on adid change. Not using adid");
                this.cD = new Info(BuildConfig.FLAVOR, false);
            }
        }
        return this.cD;
    }

    private static String zzeq(String str) {
        if (zzao.zzfl("MD5") == null) {
            return null;
        }
        return String.format(Locale.US, "%032X", new Object[]{new BigInteger(1, zzao.zzfl("MD5").digest(str.getBytes()))});
    }

    private boolean zzer(String str) {
        try {
            String zzeq = zzeq(str);
            zzes("Storing hashed adid.");
            FileOutputStream openFileOutput = getContext().openFileOutput("gaClientIdData", 0);
            openFileOutput.write(zzeq.getBytes());
            openFileOutput.close();
            this.cF = zzeq;
            return true;
        } catch (IOException e) {
            zze("Error creating hash file", e);
            return false;
        }
    }

    public boolean zzabc() {
        zzacj();
        Info zzabo = zzabo();
        return (zzabo == null || zzabo.isLimitAdTrackingEnabled()) ? false : true;
    }

    public String zzabn() {
        zzacj();
        Info zzabo = zzabo();
        CharSequence id = zzabo != null ? zzabo.getId() : null;
        return TextUtils.isEmpty(id) ? null : id;
    }

    protected Info zzabp() {
        Info info = null;
        try {
            info = AdvertisingIdClient.getAdvertisingIdInfo(getContext());
        } catch (IllegalStateException e) {
            zzev("IllegalStateException getting Ad Id Info. If you would like to see Audience reports, please ensure that you have added '<meta-data android:name=\"com.google.android.gms.version\" android:value=\"@integer/google_play_services_version\" />' to your application manifest file. See http://goo.gl/naFqQk for details.");
        } catch (Throwable th) {
            if (!cC) {
                cC = true;
                zzd("Error getting advertiser id", th);
            }
        }
        return info;
    }

    protected String zzabq() {
        Object obj;
        String str = null;
        try {
            FileInputStream openFileInput = getContext().openFileInput("gaClientIdData");
            byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS];
            int read = openFileInput.read(bArr, 0, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS);
            if (openFileInput.available() > 0) {
                zzev("Hash file seems corrupted, deleting it.");
                openFileInput.close();
                getContext().deleteFile("gaClientIdData");
                return null;
            } else if (read <= 0) {
                zzes("Hash file is empty.");
                openFileInput.close();
                return null;
            } else {
                String str2 = new String(bArr, 0, read);
                try {
                    openFileInput.close();
                    return str2;
                } catch (FileNotFoundException e) {
                    return str2;
                } catch (IOException e2) {
                    IOException iOException = e2;
                    str = str2;
                    IOException iOException2 = iOException;
                    zzd("Error reading Hash file, deleting it", obj);
                    getContext().deleteFile("gaClientIdData");
                    return str;
                }
            }
        } catch (FileNotFoundException e3) {
            return null;
        } catch (IOException e4) {
            obj = e4;
            zzd("Error reading Hash file, deleting it", obj);
            getContext().deleteFile("gaClientIdData");
            return str;
        }
    }

    protected void zzzy() {
    }
}
