package com.facebook.ads.internal.p000i.p018e.p021c;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaCodec.CryptoException;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import com.facebook.ads.AdSettings;
import com.facebook.ads.internal.util.C0777c;
import com.facebook.ads.internal.util.C0778d;
import com.facebook.internal.NativeProtocol;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.ExoPlaybackException;
import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.ExoPlayer.Factory;
import com.google.android.exoplayer.ExoPlayer.Listener;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.MediaCodecTrackRenderer.DecoderInitializationException;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer;
import com.google.android.exoplayer.MediaCodecVideoTrackRenderer.EventListener;
import com.google.android.exoplayer.TrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.text.Cue;
import com.google.android.exoplayer.upstream.BandwidthMeter;
import com.google.android.exoplayer.util.PlayerControl;
import com.google.android.exoplayer.util.Util;
import com.mp3download.zingmp3.C1569R;
import java.io.IOException;

@TargetApi(14)
/* renamed from: com.facebook.ads.internal.i.e.c.b */
public class C0703b extends TextureView implements SurfaceTextureListener, C0702e, Listener, EventListener, ExtractorSampleSource.EventListener, BandwidthMeter.EventListener {
    private static final String f1060a;
    private Uri f1061b;
    private String f1062c;
    private C0709g f1063d;
    private Handler f1064e;
    private Surface f1065f;
    private TrackRenderer f1066g;
    private TrackRenderer f1067h;
    private C0697c f1068i;
    @Nullable
    private ExoPlayer f1069j;
    @Nullable
    private C0701a f1070k;
    private MediaController f1071l;
    private C0708f f1072m;
    private C0708f f1073n;
    private View f1074o;
    private boolean f1075p;
    private boolean f1076q;
    private boolean f1077r;
    private long f1078s;
    private long f1079t;
    private long f1080u;
    private int f1081v;
    private int f1082w;
    private float f1083x;
    private boolean f1084y;

    /* renamed from: com.facebook.ads.internal.i.e.c.b.b */
    public interface C0695b {
        void m1286a(C0697c c0697c);
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.b.c */
    public interface C0697c {
        void m1289a(MediaCodecVideoTrackRenderer mediaCodecVideoTrackRenderer, MediaCodecAudioTrackRenderer mediaCodecAudioTrackRenderer);

        void m1290a(Exception exception);
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.b.1 */
    class C06981 implements C0697c {
        final /* synthetic */ C0703b f1056a;

        C06981(C0703b c0703b) {
            this.f1056a = c0703b;
        }

        public void m1291a(MediaCodecVideoTrackRenderer mediaCodecVideoTrackRenderer, MediaCodecAudioTrackRenderer mediaCodecAudioTrackRenderer) {
            this.f1056a.f1066g = mediaCodecVideoTrackRenderer;
            this.f1056a.f1067h = mediaCodecAudioTrackRenderer;
            this.f1056a.f1069j = Factory.newInstance(2);
            this.f1056a.f1069j.addListener(this.f1056a);
            this.f1056a.f1070k = new C0701a(this.f1056a);
            this.f1056a.f1069j.prepare(this.f1056a.f1066g, this.f1056a.f1067h);
            if (this.f1056a.f1077r) {
                this.f1056a.f1071l = new MediaController(this.f1056a.getContext());
                this.f1056a.f1071l.setAnchorView(this.f1056a.f1074o == null ? this.f1056a : this.f1056a.f1074o);
                this.f1056a.f1071l.setMediaPlayer(this.f1056a.f1070k);
                this.f1056a.f1071l.setEnabled(true);
            }
            this.f1056a.f1069j.sendMessage(this.f1056a.f1067h, 1, Float.valueOf(this.f1056a.f1083x));
            if (this.f1056a.isAvailable()) {
                this.f1056a.setPlayerSurfaceTexture(this.f1056a.getSurfaceTexture());
            }
        }

