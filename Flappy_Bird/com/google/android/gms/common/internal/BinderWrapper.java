package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR;
    private IBinder zzZQ;

    /* renamed from: com.google.android.gms.common.internal.BinderWrapper.1 */
    static class C01391 implements Creator<BinderWrapper> {
        C01391() {
        }

        public /* synthetic */ Object createFromParcel(Parcel x0) {
            return zzV(x0);
        }

        public /* synthetic */ Object[] newArray(int x0) {
            return zzbq(x0);
        }

        public BinderWrapper zzV(Parcel parcel) {
            return new BinderWrapper(null);
        }

        public BinderWrapper[] zzbq(int i) {
            return new BinderWrapper[i];
        }
    }

    static {
        CREATOR = new C01391();
    }

    public BinderWrapper() {
        this.zzZQ = null;
    }

    public BinderWrapper(IBinder binder) {
        this.zzZQ = null;
        this.zzZQ = binder;
    }

    private BinderWrapper(Parcel in) {
        this.zzZQ = null;
        this.zzZQ = in.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStrongBinder(this.zzZQ);
    }
}
