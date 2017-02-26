package com.google.android.gms.drive.query;

import com.google.android.gms.drive.DriveId;
import com.google.android.gms.drive.metadata.SearchableCollectionMetadataField;
import com.google.android.gms.drive.metadata.SearchableMetadataField;
import com.google.android.gms.drive.metadata.SearchableOrderedMetadataField;
import com.google.android.gms.drive.metadata.internal.AppVisibleCustomProperties;
import com.google.android.gms.internal.zzlo;
import com.google.android.gms.internal.zzlq;
import java.util.Date;

public class SearchableField {
    public static final SearchableMetadataField<Boolean> IS_PINNED;
    public static final SearchableOrderedMetadataField<Date> LAST_VIEWED_BY_ME;
    public static final SearchableMetadataField<String> MIME_TYPE;
    public static final SearchableOrderedMetadataField<Date> MODIFIED_DATE;
    public static final SearchableCollectionMetadataField<DriveId> PARENTS;
    public static final SearchableMetadataField<Boolean> STARRED;
    public static final SearchableMetadataField<String> TITLE;
    public static final SearchableMetadataField<Boolean> TRASHED;
    public static final SearchableOrderedMetadataField<Date> zzahM;
    public static final SearchableMetadataField<AppVisibleCustomProperties> zzahN;

    static {
        TITLE = zzlo.zzahp;
        MIME_TYPE = zzlo.zzahg;
        TRASHED = zzlo.zzahq;
        PARENTS = zzlo.zzahl;
        zzahM = zzlq.zzahC;
        STARRED = zzlo.zzahn;
        MODIFIED_DATE = zzlq.zzahA;
        LAST_VIEWED_BY_ME = zzlq.zzahz;
        IS_PINNED = zzlo.zzagY;
        zzahN = zzlo.zzagM;
    }
}
