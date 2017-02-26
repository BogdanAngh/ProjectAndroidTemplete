package com.google.android.gms.drive.query;

import com.google.android.gms.drive.metadata.SortableMetadataField;
import com.google.android.gms.internal.zzlo;
import com.google.android.gms.internal.zzlq;
import java.util.Date;

public class SortableField {
    public static final SortableMetadataField<Date> CREATED_DATE;
    public static final SortableMetadataField<Date> LAST_VIEWED_BY_ME;
    public static final SortableMetadataField<Date> MODIFIED_BY_ME_DATE;
    public static final SortableMetadataField<Date> MODIFIED_DATE;
    public static final SortableMetadataField<Long> QUOTA_USED;
    public static final SortableMetadataField<Date> SHARED_WITH_ME_DATE;
    public static final SortableMetadataField<String> TITLE;

    static {
        TITLE = zzlo.zzahp;
        CREATED_DATE = zzlq.zzahy;
        MODIFIED_DATE = zzlq.zzahA;
        MODIFIED_BY_ME_DATE = zzlq.zzahB;
        LAST_VIEWED_BY_ME = zzlq.zzahz;
        SHARED_WITH_ME_DATE = zzlq.zzahC;
        QUOTA_USED = zzlo.zzahm;
    }
}
