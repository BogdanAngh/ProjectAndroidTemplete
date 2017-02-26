package com.google.android.gms.ads.internal.request;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import com.google.android.gms.ads.internal.safebrowsing.SafeBrowsingConfigParcel;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

public class zzh implements Creator<AdResponseParcel> {
    static void zza(AdResponseParcel adResponseParcel, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, adResponseParcel.versionCode);
        zzb.zza(parcel, 2, adResponseParcel.zzcbo, false);
        zzb.zza(parcel, 3, adResponseParcel.body, false);
        zzb.zzb(parcel, 4, adResponseParcel.zzbvk, false);
        zzb.zzc(parcel, 5, adResponseParcel.errorCode);
        zzb.zzb(parcel, 6, adResponseParcel.zzbvl, false);
        zzb.zza(parcel, 7, adResponseParcel.zzcla);
        zzb.zza(parcel, 8, adResponseParcel.zzclb);
        zzb.zza(parcel, 9, adResponseParcel.zzclc);
        zzb.zzb(parcel, 10, adResponseParcel.zzcld, false);
        zzb.zza(parcel, 11, adResponseParcel.zzbvq);
        zzb.zzc(parcel, 12, adResponseParcel.orientation);
        zzb.zza(parcel, 13, adResponseParcel.zzcle, false);
        zzb.zza(parcel, 14, adResponseParcel.zzclf);
        zzb.zza(parcel, 15, adResponseParcel.zzclg, false);
        zzb.zza(parcel, 18, adResponseParcel.zzclh);
        zzb.zza(parcel, 19, adResponseParcel.zzcli, false);
        zzb.zza(parcel, 21, adResponseParcel.zzclj, false);
        zzb.zza(parcel, 22, adResponseParcel.zzclk);
        zzb.zza(parcel, 23, adResponseParcel.zzazt);
        zzb.zza(parcel, 24, adResponseParcel.zzckc);
        zzb.zza(parcel, 25, adResponseParcel.zzcll);
        zzb.zza(parcel, 26, adResponseParcel.zzclm);
        zzb.zza(parcel, 28, adResponseParcel.zzcln, i, false);
        zzb.zza(parcel, 29, adResponseParcel.zzclo, false);
        zzb.zza(parcel, 30, adResponseParcel.zzclp, false);
        zzb.zza(parcel, 31, adResponseParcel.zzazu);
        zzb.zza(parcel, 32, adResponseParcel.zzazv);
        zzb.zza(parcel, 33, adResponseParcel.zzclq, i, false);
        zzb.zzb(parcel, 34, adResponseParcel.zzclr, false);
        zzb.zzb(parcel, 35, adResponseParcel.zzcls, false);
        zzb.zza(parcel, 36, adResponseParcel.zzclt);
        zzb.zza(parcel, 37, adResponseParcel.zzclu, i, false);
        zzb.zza(parcel, 38, adResponseParcel.zzcks);
        zzb.zza(parcel, 39, adResponseParcel.zzckt, false);
        zzb.zzb(parcel, 40, adResponseParcel.zzbvn, false);
        zzb.zza(parcel, 42, adResponseParcel.zzbvo);
        zzb.zza(parcel, 43, adResponseParcel.zzclv, false);
        zzb.zza(parcel, 44, adResponseParcel.zzclw, i, false);
        zzb.zza(parcel, 45, adResponseParcel.zzclx, false);
        zzb.zza(parcel, 46, adResponseParcel.zzcly);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzn(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzau(i);
    }

    public AdResponseParcel[] zzau(int i) {
        return new AdResponseParcel[i];
    }

    public AdResponseParcel zzn(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        List list = null;
        int i2 = 0;
        List list2 = null;
        long j = 0;
        boolean z = false;
        long j2 = 0;
        List list3 = null;
        long j3 = 0;
        int i3 = 0;
        String str3 = null;
        long j4 = 0;
        String str4 = null;
        boolean z2 = false;
        String str5 = null;
        String str6 = null;
        boolean z3 = false;
        boolean z4 = false;
        boolean z5 = false;
        boolean z6 = false;
        boolean z7 = false;
        LargeParcelTeleporter largeParcelTeleporter = null;
        String str7 = null;
        String str8 = null;
        boolean z8 = false;
        boolean z9 = false;
        RewardItemParcel rewardItemParcel = null;
        List list4 = null;
        List list5 = null;
        boolean z10 = false;
        AutoClickProtectionConfigurationParcel autoClickProtectionConfigurationParcel = null;
        boolean z11 = false;
        String str9 = null;
        List list6 = null;
        boolean z12 = false;
        String str10 = null;
        SafeBrowsingConfigParcel safeBrowsingConfigParcel = null;
        String str11 = null;
        boolean z13 = false;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    list = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    list2 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                    j = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    z = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                    j2 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    list3 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    j3 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    j4 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                    z2 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargins /*19*/:
                    str5 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_buttonGravity /*21*/:
                    str6 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_collapseIcon /*22*/:
                    z3 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_collapseContentDescription /*23*/:
                    z4 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_navigationIcon /*24*/:
                    z5 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_navigationContentDescription /*25*/:
                    z6 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_logoDescription /*26*/:
                    z7 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextColor /*28*/:
                    largeParcelTeleporter = (LargeParcelTeleporter) zza.zza(parcel, zzcq, LargeParcelTeleporter.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeBackground /*29*/:
                    str7 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeSplitBackground /*30*/:
                    str8 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCloseDrawable /*31*/:
                    z8 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCutDrawable /*32*/:
                    z9 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeCopyDrawable /*33*/:
                    rewardItemParcel = (RewardItemParcel) zza.zza(parcel, zzcq, RewardItemParcel.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModePasteDrawable /*34*/:
                    list4 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeSelectAllDrawable /*35*/:
                    list5 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeShareDrawable /*36*/:
                    z10 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeFindDrawable /*37*/:
                    autoClickProtectionConfigurationParcel = (AutoClickProtectionConfigurationParcel) zza.zza(parcel, zzcq, AutoClickProtectionConfigurationParcel.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModeWebSearchDrawable /*38*/:
                    z11 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionModePopupWindowStyle /*39*/:
                    str9 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_textAppearanceLargePopupMenu /*40*/:
                    list6 = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
                    z12 = zza.zzc(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_dialogTheme /*43*/:
                    str10 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_dialogPreferredPadding /*44*/:
                    safeBrowsingConfigParcel = (SafeBrowsingConfigParcel) zza.zza(parcel, zzcq, SafeBrowsingConfigParcel.CREATOR);
                    break;
                case C1569R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
                    str11 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.AppCompatTheme_actionDropDownStyle /*46*/:
                    z13 = zza.zzc(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new AdResponseParcel(i, str, str2, list, i2, list2, j, z, j2, list3, j3, i3, str3, j4, str4, z2, str5, str6, z3, z4, z5, z6, z7, largeParcelTeleporter, str7, str8, z8, z9, rewardItemParcel, list4, list5, z10, autoClickProtectionConfigurationParcel, z11, str9, list6, z12, str10, safeBrowsingConfigParcel, str11, z13);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }
}
