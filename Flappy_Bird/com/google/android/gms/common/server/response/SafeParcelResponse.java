package com.google.android.gms.common.server.response;

import android.os.Bundle;
import android.os.Parcel;
import com.google.android.gms.common.internal.safeparcel.SafeParcelable;
import com.google.android.gms.common.internal.safeparcel.zza;
import com.google.android.gms.common.internal.safeparcel.zzb;
import com.google.android.gms.common.internal.zzu;
import com.google.android.gms.common.server.response.FastJsonResponse.Field;
import com.google.android.gms.drive.events.CompletionEvent;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.internal.zzky;
import com.google.android.gms.internal.zzlh;
import com.google.android.gms.internal.zzli;
import com.google.android.gms.location.places.Place;
import com.google.example.games.basegameutils.GameHelper;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class SafeParcelResponse extends FastJsonResponse implements SafeParcelable {
    public static final zze CREATOR;
    private final String mClassName;
    private final int zzCY;
    private final FieldMappingDictionary zzabO;
    private final Parcel zzabV;
    private final int zzabW;
    private int zzabX;
    private int zzabY;

    static {
        CREATOR = new zze();
    }

    SafeParcelResponse(int versionCode, Parcel parcel, FieldMappingDictionary fieldMappingDictionary) {
        this.zzCY = versionCode;
        this.zzabV = (Parcel) zzu.zzu(parcel);
        this.zzabW = 2;
        this.zzabO = fieldMappingDictionary;
        if (this.zzabO == null) {
            this.mClassName = null;
        } else {
            this.mClassName = this.zzabO.zzoC();
        }
        this.zzabX = 2;
    }

    private SafeParcelResponse(SafeParcelable safeParcelable, FieldMappingDictionary dictionary, String className) {
        this.zzCY = 1;
        this.zzabV = Parcel.obtain();
        safeParcelable.writeToParcel(this.zzabV, 0);
        this.zzabW = 1;
        this.zzabO = (FieldMappingDictionary) zzu.zzu(dictionary);
        this.mClassName = (String) zzu.zzu(className);
        this.zzabX = 2;
    }

    private static HashMap<Integer, Entry<String, Field<?, ?>>> zzC(Map<String, Field<?, ?>> map) {
        HashMap<Integer, Entry<String, Field<?, ?>>> hashMap = new HashMap();
        for (Entry entry : map.entrySet()) {
            hashMap.put(Integer.valueOf(((Field) entry.getValue()).zzot()), entry);
        }
        return hashMap;
    }

    public static <T extends FastJsonResponse & SafeParcelable> SafeParcelResponse zza(T t) {
        String canonicalName = t.getClass().getCanonicalName();
        return new SafeParcelResponse((SafeParcelable) t, zzb(t), canonicalName);
    }

    private static void zza(FieldMappingDictionary fieldMappingDictionary, FastJsonResponse fastJsonResponse) {
        Class cls = fastJsonResponse.getClass();
        if (!fieldMappingDictionary.zzb(cls)) {
            Map zzom = fastJsonResponse.zzom();
            fieldMappingDictionary.zza(cls, zzom);
            for (String str : zzom.keySet()) {
                Field field = (Field) zzom.get(str);
                Class zzou = field.zzou();
                if (zzou != null) {
                    try {
                        zza(fieldMappingDictionary, (FastJsonResponse) zzou.newInstance());
                    } catch (Throwable e) {
                        throw new IllegalStateException("Could not instantiate an object of type " + field.zzou().getCanonicalName(), e);
                    } catch (Throwable e2) {
                        throw new IllegalStateException("Could not access object of type " + field.zzou().getCanonicalName(), e2);
                    }
                }
            }
        }
    }

    private void zza(StringBuilder stringBuilder, int i, Object obj) {
        switch (i) {
            case GameHelper.CLIENT_NONE /*0*/:
            case CompletionEvent.STATUS_FAILURE /*1*/:
            case CompletionEvent.STATUS_CONFLICT /*2*/:
            case CompletionEvent.STATUS_CANCELED /*3*/:
            case GameHelper.CLIENT_APPSTATE /*4*/:
            case Place.TYPE_ART_GALLERY /*5*/:
            case Place.TYPE_ATM /*6*/:
                stringBuilder.append(obj);
            case Place.TYPE_BAKERY /*7*/:
                stringBuilder.append("\"").append(zzlh.zzcr(obj.toString())).append("\"");
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
                stringBuilder.append("\"").append(zzky.zzi((byte[]) obj)).append("\"");
            case Place.TYPE_BAR /*9*/:
                stringBuilder.append("\"").append(zzky.zzj((byte[]) obj));
                stringBuilder.append("\"");
            case Place.TYPE_BEAUTY_SALON /*10*/:
                zzli.zza(stringBuilder, (HashMap) obj);
            case Place.TYPE_BICYCLE_STORE /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown type = " + i);
        }
    }

    private void zza(StringBuilder stringBuilder, Field<?, ?> field, Parcel parcel, int i) {
        switch (field.zzol()) {
            case GameHelper.CLIENT_NONE /*0*/:
                zzb(stringBuilder, (Field) field, zza(field, Integer.valueOf(zza.zzg(parcel, i))));
            case CompletionEvent.STATUS_FAILURE /*1*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzk(parcel, i)));
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                zzb(stringBuilder, (Field) field, zza(field, Long.valueOf(zza.zzi(parcel, i))));
            case CompletionEvent.STATUS_CANCELED /*3*/:
                zzb(stringBuilder, (Field) field, zza(field, Float.valueOf(zza.zzl(parcel, i))));
            case GameHelper.CLIENT_APPSTATE /*4*/:
                zzb(stringBuilder, (Field) field, zza(field, Double.valueOf(zza.zzm(parcel, i))));
            case Place.TYPE_ART_GALLERY /*5*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzn(parcel, i)));
            case Place.TYPE_ATM /*6*/:
                zzb(stringBuilder, (Field) field, zza(field, Boolean.valueOf(zza.zzc(parcel, i))));
            case Place.TYPE_BAKERY /*7*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzo(parcel, i)));
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
            case Place.TYPE_BAR /*9*/:
                zzb(stringBuilder, (Field) field, zza(field, zza.zzr(parcel, i)));
            case Place.TYPE_BEAUTY_SALON /*10*/:
                zzb(stringBuilder, (Field) field, zza(field, zzh(zza.zzq(parcel, i))));
            case Place.TYPE_BICYCLE_STORE /*11*/:
                throw new IllegalArgumentException("Method does not accept concrete type.");
            default:
                throw new IllegalArgumentException("Unknown field out type = " + field.zzol());
        }
    }

    private void zza(StringBuilder stringBuilder, String str, Field<?, ?> field, Parcel parcel, int i) {
        stringBuilder.append("\"").append(str).append("\":");
        if (field.zzow()) {
            zza(stringBuilder, field, parcel, i);
        } else {
            zzb(stringBuilder, field, parcel, i);
        }
    }

    private void zza(StringBuilder stringBuilder, Map<String, Field<?, ?>> map, Parcel parcel) {
        HashMap zzC = zzC(map);
        stringBuilder.append('{');
        int zzab = zza.zzab(parcel);
        Object obj = null;
        while (parcel.dataPosition() < zzab) {
            int zzaa = zza.zzaa(parcel);
            Entry entry = (Entry) zzC.get(Integer.valueOf(zza.zzbA(zzaa)));
            if (entry != null) {
                if (obj != null) {
                    stringBuilder.append(",");
                }
                zza(stringBuilder, (String) entry.getKey(), (Field) entry.getValue(), parcel, zzaa);
                obj = 1;
            }
        }
        if (parcel.dataPosition() != zzab) {
            throw new zza.zza("Overread allowed size end=" + zzab, parcel);
        }
        stringBuilder.append('}');
    }

    private static FieldMappingDictionary zzb(FastJsonResponse fastJsonResponse) {
        FieldMappingDictionary fieldMappingDictionary = new FieldMappingDictionary(fastJsonResponse.getClass());
        zza(fieldMappingDictionary, fastJsonResponse);
        fieldMappingDictionary.zzoA();
        fieldMappingDictionary.zzoz();
        return fieldMappingDictionary;
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, Parcel parcel, int i) {
        if (field.zzor()) {
            stringBuilder.append("[");
            switch (field.zzol()) {
                case GameHelper.CLIENT_NONE /*0*/:
                    zzkx.zza(stringBuilder, zza.zzu(parcel, i));
                    break;
                case CompletionEvent.STATUS_FAILURE /*1*/:
                    zzkx.zza(stringBuilder, zza.zzw(parcel, i));
                    break;
                case CompletionEvent.STATUS_CONFLICT /*2*/:
                    zzkx.zza(stringBuilder, zza.zzv(parcel, i));
                    break;
                case CompletionEvent.STATUS_CANCELED /*3*/:
                    zzkx.zza(stringBuilder, zza.zzx(parcel, i));
                    break;
                case GameHelper.CLIENT_APPSTATE /*4*/:
                    zzkx.zza(stringBuilder, zza.zzy(parcel, i));
                    break;
                case Place.TYPE_ART_GALLERY /*5*/:
                    zzkx.zza(stringBuilder, zza.zzz(parcel, i));
                    break;
                case Place.TYPE_ATM /*6*/:
                    zzkx.zza(stringBuilder, zza.zzt(parcel, i));
                    break;
                case Place.TYPE_BAKERY /*7*/:
                    zzkx.zza(stringBuilder, zza.zzA(parcel, i));
                    break;
                case GameHelper.CLIENT_SNAPSHOT /*8*/:
                case Place.TYPE_BAR /*9*/:
                case Place.TYPE_BEAUTY_SALON /*10*/:
                    throw new UnsupportedOperationException("List of type BASE64, BASE64_URL_SAFE, or STRING_MAP is not supported");
                case Place.TYPE_BICYCLE_STORE /*11*/:
                    Parcel[] zzE = zza.zzE(parcel, i);
                    int length = zzE.length;
                    for (int i2 = 0; i2 < length; i2++) {
                        if (i2 > 0) {
                            stringBuilder.append(",");
                        }
                        zzE[i2].setDataPosition(0);
                        zza(stringBuilder, field.zzoy(), zzE[i2]);
                    }
                    break;
                default:
                    throw new IllegalStateException("Unknown field type out.");
            }
            stringBuilder.append("]");
            return;
        }
        switch (field.zzol()) {
            case GameHelper.CLIENT_NONE /*0*/:
                stringBuilder.append(zza.zzg(parcel, i));
            case CompletionEvent.STATUS_FAILURE /*1*/:
                stringBuilder.append(zza.zzk(parcel, i));
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                stringBuilder.append(zza.zzi(parcel, i));
            case CompletionEvent.STATUS_CANCELED /*3*/:
                stringBuilder.append(zza.zzl(parcel, i));
            case GameHelper.CLIENT_APPSTATE /*4*/:
                stringBuilder.append(zza.zzm(parcel, i));
            case Place.TYPE_ART_GALLERY /*5*/:
                stringBuilder.append(zza.zzn(parcel, i));
            case Place.TYPE_ATM /*6*/:
                stringBuilder.append(zza.zzc(parcel, i));
            case Place.TYPE_BAKERY /*7*/:
                stringBuilder.append("\"").append(zzlh.zzcr(zza.zzo(parcel, i))).append("\"");
            case GameHelper.CLIENT_SNAPSHOT /*8*/:
                stringBuilder.append("\"").append(zzky.zzi(zza.zzr(parcel, i))).append("\"");
            case Place.TYPE_BAR /*9*/:
                stringBuilder.append("\"").append(zzky.zzj(zza.zzr(parcel, i)));
                stringBuilder.append("\"");
            case Place.TYPE_BEAUTY_SALON /*10*/:
                Bundle zzq = zza.zzq(parcel, i);
                Set<String> keySet = zzq.keySet();
                keySet.size();
                stringBuilder.append("{");
                int i3 = 1;
                for (String str : keySet) {
                    if (i3 == 0) {
                        stringBuilder.append(",");
                    }
                    stringBuilder.append("\"").append(str).append("\"");
                    stringBuilder.append(":");
                    stringBuilder.append("\"").append(zzlh.zzcr(zzq.getString(str))).append("\"");
                    i3 = 0;
                }
                stringBuilder.append("}");
            case Place.TYPE_BICYCLE_STORE /*11*/:
                Parcel zzD = zza.zzD(parcel, i);
                zzD.setDataPosition(0);
                zza(stringBuilder, field.zzoy(), zzD);
            default:
                throw new IllegalStateException("Unknown field type out");
        }
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, Object obj) {
        if (field.zzoq()) {
            zzb(stringBuilder, (Field) field, (ArrayList) obj);
        } else {
            zza(stringBuilder, field.zzok(), obj);
        }
    }

    private void zzb(StringBuilder stringBuilder, Field<?, ?> field, ArrayList<?> arrayList) {
        stringBuilder.append("[");
        int size = arrayList.size();
        for (int i = 0; i < size; i++) {
            if (i != 0) {
                stringBuilder.append(",");
            }
            zza(stringBuilder, field.zzok(), arrayList.get(i));
        }
        stringBuilder.append("]");
    }

    public static HashMap<String, String> zzh(Bundle bundle) {
        HashMap<String, String> hashMap = new HashMap();
        for (String str : bundle.keySet()) {
            hashMap.put(str, bundle.getString(str));
        }
        return hashMap;
    }

    public int describeContents() {
        zze com_google_android_gms_common_server_response_zze = CREATOR;
        return 0;
    }

    public int getVersionCode() {
        return this.zzCY;
    }

    public String toString() {
        zzu.zzb(this.zzabO, (Object) "Cannot convert to JSON on client side.");
        Parcel zzoE = zzoE();
        zzoE.setDataPosition(0);
        StringBuilder stringBuilder = new StringBuilder(100);
        zza(stringBuilder, this.zzabO.zzco(this.mClassName), zzoE);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel out, int flags) {
        zze com_google_android_gms_common_server_response_zze = CREATOR;
        zze.zza(this, out, flags);
    }

    protected Object zzck(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    protected boolean zzcl(String str) {
        throw new UnsupportedOperationException("Converting to JSON does not require this method.");
    }

    public Parcel zzoE() {
        switch (this.zzabX) {
            case GameHelper.CLIENT_NONE /*0*/:
                this.zzabY = zzb.zzac(this.zzabV);
                zzb.zzH(this.zzabV, this.zzabY);
                this.zzabX = 2;
                break;
            case CompletionEvent.STATUS_FAILURE /*1*/:
                zzb.zzH(this.zzabV, this.zzabY);
                this.zzabX = 2;
                break;
        }
        return this.zzabV;
    }

    FieldMappingDictionary zzoF() {
        switch (this.zzabW) {
            case GameHelper.CLIENT_NONE /*0*/:
                return null;
            case CompletionEvent.STATUS_FAILURE /*1*/:
                return this.zzabO;
            case CompletionEvent.STATUS_CONFLICT /*2*/:
                return this.zzabO;
            default:
                throw new IllegalStateException("Invalid creation type: " + this.zzabW);
        }
    }

    public Map<String, Field<?, ?>> zzom() {
        return this.zzabO == null ? null : this.zzabO.zzco(this.mClassName);
    }
}
