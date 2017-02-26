package com.facebook.ads.internal.p006b;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import com.facebook.ads.internal.util.ad;
import java.util.ArrayList;
import java.util.List;

/* renamed from: com.facebook.ads.internal.b.a */
public final class C0482a implements ad<Bundle> {
    private final View f521a;
    private final List<C0486d> f522b;
    private final Context f523c;
    @NonNull
    private C0485c f524d;

    public C0482a(@NonNull Context context, @NonNull View view, @NonNull List<C0483b> list) {
        this.f523c = context;
        this.f521a = view;
        this.f522b = new ArrayList(list.size());
        for (C0483b c0486d : list) {
            this.f522b.add(new C0486d(c0486d));
        }
        this.f524d = new C0485c();
    }

    public C0482a(@NonNull Context context, @NonNull View view, @NonNull List<C0483b> list, @NonNull Bundle bundle) {
        this.f523c = context;
        this.f521a = view;
        this.f522b = new ArrayList(list.size());
        ArrayList parcelableArrayList = bundle.getParcelableArrayList("TESTS");
        for (int i = 0; i < list.size(); i++) {
            this.f522b.add(new C0486d((C0483b) list.get(i), (Bundle) parcelableArrayList.get(i)));
        }
        this.f524d = (C0485c) bundle.getSerializable("STATISTICS");
    }

    public void m699a() {
        this.f524d.m711a();
    }

    public void m700a(double d, double d2) {
        if (d2 >= 0.0d) {
            this.f524d.m714b(d, d2);
        }
        double a = C0487e.m720a(this.f521a, this.f523c);
        this.f524d.m712a(d, a);
        for (C0486d a2 : this.f522b) {
            a2.m719a(d, a);
        }
    }

    public C0485c m701b() {
        return this.f524d;
    }

    public Bundle getSaveInstanceState() {
        Bundle bundle = new Bundle();
        bundle.putSerializable("STATISTICS", this.f524d);
        ArrayList arrayList = new ArrayList(this.f522b.size());
        for (C0486d saveInstanceState : this.f522b) {
            arrayList.add(saveInstanceState.getSaveInstanceState());
        }
        bundle.putParcelableArrayList("TESTS", arrayList);
        return bundle;
    }
}
