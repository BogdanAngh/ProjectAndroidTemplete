package com.google.android.gms.tagmanager;

import android.content.Context;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.facebook.internal.AnalyticsEvents;
import com.google.android.gms.internal.zzafw;
import com.google.android.gms.internal.zzafw.zze;
import com.google.android.gms.internal.zzah;
import com.google.android.gms.internal.zzai.zzi;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

class zzcx {
    private static final zzce<com.google.android.gms.internal.zzaj.zza> aGL;
    private final DataLayer aDZ;
    private final com.google.android.gms.internal.zzafw.zzc aGM;
    private final zzaj aGN;
    private final Map<String, zzam> aGO;
    private final Map<String, zzam> aGP;
    private final Map<String, zzam> aGQ;
    private final zzl<com.google.android.gms.internal.zzafw.zza, zzce<com.google.android.gms.internal.zzaj.zza>> aGR;
    private final zzl<String, zzb> aGS;
    private final Set<zze> aGT;
    private final Map<String, zzc> aGU;
    private volatile String aGV;
    private int aGW;

    /* renamed from: com.google.android.gms.tagmanager.zzcx.1 */
    class C15381 implements com.google.android.gms.tagmanager.zzm.zza<com.google.android.gms.internal.zzafw.zza, zzce<com.google.android.gms.internal.zzaj.zza>> {
        final /* synthetic */ zzcx aGX;

        C15381(zzcx com_google_android_gms_tagmanager_zzcx) {
            this.aGX = com_google_android_gms_tagmanager_zzcx;
        }

        public /* synthetic */ int sizeOf(Object obj, Object obj2) {
            return zza((com.google.android.gms.internal.zzafw.zza) obj, (zzce) obj2);
        }

