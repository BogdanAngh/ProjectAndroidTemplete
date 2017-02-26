package com.facebook.ads.internal.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;
import com.facebook.ads.internal.p000i.C0710e;
import com.facebook.ads.internal.p002c.C0494c;

/* renamed from: com.facebook.ads.internal.util.p */
public class C0795p extends AsyncTask<String, Void, Bitmap[]> {
    private static final String f1460a;
    private final Context f1461b;
    private final ImageView f1462c;
    private final C0710e f1463d;
    private C0447q f1464e;

    static {
        f1460a = C0795p.class.getSimpleName();
    }

    public C0795p(ImageView imageView) {
        this.f1461b = imageView.getContext();
        this.f1463d = null;
        this.f1462c = imageView;
    }

    public C0795p(C0710e c0710e) {
        this.f1461b = c0710e.getContext();
        this.f1463d = c0710e;
        this.f1462c = null;
    }

    public C0795p m1661a(C0447q c0447q) {
        this.f1464e = c0447q;
        return this;
    }

    protected void m1662a(Bitmap[] bitmapArr) {
        if (this.f1462c != null) {
            this.f1462c.setImageBitmap(bitmapArr[0]);
        }
        if (this.f1463d != null) {
            this.f1463d.m1337a(bitmapArr[0], bitmapArr[1]);
        }
        if (this.f1464e != null) {
            this.f1464e.m428a();
        }
    }

    public void m1663a(String... strArr) {
        executeOnExecutor(THREAD_POOL_EXECUTOR, strArr);
    }

    protected Bitmap[] m1664b(String... strArr) {
        Bitmap a;
        Throwable th;
        Object obj;
        Object obj2;
        Object obj3;
        String str;
        String str2 = null;
        String str3 = strArr[0];
        try {
            a = C0494c.m741a(this.f1461b).m745a(str3);
            try {
                if (!(this.f1463d == null || a == null)) {
                    try {
                        ac acVar = new ac(a);
                        acVar.m1581a(Math.round(((float) a.getWidth()) / 40.0f));
                        str2 = acVar.m1580a();
                    } catch (Throwable th2) {
                        th = th2;
                        obj = a;
                        Log.e(f1460a, "Error downloading image: " + str3, th);
                        C0778d.m1599a(C0777c.m1596a(th, str2));
                        obj2 = a;
                        obj3 = str;
                        return new Bitmap[]{a, str2};
                    }
                }
            } catch (Throwable th22) {
                th = th22;
                obj = a;
                obj3 = str2;
                Log.e(f1460a, "Error downloading image: " + str3, th);
                C0778d.m1599a(C0777c.m1596a(th, str2));
                obj2 = a;
                obj3 = str;
                return new Bitmap[]{a, str2};
            }
        } catch (Throwable th3) {
            th = th3;
            str = str2;
            a = str2;
            Log.e(f1460a, "Error downloading image: " + str3, th);
            C0778d.m1599a(C0777c.m1596a(th, str2));
            obj2 = a;
            obj3 = str;
            return new Bitmap[]{a, str2};
        }
        return new Bitmap[]{a, str2};
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m1664b((String[]) objArr);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m1662a((Bitmap[]) obj);
    }
}
