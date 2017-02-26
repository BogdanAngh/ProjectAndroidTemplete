package com.google.android.gms.internal;

import android.content.ContentResolver;
import android.content.Context;
import android.util.Log;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.internal.zzz;
import com.mp3download.zingmp3.BuildConfig;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;

public class zzqh implements com.google.android.gms.clearcut.zza.zzb {
    private static final Charset UTF_8;
    static Boolean wH;
    final zza wI;

    static class zza {
        final ContentResolver mContentResolver;

        zza(Context context) {
            if (context == null || !zzbj(context)) {
                this.mContentResolver = null;
                return;
            }
            this.mContentResolver = context.getContentResolver();
            zzagp.zzb(this.mContentResolver, "gms:playlog:service:sampling_");
        }

        private static boolean zzbj(Context context) {
            if (zzqh.wH == null) {
                zzqh.wH = Boolean.valueOf(context.checkCallingOrSelfPermission("com.google.android.providers.gsf.permission.READ_GSERVICES") == 0);
            }
            return zzqh.wH.booleanValue();
        }

        long zzaqj() {
            return this.mContentResolver == null ? 0 : zzagp.getLong(this.mContentResolver, "android_id", 0);
        }

        String zzhf(String str) {
            if (this.mContentResolver == null) {
                return null;
            }
            ContentResolver contentResolver = this.mContentResolver;
            String valueOf = String.valueOf("gms:playlog:service:sampling_");
            String valueOf2 = String.valueOf(str);
            return zzagp.zza(contentResolver, valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), null);
        }
    }

    static class zzb {
        public final String wJ;
        public final long wK;
        public final long wL;

        public zzb(String str, long j, long j2) {
            this.wJ = str;
            this.wK = j;
            this.wL = j2;
        }

        public boolean equals(Object obj) {
            if (obj == this) {
                return true;
            }
            if (!(obj instanceof zzb)) {
                return false;
            }
            zzb com_google_android_gms_internal_zzqh_zzb = (zzb) obj;
            return zzz.equal(this.wJ, com_google_android_gms_internal_zzqh_zzb.wJ) && zzz.equal(Long.valueOf(this.wK), Long.valueOf(com_google_android_gms_internal_zzqh_zzb.wK)) && zzz.equal(Long.valueOf(this.wL), Long.valueOf(com_google_android_gms_internal_zzqh_zzb.wL));
        }

        public int hashCode() {
            return zzz.hashCode(this.wJ, Long.valueOf(this.wK), Long.valueOf(this.wL));
        }
    }

    static {
        UTF_8 = Charset.forName(C0989C.UTF8_NAME);
        wH = null;
    }

    public zzqh() {
        this(new zza(null));
    }

    public zzqh(Context context) {
        this(new zza(context));
    }

    zzqh(zza com_google_android_gms_internal_zzqh_zza) {
        this.wI = (zza) zzaa.zzy(com_google_android_gms_internal_zzqh_zza);
    }

    static boolean zza(long j, long j2, long j3) {
        if (j2 >= 0 && j3 >= 0) {
            return j3 > 0 && zzqi.zzd(j, j3) < j2;
        } else {
            throw new IllegalArgumentException("negative values not supported: " + j2 + "/" + j3);
        }
    }

    static long zzai(long j) {
        return zzqe.zzn(ByteBuffer.allocate(8).putLong(j).array());
    }

    static long zzd(String str, long j) {
        if (str == null || str.isEmpty()) {
            return zzai(j);
        }
        byte[] bytes = str.getBytes(UTF_8);
        ByteBuffer allocate = ByteBuffer.allocate(bytes.length + 8);
        allocate.put(bytes);
        allocate.putLong(j);
        return zzqe.zzn(allocate.array());
    }

    static zzb zzhe(String str) {
        int i = 0;
        if (str == null) {
            return null;
        }
        String str2 = BuildConfig.FLAVOR;
        int indexOf = str.indexOf(44);
        if (indexOf >= 0) {
            str2 = str.substring(0, indexOf);
            i = indexOf + 1;
        }
        int indexOf2 = str.indexOf(47, i);
        if (indexOf2 <= 0) {
            str2 = "LogSamplerImpl";
            String str3 = "Failed to parse the rule: ";
            String valueOf = String.valueOf(str);
            Log.e(str2, valueOf.length() != 0 ? str3.concat(valueOf) : new String(str3));
            return null;
        }
        try {
            long parseLong = Long.parseLong(str.substring(i, indexOf2));
            long parseLong2 = Long.parseLong(str.substring(indexOf2 + 1));
            if (parseLong >= 0 && parseLong2 >= 0) {
                return new zzb(str2, parseLong, parseLong2);
            }
            Log.e("LogSamplerImpl", "negative values not supported: " + parseLong + "/" + parseLong2);
            return null;
        } catch (Throwable e) {
            Throwable th = e;
            str3 = "LogSamplerImpl";
            String str4 = "parseLong() failed while parsing: ";
            valueOf = String.valueOf(str);
            Log.e(str3, valueOf.length() != 0 ? str4.concat(valueOf) : new String(str4), th);
            return null;
        }
    }

    public boolean zzh(String str, int i) {
        if (str == null || str.isEmpty()) {
            str = i >= 0 ? String.valueOf(i) : null;
        }
        if (str == null) {
            return true;
        }
        long zzaqj = this.wI.zzaqj();
        zzb zzhe = zzhe(this.wI.zzhf(str));
        return zzhe != null ? zza(zzd(zzhe.wJ, zzaqj), zzhe.wK, zzhe.wL) : true;
    }
}
