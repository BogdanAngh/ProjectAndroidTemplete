package com.google.android.gms.internal;

import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.io.IOException;

public final class zzrc extends zzrh<zzrc> {
    public zza[] zzaVl;

    public static final class zza extends zzrh<zza> {
        private static volatile zza[] zzaVm;
        public String name;
        public zza zzaVn;

        public static final class zza extends zzrh<zza> {
            private static volatile zza[] zzaVo;
            public int type;
            public zza zzaVp;

            public static final class zza extends zzrh<zza> {
                public String[] zzaVA;
                public long[] zzaVB;
                public float[] zzaVC;
                public long zzaVD;
                public byte[] zzaVq;
                public String zzaVr;
                public double zzaVs;
                public float zzaVt;
                public long zzaVu;
                public int zzaVv;
                public int zzaVw;
                public boolean zzaVx;
                public zza[] zzaVy;
                public zza[] zzaVz;

                public zza() {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r0 = this;
                    r0.<init>();
                    r0.zzBp();
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.<init>():void");
                }

                public boolean equals(java.lang.Object r7) {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r6 = this;
                    r0 = 0;
                    if (r7 != r6) goto L_0x0005;
                L_0x0003:
                    r0 = 1;
                L_0x0004:
                    return r0;
                L_0x0005:
                    r1 = r7 instanceof com.google.android.gms.internal.zzrc.zza.zza.zza;
                    if (r1 == 0) goto L_0x0004;
                L_0x0009:
                    r7 = (com.google.android.gms.internal.zzrc.zza.zza.zza) r7;
                    r1 = r6.zzaVq;
                    r2 = r7.zzaVq;
                    r1 = java.util.Arrays.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0015:
                    r1 = r6.zzaVr;
                    if (r1 != 0) goto L_0x0095;
                L_0x0019:
                    r1 = r7.zzaVr;
                    if (r1 != 0) goto L_0x0004;
                L_0x001d:
                    r2 = r6.zzaVs;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = r7.zzaVs;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x002d:
                    r1 = r6.zzaVt;
                    r1 = java.lang.Float.floatToIntBits(r1);
                    r2 = r7.zzaVt;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    if (r1 != r2) goto L_0x0004;
                L_0x003b:
                    r2 = r6.zzaVu;
                    r4 = r7.zzaVu;
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x0043:
                    r1 = r6.zzaVv;
                    r2 = r7.zzaVv;
                    if (r1 != r2) goto L_0x0004;
                L_0x0049:
                    r1 = r6.zzaVw;
                    r2 = r7.zzaVw;
                    if (r1 != r2) goto L_0x0004;
                L_0x004f:
                    r1 = r6.zzaVx;
                    r2 = r7.zzaVx;
                    if (r1 != r2) goto L_0x0004;
                L_0x0055:
                    r1 = r6.zzaVy;
                    r2 = r7.zzaVy;
                    r1 = com.google.android.gms.internal.zzrl.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x005f:
                    r1 = r6.zzaVz;
                    r2 = r7.zzaVz;
                    r1 = com.google.android.gms.internal.zzrl.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0069:
                    r1 = r6.zzaVA;
                    r2 = r7.zzaVA;
                    r1 = com.google.android.gms.internal.zzrl.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0073:
                    r1 = r6.zzaVB;
                    r2 = r7.zzaVB;
                    r1 = com.google.android.gms.internal.zzrl.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x007d:
                    r1 = r6.zzaVC;
                    r2 = r7.zzaVC;
                    r1 = com.google.android.gms.internal.zzrl.equals(r1, r2);
                    if (r1 == 0) goto L_0x0004;
                L_0x0087:
                    r2 = r6.zzaVD;
                    r4 = r7.zzaVD;
                    r1 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r1 != 0) goto L_0x0004;
                L_0x008f:
                    r0 = r6.zza(r7);
                    goto L_0x0004;
                L_0x0095:
                    r1 = r6.zzaVr;
                    r2 = r7.zzaVr;
                    r1 = r1.equals(r2);
                    if (r1 != 0) goto L_0x001d;
                L_0x009f:
                    goto L_0x0004;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.equals(java.lang.Object):boolean");
                }

