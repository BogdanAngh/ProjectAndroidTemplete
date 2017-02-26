package com.google.android.gms.wearable.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import android.util.Log;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.wearable.ChannelApi.ChannelListener;
import com.google.example.games.basegameutils.GameHelper;

public final class ChannelEventParcelable implements SafeParcelable {
    public static final Creator<ChannelEventParcelable> CREATOR;
    final int type;
    final int zzCY;
    final int zzaTN;
    final int zzaTO;
    final ChannelImpl zzaTP;

    static {
        CREATOR = new zzk();
    }

    ChannelEventParcelable(int versionCode, ChannelImpl channel, int type, int closeReason, int appErrorCode) {
        this.zzCY = versionCode;
        this.zzaTP = channel;
        this.type = type;
        this.zzaTN = closeReason;
        this.zzaTO = appErrorCode;
    }

    private static String zzjT(int i) {
        switch (i) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "CHANNEL_OPENED";
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return "CHANNEL_CLOSED";
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return "INPUT_CLOSED";
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return "OUTPUT_CLOSED";
            default:
                return Integer.toString(i);
        }
    }

    private static String zzjU(int i) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
                return "CLOSE_REASON_NORMAL";
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return "CLOSE_REASON_DISCONNECTED";
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return "CLOSE_REASON_REMOTE_CLOSE";
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return "CLOSE_REASON_LOCAL_CLOSE";
            default:
                return Integer.toString(i);
        }
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        return "ChannelEventParcelable[versionCode=" + this.zzCY + ", channel=" + this.zzaTP + ", type=" + zzjT(this.type) + ", closeReason=" + zzjU(this.zzaTN) + ", appErrorCode=" + this.zzaTO + "]";
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzk.zza(this, dest, flags);
    }

    public void zza(ChannelListener channelListener) {
        switch (this.type) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                channelListener.onChannelOpened(this.zzaTP);
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                channelListener.onChannelClosed(this.zzaTP, this.zzaTN, this.zzaTO);
            case CompletionEvent.STATUS_CANCELED /*3*/:
                channelListener.onInputClosed(this.zzaTP, this.zzaTN, this.zzaTO);
            case GameHelper.CLIENT_APPSTATE /*4*/:
                channelListener.onOutputClosed(this.zzaTP, this.zzaTN, this.zzaTO);
            default:
                Log.w("ChannelEventParcelable", "Unknown type: " + this.type);
        }
    }
}
