package com.google.android.gms.playlog.internal;

import android.content.Context;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.RemoteException;
import android.util.Log;
import com.google.android.gms.common.internal.zzb;
import com.google.android.gms.common.internal.zze;
import com.google.android.gms.common.internal.zzi;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzrn;
import com.google.android.gms.playlog.internal.zzb.zza;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class zzf extends zzi<zza> {
    private final String zzMZ;
    private final zzd zzaGW;
    private final zzb zzaGX;
    private boolean zzaGY;
    private final Object zzqt;

    public zzf(Context context, Looper looper, zzd com_google_android_gms_playlog_internal_zzd, zze com_google_android_gms_common_internal_zze) {
        super(context, looper, 24, com_google_android_gms_playlog_internal_zzd, com_google_android_gms_playlog_internal_zzd, com_google_android_gms_common_internal_zze);
        this.zzMZ = context.getPackageName();
        this.zzaGW = (zzd) zzu.zzu(com_google_android_gms_playlog_internal_zzd);
        this.zzaGW.zza(this);
        this.zzaGX = new zzb();
        this.zzqt = new Object();
        this.zzaGY = true;
    }

    private void zzc(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        this.zzaGX.zza(playLoggerContext, logEvent);
    }

    private void zzd(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        try {
            zzxp();
            ((zza) zznM()).zza(this.zzMZ, playLoggerContext, logEvent);
        } catch (RemoteException e) {
            Log.e("PlayLoggerImpl", "Couldn't send log event.  Will try caching.");
            zzc(playLoggerContext, logEvent);
        } catch (IllegalStateException e2) {
            Log.e("PlayLoggerImpl", "Service was disconnected.  Will try caching.");
            zzc(playLoggerContext, logEvent);
        }
    }

    private void zzxp() {
        zzb.zzU(!this.zzaGY);
        if (!this.zzaGX.isEmpty()) {
            PlayLoggerContext playLoggerContext = null;
            try {
                List arrayList = new ArrayList();
                Iterator it = this.zzaGX.zzxn().iterator();
                while (it.hasNext()) {
                    zza com_google_android_gms_playlog_internal_zzb_zza = (zza) it.next();
                    if (com_google_android_gms_playlog_internal_zzb_zza.zzaGM != null) {
                        ((zza) zznM()).zza(this.zzMZ, com_google_android_gms_playlog_internal_zzb_zza.zzaGK, zzrn.zzf(com_google_android_gms_playlog_internal_zzb_zza.zzaGM));
                    } else {
                        PlayLoggerContext playLoggerContext2;
                        if (com_google_android_gms_playlog_internal_zzb_zza.zzaGK.equals(playLoggerContext)) {
                            arrayList.add(com_google_android_gms_playlog_internal_zzb_zza.zzaGL);
                            playLoggerContext2 = playLoggerContext;
                        } else {
                            if (!arrayList.isEmpty()) {
                                ((zza) zznM()).zza(this.zzMZ, playLoggerContext, arrayList);
                                arrayList.clear();
                            }
                            PlayLoggerContext playLoggerContext3 = com_google_android_gms_playlog_internal_zzb_zza.zzaGK;
                            arrayList.add(com_google_android_gms_playlog_internal_zzb_zza.zzaGL);
                            playLoggerContext2 = playLoggerContext3;
                        }
                        playLoggerContext = playLoggerContext2;
                    }
                }
                if (!arrayList.isEmpty()) {
                    ((zza) zznM()).zza(this.zzMZ, playLoggerContext, arrayList);
                }
                this.zzaGX.clear();
            } catch (RemoteException e) {
                Log.e("PlayLoggerImpl", "Couldn't send cached log events to AndroidLog service.  Retaining in memory cache.");
            }
        }
    }

    protected String getServiceDescriptor() {
        return "com.google.android.gms.playlog.internal.IPlayLogService";
    }

    protected String getStartServiceAction() {
        return "com.google.android.gms.playlog.service.START";
    }

    public void start() {
        synchronized (this.zzqt) {
            if (isConnecting() || isConnected()) {
                return;
            }
            this.zzaGW.zzaj(true);
            zznJ();
        }
    }

    public void stop() {
        synchronized (this.zzqt) {
            this.zzaGW.zzaj(false);
            disconnect();
        }
    }

    protected /* synthetic */ IInterface zzT(IBinder iBinder) {
        return zzdq(iBinder);
    }

    void zzak(boolean z) {
        synchronized (this.zzqt) {
            boolean z2 = this.zzaGY;
            this.zzaGY = z;
            if (z2 && !this.zzaGY) {
                zzxp();
            }
        }
    }

    public void zzb(PlayLoggerContext playLoggerContext, LogEvent logEvent) {
        synchronized (this.zzqt) {
            if (this.zzaGY) {
                zzc(playLoggerContext, logEvent);
            } else {
                zzd(playLoggerContext, logEvent);
            }
        }
    }

    protected zza zzdq(IBinder iBinder) {
        return zza.zza.zzdp(iBinder);
    }
}
