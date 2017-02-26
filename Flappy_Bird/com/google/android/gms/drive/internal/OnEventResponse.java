package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangesAvailableEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.DriveEvent;
import com.google.android.gms.drive.events.ProgressEvent;
import com.google.android.gms.drive.events.QueryResultEventParcelable;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class OnEventResponse implements SafeParcelable {
    public static final Creator<OnEventResponse> CREATOR;
    final int zzCY;
    final int zzaca;
    final ChangeEvent zzagj;
    final CompletionEvent zzagk;
    final QueryResultEventParcelable zzagl;
    final ChangesAvailableEvent zzagm;
    final ProgressEvent zzagn;

    static {
        CREATOR = new zzaz();
    }

    OnEventResponse(int versionCode, int eventType, ChangeEvent changeEvent, CompletionEvent completionEvent, QueryResultEventParcelable queryResultEvent, ChangesAvailableEvent changesAvailableEvent, ProgressEvent progressEvent) {
        this.zzCY = versionCode;
        this.zzaca = eventType;
        this.zzagj = changeEvent;
        this.zzagk = completionEvent;
        this.zzagl = queryResultEvent;
        this.zzagm = changesAvailableEvent;
        this.zzagn = progressEvent;
    }

    public int describeContents() {
        return 0;
    }

    public void writeToParcel(Parcel dest, int flags) {
        zzaz.zza(this, dest, flags);
    }

    public DriveEvent zzpO() {
        switch (this.zzaca) {
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return this.zzagj;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return this.zzagk;
            case CompletionEvent.STATUS_CANCELED /*3*/:
                return this.zzagl;
            case GameHelper.CLIENT_APPSTATE /*4*/:
                return this.zzagm;
            case Place.TYPE_ART_GALLERY /*5*/:
            case Place.TYPE_ATM /*6*/:
                return this.zzagn;
            default:
                throw new IllegalStateException("Unexpected event type " + this.zzaca);
        }
    }
}
