package com.google.android.gms.ads.internal.purchase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.SystemClock;
import com.facebook.appevents.AppEventsConstants;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzji;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

@zzji
public class zzh {
    private static final Object zzako;
    private static final String zzcfy;
    private static zzh zzcga;
    private final zza zzcfz;

    public class zza extends SQLiteOpenHelper {
        final /* synthetic */ zzh zzcgb;

        public zza(zzh com_google_android_gms_ads_internal_purchase_zzh, Context context, String str) {
            this.zzcgb = com_google_android_gms_ads_internal_purchase_zzh;
            super(context, str, null, 4);
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            sQLiteDatabase.execSQL(zzh.zzcfy);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
            zzb.zzdh("Database updated from version " + i + " to version " + i2);
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS InAppPurchase");
            onCreate(sQLiteDatabase);
        }
    }

    static {
        zzcfy = String.format(Locale.US, "CREATE TABLE IF NOT EXISTS %s ( %s INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, %s TEXT NOT NULL, %s TEXT NOT NULL, %s INTEGER)", new Object[]{"InAppPurchase", "purchase_id", "product_id", "developer_payload", "record_time"});
        zzako = new Object();
    }

    zzh(Context context) {
        this.zzcfz = new zza(this, context, "google_inapp_purchase.db");
    }

    public static zzh zzq(Context context) {
        zzh com_google_android_gms_ads_internal_purchase_zzh;
        synchronized (zzako) {
            if (zzcga == null) {
                zzcga = new zzh(context);
            }
            com_google_android_gms_ads_internal_purchase_zzh = zzcga;
        }
        return com_google_android_gms_ads_internal_purchase_zzh;
    }

    public int getRecordCount() {
        Cursor cursor = null;
        int i = 0;
        synchronized (zzako) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
            } else {
                try {
                    cursor = writableDatabase.rawQuery("select count(*) from InAppPurchase", null);
                    if (cursor.moveToFirst()) {
                        i = cursor.getInt(0);
                        if (cursor != null) {
                            cursor.close();
                        }
                    } else {
                        if (cursor != null) {
                            cursor.close();
                        }
                    }
                } catch (SQLiteException e) {
                    String str = "Error getting record count";
                    String valueOf = String.valueOf(e.getMessage());
                    zzb.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Throwable th) {
                    if (cursor != null) {
                        cursor.close();
                    }
                }
            }
        }
        return i;
    }

    public SQLiteDatabase getWritableDatabase() {
        try {
            return this.zzcfz.getWritableDatabase();
        } catch (SQLiteException e) {
            zzb.zzdi("Error opening writable conversion tracking database");
            return null;
        }
    }

    public zzf zza(Cursor cursor) {
        return cursor == null ? null : new zzf(cursor.getLong(0), cursor.getString(1), cursor.getString(2));
    }

    public void zza(zzf com_google_android_gms_ads_internal_purchase_zzf) {
        if (com_google_android_gms_ads_internal_purchase_zzf != null) {
            synchronized (zzako) {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    return;
                }
                writableDatabase.delete("InAppPurchase", String.format(Locale.US, "%s = %d", new Object[]{"purchase_id", Long.valueOf(com_google_android_gms_ads_internal_purchase_zzf.zzcft)}), null);
            }
        }
    }

    public void zzb(zzf com_google_android_gms_ads_internal_purchase_zzf) {
        if (com_google_android_gms_ads_internal_purchase_zzf != null) {
            synchronized (zzako) {
                SQLiteDatabase writableDatabase = getWritableDatabase();
                if (writableDatabase == null) {
                    return;
                }
                ContentValues contentValues = new ContentValues();
                contentValues.put("product_id", com_google_android_gms_ads_internal_purchase_zzf.zzcfv);
                contentValues.put("developer_payload", com_google_android_gms_ads_internal_purchase_zzf.zzcfu);
                contentValues.put("record_time", Long.valueOf(SystemClock.elapsedRealtime()));
                com_google_android_gms_ads_internal_purchase_zzf.zzcft = writableDatabase.insert("InAppPurchase", null, contentValues);
                if (((long) getRecordCount()) > HlsChunkSource.DEFAULT_MAX_BUFFER_TO_SWITCH_DOWN_MS) {
                    zzru();
                }
            }
        }
    }

    public List<zzf> zzg(long j) {
        Cursor query;
        SQLiteException e;
        String str;
        String valueOf;
        Throwable th;
        synchronized (zzako) {
            List<zzf> linkedList = new LinkedList();
            if (j <= 0) {
                return linkedList;
            }
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
                return linkedList;
            }
            try {
                query = writableDatabase.query("InAppPurchase", null, null, null, null, null, "record_time ASC", String.valueOf(j));
                try {
                    if (query.moveToFirst()) {
                        do {
                            linkedList.add(zza(query));
                        } while (query.moveToNext());
                    }
                    if (query != null) {
                        query.close();
                    }
                } catch (SQLiteException e2) {
                    e = e2;
                    try {
                        str = "Error extracing purchase info: ";
                        valueOf = String.valueOf(e.getMessage());
                        zzb.zzdi(valueOf.length() == 0 ? new String(str) : str.concat(valueOf));
                        if (query != null) {
                            query.close();
                        }
                        return linkedList;
                    } catch (Throwable th2) {
                        th = th2;
                        if (query != null) {
                            query.close();
                        }
                        throw th;
                    }
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                str = "Error extracing purchase info: ";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                zzb.zzdi(valueOf.length() == 0 ? new String(str) : str.concat(valueOf));
                if (query != null) {
                    query.close();
                }
                return linkedList;
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
            return linkedList;
        }
    }

    public void zzru() {
        Cursor query;
        SQLiteException e;
        String str;
        String valueOf;
        Throwable th;
        synchronized (zzako) {
            SQLiteDatabase writableDatabase = getWritableDatabase();
            if (writableDatabase == null) {
                return;
            }
            try {
                query = writableDatabase.query("InAppPurchase", null, null, null, null, null, "record_time ASC", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                if (query != null) {
                    try {
                        if (query.moveToFirst()) {
                            zza(zza(query));
                        }
                    } catch (SQLiteException e2) {
                        e = e2;
                        try {
                            str = "Error remove oldest record";
                            valueOf = String.valueOf(e.getMessage());
                            zzb.zzdi(valueOf.length() == 0 ? new String(str) : str.concat(valueOf));
                            if (query != null) {
                                query.close();
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (query != null) {
                                query.close();
                            }
                            throw th;
                        }
                    }
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e3) {
                e = e3;
                query = null;
                str = "Error remove oldest record";
                valueOf = String.valueOf(e.getMessage());
                if (valueOf.length() == 0) {
                }
                zzb.zzdi(valueOf.length() == 0 ? new String(str) : str.concat(valueOf));
                if (query != null) {
                    query.close();
                }
            } catch (Throwable th3) {
                th = th3;
                query = null;
                if (query != null) {
                    query.close();
                }
                throw th;
            }
        }
    }
}
