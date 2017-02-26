package com.facebook.ads.internal.p000i.p016c;

import android.content.Context;
import android.graphics.PointF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.LayoutManager;
import android.support.v7.widget.RecyclerView.LayoutParams;
import android.support.v7.widget.RecyclerView.Recycler;
import android.support.v7.widget.RecyclerView.State;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.MeasureSpec;
import com.google.android.exoplayer.C0989C;

/* renamed from: com.facebook.ads.internal.i.c.b */
public class C0630b extends LinearLayoutManager {
    private final C0635d f923a;
    private final C0628a f924b;
    private final Context f925c;
    private int[] f926d;
    private int f927e;
    private float f928f;
    private C0629a f929g;
    private int f930h;

    /* renamed from: com.facebook.ads.internal.i.c.b.a */
    private class C0629a extends LinearSmoothScroller {
        final /* synthetic */ C0630b f922a;

        public C0629a(C0630b c0630b, Context context) {
            this.f922a = c0630b;
            super(context);
        }

        public int calculateDxToMakeVisible(View view, int i) {
            LayoutManager layoutManager = getLayoutManager();
            if (!layoutManager.canScrollHorizontally()) {
                return 0;
            }
            LayoutParams layoutParams = (LayoutParams) view.getLayoutParams();
            return calculateDtToFit(layoutManager.getDecoratedLeft(view) - layoutParams.leftMargin, layoutManager.getDecoratedRight(view) + layoutParams.rightMargin, layoutManager.getPaddingLeft(), layoutManager.getWidth() - layoutManager.getPaddingRight(), i) + this.f922a.f927e;
        }

        protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
            return this.f922a.f928f / ((float) displayMetrics.densityDpi);
        }

        public PointF computeScrollVectorForPosition(int i) {
            return this.f922a.computeScrollVectorForPosition(i);
        }

        protected int getHorizontalSnapPreference() {
            return -1;
        }
    }

    public C0630b(Context context, C0635d c0635d, C0628a c0628a) {
        super(context);
        this.f927e = 0;
        this.f928f = 50.0f;
        this.f925c = context;
        this.f923a = c0635d;
        this.f924b = c0628a;
        this.f930h = -1;
        this.f929g = new C0629a(this, this.f925c);
    }

    public void m1177a(double d) {
        if (d <= 0.0d) {
            d = 1.0d;
        }
        this.f928f = (float) (50.0d / d);
        this.f929g = new C0629a(this, this.f925c);
    }

    void m1178a(int i) {
        this.f930h = i;
    }

    public void m1179b(int i) {
        this.f927e = i;
    }

    public void onMeasure(Recycler recycler, State state, int i, int i2) {
        int mode = MeasureSpec.getMode(i);
        int mode2 = MeasureSpec.getMode(i2);
        if ((mode == C0989C.ENCODING_PCM_32BIT && getOrientation() == 1) || (mode2 == C0989C.ENCODING_PCM_32BIT && getOrientation() == 0)) {
            super.onMeasure(recycler, state, i, i2);
            return;
        }
        int[] a;
        int size = MeasureSpec.getSize(i);
        int size2 = MeasureSpec.getSize(i2);
        if (this.f924b.m1174b(this.f930h)) {
            a = this.f924b.m1173a(this.f930h);
        } else {
            a = new int[]{0, 0};
            if (state.getItemCount() >= 1) {
                for (int i3 = 0; i3 < 1; i3++) {
                    this.f926d = this.f923a.m1192a(recycler, i3, MeasureSpec.makeMeasureSpec(0, 0), MeasureSpec.makeMeasureSpec(0, 0));
                    if (getOrientation() == 0) {
                        a[0] = a[0] + this.f926d[0];
                        if (i3 == 0) {
                            a[1] = (this.f926d[1] + getPaddingTop()) + getPaddingBottom();
                        }
                    } else {
                        a[1] = a[1] + this.f926d[1];
                        if (i3 == 0) {
                            a[0] = (this.f926d[0] + getPaddingLeft()) + getPaddingRight();
                        }
                    }
                }
                if (this.f930h != -1) {
                    this.f924b.m1172a(this.f930h, a);
                }
            }
        }
        if (mode == C0989C.ENCODING_PCM_32BIT) {
            a[0] = size;
        }
        if (mode2 == C0989C.ENCODING_PCM_32BIT) {
            a[1] = size2;
        }
        setMeasuredDimension(a[0], a[1]);
    }

    public void scrollToPosition(int i) {
        super.scrollToPositionWithOffset(i, this.f927e);
    }

    public void smoothScrollToPosition(RecyclerView recyclerView, State state, int i) {
        this.f929g.setTargetPosition(i);
        startSmoothScroll(this.f929g);
    }
}
