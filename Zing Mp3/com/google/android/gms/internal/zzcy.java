package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.exoplayer.text.Cue;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

@zzji
public class zzcy {
    private final Object zzako;
    private int zzavx;
    private List<zzcx> zzavy;

    public zzcy() {
        this.zzako = new Object();
        this.zzavy = new LinkedList();
    }

    public boolean zza(zzcx com_google_android_gms_internal_zzcx) {
        boolean z;
        synchronized (this.zzako) {
            if (this.zzavy.contains(com_google_android_gms_internal_zzcx)) {
                z = true;
            } else {
                z = false;
            }
        }
        return z;
    }

    public boolean zzb(zzcx com_google_android_gms_internal_zzcx) {
        synchronized (this.zzako) {
            Iterator it = this.zzavy.iterator();
            while (it.hasNext()) {
                zzcx com_google_android_gms_internal_zzcx2 = (zzcx) it.next();
                if (!((Boolean) zzdr.zzbfa.get()).booleanValue() || zzu.zzgq().zzuq()) {
                    if (((Boolean) zzdr.zzbfc.get()).booleanValue() && !zzu.zzgq().zzur() && com_google_android_gms_internal_zzcx != com_google_android_gms_internal_zzcx2 && com_google_android_gms_internal_zzcx2.zzja().equals(com_google_android_gms_internal_zzcx.zzja())) {
                        it.remove();
                        return true;
                    }
                } else if (com_google_android_gms_internal_zzcx != com_google_android_gms_internal_zzcx2 && com_google_android_gms_internal_zzcx2.zziy().equals(com_google_android_gms_internal_zzcx.zziy())) {
                    it.remove();
                    return true;
                }
            }
            return false;
        }
    }

    public void zzc(zzcx com_google_android_gms_internal_zzcx) {
        synchronized (this.zzako) {
            if (this.zzavy.size() >= 10) {
                zzb.zzdg("Queue is full, current size = " + this.zzavy.size());
                this.zzavy.remove(0);
            }
            int i = this.zzavx;
            this.zzavx = i + 1;
            com_google_android_gms_internal_zzcx.zzn(i);
            this.zzavy.add(com_google_android_gms_internal_zzcx);
        }
    }

    @Nullable
    public zzcx zzjg() {
        zzcx com_google_android_gms_internal_zzcx = null;
        int i = 0;
        synchronized (this.zzako) {
            if (this.zzavy.size() == 0) {
                zzb.zzdg("Queue empty");
                return null;
            } else if (this.zzavy.size() >= 2) {
                int i2 = Cue.TYPE_UNSET;
                int i3 = 0;
                for (zzcx com_google_android_gms_internal_zzcx2 : this.zzavy) {
                    zzcx com_google_android_gms_internal_zzcx3;
                    int i4;
                    int score = com_google_android_gms_internal_zzcx2.getScore();
                    if (score > i2) {
                        i = score;
                        com_google_android_gms_internal_zzcx3 = com_google_android_gms_internal_zzcx2;
                        i4 = i3;
                    } else {
                        i4 = i;
                        com_google_android_gms_internal_zzcx3 = com_google_android_gms_internal_zzcx;
                        i = i2;
                    }
                    i3++;
                    i2 = i;
                    com_google_android_gms_internal_zzcx = com_google_android_gms_internal_zzcx3;
                    i = i4;
                }
                this.zzavy.remove(i);
                return com_google_android_gms_internal_zzcx;
            } else {
                com_google_android_gms_internal_zzcx2 = (zzcx) this.zzavy.get(0);
                com_google_android_gms_internal_zzcx2.zzjb();
                return com_google_android_gms_internal_zzcx2;
            }
        }
    }
}
