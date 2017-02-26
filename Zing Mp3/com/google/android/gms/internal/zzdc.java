package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@zzji
public abstract class zzdc {
    @Nullable
    private static MessageDigest zzaxf;
    protected Object zzako;

    static {
        zzaxf = null;
    }

    public zzdc() {
        this.zzako = new Object();
    }

    abstract byte[] zzag(String str);

    @Nullable
    protected MessageDigest zzjr() {
        MessageDigest messageDigest;
        synchronized (this.zzako) {
            if (zzaxf != null) {
                messageDigest = zzaxf;
            } else {
                for (int i = 0; i < 2; i++) {
                    try {
                        zzaxf = MessageDigest.getInstance("MD5");
                    } catch (NoSuchAlgorithmException e) {
                    }
                }
                messageDigest = zzaxf;
            }
        }
        return messageDigest;
    }
}