                public int hashCode() {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r7 = this;
                    r6 = 32;
                    r0 = r7.zzaVq;
                    r0 = java.util.Arrays.hashCode(r0);
                    r0 = r0 + 527;
                    r1 = r0 * 31;
                    r0 = r7.zzaVr;
                    if (r0 != 0) goto L_0x0084;
                L_0x0010:
                    r0 = 0;
                L_0x0011:
                    r0 = r0 + r1;
                    r2 = r7.zzaVs;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r0 = r0 * 31;
                    r4 = r2 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVt;
                    r1 = java.lang.Float.floatToIntBits(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r2 = r7.zzaVu;
                    r4 = r7.zzaVu;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVv;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVw;
                    r0 = r0 + r1;
                    r1 = r0 * 31;
                    r0 = r7.zzaVx;
                    if (r0 == 0) goto L_0x008b;
                L_0x0042:
                    r0 = 1231; // 0x4cf float:1.725E-42 double:6.08E-321;
                L_0x0044:
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVy;
                    r1 = com.google.android.gms.internal.zzrl.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVz;
                    r1 = com.google.android.gms.internal.zzrl.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVA;
                    r1 = com.google.android.gms.internal.zzrl.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVB;
                    r1 = com.google.android.gms.internal.zzrl.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzaVC;
                    r1 = com.google.android.gms.internal.zzrl.hashCode(r1);
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r2 = r7.zzaVD;
                    r4 = r7.zzaVD;
                    r4 = r4 >>> r6;
                    r2 = r2 ^ r4;
                    r1 = (int) r2;
                    r0 = r0 + r1;
                    r0 = r0 * 31;
                    r1 = r7.zzBI();
                    r0 = r0 + r1;
                    return r0;
                L_0x0084:
                    r0 = r7.zzaVr;
                    r0 = r0.hashCode();
                    goto L_0x0011;
                L_0x008b:
                    r0 = 1237; // 0x4d5 float:1.733E-42 double:6.11E-321;
                    goto L_0x0044;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.hashCode():int");
                }

                protected int zzB() {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = super.zzB();
                    r2 = r8.zzaVq;
                    r3 = com.google.android.gms.internal.zzrq.zzaWo;
                    r2 = java.util.Arrays.equals(r2, r3);
                    if (r2 != 0) goto L_0x0019;
                L_0x0011:
                    r2 = 1;
                    r3 = r8.zzaVq;
                    r2 = com.google.android.gms.internal.zzrg.zzb(r2, r3);
                    r0 = r0 + r2;
                L_0x0019:
                    r2 = r8.zzaVr;
                    r3 = "";
                    r2 = r2.equals(r3);
                    if (r2 != 0) goto L_0x002b;
                L_0x0023:
                    r2 = 2;
                    r3 = r8.zzaVr;
                    r2 = com.google.android.gms.internal.zzrg.zzk(r2, r3);
                    r0 = r0 + r2;
                L_0x002b:
                    r2 = r8.zzaVs;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r2 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r2 == 0) goto L_0x0043;
                L_0x003b:
                    r2 = 3;
                    r4 = r8.zzaVs;
                    r2 = com.google.android.gms.internal.zzrg.zzb(r2, r4);
                    r0 = r0 + r2;
                L_0x0043:
                    r2 = r8.zzaVt;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    r3 = 0;
                    r3 = java.lang.Float.floatToIntBits(r3);
                    if (r2 == r3) goto L_0x0058;
                L_0x0050:
                    r2 = 4;
                    r3 = r8.zzaVt;
                    r2 = com.google.android.gms.internal.zzrg.zzc(r2, r3);
                    r0 = r0 + r2;
                L_0x0058:
                    r2 = r8.zzaVu;
                    r2 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r2 == 0) goto L_0x0066;
                L_0x005e:
                    r2 = 5;
                    r4 = r8.zzaVu;
                    r2 = com.google.android.gms.internal.zzrg.zzd(r2, r4);
                    r0 = r0 + r2;
                L_0x0066:
                    r2 = r8.zzaVv;
                    if (r2 == 0) goto L_0x0072;
                L_0x006a:
                    r2 = 6;
                    r3 = r8.zzaVv;
                    r2 = com.google.android.gms.internal.zzrg.zzA(r2, r3);
                    r0 = r0 + r2;
                L_0x0072:
                    r2 = r8.zzaVw;
                    if (r2 == 0) goto L_0x007e;
                L_0x0076:
                    r2 = 7;
                    r3 = r8.zzaVw;
                    r2 = com.google.android.gms.internal.zzrg.zzB(r2, r3);
                    r0 = r0 + r2;
                L_0x007e:
                    r2 = r8.zzaVx;
                    if (r2 == 0) goto L_0x008b;
                L_0x0082:
                    r2 = 8;
                    r3 = r8.zzaVx;
                    r2 = com.google.android.gms.internal.zzrg.zzc(r2, r3);
                    r0 = r0 + r2;
                L_0x008b:
                    r2 = r8.zzaVy;
                    if (r2 == 0) goto L_0x00ac;
                L_0x008f:
                    r2 = r8.zzaVy;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00ac;
                L_0x0094:
                    r2 = r0;
                    r0 = r1;
                L_0x0096:
                    r3 = r8.zzaVy;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00ab;
                L_0x009b:
                    r3 = r8.zzaVy;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00a8;
                L_0x00a1:
                    r4 = 9;
                    r3 = com.google.android.gms.internal.zzrg.zzc(r4, r3);
                    r2 = r2 + r3;
                L_0x00a8:
                    r0 = r0 + 1;
                    goto L_0x0096;
                L_0x00ab:
                    r0 = r2;
                L_0x00ac:
                    r2 = r8.zzaVz;
                    if (r2 == 0) goto L_0x00cd;
                L_0x00b0:
                    r2 = r8.zzaVz;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00cd;
                L_0x00b5:
                    r2 = r0;
                    r0 = r1;
                L_0x00b7:
                    r3 = r8.zzaVz;
                    r3 = r3.length;
                    if (r0 >= r3) goto L_0x00cc;
                L_0x00bc:
                    r3 = r8.zzaVz;
                    r3 = r3[r0];
                    if (r3 == 0) goto L_0x00c9;
                L_0x00c2:
                    r4 = 10;
                    r3 = com.google.android.gms.internal.zzrg.zzc(r4, r3);
                    r2 = r2 + r3;
                L_0x00c9:
                    r0 = r0 + 1;
                    goto L_0x00b7;
                L_0x00cc:
                    r0 = r2;
                L_0x00cd:
                    r2 = r8.zzaVA;
                    if (r2 == 0) goto L_0x00f2;
                L_0x00d1:
                    r2 = r8.zzaVA;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x00f2;
                L_0x00d6:
                    r2 = r1;
                    r3 = r1;
                    r4 = r1;
                L_0x00d9:
                    r5 = r8.zzaVA;
                    r5 = r5.length;
                    if (r2 >= r5) goto L_0x00ee;
                L_0x00de:
                    r5 = r8.zzaVA;
                    r5 = r5[r2];
                    if (r5 == 0) goto L_0x00eb;
                L_0x00e4:
                    r4 = r4 + 1;
                    r5 = com.google.android.gms.internal.zzrg.zzfj(r5);
                    r3 = r3 + r5;
                L_0x00eb:
                    r2 = r2 + 1;
                    goto L_0x00d9;
                L_0x00ee:
                    r0 = r0 + r3;
                    r2 = r4 * 1;
                    r0 = r0 + r2;
                L_0x00f2:
                    r2 = r8.zzaVB;
                    if (r2 == 0) goto L_0x0114;
                L_0x00f6:
                    r2 = r8.zzaVB;
                    r2 = r2.length;
                    if (r2 <= 0) goto L_0x0114;
                L_0x00fb:
                    r2 = r1;
                L_0x00fc:
                    r3 = r8.zzaVB;
                    r3 = r3.length;
                    if (r1 >= r3) goto L_0x010d;
                L_0x0101:
                    r3 = r8.zzaVB;
                    r4 = r3[r1];
                    r3 = com.google.android.gms.internal.zzrg.zzY(r4);
                    r2 = r2 + r3;
                    r1 = r1 + 1;
                    goto L_0x00fc;
                L_0x010d:
                    r0 = r0 + r2;
                    r1 = r8.zzaVB;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0114:
                    r2 = r8.zzaVD;
                    r1 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r1 == 0) goto L_0x0123;
                L_0x011a:
                    r1 = 13;
                    r2 = r8.zzaVD;
                    r1 = com.google.android.gms.internal.zzrg.zzd(r1, r2);
                    r0 = r0 + r1;
                L_0x0123:
                    r1 = r8.zzaVC;
                    if (r1 == 0) goto L_0x0138;
                L_0x0127:
                    r1 = r8.zzaVC;
                    r1 = r1.length;
                    if (r1 <= 0) goto L_0x0138;
                L_0x012c:
                    r1 = r8.zzaVC;
                    r1 = r1.length;
                    r1 = r1 * 4;
                    r0 = r0 + r1;
                    r1 = r8.zzaVC;
                    r1 = r1.length;
                    r1 = r1 * 1;
                    r0 = r0 + r1;
                L_0x0138:
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.zzB():int");
                }

