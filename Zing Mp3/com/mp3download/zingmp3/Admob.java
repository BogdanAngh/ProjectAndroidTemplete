package com.mp3download.zingmp3;

import android.content.Context;
import android.view.View;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest.Builder;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;

public class Admob {
    private static AdView mAdView;
    public static InterstitialAd mInterstitial;

    /* renamed from: com.mp3download.zingmp3.Admob.1 */
    static class C15621 extends AdListener {
        C15621() {
        }

        public void onAdLoaded() {
            Admob.showInterstitial();
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }

        public void onAdOpened() {
            super.onAdOpened();
        }

        public void onAdClosed() {
            super.onAdClosed();
        }

        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }
    }

    /* renamed from: com.mp3download.zingmp3.Admob.2 */
    static class C15632 extends AdListener {
        C15632() {
        }

        public void onAdLoaded() {
            super.onAdLoaded();
        }

        public void onAdClosed() {
            super.onAdClosed();
        }

        public void onAdOpened() {
            super.onAdOpened();
        }

        public void onAdLeftApplication() {
            super.onAdLeftApplication();
        }

        public void onAdFailedToLoad(int errorCode) {
            super.onAdFailedToLoad(errorCode);
        }
    }

    public static void createLoadInterstitial(Context context, View view) {
        mInterstitial = new InterstitialAd(context);
        mInterstitial.setAdUnitId(context.getResources().getString(C1569R.string.admob_showIntersitial_ad_unit_id));
        mInterstitial.setAdListener(new C15621());
        loadInterstitial();
    }

    public static void loadInterstitial() {
        mInterstitial.loadAd(new Builder().build());
    }

    public static void showInterstitial() {
        if (mInterstitial.isLoaded()) {
            mInterstitial.show();
        }
    }

    public static void createLoadBanner(Context context, View view) {
        mAdView = (AdView) view.findViewById(C1569R.id.ad_view);
        mAdView.loadAd(new Builder().build());
        mAdView.setAdListener(new C15632());
    }
}
