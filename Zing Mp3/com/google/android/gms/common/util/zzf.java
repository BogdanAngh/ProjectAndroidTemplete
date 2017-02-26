package com.google.android.gms.common.util;

import android.support.v4.util.ArrayMap;
import com.mp3download.zingmp3.C1569R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class zzf {
    public static <K, V> Map<K, V> zza(K k, V v, K k2, V v2, K k3, V v3, K k4, V v4, K k5, V v5, K k6, V v6) {
        Map arrayMap = new ArrayMap(6);
        arrayMap.put(k, v);
        arrayMap.put(k2, v2);
        arrayMap.put(k3, v3);
        arrayMap.put(k4, v4);
        arrayMap.put(k5, v5);
        arrayMap.put(k6, v6);
        return Collections.unmodifiableMap(arrayMap);
    }

    public static <T> Set<T> zza(T t, T t2, T t3) {
        Set com_google_android_gms_common_util_zza = new zza(3);
        com_google_android_gms_common_util_zza.add(t);
        com_google_android_gms_common_util_zza.add(t2);
        com_google_android_gms_common_util_zza.add(t3);
        return Collections.unmodifiableSet(com_google_android_gms_common_util_zza);
    }

    public static <T> Set<T> zza(T t, T t2, T t3, T t4) {
        Set com_google_android_gms_common_util_zza = new zza(4);
        com_google_android_gms_common_util_zza.add(t);
        com_google_android_gms_common_util_zza.add(t2);
        com_google_android_gms_common_util_zza.add(t3);
        com_google_android_gms_common_util_zza.add(t4);
        return Collections.unmodifiableSet(com_google_android_gms_common_util_zza);
    }

    private static <K, V> void zza(K[] kArr, V[] vArr) {
        if (kArr.length != vArr.length) {
            int length = kArr.length;
            throw new IllegalArgumentException("Key and values array lengths not equal: " + length + " != " + vArr.length);
        }
    }

    public static <T> Set<T> zzaa(T t) {
        return Collections.singleton(t);
    }

    public static <T> Set<T> zzayj() {
        return Collections.emptySet();
    }

    public static <K, V> Map<K, V> zzayk() {
        return Collections.emptyMap();
    }

    public static <K, V> Map<K, V> zzb(K[] kArr, V[] vArr) {
        int i = 0;
        zza(kArr, vArr);
        int length = kArr.length;
        switch (length) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return zzayk();
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return zze(kArr[0], vArr[0]);
            default:
                Map arrayMap = length <= 32 ? new ArrayMap(length) : new HashMap(length, 1.0f);
                while (i < length) {
                    arrayMap.put(kArr[i], vArr[i]);
                    i++;
                }
                return Collections.unmodifiableMap(arrayMap);
        }
    }

    public static <T> List<T> zzc(T t, T t2) {
        List arrayList = new ArrayList(2);
        arrayList.add(t);
        arrayList.add(t2);
        return Collections.unmodifiableList(arrayList);
    }

    public static <T> Set<T> zzc(T... tArr) {
        switch (tArr.length) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return zzayj();
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return zzaa(tArr[0]);
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return zzd(tArr[0], tArr[1]);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return zza(tArr[0], tArr[1], tArr[2]);
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return zza(tArr[0], tArr[1], tArr[2], tArr[3]);
            default:
                return Collections.unmodifiableSet(tArr.length <= 32 ? new zza(Arrays.asList(tArr)) : new HashSet(Arrays.asList(tArr)));
        }
    }

    public static <T> Set<T> zzd(T t, T t2) {
        Set com_google_android_gms_common_util_zza = new zza(2);
        com_google_android_gms_common_util_zza.add(t);
        com_google_android_gms_common_util_zza.add(t2);
        return Collections.unmodifiableSet(com_google_android_gms_common_util_zza);
    }

    public static <K, V> Map<K, V> zze(K k, V v) {
        return Collections.singletonMap(k, v);
    }

    public static <T> List<T> zzz(T t) {
        return Collections.singletonList(t);
    }
}
