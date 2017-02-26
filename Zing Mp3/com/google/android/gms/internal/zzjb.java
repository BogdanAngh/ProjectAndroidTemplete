package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.RemoteException;
import android.text.TextUtils;
import com.facebook.GraphResponse;
import com.facebook.ads.AdError;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.google.android.exoplayer.util.MimeTypes;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzj;
import com.google.android.gms.ads.internal.zzq;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.common.util.zzs;
import com.google.android.gms.dynamic.zze;
import com.mp3download.zingmp3.BuildConfig;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzjb implements Callable<zzko> {
    static long zzchj;
    private final Context mContext;
    private final Object zzako;
    private final zzdz zzalt;
    private final zzja zzbnv;
    private final zzav zzbnx;
    private final com.google.android.gms.internal.zzko.zza zzcgf;
    private int zzcgw;
    private final zzli zzchs;
    private final zzq zzcht;
    private boolean zzchu;
    private List<String> zzchv;
    private JSONObject zzchw;

    /* renamed from: com.google.android.gms.internal.zzjb.1 */
    class C13951 extends com.google.android.gms.internal.zzja.zza {
        final /* synthetic */ String zzchx;
        final /* synthetic */ zzb zzchy;
        final /* synthetic */ zzlq zzchz;
        final /* synthetic */ zzjb zzcia;

        /* renamed from: com.google.android.gms.internal.zzjb.1.1 */
        class C13941 implements zzfe {
            final /* synthetic */ zzgi zzbof;
            final /* synthetic */ C13951 zzcib;

            C13941(C13951 c13951, zzgi com_google_android_gms_internal_zzgi) {
                this.zzcib = c13951;
                this.zzbof = com_google_android_gms_internal_zzgi;
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                try {
                    String str = (String) map.get(GraphResponse.SUCCESS_KEY);
                    if (!TextUtils.isEmpty(str)) {
                        if (this.zzcib.zzchx.equals(new JSONObject(str).optString("ads_id", BuildConfig.FLAVOR))) {
                            this.zzbof.zzb("/nativeAdPreProcess", this.zzcib.zzchy.zzciq);
                            this.zzcib.zzchz.zzh(new JSONObject(str).getJSONArray("ads").getJSONObject(0));
                        }
                    }
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Malformed native JSON response.", e);
                    this.zzcib.zzcia.zzas(0);
                    zzaa.zza(this.zzcib.zzcia.zzst(), (Object) "Unable to set the ad state error!");
                    this.zzcib.zzchz.zzh(null);
                }
            }
        }

        C13951(zzjb com_google_android_gms_internal_zzjb, String str, zzb com_google_android_gms_internal_zzjb_zzb, zzlq com_google_android_gms_internal_zzlq) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzchx = str;
            this.zzchy = com_google_android_gms_internal_zzjb_zzb;
            this.zzchz = com_google_android_gms_internal_zzlq;
        }

        public void zze(zzgi com_google_android_gms_internal_zzgi) {
            zzfe c13941 = new C13941(this, com_google_android_gms_internal_zzgi);
            this.zzchy.zzciq = c13941;
            com_google_android_gms_internal_zzgi.zza("/nativeAdPreProcess", c13941);
            try {
                JSONObject jSONObject = new JSONObject(this.zzcia.zzcgf.zzcsu.body);
                jSONObject.put("ads_id", this.zzchx);
                com_google_android_gms_internal_zzgi.zza("google.afma.nativeAds.preProcessJsonGmsg", jSONObject);
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzc("Exception occurred while invoking javascript", e);
                this.zzchz.zzh(null);
            }
        }

        public void zzsr() {
            this.zzchz.zzh(null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjb.2 */
    class C13962 implements Runnable {
        final /* synthetic */ zzjb zzcia;
        final /* synthetic */ zzlq zzcic;
        final /* synthetic */ String zzcid;

        C13962(zzjb com_google_android_gms_internal_zzjb, zzlq com_google_android_gms_internal_zzlq, String str) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzcic = com_google_android_gms_internal_zzlq;
            this.zzcid = str;
        }

        public void run() {
            this.zzcic.zzh((zzet) this.zzcia.zzcht.zzfv().get(this.zzcid));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjb.3 */
    class C13973 implements zzfe {
        final /* synthetic */ zzjb zzcia;
        final /* synthetic */ zzf zzcie;

        C13973(zzjb com_google_android_gms_internal_zzjb, zzf com_google_android_gms_ads_internal_formats_zzf) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzcie = com_google_android_gms_ads_internal_formats_zzf;
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            this.zzcia.zzb(this.zzcie, (String) map.get("asset"));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjb.4 */
    class C13984 extends com.google.android.gms.internal.zzja.zza {
        final /* synthetic */ zzjb zzcia;
        final /* synthetic */ zzfe zzcif;

        C13984(zzjb com_google_android_gms_internal_zzjb, zzfe com_google_android_gms_internal_zzfe) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzcif = com_google_android_gms_internal_zzfe;
        }

        public void zze(zzgi com_google_android_gms_internal_zzgi) {
            com_google_android_gms_internal_zzgi.zza("/nativeAdCustomClick", this.zzcif);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjb.5 */
    class C13995 implements com.google.android.gms.internal.zzls.zza<List<zzc>, com.google.android.gms.ads.internal.formats.zza> {
        final /* synthetic */ zzjb zzcia;
        final /* synthetic */ String zzcig;
        final /* synthetic */ Integer zzcih;
        final /* synthetic */ Integer zzcii;
        final /* synthetic */ int zzcij;
        final /* synthetic */ int zzcik;
        final /* synthetic */ int zzcil;
        final /* synthetic */ int zzcim;

        C13995(zzjb com_google_android_gms_internal_zzjb, String str, Integer num, Integer num2, int i, int i2, int i3, int i4) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzcig = str;
            this.zzcih = num;
            this.zzcii = num2;
            this.zzcij = i;
            this.zzcik = i2;
            this.zzcil = i3;
            this.zzcim = i4;
        }

        public /* synthetic */ Object apply(Object obj) {
            return zzj((List) obj);
        }

        public com.google.android.gms.ads.internal.formats.zza zzj(List<zzc> list) {
            com.google.android.gms.ads.internal.formats.zza com_google_android_gms_ads_internal_formats_zza;
            if (list != null) {
                try {
                    if (!list.isEmpty()) {
                        com_google_android_gms_ads_internal_formats_zza = new com.google.android.gms.ads.internal.formats.zza(this.zzcig, zzjb.zzh(list), this.zzcih, this.zzcii, this.zzcij > 0 ? Integer.valueOf(this.zzcij) : null, this.zzcik + this.zzcil, this.zzcim);
                        return com_google_android_gms_ads_internal_formats_zza;
                    }
                } catch (Throwable e) {
                    com.google.android.gms.ads.internal.util.client.zzb.zzb("Could not get attribution icon", e);
                    return null;
                }
            }
            com_google_android_gms_ads_internal_formats_zza = null;
            return com_google_android_gms_ads_internal_formats_zza;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzjb.6 */
    class C14006 implements com.google.android.gms.internal.zzli.zza<zzc> {
        final /* synthetic */ String zzbyt;
        final /* synthetic */ zzjb zzcia;
        final /* synthetic */ boolean zzcin;
        final /* synthetic */ double zzcio;
        final /* synthetic */ boolean zzcip;

        C14006(zzjb com_google_android_gms_internal_zzjb, boolean z, double d, boolean z2, String str) {
            this.zzcia = com_google_android_gms_internal_zzjb;
            this.zzcin = z;
            this.zzcio = d;
            this.zzcip = z2;
            this.zzbyt = str;
        }

        @TargetApi(19)
        public zzc zzg(InputStream inputStream) {
            Bitmap decodeStream;
            Options options = new Options();
            options.inDensity = (int) (160.0d * this.zzcio);
            if (!this.zzcip) {
                options.inPreferredConfig = Config.RGB_565;
            }
            try {
                decodeStream = BitmapFactory.decodeStream(inputStream, null, options);
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Error grabbing image.", e);
                decodeStream = null;
            }
            if (decodeStream == null) {
                this.zzcia.zza(2, this.zzcin);
                return null;
            }
            if (zzs.zzayu()) {
                int width = decodeStream.getWidth();
                int height = decodeStream.getHeight();
                zzkx.m1697v("Decoded image w: " + width + " h:" + height + " bytes: " + decodeStream.getAllocationByteCount());
            }
            return new zzc(new BitmapDrawable(Resources.getSystem(), decodeStream), Uri.parse(this.zzbyt), this.zzcio);
        }

        @TargetApi(19)
        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzg(inputStream);
        }

        public zzc zzsv() {
            this.zzcia.zza(2, this.zzcin);
            return null;
        }

        public /* synthetic */ Object zzsw() {
            return zzsv();
        }
    }

    public interface zza<T extends com.google.android.gms.ads.internal.formats.zzi.zza> {
        T zza(zzjb com_google_android_gms_internal_zzjb, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException;
    }

    class zzb {
        final /* synthetic */ zzjb zzcia;
        public zzfe zzciq;

        zzb(zzjb com_google_android_gms_internal_zzjb) {
            this.zzcia = com_google_android_gms_internal_zzjb;
        }
    }

    static {
        zzchj = TimeUnit.SECONDS.toMillis(60);
    }

    public zzjb(Context context, zzq com_google_android_gms_ads_internal_zzq, zzli com_google_android_gms_internal_zzli, zzav com_google_android_gms_internal_zzav, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz) {
        this.zzako = new Object();
        this.mContext = context;
        this.zzcht = com_google_android_gms_ads_internal_zzq;
        this.zzchs = com_google_android_gms_internal_zzli;
        this.zzcgf = com_google_android_gms_internal_zzko_zza;
        this.zzbnx = com_google_android_gms_internal_zzav;
        this.zzalt = com_google_android_gms_internal_zzdz;
        this.zzbnv = zza(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_ads_internal_zzq, com_google_android_gms_internal_zzav);
        this.zzbnv.zzsh();
        this.zzchu = false;
        this.zzcgw = -2;
        this.zzchv = null;
    }

    private com.google.android.gms.ads.internal.formats.zzi.zza zza(zza com_google_android_gms_internal_zzjb_zza, JSONObject jSONObject, String str) throws ExecutionException, InterruptedException, JSONException {
        if (zzst() || com_google_android_gms_internal_zzjb_zza == null || jSONObject == null) {
            return null;
        }
        JSONObject jSONObject2 = jSONObject.getJSONObject("tracking_urls_and_actions");
        String[] zzd = zzd(jSONObject2, "impression_tracking_urls");
        this.zzchv = zzd == null ? null : Arrays.asList(zzd);
        this.zzchw = jSONObject2.optJSONObject("active_view");
        com.google.android.gms.ads.internal.formats.zzi.zza zza = com_google_android_gms_internal_zzjb_zza.zza(this, jSONObject);
        if (zza == null) {
            com.google.android.gms.ads.internal.util.client.zzb.m1695e("Failed to retrieve ad assets.");
            return null;
        }
        zza.zzb(new zzj(this.mContext, this.zzcht, this.zzbnv, this.zzbnx, jSONObject, zza, this.zzcgf.zzcmx.zzari, str));
        return zza;
    }

    private zzlt<zzc> zza(JSONObject jSONObject, boolean z, boolean z2) throws JSONException {
        String string = z ? jSONObject.getString(NativeProtocol.WEB_DIALOG_URL) : jSONObject.optString(NativeProtocol.WEB_DIALOG_URL);
        double optDouble = jSONObject.optDouble("scale", 1.0d);
        boolean optBoolean = jSONObject.optBoolean("is_transparent", true);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? new zzlr(new zzc(null, Uri.parse(string), optDouble)) : this.zzchs.zza(string, new C14006(this, z, optDouble, optBoolean, string));
        } else {
            zza(0, z);
            return new zzlr(null);
        }
    }

    private void zza(com.google.android.gms.ads.internal.formats.zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza) {
        if (com_google_android_gms_ads_internal_formats_zzi_zza instanceof zzf) {
            zzf com_google_android_gms_ads_internal_formats_zzf = (zzf) com_google_android_gms_ads_internal_formats_zzi_zza;
            zzb com_google_android_gms_internal_zzjb_zzb = new zzb(this);
            zzfe c13973 = new C13973(this, com_google_android_gms_ads_internal_formats_zzf);
            com_google_android_gms_internal_zzjb_zzb.zzciq = c13973;
            this.zzbnv.zza(new C13984(this, c13973));
        }
    }

    private zzko zzb(com.google.android.gms.ads.internal.formats.zzi.zza com_google_android_gms_ads_internal_formats_zzi_zza) {
        int i;
        synchronized (this.zzako) {
            i = this.zzcgw;
            if (com_google_android_gms_ads_internal_formats_zzi_zza == null && this.zzcgw == -2) {
                i = 0;
            }
        }
        return new zzko(this.zzcgf.zzcmx.zzcju, null, this.zzcgf.zzcsu.zzbvk, i, this.zzcgf.zzcsu.zzbvl, this.zzchv, this.zzcgf.zzcsu.orientation, this.zzcgf.zzcsu.zzbvq, this.zzcgf.zzcmx.zzcjx, false, null, null, null, null, null, 0, this.zzcgf.zzarm, this.zzcgf.zzcsu.zzcla, this.zzcgf.zzcso, this.zzcgf.zzcsp, this.zzcgf.zzcsu.zzclg, this.zzchw, i != -2 ? null : com_google_android_gms_ads_internal_formats_zzi_zza, null, null, null, this.zzcgf.zzcsu.zzclt, this.zzcgf.zzcsu.zzclu, null, this.zzcgf.zzcsu.zzbvn, this.zzcgf.zzcsu.zzclx);
    }

    private Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    private void zzb(zzeo com_google_android_gms_internal_zzeo, String str) {
        try {
            zzes zzaa = this.zzcht.zzaa(com_google_android_gms_internal_zzeo.getCustomTemplateId());
            if (zzaa != null) {
                zzaa.zza(com_google_android_gms_internal_zzeo, str);
            }
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc(new StringBuilder(String.valueOf(str).length() + 40).append("Failed to call onCustomClick for asset ").append(str).append(".").toString(), e);
        }
    }

    private JSONObject zzcj(String str) throws ExecutionException, InterruptedException, TimeoutException, JSONException {
        if (zzst()) {
            return null;
        }
        zzlq com_google_android_gms_internal_zzlq = new zzlq();
        this.zzbnv.zza(new C13951(this, str, new zzb(this), com_google_android_gms_internal_zzlq));
        return (JSONObject) com_google_android_gms_internal_zzlq.get(zzchj, TimeUnit.MILLISECONDS);
    }

    private String[] zzd(JSONObject jSONObject, String str) throws JSONException {
        JSONArray optJSONArray = jSONObject.optJSONArray(str);
        if (optJSONArray == null) {
            return null;
        }
        String[] strArr = new String[optJSONArray.length()];
        for (int i = 0; i < optJSONArray.length(); i++) {
            strArr[i] = optJSONArray.getString(i);
        }
        return strArr;
    }

    private static List<Drawable> zzh(List<zzc> list) throws RemoteException {
        List<Drawable> arrayList = new ArrayList();
        for (zzc zzmn : list) {
            arrayList.add((Drawable) zze.zzae(zzmn.zzmn()));
        }
        return arrayList;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzss();
    }

    zzja zza(Context context, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzq com_google_android_gms_ads_internal_zzq, zzav com_google_android_gms_internal_zzav) {
        return new zzja(context, com_google_android_gms_internal_zzko_zza, com_google_android_gms_ads_internal_zzq, com_google_android_gms_internal_zzav);
    }

    zzjc zza(Context context, zzav com_google_android_gms_internal_zzav, com.google.android.gms.internal.zzko.zza com_google_android_gms_internal_zzko_zza, zzdz com_google_android_gms_internal_zzdz, zzq com_google_android_gms_ads_internal_zzq) {
        return new zzjc(context, com_google_android_gms_internal_zzav, com_google_android_gms_internal_zzko_zza, com_google_android_gms_internal_zzdz, com_google_android_gms_ads_internal_zzq);
    }

    public zzlt<zzc> zza(JSONObject jSONObject, String str, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public List<zzlt<zzc>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray jSONArray = z ? jSONObject.getJSONArray(str) : jSONObject.optJSONArray(str);
        List<zzlt<zzc>> arrayList = new ArrayList();
        if (jSONArray == null || jSONArray.length() == 0) {
            zza(0, z);
            return arrayList;
        }
        int length = z3 ? jSONArray.length() : 1;
        for (int i = 0; i < length; i++) {
            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
            if (jSONObject2 == null) {
                jSONObject2 = new JSONObject();
            }
            arrayList.add(zza(jSONObject2, z, z2));
        }
        return arrayList;
    }

    public Future<zzc> zza(JSONObject jSONObject, String str, boolean z) throws JSONException {
        JSONObject jSONObject2 = jSONObject.getJSONObject(str);
        boolean optBoolean = jSONObject2.optBoolean("require", true);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, optBoolean, z);
    }

    public void zza(int i, boolean z) {
        if (z) {
            zzas(i);
        }
    }

    public void zzas(int i) {
        synchronized (this.zzako) {
            this.zzchu = true;
            this.zzcgw = i;
        }
    }

    public zzlt<zzmd> zzc(JSONObject jSONObject, String str) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject(str);
        if (optJSONObject == null) {
            return new zzlr(null);
        }
        if (!TextUtils.isEmpty(optJSONObject.optString("vast_xml"))) {
            return zza(this.mContext, this.zzbnx, this.zzcgf, this.zzalt, this.zzcht).zzg(optJSONObject);
        }
        com.google.android.gms.ads.internal.util.client.zzb.zzdi("Required field 'vast_xml' is missing");
        return new zzlr(null);
    }

    protected zza zze(JSONObject jSONObject) throws ExecutionException, InterruptedException, JSONException, TimeoutException {
        if (zzst() || jSONObject == null) {
            return null;
        }
        String string = jSONObject.getString("template_id");
        boolean z = this.zzcgf.zzcmx.zzasa != null ? this.zzcgf.zzcmx.zzasa.zzboj : false;
        boolean z2 = this.zzcgf.zzcmx.zzasa != null ? this.zzcgf.zzcmx.zzasa.zzbol : false;
        if ("2".equals(string)) {
            return new zzjd(z, z2);
        }
        if (AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(string)) {
            return new zzje(z, z2);
        }
        if ("3".equals(string)) {
            String string2 = jSONObject.getString("custom_template_id");
            zzlq com_google_android_gms_internal_zzlq = new zzlq();
            zzlb.zzcvl.post(new C13962(this, com_google_android_gms_internal_zzlq, string2));
            if (com_google_android_gms_internal_zzlq.get(zzchj, TimeUnit.MILLISECONDS) != null) {
                return new zzjf(z);
            }
            string2 = "No handler for custom template: ";
            String valueOf = String.valueOf(jSONObject.getString("custom_template_id"));
            com.google.android.gms.ads.internal.util.client.zzb.m1695e(valueOf.length() != 0 ? string2.concat(valueOf) : new String(string2));
        } else {
            zzas(0);
        }
        return null;
    }

    public zzlt<com.google.android.gms.ads.internal.formats.zza> zzf(JSONObject jSONObject) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
        if (optJSONObject == null) {
            return new zzlr(null);
        }
        String optString = optJSONObject.optString(MimeTypes.BASE_TYPE_TEXT);
        int optInt = optJSONObject.optInt("text_size", -1);
        Integer zzb = zzb(optJSONObject, "text_color");
        Integer zzb2 = zzb(optJSONObject, "bg_color");
        int optInt2 = optJSONObject.optInt("animation_ms", AdError.NETWORK_ERROR_CODE);
        int optInt3 = optJSONObject.optInt("presentation_ms", 4000);
        int i = (this.zzcgf.zzcmx.zzasa == null || this.zzcgf.zzcmx.zzasa.versionCode < 2) ? 1 : this.zzcgf.zzcmx.zzasa.zzbom;
        List arrayList = new ArrayList();
        if (optJSONObject.optJSONArray("images") != null) {
            arrayList = zza(optJSONObject, "images", false, false, true);
        } else {
            arrayList.add(zza(optJSONObject, "image", false, false));
        }
        return zzls.zza(zzls.zzo(arrayList), new C13995(this, optString, zzb2, zzb, optInt, optInt3, optInt2, i));
    }

    public zzko zzss() {
        try {
            this.zzbnv.zzsi();
            String zzsu = zzsu();
            JSONObject zzcj = zzcj(zzsu);
            com.google.android.gms.ads.internal.formats.zzi.zza zza = zza(zze(zzcj), zzcj, zzsu);
            zza(zza);
            return zzb(zza);
        } catch (CancellationException e) {
            if (!this.zzchu) {
                zzas(0);
            }
            return zzb(null);
        } catch (ExecutionException e2) {
            if (this.zzchu) {
                zzas(0);
            }
            return zzb(null);
        } catch (InterruptedException e3) {
            if (this.zzchu) {
                zzas(0);
            }
            return zzb(null);
        } catch (Throwable e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Malformed native JSON response.", e4);
            if (this.zzchu) {
                zzas(0);
            }
            return zzb(null);
        } catch (Throwable e42) {
            com.google.android.gms.ads.internal.util.client.zzb.zzc("Timeout when loading native ad.", e42);
            if (this.zzchu) {
                zzas(0);
            }
            return zzb(null);
        }
    }

    public boolean zzst() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzchu;
        }
        return z;
    }

    String zzsu() {
        return UUID.randomUUID().toString();
    }
}
