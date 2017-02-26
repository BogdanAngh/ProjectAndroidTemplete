package com.google.android.gms.internal;

import android.content.ComponentName;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.facebook.share.internal.ShareConstants;
import com.google.android.gms.ads.internal.formats.zzg;
import com.google.android.gms.ads.internal.overlay.zzd;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.tagmanager.DataLayer;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Future;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

@zzji
public final class zzfd {
    public static final zzfe zzbpj;
    public static final zzfe zzbpk;
    public static final zzfe zzbpl;
    public static final zzfe zzbpm;
    public static final zzfe zzbpn;
    public static final zzfe zzbpo;
    public static final zzfe zzbpp;
    public static final zzfe zzbpq;
    public static final zzfe zzbpr;
    public static final zzfe zzbps;
    public static final zzfe zzbpt;
    public static final zzfe zzbpu;
    public static final zzfe zzbpv;
    public static final zzfe zzbpw;
    public static final zzfe zzbpx;
    public static final zzfe zzbpy;
    public static final zzfe zzbpz;
    public static final zzfm zzbqa;
    public static final zzfe zzbqb;
    public static final zzfe zzbqc;
    public static final zzfe zzbqd;

    /* renamed from: com.google.android.gms.internal.zzfd.1 */
    class C12961 implements zzfe {
        C12961() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.2 */
    class C12972 implements zzfe {
        C12972() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            zzg zzxs = com_google_android_gms_internal_zzmd.zzxs();
            if (zzxs != null) {
                zzxs.zzmu();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.3 */
    class C12983 implements zzfe {
        C12983() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            String str = (String) map.get("ty");
            String str2 = (String) map.get("td");
            try {
                int parseInt = Integer.parseInt((String) map.get("tx"));
                int parseInt2 = Integer.parseInt(str);
                int parseInt3 = Integer.parseInt(str2);
                zzav zzxe = com_google_android_gms_internal_zzmd.zzxe();
                if (zzxe != null) {
                    zzxe.zzaz().zza(parseInt, parseInt2, parseInt3);
                }
            } catch (NumberFormatException e) {
                zzb.zzdi("Could not parse touch parameters from gmsg.");
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.4 */
    class C12994 implements zzfe {
        C12994() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (((Boolean) zzdr.zzbhd.get()).booleanValue()) {
                com_google_android_gms_internal_zzmd.zzam(!Boolean.parseBoolean((String) map.get("disabled")));
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.5 */
    class C13005 implements zzfe {
        C13005() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            String str = (String) map.get(NativeProtocol.WEB_DIALOG_ACTION);
            if ("pause".equals(str)) {
                com_google_android_gms_internal_zzmd.zzey();
            } else if ("resume".equals(str)) {
                com_google_android_gms_internal_zzmd.zzez();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.6 */
    class C13016 implements zzfe {
        C13016() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (map.keySet().contains(TtmlNode.START)) {
                com_google_android_gms_internal_zzmd.zzxc().zzyb();
            } else if (map.keySet().contains("stop")) {
                com_google_android_gms_internal_zzmd.zzxc().zzyc();
            } else if (map.keySet().contains("cancel")) {
                com_google_android_gms_internal_zzmd.zzxc().zzyd();
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.7 */
    class C13027 implements zzfe {
        C13027() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            if (map.keySet().contains(TtmlNode.START)) {
                com_google_android_gms_internal_zzmd.zzan(true);
            }
            if (map.keySet().contains("stop")) {
                com_google_android_gms_internal_zzmd.zzan(false);
            }
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.8 */
    class C13038 implements zzfe {
        C13038() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            com_google_android_gms_internal_zzmd.zza("locationReady", zzu.zzgm().zza((View) com_google_android_gms_internal_zzmd, (WindowManager) com_google_android_gms_internal_zzmd.getContext().getSystemService("window")));
            zzb.zzdi("GET LOCATION COMPILED");
        }
    }

    /* renamed from: com.google.android.gms.internal.zzfd.9 */
    class C13049 implements zzfe {
        C13049() {
        }

        public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
            String str = (String) map.get("urls");
            if (TextUtils.isEmpty(str)) {
                zzb.zzdi("URLs missing in canOpenURLs GMSG.");
                return;
            }
            String[] split = str.split(",");
            Map hashMap = new HashMap();
            PackageManager packageManager = com_google_android_gms_internal_zzmd.getContext().getPackageManager();
            for (String str2 : split) {
                String[] split2 = str2.split(";", 2);
                hashMap.put(str2, Boolean.valueOf(packageManager.resolveActivity(new Intent(split2.length > 1 ? split2[1].trim() : "android.intent.action.VIEW", Uri.parse(split2[0].trim())), NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) != null));
            }
            com_google_android_gms_internal_zzmd.zza("openableURLs", hashMap);
        }
    }

    static {
        zzbpj = new C12961();
        zzbpk = new C13049();
        zzbpl = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                PackageManager packageManager = com_google_android_gms_internal_zzmd.getContext().getPackageManager();
                try {
                    try {
                        JSONArray jSONArray = new JSONObject((String) map.get(ShareConstants.WEB_DIALOG_PARAM_DATA)).getJSONArray("intents");
                        JSONObject jSONObject = new JSONObject();
                        for (int i = 0; i < jSONArray.length(); i++) {
                            try {
                                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                                String optString = jSONObject2.optString(TtmlNode.ATTR_ID);
                                Object optString2 = jSONObject2.optString("u");
                                Object optString3 = jSONObject2.optString("i");
                                Object optString4 = jSONObject2.optString("m");
                                Object optString5 = jSONObject2.optString(TtmlNode.TAG_P);
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
                                    jSONObject.put(optString, packageManager.resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST) != null);
                                } catch (Throwable e) {
                                    zzb.zzb("Error constructing openable urls response.", e);
                                }
                            } catch (Throwable e2) {
                                zzb.zzb("Error parsing the intent data.", e2);
                            }
                        }
                        com_google_android_gms_internal_zzmd.zzb("openableIntents", jSONObject);
                    } catch (JSONException e3) {
                        com_google_android_gms_internal_zzmd.zzb("openableIntents", new JSONObject());
                    }
                } catch (JSONException e4) {
                    com_google_android_gms_internal_zzmd.zzb("openableIntents", new JSONObject());
                }
            }
        };
        zzbpm = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                String str = (String) map.get("u");
                if (str == null) {
                    zzb.zzdi("URL missing from click GMSG.");
                    return;
                }
                Uri zza;
                Future future;
                Uri parse = Uri.parse(str);
                try {
                    zzav zzxe = com_google_android_gms_internal_zzmd.zzxe();
                    if (zzxe != null && zzxe.zzc(parse)) {
                        zza = zzxe.zza(parse, com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.getView());
                        future = (Future) new zzll(com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.zzxf().zzda, zza.toString()).zzrz();
                    }
                } catch (zzaw e) {
                    String str2 = "Unable to append parameter to URL: ";
                    str = String.valueOf(str);
                    zzb.zzdi(str.length() != 0 ? str2.concat(str) : new String(str2));
                }
                zza = parse;
                future = (Future) new zzll(com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.zzxf().zzda, zza.toString()).zzrz();
            }
        };
        zzbpn = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                zzd zzxa = com_google_android_gms_internal_zzmd.zzxa();
                if (zzxa != null) {
                    zzxa.close();
                    return;
                }
                zzxa = com_google_android_gms_internal_zzmd.zzxb();
                if (zzxa != null) {
                    zzxa.close();
                } else {
                    zzb.zzdi("A GMSG tried to close something that wasn't an overlay.");
                }
            }
        };
        zzbpo = new zzfe() {
            private void zzd(zzmd com_google_android_gms_internal_zzmd) {
                zzb.zzdh("Received support message, responding.");
                com.google.android.gms.ads.internal.zzd zzec = com_google_android_gms_internal_zzmd.zzec();
                if (!(zzec == null || zzec.zzamr == null)) {
                    com_google_android_gms_internal_zzmd.getContext();
                }
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put(DataLayer.EVENT_KEY, "checkSupport");
                    jSONObject.put("supports", false);
                    com_google_android_gms_internal_zzmd.zzb("appStreaming", jSONObject);
                } catch (Throwable th) {
                }
            }

            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                if ("checkSupport".equals(map.get(NativeProtocol.WEB_DIALOG_ACTION))) {
                    zzd(com_google_android_gms_internal_zzmd);
                    return;
                }
                zzd zzxa = com_google_android_gms_internal_zzmd.zzxa();
                if (zzxa != null) {
                    zzxa.zzg(com_google_android_gms_internal_zzmd, map);
                }
            }
        };
        zzbpp = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                com_google_android_gms_internal_zzmd.zzal(AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("custom_close")));
            }
        };
        zzbpq = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                String str = (String) map.get("u");
                if (str == null) {
                    zzb.zzdi("URL missing from httpTrack GMSG.");
                } else {
                    Future future = (Future) new zzll(com_google_android_gms_internal_zzmd.getContext(), com_google_android_gms_internal_zzmd.zzxf().zzda, str).zzrz();
                }
            }
        };
        zzbpr = new zzfe() {
            public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
                String str = "Received log message: ";
                String valueOf = String.valueOf((String) map.get("string"));
                zzb.zzdh(valueOf.length() != 0 ? str.concat(valueOf) : new String(str));
            }
        };
        zzbps = new C12972();
        zzbpt = new C12983();
        zzbpu = new C12994();
        zzbpv = new C13005();
        zzbpw = new zzfo();
        zzbpx = new zzfp();
        zzbpy = new zzft();
        zzbpz = new zzfc();
        zzbqa = new zzfm();
        zzbqb = new C13016();
        zzbqc = new C13027();
        zzbqd = new C13038();
    }
}
