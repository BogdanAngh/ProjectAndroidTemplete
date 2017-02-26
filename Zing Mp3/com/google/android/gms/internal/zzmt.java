package com.google.android.gms.internal;

import android.text.TextUtils;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.analytics.zzg;
import java.util.HashMap;
import java.util.Map;

public final class zzmt extends zzg<zzmt> {
    private String bQ;
    private String bR;
    private String bS;
    private String bT;
    private String bU;
    private String bV;
    private String bW;
    private String mName;
    private String zzbme;
    private String zzboa;

    public String getContent() {
        return this.zzbme;
    }

    public String getId() {
        return this.zzboa;
    }

    public String getName() {
        return this.mName;
    }

    public String getSource() {
        return this.bQ;
    }

    public void setName(String str) {
        this.mName = str;
    }

    public String toString() {
        Map hashMap = new HashMap();
        hashMap.put(ShareConstants.WEB_DIALOG_PARAM_NAME, this.mName);
        hashMap.put(ShareConstants.FEED_SOURCE_PARAM, this.bQ);
        hashMap.put("medium", this.bR);
        hashMap.put("keyword", this.bS);
        hashMap.put("content", this.zzbme);
        hashMap.put(TtmlNode.ATTR_ID, this.zzboa);
        hashMap.put("adNetworkId", this.bT);
        hashMap.put("gclid", this.bU);
        hashMap.put("dclid", this.bV);
        hashMap.put("aclid", this.bW);
        return zzg.zzj(hashMap);
    }

    public void zza(zzmt com_google_android_gms_internal_zzmt) {
        if (!TextUtils.isEmpty(this.mName)) {
            com_google_android_gms_internal_zzmt.setName(this.mName);
        }
        if (!TextUtils.isEmpty(this.bQ)) {
            com_google_android_gms_internal_zzmt.zzdu(this.bQ);
        }
        if (!TextUtils.isEmpty(this.bR)) {
            com_google_android_gms_internal_zzmt.zzdv(this.bR);
        }
        if (!TextUtils.isEmpty(this.bS)) {
            com_google_android_gms_internal_zzmt.zzdw(this.bS);
        }
        if (!TextUtils.isEmpty(this.zzbme)) {
            com_google_android_gms_internal_zzmt.zzdx(this.zzbme);
        }
        if (!TextUtils.isEmpty(this.zzboa)) {
            com_google_android_gms_internal_zzmt.zzdy(this.zzboa);
        }
        if (!TextUtils.isEmpty(this.bT)) {
            com_google_android_gms_internal_zzmt.zzdz(this.bT);
        }
        if (!TextUtils.isEmpty(this.bU)) {
            com_google_android_gms_internal_zzmt.zzea(this.bU);
        }
        if (!TextUtils.isEmpty(this.bV)) {
            com_google_android_gms_internal_zzmt.zzeb(this.bV);
        }
        if (!TextUtils.isEmpty(this.bW)) {
            com_google_android_gms_internal_zzmt.zzec(this.bW);
        }
    }

    public String zzaah() {
        return this.bR;
    }

    public String zzaai() {
        return this.bS;
    }

    public String zzaaj() {
        return this.bT;
    }

    public String zzaak() {
        return this.bU;
    }

    public String zzaal() {
        return this.bV;
    }

    public String zzaam() {
        return this.bW;
    }

    public /* synthetic */ void zzb(zzg com_google_android_gms_analytics_zzg) {
        zza((zzmt) com_google_android_gms_analytics_zzg);
    }

    public void zzdu(String str) {
        this.bQ = str;
    }

    public void zzdv(String str) {
        this.bR = str;
    }

    public void zzdw(String str) {
        this.bS = str;
    }

    public void zzdx(String str) {
        this.zzbme = str;
    }

    public void zzdy(String str) {
        this.zzboa = str;
    }

    public void zzdz(String str) {
        this.bT = str;
    }

    public void zzea(String str) {
        this.bU = str;
    }

    public void zzeb(String str) {
        this.bV = str;
    }

    public void zzec(String str) {
        this.bW = str;
    }
}
