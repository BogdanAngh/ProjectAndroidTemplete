package com.facebook.ads.internal.p000i.p018e.p019b;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0653n;
import com.facebook.ads.internal.p005f.C0453p;

@TargetApi(12)
/* renamed from: com.facebook.ads.internal.i.e.b.d */
public abstract class C0664d extends C0638m {
    private final Handler f981b;
    private final boolean f982c;
    @Nullable
    private C0726m f983d;
    @NonNull
    private final C0453p<C0644e> f984e;
    @NonNull
    private final C0453p<C0646g> f985f;
    @NonNull
    private final C0453p<C0653n> f986g;

    /* renamed from: com.facebook.ads.internal.i.e.b.d.1 */
    class C06661 extends C0453p<C0644e> {
        final /* synthetic */ C0664d f990a;

        C06661(C0664d c0664d) {
            this.f990a = c0664d;
        }

        public Class<C0644e> m1236a() {
            return C0644e.class;
        }

        public void m1238a(C0644e c0644e) {
            this.f990a.f981b.removeCallbacksAndMessages(null);
            this.f990a.clearAnimation();
            this.f990a.setAlpha(1.0f);
            this.f990a.setVisibility(0);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.d.2 */
    class C06672 extends C0453p<C0646g> {
        final /* synthetic */ C0664d f991a;

        C06672(C0664d c0664d) {
            this.f991a = c0664d;
        }

        public Class<C0646g> m1239a() {
            return C0646g.class;
        }

        public void m1241a(C0646g c0646g) {
            this.f991a.f981b.removeCallbacksAndMessages(null);
            this.f991a.clearAnimation();
            this.f991a.setAlpha(0.0f);
            this.f991a.setVisibility(8);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.d.3 */
    class C06713 extends C0453p<C0653n> {
        final /* synthetic */ C0664d f995a;

        /* renamed from: com.facebook.ads.internal.i.e.b.d.3.1 */
        class C06701 extends AnimatorListenerAdapter {
            final /* synthetic */ C06713 f994a;

            /* renamed from: com.facebook.ads.internal.i.e.b.d.3.1.1 */
            class C06691 implements Runnable {
                final /* synthetic */ C06701 f993a;

                /* renamed from: com.facebook.ads.internal.i.e.b.d.3.1.1.1 */
                class C06681 extends AnimatorListenerAdapter {
                    final /* synthetic */ C06691 f992a;

                    C06681(C06691 c06691) {
                        this.f992a = c06691;
                    }

                    public void onAnimationEnd(Animator animator) {
                        this.f992a.f993a.f994a.f995a.setVisibility(8);
                    }
                }

                C06691(C06701 c06701) {
                    this.f993a = c06701;
                }

                public void run() {
                    this.f993a.f994a.f995a.animate().alpha(0.0f).setDuration(500).setListener(new C06681(this));
                }
            }

            C06701(C06713 c06713) {
                this.f994a = c06713;
            }

            public void onAnimationEnd(Animator animator) {
                this.f994a.f995a.f981b.postDelayed(new C06691(this), 2000);
            }
        }

        C06713(C0664d c0664d) {
            this.f995a = c0664d;
        }

        public Class<C0653n> m1242a() {
            return C0653n.class;
        }

        public void m1244a(C0653n c0653n) {
            if (this.f995a.f983d != null && c0653n.m1210b().getAction() == 0) {
                this.f995a.f981b.removeCallbacksAndMessages(null);
                this.f995a.setVisibility(0);
                this.f995a.animate().alpha(1.0f).setDuration(500).setListener(new C06701(this));
            }
        }
    }

    public C0664d(Context context) {
        this(context, false);
    }

    public C0664d(Context context, boolean z) {
        super(context);
        this.f984e = new C06661(this);
        this.f985f = new C06672(this);
        this.f986g = new C06713(this);
        this.f982c = z;
        this.f981b = new Handler();
        if (!this.f982c) {
            setAlpha(0.0f);
            setVisibility(8);
        }
    }

    protected void m1231a(C0726m c0726m) {
        if (!this.f982c) {
            c0726m.getEventBus().m915a(this.f984e);
            c0726m.getEventBus().m915a(this.f985f);
            c0726m.getEventBus().m915a(this.f986g);
            this.f983d = c0726m;
        }
    }
}
