package com.google.android.gms.internal;

import android.content.Context;
import android.os.Build.VERSION;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.zzu;
import java.util.LinkedHashMap;
import java.util.Map;

@zzji
public class zzds {
    private Context mContext;
    private String zzasx;
    private boolean zzblg;
    private String zzblh;
    private Map<String, String> zzbli;

    public zzds(Context context, String str) {
        this.mContext = null;
        this.zzasx = null;
        this.mContext = context;
        this.zzasx = str;
        this.zzblg = ((Boolean) zzdr.zzbeq.get()).booleanValue();
        this.zzblh = (String) zzdr.zzber.get();
        this.zzbli = new LinkedHashMap();
        this.zzbli.put("s", "gmob_sdk");
        this.zzbli.put("v", "3");
        this.zzbli.put("os", VERSION.RELEASE);
        this.zzbli.put(ServerProtocol.DIALOG_PARAM_SDK_VERSION, VERSION.SDK);
        this.zzbli.put("device", zzu.zzgm().zzvt());
        this.zzbli.put("app", context.getApplicationContext() != null ? context.getApplicationContext().getPackageName() : context.getPackageName());
        this.zzbli.put("is_lite_sdk", zzu.zzgm().zzaj(context) ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
        zzjr zzv = zzu.zzgv().zzv(this.mContext);
        this.zzbli.put("network_coarse", Integer.toString(zzv.zzcqe));
        this.zzbli.put("network_fine", Integer.toString(zzv.zzcqf));
    }

    Context getContext() {
        return this.mContext;
    }

    String zzhz() {
        return this.zzasx;
    }

    boolean zzls() {
        return this.zzblg;
    }

    String zzlt() {
        return this.zzblh;
    }

    Map<String, String> zzlu() {
        return this.zzbli;
    }
}
