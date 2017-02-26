package com.google.android.gms.ads.internal.cache;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;

public class zzd implements Creator<CacheOffering> {
    static void zza(CacheOffering cacheOffering, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, cacheOffering.version);
        zzb.zza(parcel, 2, cacheOffering.url, false);
        zzb.zza(parcel, 3, cacheOffering.zzayd);
        zzb.zza(parcel, 4, cacheOffering.zzaye, false);
        zzb.zza(parcel, 5, cacheOffering.zzayf, false);
        zzb.zza(parcel, 6, cacheOffering.zzayg, false);
        zzb.zza(parcel, 7, cacheOffering.zzayh, false);
        zzb.zza(parcel, 8, cacheOffering.zzayi);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzd(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzu(i);
    }

    public CacheOffering zzd(Parcel parcel) {
        boolean z = false;
        Bundle bundle = null;
        int zzcr = zza.zzcr(parcel);
        long j = 0;
        String str = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        int i = 0;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    j = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    bundle = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new CacheOffering(i, str4, j, str3, str2, str, bundle, z);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public CacheOffering[] zzu(int i) {
        return new CacheOffering[i];
    }
}
