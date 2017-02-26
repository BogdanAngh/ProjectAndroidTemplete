package com.google.android.gms.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.util.Log;
import com.google.android.gms.clearcut.LogEventParcelable;
import com.google.android.gms.clearcut.zzb;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.zzc;

public class zzqc extends zzc<NoOptions> implements zzb {

    static final class zza extends com.google.android.gms.internal.zzqo.zza<Status, zzqd> {
        private final LogEventParcelable wF;

        /* renamed from: com.google.android.gms.internal.zzqc.zza.1 */
        class C14741 extends com.google.android.gms.internal.zzqf.zza {
            final /* synthetic */ zza wG;

            C14741(zza com_google_android_gms_internal_zzqc_zza) {
                this.wG = com_google_android_gms_internal_zzqc_zza;
            }

            public void zzv(Status status) {
                this.wG.zzc((Result) status);
            }

            public void zzw(Status status) {
                throw new UnsupportedOperationException();
            }
        }

        zza(LogEventParcelable logEventParcelable, GoogleApiClient googleApiClient) {
            super(com.google.android.gms.clearcut.zza.API, googleApiClient);
            this.wF = logEventParcelable;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            return this.wF.equals(((zza) obj).wF);
        }

        public String toString() {
            String valueOf = String.valueOf(this.wF);
            return new StringBuilder(String.valueOf(valueOf).length() + 20).append("LogEventMethodImpl(").append(valueOf).append(")").toString();
        }

        protected void zza(zzqd com_google_android_gms_internal_zzqd) throws RemoteException {
            zzqf c14741 = new C14741(this);
            try {
                zzqc.zzb(this.wF);
                com_google_android_gms_internal_zzqd.zza(c14741, this.wF);
            } catch (Throwable e) {
                Log.e("ClearcutLoggerApiImpl", "derived ClearcutLogger.MessageProducer ", e);
                zzaa(new Status(10, "MessageProducer"));
            }
        }

        protected Status zzb(Status status) {
            return status;
        }

        protected /* synthetic */ Result zzc(Status status) {
            return zzb(status);
        }
    }

    zzqc(@NonNull Context context) {
        super(context, com.google.android.gms.clearcut.zza.API, null, new zzqk());
    }

    static void zzb(LogEventParcelable logEventParcelable) {
        if (logEventParcelable.wD != null && logEventParcelable.wC.buo.length == 0) {
            logEventParcelable.wC.buo = logEventParcelable.wD.zzaqi();
        }
        if (logEventParcelable.wE != null && logEventParcelable.wC.buu.length == 0) {
            logEventParcelable.wC.buu = logEventParcelable.wE.zzaqi();
        }
        logEventParcelable.ww = zzasa.zzf(logEventParcelable.wC);
    }

    public static zzb zzbi(@NonNull Context context) {
        return new zzqc(context);
    }

    public PendingResult<Status> zza(LogEventParcelable logEventParcelable) {
        return doBestEffortWrite(new zza(logEventParcelable, asGoogleApiClient()));
    }
}
