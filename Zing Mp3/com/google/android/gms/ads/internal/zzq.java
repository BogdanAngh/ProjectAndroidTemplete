package com.google.android.gms.ads.internal;

import android.content.Context;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import com.google.android.gms.ads.internal.client.AdRequestParcel;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.formats.NativeAdOptionsParcel;
import com.google.android.gms.ads.internal.formats.zzd;
import com.google.android.gms.ads.internal.formats.zze;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.formats.zzh;
import com.google.android.gms.ads.internal.formats.zzi;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzdz;
import com.google.android.gms.internal.zzed;
import com.google.android.gms.internal.zzeq;
import com.google.android.gms.internal.zzer;
import com.google.android.gms.internal.zzes;
import com.google.android.gms.internal.zzet;
import com.google.android.gms.internal.zzgz;
import com.google.android.gms.internal.zzhd;
import com.google.android.gms.internal.zzhe;
import com.google.android.gms.internal.zzig;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzko.zza;
import com.google.android.gms.internal.zzlb;
import com.google.android.gms.internal.zzmd;
import java.util.List;

@zzji
public class zzq extends zzb {
    private zzmd zzapd;

    /* renamed from: com.google.android.gms.ads.internal.zzq.1 */
    class C11321 implements Runnable {
        final /* synthetic */ zza zzamk;
        final /* synthetic */ zzq zzape;

        C11321(zzq com_google_android_gms_ads_internal_zzq, zza com_google_android_gms_internal_zzko_zza) {
            this.zzape = com_google_android_gms_ads_internal_zzq;
            this.zzamk = com_google_android_gms_internal_zzko_zza;
        }

