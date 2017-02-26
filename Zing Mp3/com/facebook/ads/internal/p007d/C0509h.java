package com.facebook.ads.internal.p007d;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.support.annotation.WorkerThread;
import android.text.TextUtils;
import java.util.UUID;

/* renamed from: com.facebook.ads.internal.d.h */
public class C0509h extends C0500g {
    public static final C0499b f610a;
    public static final C0499b f611b;
    public static final C0499b[] f612c;
    private static final String f613d;
    private static final String f614e;
    private static final String f615f;
    private static final String f616g;

    static {
        f610a = new C0499b(0, "token_id", "TEXT PRIMARY KEY");
        f611b = new C0499b(1, "token", "TEXT");
        f612c = new C0499b[]{f610a, f611b};
        f613d = C0509h.class.getSimpleName();
        f614e = C0500g.m757a("tokens", f612c);
        f615f = C0500g.m758a("tokens", f612c, f611b);
        f616g = "DELETE FROM tokens WHERE NOT EXISTS (SELECT 1 FROM events WHERE tokens." + f610a.f577b + " = " + "events" + "." + C0501c.f581b.f577b + ")";
    }

    public C0509h(C0506d c0506d) {
        super(c0506d);
    }

    public String m790a() {
        return "tokens";
    }

    @WorkerThread
    String m791a(String str) {
        Cursor rawQuery;
        Throwable th;
        if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Invalid token.");
        }
        try {
            rawQuery = m765e().rawQuery(f615f, new String[]{str});
            try {
                String string = rawQuery.moveToNext() ? rawQuery.getString(f610a.f576a) : null;
                if (TextUtils.isEmpty(string)) {
                    string = UUID.randomUUID().toString();
                    ContentValues contentValues = new ContentValues(2);
                    contentValues.put(f610a.f577b, string);
                    contentValues.put(f611b.f577b, str);
                    m765e().insertOrThrow("tokens", null, contentValues);
                    if (rawQuery != null) {
                        rawQuery.close();
                    }
                } else if (rawQuery != null) {
                    rawQuery.close();
                }
                return string;
            } catch (Throwable th2) {
                th = th2;
                if (rawQuery != null) {
                    rawQuery.close();
                }
                throw th;
            }
        } catch (Throwable th3) {
            th = th3;
            rawQuery = null;
            if (rawQuery != null) {
                rawQuery.close();
            }
            throw th;
        }
    }

    public C0499b[] m792b() {
        return f612c;
    }

    @WorkerThread
    Cursor m793c() {
        return m765e().rawQuery(f614e, null);
    }

    @WorkerThread
    public void m794f() {
        try {
            m765e().execSQL(f616g);
        } catch (SQLException e) {
        }
    }
}
