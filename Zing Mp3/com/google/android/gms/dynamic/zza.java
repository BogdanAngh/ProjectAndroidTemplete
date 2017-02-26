package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.VisibleForTesting;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.internal.zzg;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T PT;
    private Bundle PU;
    private LinkedList<zza> PV;
    private final zzf<T> PW;

    /* renamed from: com.google.android.gms.dynamic.zza.1 */
    class C11851 implements zzf<T> {
        final /* synthetic */ zza PX;

        C11851(zza com_google_android_gms_dynamic_zza) {
            this.PX = com_google_android_gms_dynamic_zza;
        }

        public void zza(T t) {
            this.PX.PT = t;
            Iterator it = this.PX.PV.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzb(this.PX.PT);
            }
            this.PX.PV.clear();
            this.PX.PU = null;
        }
    }

    private interface zza {
        int getState();

        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    /* renamed from: com.google.android.gms.dynamic.zza.2 */
    class C11862 implements zza {
        final /* synthetic */ zza PX;
        final /* synthetic */ Bundle PY;
        final /* synthetic */ Activity val$activity;
        final /* synthetic */ Bundle zzavg;

        C11862(zza com_google_android_gms_dynamic_zza, Activity activity, Bundle bundle, Bundle bundle2) {
            this.PX = com_google_android_gms_dynamic_zza;
            this.val$activity = activity;
            this.PY = bundle;
            this.zzavg = bundle2;
        }

        public int getState() {
            return 0;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.PX.PT.onInflate(this.val$activity, this.PY, this.zzavg);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.3 */
    class C11873 implements zza {
        final /* synthetic */ zza PX;
        final /* synthetic */ Bundle zzavg;

        C11873(zza com_google_android_gms_dynamic_zza, Bundle bundle) {
            this.PX = com_google_android_gms_dynamic_zza;
            this.zzavg = bundle;
        }

        public int getState() {
            return 1;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.PX.PT.onCreate(this.zzavg);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.4 */
    class C11884 implements zza {
        final /* synthetic */ zza PX;
        final /* synthetic */ FrameLayout PZ;
        final /* synthetic */ LayoutInflater Qa;
        final /* synthetic */ ViewGroup Qb;
        final /* synthetic */ Bundle zzavg;

        C11884(zza com_google_android_gms_dynamic_zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.PX = com_google_android_gms_dynamic_zza;
            this.PZ = frameLayout;
            this.Qa = layoutInflater;
            this.Qb = viewGroup;
            this.zzavg = bundle;
        }

        public int getState() {
            return 2;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.PZ.removeAllViews();
            this.PZ.addView(this.PX.PT.onCreateView(this.Qa, this.Qb, this.zzavg));
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.5 */
    class C11895 implements OnClickListener {
        final /* synthetic */ Intent Qc;
        final /* synthetic */ Context zzang;

        C11895(Context context, Intent intent) {
            this.zzang = context;
            this.Qc = intent;
        }

        public void onClick(View view) {
            try {
                this.zzang.startActivity(this.Qc);
            } catch (Throwable e) {
                Log.e("DeferredLifecycleHelper", "Failed to start resolution intent", e);
            }
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.6 */
    class C11906 implements zza {
        final /* synthetic */ zza PX;

        C11906(zza com_google_android_gms_dynamic_zza) {
            this.PX = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 4;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.PX.PT.onStart();
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.7 */
    class C11917 implements zza {
        final /* synthetic */ zza PX;

        C11917(zza com_google_android_gms_dynamic_zza) {
            this.PX = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 5;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.PX.PT.onResume();
        }
    }

    public zza() {
        this.PW = new C11851(this);
    }

    private void zza(Bundle bundle, zza com_google_android_gms_dynamic_zza_zza) {
        if (this.PT != null) {
            com_google_android_gms_dynamic_zza_zza.zzb(this.PT);
            return;
        }
        if (this.PV == null) {
            this.PV = new LinkedList();
        }
        this.PV.add(com_google_android_gms_dynamic_zza_zza);
        if (bundle != null) {
            if (this.PU == null) {
                this.PU = (Bundle) bundle.clone();
            } else {
                this.PU.putAll(bundle);
            }
        }
        zza(this.PW);
    }

    @VisibleForTesting
    static void zza(FrameLayout frameLayout, GoogleApiAvailability googleApiAvailability) {
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = googleApiAvailability.isGooglePlayServicesAvailable(context);
        CharSequence zzi = zzg.zzi(context, isGooglePlayServicesAvailable);
        CharSequence zzk = zzg.zzk(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzi);
        linearLayout.addView(textView);
        Intent zzb = googleApiAvailability.zzb(context, isGooglePlayServicesAvailable, null);
        if (zzb != null) {
            View button = new Button(context);
            button.setId(16908313);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzk);
            linearLayout.addView(button);
            button.setOnClickListener(new C11895(context, zzb));
        }
    }

    public static void zzb(FrameLayout frameLayout) {
        zza(frameLayout, GoogleApiAvailability.getInstance());
    }

    private void zznj(int i) {
        while (!this.PV.isEmpty() && ((zza) this.PV.getLast()).getState() >= i) {
            this.PV.removeLast();
        }
    }

    public void onCreate(Bundle bundle) {
        zza(bundle, new C11873(this, bundle));
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        FrameLayout frameLayout = new FrameLayout(layoutInflater.getContext());
        zza(bundle, new C11884(this, frameLayout, layoutInflater, viewGroup, bundle));
        if (this.PT == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.PT != null) {
            this.PT.onDestroy();
        } else {
            zznj(1);
        }
    }

    public void onDestroyView() {
        if (this.PT != null) {
            this.PT.onDestroyView();
        } else {
            zznj(2);
        }
    }

    public void onInflate(Activity activity, Bundle bundle, Bundle bundle2) {
        zza(bundle2, new C11862(this, activity, bundle, bundle2));
    }

    public void onLowMemory() {
        if (this.PT != null) {
            this.PT.onLowMemory();
        }
    }

    public void onPause() {
        if (this.PT != null) {
            this.PT.onPause();
        } else {
            zznj(5);
        }
    }

    public void onResume() {
        zza(null, new C11917(this));
    }

    public void onSaveInstanceState(Bundle bundle) {
        if (this.PT != null) {
            this.PT.onSaveInstanceState(bundle);
        } else if (this.PU != null) {
            bundle.putAll(this.PU);
        }
    }

    public void onStart() {
        zza(null, new C11906(this));
    }

    public void onStop() {
        if (this.PT != null) {
            this.PT.onStop();
        } else {
            zznj(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> com_google_android_gms_dynamic_zzf_T);

    public T zzbdo() {
        return this.PT;
    }
}
