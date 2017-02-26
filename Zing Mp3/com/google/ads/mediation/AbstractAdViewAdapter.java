package com.google.ads.mediation;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdLoader.Builder;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.NativeAdView;
import com.google.android.gms.ads.formats.NativeAppInstallAd;
import com.google.android.gms.ads.formats.NativeAppInstallAd.OnAppInstallAdLoadedListener;
import com.google.android.gms.ads.formats.NativeContentAd;
import com.google.android.gms.ads.formats.NativeContentAd.OnContentAdLoadedListener;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.mediation.MediationAdRequest;
import com.google.android.gms.ads.mediation.MediationBannerAdapter;
import com.google.android.gms.ads.mediation.MediationBannerListener;
import com.google.android.gms.ads.mediation.MediationInterstitialListener;
import com.google.android.gms.ads.mediation.MediationNativeAdapter;
import com.google.android.gms.ads.mediation.MediationNativeListener;
import com.google.android.gms.ads.mediation.NativeAppInstallAdMapper;
import com.google.android.gms.ads.mediation.NativeContentAdMapper;
import com.google.android.gms.ads.mediation.NativeMediationAdRequest;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdAdapter;
import com.google.android.gms.ads.reward.mediation.MediationRewardedVideoAdListener;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzmr;
import java.util.Date;
import java.util.Set;

@zzji
public abstract class AbstractAdViewAdapter implements MediationBannerAdapter, MediationNativeAdapter, MediationRewardedVideoAdAdapter, zzmr {
    public static final String AD_UNIT_ID_PARAMETER = "pubid";
    protected AdView zzgd;
    protected InterstitialAd zzge;
    private AdLoader zzgf;
    private Context zzgg;
    private InterstitialAd zzgh;
    private MediationRewardedVideoAdListener zzgi;
    final RewardedVideoAdListener zzgj;

    /* renamed from: com.google.ads.mediation.AbstractAdViewAdapter.1 */
    class C09881 implements RewardedVideoAdListener {
        final /* synthetic */ AbstractAdViewAdapter zzgk;

        C09881(AbstractAdViewAdapter abstractAdViewAdapter) {
            this.zzgk = abstractAdViewAdapter;
        }

        public void onRewarded(RewardItem rewardItem) {
            this.zzgk.zzgi.onRewarded(this.zzgk, rewardItem);
        }

        public void onRewardedVideoAdClosed() {
            this.zzgk.zzgi.onAdClosed(this.zzgk);
            this.zzgk.zzgh = null;
        }

        public void onRewardedVideoAdFailedToLoad(int i) {
            this.zzgk.zzgi.onAdFailedToLoad(this.zzgk, i);
        }

        public void onRewardedVideoAdLeftApplication() {
            this.zzgk.zzgi.onAdLeftApplication(this.zzgk);
        }

        public void onRewardedVideoAdLoaded() {
            this.zzgk.zzgi.onAdLoaded(this.zzgk);
        }

        public void onRewardedVideoAdOpened() {
            this.zzgk.zzgi.onAdOpened(this.zzgk);
        }

        public void onRewardedVideoStarted() {
            this.zzgk.zzgi.onVideoStarted(this.zzgk);
        }
    }

    static class zza extends NativeAppInstallAdMapper {
        private final NativeAppInstallAd zzgl;

