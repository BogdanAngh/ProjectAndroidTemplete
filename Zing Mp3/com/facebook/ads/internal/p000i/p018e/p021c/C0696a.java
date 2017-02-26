package com.facebook.ads.internal.p000i.p018e.p021c;

import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.helper.ItemTouchHelper.Callback;
import com.facebook.ads.internal.p000i.p018e.p021c.C0703b.C0695b;
import com.facebook.ads.internal.p000i.p018e.p021c.C0703b.C0697c;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.DefaultLoadControl;
import com.google.android.exoplayer.LoadControl;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecSelector;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.chunk.ChunkSampleSource;
import com.google.android.exoplayer.chunk.FormatEvaluator.AdaptiveEvaluator;
import com.google.android.exoplayer.dash.DashChunkSource;
import com.google.android.exoplayer.dash.DefaultDashTrackSelector;
import com.google.android.exoplayer.dash.mpd.AdaptationSet;
import com.google.android.exoplayer.dash.mpd.MediaPresentationDescription;
import com.google.android.exoplayer.dash.mpd.MediaPresentationDescriptionParser;
import com.google.android.exoplayer.hls.HlsChunkSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.upstream.TransferListener;
import com.google.android.exoplayer.util.ManifestFetcher.ManifestCallback;
import java.io.ByteArrayInputStream;
import java.io.IOException;

/* renamed from: com.facebook.ads.internal.i.e.c.a */
public class C0696a implements C0695b, ManifestCallback<MediaPresentationDescription> {
    private C0703b f1049a;
    private C0697c f1050b;
    private Handler f1051c;
    private String f1052d;
    private String f1053e;
    private String f1054f;
    private int f1055g;

    public C0696a(int i, Handler handler, Uri uri, String str, String str2, C0703b c0703b) {
        this.f1055g = i;
        this.f1051c = handler;
        this.f1052d = uri.toString();
        this.f1053e = str;
        this.f1054f = str2;
        this.f1049a = c0703b;
    }

    public void m1287a(C0697c c0697c) {
        this.f1050b = c0697c;
        try {
            m1288a(new MediaPresentationDescriptionParser().parse(this.f1052d, new ByteArrayInputStream(this.f1053e.getBytes(C0989C.UTF8_NAME))));
        } catch (IOException e) {
            onSingleManifestError(e);
        }
    }

    public void m1288a(MediaPresentationDescription mediaPresentationDescription) {
        int i = 0;
        int i2 = 0;
        for (AdaptationSet adaptationSet : mediaPresentationDescription.getPeriod(0).adaptationSets) {
            int i3;
            if (adaptationSet.type == 1) {
                i2 += adaptationSet.representations.size();
                i3 = i;
                i = i2;
            } else if (adaptationSet.type == 0) {
                i3 = i + adaptationSet.representations.size();
                i = i2;
            } else {
                i3 = i;
                i = i2;
            }
            i2 = i;
            i = i3;
        }
        if (i == 0 || i2 == 0) {
            onSingleManifestError(new IOException());
            return;
        }
        LoadControl defaultLoadControl = new DefaultLoadControl(new DefaultAllocator(this.f1055g));
        TransferListener defaultBandwidthMeter = new DefaultBandwidthMeter(this.f1051c, this.f1049a);
        this.f1050b.m1289a(new MediaCodecVideoTrackRenderer(this.f1049a.getContext(), new ChunkSampleSource(new DashChunkSource(mediaPresentationDescription, DefaultDashTrackSelector.newVideoInstance(this.f1049a.getContext(), true, false), new DefaultUriDataSource(this.f1049a.getContext(), defaultBandwidthMeter, this.f1054f), new AdaptiveEvaluator(defaultBandwidthMeter)), defaultLoadControl, this.f1055g * Callback.DEFAULT_DRAG_ANIMATION_DURATION), MediaCodecSelector.DEFAULT, 1, HlsChunkSource.DEFAULT_MIN_BUFFER_TO_SWITCH_UP_MS, this.f1051c, this.f1049a, 50), new MediaCodecAudioTrackRenderer(new ChunkSampleSource(new DashChunkSource(mediaPresentationDescription, DefaultDashTrackSelector.newAudioInstance(), new DefaultUriDataSource(this.f1049a.getContext(), defaultBandwidthMeter, this.f1054f), new AdaptiveEvaluator(defaultBandwidthMeter)), defaultLoadControl, this.f1055g * 54), MediaCodecSelector.DEFAULT));
    }

    public /* synthetic */ void onSingleManifest(Object obj) {
        m1288a((MediaPresentationDescription) obj);
    }

    public void onSingleManifestError(IOException iOException) {
        iOException.printStackTrace();
        this.f1050b.m1290a(iOException);
    }
}
