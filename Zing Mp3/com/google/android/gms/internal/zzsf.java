package com.google.android.gms.internal;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.WorkerThread;
import android.util.Log;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.Releasable;
import com.google.android.gms.common.api.Result;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.ResultCallbacks;
import com.google.android.gms.common.api.ResultTransform;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.common.api.TransformedResult;
import com.google.android.gms.common.internal.zzaa;
import com.mp3download.zingmp3.C1569R;
import java.lang.ref.WeakReference;

public class zzsf<R extends Result> extends TransformedResult<R> implements ResultCallback<R> {
    private final zza BA;
    private boolean BB;
    private ResultTransform<? super R, ? extends Result> Bv;
    private zzsf<? extends Result> Bw;
    private volatile ResultCallbacks<? super R> Bx;
    private PendingResult<R> By;
    private Status Bz;
    private final Object yH;
    private final WeakReference<GoogleApiClient> yJ;

    /* renamed from: com.google.android.gms.internal.zzsf.1 */
    class C14951 implements Runnable {
        final /* synthetic */ Result BC;
        final /* synthetic */ zzsf BD;

        C14951(zzsf com_google_android_gms_internal_zzsf, Result result) {
            this.BD = com_google_android_gms_internal_zzsf;
            this.BC = result;
        }

        @WorkerThread
        public void run() {
            GoogleApiClient googleApiClient;
            try {
                zzqq.yG.set(Boolean.valueOf(true));
                this.BD.BA.sendMessage(this.BD.BA.obtainMessage(0, this.BD.Bv.onSuccess(this.BC)));
                zzqq.yG.set(Boolean.valueOf(false));
                this.BD.zze(this.BC);
                googleApiClient = (GoogleApiClient) this.BD.yJ.get();
                if (googleApiClient != null) {
                    googleApiClient.zzb(this.BD);
                }
            } catch (RuntimeException e) {
                this.BD.BA.sendMessage(this.BD.BA.obtainMessage(1, e));
                zzqq.yG.set(Boolean.valueOf(false));
                this.BD.zze(this.BC);
                googleApiClient = (GoogleApiClient) this.BD.yJ.get();
                if (googleApiClient != null) {
                    googleApiClient.zzb(this.BD);
                }
            } catch (Throwable th) {
                Throwable th2 = th;
                zzqq.yG.set(Boolean.valueOf(false));
                this.BD.zze(this.BC);
                googleApiClient = (GoogleApiClient) this.BD.yJ.get();
                if (googleApiClient != null) {
                    googleApiClient.zzb(this.BD);
                }
            }
        }
    }

    private final class zza extends Handler {
        final /* synthetic */ zzsf BD;

        public zza(zzsf com_google_android_gms_internal_zzsf, Looper looper) {
            this.BD = com_google_android_gms_internal_zzsf;
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case C1569R.styleable.com_facebook_login_view_com_facebook_confirm_logout /*0*/:
                    PendingResult pendingResult = (PendingResult) message.obj;
                    synchronized (this.BD.yH) {
                        if (pendingResult != null) {
                            if (pendingResult instanceof zzrz) {
                                this.BD.Bw.zzad(((zzrz) pendingResult).getStatus());
                            } else {
                                this.BD.Bw.zza(pendingResult);
                            }
                            break;
                        }
                        this.BD.Bw.zzad(new Status(13, "Transform returned null"));
                        break;
                    }
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    RuntimeException runtimeException = (RuntimeException) message.obj;
                    String str = "TransformedResultImpl";
                    String str2 = "Runtime exception on the transformation worker thread: ";
                    String valueOf = String.valueOf(runtimeException.getMessage());
                    Log.e(str, valueOf.length() != 0 ? str2.concat(valueOf) : new String(str2));
                    throw runtimeException;
                default:
                    Log.e("TransformedResultImpl", "TransformationResultHandler received unknown message type: " + message.what);
            }
        }
    }

    public zzsf(WeakReference<GoogleApiClient> weakReference) {
        this.Bv = null;
        this.Bw = null;
        this.Bx = null;
        this.By = null;
        this.yH = new Object();
        this.Bz = null;
        this.BB = false;
        zzaa.zzb((Object) weakReference, (Object) "GoogleApiClient reference must not be null");
        this.yJ = weakReference;
        GoogleApiClient googleApiClient = (GoogleApiClient) this.yJ.get();
        this.BA = new zza(this, googleApiClient != null ? googleApiClient.getLooper() : Looper.getMainLooper());
    }

    private void zzad(Status status) {
        synchronized (this.yH) {
            this.Bz = status;
            zzae(this.Bz);
        }
    }

    private void zzae(Status status) {
        synchronized (this.yH) {
            if (this.Bv != null) {
                Object onFailure = this.Bv.onFailure(status);
                zzaa.zzb(onFailure, (Object) "onFailure must not return null");
                this.Bw.zzad(onFailure);
            } else if (zzaue()) {
                this.Bx.onFailure(status);
            }
        }
    }

    private void zzauc() {
        if (this.Bv != null || this.Bx != null) {
            GoogleApiClient googleApiClient = (GoogleApiClient) this.yJ.get();
            if (!(this.BB || this.Bv == null || googleApiClient == null)) {
                googleApiClient.zza(this);
                this.BB = true;
            }
            if (this.Bz != null) {
                zzae(this.Bz);
            } else if (this.By != null) {
                this.By.setResultCallback(this);
            }
        }
    }

    private boolean zzaue() {
        return (this.Bx == null || ((GoogleApiClient) this.yJ.get()) == null) ? false : true;
    }

    private void zze(Result result) {
        if (result instanceof Releasable) {
            try {
                ((Releasable) result).release();
            } catch (Throwable e) {
                String valueOf = String.valueOf(result);
                Log.w("TransformedResultImpl", new StringBuilder(String.valueOf(valueOf).length() + 18).append("Unable to release ").append(valueOf).toString(), e);
            }
        }
    }

    public void andFinally(@NonNull ResultCallbacks<? super R> resultCallbacks) {
        boolean z = true;
        synchronized (this.yH) {
            zzaa.zza(this.Bx == null, (Object) "Cannot call andFinally() twice.");
            if (this.Bv != null) {
                z = false;
            }
            zzaa.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.Bx = resultCallbacks;
            zzauc();
        }
    }

    public void onResult(R r) {
        synchronized (this.yH) {
            if (!r.getStatus().isSuccess()) {
                zzad(r.getStatus());
                zze((Result) r);
            } else if (this.Bv != null) {
                zzry.zzatf().submit(new C14951(this, r));
            } else if (zzaue()) {
                this.Bx.onSuccess(r);
            }
        }
    }

    @NonNull
    public <S extends Result> TransformedResult<S> then(@NonNull ResultTransform<? super R, ? extends S> resultTransform) {
        TransformedResult com_google_android_gms_internal_zzsf;
        boolean z = true;
        synchronized (this.yH) {
            zzaa.zza(this.Bv == null, (Object) "Cannot call then() twice.");
            if (this.Bx != null) {
                z = false;
            }
            zzaa.zza(z, (Object) "Cannot call then() and andFinally() on the same TransformedResult.");
            this.Bv = resultTransform;
            com_google_android_gms_internal_zzsf = new zzsf(this.yJ);
            this.Bw = com_google_android_gms_internal_zzsf;
            zzauc();
        }
        return com_google_android_gms_internal_zzsf;
    }

    public void zza(PendingResult<?> pendingResult) {
        synchronized (this.yH) {
            this.By = pendingResult;
            zzauc();
        }
    }

    void zzaud() {
        this.Bx = null;
    }
}
