package com.facebook.ads;

import android.content.Context;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.adapters.C0449g;
import com.facebook.ads.internal.p000i.C0710e;
import com.facebook.ads.internal.p000i.C0727i;
import com.facebook.ads.internal.p000i.p016c.C0634c;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.C0795p;

public class MediaView extends RelativeLayout {
    private static final String f97a;
    private static final int f98b;
    @Nullable
    private MediaViewListener f99c;
    @NonNull
    private final C0710e f100d;
    @NonNull
    private final C0727i f101e;
    @NonNull
    private final C0634c f102f;
    private boolean f103g;
    @Deprecated
    private boolean f104h;

    static {
        f97a = MediaView.class.getSimpleName();
        f98b = Color.argb(51, 145, 150, 165);
    }

    public MediaView(Context context) {
        this(context, null);
    }

    public MediaView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.f103g = false;
        this.f104h = true;
        setBackgroundColor(f98b);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        this.f100d = new C0710e(context);
        this.f100d.setVisibility(8);
        addView(this.f100d, layoutParams);
        this.f101e = new C0727i(context, this, getAdProvider());
        this.f101e.setVisibility(8);
        layoutParams.addRule(13);
        addView(this.f101e, layoutParams);
        float f = context.getResources().getDisplayMetrics().density;
        int round = Math.round(4.0f * f);
        int round2 = Math.round(f * 12.0f);
        this.f102f = new C0634c(getContext());
        this.f102f.setChildSpacing(round);
        this.f102f.setPadding(0, round2, 0, round2);
        this.f102f.setVisibility(8);
        addView(this.f102f, layoutParams);
    }

    private boolean m130a(NativeAd nativeAd) {
        return VERSION.SDK_INT >= 14 && !TextUtils.isEmpty(nativeAd.m194c());
    }

    private boolean m131b(NativeAd nativeAd) {
        if (nativeAd.m198g() == null) {
            return false;
        }
        for (NativeAd adCoverImage : nativeAd.m198g()) {
            if (adCoverImage.getAdCoverImage() == null) {
                return false;
            }
        }
        return true;
    }

    protected C0783h getAdProvider() {
        return new C0783h();
    }

    @Deprecated
    public boolean isAutoplay() {
        return this.f104h;
    }

    @Deprecated
    public void setAutoplay(boolean z) {
        this.f104h = z;
        this.f101e.setAutoplay(z);
    }

    @Deprecated
    public void setAutoplayOnMobile(boolean z) {
        this.f101e.setIsAutoplayOnMobile(z);
    }

    public void setListener(@NonNull MediaViewListener mediaViewListener) {
        this.f99c = mediaViewListener;
        this.f101e.setListener(mediaViewListener);
    }

    public void setNativeAd(NativeAd nativeAd) {
        nativeAd.m193b(true);
        nativeAd.setMediaViewAutoplay(this.f104h);
        if (this.f103g) {
            this.f100d.m1337a(null, null);
            this.f103g = false;
        }
        String url = nativeAd.getAdCoverImage() != null ? nativeAd.getAdCoverImage().getUrl() : null;
        if (m131b(nativeAd)) {
            this.f100d.setVisibility(8);
            this.f101e.setVisibility(8);
            this.f102f.setVisibility(0);
            bringChildToFront(this.f102f);
            this.f102f.setCurrentPosition(0);
            this.f102f.setAdapter(new C0449g(this.f102f, nativeAd.m198g()));
        } else if (m130a(nativeAd)) {
            String c = nativeAd.m194c();
            String d = nativeAd.m195d();
            this.f101e.setImage(null);
            this.f100d.setVisibility(8);
            this.f101e.setVisibility(0);
            this.f102f.setVisibility(8);
            bringChildToFront(this.f101e);
            this.f103g = true;
            this.f101e.setAutoplay(this.f104h);
            this.f101e.setIsAutoPlayFromServer(nativeAd.m197f());
            if (url != null) {
                this.f101e.setImage(url);
            }
            this.f101e.setVideoReportURI(nativeAd.m196e());
            this.f101e.setVideoMPD(d);
            this.f101e.setVideoURI(c);
        } else if (url != null) {
            this.f100d.setVisibility(0);
            this.f101e.setVisibility(8);
            this.f102f.setVisibility(8);
            bringChildToFront(this.f100d);
            this.f103g = true;
            new C0795p(this.f100d).m1663a(url);
        }
    }
}
