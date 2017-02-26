package com.google.android.gms.analytics.internal;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Command implements Parcelable {
    @Deprecated
    public static final Creator<Command> CREATOR;
    private String dW;
    private String mValue;
    private String zzboa;

    /* renamed from: com.google.android.gms.analytics.internal.Command.1 */
    class C11461 implements Creator<Command> {
        C11461() {
        }

        @Deprecated
        public /* synthetic */ Object createFromParcel(Parcel parcel) {
            return zzw(parcel);
        }

        @Deprecated
        public /* synthetic */ Object[] newArray(int i) {
            return zzcf(i);
        }

        @Deprecated
        public Command[] zzcf(int i) {
            return new Command[i];
        }

        @Deprecated
        public Command zzw(Parcel parcel) {
            return new Command(parcel);
        }
    }

    static {
        CREATOR = new C11461();
    }

    @Deprecated
    Command(Parcel parcel) {
        readFromParcel(parcel);
    }

    @Deprecated
    private void readFromParcel(Parcel parcel) {
        this.zzboa = parcel.readString();
        this.dW = parcel.readString();
        this.mValue = parcel.readString();
    }

    @Deprecated
    public int describeContents() {
        return 0;
    }

    public String getId() {
        return this.zzboa;
    }

    public String getValue() {
        return this.mValue;
    }

    @Deprecated
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.zzboa);
        parcel.writeString(this.dW);
        parcel.writeString(this.mValue);
    }
}
