package com.google.android.gms.internal;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.SortedMap;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

public final class zzaps {
    private final Map<Type, zzaou<?>> bop;

    /* renamed from: com.google.android.gms.internal.zzaps.1 */
    class C12161 implements zzapx<T> {
        final /* synthetic */ zzaou boP;
        final /* synthetic */ Type boQ;
        final /* synthetic */ zzaps boR;

        C12161(zzaps com_google_android_gms_internal_zzaps, zzaou com_google_android_gms_internal_zzaou, Type type) {
            this.boR = com_google_android_gms_internal_zzaps;
            this.boP = com_google_android_gms_internal_zzaou;
            this.boQ = type;
        }

        public T bj() {
            return this.boP.zza(this.boQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.2 */
    class C12172 implements zzapx<T> {
        final /* synthetic */ zzaps boR;

        C12172(zzaps com_google_android_gms_internal_zzaps) {
            this.boR = com_google_android_gms_internal_zzaps;
        }

        public T bj() {
            return new LinkedHashMap();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.3 */
    class C12183 implements zzapx<T> {
        final /* synthetic */ zzaps boR;

        C12183(zzaps com_google_android_gms_internal_zzaps) {
            this.boR = com_google_android_gms_internal_zzaps;
        }

        public T bj() {
            return new zzapw();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.4 */
    class C12194 implements zzapx<T> {
        final /* synthetic */ Type boQ;
        final /* synthetic */ zzaps boR;
        private final zzaqa boS;
        final /* synthetic */ Class boT;

        C12194(zzaps com_google_android_gms_internal_zzaps, Class cls, Type type) {
            this.boR = com_google_android_gms_internal_zzaps;
            this.boT = cls;
            this.boQ = type;
            this.boS = zzaqa.bo();
        }

        public T bj() {
            try {
                return this.boS.zzf(this.boT);
            } catch (Throwable e) {
                String valueOf = String.valueOf(this.boQ);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 116).append("Unable to invoke no-args constructor for ").append(valueOf).append(". ").append("Register an InstanceCreator with Gson for this type may fix this problem.").toString(), e);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.5 */
    class C12205 implements zzapx<T> {
        final /* synthetic */ Type boQ;
        final /* synthetic */ zzaps boR;
        final /* synthetic */ zzaou boU;

        C12205(zzaps com_google_android_gms_internal_zzaps, zzaou com_google_android_gms_internal_zzaou, Type type) {
            this.boR = com_google_android_gms_internal_zzaps;
            this.boU = com_google_android_gms_internal_zzaou;
            this.boQ = type;
        }

        public T bj() {
            return this.boU.zza(this.boQ);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.6 */
    class C12216 implements zzapx<T> {
        final /* synthetic */ zzaps boR;
        final /* synthetic */ Constructor boV;

        C12216(zzaps com_google_android_gms_internal_zzaps, Constructor constructor) {
            this.boR = com_google_android_gms_internal_zzaps;
            this.boV = constructor;
        }

        public T bj() {
            String valueOf;
            try {
                return this.boV.newInstance(null);
            } catch (Throwable e) {
                valueOf = String.valueOf(this.boV);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 30).append("Failed to invoke ").append(valueOf).append(" with no args").toString(), e);
            } catch (InvocationTargetException e2) {
                valueOf = String.valueOf(this.boV);
                throw new RuntimeException(new StringBuilder(String.valueOf(valueOf).length() + 30).append("Failed to invoke ").append(valueOf).append(" with no args").toString(), e2.getTargetException());
            } catch (IllegalAccessException e3) {
                throw new AssertionError(e3);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.7 */
    class C12227 implements zzapx<T> {
        final /* synthetic */ zzaps boR;

        C12227(zzaps com_google_android_gms_internal_zzaps) {
            this.boR = com_google_android_gms_internal_zzaps;
        }

        public T bj() {
            return new TreeSet();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.8 */
    class C12238 implements zzapx<T> {
        final /* synthetic */ Type boQ;
        final /* synthetic */ zzaps boR;

        C12238(zzaps com_google_android_gms_internal_zzaps, Type type) {
            this.boR = com_google_android_gms_internal_zzaps;
            this.boQ = type;
        }

        public T bj() {
            if (this.boQ instanceof ParameterizedType) {
                Type type = ((ParameterizedType) this.boQ).getActualTypeArguments()[0];
                if (type instanceof Class) {
                    return EnumSet.noneOf((Class) type);
                }
                String str = "Invalid EnumSet type: ";
                String valueOf = String.valueOf(this.boQ.toString());
                throw new zzaoz(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
            str = "Invalid EnumSet type: ";
            valueOf = String.valueOf(this.boQ.toString());
            throw new zzaoz(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaps.9 */
    class C12249 implements zzapx<T> {
        final /* synthetic */ zzaps boR;

        C12249(zzaps com_google_android_gms_internal_zzaps) {
            this.boR = com_google_android_gms_internal_zzaps;
        }

        public T bj() {
            return new LinkedHashSet();
        }
    }

    public zzaps(Map<Type, zzaou<?>> map) {
        this.bop = map;
    }

    private <T> zzapx<T> zzc(Type type, Class<? super T> cls) {
        return Collection.class.isAssignableFrom(cls) ? SortedSet.class.isAssignableFrom(cls) ? new C12227(this) : EnumSet.class.isAssignableFrom(cls) ? new C12238(this, type) : Set.class.isAssignableFrom(cls) ? new C12249(this) : Queue.class.isAssignableFrom(cls) ? new zzapx<T>() {
            final /* synthetic */ zzaps boR;

            {
                this.boR = r1;
            }

            public T bj() {
                return new LinkedList();
            }
        } : new zzapx<T>() {
            final /* synthetic */ zzaps boR;

            {
                this.boR = r1;
            }

            public T bj() {
                return new ArrayList();
            }
        } : Map.class.isAssignableFrom(cls) ? SortedMap.class.isAssignableFrom(cls) ? new zzapx<T>() {
            final /* synthetic */ zzaps boR;

            {
                this.boR = r1;
            }

            public T bj() {
                return new TreeMap();
            }
        } : (!(type instanceof ParameterizedType) || String.class.isAssignableFrom(zzaqo.zzl(((ParameterizedType) type).getActualTypeArguments()[0]).bB())) ? new C12183(this) : new C12172(this) : null;
    }

    private <T> zzapx<T> zzd(Type type, Class<? super T> cls) {
        return new C12194(this, cls, type);
    }

    private <T> zzapx<T> zzl(Class<? super T> cls) {
        try {
            Constructor declaredConstructor = cls.getDeclaredConstructor(new Class[0]);
            if (!declaredConstructor.isAccessible()) {
                declaredConstructor.setAccessible(true);
            }
            return new C12216(this, declaredConstructor);
        } catch (NoSuchMethodException e) {
            return null;
        }
    }

    public String toString() {
        return this.bop.toString();
    }

    public <T> zzapx<T> zzb(zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
        Type bC = com_google_android_gms_internal_zzaqo_T.bC();
        Class bB = com_google_android_gms_internal_zzaqo_T.bB();
        zzaou com_google_android_gms_internal_zzaou = (zzaou) this.bop.get(bC);
        if (com_google_android_gms_internal_zzaou != null) {
            return new C12161(this, com_google_android_gms_internal_zzaou, bC);
        }
        com_google_android_gms_internal_zzaou = (zzaou) this.bop.get(bB);
        if (com_google_android_gms_internal_zzaou != null) {
            return new C12205(this, com_google_android_gms_internal_zzaou, bC);
        }
        zzapx<T> zzl = zzl(bB);
        if (zzl != null) {
            return zzl;
        }
        zzl = zzc(bC, bB);
        return zzl == null ? zzd(bC, bB) : zzl;
    }
}