                public com.google.android.gms.internal.zzrc.zza.zza.zza zzBp() {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r6 = this;
                    r4 = 0;
                    r2 = 0;
                    r0 = com.google.android.gms.internal.zzrq.zzaWo;
                    r6.zzaVq = r0;
                    r0 = "";
                    r6.zzaVr = r0;
                    r0 = 0;
                    r6.zzaVs = r0;
                    r0 = 0;
                    r6.zzaVt = r0;
                    r6.zzaVu = r4;
                    r6.zzaVv = r2;
                    r6.zzaVw = r2;
                    r6.zzaVx = r2;
                    r0 = com.google.android.gms.internal.zzrc.zza.zzBl();
                    r6.zzaVy = r0;
                    r0 = com.google.android.gms.internal.zzrc.zza.zza.zzBn();
                    r6.zzaVz = r0;
                    r0 = com.google.android.gms.internal.zzrq.zzaWm;
                    r6.zzaVA = r0;
                    r0 = com.google.android.gms.internal.zzrq.zzaWi;
                    r6.zzaVB = r0;
                    r0 = com.google.android.gms.internal.zzrq.zzaWj;
                    r6.zzaVC = r0;
                    r6.zzaVD = r4;
                    r0 = 0;
                    r6.zzaVU = r0;
                    r0 = -1;
                    r6.zzaWf = r0;
                    return r6;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.zzBp():com.google.android.gms.internal.zzrc$zza$zza$zza");
                }

                public void zza(com.google.android.gms.internal.zzrg r9) throws java.io.IOException {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r8 = this;
                    r6 = 0;
                    r1 = 0;
                    r0 = r8.zzaVq;
                    r2 = com.google.android.gms.internal.zzrq.zzaWo;
                    r0 = java.util.Arrays.equals(r0, r2);
                    if (r0 != 0) goto L_0x0013;
                L_0x000d:
                    r0 = 1;
                    r2 = r8.zzaVq;
                    r9.zza(r0, r2);
                L_0x0013:
                    r0 = r8.zzaVr;
                    r2 = "";
                    r0 = r0.equals(r2);
                    if (r0 != 0) goto L_0x0023;
                L_0x001d:
                    r0 = 2;
                    r2 = r8.zzaVr;
                    r9.zzb(r0, r2);
                L_0x0023:
                    r2 = r8.zzaVs;
                    r2 = java.lang.Double.doubleToLongBits(r2);
                    r4 = 0;
                    r4 = java.lang.Double.doubleToLongBits(r4);
                    r0 = (r2 > r4 ? 1 : (r2 == r4 ? 0 : -1));
                    if (r0 == 0) goto L_0x0039;
                L_0x0033:
                    r0 = 3;
                    r2 = r8.zzaVs;
                    r9.zza(r0, r2);
                L_0x0039:
                    r0 = r8.zzaVt;
                    r0 = java.lang.Float.floatToIntBits(r0);
                    r2 = 0;
                    r2 = java.lang.Float.floatToIntBits(r2);
                    if (r0 == r2) goto L_0x004c;
                L_0x0046:
                    r0 = 4;
                    r2 = r8.zzaVt;
                    r9.zzb(r0, r2);
                L_0x004c:
                    r2 = r8.zzaVu;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x0058;
                L_0x0052:
                    r0 = 5;
                    r2 = r8.zzaVu;
                    r9.zzb(r0, r2);
                L_0x0058:
                    r0 = r8.zzaVv;
                    if (r0 == 0) goto L_0x0062;
                L_0x005c:
                    r0 = 6;
                    r2 = r8.zzaVv;
                    r9.zzy(r0, r2);
                L_0x0062:
                    r0 = r8.zzaVw;
                    if (r0 == 0) goto L_0x006c;
                L_0x0066:
                    r0 = 7;
                    r2 = r8.zzaVw;
                    r9.zzz(r0, r2);
                L_0x006c:
                    r0 = r8.zzaVx;
                    if (r0 == 0) goto L_0x0077;
                L_0x0070:
                    r0 = 8;
                    r2 = r8.zzaVx;
                    r9.zzb(r0, r2);
                L_0x0077:
                    r0 = r8.zzaVy;
                    if (r0 == 0) goto L_0x0094;
                L_0x007b:
                    r0 = r8.zzaVy;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0094;
                L_0x0080:
                    r0 = r1;
                L_0x0081:
                    r2 = r8.zzaVy;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x0094;
                L_0x0086:
                    r2 = r8.zzaVy;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x0091;
                L_0x008c:
                    r3 = 9;
                    r9.zza(r3, r2);
                L_0x0091:
                    r0 = r0 + 1;
                    goto L_0x0081;
                L_0x0094:
                    r0 = r8.zzaVz;
                    if (r0 == 0) goto L_0x00b1;
                L_0x0098:
                    r0 = r8.zzaVz;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00b1;
                L_0x009d:
                    r0 = r1;
                L_0x009e:
                    r2 = r8.zzaVz;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00b1;
                L_0x00a3:
                    r2 = r8.zzaVz;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00ae;
                L_0x00a9:
                    r3 = 10;
                    r9.zza(r3, r2);
                L_0x00ae:
                    r0 = r0 + 1;
                    goto L_0x009e;
                L_0x00b1:
                    r0 = r8.zzaVA;
                    if (r0 == 0) goto L_0x00ce;
                L_0x00b5:
                    r0 = r8.zzaVA;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00ce;
                L_0x00ba:
                    r0 = r1;
                L_0x00bb:
                    r2 = r8.zzaVA;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00ce;
                L_0x00c0:
                    r2 = r8.zzaVA;
                    r2 = r2[r0];
                    if (r2 == 0) goto L_0x00cb;
                L_0x00c6:
                    r3 = 11;
                    r9.zzb(r3, r2);
                L_0x00cb:
                    r0 = r0 + 1;
                    goto L_0x00bb;
                L_0x00ce:
                    r0 = r8.zzaVB;
                    if (r0 == 0) goto L_0x00e9;
                L_0x00d2:
                    r0 = r8.zzaVB;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x00e9;
                L_0x00d7:
                    r0 = r1;
                L_0x00d8:
                    r2 = r8.zzaVB;
                    r2 = r2.length;
                    if (r0 >= r2) goto L_0x00e9;
                L_0x00dd:
                    r2 = 12;
                    r3 = r8.zzaVB;
                    r4 = r3[r0];
                    r9.zzb(r2, r4);
                    r0 = r0 + 1;
                    goto L_0x00d8;
                L_0x00e9:
                    r2 = r8.zzaVD;
                    r0 = (r2 > r6 ? 1 : (r2 == r6 ? 0 : -1));
                    if (r0 == 0) goto L_0x00f6;
                L_0x00ef:
                    r0 = 13;
                    r2 = r8.zzaVD;
                    r9.zzb(r0, r2);
                L_0x00f6:
                    r0 = r8.zzaVC;
                    if (r0 == 0) goto L_0x0110;
                L_0x00fa:
                    r0 = r8.zzaVC;
                    r0 = r0.length;
                    if (r0 <= 0) goto L_0x0110;
                L_0x00ff:
                    r0 = r8.zzaVC;
                    r0 = r0.length;
                    if (r1 >= r0) goto L_0x0110;
                L_0x0104:
                    r0 = 14;
                    r2 = r8.zzaVC;
                    r2 = r2[r1];
                    r9.zzb(r0, r2);
                    r1 = r1 + 1;
                    goto L_0x00ff;
                L_0x0110:
                    super.zza(r9);
                    return;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.zza(com.google.android.gms.internal.zzrg):void");
                }

