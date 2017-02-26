package com.google.android.gms.analytics.internal;

public abstract class zzd extends zzc {
    private boolean cR;

    protected zzd(zzf com_google_android_gms_analytics_internal_zzf) {
        super(com_google_android_gms_analytics_internal_zzf);
    }

    public void initialize() {
        zzzy();
        this.cR = true;
    }

    public boolean isInitialized() {
        return this.cR;
    }

    protected void zzacj() {
        if (!isInitialized()) {
            throw new IllegalStateException("Not initialized");
        }
    }

    protected abstract void zzzy();
}
