package com.google.android.gms.internal;

import android.os.Parcel;
import android.util.Base64;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.mp3download.zingmp3.BuildConfig;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

@zzji
class zzgd {
    final String zzant;
    final AdRequestParcel zzapj;
    final int zzbsq;

    zzgd(AdRequestParcel adRequestParcel, String str, int i) {
        this.zzapj = adRequestParcel;
        this.zzant = str;
        this.zzbsq = i;
    }

    zzgd(zzgb com_google_android_gms_internal_zzgb) {
        this(com_google_android_gms_internal_zzgb.zzno(), com_google_android_gms_internal_zzgb.getAdUnitId(), com_google_android_gms_internal_zzgb.getNetworkType());
    }

    static zzgd zzbn(String str) throws IOException {
        String[] split = str.split("\u0000");
        if (split.length != 3) {
            throw new IOException("Incorrect field count for QueueSeed.");
        }
        Parcel obtain = Parcel.obtain();
        try {
            String str2 = new String(Base64.decode(split[0], 0), C0989C.UTF8_NAME);
            int parseInt = Integer.parseInt(split[1]);
            byte[] decode = Base64.decode(split[2], 0);
            obtain.unmarshall(decode, 0, decode.length);
            obtain.setDataPosition(0);
            zzgd com_google_android_gms_internal_zzgd = new zzgd((AdRequestParcel) AdRequestParcel.CREATOR.createFromParcel(obtain), str2, parseInt);
            obtain.recycle();
            return com_google_android_gms_internal_zzgd;
        } catch (Throwable th) {
            obtain.recycle();
        }
    }

    String zznv() {
        Parcel obtain = Parcel.obtain();
        String encodeToString;
        try {
            encodeToString = Base64.encodeToString(this.zzant.getBytes(C0989C.UTF8_NAME), 0);
            String num = Integer.toString(this.zzbsq);
            this.zzapj.writeToParcel(obtain, 0);
            String encodeToString2 = Base64.encodeToString(obtain.marshall(), 0);
            encodeToString = new StringBuilder(((String.valueOf(encodeToString).length() + 2) + String.valueOf(num).length()) + String.valueOf(encodeToString2).length()).append(encodeToString).append("\u0000").append(num).append("\u0000").append(encodeToString2).toString();
            return encodeToString;
        } catch (UnsupportedEncodingException e) {
            encodeToString = "QueueSeed encode failed because UTF-8 is not available.";
            zzb.m1695e(encodeToString);
            return BuildConfig.FLAVOR;
        } finally {
            obtain.recycle();
        }
    }
}
