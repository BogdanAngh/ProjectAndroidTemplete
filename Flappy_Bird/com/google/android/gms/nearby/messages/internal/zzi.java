package com.google.android.gms.nearby.messages.internal;

import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.nearby.messages.Strategy;
import com.google.example.games.basegameutils.GameHelper;

public class zzi implements Creator<PublishRequest> {
    static void zza(PublishRequest publishRequest, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, publishRequest.zzCY);
        zzb.zza(parcel, 2, publishRequest.zzaGb, i, false);
        zzb.zza(parcel, 3, publishRequest.zzaGc, i, false);
        zzb.zza(parcel, 4, publishRequest.zzxa(), false);
        zzb.zza(parcel, 5, publishRequest.zzayp, false);
        zzb.zza(parcel, 6, publishRequest.zzaGe, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzfu(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzij(x0);
    }

    public PublishRequest zzfu(Parcel parcel) {
        String str = null;
        int zzab = zza.zzab(parcel);
        int i = 0;
        String str2 = null;
        IBinder iBinder = null;
        Strategy strategy = null;
        MessageWrapper messageWrapper = null;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    messageWrapper = (MessageWrapper) zza.zza(parcel, zzaa, MessageWrapper.CREATOR);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    strategy = (Strategy) zza.zza(parcel, zzaa, Strategy.CREATOR);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    iBinder = zza.zzp(parcel, zzaa);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    str2 = zza.zzo(parcel, zzaa);
                    break;
                case Place.TYPE_ATM /*6*/:
                    str = zza.zzo(parcel, zzaa);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new PublishRequest(i, messageWrapper, strategy, iBinder, str2, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public PublishRequest[] zzij(int i) {
        return new PublishRequest[i];
    }
}
