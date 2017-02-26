package com.google.android.gms.cast.internal;

import com.google.android.gms.common.api.Api.ClientKey;
import java.nio.charset.Charset;
import java.nio.charset.IllegalCharsetNameException;
import java.nio.charset.UnsupportedCharsetException;

public final class zzk {
    public static final ClientKey<zze> zzNX;
    public static final String zzUQ;
    public static final String zzUR;
    public static final Charset zzUS;

    static {
        zzNX = new ClientKey();
        zzUQ = zzf.zzbE("com.google.cast.receiver");
        zzUR = zzf.zzbE("com.google.cast.tp.connection");
        Charset charset = null;
        try {
            charset = Charset.forName("UTF-8");
        } catch (IllegalCharsetNameException e) {
        } catch (UnsupportedCharsetException e2) {
        }
        zzUS = charset;
    }
}
