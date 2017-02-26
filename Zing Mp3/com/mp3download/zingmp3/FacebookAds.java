package com.mp3download.zingmp3;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdListener;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;

public class FacebookAds {
    public static AdView adView;
    public static RelativeLayout adViewContainer;
    public static Context cnt;
    public static InterstitialAd interstitialAd;

    /* renamed from: com.mp3download.zingmp3.FacebookAds.1 */
    static class C15641 implements AdListener {
        final /* synthetic */ Context val$context;
        final /* synthetic */ View val$view;

        C15641(Context context, View view) {
            this.val$context = context;
            this.val$view = view;
        }

        public void onError(Ad ad, AdError adError) {
            Admob.createLoadBanner(this.val$context, this.val$view);
        }

        public void onAdLoaded(Ad ad) {
        }

        public void onAdClicked(Ad ad) {
        }
    }

    /* renamed from: com.mp3download.zingmp3.FacebookAds.2 */
    static class C15652 implements InterstitialAdListener {
        C15652() {
        }

        public void onInterstitialDisplayed(Ad ad) {
        }

        public void onInterstitialDismissed(Ad ad) {
        }

        public void onError(Ad ad, AdError adError) {
            Admob.createLoadInterstitial(FacebookAds.cnt, null);
        }

        public void onAdLoaded(Ad ad) {
            FacebookAds.interstitialAd.show();
        }

        public void onAdClicked(Ad ad) {
        }
    }

    public static void facebookLoadBanner(Context context, View view) {
        adViewContainer = (RelativeLayout) view.findViewById(C1569R.id.adViewContainer);
        adView = new AdView(context, context.getResources().getString(C1569R.string.facebook_banner), AdSize.BANNER_320_50);
        adViewContainer.addView(adView);
        adView.loadAd();
        cnt = context;
        adView.setAdListener(new C15641(context, view));
    }

    public static void facebookInterstitialAd(Activity context) {
        interstitialAd = new InterstitialAd(context, context.getResources().getString(C1569R.string.facebook_Intersitial));
        interstitialAd.setAdListener(new C15652());
        interstitialAd.loadAd();
        cnt = context;
    }
}
