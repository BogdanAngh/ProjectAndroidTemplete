package com.google.android.gms.tagmanager;

import android.net.Uri;
import com.google.android.exoplayer.C0989C;
import com.mp3download.zingmp3.BuildConfig;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

class zzcj {
    private static zzcj aGl;
    private volatile String aDY;
    private volatile zza aGm;
    private volatile String aGn;
    private volatile String aGo;

    enum zza {
        NONE,
        CONTAINER,
        CONTAINER_DEBUG
    }

    zzcj() {
        clear();
    }

    static zzcj zzcfz() {
        zzcj com_google_android_gms_tagmanager_zzcj;
        synchronized (zzcj.class) {
            if (aGl == null) {
                aGl = new zzcj();
            }
            com_google_android_gms_tagmanager_zzcj = aGl;
        }
        return com_google_android_gms_tagmanager_zzcj;
    }

    private String zzpo(String str) {
        return str.split("&")[0].split("=")[1];
    }

    private String zzw(Uri uri) {
        return uri.getQuery().replace("&gtm_debug=x", BuildConfig.FLAVOR);
    }

    void clear() {
        this.aGm = zza.NONE;
        this.aGn = null;
        this.aDY = null;
        this.aGo = null;
    }

    String getContainerId() {
        return this.aDY;
    }

    zza zzcga() {
        return this.aGm;
    }

    String zzcgb() {
        return this.aGn;
    }

    synchronized boolean zzv(Uri uri) {
        boolean z = true;
        synchronized (this) {
            try {
                String decode = URLDecoder.decode(uri.toString(), C0989C.UTF8_NAME);
                String str;
                String valueOf;
                if (decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_auth=\\S+&gtm_preview=\\d+(&gtm_debug=x)?$")) {
                    str = "Container preview url: ";
                    valueOf = String.valueOf(decode);
                    zzbo.m1699v(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
                    if (decode.matches(".*?&gtm_debug=x$")) {
                        this.aGm = zza.CONTAINER_DEBUG;
                    } else {
                        this.aGm = zza.CONTAINER;
                    }
                    this.aGo = zzw(uri);
                    if (this.aGm == zza.CONTAINER || this.aGm == zza.CONTAINER_DEBUG) {
                        decode = String.valueOf("/r?");
                        valueOf = String.valueOf(this.aGo);
                        this.aGn = valueOf.length() != 0 ? decode.concat(valueOf) : new String(decode);
                    }
                    this.aDY = zzpo(this.aGo);
                } else if (!decode.matches("^tagmanager.c.\\S+:\\/\\/preview\\/p\\?id=\\S+&gtm_preview=$")) {
                    str = "Invalid preview uri: ";
                    String valueOf2 = String.valueOf(decode);
                    zzbo.zzdi(valueOf2.length() != 0 ? str.concat(valueOf2) : new String(str));
                    z = false;
                } else if (zzpo(uri.getQuery()).equals(this.aDY)) {
                    decode = "Exit preview mode for container: ";
                    valueOf = String.valueOf(this.aDY);
                    zzbo.m1699v(valueOf.length() != 0 ? decode.concat(valueOf) : new String(decode));
                    this.aGm = zza.NONE;
                    this.aGn = null;
                } else {
                    z = false;
                }
            } catch (UnsupportedEncodingException e) {
                z = false;
            }
        }
        return z;
    }
}
