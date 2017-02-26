package com.facebook.ads.internal.p005f;

import android.support.annotation.Nullable;
import java.lang.ref.WeakReference;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

/* renamed from: com.facebook.ads.internal.f.o */
public class C0546o<T extends C0453p, E extends C0545n> {
    private final Map<Class<E>, List<WeakReference<T>>> f767a;
    private final Queue<E> f768b;

    public C0546o() {
        this.f767a = new HashMap();
        this.f768b = new ArrayDeque();
    }

    private void m912a(List<WeakReference<T>> list) {
        if (list != null) {
            int i = 0;
            for (int i2 = 0; i2 < list.size(); i2++) {
                WeakReference weakReference = (WeakReference) list.get(i2);
                if (weakReference.get() != null) {
                    int i3 = i + 1;
                    list.set(i, weakReference);
                    i = i3;
                }
            }
            for (int size = list.size() - 1; size >= i; size--) {
                list.remove(size);
            }
        }
    }

    private void m913b(E e) {
        if (this.f767a != null) {
            List list = (List) this.f767a.get(e.getClass());
            if (list != null) {
                m912a(list);
                if (!list.isEmpty()) {
                    for (WeakReference weakReference : new ArrayList(list)) {
                        C0453p c0453p = (C0453p) weakReference.get();
                        if (c0453p != null && c0453p.m449b(e)) {
                            c0453p.m448a(e);
                        }
                    }
                }
            }
        }
    }

    public synchronized void m914a(E e) {
        if (this.f768b.isEmpty()) {
            this.f768b.add(e);
            while (!this.f768b.isEmpty()) {
                m913b((C0545n) this.f768b.peek());
                this.f768b.remove();
            }
        } else {
            this.f768b.add(e);
        }
    }

    public synchronized boolean m915a(T t) {
        boolean z;
        if (t == null) {
            z = false;
        } else {
            Class a = t.m447a();
            if (this.f767a.get(a) == null) {
                this.f767a.put(a, new ArrayList());
            }
            List list = (List) this.f767a.get(a);
            m912a(list);
            int size = list.size();
            for (int i = 0; i < size; i++) {
                if (((WeakReference) list.get(i)).get() == t) {
                    z = false;
                    break;
                }
            }
            z = list.add(new WeakReference(t));
        }
        return z;
    }

    public synchronized boolean m916b(@Nullable T t) {
        boolean z;
        if (t == null) {
            z = false;
        } else {
            List list = (List) this.f767a.get(t.m447a());
            if (list == null) {
                z = false;
            } else {
                int size = list.size();
                for (int i = 0; i < size; i++) {
                    if (((WeakReference) list.get(i)).get() == t) {
                        ((WeakReference) list.get(i)).clear();
                        z = true;
                        break;
                    }
                }
                z = false;
            }
        }
        return z;
    }
}
