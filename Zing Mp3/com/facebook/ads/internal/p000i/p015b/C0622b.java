package com.facebook.ads.internal.p000i.p015b;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdViewAttributes;
import com.facebook.ads.internal.p000i.C0728j;
import com.facebook.ads.internal.p000i.C0739n;

/* renamed from: com.facebook.ads.internal.i.b.b */
public class C0622b extends LinearLayout {
    private ImageView f912a;
    private C0621a f913b;
    private TextView f914c;
    private LinearLayout f915d;

    public C0622b(Context context, NativeAd nativeAd, NativeAdViewAttributes nativeAdViewAttributes, boolean z, int i) {
        super(context);
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        setVerticalGravity(16);
        setOrientation(1);
        View linearLayout = new LinearLayout(getContext());
        linearLayout.setOrientation(1);
        linearLayout.setGravity(16);
        LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
        layoutParams.setMargins(Math.round(15.0f * displayMetrics.density), Math.round(15.0f * displayMetrics.density), Math.round(15.0f * displayMetrics.density), Math.round(15.0f * displayMetrics.density));
        linearLayout.setLayoutParams(layoutParams);
        addView(linearLayout);
        this.f915d = new LinearLayout(getContext());
        layoutParams = new LinearLayout.LayoutParams(-1, 0);
        this.f915d.setOrientation(0);
        this.f915d.setGravity(16);
        layoutParams.weight = 3.0f;
        this.f915d.setLayoutParams(layoutParams);
        linearLayout.addView(this.f915d);
        this.f912a = new C0623c(getContext());
        int a = m1170a(z, i);
        LayoutParams layoutParams2 = new LinearLayout.LayoutParams(Math.round(((float) a) * displayMetrics.density), Math.round(((float) a) * displayMetrics.density));
        layoutParams2.setMargins(0, 0, Math.round(15.0f * displayMetrics.density), 0);
        this.f912a.setLayoutParams(layoutParams2);
        NativeAd.downloadAndDisplayImage(nativeAd.getAdIcon(), this.f912a);
        this.f915d.addView(this.f912a);
        View linearLayout2 = new LinearLayout(getContext());
        linearLayout2.setLayoutParams(new LinearLayout.LayoutParams(-1, -1));
        linearLayout2.setOrientation(0);
        linearLayout2.setGravity(16);
        this.f915d.addView(linearLayout2);
        this.f913b = new C0621a(getContext(), nativeAd, nativeAdViewAttributes);
        layoutParams2 = new LinearLayout.LayoutParams(-2, -1);
        layoutParams2.setMargins(0, 0, Math.round(15.0f * displayMetrics.density), 0);
        layoutParams2.weight = 0.5f;
        this.f913b.setLayoutParams(layoutParams2);
        linearLayout2.addView(this.f913b);
        this.f914c = new TextView(getContext());
        this.f914c.setPadding(Math.round(6.0f * displayMetrics.density), Math.round(6.0f * displayMetrics.density), Math.round(6.0f * displayMetrics.density), Math.round(6.0f * displayMetrics.density));
        this.f914c.setText(nativeAd.getAdCallToAction());
        this.f914c.setTextColor(nativeAdViewAttributes.getButtonTextColor());
        this.f914c.setTextSize(14.0f);
        this.f914c.setTypeface(nativeAdViewAttributes.getTypeface(), 1);
        this.f914c.setMaxLines(2);
        this.f914c.setEllipsize(TruncateAt.END);
        this.f914c.setGravity(17);
        Drawable gradientDrawable = new GradientDrawable();
        gradientDrawable.setColor(nativeAdViewAttributes.getButtonColor());
        gradientDrawable.setCornerRadius(displayMetrics.density * 5.0f);
        gradientDrawable.setStroke(1, nativeAdViewAttributes.getButtonBorderColor());
        this.f914c.setBackgroundDrawable(gradientDrawable);
        LayoutParams layoutParams3 = new LinearLayout.LayoutParams(-2, -2);
        layoutParams3.weight = 0.25f;
        this.f914c.setLayoutParams(layoutParams3);
        linearLayout2.addView(this.f914c);
        if (z) {
            View c0739n = new C0739n(getContext());
            c0739n.setText(nativeAd.getAdBody());
            C0728j.m1410b(c0739n, nativeAdViewAttributes);
            c0739n.setMinTextSize((float) (nativeAdViewAttributes.getDescriptionTextSize() - 1));
            layoutParams = new LinearLayout.LayoutParams(-1, 0);
            layoutParams.weight = 1.0f;
            c0739n.setLayoutParams(layoutParams);
            c0739n.setGravity(80);
            linearLayout.addView(c0739n);
        }
    }

    private int m1170a(boolean z, int i) {
        return (int) (((double) (i - 30)) * (3.0d / ((double) ((z ? 1 : 0) + 3))));
    }

    public TextView getCallToActionView() {
        return this.f914c;
    }

    public ImageView getIconView() {
        return this.f912a;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        TextView titleTextView = this.f913b.getTitleTextView();
        if (titleTextView.getLayout().getLineEnd(titleTextView.getLineCount() - 1) < this.f913b.getMinVisibleTitleCharacters()) {
            this.f915d.removeView(this.f912a);
            super.onMeasure(i, i2);
        }
    }
}
