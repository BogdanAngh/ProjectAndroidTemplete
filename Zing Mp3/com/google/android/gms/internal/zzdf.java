package com.google.android.gms.internal;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;
import android.support.v4.view.ViewCompat;
import com.google.android.exoplayer.util.NalUnitUtil;
import java.security.MessageDigest;

@zzji
public class zzdf extends zzdc {
    private MessageDigest zzaxn;

    byte[] zza(String[] strArr) {
        int i = 0;
        if (strArr.length == 1) {
            return zzde.zzp(zzde.zzai(strArr[0]));
        }
        if (strArr.length < 5) {
            byte[] bArr = new byte[(strArr.length * 2)];
            for (int i2 = 0; i2 < strArr.length; i2++) {
                byte[] zzs = zzs(zzde.zzai(strArr[i2]));
                bArr[i2 * 2] = zzs[0];
                bArr[(i2 * 2) + 1] = zzs[1];
            }
            return bArr;
        }
        byte[] bArr2 = new byte[strArr.length];
        while (i < strArr.length) {
            bArr2[i] = zzr(zzde.zzai(strArr[i]));
            i++;
        }
        return bArr2;
    }

    public byte[] zzag(String str) {
        byte[] bArr;
        int i = 4;
        byte[] zza = zza(str.split(" "));
        this.zzaxn = zzjr();
        synchronized (this.zzako) {
            if (this.zzaxn == null) {
                bArr = new byte[0];
            } else {
                this.zzaxn.reset();
                this.zzaxn.update(zza);
                Object digest = this.zzaxn.digest();
                if (digest.length <= 4) {
                    i = digest.length;
                }
                bArr = new byte[i];
                System.arraycopy(digest, 0, bArr, 0, bArr.length);
            }
        }
        return bArr;
    }

    byte zzr(int i) {
        return (byte) ((((i & NalUnitUtil.EXTENDED_SAR) ^ ((MotionEventCompat.ACTION_POINTER_INDEX_MASK & i) >> 8)) ^ ((16711680 & i) >> 16)) ^ ((ViewCompat.MEASURED_STATE_MASK & i) >> 24));
    }

    byte[] zzs(int i) {
        int i2 = (SupportMenu.USER_MASK & i) ^ ((SupportMenu.CATEGORY_MASK & i) >> 16);
        return new byte[]{(byte) i2, (byte) (i2 >> 8)};
    }
}
