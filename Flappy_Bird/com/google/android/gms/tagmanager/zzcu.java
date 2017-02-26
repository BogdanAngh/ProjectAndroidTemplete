package com.google.android.gms.tagmanager;

import android.content.Context;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;

class zzcu extends zzct {
    private static final Object zzaND;
    private static zzcu zzaNO;
    private boolean connected;
    private Handler handler;
    private Context zzaNE;
    private zzau zzaNF;
    private volatile zzas zzaNG;
    private int zzaNH;
    private boolean zzaNI;
    private boolean zzaNJ;
    private boolean zzaNK;
    private zzav zzaNL;
    private zzbl zzaNM;
    private boolean zzaNN;

    /* renamed from: com.google.android.gms.tagmanager.zzcu.2 */
    class C02962 implements Callback {
        final /* synthetic */ zzcu zzaNP;

        C02962(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaNP = com_google_android_gms_tagmanager_zzcu;
        }

        public boolean handleMessage(Message msg) {
            if (1 == msg.what && zzcu.zzaND.equals(msg.obj)) {
                this.zzaNP.dispatch();
                if (this.zzaNP.zzaNH > 0 && !this.zzaNP.zzaNN) {
                    this.zzaNP.handler.sendMessageDelayed(this.zzaNP.handler.obtainMessage(1, zzcu.zzaND), (long) this.zzaNP.zzaNH);
                }
            }
            return true;
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.3 */
    class C02973 implements Runnable {
        final /* synthetic */ zzcu zzaNP;

        C02973(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaNP = com_google_android_gms_tagmanager_zzcu;
        }

        public void run() {
            this.zzaNP.zzaNF.dispatch();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcu.1 */
    class C05281 implements zzav {
        final /* synthetic */ zzcu zzaNP;

        C05281(zzcu com_google_android_gms_tagmanager_zzcu) {
            this.zzaNP = com_google_android_gms_tagmanager_zzcu;
        }

        public void zzan(boolean z) {
            this.zzaNP.zzd(z, this.zzaNP.connected);
        }
    }

    static {
        zzaND = new Object();
    }

    private zzcu() {
        this.zzaNH = 1800000;
        this.zzaNI = true;
        this.zzaNJ = false;
        this.connected = true;
        this.zzaNK = true;
        this.zzaNL = new C05281(this);
        this.zzaNN = false;
    }

    private void zzzA() {
        this.zzaNM = new zzbl(this);
        this.zzaNM.zzaI(this.zzaNE);
    }

    private void zzzB() {
        this.handler = new Handler(this.zzaNE.getMainLooper(), new C02962(this));
        if (this.zzaNH > 0) {
            this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaND), (long) this.zzaNH);
        }
    }

    public static zzcu zzzz() {
        if (zzaNO == null) {
            zzaNO = new zzcu();
        }
        return zzaNO;
    }

    public synchronized void dispatch() {
        if (this.zzaNJ) {
            this.zzaNG.zzf(new C02973(this));
        } else {
            zzbg.zzaB("Dispatch call queued. Dispatch will run once initialization is complete.");
            this.zzaNI = true;
        }
    }

    synchronized void zza(Context context, zzas com_google_android_gms_tagmanager_zzas) {
        if (this.zzaNE == null) {
            this.zzaNE = context.getApplicationContext();
            if (this.zzaNG == null) {
                this.zzaNG = com_google_android_gms_tagmanager_zzas;
            }
        }
    }

    synchronized void zzao(boolean z) {
        zzd(this.zzaNN, z);
    }

    synchronized void zzd(boolean z, boolean z2) {
        if (!(this.zzaNN == z && this.connected == z2)) {
            if (z || !z2) {
                if (this.zzaNH > 0) {
                    this.handler.removeMessages(1, zzaND);
                }
            }
            if (!z && z2 && this.zzaNH > 0) {
                this.handler.sendMessageDelayed(this.handler.obtainMessage(1, zzaND), (long) this.zzaNH);
            }
            StringBuilder append = new StringBuilder().append("PowerSaveMode ");
            String str = (z || !z2) ? "initiated." : "terminated.";
            zzbg.zzaB(append.append(str).toString());
            this.zzaNN = z;
            this.connected = z2;
        }
    }

    synchronized void zzhK() {
        if (!this.zzaNN && this.connected && this.zzaNH > 0) {
            this.handler.removeMessages(1, zzaND);
            this.handler.sendMessage(this.handler.obtainMessage(1, zzaND));
        }
    }

    synchronized zzau zzzC() {
        if (this.zzaNF == null) {
            if (this.zzaNE == null) {
                throw new IllegalStateException("Cant get a store unless we have a context");
            }
            this.zzaNF = new zzby(this.zzaNL, this.zzaNE);
        }
        if (this.handler == null) {
            zzzB();
        }
        this.zzaNJ = true;
        if (this.zzaNI) {
            dispatch();
            this.zzaNI = false;
        }
        if (this.zzaNM == null && this.zzaNK) {
            zzzA();
        }
        return this.zzaNF;
    }
}
