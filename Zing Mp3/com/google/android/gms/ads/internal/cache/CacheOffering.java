package com.google.android.gms.ads.internal.cache;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.support.annotation.Nullable;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.internal.zzji;
import com.mp3download.zingmp3.BuildConfig;
import java.util.List;

@zzji
public class CacheOffering extends AbstractSafeParcelable {
    public static final Creator<CacheOffering> CREATOR;
    @Nullable
    public final String url;
    public final int version;
    public final long zzayd;
    public final String zzaye;
    public final String zzayf;
    public final String zzayg;
    public final Bundle zzayh;
    public final boolean zzayi;

    static {
        CREATOR = new zzd();
    }

    CacheOffering(int i, @Nullable String str, long j, String str2, String str3, String str4, Bundle bundle, boolean z) {
        this.version = i;
        this.url = str;
        this.zzayd = j;
        if (str2 == null) {
            str2 = BuildConfig.FLAVOR;
        }
        this.zzaye = str2;
        if (str3 == null) {
            str3 = BuildConfig.FLAVOR;
        }
        this.zzayf = str3;
        if (str4 == null) {
            str4 = BuildConfig.FLAVOR;
        }
        this.zzayg = str4;
        if (bundle == null) {
            bundle = new Bundle();
        }
        this.zzayh = bundle;
        this.zzayi = z;
    }

    @Nullable
    public static CacheOffering zzak(String str) {
        return zze(Uri.parse(str));
    }

    @Nullable
    public static CacheOffering zze(Uri uri) {
        Throwable e;
        try {
            if (!"gcache".equals(uri.getScheme())) {
                return null;
            }
            List pathSegments = uri.getPathSegments();
            if (pathSegments.size() != 2) {
                zzb.zzdi("Expected 2 path parts for namespace and id, found :" + pathSegments.size());
                return null;
            }
            String str = (String) pathSegments.get(0);
            String str2 = (String) pathSegments.get(1);
            String host = uri.getHost();
            String queryParameter = uri.getQueryParameter(NativeProtocol.WEB_DIALOG_URL);
            boolean equals = AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(uri.getQueryParameter("read_only"));
            String queryParameter2 = uri.getQueryParameter("expiration");
            long parseLong = queryParameter2 == null ? 0 : Long.parseLong(queryParameter2);
            Bundle bundle = new Bundle();
            for (String queryParameter22 : zzu.zzgo().zzh(uri)) {
                if (queryParameter22.startsWith("tag.")) {
                    bundle.putString(queryParameter22.substring("tag.".length()), uri.getQueryParameter(queryParameter22));
                }
            }
            return new CacheOffering(1, queryParameter, parseLong, host, str, str2, bundle, equals);
        } catch (NullPointerException e2) {
            e = e2;
            zzb.zzc("Unable to parse Uri into cache offering.", e);
            return null;
        } catch (NumberFormatException e3) {
            e = e3;
            zzb.zzc("Unable to parse Uri into cache offering.", e);
            return null;
        }
    }

    public void writeToParcel(Parcel parcel, int i) {
        zzd.zza(this, parcel, i);
    }
}
