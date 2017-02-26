package com.facebook.ads.internal.p007d;

import android.content.ContentValues;
import android.database.Cursor;
import android.support.annotation.WorkerThread;
import com.facebook.share.internal.ShareConstants;
import java.util.Map;
import java.util.UUID;
import org.json.JSONObject;

/* renamed from: com.facebook.ads.internal.d.c */
public class C0501c extends C0500g {
    public static final C0499b f580a;
    public static final C0499b f581b;
    public static final C0499b f582c;
    public static final C0499b f583d;
    public static final C0499b f584e;
    public static final C0499b f585f;
    public static final C0499b f586g;
    public static final C0499b f587h;
    public static final C0499b[] f588i;
    private static final String f589k;

    static {
        f580a = new C0499b(0, "event_id", "TEXT PRIMARY KEY");
        f581b = new C0499b(1, "token_id", "TEXT REFERENCES tokens ON UPDATE CASCADE ON DELETE RESTRICT");
        f582c = new C0499b(2, "priority", "INTEGER");
        f583d = new C0499b(3, ShareConstants.MEDIA_TYPE, "TEXT");
        f584e = new C0499b(4, "time", "REAL");
        f585f = new C0499b(5, "session_time", "REAL");
        f586g = new C0499b(6, "session_id", "TEXT");
        f587h = new C0499b(7, ShareConstants.WEB_DIALOG_PARAM_DATA, "TEXT");
        f588i = new C0499b[]{f580a, f581b, f582c, f583d, f584e, f585f, f586g, f587h};
        f589k = C0500g.m757a("events", f588i);
    }

    public C0501c(C0506d c0506d) {
        super(c0506d);
    }

    public String m766a() {
        return "events";
    }

    @WorkerThread
    String m767a(String str, int i, String str2, double d, double d2, String str3, Map<String, String> map) {
        String uuid = UUID.randomUUID().toString();
        ContentValues contentValues = new ContentValues(7);
        contentValues.put(f580a.f577b, uuid);
        contentValues.put(f581b.f577b, str);
        contentValues.put(f582c.f577b, Integer.valueOf(i));
        contentValues.put(f583d.f577b, str2);
        contentValues.put(f584e.f577b, Double.valueOf(d));
        contentValues.put(f585f.f577b, Double.valueOf(d2));
        contentValues.put(f586g.f577b, str3);
        contentValues.put(f587h.f577b, map != null ? new JSONObject(map).toString() : null);
        m765e().insertOrThrow("events", null, contentValues);
        return uuid;
    }

    public boolean m768a(String str) {
        return m765e().delete("events", new StringBuilder().append(f580a.f577b).append(" = ?").toString(), new String[]{str}) > 0;
    }

    public C0499b[] m769b() {
        return f588i;
    }

    @WorkerThread
    Cursor m770c() {
        return m765e().rawQuery(f589k, null);
    }
}
