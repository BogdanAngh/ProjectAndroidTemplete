package com.google.android.gms.ads.internal.client;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.mediation.MediationAdapter;
import com.google.android.gms.ads.mediation.NetworkExtras;
import com.google.android.gms.ads.mediation.admob.AdMobExtras;
import com.google.android.gms.ads.mediation.customevent.CustomEvent;
import com.google.android.gms.ads.search.SearchAdRequest;
import com.google.android.gms.internal.zzji;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@zzji
public final class zzad {
    public static final String DEVICE_ID_EMULATOR;
    private final boolean zzamv;
    private final int zzazc;
    private final int zzazf;
    private final String zzazg;
    private final String zzazi;
    private final Bundle zzazk;
    private final String zzazm;
    private final boolean zzazo;
    private final Bundle zzbar;
    private final Map<Class<? extends NetworkExtras>, NetworkExtras> zzbas;
    private final SearchAdRequest zzbat;
    private final Set<String> zzbau;
    private final Set<String> zzbav;
    private final Date zzgr;
    private final Set<String> zzgt;
    private final Location zzgv;

    public static final class zza {
        private boolean zzamv;
        private int zzazc;
        private int zzazf;
        private String zzazg;
        private String zzazi;
        private final Bundle zzazk;
        private String zzazm;
        private boolean zzazo;
        private final Bundle zzbar;
        private final HashSet<String> zzbaw;
        private final HashMap<Class<? extends NetworkExtras>, NetworkExtras> zzbax;
        private final HashSet<String> zzbay;
        private final HashSet<String> zzbaz;
        private Date zzgr;
        private Location zzgv;

        public zza() {
            this.zzbaw = new HashSet();
            this.zzbar = new Bundle();
            this.zzbax = new HashMap();
            this.zzbay = new HashSet();
            this.zzazk = new Bundle();
            this.zzbaz = new HashSet();
            this.zzazc = -1;
            this.zzamv = false;
            this.zzazf = -1;
        }

        public void setManualImpressionsEnabled(boolean z) {
            this.zzamv = z;
        }

        @Deprecated
        public void zza(NetworkExtras networkExtras) {
            if (networkExtras instanceof AdMobExtras) {
                zza(AdMobAdapter.class, ((AdMobExtras) networkExtras).getExtras());
            } else {
                this.zzbax.put(networkExtras.getClass(), networkExtras);
            }
        }

        public void zza(Class<? extends MediationAdapter> cls, Bundle bundle) {
            this.zzbar.putBundle(cls.getName(), bundle);
        }

        public void zza(Date date) {
            this.zzgr = date;
        }

        public void zzam(String str) {
            this.zzbaw.add(str);
        }

        public void zzan(String str) {
            this.zzbay.add(str);
        }

        public void zzao(String str) {
            this.zzbay.remove(str);
        }

        public void zzap(String str) {
            this.zzazi = str;
        }

        public void zzaq(String str) {
            this.zzazg = str;
        }

        public void zzar(String str) {
            this.zzazm = str;
        }

        public void zzas(String str) {
            this.zzbaz.add(str);
        }

        public void zzb(Location location) {
            this.zzgv = location;
        }

        public void zzb(Class<? extends CustomEvent> cls, Bundle bundle) {
            if (this.zzbar.getBundle("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter") == null) {
                this.zzbar.putBundle("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter", new Bundle());
            }
            this.zzbar.getBundle("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter").putBundle(cls.getName(), bundle);
        }

        public void zze(String str, String str2) {
            this.zzazk.putString(str, str2);
        }

        public void zzo(boolean z) {
            this.zzazf = z ? 1 : 0;
        }

        public void zzp(boolean z) {
            this.zzazo = z;
        }

        public void zzx(int i) {
            this.zzazc = i;
        }
    }

    static {
        DEVICE_ID_EMULATOR = zzm.zzkr().zzdf("emulator");
    }

    public zzad(zza com_google_android_gms_ads_internal_client_zzad_zza) {
        this(com_google_android_gms_ads_internal_client_zzad_zza, null);
    }

    public zzad(zza com_google_android_gms_ads_internal_client_zzad_zza, SearchAdRequest searchAdRequest) {
        this.zzgr = com_google_android_gms_ads_internal_client_zzad_zza.zzgr;
        this.zzazi = com_google_android_gms_ads_internal_client_zzad_zza.zzazi;
        this.zzazc = com_google_android_gms_ads_internal_client_zzad_zza.zzazc;
        this.zzgt = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzad_zza.zzbaw);
        this.zzgv = com_google_android_gms_ads_internal_client_zzad_zza.zzgv;
        this.zzamv = com_google_android_gms_ads_internal_client_zzad_zza.zzamv;
        this.zzbar = com_google_android_gms_ads_internal_client_zzad_zza.zzbar;
        this.zzbas = Collections.unmodifiableMap(com_google_android_gms_ads_internal_client_zzad_zza.zzbax);
        this.zzazg = com_google_android_gms_ads_internal_client_zzad_zza.zzazg;
        this.zzazm = com_google_android_gms_ads_internal_client_zzad_zza.zzazm;
        this.zzbat = searchAdRequest;
        this.zzazf = com_google_android_gms_ads_internal_client_zzad_zza.zzazf;
        this.zzbau = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzad_zza.zzbay);
        this.zzazk = com_google_android_gms_ads_internal_client_zzad_zza.zzazk;
        this.zzbav = Collections.unmodifiableSet(com_google_android_gms_ads_internal_client_zzad_zza.zzbaz);
        this.zzazo = com_google_android_gms_ads_internal_client_zzad_zza.zzazo;
    }

    public Date getBirthday() {
        return this.zzgr;
    }

    public String getContentUrl() {
        return this.zzazi;
    }

    public Bundle getCustomEventExtrasBundle(Class<? extends CustomEvent> cls) {
        Bundle bundle = this.zzbar.getBundle("com.google.android.gms.ads.mediation.customevent.CustomEventAdapter");
        return bundle != null ? bundle.getBundle(cls.getName()) : null;
    }

    public Bundle getCustomTargeting() {
        return this.zzazk;
    }

    public int getGender() {
        return this.zzazc;
    }

    public Set<String> getKeywords() {
        return this.zzgt;
    }

    public Location getLocation() {
        return this.zzgv;
    }

    public boolean getManualImpressionsEnabled() {
        return this.zzamv;
    }

    @Deprecated
    public <T extends NetworkExtras> T getNetworkExtras(Class<T> cls) {
        return (NetworkExtras) this.zzbas.get(cls);
    }

    public Bundle getNetworkExtrasBundle(Class<? extends MediationAdapter> cls) {
        return this.zzbar.getBundle(cls.getName());
    }

    public String getPublisherProvidedId() {
        return this.zzazg;
    }

    public boolean isDesignedForFamilies() {
        return this.zzazo;
    }

    public boolean isTestDevice(Context context) {
        return this.zzbau.contains(zzm.zzkr().zzao(context));
    }

    public String zzkz() {
        return this.zzazm;
    }

    public SearchAdRequest zzla() {
        return this.zzbat;
    }

    public Map<Class<? extends NetworkExtras>, NetworkExtras> zzlb() {
        return this.zzbas;
    }

    public Bundle zzlc() {
        return this.zzbar;
    }

    public int zzld() {
        return this.zzazf;
    }

    public Set<String> zzle() {
        return this.zzbav;
    }
}
