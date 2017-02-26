package com.google.android.gms.ads.internal.overlay;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.InterstitialAdParameterParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;

public class zzf implements Creator<AdOverlayInfoParcel> {
    static void zza(AdOverlayInfoParcel adOverlayInfoParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, adOverlayInfoParcel.versionCode);
        zzb.zza(parcel, 2, adOverlayInfoParcel.zzcbj, i, false);
        zzb.zza(parcel, 3, adOverlayInfoParcel.zzpv(), false);
        zzb.zza(parcel, 4, adOverlayInfoParcel.zzpw(), false);
        zzb.zza(parcel, 5, adOverlayInfoParcel.zzpx(), false);
        zzb.zza(parcel, 6, adOverlayInfoParcel.zzpy(), false);
        zzb.zza(parcel, 7, adOverlayInfoParcel.zzcbo, false);
        zzb.zza(parcel, 8, adOverlayInfoParcel.zzcbp);
        zzb.zza(parcel, 9, adOverlayInfoParcel.zzcbq, false);
        zzb.zza(parcel, 10, adOverlayInfoParcel.zzqa(), false);
        zzb.zzc(parcel, 11, adOverlayInfoParcel.orientation);
        zzb.zzc(parcel, 12, adOverlayInfoParcel.zzcbs);
        zzb.zza(parcel, 13, adOverlayInfoParcel.url, false);
        zzb.zza(parcel, 14, adOverlayInfoParcel.zzari, i, false);
        zzb.zza(parcel, 15, adOverlayInfoParcel.zzpz(), false);
        zzb.zza(parcel, 16, adOverlayInfoParcel.zzcbu, false);
        zzb.zza(parcel, 17, adOverlayInfoParcel.zzcbv, i, false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzk(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzal(i);
    }

    public AdOverlayInfoParcel[] zzal(int i) {
        return new AdOverlayInfoParcel[i];
    }

    public AdOverlayInfoParcel zzk(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        AdLauncherIntentInfoParcel adLauncherIntentInfoParcel = null;
        IBinder iBinder = null;
        IBinder iBinder2 = null;
        IBinder iBinder3 = null;
        IBinder iBinder4 = null;
        String str = null;
        boolean z = false;
        String str2 = null;
        IBinder iBinder5 = null;
        int i2 = 0;
        int i3 = 0;
        String str3 = null;
        VersionInfoParcel versionInfoParcel = null;
        IBinder iBinder6 = null;
        String str4 = null;
        InterstitialAdParameterParcel interstitialAdParameterParcel = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    adLauncherIntentInfoParcel = (AdLauncherIntentInfoParcel) zza.zza(parcel, zzcq, AdLauncherIntentInfoParcel.CREATOR);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    iBinder = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    iBinder2 = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    iBinder3 = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    iBinder4 = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    iBinder5 = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    versionInfoParcel = (VersionInfoParcel) zza.zza(parcel, zzcq, VersionInfoParcel.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    iBinder6 = zza.zzr(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                    interstitialAdParameterParcel = (InterstitialAdParameterParcel) zza.zza(parcel, zzcq, InterstitialAdParameterParcel.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new AdOverlayInfoParcel(i, adLauncherIntentInfoParcel, iBinder, iBinder2, iBinder3, iBinder4, str, z, str2, iBinder5, i2, i3, str3, versionInfoParcel, iBinder6, str4, interstitialAdParameterParcel);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }
}
