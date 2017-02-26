package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup.LayoutParams;

/* renamed from: com.facebook.ads.internal.i.s */
public class C0746s extends View {
    private C0396r f1243a;

    public C0746s(Context context, C0396r c0396r) {
        super(context);
        this.f1243a = c0396r;
        setLayoutParams(new LayoutParams(0, 0));
    }

    public void onWindowVisibilityChanged(int i) {
        if (this.f1243a != null) {
            this.f1243a.m139a(i);
        }
    }
}