                public /* synthetic */ com.google.android.gms.internal.zzrn zzb(com.google.android.gms.internal.zzrf r2) throws java.io.IOException {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r1 = this;
                    r0 = r1.zzy(r2);
                    return r0;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.zzb(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrn");
                }

                public com.google.android.gms.internal.zzrc.zza.zza.zza zzy(com.google.android.gms.internal.zzrf r7) throws java.io.IOException {
                    /* JADX: method processing error */
/*
                    Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                    /*
                    r6 = this;
                    r1 = 0;
                L_0x0001:
                    r0 = r7.zzBr();
                    switch(r0) {
                        case 0: goto L_0x000e;
                        case 10: goto L_0x000f;
                        case 18: goto L_0x0016;
                        case 25: goto L_0x001d;
                        case 37: goto L_0x0024;
                        case 40: goto L_0x002b;
                        case 48: goto L_0x0032;
                        case 56: goto L_0x0039;
                        case 64: goto L_0x0040;
                        case 74: goto L_0x0047;
                        case 82: goto L_0x0087;
                        case 90: goto L_0x00c7;
                        case 96: goto L_0x00fb;
                        case 98: goto L_0x012f;
                        case 104: goto L_0x0171;
                        case 114: goto L_0x01ad;
                        case 117: goto L_0x0179;
                        default: goto L_0x0008;
                    };
                L_0x0008:
                    r0 = r6.zza(r7, r0);
                    if (r0 != 0) goto L_0x0001;
                L_0x000e:
                    return r6;
                L_0x000f:
                    r0 = r7.readBytes();
                    r6.zzaVq = r0;
                    goto L_0x0001;
                L_0x0016:
                    r0 = r7.readString();
                    r6.zzaVr = r0;
                    goto L_0x0001;
                L_0x001d:
                    r2 = r7.readDouble();
                    r6.zzaVs = r2;
                    goto L_0x0001;
                L_0x0024:
                    r0 = r7.readFloat();
                    r6.zzaVt = r0;
                    goto L_0x0001;
                L_0x002b:
                    r2 = r7.zzBt();
                    r6.zzaVu = r2;
                    goto L_0x0001;
                L_0x0032:
                    r0 = r7.zzBu();
                    r6.zzaVv = r0;
                    goto L_0x0001;
                L_0x0039:
                    r0 = r7.zzBw();
                    r6.zzaVw = r0;
                    goto L_0x0001;
                L_0x0040:
                    r0 = r7.zzBv();
                    r6.zzaVx = r0;
                    goto L_0x0001;
                L_0x0047:
                    r0 = 74;
                    r2 = com.google.android.gms.internal.zzrq.zzb(r7, r0);
                    r0 = r6.zzaVy;
                    if (r0 != 0) goto L_0x0073;
                L_0x0051:
                    r0 = r1;
                L_0x0052:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.zzrc.zza[r2];
                    if (r0 == 0) goto L_0x005c;
                L_0x0057:
                    r3 = r6.zzaVy;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x005c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0077;
                L_0x0061:
                    r3 = new com.google.android.gms.internal.zzrc$zza;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.zza(r3);
                    r7.zzBr();
                    r0 = r0 + 1;
                    goto L_0x005c;
                L_0x0073:
                    r0 = r6.zzaVy;
                    r0 = r0.length;
                    goto L_0x0052;
                L_0x0077:
                    r3 = new com.google.android.gms.internal.zzrc$zza;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.zza(r0);
                    r6.zzaVy = r2;
                    goto L_0x0001;
                L_0x0087:
                    r0 = 82;
                    r2 = com.google.android.gms.internal.zzrq.zzb(r7, r0);
                    r0 = r6.zzaVz;
                    if (r0 != 0) goto L_0x00b3;
                L_0x0091:
                    r0 = r1;
                L_0x0092:
                    r2 = r2 + r0;
                    r2 = new com.google.android.gms.internal.zzrc.zza.zza[r2];
                    if (r0 == 0) goto L_0x009c;
                L_0x0097:
                    r3 = r6.zzaVz;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x009c:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00b7;
                L_0x00a1:
                    r3 = new com.google.android.gms.internal.zzrc$zza$zza;
                    r3.<init>();
                    r2[r0] = r3;
                    r3 = r2[r0];
                    r7.zza(r3);
                    r7.zzBr();
                    r0 = r0 + 1;
                    goto L_0x009c;
                L_0x00b3:
                    r0 = r6.zzaVz;
                    r0 = r0.length;
                    goto L_0x0092;
                L_0x00b7:
                    r3 = new com.google.android.gms.internal.zzrc$zza$zza;
                    r3.<init>();
                    r2[r0] = r3;
                    r0 = r2[r0];
                    r7.zza(r0);
                    r6.zzaVz = r2;
                    goto L_0x0001;
                L_0x00c7:
                    r0 = 90;
                    r2 = com.google.android.gms.internal.zzrq.zzb(r7, r0);
                    r0 = r6.zzaVA;
                    if (r0 != 0) goto L_0x00ed;
                L_0x00d1:
                    r0 = r1;
                L_0x00d2:
                    r2 = r2 + r0;
                    r2 = new java.lang.String[r2];
                    if (r0 == 0) goto L_0x00dc;
                L_0x00d7:
                    r3 = r6.zzaVA;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x00dc:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x00f1;
                L_0x00e1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r7.zzBr();
                    r0 = r0 + 1;
                    goto L_0x00dc;
                L_0x00ed:
                    r0 = r6.zzaVA;
                    r0 = r0.length;
                    goto L_0x00d2;
                L_0x00f1:
                    r3 = r7.readString();
                    r2[r0] = r3;
                    r6.zzaVA = r2;
                    goto L_0x0001;
                L_0x00fb:
                    r0 = 96;
                    r2 = com.google.android.gms.internal.zzrq.zzb(r7, r0);
                    r0 = r6.zzaVB;
                    if (r0 != 0) goto L_0x0121;
                L_0x0105:
                    r0 = r1;
                L_0x0106:
                    r2 = r2 + r0;
                    r2 = new long[r2];
                    if (r0 == 0) goto L_0x0110;
                L_0x010b:
                    r3 = r6.zzaVB;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x0110:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x0125;
                L_0x0115:
                    r4 = r7.zzBt();
                    r2[r0] = r4;
                    r7.zzBr();
                    r0 = r0 + 1;
                    goto L_0x0110;
                L_0x0121:
                    r0 = r6.zzaVB;
                    r0 = r0.length;
                    goto L_0x0106;
                L_0x0125:
                    r4 = r7.zzBt();
                    r2[r0] = r4;
                    r6.zzaVB = r2;
                    goto L_0x0001;
                L_0x012f:
                    r0 = r7.zzBy();
                    r3 = r7.zzkC(r0);
                    r2 = r7.getPosition();
                    r0 = r1;
                L_0x013c:
                    r4 = r7.zzBD();
                    if (r4 <= 0) goto L_0x0148;
                L_0x0142:
                    r7.zzBt();
                    r0 = r0 + 1;
                    goto L_0x013c;
                L_0x0148:
                    r7.zzkE(r2);
                    r2 = r6.zzaVB;
                    if (r2 != 0) goto L_0x0166;
                L_0x014f:
                    r2 = r1;
                L_0x0150:
                    r0 = r0 + r2;
                    r0 = new long[r0];
                    if (r2 == 0) goto L_0x015a;
                L_0x0155:
                    r4 = r6.zzaVB;
                    java.lang.System.arraycopy(r4, r1, r0, r1, r2);
                L_0x015a:
                    r4 = r0.length;
                    if (r2 >= r4) goto L_0x016a;
                L_0x015d:
                    r4 = r7.zzBt();
                    r0[r2] = r4;
                    r2 = r2 + 1;
                    goto L_0x015a;
                L_0x0166:
                    r2 = r6.zzaVB;
                    r2 = r2.length;
                    goto L_0x0150;
                L_0x016a:
                    r6.zzaVB = r0;
                    r7.zzkD(r3);
                    goto L_0x0001;
                L_0x0171:
                    r2 = r7.zzBt();
                    r6.zzaVD = r2;
                    goto L_0x0001;
                L_0x0179:
                    r0 = 117; // 0x75 float:1.64E-43 double:5.8E-322;
                    r2 = com.google.android.gms.internal.zzrq.zzb(r7, r0);
                    r0 = r6.zzaVC;
                    if (r0 != 0) goto L_0x019f;
                L_0x0183:
                    r0 = r1;
                L_0x0184:
                    r2 = r2 + r0;
                    r2 = new float[r2];
                    if (r0 == 0) goto L_0x018e;
                L_0x0189:
                    r3 = r6.zzaVC;
                    java.lang.System.arraycopy(r3, r1, r2, r1, r0);
                L_0x018e:
                    r3 = r2.length;
                    r3 = r3 + -1;
                    if (r0 >= r3) goto L_0x01a3;
                L_0x0193:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r7.zzBr();
                    r0 = r0 + 1;
                    goto L_0x018e;
                L_0x019f:
                    r0 = r6.zzaVC;
                    r0 = r0.length;
                    goto L_0x0184;
                L_0x01a3:
                    r3 = r7.readFloat();
                    r2[r0] = r3;
                    r6.zzaVC = r2;
                    goto L_0x0001;
                L_0x01ad:
                    r0 = r7.zzBy();
                    r2 = r7.zzkC(r0);
                    r3 = r0 / 4;
                    r0 = r6.zzaVC;
                    if (r0 != 0) goto L_0x01d2;
                L_0x01bb:
                    r0 = r1;
                L_0x01bc:
                    r3 = r3 + r0;
                    r3 = new float[r3];
                    if (r0 == 0) goto L_0x01c6;
                L_0x01c1:
                    r4 = r6.zzaVC;
                    java.lang.System.arraycopy(r4, r1, r3, r1, r0);
                L_0x01c6:
                    r4 = r3.length;
                    if (r0 >= r4) goto L_0x01d6;
                L_0x01c9:
                    r4 = r7.readFloat();
                    r3[r0] = r4;
                    r0 = r0 + 1;
                    goto L_0x01c6;
                L_0x01d2:
                    r0 = r6.zzaVC;
                    r0 = r0.length;
                    goto L_0x01bc;
                L_0x01d6:
                    r6.zzaVC = r3;
                    r7.zzkD(r2);
                    goto L_0x0001;
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza.zzy(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrc$zza$zza$zza");
                }
            }

            public zza() {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r0 = this;
                r0.<init>();
                r0.zzBo();
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.<init>():void");
            }

            public static com.google.android.gms.internal.zzrc.zza.zza[] zzBn() {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r0 = zzaVo;
                if (r0 != 0) goto L_0x0011;
            L_0x0004:
                r1 = com.google.android.gms.internal.zzrl.zzaWe;
                monitor-enter(r1);
                r0 = zzaVo;	 Catch:{ all -> 0x0014 }
                if (r0 != 0) goto L_0x0010;	 Catch:{ all -> 0x0014 }
            L_0x000b:
                r0 = 0;	 Catch:{ all -> 0x0014 }
                r0 = new com.google.android.gms.internal.zzrc.zza.zza[r0];	 Catch:{ all -> 0x0014 }
                zzaVo = r0;	 Catch:{ all -> 0x0014 }
            L_0x0010:
                monitor-exit(r1);	 Catch:{ all -> 0x0014 }
            L_0x0011:
                r0 = zzaVo;
                return r0;
            L_0x0014:
                r0 = move-exception;
                monitor-exit(r1);	 Catch:{ all -> 0x0014 }
                throw r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zzBn():com.google.android.gms.internal.zzrc$zza$zza[]");
            }

            public boolean equals(java.lang.Object r4) {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r3 = this;
                r0 = 0;
                if (r4 != r3) goto L_0x0005;
            L_0x0003:
                r0 = 1;
            L_0x0004:
                return r0;
            L_0x0005:
                r1 = r4 instanceof com.google.android.gms.internal.zzrc.zza.zza;
                if (r1 == 0) goto L_0x0004;
            L_0x0009:
                r4 = (com.google.android.gms.internal.zzrc.zza.zza) r4;
                r1 = r3.type;
                r2 = r4.type;
                if (r1 != r2) goto L_0x0004;
            L_0x0011:
                r1 = r3.zzaVp;
                if (r1 != 0) goto L_0x001e;
            L_0x0015:
                r1 = r4.zzaVp;
                if (r1 != 0) goto L_0x0004;
            L_0x0019:
                r0 = r3.zza(r4);
                goto L_0x0004;
            L_0x001e:
                r1 = r3.zzaVp;
                r2 = r4.zzaVp;
                r1 = r1.equals(r2);
                if (r1 != 0) goto L_0x0019;
            L_0x0028:
                goto L_0x0004;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.equals(java.lang.Object):boolean");
            }

            public int hashCode() {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r2 = this;
                r0 = r2.type;
                r0 = r0 + 527;
                r1 = r0 * 31;
                r0 = r2.zzaVp;
                if (r0 != 0) goto L_0x0014;
            L_0x000a:
                r0 = 0;
            L_0x000b:
                r0 = r0 + r1;
                r0 = r0 * 31;
                r1 = r2.zzBI();
                r0 = r0 + r1;
                return r0;
            L_0x0014:
                r0 = r2.zzaVp;
                r0 = r0.hashCode();
                goto L_0x000b;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.hashCode():int");
            }

            protected int zzB() {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r3 = this;
                r0 = super.zzB();
                r1 = 1;
                r2 = r3.type;
                r1 = com.google.android.gms.internal.zzrg.zzA(r1, r2);
                r0 = r0 + r1;
                r1 = r3.zzaVp;
                if (r1 == 0) goto L_0x0018;
            L_0x0010:
                r1 = 2;
                r2 = r3.zzaVp;
                r1 = com.google.android.gms.internal.zzrg.zzc(r1, r2);
                r0 = r0 + r1;
            L_0x0018:
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zzB():int");
            }

            public com.google.android.gms.internal.zzrc.zza.zza zzBo() {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r2 = this;
                r1 = 0;
                r0 = 1;
                r2.type = r0;
                r2.zzaVp = r1;
                r2.zzaVU = r1;
                r0 = -1;
                r2.zzaWf = r0;
                return r2;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zzBo():com.google.android.gms.internal.zzrc$zza$zza");
            }

            public void zza(com.google.android.gms.internal.zzrg r3) throws java.io.IOException {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r2 = this;
                r0 = 1;
                r1 = r2.type;
                r3.zzy(r0, r1);
                r0 = r2.zzaVp;
                if (r0 == 0) goto L_0x0010;
            L_0x000a:
                r0 = 2;
                r1 = r2.zzaVp;
                r3.zza(r0, r1);
            L_0x0010:
                super.zza(r3);
                return;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zza(com.google.android.gms.internal.zzrg):void");
            }

            public /* synthetic */ com.google.android.gms.internal.zzrn zzb(com.google.android.gms.internal.zzrf r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r1 = this;
                r0 = r1.zzx(r2);
                return r0;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zzb(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrn");
            }

            public com.google.android.gms.internal.zzrc.zza.zza zzx(com.google.android.gms.internal.zzrf r2) throws java.io.IOException {
                /* JADX: method processing error */
/*
                Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
                /*
                r1 = this;
            L_0x0000:
                r0 = r2.zzBr();
                switch(r0) {
                    case 0: goto L_0x000d;
                    case 8: goto L_0x000e;
                    case 18: goto L_0x0019;
                    default: goto L_0x0007;
                };
            L_0x0007:
                r0 = r1.zza(r2, r0);
                if (r0 != 0) goto L_0x0000;
            L_0x000d:
                return r1;
            L_0x000e:
                r0 = r2.zzBu();
                switch(r0) {
                    case 1: goto L_0x0016;
                    case 2: goto L_0x0016;
                    case 3: goto L_0x0016;
                    case 4: goto L_0x0016;
                    case 5: goto L_0x0016;
                    case 6: goto L_0x0016;
                    case 7: goto L_0x0016;
                    case 8: goto L_0x0016;
                    case 9: goto L_0x0016;
                    case 10: goto L_0x0016;
                    case 11: goto L_0x0016;
                    case 12: goto L_0x0016;
                    case 13: goto L_0x0016;
                    case 14: goto L_0x0016;
                    case 15: goto L_0x0016;
                    default: goto L_0x0015;
                };
            L_0x0015:
                goto L_0x0000;
            L_0x0016:
                r1.type = r0;
                goto L_0x0000;
            L_0x0019:
                r0 = r1.zzaVp;
                if (r0 != 0) goto L_0x0024;
            L_0x001d:
                r0 = new com.google.android.gms.internal.zzrc$zza$zza$zza;
                r0.<init>();
                r1.zzaVp = r0;
            L_0x0024:
                r0 = r1.zzaVp;
                r2.zza(r0);
                goto L_0x0000;
                */
                throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza.zzx(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrc$zza$zza");
            }
        }

        public zza() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r0 = this;
            r0.<init>();
            r0.zzBm();
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.<init>():void");
        }

        public static com.google.android.gms.internal.zzrc.zza[] zzBl() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r0 = zzaVm;
            if (r0 != 0) goto L_0x0011;
        L_0x0004:
            r1 = com.google.android.gms.internal.zzrl.zzaWe;
            monitor-enter(r1);
            r0 = zzaVm;	 Catch:{ all -> 0x0014 }
            if (r0 != 0) goto L_0x0010;	 Catch:{ all -> 0x0014 }
        L_0x000b:
            r0 = 0;	 Catch:{ all -> 0x0014 }
            r0 = new com.google.android.gms.internal.zzrc.zza[r0];	 Catch:{ all -> 0x0014 }
            zzaVm = r0;	 Catch:{ all -> 0x0014 }
        L_0x0010:
            monitor-exit(r1);	 Catch:{ all -> 0x0014 }
        L_0x0011:
            r0 = zzaVm;
            return r0;
        L_0x0014:
            r0 = move-exception;
            monitor-exit(r1);	 Catch:{ all -> 0x0014 }
            throw r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zzBl():com.google.android.gms.internal.zzrc$zza[]");
        }

        public boolean equals(java.lang.Object r4) {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r3 = this;
            r0 = 0;
            if (r4 != r3) goto L_0x0005;
        L_0x0003:
            r0 = 1;
        L_0x0004:
            return r0;
        L_0x0005:
            r1 = r4 instanceof com.google.android.gms.internal.zzrc.zza;
            if (r1 == 0) goto L_0x0004;
        L_0x0009:
            r4 = (com.google.android.gms.internal.zzrc.zza) r4;
            r1 = r3.name;
            if (r1 != 0) goto L_0x0020;
        L_0x000f:
            r1 = r4.name;
            if (r1 != 0) goto L_0x0004;
        L_0x0013:
            r1 = r3.zzaVn;
            if (r1 != 0) goto L_0x002b;
        L_0x0017:
            r1 = r4.zzaVn;
            if (r1 != 0) goto L_0x0004;
        L_0x001b:
            r0 = r3.zza(r4);
            goto L_0x0004;
        L_0x0020:
            r1 = r3.name;
            r2 = r4.name;
            r1 = r1.equals(r2);
            if (r1 != 0) goto L_0x0013;
        L_0x002a:
            goto L_0x0004;
        L_0x002b:
            r1 = r3.zzaVn;
            r2 = r4.zzaVn;
            r1 = r1.equals(r2);
            if (r1 != 0) goto L_0x001b;
        L_0x0035:
            goto L_0x0004;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.equals(java.lang.Object):boolean");
        }

        public int hashCode() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r3 = this;
            r1 = 0;
            r0 = r3.name;
            if (r0 != 0) goto L_0x0017;
        L_0x0005:
            r0 = r1;
        L_0x0006:
            r0 = r0 + 527;
            r0 = r0 * 31;
            r2 = r3.zzaVn;
            if (r2 != 0) goto L_0x001e;
        L_0x000e:
            r0 = r0 + r1;
            r0 = r0 * 31;
            r1 = r3.zzBI();
            r0 = r0 + r1;
            return r0;
        L_0x0017:
            r0 = r3.name;
            r0 = r0.hashCode();
            goto L_0x0006;
        L_0x001e:
            r1 = r3.zzaVn;
            r1 = r1.hashCode();
            goto L_0x000e;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.hashCode():int");
        }

