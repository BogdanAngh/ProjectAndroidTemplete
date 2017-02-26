package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.customtabs.CustomTabsClient;
import android.support.customtabs.CustomTabsServiceConnection;
import android.support.customtabs.CustomTabsSession;
import com.facebook.internal.NativeProtocol;
import java.util.List;

@zzji
public class zzef implements zzasi {
    @Nullable
    private CustomTabsSession zzbmg;
    @Nullable
    private CustomTabsClient zzbmh;
    @Nullable
    private CustomTabsServiceConnection zzbmi;
    @Nullable
    private zza zzbmj;

    public interface zza {
        void zzmh();

        void zzmi();
    }

    public static boolean zzn(Context context) {
        PackageManager packageManager = context.getPackageManager();
        if (packageManager == null) {
            return false;
        }
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse("http://www.example.com"));
        ResolveInfo resolveActivity = packageManager.resolveActivity(intent, 0);
        List queryIntentActivities = packageManager.queryIntentActivities(intent, NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST);
        if (queryIntentActivities == null || resolveActivity == null) {
            return false;
        }
        for (int i = 0; i < queryIntentActivities.size(); i++) {
            if (resolveActivity.activityInfo.name.equals(((ResolveInfo) queryIntentActivities.get(i)).activityInfo.name)) {
                return resolveActivity.activityInfo.packageName.equals(zzasg.zzfa(context));
            }
        }
        return false;
    }

    public boolean mayLaunchUrl(Uri uri, Bundle bundle, List<Bundle> list) {
        if (this.zzbmh == null) {
            return false;
        }
        CustomTabsSession zzmf = zzmf();
        return zzmf != null ? zzmf.mayLaunchUrl(uri, bundle, list) : false;
    }

    public void zza(CustomTabsClient customTabsClient) {
        this.zzbmh = customTabsClient;
        this.zzbmh.warmup(0);
        if (this.zzbmj != null) {
            this.zzbmj.zzmh();
        }
    }

    public void zza(zza com_google_android_gms_internal_zzef_zza) {
        this.zzbmj = com_google_android_gms_internal_zzef_zza;
    }

    public void zzd(Activity activity) {
        if (this.zzbmi != null) {
            activity.unbindService(this.zzbmi);
            this.zzbmh = null;
            this.zzbmg = null;
            this.zzbmi = null;
        }
    }

    public void zze(Activity activity) {
        if (this.zzbmh == null) {
            String zzfa = zzasg.zzfa(activity);
            if (zzfa != null) {
                this.zzbmi = new zzash(this);
                CustomTabsClient.bindCustomTabsService(activity, zzfa, this.zzbmi);
            }
        }
    }

    @Nullable
    public CustomTabsSession zzmf() {
        if (this.zzbmh == null) {
            this.zzbmg = null;
        } else if (this.zzbmg == null) {
            this.zzbmg = this.zzbmh.newSession(null);
        }
        return this.zzbmg;
    }

    public void zzmg() {
        this.zzbmh = null;
        this.zzbmg = null;
        if (this.zzbmj != null) {
            this.zzbmj.zzmi();
        }
    }
}
