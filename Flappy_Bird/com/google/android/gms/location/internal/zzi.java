package com.google.android.gms.location.internal;

import android.app.PendingIntent;
import android.content.ContentProviderClient;
import android.content.Context;
import android.location.Location;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.LocationAvailability;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.example.games.basegameutils.GameHelper;
import java.util.HashMap;
import java.util.Map;

public class zzi {
    private final Context mContext;
    private Map<LocationListener, zzc> zzakE;
    private ContentProviderClient zzayL;
    private boolean zzayM;
    private Map<LocationCallback, zza> zzayN;
    private final zzn<zzg> zzayq;

    private static class zzb extends Handler {
        private final LocationListener zzayQ;

        public zzb(LocationListener locationListener) {
            this.zzayQ = locationListener;
        }

        public zzb(LocationListener locationListener, Looper looper) {
            super(looper);
            this.zzayQ = locationListener;
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    this.zzayQ.onLocationChanged(new Location((Location) msg.obj));
                default:
                    Log.e("LocationClientHelper", "unknown message in LocationHandler.handleMessage");
            }
        }
    }

    private static class zza extends com.google.android.gms.location.zzc.zza {
        private Handler zzayO;

        /* renamed from: com.google.android.gms.location.internal.zzi.zza.1 */
        class C02761 extends Handler {
            final /* synthetic */ LocationCallback zzayE;
            final /* synthetic */ zza zzayP;

            C02761(zza com_google_android_gms_location_internal_zzi_zza, Looper looper, LocationCallback locationCallback) {
                this.zzayP = com_google_android_gms_location_internal_zzi_zza;
                this.zzayE = locationCallback;
                super(looper);
            }

            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case GameHelper.CLIENT_NONE /*0*/:
                        this.zzayE.onLocationResult((LocationResult) msg.obj);
                    case CompletionEvent.STATUS_FAILURE /*1*/:
                        this.zzayE.onLocationAvailability((LocationAvailability) msg.obj);
                    default:
                }
            }
        }

        zza(LocationCallback locationCallback, Looper looper) {
            if (looper == null) {
                looper = Looper.myLooper();
                zzu.zza(looper != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.zzayO = new C02761(this, looper, locationCallback);
        }

        private void zzb(int i, Object obj) {
            if (this.zzayO == null) {
                Log.e("LocationClientHelper", "Received a data in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = i;
            obtain.obj = obj;
            this.zzayO.sendMessage(obtain);
        }

        public void onLocationAvailability(LocationAvailability state) {
            zzb(1, state);
        }

        public void onLocationResult(LocationResult locationResult) {
            zzb(0, locationResult);
        }

        public void release() {
            this.zzayO = null;
        }
    }

    private static class zzc extends com.google.android.gms.location.zzd.zza {
        private Handler zzayO;

        zzc(LocationListener locationListener, Looper looper) {
            if (looper == null) {
                zzu.zza(Looper.myLooper() != null, (Object) "Can't create handler inside thread that has not called Looper.prepare()");
            }
            this.zzayO = looper == null ? new zzb(locationListener) : new zzb(locationListener, looper);
        }

        public void onLocationChanged(Location location) {
            if (this.zzayO == null) {
                Log.e("LocationClientHelper", "Received a location in client after calling removeLocationUpdates.");
                return;
            }
            Message obtain = Message.obtain();
            obtain.what = 1;
            obtain.obj = location;
            this.zzayO.sendMessage(obtain);
        }

        public void release() {
            this.zzayO = null;
        }
    }

    public zzi(Context context, zzn<zzg> com_google_android_gms_location_internal_zzn_com_google_android_gms_location_internal_zzg) {
        this.zzayL = null;
        this.zzayM = false;
        this.zzakE = new HashMap();
        this.zzayN = new HashMap();
        this.mContext = context;
        this.zzayq = com_google_android_gms_location_internal_zzn_com_google_android_gms_location_internal_zzg;
    }

    private zza zza(LocationCallback locationCallback, Looper looper) {
        zza com_google_android_gms_location_internal_zzi_zza;
        synchronized (this.zzakE) {
            com_google_android_gms_location_internal_zzi_zza = (zza) this.zzayN.get(locationCallback);
            if (com_google_android_gms_location_internal_zzi_zza == null) {
                com_google_android_gms_location_internal_zzi_zza = new zza(locationCallback, looper);
            }
            this.zzayN.put(locationCallback, com_google_android_gms_location_internal_zzi_zza);
        }
        return com_google_android_gms_location_internal_zzi_zza;
    }

    private zzc zza(LocationListener locationListener, Looper looper) {
        zzc com_google_android_gms_location_internal_zzi_zzc;
        synchronized (this.zzakE) {
            com_google_android_gms_location_internal_zzi_zzc = (zzc) this.zzakE.get(locationListener);
            if (com_google_android_gms_location_internal_zzi_zzc == null) {
                com_google_android_gms_location_internal_zzi_zzc = new zzc(locationListener, looper);
            }
            this.zzakE.put(locationListener, com_google_android_gms_location_internal_zzi_zzc);
        }
        return com_google_android_gms_location_internal_zzi_zzc;
    }

    public Location getLastLocation() {
        this.zzayq.zznL();
        try {
            return ((zzg) this.zzayq.zznM()).zzdl(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void removeAllListeners() {
        try {
            synchronized (this.zzakE) {
                for (zzc com_google_android_gms_location_internal_zzi_zzc : this.zzakE.values()) {
                    if (com_google_android_gms_location_internal_zzi_zzc != null) {
                        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(com_google_android_gms_location_internal_zzi_zzc));
                    }
                }
                this.zzakE.clear();
                for (zza com_google_android_gms_location_internal_zzi_zza : this.zzayN.values()) {
                    if (com_google_android_gms_location_internal_zzi_zza != null) {
                        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_internal_zzi_zza));
                    }
                }
                this.zzayN.clear();
            }
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void zza(LocationCallback locationCallback) throws RemoteException {
        this.zzayq.zznL();
        zzu.zzb((Object) locationCallback, (Object) "Invalid null callback");
        synchronized (this.zzayN) {
            zza com_google_android_gms_location_internal_zzi_zza = (zza) this.zzayN.remove(locationCallback);
            if (com_google_android_gms_location_internal_zzi_zza != null) {
                com_google_android_gms_location_internal_zzi_zza.release();
                ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zza(com_google_android_gms_location_internal_zzi_zza));
            }
        }
    }

    public void zza(LocationListener locationListener) throws RemoteException {
        this.zzayq.zznL();
        zzu.zzb((Object) locationListener, (Object) "Invalid null listener");
        synchronized (this.zzakE) {
            zzc com_google_android_gms_location_internal_zzi_zzc = (zzc) this.zzakE.remove(locationListener);
            if (this.zzayL != null && this.zzakE.isEmpty()) {
                this.zzayL.release();
                this.zzayL = null;
            }
            if (com_google_android_gms_location_internal_zzi_zzc != null) {
                com_google_android_gms_location_internal_zzi_zzc.release();
                ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(com_google_android_gms_location_internal_zzi_zzc));
            }
        }
    }

    public void zza(LocationRequest locationRequest, LocationListener locationListener, Looper looper) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(locationRequest), zza(locationListener, looper)));
    }

    public void zza(LocationRequestInternal locationRequestInternal, LocationCallback locationCallback, Looper looper) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zza(locationRequestInternal, zza(locationCallback, looper)));
    }

    public void zzac(boolean z) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zzac(z);
        this.zzayM = z;
    }

    public void zzb(Location location) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zzb(location);
    }

    public void zzb(LocationRequest locationRequest, PendingIntent pendingIntent) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zzb(LocationRequestInternal.zzb(locationRequest), pendingIntent));
    }

    public void zzd(PendingIntent pendingIntent) throws RemoteException {
        this.zzayq.zznL();
        ((zzg) this.zzayq.zznM()).zza(LocationRequestUpdateData.zze(pendingIntent));
    }

    public LocationAvailability zzuw() {
        this.zzayq.zznL();
        try {
            return ((zzg) this.zzayq.zznM()).zzdm(this.mContext.getPackageName());
        } catch (Throwable e) {
            throw new IllegalStateException(e);
        }
    }

    public void zzux() {
        if (this.zzayM) {
            try {
                zzac(false);
            } catch (Throwable e) {
                throw new IllegalStateException(e);
            }
        }
    }
}
