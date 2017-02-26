package com.google.android.gms.ads.internal.request;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.os.Messenger;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

public class zzf implements Creator<AdRequestInfoParcel> {
    static void zza(AdRequestInfoParcel adRequestInfoParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, adRequestInfoParcel.versionCode);
        zzb.zza(parcel, 2, adRequestInfoParcel.zzcjt, false);
        zzb.zza(parcel, 3, adRequestInfoParcel.zzcju, i, false);
        zzb.zza(parcel, 4, adRequestInfoParcel.zzarm, i, false);
        zzb.zza(parcel, 5, adRequestInfoParcel.zzarg, false);
        zzb.zza(parcel, 6, adRequestInfoParcel.applicationInfo, i, false);
        zzb.zza(parcel, 7, adRequestInfoParcel.zzcjv, i, false);
        zzb.zza(parcel, 8, adRequestInfoParcel.zzcjw, false);
        zzb.zza(parcel, 9, adRequestInfoParcel.zzcjx, false);
        zzb.zza(parcel, 10, adRequestInfoParcel.zzcjy, false);
        zzb.zza(parcel, 11, adRequestInfoParcel.zzari, i, false);
        zzb.zza(parcel, 12, adRequestInfoParcel.zzcjz, false);
        zzb.zzc(parcel, 13, adRequestInfoParcel.zzcka);
        zzb.zzb(parcel, 14, adRequestInfoParcel.zzase, false);
        zzb.zza(parcel, 15, adRequestInfoParcel.zzckb, false);
        zzb.zza(parcel, 16, adRequestInfoParcel.zzckc);
        zzb.zza(parcel, 17, adRequestInfoParcel.zzckd, i, false);
        zzb.zzc(parcel, 18, adRequestInfoParcel.zzcke);
        zzb.zzc(parcel, 19, adRequestInfoParcel.zzckf);
        zzb.zza(parcel, 20, adRequestInfoParcel.zzavd);
        zzb.zza(parcel, 21, adRequestInfoParcel.zzckg, false);
        zzb.zza(parcel, 25, adRequestInfoParcel.zzckh);
        zzb.zza(parcel, 26, adRequestInfoParcel.zzcki, false);
        zzb.zzb(parcel, 27, adRequestInfoParcel.zzckj, false);
        zzb.zza(parcel, 28, adRequestInfoParcel.zzarf, false);
        zzb.zza(parcel, 29, adRequestInfoParcel.zzasa, i, false);
        zzb.zzb(parcel, 30, adRequestInfoParcel.zzckk, false);
        zzb.zza(parcel, 31, adRequestInfoParcel.zzckl);
        zzb.zza(parcel, 32, adRequestInfoParcel.zzckm, i, false);
        zzb.zza(parcel, 33, adRequestInfoParcel.zzckn, false);
        zzb.zza(parcel, 34, adRequestInfoParcel.zzcko);
        zzb.zzc(parcel, 35, adRequestInfoParcel.zzckp);
        zzb.zzc(parcel, 36, adRequestInfoParcel.zzckq);
        zzb.zza(parcel, 37, adRequestInfoParcel.zzckr);
        zzb.zza(parcel, 38, adRequestInfoParcel.zzcks);
        zzb.zza(parcel, 39, adRequestInfoParcel.zzckt, false);
        zzb.zza(parcel, 40, adRequestInfoParcel.zzcku);
        zzb.zza(parcel, 41, adRequestInfoParcel.zzckv, false);
        zzb.zza(parcel, 42, adRequestInfoParcel.zzbvo);
        zzb.zzc(parcel, 43, adRequestInfoParcel.zzckw);
        zzb.zza(parcel, 44, adRequestInfoParcel.zzckx, false);
        zzb.zza(parcel, 45, adRequestInfoParcel.zzcky, false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzm(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzat(i);
    }

    public AdRequestInfoParcel[] zzat(int i) {
        return new AdRequestInfoParcel[i];
    }

    public AdRequestInfoParcel zzm(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        Bundle bundle = null;
        AdRequestParcel adRequestParcel = null;
        AdSizeParcel adSizeParcel = null;
        String str = null;
        ApplicationInfo applicationInfo = null;
        PackageInfo packageInfo = null;
        String str2 = null;
        String str3 = null;
        String str4 = null;
        VersionInfoParcel versionInfoParcel = null;
        Bundle bundle2 = null;
        int i2 = 0;
        List list = null;
        Bundle bundle3 = null;
        boolean z = false;
        Messenger messenger = null;
        int i3 = 0;
        int i4 = 0;
        float f = 0.0f;
        String str5 = null;
        long j = 0;
        String str6 = null;
        List list2 = null;
        String str7 = null;
        NativeAdOptionsParcel nativeAdOptionsParcel = null;
        List list3 = null;
        long j2 = 0;
        CapabilityParcel capabilityParcel = null;
        String str8 = null;
        float f2 = 0.0f;
        boolean z2 = false;
        int i5 = 0;
        int i6 = 0;
        boolean z3 = false;
        boolean z4 = false;
        String str9 = null;
        String str10 = null;
        boolean z5 = false;
        int i7 = 0;
        Bundle bundle4 = null;
        String str11 = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    bundle = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    adRequestParcel = (AdRequestParcel) zza.zza(parcel, zzcq, AdRequestParcel.CREATOR);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    adSizeParcel = (AdSizeParcel) zza.zza(parcel, zzcq, AdSizeParcel.CREATOR);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    applicationInfo = (ApplicationInfo) zza.zza(parcel, zzcq, ApplicationInfo.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    packageInfo = (PackageInfo) zza.zza(parcel, zzcq, PackageInfo.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    versionInfoParcel = (VersionInfoParcel) zza.zza(parcel, zzcq, VersionInfoParcel.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    bundle2 = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    list = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    bundle3 = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                    messenger = (Messenger) zza.zza(parcel, zzcq, Messenger.CREATOR);
                    break;
                case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargins /*19*/:
                    i4 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_maxButtonHeight /*20*/:
                    f = zza.zzl(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_buttonGravity /*21*/:
                    str5 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_navigationContentDescription /*25*/:
                    j = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_logoDescription /*26*/:
                    str6 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleTextColor /*27*/:
                    list2 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextColor /*28*/:
                    str7 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeBackground /*29*/:
                    nativeAdOptionsParcel = (NativeAdOptionsParcel) zza.zza(parcel, zzcq, NativeAdOptionsParcel.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeSplitBackground /*30*/:
                    list3 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCloseDrawable /*31*/:
                    j2 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    capabilityParcel = (CapabilityParcel) zza.zza(parcel, zzcq, CapabilityParcel.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCopyDrawable /*33*/:
                    str8 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    f2 = zza.zzl(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeSelectAllDrawable /*35*/:
                    i5 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeShareDrawable /*36*/:
                    i6 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeFindDrawable /*37*/:
                    z3 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    z4 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    str9 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_textAppearanceLargePopupMenu /*40*/:
                    z2 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu /*41*/:
                    str10 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
                    z5 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_dialogTheme /*43*/:
                    i7 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_dialogPreferredPadding /*44*/:
                    bundle4 = zza.zzs(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    str11 = zza.zzq(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new AdRequestInfoParcel(i, bundle, adRequestParcel, adSizeParcel, str, applicationInfo, packageInfo, str2, str3, str4, versionInfoParcel, bundle2, i2, list, bundle3, z, messenger, i3, i4, f, str5, j, str6, list2, str7, nativeAdOptionsParcel, list3, j2, capabilityParcel, str8, f2, z2, i5, i6, z3, z4, str9, str10, z5, i7, bundle4, str11);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }
}
