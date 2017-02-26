package com.facebook.ads.internal;

import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.mp3download.zingmp3.C1569R;

/* renamed from: com.facebook.ads.internal.e */
public enum C0523e {
    UNKNOWN(0),
    WEBVIEW_BANNER_LEGACY(4),
    WEBVIEW_BANNER_50(5),
    WEBVIEW_BANNER_90(6),
    WEBVIEW_BANNER_250(7),
    WEBVIEW_INTERSTITIAL_UNKNOWN(100),
    WEBVIEW_INTERSTITIAL_HORIZONTAL(C1569R.styleable.AppCompatTheme_autoCompleteTextViewStyle),
    WEBVIEW_INTERSTITIAL_VERTICAL(C1569R.styleable.AppCompatTheme_buttonStyle),
    WEBVIEW_INTERSTITIAL_TABLET(C1569R.styleable.AppCompatTheme_buttonStyleSmall),
    NATIVE_UNKNOWN(Callback.DEFAULT_DRAG_ANIMATION_DURATION),
    REWARDED_VIDEO(400),
    NATIVE_250(201),
    INSTREAM_VIDEO(300);
    
    private final int f723n;

    private C0523e(int i) {
        this.f723n = i;
    }

    public int m837a() {
        return this.f723n;
    }
}
