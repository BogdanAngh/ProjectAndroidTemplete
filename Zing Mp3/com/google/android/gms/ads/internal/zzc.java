package com.google.android.gms.ads.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.request.AdResponseParcel;
import com.google.android.gms.ads.internal.safebrowsing.zzd;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzdr;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzea;
import com.google.android.gms.internal.zzec;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzgi;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzhw;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzko.zza;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import java.util.Map;

@zzji
public abstract class zzc extends zzb implements zzh, zzhw {

    /* renamed from: com.google.android.gms.ads.internal.zzc.1 */
    class C11131 implements zzfe {
        final /* synthetic */ zzc zzamj;

        C11131(zzc com_google_android_gms_ads_internal_zzc) {
            this.zzamj = com_google_android_gms_ads_internal_zzc;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (this.zzamj.zzaly.zzarn != null) {
                this.zzamj.zzama.zza(this.zzamj.zzaly.zzarm, this.zzamj.zzaly.zzarn, com_google_android_gms_internal_zzmd.getView(), (zzgi) com_google_android_gms_internal_zzmd);
            } else {
                zzb.zzdi("Request to enable ActiveView before adState is available.");
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzc.2 */
    class C11142 implements Runnable {
        final /* synthetic */ zzc zzamj;
        final /* synthetic */ zza zzamk;

        C11142(zzc com_google_android_gms_ads_internal_zzc, zza com_google_android_gms_internal_zzko_zza) {
            this.zzamj = com_google_android_gms_ads_internal_zzc;
            this.zzamk = com_google_android_gms_internal_zzko_zza;
        }

        public void run() {
            this.zzamj.zzb(new zzko(this.zzamk, null, null, null, null, null, null, null));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzc.3 */
    class C11173 implements Runnable {
        final /* synthetic */ zzc zzamj;
        final /* synthetic */ zza zzamk;
        final /* synthetic */ com.google.android.gms.ads.internal.safebrowsing.zzc zzaml;
        final /* synthetic */ zzdz zzamm;

        /* renamed from: com.google.android.gms.ads.internal.zzc.3.1 */
        class C11151 implements OnTouchListener {
            final /* synthetic */ zze zzamn;
            final /* synthetic */ C11173 zzamo;

            C11151(C11173 c11173, zze com_google_android_gms_ads_internal_zze) {
                this.zzamo = c11173;
                this.zzamn = com_google_android_gms_ads_internal_zze;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                this.zzamn.recordClick();
                return false;
            }
        }

        /* renamed from: com.google.android.gms.ads.internal.zzc.3.2 */
        class C11162 implements OnClickListener {
            final /* synthetic */ zze zzamn;
            final /* synthetic */ C11173 zzamo;

            C11162(C11173 c11173, zze com_google_android_gms_ads_internal_zze) {
                this.zzamo = c11173;
                this.zzamn = com_google_android_gms_ads_internal_zze;
            }

            public void onClick(View view) {
                this.zzamn.recordClick();
            }
        }

        C11173(zzc com_google_android_gms_ads_internal_zzc, zza com_google_android_gms_internal_zzko_zza, com.google.android.gms.ads.internal.safebrowsing.zzc com_google_android_gms_ads_internal_safebrowsing_zzc, zzdz com_google_android_gms_internal_zzdz) {
            this.zzamj = com_google_android_gms_ads_internal_zzc;
            this.zzamk = com_google_android_gms_internal_zzko_zza;
            this.zzaml = com_google_android_gms_ads_internal_safebrowsing_zzc;
            this.zzamm = com_google_android_gms_internal_zzdz;
        }

        public void run() {
            if (this.zzamk.zzcsu.zzclk && this.zzamj.zzaly.zzasc != null) {
                String str = null;
                if (this.zzamk.zzcsu.zzcbo != null) {
                    str = zzu.zzgm().zzcz(this.zzamk.zzcsu.zzcbo);
                }
                zzec com_google_android_gms_internal_zzea = new zzea(this.zzamj, str, this.zzamk.zzcsu.body);
                this.zzamj.zzaly.zzasi = 1;
                try {
                    this.zzamj.zzalw = false;
                    this.zzamj.zzaly.zzasc.zza(com_google_android_gms_internal_zzea);
                    return;
                } catch (Throwable e) {
                    zzb.zzc("Could not call the onCustomRenderedAdLoadedListener.", e);
                    this.zzamj.zzalw = true;
                }
            }
            zze com_google_android_gms_ads_internal_zze = new zze(this.zzamj.zzaly.zzahs, this.zzamk);
            zzmd zza = this.zzamj.zza(this.zzamk, com_google_android_gms_ads_internal_zze, this.zzaml);
            zza.setOnTouchListener(new C11151(this, com_google_android_gms_ads_internal_zze));
            zza.setOnClickListener(new C11162(this, com_google_android_gms_ads_internal_zze));
            this.zzamj.zzaly.zzasi = 0;
            this.zzamj.zzaly.zzarl = zzu.zzgl().zza(this.zzamj.zzaly.zzahs, this.zzamj, this.zzamk, this.zzamj.zzaly.zzarh, zza, this.zzamj.zzamf, this.zzamj, this.zzamm);
        }
    }

    public zzc(Context context, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel, zzd com_google_android_gms_ads_internal_zzd) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
    }

    protected zzmd zza(zza com_google_android_gms_internal_zzko_zza, @Nullable zze com_google_android_gms_ads_internal_zze, @Nullable com.google.android.gms.ads.internal.safebrowsing.zzc com_google_android_gms_ads_internal_safebrowsing_zzc) {
        zzmd com_google_android_gms_internal_zzmd = null;
        View nextView = this.zzaly.zzarj.getNextView();
        if (nextView instanceof zzmd) {
            com_google_android_gms_internal_zzmd = (zzmd) nextView;
            if (((Boolean) zzdr.zzbft.get()).booleanValue()) {
                zzb.zzdg("Reusing webview...");
                com_google_android_gms_internal_zzmd.zza(this.zzaly.zzahs, this.zzaly.zzarm, this.zzalt);
            } else {
                com_google_android_gms_internal_zzmd.destroy();
                com_google_android_gms_internal_zzmd = null;
            }
        }
        if (com_google_android_gms_internal_zzmd == null) {
            if (nextView != null) {
                this.zzaly.zzarj.removeView(nextView);
            }
            com_google_android_gms_internal_zzmd = zzu.zzgn().zza(this.zzaly.zzahs, this.zzaly.zzarm, false, false, this.zzaly.zzarh, this.zzaly.zzari, this.zzalt, this, this.zzamb);
            if (this.zzaly.zzarm.zzazs == null) {
                zzb(com_google_android_gms_internal_zzmd.getView());
            }
        }
        zzgi com_google_android_gms_internal_zzgi = com_google_android_gms_internal_zzmd;
        com_google_android_gms_internal_zzgi.zzxc().zza(this, this, this, this, false, this, null, com_google_android_gms_ads_internal_zze, this, com_google_android_gms_ads_internal_safebrowsing_zzc);
        zza(com_google_android_gms_internal_zzgi);
        com_google_android_gms_internal_zzgi.zzdk(com_google_android_gms_internal_zzko_zza.zzcmx.zzcki);
        return com_google_android_gms_internal_zzgi;
    }

    public void zza(int i, int i2, int i3, int i4) {
        zzem();
    }

    public void zza(zzed com_google_android_gms_internal_zzed) {
        zzaa.zzhs("setOnCustomRenderedAdLoadedListener must be called on the main UI thread.");
        this.zzaly.zzasc = com_google_android_gms_internal_zzed;
    }

    protected void zza(zzgi com_google_android_gms_internal_zzgi) {
        com_google_android_gms_internal_zzgi.zza("/trackActiveViewUnit", new C11131(this));
    }

    protected void zza(zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz) {
        if (com_google_android_gms_internal_zzko_zza.errorCode != -2) {
            zzlb.zzcvl.post(new C11142(this, com_google_android_gms_internal_zzko_zza));
            return;
        }
        if (com_google_android_gms_internal_zzko_zza.zzarm != null) {
            this.zzaly.zzarm = com_google_android_gms_internal_zzko_zza.zzarm;
        }
        if (!com_google_android_gms_internal_zzko_zza.zzcsu.zzclb || com_google_android_gms_internal_zzko_zza.zzcsu.zzazv) {
            zzd com_google_android_gms_ads_internal_safebrowsing_zzd = this.zzamb.zzams;
            Context context = this.zzaly.zzahs;
            AdResponseParcel adResponseParcel = com_google_android_gms_internal_zzko_zza.zzcsu;
            zzlb.zzcvl.post(new C11173(this, com_google_android_gms_internal_zzko_zza, null, com_google_android_gms_internal_zzdz));
            return;
        }
        this.zzaly.zzasi = 0;
        this.zzaly.zzarl = zzu.zzgl().zza(this.zzaly.zzahs, this, com_google_android_gms_internal_zzko_zza, this.zzaly.zzarh, null, this.zzamf, this, com_google_android_gms_internal_zzdz);
    }

    protected boolean zza(@Nullable zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        if (this.zzaly.zzhp() && this.zzaly.zzarj != null) {
            this.zzaly.zzarj.zzhv().zzdc(com_google_android_gms_internal_zzko2.zzclg);
        }
        return super.zza(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko2);
    }

    public void zzc(View view) {
        this.zzaly.zzash = view;
        zzb(new zzko(this.zzaly.zzaro, null, null, null, null, null, null, null));
    }

    public void zzfa() {
        onAdClicked();
    }

    public void zzfb() {
        recordImpression();
        zzei();
    }

    public void zzfc() {
        zzek();
    }
}
