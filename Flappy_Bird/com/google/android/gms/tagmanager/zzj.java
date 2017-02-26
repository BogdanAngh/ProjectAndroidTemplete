package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.google.android.gms.internal.zzad;
import com.google.android.gms.internal.zzae;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj extends zzdd {
    private static final String ID;
    private static final String URL;
    private static final String zzaKr;
    private static final String zzaKs;
    static final String zzaKt;
    private static final Set<String> zzaKu;
    private final Context mContext;
    private final zza zzaKv;

    public interface zza {
        zzar zzyi();
    }

    /* renamed from: com.google.android.gms.tagmanager.zzj.1 */
    class C05291 implements zza {
        final /* synthetic */ Context zzqV;

        C05291(Context context) {
            this.zzqV = context;
        }

        public zzar zzyi() {
            return zzz.zzaF(this.zzqV);
        }
    }

    static {
        ID = zzad.ARBITRARY_PIXEL.toString();
        URL = zzae.URL.toString();
        zzaKr = zzae.ADDITIONAL_PARAMS.toString();
        zzaKs = zzae.UNREPEATABLE.toString();
        zzaKt = "gtm_" + ID + "_unrepeatable";
        zzaKu = new HashSet();
    }

    public zzj(Context context) {
        this(context, new C05291(context));
    }

    zzj(Context context, zza com_google_android_gms_tagmanager_zzj_zza) {
        super(ID, URL);
        this.zzaKv = com_google_android_gms_tagmanager_zzj_zza;
        this.mContext = context;
    }

    private synchronized boolean zzeb(String str) {
        boolean z = true;
        synchronized (this) {
            if (!zzed(str)) {
                if (zzec(str)) {
                    zzaKu.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    public void zzG(Map<String, com.google.android.gms.internal.zzag.zza> map) {
        String zzg = map.get(zzaKs) != null ? zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(zzaKs)) : null;
        if (zzg == null || !zzeb(zzg)) {
            Builder buildUpon = Uri.parse(zzdf.zzg((com.google.android.gms.internal.zzag.zza) map.get(URL))).buildUpon();
            com.google.android.gms.internal.zzag.zza com_google_android_gms_internal_zzag_zza = (com.google.android.gms.internal.zzag.zza) map.get(zzaKr);
            if (com_google_android_gms_internal_zzag_zza != null) {
                Object zzl = zzdf.zzl(com_google_android_gms_internal_zzag_zza);
                if (zzl instanceof List) {
                    for (Object zzl2 : (List) zzl2) {
                        if (zzl2 instanceof Map) {
                            for (Entry entry : ((Map) zzl2).entrySet()) {
                                buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                            }
                        } else {
                            zzbg.zzaz("ArbitraryPixel: additional params contains non-map: not sending partial hit: " + buildUpon.build().toString());
                            return;
                        }
                    }
                }
                zzbg.zzaz("ArbitraryPixel: additional params not a list: not sending partial hit: " + buildUpon.build().toString());
                return;
            }
            String uri = buildUpon.build().toString();
            this.zzaKv.zzyi().zzes(uri);
            zzbg.zzaB("ArbitraryPixel: url = " + uri);
            if (zzg != null) {
                synchronized (zzj.class) {
                    zzaKu.add(zzg);
                    zzcv.zza(this.mContext, zzaKt, zzg, "true");
                }
            }
        }
    }

    boolean zzec(String str) {
        return this.mContext.getSharedPreferences(zzaKt, 0).contains(str);
    }

    boolean zzed(String str) {
        return zzaKu.contains(str);
    }
}
