package com.google.android.gms.internal;

import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class zzaqa {

    /* renamed from: com.google.android.gms.internal.zzaqa.1 */
    static class C12301 extends zzaqa {
        final /* synthetic */ Method bpC;
        final /* synthetic */ Object bpD;

        C12301(Method method, Object obj) {
            this.bpC = method;
            this.bpD = obj;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bpC.invoke(this.bpD, new Object[]{cls});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqa.2 */
    static class C12312 extends zzaqa {
        final /* synthetic */ Method bpE;
        final /* synthetic */ int bpF;

        C12312(Method method, int i) {
            this.bpE = method;
            this.bpF = i;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bpE.invoke(null, new Object[]{cls, Integer.valueOf(this.bpF)});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqa.3 */
    static class C12323 extends zzaqa {
        final /* synthetic */ Method bpE;

        C12323(Method method) {
            this.bpE = method;
        }

        public <T> T zzf(Class<T> cls) throws Exception {
            return this.bpE.invoke(null, new Object[]{cls, Object.class});
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqa.4 */
    static class C12334 extends zzaqa {
        C12334() {
        }

        public <T> T zzf(Class<T> cls) {
            String valueOf = String.valueOf(cls);
            throw new UnsupportedOperationException(new StringBuilder(String.valueOf(valueOf).length() + 16).append("Cannot allocate ").append(valueOf).toString());
        }
    }

    public static zzaqa bo() {
        try {
            Class cls = Class.forName("sun.misc.Unsafe");
            Field declaredField = cls.getDeclaredField("theUnsafe");
            declaredField.setAccessible(true);
            return new C12301(cls.getMethod("allocateInstance", new Class[]{Class.class}), declaredField.get(null));
        } catch (Exception e) {
            try {
                Method declaredMethod = ObjectStreamClass.class.getDeclaredMethod("getConstructorId", new Class[]{Class.class});
                declaredMethod.setAccessible(true);
                int intValue = ((Integer) declaredMethod.invoke(null, new Object[]{Object.class})).intValue();
                Method declaredMethod2 = ObjectStreamClass.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Integer.TYPE});
                declaredMethod2.setAccessible(true);
                return new C12312(declaredMethod2, intValue);
            } catch (Exception e2) {
                try {
                    Method declaredMethod3 = ObjectInputStream.class.getDeclaredMethod("newInstance", new Class[]{Class.class, Class.class});
                    declaredMethod3.setAccessible(true);
                    return new C12323(declaredMethod3);
                } catch (Exception e3) {
                    return new C12334();
                }
            }
        }
    }

    public abstract <T> T zzf(Class<T> cls) throws Exception;
}
