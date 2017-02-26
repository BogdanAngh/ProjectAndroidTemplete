package com.google.android.gms.common.api;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.google.android.gms.common.api.PendingResult.BatchCallback;
import com.google.android.gms.common.internal.ICancelToken;
import com.google.android.gms.common.internal.zzu;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public abstract class AbstractPendingResult<R extends Result> implements PendingResult<R> {
    protected final CallbackHandler<R> mHandler;
    private boolean zzL;
    private final Object zzWb;
    private final ArrayList<BatchCallback> zzWc;
    private ResultCallback<R> zzWd;
    private volatile R zzWe;
    private volatile boolean zzWf;
    private boolean zzWg;
    private ICancelToken zzWh;
    private final CountDownLatch zzoD;

    public static class CallbackHandler<R extends Result> extends Handler {
        public static final int CALLBACK_ON_COMPLETE = 1;
        public static final int CALLBACK_ON_TIMEOUT = 2;

        public CallbackHandler() {
            this(Looper.getMainLooper());
        }

        public CallbackHandler(Looper looper) {
            super(looper);
        }

        protected void deliverResultCallback(ResultCallback<R> callback, R result) {
            try {
                callback.onResult(result);
            } catch (RuntimeException e) {
                AbstractPendingResult.zzb(result);
                throw e;
            }
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CALLBACK_ON_COMPLETE /*1*/:
                    Pair pair = (Pair) msg.obj;
                    deliverResultCallback((ResultCallback) pair.first, (Result) pair.second);
                case CALLBACK_ON_TIMEOUT /*2*/:
                    ((AbstractPendingResult) msg.obj).forceFailureUnlessReady(Status.zzXS);
                default:
                    Log.wtf("AbstractPendingResult", "Don't know how to handle this message.");
            }
        }

        public void removeTimeoutMessages() {
            removeMessages(CALLBACK_ON_TIMEOUT);
        }

        public void sendResultCallback(ResultCallback<R> callback, R result) {
            sendMessage(obtainMessage(CALLBACK_ON_COMPLETE, new Pair(callback, result)));
        }

        public void sendTimeoutResultCallback(AbstractPendingResult<R> pendingResult, long millis) {
            sendMessageDelayed(obtainMessage(CALLBACK_ON_TIMEOUT, pendingResult), millis);
        }
    }

    protected AbstractPendingResult(Looper looper) {
        this.zzWb = new Object();
        this.zzoD = new CountDownLatch(1);
        this.zzWc = new ArrayList();
        this.mHandler = new CallbackHandler(looper);
    }

    protected AbstractPendingResult(CallbackHandler<R> callbackHandler) {
        this.zzWb = new Object();
        this.zzoD = new CountDownLatch(1);
        this.zzWc = new ArrayList();
        this.mHandler = (CallbackHandler) zzu.zzb((Object) callbackHandler, (Object) "CallbackHandler must not be null");
    }

    private void zza(R r) {
        this.zzWe = r;
        this.zzWh = null;
        this.zzoD.countDown();
        Status status = this.zzWe.getStatus();
        if (this.zzWd != null) {
            this.mHandler.removeTimeoutMessages();
            if (!this.zzL) {
                this.mHandler.sendResultCallback(this.zzWd, zzmo());
            }
        }
        Iterator it = this.zzWc.iterator();
        while (it.hasNext()) {
            ((BatchCallback) it.next()).zzs(status);
        }
        this.zzWc.clear();
    }

    static void zzb(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                Log.w("AbstractPendingResult", "Unable to release " + result, e);
            }
        }
    }

    private R zzmo() {
        R r;
        boolean z = true;
        synchronized (this.zzWb) {
            if (this.zzWf) {
                z = false;
            }
            zzu.zza(z, (Object) "Result has already been consumed.");
            zzu.zza(isReady(), (Object) "Result is not ready.");
            r = this.zzWe;
            this.zzWe = null;
            this.zzWd = null;
            this.zzWf = true;
        }
        onResultConsumed();
        return r;
    }

    public final void addBatchCallback(BatchCallback callback) {
        zzu.zza(!this.zzWf, (Object) "Result has already been consumed.");
        synchronized (this.zzWb) {
            if (isReady()) {
                callback.zzs(this.zzWe.getStatus());
            } else {
                this.zzWc.add(callback);
            }
        }
    }

    public final R await() {
        boolean z = true;
        zzu.zza(Looper.myLooper() != Looper.getMainLooper(), (Object) "await must not be called on the UI thread");
        if (this.zzWf) {
            z = false;
        }
        zzu.zza(z, (Object) "Result has already been consumed");
        try {
            this.zzoD.await();
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.zzXQ);
        }
        zzu.zza(isReady(), (Object) "Result is not ready.");
        return zzmo();
    }

    public final R await(long time, TimeUnit units) {
        boolean z = true;
        boolean z2 = time <= 0 || Looper.myLooper() != Looper.getMainLooper();
        zzu.zza(z2, (Object) "await must not be called on the UI thread when time is greater than zero.");
        if (this.zzWf) {
            z = false;
        }
        zzu.zza(z, (Object) "Result has already been consumed.");
        try {
            if (!this.zzoD.await(time, units)) {
                forceFailureUnlessReady(Status.zzXS);
            }
        } catch (InterruptedException e) {
            forceFailureUnlessReady(Status.zzXQ);
        }
        zzu.zza(isReady(), (Object) "Result is not ready.");
        return zzmo();
    }

    public void cancel() {
        synchronized (this.zzWb) {
            if (this.zzL || this.zzWf) {
                return;
            }
            if (this.zzWh != null) {
                try {
                    this.zzWh.cancel();
                } catch (RemoteException e) {
                }
            }
            zzb(this.zzWe);
            this.zzWd = null;
            this.zzL = true;
            zza(createFailedResult(Status.zzXT));
        }
    }

    protected abstract R createFailedResult(Status status);

    public final void forceFailureUnlessReady(Status status) {
        synchronized (this.zzWb) {
            if (!isReady()) {
                setResult(createFailedResult(status));
                this.zzWg = true;
            }
        }
    }

    public boolean isCanceled() {
        boolean z;
        synchronized (this.zzWb) {
            z = this.zzL;
        }
        return z;
    }

    public final boolean isReady() {
        return this.zzoD.getCount() == 0;
    }

    protected void onResultConsumed() {
    }

    protected final void setCancelToken(ICancelToken cancelToken) {
        synchronized (this.zzWb) {
            this.zzWh = cancelToken;
        }
    }

    public final void setResult(R result) {
        boolean z = true;
        synchronized (this.zzWb) {
            if (this.zzWg || this.zzL) {
                zzb(result);
                return;
            }
            zzu.zza(!isReady(), (Object) "Results have already been set");
            if (this.zzWf) {
                z = false;
            }
            zzu.zza(z, (Object) "Result has already been consumed");
            zza(result);
        }
    }

    public final void setResultCallback(ResultCallback<R> callback) {
        zzu.zza(!this.zzWf, (Object) "Result has already been consumed.");
        synchronized (this.zzWb) {
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.mHandler.sendResultCallback(callback, zzmo());
            } else {
                this.zzWd = callback;
            }
        }
    }

    public final void setResultCallback(ResultCallback<R> callback, long time, TimeUnit units) {
        zzu.zza(!this.zzWf, (Object) "Result has already been consumed.");
        synchronized (this.zzWb) {
            if (isCanceled()) {
                return;
            }
            if (isReady()) {
                this.mHandler.sendResultCallback(callback, zzmo());
            } else {
                this.zzWd = callback;
                this.mHandler.sendTimeoutResultCallback(this, units.toMillis(time));
            }
        }
    }
}
