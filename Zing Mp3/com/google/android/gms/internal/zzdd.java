package com.google.android.gms.internal;

import android.util.Base64OutputStream;
import com.google.android.exoplayer.util.MpegAudioHeader;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.mp3download.zingmp3.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.PriorityQueue;

@zzji
public class zzdd {
    private final int zzaxg;
    private final int zzaxh;
    private final int zzaxi;
    private final zzdc zzaxj;

    /* renamed from: com.google.android.gms.internal.zzdd.1 */
    class C12831 implements Comparator<com.google.android.gms.internal.zzdg.zza> {
        final /* synthetic */ zzdd zzaxk;

        C12831(zzdd com_google_android_gms_internal_zzdd) {
            this.zzaxk = com_google_android_gms_internal_zzdd;
        }

        public /* synthetic */ int compare(Object obj, Object obj2) {
            return zza((com.google.android.gms.internal.zzdg.zza) obj, (com.google.android.gms.internal.zzdg.zza) obj2);
        }

        public int zza(com.google.android.gms.internal.zzdg.zza com_google_android_gms_internal_zzdg_zza, com.google.android.gms.internal.zzdg.zza com_google_android_gms_internal_zzdg_zza2) {
            int i = com_google_android_gms_internal_zzdg_zza.zzaxp - com_google_android_gms_internal_zzdg_zza2.zzaxp;
            return i != 0 ? i : (int) (com_google_android_gms_internal_zzdg_zza.value - com_google_android_gms_internal_zzdg_zza2.value);
        }
    }

    static class zza {
        ByteArrayOutputStream zzaxl;
        Base64OutputStream zzaxm;

        public zza() {
            this.zzaxl = new ByteArrayOutputStream(MpegAudioHeader.MAX_FRAME_SIZE_BYTES);
            this.zzaxm = new Base64OutputStream(this.zzaxl, 10);
        }

        public String toString() {
            String byteArrayOutputStream;
            try {
                this.zzaxm.close();
            } catch (Throwable e) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e);
            }
            try {
                this.zzaxl.close();
                byteArrayOutputStream = this.zzaxl.toString();
            } catch (Throwable e2) {
                zzb.zzb("HashManager: Unable to convert to Base64.", e2);
                byteArrayOutputStream = BuildConfig.FLAVOR;
            } finally {
                this.zzaxl = null;
                this.zzaxm = null;
            }
            return byteArrayOutputStream;
        }

        public void write(byte[] bArr) throws IOException {
            this.zzaxm.write(bArr);
        }
    }

    public zzdd(int i) {
        this.zzaxj = new zzdf();
        this.zzaxh = i;
        this.zzaxg = 6;
        this.zzaxi = 0;
    }

    public String zza(ArrayList<String> arrayList) {
        StringBuffer stringBuffer = new StringBuffer();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            stringBuffer.append(((String) it.next()).toLowerCase(Locale.US));
            stringBuffer.append('\n');
        }
        return zzah(stringBuffer.toString());
    }

    String zzah(String str) {
        String[] split = str.split("\n");
        if (split.length == 0) {
            return BuildConfig.FLAVOR;
        }
        zza zzjs = zzjs();
        PriorityQueue priorityQueue = new PriorityQueue(this.zzaxh, new C12831(this));
        for (String zzaj : split) {
            String[] zzaj2 = zzde.zzaj(zzaj);
            if (zzaj2.length != 0) {
                zzdg.zza(zzaj2, this.zzaxh, this.zzaxg, priorityQueue);
            }
        }
        Iterator it = priorityQueue.iterator();
        while (it.hasNext()) {
            try {
                zzjs.write(this.zzaxj.zzag(((com.google.android.gms.internal.zzdg.zza) it.next()).zzaxo));
            } catch (Throwable e) {
                zzb.zzb("Error while writing hash to byteStream", e);
            }
        }
        return zzjs.toString();
    }

    zza zzjs() {
        return new zza();
    }
}
