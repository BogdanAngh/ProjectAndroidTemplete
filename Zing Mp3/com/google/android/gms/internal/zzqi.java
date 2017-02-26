package com.google.android.gms.internal;

import com.google.android.exoplayer.extractor.ts.PtsTimestampAdjuster;

public class zzqi {
    public static long zzd(long j, long j2) {
        return j >= 0 ? j % j2 : (((PtsTimestampAdjuster.DO_NOT_OFFSET % j2) + 1) + ((j & PtsTimestampAdjuster.DO_NOT_OFFSET) % j2)) % j2;
    }
}
