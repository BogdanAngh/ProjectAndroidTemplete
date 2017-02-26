package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

/* renamed from: com.facebook.ads.internal.i.e */
public class C0710e extends LinearLayout {
    private Bitmap f1119a;
    private Bitmap f1120b;
    private ImageView f1121c;
    private ImageView f1122d;
    private ImageView f1123e;
    private Bitmap f1124f;
    private Bitmap f1125g;
    private int f1126h;
    private int f1127i;
    private int f1128j;
    private int f1129k;
    private int f1130l;
    private int f1131m;
    private double f1132n;
    private double f1133o;

    public C0710e(Context context) {
        super(context);
        m1335d();
    }

    private int m1330a(double d) {
        return (int) Math.round(((double) getWidth()) / d);
    }

    private void m1331a() {
        if (getHeight() > 0 && getWidth() > 0) {
            this.f1133o = ((double) getMeasuredWidth()) / ((double) getMeasuredHeight());
            this.f1132n = ((double) this.f1119a.getWidth()) / ((double) this.f1119a.getHeight());
            if (this.f1132n > this.f1133o) {
                m1333b();
            } else {
                m1334c();
            }
        }
    }

    private int m1332b(double d) {
        return (int) Math.round(((double) getHeight()) * d);
    }

    private void m1333b() {
        this.f1128j = m1330a(this.f1132n);
        this.f1129k = getWidth();
        this.f1126h = (int) Math.ceil((double) (((float) (getHeight() - this.f1128j)) / 2.0f));
        if (this.f1120b != null) {
            Matrix matrix = new Matrix();
            matrix.preScale(1.0f, -1.0f);
            this.f1127i = (int) Math.floor((double) (((float) (getHeight() - this.f1128j)) / 2.0f));
            float height = ((float) this.f1119a.getHeight()) / ((float) this.f1128j);
            int min = Math.min(Math.round(((float) this.f1126h) * height), this.f1120b.getHeight());
            if (min > 0) {
                this.f1124f = Bitmap.createBitmap(this.f1120b, 0, 0, this.f1120b.getWidth(), min, matrix, true);
                this.f1121c.setImageBitmap(this.f1124f);
            }
            min = Math.min(Math.round(((float) this.f1127i) * height), this.f1120b.getHeight());
            if (min > 0) {
                this.f1125g = Bitmap.createBitmap(this.f1120b, 0, this.f1120b.getHeight() - min, this.f1120b.getWidth(), min, matrix, true);
                this.f1123e.setImageBitmap(this.f1125g);
            }
        }
    }

    private void m1334c() {
        this.f1129k = m1332b(this.f1132n);
        this.f1128j = getHeight();
        this.f1130l = (int) Math.ceil((double) (((float) (getWidth() - this.f1129k)) / 2.0f));
        if (this.f1120b != null) {
            Matrix matrix = new Matrix();
            matrix.preScale(-1.0f, 1.0f);
            this.f1131m = (int) Math.floor((double) (((float) (getWidth() - this.f1129k)) / 2.0f));
            float width = ((float) this.f1119a.getWidth()) / ((float) this.f1129k);
            int min = Math.min(Math.round(((float) this.f1130l) * width), this.f1120b.getWidth());
            if (min > 0) {
                this.f1124f = Bitmap.createBitmap(this.f1120b, 0, 0, min, this.f1120b.getHeight(), matrix, true);
                this.f1121c.setImageBitmap(this.f1124f);
            }
            int min2 = Math.min(Math.round(((float) this.f1131m) * width), this.f1120b.getWidth());
            if (min2 > 0) {
                this.f1125g = Bitmap.createBitmap(this.f1120b, this.f1120b.getWidth() - min2, 0, min2, this.f1120b.getHeight(), matrix, true);
                this.f1123e.setImageBitmap(this.f1125g);
            }
        }
    }

    private void m1335d() {
        getContext().getResources().getDisplayMetrics();
        setOrientation(1);
        this.f1121c = new ImageView(getContext());
        this.f1121c.setScaleType(ScaleType.FIT_XY);
        addView(this.f1121c);
        this.f1122d = new ImageView(getContext());
        this.f1122d.setLayoutParams(new LayoutParams(-1, -1));
        this.f1122d.setScaleType(ScaleType.FIT_XY);
        addView(this.f1122d);
        this.f1123e = new ImageView(getContext());
        this.f1123e.setScaleType(ScaleType.FIT_XY);
        addView(this.f1123e);
    }

    private boolean m1336e() {
        return ((this.f1126h + this.f1128j) + this.f1127i == getMeasuredHeight() && (this.f1130l + this.f1129k) + this.f1131m == getMeasuredWidth()) ? false : true;
    }

    public void m1337a(Bitmap bitmap, Bitmap bitmap2) {
        if (bitmap2 == null) {
            this.f1121c.setImageDrawable(null);
            this.f1123e.setImageDrawable(null);
        }
        if (bitmap == null) {
            this.f1122d.setImageDrawable(null);
            return;
        }
        this.f1122d.setImageBitmap(Bitmap.createBitmap(bitmap));
        this.f1119a = bitmap;
        this.f1120b = bitmap2;
        m1331a();
    }

    protected void onLayout(boolean z, int i, int i2, int i3, int i4) {
        if (this.f1119a == null) {
            super.onLayout(z, i, i2, i3, i4);
            return;
        }
        m1330a(this.f1132n);
        m1332b(this.f1132n);
        if (this.f1124f == null || m1336e()) {
            m1331a();
        }
        if (this.f1132n > this.f1133o) {
            this.f1121c.layout(i, i2, i3, this.f1126h);
            this.f1122d.layout(i, this.f1126h + i2, i3, this.f1126h + this.f1128j);
            this.f1123e.layout(i, (this.f1126h + i2) + this.f1128j, i3, i4);
            return;
        }
        this.f1121c.layout(i, i2, this.f1130l, i4);
        this.f1122d.layout(this.f1130l + i, i2, this.f1130l + this.f1129k, i4);
        this.f1123e.layout((this.f1130l + i) + this.f1129k, i2, i3, i4);
    }
}
