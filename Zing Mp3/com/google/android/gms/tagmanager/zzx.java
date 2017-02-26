package com.google.android.gms.tagmanager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build.VERSION;
import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.exoplayer.upstream.UdpDataSource;
import com.google.android.gms.common.util.zze;
import com.google.android.gms.common.util.zzh;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

class zzx implements zzc {
    private static final String aEP;
    private final Executor aEQ;
    private zza aER;
    private int aES;
    private final Context mContext;
    private zze zzaql;

    /* renamed from: com.google.android.gms.tagmanager.zzx.1 */
    class C15521 implements Runnable {
        final /* synthetic */ List aET;
        final /* synthetic */ long aEU;
        final /* synthetic */ zzx aEV;

        C15521(zzx com_google_android_gms_tagmanager_zzx, List list, long j) {
            this.aEV = com_google_android_gms_tagmanager_zzx;
            this.aET = list;
            this.aEU = j;
        }

        public void run() {
            this.aEV.zzb(this.aET, this.aEU);
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzx.2 */
    class C15532 implements Runnable {
        final /* synthetic */ zzx aEV;
        final /* synthetic */ com.google.android.gms.tagmanager.DataLayer.zzc.zza aEW;

        C15532(zzx com_google_android_gms_tagmanager_zzx, com.google.android.gms.tagmanager.DataLayer.zzc.zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
            this.aEV = com_google_android_gms_tagmanager_zzx;
            this.aEW = com_google_android_gms_tagmanager_DataLayer_zzc_zza;
        }

        public void run() {
            this.aEW.zzai(this.aEV.zzceu());
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzx.3 */
    class C15543 implements Runnable {
        final /* synthetic */ zzx aEV;
        final /* synthetic */ String aEX;

        C15543(zzx com_google_android_gms_tagmanager_zzx, String str) {
            this.aEV = com_google_android_gms_tagmanager_zzx;
            this.aEX = str;
        }

        public void run() {
            this.aEV.zzpe(this.aEX);
        }
    }

    class zza extends SQLiteOpenHelper {
        final /* synthetic */ zzx aEV;

        zza(zzx com_google_android_gms_tagmanager_zzx, Context context, String str) {
            this.aEV = com_google_android_gms_tagmanager_zzx;
            super(context, str, null, 1);
        }

        private boolean zza(String str, SQLiteDatabase sQLiteDatabase) {
            Cursor cursor;
            String str2;
            String valueOf;
            Throwable th;
            Cursor cursor2 = null;
            try {
                Cursor query = sQLiteDatabase.query("SQLITE_MASTER", new String[]{ShareConstants.WEB_DIALOG_PARAM_NAME}, "name=?", new String[]{str}, null, null, null);
                try {
                    boolean moveToFirst = query.moveToFirst();
                    if (query == null) {
                        return moveToFirst;
                    }
                    query.close();
                    return moveToFirst;
                } catch (SQLiteException e) {
                    cursor = query;
                    try {
                        str2 = "Error querying for table ";
                        valueOf = String.valueOf(str);
                        zzbo.zzdi(valueOf.length() == 0 ? new String(str2) : str2.concat(valueOf));
                        if (cursor != null) {
                            cursor.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        cursor2 = cursor;
                        th = th2;
                        if (cursor2 != null) {
                            cursor2.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    cursor2 = query;
                    if (cursor2 != null) {
                        cursor2.close();
                    }
                    throw th;
                }
            } catch (SQLiteException e2) {
                cursor = null;
                str2 = "Error querying for table ";
                valueOf = String.valueOf(str);
                if (valueOf.length() == 0) {
                }
                zzbo.zzdi(valueOf.length() == 0 ? new String(str2) : str2.concat(valueOf));
                if (cursor != null) {
                    cursor.close();
                }
                return false;
            } catch (Throwable th4) {
                th = th4;
                if (cursor2 != null) {
                    cursor2.close();
                }
                throw th;
            }
        }

        private void zzc(SQLiteDatabase sQLiteDatabase) {
            Cursor rawQuery = sQLiteDatabase.rawQuery("SELECT * FROM datalayer WHERE 0", null);
            Set hashSet = new HashSet();
            try {
                String[] columnNames = rawQuery.getColumnNames();
                for (Object add : columnNames) {
                    hashSet.add(add);
                }
                if (!hashSet.remove("key") || !hashSet.remove("value") || !hashSet.remove("ID") || !hashSet.remove("expires")) {
                    throw new SQLiteException("Database column missing");
                } else if (!hashSet.isEmpty()) {
                    throw new SQLiteException("Database has extra columns");
                }
            } finally {
                rawQuery.close();
            }
        }

        public SQLiteDatabase getWritableDatabase() {
            SQLiteDatabase sQLiteDatabase = null;
            try {
                sQLiteDatabase = super.getWritableDatabase();
            } catch (SQLiteException e) {
                this.aEV.mContext.getDatabasePath("google_tagmanager.db").delete();
            }
            return sQLiteDatabase == null ? super.getWritableDatabase() : sQLiteDatabase;
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            zzan.zzfd(sQLiteDatabase.getPath());
        }

        public void onOpen(SQLiteDatabase sQLiteDatabase) {
            if (VERSION.SDK_INT < 15) {
                Cursor rawQuery = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
                try {
                    rawQuery.moveToFirst();
                } finally {
                    rawQuery.close();
                }
            }
            if (zza("datalayer", sQLiteDatabase)) {
                zzc(sQLiteDatabase);
            } else {
                sQLiteDatabase.execSQL(zzx.aEP);
            }
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int i, int i2) {
        }
    }

    private static class zzb {
        final byte[] aEY;
        final String zzbcn;

        zzb(String str, byte[] bArr) {
            this.zzbcn = str;
            this.aEY = bArr;
        }

        public String toString() {
            String str = this.zzbcn;
            return new StringBuilder(String.valueOf(str).length() + 54).append("KeyAndSerialized: key = ").append(str).append(" serialized hash = ").append(Arrays.hashCode(this.aEY)).toString();
        }
    }

    static {
        aEP = String.format("CREATE TABLE IF NOT EXISTS %s ( '%s' INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, '%s' STRING NOT NULL, '%s' BLOB NOT NULL, '%s' INTEGER NOT NULL);", new Object[]{"datalayer", "ID", "key", "value", "expires"});
    }

    public zzx(Context context) {
        this(context, zzh.zzayl(), "google_tagmanager.db", UdpDataSource.DEFAULT_MAX_PACKET_SIZE, Executors.newSingleThreadExecutor());
    }

    zzx(Context context, zze com_google_android_gms_common_util_zze, String str, int i, Executor executor) {
        this.mContext = context;
        this.zzaql = com_google_android_gms_common_util_zze;
        this.aES = i;
        this.aEQ = executor;
        this.aER = new zza(this, this.mContext, str);
    }

    private void zzaaa(int i) {
        int zzcew = (zzcew() - this.aES) + i;
        if (zzcew > 0) {
            List zzaab = zzaab(zzcew);
            zzbo.zzdh("DataLayer store full, deleting " + zzaab.size() + " entries to make room.");
            zzg((String[]) zzaab.toArray(new String[0]));
        }
    }

    private List<String> zzaab(int i) {
        Cursor query;
        SQLiteException e;
        String str;
        String valueOf;
        Throwable th;
        List<String> arrayList = new ArrayList();
        if (i <= 0) {
            zzbo.zzdi("Invalid maxEntries specified. Skipping.");
            return arrayList;
        }
        SQLiteDatabase zzpf = zzpf("Error opening database for peekEntryIds.");
        if (zzpf == null) {
            return arrayList;
        }
        try {
            query = zzpf.query("datalayer", new String[]{"ID"}, null, null, null, null, String.format("%s ASC", new Object[]{"ID"}), Integer.toString(i));
            try {
                if (query.moveToFirst()) {
                    do {
                        arrayList.add(String.valueOf(query.getLong(0)));
                    } while (query.moveToNext());
                }
                if (query != null) {
                    query.close();
                }
            } catch (SQLiteException e2) {
                e = e2;
                try {
                    str = "Error in peekEntries fetching entryIds: ";
                    valueOf = String.valueOf(e.getMessage());
                    zzbo.zzdi(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
                    if (query != null) {
                        query.close();
                    }
                    return arrayList;
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
            str = "Error in peekEntries fetching entryIds: ";
            valueOf = String.valueOf(e.getMessage());
            if (valueOf.length() == 0) {
            }
            zzbo.zzdi(valueOf.length() == 0 ? str.concat(valueOf) : new String(str));
            if (query != null) {
                query.close();
            }
            return arrayList;
        } catch (Throwable th3) {
            th = th3;
            query = null;
            if (query != null) {
                query.close();
            }
            throw th;
        }
        return arrayList;
    }

    private List<zza> zzaj(List<zzb> list) {
        List<zza> arrayList = new ArrayList();
        for (zzb com_google_android_gms_tagmanager_zzx_zzb : list) {
            arrayList.add(new zza(com_google_android_gms_tagmanager_zzx_zzb.zzbcn, zzak(com_google_android_gms_tagmanager_zzx_zzb.aEY)));
        }
        return arrayList;
    }

    private Object zzak(byte[] bArr) {
        Object readObject;
        Throwable th;
        ObjectInputStream objectInputStream = null;
        InputStream byteArrayInputStream = new ByteArrayInputStream(bArr);
        ObjectInputStream objectInputStream2;
        try {
            objectInputStream2 = new ObjectInputStream(byteArrayInputStream);
            try {
                readObject = objectInputStream2.readObject();
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayInputStream.close();
            } catch (IOException e2) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (ClassNotFoundException e4) {
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e5) {
                    }
                }
                byteArrayInputStream.close();
                return readObject;
            } catch (Throwable th2) {
                th = th2;
                if (objectInputStream2 != null) {
                    try {
                        objectInputStream2.close();
                    } catch (IOException e6) {
                        throw th;
                    }
                }
                byteArrayInputStream.close();
                throw th;
            }
        } catch (IOException e7) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (ClassNotFoundException e8) {
            objectInputStream2 = objectInputStream;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            return readObject;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectInputStream2 = objectInputStream;
            th = th4;
            if (objectInputStream2 != null) {
                objectInputStream2.close();
            }
            byteArrayInputStream.close();
            throw th;
        }
        return readObject;
    }

    private List<zzb> zzak(List<zza> list) {
        List<zzb> arrayList = new ArrayList();
        for (zza com_google_android_gms_tagmanager_DataLayer_zza : list) {
            arrayList.add(new zzb(com_google_android_gms_tagmanager_DataLayer_zza.zzbcn, zzal(com_google_android_gms_tagmanager_DataLayer_zza.zzcyd)));
        }
        return arrayList;
    }

    private byte[] zzal(Object obj) {
        ObjectOutputStream objectOutputStream;
        Throwable th;
        byte[] bArr = null;
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            try {
                objectOutputStream.writeObject(obj);
                bArr = byteArrayOutputStream.toByteArray();
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e) {
                    }
                }
                byteArrayOutputStream.close();
            } catch (IOException e2) {
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e3) {
                    }
                }
                byteArrayOutputStream.close();
                return bArr;
            } catch (Throwable th2) {
                th = th2;
                if (objectOutputStream != null) {
                    try {
                        objectOutputStream.close();
                    } catch (IOException e4) {
                        throw th;
                    }
                }
                byteArrayOutputStream.close();
                throw th;
            }
        } catch (IOException e5) {
            objectOutputStream = bArr;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            return bArr;
        } catch (Throwable th3) {
            Throwable th4 = th3;
            objectOutputStream = bArr;
            th = th4;
            if (objectOutputStream != null) {
                objectOutputStream.close();
            }
            byteArrayOutputStream.close();
            throw th;
        }
        return bArr;
    }

    private synchronized void zzb(List<zzb> list, long j) {
        try {
            long currentTimeMillis = this.zzaql.currentTimeMillis();
            zzbu(currentTimeMillis);
            zzaaa(list.size());
            zzc(list, currentTimeMillis + j);
            zzcex();
        } catch (Throwable th) {
            zzcex();
        }
    }

    private void zzbu(long j) {
        SQLiteDatabase zzpf = zzpf("Error opening database for deleteOlderThan.");
        if (zzpf != null) {
            try {
                zzbo.m1699v("Deleted " + zzpf.delete("datalayer", "expires <= ?", new String[]{Long.toString(j)}) + " expired items");
            } catch (SQLiteException e) {
                zzbo.zzdi("Error deleting old entries.");
            }
        }
    }

    private void zzc(List<zzb> list, long j) {
        SQLiteDatabase zzpf = zzpf("Error opening database for writeEntryToDatabase.");
        if (zzpf != null) {
            for (zzb com_google_android_gms_tagmanager_zzx_zzb : list) {
                ContentValues contentValues = new ContentValues();
                contentValues.put("expires", Long.valueOf(j));
                contentValues.put("key", com_google_android_gms_tagmanager_zzx_zzb.zzbcn);
                contentValues.put("value", com_google_android_gms_tagmanager_zzx_zzb.aEY);
                zzpf.insert("datalayer", null, contentValues);
            }
        }
    }

    private List<zza> zzceu() {
        try {
            zzbu(this.zzaql.currentTimeMillis());
            List<zza> zzaj = zzaj(zzcev());
            return zzaj;
        } finally {
            zzcex();
        }
    }

    private List<zzb> zzcev() {
        SQLiteDatabase zzpf = zzpf("Error opening database for loadSerialized.");
        List<zzb> arrayList = new ArrayList();
        if (zzpf == null) {
            return arrayList;
        }
        Cursor query = zzpf.query("datalayer", new String[]{"key", "value"}, null, null, null, null, "ID", null);
        while (query.moveToNext()) {
            try {
                arrayList.add(new zzb(query.getString(0), query.getBlob(1)));
            } finally {
                query.close();
            }
        }
        return arrayList;
    }

    private int zzcew() {
        Cursor cursor = null;
        int i = 0;
        SQLiteDatabase zzpf = zzpf("Error opening database for getNumStoredEntries.");
        if (zzpf != null) {
            try {
                cursor = zzpf.rawQuery("SELECT COUNT(*) from datalayer", null);
                if (cursor.moveToFirst()) {
                    i = (int) cursor.getLong(0);
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (SQLiteException e) {
                zzbo.zzdi("Error getting numStoredEntries");
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
            }
        }
        return i;
    }

    private void zzcex() {
        try {
            this.aER.close();
        } catch (SQLiteException e) {
        }
    }

    private void zzg(String[] strArr) {
        if (strArr != null && strArr.length != 0) {
            SQLiteDatabase zzpf = zzpf("Error opening database for deleteEntries.");
            if (zzpf != null) {
                try {
                    zzpf.delete("datalayer", String.format("%s in (%s)", new Object[]{"ID", TextUtils.join(",", Collections.nCopies(strArr.length, "?"))}), strArr);
                } catch (SQLiteException e) {
                    String str = "Error deleting entries ";
                    String valueOf = String.valueOf(Arrays.toString(strArr));
                    zzbo.zzdi(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                }
            }
        }
    }

    private void zzpe(String str) {
        SQLiteDatabase zzpf = zzpf("Error opening database for clearKeysWithPrefix.");
        if (zzpf != null) {
            try {
                zzbo.m1699v("Cleared " + zzpf.delete("datalayer", "key = ? OR key LIKE ?", new String[]{str, String.valueOf(str).concat(".%")}) + " items");
            } catch (SQLiteException e) {
                String valueOf = String.valueOf(e);
                zzbo.zzdi(new StringBuilder((String.valueOf(str).length() + 44) + String.valueOf(valueOf).length()).append("Error deleting entries with key prefix: ").append(str).append(" (").append(valueOf).append(").").toString());
            } finally {
                zzcex();
            }
        }
    }

    private SQLiteDatabase zzpf(String str) {
        try {
            return this.aER.getWritableDatabase();
        } catch (SQLiteException e) {
            zzbo.zzdi(str);
            return null;
        }
    }

    public void zza(com.google.android.gms.tagmanager.DataLayer.zzc.zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
        this.aEQ.execute(new C15532(this, com_google_android_gms_tagmanager_DataLayer_zzc_zza));
    }

    public void zza(List<zza> list, long j) {
        this.aEQ.execute(new C15521(this, zzak((List) list), j));
    }

    public void zzpd(String str) {
        this.aEQ.execute(new C15543(this, str));
    }
}
