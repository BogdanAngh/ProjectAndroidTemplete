package com.google.android.gms.tagmanager;

import com.facebook.internal.ServerProtocol;
import com.google.android.gms.internal.zzaj.zza;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class zzdm {
    private static final Object aHR;
    private static Long aHS;
    private static Double aHT;
    private static zzdl aHU;
    private static String aHV;
    private static Boolean aHW;
    private static List<Object> aHX;
    private static Map<Object, Object> aHY;
    private static zza aHZ;

    static {
        aHR = null;
        aHS = new Long(0);
        aHT = new Double(0.0d);
        aHU = zzdl.zzbv(0);
        aHV = new String(BuildConfig.FLAVOR);
        aHW = new Boolean(false);
        aHX = new ArrayList(0);
        aHY = new HashMap();
        aHZ = zzat(aHV);
    }

    private static double getDouble(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).doubleValue();
        }
        zzbo.m1698e("getDouble received non-Number");
        return 0.0d;
    }

    public static String zzao(Object obj) {
        return obj == null ? aHV : obj.toString();
    }

    public static zzdl zzap(Object obj) {
        return obj instanceof zzdl ? (zzdl) obj : zzav(obj) ? zzdl.zzbv(zzaw(obj)) : zzau(obj) ? zzdl.zza(Double.valueOf(getDouble(obj))) : zzpy(zzao(obj));
    }

    public static Long zzaq(Object obj) {
        return zzav(obj) ? Long.valueOf(zzaw(obj)) : zzpz(zzao(obj));
    }

    public static Double zzar(Object obj) {
        return zzau(obj) ? Double.valueOf(getDouble(obj)) : zzqa(zzao(obj));
    }

    public static Boolean zzas(Object obj) {
        return obj instanceof Boolean ? (Boolean) obj : zzqb(zzao(obj));
    }

    public static zza zzat(Object obj) {
        boolean z = false;
        zza com_google_android_gms_internal_zzaj_zza = new zza();
        if (obj instanceof zza) {
            return (zza) obj;
        }
        if (obj instanceof String) {
            com_google_android_gms_internal_zzaj_zza.type = 1;
            com_google_android_gms_internal_zzaj_zza.string = (String) obj;
        } else if (obj instanceof List) {
            com_google_android_gms_internal_zzaj_zza.type = 2;
            List<Object> list = (List) obj;
            r5 = new ArrayList(list.size());
            r1 = false;
            for (Object zzat : list) {
                zza zzat2 = zzat(zzat);
                if (zzat2 == aHZ) {
                    return aHZ;
                }
                r0 = r1 || zzat2.zzyh;
                r5.add(zzat2);
                r1 = r0;
            }
            com_google_android_gms_internal_zzaj_zza.zzxy = (zza[]) r5.toArray(new zza[0]);
            z = r1;
        } else if (obj instanceof Map) {
            com_google_android_gms_internal_zzaj_zza.type = 3;
            Set<Entry> entrySet = ((Map) obj).entrySet();
            r5 = new ArrayList(entrySet.size());
            List arrayList = new ArrayList(entrySet.size());
            r1 = false;
            for (Entry entry : entrySet) {
                zza zzat3 = zzat(entry.getKey());
                zza zzat4 = zzat(entry.getValue());
                if (zzat3 == aHZ || zzat4 == aHZ) {
                    return aHZ;
                }
                r0 = r1 || zzat3.zzyh || zzat4.zzyh;
                r5.add(zzat3);
                arrayList.add(zzat4);
                r1 = r0;
            }
            com_google_android_gms_internal_zzaj_zza.zzxz = (zza[]) r5.toArray(new zza[0]);
            com_google_android_gms_internal_zzaj_zza.zzya = (zza[]) arrayList.toArray(new zza[0]);
            z = r1;
        } else if (zzau(obj)) {
            com_google_android_gms_internal_zzaj_zza.type = 1;
            com_google_android_gms_internal_zzaj_zza.string = obj.toString();
        } else if (zzav(obj)) {
            com_google_android_gms_internal_zzaj_zza.type = 6;
            com_google_android_gms_internal_zzaj_zza.zzyd = zzaw(obj);
        } else if (obj instanceof Boolean) {
            com_google_android_gms_internal_zzaj_zza.type = 8;
            com_google_android_gms_internal_zzaj_zza.zzye = ((Boolean) obj).booleanValue();
        } else {
            String str = "Converting to Value from unknown object type: ";
            String valueOf = String.valueOf(obj == null ? "null" : obj.getClass().toString());
            zzbo.m1698e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            return aHZ;
        }
        com_google_android_gms_internal_zzaj_zza.zzyh = z;
        return com_google_android_gms_internal_zzaj_zza;
    }

    private static boolean zzau(Object obj) {
        return (obj instanceof Double) || (obj instanceof Float) || ((obj instanceof zzdl) && ((zzdl) obj).zzchb());
    }

    private static boolean zzav(Object obj) {
        return (obj instanceof Byte) || (obj instanceof Short) || (obj instanceof Integer) || (obj instanceof Long) || ((obj instanceof zzdl) && ((zzdl) obj).zzchc());
    }

    private static long zzaw(Object obj) {
        if (obj instanceof Number) {
            return ((Number) obj).longValue();
        }
        zzbo.m1698e("getInt64 received non-Number");
        return 0;
    }

    public static Object zzchg() {
        return null;
    }

    public static Long zzchh() {
        return aHS;
    }

    public static Double zzchi() {
        return aHT;
    }

    public static Boolean zzchj() {
        return aHW;
    }

    public static zzdl zzchk() {
        return aHU;
    }

    public static String zzchl() {
        return aHV;
    }

    public static zza zzchm() {
        return aHZ;
    }

    public static String zzg(zza com_google_android_gms_internal_zzaj_zza) {
        return zzao(zzl(com_google_android_gms_internal_zzaj_zza));
    }

    public static zzdl zzh(zza com_google_android_gms_internal_zzaj_zza) {
        return zzap(zzl(com_google_android_gms_internal_zzaj_zza));
    }

    public static Long zzi(zza com_google_android_gms_internal_zzaj_zza) {
        return zzaq(zzl(com_google_android_gms_internal_zzaj_zza));
    }

    public static Double zzj(zza com_google_android_gms_internal_zzaj_zza) {
        return zzar(zzl(com_google_android_gms_internal_zzaj_zza));
    }

    public static Boolean zzk(zza com_google_android_gms_internal_zzaj_zza) {
        return zzas(zzl(com_google_android_gms_internal_zzaj_zza));
    }

    public static Object zzl(zza com_google_android_gms_internal_zzaj_zza) {
        int i = 0;
        if (com_google_android_gms_internal_zzaj_zza == null) {
            return null;
        }
        zza[] com_google_android_gms_internal_zzaj_zzaArr;
        int length;
        switch (com_google_android_gms_internal_zzaj_zza.type) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return com_google_android_gms_internal_zzaj_zza.string;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                ArrayList arrayList = new ArrayList(com_google_android_gms_internal_zzaj_zza.zzxy.length);
                com_google_android_gms_internal_zzaj_zzaArr = com_google_android_gms_internal_zzaj_zza.zzxy;
                length = com_google_android_gms_internal_zzaj_zzaArr.length;
                while (i < length) {
                    Object zzl = zzl(com_google_android_gms_internal_zzaj_zzaArr[i]);
                    if (zzl == null) {
                        return null;
                    }
                    arrayList.add(zzl);
                    i++;
                }
                return arrayList;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                if (com_google_android_gms_internal_zzaj_zza.zzxz.length != com_google_android_gms_internal_zzaj_zza.zzya.length) {
                    String str = "Converting an invalid value to object: ";
                    String valueOf = String.valueOf(com_google_android_gms_internal_zzaj_zza.toString());
                    zzbo.m1698e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    return null;
                }
                Map hashMap = new HashMap(com_google_android_gms_internal_zzaj_zza.zzya.length);
                while (i < com_google_android_gms_internal_zzaj_zza.zzxz.length) {
                    Object zzl2 = zzl(com_google_android_gms_internal_zzaj_zza.zzxz[i]);
                    Object zzl3 = zzl(com_google_android_gms_internal_zzaj_zza.zzya[i]);
                    if (zzl2 == null || zzl3 == null) {
                        return null;
                    }
                    hashMap.put(zzl2, zzl3);
                    i++;
                }
                return hashMap;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                zzbo.m1698e("Trying to convert a macro reference to object");
                return null;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                zzbo.m1698e("Trying to convert a function id to object");
                return null;
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                return Long.valueOf(com_google_android_gms_internal_zzaj_zza.zzyd);
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                StringBuffer stringBuffer = new StringBuffer();
                com_google_android_gms_internal_zzaj_zzaArr = com_google_android_gms_internal_zzaj_zza.zzyf;
                length = com_google_android_gms_internal_zzaj_zzaArr.length;
                while (i < length) {
                    String zzg = zzg(com_google_android_gms_internal_zzaj_zzaArr[i]);
                    if (zzg == aHV) {
                        return null;
                    }
                    stringBuffer.append(zzg);
                    i++;
                }
                return stringBuffer.toString();
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                return Boolean.valueOf(com_google_android_gms_internal_zzaj_zza.zzye);
            default:
                zzbo.m1698e("Failed to convert a value of type: " + com_google_android_gms_internal_zzaj_zza.type);
                return null;
        }
    }

    public static zza zzpx(String str) {
        zza com_google_android_gms_internal_zzaj_zza = new zza();
        com_google_android_gms_internal_zzaj_zza.type = 5;
        com_google_android_gms_internal_zzaj_zza.zzyc = str;
        return com_google_android_gms_internal_zzaj_zza;
    }

    private static zzdl zzpy(String str) {
        try {
            return zzdl.zzpw(str);
        } catch (NumberFormatException e) {
            zzbo.m1698e(new StringBuilder(String.valueOf(str).length() + 33).append("Failed to convert '").append(str).append("' to a number.").toString());
            return aHU;
        }
    }

    private static Long zzpz(String str) {
        zzdl zzpy = zzpy(str);
        return zzpy == aHU ? aHS : Long.valueOf(zzpy.longValue());
    }

    private static Double zzqa(String str) {
        zzdl zzpy = zzpy(str);
        return zzpy == aHU ? aHT : Double.valueOf(zzpy.doubleValue());
    }

    private static Boolean zzqb(String str) {
        return ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase(str) ? Boolean.TRUE : "false".equalsIgnoreCase(str) ? Boolean.FALSE : aHW;
    }
}
