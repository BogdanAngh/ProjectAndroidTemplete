package com.google.android.gms.internal;

import android.location.Location;
import android.support.annotation.Nullable;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import java.util.Date;
import java.util.Set;

@zzji
public final class zzhf implements MediationAdRequest {
    private final int zzazc;
    private final boolean zzazo;
    private final int zzbxg;
    private final Date zzgr;
    private final Set<String> zzgt;
    private final boolean zzgu;
    private final Location zzgv;

    public zzhf(@Nullable Date date, int i, @Nullable Set<String> set, @Nullable Location location, boolean z, int i2, boolean z2) {
        this.zzgr = date;
        this.zzazc = i;
        this.zzgt = set;
        this.zzgv = location;
        this.zzgu = z;
        this.zzbxg = i2;
        this.zzazo = z2;
    }

    public Date getBirthday() {
        return this.zzgr;
    }

    public int getGender() {
        return this.zzazc;
    }

    public Set<String> getKeywords() {
        return this.zzgt;
    }

    public Location getLocation() {
        return this.zzgv;
    }

    public boolean isDesignedForFamilies() {
        return this.zzazo;
    }

    public boolean isTesting() {
        return this.zzgu;
    }

    public int taggedForChildDirectedTreatment() {
        return this.zzbxg;
    }
}
