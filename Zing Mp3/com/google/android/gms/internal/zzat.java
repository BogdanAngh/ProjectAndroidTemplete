package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import com.google.android.gms.internal.zzaf.zza;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class zzat extends zzar {
    private static final String TAG;
    private static long startTime;
    protected static volatile zzbc zzagd;
    protected static final Object zzagw;
    static boolean zzagx;
    protected boolean zzagy;
    protected String zzagz;
    protected boolean zzaha;
    protected boolean zzahb;

    static {
        zzagw = new Object();
        TAG = zzat.class.getSimpleName();
        zzagd = null;
        zzagx = false;
        startTime = 0;
    }

    protected zzat(Context context, String str) {
        super(context);
        this.zzagy = false;
        this.zzaha = false;
        this.zzahb = false;
        this.zzagz = str;
        this.zzagy = false;
    }

    protected zzat(Context context, String str, boolean z) {
        super(context);
        this.zzagy = false;
        this.zzaha = false;
        this.zzahb = false;
        this.zzagz = str;
        this.zzagy = z;
    }

    static zzbd zza(zzbc com_google_android_gms_internal_zzbc, MotionEvent motionEvent, DisplayMetrics displayMetrics) throws zzaz {
        Throwable e;
        Method zzc = com_google_android_gms_internal_zzbc.zzc(zzay.zzcg(), zzay.zzch());
        if (zzc == null || motionEvent == null) {
            throw new zzaz();
        }
        try {
            return new zzbd((String) zzc.invoke(null, new Object[]{motionEvent, displayMetrics}));
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new zzaz(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new zzaz(e);
        }
    }

    protected static synchronized void zza(Context context, boolean z) {
        synchronized (zzat.class) {
            if (!zzagx) {
                startTime = zzbe.zzdf().longValue() / 1000;
                zzagd = zzb(context, z);
                zzagx = true;
            }
        }
    }

    private static void zza(zzbc com_google_android_gms_internal_zzbc) {
        List singletonList = Collections.singletonList(Context.class);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbm(), zzay.zzbn(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbw(), zzay.zzbx(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbu(), zzay.zzbv(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbg(), zzay.zzbh(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbq(), zzay.zzbr(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbc(), zzay.zzbd(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzci(), zzay.zzcj(), singletonList);
        singletonList = Arrays.asList(new Class[]{MotionEvent.class, DisplayMetrics.class});
        com_google_android_gms_internal_zzbc.zza(zzay.zzcg(), zzay.zzch(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzce(), zzay.zzcf(), singletonList);
        com_google_android_gms_internal_zzbc.zza(zzay.zzbk(), zzay.zzbl(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzcc(), zzay.zzcd(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzbs(), zzay.zzbt(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzbi(), zzay.zzbj(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzbo(), zzay.zzbp(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzca(), zzay.zzcb(), Collections.emptyList());
        com_google_android_gms_internal_zzbc.zza(zzay.zzbe(), zzay.zzbf(), Arrays.asList(new Class[]{Context.class, Boolean.TYPE, Boolean.TYPE}));
        com_google_android_gms_internal_zzbc.zza(zzay.zzby(), zzay.zzbz(), Arrays.asList(new Class[]{StackTraceElement[].class}));
        com_google_android_gms_internal_zzbc.zza(zzay.zzck(), zzay.zzcl(), Arrays.asList(new Class[]{View.class}));
    }

    private void zza(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza) {
        zzbd zza;
        zza.zza com_google_android_gms_internal_zzaf_zza_zza;
        int i = 0;
        try {
            zza = zza(com_google_android_gms_internal_zzbc, this.zzagj, this.zzagu);
            com_google_android_gms_internal_zzaf_zza.zzdn = zza.zzain;
            com_google_android_gms_internal_zzaf_zza.zzdo = zza.zzaio;
            com_google_android_gms_internal_zzaf_zza.zzdp = zza.zzaip;
            if (this.zzagt) {
                com_google_android_gms_internal_zzaf_zza.zzeb = zza.zzff;
                com_google_android_gms_internal_zzaf_zza.zzec = zza.zzfd;
            }
            if (((Boolean) zzdr.zzbhw.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
                com_google_android_gms_internal_zzaf_zza_zza = new zza.zza();
                zzbd zzb = zzb(this.zzagj);
                com_google_android_gms_internal_zzaf_zza_zza.zzdn = zzb.zzain;
                com_google_android_gms_internal_zzaf_zza_zza.zzdo = zzb.zzaio;
                com_google_android_gms_internal_zzaf_zza_zza.zzfi = zzb.zzaip;
                if (this.zzagt) {
                    com_google_android_gms_internal_zzaf_zza_zza.zzfd = zzb.zzfd;
                    com_google_android_gms_internal_zzaf_zza_zza.zzff = zzb.zzff;
                    com_google_android_gms_internal_zzaf_zza_zza.zzfh = Integer.valueOf(zzb.zzaiq.longValue() != 0 ? 1 : 0);
                    if (this.zzagm > 0) {
                        com_google_android_gms_internal_zzaf_zza_zza.zzfe = this.zzagu != null ? Long.valueOf(Math.round(((double) this.zzagr) / ((double) this.zzagm))) : null;
                        com_google_android_gms_internal_zzaf_zza_zza.zzfg = Long.valueOf(Math.round(((double) this.zzagq) / ((double) this.zzagm)));
                    }
                    com_google_android_gms_internal_zzaf_zza_zza.zzfk = zzb.zzfk;
                    com_google_android_gms_internal_zzaf_zza_zza.zzfj = zzb.zzfj;
                    com_google_android_gms_internal_zzaf_zza_zza.zzfl = Integer.valueOf(zzb.zzait.longValue() != 0 ? 1 : 0);
                    if (this.zzagp > 0) {
                        com_google_android_gms_internal_zzaf_zza_zza.zzfm = Long.valueOf(this.zzagp);
                    }
                }
                com_google_android_gms_internal_zzaf_zza.zzes = com_google_android_gms_internal_zzaf_zza_zza;
            }
        } catch (zzaz e) {
        }
        if (this.zzagl > 0) {
            com_google_android_gms_internal_zzaf_zza.zzeg = Long.valueOf(this.zzagl);
        }
        if (this.zzagm > 0) {
            com_google_android_gms_internal_zzaf_zza.zzef = Long.valueOf(this.zzagm);
        }
        if (this.zzagn > 0) {
            com_google_android_gms_internal_zzaf_zza.zzee = Long.valueOf(this.zzagn);
        }
        if (this.zzago > 0) {
            com_google_android_gms_internal_zzaf_zza.zzeh = Long.valueOf(this.zzago);
        }
        try {
            int size = this.zzagk.size() - 1;
            if (size > 0) {
                com_google_android_gms_internal_zzaf_zza.zzet = new zza.zza[size];
                while (i < size) {
                    zza = zza(com_google_android_gms_internal_zzbc, (MotionEvent) this.zzagk.get(i), this.zzagu);
                    com_google_android_gms_internal_zzaf_zza_zza = new zza.zza();
                    com_google_android_gms_internal_zzaf_zza_zza.zzdn = zza.zzain;
                    com_google_android_gms_internal_zzaf_zza_zza.zzdo = zza.zzaio;
                    com_google_android_gms_internal_zzaf_zza.zzet[i] = com_google_android_gms_internal_zzaf_zza_zza;
                    i++;
                }
            }
        } catch (zzaz e2) {
            com_google_android_gms_internal_zzaf_zza.zzet = null;
        }
    }

    protected static zzbc zzb(Context context, boolean z) {
        if (zzagd == null) {
            synchronized (zzagw) {
                if (zzagd == null) {
                    zzbc zza = zzbc.zza(context, zzay.getKey(), zzay.zzbb(), z);
                    zza(zza);
                    zzagd = zza;
                }
            }
        }
        return zzagd;
    }

    protected long zza(StackTraceElement[] stackTraceElementArr) throws zzaz {
        Throwable e;
        Method zzc = zzagd.zzc(zzay.zzby(), zzay.zzbz());
        if (zzc == null || stackTraceElementArr == null) {
            throw new zzaz();
        }
        try {
            return new zzba((String) zzc.invoke(null, new Object[]{stackTraceElementArr})).zzahn.longValue();
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new zzaz(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new zzaz(e);
        }
    }

    protected zza zza(Context context, View view) {
        zza com_google_android_gms_internal_zzaf_zza = new zza();
        if (!TextUtils.isEmpty(this.zzagz)) {
            com_google_android_gms_internal_zzaf_zza.zzda = this.zzagz;
        }
        zzbc zzb = zzb(context, this.zzagy);
        zzb.zzdd();
        zzb(zzb, com_google_android_gms_internal_zzaf_zza, view);
        zzb.zzde();
        return com_google_android_gms_internal_zzaf_zza;
    }

    protected zza zza(Context context, zzad.zza com_google_android_gms_internal_zzad_zza) {
        zza com_google_android_gms_internal_zzaf_zza = new zza();
        if (!TextUtils.isEmpty(this.zzagz)) {
            com_google_android_gms_internal_zzaf_zza.zzda = this.zzagz;
        }
        zzbc zzb = zzb(context, this.zzagy);
        zzb.zzdd();
        zza(zzb, com_google_android_gms_internal_zzaf_zza, com_google_android_gms_internal_zzad_zza);
        zzb.zzde();
        return com_google_android_gms_internal_zzaf_zza;
    }

    protected List<Callable<Void>> zza(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, View view) {
        ArrayList arrayList = new ArrayList();
        if (com_google_android_gms_internal_zzbc.zzcm() == null) {
            return arrayList;
        }
        int zzaw = com_google_android_gms_internal_zzbc.zzaw();
        arrayList.add(new zzbn(com_google_android_gms_internal_zzbc, com_google_android_gms_internal_zzaf_zza));
        ArrayList arrayList2 = arrayList;
        arrayList2.add(new zzbq(com_google_android_gms_internal_zzbc, zzay.zzbs(), zzay.zzbt(), com_google_android_gms_internal_zzaf_zza, zzaw, 1));
        arrayList2 = arrayList;
        arrayList2.add(new zzbl(com_google_android_gms_internal_zzbc, zzay.zzbk(), zzay.zzbl(), com_google_android_gms_internal_zzaf_zza, startTime, zzaw, 25));
        arrayList2 = arrayList;
        arrayList2.add(new zzbk(com_google_android_gms_internal_zzbc, zzay.zzbi(), zzay.zzbj(), com_google_android_gms_internal_zzaf_zza, zzaw, 44));
        arrayList2 = arrayList;
        arrayList2.add(new zzbg(com_google_android_gms_internal_zzbc, zzay.zzbc(), zzay.zzbd(), com_google_android_gms_internal_zzaf_zza, zzaw, 3));
        arrayList2 = arrayList;
        arrayList2.add(new zzbo(com_google_android_gms_internal_zzbc, zzay.zzbo(), zzay.zzbp(), com_google_android_gms_internal_zzaf_zza, zzaw, 22));
        if (((Boolean) zzdr.zzbic.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList2 = arrayList;
            arrayList2.add(new zzbj(com_google_android_gms_internal_zzbc, zzay.zzbg(), zzay.zzbh(), com_google_android_gms_internal_zzaf_zza, zzaw, 5));
        }
        if (((Boolean) zzdr.zzbhv.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList2 = arrayList;
            arrayList2.add(new zzbv(com_google_android_gms_internal_zzbc, zzay.zzci(), zzay.zzcj(), com_google_android_gms_internal_zzaf_zza, zzaw, 48));
        }
        if (((Boolean) zzdr.zzbia.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList2 = arrayList;
            arrayList2.add(new zzbt(com_google_android_gms_internal_zzbc, zzay.zzca(), zzay.zzcb(), com_google_android_gms_internal_zzaf_zza, zzaw, 51));
        }
        if (((Boolean) zzdr.zzbif.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList2 = arrayList;
            arrayList2.add(new zzbs(com_google_android_gms_internal_zzbc, zzay.zzby(), zzay.zzbz(), com_google_android_gms_internal_zzaf_zza, zzaw, 45, new Throwable().getStackTrace()));
        }
        if (((Boolean) zzdr.zzbig.get()).booleanValue()) {
            arrayList2 = arrayList;
            arrayList2.add(new zzbw(com_google_android_gms_internal_zzbc, zzay.zzck(), zzay.zzcl(), com_google_android_gms_internal_zzaf_zza, zzaw, 57, view));
        }
        return arrayList;
    }

    protected void zza(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, zzad.zza com_google_android_gms_internal_zzad_zza) {
        if (com_google_android_gms_internal_zzbc.zzcm() != null) {
            zza(zzb(com_google_android_gms_internal_zzbc, com_google_android_gms_internal_zzaf_zza, com_google_android_gms_internal_zzad_zza));
        }
    }

    protected void zza(List<Callable<Void>> list) {
        if (zzagd != null) {
            ExecutorService zzcm = zzagd.zzcm();
            if (zzcm != null && !list.isEmpty()) {
                try {
                    zzcm.invokeAll(list, ((Long) zzdr.zzbhm.get()).longValue(), TimeUnit.MILLISECONDS);
                } catch (Throwable e) {
                    Log.d(TAG, String.format("class methods got exception: %s", new Object[]{zzbe.zza(e)}));
                }
            }
        }
    }

    protected zzbd zzb(MotionEvent motionEvent) throws zzaz {
        Throwable e;
        Method zzc = zzagd.zzc(zzay.zzce(), zzay.zzcf());
        if (zzc == null || motionEvent == null) {
            throw new zzaz();
        }
        try {
            return new zzbd((String) zzc.invoke(null, new Object[]{motionEvent, this.zzagu}));
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new zzaz(e);
        } catch (InvocationTargetException e3) {
            e = e3;
            throw new zzaz(e);
        }
    }

    protected List<Callable<Void>> zzb(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, zzad.zza com_google_android_gms_internal_zzad_zza) {
        int zzaw = com_google_android_gms_internal_zzbc.zzaw();
        List arrayList = new ArrayList();
        String zzbe = zzay.zzbe();
        String zzbf = zzay.zzbf();
        boolean z = ((Boolean) zzdr.zzbhq.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue();
        arrayList.add(new zzbi(com_google_android_gms_internal_zzbc, zzbe, zzbf, com_google_android_gms_internal_zzaf_zza, zzaw, 27, z, com_google_android_gms_internal_zzad_zza));
        arrayList.add(new zzbl(com_google_android_gms_internal_zzbc, zzay.zzbk(), zzay.zzbl(), com_google_android_gms_internal_zzaf_zza, startTime, zzaw, 25));
        arrayList.add(new zzbq(com_google_android_gms_internal_zzbc, zzay.zzbs(), zzay.zzbt(), com_google_android_gms_internal_zzaf_zza, zzaw, 1));
        arrayList.add(new zzbr(com_google_android_gms_internal_zzbc, zzay.zzbu(), zzay.zzbv(), com_google_android_gms_internal_zzaf_zza, zzaw, 31));
        arrayList.add(new zzbu(com_google_android_gms_internal_zzbc, zzay.zzcc(), zzay.zzcd(), com_google_android_gms_internal_zzaf_zza, zzaw, 33));
        arrayList.add(new zzbh(com_google_android_gms_internal_zzbc, zzay.zzbw(), zzay.zzbx(), com_google_android_gms_internal_zzaf_zza, zzaw, 29));
        arrayList.add(new zzbj(com_google_android_gms_internal_zzbc, zzay.zzbg(), zzay.zzbh(), com_google_android_gms_internal_zzaf_zza, zzaw, 5));
        arrayList.add(new zzbp(com_google_android_gms_internal_zzbc, zzay.zzbq(), zzay.zzbr(), com_google_android_gms_internal_zzaf_zza, zzaw, 12));
        arrayList.add(new zzbg(com_google_android_gms_internal_zzbc, zzay.zzbc(), zzay.zzbd(), com_google_android_gms_internal_zzaf_zza, zzaw, 3));
        arrayList.add(new zzbk(com_google_android_gms_internal_zzbc, zzay.zzbi(), zzay.zzbj(), com_google_android_gms_internal_zzaf_zza, zzaw, 44));
        arrayList.add(new zzbo(com_google_android_gms_internal_zzbc, zzay.zzbo(), zzay.zzbp(), com_google_android_gms_internal_zzaf_zza, zzaw, 22));
        if (((Boolean) zzdr.zzbht.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList.add(new zzbv(com_google_android_gms_internal_zzbc, zzay.zzci(), zzay.zzcj(), com_google_android_gms_internal_zzaf_zza, zzaw, 48));
        }
        if (((Boolean) zzdr.zzbhy.get()).booleanValue() || ((Boolean) zzdr.zzbhr.get()).booleanValue()) {
            arrayList.add(new zzbt(com_google_android_gms_internal_zzbc, zzay.zzca(), zzay.zzcb(), com_google_android_gms_internal_zzaf_zza, zzaw, 51));
        }
        return arrayList;
    }

    protected void zzb(zzbc com_google_android_gms_internal_zzbc, zza com_google_android_gms_internal_zzaf_zza, View view) {
        zza(com_google_android_gms_internal_zzbc, com_google_android_gms_internal_zzaf_zza);
        zza(zza(com_google_android_gms_internal_zzbc, com_google_android_gms_internal_zzaf_zza, view));
    }
}
