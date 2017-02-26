package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.nearby.connection.Connections;
import com.google.example.games.basegameutils.GameHelper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;

public class zzbm {
    private final int zzrL;
    private final int zzrM;
    private final int zzrN;
    private final zzbl zzrO;

    /* renamed from: com.google.android.gms.internal.zzbm.1 */
    class C01921 implements Comparator<String> {
        final /* synthetic */ zzbm zzrP;

        C01921(zzbm com_google_android_gms_internal_zzbm) {
            this.zzrP = com_google_android_gms_internal_zzbm;
        }

        public int compare(String s1, String s2) {
            return s2.length() - s1.length();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzbm.2 */
    class C01932 implements Comparator<com.google.android.gms.internal.zzbp.zza> {
        final /* synthetic */ zzbm zzrP;

        C01932(zzbm com_google_android_gms_internal_zzbm) {
            this.zzrP = com_google_android_gms_internal_zzbm;
        }

        public /* synthetic */ int compare(Object x0, Object x1) {
            return zza((com.google.android.gms.internal.zzbp.zza) x0, (com.google.android.gms.internal.zzbp.zza) x1);
        }

        public int zza(com.google.android.gms.internal.zzbp.zza com_google_android_gms_internal_zzbp_zza, com.google.android.gms.internal.zzbp.zza com_google_android_gms_internal_zzbp_zza2) {
            return (int) (com_google_android_gms_internal_zzbp_zza.value - com_google_android_gms_internal_zzbp_zza2.value);
        }
    }

    static class zza {
        ByteArrayOutputStream zzrQ;
        Base64OutputStream zzrR;

        public zza() {
            this.zzrQ = new ByteArrayOutputStream(Connections.MAX_RELIABLE_MESSAGE_LEN);
            this.zzrR = new Base64OutputStream(this.zzrQ, 10);
        }

        public String toString() {
            String byteArrayOutputStream;
            try {
                this.zzrR.close();
            } catch (Throwable e) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e);
            }
            try {
                this.zzrQ.close();
                byteArrayOutputStream = this.zzrQ.toString();
            } catch (Throwable e2) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e2);
                byteArrayOutputStream = "";
            } finally {
                this.zzrQ = null;
                this.zzrR = null;
            }
            return byteArrayOutputStream;
        }

        public void write(byte[] data) throws IOException {
            this.zzrR.write(data);
        }
    }

    public zzbm(int i) {
        this.zzrO = new zzbo();
        this.zzrM = i;
        this.zzrL = 6;
        this.zzrN = 0;
    }

    private String zzz(String str) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return "";
        }
        zza zzcv = zzcv();
        Arrays.sort(split, new C01921(this));
        int i = 0;
        while (i < split.length && i < this.zzrM) {
            if (split[i].trim().length() != 0) {
                try {
                    zzcv.write(this.zzrO.zzy(split[i]));
                } catch (Throwable e) {
                    zzb.zzb("Error while writing hash to byteStream", e);
                }
            }
            i++;
        }
        return zzcv.toString();
    }

    String zzA(String str) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return "";
        }
        zza zzcv = zzcv();
        PriorityQueue priorityQueue = new PriorityQueue(this.zzrM, new C01932(this));
        for (String zzC : split) {
            String[] zzC2 = zzbn.zzC(zzC);
            if (zzC2.length >= this.zzrL) {
                zzbp.zza(zzC2, this.zzrM, this.zzrL, priorityQueue);
            }
        }
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            try {
                zzcv.write(this.zzrO.zzy(((com.google.android.gms.internal.zzbp.zza) it.next()).zzrT));
            } catch (Throwable e) {
                zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzcv.toString();
    }

    public String zza(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(((String) it.next()).toLowerCase(Locale.US));
            stringBuffer.append('\n');
        }
        switch (this.zzrN) {
            case GameHelper.CLIENT_NONE /*0*/:
                return zzA(stringBuffer.toString());
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return zzz(stringBuffer.toString());
            default:
                return "";
        }
    }

    zza zzcv() {
        return new zza();
    }
}
