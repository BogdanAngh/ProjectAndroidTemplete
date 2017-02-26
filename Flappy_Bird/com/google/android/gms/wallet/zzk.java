package com.google.android.gms.wallet;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.identity.intents.model.UserAddress;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;

public class zzk implements Creator<MaskedWallet> {
    static void zza(MaskedWallet maskedWallet, Parcel parcel, int i) {
        int zzac = zzb.zzac(parcel);
        zzb.zzc(parcel, 1, maskedWallet.getVersionCode());
        zzb.zza(parcel, 2, maskedWallet.zzaQm, false);
        zzb.zza(parcel, 3, maskedWallet.zzaQn, false);
        zzb.zza(parcel, 4, maskedWallet.zzaQs, false);
        zzb.zza(parcel, 5, maskedWallet.zzaQp, false);
        zzb.zza(parcel, 6, maskedWallet.zzaQq, i, false);
        zzb.zza(parcel, 7, maskedWallet.zzaQr, i, false);
        zzb.zza(parcel, 8, maskedWallet.zzaRf, i, false);
        zzb.zza(parcel, 9, maskedWallet.zzaRg, i, false);
        zzb.zza(parcel, 10, maskedWallet.zzaQt, i, false);
        zzb.zza(parcel, 11, maskedWallet.zzaQu, i, false);
        zzb.zza(parcel, 12, maskedWallet.zzaQv, i, false);
        zzb.zzH(parcel, zzac);
    }

    public /* synthetic */ Object createFromParcel(Parcel x0) {
        return zzgk(x0);
    }

    public /* synthetic */ Object[] newArray(int x0) {
        return zzjm(x0);
    }

    public MaskedWallet zzgk(Parcel parcel) {
        int zzab = zza.zzab(parcel);
        int i = 0;
        String str = null;
        String str2 = null;
        String[] strArr = null;
        String str3 = null;
        Address address = null;
        Address address2 = null;
        LoyaltyWalletObject[] loyaltyWalletObjectArr = null;
        OfferWalletObject[] offerWalletObjectArr = null;
        UserAddress userAddress = null;
        UserAddress userAddress2 = null;
        InstrumentInfo[] instrumentInfoArr = null;
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
                    str2 = zza.zzo(parcel, zzaa);
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    strArr = zza.zzA(parcel, zzaa);
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    str3 = zza.zzo(parcel, zzaa);
                    break;
                case Place.TYPE_ATM /*6*/:
                    address = (Address) zza.zza(parcel, zzaa, Address.CREATOR);
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    address2 = (Address) zza.zza(parcel, zzaa, Address.CREATOR);
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                    loyaltyWalletObjectArr = (LoyaltyWalletObject[]) zza.zzb(parcel, zzaa, LoyaltyWalletObject.CREATOR);
                    break;
                case Place.TYPE_BAR /*9*/:
                    offerWalletObjectArr = (OfferWalletObject[]) zza.zzb(parcel, zzaa, OfferWalletObject.CREATOR);
                    break;
                case Place.TYPE_BEAUTY_SALON /*10*/:
                    userAddress = (UserAddress) zza.zza(parcel, zzaa, UserAddress.CREATOR);
                    break;
                case Place.TYPE_BICYCLE_STORE /*11*/:
                    userAddress2 = (UserAddress) zza.zza(parcel, zzaa, UserAddress.CREATOR);
                    break;
                case Place.TYPE_BOOK_STORE /*12*/:
                    instrumentInfoArr = (InstrumentInfo[]) zza.zzb(parcel, zzaa, InstrumentInfo.CREATOR);
                    break;
                default:
                    zza.zzb(parcel, zzaa);
                    break;
            }
        }
        if (parcel.dataPosition() == zzab) {
            return new MaskedWallet(i, str, str2, strArr, str3, address, address2, loyaltyWalletObjectArr, offerWalletObjectArr, userAddress, userAddress2, instrumentInfoArr);
        }
        throw new zza.zza("Overread allowed size end=" + zzab, parcel);
    }

    public MaskedWallet[] zzjm(int i) {
        return new MaskedWallet[i];
    }
}
