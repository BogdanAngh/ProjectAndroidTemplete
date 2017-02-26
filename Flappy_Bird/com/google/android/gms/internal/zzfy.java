package com.google.android.gms.internal;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.formats.zzc;
import com.google.android.gms.ads.internal.formats.zzf;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.zzm;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.dynamic.zze;
import com.google.android.gms.plus.PlusShare;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
public class zzfy implements Callable<zzha> {
    private static final long zzBF;
    private final Context mContext;
    private final zzho zzBG;
    private final zzm zzBH;
    private final zzbc zzBI;
    private boolean zzBJ;
    private List<String> zzBK;
    private final com.google.android.gms.internal.zzha.zza zzBs;
    private int zzBv;
    private final Object zzqt;
    private final zzan zzvA;

    /* renamed from: com.google.android.gms.internal.zzfy.2 */
    class C02342 implements Runnable {
        final /* synthetic */ zzfy zzBO;
        final /* synthetic */ zzhs zzBP;
        final /* synthetic */ String zzBQ;

        C02342(zzfy com_google_android_gms_internal_zzfy, zzhs com_google_android_gms_internal_zzhs, String str) {
            this.zzBO = com_google_android_gms_internal_zzfy;
            this.zzBP = com_google_android_gms_internal_zzhs;
            this.zzBQ = str;
        }

        public void run() {
            this.zzBP.zzf(this.zzBO.zzBH.zzbo().get(this.zzBQ));
        }
    }

    public interface zza<T extends com.google.android.gms.ads.internal.formats.zzg.zza> {
        T zza(zzfy com_google_android_gms_internal_zzfy, JSONObject jSONObject) throws JSONException, InterruptedException, ExecutionException;
    }

    class zzb {
        final /* synthetic */ zzfy zzBO;
        public zzdg zzBX;

