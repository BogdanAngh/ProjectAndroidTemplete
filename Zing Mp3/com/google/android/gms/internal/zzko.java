package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdRequestInfoParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.request.AutoClickProtectionConfigurationParcel;
import com.google.android.gms.ads.internal.reward.mediation.client.RewardItemParcel;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

@zzji
public class zzko {
    public final int errorCode;
    public final int orientation;
    public final List<String> zzbvk;
    public final List<String> zzbvl;
    @Nullable
    public final List<String> zzbvn;
    public final long zzbvq;
    @Nullable
    public final zzgp zzbwm;
    @Nullable
    public final zzha zzbwn;
    @Nullable
    public final String zzbwo;
    @Nullable
    public final zzgs zzbwp;
    @Nullable
    public final zzmd zzcbm;
    public final AdRequestParcel zzcju;
    public final String zzcjx;
    public final long zzcla;
    public final boolean zzclb;
    public final long zzclc;
    public final List<String> zzcld;
    public final String zzclg;
    @Nullable
    public final RewardItemParcel zzclq;
    @Nullable
    public final List<String> zzcls;
    public final boolean zzclt;
    public final AutoClickProtectionConfigurationParcel zzclu;
    public final String zzclx;
    public final JSONObject zzcsi;
    public boolean zzcsj;
    public final zzgq zzcsk;
    @Nullable
    public final String zzcsl;
    public final AdSizeParcel zzcsm;
    @Nullable
    public final List<String> zzcsn;
    public final long zzcso;
    public final long zzcsp;
    @Nullable
    public final com.google.android.gms.ads.internal.formats.zzi.zza zzcsq;
    public boolean zzcsr;
    public boolean zzcss;
    public boolean zzcst;

    @zzji
    public static final class zza {
        public final int errorCode;
        @Nullable
        public final AdSizeParcel zzarm;
        public final AdRequestInfoParcel zzcmx;
        @Nullable
        public final JSONObject zzcsi;
        public final zzgq zzcsk;
        public final long zzcso;
        public final long zzcsp;
        public final AdResponseParcel zzcsu;

        public zza(AdRequestInfoParcel adRequestInfoParcel, AdResponseParcel adResponseParcel, zzgq com_google_android_gms_internal_zzgq, AdSizeParcel adSizeParcel, int i, long j, long j2, JSONObject jSONObject) {
            this.zzcmx = adRequestInfoParcel;
            this.zzcsu = adResponseParcel;
            this.zzcsk = com_google_android_gms_internal_zzgq;
            this.zzarm = adSizeParcel;
            this.errorCode = i;
            this.zzcso = j;
            this.zzcsp = j2;
            this.zzcsi = jSONObject;
        }
    }

    public zzko(AdRequestParcel adRequestParcel, @Nullable zzmd com_google_android_gms_internal_zzmd, List<String> list, int i, List<String> list2, List<String> list3, int i2, long j, String str, boolean z, @Nullable zzgp com_google_android_gms_internal_zzgp, @Nullable zzha com_google_android_gms_internal_zzha, @Nullable String str2, zzgq com_google_android_gms_internal_zzgq, @Nullable zzgs com_google_android_gms_internal_zzgs, long j2, AdSizeParcel adSizeParcel, long j3, long j4, long j5, String str3, JSONObject jSONObject, @Nullable com.google.android.gms.ads.internal.formats.zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza, RewardItemParcel rewardItemParcel, List<String> list4, List<String> list5, boolean z2, AutoClickProtectionConfigurationParcel autoClickProtectionConfigurationParcel, @Nullable String str4, List<String> list6, String str5) {
        this.zzcsr = false;
        this.zzcss = false;
        this.zzcst = false;
        this.zzcju = adRequestParcel;
        this.zzcbm = com_google_android_gms_internal_zzmd;
        this.zzbvk = zzm(list);
        this.errorCode = i;
        this.zzbvl = zzm(list2);
        this.zzcld = zzm(list3);
        this.orientation = i2;
        this.zzbvq = j;
        this.zzcjx = str;
        this.zzclb = z;
        this.zzbwm = com_google_android_gms_internal_zzgp;
        this.zzbwn = com_google_android_gms_internal_zzha;
        this.zzbwo = str2;
        this.zzcsk = com_google_android_gms_internal_zzgq;
        this.zzbwp = com_google_android_gms_internal_zzgs;
        this.zzclc = j2;
        this.zzcsm = adSizeParcel;
        this.zzcla = j3;
        this.zzcso = j4;
        this.zzcsp = j5;
        this.zzclg = str3;
        this.zzcsi = jSONObject;
        this.zzcsq = com_google_android_gms_ads_internal_formats_zzi_zza;
        this.zzclq = rewardItemParcel;
        this.zzcsn = zzm(list4);
        this.zzcls = zzm(list5);
        this.zzclt = z2;
        this.zzclu = autoClickProtectionConfigurationParcel;
        this.zzcsl = str4;
        this.zzbvn = zzm(list6);
        this.zzclx = str5;
    }

