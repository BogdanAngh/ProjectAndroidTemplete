package com.google.android.gms.internal;

import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.common.internal.zzz;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzlh {
    private final String[] zzcwh;
    private final double[] zzcwi;
    private final double[] zzcwj;
    private final int[] zzcwk;
    private int zzcwl;

    public static class zza {
        public final int count;
        public final String name;
        public final double zzcwm;
        public final double zzcwn;
        public final double zzcwo;

        public zza(String str, double d, double d2, double d3, int i) {
            this.name = str;
            this.zzcwn = d;
            this.zzcwm = d2;
            this.zzcwo = d3;
            this.count = i;
        }

        public boolean equals(Object obj) {
            if (!(obj instanceof zza)) {
                return false;
            }
            zza com_google_android_gms_internal_zzlh_zza = (zza) obj;
            return zzz.equal(this.name, com_google_android_gms_internal_zzlh_zza.name) && this.zzcwm == com_google_android_gms_internal_zzlh_zza.zzcwm && this.zzcwn == com_google_android_gms_internal_zzlh_zza.zzcwn && this.count == com_google_android_gms_internal_zzlh_zza.count && Double.compare(this.zzcwo, com_google_android_gms_internal_zzlh_zza.zzcwo) == 0;
        }

        public int hashCode() {
            return zzz.hashCode(this.name, Double.valueOf(this.zzcwm), Double.valueOf(this.zzcwn), Double.valueOf(this.zzcwo), Integer.valueOf(this.count));
        }

        public String toString() {
            return zzz.zzx(this).zzg(ShareConstants.WEB_DIALOG_PARAM_NAME, this.name).zzg("minBound", Double.valueOf(this.zzcwn)).zzg("maxBound", Double.valueOf(this.zzcwm)).zzg("percent", Double.valueOf(this.zzcwo)).zzg("count", Integer.valueOf(this.count)).toString();
        }
    }

    public static class zzb {
        private final List<String> zzcwp;
        private final List<Double> zzcwq;
        private final List<Double> zzcwr;

        public zzb() {
            this.zzcwp = new ArrayList();
            this.zzcwq = new ArrayList();
            this.zzcwr = new ArrayList();
        }

        /* JADX WARNING: inconsistent code. */
        /* Code decompiled incorrectly, please refer to instructions dump. */
        public com.google.android.gms.internal.zzlh.zzb zza(java.lang.String r7, double r8, double r10) {
            /*
            r6 = this;
            r0 = 0;
            r1 = r0;
        L_0x0002:
            r0 = r6.zzcwp;
            r0 = r0.size();
            if (r1 >= r0) goto L_0x0026;
        L_0x000a:
            r0 = r6.zzcwr;
            r0 = r0.get(r1);
            r0 = (java.lang.Double) r0;
            r2 = r0.doubleValue();
            r0 = r6.zzcwq;
            r0 = r0.get(r1);
            r0 = (java.lang.Double) r0;
            r4 = r0.doubleValue();
            r0 = (r8 > r2 ? 1 : (r8 == r2 ? 0 : -1));
            if (r0 >= 0) goto L_0x003e;
        L_0x0026:
            r0 = r6.zzcwp;
            r0.add(r1, r7);
            r0 = r6.zzcwr;
            r2 = java.lang.Double.valueOf(r8);
            r0.add(r1, r2);
            r0 = r6.zzcwq;
            r2 = java.lang.Double.valueOf(r10);
            r0.add(r1, r2);
            return r6;
        L_0x003e:
            r0 = (r2 > r8 ? 1 : (r2 == r8 ? 0 : -1));
            if (r0 != 0) goto L_0x0046;
        L_0x0042:
            r0 = (r10 > r4 ? 1 : (r10 == r4 ? 0 : -1));
            if (r0 < 0) goto L_0x0026;
        L_0x0046:
            r0 = r1 + 1;
            r1 = r0;
            goto L_0x0002;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzlh.zzb.zza(java.lang.String, double, double):com.google.android.gms.internal.zzlh$zzb");
        }

        public zzlh zzwi() {
            return new zzlh();
        }
    }

    private zzlh(zzb com_google_android_gms_internal_zzlh_zzb) {
        int size = com_google_android_gms_internal_zzlh_zzb.zzcwq.size();
        this.zzcwh = (String[]) com_google_android_gms_internal_zzlh_zzb.zzcwp.toArray(new String[size]);
        this.zzcwi = zzn(com_google_android_gms_internal_zzlh_zzb.zzcwq);
        this.zzcwj = zzn(com_google_android_gms_internal_zzlh_zzb.zzcwr);
        this.zzcwk = new int[size];
        this.zzcwl = 0;
    }

    private double[] zzn(List<Double> list) {
        double[] dArr = new double[list.size()];
        for (int i = 0; i < dArr.length; i++) {
            dArr[i] = ((Double) list.get(i)).doubleValue();
        }
        return dArr;
    }

    public List<zza> getBuckets() {
        List<zza> arrayList = new ArrayList(this.zzcwh.length);
        for (int i = 0; i < this.zzcwh.length; i++) {
            arrayList.add(new zza(this.zzcwh[i], this.zzcwj[i], this.zzcwi[i], ((double) this.zzcwk[i]) / ((double) this.zzcwl), this.zzcwk[i]));
        }
        return arrayList;
    }

    public void zza(double d) {
        this.zzcwl++;
        int i = 0;
        while (i < this.zzcwj.length) {
            if (this.zzcwj[i] <= d && d < this.zzcwi[i]) {
                int[] iArr = this.zzcwk;
                iArr[i] = iArr[i] + 1;
            }
            if (d >= this.zzcwj[i]) {
                i++;
            } else {
                return;
            }
        }
    }
}
