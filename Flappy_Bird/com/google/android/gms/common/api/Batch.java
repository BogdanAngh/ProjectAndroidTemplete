package com.google.android.gms.common.api;

import android.os.Looper;
import com.google.android.gms.common.api.AbstractPendingResult.CallbackHandler;
import com.google.android.gms.common.api.PendingResult.BatchCallback;
import java.util.ArrayList;
import java.util.List;

public final class Batch extends AbstractPendingResult<BatchResult> {
    private int zzWn;
    private boolean zzWo;
    private boolean zzWp;
    private final PendingResult<?>[] zzWq;
    private final Object zzqt;

    public static final class Builder {
        private List<PendingResult<?>> zzWs;
        private Looper zzWt;

        public Builder(GoogleApiClient googleApiClient) {
            this.zzWs = new ArrayList();
            this.zzWt = googleApiClient.getLooper();
        }

        public <R extends Result> BatchResultToken<R> add(PendingResult<R> pendingResult) {
            BatchResultToken<R> batchResultToken = new BatchResultToken(this.zzWs.size());
            this.zzWs.add(pendingResult);
            return batchResultToken;
        }

        public Batch build() {
            return new Batch(this.zzWt, null);
        }
    }

    /* renamed from: com.google.android.gms.common.api.Batch.1 */
    class C04091 implements BatchCallback {
        final /* synthetic */ Batch zzWr;

        C04091(Batch batch) {
            this.zzWr = batch;
        }

        public void zzs(Status status) {
            synchronized (this.zzWr.zzqt) {
                if (this.zzWr.isCanceled()) {
                    return;
                }
                if (status.isCanceled()) {
                    this.zzWr.zzWp = true;
                } else if (!status.isSuccess()) {
                    this.zzWr.zzWo = true;
                }
                this.zzWr.zzWn = this.zzWr.zzWn - 1;
                if (this.zzWr.zzWn == 0) {
                    if (this.zzWr.zzWp) {
                        super.cancel();
                    } else {
                        this.zzWr.setResult(new BatchResult(this.zzWr.zzWo ? new Status(13) : Status.zzXP, this.zzWr.zzWq));
                    }
                }
            }
        }
    }

    private Batch(List<PendingResult<?>> pendingResultList, Looper looper) {
        super(new CallbackHandler(looper));
        this.zzqt = new Object();
        this.zzWn = pendingResultList.size();
        this.zzWq = new PendingResult[this.zzWn];
        for (int i = 0; i < pendingResultList.size(); i++) {
            PendingResult pendingResult = (PendingResult) pendingResultList.get(i);
            this.zzWq[i] = pendingResult;
            pendingResult.addBatchCallback(new C04091(this));
        }
    }

    public void cancel() {
        super.cancel();
        for (PendingResult cancel : this.zzWq) {
            cancel.cancel();
        }
    }

    public BatchResult createFailedResult(Status status) {
        return new BatchResult(status, this.zzWq);
    }
}
