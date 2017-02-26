package com.facebook.ads;

import android.content.Context;
import android.view.View;
import com.facebook.ads.internal.p000i.C0620a;
import com.mp3download.zingmp3.C1569R;

public class NativeAdView {

    public enum Type {
        HEIGHT_100(-1, 100),
        HEIGHT_120(-1, 120),
        HEIGHT_300(-1, 300),
        HEIGHT_400(-1, 400);
        
        private final int f172a;
        private final int f173b;

        private Type(int i, int i2) {
            this.f172a = i;
            this.f173b = i2;
        }

        public int getHeight() {
            return this.f173b;
        }

        public int getValue() {
            switch (this.f173b) {
                case C1569R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                    return 1;
                case 120:
                    return 2;
                case 300:
                    return 3;
                case 400:
                    return 4;
                default:
                    return -1;
            }
        }

        public int getWidth() {
            return this.f172a;
        }
    }

    public static View render(Context context, NativeAd nativeAd, Type type) {
        return render(context, nativeAd, type, null);
    }

    public static View render(Context context, NativeAd nativeAd, Type type, NativeAdViewAttributes nativeAdViewAttributes) {
        if (nativeAd.isNativeConfigEnabled()) {
            nativeAdViewAttributes = nativeAd.getAdViewAttributes();
        } else if (nativeAdViewAttributes == null) {
            nativeAdViewAttributes = new NativeAdViewAttributes();
        }
        nativeAd.m190a(type);
        return new C0620a(context, nativeAd, type, nativeAdViewAttributes);
    }
}
