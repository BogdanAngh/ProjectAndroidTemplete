package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzgh.zzc;
import java.util.Map;
import java.util.concurrent.Future;

@zzji
public final class zzjn {
    private final Object zzako;
    private String zzcec;
    private String zzcnz;
    private zzlq<zzjq> zzcoa;
    zzc zzcob;
    public final zzfe zzcoc;
    public final zzfe zzcod;
    public final zzfe zzcoe;

    /* renamed from: com.google.android.gms.internal.zzjn.1 */
    class C14161 implements zzfe {
        final /* synthetic */ zzjn zzcof;

        C14161(zzjn com_google_android_gms_internal_zzjn) {
            this.zzcof = com_google_android_gms_internal_zzjn;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            synchronized (this.zzcof.zzako) {
                if (this.zzcof.zzcoa.isDone()) {
                } else if (this.zzcof.zzcec.equals(map.get("request_id"))) {
                    zzjq com_google_android_gms_internal_zzjq = new zzjq(1, map);
                    String valueOf = String.valueOf(com_google_android_gms_internal_zzjq.getType());
                    String valueOf2 = String.valueOf(com_google_android_gms_internal_zzjq.zztm());
                    zzb.zzdi(new StringBuilder((String.valueOf(valueOf).length() + 24) + String.valueOf(valueOf2).length()).append("Invalid ").append(valueOf).append(" request error: ").append(valueOf2).toString());
                    this.zzcof.zzcoa.zzh(com_google_android_gms_internal_zzjq);
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjn.2 */
    class C14172 implements zzfe {
        final /* synthetic */ zzjn zzcof;

        C14172(zzjn com_google_android_gms_internal_zzjn) {
            this.zzcof = com_google_android_gms_internal_zzjn;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            synchronized (this.zzcof.zzako) {
                if (this.zzcof.zzcoa.isDone()) {
                    return;
                }
                zzjq com_google_android_gms_internal_zzjq = new zzjq(-2, map);
                if (this.zzcof.zzcec.equals(com_google_android_gms_internal_zzjq.getRequestId())) {
                    String url = com_google_android_gms_internal_zzjq.getUrl();
                    if (url == null) {
                        zzb.zzdi("URL missing in loadAdUrl GMSG.");
                        return;
                    }
                    if (url.contains("%40mediation_adapters%40")) {
                        String replaceAll = url.replaceAll("%40mediation_adapters%40", zzkv.zza(com_google_android_gms_internal_zzmd.getContext(), (String) map.get("check_adapters"), this.zzcof.zzcnz));
                        com_google_android_gms_internal_zzjq.setUrl(replaceAll);
                        url = "Ad request URL modified to ";
                        replaceAll = String.valueOf(replaceAll);
                        zzkx.m1697v(replaceAll.length() != 0 ? url.concat(replaceAll) : new String(url));
                    }
                    this.zzcof.zzcoa.zzh(com_google_android_gms_internal_zzjq);
                    return;
                }
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjn.3 */
    class C14183 implements zzfe {
        final /* synthetic */ zzjn zzcof;

        C14183(zzjn com_google_android_gms_internal_zzjn) {
            this.zzcof = com_google_android_gms_internal_zzjn;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            synchronized (this.zzcof.zzako) {
                if (this.zzcof.zzcoa.isDone()) {
                    return;
                }
                zzjq com_google_android_gms_internal_zzjq = new zzjq(-2, map);
                if (this.zzcof.zzcec.equals(com_google_android_gms_internal_zzjq.getRequestId())) {
                    com_google_android_gms_internal_zzjq.zztq();
                    this.zzcof.zzcoa.zzh(com_google_android_gms_internal_zzjq);
                    return;
                }
            }
        }
    }

    public zzjn(String str, String str2) {
        this.zzako = new Object();
        this.zzcoa = new zzlq();
        this.zzcoc = new C14161(this);
        this.zzcod = new C14172(this);
        this.zzcoe = new C14183(this);
        this.zzcnz = str2;
        this.zzcec = str;
    }

    public void zzb(zzc com_google_android_gms_internal_zzgh_zzc) {
        this.zzcob = com_google_android_gms_internal_zzgh_zzc;
    }

    public zzc zztj() {
        return this.zzcob;
    }

    public Future<zzjq> zztk() {
        return this.zzcoa;
    }

    public void zztl() {
    }
}
