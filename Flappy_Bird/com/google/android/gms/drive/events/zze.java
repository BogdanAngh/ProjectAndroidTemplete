package com.google.android.gms.drive.events;

import android.os.IBinder;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.internal.MetadataBundle;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.List;

public class zze implements Creator<CompletionEvent> {
    static void zza(CompletionEvent completionEvent, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, completionEvent.zzCY);
        zzb.zza(parcel, 2, completionEvent.zzacT, i, false);
        zzb.zza(parcel, 3, completionEvent.zzOx, false);
        zzb.zza(parcel, 4, completionEvent.zzadT, i, false);
        zzb.zza(parcel, 5, completionEvent.zzadU, i, false);
        zzb.zza(parcel, 6, completionEvent.zzadV, i, false);
        zzb.zzb(parcel, 7, completionEvent.zzadW, false);
        zzb.zzc(parcel, 8, completionEvent.zzwS);
        zzb.zza(parcel, 9, completionEvent.zzadX, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzay(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzch(x0);
    }

    public CompletionEvent zzay(Parcel parcel) {
        int i = 0;
        IBinder iBinder = null;
        int zzab = zza.zzab(parcel);
        List list = null;
        MetadataBundle metadataBundle = null;
        ParcelFileDescriptor parcelFileDescriptor = null;
        ParcelFileDescriptor parcelFileDescriptor2 = null;
        String str = null;
        DriveId driveId = null;
        int i2 = 0;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    i2 = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    driveId = (DriveId) zza.zza(parcel, zzaa, DriveId.CREATOR);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    str = zza.zzo(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    parcelFileDescriptor2 = (ParcelFileDescriptor) zza.zza(parcel, zzaa, ParcelFileDescriptor.CREATOR);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    parcelFileDescriptor = (ParcelFileDescriptor) zza.zza(parcel, zzaa, ParcelFileDescriptor.CREATOR);
                    break;
                case Place.TYPE_ATM /*6*/:
                    metadataBundle = (MetadataBundle) zza.zza(parcel, zzaa, MetadataBundle.CREATOR);
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    list = zza.zzC(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                case Place.TYPE_BAR /*9*/:
                    iBinder = zza.zzp(parcel, zzaa);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new CompletionEvent(i2, driveId, str, parcelFileDescriptor2, parcelFileDescriptor, metadataBundle, list, i, iBinder);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public CompletionEvent[] zzch(int i) {
        return new CompletionEvent[i];
    }
}
