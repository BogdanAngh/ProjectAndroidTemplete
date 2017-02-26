package com.google.android.gms.common.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.annotation.KeepName;

@KeepName
public final class BinderWrapper implements Parcelable {
    public static final Creator<BinderWrapper> CREATOR;
    private IBinder DI;

    /* renamed from: com.google.android.gms.common.internal.BinderWrapper.1 */
    class C11761 implements Creator<BinderWrapper> {
        C11761() {
        }

        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzck(parcel);
        }

        public /* synthetic */ Object[] newArray(int i) {
            return zzgl(i);
        }

        public BinderWrapper zzck(Parcel parcel) {
            return new BinderWrapper(null);
        }

        public BinderWrapper[] zzgl(int i) {
            return new BinderWrapper[i];
        }
    }

    static {
        CREATOR = new C11761();
    }

    public BinderWrapper() {
        this.DI = null;
    }

    public BinderWrapper(IBinder iBinder) {
        this.DI = null;
        this.DI = iBinder;
    }

    private BinderWrapper(Parcel parcel) {
        this.DI = null;
        this.DI = parcel.readStrongBinder();
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStrongBinder(this.DI);
    }
}
