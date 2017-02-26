package com.google.android.gms.ads.internal.client;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;

public class zzao implements Creator<SearchAdRequestParcel> {
    static void zza(SearchAdRequestParcel searchAdRequestParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, searchAdRequestParcel.versionCode);
        zzb.zzc(parcel, 2, searchAdRequestParcel.zzbbx);
        zzb.zzc(parcel, 3, searchAdRequestParcel.backgroundColor);
        zzb.zzc(parcel, 4, searchAdRequestParcel.zzbby);
        zzb.zzc(parcel, 5, searchAdRequestParcel.zzbbz);
        zzb.zzc(parcel, 6, searchAdRequestParcel.zzbca);
        zzb.zzc(parcel, 7, searchAdRequestParcel.zzbcb);
        zzb.zzc(parcel, 8, searchAdRequestParcel.zzbcc);
        zzb.zzc(parcel, 9, searchAdRequestParcel.zzbcd);
        zzb.zza(parcel, 10, searchAdRequestParcel.zzbce, false);
        zzb.zzc(parcel, 11, searchAdRequestParcel.zzbcf);
        zzb.zza(parcel, 12, searchAdRequestParcel.zzbcg, false);
        zzb.zzc(parcel, 13, searchAdRequestParcel.zzbch);
        zzb.zzc(parcel, 14, searchAdRequestParcel.zzbci);
        zzb.zza(parcel, 15, searchAdRequestParcel.zzbcj, false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzg(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzz(i);
    }

    public SearchAdRequestParcel zzg(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        int i9 = 0;
        String str = null;
        int i10 = 0;
        String str2 = null;
        int i11 = 0;
        int i12 = 0;
        String str3 = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    i4 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    i5 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    i6 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    i7 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    i8 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                    i9 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    i10 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    i11 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    i12 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new SearchAdRequestParcel(i, i2, i3, i4, i5, i6, i7, i8, i9, str, i10, str2, i11, i12, str3);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public SearchAdRequestParcel[] zzz(int i) {
        return new SearchAdRequestParcel[i];
    }
}
