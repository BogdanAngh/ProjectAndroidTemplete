package com.facebook.ads.internal.p004a;

import android.content.Context;
import android.net.Uri;
import com.mp3download.zingmp3.C1569R;
import java.util.Map;

/* renamed from: com.facebook.ads.internal.a.b */
public class C0430b {
    public static C0429a m321a(Context context, String str, Uri uri, Map<String, String> map) {
        String authority = uri.getAuthority();
        String queryParameter = uri.getQueryParameter("video_url");
        Object obj = -1;
        switch (authority.hashCode()) {
            case -1458789996:
                if (authority.equals("passthrough")) {
                    obj = 2;
                    break;
                }
                break;
            case 109770977:
                if (authority.equals("store")) {
                    obj = null;
                    break;
                }
                break;
            case 1546100943:
                if (authority.equals("open_link")) {
                    obj = 1;
                    break;
                }
                break;
        }
        switch (obj) {
            case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                return queryParameter != null ? null : new C0431c(context, str, uri, map);
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                return new C0432d(context, str, uri, map);
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                return new C0433e(context, str, uri, map);
            default:
                return new C0434f(context, str, uri);
        }
    }

    public static boolean m322a(String str) {
        return "store".equalsIgnoreCase(str) || "open_link".equalsIgnoreCase(str);
    }
}
