package com.google.android.gms.common.internal;

import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.atomic.AtomicInteger;

public final class zzj implements Callback {
    private final Handler mHandler;
    private final zza zzaaC;
    private final ArrayList<ConnectionCallbacks> zzaaD;
    final ArrayList<ConnectionCallbacks> zzaaE;
    private final ArrayList<OnConnectionFailedListener> zzaaF;
    private volatile boolean zzaaG;
    private final AtomicInteger zzaaH;
    private boolean zzaaI;
    private final Object zzqt;

    public interface zza {
        boolean isConnected();

        Bundle zzlM();
    }

    public zzj(Looper looper, zza com_google_android_gms_common_internal_zzj_zza) {
        this.zzaaD = new ArrayList();
        this.zzaaE = new ArrayList();
        this.zzaaF = new ArrayList();
        this.zzaaG = false;
        this.zzaaH = new AtomicInteger(0);
        this.zzaaI = false;
        this.zzqt = new Object();
        this.zzaaC = com_google_android_gms_common_internal_zzj_zza;
        this.mHandler = new Handler(looper, this);
    }

    public boolean handleMessage(Message msg) {
        if (msg.what == 1) {
            ConnectionCallbacks connectionCallbacks = (ConnectionCallbacks) msg.obj;
            synchronized (this.zzqt) {
                if (this.zzaaG && this.zzaaC.isConnected() && this.zzaaD.contains(connectionCallbacks)) {
                    connectionCallbacks.onConnected(this.zzaaC.zzlM());
                }
            }
            return true;
        }
        Log.wtf("GmsClientEvents", "Don't know how to handle this message.");
        return false;
    }

