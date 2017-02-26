package com.google.android.gms.ads.internal.overlay;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.SurfaceTexture;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnBufferingUpdateListener;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnInfoListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.MediaPlayer.OnVideoSizeChangedListener;
import android.net.Uri;
import android.os.Build.VERSION;
import android.view.TextureView.SurfaceTextureListener;
import android.view.View.MeasureSpec;
import com.google.android.exoplayer.C0989C;
import com.google.android.exoplayer.text.Cue;
import com.google.android.gms.ads.internal.util.client.zzb;
import com.google.android.gms.ads.internal.zzu;
import com.google.android.gms.internal.zzji;
import com.google.android.gms.internal.zzkx;
import com.google.android.gms.internal.zzlb;
import com.mp3download.zingmp3.BuildConfig;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@zzji
@TargetApi(14)
public class zzc extends zzi implements OnBufferingUpdateListener, OnCompletionListener, OnErrorListener, OnInfoListener, OnPreparedListener, OnVideoSizeChangedListener, SurfaceTextureListener {
    private static final Map<Integer, String> zzbzp;
    private final zzy zzbzq;
    private final boolean zzbzr;
    private int zzbzs;
    private int zzbzt;
    private MediaPlayer zzbzu;
    private Uri zzbzv;
    private int zzbzw;
    private int zzbzx;
    private int zzbzy;
    private int zzbzz;
    private int zzcaa;
    private zzx zzcab;
    private boolean zzcac;
    private int zzcad;
    private zzh zzcae;

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.1 */
    class C10791 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10791(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzqc();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.2 */
    class C10802 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10802(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzqe();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.3 */
    class C10813 implements Runnable {
        final /* synthetic */ zzc zzcaf;
        final /* synthetic */ String zzcag;
        final /* synthetic */ String zzcah;

        C10813(zzc com_google_android_gms_ads_internal_overlay_zzc, String str, String str2) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
            this.zzcag = str;
            this.zzcah = str2;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzk(this.zzcag, this.zzcah);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.4 */
    class C10824 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10824(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzqb();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.5 */
    class C10835 implements Runnable {
        final /* synthetic */ zzc zzcaf;
        final /* synthetic */ int zzcai;
        final /* synthetic */ int zzcaj;

        C10835(zzc com_google_android_gms_ads_internal_overlay_zzc, int i, int i2) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
            this.zzcai = i;
            this.zzcaj = i2;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzf(this.zzcai, this.zzcaj);
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.6 */
    class C10846 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10846(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.onPaused();
                this.zzcaf.zzcae.zzqf();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.7 */
    class C10857 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10857(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.zzqd();
            }
        }
    }

    /* renamed from: com.google.android.gms.ads.internal.overlay.zzc.8 */
    class C10868 implements Runnable {
        final /* synthetic */ zzc zzcaf;

        C10868(zzc com_google_android_gms_ads_internal_overlay_zzc) {
            this.zzcaf = com_google_android_gms_ads_internal_overlay_zzc;
        }

        public void run() {
            if (this.zzcaf.zzcae != null) {
                this.zzcaf.zzcae.onPaused();
            }
        }
    }

    static {
        zzbzp = new HashMap();
        if (VERSION.SDK_INT >= 17) {
            zzbzp.put(Integer.valueOf(-1004), "MEDIA_ERROR_IO");
            zzbzp.put(Integer.valueOf(-1007), "MEDIA_ERROR_MALFORMED");
            zzbzp.put(Integer.valueOf(-1010), "MEDIA_ERROR_UNSUPPORTED");
            zzbzp.put(Integer.valueOf(-110), "MEDIA_ERROR_TIMED_OUT");
            zzbzp.put(Integer.valueOf(3), "MEDIA_INFO_VIDEO_RENDERING_START");
        }
        zzbzp.put(Integer.valueOf(100), "MEDIA_ERROR_SERVER_DIED");
        zzbzp.put(Integer.valueOf(1), "MEDIA_ERROR_UNKNOWN");
        zzbzp.put(Integer.valueOf(1), "MEDIA_INFO_UNKNOWN");
        zzbzp.put(Integer.valueOf(700), "MEDIA_INFO_VIDEO_TRACK_LAGGING");
        zzbzp.put(Integer.valueOf(701), "MEDIA_INFO_BUFFERING_START");
        zzbzp.put(Integer.valueOf(702), "MEDIA_INFO_BUFFERING_END");
        zzbzp.put(Integer.valueOf(800), "MEDIA_INFO_BAD_INTERLEAVING");
        zzbzp.put(Integer.valueOf(801), "MEDIA_INFO_NOT_SEEKABLE");
        zzbzp.put(Integer.valueOf(802), "MEDIA_INFO_METADATA_UPDATE");
        if (VERSION.SDK_INT >= 19) {
            zzbzp.put(Integer.valueOf(901), "MEDIA_INFO_UNSUPPORTED_SUBTITLE");
            zzbzp.put(Integer.valueOf(902), "MEDIA_INFO_SUBTITLE_TIMED_OUT");
        }
    }

    public zzc(Context context, boolean z, boolean z2, zzy com_google_android_gms_ads_internal_overlay_zzy) {
        super(context);
        this.zzbzs = 0;
        this.zzbzt = 0;
        setSurfaceTextureListener(this);
        this.zzbzq = com_google_android_gms_ads_internal_overlay_zzy;
        this.zzcac = z;
        this.zzbzr = z2;
        this.zzbzq.zza((zzi) this);
    }

    private void zza(float f) {
        if (this.zzbzu != null) {
            try {
                this.zzbzu.setVolume(f, f);
                return;
            } catch (IllegalStateException e) {
                return;
            }
        }
        zzb.zzdi("AdMediaPlayerView setMediaPlayerVolume() called before onPrepared().");
    }

    private void zzai(int i) {
        if (i == 3) {
            this.zzbzq.zzre();
            this.zzcbx.zzre();
        } else if (this.zzbzs == 3) {
            this.zzbzq.zzrf();
            this.zzcbx.zzrf();
        }
        this.zzbzs = i;
    }

    private void zzaj(int i) {
        this.zzbzt = i;
    }

    private void zzph() {
        Throwable e;
        String valueOf;
        zzkx.m1697v("AdMediaPlayerView init MediaPlayer");
        SurfaceTexture surfaceTexture = getSurfaceTexture();
        if (this.zzbzv != null && surfaceTexture != null) {
            zzz(false);
            try {
                SurfaceTexture zzqt;
                this.zzbzu = zzu.zzhd().zzqr();
                this.zzbzu.setOnBufferingUpdateListener(this);
                this.zzbzu.setOnCompletionListener(this);
                this.zzbzu.setOnErrorListener(this);
                this.zzbzu.setOnInfoListener(this);
                this.zzbzu.setOnPreparedListener(this);
                this.zzbzu.setOnVideoSizeChangedListener(this);
                this.zzbzy = 0;
                if (this.zzcac) {
                    this.zzcab = new zzx(getContext());
                    this.zzcab.zza(surfaceTexture, getWidth(), getHeight());
                    this.zzcab.start();
                    zzqt = this.zzcab.zzqt();
                    if (zzqt == null) {
                        this.zzcab.zzqs();
                        this.zzcab = null;
                    }
                    this.zzbzu.setDataSource(getContext(), this.zzbzv);
                    this.zzbzu.setSurface(zzu.zzhe().zza(zzqt));
                    this.zzbzu.setAudioStreamType(3);
                    this.zzbzu.setScreenOnWhilePlaying(true);
                    this.zzbzu.prepareAsync();
                    zzai(1);
                }
                zzqt = surfaceTexture;
                this.zzbzu.setDataSource(getContext(), this.zzbzv);
                this.zzbzu.setSurface(zzu.zzhe().zza(zzqt));
                this.zzbzu.setAudioStreamType(3);
                this.zzbzu.setScreenOnWhilePlaying(true);
                this.zzbzu.prepareAsync();
                zzai(1);
            } catch (IOException e2) {
                e = e2;
                valueOf = String.valueOf(this.zzbzv);
                zzb.zzc(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed to initialize MediaPlayer at ").append(valueOf).toString(), e);
                onError(this.zzbzu, 1, 0);
            } catch (IllegalArgumentException e3) {
                e = e3;
                valueOf = String.valueOf(this.zzbzv);
                zzb.zzc(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed to initialize MediaPlayer at ").append(valueOf).toString(), e);
                onError(this.zzbzu, 1, 0);
            } catch (IllegalStateException e4) {
                e = e4;
                valueOf = String.valueOf(this.zzbzv);
                zzb.zzc(new StringBuilder(String.valueOf(valueOf).length() + 36).append("Failed to initialize MediaPlayer at ").append(valueOf).toString(), e);
                onError(this.zzbzu, 1, 0);
            }
        }
    }

    private void zzpi() {
        if (this.zzbzr && zzpj() && this.zzbzu.getCurrentPosition() > 0 && this.zzbzt != 3) {
            zzkx.m1697v("AdMediaPlayerView nudging MediaPlayer");
            zza(0.0f);
            this.zzbzu.start();
            int currentPosition = this.zzbzu.getCurrentPosition();
            long currentTimeMillis = zzu.zzgs().currentTimeMillis();
            while (zzpj() && this.zzbzu.getCurrentPosition() == currentPosition) {
                if (zzu.zzgs().currentTimeMillis() - currentTimeMillis > 250) {
                    break;
                }
            }
            this.zzbzu.pause();
            zzpk();
        }
    }

    private boolean zzpj() {
        return (this.zzbzu == null || this.zzbzs == -1 || this.zzbzs == 0 || this.zzbzs == 1) ? false : true;
    }

    private void zzz(boolean z) {
        zzkx.m1697v("AdMediaPlayerView release");
        if (this.zzcab != null) {
            this.zzcab.zzqs();
            this.zzcab = null;
        }
        if (this.zzbzu != null) {
            this.zzbzu.reset();
            this.zzbzu.release();
            this.zzbzu = null;
            zzai(0);
            if (z) {
                this.zzbzt = 0;
                zzaj(0);
            }
        }
    }

    public int getCurrentPosition() {
        return zzpj() ? this.zzbzu.getCurrentPosition() : 0;
    }

    public int getDuration() {
        return zzpj() ? this.zzbzu.getDuration() : -1;
    }

    public int getVideoHeight() {
        return this.zzbzu != null ? this.zzbzu.getVideoHeight() : 0;
    }

    public int getVideoWidth() {
        return this.zzbzu != null ? this.zzbzu.getVideoWidth() : 0;
    }

    public void onBufferingUpdate(MediaPlayer mediaPlayer, int i) {
        this.zzbzy = i;
    }

    public void onCompletion(MediaPlayer mediaPlayer) {
        zzkx.m1697v("AdMediaPlayerView completion");
        zzai(5);
        zzaj(5);
        zzlb.zzcvl.post(new C10802(this));
    }

    public boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
        String str = (String) zzbzp.get(Integer.valueOf(i));
        String str2 = (String) zzbzp.get(Integer.valueOf(i2));
        zzb.zzdi(new StringBuilder((String.valueOf(str).length() + 38) + String.valueOf(str2).length()).append("AdMediaPlayerView MediaPlayer error: ").append(str).append(":").append(str2).toString());
        zzai(-1);
        zzaj(-1);
        zzlb.zzcvl.post(new C10813(this, str, str2));
        return true;
    }

    public boolean onInfo(MediaPlayer mediaPlayer, int i, int i2) {
        String str = (String) zzbzp.get(Integer.valueOf(i));
        String str2 = (String) zzbzp.get(Integer.valueOf(i2));
        zzkx.m1697v(new StringBuilder((String.valueOf(str).length() + 37) + String.valueOf(str2).length()).append("AdMediaPlayerView MediaPlayer info: ").append(str).append(":").append(str2).toString());
        return true;
    }

    protected void onMeasure(int i, int i2) {
        int defaultSize = getDefaultSize(this.zzbzw, i);
        int defaultSize2 = getDefaultSize(this.zzbzx, i2);
        if (this.zzbzw > 0 && this.zzbzx > 0 && this.zzcab == null) {
            int mode = MeasureSpec.getMode(i);
            int size = MeasureSpec.getSize(i);
            int mode2 = MeasureSpec.getMode(i2);
            defaultSize2 = MeasureSpec.getSize(i2);
            if (mode == C0989C.ENCODING_PCM_32BIT && mode2 == C0989C.ENCODING_PCM_32BIT) {
                if (this.zzbzw * defaultSize2 < this.zzbzx * size) {
                    defaultSize = (this.zzbzw * defaultSize2) / this.zzbzx;
                } else if (this.zzbzw * defaultSize2 > this.zzbzx * size) {
                    defaultSize2 = (this.zzbzx * size) / this.zzbzw;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.zzbzx * size) / this.zzbzw;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = size;
                } else {
                    defaultSize = size;
                }
            } else if (mode2 == C0989C.ENCODING_PCM_32BIT) {
                defaultSize = (this.zzbzw * defaultSize2) / this.zzbzx;
                if (mode == Cue.TYPE_UNSET && defaultSize > size) {
                    defaultSize = size;
                }
            } else {
                int i3 = this.zzbzw;
                defaultSize = this.zzbzx;
                if (mode2 != Cue.TYPE_UNSET || defaultSize <= defaultSize2) {
                    defaultSize2 = defaultSize;
                    defaultSize = i3;
                } else {
                    defaultSize = (this.zzbzw * defaultSize2) / this.zzbzx;
                }
                if (mode == Cue.TYPE_UNSET && r1 > size) {
                    defaultSize2 = (this.zzbzx * size) / this.zzbzw;
                    defaultSize = size;
                }
            }
        }
        setMeasuredDimension(defaultSize, defaultSize2);
        if (this.zzcab != null) {
            this.zzcab.zzi(defaultSize, defaultSize2);
        }
        if (VERSION.SDK_INT == 16) {
            if ((this.zzbzz > 0 && this.zzbzz != defaultSize) || (this.zzcaa > 0 && this.zzcaa != defaultSize2)) {
                zzpi();
            }
            this.zzbzz = defaultSize;
            this.zzcaa = defaultSize2;
        }
    }

    public void onPrepared(MediaPlayer mediaPlayer) {
        zzkx.m1697v("AdMediaPlayerView prepared");
        zzai(2);
        this.zzbzq.zzqc();
        zzlb.zzcvl.post(new C10791(this));
        this.zzbzw = mediaPlayer.getVideoWidth();
        this.zzbzx = mediaPlayer.getVideoHeight();
        if (this.zzcad != 0) {
            seekTo(this.zzcad);
        }
        zzpi();
        int i = this.zzbzw;
        zzb.zzdh("AdMediaPlayerView stream dimensions: " + i + " x " + this.zzbzx);
        if (this.zzbzt == 3) {
            play();
        }
        zzpk();
    }

    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i2) {
        zzkx.m1697v("AdMediaPlayerView surface created");
        zzph();
        zzlb.zzcvl.post(new C10824(this));
    }

    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        zzkx.m1697v("AdMediaPlayerView surface destroyed");
        if (this.zzbzu != null && this.zzcad == 0) {
            this.zzcad = this.zzbzu.getCurrentPosition();
        }
        if (this.zzcab != null) {
            this.zzcab.zzqs();
        }
        zzlb.zzcvl.post(new C10846(this));
        zzz(true);
        return true;
    }

    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i2) {
        Object obj = 1;
        zzkx.m1697v("AdMediaPlayerView surface changed");
        Object obj2 = this.zzbzt == 3 ? 1 : null;
        if (!(this.zzbzw == i && this.zzbzx == i2)) {
            obj = null;
        }
        if (!(this.zzbzu == null || obj2 == null || r1 == null)) {
            if (this.zzcad != 0) {
                seekTo(this.zzcad);
            }
            play();
        }
        if (this.zzcab != null) {
            this.zzcab.zzi(i, i2);
        }
        zzlb.zzcvl.post(new C10835(this, i, i2));
    }

    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {
        this.zzbzq.zzb(this);
        this.zzcbw.zza(surfaceTexture, this.zzcae);
    }

    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int i, int i2) {
        zzkx.m1697v("AdMediaPlayerView size changed: " + i + " x " + i2);
        this.zzbzw = mediaPlayer.getVideoWidth();
        this.zzbzx = mediaPlayer.getVideoHeight();
        if (this.zzbzw != 0 && this.zzbzx != 0) {
            requestLayout();
        }
    }