        public int zza(com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza, zzce<com.google.android.gms.internal.zzaj.zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza) {
            return ((com.google.android.gms.internal.zzaj.zza) com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza.getObject()).cy();
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcx.2 */
    class C15392 implements com.google.android.gms.tagmanager.zzm.zza<String, zzb> {
        final /* synthetic */ zzcx aGX;

        C15392(zzcx com_google_android_gms_tagmanager_zzcx) {
            this.aGX = com_google_android_gms_tagmanager_zzcx;
        }

        public /* synthetic */ int sizeOf(Object obj, Object obj2) {
            return zza((String) obj, (zzb) obj2);
        }

        public int zza(String str, zzb com_google_android_gms_tagmanager_zzcx_zzb) {
            return str.length() + com_google_android_gms_tagmanager_zzcx_zzb.getSize();
        }
    }

    interface zza {
        void zza(zze com_google_android_gms_internal_zzafw_zze, Set<com.google.android.gms.internal.zzafw.zza> set, Set<com.google.android.gms.internal.zzafw.zza> set2, zzcs com_google_android_gms_tagmanager_zzcs);
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcx.3 */
    class C15403 implements zza {
        final /* synthetic */ zzcx aGX;
        final /* synthetic */ Map aGY;
        final /* synthetic */ Map aGZ;
        final /* synthetic */ Map aHa;
        final /* synthetic */ Map aHb;

        C15403(zzcx com_google_android_gms_tagmanager_zzcx, Map map, Map map2, Map map3, Map map4) {
            this.aGX = com_google_android_gms_tagmanager_zzcx;
            this.aGY = map;
            this.aGZ = map2;
            this.aHa = map3;
            this.aHb = map4;
        }

        public void zza(zze com_google_android_gms_internal_zzafw_zze, Set<com.google.android.gms.internal.zzafw.zza> set, Set<com.google.android.gms.internal.zzafw.zza> set2, zzcs com_google_android_gms_tagmanager_zzcs) {
            List list = (List) this.aGY.get(com_google_android_gms_internal_zzafw_zze);
            List list2 = (List) this.aGZ.get(com_google_android_gms_internal_zzafw_zze);
            if (list != null) {
                set.addAll(list);
                com_google_android_gms_tagmanager_zzcs.zzcfo().zzc(list, list2);
            }
            list = (List) this.aHa.get(com_google_android_gms_internal_zzafw_zze);
            list2 = (List) this.aHb.get(com_google_android_gms_internal_zzafw_zze);
            if (list != null) {
                set2.addAll(list);
                com_google_android_gms_tagmanager_zzcs.zzcfp().zzc(list, list2);
            }
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.zzcx.4 */
    class C15414 implements zza {
        final /* synthetic */ zzcx aGX;

        C15414(zzcx com_google_android_gms_tagmanager_zzcx) {
            this.aGX = com_google_android_gms_tagmanager_zzcx;
        }

        public void zza(zze com_google_android_gms_internal_zzafw_zze, Set<com.google.android.gms.internal.zzafw.zza> set, Set<com.google.android.gms.internal.zzafw.zza> set2, zzcs com_google_android_gms_tagmanager_zzcs) {
            set.addAll(com_google_android_gms_internal_zzafw_zze.zzcjx());
            set2.addAll(com_google_android_gms_internal_zzafw_zze.zzcjy());
            com_google_android_gms_tagmanager_zzcs.zzcfq().zzc(com_google_android_gms_internal_zzafw_zze.zzcjx(), com_google_android_gms_internal_zzafw_zze.zzclb());
            com_google_android_gms_tagmanager_zzcs.zzcfr().zzc(com_google_android_gms_internal_zzafw_zze.zzcjy(), com_google_android_gms_internal_zzafw_zze.zzclc());
        }
    }

    private static class zzb {
        private zzce<com.google.android.gms.internal.zzaj.zza> aHc;
        private com.google.android.gms.internal.zzaj.zza aHd;

        public zzb(zzce<com.google.android.gms.internal.zzaj.zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza, com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza) {
            this.aHc = com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
            this.aHd = com_google_android_gms_internal_zzaj_zza;
        }

        public int getSize() {
            return (this.aHd == null ? 0 : this.aHd.cy()) + ((com.google.android.gms.internal.zzaj.zza) this.aHc.getObject()).cy();
        }

        public zzce<com.google.android.gms.internal.zzaj.zza> zzcgl() {
            return this.aHc;
        }

        public com.google.android.gms.internal.zzaj.zza zzcgm() {
            return this.aHd;
        }
    }

    private static class zzc {
        private final Set<zze> aGT;
        private final Map<zze, List<com.google.android.gms.internal.zzafw.zza>> aHe;
        private final Map<zze, List<com.google.android.gms.internal.zzafw.zza>> aHf;
        private final Map<zze, List<String>> aHg;
        private final Map<zze, List<String>> aHh;
        private com.google.android.gms.internal.zzafw.zza aHi;

        public zzc() {
            this.aGT = new HashSet();
            this.aHe = new HashMap();
            this.aHg = new HashMap();
            this.aHf = new HashMap();
            this.aHh = new HashMap();
        }

        public void zza(zze com_google_android_gms_internal_zzafw_zze) {
            this.aGT.add(com_google_android_gms_internal_zzafw_zze);
        }

        public void zza(zze com_google_android_gms_internal_zzafw_zze, com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza) {
            List list = (List) this.aHe.get(com_google_android_gms_internal_zzafw_zze);
            if (list == null) {
                list = new ArrayList();
                this.aHe.put(com_google_android_gms_internal_zzafw_zze, list);
            }
            list.add(com_google_android_gms_internal_zzafw_zza);
        }

        public void zza(zze com_google_android_gms_internal_zzafw_zze, String str) {
            List list = (List) this.aHg.get(com_google_android_gms_internal_zzafw_zze);
            if (list == null) {
                list = new ArrayList();
                this.aHg.put(com_google_android_gms_internal_zzafw_zze, list);
            }
            list.add(str);
        }

        public void zzb(com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza) {
            this.aHi = com_google_android_gms_internal_zzafw_zza;
        }

        public void zzb(zze com_google_android_gms_internal_zzafw_zze, com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza) {
            List list = (List) this.aHf.get(com_google_android_gms_internal_zzafw_zze);
            if (list == null) {
                list = new ArrayList();
                this.aHf.put(com_google_android_gms_internal_zzafw_zze, list);
            }
            list.add(com_google_android_gms_internal_zzafw_zza);
        }

        public void zzb(zze com_google_android_gms_internal_zzafw_zze, String str) {
            List list = (List) this.aHh.get(com_google_android_gms_internal_zzafw_zze);
            if (list == null) {
                list = new ArrayList();
                this.aHh.put(com_google_android_gms_internal_zzafw_zze, list);
            }
            list.add(str);
        }

        public Set<zze> zzcgn() {
            return this.aGT;
        }

        public Map<zze, List<com.google.android.gms.internal.zzafw.zza>> zzcgo() {
            return this.aHe;
        }

        public Map<zze, List<String>> zzcgp() {
            return this.aHg;
        }

        public Map<zze, List<String>> zzcgq() {
            return this.aHh;
        }

        public Map<zze, List<com.google.android.gms.internal.zzafw.zza>> zzcgr() {
            return this.aHf;
        }

        public com.google.android.gms.internal.zzafw.zza zzcgs() {
            return this.aHi;
        }
    }

    static {
        aGL = new zzce(zzdm.zzchm(), true);
    }

    public zzcx(Context context, com.google.android.gms.internal.zzafw.zzc com_google_android_gms_internal_zzafw_zzc, DataLayer dataLayer, com.google.android.gms.tagmanager.zzu.zza com_google_android_gms_tagmanager_zzu_zza, com.google.android.gms.tagmanager.zzu.zza com_google_android_gms_tagmanager_zzu_zza2, zzaj com_google_android_gms_tagmanager_zzaj) {
        if (com_google_android_gms_internal_zzafw_zzc == null) {
            throw new NullPointerException("resource cannot be null");
        }
        this.aGM = com_google_android_gms_internal_zzafw_zzc;
        this.aGT = new HashSet(com_google_android_gms_internal_zzafw_zzc.zzcjr());
        this.aDZ = dataLayer;
        this.aGN = com_google_android_gms_tagmanager_zzaj;
        this.aGR = new zzm().zza(AccessibilityNodeInfoCompat.ACTION_DISMISS, new C15381(this));
        this.aGS = new zzm().zza(AccessibilityNodeInfoCompat.ACTION_DISMISS, new C15392(this));
        this.aGO = new HashMap();
        zzb(new zzj(context));
        zzb(new zzu(com_google_android_gms_tagmanager_zzu_zza2));
        zzb(new zzy(dataLayer));
        zzb(new zzdn(context, dataLayer));
        this.aGP = new HashMap();
        zzc(new zzs());
        zzc(new zzag());
        zzc(new zzah());
        zzc(new zzao());
        zzc(new zzap());
        zzc(new zzbk());
        zzc(new zzbl());
        zzc(new zzcn());
        zzc(new zzdg());
        this.aGQ = new HashMap();
        zza(new zzb(context));
        zza(new zzc(context));
        zza(new zze(context));
        zza(new zzf(context));
        zza(new zzg(context));
        zza(new zzh(context));
        zza(new zzi(context));
        zza(new zzn());
        zza(new zzr(this.aGM.getVersion()));
        zza(new zzu(com_google_android_gms_tagmanager_zzu_zza));
        zza(new zzw(dataLayer));
        zza(new zzab(context));
        zza(new zzac());
        zza(new zzaf());
        zza(new zzak(this));
        zza(new zzaq());
        zza(new zzar());
        zza(new zzbe(context));
        zza(new zzbg());
        zza(new zzbj());
        zza(new zzbq());
        zza(new zzbs(context));
        zza(new zzcf());
        zza(new zzch());
        zza(new zzck());
        zza(new zzcm());
        zza(new zzco(context));
        zza(new zzcy());
        zza(new zzcz());
        zza(new zzdi());
        zza(new zzdo());
        this.aGU = new HashMap();
        for (zze com_google_android_gms_internal_zzafw_zze : this.aGT) {
            int i;
            for (i = 0; i < com_google_android_gms_internal_zzafw_zze.zzcla().size(); i++) {
                com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza = (com.google.android.gms.internal.zzafw.zza) com_google_android_gms_internal_zzafw_zze.zzcla().get(i);
                String str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                zzc zzi = zzi(this.aGU, zza(com_google_android_gms_internal_zzafw_zza));
                zzi.zza(com_google_android_gms_internal_zzafw_zze);
                zzi.zza(com_google_android_gms_internal_zzafw_zze, com_google_android_gms_internal_zzafw_zza);
                zzi.zza(com_google_android_gms_internal_zzafw_zze, str);
            }
            for (i = 0; i < com_google_android_gms_internal_zzafw_zze.zzcld().size(); i++) {
                com_google_android_gms_internal_zzafw_zza = (com.google.android.gms.internal.zzafw.zza) com_google_android_gms_internal_zzafw_zze.zzcld().get(i);
                str = AnalyticsEvents.PARAMETER_DIALOG_OUTCOME_VALUE_UNKNOWN;
                zzi = zzi(this.aGU, zza(com_google_android_gms_internal_zzafw_zza));
                zzi.zza(com_google_android_gms_internal_zzafw_zze);
                zzi.zzb(com_google_android_gms_internal_zzafw_zze, com_google_android_gms_internal_zzafw_zza);
                zzi.zzb(com_google_android_gms_internal_zzafw_zze, str);
            }
        }
        for (Entry entry : this.aGM.zzckx().entrySet()) {
            for (com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza2 : (List) entry.getValue()) {
                if (!zzdm.zzk((com.google.android.gms.internal.zzaj.zza) com_google_android_gms_internal_zzafw_zza2.zzcjt().get(zzah.NOT_DEFAULT_MACRO.toString())).booleanValue()) {
                    zzi(this.aGU, (String) entry.getKey()).zzb(com_google_android_gms_internal_zzafw_zza2);
                }
            }
        }
    }

    private zzce<com.google.android.gms.internal.zzaj.zza> zza(com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza, Set<String> set, zzdp com_google_android_gms_tagmanager_zzdp) {
        if (!com_google_android_gms_internal_zzaj_zza.zzyh) {
            return new zzce(com_google_android_gms_internal_zzaj_zza, true);
        }
        com.google.android.gms.internal.zzaj.zza zzo;
        int i;
        zzce zza;
        String str;
        String valueOf;
        switch (com_google_android_gms_internal_zzaj_zza.type) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                zzo = zzafw.zzo(com_google_android_gms_internal_zzaj_zza);
                zzo.zzxy = new com.google.android.gms.internal.zzaj.zza[com_google_android_gms_internal_zzaj_zza.zzxy.length];
                for (i = 0; i < com_google_android_gms_internal_zzaj_zza.zzxy.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzaj_zza.zzxy[i], (Set) set, com_google_android_gms_tagmanager_zzdp.zzaac(i));
                    if (zza == aGL) {
                        return aGL;
                    }
                    zzo.zzxy[i] = (com.google.android.gms.internal.zzaj.zza) zza.getObject();
                }
                return new zzce(zzo, false);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                zzo = zzafw.zzo(com_google_android_gms_internal_zzaj_zza);
                if (com_google_android_gms_internal_zzaj_zza.zzxz.length != com_google_android_gms_internal_zzaj_zza.zzya.length) {
                    str = "Invalid serving value: ";
                    valueOf = String.valueOf(com_google_android_gms_internal_zzaj_zza.toString());
                    zzbo.m1698e(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    return aGL;
                }
                zzo.zzxz = new com.google.android.gms.internal.zzaj.zza[com_google_android_gms_internal_zzaj_zza.zzxz.length];
                zzo.zzya = new com.google.android.gms.internal.zzaj.zza[com_google_android_gms_internal_zzaj_zza.zzxz.length];
                for (i = 0; i < com_google_android_gms_internal_zzaj_zza.zzxz.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzaj_zza.zzxz[i], (Set) set, com_google_android_gms_tagmanager_zzdp.zzaad(i));
                    zzce zza2 = zza(com_google_android_gms_internal_zzaj_zza.zzya[i], (Set) set, com_google_android_gms_tagmanager_zzdp.zzaae(i));
                    if (zza == aGL || zza2 == aGL) {
                        return aGL;
                    }
                    zzo.zzxz[i] = (com.google.android.gms.internal.zzaj.zza) zza.getObject();
                    zzo.zzya[i] = (com.google.android.gms.internal.zzaj.zza) zza2.getObject();
                }
                return new zzce(zzo, false);
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                if (set.contains(com_google_android_gms_internal_zzaj_zza.zzyb)) {
                    valueOf = String.valueOf(com_google_android_gms_internal_zzaj_zza.zzyb);
                    str = String.valueOf(set.toString());
                    zzbo.m1698e(new StringBuilder((String.valueOf(valueOf).length() + 79) + String.valueOf(str).length()).append("Macro cycle detected.  Current macro reference: ").append(valueOf).append(".  Previous macro references: ").append(str).append(".").toString());
                    return aGL;
                }
                set.add(com_google_android_gms_internal_zzaj_zza.zzyb);
                zzce<com.google.android.gms.internal.zzaj.zza> zza3 = zzdq.zza(zza(com_google_android_gms_internal_zzaj_zza.zzyb, (Set) set, com_google_android_gms_tagmanager_zzdp.zzcft()), com_google_android_gms_internal_zzaj_zza.zzyg);
                set.remove(com_google_android_gms_internal_zzaj_zza.zzyb);
                return zza3;
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                zzo = zzafw.zzo(com_google_android_gms_internal_zzaj_zza);
                zzo.zzyf = new com.google.android.gms.internal.zzaj.zza[com_google_android_gms_internal_zzaj_zza.zzyf.length];
                for (i = 0; i < com_google_android_gms_internal_zzaj_zza.zzyf.length; i++) {
                    zza = zza(com_google_android_gms_internal_zzaj_zza.zzyf[i], (Set) set, com_google_android_gms_tagmanager_zzdp.zzaaf(i));
                    if (zza == aGL) {
                        return aGL;
                    }
                    zzo.zzyf[i] = (com.google.android.gms.internal.zzaj.zza) zza.getObject();
                }
                return new zzce(zzo, false);
            default:
                zzbo.m1698e("Unknown type: " + com_google_android_gms_internal_zzaj_zza.type);
                return aGL;
        }
    }

    private zzce<com.google.android.gms.internal.zzaj.zza> zza(String str, Set<String> set, zzbr com_google_android_gms_tagmanager_zzbr) {
        this.aGW++;
        zzb com_google_android_gms_tagmanager_zzcx_zzb = (zzb) this.aGS.get(str);
        if (com_google_android_gms_tagmanager_zzcx_zzb != null) {
            zzaj com_google_android_gms_tagmanager_zzaj = this.aGN;
            zza(com_google_android_gms_tagmanager_zzcx_zzb.zzcgm(), (Set) set);
            this.aGW--;
            return com_google_android_gms_tagmanager_zzcx_zzb.zzcgl();
        }
        zzc com_google_android_gms_tagmanager_zzcx_zzc = (zzc) this.aGU.get(str);
        if (com_google_android_gms_tagmanager_zzcx_zzc == null) {
            String valueOf = String.valueOf(zzcgk());
            zzbo.m1698e(new StringBuilder((String.valueOf(valueOf).length() + 15) + String.valueOf(str).length()).append(valueOf).append("Invalid macro: ").append(str).toString());
            this.aGW--;
            return aGL;
        }
        com.google.android.gms.internal.zzafw.zza zzcgs;
        zzce zza = zza(str, com_google_android_gms_tagmanager_zzcx_zzc.zzcgn(), com_google_android_gms_tagmanager_zzcx_zzc.zzcgo(), com_google_android_gms_tagmanager_zzcx_zzc.zzcgp(), com_google_android_gms_tagmanager_zzcx_zzc.zzcgr(), com_google_android_gms_tagmanager_zzcx_zzc.zzcgq(), set, com_google_android_gms_tagmanager_zzbr.zzcet());
        if (((Set) zza.getObject()).isEmpty()) {
            zzcgs = com_google_android_gms_tagmanager_zzcx_zzc.zzcgs();
        } else {
            if (((Set) zza.getObject()).size() > 1) {
                valueOf = String.valueOf(zzcgk());
                zzbo.zzdi(new StringBuilder((String.valueOf(valueOf).length() + 37) + String.valueOf(str).length()).append(valueOf).append("Multiple macros active for macroName ").append(str).toString());
            }
            zzcgs = (com.google.android.gms.internal.zzafw.zza) ((Set) zza.getObject()).iterator().next();
        }
        if (zzcgs == null) {
            this.aGW--;
            return aGL;
        }
        zzce zza2 = zza(this.aGQ, zzcgs, (Set) set, com_google_android_gms_tagmanager_zzbr.zzcfl());
        boolean z = zza.zzcfu() && zza2.zzcfu();
        zzce<com.google.android.gms.internal.zzaj.zza> com_google_android_gms_tagmanager_zzce = zza2 == aGL ? aGL : new zzce((com.google.android.gms.internal.zzaj.zza) zza2.getObject(), z);
        com.google.android.gms.internal.zzaj.zza zzcgm = zzcgs.zzcgm();
        if (com_google_android_gms_tagmanager_zzce.zzcfu()) {
            this.aGS.zzi(str, new zzb(com_google_android_gms_tagmanager_zzce, zzcgm));
        }
        zza(zzcgm, (Set) set);
        this.aGW--;
        return com_google_android_gms_tagmanager_zzce;
    }

    private zzce<com.google.android.gms.internal.zzaj.zza> zza(Map<String, zzam> map, com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza, Set<String> set, zzcp com_google_android_gms_tagmanager_zzcp) {
        boolean z = true;
        com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza = (com.google.android.gms.internal.zzaj.zza) com_google_android_gms_internal_zzafw_zza.zzcjt().get(zzah.FUNCTION.toString());
        if (com_google_android_gms_internal_zzaj_zza == null) {
            zzbo.m1698e("No function id in properties");
            return aGL;
        }
        String str = com_google_android_gms_internal_zzaj_zza.zzyc;
        zzam com_google_android_gms_tagmanager_zzam = (zzam) map.get(str);
        if (com_google_android_gms_tagmanager_zzam == null) {
            zzbo.m1698e(String.valueOf(str).concat(" has no backing implementation."));
            return aGL;
        }
        zzce<com.google.android.gms.internal.zzaj.zza> com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza = (zzce) this.aGR.get(com_google_android_gms_internal_zzafw_zza);
        if (com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza != null) {
            zzaj com_google_android_gms_tagmanager_zzaj = this.aGN;
            return com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
        }
        Map hashMap = new HashMap();
        boolean z2 = true;
        for (Entry entry : com_google_android_gms_internal_zzafw_zza.zzcjt().entrySet()) {
            zzce zza = zza((com.google.android.gms.internal.zzaj.zza) entry.getValue(), (Set) set, com_google_android_gms_tagmanager_zzcp.zzpn((String) entry.getKey()).zze((com.google.android.gms.internal.zzaj.zza) entry.getValue()));
            if (zza == aGL) {
                return aGL;
            }
            boolean z3;
            if (zza.zzcfu()) {
                com_google_android_gms_internal_zzafw_zza.zza((String) entry.getKey(), (com.google.android.gms.internal.zzaj.zza) zza.getObject());
                z3 = z2;
            } else {
                z3 = false;
            }
            hashMap.put((String) entry.getKey(), (com.google.android.gms.internal.zzaj.zza) zza.getObject());
            z2 = z3;
        }
        if (com_google_android_gms_tagmanager_zzam.zzf(hashMap.keySet())) {
            if (!(z2 && com_google_android_gms_tagmanager_zzam.zzcdu())) {
                z = false;
            }
            com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza = new zzce(com_google_android_gms_tagmanager_zzam.zzay(hashMap), z);
            if (z) {
                this.aGR.zzi(com_google_android_gms_internal_zzafw_zza, com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza);
            }
            com_google_android_gms_tagmanager_zzcp.zzd((com.google.android.gms.internal.zzaj.zza) com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza.getObject());
            return com_google_android_gms_tagmanager_zzce_com_google_android_gms_internal_zzaj_zza;
        }
        String valueOf = String.valueOf(com_google_android_gms_tagmanager_zzam.zzcfh());
        String valueOf2 = String.valueOf(hashMap.keySet());
        zzbo.m1698e(new StringBuilder(((String.valueOf(str).length() + 43) + String.valueOf(valueOf).length()) + String.valueOf(valueOf2).length()).append("Incorrect keys for function ").append(str).append(" required ").append(valueOf).append(" had ").append(valueOf2).toString());
        return aGL;
    }

    private zzce<Set<com.google.android.gms.internal.zzafw.zza>> zza(Set<zze> set, Set<String> set2, zza com_google_android_gms_tagmanager_zzcx_zza, zzcw com_google_android_gms_tagmanager_zzcw) {
        Set hashSet = new HashSet();
        Collection hashSet2 = new HashSet();
        boolean z = true;
        for (zze com_google_android_gms_internal_zzafw_zze : set) {
            zzcs zzcfs = com_google_android_gms_tagmanager_zzcw.zzcfs();
            zzce zza = zza(com_google_android_gms_internal_zzafw_zze, (Set) set2, zzcfs);
            if (((Boolean) zza.getObject()).booleanValue()) {
                com_google_android_gms_tagmanager_zzcx_zza.zza(com_google_android_gms_internal_zzafw_zze, hashSet, hashSet2, zzcfs);
            }
            boolean z2 = z && zza.zzcfu();
            z = z2;
        }
        hashSet.removeAll(hashSet2);
        com_google_android_gms_tagmanager_zzcw.zzg(hashSet);
        return new zzce(hashSet, z);
    }

    private static String zza(com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza) {
        return zzdm.zzg((com.google.android.gms.internal.zzaj.zza) com_google_android_gms_internal_zzafw_zza.zzcjt().get(zzah.INSTANCE_NAME.toString()));
    }

    private void zza(com.google.android.gms.internal.zzaj.zza com_google_android_gms_internal_zzaj_zza, Set<String> set) {
        if (com_google_android_gms_internal_zzaj_zza != null) {
            zzce zza = zza(com_google_android_gms_internal_zzaj_zza, (Set) set, new zzcc());
            if (zza != aGL) {
                Object zzl = zzdm.zzl((com.google.android.gms.internal.zzaj.zza) zza.getObject());
                if (zzl instanceof Map) {
                    this.aDZ.push((Map) zzl);
                } else if (zzl instanceof List) {
                    for (Object zzl2 : (List) zzl2) {
                        if (zzl2 instanceof Map) {
                            this.aDZ.push((Map) zzl2);
                        } else {
                            zzbo.zzdi("pushAfterEvaluate: value not a Map");
                        }
                    }
                } else {
                    zzbo.zzdi("pushAfterEvaluate: value not a Map or List");
                }
            }
        }
    }

    private static void zza(Map<String, zzam> map, zzam com_google_android_gms_tagmanager_zzam) {
        if (map.containsKey(com_google_android_gms_tagmanager_zzam.zzcfg())) {
            String str = "Duplicate function type name: ";
            String valueOf = String.valueOf(com_google_android_gms_tagmanager_zzam.zzcfg());
            throw new IllegalArgumentException(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
        map.put(com_google_android_gms_tagmanager_zzam.zzcfg(), com_google_android_gms_tagmanager_zzam);
    }

    private String zzcgk() {
        if (this.aGW <= 1) {
            return BuildConfig.FLAVOR;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Integer.toString(this.aGW));
        for (int i = 2; i < this.aGW; i++) {
            stringBuilder.append(' ');
        }
        stringBuilder.append(": ");
        return stringBuilder.toString();
    }

    private static zzc zzi(Map<String, zzc> map, String str) {
        zzc com_google_android_gms_tagmanager_zzcx_zzc = (zzc) map.get(str);
        if (com_google_android_gms_tagmanager_zzcx_zzc != null) {
            return com_google_android_gms_tagmanager_zzcx_zzc;
        }
        com_google_android_gms_tagmanager_zzcx_zzc = new zzc();
        map.put(str, com_google_android_gms_tagmanager_zzcx_zzc);
        return com_google_android_gms_tagmanager_zzcx_zzc;
    }

    zzce<Boolean> zza(com.google.android.gms.internal.zzafw.zza com_google_android_gms_internal_zzafw_zza, Set<String> set, zzcp com_google_android_gms_tagmanager_zzcp) {
        zzce zza = zza(this.aGP, com_google_android_gms_internal_zzafw_zza, (Set) set, com_google_android_gms_tagmanager_zzcp);
        Boolean zzk = zzdm.zzk((com.google.android.gms.internal.zzaj.zza) zza.getObject());
        com_google_android_gms_tagmanager_zzcp.zzd(zzdm.zzat(zzk));
        return new zzce(zzk, zza.zzcfu());
    }

    zzce<Boolean> zza(zze com_google_android_gms_internal_zzafw_zze, Set<String> set, zzcs com_google_android_gms_tagmanager_zzcs) {
        boolean z = true;
        for (com.google.android.gms.internal.zzafw.zza zza : com_google_android_gms_internal_zzafw_zze.zzcjw()) {
            zzce zza2 = zza(zza, (Set) set, com_google_android_gms_tagmanager_zzcs.zzcfm());
            if (((Boolean) zza2.getObject()).booleanValue()) {
                com_google_android_gms_tagmanager_zzcs.zzf(zzdm.zzat(Boolean.valueOf(false)));
                return new zzce(Boolean.valueOf(false), zza2.zzcfu());
            }
            boolean z2 = z && zza2.zzcfu();
            z = z2;
        }
        for (com.google.android.gms.internal.zzafw.zza zza3 : com_google_android_gms_internal_zzafw_zze.zzcjv()) {
            zza2 = zza(zza3, (Set) set, com_google_android_gms_tagmanager_zzcs.zzcfn());
            if (((Boolean) zza2.getObject()).booleanValue()) {
                z = z && zza2.zzcfu();
            } else {
                com_google_android_gms_tagmanager_zzcs.zzf(zzdm.zzat(Boolean.valueOf(false)));
                return new zzce(Boolean.valueOf(false), zza2.zzcfu());
            }
        }
        com_google_android_gms_tagmanager_zzcs.zzf(zzdm.zzat(Boolean.valueOf(true)));
        return new zzce(Boolean.valueOf(true), z);
    }

    zzce<Set<com.google.android.gms.internal.zzafw.zza>> zza(String str, Set<zze> set, Map<zze, List<com.google.android.gms.internal.zzafw.zza>> map, Map<zze, List<String>> map2, Map<zze, List<com.google.android.gms.internal.zzafw.zza>> map3, Map<zze, List<String>> map4, Set<String> set2, zzcw com_google_android_gms_tagmanager_zzcw) {
        return zza((Set) set, (Set) set2, new C15403(this, map, map2, map3, map4), com_google_android_gms_tagmanager_zzcw);
    }

    zzce<Set<com.google.android.gms.internal.zzafw.zza>> zza(Set<zze> set, zzcw com_google_android_gms_tagmanager_zzcw) {
        return zza((Set) set, new HashSet(), new C15414(this), com_google_android_gms_tagmanager_zzcw);
    }

    void zza(zzam com_google_android_gms_tagmanager_zzam) {
        zza(this.aGQ, com_google_android_gms_tagmanager_zzam);
    }

    public synchronized void zzam(List<zzi> list) {
        for (zzi com_google_android_gms_internal_zzai_zzi : list) {
            if (com_google_android_gms_internal_zzai_zzi.name == null || !com_google_android_gms_internal_zzai_zzi.name.startsWith("gaExperiment:")) {
                String valueOf = String.valueOf(com_google_android_gms_internal_zzai_zzi);
                zzbo.m1699v(new StringBuilder(String.valueOf(valueOf).length() + 22).append("Ignored supplemental: ").append(valueOf).toString());
            } else {
                zzal.zza(this.aDZ, com_google_android_gms_internal_zzai_zzi);
            }
        }
    }

    void zzb(zzam com_google_android_gms_tagmanager_zzam) {
        zza(this.aGO, com_google_android_gms_tagmanager_zzam);
    }

    void zzc(zzam com_google_android_gms_tagmanager_zzam) {
        zza(this.aGP, com_google_android_gms_tagmanager_zzam);
    }

    synchronized String zzcgj() {
        return this.aGV;
    }

    public synchronized void zzov(String str) {
        zzps(str);
        zzai zzpi = this.aGN.zzpi(str);
        zzv zzcfe = zzpi.zzcfe();
        for (com.google.android.gms.internal.zzafw.zza zza : (Set) zza(this.aGT, zzcfe.zzcet()).getObject()) {
            zza(this.aGO, zza, new HashSet(), zzcfe.zzces());
        }
        zzpi.zzcff();
        zzps(null);
    }

    public zzce<com.google.android.gms.internal.zzaj.zza> zzpr(String str) {
        this.aGW = 0;
        zzai zzph = this.aGN.zzph(str);
        zzce<com.google.android.gms.internal.zzaj.zza> zza = zza(str, new HashSet(), zzph.zzcfd());
        zzph.zzcff();
        return zza;
    }

    synchronized void zzps(String str) {
        this.aGV = str;
    }
}
