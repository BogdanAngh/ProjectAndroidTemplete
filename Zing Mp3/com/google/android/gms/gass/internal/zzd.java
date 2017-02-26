package com.google.android.gms.gass.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;

public class zzd implements Creator<GassResponseParcel> {
    static void zza(GassResponseParcel gassResponseParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, gassResponseParcel.versionCode);
        zzb.zza(parcel, 2, gassResponseParcel.zzbnn(), false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzmy(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zztk(i);
    }

    public GassResponseParcel zzmy(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        byte[] bArr = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    bArr = zza.zzt(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new GassResponseParcel(i, bArr);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public GassResponseParcel[] zztk(int i) {
        return new GassResponseParcel[i];
    }
}
