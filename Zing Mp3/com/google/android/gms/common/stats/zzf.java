package com.google.android.gms.common.stats;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.mp3download.zingmp3.C1569R;
import java.util.List;

public class zzf implements Creator<WakeLockEvent> {
    static void zza(WakeLockEvent wakeLockEvent, Parcel parcel, int i) {
        int zzcs = zzb.zzcs(parcel);
        zzb.zzc(parcel, 1, wakeLockEvent.mVersionCode);
        zzb.zza(parcel, 2, wakeLockEvent.getTimeMillis());
        zzb.zza(parcel, 4, wakeLockEvent.zzaxv(), false);
        zzb.zzc(parcel, 5, wakeLockEvent.zzaxy());
        zzb.zzb(parcel, 6, wakeLockEvent.zzaxz(), false);
        zzb.zza(parcel, 8, wakeLockEvent.zzayb());
        zzb.zza(parcel, 10, wakeLockEvent.zzaxw(), false);
        zzb.zzc(parcel, 11, wakeLockEvent.getEventType());
        zzb.zza(parcel, 12, wakeLockEvent.zzaya(), false);
        zzb.zza(parcel, 13, wakeLockEvent.zzayd(), false);
        zzb.zzc(parcel, 14, wakeLockEvent.zzayc());
        zzb.zza(parcel, 15, wakeLockEvent.zzaye());
        zzb.zza(parcel, 16, wakeLockEvent.zzayf());
        zzb.zza(parcel, 17, wakeLockEvent.zzaxx(), false);
        zzb.zzaj(parcel, zzcs);
    }

    public /* synthetic */ Object createFromParcel(Parcel parcel) {
        return zzdc(parcel);
    }

    public /* synthetic */ Object[] newArray(int i) {
        return zzhf(i);
    }

    public WakeLockEvent zzdc(Parcel parcel) {
        int zzcr = zza.zzcr(parcel);
        int i = 0;
        long j = 0;
        int i2 = 0;
        String str = null;
        int i3 = 0;
        List list = null;
        String str2 = null;
        long j2 = 0;
        int i4 = 0;
        String str3 = null;
        String str4 = null;
        float f = 0.0f;
        long j3 = 0;
        String str5 = null;
        while (parcel.dataPosition() < zzcr) {
            int zzcq = zza.zzcq(parcel);
            switch (zza.zzgu(zzcq)) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    i = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    j = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    str = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    i3 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    list = zza.zzae(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                    j2 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                    str3 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_popupTheme /*11*/:
                    i2 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleTextAppearance /*12*/:
                    str2 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_subtitleTextAppearance /*13*/:
                    str4 = zza.zzq(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMargin /*14*/:
                    i4 = zza.zzg(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginStart /*15*/:
                    f = zza.zzl(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                    j3 = zza.zzi(parcel, zzcq);
                    break;
                case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                    str5 = zza.zzq(parcel, zzcq);
                    break;
                default:
                    zza.zzb(parcel, zzcq);
                    break;
            }
        }
        if (parcel.dataPosition() == zzcr) {
            return new WakeLockEvent(i, j, i2, str, i3, list, str2, j2, i4, str3, str4, f, j3, str5);
        }
        throw new zza.zza("Overread allowed size end=" + zzcr, parcel);
    }

    public WakeLockEvent[] zzhf(int i) {
        return new WakeLockEvent[i];
    }
}
