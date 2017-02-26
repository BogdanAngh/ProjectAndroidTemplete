package com.google.android.gms.tagmanager;

import android.content.Context;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.concurrent.LinkedBlockingQueue;

class zzat extends Thread implements zzas {
    private static zzat zzaLP;
    private volatile boolean mClosed;
    private final Context mContext;
    private volatile boolean zzKU;
    private final LinkedBlockingQueue<Runnable> zzaLO;
    private volatile zzau zzaLQ;

    /* renamed from: com.google.android.gms.tagmanager.zzat.1 */
    class C02911 implements Runnable {
        final /* synthetic */ zzas zzaLR;
        final /* synthetic */ long zzaLS;
        final /* synthetic */ zzat zzaLT;
        final /* synthetic */ String zzwJ;

        C02911(zzat com_google_android_gms_tagmanager_zzat, zzas com_google_android_gms_tagmanager_zzas, long j, String str) {
            this.zzaLT = com_google_android_gms_tagmanager_zzat;
            this.zzaLR = com_google_android_gms_tagmanager_zzas;
            this.zzaLS = j;
            this.zzwJ = str;
        }

        public void run() {
            if (this.zzaLT.zzaLQ == null) {
                zzcu zzzz = zzcu.zzzz();
                zzzz.zza(this.zzaLT.mContext, this.zzaLR);
                this.zzaLT.zzaLQ = zzzz.zzzC();
            }
            this.zzaLT.zzaLQ.zzg(this.zzaLS, this.zzwJ);
        }
    }

    private zzat(Context context) {
        super("GAThread");
        this.zzaLO = new LinkedBlockingQueue();
        this.zzKU = false;
        this.mClosed = false;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        start();
    }

    static zzat zzaH(Context context) {
        if (zzaLP == null) {
            zzaLP = new zzat(context);
        }
        return zzaLP;
    }

    private String zzd(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void run() {
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.zzaLO.take();
                if (!this.zzKU) {
                    runnable.run();
                }
            } catch (InterruptedException e) {
                zzbg.zzaA(e.toString());
            } catch (Throwable th) {
                zzbg.zzaz("Error on Google TagManager Thread: " + zzd(th));
                zzbg.zzaz("Google TagManager is shutting down.");
                this.zzKU = true;
            }
        }
    }

    public void zzew(String str) {
        zzh(str, System.currentTimeMillis());
    }

    public void zzf(Runnable runnable) {
        this.zzaLO.add(runnable);
    }

    void zzh(String str, long j) {
        zzf(new C02911(this, this, j, str));
    }
}
