package com.facebook.ads.internal.p000i;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.ads.AudienceNetworkActivity.Type;
import com.facebook.ads.InterstitialAdActivity;
import com.facebook.ads.MediaView;
import com.facebook.ads.MediaViewListener;
import com.facebook.ads.internal.adapters.aa;
import com.facebook.ads.internal.p000i.p018e.p019b.C0638m;
import com.facebook.ads.internal.p000i.p018e.p019b.C0676f;
import com.facebook.ads.internal.p000i.p018e.p019b.C0681g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0641b;
import com.facebook.ads.internal.p000i.p018e.p020a.C0642c;
import com.facebook.ads.internal.p000i.p018e.p020a.C0644e;
import com.facebook.ads.internal.p000i.p018e.p020a.C0645f;
import com.facebook.ads.internal.p000i.p018e.p020a.C0646g;
import com.facebook.ads.internal.p000i.p018e.p020a.C0647h;
import com.facebook.ads.internal.p000i.p018e.p020a.C0648i;
import com.facebook.ads.internal.p000i.p018e.p020a.C0649j;
import com.facebook.ads.internal.p000i.p018e.p021c.C0708f;
import com.facebook.ads.internal.p003j.C0749a;
import com.facebook.ads.internal.p003j.C0749a.C0398a;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.ads.internal.util.C0783h;
import com.facebook.ads.internal.util.ab;
import java.util.UUID;

/* renamed from: com.facebook.ads.internal.i.i */
public class C0727i extends C0726m {
    static final /* synthetic */ boolean f1185a;
    private final C0647h f1186c;
    private final C0645f f1187d;
    private final C0649j f1188e;
    private final C0642c f1189f;
    @NonNull
    private final String f1190g;
    @NonNull
    private final MediaView f1191h;
    @NonNull
    private final C0783h f1192i;
    @NonNull
    private final C0749a f1193j;
    @NonNull
    private final aa f1194k;
    @NonNull
    private final C0676f f1195l;
    @Nullable
    private ab f1196m;
    @Nullable
    private String f1197n;
    @Nullable
    private Uri f1198o;
    @Nullable
    private String f1199p;
    @Nullable
    private MediaViewListener f1200q;
    private boolean f1201r;

    /* renamed from: com.facebook.ads.internal.i.i.1 */
    class C07191 extends C0647h {
        final /* synthetic */ C0727i f1162a;

        C07191(C0727i c0727i) {
            this.f1162a = c0727i;
        }

