package com.google.android.gms.internal;

import android.content.Context;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import com.google.android.gms.ads.internal.client.AdSizeParcel;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzko.zza;
import com.google.android.gms.internal.zzme.zzb;
import java.lang.ref.WeakReference;
import java.util.Map;
import org.json.JSONObject;

@zzji
public class zzjc {
    private final Context mContext;
    private final Object zzako;
    private final zzdz zzalt;
    private int zzasl;
    private int zzasm;
    private zzlm zzasn;
    private final zzq zzbnr;
    private final zzav zzbnx;
    private final zza zzcgf;
    private OnGlobalLayoutListener zzcir;
    private OnScrollChangedListener zzcis;

    /* renamed from: com.google.android.gms.internal.zzjc.1 */
    class C14031 implements Runnable {
        final /* synthetic */ JSONObject zzcit;
        final /* synthetic */ zzlq zzciu;
        final /* synthetic */ zzjc zzciv;

        /* renamed from: com.google.android.gms.internal.zzjc.1.1 */
        class C14011 implements zzb {
            final /* synthetic */ zzmd zzaos;
            final /* synthetic */ C14031 zzciw;

            C14011(C14031 c14031, zzmd com_google_android_gms_internal_zzmd) {
                this.zzciw = c14031;
                this.zzaos = com_google_android_gms_internal_zzmd;
            }

            public void zzk(zzmd com_google_android_gms_internal_zzmd) {
                this.zzaos.zza("google.afma.nativeAds.renderVideo", this.zzciw.zzcit);
            }
        }

        /* renamed from: com.google.android.gms.internal.zzjc.1.2 */
        class C14022 implements zzme.zza {
            final /* synthetic */ C14031 zzciw;

            C14022(C14031 c14031) {
                this.zzciw = c14031;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
                this.zzciw.zzciv.zzbnr.zzfx();
                this.zzciw.zzciu.zzh(com_google_android_gms_internal_zzmd);
            }
        }

        C14031(zzjc com_google_android_gms_internal_zzjc, JSONObject jSONObject, zzlq com_google_android_gms_internal_zzlq) {
            this.zzciv = com_google_android_gms_internal_zzjc;
            this.zzcit = jSONObject;
            this.zzciu = com_google_android_gms_internal_zzlq;
        }

        public void run() {
            try {
                zzmd zzsx = this.zzciv.zzsx();
                this.zzciv.zzbnr.zzc(zzsx);
                WeakReference weakReference = new WeakReference(zzsx);
                zzsx.zzxc().zza(this.zzciv.zza(weakReference), this.zzciv.zzb(weakReference));
                this.zzciv.zzj(zzsx);
                zzsx.zzxc().zza(new C14011(this, zzsx));
                zzsx.zzxc().zza(new C14022(this));
                zzsx.loadUrl(zzja.zza(this.zzciv.zzcgf, (String) zzdr.zzbiy.get()));
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Exception occurred while getting video view", e);
                this.zzciu.zzh(null);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjc.2 */
    class C14042 implements zzfe {
        final /* synthetic */ zzjc zzciv;

        C14042(zzjc com_google_android_gms_internal_zzjc) {
            this.zzciv = com_google_android_gms_internal_zzjc;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzciv.zzbnr.zzfu();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjc.3 */
    class C14053 implements OnGlobalLayoutListener {
        final /* synthetic */ zzjc zzciv;
        final /* synthetic */ WeakReference zzcix;

        C14053(zzjc com_google_android_gms_internal_zzjc, WeakReference weakReference) {
            this.zzciv = com_google_android_gms_internal_zzjc;
            this.zzcix = weakReference;
        }

        public void onGlobalLayout() {
            this.zzciv.zza(this.zzcix, false);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjc.4 */
    class C14064 implements OnScrollChangedListener {
        final /* synthetic */ zzjc zzciv;
        final /* synthetic */ WeakReference zzcix;

        C14064(zzjc com_google_android_gms_internal_zzjc, WeakReference weakReference) {
            this.zzciv = com_google_android_gms_internal_zzjc;
            this.zzcix = weakReference;
        }

        public void onScrollChanged() {
            this.zzciv.zza(this.zzcix, true);
        }
    }

    public zzjc(Context context, zzav com_google_android_gms_internal_zzav, zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz, zzq com_google_android_gms_ads_internal_zzq) {
        this.zzako = new Object();
        this.zzasl = -1;
        this.zzasm = -1;
        this.mContext = context;
        this.zzbnx = com_google_android_gms_internal_zzav;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzalt = com_google_android_gms_internal_zzdz;
        this.zzbnr = com_google_android_gms_ads_internal_zzq;
        this.zzasn = new zzlm(200);
    }

    private OnGlobalLayoutListener zza(WeakReference<zzmd> weakReference) {
        if (this.zzcir == null) {
            this.zzcir = new C14053(this, weakReference);
        }
        return this.zzcir;
    }

    private void zza(WeakReference<zzmd> weakReference, boolean z) {
        if (weakReference != null) {
            zzmd com_google_android_gms_internal_zzmd = (zzmd) weakReference.get();
            if (com_google_android_gms_internal_zzmd != null && com_google_android_gms_internal_zzmd.getView() != null) {
                if (!z || this.zzasn.tryAcquire()) {
                    int[] iArr = new int[2];
                    com_google_android_gms_internal_zzmd.getView().getLocationOnScreen(iArr);
                    int zzc = zzm.zzkr().zzc(this.mContext, iArr[0]);
                    int zzc2 = zzm.zzkr().zzc(this.mContext, iArr[1]);
                    synchronized (this.zzako) {
                        if (!(this.zzasl == zzc && this.zzasm == zzc2)) {
                            this.zzasl = zzc;
                            this.zzasm = zzc2;
                            com_google_android_gms_internal_zzmd.zzxc().zza(this.zzasl, this.zzasm, !z);
                        }
                    }
                }
            }
        }
    }

    private OnScrollChangedListener zzb(WeakReference<zzmd> weakReference) {
        if (this.zzcis == null) {
            this.zzcis = new C14064(this, weakReference);
        }
        return this.zzcis;
    }

    private void zzj(zzmd com_google_android_gms_internal_zzmd) {
        zzme zzxc = com_google_android_gms_internal_zzmd.zzxc();
        zzxc.zza("/video", zzfd.zzbpw);
        zzxc.zza("/videoMeta", zzfd.zzbpx);
        zzxc.zza("/precache", zzfd.zzbpy);
        zzxc.zza("/delayPageLoaded", zzfd.zzbqb);
        zzxc.zza("/instrument", zzfd.zzbpz);
        zzxc.zza("/log", zzfd.zzbpr);
        zzxc.zza("/videoClicked", zzfd.zzbps);
        zzxc.zza("/trackActiveViewUnit", new C14042(this));
    }

    public zzlt<zzmd> zzg(JSONObject jSONObject) {
        zzlt com_google_android_gms_internal_zzlq = new zzlq();
        zzu.zzgm().runOnUiThread(new C14031(this, jSONObject, com_google_android_gms_internal_zzlq));
        return com_google_android_gms_internal_zzlq;
    }

    zzmd zzsx() {
        return zzu.zzgn().zza(this.mContext, AdSizeParcel.zzj(this.mContext), false, false, this.zzbnx, this.zzcgf.zzcmx.zzari, this.zzalt, null, this.zzbnr.zzec());
    }
}
