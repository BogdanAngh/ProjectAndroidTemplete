package com.facebook.ads.internal;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Parcel;
import android.text.TextUtils;
import com.facebook.ads.internal.util.C0785i;
import com.facebook.ads.internal.util.C0785i.C0784a;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import com.google.android.gms.common.GooglePlayServicesUtil;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.atomic.AtomicBoolean;

/* renamed from: com.facebook.ads.internal.f */
public class C0547f {
    public static final String f769a;
    private final String f770b;
    private final boolean f771c;
    private final C0527c f772d;

    /* renamed from: com.facebook.ads.internal.f.a */
    private static final class C0525a implements IInterface {
        private IBinder f724a;

        C0525a(IBinder iBinder) {
            this.f724a = iBinder;
        }

        public String m838a() {
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                this.f724a.transact(1, obtain, obtain2, 0);
                obtain2.readException();
                String readString = obtain2.readString();
                return readString;
            } finally {
                obtain2.recycle();
                obtain.recycle();
            }
        }

        public IBinder asBinder() {
            return this.f724a;
        }

        public boolean m839b() {
            boolean z = true;
            Parcel obtain = Parcel.obtain();
            Parcel obtain2 = Parcel.obtain();
            try {
                obtain.writeInterfaceToken("com.google.android.gms.ads.identifier.internal.IAdvertisingIdService");
                obtain.writeInt(1);
                this.f724a.transact(2, obtain, obtain2, 0);
                obtain2.readException();
                if (obtain2.readInt() == 0) {
                    z = false;
                }
                obtain2.recycle();
                obtain.recycle();
                return z;
            } catch (Throwable th) {
                obtain2.recycle();
                obtain.recycle();
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.f.b */
    private static final class C0526b implements ServiceConnection {
        private AtomicBoolean f725a;
        private final BlockingQueue<IBinder> f726b;

        private C0526b() {
            this.f725a = new AtomicBoolean(false);
            this.f726b = new LinkedBlockingDeque();
        }

        public IBinder m840a() {
            if (!this.f725a.compareAndSet(true, true)) {
                return (IBinder) this.f726b.take();
            }
            throw new IllegalStateException("Binder already consumed");
        }

        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            try {
                this.f726b.put(iBinder);
            } catch (InterruptedException e) {
            }
        }

        public void onServiceDisconnected(ComponentName componentName) {
        }
    }

    /* renamed from: com.facebook.ads.internal.f.c */
    public enum C0527c {
        SHARED_PREFS,
        FB4A,
        DIRECT,
        REFLECTION,
        SERVICE
    }

    static {
        f769a = C0547f.class.getSimpleName();
    }

    private C0547f(String str, boolean z, C0527c c0527c) {
        this.f770b = str;
        this.f771c = z;
        this.f772d = c0527c;
    }

    private static C0547f m917a(Context context) {
        try {
            Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
            if (advertisingIdInfo != null) {
                return new C0547f(advertisingIdInfo.getId(), advertisingIdInfo.isLimitAdTrackingEnabled(), C0527c.DIRECT);
            }
        } catch (Throwable th) {
        }
        return null;
    }

    public static C0547f m918a(Context context, C0784a c0784a) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot get advertising info on main thread.");
        } else if (c0784a != null && !TextUtils.isEmpty(c0784a.f1425b)) {
            return new C0547f(c0784a.f1425b, c0784a.f1426c, C0527c.FB4A);
        } else {
            C0547f a = C0547f.m917a(context);
            if (a == null || TextUtils.isEmpty(a.m921a())) {
                a = C0547f.m919b(context);
            }
            return (a == null || TextUtils.isEmpty(a.m921a())) ? C0547f.m920c(context) : a;
        }
    }

    private static C0547f m919b(Context context) {
        Method a = C0785i.m1625a("com.google.android.gms.common.GooglePlayServicesUtil", "isGooglePlayServicesAvailable", Context.class);
        if (a == null) {
            return null;
        }
        Object a2 = C0785i.m1616a(null, a, context);
        if (a2 == null || ((Integer) a2).intValue() != 0) {
            return null;
        }
        a = C0785i.m1625a("com.google.android.gms.ads.identifier.AdvertisingIdClient", "getAdvertisingIdInfo", Context.class);
        if (a == null) {
            return null;
        }
        Object a3 = C0785i.m1616a(null, a, context);
        if (a3 == null) {
            return null;
        }
        a = C0785i.m1624a(a3.getClass(), "getId", new Class[0]);
        Method a4 = C0785i.m1624a(a3.getClass(), "isLimitAdTrackingEnabled", new Class[0]);
        return (a == null || a4 == null) ? null : new C0547f((String) C0785i.m1616a(a3, a, new Object[0]), ((Boolean) C0785i.m1616a(a3, a4, new Object[0])).booleanValue(), C0527c.REFLECTION);
    }

    private static C0547f m920c(Context context) {
        ServiceConnection c0526b = new C0526b();
        Intent intent = new Intent("com.google.android.gms.ads.identifier.service.START");
        intent.setPackage(GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
        if (context.bindService(intent, c0526b, 1)) {
            C0547f c0547f;
            try {
                C0525a c0525a = new C0525a(c0526b.m840a());
                c0547f = new C0547f(c0525a.m838a(), c0525a.m839b(), C0527c.SERVICE);
                return c0547f;
            } catch (Exception e) {
                c0547f = e;
            } finally {
                context.unbindService(c0526b);
            }
        }
        return null;
    }

    public String m921a() {
        return this.f770b;
    }

    public boolean m922b() {
        return this.f771c;
    }

    public C0527c m923c() {
        return this.f772d;
    }
}
