package com.google.android.gms.ads;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.client.zzab;
import com.google.android.gms.ads.internal.client.zzap;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.common.internal.zzaa;
import com.google.android.gms.internal.zzji;

@zzji
public final class VideoController {
    private final Object zzako;
    @Nullable
    private zzab zzakp;
    @Nullable
    private VideoLifecycleCallbacks zzakq;

    public static abstract class VideoLifecycleCallbacks {
        public void onVideoEnd() {
        }
    }

    public VideoController() {
        this.zzako = new Object();
    }

    public float getAspectRatio() {
        float f = 0.0f;
        synchronized (this.zzako) {
            if (this.zzakp == null) {
            } else {
                try {
                    f = this.zzakp.getAspectRatio();
                } catch (Throwable e) {
                    zzb.zzb("Unable to call getAspectRatio on video controller.", e);
                }
            }
        }
        return f;
    }

    @Nullable
    public VideoLifecycleCallbacks getVideoLifecycleCallbacks() {
        VideoLifecycleCallbacks videoLifecycleCallbacks;
        synchronized (this.zzako) {
            videoLifecycleCallbacks = this.zzakq;
        }
        return videoLifecycleCallbacks;
    }

    public boolean hasVideoContent() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzakp != null;
        }
        return z;
    }

    public void setVideoLifecycleCallbacks(VideoLifecycleCallbacks videoLifecycleCallbacks) {
        zzaa.zzb((Object) videoLifecycleCallbacks, (Object) "VideoLifecycleCallbacks may not be null.");
        synchronized (this.zzako) {
            this.zzakq = videoLifecycleCallbacks;
            if (this.zzakp == null) {
                return;
            }
            try {
                this.zzakp.zza(new zzap(videoLifecycleCallbacks));
            } catch (Throwable e) {
                zzb.zzb("Unable to call setVideoLifecycleCallbacks on video controller.", e);
            }
        }
    }

    public void zza(zzab com_google_android_gms_ads_internal_client_zzab) {
        synchronized (this.zzako) {
            this.zzakp = com_google_android_gms_ads_internal_client_zzab;
            if (this.zzakq != null) {
                setVideoLifecycleCallbacks(this.zzakq);
            }
        }
    }

    public zzab zzdw() {
        zzab com_google_android_gms_ads_internal_client_zzab;
        synchronized (this.zzako) {
            com_google_android_gms_ads_internal_client_zzab = this.zzakp;
        }
        return com_google_android_gms_ads_internal_client_zzab;
    }
}
