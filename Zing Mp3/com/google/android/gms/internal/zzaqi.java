package com.google.android.gms.internal;

import com.mp3download.zingmp3.C1569R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class zzaqi extends zzapk<Object> {
    public static final zzapl bpG;
    private final zzaos boC;

    /* renamed from: com.google.android.gms.internal.zzaqi.1 */
    static class C12381 implements zzapl {
        C12381() {
        }

        public <T> zzapk<T> zza(zzaos com_google_android_gms_internal_zzaos, zzaqo<T> com_google_android_gms_internal_zzaqo_T) {
            return com_google_android_gms_internal_zzaqo_T.bB() == Object.class ? new zzaqi(null) : null;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzaqi.2 */
    static /* synthetic */ class C12392 {
        static final /* synthetic */ int[] bpW;

        static {
            bpW = new int[zzaqq.values().length];
            try {
                bpW[zzaqq.BEGIN_ARRAY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                bpW[zzaqq.BEGIN_OBJECT.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                bpW[zzaqq.STRING.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                bpW[zzaqq.NUMBER.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
            try {
                bpW[zzaqq.BOOLEAN.ordinal()] = 5;
            } catch (NoSuchFieldError e5) {
            }
            try {
                bpW[zzaqq.NULL.ordinal()] = 6;
            } catch (NoSuchFieldError e6) {
            }
        }
    }

    static {
        bpG = new C12381();
    }

    private zzaqi(zzaos com_google_android_gms_internal_zzaos) {
        this.boC = com_google_android_gms_internal_zzaos;
    }

    public void zza(zzaqr com_google_android_gms_internal_zzaqr, Object obj) throws IOException {
        if (obj == null) {
            com_google_android_gms_internal_zzaqr.bA();
            return;
        }
        zzapk zzk = this.boC.zzk(obj.getClass());
        if (zzk instanceof zzaqi) {
            com_google_android_gms_internal_zzaqr.by();
            com_google_android_gms_internal_zzaqr.bz();
            return;
        }
        zzk.zza(com_google_android_gms_internal_zzaqr, obj);
    }

    public Object zzb(zzaqp com_google_android_gms_internal_zzaqp) throws IOException {
        switch (C12392.bpW[com_google_android_gms_internal_zzaqp.bq().ordinal()]) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                List arrayList = new ArrayList();
                com_google_android_gms_internal_zzaqp.beginArray();
                while (com_google_android_gms_internal_zzaqp.hasNext()) {
                    arrayList.add(zzb(com_google_android_gms_internal_zzaqp));
                }
                com_google_android_gms_internal_zzaqp.endArray();
                return arrayList;
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                Map com_google_android_gms_internal_zzapw = new zzapw();
                com_google_android_gms_internal_zzaqp.beginObject();
                while (com_google_android_gms_internal_zzaqp.hasNext()) {
                    com_google_android_gms_internal_zzapw.put(com_google_android_gms_internal_zzaqp.nextName(), zzb(com_google_android_gms_internal_zzaqp));
                }
                com_google_android_gms_internal_zzaqp.endObject();
                return com_google_android_gms_internal_zzapw;
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return com_google_android_gms_internal_zzaqp.nextString();
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                return Double.valueOf(com_google_android_gms_internal_zzaqp.nextDouble());
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return Boolean.valueOf(com_google_android_gms_internal_zzaqp.nextBoolean());
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                com_google_android_gms_internal_zzaqp.nextNull();
                return null;
            default:
                throw new IllegalStateException();
        }
    }
}
