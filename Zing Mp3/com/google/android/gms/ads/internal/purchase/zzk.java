package com.google.android.gms.ads.internal.purchase;

import android.content.Intent;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzji;

@zzji
public class zzk {
    private final String zzbbj;

    public zzk(String str) {
        this.zzbbj = str;
    }

    public boolean zza(String str, int i, Intent intent) {
        if (str == null || intent == null) {
            return false;
        }
        String zze = zzu.zzha().zze(intent);
        String zzf = zzu.zzha().zzf(intent);
        if (zze == null || zzf == null) {
            return false;
        }
        if (!str.equals(zzu.zzha().zzcg(zze))) {
            zzb.zzdi("Developer payload not match.");
            return false;
        } else if (this.zzbbj == null || zzl.zzc(this.zzbbj, zze, zzf)) {
            return true;
        } else {
            zzb.zzdi("Fail to verify signature.");
            return false;
        }
    }

    public String zzrv() {
        return zzu.zzgm().zzvs();
    }
}
