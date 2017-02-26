package com.facebook.ads.internal.p000i.p018e.p021c;

import android.net.Uri;
import android.os.Handler;
import com.facebook.ads.internal.p000i.p018e.p021c.C0703b.C0695b;
import com.facebook.ads.internal.p000i.p018e.p021c.C0703b.C0697c;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.SampleSource;
import com.google.android.exoplayer.extractor.Extractor;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;

/* renamed from: com.facebook.ads.internal.i.e.c.c */
public class C0704c implements C0695b {
    private Handler f1085a;
    private C0703b f1086b;
    private Uri f1087c;
    private String f1088d;
    private int f1089e;
    private int f1090f;

    public C0704c(int i, int i2, Handler handler, Uri uri, String str, C0703b c0703b) {
        this.f1089e = i;
        this.f1090f = i2;
        this.f1085a = handler;
        this.f1087c = uri;
        this.f1088d = str;
        this.f1086b = c0703b;
    }

    public void m1321a(C0697c c0697c) {
        SampleSource extractorSampleSource = new ExtractorSampleSource(this.f1087c, new DefaultUriDataSource(this.f1086b.getContext(), new DefaultBandwidthMeter(this.f1085a, null), this.f1088d), new DefaultAllocator(this.f1089e), this.f1090f * this.f1089e, this.f1085a, this.f1086b, 0, new Extractor[0]);
        c0697c.m1289a(new MediaCodecVideoTrackRenderer(this.f1086b.getContext(), extractorSampleSource, MediaCodecSelector.DEFAULT, 1, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, this.f1085a, this.f1086b, 50), new MediaCodecAudioTrackRenderer(extractorSampleSource, MediaCodecSelector.DEFAULT));
    }
}
