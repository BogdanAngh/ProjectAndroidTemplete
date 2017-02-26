package com.google.android.gms.location.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.List;

public class zzk implements Creator<LocationRequestInternal> {
    static void zza(LocationRequestInternal locationRequestInternal, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, locationRequestInternal.zzamz, i, false);
        zzb.zzc(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, locationRequestInternal.getVersionCode());
        zzb.zza(parcel, 2, locationRequestInternal.zzazb);
        zzb.zza(parcel, 3, locationRequestInternal.zzazc);
        zzb.zza(parcel, 4, locationRequestInternal.zzazd);
        zzb.zzc(parcel, 5, locationRequestInternal.zzaze, false);
        zzb.zza(parcel, 6, locationRequestInternal.mTag, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzek(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzgE(x0);
    }

    public LocationRequestInternal zzek(Parcel parcel) {
        String str = null;
        boolean z = true;
        boolean z2 = false;
        int zzab = zza.zzab(parcel);
        List list = LocationRequestInternal.zzaza;
        boolean z3 = true;
        LocationRequest locationRequest = null;
        int i = 0;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    locationRequest = (LocationRequest) zza.zza(parcel, zzaa, LocationRequest.CREATOR);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    z2 = zza.zzc(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    z3 = zza.zzc(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    z = zza.zzc(parcel, zzaa);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    list = zza.zzc(parcel, zzaa, ClientIdentity.CREATOR);
                    break;
                case Place.TYPE_ATM /*6*/:
                    str = zza.zzo(parcel, zzaa);
                    break;
                case GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE /*1000*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new LocationRequestInternal(i, locationRequest, z2, z3, z, list, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public LocationRequestInternal[] zzgE(int i) {
        return new LocationRequestInternal[i];
    }
}
