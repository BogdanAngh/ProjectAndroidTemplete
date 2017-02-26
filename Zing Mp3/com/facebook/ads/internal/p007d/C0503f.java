package com.facebook.ads.internal.p007d;

import com.facebook.ads.AdError;

/* renamed from: com.facebook.ads.internal.d.f */
abstract class C0503f<T> {
    private C0508a f594a;

    /* renamed from: com.facebook.ads.internal.d.f.a */
    public enum C0508a {
        UNKNOWN(9000, "An unknown error has occurred."),
        DATABASE_SELECT(AdError.MEDIATION_ERROR_CODE, "Failed to read from database."),
        DATABASE_INSERT(3002, "Failed to insert row into database."),
        DATABASE_UPDATE(3003, "Failed to update row in database."),
        DATABASE_DELETE(3004, "Failed to delete row from database.");
        
        private final int f608f;
        private final String f609g;

        private C0508a(int i, String str) {
            this.f608f = i;
            this.f609g = str;
        }

        public int m788a() {
            return this.f608f;
        }

        public String m789b() {
            return this.f609g;
        }
    }

    C0503f() {
    }

    protected void m772a(C0508a c0508a) {
        this.f594a = c0508a;
    }

    abstract T m773b();

    public C0508a m774c() {
        return this.f594a;
    }
}
