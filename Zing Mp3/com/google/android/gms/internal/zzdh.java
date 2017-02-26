package com.google.android.gms.internal;

import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.util.NalUnitUtil;
import java.nio.charset.Charset;
import java.security.MessageDigest;

@zzji
public class zzdh extends zzdc {
    private MessageDigest zzaxn;
    private final int zzaxq;
    private final int zzaxr;

    public zzdh(int i) {
        int i2 = i / 8;
        if (i % 8 > 0) {
            i2++;
        }
        this.zzaxq = i2;
        this.zzaxr = i;
    }

    public byte[] zzag(String str) {
        byte[] bArr;
        synchronized (this.zzako) {
            this.zzaxn = zzjr();
            if (this.zzaxn == null) {
                bArr = new byte[0];
            } else {
                this.zzaxn.reset();
                this.zzaxn.update(str.getBytes(Charset.forName(C0989C.UTF8_NAME)));
                Object digest = this.zzaxn.digest();
                bArr = new byte[(digest.length > this.zzaxq ? this.zzaxq : digest.length)];
                System.arraycopy(digest, 0, bArr, 0, bArr.length);
                if (this.zzaxr % 8 > 0) {
                    int i;
                    long j = 0;
                    for (i = 0; i < bArr.length; i++) {
                        if (i > 0) {
                            j <<= 8;
                        }
                        j += (long) (bArr[i] & NalUnitUtil.EXTENDED_SAR);
                    }
                    j >>>= 8 - (this.zzaxr % 8);
                    for (i = this.zzaxq - 1; i >= 0; i--) {
                        bArr[i] = (byte) ((int) (255 & j));
                        j >>>= 8;
                    }
                }
            }
        }
        return bArr;
    }
}
