package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.ChangeEvent;
import com.google.android.gms.drive.events.ChangesAvailableEvent;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.events.ProgressEvent;
import com.google.android.gms.drive.events.QueryResultEventParcelable;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class zzaz implements Creator<OnEventResponse> {
    static void zza(OnEventResponse onEventResponse, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, onEventResponse.zzCY);
        zzb.zzc(parcel, 2, onEventResponse.zzaca);
        zzb.zza(parcel, 3, onEventResponse.zzagj, i, false);
        zzb.zza(parcel, 5, onEventResponse.zzagk, i, false);
        zzb.zza(parcel, 6, onEventResponse.zzagl, i, false);
        zzb.zza(parcel, 7, onEventResponse.zzagm, i, false);
        zzb.zza(parcel, 8, onEventResponse.zzagn, i, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzbe(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzcT(x0);
    }

    public OnEventResponse zzbe(Parcel parcel) {
        int i = 0;
        ProgressEvent progressEvent = null;
        int zzab = zza.zzab(parcel);
        ChangesAvailableEvent changesAvailableEvent = null;
        QueryResultEventParcelable queryResultEventParcelable = null;
        CompletionEvent completionEvent = null;
        ChangeEvent changeEvent = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    i2 = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    changeEvent = (ChangeEvent) zza.zza(parcel, zzaa, ChangeEvent.CREATOR);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    completionEvent = (CompletionEvent) zza.zza(parcel, zzaa, CompletionEvent.CREATOR);
                    break;
                case Place.TYPE_ATM /*6*/:
                    queryResultEventParcelable = (QueryResultEventParcelable) zza.zza(parcel, zzaa, QueryResultEventParcelable.CREATOR);
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    changesAvailableEvent = (ChangesAvailableEvent) zza.zza(parcel, zzaa, ChangesAvailableEvent.CREATOR);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    progressEvent = (ProgressEvent) zza.zza(parcel, zzaa, ProgressEvent.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new OnEventResponse(i2, i, changeEvent, completionEvent, queryResultEventParcelable, changesAvailableEvent, progressEvent);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public OnEventResponse[] zzcT(int i) {
        return new OnEventResponse[i];
    }
}
