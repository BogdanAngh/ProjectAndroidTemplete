package com.google.android.exoplayer.extractor.ogg;

import com.google.android.exoplayer.extractor.ExtractorInput;
import com.google.android.exoplayer.extractor.ExtractorOutput;
import com.google.android.exoplayer.extractor.PositionHolder;
import com.google.android.exoplayer.extractor.TrackOutput;
import com.google.android.exoplayer.util.ParsableByteArray;
import java.io.IOException;

abstract class StreamReader {
    protected ExtractorOutput extractorOutput;
    protected final OggParser oggParser;
    protected final ParsableByteArray scratch;
    protected TrackOutput trackOutput;

    abstract int read(ExtractorInput extractorInput, PositionHolder positionHolder) throws IOException, InterruptedException;

    StreamReader() {
        this.scratch = new ParsableByteArray(new byte[65025], 0);
        this.oggParser = new OggParser();
    }

    void init(ExtractorOutput output, TrackOutput trackOutput) {
        this.extractorOutput = output;
        this.trackOutput = trackOutput;
    }

    void seek() {
        this.oggParser.reset();
        this.scratch.reset();
    }
}
