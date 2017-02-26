package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.wallet.wobs.CommonWalletObject;
import com.google.example.games.basegameutils.GameHelper;

public class zzg implements Creator<GiftCardWalletObject> {
    static void zza(GiftCardWalletObject giftCardWalletObject, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, giftCardWalletObject.getVersionCode());
        zzb.zza(parcel, 2, giftCardWalletObject.zzaQz, i, false);
        zzb.zza(parcel, 3, giftCardWalletObject.zzaQA, false);
        zzb.zza(parcel, 4, giftCardWalletObject.pin, false);
        zzb.zza(parcel, 5, giftCardWalletObject.zzaQB, false);
        zzb.zza(parcel, 6, giftCardWalletObject.zzaQC);
        zzb.zza(parcel, 7, giftCardWalletObject.zzaQD, false);
        zzb.zza(parcel, 8, giftCardWalletObject.zzaQE);
        zzb.zza(parcel, 9, giftCardWalletObject.zzaQF, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzgg(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzji(x0);
    }

    public GiftCardWalletObject zzgg(Parcel parcel) {
        long j = 0;
        String str = null;
        int zzab = zza.zzab(parcel);
        int i = 0;
        String str2 = null;
        long j2 = 0;
        String str3 = null;
        String str4 = null;
        String str5 = null;
        CommonWalletObject commonWalletObject = null;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            switch (zza.zzbA(zzaa)) {
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    i = zza.zzg(parcel, zzaa);
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    commonWalletObject = (CommonWalletObject) zza.zza(parcel, zzaa, CommonWalletObject.CREATOR);
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    str5 = zza.zzo(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    str4 = zza.zzo(parcel, zzaa);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    str3 = zza.zzo(parcel, zzaa);
                    break;
                case Place.TYPE_ATM /*6*/:
                    j2 = zza.zzi(parcel, zzaa);
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    str2 = zza.zzo(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    j = zza.zzi(parcel, zzaa);
                    break;
                case Place.TYPE_BAR /*9*/:
                    str = zza.zzo(parcel, zzaa);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new GiftCardWalletObject(i, commonWalletObject, str5, str4, str3, j2, str2, j, str);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public GiftCardWalletObject[] zzji(int i) {
        return new GiftCardWalletObject[i];
    }
}
