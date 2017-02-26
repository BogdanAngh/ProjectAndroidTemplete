package com.google.android.gms.analytics;

import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.ecommerce.Product;
import com.google.android.gms.analytics.ecommerce.ProductAction;
import com.google.android.gms.analytics.ecommerce.Promotion;
import com.google.android.gms.analytics.internal.zzab;
import com.google.android.gms.analytics.internal.zzao;
import com.google.android.gms.analytics.internal.zzc;
import com.google.android.gms.analytics.internal.zze;
import com.google.android.gms.analytics.internal.zzf;
import com.google.android.gms.analytics.internal.zzh;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzms;
import com.google.android.gms.internal.zzmt;
import com.google.android.gms.internal.zzmu;
import com.google.android.gms.internal.zzmv;
import com.google.android.gms.internal.zzmw;
import com.google.android.gms.internal.zzmx;
import com.google.android.gms.internal.zzmy;
import com.google.android.gms.internal.zzmz;
import com.google.android.gms.internal.zzna;
import com.google.android.gms.internal.zznb;
import com.google.android.gms.internal.zznc;
import com.google.android.gms.internal.zznd;
import com.google.android.gms.internal.zzne;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzb extends zzc implements zzk {
    private static DecimalFormat as;
    private final zzf ao;
    private final String at;
    private final Uri au;
    private final boolean av;
    private final boolean aw;

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, String str) {
        this(com_google_android_gms_analytics_internal_zzf, str, true, false);
    }

    public zzb(zzf com_google_android_gms_analytics_internal_zzf, String str, boolean z, boolean z2) {
        super(com_google_android_gms_analytics_internal_zzf);
        zzaa.zzib(str);
        this.ao = com_google_android_gms_analytics_internal_zzf;
        this.at = str;
        this.av = z;
        this.aw = z2;
        this.au = zzdt(this.at);
    }

    private static void zza(Map<String, String> map, String str, double d) {
        if (d != 0.0d) {
            map.put(str, zzb(d));
        }
    }

    private static void zza(Map<String, String> map, String str, int i, int i2) {
        if (i > 0 && i2 > 0) {
            map.put(str, i + "x" + i2);
        }
    }

    private static void zza(Map<String, String> map, String str, boolean z) {
        if (z) {
            map.put(str, AppEventsConstants.EVENT_PARAM_VALUE_YES);
        }
    }

    private static String zzaq(Map<String, String> map) {
        StringBuilder stringBuilder = new StringBuilder();
        for (Entry entry : map.entrySet()) {
            if (stringBuilder.length() != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append((String) entry.getKey());
            stringBuilder.append("=");
            stringBuilder.append((String) entry.getValue());
        }
        return stringBuilder.toString();
    }

    static String zzb(double d) {
        if (as == null) {
            as = new DecimalFormat("0.######");
        }
        return as.format(d);
    }

    private static void zzb(Map<String, String> map, String str, String str2) {
        if (!TextUtils.isEmpty(str2)) {
            map.put(str, str2);
        }
    }

    public static Map<String, String> zzc(zze com_google_android_gms_analytics_zze) {
        CharSequence zzbm;
        Map hashMap = new HashMap();
        zzmw com_google_android_gms_internal_zzmw = (zzmw) com_google_android_gms_analytics_zze.zza(zzmw.class);
        if (com_google_android_gms_internal_zzmw != null) {
            for (Entry entry : com_google_android_gms_internal_zzmw.zzaap().entrySet()) {
                String zzi = zzi(entry.getValue());
                if (zzi != null) {
                    hashMap.put((String) entry.getKey(), zzi);
                }
            }
        }
        zznb com_google_android_gms_internal_zznb = (zznb) com_google_android_gms_analytics_zze.zza(zznb.class);
        if (com_google_android_gms_internal_zznb != null) {
            zzb(hashMap, "t", com_google_android_gms_internal_zznb.zzaba());
            zzb(hashMap, "cid", com_google_android_gms_internal_zznb.zzze());
            zzb(hashMap, "uid", com_google_android_gms_internal_zznb.getUserId());
            zzb(hashMap, "sc", com_google_android_gms_internal_zznb.zzabd());
            zza(hashMap, "sf", com_google_android_gms_internal_zznb.zzabf());
            zza(hashMap, "ni", com_google_android_gms_internal_zznb.zzabe());
            zzb(hashMap, "adid", com_google_android_gms_internal_zznb.zzabb());
            zza(hashMap, "ate", com_google_android_gms_internal_zznb.zzabc());
        }
        zznc com_google_android_gms_internal_zznc = (zznc) com_google_android_gms_analytics_zze.zza(zznc.class);
        if (com_google_android_gms_internal_zznc != null) {
            zzb(hashMap, "cd", com_google_android_gms_internal_zznc.zzabh());
            zza(hashMap, "a", (double) com_google_android_gms_internal_zznc.zzabi());
            zzb(hashMap, "dr", com_google_android_gms_internal_zznc.zzabj());
        }
        zzmz com_google_android_gms_internal_zzmz = (zzmz) com_google_android_gms_analytics_zze.zza(zzmz.class);
        if (com_google_android_gms_internal_zzmz != null) {
            zzb(hashMap, "ec", com_google_android_gms_internal_zzmz.getCategory());
            zzb(hashMap, "ea", com_google_android_gms_internal_zzmz.getAction());
            zzb(hashMap, "el", com_google_android_gms_internal_zzmz.getLabel());
            zza(hashMap, "ev", (double) com_google_android_gms_internal_zzmz.getValue());
        }
        zzmt com_google_android_gms_internal_zzmt = (zzmt) com_google_android_gms_analytics_zze.zza(zzmt.class);
        if (com_google_android_gms_internal_zzmt != null) {
            zzb(hashMap, "cn", com_google_android_gms_internal_zzmt.getName());
            zzb(hashMap, "cs", com_google_android_gms_internal_zzmt.getSource());
            zzb(hashMap, "cm", com_google_android_gms_internal_zzmt.zzaah());
            zzb(hashMap, "ck", com_google_android_gms_internal_zzmt.zzaai());
            zzb(hashMap, "cc", com_google_android_gms_internal_zzmt.getContent());
            zzb(hashMap, "ci", com_google_android_gms_internal_zzmt.getId());
            zzb(hashMap, "anid", com_google_android_gms_internal_zzmt.zzaaj());
            zzb(hashMap, "gclid", com_google_android_gms_internal_zzmt.zzaak());
            zzb(hashMap, "dclid", com_google_android_gms_internal_zzmt.zzaal());
            zzb(hashMap, "aclid", com_google_android_gms_internal_zzmt.zzaam());
        }
        zzna com_google_android_gms_internal_zzna = (zzna) com_google_android_gms_analytics_zze.zza(zzna.class);
        if (com_google_android_gms_internal_zzna != null) {
            zzb(hashMap, "exd", com_google_android_gms_internal_zzna.getDescription());
            zza(hashMap, "exf", com_google_android_gms_internal_zzna.zzaaz());
        }
        zznd com_google_android_gms_internal_zznd = (zznd) com_google_android_gms_analytics_zze.zza(zznd.class);
        if (com_google_android_gms_internal_zznd != null) {
            zzb(hashMap, "sn", com_google_android_gms_internal_zznd.zzabl());
            zzb(hashMap, "sa", com_google_android_gms_internal_zznd.getAction());
            zzb(hashMap, "st", com_google_android_gms_internal_zznd.getTarget());
        }
        zzne com_google_android_gms_internal_zzne = (zzne) com_google_android_gms_analytics_zze.zza(zzne.class);
        if (com_google_android_gms_internal_zzne != null) {
            zzb(hashMap, "utv", com_google_android_gms_internal_zzne.zzabm());
            zza(hashMap, "utt", (double) com_google_android_gms_internal_zzne.getTimeInMillis());
            zzb(hashMap, "utc", com_google_android_gms_internal_zzne.getCategory());
            zzb(hashMap, "utl", com_google_android_gms_internal_zzne.getLabel());
        }
        zzmu com_google_android_gms_internal_zzmu = (zzmu) com_google_android_gms_analytics_zze.zza(zzmu.class);
        if (com_google_android_gms_internal_zzmu != null) {
            for (Entry entry2 : com_google_android_gms_internal_zzmu.zzaan().entrySet()) {
                zzbm = zzc.zzbm(((Integer) entry2.getKey()).intValue());
                if (!TextUtils.isEmpty(zzbm)) {
                    hashMap.put(zzbm, (String) entry2.getValue());
                }
            }
        }
        zzmv com_google_android_gms_internal_zzmv = (zzmv) com_google_android_gms_analytics_zze.zza(zzmv.class);
        if (com_google_android_gms_internal_zzmv != null) {
            for (Entry entry22 : com_google_android_gms_internal_zzmv.zzaao().entrySet()) {
                zzbm = zzc.zzbo(((Integer) entry22.getKey()).intValue());
                if (!TextUtils.isEmpty(zzbm)) {
                    hashMap.put(zzbm, zzb(((Double) entry22.getValue()).doubleValue()));
                }
            }
        }
        zzmy com_google_android_gms_internal_zzmy = (zzmy) com_google_android_gms_analytics_zze.zza(zzmy.class);
        if (com_google_android_gms_internal_zzmy != null) {
            ProductAction zzaav = com_google_android_gms_internal_zzmy.zzaav();
            if (zzaav != null) {
                for (Entry entry3 : zzaav.build().entrySet()) {
                    if (((String) entry3.getKey()).startsWith("&")) {
                        hashMap.put(((String) entry3.getKey()).substring(1), (String) entry3.getValue());
                    } else {
                        hashMap.put((String) entry3.getKey(), (String) entry3.getValue());
                    }
                }
            }
            int i = 1;
            for (Promotion zzep : com_google_android_gms_internal_zzmy.zzaay()) {
                hashMap.putAll(zzep.zzep(zzc.zzbs(i)));
                i++;
            }
            i = 1;
            for (Product zzep2 : com_google_android_gms_internal_zzmy.zzaaw()) {
                hashMap.putAll(zzep2.zzep(zzc.zzbq(i)));
                i++;
            }
            i = 1;
            for (Entry entry222 : com_google_android_gms_internal_zzmy.zzaax().entrySet()) {
                List<Product> list = (List) entry222.getValue();
                String zzbv = zzc.zzbv(i);
                int i2 = 1;
                for (Product zzep22 : list) {
                    String valueOf = String.valueOf(zzbv);
                    String valueOf2 = String.valueOf(zzc.zzbt(i2));
                    hashMap.putAll(zzep22.zzep(valueOf2.length() != 0 ? valueOf.concat(valueOf2) : new String(valueOf)));
                    i2++;
                }
                if (!TextUtils.isEmpty((CharSequence) entry222.getKey())) {
                    String valueOf3 = String.valueOf(zzbv);
                    String valueOf4 = String.valueOf("nm");
                    hashMap.put(valueOf4.length() != 0 ? valueOf3.concat(valueOf4) : new String(valueOf3), (String) entry222.getKey());
                }
                i++;
            }
        }
        zzmx com_google_android_gms_internal_zzmx = (zzmx) com_google_android_gms_analytics_zze.zza(zzmx.class);
        if (com_google_android_gms_internal_zzmx != null) {
            zzb(hashMap, "ul", com_google_android_gms_internal_zzmx.getLanguage());
            zza(hashMap, "sd", (double) com_google_android_gms_internal_zzmx.zzaaq());
            zza(hashMap, "sr", com_google_android_gms_internal_zzmx.zzaar(), com_google_android_gms_internal_zzmx.zzaas());
            zza(hashMap, "vp", com_google_android_gms_internal_zzmx.zzaat(), com_google_android_gms_internal_zzmx.zzaau());
        }
        zzms com_google_android_gms_internal_zzms = (zzms) com_google_android_gms_analytics_zze.zza(zzms.class);
        if (com_google_android_gms_internal_zzms != null) {
            zzb(hashMap, "an", com_google_android_gms_internal_zzms.zzaae());
            zzb(hashMap, "aid", com_google_android_gms_internal_zzms.zzup());
            zzb(hashMap, "aiid", com_google_android_gms_internal_zzms.zzaag());
            zzb(hashMap, "av", com_google_android_gms_internal_zzms.zzaaf());
        }
        return hashMap;
    }

    static Uri zzdt(String str) {
        zzaa.zzib(str);
        Builder builder = new Builder();
        builder.scheme(ShareConstants.MEDIA_URI);
        builder.authority("google-analytics.com");
        builder.path(str);
        return builder.build();
    }

    private static String zzi(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            String str = (String) obj;
            return TextUtils.isEmpty(str) ? null : str;
        } else if (!(obj instanceof Double)) {
            return obj instanceof Boolean ? obj != Boolean.FALSE ? AppEventsConstants.EVENT_PARAM_VALUE_YES : null : String.valueOf(obj);
        } else {
            Double d = (Double) obj;
            return d.doubleValue() != 0.0d ? zzb(d.doubleValue()) : null;
        }
    }

    public void zzb(zze com_google_android_gms_analytics_zze) {
        zzaa.zzy(com_google_android_gms_analytics_zze);
        zzaa.zzb(com_google_android_gms_analytics_zze.zzzn(), (Object) "Can't deliver not submitted measurement");
        zzaa.zzht("deliver should be called on worker thread");
        zze zzzi = com_google_android_gms_analytics_zze.zzzi();
        zznb com_google_android_gms_internal_zznb = (zznb) zzzi.zzb(zznb.class);
        if (TextUtils.isEmpty(com_google_android_gms_internal_zznb.zzaba())) {
            zzaca().zzh(zzc(zzzi), "Ignoring measurement without type");
        } else if (TextUtils.isEmpty(com_google_android_gms_internal_zznb.zzze())) {
            zzaca().zzh(zzc(zzzi), "Ignoring measurement without client id");
        } else if (!this.ao.zzacn().getAppOptOut()) {
            double zzabf = com_google_android_gms_internal_zznb.zzabf();
            if (zzao.zza(zzabf, com_google_android_gms_internal_zznb.zzze())) {
                zzb("Sampling enabled. Hit sampled out. sampling rate", Double.valueOf(zzabf));
                return;
            }
            Map zzc = zzc(zzzi);
            zzc.put("v", AppEventsConstants.EVENT_PARAM_VALUE_YES);
            zzc.put("_v", zze.cS);
            zzc.put("tid", this.at);
            if (this.ao.zzacn().isDryRunEnabled()) {
                zzc("Dry run is enabled. GoogleAnalytics would have sent", zzaq(zzc));
                return;
            }
            Map hashMap = new HashMap();
            zzao.zzc(hashMap, "uid", com_google_android_gms_internal_zznb.getUserId());
            zzms com_google_android_gms_internal_zzms = (zzms) com_google_android_gms_analytics_zze.zza(zzms.class);
            if (com_google_android_gms_internal_zzms != null) {
                zzao.zzc(hashMap, "an", com_google_android_gms_internal_zzms.zzaae());
                zzao.zzc(hashMap, "aid", com_google_android_gms_internal_zzms.zzup());
                zzao.zzc(hashMap, "av", com_google_android_gms_internal_zzms.zzaaf());
                zzao.zzc(hashMap, "aiid", com_google_android_gms_internal_zzms.zzaag());
            }
            zzc.put("_s", String.valueOf(zzzg().zza(new zzh(0, com_google_android_gms_internal_zznb.zzze(), this.at, !TextUtils.isEmpty(com_google_android_gms_internal_zznb.zzabb()), 0, hashMap))));
            zzzg().zza(new zzab(zzaca(), zzc, com_google_android_gms_analytics_zze.zzzl(), true));
        }
    }

    public Uri zzyx() {
        return this.au;
    }
}
