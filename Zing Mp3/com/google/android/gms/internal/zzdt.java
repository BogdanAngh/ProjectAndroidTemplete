package com.google.android.gms.internal;

import android.content.Context;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

@zzji
public class zzdt {
    final Context mContext;
    final String zzasx;
    String zzblh;
    BlockingQueue<zzdz> zzblj;
    ExecutorService zzblk;
    LinkedHashMap<String, String> zzbll;
    Map<String, zzdw> zzblm;
    private AtomicBoolean zzbln;
    private File zzblo;

    /* renamed from: com.google.android.gms.internal.zzdt.1 */
    class C12911 implements Runnable {
        final /* synthetic */ zzdt zzblp;

        C12911(zzdt com_google_android_gms_internal_zzdt) {
            this.zzblp = com_google_android_gms_internal_zzdt;
        }

        public void run() {
            this.zzblp.zzlv();
        }
    }

    public zzdt(Context context, String str, String str2, Map<String, String> map) {
        this.zzbll = new LinkedHashMap();
        this.zzblm = new HashMap();
        this.mContext = context;
        this.zzasx = str;
        this.zzblh = str2;
        this.zzbln = new AtomicBoolean(false);
        this.zzbln.set(((Boolean) zzdr.zzbes.get()).booleanValue());
        if (this.zzbln.get()) {
            File externalStorageDirectory = Environment.getExternalStorageDirectory();
            if (externalStorageDirectory != null) {
                this.zzblo = new File(externalStorageDirectory, "sdk_csi_data.txt");
            }
        }
        for (Entry entry : map.entrySet()) {
            this.zzbll.put((String) entry.getKey(), (String) entry.getValue());
        }
        this.zzblj = new ArrayBlockingQueue(30);
        this.zzblk = Executors.newSingleThreadExecutor();
        this.zzblk.execute(new C12911(this));
        this.zzblm.put(NativeProtocol.WEB_DIALOG_ACTION, zzdw.zzblr);
        this.zzblm.put("ad_format", zzdw.zzblr);
        this.zzblm.put("e", zzdw.zzbls);
    }

    private void zzc(@Nullable File file, String str) {
        Throwable e;
        if (file != null) {
            FileOutputStream fileOutputStream;
            try {
                fileOutputStream = new FileOutputStream(file, true);
                try {
                    fileOutputStream.write(str.getBytes());
                    fileOutputStream.write(10);
                    if (fileOutputStream != null) {
                        try {
                            fileOutputStream.close();
                            return;
                        } catch (Throwable e2) {
                            zzb.zzc("CsiReporter: Cannot close file: sdk_csi_data.txt.", e2);
                            return;
                        }
                    }
                    return;
                } catch (IOException e3) {
                    e2 = e3;
                    try {
                        zzb.zzc("CsiReporter: Cannot write to file: sdk_csi_data.txt.", e2);
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                                return;
                            } catch (Throwable e22) {
                                zzb.zzc("CsiReporter: Cannot close file: sdk_csi_data.txt.", e22);
                                return;
                            }
                        }
                        return;
                    } catch (Throwable th) {
                        e22 = th;
                        if (fileOutputStream != null) {
                            try {
                                fileOutputStream.close();
                            } catch (Throwable e4) {
                                zzb.zzc("CsiReporter: Cannot close file: sdk_csi_data.txt.", e4);
                            }
                        }
                        throw e22;
                    }
                }
            } catch (IOException e5) {
                e22 = e5;
                fileOutputStream = null;
                zzb.zzc("CsiReporter: Cannot write to file: sdk_csi_data.txt.", e22);
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                    return;
                }
                return;
            } catch (Throwable th2) {
                e22 = th2;
                fileOutputStream = null;
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
                throw e22;
            }
        }
        zzb.zzdi("CsiReporter: File doesn't exists. Cannot write CSI data to file.");
    }

    private void zzc(Map<String, String> map, String str) {
        String zza = zza(this.zzblh, map, str);
        if (this.zzbln.get()) {
            zzc(this.zzblo, zza);
        } else {
            zzu.zzgm().zzc(this.mContext, this.zzasx, zza);
        }
    }

    private void zzlv() {
        while (true) {
            try {
                zzdz com_google_android_gms_internal_zzdz = (zzdz) this.zzblj.take();
                String zzmb = com_google_android_gms_internal_zzdz.zzmb();
                if (!TextUtils.isEmpty(zzmb)) {
                    zzc(zza(this.zzbll, com_google_android_gms_internal_zzdz.zzmc()), zzmb);
                }
            } catch (Throwable e) {
                zzb.zzc("CsiReporter:reporter interrupted", e);
                return;
            }
        }
    }

    String zza(String str, Map<String, String> map, @NonNull String str2) {
        Builder buildUpon = Uri.parse(str).buildUpon();
        for (Entry entry : map.entrySet()) {
            buildUpon.appendQueryParameter((String) entry.getKey(), (String) entry.getValue());
        }
        StringBuilder stringBuilder = new StringBuilder(buildUpon.build().toString());
        stringBuilder.append("&").append("it").append("=").append(str2);
        return stringBuilder.toString();
    }

    Map<String, String> zza(Map<String, String> map, @Nullable Map<String, String> map2) {
        Map<String, String> linkedHashMap = new LinkedHashMap(map);
        if (map2 == null) {
            return linkedHashMap;
        }
        for (Entry entry : map2.entrySet()) {
            String str = (String) entry.getKey();
            String str2 = (String) linkedHashMap.get(str);
            linkedHashMap.put(str, zzax(str).zzf(str2, (String) entry.getValue()));
        }
        return linkedHashMap;
    }

    public boolean zza(zzdz com_google_android_gms_internal_zzdz) {
        return this.zzblj.offer(com_google_android_gms_internal_zzdz);
    }

    public zzdw zzax(String str) {
        zzdw com_google_android_gms_internal_zzdw = (zzdw) this.zzblm.get(str);
        return com_google_android_gms_internal_zzdw != null ? com_google_android_gms_internal_zzdw : zzdw.zzblq;
    }

    public void zzc(@Nullable List<String> list) {
        if (list != null && !list.isEmpty()) {
            this.zzbll.put("e", TextUtils.join(",", list));
        }
    }
}
