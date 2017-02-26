package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.zzu;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzji
public class zzfr implements Iterable<zzfq> {
    private final List<zzfq> zzbrd;

    public zzfr() {
        this.zzbrd = new LinkedList();
    }

    private zzfq zzg(zzmd com_google_android_gms_internal_zzmd) {
        Iterator it = zzu.zzhj().iterator();
        while (it.hasNext()) {
            zzfq com_google_android_gms_internal_zzfq = (zzfq) it.next();
            if (com_google_android_gms_internal_zzfq.zzbnz == com_google_android_gms_internal_zzmd) {
                return com_google_android_gms_internal_zzfq;
            }
        }
        return null;
    }

    public Iterator<zzfq> iterator() {
        return this.zzbrd.iterator();
    }

    public void zza(zzfq com_google_android_gms_internal_zzfq) {
        this.zzbrd.add(com_google_android_gms_internal_zzfq);
    }

    public void zzb(zzfq com_google_android_gms_internal_zzfq) {
        this.zzbrd.remove(com_google_android_gms_internal_zzfq);
    }

    public boolean zze(zzmd com_google_android_gms_internal_zzmd) {
        zzfq zzg = zzg(com_google_android_gms_internal_zzmd);
        if (zzg == null) {
            return false;
        }
        zzg.zzbra.abort();
        return true;
    }

    public boolean zzf(zzmd com_google_android_gms_internal_zzmd) {
        return zzg(com_google_android_gms_internal_zzmd) != null;
    }

    public int zzni() {
        return this.zzbrd.size();
    }
}