        protected int zzB() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r3 = this;
            r0 = super.zzB();
            r1 = 1;
            r2 = r3.name;
            r1 = com.google.android.gms.internal.zzrg.zzk(r1, r2);
            r0 = r0 + r1;
            r1 = r3.zzaVn;
            if (r1 == 0) goto L_0x0018;
        L_0x0010:
            r1 = 2;
            r2 = r3.zzaVn;
            r1 = com.google.android.gms.internal.zzrg.zzc(r1, r2);
            r0 = r0 + r1;
        L_0x0018:
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zzB():int");
        }

        public com.google.android.gms.internal.zzrc.zza zzBm() {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r2 = this;
            r1 = 0;
            r0 = "";
            r2.name = r0;
            r2.zzaVn = r1;
            r2.zzaVU = r1;
            r0 = -1;
            r2.zzaWf = r0;
            return r2;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zzBm():com.google.android.gms.internal.zzrc$zza");
        }

        public void zza(com.google.android.gms.internal.zzrg r3) throws java.io.IOException {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r2 = this;
            r0 = 1;
            r1 = r2.name;
            r3.zzb(r0, r1);
            r0 = r2.zzaVn;
            if (r0 == 0) goto L_0x0010;
        L_0x000a:
            r0 = 2;
            r1 = r2.zzaVn;
            r3.zza(r0, r1);
        L_0x0010:
            super.zza(r3);
            return;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zza(com.google.android.gms.internal.zzrg):void");
        }

        public /* synthetic */ com.google.android.gms.internal.zzrn zzb(com.google.android.gms.internal.zzrf r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r1 = this;
            r0 = r1.zzw(r2);
            return r0;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zzb(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrn");
        }

        public com.google.android.gms.internal.zzrc.zza zzw(com.google.android.gms.internal.zzrf r2) throws java.io.IOException {
            /* JADX: method processing error */
/*
            Error: jadx.core.utils.exceptions.JadxRuntimeException: SSA rename variables already executed
	at jadx.core.dex.visitors.ssa.SSATransform.renameVariables(SSATransform.java:119)
	at jadx.core.dex.visitors.ssa.SSATransform.process(SSATransform.java:52)
	at jadx.core.dex.visitors.ssa.SSATransform.visit(SSATransform.java:42)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:31)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:17)
	at jadx.core.dex.visitors.DepthTraversal.visit(DepthTraversal.java:14)
	at jadx.core.ProcessClass.process(ProcessClass.java:37)
	at jadx.core.ProcessClass.processDependencies(ProcessClass.java:59)
	at jadx.core.ProcessClass.process(ProcessClass.java:42)
	at jadx.api.JadxDecompiler.processClass(JadxDecompiler.java:281)
	at jadx.api.JavaClass.decompile(JavaClass.java:59)
	at jadx.api.JadxDecompiler$1.run(JadxDecompiler.java:161)
*/
            /*
            r1 = this;
        L_0x0000:
            r0 = r2.zzBr();
            switch(r0) {
                case 0: goto L_0x000d;
                case 10: goto L_0x000e;
                case 18: goto L_0x0015;
                default: goto L_0x0007;
            };
        L_0x0007:
            r0 = r1.zza(r2, r0);
            if (r0 != 0) goto L_0x0000;
        L_0x000d:
            return r1;
        L_0x000e:
            r0 = r2.readString();
            r1.name = r0;
            goto L_0x0000;
        L_0x0015:
            r0 = r1.zzaVn;
            if (r0 != 0) goto L_0x0020;
        L_0x0019:
            r0 = new com.google.android.gms.internal.zzrc$zza$zza;
            r0.<init>();
            r1.zzaVn = r0;
        L_0x0020:
            r0 = r1.zzaVn;
            r2.zza(r0);
            goto L_0x0000;
            */
            throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzrc.zza.zzw(com.google.android.gms.internal.zzrf):com.google.android.gms.internal.zzrc$zza");
        }
    }

    public zzrc() {
        zzBk();
    }

    public static zzrc zzw(byte[] bArr) throws zzrm {
        return (zzrc) zzrn.zza(new zzrc(), bArr);
    }

    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof zzrc)) {
            return false;
        }
        zzrc com_google_android_gms_internal_zzrc = (zzrc) o;
        return zzrl.equals(this.zzaVl, com_google_android_gms_internal_zzrc.zzaVl) ? zza((zzrh) com_google_android_gms_internal_zzrc) : false;
    }

    public int hashCode() {
        return ((zzrl.hashCode(this.zzaVl) + 527) * 31) + zzBI();
    }

    protected int zzB() {
        int zzB = super.zzB();
        if (this.zzaVl != null && this.zzaVl.length > 0) {
            for (zzrn com_google_android_gms_internal_zzrn : this.zzaVl) {
                if (com_google_android_gms_internal_zzrn != null) {
                    zzB += zzrg.zzc(1, com_google_android_gms_internal_zzrn);
                }
            }
        }
        return zzB;
    }

    public zzrc zzBk() {
        this.zzaVl = zza.zzBl();
        this.zzaVU = null;
        this.zzaWf = -1;
        return this;
    }

    public void zza(zzrg com_google_android_gms_internal_zzrg) throws IOException {
        if (this.zzaVl != null && this.zzaVl.length > 0) {
            for (zzrn com_google_android_gms_internal_zzrn : this.zzaVl) {
                if (com_google_android_gms_internal_zzrn != null) {
                    com_google_android_gms_internal_zzrg.zza(1, com_google_android_gms_internal_zzrn);
                }
            }
        }
        super.zza(com_google_android_gms_internal_zzrg);
    }

    public /* synthetic */ zzrn zzb(zzrf com_google_android_gms_internal_zzrf) throws IOException {
        return zzv(com_google_android_gms_internal_zzrf);
    }

    public zzrc zzv(zzrf com_google_android_gms_internal_zzrf) throws IOException {
        while (true) {
            int zzBr = com_google_android_gms_internal_zzrf.zzBr();
            switch (zzBr) {
                case GameHelper.CLIENT_NONE /*0*/:
                    break;
                case Place.TYPE_BEAUTY_SALON /*10*/:
                    int zzb = zzrq.zzb(com_google_android_gms_internal_zzrf, 10);
                    zzBr = this.zzaVl == null ? 0 : this.zzaVl.length;
                    Object obj = new zza[(zzb + zzBr)];
                    if (zzBr != 0) {
                        System.arraycopy(this.zzaVl, 0, obj, 0, zzBr);
                    }
                    while (zzBr < obj.length - 1) {
                        obj[zzBr] = new zza();
                        com_google_android_gms_internal_zzrf.zza(obj[zzBr]);
                        com_google_android_gms_internal_zzrf.zzBr();
                        zzBr++;
                    }
                    obj[zzBr] = new zza();
                    com_google_android_gms_internal_zzrf.zza(obj[zzBr]);
                    this.zzaVl = obj;
                    continue;
                default:
                    if (!zza(com_google_android_gms_internal_zzrf, zzBr)) {
                        break;
                    }
                    continue;
            }
            return this;
        }
    }
}
