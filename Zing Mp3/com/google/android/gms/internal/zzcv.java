package com.google.android.gms.internal;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.os.Bundle;
import com.google.android.gms.ads.internal.util.client.zzb;
import java.lang.ref.WeakReference;

class zzcv implements ActivityLifecycleCallbacks {
    private final Application zzaul;
    private final WeakReference<ActivityLifecycleCallbacks> zzavf;

    public interface zza {
        void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks);
    }

    /* renamed from: com.google.android.gms.internal.zzcv.1 */
    class C12721 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ Bundle zzavg;
        final /* synthetic */ zzcv zzavh;

        C12721(zzcv com_google_android_gms_internal_zzcv, Activity activity, Bundle bundle) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
            this.zzavg = bundle;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityCreated(this.val$activity, this.zzavg);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.2 */
    class C12732 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzcv zzavh;

        C12732(zzcv com_google_android_gms_internal_zzcv, Activity activity) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityStarted(this.val$activity);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.3 */
    class C12743 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzcv zzavh;

        C12743(zzcv com_google_android_gms_internal_zzcv, Activity activity) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityResumed(this.val$activity);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.4 */
    class C12754 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzcv zzavh;

        C12754(zzcv com_google_android_gms_internal_zzcv, Activity activity) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityPaused(this.val$activity);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.5 */
    class C12765 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzcv zzavh;

        C12765(zzcv com_google_android_gms_internal_zzcv, Activity activity) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityStopped(this.val$activity);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.6 */
    class C12776 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ Bundle zzavg;
        final /* synthetic */ zzcv zzavh;

        C12776(zzcv com_google_android_gms_internal_zzcv, Activity activity, Bundle bundle) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
            this.zzavg = bundle;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivitySaveInstanceState(this.val$activity, this.zzavg);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzcv.7 */
    class C12787 implements zza {
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ zzcv zzavh;

        C12787(zzcv com_google_android_gms_internal_zzcv, Activity activity) {
            this.zzavh = com_google_android_gms_internal_zzcv;
            this.val$activity = activity;
        }

        public void zza(ActivityLifecycleCallbacks activityLifecycleCallbacks) {
            activityLifecycleCallbacks.onActivityDestroyed(this.val$activity);
        }
    }

    public zzcv(Application application, ActivityLifecycleCallbacks activityLifecycleCallbacks) {
        this.zzavf = new WeakReference(activityLifecycleCallbacks);
        this.zzaul = application;
    }

    public void onActivityCreated(Activity activity, Bundle bundle) {
        zza(new C12721(this, activity, bundle));
    }

    public void onActivityDestroyed(Activity activity) {
        zza(new C12787(this, activity));
    }

    public void onActivityPaused(Activity activity) {
        zza(new C12754(this, activity));
    }

    public void onActivityResumed(Activity activity) {
        zza(new C12743(this, activity));
    }

    public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        zza(new C12776(this, activity, bundle));
    }

    public void onActivityStarted(Activity activity) {
        zza(new C12732(this, activity));
    }

    public void onActivityStopped(Activity activity) {
        zza(new C12765(this, activity));
    }

    protected void zza(zza com_google_android_gms_internal_zzcv_zza) {
        try {
            ActivityLifecycleCallbacks activityLifecycleCallbacks = (ActivityLifecycleCallbacks) this.zzavf.get();
            if (activityLifecycleCallbacks != null) {
                com_google_android_gms_internal_zzcv_zza.zza(activityLifecycleCallbacks);
            } else {
                this.zzaul.unregisterActivityLifecycleCallbacks(this);
            }
        } catch (Throwable e) {
            zzb.zzb("Error while dispatching lifecycle callback.", e);
        }
    }
}
