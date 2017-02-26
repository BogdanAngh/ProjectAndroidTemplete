package com.facebook.ads.internal.p000i;

import android.content.Context;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.TextureView;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.RelativeLayout;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0643d;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0650k;
import com.facebook.ads.internal.p000i.p018e.p020a.C0651l;
import com.facebook.ads.internal.p000i.p018e.p020a.C0652m;
import com.facebook.ads.internal.p000i.p018e.p020a.C0653n;
import com.facebook.ads.internal.p000i.p018e.p021c.C0702e;
import com.facebook.ads.internal.p000i.p018e.p021c.C0703b;
import com.facebook.ads.internal.p000i.p018e.p021c.C0707d;
import com.facebook.ads.internal.p000i.p018e.p021c.C0708f;
import com.facebook.ads.internal.p000i.p018e.p021c.C0709g;
import com.facebook.ads.internal.p005f.C0453p;
import com.facebook.ads.internal.p005f.C0545n;
import com.facebook.ads.internal.p005f.C0546o;
import com.facebook.ads.internal.util.C0806x;
import com.facebook.ads.internal.util.C0806x.C0805a;
import com.facebook.ads.internal.util.ag.C0725a;
import com.facebook.ads.internal.util.ah;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.facebook.ads.internal.i.m */
public class C0726m extends RelativeLayout implements C0709g, C0725a {
    private static final C0648i f1168i;
    private static final C0643d f1169j;
    private static final C0641b f1170k;
    private static final C0650k f1171l;
    private static final C0652m f1172m;
    private static final C0644e f1173n;
    private static final C0646g f1174o;
    @NonNull
    private final C0546o<C0453p, C0545n> f1175a;
    @NonNull
    protected final C0702e f1176b;
    @NonNull
    private final List<C0638m> f1177c;
    private boolean f1178d;
    @Deprecated
    private boolean f1179e;
    @Deprecated
    private boolean f1180f;
    private ah f1181g;
    private boolean f1182h;
    private final Handler f1183p;
    private final OnTouchListener f1184q;

    /* renamed from: com.facebook.ads.internal.i.m.1 */
    class C07371 implements Runnable {
        final /* synthetic */ C0726m f1226a;

        C07371(C0726m c0726m) {
            this.f1226a = c0726m;
        }

