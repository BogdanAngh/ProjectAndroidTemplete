package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.common.util.zzf;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;

@zzji
public class zzfk implements zzfe {
    static final Map<String, Integer> zzbqv;
    private final zze zzbqt;
    private final zzhq zzbqu;

    static {
        zzbqv = zzf.zza("resize", Integer.valueOf(1), "playVideo", Integer.valueOf(2), "storePicture", Integer.valueOf(3), "createCalendarEvent", Integer.valueOf(4), "setOrientationProperties", Integer.valueOf(5), "closeResizedAd", Integer.valueOf(6));
    }

    public zzfk(zze com_google_android_gms_ads_internal_zze, zzhq com_google_android_gms_internal_zzhq) {
        this.zzbqt = com_google_android_gms_ads_internal_zze;
        this.zzbqu = com_google_android_gms_internal_zzhq;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        int intValue = ((Integer) zzbqv.get((String) map.get("a"))).intValue();
        if (intValue == 5 || this.zzbqt == null || this.zzbqt.zzfe()) {
            switch (intValue) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    this.zzbqu.execute(map);
                    return;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                    new zzhs(com_google_android_gms_internal_zzmd, map).execute();
                    return;
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    new zzhp(com_google_android_gms_internal_zzmd, map).execute();
                    return;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    new zzhr(com_google_android_gms_internal_zzmd, map).execute();
                    return;
                case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                    this.zzbqu.zzt(true);
                    return;
                default:
                    zzb.zzdh("Unknown MRAID command called.");
                    return;
            }
        }
        this.zzbqt.zzy(null);
    }
}
