package com.google.android.gms.internal;

import com.google.android.exoplayer.C0989C;
import com.google.android.gms.internal.zzax.zza;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class zzbx {
    protected static final String TAG;
    private final String className;
    private final zzbc zzagd;
    private final String zzaji;
    private final int zzajj;
    private volatile Method zzajk;
    private List<Class> zzajl;
    private CountDownLatch zzajm;

    /* renamed from: com.google.android.gms.internal.zzbx.1 */
    class C12571 implements Runnable {
        final /* synthetic */ zzbx zzajn;

        C12571(zzbx com_google_android_gms_internal_zzbx) {
            this.zzajn = com_google_android_gms_internal_zzbx;
        }

        public void run() {
            this.zzajn.zzdp();
        }
    }

    static {
        TAG = zzbx.class.getSimpleName();
    }

    public zzbx(zzbc com_google_android_gms_internal_zzbc, String str, String str2, List<Class> list) {
        this.zzajj = 2;
        this.zzajk = null;
        this.zzajm = new CountDownLatch(1);
        this.zzagd = com_google_android_gms_internal_zzbc;
        this.className = str;
        this.zzaji = str2;
        this.zzajl = new ArrayList(list);
        this.zzagd.zzcm().submit(new C12571(this));
    }

    private String zzd(byte[] bArr, String str) throws zza, UnsupportedEncodingException {
        return new String(this.zzagd.zzco().zzc(bArr, str), C0989C.UTF8_NAME);
    }

    private void zzdp() {
        try {
            Class loadClass = this.zzagd.zzcn().loadClass(zzd(this.zzagd.zzcp(), this.className));
            if (loadClass != null) {
                this.zzajk = loadClass.getMethod(zzd(this.zzagd.zzcp(), this.zzaji), (Class[]) this.zzajl.toArray(new Class[this.zzajl.size()]));
                if (this.zzajk == null) {
                    this.zzajm.countDown();
                } else {
                    this.zzajm.countDown();
                }
            }
        } catch (zza e) {
        } catch (UnsupportedEncodingException e2) {
        } catch (ClassNotFoundException e3) {
        } catch (NoSuchMethodException e4) {
        } catch (NullPointerException e5) {
        } finally {
            this.zzajm.countDown();
        }
    }

    public Method zzdq() {
        if (this.zzajk != null) {
            return this.zzajk;
        }
        try {
            return this.zzajm.await(2, TimeUnit.SECONDS) ? this.zzajk : null;
        } catch (InterruptedException e) {
            return null;
        }
    }
}
