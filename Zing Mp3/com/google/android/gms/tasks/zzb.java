package com.google.android.gms.tasks;

import android.support.annotation.NonNull;
import java.util.concurrent.Executor;

class zzb<TResult, TContinuationResult> implements OnFailureListener, OnSuccessListener<TContinuationResult>, zzf<TResult> {
    private final Executor aEQ;
    private final Continuation<TResult, Task<TContinuationResult>> aMF;
    private final zzh<TContinuationResult> aMG;

    /* renamed from: com.google.android.gms.tasks.zzb.1 */
    class C15581 implements Runnable {
        final /* synthetic */ Task aMH;
        final /* synthetic */ zzb aMJ;

        C15581(zzb com_google_android_gms_tasks_zzb, Task task) {
            this.aMJ = com_google_android_gms_tasks_zzb;
            this.aMH = task;
        }

        public void run() {
            try {
                Task task = (Task) this.aMJ.aMF.then(this.aMH);
                if (task == null) {
                    this.aMJ.onFailure(new NullPointerException("Continuation returned null"));
                    return;
                }
                task.addOnSuccessListener(TaskExecutors.aMT, this.aMJ);
                task.addOnFailureListener(TaskExecutors.aMT, this.aMJ);
            } catch (Exception e) {
                if (e.getCause() instanceof Exception) {
                    this.aMJ.aMG.setException((Exception) e.getCause());
                } else {
                    this.aMJ.aMG.setException(e);
                }
            } catch (Exception e2) {
                this.aMJ.aMG.setException(e2);
            }
        }
    }

    public zzb(@NonNull Executor executor, @NonNull Continuation<TResult, Task<TContinuationResult>> continuation, @NonNull zzh<TContinuationResult> com_google_android_gms_tasks_zzh_TContinuationResult) {
        this.aEQ = executor;
        this.aMF = continuation;
        this.aMG = com_google_android_gms_tasks_zzh_TContinuationResult;
    }

    public void cancel() {
        throw new UnsupportedOperationException();
    }

    public void onComplete(@NonNull Task<TResult> task) {
        this.aEQ.execute(new C15581(this, task));
    }

    public void onFailure(@NonNull Exception exception) {
        this.aMG.setException(exception);
    }

    public void onSuccess(TContinuationResult tContinuationResult) {
        this.aMG.setResult(tContinuationResult);
    }
}
