package com.google.android.gms.internal;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.exoplayer.util.NalUnitUtil;

public class zzaqt {
    private final byte[] brQ;
    private int brR;
    private int brS;

    public zzaqt(byte[] bArr) {
        int i;
        this.brQ = new byte[AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY];
        for (i = 0; i < AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY; i++) {
            this.brQ[i] = (byte) i;
        }
        i = 0;
        for (int i2 = 0; i2 < AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY; i2++) {
            i = ((i + this.brQ[i2]) + bArr[i2 % bArr.length]) & NalUnitUtil.EXTENDED_SAR;
            byte b = this.brQ[i2];
            this.brQ[i2] = this.brQ[i];
            this.brQ[i] = b;
        }
        this.brR = 0;
        this.brS = 0;
    }

    public void zzay(byte[] bArr) {
        int i = this.brR;
        int i2 = this.brS;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & NalUnitUtil.EXTENDED_SAR;
            i2 = (i2 + this.brQ[i]) & NalUnitUtil.EXTENDED_SAR;
            byte b = this.brQ[i];
            this.brQ[i] = this.brQ[i2];
            this.brQ[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.brQ[(this.brQ[i] + this.brQ[i2]) & NalUnitUtil.EXTENDED_SAR]);
        }
        this.brR = i;
        this.brS = i2;
    }
}
