package com.facebook.ads.internal.p005f;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import android.text.TextUtils;
import com.facebook.ads.internal.C0604h;
import com.facebook.ads.internal.p010h.p011a.C0554a;
import com.facebook.ads.internal.p010h.p011a.C0567n;
import com.facebook.ads.internal.p010h.p011a.C0569p;
import com.facebook.ads.internal.server.C0757b;
import com.facebook.ads.internal.util.C0806x;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import org.json.JSONArray;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.f.e */
public class C0535e {
    private static final String f740a;
    private static final String f741b;
    private final C0534a f742c;
    private final ThreadPoolExecutor f743d;
    private final ConnectivityManager f744e;
    private final C0554a f745f;
    private final Handler f746g;
    private final long f747h;
    private final long f748i;
    private final Runnable f749j;
    private volatile boolean f750k;
    private int f751l;
    private long f752m;

    /* renamed from: com.facebook.ads.internal.f.e.1 */
    class C05331 implements Runnable {
        final /* synthetic */ C0535e f739a;

        /* renamed from: com.facebook.ads.internal.f.e.1.1 */
        class C05321 extends AsyncTask<Void, Void, Void> {
            final /* synthetic */ C05331 f738a;

            C05321(C05331 c05331) {
                this.f738a = c05331;
            }

            protected Void m860a(Void... voidArr) {
                C0535e.m868b(this.f738a.f739a);
                if (this.f738a.f739a.f752m > 0) {
                    try {
                        Thread.sleep(this.f738a.f739a.f752m);
                    } catch (InterruptedException e) {
                    }
                }
                this.f738a.f739a.m869b();
                return null;
            }

            protected /* synthetic */ Object doInBackground(Object[] objArr) {
                return m860a((Void[]) objArr);
            }
        }

        C05331(C0535e c0535e) {
            this.f739a = c0535e;
        }

        public void run() {
            this.f739a.f750k = false;
            if (this.f739a.f743d.getQueue().isEmpty()) {
                new C05321(this).executeOnExecutor(this.f739a.f743d, new Void[0]);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.f.e.a */
    interface C0534a {
        JSONObject m861a();

        boolean m862a(JSONArray jSONArray);

        void m863b();
    }

    static {
        f740a = C0535e.class.getSimpleName();
        f741b = C0757b.m1521b();
    }

    public C0535e(Context context, C0534a c0534a) {
        this.f749j = new C05331(this);
        this.f742c = c0534a;
        this.f743d = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingQueue());
        this.f744e = (ConnectivityManager) context.getSystemService("connectivity");
        this.f745f = C0806x.m1685b(context);
        this.f746g = new Handler(Looper.getMainLooper());
        this.f747h = C0604h.m1122c(context);
        this.f748i = C0604h.m1123d(context);
    }

    private void m865a() {
        if (this.f751l >= 3) {
            m871c();
            m873a(false);
            return;
        }
        if (this.f751l == 1) {
            this.f752m = 2000;
        } else {
            this.f752m *= 2;
        }
        m873a(true);
    }

    private void m866a(long j) {
        this.f746g.postDelayed(this.f749j, j);
    }

    static /* synthetic */ int m868b(C0535e c0535e) {
        int i = c0535e.f751l + 1;
        c0535e.f751l = i;
        return i;
    }

    @WorkerThread
    private void m869b() {
        try {
            NetworkInfo activeNetworkInfo = this.f744e.getActiveNetworkInfo();
            if (activeNetworkInfo == null || !activeNetworkInfo.isConnectedOrConnecting()) {
                m866a(this.f748i);
                return;
            }
            JSONObject a = this.f742c.m861a();
            if (a == null) {
                m871c();
                return;
            }
            C0569p c0569p = new C0569p();
            c0569p.m1000a("payload", a.toString());
            C0567n b = this.f745f.m959b(f741b, c0569p);
            Object e = b != null ? b.m994e() : null;
            if (TextUtils.isEmpty(e)) {
                m865a();
            } else if (b.m990a() != Callback.DEFAULT_DRAG_ANIMATION_DURATION) {
                m865a();
            } else if (this.f742c.m862a(new JSONArray(e))) {
                m871c();
            } else {
                m865a();
            }
        } catch (Exception e2) {
            m865a();
        }
    }

    private void m871c() {
        this.f751l = 0;
        this.f752m = 0;
        if (this.f743d.getQueue().size() == 0) {
            this.f742c.m863b();
        }
    }

    public void m873a(boolean z) {
        if (z || !this.f750k) {
            this.f750k = true;
            this.f746g.removeCallbacks(this.f749j);
            m866a(z ? this.f747h : this.f748i);
        }
    }
}