        public zza(NativeAppInstallAd nativeAppInstallAd) {
            this.zzgl = nativeAppInstallAd;
            setHeadline(nativeAppInstallAd.getHeadline().toString());
            setImages(nativeAppInstallAd.getImages());
            setBody(nativeAppInstallAd.getBody().toString());
            setIcon(nativeAppInstallAd.getIcon());
            setCallToAction(nativeAppInstallAd.getCallToAction().toString());
            if (nativeAppInstallAd.getStarRating() != null) {
                setStarRating(nativeAppInstallAd.getStarRating().doubleValue());
            }
            if (nativeAppInstallAd.getStore() != null) {
                setStore(nativeAppInstallAd.getStore().toString());
            }
            if (nativeAppInstallAd.getPrice() != null) {
                setPrice(nativeAppInstallAd.getPrice().toString());
            }
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
            zza(nativeAppInstallAd.getVideoController());
        }

        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzgl);
            }
        }
    }

    static class zzb extends NativeContentAdMapper {
        private final NativeContentAd zzgm;

        public zzb(NativeContentAd nativeContentAd) {
            this.zzgm = nativeContentAd;
            setHeadline(nativeContentAd.getHeadline().toString());
            setImages(nativeContentAd.getImages());
            setBody(nativeContentAd.getBody().toString());
            if (nativeContentAd.getLogo() != null) {
                setLogo(nativeContentAd.getLogo());
            }
            setCallToAction(nativeContentAd.getCallToAction().toString());
            setAdvertiser(nativeContentAd.getAdvertiser().toString());
            setOverrideImpressionRecording(true);
            setOverrideClickHandling(true);
        }

        public void trackView(View view) {
            if (view instanceof NativeAdView) {
                ((NativeAdView) view).setNativeAd(this.zzgm);
            }
        }
    }

    static final class zzc extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzgn;
        final MediationBannerListener zzgo;

        public zzc(AbstractAdViewAdapter abstractAdViewAdapter, MediationBannerListener mediationBannerListener) {
            this.zzgn = abstractAdViewAdapter;
            this.zzgo = mediationBannerListener;
        }

        public void onAdClicked() {
            this.zzgo.onAdClicked(this.zzgn);
        }

        public void onAdClosed() {
            this.zzgo.onAdClosed(this.zzgn);
        }

        public void onAdFailedToLoad(int i) {
            this.zzgo.onAdFailedToLoad(this.zzgn, i);
        }

        public void onAdLeftApplication() {
            this.zzgo.onAdLeftApplication(this.zzgn);
        }

        public void onAdLoaded() {
            this.zzgo.onAdLoaded(this.zzgn);
        }

        public void onAdOpened() {
            this.zzgo.onAdOpened(this.zzgn);
        }
    }

    static final class zzd extends AdListener implements com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzgn;
        final MediationInterstitialListener zzgp;

        public zzd(AbstractAdViewAdapter abstractAdViewAdapter, MediationInterstitialListener mediationInterstitialListener) {
            this.zzgn = abstractAdViewAdapter;
            this.zzgp = mediationInterstitialListener;
        }

        public void onAdClicked() {
            this.zzgp.onAdClicked(this.zzgn);
        }

        public void onAdClosed() {
            this.zzgp.onAdClosed(this.zzgn);
        }

        public void onAdFailedToLoad(int i) {
            this.zzgp.onAdFailedToLoad(this.zzgn, i);
        }

        public void onAdLeftApplication() {
            this.zzgp.onAdLeftApplication(this.zzgn);
        }

        public void onAdLoaded() {
            this.zzgp.onAdLoaded(this.zzgn);
        }

        public void onAdOpened() {
            this.zzgp.onAdOpened(this.zzgn);
        }
    }

    static final class zze extends AdListener implements OnAppInstallAdLoadedListener, OnContentAdLoadedListener, com.google.android.gms.ads.internal.client.zza {
        final AbstractAdViewAdapter zzgn;
        final MediationNativeListener zzgq;

        public zze(AbstractAdViewAdapter abstractAdViewAdapter, MediationNativeListener mediationNativeListener) {
            this.zzgn = abstractAdViewAdapter;
            this.zzgq = mediationNativeListener;
        }

        public void onAdClicked() {
            this.zzgq.onAdClicked(this.zzgn);
        }

        public void onAdClosed() {
            this.zzgq.onAdClosed(this.zzgn);
        }

        public void onAdFailedToLoad(int i) {
            this.zzgq.onAdFailedToLoad(this.zzgn, i);
        }

        public void onAdLeftApplication() {
            this.zzgq.onAdLeftApplication(this.zzgn);
        }

        public void onAdLoaded() {
        }

        public void onAdOpened() {
            this.zzgq.onAdOpened(this.zzgn);
        }

        public void onAppInstallAdLoaded(NativeAppInstallAd nativeAppInstallAd) {
            this.zzgq.onAdLoaded(this.zzgn, new zza(nativeAppInstallAd));
        }

        public void onContentAdLoaded(NativeContentAd nativeContentAd) {
            this.zzgq.onAdLoaded(this.zzgn, new zzb(nativeContentAd));
        }
    }

    public AbstractAdViewAdapter() {
        this.zzgj = new C09881(this);
    }

    public String getAdUnitId(Bundle bundle) {
        return bundle.getString(AD_UNIT_ID_PARAMETER);
    }

    public View getBannerView() {
        return this.zzgd;
    }

    public Bundle getInterstitialAdapterInfo() {
        return new com.google.android.gms.ads.mediation.MediationAdapter.zza().zzbk(1).zzys();
    }

    public void initialize(Context context, MediationAdRequest mediationAdRequest, String str, MediationRewardedVideoAdListener mediationRewardedVideoAdListener, Bundle bundle, Bundle bundle2) {
        this.zzgg = context.getApplicationContext();
        this.zzgi = mediationRewardedVideoAdListener;
        this.zzgi.onInitializationSucceeded(this);
    }

    public boolean isInitialized() {
        return this.zzgi != null;
    }

    public void loadAd(MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        if (this.zzgg == null || this.zzgi == null) {
            com.google.android.gms.ads.internal.util.client.zzb.m1695e("AdMobAdapter.loadAd called before initialize.");
            return;
        }
        this.zzgh = new InterstitialAd(this.zzgg);
        this.zzgh.zzd(true);
        this.zzgh.setAdUnitId(getAdUnitId(bundle));
        this.zzgh.setRewardedVideoAdListener(this.zzgj);
        this.zzgh.loadAd(zza(this.zzgg, mediationAdRequest, bundle2, bundle));
    }

    public void onDestroy() {
        if (this.zzgd != null) {
            this.zzgd.destroy();
            this.zzgd = null;
        }
        if (this.zzge != null) {
            this.zzge = null;
        }
        if (this.zzgf != null) {
            this.zzgf = null;
        }
        if (this.zzgh != null) {
            this.zzgh = null;
        }
    }

    public void onPause() {
        if (this.zzgd != null) {
            this.zzgd.pause();
        }
    }

    public void onResume() {
        if (this.zzgd != null) {
            this.zzgd.resume();
        }
    }

    public void requestBannerAd(Context context, MediationBannerListener mediationBannerListener, Bundle bundle, AdSize adSize, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.zzgd = new AdView(context);
        this.zzgd.setAdSize(new AdSize(adSize.getWidth(), adSize.getHeight()));
        this.zzgd.setAdUnitId(getAdUnitId(bundle));
        this.zzgd.setAdListener(new zzc(this, mediationBannerListener));
        this.zzgd.loadAd(zza(context, mediationAdRequest, bundle2, bundle));
    }

    public void requestInterstitialAd(Context context, MediationInterstitialListener mediationInterstitialListener, Bundle bundle, MediationAdRequest mediationAdRequest, Bundle bundle2) {
        this.zzge = new InterstitialAd(context);
        this.zzge.setAdUnitId(getAdUnitId(bundle));
        this.zzge.setAdListener(new zzd(this, mediationInterstitialListener));
        this.zzge.loadAd(zza(context, mediationAdRequest, bundle2, bundle));
    }

    public void requestNativeAd(Context context, MediationNativeListener mediationNativeListener, Bundle bundle, NativeMediationAdRequest nativeMediationAdRequest, Bundle bundle2) {
        OnContentAdLoadedListener com_google_ads_mediation_AbstractAdViewAdapter_zze = new zze(this, mediationNativeListener);
        Builder withAdListener = zza(context, bundle.getString(AD_UNIT_ID_PARAMETER)).withAdListener(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        NativeAdOptions nativeAdOptions = nativeMediationAdRequest.getNativeAdOptions();
        if (nativeAdOptions != null) {
            withAdListener.withNativeAdOptions(nativeAdOptions);
        }
        if (nativeMediationAdRequest.isAppInstallAdRequested()) {
            withAdListener.forAppInstallAd(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        }
        if (nativeMediationAdRequest.isContentAdRequested()) {
            withAdListener.forContentAd(com_google_ads_mediation_AbstractAdViewAdapter_zze);
        }
        this.zzgf = withAdListener.build();
        this.zzgf.loadAd(zza(context, nativeMediationAdRequest, bundle2, bundle));
    }

    public void showInterstitial() {
        this.zzge.show();
    }

    public void showVideo() {
        this.zzgh.show();
    }

    protected abstract Bundle zza(Bundle bundle, Bundle bundle2);

    Builder zza(Context context, String str) {
        return new Builder(context, str);
    }

    AdRequest zza(Context context, MediationAdRequest mediationAdRequest, Bundle bundle, Bundle bundle2) {
        AdRequest.Builder builder = new AdRequest.Builder();
        Date birthday = mediationAdRequest.getBirthday();
        if (birthday != null) {
            builder.setBirthday(birthday);
        }
        int gender = mediationAdRequest.getGender();
        if (gender != 0) {
            builder.setGender(gender);
        }
        Set<String> keywords = mediationAdRequest.getKeywords();
        if (keywords != null) {
            for (String addKeyword : keywords) {
                builder.addKeyword(addKeyword);
            }
        }
        Location location = mediationAdRequest.getLocation();
        if (location != null) {
            builder.setLocation(location);
        }
        if (mediationAdRequest.isTesting()) {
            builder.addTestDevice(zzm.zzkr().zzao(context));
        }
        if (mediationAdRequest.taggedForChildDirectedTreatment() != -1) {
            builder.tagForChildDirectedTreatment(mediationAdRequest.taggedForChildDirectedTreatment() == 1);
        }
        builder.setIsDesignedForFamilies(mediationAdRequest.isDesignedForFamilies());
        builder.addNetworkExtrasBundle(AdMobAdapter.class, zza(bundle, bundle2));
        return builder.build();
    }
}
