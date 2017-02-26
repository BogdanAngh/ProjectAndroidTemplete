package com.google.android.gms.tagmanager;

import com.facebook.internal.NativeProtocol;
import com.google.android.gms.internal.zzag;
import com.google.android.gms.internal.zzah;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

class zzbg extends zzam {
    private static final String ID;
    private static final String aFC;
    private static final String aFD;
    private static final String aFE;
    private static final String aFk;

    /* renamed from: com.google.android.gms.tagmanager.zzbg.1 */
    static /* synthetic */ class C15321 {
        static final /* synthetic */ int[] aFF;

        static {
            aFF = new int[zza.values().length];
            try {
                aFF[zza.URL.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                aFF[zza.BACKSLASH.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                aFF[zza.NONE.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
        }
    }

    private enum zza {
        NONE,
        URL,
        BACKSLASH
    }

    static {
        ID = zzag.JOINER.toString();
        aFk = zzah.ARG0.toString();
        aFC = zzah.ITEM_SEPARATOR.toString();
        aFD = zzah.KEY_VALUE_SEPARATOR.toString();
        aFE = zzah.ESCAPE.toString();
    }

    public zzbg() {
        super(ID, aFk);
    }

    private String zza(String str, zza com_google_android_gms_tagmanager_zzbg_zza, Set<Character> set) {
        switch (C15321.aFF[com_google_android_gms_tagmanager_zzbg_zza.ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                try {
                    return zzdq.zzqe(str);
                } catch (Throwable e) {
                    zzbo.zzb("Joiner: unsupported encoding", e);
                    return str;
                }
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                String replace = str.replace("\\", "\\\\");
                String str2 = replace;
                for (Character ch : set) {
                    CharSequence ch2 = ch.toString();
                    String str3 = "\\";
                    replace = String.valueOf(ch2);
                    str2 = str2.replace(ch2, replace.length() != 0 ? str3.concat(replace) : new String(str3));
                }
                return str2;
            default:
                return str;
        }
    }

    private void zza(StringBuilder stringBuilder, String str, zza com_google_android_gms_tagmanager_zzbg_zza, Set<Character> set) {
        stringBuilder.append(zza(str, com_google_android_gms_tagmanager_zzbg_zza, set));
    }

    private void zza(Set<Character> set, String str) {
        for (int i = 0; i < str.length(); i++) {
            set.add(Character.valueOf(str.charAt(i)));
        }
    }

    public com.google.android.gms.internal.zzaj.zza zzay(Map<String, com.google.android.gms.internal.zzaj.zza> map) {
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) map.get(aFk);
        if (com_google_android_gms_internal_zzaj_zza == null) {
            return zzdm.zzchm();
        }
        zza com_google_android_gms_tagmanager_zzbg_zza;
        Set set;
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza2 = (com.google.android.gms.internal.zzaj.zza) map.get(aFC);
        String zzg = com_google_android_gms_internal_zzaj_zza2 != null ? zzdm.zzg(com_google_android_gms_internal_zzaj_zza2) : BuildConfig.FLAVOR;
        com_google_android_gms_internal_zzaj_zza2 = (com.google.android.gms.internal.zzaj.zza) map.get(aFD);
        String zzg2 = com_google_android_gms_internal_zzaj_zza2 != null ? zzdm.zzg(com_google_android_gms_internal_zzaj_zza2) : "=";
        zza com_google_android_gms_tagmanager_zzbg_zza2 = zza.NONE;
        com_google_android_gms_internal_zzaj_zza2 = (com.google.android.gms.internal.zzaj.zza) map.get(aFE);
        if (com_google_android_gms_internal_zzaj_zza2 != null) {
            String zzg3 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza2);
            if (NativeProtocol.WEB_DIALOG_URL.equals(zzg3)) {
                com_google_android_gms_tagmanager_zzbg_zza = zza.URL;
                set = null;
            } else if ("backslash".equals(zzg3)) {
                com_google_android_gms_tagmanager_zzbg_zza = zza.BACKSLASH;
                set = new HashSet();
                zza(set, zzg);
                zza(set, zzg2);
                set.remove(Character.valueOf('\\'));
            } else {
                zzg = "Joiner: unsupported escape type: ";
                String valueOf = String.valueOf(zzg3);
                zzbo.m1698e(valueOf.length() != 0 ? zzg.concat(valueOf) : new String(zzg));
                return zzdm.zzchm();
            }
        }
        set = null;
        com_google_android_gms_tagmanager_zzbg_zza = com_google_android_gms_tagmanager_zzbg_zza2;
        StringBuilder stringBuilder = new StringBuilder();
        switch (com_google_android_gms_internal_zzaj_zza.type) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                Object obj = 1;
                com.google.android.gms.internal.zzaj.zza[] com_google_android_gms_internal_zzaj_zzaArr = com_google_android_gms_internal_zzaj_zza.zzxy;
                int length = com_google_android_gms_internal_zzaj_zzaArr.length;
                int i = 0;
                while (i < length) {
                    com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza3 = com_google_android_gms_internal_zzaj_zzaArr[i];
                    if (obj == null) {
                        stringBuilder.append(zzg);
                    }
                    zza(stringBuilder, zzdm.zzg(com_google_android_gms_internal_zzaj_zza3), com_google_android_gms_tagmanager_zzbg_zza, set);
                    i++;
                    obj = null;
                }
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                for (int i2 = 0; i2 < com_google_android_gms_internal_zzaj_zza.zzxz.length; i2++) {
                    if (i2 > 0) {
                        stringBuilder.append(zzg);
                    }
                    String zzg4 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza.zzxz[i2]);
                    String zzg5 = zzdm.zzg(com_google_android_gms_internal_zzaj_zza.zzya[i2]);
                    zza(stringBuilder, zzg4, com_google_android_gms_tagmanager_zzbg_zza, set);
                    stringBuilder.append(zzg2);
                    zza(stringBuilder, zzg5, com_google_android_gms_tagmanager_zzbg_zza, set);
                }
                break;
            default:
                zza(stringBuilder, zzdm.zzg(com_google_android_gms_internal_zzaj_zza), com_google_android_gms_tagmanager_zzbg_zza, set);
                break;
        }
        return zzdm.zzat(stringBuilder.toString());
    }

    public boolean zzcdu() {
        return true;
    }
}