    public boolean isConnectionCallbacksRegistered(ConnectionCallbacks listener) {
        boolean contains;
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            contains = this.zzaaD.contains(listener);
        }
        return contains;
    }

    public boolean isConnectionFailedListenerRegistered(OnConnectionFailedListener listener) {
        boolean contains;
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            contains = this.zzaaF.contains(listener);
        }
        return contains;
    }

    public void registerConnectionCallbacks(ConnectionCallbacks listener) {
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            if (this.zzaaD.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionCallbacks(): listener " + listener + " is already registered");
            } else {
                this.zzaaD.add(listener);
            }
        }
        if (this.zzaaC.isConnected()) {
            this.mHandler.sendMessage(this.mHandler.obtainMessage(1, listener));
        }
    }

    public void registerConnectionFailedListener(OnConnectionFailedListener listener) {
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            if (this.zzaaF.contains(listener)) {
                Log.w("GmsClientEvents", "registerConnectionFailedListener(): listener " + listener + " is already registered");
            } else {
                this.zzaaF.add(listener);
            }
        }
    }

    public void unregisterConnectionCallbacks(ConnectionCallbacks listener) {
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            if (!this.zzaaD.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionCallbacks(): listener " + listener + " not found");
            } else if (this.zzaaI) {
                this.zzaaE.add(listener);
            }
        }
    }

    public void unregisterConnectionFailedListener(OnConnectionFailedListener listener) {
        zzu.zzu(listener);
        synchronized (this.zzqt) {
            if (!this.zzaaF.remove(listener)) {
                Log.w("GmsClientEvents", "unregisterConnectionFailedListener(): listener " + listener + " not found");
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzbu(int r6) {
        /*
        r5 = this;
        r1 = 1;
        r0 = r5.mHandler;
        r0.removeMessages(r1);
        r1 = r5.zzqt;
        monitor-enter(r1);
        r0 = 1;
        r5.zzaaI = r0;	 Catch:{ all -> 0x004b }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x004b }
        r2 = r5.zzaaD;	 Catch:{ all -> 0x004b }
        r0.<init>(r2);	 Catch:{ all -> 0x004b }
        r2 = r5.zzaaH;	 Catch:{ all -> 0x004b }
        r2 = r2.get();	 Catch:{ all -> 0x004b }
        r3 = r0.iterator();	 Catch:{ all -> 0x004b }
    L_0x001d:
        r0 = r3.hasNext();	 Catch:{ all -> 0x004b }
        if (r0 == 0) goto L_0x0035;
    L_0x0023:
        r0 = r3.next();	 Catch:{ all -> 0x004b }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) r0;	 Catch:{ all -> 0x004b }
        r4 = r5.zzaaG;	 Catch:{ all -> 0x004b }
        if (r4 == 0) goto L_0x0035;
    L_0x002d:
        r4 = r5.zzaaH;	 Catch:{ all -> 0x004b }
        r4 = r4.get();	 Catch:{ all -> 0x004b }
        if (r4 == r2) goto L_0x003f;
    L_0x0035:
        r0 = r5.zzaaE;	 Catch:{ all -> 0x004b }
        r0.clear();	 Catch:{ all -> 0x004b }
        r0 = 0;
        r5.zzaaI = r0;	 Catch:{ all -> 0x004b }
        monitor-exit(r1);	 Catch:{ all -> 0x004b }
        return;
    L_0x003f:
        r4 = r5.zzaaD;	 Catch:{ all -> 0x004b }
        r4 = r4.contains(r0);	 Catch:{ all -> 0x004b }
        if (r4 == 0) goto L_0x001d;
    L_0x0047:
        r0.onConnectionSuspended(r6);	 Catch:{ all -> 0x004b }
        goto L_0x001d;
    L_0x004b:
        r0 = move-exception;
        monitor-exit(r1);	 Catch:{ all -> 0x004b }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzj.zzbu(int):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzg(android.os.Bundle r6) {
        /*
        r5 = this;
        r1 = 0;
        r0 = 1;
        r3 = r5.zzqt;
        monitor-enter(r3);
        r2 = r5.zzaaI;	 Catch:{ all -> 0x006c }
        if (r2 != 0) goto L_0x005c;
    L_0x0009:
        r2 = r0;
    L_0x000a:
        com.google.android.gms.common.internal.zzu.zzU(r2);	 Catch:{ all -> 0x006c }
        r2 = r5.mHandler;	 Catch:{ all -> 0x006c }
        r4 = 1;
        r2.removeMessages(r4);	 Catch:{ all -> 0x006c }
        r2 = 1;
        r5.zzaaI = r2;	 Catch:{ all -> 0x006c }
        r2 = r5.zzaaE;	 Catch:{ all -> 0x006c }
        r2 = r2.size();	 Catch:{ all -> 0x006c }
        if (r2 != 0) goto L_0x005e;
    L_0x001e:
        com.google.android.gms.common.internal.zzu.zzU(r0);	 Catch:{ all -> 0x006c }
        r0 = new java.util.ArrayList;	 Catch:{ all -> 0x006c }
        r1 = r5.zzaaD;	 Catch:{ all -> 0x006c }
        r0.<init>(r1);	 Catch:{ all -> 0x006c }
        r1 = r5.zzaaH;	 Catch:{ all -> 0x006c }
        r1 = r1.get();	 Catch:{ all -> 0x006c }
        r2 = r0.iterator();	 Catch:{ all -> 0x006c }
    L_0x0032:
        r0 = r2.hasNext();	 Catch:{ all -> 0x006c }
        if (r0 == 0) goto L_0x0052;
    L_0x0038:
        r0 = r2.next();	 Catch:{ all -> 0x006c }
        r0 = (com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks) r0;	 Catch:{ all -> 0x006c }
        r4 = r5.zzaaG;	 Catch:{ all -> 0x006c }
        if (r4 == 0) goto L_0x0052;
    L_0x0042:
        r4 = r5.zzaaC;	 Catch:{ all -> 0x006c }
        r4 = r4.isConnected();	 Catch:{ all -> 0x006c }
        if (r4 == 0) goto L_0x0052;
    L_0x004a:
        r4 = r5.zzaaH;	 Catch:{ all -> 0x006c }
        r4 = r4.get();	 Catch:{ all -> 0x006c }
        if (r4 == r1) goto L_0x0060;
    L_0x0052:
        r0 = r5.zzaaE;	 Catch:{ all -> 0x006c }
        r0.clear();	 Catch:{ all -> 0x006c }
        r0 = 0;
        r5.zzaaI = r0;	 Catch:{ all -> 0x006c }
        monitor-exit(r3);	 Catch:{ all -> 0x006c }
        return;
    L_0x005c:
        r2 = r1;
        goto L_0x000a;
    L_0x005e:
        r0 = r1;
        goto L_0x001e;
    L_0x0060:
        r4 = r5.zzaaE;	 Catch:{ all -> 0x006c }
        r4 = r4.contains(r0);	 Catch:{ all -> 0x006c }
        if (r4 != 0) goto L_0x0032;
    L_0x0068:
        r0.onConnected(r6);	 Catch:{ all -> 0x006c }
        goto L_0x0032;
    L_0x006c:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x006c }
        throw r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.common.internal.zzj.zzg(android.os.Bundle):void");
    }

    public void zzh(ConnectionResult connectionResult) {
        this.mHandler.removeMessages(1);
        synchronized (this.zzqt) {
            ArrayList arrayList = new ArrayList(this.zzaaF);
            int i = this.zzaaH.get();
            Iterator it = arrayList.iterator();
            while (it.hasNext()) {
                OnConnectionFailedListener onConnectionFailedListener = (OnConnectionFailedListener) it.next();
                if (!this.zzaaG || this.zzaaH.get() != i) {
                    return;
                } else if (this.zzaaF.contains(onConnectionFailedListener)) {
                    onConnectionFailedListener.onConnectionFailed(connectionResult);
                }
            }
        }
    }

    public void zznT() {
        this.zzaaG = false;
        this.zzaaH.incrementAndGet();
    }

    public void zznU() {
        this.zzaaG = true;
    }
}
