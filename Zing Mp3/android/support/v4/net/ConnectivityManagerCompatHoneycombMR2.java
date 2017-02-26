package android.support.v4.net;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import com.mp3download.zingmp3.C1569R;

class ConnectivityManagerCompatHoneycombMR2 {
    ConnectivityManagerCompatHoneycombMR2() {
    }

    public static boolean isActiveNetworkMetered(ConnectivityManager cm) {
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return true;
        }
        switch (info.getType()) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
            case C1569R.styleable.Toolbar_contentInsetEnd /*6*/:
                return true;
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
            case C1569R.styleable.Toolbar_contentInsetLeft /*7*/:
            case C1569R.styleable.Toolbar_contentInsetStartWithNavigation /*9*/:
                return false;
            default:
                return true;
        }
    }
}