        public void m1292a(Exception exception) {
            if (this.f1056a.f1062c != null && this.f1056a.f1062c.length() > 0) {
                this.f1056a.f1062c = null;
                this.f1056a.setup(this.f1056a.f1061b);
            }
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.b.2 */
    class C06992 implements OnTouchListener {
        final /* synthetic */ C0703b f1057a;

        C06992(C0703b c0703b) {
            this.f1057a = c0703b;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(this.f1057a.f1084y || this.f1057a.f1071l == null || motionEvent.getAction() != 1)) {
                if (this.f1057a.f1071l.isShowing()) {
                    this.f1057a.f1071l.hide();
                } else {
                    this.f1057a.f1071l.show();
                }
            }
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.b.3 */
    class C07003 implements OnTouchListener {
        final /* synthetic */ C0703b f1058a;

        C07003(C0703b c0703b) {
            this.f1058a = c0703b;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(this.f1058a.f1084y || this.f1058a.f1071l == null || motionEvent.getAction() != 1)) {
                if (this.f1058a.f1071l.isShowing()) {
                    this.f1058a.f1071l.hide();
                } else {
                    this.f1058a.f1071l.show();
                }
            }
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.b.a */
    private class C0701a extends PlayerControl {
        final /* synthetic */ C0703b f1059a;

        public C0701a(C0703b c0703b) {
            this.f1059a = c0703b;
            super(c0703b.f1069j);
        }

        public void pause() {
            super.pause();
            this.f1059a.f1073n = C0708f.PAUSED;
        }

        public void seekTo(int i) {
            super.seekTo(i);
            this.f1059a.f1080u = (long) i;
        }

        public void start() {
            super.start();
            this.f1059a.f1073n = C0708f.STARTED;
        }
    }

    static {
        f1060a = C0703b.class.getSimpleName();
    }

    public C0703b(Context context) {
        super(context);
        this.f1072m = C0708f.IDLE;
        this.f1073n = C0708f.IDLE;
        this.f1075p = false;
        this.f1076q = false;
        this.f1077r = false;
        this.f1083x = 1.0f;
        this.f1084y = false;
        this.f1064e = new Handler();
        this.f1068i = new C06981(this);
    }

    private void m1308c() {
        C0695b c0704c = (this.f1062c == null || this.f1062c.length() <= 0 || AdSettings.isTestMode(getContext())) ? new C0704c(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST, AccessibilityNodeInfoCompat.ACTION_NEXT_AT_MOVEMENT_GRANULARITY, this.f1064e, this.f1061b, Util.getUserAgent(getContext(), "ads"), this) : new C0696a(NativeProtocol.MESSAGE_GET_ACCESS_TOKEN_REQUEST, this.f1064e, this.f1061b, this.f1062c, Util.getUserAgent(getContext(), "ads"), this);
        c0704c.m1286a(this.f1068i);
    }

    private void m1309d() {
        if (this.f1065f != null) {
            this.f1065f.release();
            this.f1065f = null;
        }
        if (this.f1069j != null) {
            this.f1069j.release();
            this.f1069j = null;
        }
        this.f1071l = null;
        this.f1076q = false;
        setVideoState(C0708f.IDLE);
    }

    private void setPlayerSurfaceTexture(SurfaceTexture surfaceTexture) {
        if (this.f1065f != null) {
            this.f1065f.release();
        }
        this.f1065f = new Surface(surfaceTexture);
        if (this.f1069j != null) {
            this.f1069j.sendMessage(this.f1066g, 1, this.f1065f);
        }
    }

    private void setVideoState(C0708f c0708f) {
        if (c0708f != this.f1072m) {
            this.f1072m = c0708f;
            if (this.f1072m == C0708f.STARTED) {
                this.f1076q = true;
            }
            if (this.f1063d != null) {
                this.f1063d.m1329a(c0708f);
            }
        }
    }

    public void m1318a() {
        setVideoState(C0708f.PLAYBACK_COMPLETED);
        if (this.f1069j != null) {
            this.f1069j.setPlayWhenReady(false);
            this.f1080u = 0;
            m1309d();
        }
    }

    public void m1319a(boolean z) {
        if (this.f1071l != null) {
            this.f1071l.setVisibility(8);
        }
        this.f1084y = true;
    }

    public void m1320b() {
        setVideoState(C0708f.IDLE);
        if (this.f1069j != null) {
            this.f1069j.setPlayWhenReady(false);
            this.f1080u = this.f1069j.getCurrentPosition();
            this.f1069j.removeListener(this);
            m1309d();
        }
    }

    public int getCurrentPosition() {
        return this.f1070k != null ? this.f1070k.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return this.f1069j == null ? 0 : (int) this.f1069j.getDuration();
    }

    public long getInitialBufferTime() {
        return this.f1079t;
    }

    public C0708f getState() {
        return this.f1072m;
    }

    public C0708f getTargetState() {
        return this.f1073n;
    }

    public View getView() {
        return this;
    }

    public float getVolume() {
        return this.f1083x;
    }

    public void onBandwidthSample(int i, long j, long j2) {
    }

    public void onCryptoError(CryptoException cryptoException) {
        setVideoState(C0708f.ERROR);
        cryptoException.printStackTrace();
        C0778d.m1599a(C0777c.m1596a(cryptoException, "[ExoPlayer] Error during decoder operation"));
    }

    public void onDecoderInitializationError(DecoderInitializationException decoderInitializationException) {
        setVideoState(C0708f.ERROR);
        decoderInitializationException.printStackTrace();
        C0778d.m1599a(C0777c.m1596a(decoderInitializationException, "[ExoPlayer] Error while instantiating the decoder for mime type " + decoderInitializationException.mimeType));
    }

    public void onDecoderInitialized(String str, long j, long j2) {
    }

    public void onDrawnToSurface(Surface surface) {
    }

    public void onDroppedFrames(int i, long j) {
    }

    public void onLoadError(int i, IOException iOException) {
        setVideoState(C0708f.ERROR);
        iOException.printStackTrace();
        C0778d.m1599a(C0777c.m1596a(iOException, "[ExoPlayer] Error loading media data from sourceID " + i));
    }

    protected void onMeasure(int i, int i2) {
        int defaultSize = C0703b.getDefaultSize(this.f1081v, i);
        int defaultSize2 = C0703b.getDefaultSize(this.f1082w, i2);
        if (this.f1081v > 0 && this.f1082w > 0) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            defaultSize2 = MeasureSpec.getSize(i2);
            if (mode == C0989C.ENCODING_PCM_32BIT && mode2 == C0989C.ENCODING_PCM_32BIT) {
                if (this.f1081v * defaultSize2 < this.f1082w * size) {
                    defaultSize = (this.f1081v * defaultSize2) / this.f1082w;
                } else if (this.f1081v * defaultSize2 > this.f1082w * size) {
                    defaultSize2 = (this.f1082w * size) / this.f1081v;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.f1082w * size) / this.f1081v;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode2 == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.f1081v * defaultSize2) / this.f1082w;
                if (mode == Cue.TYPE_UNSET && defaultSize > size) {
                    defaultSize = size;
                }
            } else {
                int i3 = this.f1081v;
                defaultSize = this.f1082w;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = i3;
                } else {
                    defaultSize = (this.f1081v * defaultSize2) / this.f1082w;
                }
                if (mode == Cue.TYPE_UNSET && r1 > size) {
                    defaultSize2 = (this.f1082w * size) / this.f1081v;
                    defaultSize = size;
                }
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    public void onPlayWhenReadyCommitted() {
    }

    public void onPlayerError(ExoPlaybackException exoPlaybackException) {
        setVideoState(C0708f.ERROR);
        exoPlaybackException.printStackTrace();
        C0778d.m1599a(C0777c.m1596a(exoPlaybackException, "[ExoPlayer] Error during playback of ExoPlayer"));
    }

    public void onPlayerStateChanged(boolean z, int i) {
        switch (i) {
            case C1569R.styleable.com_facebook_profile_picture_view_com_facebook_is_cropped /*1*/:
                setVideoState(C0708f.IDLE);
            case C1569R.styleable.com_facebook_login_view_com_facebook_logout_text /*2*/:
                this.f1078s = System.currentTimeMillis();
                setVideoState(C0708f.PREPARING);
            case C1569R.styleable.com_facebook_login_view_com_facebook_tooltip_mode /*3*/:
                setVideoState(C0708f.BUFFERING);
            case C1569R.styleable.com_facebook_like_view_com_facebook_auxiliary_view_position /*4*/:
                if (this.f1078s != 0) {
                    this.f1079t = System.currentTimeMillis() - this.f1078s;
                }
                setRequestedVolume(this.f1083x);
                if (this.f1080u > 0 && this.f1080u < this.f1069j.getDuration()) {
                    this.f1069j.seekTo(this.f1080u);
                    this.f1080u = 0;
                }
                if (this.f1069j.getCurrentPosition() == 0 || z || !this.f1076q) {
                    setVideoState(z ? C0708f.STARTED : C0708f.PREPARED);
                } else {
                    setVideoState(C0708f.PAUSED);
                }
            case C1569R.styleable.com_facebook_like_view_com_facebook_horizontal_alignment /*5*/:
                setVideoState(C0708f.PLAYBACK_COMPLETED);
                if (this.f1070k != null) {
                    this.f1070k.seekTo(0);
                }
                if (this.f1069j != null) {
                    this.f1069j.setPlayWhenReady(false);
                }
                this.f1076q = false;
            default:
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f1075p && this.f1069j == null) {
            setup(this.f1061b);
            this.f1075p = false;
        }
        setPlayerSurfaceTexture(surfaceTexture);
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        this.f1073n = this.f1072m;
        this.f1075p = true;
        boolean z = this.f1076q;
        m1320b();
        this.f1076q = z;
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void onVideoSizeChanged(int i, int i2, int i3, float f) {
        this.f1081v = i;
        this.f1082w = i2;
        requestLayout();
    }

    public void pause() {
        if (this.f1070k != null) {
            this.f1070k.pause();
        }
    }

    public void seekTo(int i) {
        if (this.f1070k != null) {
            this.f1070k.seekTo(i);
        } else {
            this.f1080u = (long) i;
        }
    }

    public void setControlsAnchorView(View view) {
        this.f1074o = view;
        view.setOnTouchListener(new C07003(this));
    }

    public void setFullScreen(boolean z) {
        this.f1077r = z;
        if (z) {
            setOnTouchListener(new C06992(this));
        }
    }

    public void setRequestedVolume(float f) {
        this.f1083x = f;
        if (this.f1069j != null && this.f1072m != C0708f.PREPARING && this.f1072m != C0708f.IDLE) {
            this.f1069j.sendMessage(this.f1067h, 1, Float.valueOf(f));
        }
    }

    public void setVideoMPD(String str) {
        this.f1062c = str;
    }

    public void setVideoStateChangeListener(C0709g c0709g) {
        this.f1063d = c0709g;
    }

    public void setup(Uri uri) {
        if (!(this.f1069j == null || this.f1072m == C0708f.PLAYBACK_COMPLETED)) {
            m1309d();
        }
        this.f1061b = uri;
        setSurfaceTextureListener(this);
        m1308c();
    }

    public void start() {
        if (this.f1070k == null) {
            setup(this.f1061b);
            this.f1073n = C0708f.STARTED;
            return;
        }
        this.f1070k.start();
    }
}
