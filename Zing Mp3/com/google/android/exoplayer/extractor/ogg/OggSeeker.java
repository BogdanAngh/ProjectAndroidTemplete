package com.google.android.exoplayer.extractor.ogg;

import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ogg.OggUtil.PageHeader;
import com.google.android.exoplayer.util.Assertions;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

final class OggSeeker {
    private static final int MATCH_RANGE = 72000;
    private long audioDataLength;
    private final ParsableByteArray headerArray;
    private final PageHeader pageHeader;
    private long totalSamples;

    OggSeeker() {
        this.pageHeader = new PageHeader();
        this.headerArray = new ParsableByteArray(282);
        this.audioDataLength = -1;
    }

    public void setup(long audioDataLength, long totalSamples) {
        boolean z = audioDataLength > 0 && totalSamples > 0;
        Assertions.checkArgument(z);
        this.audioDataLength = audioDataLength;
        this.totalSamples = totalSamples;
    }

    public long getNextSeekPosition(long targetGranule, ExtractorInput input) throws IOException, InterruptedException {
        boolean z = (this.audioDataLength == -1 || this.totalSamples == 0) ? false : true;
        Assertions.checkState(z);
        OggUtil.populatePageHeader(input, this.pageHeader, this.headerArray, false);
        long granuleDistance = targetGranule - this.pageHeader.granulePosition;
        if (granuleDistance <= 0 || granuleDistance > 72000) {
            return (input.getPosition() - ((long) ((granuleDistance <= 0 ? 2 : 1) * (this.pageHeader.headerSize + this.pageHeader.bodySize)))) + ((this.audioDataLength * granuleDistance) / this.totalSamples);
        }
        input.resetPeekPosition();
        return -1;
    }
}
