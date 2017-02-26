package com.google.android.gms.internal;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import com.google.android.gms.common.util.zzs;
import java.util.ArrayList;
import java.util.List;

@zzji
public class zzcz {
    private final Object zzavz;
    private zza zzawa;
    private boolean zzawb;

    public interface zzb {
        void zzk(boolean z);
    }

    @TargetApi(14)
    static class zza implements ActivityLifecycleCallbacks {
        @Nullable
        private Activity mActivity;
        private Context mContext;
        private List<zzb> mListeners;
        private final Object zzako;
        private boolean zzaoz;
        private boolean zzawc;
        private boolean zzawd;
        private Runnable zzawe;
        private long zzawf;

        /* renamed from: com.google.android.gms.internal.zzcz.zza.1 */
        class C12791 implements Runnable {
            final /* synthetic */ zza zzawg;

            C12791(zza com_google_android_gms_internal_zzcz_zza) {
                this.zzawg = com_google_android_gms_internal_zzcz_zza;
            }

            public void run() {
                synchronized (this.zzawg.zzako) {
                    if (this.zzawg.zzawc && this.zzawg.zzawd) {
                        this.zzawg.zzawc = false;
                        com.google.android.gms.ads.internal.util.client.zzb.zzdg("App went background");
                        for (zzb zzk : this.zzawg.mListeners) {
                            try {
                                zzk.zzk(false);
                            } catch (Throwable e) {
                                com.google.android.gms.ads.internal.util.client.zzb.zzb("OnForegroundStateChangedListener threw exception.", e);
                            }
                        }
                    } else {
                        com.google.android.gms.ads.internal.util.client.zzb.zzdg("App is still foreground");
                    }
                }
            }
        }

        zza() {
            this.zzako = new Object();
            this.zzawc = true;
            this.zzawd = false;
            this.mListeners = new ArrayList();
            this.zzaoz = false;
        }

        private void setActivity(Activity activity) {
            synchronized (this.zzako) {
                if (!activity.getClass().getName().startsWith("com.google.android.gms.ads")) {
                    this.mActivity = activity;
                }
            }
        }

        @Nullable
        public Activity getActivity() {
            return this.mActivity;
        }

        @Nullable
        public Context getContext() {
            return this.mContext;
        }

        public void onActivityCreated(Activity activity, Bundle bundle) {
        }

        public void onActivityDestroyed(Activity activity) {
            synchronized (this.zzako) {
                if (this.mActivity == null) {
                    return;
                }
                if (this.mActivity.equals(activity)) {
                    this.mActivity = null;
                }
            }
        }

        public void onActivityPaused(Activity activity) {
            setActivity(activity);
            this.zzawd = true;
            if (this.zzawe != null) {
                zzlb.zzcvl.removeCallbacks(this.zzawe);
            }
            Handler handler = zzlb.zzcvl;
            Runnable c12791 = new C12791(this);
            this.zzawe = c12791;
            handler.postDelayed(c12791, this.zzawf);
        }

        public void onActivityResumed(Activity activity) {
            boolean z = false;
            setActivity(activity);
            this.zzawd = false;
            if (!this.zzawc) {
                z = true;
            }
            this.zzawc = true;
            if (this.zzawe != null) {
                zzlb.zzcvl.removeCallbacks(this.zzawe);
            }
            synchronized (this.zzako) {
                if (z) {
                    for (zzb zzk : this.mListeners) {
                        try {
                            zzk.zzk(true);
                        } catch (Throwable e) {
                            com.google.android.gms.ads.internal.util.client.zzb.zzb("OnForegroundStateChangedListener threw exception.", e);
                        }
                    }
                } else {
                    com.google.android.gms.ads.internal.util.client.zzb.zzdg("App is still foreground.");
                }
            }
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
        }

        public void onActivityStarted(Activity activity) {
            setActivity(activity);
        }

        public void onActivityStopped(Activity activity) {
        }

        public void zza(Application application, Context context) {
            if (!this.zzaoz) {
                application.registerActivityLifecycleCallbacks(this);
                if (context instanceof Activity) {
                    setActivity((Activity) context);
                }
                this.mContext = context;
                this.zzawf = ((Long) zzdr.zzbga.get()).longValue();
                this.zzaoz = true;
            }
        }

        public void zza(zzb com_google_android_gms_internal_zzcz_zzb) {
            this.mListeners.add(com_google_android_gms_internal_zzcz_zzb);
        }
    }

    public zzcz() {
        this.zzavz = new Object();
        this.zzawa = null;
        this.zzawb = false;
    }

    @Nullable
    public Activity getActivity() {
        Activity activity = null;
        synchronized (this.zzavz) {
            if (!zzs.zzayq()) {
            } else if (this.zzawa != null) {
                activity = this.zzawa.getActivity();
            }
        }
        return activity;
    }

    @Nullable
    public Context getContext() {
        Context context = null;
        synchronized (this.zzavz) {
            if (!zzs.zzayq()) {
            } else if (this.zzawa != null) {
                context = this.zzawa.getContext();
            }
        }
        return context;
    }

    public void initialize(Context context) {
        synchronized (this.zzavz) {
            if (!this.zzawb) {
                if (!zzs.zzayq()) {
                    return;
                } else if (((Boolean) zzdr.zzbfz.get()).booleanValue()) {
                    Context applicationContext = context.getApplicationContext();
                    if (applicationContext == null) {
                        applicationContext = context;
                    }
                    Application application = applicationContext instanceof Application ? (Application) applicationContext : null;
                    if (application == null) {
                        com.google.android.gms.ads.internal.util.client.zzb.zzdi("Can not cast Context to Application");
                        return;
                    }
                    if (this.zzawa == null) {
                        this.zzawa = new zza();
                    }
                    this.zzawa.zza(application, context);
                    this.zzawb = true;
                } else {
                    return;
                }
            }
        }
    }

    public void zza(zzb com_google_android_gms_internal_zzcz_zzb) {
        synchronized (this.zzavz) {
            if (!zzs.zzayq()) {
            } else if (((Boolean) zzdr.zzbfz.get()).booleanValue()) {
                if (this.zzawa == null) {
                    this.zzawa = new zza();
                }
                this.zzawa.zza(com_google_android_gms_internal_zzcz_zzb);
            }
        }
    }
}
