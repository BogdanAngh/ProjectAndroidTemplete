package com.google.android.gms.drive.query.internal;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.GeofenceStatusCodes;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class zzd implements Creator<FilterHolder> {
    static void zza(FilterHolder filterHolder, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zza(parcel, 1, filterHolder.zzahU, i, false);
        zzb.zzc(parcel, GeofenceStatusCodes.GEOFENCE_NOT_AVAILABLE, filterHolder.zzCY);
        zzb.zza(parcel, 2, filterHolder.zzahV, i, false);
        zzb.zza(parcel, 3, filterHolder.zzahW, i, false);
        zzb.zza(parcel, 4, filterHolder.zzahX, i, false);
        zzb.zza(parcel, 5, filterHolder.zzahY, i, false);
        zzb.zza(parcel, 6, filterHolder.zzahZ, i, false);
        zzb.zza(parcel, 7, filterHolder.zzaia, i, false);
        zzb.zza(parcel, 8, filterHolder.zzaib, i, false);
        zzb.zza(parcel, 9, filterHolder.zzaic, i, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzbK(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzdz(x0);
    }

    public FilterHolder zzbK(Parcel parcel) {
        OwnedByMeFilter ownedByMeFilter = null;
        int zzab = zza.zzab(parcel);
        int i = 0;
        FullTextSearchFilter fullTextSearchFilter = null;
        HasFilter hasFilter = null;
        MatchAllFilter matchAllFilter = null;
        InFilter inFilter = null;
        NotFilter notFilter = null;
        LogicalFilter logicalFilter = null;
        FieldOnlyFilter fieldOnlyFilter = null;
        ComparisonFilter comparisonFilter = null;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    comparisonFilter = (ComparisonFilter) zza.zza(parcel, zzaa, ComparisonFilter.CREATOR);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    fieldOnlyFilter = (FieldOnlyFilter) zza.zza(parcel, zzaa, FieldOnlyFilter.CREATOR);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    logicalFilter = (LogicalFilter) zza.zza(parcel, zzaa, LogicalFilter.CREATOR);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    notFilter = (NotFilter) zza.zza(parcel, zzaa, NotFilter.CREATOR);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    inFilter = (InFilter) zza.zza(parcel, zzaa, InFilter.CREATOR);
                    break;
                case Place.TYPE_ATM /*6*/:
                    matchAllFilter = (MatchAllFilter) zza.zza(parcel, zzaa, MatchAllFilter.CREATOR);
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    hasFilter = (HasFilter) zza.zza(parcel, zzaa, HasFilter.CREATOR);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    fullTextSearchFilter = (FullTextSearchFilter) zza.zza(parcel, zzaa, FullTextSearchFilter.CREATOR);
                    break;
                case Place.TYPE_BAR /*9*/:
                    ownedByMeFilter = (OwnedByMeFilter) zza.zza(parcel, zzaa, OwnedByMeFilter.CREATOR);
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
            return new FilterHolder(i, comparisonFilter, fieldOnlyFilter, logicalFilter, notFilter, inFilter, matchAllFilter, hasFilter, fullTextSearchFilter, ownedByMeFilter);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public FilterHolder[] zzdz(int i) {
        return new FilterHolder[i];
    }
}
