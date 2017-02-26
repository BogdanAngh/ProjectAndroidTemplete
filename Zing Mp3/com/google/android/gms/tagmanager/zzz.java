package com.google.android.gms.tagmanager;

import android.util.Log;

public class zzz implements zzbp {
    private int ee;

    public zzz() {
        this.ee = 5;
    }

    public void m1702e(String str) {
        if (this.ee <= 6) {
            Log.e("GoogleTagManager", str);
        }
    }

    public void setLogLevel(int i) {
        this.ee = i;
    }

    public void m1703v(String str) {
        if (this.ee <= 2) {
            Log.v("GoogleTagManager", str);
        }
    }

    public void zzb(String str, Throwable th) {
        if (this.ee <= 6) {
            Log.e("GoogleTagManager", str, th);
        }
    }

    public void zzc(String str, Throwable th) {
        if (this.ee <= 5) {
            Log.w("GoogleTagManager", str, th);
        }
    }

    public void zzdg(String str) {
        if (this.ee <= 3) {
            Log.d("GoogleTagManager", str);
        }
    }

    public void zzdh(String str) {
        if (this.ee <= 4) {
            Log.i("GoogleTagManager", str);
        }
    }

    public void zzdi(String str) {
        if (this.ee <= 5) {
            Log.w("GoogleTagManager", str);
        }
    }
}
