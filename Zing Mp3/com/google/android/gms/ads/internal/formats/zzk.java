package com.google.android.gms.ads.internal.formats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.client.VideoOptionsParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;

public class zzk implements Creator<NativeAdOptionsParcel> {
    static void zza(NativeAdOptionsParcel nativeAdOptionsParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, nativeAdOptionsParcel.versionCode);
        zzb.zza(parcel, 2, nativeAdOptionsParcel.zzboj);
        zzb.zzc(parcel, 3, nativeAdOptionsParcel.zzbok);
        zzb.zza(parcel, 4, nativeAdOptionsParcel.zzbol);
        zzb.zzc(parcel, 5, nativeAdOptionsParcel.zzbom);
        zzb.zza(parcel, 6, nativeAdOptionsParcel.zzbon, i, false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzi(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzac(i);
    }

    public NativeAdOptionsParcel[] zzac(int i) {
        return new NativeAdOptionsParcel[i];
    }

    public NativeAdOptionsParcel zzi(Parcel parcel) {
        int i = 0;
        int zzcr = zza.zzcr(parcel);
        VideoOptionsParcel videoOptionsParcel = null;
        boolean z = false;
        int i2 = 0;
        boolean z2 = false;
        int i3 = 0;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    z2 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    videoOptionsParcel = (VideoOptionsParcel) zza.zza(parcel, zzcq, VideoOptionsParcel.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new NativeAdOptionsParcel(i3, z2, i2, z, i, videoOptionsParcel);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }
}
