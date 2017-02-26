package com.google.android.gms.analytics;

import com.google.android.gms.common.internal.zzaa;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public final class zze {
    private final zzh aW;
    private boolean aX;
    private long aY;
    private long aZ;
    private long ba;
    private long bb;
    private long bc;
    private boolean bd;
    private final Map<Class<? extends zzg>, zzg> be;
    private final List<zzk> bf;
    private final com.google.android.gms.common.util.zze zzaql;

    zze(zze com_google_android_gms_analytics_zze) {
        this.aW = com_google_android_gms_analytics_zze.aW;
        this.zzaql = com_google_android_gms_analytics_zze.zzaql;
        this.aY = com_google_android_gms_analytics_zze.aY;
        this.aZ = com_google_android_gms_analytics_zze.aZ;
        this.ba = com_google_android_gms_analytics_zze.ba;
        this.bb = com_google_android_gms_analytics_zze.bb;
        this.bc = com_google_android_gms_analytics_zze.bc;
        this.bf = new ArrayList(com_google_android_gms_analytics_zze.bf);
        this.be = new HashMap(com_google_android_gms_analytics_zze.be.size());
        for (Entry entry : com_google_android_gms_analytics_zze.be.entrySet()) {
            zzg zzc = zzc((Class) entry.getKey());
            ((zzg) entry.getValue()).zzb(zzc);
            this.be.put((Class) entry.getKey(), zzc);
        }
    }

    zze(zzh com_google_android_gms_analytics_zzh, com.google.android.gms.common.util.zze com_google_android_gms_common_util_zze) {
        zzaa.zzy(com_google_android_gms_analytics_zzh);
        zzaa.zzy(com_google_android_gms_common_util_zze);
        this.aW = com_google_android_gms_analytics_zzh;
        this.zzaql = com_google_android_gms_common_util_zze;
        this.bb = 1800000;
        this.bc = 3024000000L;
        this.be = new HashMap();
        this.bf = new ArrayList();
    }

    private static <T extends zzg> T zzc(Class<T> cls) {
        try {
            return (zzg) cls.newInstance();
        } catch (Throwable e) {
            throw new IllegalArgumentException("dataType doesn't have default constructor", e);
        } catch (Throwable e2) {
            throw new IllegalArgumentException("dataType default constructor is not accessible", e2);
        }
    }

    public <T extends zzg> T zza(Class<T> cls) {
        return (zzg) this.be.get(cls);
    }

    public void zza(zzg com_google_android_gms_analytics_zzg) {
        zzaa.zzy(com_google_android_gms_analytics_zzg);
        Class cls = com_google_android_gms_analytics_zzg.getClass();
        if (cls.getSuperclass() != zzg.class) {
            throw new IllegalArgumentException();
        }
        com_google_android_gms_analytics_zzg.zzb(zzb(cls));
    }

    public <T extends zzg> T zzb(Class<T> cls) {
        zzg com_google_android_gms_analytics_zzg = (zzg) this.be.get(cls);
        if (com_google_android_gms_analytics_zzg != null) {
            return com_google_android_gms_analytics_zzg;
        }
        T zzc = zzc(cls);
        this.be.put(cls, zzc);
        return zzc;
    }

    public void zzp(long j) {
        this.aZ = j;
    }

    public zze zzzi() {
        return new zze(this);
    }

    public Collection<zzg> zzzj() {
        return this.be.values();
    }

    public List<zzk> zzzk() {
        return this.bf;
    }

    public long zzzl() {
        return this.aY;
    }

    public void zzzm() {
        zzzq().zze(this);
    }

    public boolean zzzn() {
        return this.aX;
    }

    void zzzo() {
        this.ba = this.zzaql.elapsedRealtime();
        if (this.aZ != 0) {
            this.aY = this.aZ;
        } else {
            this.aY = this.zzaql.currentTimeMillis();
        }
        this.aX = true;
    }

    zzh zzzp() {
        return this.aW;
    }

    zzi zzzq() {
        return this.aW.zzzq();
    }

    boolean zzzr() {
        return this.bd;
    }

    void zzzs() {
        this.bd = true;
    }
}
