package com.google.android.gms.ads.mediation;

import android.os.Bundle;

public interface MediationAdapter {

    public static class zza {
        private int f1508P;

        public zza zzbk(int i) {
            this.f1508P = i;
            return this;
        }

        public Bundle zzys() {
            Bundle bundle = new Bundle();
            bundle.putInt("capabilities", this.f1508P);
            return bundle;
        }
    }

    void onDestroy();

    void onPause();

    void onResume();
}
