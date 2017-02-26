package com.google.android.gms.ads.internal;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.view.View.OnClickListener;
import com.facebook.ads.AudienceNetworkActivity;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.applinks.AppLinkData;
import com.google.android.exoplayer.C0989C;
import com.google.android.gms.ads.internal.formats.zzd;
import com.google.android.gms.ads.internal.formats.zze;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.internal.zzeg;
import com.google.android.gms.internal.zzfe;
import com.google.android.gms.internal.zzgu;
import com.google.android.gms.internal.zzhd;
import com.google.android.gms.internal.zzhe;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzko;
import com.google.android.gms.internal.zzmd;
import com.google.android.gms.internal.zzme.zza;
import com.mp3download.zingmp3.BuildConfig;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzn {

    /* renamed from: com.google.android.gms.ads.internal.zzn.1 */
    class C11271 implements zza {
        final /* synthetic */ zzd zzaoq;
        final /* synthetic */ String zzaor;
        final /* synthetic */ zzmd zzaos;

        C11271(zzd com_google_android_gms_ads_internal_formats_zzd, String str, zzmd com_google_android_gms_internal_zzmd) {
            this.zzaoq = com_google_android_gms_ads_internal_formats_zzd;
            this.zzaor = str;
            this.zzaos = com_google_android_gms_internal_zzmd;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("headline", this.zzaoq.getHeadline());
                jSONObject.put(TtmlNode.TAG_BODY, this.zzaoq.getBody());
                jSONObject.put("call_to_action", this.zzaoq.getCallToAction());
                jSONObject.put("price", this.zzaoq.getPrice());
                jSONObject.put("star_rating", String.valueOf(this.zzaoq.getStarRating()));
                jSONObject.put("store", this.zzaoq.getStore());
                jSONObject.put("icon", zzn.zza(this.zzaoq.zzmo()));
                JSONArray jSONArray = new JSONArray();
                List<Object> images = this.zzaoq.getImages();
                if (images != null) {
                    for (Object zzf : images) {
                        jSONArray.put(zzn.zza(zzn.zze(zzf)));
                    }
                }
                jSONObject.put("images", jSONArray);
                jSONObject.put(AppLinkData.ARGUMENTS_EXTRAS_KEY, zzn.zza(this.zzaoq.getExtras(), this.zzaor));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("assets", jSONObject);
                jSONObject2.put("template_id", "2");
                this.zzaos.zza("google.afma.nativeExpressAds.loadAssets", jSONObject2);
            } catch (Throwable e) {
                zzb.zzc("Exception occurred when loading assets", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.2 */
    class C11282 implements zza {
        final /* synthetic */ String zzaor;
        final /* synthetic */ zzmd zzaos;
        final /* synthetic */ zze zzaot;

        C11282(zze com_google_android_gms_ads_internal_formats_zze, String str, zzmd com_google_android_gms_internal_zzmd) {
            this.zzaot = com_google_android_gms_ads_internal_formats_zze;
            this.zzaor = str;
            this.zzaos = com_google_android_gms_internal_zzmd;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, boolean z) {
            try {
                JSONObject jSONObject = new JSONObject();
                jSONObject.put("headline", this.zzaot.getHeadline());
                jSONObject.put(TtmlNode.TAG_BODY, this.zzaot.getBody());
                jSONObject.put("call_to_action", this.zzaot.getCallToAction());
                jSONObject.put("advertiser", this.zzaot.getAdvertiser());
                jSONObject.put("logo", zzn.zza(this.zzaot.zzmt()));
                JSONArray jSONArray = new JSONArray();
                List<Object> images = this.zzaot.getImages();
                if (images != null) {
                    for (Object zzf : images) {
                        jSONArray.put(zzn.zza(zzn.zze(zzf)));
                    }
                }
                jSONObject.put("images", jSONArray);
                jSONObject.put(AppLinkData.ARGUMENTS_EXTRAS_KEY, zzn.zza(this.zzaot.getExtras(), this.zzaor));
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put("assets", jSONObject);
                jSONObject2.put("template_id", AppEventsConstants.EVENT_PARAM_VALUE_YES);
                this.zzaos.zza("google.afma.nativeExpressAds.loadAssets", jSONObject2);
            } catch (Throwable e) {
                zzb.zzc("Exception occurred when loading assets", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.3 */
    class C11293 implements zzfe {
        final /* synthetic */ CountDownLatch zzamc;

        C11293(CountDownLatch countDownLatch) {
            this.zzamc = countDownLatch;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzamc.countDown();
            com_google_android_gms_internal_zzmd.getView().setVisibility(0);
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.4 */
    class C11304 implements zzfe {
        final /* synthetic */ CountDownLatch zzamc;

        C11304(CountDownLatch countDownLatch) {
            this.zzamc = countDownLatch;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            zzb.zzdi("Adapter returned an ad, but assets substitution failed");
            this.zzamc.countDown();
            com_google_android_gms_internal_zzmd.destroy();
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.zzn.5 */
    class C11315 implements zzfe {
        final /* synthetic */ zzhd zzaou;
        final /* synthetic */ zzf.zza zzaov;
        final /* synthetic */ zzhe zzaow;

        C11315(zzhd com_google_android_gms_internal_zzhd, zzf.zza com_google_android_gms_ads_internal_zzf_zza, zzhe com_google_android_gms_internal_zzhe) {
            this.zzaou = com_google_android_gms_internal_zzhd;
            this.zzaov = com_google_android_gms_ads_internal_zzf_zza;
            this.zzaow = com_google_android_gms_internal_zzhe;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            View view = com_google_android_gms_internal_zzmd.getView();
            if (view != null) {
                try {
                    if (this.zzaou != null) {
                        if (this.zzaou.getOverrideClickHandling()) {
                            zzn.zza(com_google_android_gms_internal_zzmd);
                            return;
                        }
                        this.zzaou.zzk(com.google.android.gms.dynamic.zze.zzac(view));
                        this.zzaov.onClick();
                    } else if (this.zzaow == null) {
                    } else {
                        if (this.zzaow.getOverrideClickHandling()) {
                            zzn.zza(com_google_android_gms_internal_zzmd);
                            return;
                        }
                        this.zzaow.zzk(com.google.android.gms.dynamic.zze.zzac(view));
                        this.zzaov.onClick();
                    }
                } catch (Throwable e) {
                    zzb.zzc("Unable to call handleClick on mapper", e);
                }
            }
        }
    }

    private static zzd zza(zzhd com_google_android_gms_internal_zzhd) throws RemoteException {
        return new zzd(com_google_android_gms_internal_zzhd.getHeadline(), com_google_android_gms_internal_zzhd.getImages(), com_google_android_gms_internal_zzhd.getBody(), com_google_android_gms_internal_zzhd.zzmo(), com_google_android_gms_internal_zzhd.getCallToAction(), com_google_android_gms_internal_zzhd.getStarRating(), com_google_android_gms_internal_zzhd.getStore(), com_google_android_gms_internal_zzhd.getPrice(), null, com_google_android_gms_internal_zzhd.getExtras(), null, null);
    }

    private static zze zza(zzhe com_google_android_gms_internal_zzhe) throws RemoteException {
        return new zze(com_google_android_gms_internal_zzhe.getHeadline(), com_google_android_gms_internal_zzhe.getImages(), com_google_android_gms_internal_zzhe.getBody(), com_google_android_gms_internal_zzhe.zzmt(), com_google_android_gms_internal_zzhe.getCallToAction(), com_google_android_gms_internal_zzhe.getAdvertiser(), null, com_google_android_gms_internal_zzhe.getExtras());
    }

    static zzfe zza(@Nullable zzhd com_google_android_gms_internal_zzhd, @Nullable zzhe com_google_android_gms_internal_zzhe, zzf.zza com_google_android_gms_ads_internal_zzf_zza) {
        return new C11315(com_google_android_gms_internal_zzhd, com_google_android_gms_ads_internal_zzf_zza, com_google_android_gms_internal_zzhe);
    }

    static zzfe zza(CountDownLatch countDownLatch) {
        return new C11293(countDownLatch);
    }

    private static String zza(@Nullable Bitmap bitmap) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        if (bitmap == null) {
            zzb.zzdi("Bitmap is null. Returning empty string");
            return BuildConfig.FLAVOR;
        }
        bitmap.compress(CompressFormat.PNG, 100, byteArrayOutputStream);
        String encodeToString = Base64.encodeToString(byteArrayOutputStream.toByteArray(), 0);
        String valueOf = String.valueOf("data:image/png;base64,");
        encodeToString = String.valueOf(encodeToString);
        return encodeToString.length() != 0 ? valueOf.concat(encodeToString) : new String(valueOf);
    }

    static String zza(@Nullable zzeg com_google_android_gms_internal_zzeg) {
        if (com_google_android_gms_internal_zzeg == null) {
            zzb.zzdi("Image is null. Returning empty string");
            return BuildConfig.FLAVOR;
        }
        try {
            Uri uri = com_google_android_gms_internal_zzeg.getUri();
            if (uri != null) {
                return uri.toString();
            }
        } catch (RemoteException e) {
            zzb.zzdi("Unable to get image uri. Trying data uri next");
        }
        return zzb(com_google_android_gms_internal_zzeg);
    }

    private static JSONObject zza(@Nullable Bundle bundle, String str) throws JSONException {
        JSONObject jSONObject = new JSONObject();
        if (bundle == null || TextUtils.isEmpty(str)) {
            return jSONObject;
        }
        JSONObject jSONObject2 = new JSONObject(str);
        Iterator keys = jSONObject2.keys();
        while (keys.hasNext()) {
            String str2 = (String) keys.next();
            if (bundle.containsKey(str2)) {
                if ("image".equals(jSONObject2.getString(str2))) {
                    Object obj = bundle.get(str2);
                    if (obj instanceof Bitmap) {
                        jSONObject.put(str2, zza((Bitmap) obj));
                    } else {
                        zzb.zzdi("Invalid type. An image type extra should return a bitmap");
                    }
                } else if (bundle.get(str2) instanceof Bitmap) {
                    zzb.zzdi("Invalid asset type. Bitmap should be returned only for image type");
                } else {
                    jSONObject.put(str2, String.valueOf(bundle.get(str2)));
                }
            }
        }
        return jSONObject;
    }

    public static void zza(@Nullable zzko com_google_android_gms_internal_zzko, zzf.zza com_google_android_gms_ads_internal_zzf_zza) {
        zzhe com_google_android_gms_internal_zzhe = null;
        if (com_google_android_gms_internal_zzko != null && zzh(com_google_android_gms_internal_zzko)) {
            zzmd com_google_android_gms_internal_zzmd = com_google_android_gms_internal_zzko.zzcbm;
            Object view = com_google_android_gms_internal_zzmd != null ? com_google_android_gms_internal_zzmd.getView() : null;
            if (view == null) {
                zzb.zzdi("AdWebView is null");
                return;
            }
            try {
                List list = com_google_android_gms_internal_zzko.zzbwm != null ? com_google_android_gms_internal_zzko.zzbwm.zzbvg : null;
                if (list == null || list.isEmpty()) {
                    zzb.zzdi("No template ids present in mediation response");
                    return;
                }
                zzhd zzom = com_google_android_gms_internal_zzko.zzbwn != null ? com_google_android_gms_internal_zzko.zzbwn.zzom() : null;
                if (com_google_android_gms_internal_zzko.zzbwn != null) {
                    com_google_android_gms_internal_zzhe = com_google_android_gms_internal_zzko.zzbwn.zzon();
                }
                if (list.contains("2") && zzom != null) {
                    zzom.zzl(com.google.android.gms.dynamic.zze.zzac(view));
                    if (!zzom.getOverrideImpressionRecording()) {
                        zzom.recordImpression();
                    }
                    com_google_android_gms_internal_zzmd.zzxc().zza("/nativeExpressViewClicked", zza(zzom, null, com_google_android_gms_ads_internal_zzf_zza));
                } else if (!list.contains(AppEventsConstants.EVENT_PARAM_VALUE_YES) || com_google_android_gms_internal_zzhe == null) {
                    zzb.zzdi("No matching template id and mapper");
                } else {
                    com_google_android_gms_internal_zzhe.zzl(com.google.android.gms.dynamic.zze.zzac(view));
                    if (!com_google_android_gms_internal_zzhe.getOverrideImpressionRecording()) {
                        com_google_android_gms_internal_zzhe.recordImpression();
                    }
                    com_google_android_gms_internal_zzmd.zzxc().zza("/nativeExpressViewClicked", zza(null, com_google_android_gms_internal_zzhe, com_google_android_gms_ads_internal_zzf_zza));
                }
            } catch (Throwable e) {
                zzb.zzc("Error occurred while recording impression and registering for clicks", e);
            }
        }
    }

    private static void zza(zzmd com_google_android_gms_internal_zzmd) {
        OnClickListener zzxr = com_google_android_gms_internal_zzmd.zzxr();
        if (zzxr != null) {
            zzxr.onClick(com_google_android_gms_internal_zzmd.getView());
        }
    }

    private static void zza(zzmd com_google_android_gms_internal_zzmd, zzd com_google_android_gms_ads_internal_formats_zzd, String str) {
        com_google_android_gms_internal_zzmd.zzxc().zza(new C11271(com_google_android_gms_ads_internal_formats_zzd, str, com_google_android_gms_internal_zzmd));
    }

    private static void zza(zzmd com_google_android_gms_internal_zzmd, zze com_google_android_gms_ads_internal_formats_zze, String str) {
        com_google_android_gms_internal_zzmd.zzxc().zza(new C11282(com_google_android_gms_ads_internal_formats_zze, str, com_google_android_gms_internal_zzmd));
    }

    private static void zza(zzmd com_google_android_gms_internal_zzmd, CountDownLatch countDownLatch) {
        com_google_android_gms_internal_zzmd.zzxc().zza("/nativeExpressAssetsLoaded", zza(countDownLatch));
        com_google_android_gms_internal_zzmd.zzxc().zza("/nativeExpressAssetsLoadingFailed", zzb(countDownLatch));
    }

    public static boolean zza(zzmd com_google_android_gms_internal_zzmd, zzgu com_google_android_gms_internal_zzgu, CountDownLatch countDownLatch) {
        boolean z = false;
        try {
            z = zzb(com_google_android_gms_internal_zzmd, com_google_android_gms_internal_zzgu, countDownLatch);
        } catch (Throwable e) {
            zzb.zzc("Unable to invoke load assets", e);
        } catch (RuntimeException e2) {
            countDownLatch.countDown();
            throw e2;
        }
        if (!z) {
            countDownLatch.countDown();
        }
        return z;
    }

    static zzfe zzb(CountDownLatch countDownLatch) {
        return new C11304(countDownLatch);
    }

    private static String zzb(zzeg com_google_android_gms_internal_zzeg) {
        try {
            com.google.android.gms.dynamic.zzd zzmn = com_google_android_gms_internal_zzeg.zzmn();
            if (zzmn == null) {
                zzb.zzdi("Drawable is null. Returning empty string");
                return BuildConfig.FLAVOR;
            }
            Drawable drawable = (Drawable) com.google.android.gms.dynamic.zze.zzae(zzmn);
            if (drawable instanceof BitmapDrawable) {
                return zza(((BitmapDrawable) drawable).getBitmap());
            }
            zzb.zzdi("Drawable is not an instance of BitmapDrawable. Returning empty string");
            return BuildConfig.FLAVOR;
        } catch (RemoteException e) {
            zzb.zzdi("Unable to get drawable. Returning empty string");
            return BuildConfig.FLAVOR;
        }
    }

    private static boolean zzb(zzmd com_google_android_gms_internal_zzmd, zzgu com_google_android_gms_internal_zzgu, CountDownLatch countDownLatch) throws RemoteException {
        View view = com_google_android_gms_internal_zzmd.getView();
        if (view == null) {
            zzb.zzdi("AdWebView is null");
            return false;
        }
        view.setVisibility(4);
        List list = com_google_android_gms_internal_zzgu.zzbwm.zzbvg;
        if (list == null || list.isEmpty()) {
            zzb.zzdi("No template ids present in mediation response");
            return false;
        }
        zza(com_google_android_gms_internal_zzmd, countDownLatch);
        zzhd zzom = com_google_android_gms_internal_zzgu.zzbwn.zzom();
        zzhe zzon = com_google_android_gms_internal_zzgu.zzbwn.zzon();
        if (list.contains("2") && zzom != null) {
            zza(com_google_android_gms_internal_zzmd, zza(zzom), com_google_android_gms_internal_zzgu.zzbwm.zzbvf);
        } else if (!list.contains(AppEventsConstants.EVENT_PARAM_VALUE_YES) || zzon == null) {
            zzb.zzdi("No matching template id and mapper");
            return false;
        } else {
            zza(com_google_android_gms_internal_zzmd, zza(zzon), com_google_android_gms_internal_zzgu.zzbwm.zzbvf);
        }
        String str = com_google_android_gms_internal_zzgu.zzbwm.zzbvd;
        String str2 = com_google_android_gms_internal_zzgu.zzbwm.zzbve;
        if (str2 != null) {
            com_google_android_gms_internal_zzmd.loadDataWithBaseURL(str2, str, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME, null);
        } else {
            com_google_android_gms_internal_zzmd.loadData(str, AudienceNetworkActivity.WEBVIEW_MIME_TYPE, C0989C.UTF8_NAME);
        }
        return true;
    }

    @Nullable
    private static zzeg zze(Object obj) {
        return obj instanceof IBinder ? zzeg.zza.zzab((IBinder) obj) : null;
    }

    @Nullable
    public static View zzg(@Nullable zzko com_google_android_gms_internal_zzko) {
        if (com_google_android_gms_internal_zzko == null) {
            zzb.m1695e("AdState is null");
            return null;
        } else if (zzh(com_google_android_gms_internal_zzko) && com_google_android_gms_internal_zzko.zzcbm != null) {
            return com_google_android_gms_internal_zzko.zzcbm.getView();
        } else {
            try {
                com.google.android.gms.dynamic.zzd view = com_google_android_gms_internal_zzko.zzbwn != null ? com_google_android_gms_internal_zzko.zzbwn.getView() : null;
                if (view != null) {
                    return (View) com.google.android.gms.dynamic.zze.zzae(view);
                }
                zzb.zzdi("View in mediation adapter is null.");
                return null;
            } catch (Throwable e) {
                zzb.zzc("Could not get View from mediation adapter.", e);
                return null;
            }
        }
    }

    public static boolean zzh(@Nullable zzko com_google_android_gms_internal_zzko) {
        return (com_google_android_gms_internal_zzko == null || !com_google_android_gms_internal_zzko.zzclb || com_google_android_gms_internal_zzko.zzbwm == null || com_google_android_gms_internal_zzko.zzbwm.zzbvd == null) ? false : true;
    }
}
