package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import android.text.TextUtils;

@zzji
public class zzdu {
    @Nullable
    public zzdt zza(@Nullable zzds com_google_android_gms_internal_zzds) {
        if (com_google_android_gms_internal_zzds == null) {
            throw new IllegalArgumentException("CSI configuration can't be null. ");
        } else if (!com_google_android_gms_internal_zzds.zzls()) {
            zzkx.m1697v("CsiReporterFactory: CSI is not enabled. No CSI reporter created.");
            return null;
        } else if (com_google_android_gms_internal_zzds.getContext() == null) {
            throw new IllegalArgumentException("Context can't be null. Please set up context in CsiConfiguration.");
        } else if (!TextUtils.isEmpty(com_google_android_gms_internal_zzds.zzhz())) {
            return new zzdt(com_google_android_gms_internal_zzds.getContext(), com_google_android_gms_internal_zzds.zzhz(), com_google_android_gms_internal_zzds.zzlt(), com_google_android_gms_internal_zzds.zzlu());
        } else {
            throw new IllegalArgumentException("AfmaVersion can't be null or empty. Please set up afmaVersion in CsiConfiguration.");
        }
    }
}
