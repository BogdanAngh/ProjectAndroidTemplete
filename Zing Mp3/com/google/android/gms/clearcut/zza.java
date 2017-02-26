package com.google.android.gms.clearcut;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Looper;
import android.util.Log;
import com.facebook.ads.AdError;
import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Api.zzf;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.PendingResults;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import com.google.android.gms.internal.zzqc;
import com.google.android.gms.internal.zzqd;
import com.google.android.gms.internal.zzqh;
import com.google.android.gms.playlog.internal.PlayLoggerContext;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TimeZone;

public final class zza {
    @Deprecated
    public static final Api<NoOptions> API;
    public static final zzf<zzqd> hg;
    public static final com.google.android.gms.common.api.Api.zza<zzqd, NoOptions> hh;
    private final int wc;
    private String wd;
    private int we;
    private String wf;
    private String wg;
    private final boolean wh;
    private int wi;
    private final zzb wj;
    private zzd wk;
    private final zzb wl;
    private final zze zzaql;
    private final String zzcjc;

    /* renamed from: com.google.android.gms.clearcut.zza.1 */
    class C11731 extends com.google.android.gms.common.api.Api.zza<zzqd, NoOptions> {
        C11731() {
        }

        public /* synthetic */ Api.zze zza(Context context, Looper looper, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, Object obj, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return zze(context, looper, com_google_android_gms_common_internal_zzf, (NoOptions) obj, connectionCallbacks, onConnectionFailedListener);
        }

        public zzqd zze(Context context, Looper looper, com.google.android.gms.common.internal.zzf com_google_android_gms_common_internal_zzf, NoOptions noOptions, ConnectionCallbacks connectionCallbacks, OnConnectionFailedListener onConnectionFailedListener) {
            return new zzqd(context, looper, com_google_android_gms_common_internal_zzf, connectionCallbacks, onConnectionFailedListener);
        }
    }

    public class zza {
        private String wd;
        private int we;
        private String wf;
        private String wg;
        private int wi;
        private final zzc wm;
        private ArrayList<Integer> wn;
        private ArrayList<String> wo;
        private ArrayList<Integer> wp;
        private ArrayList<byte[]> wq;
        private boolean wr;
        private final com.google.android.gms.internal.zzasf.zzc ws;
        private boolean wt;
        final /* synthetic */ zza wu;

        private zza(zza com_google_android_gms_clearcut_zza, byte[] bArr) {
            this(com_google_android_gms_clearcut_zza, bArr, null);
        }

        private zza(zza com_google_android_gms_clearcut_zza, byte[] bArr, zzc com_google_android_gms_clearcut_zza_zzc) {
            this.wu = com_google_android_gms_clearcut_zza;
            this.we = this.wu.we;
            this.wd = this.wu.wd;
            this.wf = this.wu.wf;
            this.wg = this.wu.wg;
            this.wi = 0;
            this.wn = null;
            this.wo = null;
            this.wp = null;
            this.wq = null;
            this.wr = true;
            this.ws = new com.google.android.gms.internal.zzasf.zzc();
            this.wt = false;
            this.wf = com_google_android_gms_clearcut_zza.wf;
            this.wg = com_google_android_gms_clearcut_zza.wg;
            this.ws.buh = com_google_android_gms_clearcut_zza.zzaql.currentTimeMillis();
            this.ws.bui = com_google_android_gms_clearcut_zza.zzaql.elapsedRealtime();
            this.ws.bus = com_google_android_gms_clearcut_zza.wk.zzag(this.ws.buh);
            if (bArr != null) {
                this.ws.buo = bArr;
            }
            this.wm = com_google_android_gms_clearcut_zza_zzc;
        }

        public LogEventParcelable zzaqg() {
            return new LogEventParcelable(new PlayLoggerContext(this.wu.zzcjc, this.wu.wc, this.we, this.wd, this.wf, this.wg, this.wu.wh, this.wi), this.ws, this.wm, null, zza.zzb(null), zza.zzc(null), zza.zzb(null), zza.zzd(null), this.wr);
        }

        @Deprecated
        public PendingResult<Status> zzaqh() {
            if (this.wt) {
                throw new IllegalStateException("do not reuse LogEventBuilder");
            }
            this.wt = true;
            LogEventParcelable zzaqg = zzaqg();
            PlayLoggerContext playLoggerContext = zzaqg.wv;
            return this.wu.wl.zzh(playLoggerContext.aAG, playLoggerContext.aAC) ? this.wu.wj.zza(zzaqg) : PendingResults.immediatePendingResult(Status.xZ);
        }

        @Deprecated
        public PendingResult<Status> zze(GoogleApiClient googleApiClient) {
            return zzaqh();
        }

        public zza zzfl(int i) {
            this.ws.buk = i;
            return this;
        }

        public zza zzfm(int i) {
            this.ws.zzajo = i;
            return this;
        }
    }

    public interface zzb {
        boolean zzh(String str, int i);
    }

    public interface zzc {
        byte[] zzaqi();
    }

    public static class zzd {
        public long zzag(long j) {
            return (long) (TimeZone.getDefault().getOffset(j) / AdError.NETWORK_ERROR_CODE);
        }
    }

    static {
        hg = new zzf();
        hh = new C11731();
        API = new Api("ClearcutLogger.API", hh, hg);
    }

    public zza(Context context, int i, String str, String str2, String str3, boolean z, zzb com_google_android_gms_clearcut_zzb, zze com_google_android_gms_common_util_zze, zzd com_google_android_gms_clearcut_zza_zzd, zzb com_google_android_gms_clearcut_zza_zzb) {
        boolean z2 = false;
        this.we = -1;
        this.wi = 0;
        this.zzcjc = context.getPackageName();
        this.wc = zzbh(context);
        this.we = i;
        this.wd = str;
        this.wf = str2;
        this.wg = str3;
        this.wh = z;
        this.wj = com_google_android_gms_clearcut_zzb;
        this.zzaql = com_google_android_gms_common_util_zze;
        if (com_google_android_gms_clearcut_zza_zzd == null) {
            com_google_android_gms_clearcut_zza_zzd = new zzd();
        }
        this.wk = com_google_android_gms_clearcut_zza_zzd;
        this.wi = 0;
        this.wl = com_google_android_gms_clearcut_zza_zzb;
        if (this.wh) {
            if (this.wf == null) {
                z2 = true;
            }
            zzaa.zzb(z2, (Object) "can't be anonymous with an upload account");
        }
    }

    public zza(Context context, String str, String str2) {
        this(context, -1, str, str2, null, false, zzqc.zzbi(context), zzh.zzayl(), null, new zzqh(context));
    }

    private static int[] zzb(ArrayList<Integer> arrayList) {
        if (arrayList == null) {
            return null;
        }
        int[] iArr = new int[arrayList.size()];
        Iterator it = arrayList.iterator();
        int i = 0;
        while (it.hasNext()) {
            int i2 = i + 1;
            iArr[i] = ((Integer) it.next()).intValue();
            i = i2;
        }
        return iArr;
    }

    private int zzbh(Context context) {
        int i = 0;
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            Log.wtf("ClearcutLogger", "This can't happen.");
            return i;
        }
    }

    private static String[] zzc(ArrayList<String> arrayList) {
        return arrayList == null ? null : (String[]) arrayList.toArray(new String[0]);
    }

    private static byte[][] zzd(ArrayList<byte[]> arrayList) {
        return arrayList == null ? null : (byte[][]) arrayList.toArray(new byte[0][]);
    }

    public zza zzm(byte[] bArr) {
        return new zza(bArr, null);
    }
}
