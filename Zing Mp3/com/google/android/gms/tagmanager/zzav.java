package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzav extends Thread implements zzau {
    private static zzav aFv;
    private final LinkedBlockingQueue<Runnable> aFu;
    private volatile zzaw aFw;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean zzcbf;

    /* renamed from: com.google.android.gms.tagmanager.zzav.1 */
    class C15311 implements Runnable {
        final /* synthetic */ zzau aFx;
        final /* synthetic */ long aFy;
        final /* synthetic */ zzav aFz;
        final /* synthetic */ String zzall;

        C15311(zzav com_google_android_gms_tagmanager_zzav, zzau com_google_android_gms_tagmanager_zzau, long j, String str) {
            this.aFz = com_google_android_gms_tagmanager_zzav;
            this.aFx = com_google_android_gms_tagmanager_zzau;
            this.aFy = j;
            this.zzall = str;
        }

        public void run() {
            if (this.aFz.aFw == null) {
                zzdc zzcgt = zzdc.zzcgt();
                zzcgt.zza(this.aFz.mContext, this.aFx);
                this.aFz.aFw = zzcgt.zzcgw();
            }
            this.aFz.aFw.zzg(this.aFy, this.zzall);
        }
    }

    private zzav(Context context) {
        super("GAThread");
        this.aFu = new LinkedBlockingQueue();
        this.zzcbf = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static zzav zzee(Context context) {
        if (aFv == null) {
            aFv = new zzav(context);
        }
        return aFv;
    }

    private String zzg(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void run() {
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.aFu.take();
                if (!this.zzcbf) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                zzbo.zzdh(e.toString());
            } catch (Throwable th) {
                String str = "Error on Google TagManager Thread: ";
                String valueOf = String.valueOf(zzg(th));
                zzbo.m1698e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                zzbo.m1698e("Google TagManager is shutting down.");
                this.zzcbf = true;
            }
        }
    }

    void zzl(String str, long j) {
        zzp(new C15311(this, this, j, str));
    }

    public void zzp(Runnable runnable) {
        this.aFu.add(runnable);
    }

    public void zzpk(String str) {
        zzl(str, System.currentTimeMillis());
    }
}
