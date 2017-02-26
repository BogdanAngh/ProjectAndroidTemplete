package com.google.android.gms.internal;

import com.google.android.gms.ads.internal.util.client.zzb;
import com.mp3download.zingmp3.BuildConfig;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;

@zzji
public class zzdi {
    private final int zzaxh;
    private final zzdc zzaxj;
    private String zzaxs;
    private String zzaxt;
    private final boolean zzaxu;
    private final int zzaxv;
    private final int zzaxw;

    public class zza implements Comparator<zzdb> {
        final /* synthetic */ zzdi zzaxx;

        public zza(zzdi com_google_android_gms_internal_zzdi) {
            this.zzaxx = com_google_android_gms_internal_zzdi;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((zzdb) obj, (zzdb) obj2);
        }

        public int zza(zzdb com_google_android_gms_internal_zzdb, zzdb com_google_android_gms_internal_zzdb2) {
            if (com_google_android_gms_internal_zzdb.zzjn() < com_google_android_gms_internal_zzdb2.zzjn()) {
                return -1;
            }
            if (com_google_android_gms_internal_zzdb.zzjn() > com_google_android_gms_internal_zzdb2.zzjn()) {
                return 1;
            }
            if (com_google_android_gms_internal_zzdb.zzjm() < com_google_android_gms_internal_zzdb2.zzjm()) {
                return -1;
            }
            if (com_google_android_gms_internal_zzdb.zzjm() > com_google_android_gms_internal_zzdb2.zzjm()) {
                return 1;
            }
            float zzjp = (com_google_android_gms_internal_zzdb.zzjp() - com_google_android_gms_internal_zzdb.zzjn()) * (com_google_android_gms_internal_zzdb.zzjo() - com_google_android_gms_internal_zzdb.zzjm());
            float zzjp2 = (com_google_android_gms_internal_zzdb2.zzjp() - com_google_android_gms_internal_zzdb2.zzjn()) * (com_google_android_gms_internal_zzdb2.zzjo() - com_google_android_gms_internal_zzdb2.zzjm());
            return zzjp <= zzjp2 ? zzjp < zzjp2 ? 1 : 0 : -1;
        }
    }

    public zzdi(int i, int i2, int i3) {
        this.zzaxh = i;
        this.zzaxu = false;
        if (i2 > 64 || i2 < 0) {
            this.zzaxv = 64;
        } else {
            this.zzaxv = i2;
        }
        if (i3 < 1) {
            this.zzaxw = 1;
        } else {
            this.zzaxw = i3;
        }
        this.zzaxj = new zzdh(this.zzaxv);
    }

    String zza(String str, char c) {
        StringBuilder stringBuilder = new StringBuilder(str);
        Object obj = null;
        int i = 1;
        while (i + 2 <= stringBuilder.length()) {
            if (stringBuilder.charAt(i) == '\'') {
                if (stringBuilder.charAt(i - 1) == c || !((stringBuilder.charAt(i + 1) == 's' || stringBuilder.charAt(i + 1) == 'S') && (i + 2 == stringBuilder.length() || stringBuilder.charAt(i + 2) == c))) {
                    stringBuilder.setCharAt(i, c);
                } else {
                    stringBuilder.insert(i, c);
                    i += 2;
                }
                obj = 1;
            }
            i++;
        }
        return obj != null ? stringBuilder.toString() : null;
    }

    public String zza(ArrayList<String> arrayList, ArrayList<zzdb> arrayList2) {
        Collections.sort(arrayList2, new zza(this));
        HashSet hashSet = new HashSet();
        int i = 0;
        while (i < arrayList2.size() && zza(Normalizer.normalize((CharSequence) arrayList.get(((zzdb) arrayList2.get(i)).zzjq()), Form.NFKC).toLowerCase(Locale.US), hashSet)) {
            i++;
        }
        zza com_google_android_gms_internal_zzdd_zza = new zza();
        this.zzaxs = BuildConfig.FLAVOR;
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            try {
                com_google_android_gms_internal_zzdd_zza.write(this.zzaxj.zzag((String) it.next()));
            } catch (Throwable e) {
                zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return com_google_android_gms_internal_zzdd_zza.toString();
    }

    boolean zza(String str, HashSet<String> hashSet) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return true;
        }
        for (String str2 : split) {
            String str22;
            String zza;
            String[] zzd;
            int i;
            Object obj;
            int i2;
            boolean z;
            if (str22.indexOf("'") != -1) {
                zza = zza(str22, ' ');
                if (zza != null) {
                    this.zzaxt = zza;
                    zzd = zzde.zzd(zza, true);
                    if (zzd.length < this.zzaxw) {
                        for (i = 0; i < zzd.length; i++) {
                            obj = BuildConfig.FLAVOR;
                            for (i2 = 0; i2 < this.zzaxw; i2++) {
                                if (i + i2 < zzd.length) {
                                    z = false;
                                    break;
                                }
                                if (i2 > 0) {
                                    obj = String.valueOf(obj).concat(" ");
                                }
                                String valueOf = String.valueOf(obj);
                                str22 = String.valueOf(zzd[i + i2]);
                                obj = str22.length() == 0 ? valueOf.concat(str22) : new String(valueOf);
                            }
                            z = true;
                            if (z) {
                                break;
                            }
                            hashSet.add(obj);
                            if (hashSet.size() < this.zzaxh) {
                                return false;
                            }
                        }
                        if (hashSet.size() < this.zzaxh) {
                            return false;
                        }
                    }
                }
            }
            zza = str22;
            zzd = zzde.zzd(zza, true);
            if (zzd.length < this.zzaxw) {
                while (i < zzd.length) {
                    obj = BuildConfig.FLAVOR;
                    while (i2 < this.zzaxw) {
                        if (i + i2 < zzd.length) {
                            if (i2 > 0) {
                                obj = String.valueOf(obj).concat(" ");
                            }
                            String valueOf2 = String.valueOf(obj);
                            str22 = String.valueOf(zzd[i + i2]);
                            if (str22.length() == 0) {
                            }
                        } else {
                            z = false;
                            break;
                            if (z) {
                                break;
                                if (hashSet.size() < this.zzaxh) {
                                    return false;
                                }
                            } else {
                                hashSet.add(obj);
                                if (hashSet.size() < this.zzaxh) {
                                    return false;
                                }
                            }
                        }
                    }
                    z = true;
                    if (z) {
                        break;
                        if (hashSet.size() < this.zzaxh) {
                            return false;
                        }
                    } else {
                        hashSet.add(obj);
                        if (hashSet.size() < this.zzaxh) {
                            return false;
                        }
                    }
                }
                if (hashSet.size() < this.zzaxh) {
                    return false;
                }
            }
        }
        return true;
    }
}