        public void run() {
            if (!this.f1226a.f1178d) {
                this.f1226a.f1175a.m914a(C0726m.f1171l);
                this.f1226a.f1183p.postDelayed(this, 250);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.m.2 */
    class C07382 implements OnTouchListener {
        final /* synthetic */ C0726m f1227a;

        C07382(C0726m c0726m) {
            this.f1227a = c0726m;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            this.f1227a.f1175a.m914a(new C0653n(view, motionEvent));
            return true;
        }
    }

    static {
        f1168i = new C0648i();
        f1169j = new C0643d();
        f1170k = new C0641b();
        f1171l = new C0650k();
        f1172m = new C0652m();
        f1173n = new C0644e();
        f1174o = new C0646g();
    }

    public C0726m(@Nullable Context context) {
        this(context, null);
    }

    public C0726m(@Nullable Context context, @Nullable AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public C0726m(@Nullable Context context, @Nullable AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        this.f1177c = new ArrayList();
        this.f1178d = false;
        this.f1179e = false;
        this.f1180f = false;
        this.f1181g = ah.UNKNOWN;
        this.f1182h = false;
        this.f1184q = new C07382(this);
        if (C0604h.m1120a(getContext())) {
            this.f1176b = new C0703b(getContext());
        } else {
            this.f1176b = new C0707d(getContext());
        }
        this.f1176b.setRequestedVolume(1.0f);
        this.f1176b.setVideoStateChangeListener(this);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-1, -1);
        layoutParams.addRule(13);
        addView((View) this.f1176b, layoutParams);
        this.f1183p = new Handler();
        this.f1175a = new C0546o();
        setOnTouchListener(this.f1184q);
    }

    public void m1388a(int i) {
        this.f1176b.seekTo(i);
    }

    public void m1389a(int i, int i2) {
        this.f1175a.m914a(new C0651l(i, i2));
    }

    public void m1390a(@NonNull C0638m c0638m) {
        this.f1177c.add(c0638m);
    }

    public void m1391a(C0708f c0708f) {
        if (c0708f == C0708f.PREPARED) {
            this.f1175a.m914a(f1168i);
            if (m1400i() && !this.f1178d) {
                m1395d();
            }
        } else if (c0708f == C0708f.ERROR) {
            this.f1178d = true;
            this.f1175a.m914a(f1169j);
        } else if (c0708f == C0708f.PLAYBACK_COMPLETED) {
            this.f1178d = true;
            this.f1183p.removeCallbacksAndMessages(null);
            this.f1175a.m914a(f1170k);
        } else if (c0708f == C0708f.STARTED) {
            this.f1175a.m914a(f1174o);
            this.f1183p.removeCallbacksAndMessages(null);
            this.f1183p.postDelayed(new C07371(this), 250);
        } else if (c0708f == C0708f.PAUSED) {
            this.f1175a.m914a(f1173n);
            this.f1183p.removeCallbacksAndMessages(null);
        }
    }

    public boolean m1392a() {
        return m1400i();
    }

    public boolean m1393b() {
        return C0604h.m1120a(getContext());
    }

    public boolean m1394c() {
        return this.f1182h;
    }

    public void m1395d() {
        if (this.f1178d && this.f1176b.getState() == C0708f.PLAYBACK_COMPLETED) {
            this.f1178d = false;
        }
        this.f1176b.start();
    }

    public void m1396e() {
        this.f1176b.pause();
    }

    public void m1397f() {
        getEventBus().m914a(f1172m);
        this.f1176b.m1293a();
    }

    public void m1398g() {
        this.f1176b.m1295b();
    }

    public int getCurrentPosition() {
        return this.f1176b.getCurrentPosition();
    }

    public int getDuration() {
        return this.f1176b.getDuration();
    }

    @NonNull
    public C0546o<C0453p, C0545n> getEventBus() {
        return this.f1175a;
    }

    public long getInitialBufferTime() {
        return this.f1176b.getInitialBufferTime();
    }

    public ah getIsAutoPlayFromServer() {
        return this.f1181g;
    }

    public C0708f getState() {
        return this.f1176b.getState();
    }

    public TextureView getTextureView() {
        return (TextureView) this.f1176b;
    }

    public View getVideoView() {
        return this.f1176b.getView();
    }

    public float getVolume() {
        return this.f1176b.getVolume();
    }

    public void m1399h() {
        this.f1176b.m1294a(true);
    }

    protected boolean m1400i() {
        boolean z = this.f1179e && (!this.f1180f || C0806x.m1687c(getContext()) == C0805a.MOBILE_INTERNET);
        return getIsAutoPlayFromServer() == ah.UNKNOWN ? z : getIsAutoPlayFromServer() == ah.ON;
    }

    @Deprecated
    public void setAutoplay(boolean z) {
        this.f1179e = z;
    }

    public void setControlsAnchorView(View view) {
        if (this.f1176b != null) {
            this.f1176b.setControlsAnchorView(view);
        }
    }

    public void setIsAutoPlayFromServer(ah ahVar) {
        this.f1181g = ahVar;
    }

    @Deprecated
    public void setIsAutoplayOnMobile(boolean z) {
        this.f1180f = z;
    }

    public void setIsFullScreen(boolean z) {
        this.f1182h = z;
        this.f1176b.setFullScreen(z);
    }

    public void setVideoMPD(String str) {
        this.f1176b.setVideoMPD(str);
    }

    public void setVideoURI(@NonNull Uri uri) {
        for (C0638m c0638m : this.f1177c) {
            if (c0638m.getParent() == null) {
                addView(c0638m);
                c0638m.m1200b(this);
            }
        }
        this.f1178d = false;
        this.f1176b.setup(uri);
    }

    public void setVideoURI(@NonNull String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVolume(float f) {
        this.f1176b.setRequestedVolume(f);
    }
}
