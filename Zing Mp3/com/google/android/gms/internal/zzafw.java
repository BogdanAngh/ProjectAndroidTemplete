package com.google.android.gms.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.gms.internal.zzai.zzh;
import com.google.android.gms.tagmanager.zzbo;
import com.google.android.gms.tagmanager.zzdm;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class zzafw {

    public static class zza {
        private final com.google.android.gms.internal.zzaj.zza aHd;
        private final Map<String, com.google.android.gms.internal.zzaj.zza> aLM;

        private zza(Map<String, com.google.android.gms.internal.zzaj.zza> map, com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
            this.aLM = map;
            this.aHd = com_google_android_gms_internal_zzaj_zza;
        }

        public static zzb zzcku() {
            return new zzb();
        }

        public String toString() {
            String valueOf = String.valueOf(zzcjt());
            String valueOf2 = String.valueOf(this.aHd);
            return new StringBuilder((String.valueOf(valueOf).length() + 32) + String.valueOf(valueOf2).length()).append("Properties: ").append(valueOf).append(" pushAfterEvaluate: ").append(valueOf2).toString();
        }

        public void zza(String str, com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
            this.aLM.put(str, com_google_android_gms_internal_zzaj_zza);
        }

        public com.google.android.gms.internal.zzaj.zza zzcgm() {
            return this.aHd;
        }

        public Map<String, com.google.android.gms.internal.zzaj.zza> zzcjt() {
            return Collections.unmodifiableMap(this.aLM);
        }
    }

    public static class zzb {
        private com.google.android.gms.internal.zzaj.zza aHd;
        private final Map<String, com.google.android.gms.internal.zzaj.zza> aLM;

        private zzb() {
            this.aLM = new HashMap();
        }

        public zzb zzb(String str, com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
            this.aLM.put(str, com_google_android_gms_internal_zzaj_zza);
            return this;
        }

        public zza zzckv() {
            return new zza(this.aHd, null);
        }

        public zzb zzq(com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
            this.aHd = com_google_android_gms_internal_zzaj_zza;
            return this;
        }
    }

    public static class zzc {
        private final List<zze> aLJ;
        private final Map<String, List<zza>> aLK;
        private final int aLL;
        private final String um;

        private zzc(List<zze> list, Map<String, List<zza>> map, String str, int i) {
            this.aLJ = Collections.unmodifiableList(list);
            this.aLK = Collections.unmodifiableMap(map);
            this.um = str;
            this.aLL = i;
        }

        public static zzd zzckw() {
            return new zzd();
        }

        public String getVersion() {
            return this.um;
        }

        public String toString() {
            String valueOf = String.valueOf(zzcjr());
            String valueOf2 = String.valueOf(this.aLK);
            return new StringBuilder((String.valueOf(valueOf).length() + 17) + String.valueOf(valueOf2).length()).append("Rules: ").append(valueOf).append("  Macros: ").append(valueOf2).toString();
        }

        public List<zze> zzcjr() {
            return this.aLJ;
        }

        public Map<String, List<zza>> zzckx() {
            return this.aLK;
        }
    }

    public static class zzd {
        private final List<zze> aLJ;
        private final Map<String, List<zza>> aLK;
        private int aLL;
        private String um;

        private zzd() {
            this.aLJ = new ArrayList();
            this.aLK = new HashMap();
            this.um = BuildConfig.FLAVOR;
            this.aLL = 0;
        }

        public zzd zzaao(int i) {
            this.aLL = i;
            return this;
        }

        public zzd zzb(zze com_google_android_gms_internal_zzafw_zze) {
            this.aLJ.add(com_google_android_gms_internal_zzafw_zze);
            return this;
        }

        public zzd zzc(zza com_google_android_gms_internal_zzafw_zza) {
            String zzg = zzdm.zzg((com.google.android.gms.internal.zzaj.zza) com_google_android_gms_internal_zzafw_zza.zzcjt().get(zzah.INSTANCE_NAME.toString()));
            List list = (List) this.aLK.get(zzg);
            if (list == null) {
                list = new ArrayList();
                this.aLK.put(zzg, list);
            }
            list.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzc zzcky() {
            return new zzc(this.aLK, this.um, this.aLL, null);
        }

        public zzd zzri(String str) {
            this.um = str;
            return this;
        }
    }

    public static class zze {
        private final List<zza> aLO;
        private final List<zza> aLP;
        private final List<zza> aLQ;
        private final List<zza> aLR;
        private final List<String> aMA;
        private final List<String> aMB;
        private final List<String> aMC;
        private final List<zza> aMx;
        private final List<zza> aMy;
        private final List<String> aMz;

        private zze(List<zza> list, List<zza> list2, List<zza> list3, List<zza> list4, List<zza> list5, List<zza> list6, List<String> list7, List<String> list8, List<String> list9, List<String> list10) {
            this.aLO = Collections.unmodifiableList(list);
            this.aLP = Collections.unmodifiableList(list2);
            this.aLQ = Collections.unmodifiableList(list3);
            this.aLR = Collections.unmodifiableList(list4);
            this.aMx = Collections.unmodifiableList(list5);
            this.aMy = Collections.unmodifiableList(list6);
            this.aMz = Collections.unmodifiableList(list7);
            this.aMA = Collections.unmodifiableList(list8);
            this.aMB = Collections.unmodifiableList(list9);
            this.aMC = Collections.unmodifiableList(list10);
        }

        public static zzf zzckz() {
            return new zzf();
        }

        public String toString() {
            String valueOf = String.valueOf(zzcjv());
            String valueOf2 = String.valueOf(zzcjw());
            String valueOf3 = String.valueOf(zzcjx());
            String valueOf4 = String.valueOf(zzcjy());
            String valueOf5 = String.valueOf(zzcla());
            String valueOf6 = String.valueOf(zzcld());
            return new StringBuilder((((((String.valueOf(valueOf).length() + C1569R.styleable.AppCompatTheme_buttonStyle) + String.valueOf(valueOf2).length()) + String.valueOf(valueOf3).length()) + String.valueOf(valueOf4).length()) + String.valueOf(valueOf5).length()) + String.valueOf(valueOf6).length()).append("Positive predicates: ").append(valueOf).append("  Negative predicates: ").append(valueOf2).append("  Add tags: ").append(valueOf3).append("  Remove tags: ").append(valueOf4).append("  Add macros: ").append(valueOf5).append("  Remove macros: ").append(valueOf6).toString();
        }

        public List<zza> zzcjv() {
            return this.aLO;
        }

        public List<zza> zzcjw() {
            return this.aLP;
        }

        public List<zza> zzcjx() {
            return this.aLQ;
        }

        public List<zza> zzcjy() {
            return this.aLR;
        }

        public List<zza> zzcla() {
            return this.aMx;
        }

        public List<String> zzclb() {
            return this.aMB;
        }

        public List<String> zzclc() {
            return this.aMC;
        }

        public List<zza> zzcld() {
            return this.aMy;
        }
    }

    public static class zzf {
        private final List<zza> aLO;
        private final List<zza> aLP;
        private final List<zza> aLQ;
        private final List<zza> aLR;
        private final List<String> aMA;
        private final List<String> aMB;
        private final List<String> aMC;
        private final List<zza> aMx;
        private final List<zza> aMy;
        private final List<String> aMz;

        private zzf() {
            this.aLO = new ArrayList();
            this.aLP = new ArrayList();
            this.aLQ = new ArrayList();
            this.aLR = new ArrayList();
            this.aMx = new ArrayList();
            this.aMy = new ArrayList();
            this.aMz = new ArrayList();
            this.aMA = new ArrayList();
            this.aMB = new ArrayList();
            this.aMC = new ArrayList();
        }

        public zze zzcle() {
            return new zze(this.aLP, this.aLQ, this.aLR, this.aMx, this.aMy, this.aMz, this.aMA, this.aMB, this.aMC, null);
        }

        public zzf zzd(zza com_google_android_gms_internal_zzafw_zza) {
            this.aLO.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zze(zza com_google_android_gms_internal_zzafw_zza) {
            this.aLP.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zzf(zza com_google_android_gms_internal_zzafw_zza) {
            this.aLQ.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zzg(zza com_google_android_gms_internal_zzafw_zza) {
            this.aLR.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zzh(zza com_google_android_gms_internal_zzafw_zza) {
            this.aMx.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zzi(zza com_google_android_gms_internal_zzafw_zza) {
            this.aMy.add(com_google_android_gms_internal_zzafw_zza);
            return this;
        }

        public zzf zzrj(String str) {
            this.aMB.add(str);
            return this;
        }

        public zzf zzrk(String str) {
            this.aMC.add(str);
            return this;
        }

        public zzf zzrl(String str) {
            this.aMz.add(str);
            return this;
        }

        public zzf zzrm(String str) {
            this.aMA.add(str);
            return this;
        }
    }

    public static class zzg extends Exception {
        public zzg(String str) {
            super(str);
        }
    }

    private static zza zza(com.google.android.gms.internal.zzai.zzb com_google_android_gms_internal_zzai_zzb, com.google.android.gms.internal.zzai.zzf com_google_android_gms_internal_zzai_zzf, com.google.android.gms.internal.zzaj.zza[] com_google_android_gms_internal_zzaj_zzaArr, int i) throws zzg {
        zzb zzcku = zza.zzcku();
        for (int valueOf : com_google_android_gms_internal_zzai_zzb.zzvu) {
            com.google.android.gms.internal.zzai.zze com_google_android_gms_internal_zzai_zze = (com.google.android.gms.internal.zzai.zze) zza(com_google_android_gms_internal_zzai_zzf.zzwk, Integer.valueOf(valueOf).intValue(), "properties");
            String str = (String) zza(com_google_android_gms_internal_zzai_zzf.zzwi, com_google_android_gms_internal_zzai_zze.key, "keys");
            com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) zza(com_google_android_gms_internal_zzaj_zzaArr, com_google_android_gms_internal_zzai_zze.value, "values");
            if (zzah.PUSH_AFTER_EVALUATE.toString().equals(str)) {
                zzcku.zzq(com_google_android_gms_internal_zzaj_zza);
            } else {
                zzcku.zzb(str, com_google_android_gms_internal_zzaj_zza);
            }
        }
        return zzcku.zzckv();
    }

    private static zze zza(com.google.android.gms.internal.zzai.zzg com_google_android_gms_internal_zzai_zzg, List<zza> list, List<zza> list2, List<zza> list3, com.google.android.gms.internal.zzai.zzf com_google_android_gms_internal_zzai_zzf) {
        zzf zzckz = zze.zzckz();
        for (int valueOf : com_google_android_gms_internal_zzai_zzg.zzwy) {
            zzckz.zzd((zza) list3.get(Integer.valueOf(valueOf).intValue()));
        }
        for (int valueOf2 : com_google_android_gms_internal_zzai_zzg.zzwz) {
            zzckz.zze((zza) list3.get(Integer.valueOf(valueOf2).intValue()));
        }
        for (int valueOf22 : com_google_android_gms_internal_zzai_zzg.zzxa) {
            zzckz.zzf((zza) list.get(Integer.valueOf(valueOf22).intValue()));
        }
        for (int valueOf3 : com_google_android_gms_internal_zzai_zzg.zzxc) {
            zzckz.zzrj(com_google_android_gms_internal_zzai_zzf.zzwj[Integer.valueOf(valueOf3).intValue()].string);
        }
        for (int valueOf222 : com_google_android_gms_internal_zzai_zzg.zzxb) {
            zzckz.zzg((zza) list.get(Integer.valueOf(valueOf222).intValue()));
        }
        for (int valueOf32 : com_google_android_gms_internal_zzai_zzg.zzxd) {
            zzckz.zzrk(com_google_android_gms_internal_zzai_zzf.zzwj[Integer.valueOf(valueOf32).intValue()].string);
        }
        for (int valueOf2222 : com_google_android_gms_internal_zzai_zzg.zzxe) {
            zzckz.zzh((zza) list2.get(Integer.valueOf(valueOf2222).intValue()));
        }
        for (int valueOf322 : com_google_android_gms_internal_zzai_zzg.zzxg) {
            zzckz.zzrl(com_google_android_gms_internal_zzai_zzf.zzwj[Integer.valueOf(valueOf322).intValue()].string);
        }
        for (int valueOf22222 : com_google_android_gms_internal_zzai_zzg.zzxf) {
            zzckz.zzi((zza) list2.get(Integer.valueOf(valueOf22222).intValue()));
        }
        for (int valueOf4 : com_google_android_gms_internal_zzai_zzg.zzxh) {
            zzckz.zzrm(com_google_android_gms_internal_zzai_zzf.zzwj[Integer.valueOf(valueOf4).intValue()].string);
        }
        return zzckz.zzcle();
    }

    private static com.google.android.gms.internal.zzaj.zza zza(int i, com.google.android.gms.internal.zzai.zzf com_google_android_gms_internal_zzai_zzf, com.google.android.gms.internal.zzaj.zza[] com_google_android_gms_internal_zzaj_zzaArr, Set<Integer> set) throws zzg {
        int i2 = 0;
        if (set.contains(Integer.valueOf(i))) {
            String valueOf = String.valueOf(set);
            zzqt(new StringBuilder(String.valueOf(valueOf).length() + 90).append("Value cycle detected.  Current value reference: ").append(i).append(".  Previous value references: ").append(valueOf).append(".").toString());
        }
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) zza(com_google_android_gms_internal_zzai_zzf.zzwj, i, "values");
        if (com_google_android_gms_internal_zzaj_zzaArr[i] != null) {
            return com_google_android_gms_internal_zzaj_zzaArr[i];
        }
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza2 = null;
        set.add(Integer.valueOf(i));
        zzh zzp;
        int[] iArr;
        int length;
        int i3;
        int i4;
        switch (com_google_android_gms_internal_zzaj_zza.type) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                com_google_android_gms_internal_zzaj_zza2 = com_google_android_gms_internal_zzaj_zza;
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                zzp = zzp(com_google_android_gms_internal_zzaj_zza);
                com_google_android_gms_internal_zzaj_zza2 = zzo(com_google_android_gms_internal_zzaj_zza);
                com_google_android_gms_internal_zzaj_zza2.zzxy = new com.google.android.gms.internal.zzaj.zza[zzp.zzxk.length];
                iArr = zzp.zzxk;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    com_google_android_gms_internal_zzaj_zza2.zzxy[i3] = zza(iArr[i2], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                com_google_android_gms_internal_zzaj_zza2 = zzo(com_google_android_gms_internal_zzaj_zza);
                zzh zzp2 = zzp(com_google_android_gms_internal_zzaj_zza);
                if (zzp2.zzxl.length != zzp2.zzxm.length) {
                    i3 = zzp2.zzxl.length;
                    zzqt("Uneven map keys (" + i3 + ") and map values (" + zzp2.zzxm.length + ")");
                }
                com_google_android_gms_internal_zzaj_zza2.zzxz = new com.google.android.gms.internal.zzaj.zza[zzp2.zzxl.length];
                com_google_android_gms_internal_zzaj_zza2.zzya = new com.google.android.gms.internal.zzaj.zza[zzp2.zzxl.length];
                int[] iArr2 = zzp2.zzxl;
                int length2 = iArr2.length;
                i3 = 0;
                i4 = 0;
                while (i3 < length2) {
                    int i5 = i4 + 1;
                    com_google_android_gms_internal_zzaj_zza2.zzxz[i4] = zza(iArr2[i3], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, (Set) set);
                    i3++;
                    i4 = i5;
                }
                iArr = zzp2.zzxm;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    com_google_android_gms_internal_zzaj_zza2.zzya[i3] = zza(iArr[i2], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                com_google_android_gms_internal_zzaj_zza2 = zzo(com_google_android_gms_internal_zzaj_zza);
                com_google_android_gms_internal_zzaj_zza2.zzyb = zzdm.zzg(zza(zzp(com_google_android_gms_internal_zzaj_zza).zzxp, com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, (Set) set));
                break;
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                com_google_android_gms_internal_zzaj_zza2 = zzo(com_google_android_gms_internal_zzaj_zza);
                zzp = zzp(com_google_android_gms_internal_zzaj_zza);
                com_google_android_gms_internal_zzaj_zza2.zzyf = new com.google.android.gms.internal.zzaj.zza[zzp.zzxo.length];
                iArr = zzp.zzxo;
                length = iArr.length;
                i3 = 0;
                while (i2 < length) {
                    i4 = i3 + 1;
                    com_google_android_gms_internal_zzaj_zza2.zzyf[i3] = zza(iArr[i2], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, (Set) set);
                    i2++;
                    i3 = i4;
                }
                break;
        }
        if (com_google_android_gms_internal_zzaj_zza2 == null) {
            valueOf = String.valueOf(com_google_android_gms_internal_zzaj_zza);
            zzqt(new StringBuilder(String.valueOf(valueOf).length() + 15).append("Invalid value: ").append(valueOf).toString());
        }
        com_google_android_gms_internal_zzaj_zzaArr[i] = com_google_android_gms_internal_zzaj_zza2;
        set.remove(Integer.valueOf(i));
        return com_google_android_gms_internal_zzaj_zza2;
    }

    private static <T> T zza(T[] tArr, int i, String str) throws zzg {
        if (i < 0 || i >= tArr.length) {
            zzqt(new StringBuilder(String.valueOf(str).length() + 45).append("Index out of bounds detected: ").append(i).append(" in ").append(str).toString());
        }
        return tArr[i];
    }

    public static zzc zzb(com.google.android.gms.internal.zzai.zzf com_google_android_gms_internal_zzai_zzf) throws zzg {
        int i;
        int i2 = 0;
        com.google.android.gms.internal.zzaj.zza[] com_google_android_gms_internal_zzaj_zzaArr = new com.google.android.gms.internal.zzaj.zza[com_google_android_gms_internal_zzai_zzf.zzwj.length];
        for (i = 0; i < com_google_android_gms_internal_zzai_zzf.zzwj.length; i++) {
            zza(i, com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, new HashSet(0));
        }
        zzd zzckw = zzc.zzckw();
        List arrayList = new ArrayList();
        for (i = 0; i < com_google_android_gms_internal_zzai_zzf.zzwm.length; i++) {
            arrayList.add(zza(com_google_android_gms_internal_zzai_zzf.zzwm[i], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, i));
        }
        List arrayList2 = new ArrayList();
        for (i = 0; i < com_google_android_gms_internal_zzai_zzf.zzwn.length; i++) {
            arrayList2.add(zza(com_google_android_gms_internal_zzai_zzf.zzwn[i], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, i));
        }
        List arrayList3 = new ArrayList();
        for (i = 0; i < com_google_android_gms_internal_zzai_zzf.zzwl.length; i++) {
            zza zza = zza(com_google_android_gms_internal_zzai_zzf.zzwl[i], com_google_android_gms_internal_zzai_zzf, com_google_android_gms_internal_zzaj_zzaArr, i);
            zzckw.zzc(zza);
            arrayList3.add(zza);
        }
        com.google.android.gms.internal.zzai.zzg[] com_google_android_gms_internal_zzai_zzgArr = com_google_android_gms_internal_zzai_zzf.zzwo;
        int length = com_google_android_gms_internal_zzai_zzgArr.length;
        while (i2 < length) {
            zzckw.zzb(zza(com_google_android_gms_internal_zzai_zzgArr[i2], arrayList, arrayList3, arrayList2, com_google_android_gms_internal_zzai_zzf));
            i2++;
        }
        zzckw.zzri(com_google_android_gms_internal_zzai_zzf.version);
        zzckw.zzaao(com_google_android_gms_internal_zzai_zzf.zzww);
        return zzckw.zzcky();
    }

    public static void zzc(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] bArr = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_HTML_ELEMENT];
        while (true) {
            int read = inputStream.read(bArr);
            if (read != -1) {
                outputStream.write(bArr, 0, read);
            } else {
                return;
            }
        }
    }

    public static com.google.android.gms.internal.zzaj.zza zzo(com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza2 = new com.google.android.gms.internal.zzaj.zza();
        com_google_android_gms_internal_zzaj_zza2.type = com_google_android_gms_internal_zzaj_zza.type;
        com_google_android_gms_internal_zzaj_zza2.zzyg = (int[]) com_google_android_gms_internal_zzaj_zza.zzyg.clone();
        if (com_google_android_gms_internal_zzaj_zza.zzyh) {
            com_google_android_gms_internal_zzaj_zza2.zzyh = com_google_android_gms_internal_zzaj_zza.zzyh;
        }
        return com_google_android_gms_internal_zzaj_zza2;
    }

    private static zzh zzp(com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) throws zzg {
        if (((zzh) com_google_android_gms_internal_zzaj_zza.zza(zzh.zzxi)) == null) {
            String valueOf = String.valueOf(com_google_android_gms_internal_zzaj_zza);
            zzqt(new StringBuilder(String.valueOf(valueOf).length() + 54).append("Expected a ServingValue and didn't get one. Value is: ").append(valueOf).toString());
        }
        return (zzh) com_google_android_gms_internal_zzaj_zza.zza(zzh.zzxi);
    }

    private static void zzqt(String str) throws zzg {
        zzbo.m1698e(str);
        throw new zzg(str);
    }
}
