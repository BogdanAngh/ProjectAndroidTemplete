package com.facebook.ads.internal.adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView.Adapter;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.ImageView.ScaleType;
import com.facebook.ads.NativeAd;
import com.facebook.ads.internal.p000i.C0715g;
import com.facebook.ads.internal.p000i.C0741p;
import com.facebook.ads.internal.p000i.p016c.C0634c;
import com.facebook.ads.internal.util.C0447q;
import com.facebook.ads.internal.util.C0795p;
import java.util.List;

/* renamed from: com.facebook.ads.internal.adapters.g */
public class C0449g extends Adapter<C0715g> {
    private static final int f337a;
    private final List<NativeAd> f338b;
    private final int f339c;
    private final int f340d;

    /* renamed from: com.facebook.ads.internal.adapters.g.1 */
    class C04481 implements C0447q {
        final /* synthetic */ C0715g f335a;
        final /* synthetic */ C0449g f336b;

        C04481(C0449g c0449g, C0715g c0715g) {
            this.f336b = c0449g;
            this.f335a = c0715g;
        }

        public void m429a() {
            this.f335a.f1150a.setBackgroundColor(C0449g.f337a);
        }
    }

    static {
        f337a = Color.argb(51, 0, 0, 0);
    }

    public C0449g(C0634c c0634c, List<NativeAd> list) {
        float f = c0634c.getContext().getResources().getDisplayMetrics().density;
        this.f338b = list;
        this.f339c = Math.round(f * 1.0f);
        this.f340d = c0634c.getChildSpacing();
    }

    public C0715g m431a(ViewGroup viewGroup, int i) {
        C0741p c0741p = new C0741p(viewGroup.getContext());
        c0741p.setScaleType(ScaleType.CENTER_CROP);
        return new C0715g(c0741p);
    }

    public void m432a(C0715g c0715g, int i) {
        LayoutParams marginLayoutParams = new MarginLayoutParams(-2, -1);
        marginLayoutParams.setMargins(i == 0 ? this.f340d * 2 : this.f340d, 0, i >= this.f338b.size() + -1 ? this.f340d * 2 : this.f340d, 0);
        c0715g.f1150a.setBackgroundColor(0);
        c0715g.f1150a.setImageDrawable(null);
        c0715g.f1150a.setLayoutParams(marginLayoutParams);
        c0715g.f1150a.setPadding(this.f339c, this.f339c, this.f339c, this.f339c);
        NativeAd nativeAd = (NativeAd) this.f338b.get(i);
        nativeAd.registerViewForInteraction(c0715g.f1150a);
        if (nativeAd.getAdCoverImage() != null) {
            C0795p c0795p = new C0795p(c0715g.f1150a);
            c0795p.m1661a(new C04481(this, c0715g));
            c0795p.m1663a(r0.getUrl());
        }
    }

    public int getItemCount() {
        return this.f338b.size();
    }

    public /* synthetic */ void onBindViewHolder(ViewHolder viewHolder, int i) {
        m432a((C0715g) viewHolder, i);
    }

    public /* synthetic */ ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        return m431a(viewGroup, i);
    }
}
