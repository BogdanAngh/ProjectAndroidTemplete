package com.google.android.gms.internal;

import android.os.Handler;
import android.os.RemoteException;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzl;
import com.google.android.gms.ads.internal.zzu;
import java.util.LinkedList;
import java.util.List;

@zzji
class zzfx {
    private final List<zza> zzani;

    interface zza {
        void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException;
    }

    /* renamed from: com.google.android.gms.internal.zzfx.1 */
    class C13161 extends com.google.android.gms.ads.internal.client.zzq.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.1.1 */
        class C13111 implements zza {
            final /* synthetic */ C13161 zzbrs;

            C13111(C13161 c13161) {
                this.zzbrs = c13161;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzanl != null) {
                    com_google_android_gms_internal_zzfy.zzanl.onAdClosed();
                }
                zzu.zzhb().zznm();
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.1.2 */
        class C13122 implements zza {
            final /* synthetic */ C13161 zzbrs;
            final /* synthetic */ int zzbrt;

            C13122(C13161 c13161, int i) {
                this.zzbrs = c13161;
                this.zzbrt = i;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzanl != null) {
                    com_google_android_gms_internal_zzfy.zzanl.onAdFailedToLoad(this.zzbrt);
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.1.3 */
        class C13133 implements zza {
            final /* synthetic */ C13161 zzbrs;

            C13133(C13161 c13161) {
                this.zzbrs = c13161;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzanl != null) {
                    com_google_android_gms_internal_zzfy.zzanl.onAdLeftApplication();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.1.4 */
        class C13144 implements zza {
            final /* synthetic */ C13161 zzbrs;

            C13144(C13161 c13161) {
                this.zzbrs = c13161;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzanl != null) {
                    com_google_android_gms_internal_zzfy.zzanl.onAdLoaded();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.1.5 */
        class C13155 implements zza {
            final /* synthetic */ C13161 zzbrs;

            C13155(C13161 c13161) {
                this.zzbrs = c13161;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzanl != null) {
                    com_google_android_gms_internal_zzfy.zzanl.onAdOpened();
                }
            }
        }

        C13161(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void onAdClosed() throws RemoteException {
            this.zzbrr.zzani.add(new C13111(this));
        }

        public void onAdFailedToLoad(int i) throws RemoteException {
            this.zzbrr.zzani.add(new C13122(this, i));
            zzkx.m1697v("Pooled interstitial failed to load.");
        }

        public void onAdLeftApplication() throws RemoteException {
            this.zzbrr.zzani.add(new C13133(this));
        }

        public void onAdLoaded() throws RemoteException {
            this.zzbrr.zzani.add(new C13144(this));
            zzkx.m1697v("Pooled interstitial loaded.");
        }

        public void onAdOpened() throws RemoteException {
            this.zzbrr.zzani.add(new C13155(this));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.2 */
    class C13182 extends com.google.android.gms.ads.internal.client.zzw.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.2.1 */
        class C13171 implements zza {
            final /* synthetic */ String val$name;
            final /* synthetic */ String zzbru;
            final /* synthetic */ C13182 zzbrv;

            C13171(C13182 c13182, String str, String str2) {
                this.zzbrv = c13182;
                this.val$name = str;
                this.zzbru = str2;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsf != null) {
                    com_google_android_gms_internal_zzfy.zzbsf.onAppEvent(this.val$name, this.zzbru);
                }
            }
        }

        C13182(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void onAppEvent(String str, String str2) throws RemoteException {
            this.zzbrr.zzani.add(new C13171(this, str, str2));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.3 */
    class C13203 extends com.google.android.gms.internal.zzig.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.3.1 */
        class C13191 implements zza {
            final /* synthetic */ zzif zzbrw;
            final /* synthetic */ C13203 zzbrx;

            C13191(C13203 c13203, zzif com_google_android_gms_internal_zzif) {
                this.zzbrx = c13203;
                this.zzbrw = com_google_android_gms_internal_zzif;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsg != null) {
                    com_google_android_gms_internal_zzfy.zzbsg.zza(this.zzbrw);
                }
            }
        }

        C13203(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void zza(zzif com_google_android_gms_internal_zzif) throws RemoteException {
            this.zzbrr.zzani.add(new C13191(this, com_google_android_gms_internal_zzif));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.4 */
    class C13224 extends com.google.android.gms.internal.zzed.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.4.1 */
        class C13211 implements zza {
            final /* synthetic */ zzec zzbry;
            final /* synthetic */ C13224 zzbrz;

            C13211(C13224 c13224, zzec com_google_android_gms_internal_zzec) {
                this.zzbrz = c13224;
                this.zzbry = com_google_android_gms_internal_zzec;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsh != null) {
                    com_google_android_gms_internal_zzfy.zzbsh.zza(this.zzbry);
                }
            }
        }

        C13224(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void zza(zzec com_google_android_gms_internal_zzec) throws RemoteException {
            this.zzbrr.zzani.add(new C13211(this, com_google_android_gms_internal_zzec));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.5 */
    class C13245 extends com.google.android.gms.ads.internal.client.zzp.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.5.1 */
        class C13231 implements zza {
            final /* synthetic */ C13245 zzbsa;

            C13231(C13245 c13245) {
                this.zzbsa = c13245;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsi != null) {
                    com_google_android_gms_internal_zzfy.zzbsi.onAdClicked();
                }
            }
        }

        C13245(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void onAdClicked() throws RemoteException {
            this.zzbrr.zzani.add(new C13231(this));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.6 */
    class C13326 extends com.google.android.gms.ads.internal.reward.client.zzd.zza {
        final /* synthetic */ zzfx zzbrr;

        /* renamed from: com.google.android.gms.internal.zzfx.6.1 */
        class C13251 implements zza {
            final /* synthetic */ C13326 zzbsb;

            C13251(C13326 c13326) {
                this.zzbsb = c13326;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoAdLoaded();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.2 */
        class C13262 implements zza {
            final /* synthetic */ C13326 zzbsb;

            C13262(C13326 c13326) {
                this.zzbsb = c13326;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoAdOpened();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.3 */
        class C13273 implements zza {
            final /* synthetic */ C13326 zzbsb;

            C13273(C13326 c13326) {
                this.zzbsb = c13326;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoStarted();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.4 */
        class C13284 implements zza {
            final /* synthetic */ C13326 zzbsb;

            C13284(C13326 c13326) {
                this.zzbsb = c13326;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoAdClosed();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.5 */
        class C13295 implements zza {
            final /* synthetic */ C13326 zzbsb;
            final /* synthetic */ com.google.android.gms.ads.internal.reward.client.zza zzbsc;

            C13295(C13326 c13326, com.google.android.gms.ads.internal.reward.client.zza com_google_android_gms_ads_internal_reward_client_zza) {
                this.zzbsb = c13326;
                this.zzbsc = com_google_android_gms_ads_internal_reward_client_zza;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.zza(this.zzbsc);
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.6 */
        class C13306 implements zza {
            final /* synthetic */ C13326 zzbsb;

            C13306(C13326 c13326) {
                this.zzbsb = c13326;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoAdLeftApplication();
                }
            }
        }

        /* renamed from: com.google.android.gms.internal.zzfx.6.7 */
        class C13317 implements zza {
            final /* synthetic */ int zzbrt;
            final /* synthetic */ C13326 zzbsb;

            C13317(C13326 c13326, int i) {
                this.zzbsb = c13326;
                this.zzbrt = i;
            }

            public void zzb(zzfy com_google_android_gms_internal_zzfy) throws RemoteException {
                if (com_google_android_gms_internal_zzfy.zzbsj != null) {
                    com_google_android_gms_internal_zzfy.zzbsj.onRewardedVideoAdFailedToLoad(this.zzbrt);
                }
            }
        }

        C13326(zzfx com_google_android_gms_internal_zzfx) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
        }

        public void onRewardedVideoAdClosed() throws RemoteException {
            this.zzbrr.zzani.add(new C13284(this));
        }

        public void onRewardedVideoAdFailedToLoad(int i) throws RemoteException {
            this.zzbrr.zzani.add(new C13317(this, i));
        }

        public void onRewardedVideoAdLeftApplication() throws RemoteException {
            this.zzbrr.zzani.add(new C13306(this));
        }

        public void onRewardedVideoAdLoaded() throws RemoteException {
            this.zzbrr.zzani.add(new C13251(this));
        }

        public void onRewardedVideoAdOpened() throws RemoteException {
            this.zzbrr.zzani.add(new C13262(this));
        }

        public void onRewardedVideoStarted() throws RemoteException {
            this.zzbrr.zzani.add(new C13273(this));
        }

        public void zza(com.google.android.gms.ads.internal.reward.client.zza com_google_android_gms_ads_internal_reward_client_zza) throws RemoteException {
            this.zzbrr.zzani.add(new C13295(this, com_google_android_gms_ads_internal_reward_client_zza));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfx.7 */
    class C13337 implements Runnable {
        final /* synthetic */ zzfx zzbrr;
        final /* synthetic */ zza zzbsd;
        final /* synthetic */ zzfy zzbse;

        C13337(zzfx com_google_android_gms_internal_zzfx, zza com_google_android_gms_internal_zzfx_zza, zzfy com_google_android_gms_internal_zzfy) {
            this.zzbrr = com_google_android_gms_internal_zzfx;
            this.zzbsd = com_google_android_gms_internal_zzfx_zza;
            this.zzbse = com_google_android_gms_internal_zzfy;
        }

        public void run() {
            try {
                this.zzbsd.zzb(this.zzbse);
            } catch (Throwable e) {
                zzb.zzc("Could not propagate interstitial ad event.", e);
            }
        }
    }

    zzfx() {
        this.zzani = new LinkedList();
    }

    void zza(zzfy com_google_android_gms_internal_zzfy) {
        Handler handler = zzlb.zzcvl;
        for (zza c13337 : this.zzani) {
            handler.post(new C13337(this, c13337, com_google_android_gms_internal_zzfy));
        }
        this.zzani.clear();
    }

    void zzc(zzl com_google_android_gms_ads_internal_zzl) {
        com_google_android_gms_ads_internal_zzl.zza(new C13161(this));
        com_google_android_gms_ads_internal_zzl.zza(new C13182(this));
        com_google_android_gms_ads_internal_zzl.zza(new C13203(this));
        com_google_android_gms_ads_internal_zzl.zza(new C13224(this));
        com_google_android_gms_ads_internal_zzl.zza(new C13245(this));
        com_google_android_gms_ads_internal_zzl.zza(new C13326(this));
    }
}
