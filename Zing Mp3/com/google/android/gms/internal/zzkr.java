package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;
import android.security.NetworkSecurityPolicy;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.dynamite.descriptors.com.google.android.gms.ads.dynamite.ModuleDescriptor;
import com.google.android.gms.internal.zzcz.zzb;
import com.mp3download.zingmp3.BuildConfig;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.concurrent.Future;

@zzji
public class zzkr implements zzb, zzkz.zzb {
    private Context mContext;
    private final Object zzako;
    private zzco zzama;
    private VersionInfoParcel zzanu;
    private boolean zzaoz;
    private zzcy zzawi;
    private String zzbre;
    private boolean zzcot;
    private boolean zzcou;
    private boolean zzcov;
    private boolean zzcpd;
    private final String zzctq;
    private final zzks zzctr;
    private BigInteger zzcts;
    private final HashSet<zzkp> zzctt;
    private final HashMap<String, zzku> zzctu;
    private boolean zzctv;
    private int zzctw;
    private zzdt zzctx;
    private zzda zzcty;
    private String zzctz;
    private String zzcua;
    private Boolean zzcub;
    private boolean zzcuc;
    private boolean zzcud;
    private boolean zzcue;
    private String zzcuf;
    private long zzcug;
    private long zzcuh;
    private int zzcui;

    public zzkr(zzlb com_google_android_gms_internal_zzlb) {
        this.zzako = new Object();
        this.zzcts = BigInteger.ONE;
        this.zzctt = new HashSet();
        this.zzctu = new HashMap();
        this.zzctv = false;
        this.zzcot = true;
        this.zzctw = 0;
        this.zzaoz = false;
        this.zzctx = null;
        this.zzcou = true;
        this.zzcov = true;
        this.zzcty = null;
        this.zzawi = null;
        this.zzcub = null;
        this.zzcuc = false;
        this.zzcud = false;
        this.zzcpd = false;
        this.zzcue = false;
        this.zzcuf = BuildConfig.FLAVOR;
        this.zzcug = 0;
        this.zzcuh = 0;
        this.zzcui = -1;
        this.zzctq = com_google_android_gms_internal_zzlb.zzvs();
        this.zzctr = new zzks(this.zzctq);
    }

    public Resources getResources() {
        if (this.zzanu.zzcyc) {
            return this.mContext.getResources();
        }
        try {
            zztl zza = zztl.zza(this.mContext, zztl.Qm, ModuleDescriptor.MODULE_ID);
            return zza != null ? zza.zzbdt().getResources() : null;
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot load resource from dynamite apk or local jar", e);
            return null;
        }
    }

    public String getSessionId() {
        return this.zzctq;
    }

    public Bundle zza(Context context, zzkt com_google_android_gms_internal_zzkt, String str) {
        Bundle bundle;
        synchronized (this.zzako) {
            bundle = new Bundle();
            bundle.putBundle("app", this.zzctr.zze(context, str));
            Bundle bundle2 = new Bundle();
            for (String str2 : this.zzctu.keySet()) {
                bundle2.putBundle(str2, ((zzku) this.zzctu.get(str2)).toBundle());
            }
            bundle.putBundle("slots", bundle2);
            ArrayList arrayList = new ArrayList();
            Iterator it = this.zzctt.iterator();
            while (it.hasNext()) {
                arrayList.add(((zzkp) it.next()).toBundle());
            }
            bundle.putParcelableArrayList("ads", arrayList);
            com_google_android_gms_internal_zzkt.zza(this.zzctt);
            this.zzctt.clear();
        }
        return bundle;
    }

    public void zza(zzkp com_google_android_gms_internal_zzkp) {
        synchronized (this.zzako) {
            this.zzctt.add(com_google_android_gms_internal_zzkp);
        }
    }

    public void zza(String str, zzku com_google_android_gms_internal_zzku) {
        synchronized (this.zzako) {
            this.zzctu.put(str, com_google_android_gms_internal_zzku);
        }
    }

    public void zza(Throwable th, String str) {
        zzjg.zzb(this.mContext, this.zzanu).zza(th, str);
    }

