package com.google.android.gms.fitness.data;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.provider.Settings.Secure;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.zzt;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.internal.zzlv;
import com.google.android.gms.internal.zzmy;

public final class Device implements SafeParcelable {
    public static final Creator<Device> CREATOR;
    public static final int TYPE_CHEST_STRAP = 4;
    public static final int TYPE_PHONE = 1;
    public static final int TYPE_SCALE = 5;
    public static final int TYPE_TABLET = 2;
    public static final int TYPE_UNKNOWN = 0;
    public static final int TYPE_WATCH = 3;
    private final int zzCY;
    private final int zzSq;
    private final String zzTQ;
    private final String zzakr;
    private final String zzaks;
    private final String zzakt;
    private final int zzaku;

    static {
        CREATOR = new zzh();
    }

    Device(int versionCode, String manufacturer, String model, String version, String uid, int type, int platformType) {
        this.zzCY = versionCode;
        this.zzakr = (String) zzu.zzu(manufacturer);
        this.zzaks = (String) zzu.zzu(model);
        this.zzTQ = "";
        this.zzakt = (String) zzu.zzu(uid);
        this.zzSq = type;
        this.zzaku = platformType;
    }

    public Device(String manufacturer, String model, String uid, int type) {
        this(manufacturer, model, "", uid, type, TYPE_UNKNOWN);
    }

    public Device(String manufacturer, String model, String version, String uid, int type, int platformType) {
        this(TYPE_PHONE, manufacturer, model, "", uid, type, platformType);
    }

    public static Device getLocalDevice(Context context) {
        int zzap = zzlv.zzap(context);
        return new Device(Build.MANUFACTURER, Build.MODEL, VERSION.RELEASE, zzal(context), zzap, TYPE_TABLET);
    }

    private boolean zza(Device device) {
        return zzt.equal(this.zzakr, device.zzakr) && zzt.equal(this.zzaks, device.zzaks) && zzt.equal(this.zzTQ, device.zzTQ) && zzt.equal(this.zzakt, device.zzakt) && this.zzSq == device.zzSq && this.zzaku == device.zzaku;
    }

    private static String zzal(Context context) {
        return Secure.getString(context.getContentResolver(), "android_id");
    }

    private boolean zzqF() {
        return zzqE() == TYPE_PHONE;
    }

    public int describeContents() {
        return TYPE_UNKNOWN;
    }

    public boolean equals(Object that) {
        return this == that || ((that instanceof Device) && zza((Device) that));
    }

    public String getManufacturer() {
        return this.zzakr;
    }

    public String getModel() {
        return this.zzaks;
    }

    String getStreamIdentifier() {
        Object[] objArr = new Object[TYPE_WATCH];
        objArr[TYPE_UNKNOWN] = this.zzakr;
        objArr[TYPE_PHONE] = this.zzaks;
        objArr[TYPE_TABLET] = this.zzakt;
        return String.format("%s:%s:%s", objArr);
    }

    public int getType() {
        return this.zzSq;
    }

    public String getUid() {
        return this.zzakt;
    }

    public String getVersion() {
        return this.zzTQ;
    }

    int getVersionCode() {
        return this.zzCY;
    }

    public int hashCode() {
        Object[] objArr = new Object[TYPE_SCALE];
        objArr[TYPE_UNKNOWN] = this.zzakr;
        objArr[TYPE_PHONE] = this.zzaks;
        objArr[TYPE_TABLET] = this.zzTQ;
        objArr[TYPE_WATCH] = this.zzakt;
        objArr[TYPE_CHEST_STRAP] = Integer.valueOf(this.zzSq);
        return zzt.hashCode(objArr);
    }

    public String toString() {
        Object[] objArr = new Object[TYPE_CHEST_STRAP];
        objArr[TYPE_UNKNOWN] = getStreamIdentifier();
        objArr[TYPE_PHONE] = this.zzTQ;
        objArr[TYPE_TABLET] = Integer.valueOf(this.zzSq);
        objArr[TYPE_WATCH] = Integer.valueOf(this.zzaku);
        return String.format("Device{%s:%s:%s:%s}", objArr);
    }

    public void writeToParcel(Parcel parcel, int flags) {
        zzh.zza(this, parcel, flags);
    }

    public int zzqE() {
        return this.zzaku;
    }

    public String zzqG() {
        return zzqF() ? this.zzakt : zzmy.zzcL(this.zzakt);
    }
}
