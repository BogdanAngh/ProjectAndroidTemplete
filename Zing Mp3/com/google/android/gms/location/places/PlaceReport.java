package com.google.android.gms.location.places;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.facebook.internal.AnalyticsEvents;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.ReflectedParcelable;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzz;
import com.google.android.gms.common.internal.zzz.zza;
import com.mp3download.zingmp3.C1569R;

public class PlaceReport extends AbstractSafeParcelable implements ReflectedParcelable {
    public static final Creator<PlaceReport> CREATOR;
    private final String alV;
    private final String bQ;
    private final String mTag;
    final int mVersionCode;

    static {
        CREATOR = new zzi();
    }

    PlaceReport(int i, String str, String str2, String str3) {
        this.mVersionCode = i;
        this.alV = str;
        this.mTag = str2;
        this.bQ = str3;
    }

    public static PlaceReport create(String str, String str2) {
        return zzj(str, str2, AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN);
    }

    public static PlaceReport zzj(String str, String str2, String str3) {
        zzaa.zzy(str);
        zzaa.zzib(str2);
        zzaa.zzib(str3);
        zzaa.zzb(zzla(str3), (Object) "Invalid source");
        return new PlaceReport(1, str, str2, str3);
    }

    private static boolean zzla(String str) {
        boolean z = true;
        switch (str.hashCode()) {
            case -1436706272:
                if (str.equals("inferredGeofencing")) {
                    z = true;
                    break;
                }
                break;
            case -1194968642:
                if (str.equals("userReported")) {
                    z = true;
                    break;
                }
                break;
            case -284840886:
                if (str.equals(AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN)) {
                    z = false;
                    break;
                }
                break;
            case -262743844:
                if (str.equals("inferredReverseGeocoding")) {
                    z = true;
                    break;
                }
                break;
            case 1164924125:
                if (str.equals("inferredSnappedToRoad")) {
                    z = true;
                    break;
                }
                break;
            case 1287171955:
                if (str.equals("inferredRadioSignals")) {
                    z = true;
                    break;
                }
                break;
        }
        switch (z) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return true;
            default:
                return false;
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof PlaceReport)) {
            return false;
        }
        PlaceReport placeReport = (PlaceReport) obj;
        return zzz.equal(this.alV, placeReport.alV) && zzz.equal(this.mTag, placeReport.mTag) && zzz.equal(this.bQ, placeReport.bQ);
    }

    public String getPlaceId() {
        return this.alV;
    }

    public String getSource() {
        return this.bQ;
    }

    public String getTag() {
        return this.mTag;
    }

    public int hashCode() {
        return zzz.hashCode(this.alV, this.mTag, this.bQ);
    }

    public String toString() {
        zza zzx = zzz.zzx(this);
        zzx.zzg("placeId", this.alV);
        zzx.zzg("tag", this.mTag);
        if (!AnalyticsEvents.PARAMETER_SHARE_OUTCOME_UNKNOWN.equals(this.bQ)) {
            zzx.zzg(ShareConstants.FEED_SOURCE_PARAM, this.bQ);
        }
        return zzx.toString();
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzi.zza(this, parcel, i);
    }
}
