package com.google.android.gms.tagmanager;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzj extends zzdk {
    private static final String ID;
    private static final String URL;
    private static final String aDR;
    private static final String aDS;
    static final String aDT;
    private static final Set<String> aDU;
    private final zza aDV;
    private final Context mContext;

    public interface zza {
        zzat zzcdv();
    }

    /* renamed from: com.google.android.gms.tagmanager.zzj.1 */
    class C15471 implements zza {
        final /* synthetic */ Context zzang;

        C15471(Context context) {
            this.zzang = context;
        }

        public zzat zzcdv() {
            return zzaa.zzdx(this.zzang);
        }
    }

    static {
        ID = zzag.ARBITRARY_PIXEL.toString();
        URL = zzah.URL.toString();
        aDR = zzah.ADDITIONAL_PARAMS.toString();
        aDS = zzah.UNREPEATABLE.toString();
        String str = ID;
        aDT = new StringBuilder(String.valueOf(str).length() + 17).append("gtm_").append(str).append("_unrepeatable").toString();
        aDU = new HashSet();
    }

    public zzj(Context context) {
        this(context, new C15471(context));
    }

    zzj(Context context, zza com_google_android_gms_tagmanager_zzj_zza) {
        super(ID, URL);
        this.aDV = com_google_android_gms_tagmanager_zzj_zza;
        this.mContext = context;
    }

    private synchronized boolean zzop(String str) {
        boolean z = true;
        synchronized (this) {
            if (!zzor(str)) {
                if (zzoq(str)) {
                    aDU.add(str);
                } else {
                    z = false;
                }
            }
        }
        return z;
    }

    public void zzba(Map<String, com.google.android.gms.internal.zzaj.zza> map) {
        String zzg = map.get(aDS) != null ? zzdm.zzg((com.google.android.gms.internal.zzaj.zza) map.get(aDS)) : null;
        if (zzg == null || !zzop(zzg)) {
            String valueOf;
            Builder buildUpon = Uri.parse(zzdm.zzg((com.google.android.gms.internal.zzaj.zza) map.get(URL))).buildUpon();
            com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) map.get(aDR);
            if (com_google_android_gms_internal_zzaj_zza != null) {
                Object zzl = zzdm.zzl(com_google_android_gms_internal_zzaj_zza);
                if (zzl instanceof List) {
                    for (Object zzl2 : (List) zzl2) {
                        if (zzl2 instanceof Map) {
                            for (Entry entry : ((Map) zzl2).entrySet()) {
                                buildUpon.appendQueryParameter(entry.getKey().toString(), entry.getValue().toString());
                            }
                        } else {
                            zzg = "ArbitraryPixel: additional params contains non-map: not sending partial hit: ";
                            valueOf = String.valueOf(buildUpon.build().toString());
                            zzbo.m1698e(valueOf.length() != 0 ? zzg.concat(valueOf) : new String(zzg));
                            return;
                        }
                    }
                }
                zzg = "ArbitraryPixel: additional params not a list: not sending partial hit: ";
                valueOf = String.valueOf(buildUpon.build().toString());
                zzbo.m1698e(valueOf.length() != 0 ? zzg.concat(valueOf) : new String(zzg));
                return;
            }
            valueOf = buildUpon.build().toString();
            this.aDV.zzcdv().zzpg(valueOf);
            String str = "ArbitraryPixel: url = ";
            valueOf = String.valueOf(valueOf);
            zzbo.m1699v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            if (zzg != null) {
                synchronized (zzj.class) {
                    aDU.add(zzg);
                    zzdd.zzc(this.mContext, aDT, zzg, ServerProtocol.DIALOG_RETURN_SCOPES_TRUE);
                }
            }
        }
    }

    boolean zzoq(String str) {
        return this.mContext.getSharedPreferences(aDT, 0).contains(str);
    }

    boolean zzor(String str) {
        return aDU.contains(str);
    }
}
