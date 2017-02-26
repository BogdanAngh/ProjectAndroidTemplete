package com.facebook.ads.internal.p007d;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Looper;
import android.support.annotation.WorkerThread;
import com.facebook.ads.internal.p005f.C0528d;
import com.facebook.ads.internal.p007d.C0503f.C0508a;
import com.facebook.ads.internal.util.C0785i;

/* renamed from: com.facebook.ads.internal.d.d */
public class C0506d {
    private final Context f597a;
    private final C0509h f598b;
    private final C0501c f599c;
    private SQLiteOpenHelper f600d;

    /* renamed from: com.facebook.ads.internal.d.d.1 */
    class C05021 extends AsyncTask<Void, Void, T> {
        final /* synthetic */ C0503f f590a;
        final /* synthetic */ C0498a f591b;
        final /* synthetic */ C0506d f592c;
        private C0508a f593d;

        C05021(C0506d c0506d, C0503f c0503f, C0498a c0498a) {
            this.f592c = c0506d;
            this.f590a = c0503f;
            this.f591b = c0498a;
        }

        protected T m771a(Void... voidArr) {
            T t = null;
            try {
                t = this.f590a.m773b();
                this.f593d = this.f590a.m774c();
                return t;
            } catch (SQLiteException e) {
                this.f593d = C0508a.UNKNOWN;
                return t;
            }
        }

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return m771a((Void[]) objArr);
        }

        protected void onPostExecute(T t) {
            if (this.f593d == null) {
                this.f591b.m755a(t);
            } else {
                this.f591b.m754a(this.f593d.m788a(), this.f593d.m789b());
            }
            this.f591b.m753a();
        }
    }

    /* renamed from: com.facebook.ads.internal.d.d.2 */
    class C05052 extends C0504i<String> {
        final /* synthetic */ C0528d f595a;
        final /* synthetic */ C0506d f596b;

        C05052(C0506d c0506d, C0528d c0528d) {
            this.f596b = c0506d;
            this.f595a = c0528d;
        }

        public String m775a() {
            try {
                SQLiteDatabase a = this.f596b.m779a();
                a.beginTransaction();
                String a2 = this.f596b.f599c.m767a(this.f596b.f598b.m791a(this.f595a.m845d()), this.f595a.m842a().f764c, this.f595a.m843b(), this.f595a.m846e(), this.f595a.m847f(), this.f595a.m848g(), this.f595a.m849h());
                a.setTransactionSuccessful();
                a.endTransaction();
                return a2;
            } catch (Exception e) {
                m772a(C0508a.DATABASE_INSERT);
                return null;
            }
        }

        public /* synthetic */ Object m776b() {
            return m775a();
        }
    }

    public C0506d(Context context) {
        this.f597a = context;
        this.f598b = new C0509h(this);
        this.f599c = new C0501c(this);
    }

    public synchronized SQLiteDatabase m779a() {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            throw new IllegalStateException("Cannot call getDatabase from the UI thread!");
        }
        if (this.f600d == null) {
            this.f600d = new C0507e(this.f597a, this);
        }
        return this.f600d.getWritableDatabase();
    }

    public <T> AsyncTask m780a(C0503f<T> c0503f, C0498a<T> c0498a) {
        return C0785i.m1613a(new C05021(this, c0503f, c0498a), new Void[0]);
    }

    public AsyncTask m781a(C0528d c0528d, C0498a<String> c0498a) {
        return m780a(new C05052(this, c0528d), (C0498a) c0498a);
    }

    @WorkerThread
    public boolean m782a(String str) {
        return this.f599c.m768a(str);
    }

    public void m783b() {
        for (C0500g d : m784c()) {
            d.m764d();
        }
        if (this.f600d != null) {
            this.f600d.close();
            this.f600d = null;
        }
    }

    public C0500g[] m784c() {
        return new C0500g[]{this.f598b, this.f599c};
    }

    @WorkerThread
    public Cursor m785d() {
        return this.f599c.m770c();
    }

    @WorkerThread
    public Cursor m786e() {
        return this.f598b.m793c();
    }

    @WorkerThread
    public void m787f() {
        this.f598b.m794f();
    }
}
