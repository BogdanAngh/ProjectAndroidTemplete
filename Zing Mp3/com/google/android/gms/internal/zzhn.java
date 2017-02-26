package com.google.android.gms.internal;

import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdRequest.Gender;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.zza;
import com.mp3download.zingmp3.C1569R;
import java.util.Date;
import java.util.HashSet;

@zzji
public final class zzhn {

    /* renamed from: com.google.android.gms.internal.zzhn.1 */
    static /* synthetic */ class C13731 {
        static final /* synthetic */ int[] zzbxr;
        static final /* synthetic */ int[] zzbxs;

        static {
            zzbxs = new int[ErrorCode.values().length];
            try {
                zzbxs[ErrorCode.INTERNAL_ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                zzbxs[ErrorCode.INVALID_REQUEST.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                zzbxs[ErrorCode.NETWORK_ERROR.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                zzbxs[ErrorCode.NO_FILL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            zzbxr = new int[Gender.values().length];
            try {
                zzbxr[Gender.FEMALE.ordinal()] = 1;
            } catch (NoSuchFieldError e5) {
            }
            try {
                zzbxr[Gender.MALE.ordinal()] = 2;
            } catch (NoSuchFieldError e6) {
            }
            try {
                zzbxr[Gender.UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError e7) {
            }
        }
    }

    public static int zza(ErrorCode errorCode) {
        switch (C13731.zzbxs[errorCode.ordinal()]) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return 1;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return 2;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return 3;
            default:
                return 0;
        }
    }

    public static Gender zzag(int i) {
        switch (i) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return Gender.MALE;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return Gender.FEMALE;
            default:
                return Gender.UNKNOWN;
        }
    }

    public static AdSize zzc(AdSizeParcel adSizeParcel) {
        int i = 0;
        AdSize[] adSizeArr = new AdSize[]{AdSize.SMART_BANNER, AdSize.BANNER, AdSize.IAB_MRECT, AdSize.IAB_BANNER, AdSize.IAB_LEADERBOARD, AdSize.IAB_WIDE_SKYSCRAPER};
        while (i < 6) {
            if (adSizeArr[i].getWidth() == adSizeParcel.width && adSizeArr[i].getHeight() == adSizeParcel.height) {
                return adSizeArr[i];
            }
            i++;
        }
        return new AdSize(zza.zza(adSizeParcel.width, adSizeParcel.height, adSizeParcel.zzazq));
    }

    public static MediationAdRequest zzs(AdRequestParcel adRequestParcel) {
        return new MediationAdRequest(new Date(adRequestParcel.zzayl), zzag(adRequestParcel.zzaym), adRequestParcel.zzayn != null ? new HashSet(adRequestParcel.zzayn) : null, adRequestParcel.zzayo, adRequestParcel.zzayt);
    }
}
