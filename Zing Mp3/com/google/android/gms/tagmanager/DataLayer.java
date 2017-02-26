package com.google.android.gms.tagmanager;

import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataLayer {
    public static final String EVENT_KEY = "event";
    public static final Object OBJECT_NOT_PRESENT;
    static final String[] aEF;
    private static final Pattern aEG;
    private final ConcurrentHashMap<zzb, Integer> aEH;
    private final Map<String, Object> aEI;
    private final ReentrantLock aEJ;
    private final LinkedList<Map<String, Object>> aEK;
    private final zzc aEL;
    private final CountDownLatch aEM;

    interface zzc {

        public interface zza {
            void zzai(List<zza> list);
        }

        void zza(zza com_google_android_gms_tagmanager_DataLayer_zzc_zza);

        void zza(List<zza> list, long j);

        void zzpd(String str);
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.1 */
    class C15221 implements zzc {
        C15221() {
        }

        public void zza(zza com_google_android_gms_tagmanager_DataLayer_zzc_zza) {
            com_google_android_gms_tagmanager_DataLayer_zzc_zza.zzai(new ArrayList());
        }

        public void zza(List<zza> list, long j) {
        }

        public void zzpd(String str) {
        }
    }

    /* renamed from: com.google.android.gms.tagmanager.DataLayer.2 */
    class C15232 implements zza {
        final /* synthetic */ DataLayer aEN;

        C15232(DataLayer dataLayer) {
            this.aEN = dataLayer;
        }

        public void zzai(List<zza> list) {
            for (zza com_google_android_gms_tagmanager_DataLayer_zza : list) {
                this.aEN.zzbb(this.aEN.zzo(com_google_android_gms_tagmanager_DataLayer_zza.zzbcn, com_google_android_gms_tagmanager_DataLayer_zza.zzcyd));
            }
            this.aEN.aEM.countDown();
        }
    }

    static final class zza {
        public final String zzbcn;
        public final Object zzcyd;

        zza(String str, Object obj) {
            this.zzbcn = str;
            this.zzcyd = obj;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_tagmanager_DataLayer_zza = (zza) obj;
            return this.zzbcn.equals(com_google_android_gms_tagmanager_DataLayer_zza.zzbcn) && this.zzcyd.equals(com_google_android_gms_tagmanager_DataLayer_zza.zzcyd);
        }

        public int hashCode() {
            return Arrays.hashCode(new Integer[]{Integer.valueOf(this.zzbcn.hashCode()), Integer.valueOf(this.zzcyd.hashCode())});
        }

        public String toString() {
            String str = this.zzbcn;
            String valueOf = String.valueOf(this.zzcyd.toString());
            return new StringBuilder((String.valueOf(str).length() + 13) + String.valueOf(valueOf).length()).append("Key: ").append(str).append(" value: ").append(valueOf).toString();
        }
    }

    interface zzb {
        void zzaz(Map<String, Object> map);
    }

    static {
        OBJECT_NOT_PRESENT = new Object();
        aEF = "gtm.lifetime".toString().split("\\.");
        aEG = Pattern.compile("(\\d+)\\s*([smhd]?)");
    }

    DataLayer() {
        this(new C15221());
    }

    DataLayer(zzc com_google_android_gms_tagmanager_DataLayer_zzc) {
        this.aEL = com_google_android_gms_tagmanager_DataLayer_zzc;
        this.aEH = new ConcurrentHashMap();
        this.aEI = new HashMap();
        this.aEJ = new ReentrantLock();
        this.aEK = new LinkedList();
        this.aEM = new CountDownLatch(1);
        zzceq();
    }

    public static List<Object> listOf(Object... objArr) {
        List<Object> arrayList = new ArrayList();
        for (Object add : objArr) {
            arrayList.add(add);
        }
        return arrayList;
    }

    public static Map<String, Object> mapOf(Object... objArr) {
        if (objArr.length % 2 != 0) {
            throw new IllegalArgumentException("expected even number of key-value pairs");
        }
        Map<String, Object> hashMap = new HashMap();
        int i = 0;
        while (i < objArr.length) {
            if (objArr[i] instanceof String) {
                hashMap.put((String) objArr[i], objArr[i + 1]);
                i += 2;
            } else {
                String valueOf = String.valueOf(objArr[i]);
                throw new IllegalArgumentException(new StringBuilder(String.valueOf(valueOf).length() + 21).append("key is not a string: ").append(valueOf).toString());
            }
        }
        return hashMap;
    }

    private void zza(Map<String, Object> map, String str, Collection<zza> collection) {
        for (Entry entry : map.entrySet()) {
            String str2 = str.length() == 0 ? BuildConfig.FLAVOR : ".";
            String str3 = (String) entry.getKey();
            str3 = new StringBuilder(((String.valueOf(str).length() + 0) + String.valueOf(str2).length()) + String.valueOf(str3).length()).append(str).append(str2).append(str3).toString();
            if (entry.getValue() instanceof Map) {
                zza((Map) entry.getValue(), str3, collection);
            } else if (!str3.equals("gtm.lifetime")) {
                collection.add(new zza(str3, entry.getValue()));
            }
        }
    }

    private void zzbb(Map<String, Object> map) {
        this.aEJ.lock();
        try {
            this.aEK.offer(map);
            if (this.aEJ.getHoldCount() == 1) {
                zzcer();
            }
            zzbc(map);
        } finally {
            this.aEJ.unlock();
        }
    }

    private void zzbc(Map<String, Object> map) {
        Long zzbd = zzbd(map);
        if (zzbd != null) {
            List zzbf = zzbf(map);
            zzbf.remove("gtm.lifetime");
            this.aEL.zza(zzbf, zzbd.longValue());
        }
    }

    private Long zzbd(Map<String, Object> map) {
        Object zzbe = zzbe(map);
        return zzbe == null ? null : zzpc(zzbe.toString());
    }

    private Object zzbe(Map<String, Object> map) {
        String[] strArr = aEF;
        int length = strArr.length;
        int i = 0;
        Object obj = map;
        while (i < length) {
            Object obj2 = strArr[i];
            if (!(obj instanceof Map)) {
                return null;
            }
            i++;
            obj = ((Map) obj).get(obj2);
        }
        return obj;
    }

    private List<zza> zzbf(Map<String, Object> map) {
        Object arrayList = new ArrayList();
        zza(map, BuildConfig.FLAVOR, arrayList);
        return arrayList;
    }

    private void zzbg(Map<String, Object> map) {
        synchronized (this.aEI) {
            for (String str : map.keySet()) {
                zzd(zzo(str, map.get(str)), this.aEI);
            }
        }
        zzbh(map);
    }

    private void zzbh(Map<String, Object> map) {
        for (zzb zzaz : this.aEH.keySet()) {
            zzaz.zzaz(map);
        }
    }

    private void zzceq() {
        this.aEL.zza(new C15232(this));
    }

    private void zzcer() {
        int i = 0;
        while (true) {
            Map map = (Map) this.aEK.poll();
            if (map != null) {
                zzbg(map);
                int i2 = i + 1;
                if (i2 > 500) {
                    break;
                }
                i = i2;
            } else {
                return;
            }
        }
        this.aEK.clear();
        throw new RuntimeException("Seems like an infinite loop of pushing to the data layer");
    }

    static Long zzpc(String str) {
        String str2;
        String valueOf;
        Matcher matcher = aEG.matcher(str);
        if (matcher.matches()) {
            long parseLong;
            try {
                parseLong = Long.parseLong(matcher.group(1));
            } catch (NumberFormatException e) {
                str2 = "illegal number in _lifetime value: ";
                valueOf = String.valueOf(str);
                zzbo.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                parseLong = 0;
            }
            if (parseLong <= 0) {
                str2 = "non-positive _lifetime: ";
                valueOf = String.valueOf(str);
                zzbo.zzdh(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                return null;
            }
            valueOf = matcher.group(2);
            if (valueOf.length() == 0) {
                return Long.valueOf(parseLong);
            }
            switch (valueOf.charAt(0)) {
                case C1569R.styleable.AppCompatTheme_buttonBarNeutralButtonStyle /*100*/:
                    return Long.valueOf((((parseLong * 1000) * 60) * 60) * 24);
                case C1569R.styleable.AppCompatTheme_checkboxStyle /*104*/:
                    return Long.valueOf(((parseLong * 1000) * 60) * 60);
                case C1569R.styleable.AppCompatTheme_ratingBarStyleIndicator /*109*/:
                    return Long.valueOf((parseLong * 1000) * 60);
                case 's':
                    return Long.valueOf(parseLong * 1000);
                default:
                    str2 = "unknown units in _lifetime: ";
                    valueOf = String.valueOf(str);
                    zzbo.zzdi(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    return null;
            }
        }
        str2 = "unknown _lifetime: ";
        valueOf = String.valueOf(str);
        zzbo.zzdh(valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
        return null;
    }

    public Object get(String str) {
        synchronized (this.aEI) {
            Map map = this.aEI;
            String[] split = str.split("\\.");
            int length = split.length;
            Object obj = map;
            int i = 0;
            while (i < length) {
                Object obj2 = split[i];
                if (obj instanceof Map) {
                    obj2 = ((Map) obj).get(obj2);
                    if (obj2 == null) {
                        return null;
                    }
                    i++;
                    obj = obj2;
                } else {
                    return null;
                }
            }
            return obj;
        }
    }

    public void push(String str, Object obj) {
        push(zzo(str, obj));
    }

    public void push(Map<String, Object> map) {
        try {
            this.aEM.await();
        } catch (InterruptedException e) {
            zzbo.zzdi("DataLayer.push: unexpected InterruptedException");
        }
        zzbb(map);
    }

    public void pushEvent(String str, Map<String, Object> map) {
        Map hashMap = new HashMap(map);
        hashMap.put(EVENT_KEY, str);
        push(hashMap);
    }

    public String toString() {
        String stringBuilder;
        synchronized (this.aEI) {
            StringBuilder stringBuilder2 = new StringBuilder();
            for (Entry entry : this.aEI.entrySet()) {
                stringBuilder2.append(String.format("{\n\tKey: %s\n\tValue: %s\n}\n", new Object[]{entry.getKey(), entry.getValue()}));
            }
            stringBuilder = stringBuilder2.toString();
        }
        return stringBuilder;
    }

    void zza(zzb com_google_android_gms_tagmanager_DataLayer_zzb) {
        this.aEH.put(com_google_android_gms_tagmanager_DataLayer_zzb, Integer.valueOf(0));
    }

    void zzb(List<Object> list, List<Object> list2) {
        while (list2.size() < list.size()) {
            list2.add(null);
        }
        for (int i = 0; i < list.size(); i++) {
            Object obj = list.get(i);
            if (obj instanceof List) {
                if (!(list2.get(i) instanceof List)) {
                    list2.set(i, new ArrayList());
                }
                zzb((List) obj, (List) list2.get(i));
            } else if (obj instanceof Map) {
                if (!(list2.get(i) instanceof Map)) {
                    list2.set(i, new HashMap());
                }
                zzd((Map) obj, (Map) list2.get(i));
            } else if (obj != OBJECT_NOT_PRESENT) {
                list2.set(i, obj);
            }
        }
    }

    void zzd(Map<String, Object> map, Map<String, Object> map2) {
        for (String str : map.keySet()) {
            Object obj = map.get(str);
            if (obj instanceof List) {
                if (!(map2.get(str) instanceof List)) {
                    map2.put(str, new ArrayList());
                }
                zzb((List) obj, (List) map2.get(str));
            } else if (obj instanceof Map) {
                if (!(map2.get(str) instanceof Map)) {
                    map2.put(str, new HashMap());
                }
                zzd((Map) obj, (Map) map2.get(str));
            } else {
                map2.put(str, obj);
            }
        }
    }

    Map<String, Object> zzo(String str, Object obj) {
        Map hashMap = new HashMap();
        String[] split = str.toString().split("\\.");
        int i = 0;
        Map map = hashMap;
        while (i < split.length - 1) {
            HashMap hashMap2 = new HashMap();
            map.put(split[i], hashMap2);
            i++;
            Object obj2 = hashMap2;
        }
        map.put(split[split.length - 1], obj);
        return hashMap;
    }

    void zzpb(String str) {
        push(str, null);
        this.aEL.zzpd(str);
    }
}
