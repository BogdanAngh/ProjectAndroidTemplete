package com.facebook.ads.internal.p010h.p012b.p013a;

import com.facebook.ads.internal.p010h.p012b.C0571a;
import com.facebook.ads.internal.p010h.p012b.C0597l;
import java.io.File;
import java.io.RandomAccessFile;

/* renamed from: com.facebook.ads.internal.h.b.a.b */
public class C0572b implements C0571a {
    public File f814a;
    private final C0570a f815b;
    private RandomAccessFile f816c;

    public C0572b(File file, C0570a c0570a) {
        if (c0570a == null) {
            try {
                throw new NullPointerException();
            } catch (Throwable e) {
                throw new C0597l("Error using file " + file + " as disc cache", e);
            }
        }
        this.f815b = c0570a;
        C0576d.m1020a(file.getParentFile());
        boolean exists = file.exists();
        this.f814a = exists ? file : new File(file.getParentFile(), file.getName() + ".download");
        this.f816c = new RandomAccessFile(this.f814a, exists ? "r" : "rw");
    }

    private boolean m1010a(File file) {
        return file.getName().endsWith(".download");
    }

    public synchronized int m1011a() {
        try {
        } catch (Throwable e) {
            throw new C0597l("Error reading length of file " + this.f814a, e);
        }
        return (int) this.f816c.length();
    }

    public synchronized int m1012a(byte[] bArr, long j, int i) {
        try {
            this.f816c.seek(j);
        } catch (Throwable e) {
            throw new C0597l(String.format("Error reading %d bytes with offset %d from file[%d bytes] to buffer[%d bytes]", new Object[]{Integer.valueOf(i), Long.valueOf(j), Integer.valueOf(m1011a()), Integer.valueOf(bArr.length)}), e);
        }
        return this.f816c.read(bArr, 0, i);
    }

    public synchronized void m1013a(byte[] bArr, int i) {
        try {
            if (m1016d()) {
                throw new C0597l("Error append cache: cache file " + this.f814a + " is completed!");
            }
            this.f816c.seek((long) m1011a());
            this.f816c.write(bArr, 0, i);
        } catch (Throwable e) {
            throw new C0597l(String.format("Error writing %d bytes to %s from buffer with size %d", new Object[]{Integer.valueOf(i), this.f816c, Integer.valueOf(bArr.length)}), e);
        }
    }

    public synchronized void m1014b() {
        try {
            this.f816c.close();
            this.f815b.m1003a(this.f814a);
        } catch (Throwable e) {
            throw new C0597l("Error closing file " + this.f814a, e);
        }
    }

    public synchronized void m1015c() {
        if (!m1016d()) {
            m1014b();
            File file = new File(this.f814a.getParentFile(), this.f814a.getName().substring(0, this.f814a.getName().length() - ".download".length()));
            if (this.f814a.renameTo(file)) {
                this.f814a = file;
                try {
                    this.f816c = new RandomAccessFile(this.f814a, "r");
                } catch (Throwable e) {
                    throw new C0597l("Error opening " + this.f814a + " as disc cache", e);
                }
            }
            throw new C0597l("Error renaming file " + this.f814a + " to " + file + " for completion!");
        }
    }

    public synchronized boolean m1016d() {
        return !m1010a(this.f814a);
    }
}