    public zzko(zza com_google_android_gms_internal_zzko_zza, @Nullable zzmd com_google_android_gms_internal_zzmd, @Nullable zzgp com_google_android_gms_internal_zzgp, @Nullable zzha com_google_android_gms_internal_zzha, @Nullable String str, @Nullable zzgs com_google_android_gms_internal_zzgs, @Nullable com.google.android.gms.ads.internal.formats.zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza, @Nullable String str2) {
        zzmd com_google_android_gms_internal_zzmd2 = com_google_android_gms_internal_zzmd;
        zzgp com_google_android_gms_internal_zzgp2 = com_google_android_gms_internal_zzgp;
        zzha com_google_android_gms_internal_zzha2 = com_google_android_gms_internal_zzha;
        String str3 = str;
        zzgs com_google_android_gms_internal_zzgs2 = com_google_android_gms_internal_zzgs;
        com.google.android.gms.ads.internal.formats.zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza2 = com_google_android_gms_ads_internal_formats_zzi_zza;
        String str4 = str2;
        this(com_google_android_gms_internal_zzko_zza.zzcmx.zzcju, com_google_android_gms_internal_zzmd2, com_google_android_gms_internal_zzko_zza.zzcsu.zzbvk, com_google_android_gms_internal_zzko_zza.errorCode, com_google_android_gms_internal_zzko_zza.zzcsu.zzbvl, com_google_android_gms_internal_zzko_zza.zzcsu.zzcld, com_google_android_gms_internal_zzko_zza.zzcsu.orientation, com_google_android_gms_internal_zzko_zza.zzcsu.zzbvq, com_google_android_gms_internal_zzko_zza.zzcmx.zzcjx, com_google_android_gms_internal_zzko_zza.zzcsu.zzclb, com_google_android_gms_internal_zzgp2, com_google_android_gms_internal_zzha2, str3, com_google_android_gms_internal_zzko_zza.zzcsk, com_google_android_gms_internal_zzgs2, com_google_android_gms_internal_zzko_zza.zzcsu.zzclc, com_google_android_gms_internal_zzko_zza.zzarm, com_google_android_gms_internal_zzko_zza.zzcsu.zzcla, com_google_android_gms_internal_zzko_zza.zzcso, com_google_android_gms_internal_zzko_zza.zzcsp, com_google_android_gms_internal_zzko_zza.zzcsu.zzclg, com_google_android_gms_internal_zzko_zza.zzcsi, com_google_android_gms_ads_internal_formats_zzi_zza2, com_google_android_gms_internal_zzko_zza.zzcsu.zzclq, com_google_android_gms_internal_zzko_zza.zzcsu.zzclr, com_google_android_gms_internal_zzko_zza.zzcsu.zzclr, com_google_android_gms_internal_zzko_zza.zzcsu.zzclt, com_google_android_gms_internal_zzko_zza.zzcsu.zzclu, str4, com_google_android_gms_internal_zzko_zza.zzcsu.zzbvn, com_google_android_gms_internal_zzko_zza.zzcsu.zzclx);
    }

    @Nullable
    private static <T> List<T> zzm(@Nullable List<T> list) {
        return list == null ? null : Collections.unmodifiableList(list);
    }

    public boolean zzic() {
        return (this.zzcbm == null || this.zzcbm.zzxc() == null) ? false : this.zzcbm.zzxc().zzic();
    }
}
