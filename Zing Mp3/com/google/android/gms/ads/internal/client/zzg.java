package com.google.android.gms.ads.internal.client;

import android.location.Location;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

public class zzg implements Creator<AdRequestParcel> {
    static void zza(AdRequestParcel adRequestParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, adRequestParcel.versionCode);
        zzb.zza(parcel, 2, adRequestParcel.zzayl);
        zzb.zza(parcel, 3, adRequestParcel.extras, false);
        zzb.zzc(parcel, 4, adRequestParcel.zzaym);
        zzb.zzb(parcel, 5, adRequestParcel.zzayn, false);
        zzb.zza(parcel, 6, adRequestParcel.zzayo);
        zzb.zzc(parcel, 7, adRequestParcel.zzayp);
        zzb.zza(parcel, 8, adRequestParcel.zzayq);
        zzb.zza(parcel, 9, adRequestParcel.zzayr, false);
        zzb.zza(parcel, 10, adRequestParcel.zzays, i, false);
        zzb.zza(parcel, 11, adRequestParcel.zzayt, i, false);
        zzb.zza(parcel, 12, adRequestParcel.zzayu, false);
        zzb.zza(parcel, 13, adRequestParcel.zzayv, false);
        zzb.zza(parcel, 14, adRequestParcel.zzayw, false);
        zzb.zzb(parcel, 15, adRequestParcel.zzayx, false);
        zzb.zza(parcel, 16, adRequestParcel.zzayy, false);
        zzb.zza(parcel, 17, adRequestParcel.zzayz, false);
        zzb.zza(parcel, 18, adRequestParcel.zzaza);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zze(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzv(i);
    }

    public AdRequestParcel zze(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        long j = 0;
        Bundle bundle = null;
        int i2 = 0;
        List list = null;
        boolean z = false;
        int i3 = 0;
        boolean z2 = false;
        String str = null;
        SearchAdRequestParcel searchAdRequestParcel = null;
        Location location = null;
        String str2 = null;
        Bundle bundle2 = null;
        Bundle bundle3 = null;
        List list2 = null;
        String str3 = null;
        String str4 = null;
        boolean z3 = false;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    j = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    bundle = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    list = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    z2 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    searchAdRequestParcel = (SearchAdRequestParcel) zza.zza(parcel, zzcq, SearchAdRequestParcel.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    location = (Location) zza.zza(parcel, zzcq, Location.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    bundle2 = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    bundle3 = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    list2 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                    z3 = zza.zzc(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new AdRequestParcel(i, j, bundle, i2, list, z, i3, z2, str, searchAdRequestParcel, location, str2, bundle2, bundle3, list2, str3, str4, z3);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public AdRequestParcel[] zzv(int i) {
        return new AdRequestParcel[i];
    }
}
