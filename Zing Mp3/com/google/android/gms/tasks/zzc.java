package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzc<TResult> implements zzf<TResult> {
    private final Executor aEQ;
    private OnCompleteListener<TResult> aMK;
    private final Object zzako;

    /* renamed from: com.google.android.gms.tasks.zzc.1 */
    class C15591 implements Runnable {
        final /* synthetic */ Task aMH;
        final /* synthetic */ zzc aML;

        C15591(zzc com_google_android_gms_tasks_zzc, Task task) {
            this.aML = com_google_android_gms_tasks_zzc;
            this.aMH = task;
        }

        public void run() {
            synchronized (this.aML.zzako) {
                if (this.aML.aMK != null) {
                    this.aML.aMK.onComplete(this.aMH);
                }
            }
        }
    }

    public zzc(@NonNull Executor executor, @NonNull OnCompleteListener<TResult> onCompleteListener) {
        this.zzako = new Object();
        this.aEQ = executor;
        this.aMK = onCompleteListener;
    }

    public void cancel() {
        synchronized (this.zzako) {
            this.aMK = null;
        }
    }

    public void onComplete(@NonNull Task<TResult> task) {
        synchronized (this.zzako) {
            if (this.aMK == null) {
                return;
            }
            this.aEQ.execute(new C15591(this, task));
        }
    }
}
