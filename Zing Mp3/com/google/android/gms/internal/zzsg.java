package com.google.android.gms.internal;

import android.os.IBinder;
import android.os.IBinder.DeathRecipient;
import android.os.RemoteException;
import com.google.android.gms.common.api.Api.zzc;
import com.google.android.gms.common.api.Api.zze;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;

public class zzsg {
    private static final zzqq<?>[] BE;
    public static final Status ym;
    private final Map<zzc<?>, zze> Aj;
    final Set<zzqq<?>> BF;
    private final zzb BG;

    interface zzb {
        void zzc(zzqq<?> com_google_android_gms_internal_zzqq_);
    }

    /* renamed from: com.google.android.gms.internal.zzsg.1 */
    class C14961 implements zzb {
        final /* synthetic */ zzsg BH;

        C14961(zzsg com_google_android_gms_internal_zzsg) {
            this.BH = com_google_android_gms_internal_zzsg;
        }

        public void zzc(zzqq<?> com_google_android_gms_internal_zzqq_) {
            this.BH.BF.remove(com_google_android_gms_internal_zzqq_);
            if (com_google_android_gms_internal_zzqq_.zzarh() != null && null != null) {
                null.remove(com_google_android_gms_internal_zzqq_.zzarh().intValue());
            }
        }
    }

    private static class zza implements DeathRecipient, zzb {
        private final WeakReference<zzqq<?>> BI;
        private final WeakReference<com.google.android.gms.common.api.zze> BJ;
        private final WeakReference<IBinder> BK;

        private zza(zzqq<?> com_google_android_gms_internal_zzqq_, com.google.android.gms.common.api.zze com_google_android_gms_common_api_zze, IBinder iBinder) {
            this.BJ = new WeakReference(com_google_android_gms_common_api_zze);
            this.BI = new WeakReference(com_google_android_gms_internal_zzqq_);
            this.BK = new WeakReference(iBinder);
        }

        private void zzaug() {
            zzqq com_google_android_gms_internal_zzqq = (zzqq) this.BI.get();
            com.google.android.gms.common.api.zze com_google_android_gms_common_api_zze = (com.google.android.gms.common.api.zze) this.BJ.get();
            if (!(com_google_android_gms_common_api_zze == null || com_google_android_gms_internal_zzqq == null)) {
                com_google_android_gms_common_api_zze.remove(com_google_android_gms_internal_zzqq.zzarh().intValue());
            }
            IBinder iBinder = (IBinder) this.BK.get();
            if (iBinder != null) {
                iBinder.unlinkToDeath(this, 0);
            }
        }

        public void binderDied() {
            zzaug();
        }

        public void zzc(zzqq<?> com_google_android_gms_internal_zzqq_) {
            zzaug();
        }
    }

    static {
        ym = new Status(8, "The connection to Google Play services was lost");
        BE = new zzqq[0];
    }

    public zzsg(Map<zzc<?>, zze> map) {
        this.BF = Collections.synchronizedSet(Collections.newSetFromMap(new WeakHashMap()));
        this.BG = new C14961(this);
        this.Aj = map;
    }

    private static void zza(zzqq<?> com_google_android_gms_internal_zzqq_, com.google.android.gms.common.api.zze com_google_android_gms_common_api_zze, IBinder iBinder) {
        if (com_google_android_gms_internal_zzqq_.isReady()) {
            com_google_android_gms_internal_zzqq_.zza(new zza(com_google_android_gms_common_api_zze, iBinder, null));
        } else if (iBinder == null || !iBinder.isBinderAlive()) {
            com_google_android_gms_internal_zzqq_.zza(null);
            com_google_android_gms_internal_zzqq_.cancel();
            com_google_android_gms_common_api_zze.remove(com_google_android_gms_internal_zzqq_.zzarh().intValue());
        } else {
            zzb com_google_android_gms_internal_zzsg_zza = new zza(com_google_android_gms_common_api_zze, iBinder, null);
            com_google_android_gms_internal_zzqq_.zza(com_google_android_gms_internal_zzsg_zza);
            try {
                iBinder.linkToDeath(com_google_android_gms_internal_zzsg_zza, 0);
            } catch (RemoteException e) {
                com_google_android_gms_internal_zzqq_.cancel();
                com_google_android_gms_common_api_zze.remove(com_google_android_gms_internal_zzqq_.zzarh().intValue());
            }
        }
    }

    public void dump(PrintWriter printWriter) {
        printWriter.append(" mUnconsumedApiCalls.size()=").println(this.BF.size());
    }

    public void release() {
        for (zzqq com_google_android_gms_internal_zzqq : (zzqq[]) this.BF.toArray(BE)) {
            com_google_android_gms_internal_zzqq.zza(null);
            if (com_google_android_gms_internal_zzqq.zzarh() != null) {
                com_google_android_gms_internal_zzqq.zzaru();
                zza(com_google_android_gms_internal_zzqq, null, ((zze) this.Aj.get(((com.google.android.gms.internal.zzqo.zza) com_google_android_gms_internal_zzqq).zzaqv())).zzaqy());
                this.BF.remove(com_google_android_gms_internal_zzqq);
            } else if (com_google_android_gms_internal_zzqq.zzars()) {
                this.BF.remove(com_google_android_gms_internal_zzqq);
            }
        }
    }

    public void zzauf() {
        for (zzqq zzab : (zzqq[]) this.BF.toArray(BE)) {
            zzab.zzab(ym);
        }
    }

    void zzb(zzqq<? extends Result> com_google_android_gms_internal_zzqq__extends_com_google_android_gms_common_api_Result) {
        this.BF.add(com_google_android_gms_internal_zzqq__extends_com_google_android_gms_common_api_Result);
        com_google_android_gms_internal_zzqq__extends_com_google_android_gms_common_api_Result.zza(this.BG);
    }
}
