package com.google.android.gms.internal;

import android.content.Context;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.client.zzm;
import com.google.android.gms.ads.internal.util.client.zza;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.tagmanager.DataLayer;
import com.mp3download.zingmp3.C1569R;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

@zzji
public abstract class zzfs implements Releasable {
    protected Context mContext;
    protected String zzbre;
    protected WeakReference<zzmd> zzbrf;

    /* renamed from: com.google.android.gms.internal.zzfs.1 */
    class C13081 implements Runnable {
        final /* synthetic */ String zzall;
        final /* synthetic */ String zzbrg;
        final /* synthetic */ int zzbrh;
        final /* synthetic */ int zzbri;
        final /* synthetic */ boolean zzbrj;
        final /* synthetic */ zzfs zzbrk;

        C13081(zzfs com_google_android_gms_internal_zzfs, String str, String str2, int i, int i2, boolean z) {
            this.zzbrk = com_google_android_gms_internal_zzfs;
            this.zzall = str;
            this.zzbrg = str2;
            this.zzbrh = i;
            this.zzbri = i2;
            this.zzbrj = z;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put(DataLayer.EVENT_KEY, "precacheProgress");
            hashMap.put("src", this.zzall);
            hashMap.put("cachedSrc", this.zzbrg);
            hashMap.put("bytesLoaded", Integer.toString(this.zzbrh));
            hashMap.put("totalBytes", Integer.toString(this.zzbri));
            hashMap.put("cacheReady", this.zzbrj ? AppEventsConstants.EVENT_PARAM_VALUE_YES : AppEventsConstants.EVENT_PARAM_VALUE_NO);
            this.zzbrk.zza("onPrecacheEvent", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfs.2 */
    class C13092 implements Runnable {
        final /* synthetic */ String zzall;
        final /* synthetic */ String zzbrg;
        final /* synthetic */ int zzbri;
        final /* synthetic */ zzfs zzbrk;

        C13092(zzfs com_google_android_gms_internal_zzfs, String str, String str2, int i) {
            this.zzbrk = com_google_android_gms_internal_zzfs;
            this.zzall = str;
            this.zzbrg = str2;
            this.zzbri = i;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put(DataLayer.EVENT_KEY, "precacheComplete");
            hashMap.put("src", this.zzall);
            hashMap.put("cachedSrc", this.zzbrg);
            hashMap.put("totalBytes", Integer.toString(this.zzbri));
            this.zzbrk.zza("onPrecacheEvent", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfs.3 */
    class C13103 implements Runnable {
        final /* synthetic */ String zzall;
        final /* synthetic */ String zzbrg;
        final /* synthetic */ zzfs zzbrk;
        final /* synthetic */ String zzbrl;
        final /* synthetic */ String zzbrm;

        C13103(zzfs com_google_android_gms_internal_zzfs, String str, String str2, String str3, String str4) {
            this.zzbrk = com_google_android_gms_internal_zzfs;
            this.zzall = str;
            this.zzbrg = str2;
            this.zzbrl = str3;
            this.zzbrm = str4;
        }

        public void run() {
            Map hashMap = new HashMap();
            hashMap.put(DataLayer.EVENT_KEY, "precacheCanceled");
            hashMap.put("src", this.zzall);
            if (!TextUtils.isEmpty(this.zzbrg)) {
                hashMap.put("cachedSrc", this.zzbrg);
            }
            hashMap.put(ShareConstants.MEDIA_TYPE, this.zzbrk.zzbi(this.zzbrl));
            hashMap.put("reason", this.zzbrl);
            if (!TextUtils.isEmpty(this.zzbrm)) {
                hashMap.put(ShareConstants.WEB_DIALOG_PARAM_MESSAGE, this.zzbrm);
            }
            this.zzbrk.zza("onPrecacheEvent", hashMap);
        }
    }

    public zzfs(zzmd com_google_android_gms_internal_zzmd) {
        this.mContext = com_google_android_gms_internal_zzmd.getContext();
        this.zzbre = zzu.zzgm().zzh(this.mContext, com_google_android_gms_internal_zzmd.zzxf().zzda);
        this.zzbrf = new WeakReference(com_google_android_gms_internal_zzmd);
    }

    private void zza(String str, Map<String, String> map) {
        zzmd com_google_android_gms_internal_zzmd = (zzmd) this.zzbrf.get();
        if (com_google_android_gms_internal_zzmd != null) {
            com_google_android_gms_internal_zzmd.zza(str, (Map) map);
        }
    }

    private String zzbi(String str) {
        String str2 = "internal";
        Object obj = -1;
        switch (str.hashCode()) {
            case -1396664534:
                if (str.equals("badUrl")) {
                    obj = 6;
                    break;
                }
                break;
            case -1347010958:
                if (str.equals("inProgress")) {
                    obj = 2;
                    break;
                }
                break;
            case -918817863:
                if (str.equals("downloadTimeout")) {
                    obj = 7;
                    break;
                }
                break;
            case -659376217:
                if (str.equals("contentLengthMissing")) {
                    obj = 3;
                    break;
                }
                break;
            case -642208130:
                if (str.equals("playerFailed")) {
                    obj = 1;
                    break;
                }
                break;
            case -354048396:
                if (str.equals("sizeExceeded")) {
                    obj = 8;
                    break;
                }
                break;
            case -32082395:
                if (str.equals("externalAbort")) {
                    obj = 9;
                    break;
                }
                break;
            case 96784904:
                if (str.equals(NativeProtocol.BRIDGE_ARG_ERROR_BUNDLE)) {
                    obj = null;
                    break;
                }
                break;
            case 580119100:
                if (str.equals("expireFailed")) {
                    obj = 5;
                    break;
                }
                break;
            case 725497484:
                if (str.equals("noCacheDir")) {
                    obj = 4;
                    break;
                }
                break;
        }
        switch (obj) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return "internal";
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return "io";
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                return "network";
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return "policy";
            default:
                return str2;
        }
    }

    public abstract void abort();

    public void release() {
    }

    protected void zza(String str, String str2, int i) {
        zza.zzcxr.post(new C13092(this, str, str2, i));
    }

    protected void zza(String str, String str2, int i, int i2, boolean z) {
        zza.zzcxr.post(new C13081(this, str, str2, i, i2, z));
    }

    public void zza(String str, String str2, String str3, @Nullable String str4) {
        zza.zzcxr.post(new C13103(this, str, str2, str3, str4));
    }

    public abstract boolean zzbg(String str);

    protected String zzbh(String str) {
        return zzm.zzkr().zzdf(str);
    }
}
