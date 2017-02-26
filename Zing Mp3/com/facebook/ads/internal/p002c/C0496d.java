package com.facebook.ads.internal.p002c;

import android.content.Context;
import android.support.annotation.Nullable;
import com.facebook.ads.internal.p010h.p012b.C0592f;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/* renamed from: com.facebook.ads.internal.c.d */
public class C0496d {
    private static final String f569a;
    private static C0496d f570b;
    private final Future<C0592f> f571c;

    /* renamed from: com.facebook.ads.internal.c.d.1 */
    class C04951 implements Callable<C0592f> {
        final /* synthetic */ Context f567a;
        final /* synthetic */ C0496d f568b;

        C04951(C0496d c0496d, Context context) {
            this.f568b = c0496d;
            this.f567a = context;
        }

        public C0592f m747a() {
            return new C0592f(this.f567a);
        }

        public /* synthetic */ Object call() {
            return m747a();
        }
    }

    static {
        f569a = C0496d.class.getSimpleName();
    }

    private C0496d(Context context) {
        this.f571c = Executors.newSingleThreadExecutor().submit(new C04951(this, context));
    }

    public static C0496d m748a(Context context) {
        if (f570b == null) {
            Context applicationContext = context.getApplicationContext();
            synchronized (applicationContext) {
                if (f570b == null) {
                    f570b = new C0496d(applicationContext);
                }
            }
        }
        return f570b;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    @android.support.annotation.Nullable
    private com.facebook.ads.internal.p010h.p012b.C0592f m749a() {
        /*
        r4 = this;
        r0 = r4.f571c;	 Catch:{ InterruptedException -> 0x000d, ExecutionException -> 0x0017, TimeoutException -> 0x0019 }
        r2 = 500; // 0x1f4 float:7.0E-43 double:2.47E-321;
        r1 = java.util.concurrent.TimeUnit.MILLISECONDS;	 Catch:{ InterruptedException -> 0x000d, ExecutionException -> 0x0017, TimeoutException -> 0x0019 }
        r0 = r0.get(r2, r1);	 Catch:{ InterruptedException -> 0x000d, ExecutionException -> 0x0017, TimeoutException -> 0x0019 }
        r0 = (com.facebook.ads.internal.p010h.p012b.C0592f) r0;	 Catch:{ InterruptedException -> 0x000d, ExecutionException -> 0x0017, TimeoutException -> 0x0019 }
    L_0x000c:
        return r0;
    L_0x000d:
        r0 = move-exception;
    L_0x000e:
        r1 = f569a;
        r2 = "Timed out waiting for cache server.";
        android.util.Log.e(r1, r2, r0);
        r0 = 0;
        goto L_0x000c;
    L_0x0017:
        r0 = move-exception;
        goto L_0x000e;
    L_0x0019:
        r0 = move-exception;
        goto L_0x000e;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.facebook.ads.internal.c.d.a():com.facebook.ads.internal.h.b.f");
    }

    public void m750a(String str) {
        C0592f a = m749a();
        if (a != null) {
            a.m1086a(str);
        }
    }

    @Nullable
    public String m751b(String str) {
        C0592f a = m749a();
        return a == null ? null : a.m1087b(str);
    }
}
