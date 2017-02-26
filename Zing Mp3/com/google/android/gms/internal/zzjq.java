package com.google.android.gms.internal;

import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.facebook.share.internal.ShareConstants;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@zzji
class zzjq {
    private String zzae;
    private final String zzcec;
    private int zzcgw;
    private final List<String> zzcpj;
    private final List<String> zzcpk;
    private final String zzcpl;
    private final String zzcpm;
    private final String zzcpn;
    private final String zzcpo;
    private final boolean zzcpp;
    private final boolean zzcpq;
    private final String zzcpr;

    public zzjq(int i, Map<String, String> map) {
        this.zzae = (String) map.get(NativeProtocol.WEB_DIALOG_URL);
        this.zzcpm = (String) map.get("base_uri");
        this.zzcpn = (String) map.get("post_parameters");
        this.zzcpp = parseBoolean((String) map.get("drt_include"));
        this.zzcpq = parseBoolean((String) map.get("pan_include"));
        this.zzcpl = (String) map.get("activation_overlay_url");
        this.zzcpk = zzco((String) map.get("check_packages"));
        this.zzcec = (String) map.get("request_id");
        this.zzcpo = (String) map.get(ShareConstants.MEDIA_TYPE);
        this.zzcpj = zzco((String) map.get("errors"));
        this.zzcgw = i;
        this.zzcpr = (String) map.get("fetched_ad");
    }

    private static boolean parseBoolean(String str) {
        return str != null && (str.equals(AppEventsConstants.EVENT_PARAM_VALUE_YES) || str.equals(ServerProtocol.DIALOG_RETURN_SCOPES_TRUE));
    }

    private List<String> zzco(String str) {
        return str == null ? null : Arrays.asList(str.split(","));
    }

    public int getErrorCode() {
        return this.zzcgw;
    }

    public String getRequestId() {
        return this.zzcec;
    }

    public String getType() {
        return this.zzcpo;
    }

    public String getUrl() {
        return this.zzae;
    }

    public void setUrl(String str) {
        this.zzae = str;
    }

    public List<String> zztm() {
        return this.zzcpj;
    }

    public String zztn() {
        return this.zzcpm;
    }

    public String zzto() {
        return this.zzcpn;
    }

    public boolean zztp() {
        return this.zzcpp;
    }

    public String zztq() {
        return this.zzcpr;
    }
}
