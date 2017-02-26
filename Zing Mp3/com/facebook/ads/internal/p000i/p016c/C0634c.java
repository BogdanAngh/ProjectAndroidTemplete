package com.facebook.ads.internal.p000i.p016c;

import android.content.Context;
import android.support.v7.widget.RecyclerView.Adapter;
import android.view.View.MeasureSpec;
import com.facebook.ads.internal.p000i.p016c.C0632e.C0633a;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.text.Cue;

/* renamed from: com.facebook.ads.internal.i.c.c */
public class C0634c extends C0632e implements C0633a {
    private C0630b f938c;
    private C0631a f939d;
    private int f940e;
    private int f941f;
    private int f942g;
    private int f943h;

    /* renamed from: com.facebook.ads.internal.i.c.c.a */
    public interface C0631a {
        void m1180a(int i, int i2);
    }

    public C0634c(Context context) {
        super(context);
        this.f940e = -1;
        this.f941f = -1;
        this.f942g = 0;
        this.f943h = 0;
        m1187a();
    }

    private void m1187a() {
        this.f938c = new C0630b(getContext(), new C0635d(), new C0628a());
        this.f938c.setOrientation(0);
        setLayoutManager(this.f938c);
        setSnapDelegate(this);
    }

    private void m1188a(int i, int i2) {
        if (i != this.f940e || i2 != this.f941f) {
            this.f940e = i;
            this.f941f = i2;
            if (this.f939d != null) {
                this.f939d.m1180a(this.f940e, this.f941f);
            }
        }
    }

    private int m1189b(int i) {
        int i2 = this.f943h * 2;
        int measuredWidth = (getMeasuredWidth() - getPaddingLeft()) - i2;
        int itemCount = getAdapter().getItemCount();
        int i3 = 0;
        int i4 = ActivityChooserViewAdapter.MAX_ACTIVITY_COUNT_UNLIMITED;
        while (i4 > i) {
            i3++;
            if (i3 >= itemCount) {
                return i;
            }
            i4 = (int) (((float) (measuredWidth - (i3 * i2))) / (((float) i3) + 0.333f));
        }
        return i4;
    }

    public int m1190a(int i) {
        int abs = Math.abs(i);
        return abs <= this.b ? 0 : this.f942g == 0 ? 1 : (abs / this.f942g) + 1;
    }

    protected void m1191a(int i, boolean z) {
        super.m1185a(i, z);
        m1188a(i, 0);
    }

    public int getChildSpacing() {
        return this.f943h;
    }

    protected void onMeasure(int i, int i2) {
        super.onMeasure(i, i2);
        int round = Math.round(((float) getMeasuredWidth()) / 1.91f);
        switch (MeasureSpec.getMode(i2)) {
            case Cue.TYPE_UNSET /*-2147483648*/:
                round = Math.min(MeasureSpec.getSize(i2), round);
                break;
            case C0989C.ENCODING_PCM_32BIT /*1073741824*/:
                round = MeasureSpec.getSize(i2);
                break;
        }
        int paddingTop = getPaddingTop() + getPaddingBottom();
        round = m1189b(round - paddingTop);
        setMeasuredDimension(getMeasuredWidth(), paddingTop + round);
        setChildWidth(round + (this.f943h * 2));
    }

    public void setAdapter(Adapter adapter) {
        this.f938c.m1178a(adapter == null ? -1 : adapter.hashCode());
        super.setAdapter(adapter);
    }

    public void setChildSpacing(int i) {
        this.f943h = i;
    }

    public void setChildWidth(int i) {
        this.f942g = i;
        int measuredWidth = getMeasuredWidth();
        this.f938c.m1179b((((measuredWidth - getPaddingLeft()) - getPaddingRight()) - this.f942g) / 2);
        this.f938c.m1177a(((double) this.f942g) / ((double) measuredWidth));
    }

    public void setCurrentPosition(int i) {
        m1191a(i, false);
    }

    public void setOnPageChangedListener(C0631a c0631a) {
        this.f939d = c0631a;
    }
}
