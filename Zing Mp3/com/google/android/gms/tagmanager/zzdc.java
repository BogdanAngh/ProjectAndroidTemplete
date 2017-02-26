package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

class zzdc extends zzdb {
    private static final Object aHk;
    private static zzdc aHw;
    private Context aHl;
    private zzaw aHm;
    private volatile zzau aHn;
    private int aHo;
    private boolean aHp;
    private boolean aHq;
    private boolean aHr;
    private zzax aHs;
    private zza aHt;
    private zzbt aHu;
    private boolean aHv;
    private boolean connected;

    /* renamed from: com.google.android.gms.tagmanager.zzdc.1 */
    class C15421 implements zzax {
        final /* synthetic */ zzdc aHx;

        C15421(zzdc com_google_android_gms_tagmanager_zzdc) {
            this.aHx = com_google_android_gms_tagmanager_zzdc;
        }

        public void zzcn(boolean z) {
            this.aHx.zze(z, this.aHx.connected);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzdc.2 */
    class C15432 implements Runnable {
        final /* synthetic */ zzdc aHx;

        C15432(zzdc com_google_android_gms_tagmanager_zzdc) {
            this.aHx = com_google_android_gms_tagmanager_zzdc;
        }

        public void run() {
            this.aHx.aHm.dispatch();
        }
    }

    public interface zza {
        void cancel();

        void zzcgy();

        void zzx(long j);
    }

    private class zzb implements zza {
        final /* synthetic */ zzdc aHx;
        private Handler handler;

        /* renamed from: com.google.android.gms.tagmanager.zzdc.zzb.1 */
        class C15441 implements Callback {
            final /* synthetic */ zzb aHy;

            C15441(zzb com_google_android_gms_tagmanager_zzdc_zzb) {
                this.aHy = com_google_android_gms_tagmanager_zzdc_zzb;
            }

            public boolean handleMessage(Message message) {
                if (1 == message.what && zzdc.aHk.equals(message.obj)) {
                    this.aHy.aHx.dispatch();
                    if (!this.aHy.aHx.isPowerSaveMode()) {
                        this.aHy.zzx((long) this.aHy.aHx.aHo);
                    }
                }
                return true;
            }
        }

        private zzb(zzdc com_google_android_gms_tagmanager_zzdc) {
            this.aHx = com_google_android_gms_tagmanager_zzdc;
            this.handler = new Handler(this.aHx.aHl.getMainLooper(), new C15441(this));
        }

        private Message obtainMessage() {
            return this.handler.obtainMessage(1, zzdc.aHk);
        }

        public void cancel() {
            this.handler.removeMessages(1, zzdc.aHk);
        }

        public void zzcgy() {
            this.handler.removeMessages(1, zzdc.aHk);
            this.handler.sendMessage(obtainMessage());
        }

        public void zzx(long j) {
            this.handler.removeMessages(1, zzdc.aHk);
            this.handler.sendMessageDelayed(obtainMessage(), j);
        }
    }

    static {
        aHk = new Object();
    }

    private zzdc() {
        this.aHo = 1800000;
        this.aHp = true;
        this.aHq = false;
        this.connected = true;
        this.aHr = true;
        this.aHs = new C15421(this);
        this.aHv = false;
    }

    private boolean isPowerSaveMode() {
        return this.aHv || !this.connected || this.aHo <= 0;
    }

    private void zzadp() {
        if (isPowerSaveMode()) {
            this.aHt.cancel();
            zzbo.m1699v("PowerSaveMode initiated.");
            return;
        }
        this.aHt.zzx((long) this.aHo);
        zzbo.m1699v("PowerSaveMode terminated.");
    }

    public static zzdc zzcgt() {
        if (aHw == null) {
            aHw = new zzdc();
        }
        return aHw;
    }

    private void zzcgu() {
        this.aHu = new zzbt(this);
        this.aHu.zzef(this.aHl);
    }

    private void zzcgv() {
        this.aHt = new zzb();
        if (this.aHo > 0) {
            this.aHt.zzx((long) this.aHo);
        }
    }

    public synchronized void dispatch() {
        if (this.aHq) {
            this.aHn.zzp(new C15432(this));
        } else {
            zzbo.m1699v("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.aHp = true;
        }
    }

    synchronized void zza(Context context, zzau com_google_android_gms_tagmanager_zzau) {
        if (this.aHl == null) {
            this.aHl = context.getApplicationContext();
            if (this.aHn == null) {
                this.aHn = com_google_android_gms_tagmanager_zzau;
            }
        }
    }

    public synchronized void zzabv() {
        if (!isPowerSaveMode()) {
            this.aHt.zzcgy();
        }
    }

    synchronized zzaw zzcgw() {
        if (this.aHm == null) {
            if (this.aHl == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.aHm = new zzcg(this.aHs, this.aHl);
        }
        if (this.aHt == null) {
            zzcgv();
        }
        this.aHq = true;
        if (this.aHp) {
            dispatch();
            this.aHp = false;
        }
        if (this.aHu == null && this.aHr) {
            zzcgu();
        }
        return this.aHm;
    }

    public synchronized void zzco(boolean z) {
        zze(this.aHv, z);
    }

    synchronized void zze(boolean z, boolean z2) {
        boolean isPowerSaveMode = isPowerSaveMode();
        this.aHv = z;
        this.connected = z2;
        if (isPowerSaveMode() != isPowerSaveMode) {
            zzadp();
        }
    }
}
