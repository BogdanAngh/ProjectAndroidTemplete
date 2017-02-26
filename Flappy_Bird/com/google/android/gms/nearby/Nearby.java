package com.google.android.gms.nearby;

import com.google.android.gms.common.api.Api;
import com.google.android.gms.common.api.Api.ApiOptions.NoOptions;
import com.google.android.gms.common.api.Scope;
import com.google.android.gms.internal.zzoi;
import com.google.android.gms.internal.zzoq;
import com.google.android.gms.nearby.bootstrap.zza;
import com.google.android.gms.nearby.connection.Connections;
import com.google.android.gms.nearby.messages.internal.zzg;
import com.google.android.gms.nearby.messages.internal.zzh;
import com.google.android.gms.nearby.messages.zzc;
import com.google.android.gms.nearby.messages.zzd;
import com.google.android.gms.nearby.messages.zze;
import com.google.android.gms.nearby.sharing.internal.zzi;

public final class Nearby {
    public static final Api<NoOptions> CONNECTIONS_API;
    public static final Connections Connections;
    public static final Api<zze> zzaEX;
    public static final zzc zzaEY;
    public static final zzd zzaEZ;
    public static final Api<NoOptions> zzaFa;
    public static final com.google.android.gms.nearby.sharing.zzd zzaFb;
    public static final com.google.android.gms.nearby.sharing.zze zzaFc;
    public static final Api<NoOptions> zzaFd;
    public static final zza zzaFe;

    static {
        CONNECTIONS_API = new Api("Nearby.CONNECTIONS_API", zzoq.zzNY, zzoq.zzNX, new Scope[0]);
        Connections = new zzoq();
        zzaEX = new Api("Nearby.MESSAGES_API", zzg.zzNY, zzg.zzNX, new Scope[0]);
        zzaEY = new zzg();
        zzaEZ = new zzh();
        zzaFa = new Api("Nearby.SHARING_API", com.google.android.gms.nearby.sharing.internal.zzh.zzNY, com.google.android.gms.nearby.sharing.internal.zzh.zzNX, new Scope[0]);
        zzaFb = new com.google.android.gms.nearby.sharing.internal.zzh();
        zzaFc = new zzi();
        zzaFd = new Api("Nearby.BOOTSTRAP_API", zzoi.zzNY, zzoi.zzNX, new Scope[0]);
        zzaFe = new zzoi();
    }

    private Nearby() {
    }
}
