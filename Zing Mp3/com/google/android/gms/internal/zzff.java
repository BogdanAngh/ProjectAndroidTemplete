package com.google.android.gms.internal;

import android.content.Context;
import android.text.TextUtils;
import com.facebook.GraphResponse;
import com.facebook.internal.NativeProtocol;
import com.google.android.gms.ads.internal.util.client.VersionInfoParcel;
import com.google.android.gms.ads.internal.zzu;
import com.mp3download.zingmp3.BuildConfig;
import java.io.BufferedOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public class zzff implements zzfe {
    private final Context mContext;
    private final VersionInfoParcel zzanu;

    /* renamed from: com.google.android.gms.internal.zzff.1 */
    class C13061 implements Runnable {
        final /* synthetic */ Map zzboh;
        final /* synthetic */ zzmd zzbqe;
        final /* synthetic */ zzff zzbqf;

        /* renamed from: com.google.android.gms.internal.zzff.1.1 */
        class C13051 implements Runnable {
            final /* synthetic */ JSONObject zzbqg;
            final /* synthetic */ C13061 zzbqh;

            C13051(C13061 c13061, JSONObject jSONObject) {
                this.zzbqh = c13061;
                this.zzbqg = jSONObject;
            }

            public void run() {
                this.zzbqh.zzbqe.zzb("fetchHttpRequestCompleted", this.zzbqg);
                com.google.android.gms.ads.internal.util.client.zzb.zzdg("Dispatched http response.");
            }
        }

        C13061(zzff com_google_android_gms_internal_zzff, Map map, zzmd com_google_android_gms_internal_zzmd) {
            this.zzbqf = com_google_android_gms_internal_zzff;
            this.zzboh = map;
            this.zzbqe = com_google_android_gms_internal_zzmd;
        }

        public void run() {
            com.google.android.gms.ads.internal.util.client.zzb.zzdg("Received Http request.");
            JSONObject zzbc = this.zzbqf.zzbc((String) this.zzboh.get("http_request"));
            if (zzbc == null) {
                com.google.android.gms.ads.internal.util.client.zzb.m1695e("Response should not be null.");
            } else {
                zzlb.zzcvl.post(new C13051(this, zzbc));
            }
        }
    }

    @zzji
    static class zza {
        private final String mValue;
        private final String zzbcn;

        public zza(String str, String str2) {
            this.zzbcn = str;
            this.mValue = str2;
        }

        public String getKey() {
            return this.zzbcn;
        }

        public String getValue() {
            return this.mValue;
        }
    }

    @zzji
    static class zzb {
        private final String zzbqi;
        private final URL zzbqj;
        private final ArrayList<zza> zzbqk;
        private final String zzbql;

        public zzb(String str, URL url, ArrayList<zza> arrayList, String str2) {
            this.zzbqi = str;
            this.zzbqj = url;
            if (arrayList == null) {
                this.zzbqk = new ArrayList();
            } else {
                this.zzbqk = arrayList;
            }
            this.zzbql = str2;
        }

        public String zznc() {
            return this.zzbqi;
        }

        public URL zznd() {
            return this.zzbqj;
        }

        public ArrayList<zza> zzne() {
            return this.zzbqk;
        }

        public String zznf() {
            return this.zzbql;
        }
    }

    @zzji
    class zzc {
        final /* synthetic */ zzff zzbqf;
        private final zzd zzbqm;
        private final boolean zzbqn;
        private final String zzbqo;

        public zzc(zzff com_google_android_gms_internal_zzff, boolean z, zzd com_google_android_gms_internal_zzff_zzd, String str) {
            this.zzbqf = com_google_android_gms_internal_zzff;
            this.zzbqn = z;
            this.zzbqm = com_google_android_gms_internal_zzff_zzd;
            this.zzbqo = str;
        }

        public String getReason() {
            return this.zzbqo;
        }

        public boolean isSuccess() {
            return this.zzbqn;
        }

        public zzd zzng() {
            return this.zzbqm;
        }
    }

    @zzji
    static class zzd {
        private final String zzbna;
        private final String zzbqi;
        private final int zzbqp;
        private final List<zza> zzbqq;

        public zzd(String str, int i, List<zza> list, String str2) {
            this.zzbqi = str;
            this.zzbqp = i;
            if (list == null) {
                this.zzbqq = new ArrayList();
            } else {
                this.zzbqq = list;
            }
            this.zzbna = str2;
        }

        public String getBody() {
            return this.zzbna;
        }

        public int getResponseCode() {
            return this.zzbqp;
        }

        public String zznc() {
            return this.zzbqi;
        }

        public Iterable<zza> zznh() {
            return this.zzbqq;
        }
    }

    public zzff(Context context, VersionInfoParcel versionInfoParcel) {
        this.mContext = context;
        this.zzanu = versionInfoParcel;
    }

    protected zzc zza(zzb com_google_android_gms_internal_zzff_zzb) {
        try {
            HttpURLConnection httpURLConnection = (HttpURLConnection) com_google_android_gms_internal_zzff_zzb.zznd().openConnection();
            zzu.zzgm().zza(this.mContext, this.zzanu.zzda, false, httpURLConnection);
            Iterator it = com_google_android_gms_internal_zzff_zzb.zzne().iterator();
            while (it.hasNext()) {
                zza com_google_android_gms_internal_zzff_zza = (zza) it.next();
                httpURLConnection.addRequestProperty(com_google_android_gms_internal_zzff_zza.getKey(), com_google_android_gms_internal_zzff_zza.getValue());
            }
            if (!TextUtils.isEmpty(com_google_android_gms_internal_zzff_zzb.zznf())) {
                httpURLConnection.setDoOutput(true);
                byte[] bytes = com_google_android_gms_internal_zzff_zzb.zznf().getBytes();
                httpURLConnection.setFixedLengthStreamingMode(bytes.length);
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(httpURLConnection.getOutputStream());
                bufferedOutputStream.write(bytes);
                bufferedOutputStream.close();
            }
            List arrayList = new ArrayList();
            if (httpURLConnection.getHeaderFields() != null) {
                for (Entry entry : httpURLConnection.getHeaderFields().entrySet()) {
                    for (String com_google_android_gms_internal_zzff_zza2 : (List) entry.getValue()) {
                        arrayList.add(new zza((String) entry.getKey(), com_google_android_gms_internal_zzff_zza2));
                    }
                }
            }
            return new zzc(this, true, new zzd(com_google_android_gms_internal_zzff_zzb.zznc(), httpURLConnection.getResponseCode(), arrayList, zzu.zzgm().zza(new InputStreamReader(httpURLConnection.getInputStream()))), null);
        } catch (Exception e) {
            return new zzc(this, false, null, e.toString());
        }
    }

    protected JSONObject zza(zzd com_google_android_gms_internal_zzff_zzd) {
        JSONObject jSONObject = new JSONObject();
        try {
            jSONObject.put("http_request_id", com_google_android_gms_internal_zzff_zzd.zznc());
            if (com_google_android_gms_internal_zzff_zzd.getBody() != null) {
                jSONObject.put(TtmlNode.TAG_BODY, com_google_android_gms_internal_zzff_zzd.getBody());
            }
            JSONArray jSONArray = new JSONArray();
            for (zza com_google_android_gms_internal_zzff_zza : com_google_android_gms_internal_zzff_zzd.zznh()) {
                jSONArray.put(new JSONObject().put("key", com_google_android_gms_internal_zzff_zza.getKey()).put("value", com_google_android_gms_internal_zzff_zza.getValue()));
            }
            jSONObject.put("headers", jSONArray);
            jSONObject.put("response_code", com_google_android_gms_internal_zzff_zzd.getResponseCode());
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing JSON for http response.", e);
        }
        return jSONObject;
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        zzla.zza(new C13061(this, map, com_google_android_gms_internal_zzmd));
    }

    protected zzb zzb(JSONObject jSONObject) {
        URL url;
        String optString = jSONObject.optString("http_request_id");
        String optString2 = jSONObject.optString(NativeProtocol.WEB_DIALOG_URL);
        String optString3 = jSONObject.optString("post_body", null);
        try {
            url = new URL(optString2);
        } catch (Throwable e) {
            com.google.android.gms.ads.internal.util.client.zzb.zzb("Error constructing http request.", e);
            url = null;
        }
        ArrayList arrayList = new ArrayList();
        JSONArray optJSONArray = jSONObject.optJSONArray("headers");
        if (optJSONArray == null) {
            optJSONArray = new JSONArray();
        }
        for (int i = 0; i < optJSONArray.length(); i++) {
            JSONObject optJSONObject = optJSONArray.optJSONObject(i);
            if (optJSONObject != null) {
                arrayList.add(new zza(optJSONObject.optString("key"), optJSONObject.optString("value")));
            }
        }
        return new zzb(optString, url, arrayList, optString3);
    }

    public JSONObject zzbc(String str) {
        try {
            JSONObject jSONObject = new JSONObject(str);
            JSONObject jSONObject2 = new JSONObject();
            Object obj = BuildConfig.FLAVOR;
            try {
                obj = jSONObject.optString("http_request_id");
                zzc zza = zza(zzb(jSONObject));
                if (zza.isSuccess()) {
                    jSONObject2.put("response", zza(zza.zzng()));
                    jSONObject2.put(GraphResponse.SUCCESS_KEY, true);
                    return jSONObject2;
                }
                jSONObject2.put("response", new JSONObject().put("http_request_id", obj));
                jSONObject2.put(GraphResponse.SUCCESS_KEY, false);
                jSONObject2.put("reason", zza.getReason());
                return jSONObject2;
            } catch (Exception e) {
                try {
                    jSONObject2.put("response", new JSONObject().put("http_request_id", obj));
                    jSONObject2.put(GraphResponse.SUCCESS_KEY, false);
                    jSONObject2.put("reason", e.toString());
                    return jSONObject2;
                } catch (JSONException e2) {
                    return jSONObject2;
                }
            }
        } catch (JSONException e3) {
            com.google.android.gms.ads.internal.util.client.zzb.m1695e("The request is not a valid JSON.");
            try {
                return new JSONObject().put(GraphResponse.SUCCESS_KEY, false);
            } catch (JSONException e4) {
                return new JSONObject();
            }
        }
    }
}
