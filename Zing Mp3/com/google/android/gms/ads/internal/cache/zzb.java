package com.google.android.gms.ads.internal.cache;

import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.mp3download.zingmp3.C1569R;

public class zzb implements Creator<CacheEntryParcel> {
    static void zza(CacheEntryParcel cacheEntryParcel, Parcel parcel, int i) {
        int zzcs = com.google.android.gms.common.internal.safeparcel.zzb.zzcs(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, cacheEntryParcel.version);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, cacheEntryParcel.zzjw(), i, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzc(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzt(i);
    }

    public CacheEntryParcel zzc(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        ParcelFileDescriptor parcelFileDescriptor = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    parcelFileDescriptor = (ParcelFileDescriptor) zza.zza(parcel, zzcq, ParcelFileDescriptor.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new CacheEntryParcel(i, parcelFileDescriptor);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public CacheEntryParcel[] zzt(int i) {
        return new CacheEntryParcel[i];
    }
}
