package com.google.android.gms.common.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import com.google.android.gms.C0074R;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.internal.zzle;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public final class zzf {
    public static String zzb(Context context, int i, String str) {
        Resources resources = context.getResources();
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                if (zzle.zzb(resources)) {
                    return resources.getString(C0074R.string.common_google_play_services_install_text_tablet, new Object[]{str});
                }
                return resources.getString(C0074R.string.common_google_play_services_install_text_phone, new Object[]{str});
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return resources.getString(C0074R.string.common_google_play_services_update_text, new Object[]{str});
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return resources.getString(C0074R.string.common_google_play_services_enable_text, new Object[]{str});
            case Place.TYPE_ART_GALLERY /*5*/:
                return resources.getString(C0074R.string.common_google_play_services_invalid_account_text);
            case Place.TYPE_BAKERY /*7*/:
                return resources.getString(C0074R.string.common_google_play_services_network_error_text);
            case Place.TYPE_BAR /*9*/:
                return resources.getString(C0074R.string.common_google_play_services_unsupported_text, new Object[]{str});
            case Place.TYPE_CAMPGROUND /*16*/:
                return resources.getString(C0074R.string.common_google_play_services_api_unavailable_text, new Object[]{str});
            case Place.TYPE_CAR_DEALER /*17*/:
                return resources.getString(C0074R.string.common_google_play_services_sign_in_failed_text);
            case Place.TYPE_CAR_RENTAL /*18*/:
                return resources.getString(C0074R.string.common_google_play_services_updating_text, new Object[]{str});
            case Place.TYPE_GENERAL_CONTRACTOR /*42*/:
                return resources.getString(C0074R.string.common_android_wear_update_text, new Object[]{str});
            default:
                return resources.getString(C0074R.string.common_google_play_services_unknown_issue);
        }
    }

    public static String zzc(Context context, int i, String str) {
        Resources resources = context.getResources();
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                if (zzle.zzb(resources)) {
                    return resources.getString(C0074R.string.common_google_play_services_install_text_tablet, new Object[]{str});
                }
                return resources.getString(C0074R.string.common_google_play_services_install_text_phone, new Object[]{str});
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return resources.getString(C0074R.string.common_google_play_services_update_text, new Object[]{str});
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return resources.getString(C0074R.string.common_google_play_services_enable_text, new Object[]{str});
            case Place.TYPE_ART_GALLERY /*5*/:
                return resources.getString(C0074R.string.common_google_play_services_invalid_account_text);
            case Place.TYPE_BAKERY /*7*/:
                return resources.getString(C0074R.string.common_google_play_services_network_error_text);
            case Place.TYPE_BAR /*9*/:
                return resources.getString(C0074R.string.common_google_play_services_unsupported_text, new Object[]{str});
            case Place.TYPE_CAMPGROUND /*16*/:
                return resources.getString(C0074R.string.common_google_play_services_api_unavailable_text, new Object[]{str});
            case Place.TYPE_CAR_DEALER /*17*/:
                return resources.getString(C0074R.string.common_google_play_services_sign_in_failed_text);
            case Place.TYPE_CAR_RENTAL /*18*/:
                return resources.getString(C0074R.string.common_google_play_services_updating_text, new Object[]{str});
            case Place.TYPE_GENERAL_CONTRACTOR /*42*/:
                return resources.getString(C0074R.string.common_android_wear_notification_needs_update_text, new Object[]{str});
            default:
                return resources.getString(C0074R.string.common_google_play_services_unknown_issue);
        }
    }

    public static final String zzg(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return resources.getString(C0074R.string.common_google_play_services_install_title);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return resources.getString(C0074R.string.common_google_play_services_update_title);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return resources.getString(C0074R.string.common_google_play_services_enable_title);
            case GameHelper.CLIENT_APPSTATE /*4*/:
            case Place.TYPE_ATM /*6*/:
                return null;
            case Place.TYPE_ART_GALLERY /*5*/:
                Log.e("GooglePlayServicesUtil", "An invalid account was specified when connecting. Please provide a valid account.");
                return resources.getString(C0074R.string.common_google_play_services_invalid_account_title);
            case Place.TYPE_BAKERY /*7*/:
                Log.e("GooglePlayServicesUtil", "Network error occurred. Please retry request later.");
                return resources.getString(C0074R.string.common_google_play_services_network_error_title);
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
                Log.e("GooglePlayServicesUtil", "Internal error occurred. Please see logs for detailed information");
                return null;
            case Place.TYPE_BAR /*9*/:
                Log.e("GooglePlayServicesUtil", "Google Play services is invalid. Cannot recover.");
                return resources.getString(C0074R.string.common_google_play_services_unsupported_title);
            case Place.TYPE_BEAUTY_SALON /*10*/:
                Log.e("GooglePlayServicesUtil", "Developer error occurred. Please see logs for detailed information");
                return null;
            case Place.TYPE_BICYCLE_STORE /*11*/:
                Log.e("GooglePlayServicesUtil", "The application is not licensed to the user.");
                return null;
            case Place.TYPE_CAMPGROUND /*16*/:
                Log.e("GooglePlayServicesUtil", "One of the API components you attempted to connect to is not available.");
                return null;
            case Place.TYPE_CAR_DEALER /*17*/:
                Log.e("GooglePlayServicesUtil", "The specified account could not be signed in.");
                return resources.getString(C0074R.string.common_google_play_services_sign_in_failed_title);
            case Place.TYPE_CAR_RENTAL /*18*/:
                return resources.getString(C0074R.string.common_google_play_services_updating_title);
            case Place.TYPE_GENERAL_CONTRACTOR /*42*/:
                return resources.getString(C0074R.string.common_android_wear_update_title);
            default:
                Log.e("GooglePlayServicesUtil", "Unexpected error code " + i);
                return null;
        }
    }

    public static String zzh(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return resources.getString(C0074R.string.common_google_play_services_install_button);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
            case Place.TYPE_GENERAL_CONTRACTOR /*42*/:
                return resources.getString(C0074R.string.common_google_play_services_update_button);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return resources.getString(C0074R.string.common_google_play_services_enable_button);
            default:
                return resources.getString(17039370);
        }
    }

    public static final String zzi(Context context, int i) {
        Resources resources = context.getResources();
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return resources.getString(C0074R.string.common_google_play_services_install_title);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return resources.getString(C0074R.string.common_google_play_services_update_title);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return resources.getString(C0074R.string.common_google_play_services_enable_title);
            case GameHelper.CLIENT_APPSTATE /*4*/:
            case Place.TYPE_ATM /*6*/:
                return null;
            case Place.TYPE_ART_GALLERY /*5*/:
                Log.e("GooglePlayServicesUtil", "An invalid account was specified when connecting. Please provide a valid account.");
                return resources.getString(C0074R.string.common_google_play_services_invalid_account_title);
            case Place.TYPE_BAKERY /*7*/:
                Log.e("GooglePlayServicesUtil", "Network error occurred. Please retry request later.");
                return resources.getString(C0074R.string.common_google_play_services_network_error_title);
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
                Log.e("GooglePlayServicesUtil", "Internal error occurred. Please see logs for detailed information");
                return null;
            case Place.TYPE_BAR /*9*/:
                Log.e("GooglePlayServicesUtil", "Google Play services is invalid. Cannot recover.");
                return resources.getString(C0074R.string.common_google_play_services_unsupported_title);
            case Place.TYPE_BEAUTY_SALON /*10*/:
                Log.e("GooglePlayServicesUtil", "Developer error occurred. Please see logs for detailed information");
                return null;
            case Place.TYPE_BICYCLE_STORE /*11*/:
                Log.e("GooglePlayServicesUtil", "The application is not licensed to the user.");
                return null;
            case Place.TYPE_CAMPGROUND /*16*/:
                Log.e("GooglePlayServicesUtil", "One of the API components you attempted to connect to is not available.");
                return null;
            case Place.TYPE_CAR_DEALER /*17*/:
                Log.e("GooglePlayServicesUtil", "The specified account could not be signed in.");
                return resources.getString(C0074R.string.common_google_play_services_sign_in_failed_title);
            case Place.TYPE_CAR_RENTAL /*18*/:
                return resources.getString(C0074R.string.common_google_play_services_updating_title);
            case Place.TYPE_GENERAL_CONTRACTOR /*42*/:
                return resources.getString(C0074R.string.common_android_wear_update_title);
            default:
                Log.e("GooglePlayServicesUtil", "Unexpected error code " + i);
                return null;
        }
    }
}
