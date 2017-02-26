package com.facebook.ads.internal.p010h.p011a;

import android.os.AsyncTask;

/* renamed from: com.facebook.ads.internal.h.a.h */
public class C0561h extends AsyncTask<C0562l, Void, C0567n> implements C0556c {
    private C0554a f796a;
    private C0555b f797b;
    private Exception f798c;

    public C0561h(C0554a c0554a, C0555b c0555b) {
        this.f796a = c0554a;
        this.f797b = c0555b;
    }

    protected C0567n m979a(C0562l... c0562lArr) {
        if (c0562lArr != null) {
            try {
                if (c0562lArr.length > 0) {
                    return this.f796a.m948a(c0562lArr[0]);
                }
            } catch (Exception e) {
                this.f798c = e;
                cancel(true);
                return null;
            }
        }
        throw new IllegalArgumentException("DoHttpRequestTask takes exactly one argument of type HttpRequest");
    }

    public void m980a(C0562l c0562l) {
        super.execute(new C0562l[]{c0562l});
    }

    protected void m981a(C0567n c0567n) {
        this.f797b.m965a(c0567n);
    }

    protected /* synthetic */ Object doInBackground(Object[] objArr) {
        return m979a((C0562l[]) objArr);
    }

    protected void onCancelled() {
        this.f797b.m966a(this.f798c);
    }

    protected /* synthetic */ void onPostExecute(Object obj) {
        m981a((C0567n) obj);
    }
}
