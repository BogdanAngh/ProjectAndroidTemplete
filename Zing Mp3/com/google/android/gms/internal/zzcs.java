package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgh.zzc;
import com.google.android.gms.internal.zzlw.zza;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzcs implements zzct {
    private final zzcp zzaua;
    private final zzfe zzauc;
    private final zzfe zzaud;
    private final zzfe zzaue;
    private zzc zzaug;
    private boolean zzauh;

    /* renamed from: com.google.android.gms.internal.zzcs.1 */
    class C12631 implements zzlw.zzc<zzgi> {
        final /* synthetic */ zzcs zzaui;

        C12631(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void zzb(zzgi com_google_android_gms_internal_zzgi) {
            this.zzaui.zzauh = true;
            this.zzaui.zzc(com_google_android_gms_internal_zzgi);
        }

        public /* synthetic */ void zzd(Object obj) {
            zzb((zzgi) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.2 */
    class C12642 implements zza {
        final /* synthetic */ zzcs zzaui;

        C12642(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void run() {
            this.zzaui.zzaua.zzb(this.zzaui);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.3 */
    class C12653 implements zzlw.zzc<zzgi> {
        final /* synthetic */ zzcs zzaui;
        final /* synthetic */ JSONObject zzauj;

        C12653(zzcs com_google_android_gms_internal_zzcs, JSONObject jSONObject) {
            this.zzaui = com_google_android_gms_internal_zzcs;
            this.zzauj = jSONObject;
        }

        public void zzb(zzgi com_google_android_gms_internal_zzgi) {
            com_google_android_gms_internal_zzgi.zza("AFMA_updateActiveView", this.zzauj);
        }

        public /* synthetic */ void zzd(Object obj) {
            zzb((zzgi) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.4 */
    class C12664 implements zzlw.zzc<zzgi> {
        final /* synthetic */ zzcs zzaui;

        C12664(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void zzb(zzgi com_google_android_gms_internal_zzgi) {
            this.zzaui.zzd(com_google_android_gms_internal_zzgi);
        }

        public /* synthetic */ void zzd(Object obj) {
            zzb((zzgi) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.5 */
    class C12675 implements zzfe {
        final /* synthetic */ zzcs zzaui;

        C12675(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (this.zzaui.zzaua.zzb((Map) map)) {
                this.zzaui.zzaua.zzb(com_google_android_gms_internal_zzmd, (Map) map);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.6 */
    class C12686 implements zzfe {
        final /* synthetic */ zzcs zzaui;

        C12686(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (this.zzaui.zzaua.zzb((Map) map)) {
                this.zzaui.zzaua.zza(this.zzaui, (Map) map);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcs.7 */
    class C12697 implements zzfe {
        final /* synthetic */ zzcs zzaui;

        C12697(zzcs com_google_android_gms_internal_zzcs) {
            this.zzaui = com_google_android_gms_internal_zzcs;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (this.zzaui.zzaua.zzb((Map) map)) {
                this.zzaui.zzaua.zzc(map);
            }
        }
    }

    public zzcs(zzcp com_google_android_gms_internal_zzcp, zzgh com_google_android_gms_internal_zzgh) {
        this.zzauc = new C12675(this);
        this.zzaud = new C12686(this);
        this.zzaue = new C12697(this);
        this.zzaua = com_google_android_gms_internal_zzcp;
        this.zzaug = com_google_android_gms_internal_zzgh.zzny();
        this.zzaug.zza(new C12631(this), new C12642(this));
        String str = "Core JS tracking ad unit: ";
        String valueOf = String.valueOf(this.zzaua.zziq().zzib());
        zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    void zzc(zzgi com_google_android_gms_internal_zzgi) {
        com_google_android_gms_internal_zzgi.zza("/updateActiveView", this.zzauc);
        com_google_android_gms_internal_zzgi.zza("/untrackActiveViewUnit", this.zzaud);
        com_google_android_gms_internal_zzgi.zza("/visibilityChanged", this.zzaue);
    }

    public void zzc(JSONObject jSONObject, boolean z) {
        this.zzaug.zza(new C12653(this, jSONObject), new zzlw.zzb());
    }

    void zzd(zzgi com_google_android_gms_internal_zzgi) {
        com_google_android_gms_internal_zzgi.zzb("/visibilityChanged", this.zzaue);
        com_google_android_gms_internal_zzgi.zzb("/untrackActiveViewUnit", this.zzaud);
        com_google_android_gms_internal_zzgi.zzb("/updateActiveView", this.zzauc);
    }

    public boolean zziu() {
        return this.zzauh;
    }

    public void zziv() {
        this.zzaug.zza(new C12664(this), new zzlw.zzb());
        this.zzaug.release();
    }
}
