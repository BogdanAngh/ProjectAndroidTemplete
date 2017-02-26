package com.google.android.gms.wearable.internal;

import android.os.RemoteException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.wearable.WearableStatusCodes;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

final class zzau<T> {
    private final Map<T, zzbl<T>> zzakE;

    private static class zza<T> extends zzb<Status> {
        private WeakReference<Map<T, zzbl<T>>> zzaUA;
        private WeakReference<T> zzaUB;

        zza(Map<T, zzbl<T>> map, T t, com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status) {
            super(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status);
            this.zzaUA = new WeakReference(map);
            this.zzaUB = new WeakReference(t);
        }

        public void zza(Status status) {
            Map map = (Map) this.zzaUA.get();
            Object obj = this.zzaUB.get();
            if (!(status.getStatus().isSuccess() || map == null || obj == null)) {
                synchronized (map) {
                    zzbl com_google_android_gms_wearable_internal_zzbl = (zzbl) map.remove(obj);
                    if (com_google_android_gms_wearable_internal_zzbl != null) {
                        com_google_android_gms_wearable_internal_zzbl.clear();
                    }
                }
            }
            zzP(status);
        }
    }

    private static class zzb<T> extends zzb<Status> {
        private WeakReference<Map<T, zzbl<T>>> zzaUA;
        private WeakReference<T> zzaUB;

        zzb(Map<T, zzbl<T>> map, T t, com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status) {
            super(com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status);
            this.zzaUA = new WeakReference(map);
            this.zzaUB = new WeakReference(t);
        }

        public void zza(Status status) {
            Map map = (Map) this.zzaUA.get();
            Object obj = this.zzaUB.get();
            if (!(status.getStatus().getStatusCode() != WearableStatusCodes.UNKNOWN_LISTENER || map == null || obj == null)) {
                synchronized (map) {
                    zzbl com_google_android_gms_wearable_internal_zzbl = (zzbl) map.remove(obj);
                    if (com_google_android_gms_wearable_internal_zzbl != null) {
                        com_google_android_gms_wearable_internal_zzbl.clear();
                    }
                }
            }
            zzP(status);
        }
    }

    zzau() {
        this.zzakE = new HashMap();
    }

    public boolean isEmpty() {
        boolean isEmpty;
        synchronized (this.zzakE) {
            isEmpty = this.zzakE.isEmpty();
        }
        return isEmpty;
    }

    public void zza(zzbk com_google_android_gms_wearable_internal_zzbk, com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, T t) throws RemoteException {
        synchronized (this.zzakE) {
            zzbl com_google_android_gms_wearable_internal_zzbl = (zzbl) this.zzakE.remove(t);
            if (com_google_android_gms_wearable_internal_zzbl == null) {
                com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status.zzm(new Status(WearableStatusCodes.UNKNOWN_LISTENER));
                return;
            }
            com_google_android_gms_wearable_internal_zzbl.clear();
            ((zzat) com_google_android_gms_wearable_internal_zzbk.zznM()).zza(new zzb(this.zzakE, t, com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status), new RemoveListenerRequest(com_google_android_gms_wearable_internal_zzbl));
        }
    }

