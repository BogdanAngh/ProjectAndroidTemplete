package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zze<TResult> implements zzf<TResult> {
    private final Executor aEQ;
    private OnSuccessListener<? super TResult> aMO;
    private final Object zzako;

    /* renamed from: com.google.android.gms.tasks.zze.1 */
    class C15611 implements Runnable {
        final /* synthetic */ Task aMH;
        final /* synthetic */ zze aMP;

        C15611(zze com_google_android_gms_tasks_zze, Task task) {
            this.aMP = com_google_android_gms_tasks_zze;
            this.aMH = task;
        }

        public void run() {
            synchronized (this.aMP.zzako) {
                if (this.aMP.aMO != null) {
                    this.aMP.aMO.onSuccess(this.aMH.getResult());
                }
            }
        }
    }

    public zze(@NonNull Executor executor, @NonNull OnSuccessListener<? super TResult> onSuccessListener) {
        this.zzako = new Object();
        this.aEQ = executor;
        this.aMO = onSuccessListener;
    }

    public void cancel() {
        synchronized (this.zzako) {
            this.aMO = null;
        }
    }

    public void onComplete(@NonNull Task<TResult> task) {
        if (task.isSuccessful()) {
            synchronized (this.zzako) {
                if (this.aMO == null) {
                    return;
                }
                this.aEQ.execute(new C15611(this, task));
            }
        }
    }
}
