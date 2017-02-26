package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzd<TResult> implements zzf<TResult> {
    private final Executor aEQ;
    private OnFailureListener aMM;
    private final Object zzako;

    /* renamed from: com.google.android.gms.tasks.zzd.1 */
    class C15601 implements Runnable {
        final /* synthetic */ Task aMH;
        final /* synthetic */ zzd aMN;

        C15601(zzd com_google_android_gms_tasks_zzd, Task task) {
            this.aMN = com_google_android_gms_tasks_zzd;
            this.aMH = task;
        }

        public void run() {
            synchronized (this.aMN.zzako) {
                if (this.aMN.aMM != null) {
                    this.aMN.aMM.onFailure(this.aMH.getException());
                }
            }
        }
    }

    public zzd(@NonNull Executor executor, @NonNull OnFailureListener onFailureListener) {
        this.zzako = new Object();
        this.aEQ = executor;
        this.aMM = onFailureListener;
    }

    public void cancel() {
        synchronized (this.zzako) {
            this.aMM = null;
        }
    }

    public void onComplete(@NonNull Task<TResult> task) {
        if (!task.isSuccessful()) {
            synchronized (this.zzako) {
                if (this.aMM == null) {
                    return;
                }
                this.aEQ.execute(new C15601(this, task));
            }
        }
    }
}
