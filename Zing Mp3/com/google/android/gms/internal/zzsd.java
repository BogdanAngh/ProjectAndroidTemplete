package com.google.android.gms.internal;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.util.ArrayMap;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.Map.Entry;
import java.util.WeakHashMap;

public final class zzsd extends Fragment implements zzrp {
    private static WeakHashMap<FragmentActivity, WeakReference<zzsd>> Bg;
    private Map<String, zzro> Bh;
    private Bundle Bi;
    private int zzbtt;

    /* renamed from: com.google.android.gms.internal.zzsd.1 */
    class C14941 implements Runnable {
        final /* synthetic */ zzro Bj;
        final /* synthetic */ zzsd Bu;
        final /* synthetic */ String zzap;

        C14941(zzsd com_google_android_gms_internal_zzsd, zzro com_google_android_gms_internal_zzro, String str) {
            this.Bu = com_google_android_gms_internal_zzsd;
            this.Bj = com_google_android_gms_internal_zzro;
            this.zzap = str;
        }

        public void run() {
            if (this.Bu.zzbtt >= 1) {
                this.Bj.onCreate(this.Bu.Bi != null ? this.Bu.Bi.getBundle(this.zzap) : null);
            }
            if (this.Bu.zzbtt >= 2) {
                this.Bj.onStart();
            }
            if (this.Bu.zzbtt >= 3) {
                this.Bj.onStop();
            }
            if (this.Bu.zzbtt >= 4) {
                this.Bj.onDestroy();
            }
        }
    }

    static {
        Bg = new WeakHashMap();
    }

    public zzsd() {
        this.Bh = new ArrayMap();
        this.zzbtt = 0;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static com.google.android.gms.internal.zzsd zza(android.support.v4.app.FragmentActivity r3) {
        /*
        r0 = Bg;
        r0 = r0.get(r3);
        r0 = (java.lang.ref.WeakReference) r0;
        if (r0 == 0) goto L_0x0013;
    L_0x000a:
        r0 = r0.get();
        r0 = (com.google.android.gms.internal.zzsd) r0;
        if (r0 == 0) goto L_0x0013;
    L_0x0012:
        return r0;
    L_0x0013:
        r0 = r3.getSupportFragmentManager();	 Catch:{ ClassCastException -> 0x0048 }
        r1 = "SupportLifecycleFragmentImpl";
        r0 = r0.findFragmentByTag(r1);	 Catch:{ ClassCastException -> 0x0048 }
        r0 = (com.google.android.gms.internal.zzsd) r0;	 Catch:{ ClassCastException -> 0x0048 }
        if (r0 == 0) goto L_0x0027;
    L_0x0021:
        r1 = r0.isRemoving();
        if (r1 == 0) goto L_0x003d;
    L_0x0027:
        r0 = new com.google.android.gms.internal.zzsd;
        r0.<init>();
        r1 = r3.getSupportFragmentManager();
        r1 = r1.beginTransaction();
        r2 = "SupportLifecycleFragmentImpl";
        r1 = r1.add(r0, r2);
        r1.commitAllowingStateLoss();
    L_0x003d:
        r1 = Bg;
        r2 = new java.lang.ref.WeakReference;
        r2.<init>(r0);
        r1.put(r3, r2);
        goto L_0x0012;
    L_0x0048:
        r0 = move-exception;
        r1 = new java.lang.IllegalStateException;
        r2 = "Fragment with tag SupportLifecycleFragmentImpl is not a SupportLifecycleFragmentImpl";
        r1.<init>(r2, r0);
        throw r1;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.gms.internal.zzsd.zza(android.support.v4.app.FragmentActivity):com.google.android.gms.internal.zzsd");
    }

    private void zzb(String str, @NonNull zzro com_google_android_gms_internal_zzro) {
        if (this.zzbtt > 0) {
            new Handler(Looper.getMainLooper()).post(new C14941(this, com_google_android_gms_internal_zzro, str));
        }
    }

    public void dump(String str, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        super.dump(str, fileDescriptor, printWriter, strArr);
        for (zzro dump : this.Bh.values()) {
            dump.dump(str, fileDescriptor, printWriter, strArr);
        }
    }

    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        for (zzro onActivityResult : this.Bh.values()) {
            onActivityResult.onActivityResult(i, i2, intent);
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.zzbtt = 1;
        this.Bi = bundle;
        for (Entry entry : this.Bh.entrySet()) {
            ((zzro) entry.getValue()).onCreate(bundle != null ? bundle.getBundle((String) entry.getKey()) : null);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.zzbtt = 4;
        for (zzro onDestroy : this.Bh.values()) {
            onDestroy.onDestroy();
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        if (bundle != null) {
            for (Entry entry : this.Bh.entrySet()) {
                Bundle bundle2 = new Bundle();
                ((zzro) entry.getValue()).onSaveInstanceState(bundle2);
                bundle.putBundle((String) entry.getKey(), bundle2);
            }
        }
    }

    public void onStart() {
        super.onStart();
        this.zzbtt = 2;
        for (zzro onStart : this.Bh.values()) {
            onStart.onStart();
        }
    }

    public void onStop() {
        super.onStop();
        this.zzbtt = 3;
        for (zzro onStop : this.Bh.values()) {
            onStop.onStop();
        }
    }

    public <T extends zzro> T zza(String str, Class<T> cls) {
        return (zzro) cls.cast(this.Bh.get(str));
    }

    public void zza(String str, @NonNull zzro com_google_android_gms_internal_zzro) {
        if (this.Bh.containsKey(str)) {
            throw new IllegalArgumentException(new StringBuilder(String.valueOf(str).length() + 59).append("LifecycleCallback with tag ").append(str).append(" already added to this fragment.").toString());
        }
        this.Bh.put(str, com_google_android_gms_internal_zzro);
        zzb(str, com_google_android_gms_internal_zzro);
    }

    public /* synthetic */ Activity zzaty() {
        return zzaub();
    }

    public FragmentActivity zzaub() {
        return getActivity();
    }
}
