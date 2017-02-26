package com.google.android.gms.tagmanager;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.annotation.VisibleForTesting;
import com.google.android.gms.common.internal.zzaa;
import java.util.Random;

public class zzq {
    private final String aDY;
    private final Context mContext;
    private final Random zzbao;

    public zzq(Context context, String str) {
        this(context, str, new Random());
    }

    @VisibleForTesting
    zzq(Context context, String str, Random random) {
        this.mContext = (Context) zzaa.zzy(context);
        this.aDY = (String) zzaa.zzy(str);
        this.zzbao = random;
    }

    private SharedPreferences zzceo() {
        Context context = this.mContext;
        String valueOf = String.valueOf("_gtmContainerRefreshPolicy_");
        String valueOf2 = String.valueOf(this.aDY);
        return context.getSharedPreferences(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf), 0);
    }

    private long zzg(long j, long j2) {
        SharedPreferences zzceo = zzceo();
        long max = Math.max(0, zzceo.getLong("FORBIDDEN_COUNT", 0));
        return (long) (((float) (((long) ((((float) max) / ((float) ((Math.max(0, zzceo.getLong("SUCCESSFUL_COUNT", 0)) + max) + 1))) * ((float) (j2 - j)))) + j)) * this.zzbao.nextFloat());
    }

    public long zzcek() {
        return 43200000 + zzg(7200000, 259200000);
    }

    public long zzcel() {
        return 3600000 + zzg(600000, 86400000);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void zzcem() {
        SharedPreferences zzceo = zzceo();
        long j = zzceo.getLong("FORBIDDEN_COUNT", 0);
        long j2 = zzceo.getLong("SUCCESSFUL_COUNT", 0);
        Editor edit = zzceo.edit();
        long min = j == 0 ? 3 : Math.min(10, 1 + j);
        j = Math.max(0, Math.min(j2, 10 - min));
        edit.putLong("FORBIDDEN_COUNT", min);
        edit.putLong("SUCCESSFUL_COUNT", j);
        zzdd.zza(edit);
    }

    @SuppressLint({"CommitPrefEdits"})
    public void zzcen() {
        SharedPreferences zzceo = zzceo();
        long j = zzceo.getLong("SUCCESSFUL_COUNT", 0);
        long j2 = zzceo.getLong("FORBIDDEN_COUNT", 0);
        j = Math.min(10, j + 1);
        j2 = Math.max(0, Math.min(j2, 10 - j));
        Editor edit = zzceo.edit();
        edit.putLong("SUCCESSFUL_COUNT", j);
        edit.putLong("FORBIDDEN_COUNT", j2);
        zzdd.zza(edit);
    }
}
