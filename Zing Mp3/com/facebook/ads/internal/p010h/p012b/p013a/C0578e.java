package com.facebook.ads.internal.p010h.p012b.p013a;

import android.util.Log;
import java.io.File;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* renamed from: com.facebook.ads.internal.h.b.a.e */
abstract class C0578e implements C0570a {
    private final ExecutorService f819a;

    /* renamed from: com.facebook.ads.internal.h.b.a.e.a */
    private class C0577a implements Callable<Void> {
        final /* synthetic */ C0578e f817a;
        private final File f818b;

        public C0577a(C0578e c0578e, File file) {
            this.f817a = c0578e;
            this.f818b = file;
        }

        public Void m1025a() {
            this.f817a.m1029b(this.f818b);
            return null;
        }

        public /* synthetic */ Object call() {
            return m1025a();
        }
    }

    C0578e() {
        this.f819a = Executors.newSingleThreadExecutor();
    }

    private void m1027a(List<File> list) {
        long b = m1028b((List) list);
        int size = list.size();
        int i = size;
        for (File file : list) {
            if (!m1031a(file, b, i)) {
                long length = file.length();
                if (file.delete()) {
                    i--;
                    b -= length;
                    Log.i("ProxyCache", "Cache file " + file + " is deleted because it exceeds cache limit");
                    size = i;
                    i = size;
                } else {
                    Log.e("ProxyCache", "Error deleting file " + file + " for trimming cache");
                }
            }
            size = i;
            i = size;
        }
    }

    private long m1028b(List<File> list) {
        long j = 0;
        for (File length : list) {
            j = length.length() + j;
        }
        return j;
    }

    private void m1029b(File file) {
        C0576d.m1022c(file);
        m1027a(C0576d.m1021b(file.getParentFile()));
    }

    public void m1030a(File file) {
        this.f819a.submit(new C0577a(this, file));
    }

    protected abstract boolean m1031a(File file, long j, int i);
}
