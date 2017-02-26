package com.facebook.ads.internal.p000i.p018e.p021c;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnSeekCompleteListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.widget.MediaController;
import android.widget.MediaController.MediaPlayerControl;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.text.Cue;
import java.io.IOException;

@TargetApi(14)
/* renamed from: com.facebook.ads.internal.i.e.c.d */
public class C0707d extends TextureView implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnSeekCompleteListener, OnVideoSizeChangedListener, SurfaceTextureListener, MediaPlayerControl, C0702e {
    private static final String f1093o;
    private Uri f1094a;
    private C0709g f1095b;
    private Surface f1096c;
    @Nullable
    private MediaPlayer f1097d;
    private MediaController f1098e;
    private C0708f f1099f;
    private C0708f f1100g;
    private View f1101h;
    private int f1102i;
    private long f1103j;
    private int f1104k;
    private int f1105l;
    private float f1106m;
    private boolean f1107n;
    private int f1108p;
    private boolean f1109q;

    /* renamed from: com.facebook.ads.internal.i.e.c.d.1 */
    class C07051 implements OnTouchListener {
        final /* synthetic */ C0707d f1091a;

        C07051(C0707d c0707d) {
            this.f1091a = c0707d;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(this.f1091a.f1109q || this.f1091a.f1098e == null || motionEvent.getAction() != 1)) {
                if (this.f1091a.f1098e.isShowing()) {
                    this.f1091a.f1098e.hide();
                } else {
                    this.f1091a.f1098e.show();
                }
            }
            return true;
        }
    }

    /* renamed from: com.facebook.ads.internal.i.e.c.d.2 */
    class C07062 implements OnTouchListener {
        final /* synthetic */ C0707d f1092a;

        C07062(C0707d c0707d) {
            this.f1092a = c0707d;
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (!(this.f1092a.f1109q || this.f1092a.f1098e == null || motionEvent.getAction() != 1)) {
                if (this.f1092a.f1098e.isShowing()) {
                    this.f1092a.f1098e.hide();
                } else {
                    this.f1092a.f1098e.show();
                }
            }
            return true;
        }
    }

    static {
        f1093o = C0707d.class.getSimpleName();
    }

    public C0707d(Context context) {
        super(context);
        this.f1099f = C0708f.IDLE;
        this.f1100g = C0708f.IDLE;
        this.f1102i = 0;
        this.f1104k = 0;
        this.f1105l = 0;
        this.f1106m = 1.0f;
        this.f1107n = false;
        this.f1108p = 0;
        this.f1109q = false;
    }

    private boolean m1324c() {
        return this.f1099f == C0708f.PREPARED || this.f1099f == C0708f.STARTED || this.f1099f == C0708f.PAUSED || this.f1099f == C0708f.PLAYBACK_COMPLETED;
    }

    private void setVideoState(C0708f c0708f) {
        if (c0708f != this.f1099f) {
            this.f1099f = c0708f;
            if (this.f1095b != null) {
                this.f1095b.m1329a(c0708f);
            }
        }
    }

    public void m1325a() {
        setVideoState(C0708f.PLAYBACK_COMPLETED);
        m1327b();
        this.f1102i = 0;
    }

    public void m1326a(boolean z) {
        if (this.f1098e != null) {
            this.f1098e.setVisibility(8);
        }
        this.f1109q = true;
    }

    public void m1327b() {
        this.f1100g = C0708f.IDLE;
        if (this.f1097d != null) {
            int currentPosition = this.f1097d.getCurrentPosition();
            if (currentPosition > 0) {
                this.f1102i = currentPosition;
            }
            this.f1097d.stop();
            this.f1097d.reset();
            this.f1097d.release();
            this.f1097d = null;
            if (this.f1098e != null) {
                this.f1098e.hide();
                this.f1098e.setEnabled(false);
            }
        }
        setVideoState(C0708f.IDLE);
    }

    public boolean canPause() {
        return this.f1099f != C0708f.PREPARING;
    }

    public boolean canSeekBackward() {
        return true;
    }

    public boolean canSeekForward() {
        return true;
    }

    public int getAudioSessionId() {
        return this.f1097d != null ? this.f1097d.getAudioSessionId() : 0;
    }

    public int getBufferPercentage() {
        return 0;
    }

    public int getCurrentPosition() {
        return this.f1097d != null ? this.f1097d.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return this.f1097d == null ? 0 : this.f1097d.getDuration();
    }

    public long getInitialBufferTime() {
        return this.f1103j;
    }

    public C0708f getState() {
        return this.f1099f;
    }

    public C0708f getTargetState() {
        return this.f1100g;
    }

    public View getView() {
        return this;
    }

    public float getVolume() {
        return this.f1106m;
    }

    public boolean isPlaying() {
        return this.f1097d != null && this.f1097d.isPlaying();
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        if (this.f1097d != null) {
            this.f1097d.pause();
        }
        setVideoState(C0708f.PLAYBACK_COMPLETED);
        seekTo(0);
        this.f1102i = 0;
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        setVideoState(C0708f.ERROR);
        m1327b();
        return true;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        switch (i) {
            case 701:
                setVideoState(C0708f.BUFFERING);
                break;
            case 702:
                setVideoState(C0708f.STARTED);
                break;
        }
        return false;
    }

    protected void onMeasure(int i, int i2) {
        int defaultSize = C0707d.getDefaultSize(this.f1104k, i);
        int defaultSize2 = C0707d.getDefaultSize(this.f1105l, i2);
        if (this.f1104k > 0 && this.f1105l > 0) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            defaultSize2 = MeasureSpec.getSize(i2);
            if (mode == C0989C.ENCODING_PCM_32BIT && mode2 == C0989C.ENCODING_PCM_32BIT) {
                if (this.f1104k * defaultSize2 < this.f1105l * size) {
                    defaultSize = (this.f1104k * defaultSize2) / this.f1105l;
                } else if (this.f1104k * defaultSize2 > this.f1105l * size) {
                    defaultSize2 = (this.f1105l * size) / this.f1104k;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.f1105l * size) / this.f1104k;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode2 == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.f1104k * defaultSize2) / this.f1105l;
                if (mode == Cue.TYPE_UNSET && defaultSize > size) {
                    defaultSize = size;
                }
            } else {
                int i3 = this.f1104k;
                defaultSize = this.f1105l;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = i3;
                } else {
                    defaultSize = (this.f1104k * defaultSize2) / this.f1105l;
                }
                if (mode == Cue.TYPE_UNSET && r1 > size) {
                    defaultSize2 = (this.f1105l * size) / this.f1104k;
                    defaultSize = size;
                }
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        setVideoState(C0708f.PREPARED);
        if (this.f1107n) {
            this.f1098e = new MediaController(getContext());
            this.f1098e.setAnchorView(this.f1101h == null ? this : this.f1101h);
            this.f1098e.setMediaPlayer(this);
            this.f1098e.setEnabled(true);
        }
        setRequestedVolume(this.f1106m);
        this.f1104k = mediaPlayer.getVideoWidth();
        this.f1105l = mediaPlayer.getVideoHeight();
        if (this.f1102i > 0) {
            if (this.f1102i >= this.f1097d.getDuration()) {
                this.f1102i = 0;
            }
            this.f1097d.seekTo(this.f1102i);
            this.f1102i = 0;
        }
        if (this.f1100g == C0708f.STARTED) {
            start();
        }
    }

    public void onSeekComplete(MediaPlayer mediaPlayer) {
        if (this.f1095b != null) {
            this.f1095b.m1328a(this.f1108p, this.f1102i);
            this.f1102i = 0;
        }
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        if (this.f1096c == null) {
            this.f1096c = new Surface(surfaceTexture);
        }
        if (this.f1097d != null) {
            this.f1097d.setSurface(this.f1096c);
            if (this.f1099f == C0708f.PAUSED && this.f1100g != C0708f.PAUSED) {
                start();
            }
        }
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        if (this.f1096c != null) {
            this.f1096c.release();
            this.f1096c = null;
        }
        this.f1100g = this.f1107n ? C0708f.STARTED : this.f1099f;
        pause();
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        this.f1104k = mediaPlayer.getVideoWidth();
        this.f1105l = mediaPlayer.getVideoHeight();
        if (this.f1104k != 0 && this.f1105l != 0) {
            requestLayout();
        }
    }

    public void pause() {
        if (this.f1097d == null) {
            setVideoState(C0708f.IDLE);
        } else if (canPause()) {
            this.f1097d.pause();
            setVideoState(C0708f.PAUSED);
        }
    }

    public void seekTo(int i) {
        if (this.f1097d == null || !m1324c()) {
            this.f1102i = i;
        } else if (i < getDuration() && i > 0) {
            this.f1108p = getCurrentPosition();
            this.f1102i = i;
            this.f1097d.seekTo(i);
        }
    }

    public void setControlsAnchorView(View view) {
        this.f1101h = view;
        view.setOnTouchListener(new C07062(this));
    }

    public void setFullScreen(boolean z) {
        this.f1107n = z;
        if (this.f1107n) {
            setOnTouchListener(new C07051(this));
        }
    }

    public void setRequestedVolume(float f) {
        this.f1106m = f;
        if (this.f1097d != null && this.f1099f != C0708f.PREPARING && this.f1099f != C0708f.IDLE) {
            this.f1097d.setVolume(f, f);
        }
    }

    public void setVideoMPD(String str) {
    }

    public void setVideoStateChangeListener(C0709g c0709g) {
        this.f1095b = c0709g;
    }

    public void setup(Uri uri) {
        MediaPlayer mediaPlayer;
        Object e;
        Throwable th;
        AssetFileDescriptor assetFileDescriptor = null;
        this.f1094a = uri;
        if (this.f1097d != null) {
            this.f1097d.reset();
            this.f1097d.setSurface(null);
            mediaPlayer = this.f1097d;
        } else {
            mediaPlayer = new MediaPlayer();
        }
        try {
            if (uri.getScheme().equals("asset")) {
                try {
                    AssetFileDescriptor openFd = getContext().getAssets().openFd(uri.getPath().substring(1));
                    try {
                        mediaPlayer.setDataSource(openFd.getFileDescriptor(), openFd.getStartOffset(), openFd.getLength());
                        if (openFd != null) {
                            try {
                                openFd.close();
                            } catch (IOException e2) {
                                Log.w(f1093o, "Unable to close" + e2);
                            }
                        }
                    } catch (SecurityException e3) {
                        e = e3;
                        assetFileDescriptor = openFd;
                        try {
                            Log.w(f1093o, "Failed to open assets " + e);
                            setVideoState(C0708f.ERROR);
                            if (assetFileDescriptor != null) {
                                try {
                                    assetFileDescriptor.close();
                                } catch (IOException e22) {
                                    Log.w(f1093o, "Unable to close" + e22);
                                }
                            }
                            mediaPlayer.setLooping(false);
                            mediaPlayer.setOnBufferingUpdateListener(this);
                            mediaPlayer.setOnCompletionListener(this);
                            mediaPlayer.setOnErrorListener(this);
                            mediaPlayer.setOnInfoListener(this);
                            mediaPlayer.setOnPreparedListener(this);
                            mediaPlayer.setOnVideoSizeChangedListener(this);
                            mediaPlayer.setOnSeekCompleteListener(this);
                            mediaPlayer.prepareAsync();
                            this.f1097d = mediaPlayer;
                            setVideoState(C0708f.PREPARING);
                            setSurfaceTextureListener(this);
                            if (isAvailable()) {
                                onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            if (assetFileDescriptor != null) {
                                try {
                                    assetFileDescriptor.close();
                                } catch (IOException e4) {
                                    Log.w(f1093o, "Unable to close" + e4);
                                }
                            }
                            throw th;
                        }
                    } catch (IOException e5) {
                        e = e5;
                        assetFileDescriptor = openFd;
                        Log.w(f1093o, "Failed to open assets " + e);
                        setVideoState(C0708f.ERROR);
                        if (assetFileDescriptor != null) {
                            assetFileDescriptor.close();
                        }
                        mediaPlayer.setLooping(false);
                        mediaPlayer.setOnBufferingUpdateListener(this);
                        mediaPlayer.setOnCompletionListener(this);
                        mediaPlayer.setOnErrorListener(this);
                        mediaPlayer.setOnInfoListener(this);
                        mediaPlayer.setOnPreparedListener(this);
                        mediaPlayer.setOnVideoSizeChangedListener(this);
                        mediaPlayer.setOnSeekCompleteListener(this);
                        mediaPlayer.prepareAsync();
                        this.f1097d = mediaPlayer;
                        setVideoState(C0708f.PREPARING);
                        setSurfaceTextureListener(this);
                        if (isAvailable()) {
                            onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
                        }
                    } catch (Throwable th3) {
                        th = th3;
                        assetFileDescriptor = openFd;
                        if (assetFileDescriptor != null) {
                            assetFileDescriptor.close();
                        }
                        throw th;
                    }
                } catch (SecurityException e6) {
                    e = e6;
                    Log.w(f1093o, "Failed to open assets " + e);
                    setVideoState(C0708f.ERROR);
                    if (assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                    mediaPlayer.setLooping(false);
                    mediaPlayer.setOnBufferingUpdateListener(this);
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.setOnErrorListener(this);
                    mediaPlayer.setOnInfoListener(this);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.setOnVideoSizeChangedListener(this);
                    mediaPlayer.setOnSeekCompleteListener(this);
                    mediaPlayer.prepareAsync();
                    this.f1097d = mediaPlayer;
                    setVideoState(C0708f.PREPARING);
                    setSurfaceTextureListener(this);
                    if (isAvailable()) {
                        onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
                    }
                } catch (IOException e7) {
                    e = e7;
                    Log.w(f1093o, "Failed to open assets " + e);
                    setVideoState(C0708f.ERROR);
                    if (assetFileDescriptor != null) {
                        assetFileDescriptor.close();
                    }
                    mediaPlayer.setLooping(false);
                    mediaPlayer.setOnBufferingUpdateListener(this);
                    mediaPlayer.setOnCompletionListener(this);
                    mediaPlayer.setOnErrorListener(this);
                    mediaPlayer.setOnInfoListener(this);
                    mediaPlayer.setOnPreparedListener(this);
                    mediaPlayer.setOnVideoSizeChangedListener(this);
                    mediaPlayer.setOnSeekCompleteListener(this);
                    mediaPlayer.prepareAsync();
                    this.f1097d = mediaPlayer;
                    setVideoState(C0708f.PREPARING);
                    setSurfaceTextureListener(this);
                    if (isAvailable()) {
                        onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
                    }
                }
            }
            mediaPlayer.setDataSource(getContext(), uri);
            mediaPlayer.setLooping(false);
            mediaPlayer.setOnBufferingUpdateListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnInfoListener(this);
            mediaPlayer.setOnPreparedListener(this);
            mediaPlayer.setOnVideoSizeChangedListener(this);
            mediaPlayer.setOnSeekCompleteListener(this);
            mediaPlayer.prepareAsync();
            this.f1097d = mediaPlayer;
            setVideoState(C0708f.PREPARING);
        } catch (Exception e8) {
            setVideoState(C0708f.ERROR);
            mediaPlayer.release();
            Log.e(f1093o, "Cannot prepare media player with SurfaceTexture: " + e8);
        }
        setSurfaceTextureListener(this);
        if (isAvailable()) {
            onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
        }
    }

    public void start() {
        this.f1100g = C0708f.STARTED;
        if (this.f1099f == C0708f.STARTED || this.f1099f == C0708f.PREPARED || this.f1099f == C0708f.PAUSED || this.f1099f == C0708f.PLAYBACK_COMPLETED) {
            if (this.f1097d == null) {
                setup(this.f1094a);
            } else {
                if (this.f1102i > 0) {
                    this.f1097d.seekTo(this.f1102i);
                }
                this.f1097d.start();
                setVideoState(C0708f.STARTED);
            }
        }
        if (isAvailable()) {
            onSurfaceTextureAvailable(getSurfaceTexture(), 0, 0);
        }
    }
}
