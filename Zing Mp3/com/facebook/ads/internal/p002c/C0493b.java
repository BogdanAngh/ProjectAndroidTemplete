package com.facebook.ads.internal.p002c;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* renamed from: com.facebook.ads.internal.c.b */
public class C0493b {
    private static final String f558a;
    private final Handler f559b;
    private final ExecutorService f560c;
    private final C0494c f561d;
    private final C0496d f562e;
    private final List<Callable<Boolean>> f563f;

    /* renamed from: com.facebook.ads.internal.c.b.1 */
    class C04901 implements Runnable {
        final /* synthetic */ ArrayList f551a;
        final /* synthetic */ C0393a f552b;
        final /* synthetic */ C0493b f553c;

        /* renamed from: com.facebook.ads.internal.c.b.1.1 */
        class C04891 implements Runnable {
            final /* synthetic */ C04901 f550a;

            C04891(C04901 c04901) {
                this.f550a = c04901;
            }

            public void run() {
                this.f550a.f552b.m132a();
            }
        }

        C04901(C0493b c0493b, ArrayList arrayList, C0393a c0393a) {
            this.f553c = c0493b;
            this.f551a = arrayList;
            this.f552b = c0393a;
        }

        public void run() {
            Throwable e;
            List<Future> arrayList = new ArrayList(this.f551a.size());
            Iterator it = this.f551a.iterator();
            while (it.hasNext()) {
                arrayList.add(this.f553c.f560c.submit((Callable) it.next()));
            }
            try {
                for (Future future : arrayList) {
                    future.get();
                }
            } catch (InterruptedException e2) {
                e = e2;
                Log.e(C0493b.f558a, "Exception while executing cache downloads.", e);
                this.f553c.f559b.post(new C04891(this));
            } catch (ExecutionException e3) {
                e = e3;
                Log.e(C0493b.f558a, "Exception while executing cache downloads.", e);
                this.f553c.f559b.post(new C04891(this));
            }
            this.f553c.f559b.post(new C04891(this));
        }
    }

    /* renamed from: com.facebook.ads.internal.c.b.a */
    private class C0491a implements Callable<Boolean> {
        final /* synthetic */ C0493b f554a;
        private final String f555b;

        public C0491a(C0493b c0493b, String str) {
            this.f554a = c0493b;
            this.f555b = str;
        }

        public Boolean m730a() {
            this.f554a.f561d.m745a(this.f555b);
            return Boolean.valueOf(true);
        }

        public /* synthetic */ Object call() {
            return m730a();
        }
    }

    /* renamed from: com.facebook.ads.internal.c.b.b */
    private class C0492b implements Callable<Boolean> {
        final /* synthetic */ C0493b f556a;
        private final String f557b;

        public C0492b(C0493b c0493b, String str) {
            this.f556a = c0493b;
            this.f557b = str;
        }

        public Boolean m731a() {
            this.f556a.f562e.m750a(this.f557b);
            return Boolean.valueOf(true);
        }

        public /* synthetic */ Object call() {
            return m731a();
        }
    }

    static {
        f558a = C0493b.class.getSimpleName();
    }

    public C0493b(Context context) {
        this.f559b = new Handler();
        this.f560c = Executors.newFixedThreadPool(10);
        this.f561d = C0494c.m741a(context);
        this.f562e = C0496d.m748a(context);
        this.f563f = new ArrayList();
    }

    public void m737a(C0393a c0393a) {
        this.f560c.submit(new C04901(this, new ArrayList(this.f563f), c0393a));
        this.f563f.clear();
    }

    public void m738a(String str) {
        this.f563f.add(new C0491a(this, str));
    }

    public void m739b(String str) {
        this.f563f.add(new C0492b(this, str));
    }

    public String m740c(String str) {
        return this.f562e.m751b(str);
    }
}
