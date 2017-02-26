package com.google.android.gms.internal;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;

public class zzre {
    private final byte[] zzaVH;
    private int zzaVI;
    private int zzaVJ;

    public zzre(byte[] bArr) {
        int i;
        this.zzaVH = new byte[GL20.GL_DEPTH_BUFFER_BIT];
        for (i = 0; i < GL20.GL_DEPTH_BUFFER_BIT; i++) {
            this.zzaVH[i] = (byte) i;
        }
        i = 0;
        for (int i2 = 0; i2 < GL20.GL_DEPTH_BUFFER_BIT; i2++) {
            i = ((i + this.zzaVH[i2]) + bArr[i2 % bArr.length]) & Keys.F12;
            byte b = this.zzaVH[i2];
            this.zzaVH[i2] = this.zzaVH[i];
            this.zzaVH[i] = b;
        }
        this.zzaVI = 0;
        this.zzaVJ = 0;
    }

    public void zzy(byte[] bArr) {
        int i = this.zzaVI;
        int i2 = this.zzaVJ;
        for (int i3 = 0; i3 < bArr.length; i3++) {
            i = (i + 1) & Keys.F12;
            i2 = (i2 + this.zzaVH[i]) & Keys.F12;
            byte b = this.zzaVH[i];
            this.zzaVH[i] = this.zzaVH[i2];
            this.zzaVH[i2] = b;
            bArr[i3] = (byte) (bArr[i3] ^ this.zzaVH[(this.zzaVH[i] + this.zzaVH[i2]) & Keys.F12]);
        }
        this.zzaVI = i;
        this.zzaVJ = i2;
    }
}
