package com.google.android.gms.dynamic;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.FrameLayout.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.internal.zzf;
import java.util.Iterator;
import java.util.LinkedList;

public abstract class zza<T extends LifecycleDelegate> {
    private T zzajh;
    private Bundle zzaji;
    private LinkedList<zza> zzajj;
    private final zzf<T> zzajk;

    /* renamed from: com.google.android.gms.dynamic.zza.5 */
    static class C01465 implements OnClickListener {
        final /* synthetic */ int zzajs;
        final /* synthetic */ Context zzqV;

        C01465(Context context, int i) {
            this.zzqV = context;
            this.zzajs = i;
        }

        public void onClick(View v) {
            this.zzqV.startActivity(GooglePlayServicesUtil.zzaT(this.zzajs));
        }
    }

    private interface zza {
        int getState();

        void zzb(LifecycleDelegate lifecycleDelegate);
    }

    /* renamed from: com.google.android.gms.dynamic.zza.1 */
    class C04201 implements zzf<T> {
        final /* synthetic */ zza zzajl;

        C04201(zza com_google_android_gms_dynamic_zza) {
            this.zzajl = com_google_android_gms_dynamic_zza;
        }

        public void zza(T t) {
            this.zzajl.zzajh = t;
            Iterator it = this.zzajl.zzajj.iterator();
            while (it.hasNext()) {
                ((zza) it.next()).zzb(this.zzajl.zzajh);
            }
            this.zzajl.zzajj.clear();
            this.zzajl.zzaji = null;
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.2 */
    class C04212 implements zza {
        final /* synthetic */ zza zzajl;
        final /* synthetic */ Activity zzajm;
        final /* synthetic */ Bundle zzajn;
        final /* synthetic */ Bundle zzajo;

        C04212(zza com_google_android_gms_dynamic_zza, Activity activity, Bundle bundle, Bundle bundle2) {
            this.zzajl = com_google_android_gms_dynamic_zza;
            this.zzajm = activity;
            this.zzajn = bundle;
            this.zzajo = bundle2;
        }

        public int getState() {
            return 0;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.zzajl.zzajh.onInflate(this.zzajm, this.zzajn, this.zzajo);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.3 */
    class C04223 implements zza {
        final /* synthetic */ zza zzajl;
        final /* synthetic */ Bundle zzajo;

        C04223(zza com_google_android_gms_dynamic_zza, Bundle bundle) {
            this.zzajl = com_google_android_gms_dynamic_zza;
            this.zzajo = bundle;
        }

        public int getState() {
            return 1;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.zzajl.zzajh.onCreate(this.zzajo);
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.4 */
    class C04234 implements zza {
        final /* synthetic */ zza zzajl;
        final /* synthetic */ Bundle zzajo;
        final /* synthetic */ FrameLayout zzajp;
        final /* synthetic */ LayoutInflater zzajq;
        final /* synthetic */ ViewGroup zzajr;

        C04234(zza com_google_android_gms_dynamic_zza, FrameLayout frameLayout, LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
            this.zzajl = com_google_android_gms_dynamic_zza;
            this.zzajp = frameLayout;
            this.zzajq = layoutInflater;
            this.zzajr = viewGroup;
            this.zzajo = bundle;
        }

        public int getState() {
            return 2;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.zzajp.removeAllViews();
            this.zzajp.addView(this.zzajl.zzajh.onCreateView(this.zzajq, this.zzajr, this.zzajo));
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.6 */
    class C04246 implements zza {
        final /* synthetic */ zza zzajl;

        C04246(zza com_google_android_gms_dynamic_zza) {
            this.zzajl = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 4;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.zzajl.zzajh.onStart();
        }
    }

    /* renamed from: com.google.android.gms.dynamic.zza.7 */
    class C04257 implements zza {
        final /* synthetic */ zza zzajl;

        C04257(zza com_google_android_gms_dynamic_zza) {
            this.zzajl = com_google_android_gms_dynamic_zza;
        }

        public int getState() {
            return 5;
        }

        public void zzb(LifecycleDelegate lifecycleDelegate) {
            this.zzajl.zzajh.onResume();
        }
    }

    public zza() {
        this.zzajk = new C04201(this);
    }

    private void zza(Bundle bundle, zza com_google_android_gms_dynamic_zza_zza) {
        if (this.zzajh != null) {
            com_google_android_gms_dynamic_zza_zza.zzb(this.zzajh);
            return;
        }
        if (this.zzajj == null) {
            this.zzajj = new LinkedList();
        }
        this.zzajj.add(com_google_android_gms_dynamic_zza_zza);
        if (bundle != null) {
            if (this.zzaji == null) {
                this.zzaji = (Bundle) bundle.clone();
            } else {
                this.zzaji.putAll(bundle);
            }
        }
        zza(this.zzajk);
    }

    public static void zzb(FrameLayout frameLayout) {
        Context context = frameLayout.getContext();
        int isGooglePlayServicesAvailable = GooglePlayServicesUtil.isGooglePlayServicesAvailable(context);
        CharSequence zzb = zzf.zzb(context, isGooglePlayServicesAvailable, GooglePlayServicesUtil.zzad(context));
        CharSequence zzh = zzf.zzh(context, isGooglePlayServicesAvailable);
        View linearLayout = new LinearLayout(frameLayout.getContext());
        linearLayout.setOrientation(1);
        linearLayout.setLayoutParams(new LayoutParams(-2, -2));
        frameLayout.addView(linearLayout);
        View textView = new TextView(frameLayout.getContext());
        textView.setLayoutParams(new LayoutParams(-2, -2));
        textView.setText(zzb);
        linearLayout.addView(textView);
        if (zzh != null) {
            View button = new Button(context);
            button.setLayoutParams(new LayoutParams(-2, -2));
            button.setText(zzh);
            linearLayout.addView(button);
            button.setOnClickListener(new C01465(context, isGooglePlayServicesAvailable));
        }
    }

    private void zzdY(int i) {
        while (!this.zzajj.isEmpty() && ((zza) this.zzajj.getLast()).getState() >= i) {
            this.zzajj.removeLast();
        }
    }

    public void onCreate(Bundle savedInstanceState) {
        zza(savedInstanceState, new C04223(this, savedInstanceState));
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout frameLayout = new FrameLayout(inflater.getContext());
        zza(savedInstanceState, new C04234(this, frameLayout, inflater, container, savedInstanceState));
        if (this.zzajh == null) {
            zza(frameLayout);
        }
        return frameLayout;
    }

    public void onDestroy() {
        if (this.zzajh != null) {
            this.zzajh.onDestroy();
        } else {
            zzdY(1);
        }
    }

    public void onDestroyView() {
        if (this.zzajh != null) {
            this.zzajh.onDestroyView();
        } else {
            zzdY(2);
        }
    }

    public void onInflate(Activity activity, Bundle attrs, Bundle savedInstanceState) {
        zza(savedInstanceState, new C04212(this, activity, attrs, savedInstanceState));
    }

    public void onLowMemory() {
        if (this.zzajh != null) {
            this.zzajh.onLowMemory();
        }
    }

    public void onPause() {
        if (this.zzajh != null) {
            this.zzajh.onPause();
        } else {
            zzdY(5);
        }
    }

    public void onResume() {
        zza(null, new C04257(this));
    }

    public void onSaveInstanceState(Bundle outState) {
        if (this.zzajh != null) {
            this.zzajh.onSaveInstanceState(outState);
        } else if (this.zzaji != null) {
            outState.putAll(this.zzaji);
        }
    }

    public void onStart() {
        zza(null, new C04246(this));
    }

    public void onStop() {
        if (this.zzajh != null) {
            this.zzajh.onStop();
        } else {
            zzdY(4);
        }
    }

    protected void zza(FrameLayout frameLayout) {
        zzb(frameLayout);
    }

    protected abstract void zza(zzf<T> com_google_android_gms_dynamic_zzf_T);

    public T zzqj() {
        return this.zzajh;
    }
}
