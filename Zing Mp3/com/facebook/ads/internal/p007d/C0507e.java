package com.facebook.ads.internal.p007d;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/* renamed from: com.facebook.ads.internal.d.e */
public class C0507e extends SQLiteOpenHelper {
    private final C0506d f601a;

    public C0507e(Context context, C0506d c0506d) {
        super(context, "ads.db", null, 1);
        this.f601a = c0506d;
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        for (C0500g a : this.f601a.m784c()) {
            a.m761a(sQLiteDatabase);
        }
    }

    public void onDowngrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        onUpgrade(sQLiteDatabase, i, i2);
    }

    public void onOpen(SQLiteDatabase sQLiteDatabase) {
        super.onOpen(sQLiteDatabase);
        if (!sQLiteDatabase.isReadOnly()) {
            sQLiteDatabase.execSQL("PRAGMA foreign_keys = ON;");
        }
    }

    public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        for (C0500g c0500g : this.f601a.m784c()) {
            c0500g.m762b(sQLiteDatabase);
            c0500g.m761a(sQLiteDatabase);
        }
    }
}
