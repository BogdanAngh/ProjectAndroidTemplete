package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import com.google.android.gms.ads.internal.overlay.zzc;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.drive.ExecutionOptions;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzgd
public final class zzdf {
    public static final zzdg zzvV;
    public static final zzdg zzvW;
    public static final zzdg zzvX;
    public static final zzdg zzvY;
    public static final zzdg zzvZ;
    public static final zzdg zzwa;
    public static final zzdg zzwb;
    public static final zzdg zzwc;
    public static final zzdg zzwd;
    public static final zzdg zzwe;
    public static final zzdg zzwf;

    /* renamed from: com.google.android.gms.internal.zzdf.1 */
    static class C04441 implements zzdg {
        C04441() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.2 */
    static class C04452 implements zzdg {
        C04452() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            String str = (String) map.get("urls");
            if (TextUtils.isEmpty(str)) {
                zzb.zzaC("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] split = str.split(",");
            Map hashMap = new HashMap();
            PackageManager packageManager = com_google_android_gms_internal_zzid.getContext().getPackageManager();
            for (String str2 : split) {
                String[] split2 = str2.split(";", 2);
                hashMap.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(split2.length > 1 ? split2[1].trim() : "android.intent.action.VIEW", Uri.parse(split2[0].trim())), ExecutionOptions.MAX_TRACKING_TAG_STRING_LENGTH) != null));
            }
            com_google_android_gms_internal_zzid.zzc("openableURLs", hashMap);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.3 */
    static class C04463 implements zzdg {
        C04463() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            PackageManager packageManager = com_google_android_gms_internal_zzid.getContext().getPackageManager();
            try {
                try {
                    JSONArray jSONArray = new JSONObject((String) map.get("data")).getJSONArray("intents");
                    JSONObject jSONObject = new JSONObject();
                    for (int i = 0; i < jSONArray.length(); i++) {
                        try {
                            JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                            String optString = jSONObject2.optString("id");
                            Object optString2 = jSONObject2.optString("u");
                            Object optString3 = jSONObject2.optString("i");
                            Object optString4 = jSONObject2.optString("m");
                            Object optString5 = jSONObject2.optString("p");
                            Object optString6 = jSONObject2.optString("c");
                            jSONObject2.optString("f");
                            jSONObject2.optString("e");
                            Intent intent = new Intent();
                            if (!TextUtils.isEmpty(optString2)) {
                                intent.setData(Uri.parse(optString2));
                            }
                            if (!TextUtils.isEmpty(optString3)) {
                                intent.setAction(optString3);
                            }
                            if (!TextUtils.isEmpty(optString4)) {
                                intent.setType(optString4);
                            }
                            if (!TextUtils.isEmpty(optString5)) {
                                intent.setPackage(optString5);
                            }
                            if (!TextUtils.isEmpty(optString6)) {
                                String[] split = optString6.split("/", 2);
                                if (split.length == 2) {
                                    intent.setComponent(new ComponentName(split[0], split[1]));
                                }
                            }
                            try {
                                jSONObject.put(optString, packageManager.resolveActivity(intent, ExecutionOptions.MAX_TRACKING_TAG_STRING_LENGTH) != null);
                            } catch (Throwable e) {
                                zzb.zzb("Error constructing openable urls response.", e);
                            }
                        } catch (Throwable e2) {
                            zzb.zzb("Error parsing the intent data.", e2);
                        }
                    }
                    com_google_android_gms_internal_zzid.zzb("openableIntents", jSONObject);
                } catch (JSONException e3) {
                    com_google_android_gms_internal_zzid.zzb("openableIntents", new JSONObject());
                }
            } catch (JSONException e4) {
                com_google_android_gms_internal_zzid.zzb("openableIntents", new JSONObject());
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.4 */
    static class C04474 implements zzdg {
        C04474() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            String str = (String) map.get("u");
            if (str == null) {
                zzb.zzaC("URL missing from click GMSG.");
                return;
            }
            Uri zza;
            Uri parse = Uri.parse(str);
            try {
                zzan zzgH = com_google_android_gms_internal_zzid.zzgH();
                if (zzgH != null && zzgH.zzb(parse)) {
                    zza = zzgH.zza(parse, com_google_android_gms_internal_zzid.getContext());
                    new zzhp(com_google_android_gms_internal_zzid.getContext(), com_google_android_gms_internal_zzid.zzgI().zzGG, zza.toString()).zzgi();
                }
            } catch (zzao e) {
                zzb.zzaC("Unable to append parameter to URL: " + str);
            }
            zza = parse;
            new zzhp(com_google_android_gms_internal_zzid.getContext(), com_google_android_gms_internal_zzid.zzgI().zzGG, zza.toString()).zzgi();
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.5 */
    static class C04485 implements zzdg {
        C04485() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            zzc zzgD = com_google_android_gms_internal_zzid.zzgD();
            if (zzgD != null) {
                zzgD.close();
                return;
            }
            zzgD = com_google_android_gms_internal_zzid.zzgE();
            if (zzgD != null) {
                zzgD.close();
            } else {
                zzb.zzaC("A GMSG tried to close something that wasn't an overlay.");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.6 */
    static class C04496 implements zzdg {
        C04496() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            com_google_android_gms_internal_zzid.zzC("1".equals(map.get("custom_close")));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.7 */
    static class C04507 implements zzdg {
        C04507() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            String str = (String) map.get("u");
            if (str == null) {
                zzb.zzaC("URL missing from httpTrack GMSG.");
            } else {
                new zzhp(com_google_android_gms_internal_zzid.getContext(), com_google_android_gms_internal_zzid.zzgI().zzGG, str).zzgi();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.8 */
    static class C04518 implements zzdg {
        C04518() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            zzb.zzaA("Received log message: " + ((String) map.get("string")));
        }
    }

    /* renamed from: com.google.android.gms.internal.zzdf.9 */
    static class C04529 implements zzdg {
        C04529() {
        }

        public void zza(zzid com_google_android_gms_internal_zzid, Map<String, String> map) {
            String str = (String) map.get("ty");
            String str2 = (String) map.get("td");
            try {
                int parseInt = Integer.parseInt((String) map.get("tx"));
                int parseInt2 = Integer.parseInt(str);
                int parseInt3 = Integer.parseInt(str2);
                zzan zzgH = com_google_android_gms_internal_zzid.zzgH();
                if (zzgH != null) {
                    zzgH.zzab().zza(parseInt, parseInt2, parseInt3);
                }
            } catch (NumberFormatException e) {
                zzb.zzaC("Could not parse touch parameters from gmsg.");
            }
        }
    }

    static {
        zzvV = new C04441();
        zzvW = new C04452();
        zzvX = new C04463();
        zzvY = new C04474();
        zzvZ = new C04485();
        zzwa = new C04496();
        zzwb = new C04507();
        zzwc = new C04518();
        zzwd = new C04529();
        zzwe = new zzdo();
        zzwf = new zzds();
    }
}
