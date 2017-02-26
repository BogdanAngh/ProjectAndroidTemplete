package com.google.android.gms.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.Pair;
import com.google.android.exoplayer.text.Cue;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.Builder;
import com.google.android.gms.common.zzc;
import com.google.android.gms.internal.zzaf.zza;
import com.google.android.gms.internal.zzaf.zzd;
import dalvik.system.DexClassLoader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class zzbc {
    private static final String TAG;
    protected static final Object zzaid;
    protected static final Object zzaih;
    private static zzc zzaij;
    private volatile boolean zzagy;
    protected Context zzahs;
    protected Context zzaht;
    private ExecutorService zzahu;
    private DexClassLoader zzahv;
    private zzax zzahw;
    private byte[] zzahx;
    private volatile AdvertisingIdClient zzahy;
    private Future zzahz;
    private volatile zza zzaia;
    private Future zzaib;
    private volatile boolean zzaic;
    private zzap zzaie;
    private GoogleApiClient zzaif;
    protected boolean zzaig;
    protected boolean zzaii;
    protected boolean zzaik;
    private Map<Pair<String, String>, zzbx> zzail;

    /* renamed from: com.google.android.gms.internal.zzbc.1 */
    class C12541 implements Runnable {
        final /* synthetic */ zzbc zzaim;

        C12541(zzbc com_google_android_gms_internal_zzbc) {
            this.zzaim = com_google_android_gms_internal_zzbc;
        }

        public void run() {
            this.zzaim.zzcx();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbc.2 */
    class C12552 implements Runnable {
        final /* synthetic */ zzbc zzaim;

        C12552(zzbc com_google_android_gms_internal_zzbc) {
            this.zzaim = com_google_android_gms_internal_zzbc;
        }

        public void run() {
            this.zzaim.zzda();
            synchronized (zzbc.zzaid) {
                this.zzaim.zzaic = false;
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbc.3 */
    class C12563 implements Runnable {
        final /* synthetic */ zzbc zzaim;

        C12563(zzbc com_google_android_gms_internal_zzbc) {
            this.zzaim = com_google_android_gms_internal_zzbc;
        }

        public void run() {
            zzdr.initialize(this.zzaim.zzahs);
        }
    }

    static {
        TAG = zzbc.class.getSimpleName();
        zzaid = new Object();
        zzaih = new Object();
        zzaij = null;
    }

    private zzbc(Context context) {
        this.zzahy = null;
        this.zzagy = false;
        this.zzahz = null;
        this.zzaia = null;
        this.zzaib = null;
        this.zzaic = false;
        this.zzaif = null;
        this.zzaig = false;
        this.zzaii = false;
        this.zzaik = false;
        this.zzahs = context;
        this.zzaht = context.getApplicationContext();
        this.zzail = new HashMap();
    }

    public static zzbc zza(Context context, String str, String str2, boolean z) {
        zzbc com_google_android_gms_internal_zzbc = new zzbc(context);
        try {
            if (com_google_android_gms_internal_zzbc.zzc(str, str2, z)) {
                return com_google_android_gms_internal_zzbc;
            }
        } catch (zzaz e) {
        }
        return null;
    }

    @NonNull
    private File zza(String str, File file, String str2) throws zzax.zza, IOException {
        File file2 = new File(String.format("%s/%s.jar", new Object[]{file, str2}));
        if (!file2.exists()) {
            byte[] zzc = this.zzahw.zzc(this.zzahx, str);
            file2.createNewFile();
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            fileOutputStream.write(zzc, 0, zzc.length);
            fileOutputStream.close();
        }
        return file2;
    }

    private void zza(File file) {
        if (file.exists()) {
            file.delete();
            return;
        }
        Log.d(TAG, String.format("File %s not found. No need for deletion", new Object[]{file.getAbsolutePath()}));
    }

    private void zza(File file, String str) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream2;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        File file2 = new File(String.format("%s/%s.tmp", new Object[]{file, str}));
        if (!file2.exists()) {
            File file3 = new File(String.format("%s/%s.dex", new Object[]{file, str}));
            if (file3.exists()) {
                long length = file3.length();
                if (length > 0) {
                    byte[] bArr = new byte[((int) length)];
                    try {
                        fileInputStream = new FileInputStream(file3);
                        try {
                            if (fileInputStream.read(bArr) <= 0) {
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e) {
                                    }
                                }
                                zza(file3);
                                return;
                            }
                            zzasa com_google_android_gms_internal_zzaf_zzd = new zzd();
                            com_google_android_gms_internal_zzaf_zzd.zzfx = VERSION.SDK.getBytes();
                            com_google_android_gms_internal_zzaf_zzd.zzfw = str.getBytes();
                            bArr = this.zzahw.zzd(this.zzahx, bArr).getBytes();
                            com_google_android_gms_internal_zzaf_zzd.data = bArr;
                            com_google_android_gms_internal_zzaf_zzd.zzfv = zzan.zzh(bArr);
                            file2.createNewFile();
                            fileOutputStream = new FileOutputStream(file2);
                            try {
                                byte[] zzf = zzasa.zzf(com_google_android_gms_internal_zzaf_zzd);
                                fileOutputStream.write(zzf, 0, zzf.length);
                                fileOutputStream.close();
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e2) {
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e3) {
                                    }
                                }
                                zza(file3);
                            } catch (IOException e4) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    try {
                                        fileInputStream2.close();
                                    } catch (IOException e5) {
                                    }
                                }
                                if (fileOutputStream != null) {
                                    try {
                                        fileOutputStream.close();
                                    } catch (IOException e6) {
                                    }
                                }
                                zza(file3);
                            } catch (NoSuchAlgorithmException e7) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                zza(file3);
                            } catch (zzax.zza e8) {
                                fileInputStream2 = fileInputStream;
                                if (fileInputStream2 != null) {
                                    fileInputStream2.close();
                                }
                                if (fileOutputStream != null) {
                                    fileOutputStream.close();
                                }
                                zza(file3);
                            } catch (Throwable th2) {
                                Throwable th3 = th2;
                                fileOutputStream2 = fileOutputStream;
                                th = th3;
                                if (fileInputStream != null) {
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e9) {
                                    }
                                }
                                if (fileOutputStream2 != null) {
                                    try {
                                        fileOutputStream2.close();
                                    } catch (IOException e10) {
                                    }
                                }
                                zza(file3);
                                throw th;
                            }
                        } catch (IOException e11) {
                            fileOutputStream = null;
                            fileInputStream2 = fileInputStream;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            zza(file3);
                        } catch (NoSuchAlgorithmException e12) {
                            fileOutputStream = null;
                            fileInputStream2 = fileInputStream;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            zza(file3);
                        } catch (zzax.zza e13) {
                            fileOutputStream = null;
                            fileInputStream2 = fileInputStream;
                            if (fileInputStream2 != null) {
                                fileInputStream2.close();
                            }
                            if (fileOutputStream != null) {
                                fileOutputStream.close();
                            }
                            zza(file3);
                        } catch (Throwable th4) {
                            th = th4;
                            if (fileInputStream != null) {
                                fileInputStream.close();
                            }
                            if (fileOutputStream2 != null) {
                                fileOutputStream2.close();
                            }
                            zza(file3);
                            throw th;
                        }
                    } catch (IOException e14) {
                        fileOutputStream = null;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        zza(file3);
                    } catch (NoSuchAlgorithmException e15) {
                        fileOutputStream = null;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        zza(file3);
                    } catch (zzax.zza e16) {
                        fileOutputStream = null;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        zza(file3);
                    } catch (Throwable th5) {
                        th = th5;
                        fileInputStream = null;
                        if (fileInputStream != null) {
                            fileInputStream.close();
                        }
                        if (fileOutputStream2 != null) {
                            fileOutputStream2.close();
                        }
                        zza(file3);
                        throw th;
                    }
                }
            }
        }
    }

    private boolean zzb(File file, String str) {
        FileInputStream fileInputStream;
        FileOutputStream fileOutputStream;
        FileInputStream fileInputStream2;
        Throwable th;
        FileOutputStream fileOutputStream2 = null;
        File file2 = new File(String.format("%s/%s.tmp", new Object[]{file, str}));
        if (!file2.exists()) {
            return false;
        }
        File file3 = new File(String.format("%s/%s.dex", new Object[]{file, str}));
        if (file3.exists()) {
            return false;
        }
        try {
            long length = file2.length();
            if (length <= 0) {
                zza(file2);
                return false;
            }
            byte[] bArr = new byte[((int) length)];
            fileInputStream = new FileInputStream(file2);
            try {
                if (fileInputStream.read(bArr) <= 0) {
                    Log.d(TAG, "Cannot read the cache data.");
                    zza(file2);
                    if (fileInputStream != null) {
                        try {
                            fileInputStream.close();
                        } catch (IOException e) {
                        }
                    }
                    return false;
                }
                zzd zze = zzd.zze(bArr);
                if (str.equals(new String(zze.zzfw)) && Arrays.equals(zze.zzfv, zzan.zzh(zze.data)) && Arrays.equals(zze.zzfx, VERSION.SDK.getBytes())) {
                    bArr = this.zzahw.zzc(this.zzahx, new String(zze.data));
                    file3.createNewFile();
                    FileOutputStream fileOutputStream3 = new FileOutputStream(file3);
                    try {
                        fileOutputStream3.write(bArr, 0, bArr.length);
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e2) {
                            }
                        }
                        if (fileOutputStream3 == null) {
                            return true;
                        }
                        try {
                            fileOutputStream3.close();
                            return true;
                        } catch (IOException e3) {
                            return true;
                        }
                    } catch (IOException e4) {
                        fileOutputStream = fileOutputStream3;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            try {
                                fileInputStream2.close();
                            } catch (IOException e5) {
                            }
                        }
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (IOException e6) {
                            }
                        }
                        return false;
                    } catch (NoSuchAlgorithmException e7) {
                        fileOutputStream = fileOutputStream3;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return false;
                    } catch (zzax.zza e8) {
                        fileOutputStream = fileOutputStream3;
                        fileInputStream2 = fileInputStream;
                        if (fileInputStream2 != null) {
                            fileInputStream2.close();
                        }
                        if (fileOutputStream != null) {
                            fileOutputStream.close();
                        }
                        return false;
                    } catch (Throwable th2) {
                        th = th2;
                        fileOutputStream2 = fileOutputStream3;
                        if (fileInputStream != null) {
                            try {
                                fileInputStream.close();
                            } catch (IOException e9) {
                            }
                        }
                        if (fileOutputStream2 != null) {
                            try {
                                fileOutputStream2.close();
                            } catch (IOException e10) {
                            }
                        }
                        throw th;
                    }
                }
                zza(file2);
                if (fileInputStream != null) {
                    try {
                        fileInputStream.close();
                    } catch (IOException e11) {
                    }
                }
                return false;
            } catch (IOException e12) {
                fileOutputStream = null;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return false;
            } catch (NoSuchAlgorithmException e13) {
                fileOutputStream = null;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return false;
            } catch (zzax.zza e14) {
                fileOutputStream = null;
                fileInputStream2 = fileInputStream;
                if (fileInputStream2 != null) {
                    fileInputStream2.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                return false;
            } catch (Throwable th3) {
                th = th3;
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
                if (fileOutputStream2 != null) {
                    fileOutputStream2.close();
                }
                throw th;
            }
        } catch (IOException e15) {
            fileOutputStream = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return false;
        } catch (NoSuchAlgorithmException e16) {
            fileOutputStream = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return false;
        } catch (zzax.zza e17) {
            fileOutputStream = null;
            if (fileInputStream2 != null) {
                fileInputStream2.close();
            }
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
            return false;
        } catch (Throwable th4) {
            th = th4;
            fileInputStream = null;
            if (fileInputStream != null) {
                fileInputStream.close();
            }
            if (fileOutputStream2 != null) {
                fileOutputStream2.close();
            }
            throw th;
        }
    }

    private void zzc(boolean z) {
        this.zzagy = z;
        if (z) {
            this.zzahz = this.zzahu.submit(new C12541(this));
        }
    }

    private boolean zzc(String str, String str2, boolean z) throws zzaz {
        this.zzahu = Executors.newCachedThreadPool();
        zzc(z);
        zzdb();
        zzcy();
        if (zzbe.zzdg() && ((Boolean) zzdr.zzbih.get()).booleanValue()) {
            throw new IllegalStateException("Task Context initialization must not be called from the UI thread.");
        }
        zzo(str);
        zzp(str2);
        this.zzaie = new zzap(this);
        return true;
    }

    private void zzcx() {
        try {
            if (this.zzahy == null && this.zzaht != null) {
                AdvertisingIdClient advertisingIdClient = new AdvertisingIdClient(this.zzaht);
                advertisingIdClient.start();
                this.zzahy = advertisingIdClient;
            }
        } catch (GooglePlayServicesNotAvailableException e) {
            this.zzahy = null;
        } catch (IOException e2) {
            this.zzahy = null;
        } catch (GooglePlayServicesRepairableException e3) {
            this.zzahy = null;
        }
    }

    private void zzcy() {
        if (((Boolean) zzdr.zzbij.get()).booleanValue()) {
            zzcz();
        }
    }

    private void zzda() {
        if (this.zzaii) {
            try {
                this.zzaia = com.google.android.gms.gass.internal.zza.zzi(this.zzahs, this.zzahs.getPackageName(), Integer.toString(this.zzahs.getPackageManager().getPackageInfo(this.zzahs.getPackageName(), 0).versionCode));
            } catch (NameNotFoundException e) {
            }
        }
    }

    private void zzdb() {
        boolean z = true;
        this.zzahu.execute(new C12563(this));
        zzaij = zzc.zzaql();
        this.zzaig = zzaij.zzbk(this.zzahs) > 0;
        if (zzaij.isGooglePlayServicesAvailable(this.zzahs) != 0) {
            z = false;
        }
        this.zzaii = z;
        if (this.zzahs.getApplicationContext() != null) {
            this.zzaif = new Builder(this.zzahs).addApi(com.google.android.gms.clearcut.zza.API).build();
        }
    }

    private void zzo(String str) throws zzaz {
        this.zzahw = new zzax(null);
        try {
            this.zzahx = this.zzahw.zzn(str);
        } catch (Throwable e) {
            throw new zzaz(e);
        }
    }

    private boolean zzp(String str) throws zzaz {
        File file;
        String zzba;
        File zza;
        try {
            File cacheDir = this.zzahs.getCacheDir();
            if (cacheDir == null) {
                cacheDir = this.zzahs.getDir("dex", 0);
                if (cacheDir == null) {
                    throw new zzaz();
                }
            }
            file = cacheDir;
            zzba = zzay.zzba();
            zza = zza(str, file, zzba);
            zzb(file, zzba);
            this.zzahv = new DexClassLoader(zza.getAbsolutePath(), file.getAbsolutePath(), null, this.zzahs.getClassLoader());
            zza(zza);
            zza(file, zzba);
            zzq(String.format("%s/%s.dex", new Object[]{file, zzba}));
            return true;
        } catch (Throwable e) {
            throw new zzaz(e);
        } catch (Throwable e2) {
            throw new zzaz(e2);
        } catch (Throwable e22) {
            throw new zzaz(e22);
        } catch (Throwable e222) {
            throw new zzaz(e222);
        } catch (Throwable th) {
            zza(zza);
            zza(file, zzba);
            zzq(String.format("%s/%s.dex", new Object[]{file, zzba}));
        }
    }

    private void zzq(String str) {
        zza(new File(str));
    }

    public Context getApplicationContext() {
        return this.zzaht;
    }

    public Context getContext() {
        return this.zzahs;
    }

    public boolean zza(String str, String str2, List<Class> list) {
        if (this.zzail.containsKey(new Pair(str, str2))) {
            return false;
        }
        this.zzail.put(new Pair(str, str2), new zzbx(this, str, str2, list));
        return true;
    }

    public int zzaw() {
        zzap zzct = zzct();
        return zzct != null ? zzct.zzaw() : Cue.TYPE_UNSET;
    }

    public Method zzc(String str, String str2) {
        zzbx com_google_android_gms_internal_zzbx = (zzbx) this.zzail.get(new Pair(str, str2));
        return com_google_android_gms_internal_zzbx == null ? null : com_google_android_gms_internal_zzbx.zzdq();
    }

    public ExecutorService zzcm() {
        return this.zzahu;
    }

    public DexClassLoader zzcn() {
        return this.zzahv;
    }

    public zzax zzco() {
        return this.zzahw;
    }

    public byte[] zzcp() {
        return this.zzahx;
    }

    public GoogleApiClient zzcq() {
        return this.zzaif;
    }

    public boolean zzcr() {
        return this.zzaig;
    }

    public boolean zzcs() {
        return this.zzaik;
    }

    public zzap zzct() {
        return this.zzaie;
    }

    public boolean zzcu() {
        return this.zzaii;
    }

    public zza zzcv() {
        return this.zzaia;
    }

    public Future zzcw() {
        return this.zzaib;
    }

    public void zzcz() {
        synchronized (zzaid) {
            if (!this.zzaic) {
                this.zzaib = this.zzahu.submit(new C12552(this));
                this.zzaic = true;
            }
        }
    }

    public AdvertisingIdClient zzdc() {
        if (!this.zzagy) {
            return null;
        }
        if (this.zzahy != null) {
            return this.zzahy;
        }
        if (this.zzahz != null) {
            try {
                this.zzahz.get(2000, TimeUnit.MILLISECONDS);
                this.zzahz = null;
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            } catch (TimeoutException e3) {
                this.zzahz.cancel(true);
            }
        }
        return this.zzahy;
    }

    public void zzdd() {
        synchronized (zzaih) {
            if (this.zzaik) {
                return;
            }
            if (!this.zzaii || this.zzaif == null) {
                this.zzaik = false;
            } else {
                this.zzaif.connect();
                this.zzaik = true;
            }
        }
    }

    public void zzde() {
        synchronized (zzaih) {
            if (this.zzaik && this.zzaif != null) {
                this.zzaif.disconnect();
                this.zzaik = false;
            }
        }
    }
}
