package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.internal.zzafw;
import com.google.android.gms.internal.zzafw.zzc;
import com.google.android.gms.internal.zzafw.zzg;
import com.google.android.gms.internal.zzai.zzf;
import com.google.android.gms.internal.zzai.zzi;
import com.google.android.gms.internal.zzai.zzj;
import com.mp3download.zingmp3.BuildConfig;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container {
    private final String aDY;
    private final DataLayer aDZ;
    private zzcx aEa;
    private Map<String, FunctionCallMacroCallback> aEb;
    private Map<String, FunctionCallTagCallback> aEc;
    private volatile long aEd;
    private volatile String aEe;
    private final Context mContext;

    public interface FunctionCallMacroCallback {
        Object getValue(String str, Map<String, Object> map);
    }

    public interface FunctionCallTagCallback {
        void execute(String str, Map<String, Object> map);
    }

    private class zza implements com.google.android.gms.tagmanager.zzu.zza {
        final /* synthetic */ Container aEf;

        private zza(Container container) {
            this.aEf = container;
        }

        public Object zzf(String str, Map<String, Object> map) {
            FunctionCallMacroCallback zzot = this.aEf.zzot(str);
            return zzot == null ? null : zzot.getValue(str, map);
        }
    }

    private class zzb implements com.google.android.gms.tagmanager.zzu.zza {
        final /* synthetic */ Container aEf;

        private zzb(Container container) {
            this.aEf = container;
        }

        public Object zzf(String str, Map<String, Object> map) {
            FunctionCallTagCallback zzou = this.aEf.zzou(str);
            if (zzou != null) {
                zzou.execute(str, map);
            }
            return zzdm.zzchl();
        }
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzc com_google_android_gms_internal_zzafw_zzc) {
        this.aEb = new HashMap();
        this.aEc = new HashMap();
        this.aEe = BuildConfig.FLAVOR;
        this.mContext = context;
        this.aDZ = dataLayer;
        this.aDY = str;
        this.aEd = j;
        zza(com_google_android_gms_internal_zzafw_zzc);
    }

    Container(Context context, DataLayer dataLayer, String str, long j, zzj com_google_android_gms_internal_zzai_zzj) {
        this.aEb = new HashMap();
        this.aEc = new HashMap();
        this.aEe = BuildConfig.FLAVOR;
        this.mContext = context;
        this.aDZ = dataLayer;
        this.aDY = str;
        this.aEd = j;
        zza(com_google_android_gms_internal_zzai_zzj.zzxv);
        if (com_google_android_gms_internal_zzai_zzj.zzxu != null) {
            zza(com_google_android_gms_internal_zzai_zzj.zzxu);
        }
    }

    private void zza(zzc com_google_android_gms_internal_zzafw_zzc) {
        this.aEe = com_google_android_gms_internal_zzafw_zzc.getVersion();
        zzc com_google_android_gms_internal_zzafw_zzc2 = com_google_android_gms_internal_zzafw_zzc;
        zza(new zzcx(this.mContext, com_google_android_gms_internal_zzafw_zzc2, this.aDZ, new zza(), new zzb(), zzow(this.aEe)));
        if (getBoolean("_gtm.loadEventEnabled")) {
            this.aDZ.pushEvent("gtm.load", DataLayer.mapOf("gtm.id", this.aDY));
        }
    }

    private void zza(zzf com_google_android_gms_internal_zzai_zzf) {
        if (com_google_android_gms_internal_zzai_zzf == null) {
            throw new NullPointerException();
        }
        try {
            zza(zzafw.zzb(com_google_android_gms_internal_zzai_zzf));
        } catch (zzg e) {
            String valueOf = String.valueOf(com_google_android_gms_internal_zzai_zzf);
            String valueOf2 = String.valueOf(e.toString());
            zzbo.m1698e(new StringBuilder((String.valueOf(valueOf).length() + 46) + String.valueOf(valueOf2).length()).append("Not loading resource: ").append(valueOf).append(" because it is invalid: ").append(valueOf2).toString());
        }
    }

    private synchronized void zza(zzcx com_google_android_gms_tagmanager_zzcx) {
        this.aEa = com_google_android_gms_tagmanager_zzcx;
    }

    private void zza(zzi[] com_google_android_gms_internal_zzai_zziArr) {
        List arrayList = new ArrayList();
        for (Object add : com_google_android_gms_internal_zzai_zziArr) {
            arrayList.add(add);
        }
        zzcdz().zzam(arrayList);
    }

    private synchronized zzcx zzcdz() {
        return this.aEa;
    }

    public boolean getBoolean(String str) {
        zzcx zzcdz = zzcdz();
        if (zzcdz == null) {
            zzbo.m1698e("getBoolean called for closed container.");
            return zzdm.zzchj().booleanValue();
        }
        try {
            return zzdm.zzk((com.google.android.gms.internal.zzaj.zza) zzcdz.zzpr(str).getObject()).booleanValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzbo.m1698e(new StringBuilder(String.valueOf(valueOf).length() + 66).append("Calling getBoolean() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzdm.zzchj().booleanValue();
        }
    }

    public String getContainerId() {
        return this.aDY;
    }

    public double getDouble(String str) {
        zzcx zzcdz = zzcdz();
        if (zzcdz == null) {
            zzbo.m1698e("getDouble called for closed container.");
            return zzdm.zzchi().doubleValue();
        }
        try {
            return zzdm.zzj((com.google.android.gms.internal.zzaj.zza) zzcdz.zzpr(str).getObject()).doubleValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzbo.m1698e(new StringBuilder(String.valueOf(valueOf).length() + 65).append("Calling getDouble() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzdm.zzchi().doubleValue();
        }
    }

    public long getLastRefreshTime() {
        return this.aEd;
    }

    public long getLong(String str) {
        zzcx zzcdz = zzcdz();
        if (zzcdz == null) {
            zzbo.m1698e("getLong called for closed container.");
            return zzdm.zzchh().longValue();
        }
        try {
            return zzdm.zzi((com.google.android.gms.internal.zzaj.zza) zzcdz.zzpr(str).getObject()).longValue();
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzbo.m1698e(new StringBuilder(String.valueOf(valueOf).length() + 63).append("Calling getLong() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzdm.zzchh().longValue();
        }
    }

    public String getString(String str) {
        zzcx zzcdz = zzcdz();
        if (zzcdz == null) {
            zzbo.m1698e("getString called for closed container.");
            return zzdm.zzchl();
        }
        try {
            return zzdm.zzg((com.google.android.gms.internal.zzaj.zza) zzcdz.zzpr(str).getObject());
        } catch (Exception e) {
            String valueOf = String.valueOf(e.getMessage());
            zzbo.m1698e(new StringBuilder(String.valueOf(valueOf).length() + 65).append("Calling getString() threw an exception: ").append(valueOf).append(" Returning default value.").toString());
            return zzdm.zzchl();
        }
    }

    public boolean isDefault() {
        return getLastRefreshTime() == 0;
    }

    public void registerFunctionCallMacroCallback(String str, FunctionCallMacroCallback functionCallMacroCallback) {
        if (functionCallMacroCallback == null) {
            throw new NullPointerException("Macro handler must be non-null");
        }
        synchronized (this.aEb) {
            this.aEb.put(str, functionCallMacroCallback);
        }
    }

    public void registerFunctionCallTagCallback(String str, FunctionCallTagCallback functionCallTagCallback) {
        if (functionCallTagCallback == null) {
            throw new NullPointerException("Tag callback must be non-null");
        }
        synchronized (this.aEc) {
            this.aEc.put(str, functionCallTagCallback);
        }
    }

    void release() {
        this.aEa = null;
    }

    public void unregisterFunctionCallMacroCallback(String str) {
        synchronized (this.aEb) {
            this.aEb.remove(str);
        }
    }

    public void unregisterFunctionCallTagCallback(String str) {
        synchronized (this.aEc) {
            this.aEc.remove(str);
        }
    }

    public String zzcdy() {
        return this.aEe;
    }

    FunctionCallMacroCallback zzot(String str) {
        FunctionCallMacroCallback functionCallMacroCallback;
        synchronized (this.aEb) {
            functionCallMacroCallback = (FunctionCallMacroCallback) this.aEb.get(str);
        }
        return functionCallMacroCallback;
    }

    public FunctionCallTagCallback zzou(String str) {
        FunctionCallTagCallback functionCallTagCallback;
        synchronized (this.aEc) {
            functionCallTagCallback = (FunctionCallTagCallback) this.aEc.get(str);
        }
        return functionCallTagCallback;
    }

    public void zzov(String str) {
        zzcdz().zzov(str);
    }

    zzaj zzow(String str) {
        if (zzcj.zzcfz().zzcga().equals(zza.CONTAINER_DEBUG)) {
        }
        return new zzbw();
    }
}
