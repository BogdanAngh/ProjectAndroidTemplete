package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.internal.reward.client.RewardedVideoAdRequestParcel;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.zzji;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@zzji
public class zzh {
    public static final zzh zzazp;

    static {
        zzazp = new zzh();
    }

    protected zzh() {
    }

    public static zzh zzkb() {
        return zzazp;
    }

    public AdRequestParcel zza(Context context, zzad com_google_android_gms_ads_internal_client_zzad) {
        Date birthday = com_google_android_gms_ads_internal_client_zzad.getBirthday();
        long time = birthday != null ? birthday.getTime() : -1;
        String contentUrl = com_google_android_gms_ads_internal_client_zzad.getContentUrl();
        int gender = com_google_android_gms_ads_internal_client_zzad.getGender();
        Collection keywords = com_google_android_gms_ads_internal_client_zzad.getKeywords();
        List unmodifiableList = !keywords.isEmpty() ? Collections.unmodifiableList(new ArrayList(keywords)) : null;
        boolean isTestDevice = com_google_android_gms_ads_internal_client_zzad.isTestDevice(context);
        int zzld = com_google_android_gms_ads_internal_client_zzad.zzld();
        Location location = com_google_android_gms_ads_internal_client_zzad.getLocation();
        Bundle networkExtrasBundle = com_google_android_gms_ads_internal_client_zzad.getNetworkExtrasBundle(AdMobAdapter.class);
        boolean manualImpressionsEnabled = com_google_android_gms_ads_internal_client_zzad.getManualImpressionsEnabled();
        String publisherProvidedId = com_google_android_gms_ads_internal_client_zzad.getPublisherProvidedId();
        SearchAdRequest zzla = com_google_android_gms_ads_internal_client_zzad.zzla();
        SearchAdRequestParcel searchAdRequestParcel = zzla != null ? new SearchAdRequestParcel(zzla) : null;
        String str = null;
        Context applicationContext = context.getApplicationContext();
        if (applicationContext != null) {
            str = zzm.zzkr().zza(Thread.currentThread().getStackTrace(), applicationContext.getPackageName());
        }
        return new AdRequestParcel(7, time, networkExtrasBundle, gender, unmodifiableList, isTestDevice, zzld, manualImpressionsEnabled, publisherProvidedId, searchAdRequestParcel, location, contentUrl, com_google_android_gms_ads_internal_client_zzad.zzlc(), com_google_android_gms_ads_internal_client_zzad.getCustomTargeting(), Collections.unmodifiableList(new ArrayList(com_google_android_gms_ads_internal_client_zzad.zzle())), com_google_android_gms_ads_internal_client_zzad.zzkz(), str, com_google_android_gms_ads_internal_client_zzad.isDesignedForFamilies());
    }

    public RewardedVideoAdRequestParcel zza(Context context, zzad com_google_android_gms_ads_internal_client_zzad, String str) {
        return new RewardedVideoAdRequestParcel(zza(context, com_google_android_gms_ads_internal_client_zzad), str);
    }
}
