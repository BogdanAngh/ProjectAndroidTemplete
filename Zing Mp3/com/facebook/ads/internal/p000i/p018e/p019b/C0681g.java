package com.facebook.ads.internal.p000i.p018e.p019b;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.p000i.C0726m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0642c;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0645f;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0647h;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0649j;
import com.facebook.ads.internal.p000i.p018e.p021c.C0708f;
import com.facebook.ads.internal.p005f.C0546o;

/* renamed from: com.facebook.ads.internal.i.e.b.g */
public class C0681g extends C0638m implements OnTouchListener {
    @NonNull
    private final C0649j f1013b;
    @NonNull
    private final C0645f f1014c;
    @NonNull
    private final C0647h f1015d;
    @NonNull
    private final C0642c f1016e;
    @NonNull
    private final C0694l f1017f;

    /* renamed from: com.facebook.ads.internal.i.e.b.g.1 */
    class C06771 extends C0649j {
        final /* synthetic */ C0681g f1009a;

        C06771(C0681g c0681g) {
            this.f1009a = c0681g;
        }

        public void m1257a(C0648i c0648i) {
            this.f1009a.setVisibility(0);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.g.2 */
    class C06782 extends C0645f {
        final /* synthetic */ C0681g f1010a;

        C06782(C0681g c0681g) {
            this.f1010a = c0681g;
        }

        public void m1259a(C0644e c0644e) {
            this.f1010a.f1017f.setChecked(true);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.g.3 */
    class C06793 extends C0647h {
        final /* synthetic */ C0681g f1011a;

        C06793(C0681g c0681g) {
            this.f1011a = c0681g;
        }

        public void m1261a(C0646g c0646g) {
            this.f1011a.f1017f.setChecked(false);
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.b.g.4 */
    class C06804 extends C0642c {
        final /* synthetic */ C0681g f1012a;

        C06804(C0681g c0681g) {
            this.f1012a = c0681g;
        }

        public void m1263a(C0641b c0641b) {
            this.f1012a.f1017f.setChecked(true);
        }
    }

    public C0681g(Context context) {
        this(context, null);
    }

    public C0681g(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public C0681g(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1013b = new C06771(this);
        this.f1014c = new C06782(this);
        this.f1015d = new C06793(this);
        this.f1016e = new C06804(this);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        this.f1017f = new C0694l(context);
        this.f1017f.setChecked(true);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams((int) (displayMetrics.density * 25.0f), (int) (displayMetrics.density * 25.0f));
        setVisibility(8);
        addView(this.f1017f, layoutParams);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    protected void m1265a(C0726m c0726m) {
        this.f1017f.setOnTouchListener(this);
        setOnTouchListener(this);
        C0546o eventBus = c0726m.getEventBus();
        eventBus.m915a(this.f1013b);
        eventBus.m915a(this.f1016e);
        eventBus.m915a(this.f1014c);
        eventBus.m915a(this.f1015d);
    }

    public boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() != 1) {
            return false;
        }
        C0726m videoView = getVideoView();
        if (videoView == null) {
            return false;
        }
        if (videoView.getState() == C0708f.PREPARED || videoView.getState() == C0708f.PAUSED || videoView.getState() == C0708f.PLAYBACK_COMPLETED) {
            videoView.m1395d();
            return true;
        } else if (videoView.getState() != C0708f.STARTED) {
            return false;
        } else {
            videoView.m1396e();
            return false;
        }
    }
}
