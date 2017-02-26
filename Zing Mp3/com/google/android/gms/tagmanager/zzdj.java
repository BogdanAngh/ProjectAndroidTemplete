package com.google.android.gms.tagmanager;

import android.content.Context;
import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.Logger;
import com.google.android.gms.analytics.Tracker;
import com.mp3download.zingmp3.BuildConfig;
import com.mp3download.zingmp3.C1569R;

public class zzdj {
    private Tracker aE;
    private GoogleAnalytics aG;
    private Context mContext;

    static class zza implements Logger {
        zza() {
        }

        private static int zzaai(int i) {
            switch (i) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    return 0;
                case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                    return 1;
                case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                    return 2;
                default:
                    return 3;
            }
        }

        public void error(Exception exception) {
            zzbo.zzb(BuildConfig.FLAVOR, exception);
        }

        public void error(String str) {
            zzbo.m1698e(str);
        }

        public int getLogLevel() {
            return zzaai(zzbo.getLogLevel());
        }

        public void info(String str) {
            zzbo.zzdh(str);
        }

        public void setLogLevel(int i) {
            zzbo.zzdi("GA uses GTM logger. Please use TagManager.setLogLevel(int) instead.");
        }

        public void verbose(String str) {
            zzbo.m1699v(str);
        }

        public void warn(String str) {
            zzbo.zzdi(str);
        }
    }

    public zzdj(Context context) {
        this.mContext = context;
    }

    private synchronized void zzpv(String str) {
        if (this.aG == null) {
            this.aG = GoogleAnalytics.getInstance(this.mContext);
            this.aG.setLogger(new zza());
            this.aE = this.aG.newTracker(str);
        }
    }

    public Tracker zzpu(String str) {
        zzpv(str);
        return this.aE;
    }
}
