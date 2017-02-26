package com.facebook.ads.internal.p000i.p014a;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.text.TextUtils.TruncateAt;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.ads.internal.util.C0796r;
import com.facebook.ads.internal.util.C0798t;

@TargetApi(19)
/* renamed from: com.facebook.ads.internal.i.a.c */
public class C0614c extends LinearLayout {
    private TextView f892a;
    private TextView f893b;
    private Drawable f894c;

    public C0614c(Context context) {
        super(context);
        m1144a();
    }

    private void m1144a() {
        float f = getResources().getDisplayMetrics().density;
        setOrientation(1);
        this.f892a = new TextView(getContext());
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -2);
        this.f892a.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.f892a.setTextSize(2, 20.0f);
        this.f892a.setEllipsize(TruncateAt.END);
        this.f892a.setSingleLine(true);
        this.f892a.setVisibility(8);
        addView(this.f892a, layoutParams);
        this.f893b = new TextView(getContext());
        layoutParams = new LinearLayout.LayoutParams(-1, -2);
        this.f893b.setAlpha(0.5f);
        this.f893b.setTextColor(ViewCompat.MEASURED_STATE_MASK);
        this.f893b.setTextSize(2, 15.0f);
        this.f893b.setCompoundDrawablePadding((int) (f * 5.0f));
        this.f893b.setEllipsize(TruncateAt.END);
        this.f893b.setSingleLine(true);
        this.f893b.setVisibility(8);
        addView(this.f893b, layoutParams);
    }

    private Drawable getPadlockDrawable() {
        if (this.f894c == null) {
            this.f894c = C0798t.m1672b(getContext(), C0796r.BROWSER_PADLOCK);
        }
        return this.f894c;
    }

    public void setSubtitle(String str) {
        if (TextUtils.isEmpty(str)) {
            this.f893b.setText(null);
            this.f893b.setVisibility(8);
            return;
        }
        Uri parse = Uri.parse(str);
        this.f893b.setText(parse.getHost());
        this.f893b.setCompoundDrawablesRelativeWithIntrinsicBounds("https".equals(parse.getScheme()) ? getPadlockDrawable() : null, null, null, null);
        this.f893b.setVisibility(0);
    }

    public void setTitle(String str) {
        if (TextUtils.isEmpty(str)) {
            this.f892a.setText(null);
            this.f892a.setVisibility(8);
            return;
        }
        this.f892a.setText(str);
        this.f892a.setVisibility(0);
    }
}
