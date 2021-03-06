package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zza<TResult, TContinuationResult> implements zzf<TResult> {
    private final Executor aEQ;
    private final Continuation<TResult, TContinuationResult> aMF;
    private final zzh<TContinuationResult> aMG;

    /* renamed from: com.google.android.gms.tasks.zza.1 */
    class C15571 implements Runnable {
        final /* synthetic */ Task aMH;
        final /* synthetic */ zza aMI;

        C15571(zza com_google_android_gms_tasks_zza, Task task) {
            this.aMI = com_google_android_gms_tasks_zza;
            this.aMH = task;
        }

        public void run() {
            try {
                this.aMI.aMG.setResult(this.aMI.aMF.then(this.aMH));
            } catch (Exception e) {
                if (e.getCause() instanceof Exception) {
                    this.aMI.aMG.setException((Exception) e.getCause());
                } else {
                    this.aMI.aMG.setException(e);
                }
            } catch (Exception e2) {
                this.aMI.aMG.setException(e2);
            }
        }
    }

    public zza(@NonNull Executor executor, @NonNull Continuation<TResult, TContinuationResult> continuation, @NonNull zzh<TContinuationResult> com_google_android_gms_tasks_zzh_TContinuationResult) {
        this.aEQ = executor;
        this.aMF = continuation;
        this.aMG = com_google_android_gms_tasks_zzh_TContinuationResult;
    }

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public void onComplete(@NonNull Task<TResult> task) {
        this.aEQ.execute(new C15571(this, task));
    }
}
