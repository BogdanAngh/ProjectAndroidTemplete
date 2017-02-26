package com.google.android.gms.internal;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.text.TextUtils;
import com.facebook.appevents.AppEventsConstants;
import com.facebook.internal.NativeProtocol;
import com.facebook.internal.ServerProtocol;
import com.google.android.gms.ads.internal.overlay.AdLauncherIntentInfoParcel;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zze;
import com.google.android.gms.ads.internal.zzu;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@zzji
public final class zzfl implements zzfe {
    private final zze zzbqt;
    private final zzhq zzbqu;
    private final zzfg zzbqw;

    public static class zza {
        private final zzmd zzbnz;

        public zza(zzmd com_google_android_gms_internal_zzmd) {
            this.zzbnz = com_google_android_gms_internal_zzmd;
        }

        public Intent zza(Context context, Map<String, String> map) {
            ActivityManager activityManager = (ActivityManager) context.getSystemService("activity");
            String str = (String) map.get("u");
            if (TextUtils.isEmpty(str)) {
                return null;
            }
            if (this.zzbnz != null) {
                str = zzu.zzgm().zza(this.zzbnz, str);
            }
            Uri parse = Uri.parse(str);
            boolean parseBoolean = Boolean.parseBoolean((String) map.get("use_first_package"));
            boolean parseBoolean2 = Boolean.parseBoolean((String) map.get("use_running_process"));
            Uri build = "http".equalsIgnoreCase(parse.getScheme()) ? parse.buildUpon().scheme("https").build() : "https".equalsIgnoreCase(parse.getScheme()) ? parse.buildUpon().scheme("http").build() : null;
            ArrayList arrayList = new ArrayList();
            Intent zzf = zzf(parse);
            Intent zzf2 = zzf(build);
            ResolveInfo zza = zza(context, zzf, arrayList);
            if (zza != null) {
                return zza(zzf, zza);
            }
            if (zzf2 != null) {
                ResolveInfo zza2 = zza(context, zzf2);
                if (zza2 != null) {
                    Intent zza3 = zza(zzf, zza2);
                    if (zza(context, zza3) != null) {
                        return zza3;
                    }
                }
            }
            if (arrayList.size() == 0) {
                return zzf;
            }
            if (parseBoolean2 && activityManager != null) {
                List<RunningAppProcessInfo> runningAppProcesses = activityManager.getRunningAppProcesses();
                if (runningAppProcesses != null) {
                    Iterator it = arrayList.iterator();
                    while (it.hasNext()) {
                        ResolveInfo resolveInfo = (ResolveInfo) it.next();
                        for (RunningAppProcessInfo runningAppProcessInfo : runningAppProcesses) {
                            if (runningAppProcessInfo.processName.equals(resolveInfo.activityInfo.packageName)) {
                                return zza(zzf, resolveInfo);
                            }
                        }
                    }
                }
            }
            return parseBoolean ? zza(zzf, (ResolveInfo) arrayList.get(0)) : zzf;
        }

        public Intent zza(Intent intent, ResolveInfo resolveInfo) {
            Intent intent2 = new Intent(intent);
            intent2.setClassName(resolveInfo.activityInfo.packageName, resolveInfo.activityInfo.name);
            return intent2;
        }

        public ResolveInfo zza(Context context, Intent intent) {
            return zza(context, intent, new ArrayList());
        }

        public ResolveInfo zza(Context context, Intent intent, ArrayList<ResolveInfo> arrayList) {
            PackageManager packageManager = context.getPackageManager();
            if (packageManager == null) {
                return null;
            }
            ResolveInfo resolveInfo;
            Collection queryIntentActivities = packageManager.queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            ResolveInfo resolveActivity = packageManager.resolveActivity(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
            if (!(queryIntentActivities == null || resolveActivity == null)) {
                for (int i = 0; i < queryIntentActivities.size(); i++) {
                    resolveInfo = (ResolveInfo) queryIntentActivities.get(i);
                    if (resolveActivity != null && resolveActivity.activityInfo.name.equals(resolveInfo.activityInfo.name)) {
                        resolveInfo = resolveActivity;
                        break;
                    }
                }
            }
            resolveInfo = null;
            arrayList.addAll(queryIntentActivities);
            return resolveInfo;
        }

        public Intent zzf(Uri uri) {
            if (uri == null) {
                return null;
            }
            Intent intent = new Intent("android.intent.action.VIEW");
            intent.addFlags(268435456);
            intent.setData(uri);
            intent.setAction("android.intent.action.VIEW");
            return intent;
        }
    }

