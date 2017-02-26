package com.google.android.gms.internal;

import android.graphics.Bitmap;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@zzji
public class zzlk {
    Map<Integer, Bitmap> zzcxf;
    private AtomicInteger zzcxg;

    public zzlk() {
        this.zzcxf = new ConcurrentHashMap();
        this.zzcxg = new AtomicInteger(0);
    }

    public Bitmap zza(Integer num) {
        return (Bitmap) this.zzcxf.get(num);
    }

    public int zzb(Bitmap bitmap) {
        if (bitmap == null) {
            zzb.zzdg("Bitmap is null. Skipping putting into the Memory Map.");
            return -1;
        }
        this.zzcxf.put(Integer.valueOf(this.zzcxg.get()), bitmap);
        return this.zzcxg.getAndIncrement();
    }

    public void zzb(Integer num) {
        this.zzcxf.remove(num);
    }
}
