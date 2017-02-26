package com.badlogic.gdx.backends.android;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.IOException;

public class AndroidMusic implements Music, OnCompletionListener {
    private final AndroidAudio audio;
    private boolean isPrepared;
    protected Music.OnCompletionListener onCompletionListener;
    private MediaPlayer player;
    private float volume;
    protected boolean wasPlaying;

    /* renamed from: com.badlogic.gdx.backends.android.AndroidMusic.1 */
    class C00461 implements Runnable {
        C00461() {
        }

        public void run() {
            AndroidMusic.this.onCompletionListener.onCompletion(AndroidMusic.this);
        }
    }

    AndroidMusic(AndroidAudio audio, MediaPlayer player) {
        this.isPrepared = true;
        this.wasPlaying = false;
        this.volume = TextTrackStyle.DEFAULT_FONT_SCALE;
        this.audio = audio;
        this.player = player;
        this.onCompletionListener = null;
        this.player.setOnCompletionListener(this);
    }

    public void dispose() {
        if (this.player != null) {
            try {
                if (this.player.isPlaying()) {
                    this.player.stop();
                }
                this.player.release();
                this.player = null;
                this.onCompletionListener = null;
                synchronized (this.audio.musics) {
                    this.audio.musics.remove(this);
                }
            } catch (Throwable th) {
                this.player = null;
                this.onCompletionListener = null;
                synchronized (this.audio.musics) {
                }
                this.audio.musics.remove(this);
            }
        }
    }

    public boolean isLooping() {
        return this.player.isLooping();
    }

    public boolean isPlaying() {
        return this.player.isPlaying();
    }

    public void pause() {
        if (this.player.isPlaying()) {
            this.player.pause();
        }
    }

    public void play() {
        if (!this.player.isPlaying()) {
            try {
                if (!this.isPrepared) {
                    this.player.prepare();
                    this.isPrepared = true;
                }
                this.player.start();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e2) {
                e2.printStackTrace();
            }
        }
    }

    public void setLooping(boolean isLooping) {
        this.player.setLooping(isLooping);
    }

    public void setVolume(float volume) {
        this.player.setVolume(volume, volume);
        this.volume = volume;
    }

    public float getVolume() {
        return this.volume;
    }

    public void setPan(float pan, float volume) {
        float leftVolume = volume;
        float rightVolume = volume;
        if (pan < 0.0f) {
            rightVolume *= TextTrackStyle.DEFAULT_FONT_SCALE - Math.abs(pan);
        } else if (pan > 0.0f) {
            leftVolume *= TextTrackStyle.DEFAULT_FONT_SCALE - Math.abs(pan);
        }
        this.player.setVolume(leftVolume, rightVolume);
        this.volume = volume;
    }

    public void stop() {
        if (this.isPrepared) {
            this.player.seekTo(0);
        }
        this.player.stop();
        this.isPrepared = false;
    }

    public float getPosition() {
        return ((float) this.player.getCurrentPosition()) / 1000.0f;
    }

    public void setOnCompletionListener(Music.OnCompletionListener listener) {
        this.onCompletionListener = listener;
    }

    public void onCompletion(MediaPlayer mp) {
        if (this.onCompletionListener != null) {
            Gdx.app.postRunnable(new C00461());
        }
    }
}
