package com.facebook.ads.internal.adapters;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.content.LocalBroadcastManager;
import com.facebook.ads.AdError;

/* renamed from: com.facebook.ads.internal.adapters.s */
public class C0479s extends BroadcastReceiver {
    private String f507a;
    private Context f508b;
    private InterstitialAdapterListener f509c;
    private InterstitialAdapter f510d;

    public C0479s(Context context, String str, InterstitialAdapter interstitialAdapter, InterstitialAdapterListener interstitialAdapterListener) {
        this.f508b = context;
        this.f507a = str;
        this.f509c = interstitialAdapterListener;
        this.f510d = interstitialAdapter;
    }

    public void m686a() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.facebook.ads.interstitial.impression.logged:" + this.f507a);
        intentFilter.addAction("com.facebook.ads.interstitial.displayed:" + this.f507a);
        intentFilter.addAction("com.facebook.ads.interstitial.dismissed:" + this.f507a);
        intentFilter.addAction("com.facebook.ads.interstitial.clicked:" + this.f507a);
        intentFilter.addAction("com.facebook.ads.interstitial.error:" + this.f507a);
        LocalBroadcastManager.getInstance(this.f508b).registerReceiver(this, intentFilter);
    }

    public void m687b() {
        try {
            LocalBroadcastManager.getInstance(this.f508b).unregisterReceiver(this);
        } catch (Exception e) {
        }
    }

    public void onReceive(Context context, Intent intent) {
        Object obj = intent.getAction().split(":")[0];
        if (this.f509c != null && obj != null) {
            if ("com.facebook.ads.interstitial.clicked".equals(obj)) {
                this.f509c.onInterstitialAdClicked(this.f510d, null, true);
            } else if ("com.facebook.ads.interstitial.dismissed".equals(obj)) {
                this.f509c.onInterstitialAdDismissed(this.f510d);
            } else if ("com.facebook.ads.interstitial.displayed".equals(obj)) {
                this.f509c.onInterstitialAdDisplayed(this.f510d);
            } else if ("com.facebook.ads.interstitial.impression.logged".equals(obj)) {
                this.f509c.onInterstitialLoggingImpression(this.f510d);
            } else if ("com.facebook.ads.interstitial.error".equals(obj)) {
                this.f509c.onInterstitialError(this.f510d, AdError.INTERNAL_ERROR);
            }
        }
    }
}
