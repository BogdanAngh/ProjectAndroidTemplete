package com.google.android.gms.drive.metadata.internal;

import android.os.Bundle;
import com.google.android.gms.common.data.DataHolder;
import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class zzl extends zzi<DriveId> implements SearchableCollectionMetadataField<DriveId> {
    public zzl(int i) {
        super("parents", Collections.emptySet(), Arrays.asList(new String[]{"parentsExtra", "dbInstanceId", "parentsExtraHolder"}), i);
    }

    private void zzc(DataHolder dataHolder) {
        synchronized (dataHolder) {
            DataHolder dataHolder2 = (DataHolder) dataHolder.zznb().getParcelable("parentsExtraHolder");
            if (dataHolder2 == null) {
                return;
            }
            try {
                int i;
                int count = dataHolder.getCount();
                ArrayList arrayList = new ArrayList(count);
                Map hashMap = new HashMap(count);
                for (i = 0; i < count; i++) {
                    int zzbh = dataHolder.zzbh(i);
                    ParentDriveIdSet parentDriveIdSet = new ParentDriveIdSet();
                    arrayList.add(parentDriveIdSet);
                    hashMap.put(Long.valueOf(dataHolder.zzb("sqlId", i, zzbh)), parentDriveIdSet);
                }
                Bundle zznb = dataHolder2.zznb();
                String string = zznb.getString("childSqlIdColumn");
                String string2 = zznb.getString("parentSqlIdColumn");
                String string3 = zznb.getString("parentResIdColumn");
                int count2 = dataHolder2.getCount();
                for (i = 0; i < count2; i++) {
                    int zzbh2 = dataHolder2.zzbh(i);
                    ((ParentDriveIdSet) hashMap.get(Long.valueOf(dataHolder2.zzb(string, i, zzbh2)))).zza(new PartialDriveId(dataHolder2.zzd(string3, i, zzbh2), dataHolder2.zzb(string2, i, zzbh2), 1));
                }
                dataHolder.zznb().putParcelableArrayList("parentsExtra", arrayList);
            } finally {
                dataHolder2.close();
                dataHolder.zznb().remove("parentsExtraHolder");
            }
        }
    }

    protected /* synthetic */ Object zzc(DataHolder dataHolder, int i, int i2) {
        return zzd(dataHolder, i, i2);
    }

    protected Collection<DriveId> zzd(DataHolder dataHolder, int i, int i2) {
        Bundle zznb = dataHolder.zznb();
        List parcelableArrayList = zznb.getParcelableArrayList("parentsExtra");
        if (parcelableArrayList == null) {
            if (zznb.getParcelable("parentsExtraHolder") != null) {
                zzc(dataHolder);
                parcelableArrayList = zznb.getParcelableArrayList("parentsExtra");
            }
            if (parcelableArrayList == null) {
                return null;
            }
        }
        return ((ParentDriveIdSet) parcelableArrayList.get(i)).zzC(zznb.getLong("dbInstanceId"));
    }

    public void zzd(DataHolder dataHolder) {
        Bundle zznb = dataHolder.zznb();
        if (zznb != null) {
            synchronized (dataHolder) {
                DataHolder dataHolder2 = (DataHolder) zznb.getParcelable("parentsExtraHolder");
                if (dataHolder2 != null) {
                    dataHolder2.close();
                    zznb.remove("parentsExtraHolder");
                }
            }
        }
    }

    protected /* synthetic */ Object zzj(Bundle bundle) {
        return zzo(bundle);
    }

    protected Collection<DriveId> zzo(Bundle bundle) {
        Collection zzo = super.zzo(bundle);
        return zzo == null ? null : new HashSet(zzo);
    }
}
