package com.google.android.gms.common.internal;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import com.google.android.gms.dynamic.zzd;
import com.mp3download.zingmp3.C1569R;

public interface zzr extends IInterface {

    public static abstract class zza extends Binder implements zzr {
        public zza() {
            attachInterface(this, "com.google.android.gms.common.internal.ICertData");
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            switch (i) {
                case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                    parcel.enforceInterface("com.google.android.gms.common.internal.ICertData");
                    zzd zzaqn = zzaqn();
                    parcel2.writeNoException();
                    parcel2.writeStrongBinder(zzaqn != null ? zzaqn.asBinder() : null);
                    return true;
                case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                    parcel.enforceInterface("com.google.android.gms.common.internal.ICertData");
                    int zzaqo = zzaqo();
                    parcel2.writeNoException();
                    parcel2.writeInt(zzaqo);
                    return true;
                case 1598968902:
                    parcel2.writeString("com.google.android.gms.common.internal.ICertData");
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }
    }

    zzd zzaqn() throws RemoteException;

    int zzaqo() throws RemoteException;
}
