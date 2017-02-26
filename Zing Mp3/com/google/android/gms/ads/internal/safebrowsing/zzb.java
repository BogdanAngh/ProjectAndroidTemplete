package com.google.android.gms.ads.internal.safebrowsing;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

public class zzb implements Creator<SafeBrowsingConfigParcel> {
    static void zza(SafeBrowsingConfigParcel safeBrowsingConfigParcel, Parcel parcel, int i) {
        int zzcs = com.google.android.gms.common.internal.safeparcel.zzb.zzcs(parcel);
        com.google.android.gms.common.internal.safeparcel.zzb.zzc(parcel, 1, safeBrowsingConfigParcel.versionCode);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 2, safeBrowsingConfigParcel.zzcsd, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 3, safeBrowsingConfigParcel.zzcse, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 4, safeBrowsingConfigParcel.zzcsf);
        com.google.android.gms.common.internal.safeparcel.zzb.zza(parcel, 5, safeBrowsingConfigParcel.zzcsg);
        com.google.android.gms.common.internal.safeparcel.zzb.zzb(parcel, 6, safeBrowsingConfigParcel.zzcsh, false);
        com.google.android.gms.common.internal.safeparcel.zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzu(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzbe(i);
    }

    public SafeBrowsingConfigParcel[] zzbe(int i) {
        return new SafeBrowsingConfigParcel[i];
    }

    public SafeBrowsingConfigParcel zzu(Parcel parcel) {
        List list = null;
        boolean z = false;
        int zzcr = zza.zzcr(parcel);
        boolean z2 = false;
        String str = null;
        String str2 = null;
        int i = 0;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    z2 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    list = zza.zzae(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new SafeBrowsingConfigParcel(i, str2, str, z2, z, list);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }
}