        public void run() {
            this.zzape.zzb(new zzko(this.zzamk, null, null, null, null, null, null, null));
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzq.2 */
    class C11332 implements Runnable {
        final /* synthetic */ zzq zzape;
        final /* synthetic */ zzd zzapf;

        C11332(zzq com_google_android_gms_ads_internal_zzq, zzd com_google_android_gms_ads_internal_formats_zzd) {
            this.zzape = com_google_android_gms_ads_internal_zzq;
            this.zzapf = com_google_android_gms_ads_internal_formats_zzd;
        }

        public void run() {
            try {
                if (this.zzape.zzaly.zzarw != null) {
                    this.zzape.zzaly.zzarw.zza(this.zzapf);
                }
            } catch (Throwable e) {
                zzb.zzc("Could not call OnAppInstallAdLoadedListener.onAppInstallAdLoaded().", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzq.3 */
    class C11343 implements Runnable {
        final /* synthetic */ zzq zzape;
        final /* synthetic */ zze zzapg;

        C11343(zzq com_google_android_gms_ads_internal_zzq, zze com_google_android_gms_ads_internal_formats_zze) {
            this.zzape = com_google_android_gms_ads_internal_zzq;
            this.zzapg = com_google_android_gms_ads_internal_formats_zze;
        }

        public void run() {
            try {
                if (this.zzape.zzaly.zzarx != null) {
                    this.zzape.zzaly.zzarx.zza(this.zzapg);
                }
            } catch (Throwable e) {
                zzb.zzc("Could not call OnContentAdLoadedListener.onContentAdLoaded().", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzq.4 */
    class C11354 implements Runnable {
        final /* synthetic */ zzko zzamz;
        final /* synthetic */ zzq zzape;
        final /* synthetic */ String zzaph;

        C11354(zzq com_google_android_gms_ads_internal_zzq, String str, zzko com_google_android_gms_internal_zzko) {
            this.zzape = com_google_android_gms_ads_internal_zzq;
            this.zzaph = str;
            this.zzamz = com_google_android_gms_internal_zzko;
        }

        public void run() {
            try {
                ((zzet) this.zzape.zzaly.zzarz.get(this.zzaph)).zza((zzf) this.zzamz.zzcsq);
            } catch (Throwable e) {
                zzb.zzc("Could not call onCustomTemplateAdLoadedListener.onCustomTemplateAdLoaded().", e);
            }
        }
    }

    public zzq(Context context, zzd com_google_android_gms_ads_internal_zzd, AdSizeParcel adSizeParcel, String str, zzgz com_google_android_gms_internal_zzgz, VersionInfoParcel versionInfoParcel) {
        super(context, adSizeParcel, str, com_google_android_gms_internal_zzgz, versionInfoParcel, com_google_android_gms_ads_internal_zzd);
    }

    private static zzd zza(zzhd com_google_android_gms_internal_zzhd) throws RemoteException {
        return new zzd(com_google_android_gms_internal_zzhd.getHeadline(), com_google_android_gms_internal_zzhd.getImages(), com_google_android_gms_internal_zzhd.getBody(), com_google_android_gms_internal_zzhd.zzmo() != null ? com_google_android_gms_internal_zzhd.zzmo() : null, com_google_android_gms_internal_zzhd.getCallToAction(), com_google_android_gms_internal_zzhd.getStarRating(), com_google_android_gms_internal_zzhd.getStore(), com_google_android_gms_internal_zzhd.getPrice(), null, com_google_android_gms_internal_zzhd.getExtras(), com_google_android_gms_internal_zzhd.zzej(), null);
    }

    private static zze zza(zzhe com_google_android_gms_internal_zzhe) throws RemoteException {
        return new zze(com_google_android_gms_internal_zzhe.getHeadline(), com_google_android_gms_internal_zzhe.getImages(), com_google_android_gms_internal_zzhe.getBody(), com_google_android_gms_internal_zzhe.zzmt() != null ? com_google_android_gms_internal_zzhe.zzmt() : null, com_google_android_gms_internal_zzhe.getCallToAction(), com_google_android_gms_internal_zzhe.getAdvertiser(), null, com_google_android_gms_internal_zzhe.getExtras());
    }

    private void zza(zzd com_google_android_gms_ads_internal_formats_zzd) {
        zzlb.zzcvl.post(new C11332(this, com_google_android_gms_ads_internal_formats_zzd));
    }

    private void zza(zze com_google_android_gms_ads_internal_formats_zze) {
        zzlb.zzcvl.post(new C11343(this, com_google_android_gms_ads_internal_formats_zze));
    }

    private void zza(zzko com_google_android_gms_internal_zzko, String str) {
        zzlb.zzcvl.post(new C11354(this, str, com_google_android_gms_internal_zzko));
    }

    public void pause() {
        throw new IllegalStateException("Native Ad DOES NOT support pause().");
    }

    public void resume() {
        throw new IllegalStateException("Native Ad DOES NOT support resume().");
    }

    public void showInterstitial() {
        throw new IllegalStateException("Interstitial is NOT supported by NativeAdManager.");
    }

    public void zza(SimpleArrayMap<String, zzet> simpleArrayMap) {
        zzaa.zzhs("setOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        this.zzaly.zzarz = simpleArrayMap;
    }

    public void zza(zzg com_google_android_gms_ads_internal_formats_zzg) {
        if (this.zzapd != null) {
            this.zzapd.zzb(com_google_android_gms_ads_internal_formats_zzg);
        }
    }

    public void zza(zzi com_google_android_gms_ads_internal_formats_zzi) {
        if (this.zzaly.zzarn.zzcsi != null) {
            zzu.zzgq().zzvg().zza(this.zzaly.zzarm, this.zzaly.zzarn, com_google_android_gms_ads_internal_formats_zzi);
        }
    }

    public void zza(zzed com_google_android_gms_internal_zzed) {
        throw new IllegalStateException("CustomRendering is NOT supported by NativeAdManager.");
    }

    public void zza(zzig com_google_android_gms_internal_zzig) {
        throw new IllegalStateException("In App Purchase is NOT supported by NativeAdManager.");
    }

    public void zza(zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz) {
        if (com_google_android_gms_internal_zzko_zza.zzarm != null) {
            this.zzaly.zzarm = com_google_android_gms_internal_zzko_zza.zzarm;
        }
        if (com_google_android_gms_internal_zzko_zza.errorCode != -2) {
            zzlb.zzcvl.post(new C11321(this, com_google_android_gms_internal_zzko_zza));
            return;
        }
        this.zzaly.zzasi = 0;
        this.zzaly.zzarl = zzu.zzgl().zza(this.zzaly.zzahs, this, com_google_android_gms_internal_zzko_zza, this.zzaly.zzarh, null, this.zzamf, this, com_google_android_gms_internal_zzdz);
        String str = "AdRenderer: ";
        String valueOf = String.valueOf(this.zzaly.zzarl.getClass().getName());
        zzb.zzdg(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
    }

    protected boolean zza(AdRequestParcel adRequestParcel, zzko com_google_android_gms_internal_zzko, boolean z) {
        return this.zzalx.zzfy();
    }

    protected boolean zza(zzko com_google_android_gms_internal_zzko, zzko com_google_android_gms_internal_zzko2) {
        zzb(null);
        if (this.zzaly.zzhp()) {
            if (com_google_android_gms_internal_zzko2.zzclb) {
                try {
                    zzhd zzom = com_google_android_gms_internal_zzko2.zzbwn != null ? com_google_android_gms_internal_zzko2.zzbwn.zzom() : null;
                    zzhe zzon = com_google_android_gms_internal_zzko2.zzbwn != null ? com_google_android_gms_internal_zzko2.zzbwn.zzon() : null;
                    if (zzom == null || this.zzaly.zzarw == null) {
                        if (zzon != null) {
                            if (this.zzaly.zzarx != null) {
                                zze zza = zza(zzon);
                                zza.zzb(new zzh(this.zzaly.zzahs, this, this.zzaly.zzarh, zzon, (zzi.zza) zza));
                                zza(zza);
                            }
                        }
                        zzb.zzdi("No matching mapper/listener for retrieved native ad template.");
                        zzh(0);
                        return false;
                    }
                    zzd zza2 = zza(zzom);
                    zza2.zzb(new zzh(this.zzaly.zzahs, this, this.zzaly.zzarh, zzom, (zzi.zza) zza2));
                    zza(zza2);
                } catch (Throwable e) {
                    zzb.zzc("Failed to get native ad mapper", e);
                }
            } else {
                zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza = com_google_android_gms_internal_zzko2.zzcsq;
                if ((com_google_android_gms_ads_internal_formats_zzi_zza instanceof zze) && this.zzaly.zzarx != null) {
                    zza((zze) com_google_android_gms_internal_zzko2.zzcsq);
                } else if ((com_google_android_gms_ads_internal_formats_zzi_zza instanceof zzd) && this.zzaly.zzarw != null) {
                    zza((zzd) com_google_android_gms_internal_zzko2.zzcsq);
                } else if (!(com_google_android_gms_ads_internal_formats_zzi_zza instanceof zzf) || this.zzaly.zzarz == null || this.zzaly.zzarz.get(((zzf) com_google_android_gms_ads_internal_formats_zzi_zza).getCustomTemplateId()) == null) {
                    zzb.zzdi("No matching listener for retrieved native ad template.");
                    zzh(0);
                    return false;
                } else {
                    zza(com_google_android_gms_internal_zzko2, ((zzf) com_google_android_gms_ads_internal_formats_zzi_zza).getCustomTemplateId());
                }
            }
            return super.zza(com_google_android_gms_internal_zzko, com_google_android_gms_internal_zzko2);
        }
        throw new IllegalStateException("Native ad DOES NOT have custom rendering mode.");
    }

    @Nullable
    public zzes zzaa(String str) {
        zzaa.zzhs("getOnCustomClickListener must be called on the main UI thread.");
        return (zzes) this.zzaly.zzary.get(str);
    }

    public void zzb(SimpleArrayMap<String, zzes> simpleArrayMap) {
        zzaa.zzhs("setOnCustomClickListener must be called on the main UI thread.");
        this.zzaly.zzary = simpleArrayMap;
    }

    public void zzb(NativeAdOptionsParcel nativeAdOptionsParcel) {
        zzaa.zzhs("setNativeAdOptions must be called on the main UI thread.");
        this.zzaly.zzasa = nativeAdOptionsParcel;
    }

    public void zzb(zzeq com_google_android_gms_internal_zzeq) {
        zzaa.zzhs("setOnAppInstallAdLoadedListener must be called on the main UI thread.");
        this.zzaly.zzarw = com_google_android_gms_internal_zzeq;
    }

    public void zzb(zzer com_google_android_gms_internal_zzer) {
        zzaa.zzhs("setOnContentAdLoadedListener must be called on the main UI thread.");
        this.zzaly.zzarx = com_google_android_gms_internal_zzer;
    }

    public void zzb(@Nullable List<String> list) {
        zzaa.zzhs("setNativeTemplates must be called on the main UI thread.");
        this.zzaly.zzase = list;
    }

    public void zzc(zzmd com_google_android_gms_internal_zzmd) {
        this.zzapd = com_google_android_gms_internal_zzmd;
    }

    public void zzfu() {
        if (this.zzaly.zzarn == null || this.zzapd == null) {
            zzb.zzdi("Request to enable ActiveView before adState is available.");
        } else {
            zzu.zzgq().zzvg().zza(this.zzaly.zzarm, this.zzaly.zzarn, this.zzapd.getView(), this.zzapd);
        }
    }

    public SimpleArrayMap<String, zzet> zzfv() {
        zzaa.zzhs("getOnCustomTemplateAdLoadedListeners must be called on the main UI thread.");
        return this.zzaly.zzarz;
    }

    public void zzfw() {
        if (this.zzapd != null) {
            this.zzapd.destroy();
            this.zzapd = null;
        }
    }

    public void zzfx() {
        if (this.zzapd != null && this.zzapd.zzxn() != null && this.zzaly.zzasa != null && this.zzaly.zzasa.zzbon != null) {
            this.zzapd.zzxn().zzaq(this.zzaly.zzasa.zzbon.zzbck);
        }
    }
}