    public void zzaf(boolean z) {
        synchronized (this.zzako) {
            if (this.zzcou != z) {
                zzkz.zze(this.mContext, z);
            }
            this.zzcou = z;
            zzda zzw = zzw(this.mContext);
            if (!(zzw == null || zzw.isAlive())) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdh("start fetching content...");
                zzw.zzjh();
            }
        }
    }

    public void zzag(boolean z) {
        synchronized (this.zzako) {
            if (this.zzcov != z) {
                zzkz.zze(this.mContext, z);
            }
            zzkz.zze(this.mContext, z);
            this.zzcov = z;
            zzda zzw = zzw(this.mContext);
            if (!(zzw == null || zzw.isAlive())) {
                com.google.android.gms.ads.internal.util.client.zzb.zzdh("start fetching content...");
                zzw.zzjh();
            }
        }
    }

    public void zzah(boolean z) {
        this.zzcue = z;
    }

    public void zzai(boolean z) {
        synchronized (this.zzako) {
            this.zzcuc = z;
        }
    }

    public void zzb(Boolean bool) {
        synchronized (this.zzako) {
            this.zzcub = bool;
        }
    }

    public void zzb(HashSet<zzkp> hashSet) {
        synchronized (this.zzako) {
            this.zzctt.addAll(hashSet);
        }
    }

    public Future zzbf(int i) {
        Future zza;
        synchronized (this.zzako) {
            this.zzcui = i;
            zza = zzkz.zza(this.mContext, i);
        }
        return zza;
    }

    public Future zzc(Context context, boolean z) {
        Future zzc;
        synchronized (this.zzako) {
            if (z != this.zzcot) {
                this.zzcot = z;
                zzc = zzkz.zzc(context, z);
            } else {
                zzc = null;
            }
        }
        return zzc;
    }

    @TargetApi(23)
    public void zzc(Context context, VersionInfoParcel versionInfoParcel) {
        synchronized (this.zzako) {
            if (!this.zzaoz) {
                this.mContext = context.getApplicationContext();
                this.zzanu = versionInfoParcel;
                zzu.zzgp().zza(this);
                zzkz.zza(context, (zzkz.zzb) this);
                zzkz.zzb(context, this);
                zzkz.zzc(context, (zzkz.zzb) this);
                zzkz.zzd(context, this);
                zzkz.zze(context, (zzkz.zzb) this);
                zzkz.zzf(context, (zzkz.zzb) this);
                zzkz.zzg(context, (zzkz.zzb) this);
                zzvh();
                this.zzbre = zzu.zzgm().zzh(context, versionInfoParcel.zzda);
                if (zzs.zzayy() && !NetworkSecurityPolicy.getInstance().isCleartextTrafficPermitted()) {
                    this.zzcud = true;
                }
                this.zzama = new zzco(context.getApplicationContext(), this.zzanu, zzu.zzgm().zzd(context, versionInfoParcel));
                zzvj();
                zzu.zzha().zzr(this.mContext);
                this.zzaoz = true;
            }
        }
    }

    public Future zzcw(String str) {
        Future zzf;
        synchronized (this.zzako) {
            if (str != null) {
                if (!str.equals(this.zzctz)) {
                    this.zzctz = str;
                    zzf = zzkz.zzf(this.mContext, str);
                }
            }
            zzf = null;
        }
        return zzf;
    }

    public Future zzcx(String str) {
        Future zzg;
        synchronized (this.zzako) {
            if (str != null) {
                if (!str.equals(this.zzcua)) {
                    this.zzcua = str;
                    zzg = zzkz.zzg(this.mContext, str);
                }
            }
            zzg = null;
        }
        return zzg;
    }

    public Future zzd(Context context, String str) {
        Future zza;
        this.zzcug = zzu.zzgs().currentTimeMillis();
        synchronized (this.zzako) {
            if (str != null) {
                if (!str.equals(this.zzcuf)) {
                    this.zzcuf = str;
                    zza = zzkz.zza(context, str, this.zzcug);
                }
            }
            zza = null;
        }
        return zza;
    }

    public Future zzd(Context context, boolean z) {
        Future zzf;
        synchronized (this.zzako) {
            if (z != this.zzcpd) {
                this.zzcpd = z;
                zzf = zzkz.zzf(context, z);
            } else {
                zzf = null;
            }
        }
        return zzf;
    }

    public void zzh(Bundle bundle) {
        synchronized (this.zzako) {
            this.zzcot = bundle.getBoolean("use_https", this.zzcot);
            this.zzctw = bundle.getInt("webview_cache_version", this.zzctw);
            if (bundle.containsKey("content_url_opted_out")) {
                zzaf(bundle.getBoolean("content_url_opted_out"));
            }
            if (bundle.containsKey("content_url_hashes")) {
                this.zzctz = bundle.getString("content_url_hashes");
            }
            this.zzcpd = bundle.getBoolean("auto_collect_location", this.zzcpd);
            if (bundle.containsKey("content_vertical_opted_out")) {
                zzag(bundle.getBoolean("content_vertical_opted_out"));
            }
            if (bundle.containsKey("content_vertical_hashes")) {
                this.zzcua = bundle.getString("content_vertical_hashes");
            }
            this.zzcuf = bundle.containsKey("app_settings_json") ? bundle.getString("app_settings_json") : this.zzcuf;
            this.zzcug = bundle.getLong("app_settings_last_update_ms", this.zzcug);
            this.zzcuh = bundle.getLong("app_last_background_time_ms", this.zzcuh);
            this.zzcui = bundle.getInt("request_in_session_count", this.zzcui);
        }
    }

    public void zzk(boolean z) {
        if (!z) {
            zzo(zzu.zzgs().currentTimeMillis());
            zzbf(this.zzctr.zzvd());
        } else if (zzu.zzgs().currentTimeMillis() - this.zzcuh > ((Long) zzdr.zzbgb.get()).longValue()) {
            this.zzctr.zzbg(-1);
        } else {
            this.zzctr.zzbg(this.zzcui);
        }
    }

    public Future zzo(long j) {
        Future zza;
        synchronized (this.zzako) {
            if (this.zzcuh < j) {
                this.zzcuh = j;
                zza = zzkz.zza(this.mContext, j);
            } else {
                zza = null;
            }
        }
        return zza;
    }

    public boolean zzuq() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcou;
        }
        return z;
    }

    public boolean zzur() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcov;
        }
        return z;
    }

    public String zzus() {
        String bigInteger;
        synchronized (this.zzako) {
            bigInteger = this.zzcts.toString();
            this.zzcts = this.zzcts.add(BigInteger.ONE);
        }
        return bigInteger;
    }

    public zzks zzut() {
        zzks com_google_android_gms_internal_zzks;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzks = this.zzctr;
        }
        return com_google_android_gms_internal_zzks;
    }

    public zzdt zzuu() {
        zzdt com_google_android_gms_internal_zzdt;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzdt = this.zzctx;
        }
        return com_google_android_gms_internal_zzdt;
    }

    public boolean zzuv() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzctv;
            this.zzctv = true;
        }
        return z;
    }

    public boolean zzuw() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcot || this.zzcud;
        }
        return z;
    }

    public String zzux() {
        String str;
        synchronized (this.zzako) {
            str = this.zzbre;
        }
        return str;
    }

    public String zzuy() {
        String str;
        synchronized (this.zzako) {
            str = this.zzctz;
        }
        return str;
    }

    public String zzuz() {
        String str;
        synchronized (this.zzako) {
            str = this.zzcua;
        }
        return str;
    }

    public Boolean zzva() {
        Boolean bool;
        synchronized (this.zzako) {
            bool = this.zzcub;
        }
        return bool;
    }

    public boolean zzvb() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcpd;
        }
        return z;
    }

    public long zzvc() {
        long j;
        synchronized (this.zzako) {
            j = this.zzcuh;
        }
        return j;
    }

    public int zzvd() {
        int i;
        synchronized (this.zzako) {
            i = this.zzcui;
        }
        return i;
    }

    public boolean zzve() {
        return this.zzcue;
    }

    public zzkq zzvf() {
        zzkq com_google_android_gms_internal_zzkq;
        synchronized (this.zzako) {
            com_google_android_gms_internal_zzkq = new zzkq(this.zzcuf, this.zzcug);
        }
        return com_google_android_gms_internal_zzkq;
    }

    public zzco zzvg() {
        return this.zzama;
    }

    public void zzvh() {
        zzjg.zzb(this.mContext, this.zzanu);
    }

    public boolean zzvi() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzcuc;
        }
        return z;
    }

    void zzvj() {
        try {
            this.zzctx = zzu.zzgt().zza(new zzds(this.mContext, this.zzanu.zzda));
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Cannot initialize CSI reporter.", e);
        }
    }

    public zzda zzw(Context context) {
        if (!((Boolean) zzdr.zzbeu.get()).booleanValue()) {
            return null;
        }
        if (!zzs.zzayq()) {
            return null;
        }
        if (!((Boolean) zzdr.zzbfc.get()).booleanValue() && !((Boolean) zzdr.zzbfa.get()).booleanValue()) {
            return null;
        }
        if (zzuq() && zzur()) {
            return null;
        }
        synchronized (this.zzako) {
            if (Looper.getMainLooper() == null || context == null) {
                return null;
            }
            if (this.zzawi == null) {
                this.zzawi = new zzcy();
            }
            if (this.zzcty == null) {
                this.zzcty = new zzda(this.zzawi, zzjg.zzb(this.mContext, this.zzanu));
            }
            this.zzcty.zzjh();
            zzda com_google_android_gms_internal_zzda = this.zzcty;
            return com_google_android_gms_internal_zzda;
        }
    }
}
