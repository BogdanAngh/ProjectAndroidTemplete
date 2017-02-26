package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import com.facebook.ads.internal.p000i.C0726m;

/* renamed from: com.facebook.ads.internal.i.e.b.m */
public abstract class C0638m extends RelativeLayout {
    static final /* synthetic */ boolean f949a;
    private C0726m f950b;

    static {
        f949a = !C0638m.class.desiredAssertionStatus();
    }

    public C0638m(Context context) {
        super(context);
    }

    public C0638m(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        setLayoutParams(new LayoutParams(-1, -1));
    }

    protected void m1199a(@NonNull C0726m c0726m) {
    }

    public void m1200b(C0726m c0726m) {
        this.f950b = c0726m;
        m1199a(c0726m);
    }

    @NonNull
    protected C0726m getVideoView() {
        if (f949a || this.f950b != null) {
            return this.f950b;
        }
        throw new AssertionError();
    }
}