    public zzfl(zzfg com_google_android_gms_internal_zzfg, zze com_google_android_gms_ads_internal_zze, zzhq com_google_android_gms_internal_zzhq) {
        this.zzbqw = com_google_android_gms_internal_zzfg;
        this.zzbqt = com_google_android_gms_ads_internal_zze;
        this.zzbqu = com_google_android_gms_internal_zzhq;
    }

    private static boolean zzd(Map<String, String> map) {
        return AppEventsConstants.EVENT_PARAM_VALUE_YES.equals(map.get("custom_close"));
    }

    private static int zze(Map<String, String> map) {
        String str = (String) map.get("o");
        if (str != null) {
            if (TtmlNode.TAG_P.equalsIgnoreCase(str)) {
                return zzu.zzgo().zzvx();
            }
            if ("l".equalsIgnoreCase(str)) {
                return zzu.zzgo().zzvw();
            }
            if ("c".equalsIgnoreCase(str)) {
                return zzu.zzgo().zzvy();
            }
        }
        return -1;
    }

    private static void zzf(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        Context context = com_google_android_gms_internal_zzmd.getContext();
        if (TextUtils.isEmpty((String) map.get("u"))) {
            zzb.zzdi("Destination url cannot be empty.");
            return;
        }
        try {
            com_google_android_gms_internal_zzmd.zzxc().zza(new AdLauncherIntentInfoParcel(new zza(com_google_android_gms_internal_zzmd).zza(context, (Map) map)));
        } catch (ActivityNotFoundException e) {
            zzb.zzdi(e.getMessage());
        }
    }

    private void zzs(boolean z) {
        if (this.zzbqu != null) {
            this.zzbqu.zzt(z);
        }
    }

    public void zza(zzmd com_google_android_gms_internal_zzmd, Map<String, String> map) {
        String str = (String) map.get("a");
        if (str == null) {
            zzb.zzdi("Action missing from an open GMSG.");
        } else if (this.zzbqt == null || this.zzbqt.zzfe()) {
            zzme zzxc = com_google_android_gms_internal_zzmd.zzxc();
            if ("expand".equalsIgnoreCase(str)) {
                if (com_google_android_gms_internal_zzmd.zzxg()) {
                    zzb.zzdi("Cannot expand WebView that is already expanded.");
                    return;
                }
                zzs(false);
                zzxc.zza(zzd(map), zze(map));
            } else if ("webapp".equalsIgnoreCase(str)) {
                str = (String) map.get("u");
                zzs(false);
                if (str != null) {
                    zzxc.zza(zzd(map), zze(map), str);
                } else {
                    zzxc.zza(zzd(map), zze(map), (String) map.get("html"), (String) map.get("baseurl"));
                }
            } else if ("in_app_purchase".equalsIgnoreCase(str)) {
                str = (String) map.get("product_id");
                String str2 = (String) map.get("report_urls");
                if (this.zzbqw == null) {
                    return;
                }
                if (str2 == null || str2.isEmpty()) {
                    this.zzbqw.zza(str, new ArrayList());
                } else {
                    this.zzbqw.zza(str, new ArrayList(Arrays.asList(str2.split(" "))));
                }
            } else if ("app".equalsIgnoreCase(str) && ServerProtocol.DIALOG_RETURN_SCOPES_TRUE.equalsIgnoreCase((String) map.get("system_browser"))) {
                zzs(true);
                zzf(com_google_android_gms_internal_zzmd, map);
            } else {
                zzs(true);
                str = (String) map.get("u");
                zzxc.zza(new AdLauncherIntentInfoParcel((String) map.get("i"), !TextUtils.isEmpty(str) ? zzu.zzgm().zza(com_google_android_gms_internal_zzmd, str) : str, (String) map.get("m"), (String) map.get(TtmlNode.TAG_P), (String) map.get("c"), (String) map.get("f"), (String) map.get("e")));
            }
        } else {
            this.zzbqt.zzy((String) map.get("u"));
        }
    }
}
