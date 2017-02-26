package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.SimpleArrayMap;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.gms.C1044R;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.util.zzi;
import com.google.android.gms.internal.zzsz;
import com.mp3download.zingmp3.C1569R;

public final class zzg {
    private static final SimpleArrayMap<String, String> DO;

    static {
        DO = new SimpleArrayMap();
    }

    public static String zzcb(Context context) {
        String str = context.getApplicationInfo().name;
        if (TextUtils.isEmpty(str)) {
            str = context.getPackageName();
            try {
                str = zzsz.zzco(context).zzik(context.getPackageName()).toString();
            } catch (NameNotFoundException e) {
            }
        }
        return str;
    }

    @Nullable
    public static String zzg(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return resources.getString(C1044R.string.common_google_play_services_install_title);
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return resources.getString(C1044R.string.common_google_play_services_update_title);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return resources.getString(C1044R.string.common_google_play_services_enable_title);
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
            case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                return null;
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                Log.e("GoogleApiAvailability", "An invalid account was specified when connecting. Please provide a valid account.");
                return zzu(context, "common_google_play_services_invalid_account_title");
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                Log.e("GoogleApiAvailability", "Network error occurred. Please retry request later.");
                return zzu(context, "common_google_play_services_network_error_title");
            case C1569R.styleable.Toolbar_contentInsetRight /*8*/:
                Log.e("GoogleApiAvailability", "Internal error occurred. Please see logs for detailed information");
                return null;
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                Log.e("GoogleApiAvailability", "Google Play services is invalid. Cannot recover.");
                return null;
            case C1569R.styleable.Toolbar_contentInsetEndWithActions /*10*/:
                Log.e("GoogleApiAvailability", "Developer error occurred. Please see logs for detailed information");
                return null;
            case C1569R.styleable.Toolbar_popupTheme /*11*/:
                Log.e("GoogleApiAvailability", "The application is not licensed to the user.");
                return null;
            case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                Log.e("GoogleApiAvailability", "One of the API components you attempted to connect to is not available.");
                return null;
            case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                Log.e("GoogleApiAvailability", "The specified account could not be signed in.");
                return zzu(context, "common_google_play_services_sign_in_failed_title");
            case C1569R.styleable.Toolbar_maxButtonHeight /*20*/:
                Log.e("GoogleApiAvailability", "The current user profile is restricted and could not use authenticated features.");
                return zzu(context, "common_google_play_services_restricted_profile_title");
            default:
                Log.e("GoogleApiAvailability", "Unexpected error code " + i);
                return null;
        }
    }

    private static String zzg(Context context, String str, String str2) {
        Resources resources = context.getResources();
        String zzu = zzu(context, str);
        if (zzu == null) {
            zzu = resources.getString(C1044R.string.common_google_play_services_unknown_issue);
        }
        return String.format(resources.getConfiguration().locale, zzu, new Object[]{str2});
    }

    @NonNull
    public static String zzh(Context context, int i) {
        String zzu = i == 6 ? zzu(context, "common_google_play_services_resolution_required_title") : zzg(context, i);
        return zzu == null ? context.getResources().getString(C1044R.string.common_google_play_services_notification_ticker) : zzu;
    }

    @NonNull
    public static String zzi(Context context, int i) {
        Resources resources = context.getResources();
        String zzcb = zzcb(context);
        switch (i) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return resources.getString(C1044R.string.common_google_play_services_install_text, new Object[]{zzcb});
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                if (zzi.zzci(context)) {
                    return resources.getString(C1044R.string.common_google_play_services_wear_update_text);
                }
                return resources.getString(C1044R.string.common_google_play_services_update_text, new Object[]{zzcb});
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return resources.getString(C1044R.string.common_google_play_services_enable_text, new Object[]{zzcb});
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                return zzg(context, "common_google_play_services_invalid_account_text", zzcb);
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
                return zzg(context, "common_google_play_services_network_error_text", zzcb);
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return resources.getString(C1044R.string.common_google_play_services_unsupported_text, new Object[]{zzcb});
            case C1569R.styleable.Toolbar_titleMarginEnd /*16*/:
                return zzg(context, "common_google_play_services_api_unavailable_text", zzcb);
            case C1569R.styleable.Toolbar_titleMarginTop /*17*/:
                return zzg(context, "common_google_play_services_sign_in_failed_text", zzcb);
            case C1569R.styleable.Toolbar_titleMarginBottom /*18*/:
                return resources.getString(C1044R.string.common_google_play_services_updating_text, new Object[]{zzcb});
            case C1569R.styleable.Toolbar_maxButtonHeight /*20*/:
                return zzg(context, "common_google_play_services_restricted_profile_text", zzcb);
            default:
                return resources.getString(C1044R.string.common_google_play_services_unknown_issue, new Object[]{zzcb});
        }
    }

    @NonNull
    public static String zzj(Context context, int i) {
        return i == 6 ? zzg(context, "common_google_play_services_resolution_required_text", zzcb(context)) : zzi(context, i);
    }

    @NonNull
    public static String zzk(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return resources.getString(C1044R.string.common_google_play_services_install_button);
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return resources.getString(C1044R.string.common_google_play_services_update_button);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                return resources.getString(C1044R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    @Nullable
    private static String zzu(Context context, String str) {
        synchronized (DO) {
            String str2 = (String) DO.get(str);
            if (str2 != null) {
                return str2;
            }
            Resources remoteResource = GooglePlayServicesUtil.getRemoteResource(context);
            if (remoteResource == null) {
                return null;
            }
            int identifier = remoteResource.getIdentifier(str, "string", GooglePlayServicesUtil.GOOGLE_PLAY_SERVICES_PACKAGE);
            if (identifier == 0) {
                String str3 = "GoogleApiAvailability";
                String str4 = "Missing resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            Object string = remoteResource.getString(identifier);
            if (TextUtils.isEmpty(string)) {
                str3 = "GoogleApiAvailability";
                str4 = "Got empty resource: ";
                str2 = String.valueOf(str);
                Log.w(str3, str2.length() != 0 ? str4.concat(str2) : new String(str4));
                return null;
            }
            DO.put(str, string);
            return string;
        }
    }
}
