package com.google.android.gms.analytics.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.Utility;
import com.google.android.gms.common.internal.zzaa;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class zzab {
    private final List<Command> fi;
    private final long fj;
    private final long fk;
    private final int fl;
    private final boolean fm;
    private final String fn;
    private final Map<String, String> zzbly;

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z) {
        this(com_google_android_gms_analytics_internal_zzc, map, j, z, 0, 0, null);
    }

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z, long j2, int i) {
        this(com_google_android_gms_analytics_internal_zzc, map, j, z, j2, i, null);
    }

    public zzab(zzc com_google_android_gms_analytics_internal_zzc, Map<String, String> map, long j, boolean z, long j2, int i, List<Command> list) {
        zzaa.zzy(com_google_android_gms_analytics_internal_zzc);
        zzaa.zzy(map);
        this.fk = j;
        this.fm = z;
        this.fj = j2;
        this.fl = i;
        this.fi = list != null ? list : Collections.emptyList();
        this.fn = zzs(list);
        Map hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            String zza;
            if (zzl(entry.getKey())) {
                zza = zza(com_google_android_gms_analytics_internal_zzc, entry.getKey());
                if (zza != null) {
                    hashMap.put(zza, zzb(com_google_android_gms_analytics_internal_zzc, entry.getValue()));
                }
            }
        }
        for (Entry entry2 : map.entrySet()) {
            if (!zzl(entry2.getKey())) {
                zza = zza(com_google_android_gms_analytics_internal_zzc, entry2.getKey());
                if (zza != null) {
                    hashMap.put(zza, zzb(com_google_android_gms_analytics_internal_zzc, entry2.getValue()));
                }
            }
        }
        if (!TextUtils.isEmpty(this.fn)) {
            zzao.zzc(hashMap, "_v", this.fn);
            if (this.fn.equals("ma4.0.0") || this.fn.equals("ma4.0.1")) {
                hashMap.remove("adid");
            }
        }
        this.zzbly = Collections.unmodifiableMap(hashMap);
    }

    public static zzab zza(zzc com_google_android_gms_analytics_internal_zzc, zzab com_google_android_gms_analytics_internal_zzab, Map<String, String> map) {
        return new zzab(com_google_android_gms_analytics_internal_zzc, map, com_google_android_gms_analytics_internal_zzab.zzaga(), com_google_android_gms_analytics_internal_zzab.zzagc(), com_google_android_gms_analytics_internal_zzab.zzafz(), com_google_android_gms_analytics_internal_zzab.zzafy(), com_google_android_gms_analytics_internal_zzab.zzagb());
    }

    private static String zza(zzc com_google_android_gms_analytics_internal_zzc, Object obj) {
        if (obj == null) {
            return null;
        }
        Object obj2 = obj.toString();
        if (obj2.startsWith("&")) {
            obj2 = obj2.substring(1);
        }
        int length = obj2.length();
        if (length > AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY) {
            obj2 = obj2.substring(0, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY);
            com_google_android_gms_analytics_internal_zzc.zzc("Hit param name is too long and will be trimmed", Integer.valueOf(length), obj2);
        }
        return TextUtils.isEmpty(obj2) ? null : obj2;
    }

    private static String zzb(zzc com_google_android_gms_analytics_internal_zzc, Object obj) {
        String obj2 = obj == null ? BuildConfig.FLAVOR : obj.toString();
        int length = obj2.length();
        if (length <= Utility.DEFAULT_STREAM_BUFFER_SIZE) {
            return obj2;
        }
        obj2 = obj2.substring(0, Utility.DEFAULT_STREAM_BUFFER_SIZE);
        com_google_android_gms_analytics_internal_zzc.zzc("Hit param value is too long and will be trimmed", Integer.valueOf(length), obj2);
        return obj2;
    }

    private static boolean zzl(Object obj) {
        return obj == null ? false : obj.toString().startsWith("&");
    }

    private String zzr(String str, String str2) {
        zzaa.zzib(str);
        zzaa.zzb(!str.startsWith("&"), (Object) "Short param name required");
        String str3 = (String) this.zzbly.get(str);
        return str3 != null ? str3 : str2;
    }

    private static String zzs(List<Command> list) {
        CharSequence value;
        if (list != null) {
            for (Command command : list) {
                if ("appendVersion".equals(command.getId())) {
                    value = command.getValue();
                    break;
                }
            }
        }
        value = null;
        return TextUtils.isEmpty(value) ? null : value;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("ht=").append(this.fk);
        if (this.fj != 0) {
            stringBuffer.append(", dbId=").append(this.fj);
        }
        if (this.fl != 0) {
            stringBuffer.append(", appUID=").append(this.fl);
        }
        List<String> arrayList = new ArrayList(this.zzbly.keySet());
        Collections.sort(arrayList);
        for (String str : arrayList) {
            stringBuffer.append(", ");
            stringBuffer.append(str);
            stringBuffer.append("=");
            stringBuffer.append((String) this.zzbly.get(str));
        }
        return stringBuffer.toString();
    }

    public int zzafy() {
        return this.fl;
    }

    public long zzafz() {
        return this.fj;
    }

    public long zzaga() {
        return this.fk;
    }

    public List<Command> zzagb() {
        return this.fi;
    }

    public boolean zzagc() {
        return this.fm;
    }

    public long zzagd() {
        return zzao.zzfj(zzr("_s", AppEventsConstants.EVENT_PARAM_VALUE_NO));
    }

    public String zzage() {
        return zzr("_m", BuildConfig.FLAVOR);
    }

    public Map<String, String> zzmc() {
        return this.zzbly;
    }
}
