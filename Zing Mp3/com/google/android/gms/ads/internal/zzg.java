package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzgh;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkq;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzlw;
import com.google.android.gms.internal.zzlw.zzc;
import com.google.android.gms.internal.zzmd;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzg {
    private Context mContext;
    private final Object zzako;
    public final zzfe zzana;

    /* renamed from: com.google.android.gms.ads.internal.zzg.1 */
    class C11211 implements zzfe {
        final /* synthetic */ zzg zzanb;

        C11211(zzg com_google_android_gms_ads_internal_zzg) {
            this.zzanb = com_google_android_gms_ads_internal_zzg;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            com_google_android_gms_internal_zzmd.zzb("/appSettingsFetched", (zzfe) this);
            synchronized (this.zzanb.zzako) {
                if (map != null) {
                    if (ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase((String) map.get("isSuccessful"))) {
                        zzu.zzgq().zzd(this.zzanb.mContext, (String) map.get("appSettingsJson"));
                    }
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzg.2 */
    class C11232 implements Runnable {
        final /* synthetic */ zzg zzanb;
        final /* synthetic */ zzgh zzanc;
        final /* synthetic */ String zzand;
        final /* synthetic */ String zzane;
        final /* synthetic */ boolean zzanf;
        final /* synthetic */ Context zzang;

        /* renamed from: com.google.android.gms.ads.internal.zzg.2.1 */
        class C11221 implements zzc<zzgi> {
            final /* synthetic */ C11232 zzanh;

            C11221(C11232 c11232) {
                this.zzanh = c11232;
            }

            public void zzb(zzgi com_google_android_gms_internal_zzgi) {
                com_google_android_gms_internal_zzgi.zza("/appSettingsFetched", this.zzanh.zzanb.zzana);
                try {
                    JSONObject jSONObject = new JSONObject();
                    if (!TextUtils.isEmpty(this.zzanh.zzand)) {
                        jSONObject.put(ServerProtocol.FALLBACK_DIALOG_PARAM_APP_ID, this.zzanh.zzand);
                    } else if (!TextUtils.isEmpty(this.zzanh.zzane)) {
                        jSONObject.put("ad_unit_id", this.zzanh.zzane);
                    }
                    jSONObject.put("is_init", this.zzanh.zzanf);
                    jSONObject.put("pn", this.zzanh.zzang.getPackageName());
                    com_google_android_gms_internal_zzgi.zza("AFMA_fetchAppSettings", jSONObject);
                } catch (Throwable e) {
                    com_google_android_gms_internal_zzgi.zzb("/appSettingsFetched", this.zzanh.zzanb.zzana);
                    zzb.zzb("Error requesting application settings", e);
                }
            }

            public /* synthetic */ void zzd(Object obj) {
                zzb((zzgi) obj);
            }
        }

        C11232(zzg com_google_android_gms_ads_internal_zzg, zzgh com_google_android_gms_internal_zzgh, String str, String str2, boolean z, Context context) {
            this.zzanb = com_google_android_gms_ads_internal_zzg;
            this.zzanc = com_google_android_gms_internal_zzgh;
            this.zzand = str;
            this.zzane = str2;
            this.zzanf = z;
            this.zzang = context;
        }

        public void run() {
            this.zzanc.zzny().zza(new C11221(this), new zzlw.zzb());
        }
    }

    public zzg() {
        this.zzako = new Object();
        this.zzana = new C11211(this);
    }

    private static boolean zza(@Nullable zzkq com_google_android_gms_internal_zzkq) {
        if (com_google_android_gms_internal_zzkq == null) {
            return true;
        }
        boolean z = (((zzu.zzgs().currentTimeMillis() - com_google_android_gms_internal_zzkq.zzum()) > ((Long) zzdr.zzbjx.get()).longValue() ? 1 : ((zzu.zzgs().currentTimeMillis() - com_google_android_gms_internal_zzkq.zzum()) == ((Long) zzdr.zzbjx.get()).longValue() ? 0 : -1)) > 0) || !com_google_android_gms_internal_zzkq.zzun();
        return z;
    }

    public void zza(Context context, VersionInfoParcel versionInfoParcel, boolean z, @Nullable zzkq com_google_android_gms_internal_zzkq, String str, @Nullable String str2) {
        if (!zza(com_google_android_gms_internal_zzkq)) {
            return;
        }
        if (context == null) {
            zzb.zzdi("Context not provided to fetch application settings");
        } else if (TextUtils.isEmpty(str) && TextUtils.isEmpty(str2)) {
            zzb.zzdi("App settings could not be fetched. Required parameters missing");
        } else {
            this.mContext = context;
            zzlb.zzcvl.post(new C11232(this, zzu.zzgm().zzd(context, versionInfoParcel), str, str2, z, context));
        }
    }
}