    public void pause() {
        zzkx.m1697v("AdMediaPlayerView pause");
        if (zzpj() && this.zzbzu.isPlaying()) {
            this.zzbzu.pause();
            zzai(4);
            zzlb.zzcvl.post(new C10868(this));
        }
        zzaj(4);
    }

    public void play() {
        zzkx.m1697v("AdMediaPlayerView play");
        if (zzpj()) {
            this.zzbzu.start();
            zzai(3);
            this.zzcbw.zzqd();
            zzlb.zzcvl.post(new C10857(this));
        }
        zzaj(3);
    }

    public void seekTo(int i) {
        zzkx.m1697v("AdMediaPlayerView seek " + i);
        if (zzpj()) {
            this.zzbzu.seekTo(i);
            this.zzcad = 0;
            return;
        }
        this.zzcad = i;
    }

    public void setVideoPath(String str) {
        setVideoURI(Uri.parse(str));
    }

    public void setVideoURI(Uri uri) {
        this.zzbzv = uri;
        this.zzcad = 0;
        zzph();
        requestLayout();
        invalidate();
    }

    public void stop() {
        zzkx.m1697v("AdMediaPlayerView stop");
        if (this.zzbzu != null) {
            this.zzbzu.stop();
            this.zzbzu.release();
            this.zzbzu = null;
            zzai(0);
            zzaj(0);
        }
        this.zzbzq.onStop();
    }

    public String toString() {
        String valueOf = String.valueOf(getClass().getName());
        String valueOf2 = String.valueOf(Integer.toHexString(hashCode()));
        return new StringBuilder((String.valueOf(valueOf).length() + 1) + String.valueOf(valueOf2).length()).append(valueOf).append("@").append(valueOf2).toString();
    }

    public void zza(float f, float f2) {
        if (this.zzcab != null) {
            this.zzcab.zzb(f, f2);
        }
    }

    public void zza(zzh com_google_android_gms_ads_internal_overlay_zzh) {
        this.zzcae = com_google_android_gms_ads_internal_overlay_zzh;
    }

    public String zzpg() {
        String str = "MediaPlayer";
        String valueOf = String.valueOf(this.zzcac ? " spherical" : BuildConfig.FLAVOR);
        return valueOf.length() != 0 ? str.concat(valueOf) : new String(str);
    }

    public void zzpk() {
        zza(this.zzcbx.zzrh());
    }
}
