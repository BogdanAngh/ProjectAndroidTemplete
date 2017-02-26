package com.google.android.gms.internal;

import android.support.annotation.Nullable;
import com.google.android.gms.ads.internal.zzu;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

@zzji
public class zzlq<T> implements zzlt<T> {
    private final Object zzako;
    private boolean zzbww;
    private T zzcyd;
    private Throwable zzcye;
    private boolean zzcyf;
    private final zzlu zzcyg;

    public zzlq() {
        this.zzako = new Object();
        this.zzcyg = new zzlu();
    }

    private boolean zzws() {
        return this.zzcye != null || this.zzcyf;
    }

    public boolean cancel(boolean z) {
        if (!z) {
            return false;
        }
        synchronized (this.zzako) {
            if (zzws()) {
                return false;
            }
            this.zzbww = true;
            this.zzcyf = true;
            this.zzako.notifyAll();
            this.zzcyg.zzwt();
            return true;
        }
    }

    public T get() throws CancellationException, ExecutionException, InterruptedException {
        T t;
        synchronized (this.zzako) {
            if (!zzws()) {
                try {
                    this.zzako.wait();
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            if (this.zzcye != null) {
                throw new ExecutionException(this.zzcye);
            } else if (this.zzbww) {
                throw new CancellationException("CallbackFuture was cancelled.");
            } else {
                t = this.zzcyd;
            }
        }
        return t;
    }

    public T get(long j, TimeUnit timeUnit) throws CancellationException, ExecutionException, InterruptedException, TimeoutException {
        T t;
        synchronized (this.zzako) {
            if (!zzws()) {
                try {
                    long toMillis = timeUnit.toMillis(j);
                    if (toMillis != 0) {
                        this.zzako.wait(toMillis);
                    }
                } catch (InterruptedException e) {
                    throw e;
                }
            }
            if (this.zzcye != null) {
                throw new ExecutionException(this.zzcye);
            } else if (!this.zzcyf) {
                throw new TimeoutException("CallbackFuture timed out.");
            } else if (this.zzbww) {
                throw new CancellationException("CallbackFuture was cancelled.");
            } else {
                t = this.zzcyd;
            }
        }
        return t;
    }

    public boolean isCancelled() {
        boolean z;
        synchronized (this.zzako) {
            z = this.zzbww;
        }
        return z;
    }

    public boolean isDone() {
        boolean zzws;
        synchronized (this.zzako) {
            zzws = zzws();
        }
        return zzws;
    }

    public void zzc(Runnable runnable) {
        this.zzcyg.zzc(runnable);
    }

    public void zzd(Runnable runnable) {
        this.zzcyg.zzd(runnable);
    }

    public void zze(Throwable th) {
        synchronized (this.zzako) {
            if (this.zzbww) {
            } else if (zzws()) {
                zzu.zzgq().zza(new IllegalStateException("Provided CallbackFuture with multiple values."), "CallbackFuture.provideException");
            } else {
                this.zzcye = th;
                this.zzako.notifyAll();
                this.zzcyg.zzwt();
            }
        }
    }

    public void zzh(@Nullable T t) {
        synchronized (this.zzako) {
            if (this.zzbww) {
            } else if (zzws()) {
                zzu.zzgq().zza(new IllegalStateException("Provided CallbackFuture with multiple values."), "CallbackFuture.provideValue");
            } else {
                this.zzcyf = true;
                this.zzcyd = t;
                this.zzako.notifyAll();
                this.zzcyg.zzwt();
            }
        }
    }
}
