package com.facebook.ads.internal.p007d;

import android.database.sqlite.SQLiteDatabase;
import com.mp3download.zingmp3.BuildConfig;

/* renamed from: com.facebook.ads.internal.d.g */
public abstract class C0500g {
    protected final C0506d f579j;

    protected C0500g(C0506d c0506d) {
        this.f579j = c0506d;
    }

    public static String m757a(String str, C0499b[] c0499bArr) {
        StringBuilder stringBuilder = new StringBuilder("SELECT ");
        for (int i = 0; i < c0499bArr.length - 1; i++) {
            stringBuilder.append(c0499bArr[i].f577b);
            stringBuilder.append(", ");
        }
        stringBuilder.append(c0499bArr[c0499bArr.length - 1].f577b);
        stringBuilder.append(" FROM ");
        stringBuilder.append(str);
        return stringBuilder.toString();
    }

    public static String m758a(String str, C0499b[] c0499bArr, C0499b c0499b) {
        StringBuilder stringBuilder = new StringBuilder(C0500g.m757a(str, c0499bArr));
        stringBuilder.append(" WHERE ");
        stringBuilder.append(c0499b.f577b);
        stringBuilder.append(" = ?");
        return stringBuilder.toString();
    }

    private String m759c() {
        C0499b[] b = m763b();
        if (b.length < 1) {
            return null;
        }
        String str = BuildConfig.FLAVOR;
        for (int i = 0; i < b.length - 1; i++) {
            str = str + b[i].m756a() + ", ";
        }
        return str + b[b.length - 1].m756a();
    }

    public abstract String m760a();

    public void m761a(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("CREATE TABLE " + m760a() + " (" + m759c() + ")");
    }

    public void m762b(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + m760a());
    }

    public abstract C0499b[] m763b();

    public void m764d() {
    }

    protected SQLiteDatabase m765e() {
        return this.f579j.m779a();
    }
}
