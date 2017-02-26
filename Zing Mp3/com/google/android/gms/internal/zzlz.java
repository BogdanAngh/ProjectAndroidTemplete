package com.google.android.gms.internal;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.google.android.gms.ads.internal.zzu;
import java.lang.ref.WeakReference;

@zzji
class zzlz extends zzmb implements OnGlobalLayoutListener {
    private final WeakReference<OnGlobalLayoutListener> zzcyx;

    public zzlz(View view, OnGlobalLayoutListener onGlobalLayoutListener) {
        super(view);
        this.zzcyx = new WeakReference(onGlobalLayoutListener);
    }

    public void onGlobalLayout() {
        OnGlobalLayoutListener onGlobalLayoutListener = (OnGlobalLayoutListener) this.zzcyx.get();
        if (onGlobalLayoutListener != null) {
            onGlobalLayoutListener.onGlobalLayout();
        } else {
            detach();
        }
    }

    protected void zza(ViewTreeObserver viewTreeObserver) {
        viewTreeObserver.addOnGlobalLayoutListener(this);
    }

    protected void zzb(ViewTreeObserver viewTreeObserver) {
        zzu.zzgo().zza(viewTreeObserver, (OnGlobalLayoutListener) this);
    }
}
