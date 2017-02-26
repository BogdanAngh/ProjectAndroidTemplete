package com.google.android.gms.ads.doubleclick;

import android.content.Context;
import android.support.annotation.RequiresPermission;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.Correlator;
import com.google.android.gms.ads.internal.client.zzae;
import com.google.android.gms.ads.internal.util.client.zzb;

public final class PublisherAdView extends ViewGroup {
    private final zzae zzakk;

    public PublisherAdView(Context context) {
        super(context);
        this.zzakk = new zzae(this);
    }

    public PublisherAdView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.zzakk = new zzae(this, attributeSet, true);
    }

    public PublisherAdView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.zzakk = new zzae(this, attributeSet, true);
    }

    public void destroy() {
        this.zzakk.destroy();
    }

    public AdListener getAdListener() {
        return this.zzakk.getAdListener();
    }

    public AdSize getAdSize() {
        return this.zzakk.getAdSize();
    }

    public AdSize[] getAdSizes() {
        return this.zzakk.getAdSizes();
    }

    public String getAdUnitId() {
        return this.zzakk.getAdUnitId();
    }

    public AppEventListener getAppEventListener() {
        return this.zzakk.getAppEventListener();
    }

    public String getMediationAdapterClassName() {
        return this.zzakk.getMediationAdapterClassName();
    }

    public OnCustomRenderedAdLoadedListener getOnCustomRenderedAdLoadedListener() {
        return this.zzakk.getOnCustomRenderedAdLoadedListener();
    }

    public boolean isLoading() {
        return this.zzakk.isLoading();
    }

    @RequiresPermission("android.permission.INTERNET")
    public void loadAd(PublisherAdRequest publisherAdRequest) {
        this.zzakk.zza(publisherAdRequest.zzdt());
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        View childAt = getChildAt(0);
        if (childAt != null && childAt.getVisibility() != 8) {
            int measuredWidth = childAt.getMeasuredWidth();
            int measuredHeight = childAt.getMeasuredHeight();
            int i5 = ((i3 - i) - measuredWidth) / 2;
            int i6 = ((i4 - i2) - measuredHeight) / 2;
            childAt.layout(i5, i6, measuredWidth + i5, measuredHeight + i6);
        }
    }

    protected void onMeasure(int i, int i2) {
        int widthInPixels;
        int i3 = 0;
        View childAt = getChildAt(0);
        if (childAt == null || childAt.getVisibility() == 8) {
            AdSize adSize;
            AdSize adSize2 = null;
            try {
                adSize = getAdSize();
            } catch (Throwable e) {
                zzb.zzb("Unable to retrieve ad size.", e);
                adSize = adSize2;
            }
            if (adSize != null) {
                Context context = getContext();
                widthInPixels = adSize.getWidthInPixels(context);
                i3 = adSize.getHeightInPixels(context);
            } else {
                widthInPixels = 0;
            }
        } else {
            measureChild(childAt, i, i2);
            widthInPixels = childAt.getMeasuredWidth();
            i3 = childAt.getMeasuredHeight();
        }
        setMeasuredDimension(View.resolveSize(Math.max(widthInPixels, getSuggestedMinimumWidth()), i), View.resolveSize(Math.max(i3, getSuggestedMinimumHeight()), i2));
    }

    public void pause() {
        this.zzakk.pause();
    }

    public void recordManualImpression() {
        this.zzakk.recordManualImpression();
    }

    public void resume() {
        this.zzakk.resume();
    }

    public void setAdListener(AdListener adListener) {
        this.zzakk.setAdListener(adListener);
    }

    public void setAdSizes(AdSize... adSizeArr) {
        if (adSizeArr == null || adSizeArr.length < 1) {
            throw new IllegalArgumentException("The supported ad sizes must contain at least one valid ad size.");
        }
        this.zzakk.zza(adSizeArr);
    }

    public void setAdUnitId(String str) {
        this.zzakk.setAdUnitId(str);
    }

    public void setAppEventListener(AppEventListener appEventListener) {
        this.zzakk.setAppEventListener(appEventListener);
    }

    public void setCorrelator(Correlator correlator) {
        this.zzakk.setCorrelator(correlator);
    }

    public void setManualImpressionsEnabled(boolean z) {
        this.zzakk.setManualImpressionsEnabled(z);
    }

    public void setOnCustomRenderedAdLoadedListener(OnCustomRenderedAdLoadedListener onCustomRenderedAdLoadedListener) {
        this.zzakk.setOnCustomRenderedAdLoadedListener(onCustomRenderedAdLoadedListener);
    }
}
