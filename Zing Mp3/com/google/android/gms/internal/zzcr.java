package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzcr implements zzct {
    private final zzcp zzaua;
    private final zzgi zzaub;
    private final zzfe zzauc;
    private final zzfe zzaud;
    private final zzfe zzaue;

    /* renamed from: com.google.android.gms.internal.zzcr.1 */
    class C12601 implements zzfe {
        final /* synthetic */ zzcr zzauf;

        C12601(zzcr com_google_android_gms_internal_zzcr) {
            this.zzauf = com_google_android_gms_internal_zzcr;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzauf.zzaua.zzb(com_google_android_gms_internal_zzmd, (Map) map);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcr.2 */
    class C12612 implements zzfe {
        final /* synthetic */ zzcr zzauf;

        C12612(zzcr com_google_android_gms_internal_zzcr) {
            this.zzauf = com_google_android_gms_internal_zzcr;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzauf.zzaua.zza(this.zzauf, (Map) map);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcr.3 */
    class C12623 implements zzfe {
        final /* synthetic */ zzcr zzauf;

        C12623(zzcr com_google_android_gms_internal_zzcr) {
            this.zzauf = com_google_android_gms_internal_zzcr;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzauf.zzaua.zzc(map);
        }
    }

    public zzcr(zzcp com_google_android_gms_internal_zzcp, zzgi com_google_android_gms_internal_zzgi) {
        this.zzauc = new C12601(this);
        this.zzaud = new C12612(this);
        this.zzaue = new C12623(this);
        this.zzaua = com_google_android_gms_internal_zzcp;
        this.zzaub = com_google_android_gms_internal_zzgi;
        zzc(this.zzaub);
        String str = "Custom JS tracking ad unit: ";
        String valueOf = String.valueOf(this.zzaua.zziq().zzib());
        zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    void zzc(zzgi com_google_android_gms_internal_zzgi) {
        com_google_android_gms_internal_zzgi.zza("/updateActiveView", this.zzauc);
        com_google_android_gms_internal_zzgi.zza("/untrackActiveViewUnit", this.zzaud);
        com_google_android_gms_internal_zzgi.zza("/visibilityChanged", this.zzaue);
    }

    public void zzc(JSONObject jSONObject, boolean z) {
        if (z) {
            this.zzaua.zzb((zzct) this);
        } else {
            this.zzaub.zza("AFMA_updateActiveView", jSONObject);
        }
    }

    void zzd(zzgi com_google_android_gms_internal_zzgi) {
        com_google_android_gms_internal_zzgi.zzb("/visibilityChanged", this.zzaue);
        com_google_android_gms_internal_zzgi.zzb("/untrackActiveViewUnit", this.zzaud);
        com_google_android_gms_internal_zzgi.zzb("/updateActiveView", this.zzauc);
    }

    public boolean zziu() {
        return true;
    }

    public void zziv() {
        zzd(this.zzaub);
    }
}