    public void zza(zzbk com_google_android_gms_wearable_internal_zzbk, com.google.android.gms.common.api.zza.zzb<Status> com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status, T t, zzbl<T> com_google_android_gms_wearable_internal_zzbl_T) throws RemoteException {
        synchronized (this.zzakE) {
            if (this.zzakE.get(t) != null) {
                com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status.zzm(new Status(WearableStatusCodes.DUPLICATE_LISTENER));
                return;
            }
            this.zzakE.put(t, com_google_android_gms_wearable_internal_zzbl_T);
            try {
                ((zzat) com_google_android_gms_wearable_internal_zzbk.zznM()).zza(new zza(this.zzakE, t, com_google_android_gms_common_api_zza_zzb_com_google_android_gms_common_api_Status), new AddListenerRequest(com_google_android_gms_wearable_internal_zzbl_T));
            } catch (RemoteException e) {
                this.zzakE.remove(t);
                throw e;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzb(com.google.android.gms.wearable.internal.zzbk r9) {
        /*
        r8 = this;
        r3 = r8.zzakE;
        monitor-enter(r3);
        r4 = new com.google.android.gms.wearable.internal.zzbj$zzo;	 Catch:{ all -> 0x0096 }
        r4.<init>();	 Catch:{ all -> 0x0096 }
        r0 = r8.zzakE;	 Catch:{ all -> 0x0096 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0096 }
        r5 = r0.iterator();	 Catch:{ all -> 0x0096 }
    L_0x0012:
        r0 = r5.hasNext();	 Catch:{ all -> 0x0096 }
        if (r0 == 0) goto L_0x0099;
    L_0x0018:
        r0 = r5.next();	 Catch:{ all -> 0x0096 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0096 }
        r1 = r0.getValue();	 Catch:{ all -> 0x0096 }
        r1 = (com.google.android.gms.wearable.internal.zzbl) r1;	 Catch:{ all -> 0x0096 }
        if (r1 == 0) goto L_0x0012;
    L_0x0026:
        r1.clear();	 Catch:{ all -> 0x0096 }
        r2 = r9.isConnected();	 Catch:{ all -> 0x0096 }
        if (r2 == 0) goto L_0x0012;
    L_0x002f:
        r2 = r9.zznM();	 Catch:{ RemoteException -> 0x006d }
        r2 = (com.google.android.gms.wearable.internal.zzat) r2;	 Catch:{ RemoteException -> 0x006d }
        r6 = new com.google.android.gms.wearable.internal.RemoveListenerRequest;	 Catch:{ RemoteException -> 0x006d }
        r6.<init>(r1);	 Catch:{ RemoteException -> 0x006d }
        r2.zza(r4, r6);	 Catch:{ RemoteException -> 0x006d }
        r2 = "WearableClient";
        r6 = 2;
        r2 = android.util.Log.isLoggable(r2, r6);	 Catch:{ RemoteException -> 0x006d }
        if (r2 == 0) goto L_0x0012;
    L_0x0046:
        r2 = "WearableClient";
        r6 = new java.lang.StringBuilder;	 Catch:{ RemoteException -> 0x006d }
        r6.<init>();	 Catch:{ RemoteException -> 0x006d }
        r7 = "disconnect: removed: ";
        r6 = r6.append(r7);	 Catch:{ RemoteException -> 0x006d }
        r7 = r0.getKey();	 Catch:{ RemoteException -> 0x006d }
        r6 = r6.append(r7);	 Catch:{ RemoteException -> 0x006d }
        r7 = "/";
        r6 = r6.append(r7);	 Catch:{ RemoteException -> 0x006d }
        r6 = r6.append(r1);	 Catch:{ RemoteException -> 0x006d }
        r6 = r6.toString();	 Catch:{ RemoteException -> 0x006d }
        android.util.Log.d(r2, r6);	 Catch:{ RemoteException -> 0x006d }
        goto L_0x0012;
    L_0x006d:
        r2 = move-exception;
        r2 = "WearableClient";
        r6 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0096 }
        r6.<init>();	 Catch:{ all -> 0x0096 }
        r7 = "disconnect: Didn't remove: ";
        r6 = r6.append(r7);	 Catch:{ all -> 0x0096 }
        r0 = r0.getKey();	 Catch:{ all -> 0x0096 }
        r0 = r6.append(r0);	 Catch:{ all -> 0x0096 }
        r6 = "/";
        r0 = r0.append(r6);	 Catch:{ all -> 0x0096 }
        r0 = r0.append(r1);	 Catch:{ all -> 0x0096 }
        r0 = r0.toString();	 Catch:{ all -> 0x0096 }
        android.util.Log.w(r2, r0);	 Catch:{ all -> 0x0096 }
        goto L_0x0012;
    L_0x0096:
        r0 = move-exception;
        monitor-exit(r3);	 Catch:{ all -> 0x0096 }
        throw r0;
    L_0x0099:
        r0 = r8.zzakE;	 Catch:{ all -> 0x0096 }
        r0.clear();	 Catch:{ all -> 0x0096 }
        monitor-exit(r3);	 Catch:{ all -> 0x0096 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.internal.zzau.zzb(com.google.android.gms.wearable.internal.zzbk):void");
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public void zzdR(android.os.IBinder r10) {
        /*
        r9 = this;
        r2 = r9.zzakE;
        monitor-enter(r2);
        r3 = com.google.android.gms.wearable.internal.zzat.zza.zzdQ(r10);	 Catch:{ all -> 0x0088 }
        r4 = new com.google.android.gms.wearable.internal.zzbj$zzo;	 Catch:{ all -> 0x0088 }
        r4.<init>();	 Catch:{ all -> 0x0088 }
        r0 = r9.zzakE;	 Catch:{ all -> 0x0088 }
        r0 = r0.entrySet();	 Catch:{ all -> 0x0088 }
        r5 = r0.iterator();	 Catch:{ all -> 0x0088 }
    L_0x0016:
        r0 = r5.hasNext();	 Catch:{ all -> 0x0088 }
        if (r0 == 0) goto L_0x008b;
    L_0x001c:
        r0 = r5.next();	 Catch:{ all -> 0x0088 }
        r0 = (java.util.Map.Entry) r0;	 Catch:{ all -> 0x0088 }
        r1 = r0.getValue();	 Catch:{ all -> 0x0088 }
        r1 = (com.google.android.gms.wearable.internal.zzbl) r1;	 Catch:{ all -> 0x0088 }
        r6 = new com.google.android.gms.wearable.internal.AddListenerRequest;	 Catch:{ RemoteException -> 0x0060 }
        r6.<init>(r1);	 Catch:{ RemoteException -> 0x0060 }
        r3.zza(r4, r6);	 Catch:{ RemoteException -> 0x0060 }
        r6 = "WearableClient";
        r7 = 2;
        r6 = android.util.Log.isLoggable(r6, r7);	 Catch:{ RemoteException -> 0x0060 }
        if (r6 == 0) goto L_0x0016;
    L_0x0039:
        r6 = "WearableClient";
        r7 = new java.lang.StringBuilder;	 Catch:{ RemoteException -> 0x0060 }
        r7.<init>();	 Catch:{ RemoteException -> 0x0060 }
        r8 = "onPostInitHandler: added: ";
        r7 = r7.append(r8);	 Catch:{ RemoteException -> 0x0060 }
        r8 = r0.getKey();	 Catch:{ RemoteException -> 0x0060 }
        r7 = r7.append(r8);	 Catch:{ RemoteException -> 0x0060 }
        r8 = "/";
        r7 = r7.append(r8);	 Catch:{ RemoteException -> 0x0060 }
        r7 = r7.append(r1);	 Catch:{ RemoteException -> 0x0060 }
        r7 = r7.toString();	 Catch:{ RemoteException -> 0x0060 }
        android.util.Log.d(r6, r7);	 Catch:{ RemoteException -> 0x0060 }
        goto L_0x0016;
    L_0x0060:
        r6 = move-exception;
        r6 = "WearableClient";
        r7 = new java.lang.StringBuilder;	 Catch:{ all -> 0x0088 }
        r7.<init>();	 Catch:{ all -> 0x0088 }
        r8 = "onPostInitHandler: Didn't add: ";
        r7 = r7.append(r8);	 Catch:{ all -> 0x0088 }
        r0 = r0.getKey();	 Catch:{ all -> 0x0088 }
        r0 = r7.append(r0);	 Catch:{ all -> 0x0088 }
        r7 = "/";
        r0 = r0.append(r7);	 Catch:{ all -> 0x0088 }
        r0 = r0.append(r1);	 Catch:{ all -> 0x0088 }
        r0 = r0.toString();	 Catch:{ all -> 0x0088 }
        android.util.Log.d(r6, r0);	 Catch:{ all -> 0x0088 }
        goto L_0x0016;
    L_0x0088:
        r0 = move-exception;
        monitor-exit(r2);	 Catch:{ all -> 0x0088 }
        throw r0;
    L_0x008b:
        monitor-exit(r2);	 Catch:{ all -> 0x0088 }
        return;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.wearable.internal.zzau.zzdR(android.os.IBinder):void");
    }
}
