package com.google.android.gms.drive.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.drive.query.internal.FilterHolder;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class zzbj implements Creator<OpenFileIntentSenderRequest> {
    static void zza(OpenFileIntentSenderRequest openFileIntentSenderRequest, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, openFileIntentSenderRequest.zzCY);
        zzb.zza(parcel, 2, openFileIntentSenderRequest.zzadv, false);
        zzb.zza(parcel, 3, openFileIntentSenderRequest.zzadw, false);
        zzb.zza(parcel, 4, openFileIntentSenderRequest.zzady, i, false);
        zzb.zza(parcel, 5, openFileIntentSenderRequest.zzagt, i, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzbn(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzdc(x0);
    }

    public OpenFileIntentSenderRequest zzbn(Parcel parcel) {
        FilterHolder filterHolder = null;
        int zzab = zza.zzab(parcel);
        int i = 0;
        DriveId driveId = null;
        String[] strArr = null;
        String str = null;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    str = zza.zzo(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    strArr = zza.zzA(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    driveId = (DriveId) zza.zza(parcel, zzaa, DriveId.CREATOR);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    filterHolder = (FilterHolder) zza.zza(parcel, zzaa, FilterHolder.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new OpenFileIntentSenderRequest(i, str, strArr, driveId, filterHolder);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public OpenFileIntentSenderRequest[] zzdc(int i) {
        return new OpenFileIntentSenderRequest[i];
    }
}
