package com.google.android.gms.ads.internal;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.request.AutoClickProtectionConfigurationParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko.zza;
import com.mp3download.zingmp3.BuildConfig;

@zzji
public class zze {
    private final Context mContext;
    private final AutoClickProtectionConfigurationParcel zzamt;
    private boolean zzamu;

    public zze(Context context) {
        this(context, false);
    }

    public zze(Context context, @Nullable zza com_google_android_gms_internal_zzko_zza) {
        this.mContext = context;
        if (com_google_android_gms_internal_zzko_zza == null || com_google_android_gms_internal_zzko_zza.zzcsu.zzclu == null) {
            this.zzamt = new AutoClickProtectionConfigurationParcel();
        } else {
            this.zzamt = com_google_android_gms_internal_zzko_zza.zzcsu.zzclu;
        }
    }

    public zze(Context context, boolean z) {
        this.mContext = context;
        this.zzamt = new AutoClickProtectionConfigurationParcel(z);
    }

    public void recordClick() {
        this.zzamu = true;
    }

    public boolean zzfe() {
        return !this.zzamt.zzclz || this.zzamu;
    }

    public void zzy(@Nullable String str) {
        if (str == null) {
            str = BuildConfig.FLAVOR;
        }
        zzb.zzdh("Action was blocked because no touch was detected.");
        if (this.zzamt.zzclz && this.zzamt.zzcma != null) {
            for (String str2 : this.zzamt.zzcma) {
                if (!TextUtils.isEmpty(str2)) {
                    zzu.zzgm().zzc(this.mContext, BuildConfig.FLAVOR, str2.replace("{NAVIGATION_URL}", Uri.encode(str)));
                }
            }
        }
    }
}
