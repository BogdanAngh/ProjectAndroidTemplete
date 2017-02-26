package com.google.android.gms.internal;

import com.google.android.gms.common.data.BitmapTeleporter;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.DriveSpace;
import com.google.android.gms.drive.metadata.MetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.drive.metadata.internal.zzi;
import com.google.android.gms.drive.metadata.internal.zzj;
import com.google.android.gms.drive.metadata.internal.zzl;
import com.google.android.gms.drive.metadata.internal.zzn;
import com.google.android.gms.drive.metadata.internal.zzo;
import com.google.android.gms.drive.metadata.internal.zzp;
import com.google.android.gms.plus.PlusShare;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class zzlo {
    public static final MetadataField<DriveId> zzagK;
    public static final MetadataField<String> zzagL;
    public static final zza zzagM;
    public static final MetadataField<String> zzagN;
    public static final MetadataField<String> zzagO;
    public static final MetadataField<String> zzagP;
    public static final MetadataField<Long> zzagQ;
    public static final MetadataField<String> zzagR;
    public static final MetadataField<Boolean> zzagS;
    public static final MetadataField<String> zzagT;
    public static final MetadataField<Boolean> zzagU;
    public static final MetadataField<Boolean> zzagV;
    public static final MetadataField<Boolean> zzagW;
    public static final MetadataField<Boolean> zzagX;
    public static final zzb zzagY;
    public static final MetadataField<Boolean> zzagZ;
    public static final MetadataField<Boolean> zzaha;
    public static final MetadataField<Boolean> zzahb;
    public static final MetadataField<Boolean> zzahc;
    public static final MetadataField<Boolean> zzahd;
    public static final MetadataField<Boolean> zzahe;
    public static final MetadataField<Boolean> zzahf;
    public static final zzc zzahg;
    public static final MetadataField<String> zzahh;
    public static final com.google.android.gms.drive.metadata.zzb<String> zzahi;
    public static final zzp zzahj;
    public static final zzp zzahk;
    public static final zzl zzahl;
    public static final zzd zzahm;
    public static final zzf zzahn;
    public static final MetadataField<BitmapTeleporter> zzaho;
    public static final zzg zzahp;
    public static final zzh zzahq;
    public static final MetadataField<String> zzahr;
    public static final MetadataField<String> zzahs;
    public static final MetadataField<String> zzaht;
    public static final com.google.android.gms.drive.metadata.internal.zzb zzahu;
    public static final MetadataField<String> zzahv;
    public static final MetadataField<String> zzahw;
    public static final zze zzahx;

    /* renamed from: com.google.android.gms.internal.zzlo.1 */
    static class C08471 extends com.google.android.gms.drive.metadata.internal.zzb {
        C08471(String str, Collection collection, Collection collection2, int i) {
            super(str, collection, collection2, i);
        }

        protected /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
            return zze(dataHolder, i, i2);
        }

        protected Boolean zze(DataHolder dataHolder, int i, int i2) {
            return Boolean.valueOf(dataHolder.zzc("trashed", i, i2) == 2);
        }
    }

    /* renamed from: com.google.android.gms.internal.zzlo.2 */
    static class C08482 extends zzj<BitmapTeleporter> {
        C08482(String str, Collection collection, Collection collection2, int i) {
            super(str, collection, collection2, i);
        }

        protected /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
            return zzk(dataHolder, i, i2);
        }

        protected BitmapTeleporter zzk(DataHolder dataHolder, int i, int i2) {
            throw new IllegalStateException("Thumbnail field is write only");
        }
    }

    public static class zzb extends com.google.android.gms.drive.metadata.internal.zzb implements SearchableMetadataField<Boolean> {
        public zzb(String str, int i) {
            super(str, i);
        }
    }

    public static class zzc extends zzo implements SearchableMetadataField<String> {
        public zzc(int i) {
            super("mimeType", i);
        }
    }

    public static class zzd extends com.google.android.gms.drive.metadata.internal.zzg implements SortableMetadataField<Long> {
        public zzd(String str, int i) {
            super(str, i);
        }
    }

    public static class zzf extends com.google.android.gms.drive.metadata.internal.zzb implements SearchableMetadataField<Boolean> {
        public zzf(String str, int i) {
            super(str, i);
        }
    }

    public static class zzg extends zzo implements SearchableMetadataField<String>, SortableMetadataField<String> {
        public zzg(String str, int i) {
            super(str, i);
        }
    }

    public static class zzh extends com.google.android.gms.drive.metadata.internal.zzb implements SearchableMetadataField<Boolean> {
        public zzh(String str, int i) {
            super(str, i);
        }

        protected /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
            return zze(dataHolder, i, i2);
        }

        protected Boolean zze(DataHolder dataHolder, int i, int i2) {
            return Boolean.valueOf(dataHolder.zzc(getName(), i, i2) != 0);
        }
    }

    public static class zza extends zzlp implements SearchableMetadataField<AppVisibleCustomProperties> {
        public zza(int i) {
            super(i);
        }
    }

    public static class zze extends zzi<DriveSpace> {
        public zze(int i) {
            super("spaces", Arrays.asList(new String[]{"inDriveSpace", "isAppData", "inGooglePhotosSpace"}), Collections.emptySet(), i);
        }

        protected /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
            return zzd(dataHolder, i, i2);
        }

        protected Collection<DriveSpace> zzd(DataHolder dataHolder, int i, int i2) {
            Collection arrayList = new ArrayList();
            if (dataHolder.zze("inDriveSpace", i, i2)) {
                arrayList.add(DriveSpace.zzadi);
            }
            if (dataHolder.zze("isAppData", i, i2)) {
                arrayList.add(DriveSpace.zzadj);
            }
            if (dataHolder.zze("inGooglePhotosSpace", i, i2)) {
                arrayList.add(DriveSpace.zzadk);
            }
            return arrayList;
        }
    }

    static {
        zzagK = zzlr.zzahD;
        zzagL = new zzo("alternateLink", 4300000);
        zzagM = new zza(5000000);
        zzagN = new zzo(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_DESCRIPTION, 4300000);
        zzagO = new zzo("embedLink", 4300000);
        zzagP = new zzo("fileExtension", 4300000);
        zzagQ = new com.google.android.gms.drive.metadata.internal.zzg("fileSize", 4300000);
        zzagR = new zzo("folderColorRgb", 7500000);
        zzagS = new com.google.android.gms.drive.metadata.internal.zzb("hasThumbnail", 4300000);
        zzagT = new zzo("indexableText", 4300000);
        zzagU = new com.google.android.gms.drive.metadata.internal.zzb("isAppData", 4300000);
        zzagV = new com.google.android.gms.drive.metadata.internal.zzb("isCopyable", 4300000);
        zzagW = new com.google.android.gms.drive.metadata.internal.zzb("isEditable", 4100000);
        zzagX = new C08471("isExplicitlyTrashed", Collections.singleton("trashed"), Collections.emptySet(), 7000000);
        zzagY = new zzb("isPinned", 4100000);
        zzagZ = new com.google.android.gms.drive.metadata.internal.zzb("isOpenable", 7200000);
        zzaha = new com.google.android.gms.drive.metadata.internal.zzb("isRestricted", 4300000);
        zzahb = new com.google.android.gms.drive.metadata.internal.zzb("isShared", 4300000);
        zzahc = new com.google.android.gms.drive.metadata.internal.zzb("isGooglePhotosFolder", 7000000);
        zzahd = new com.google.android.gms.drive.metadata.internal.zzb("isGooglePhotosRootFolder", 7000000);
        zzahe = new com.google.android.gms.drive.metadata.internal.zzb("isTrashable", 4400000);
        zzahf = new com.google.android.gms.drive.metadata.internal.zzb("isViewed", 4300000);
        zzahg = new zzc(4100000);
        zzahh = new zzo("originalFilename", 4300000);
        zzahi = new zzn("ownerNames", 4300000);
        zzahj = new zzp("lastModifyingUser", 6000000);
        zzahk = new zzp("sharingUser", 6000000);
        zzahl = new zzl(4100000);
        zzahm = new zzd("quotaBytesUsed", 4300000);
        zzahn = new zzf("starred", 4100000);
        zzaho = new C08482("thumbnail", Collections.emptySet(), Collections.emptySet(), 4400000);
        zzahp = new zzg(PlusShare.KEY_CONTENT_DEEP_LINK_METADATA_TITLE, 4100000);
        zzahq = new zzh("trashed", 4100000);
        zzahr = new zzo("webContentLink", 4300000);
        zzahs = new zzo("webViewLink", 4300000);
        zzaht = new zzo("uniqueIdentifier", 5000000);
        zzahu = new com.google.android.gms.drive.metadata.internal.zzb("writersCanShare", 6000000);
        zzahv = new zzo("role", 6000000);
        zzahw = new zzo("md5Checksum", 7000000);
        zzahx = new zze(7000000);
    }
}