        zzb(zzfy com_google_android_gms_internal_zzfy) {
            this.zzBO = com_google_android_gms_internal_zzfy;
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfy.1 */
    class C04641 implements zzdg {
        final /* synthetic */ zzbb zzBL;
        final /* synthetic */ zzb zzBM;
        final /* synthetic */ zzhs zzBN;
        final /* synthetic */ zzfy zzBO;

        C04641(zzfy com_google_android_gms_internal_zzfy, zzbb com_google_android_gms_internal_zzbb, zzb com_google_android_gms_internal_zzfy_zzb, zzhs com_google_android_gms_internal_zzhs) {
            this.zzBO = com_google_android_gms_internal_zzfy;
            this.zzBL = com_google_android_gms_internal_zzbb;
            this.zzBM = com_google_android_gms_internal_zzfy_zzb;
            this.zzBN = com_google_android_gms_internal_zzhs;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            this.zzBL.zzb("/nativeAdPreProcess", this.zzBM.zzBX);
            try {
                String str = (String) map.get("success");
                if (!TextUtils.isEmpty(str)) {
                    this.zzBN.zzf(new JSONObject(str).getJSONArray("ads").getJSONObject(0));
                    return;
                }
            } catch (Throwable e) {
                com.google.android.gms.ads.internal.util.client.zzb.zzb("Malformed native JSON response.", e);
            }
            this.zzBO.zzB(0);
            zzu.zza(this.zzBO.zzfr(), (Object) "Unable to set the ad state error!");
            this.zzBN.zzf(null);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfy.3 */
    class C04653 implements zzdg {
        final /* synthetic */ zzfy zzBO;
        final /* synthetic */ zzf zzBR;

        C04653(zzfy com_google_android_gms_internal_zzfy, zzf com_google_android_gms_ads_internal_formats_zzf) {
            this.zzBO = com_google_android_gms_internal_zzfy;
            this.zzBR = com_google_android_gms_ads_internal_formats_zzf;
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            this.zzBO.zzb(this.zzBR, (String) map.get("asset"));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfy.4 */
    class C04664 implements com.google.android.gms.internal.zzhu.zza<zzc, com.google.android.gms.ads.internal.formats.zza> {
        final /* synthetic */ zzfy zzBO;
        final /* synthetic */ String zzBS;
        final /* synthetic */ Integer zzBT;
        final /* synthetic */ Integer zzBU;
        final /* synthetic */ int zzBV;

        C04664(zzfy com_google_android_gms_internal_zzfy, String str, Integer num, Integer num2, int i) {
            this.zzBO = com_google_android_gms_internal_zzfy;
            this.zzBS = str;
            this.zzBT = num;
            this.zzBU = num2;
            this.zzBV = i;
        }

        public com.google.android.gms.ads.internal.formats.zza zza(zzc com_google_android_gms_ads_internal_formats_zzc) {
            com.google.android.gms.ads.internal.formats.zza com_google_android_gms_ads_internal_formats_zza;
            if (com_google_android_gms_ads_internal_formats_zzc != null) {
                try {
                    if (!TextUtils.isEmpty(this.zzBS)) {
                        com_google_android_gms_ads_internal_formats_zza = new com.google.android.gms.ads.internal.formats.zza(this.zzBS, (Drawable) zze.zzn(com_google_android_gms_ads_internal_formats_zzc.zzdw()), this.zzBT, this.zzBU, this.zzBV > 0 ? Integer.valueOf(this.zzBV) : null);
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

        public /* synthetic */ Object zze(Object obj) {
            return zza((zzc) obj);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfy.5 */
    class C04675 implements com.google.android.gms.internal.zzho.zza<zzc> {
        final /* synthetic */ zzfy zzBO;
        final /* synthetic */ boolean zzBW;
        final /* synthetic */ String zzyL;

        C04675(zzfy com_google_android_gms_internal_zzfy, boolean z, String str) {
            this.zzBO = com_google_android_gms_internal_zzfy;
            this.zzBW = z;
            this.zzyL = str;
        }

        public zzc zzfs() {
            this.zzBO.zza(2, this.zzBW);
            return null;
        }

        public /* synthetic */ Object zzft() {
            return zzfs();
        }

        public zzc zzg(InputStream inputStream) {
            byte[] zzk;
            try {
                zzk = zzlg.zzk(inputStream);
            } catch (IOException e) {
                zzk = null;
            }
            if (zzk == null) {
                this.zzBO.zza(2, this.zzBW);
                return null;
            }
            Bitmap decodeByteArray = BitmapFactory.decodeByteArray(zzk, 0, zzk.length);
            if (decodeByteArray != null) {
                return new zzc(new BitmapDrawable(Resources.getSystem(), decodeByteArray), Uri.parse(this.zzyL));
            }
            this.zzBO.zza(2, this.zzBW);
            return null;
        }

        public /* synthetic */ Object zzh(InputStream inputStream) {
            return zzg(inputStream);
        }
    }

    static {
        zzBF = TimeUnit.SECONDS.toMillis(60);
    }

    public zzfy(Context context, zzm com_google_android_gms_ads_internal_zzm, zzbc com_google_android_gms_internal_zzbc, zzho com_google_android_gms_internal_zzho, zzan com_google_android_gms_internal_zzan, com.google.android.gms.internal.zzha.zza com_google_android_gms_internal_zzha_zza) {
        this.zzqt = new Object();
        this.mContext = context;
        this.zzBH = com_google_android_gms_ads_internal_zzm;
        this.zzBG = com_google_android_gms_internal_zzho;
        this.zzBI = com_google_android_gms_internal_zzbc;
        this.zzBs = com_google_android_gms_internal_zzha_zza;
        this.zzvA = com_google_android_gms_internal_zzan;
        this.zzBJ = false;
        this.zzBv = -2;
        this.zzBK = null;
    }

    private com.google.android.gms.ads.internal.formats.zzg.zza zza(zzbb com_google_android_gms_internal_zzbb, zza com_google_android_gms_internal_zzfy_zza, JSONObject jSONObject) throws ExecutionException, InterruptedException, JSONException {
        if (zzfr()) {
            return null;
        }
        String[] zzc = zzc(jSONObject.getJSONObject("tracking_urls_and_actions"), "impression_tracking_urls");
        this.zzBK = zzc == null ? null : Arrays.asList(zzc);
        com.google.android.gms.ads.internal.formats.zzg.zza zza = com_google_android_gms_internal_zzfy_zza.zza(this, jSONObject);
        if (zza == null) {
            com.google.android.gms.ads.internal.util.client.zzb.zzaz("Failed to retrieve ad assets.");
            return null;
        }
        zza.zza(new zzg(this.mContext, this.zzBH, com_google_android_gms_internal_zzbb, this.zzvA, jSONObject, zza));
        return zza;
    }

    private zzha zza(com.google.android.gms.ads.internal.formats.zzg.zza com_google_android_gms_ads_internal_formats_zzg_zza) {
        int i;
        synchronized (this.zzqt) {
            i = this.zzBv;
            if (com_google_android_gms_ads_internal_formats_zzg_zza == null && this.zzBv == -2) {
                i = 0;
            }
        }
        return new zzha(this.zzBs.zzFr.zzCm, null, this.zzBs.zzFs.zzxF, i, this.zzBs.zzFs.zzxG, this.zzBK, this.zzBs.zzFs.orientation, this.zzBs.zzFs.zzxJ, this.zzBs.zzFr.zzCp, false, null, null, null, null, null, 0, this.zzBs.zzpN, this.zzBs.zzFs.zzCJ, this.zzBs.zzFo, this.zzBs.zzFp, this.zzBs.zzFs.zzCP, this.zzBs.zzFl, i != -2 ? null : com_google_android_gms_ads_internal_formats_zzg_zza, this.zzBs.zzFr.zzCC);
    }

    private zzhv<zzc> zza(JSONObject jSONObject, boolean z, boolean z2) throws JSONException {
        String string = z ? jSONObject.getString(PlusShare.KEY_CALL_TO_ACTION_URL) : jSONObject.optString(PlusShare.KEY_CALL_TO_ACTION_URL);
        if (!TextUtils.isEmpty(string)) {
            return z2 ? new zzht(new zzc(null, Uri.parse(string))) : this.zzBG.zza(string, new C04675(this, z, string));
        } else {
            zza(0, z);
            return new zzht(null);
        }
    }

    private void zza(com.google.android.gms.ads.internal.formats.zzg.zza com_google_android_gms_ads_internal_formats_zzg_zza, zzbb com_google_android_gms_internal_zzbb) {
        if (com_google_android_gms_ads_internal_formats_zzg_zza instanceof zzf) {
            zzf com_google_android_gms_ads_internal_formats_zzf = (zzf) com_google_android_gms_ads_internal_formats_zzg_zza;
            zzb com_google_android_gms_internal_zzfy_zzb = new zzb(this);
            zzdg c04653 = new C04653(this, com_google_android_gms_ads_internal_formats_zzf);
            com_google_android_gms_internal_zzfy_zzb.zzBX = c04653;
            com_google_android_gms_internal_zzbb.zza("/nativeAdCustomClick", c04653);
        }
    }

    private Integer zzb(JSONObject jSONObject, String str) {
        try {
            JSONObject jSONObject2 = jSONObject.getJSONObject(str);
            return Integer.valueOf(Color.rgb(jSONObject2.getInt("r"), jSONObject2.getInt("g"), jSONObject2.getInt("b")));
        } catch (JSONException e) {
            return null;
        }
    }

    private JSONObject zzb(zzbb com_google_android_gms_internal_zzbb) throws TimeoutException, JSONException {
        if (zzfr()) {
            return null;
        }
        zzhs com_google_android_gms_internal_zzhs = new zzhs();
        zzb com_google_android_gms_internal_zzfy_zzb = new zzb(this);
        zzdg c04641 = new C04641(this, com_google_android_gms_internal_zzbb, com_google_android_gms_internal_zzfy_zzb, com_google_android_gms_internal_zzhs);
        com_google_android_gms_internal_zzfy_zzb.zzBX = c04641;
        com_google_android_gms_internal_zzbb.zza("/nativeAdPreProcess", c04641);
        com_google_android_gms_internal_zzbb.zza("google.afma.nativeAds.preProcessJsonGmsg", new JSONObject(this.zzBs.zzFs.zzCI));
        return (JSONObject) com_google_android_gms_internal_zzhs.get(zzBF, TimeUnit.MILLISECONDS);
    }

    private void zzb(zzcs com_google_android_gms_internal_zzcs, String str) {
        try {
            zzcw zzq = this.zzBH.zzq(com_google_android_gms_internal_zzcs.getCustomTemplateId());
            if (zzq != null) {
                zzq.zza(com_google_android_gms_internal_zzcs, str);
            }
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Failed to call onCustomClick for asset " + str + ".", e);
        }
    }

    private String[] zzc(JSONObject jSONObject, String str) throws JSONException {
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

    private zzbb zzfq() throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        if (zzfr()) {
            return null;
        }
        zzbb com_google_android_gms_internal_zzbb = (zzbb) this.zzBI.zza(this.mContext, this.zzBs.zzFr.zzpJ, (this.zzBs.zzFs.zzzG.indexOf("https") == 0 ? "https:" : "http:") + ((String) zzbz.zzur.get())).get(zzBF, TimeUnit.MILLISECONDS);
        com_google_android_gms_internal_zzbb.zza(this.zzBH, this.zzBH, this.zzBH, this.zzBH, false, null, null, null, null);
        return com_google_android_gms_internal_zzbb;
    }

    public /* synthetic */ Object call() throws Exception {
        return zzfp();
    }

    public void zzB(int i) {
        synchronized (this.zzqt) {
            this.zzBJ = true;
            this.zzBv = i;
        }
    }

    public zzhv<zzc> zza(JSONObject jSONObject, String str, boolean z, boolean z2) throws JSONException {
        JSONObject jSONObject2 = z ? jSONObject.getJSONObject(str) : jSONObject.optJSONObject(str);
        if (jSONObject2 == null) {
            jSONObject2 = new JSONObject();
        }
        return zza(jSONObject2, z, z2);
    }

    public List<zzhv<zzc>> zza(JSONObject jSONObject, String str, boolean z, boolean z2, boolean z3) throws JSONException {
        JSONArray jSONArray = z ? jSONObject.getJSONArray(str) : jSONObject.optJSONArray(str);
        List<zzhv<zzc>> arrayList = new ArrayList();
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
            zzB(i);
        }
    }

    protected zza zzd(JSONObject jSONObject) throws JSONException, TimeoutException {
        if (zzfr()) {
            return null;
        }
        String string = jSONObject.getString("template_id");
        boolean z = this.zzBs.zzFr.zzqb != null ? this.zzBs.zzFr.zzqb.zzvC : false;
        boolean z2 = this.zzBs.zzFr.zzqb != null ? this.zzBs.zzFr.zzqb.zzvE : false;
        if ("2".equals(string)) {
            return new zzfz(z, z2);
        }
        if ("1".equals(string)) {
            return new zzga(z, z2);
        }
        if ("3".equals(string)) {
            String string2 = jSONObject.getString("custom_template_id");
            zzhs com_google_android_gms_internal_zzhs = new zzhs();
            zzhl.zzGk.post(new C02342(this, com_google_android_gms_internal_zzhs, string2));
            if (com_google_android_gms_internal_zzhs.get(zzBF, TimeUnit.MILLISECONDS) != null) {
                return new zzgb(z);
            }
            com.google.android.gms.ads.internal.util.client.zzb.zzaz("No handler for custom template: " + jSONObject.getString("custom_template_id"));
        } else {
            zzB(0);
        }
        return null;
    }

    public zzhv<com.google.android.gms.ads.internal.formats.zza> zze(JSONObject jSONObject) throws JSONException {
        JSONObject optJSONObject = jSONObject.optJSONObject("attribution");
        if (optJSONObject == null) {
            return new zzht(null);
        }
        String optString = optJSONObject.optString("text");
        int optInt = optJSONObject.optInt("text_size", -1);
        Integer zzb = zzb(optJSONObject, "text_color");
        return zzhu.zza(zza(optJSONObject, "image", false, false), new C04664(this, optString, zzb(optJSONObject, "bg_color"), zzb, optInt));
    }

    public zzha zzfp() {
        try {
            zzbb zzfq = zzfq();
            JSONObject zzb = zzb(zzfq);
            com.google.android.gms.ads.internal.formats.zzg.zza zza = zza(zzfq, zzd(zzb), zzb);
            zza(zza, zzfq);
            return zza(zza);
        } catch (CancellationException e) {
            if (!this.zzBJ) {
                zzB(0);
            }
            return zza(null);
        } catch (ExecutionException e2) {
            if (this.zzBJ) {
                zzB(0);
            }
            return zza(null);
        } catch (InterruptedException e3) {
            if (this.zzBJ) {
                zzB(0);
            }
            return zza(null);
        } catch (Throwable e4) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Malformed native JSON response.", e4);
            if (this.zzBJ) {
                zzB(0);
            }
            return zza(null);
        } catch (Throwable e42) {
            com.google.android.gms.ads.internal.util.client.zzb.zzd("Timeout when loading native ad.", e42);
            if (this.zzBJ) {
                zzB(0);
            }
            return zza(null);
        }
    }

    public boolean zzfr() {
        boolean z;
        synchronized (this.zzqt) {
            z = this.zzBJ;
        }
        return z;
    }
}