        public void m1372a(C0646g c0646g) {
            if (this.f1162a.f1200q != null) {
                this.f1162a.f1200q.onPlay(this.f1162a.f1191h);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.i.2 */
    class C07202 extends C0645f {
        final /* synthetic */ C0727i f1163a;

        C07202(C0727i c0727i) {
            this.f1163a = c0727i;
        }

        public void m1374a(C0644e c0644e) {
            if (this.f1163a.f1200q != null) {
                this.f1163a.f1200q.onPause(this.f1163a.f1191h);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.i.3 */
    class C07223 extends C0649j {
        final /* synthetic */ C0727i f1165a;

        /* renamed from: com.facebook.ads.internal.i.i.3.1 */
        class C07211 implements OnTouchListener {
            final /* synthetic */ C07223 f1164a;

            C07211(C07223 c07223) {
                this.f1164a = c07223;
            }

            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 1) {
                    this.f1164a.f1165a.m1405k();
                }
                return true;
            }
        }

        C07223(C0727i c0727i) {
            this.f1165a = c0727i;
        }

        public void m1376a(C0648i c0648i) {
            if (this.f1165a.m1400i()) {
                this.f1165a.m1407d();
            }
            this.f1165a.setOnTouchListener(new C07211(this));
        }
    }

    /* renamed from: com.facebook.ads.internal.i.i.4 */
    class C07234 extends C0642c {
        final /* synthetic */ C0727i f1166a;

        C07234(C0727i c0727i) {
            this.f1166a = c0727i;
        }

        public void m1378a(C0641b c0641b) {
            if (this.f1166a.f1200q != null) {
                this.f1166a.f1200q.onComplete(this.f1166a.f1191h);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.i.5 */
    class C07245 extends C0398a {
        final /* synthetic */ C0727i f1167a;

        C07245(C0727i c0727i) {
            this.f1167a = c0727i;
        }

        public void m1379a() {
            if ((this.f1167a.m1400i() || this.f1167a.b.getTargetState() == C0708f.STARTED) && this.f1167a.b.getTargetState() != C0708f.PAUSED) {
                this.f1167a.m1407d();
            }
        }

        public void m1380b() {
            this.f1167a.m1396e();
        }
    }

    static {
        f1185a = !C0727i.class.desiredAssertionStatus();
    }

    public C0727i(@NonNull Context context, @NonNull MediaView mediaView, @NonNull C0783h c0783h) {
        super(context);
        this.f1186c = new C07191(this);
        this.f1187d = new C07202(this);
        this.f1188e = new C07223(this);
        this.f1189f = new C07234(this);
        this.f1190g = UUID.randomUUID().toString();
        this.f1201r = false;
        this.f1191h = mediaView;
        this.f1192i = c0783h;
        getEventBus().m915a(this.f1186c);
        getEventBus().m915a(this.f1187d);
        getEventBus().m915a(this.f1189f);
        setAutoplay(true);
        setVolume(0.0f);
        this.f1195l = new C0676f(context);
        m1390a(this.f1195l);
        DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
        C0638m c0681g = new C0681g(context);
        LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(9);
        layoutParams.addRule(12);
        c0681g.setPadding((int) (displayMetrics.density * 2.0f), (int) (displayMetrics.density * 25.0f), (int) (displayMetrics.density * 25.0f), (int) (displayMetrics.density * 2.0f));
        c0681g.setLayoutParams(layoutParams);
        m1390a(c0681g);
        getEventBus().m915a(this.f1188e);
        this.f1194k = new aa(this, getContext());
        this.f1193j = new C0749a(this, 50, true, new C07245(this));
        this.f1193j.m1491a(0);
        this.f1193j.m1494b((int) Callback.DEFAULT_SWIPE_ANIMATION_DURATION);
    }

    private void m1402a(Context context, Intent intent) {
        if (!f1185a && this.f1197n == null) {
            throw new AssertionError();
        } else if (!f1185a && this.f1198o == null && this.f1199p == null) {
            throw new AssertionError();
        } else {
            ((WindowManager) context.getSystemService("window")).getDefaultDisplay().getMetrics(new DisplayMetrics());
            intent.putExtra("useNativeCloseButton", true);
            intent.putExtra(AudienceNetworkActivity.VIEW_TYPE, Type.VIDEO);
            intent.putExtra(AudienceNetworkActivity.VIDEO_URL, this.f1198o.toString());
            intent.putExtra(AudienceNetworkActivity.VIDEO_MPD, this.f1199p);
            intent.putExtra(AudienceNetworkActivity.VIDEO_REPORT_URL, this.f1197n);
            intent.putExtra(AudienceNetworkActivity.PREDEFINED_ORIENTATION_KEY, 13);
            intent.putExtra(AudienceNetworkActivity.AUTOPLAY, m1392a());
            intent.putExtra(AudienceNetworkActivity.VIDEO_SEEK_TIME, getCurrentPosition());
            intent.putExtra(AudienceNetworkActivity.AUDIENCE_NETWORK_UNIQUE_ID_EXTRA, this.f1190g);
            intent.putExtra(AudienceNetworkActivity.VIDEO_LOGGER, this.f1196m.getSaveInstanceState());
            intent.addFlags(268435456);
        }
    }

    private void m1405k() {
        Context context = getContext();
        Intent intent = new Intent(context, AudienceNetworkActivity.class);
        m1402a(context, intent);
        try {
            m1396e();
            setVisibility(8);
            context.startActivity(intent);
        } catch (ActivityNotFoundException e) {
            try {
                intent.setClass(context, InterstitialAdActivity.class);
                context.startActivity(intent);
            } catch (Throwable e2) {
                C0778d.m1599a(C0777c.m1596a(e2, "Error occurred while loading fullscreen video activity."));
            }
        } catch (Throwable e22) {
            C0778d.m1599a(C0777c.m1596a(e22, "Error occurred while loading fullscreen video activity."));
        }
    }

    private void m1406l() {
        if (getVisibility() == 0 && this.f1201r) {
            this.f1193j.m1490a();
        } else {
            this.f1193j.m1493b();
        }
    }

    public void m1407d() {
        if (C0749a.m1475a((View) this, 50).m1495a()) {
            super.m1395d();
        }
    }

    @Nullable
    public MediaViewListener getListener() {
        return this.f1200q;
    }

    @NonNull
    public MediaView getMediaView() {
        return this.f1191h;
    }

    @NonNull
    public String getUniqueId() {
        return this.f1190g;
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.f1201r = true;
        this.f1194k.m339a();
        m1406l();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        this.f1201r = false;
        this.f1194k.m340b();
        m1406l();
    }

    protected void onVisibilityChanged(View view, int i) {
        m1406l();
        super.onVisibilityChanged(view, i);
    }

    public void setImage(String str) {
        this.f1195l.setImage(str);
    }

    public void setListener(@NonNull MediaViewListener mediaViewListener) {
        this.f1200q = mediaViewListener;
    }

    public void setVideoMPD(@NonNull String str) {
        if (f1185a || this.f1196m != null) {
            this.f1199p = str;
            super.setVideoMPD(str);
            return;
        }
        throw new AssertionError();
    }

    public void setVideoReportURI(String str) {
        if (this.f1196m != null) {
            this.f1196m.m1579a();
        }
        this.f1196m = new ab(getContext(), this.f1192i, this, str);
        this.f1197n = str;
    }

    public void setVideoURI(@NonNull Uri uri) {
        if (f1185a || this.f1196m != null) {
            this.f1198o = uri;
            super.setVideoURI(uri);
            return;
        }
        throw new AssertionError();
    }
}
