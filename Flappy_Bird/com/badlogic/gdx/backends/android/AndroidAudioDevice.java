package com.badlogic.gdx.backends.android;

import android.media.AudioTrack;
import com.badlogic.gdx.audio.AudioDevice;
import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.maps.model.GroundOverlayOptions;

class AndroidAudioDevice implements AudioDevice {
    private short[] buffer;
    private final boolean isMono;
    private final int latency;
    private final AudioTrack track;

    AndroidAudioDevice(int samplingRate, boolean isMono) {
        int i;
        int i2;
        int i3 = 1;
        this.buffer = new short[Place.TYPE_SUBLOCALITY_LEVEL_2];
        this.isMono = isMono;
        if (isMono) {
            i = 2;
        } else {
            i = 3;
        }
        int minSize = AudioTrack.getMinBufferSize(samplingRate, i, 2);
        if (isMono) {
            i2 = 2;
        } else {
            i2 = 3;
        }
        this.track = new AudioTrack(3, samplingRate, i2, 2, minSize, 1);
        this.track.play();
        if (!isMono) {
            i3 = 2;
        }
        this.latency = minSize / i3;
    }

    public void dispose() {
        this.track.stop();
        this.track.release();
    }

    public boolean isMono() {
        return this.isMono;
    }

    public void writeSamples(short[] samples, int offset, int numSamples) {
        int writtenSamples = this.track.write(samples, offset, numSamples);
        while (writtenSamples != numSamples) {
            writtenSamples += this.track.write(samples, offset + writtenSamples, numSamples - writtenSamples);
        }
    }

    public void writeSamples(float[] samples, int offset, int numSamples) {
        if (this.buffer.length < samples.length) {
            this.buffer = new short[samples.length];
        }
        int bound = offset + numSamples;
        int i = offset;
        int j = 0;
        while (i < bound) {
            float fValue = samples[i];
            if (fValue > TextTrackStyle.DEFAULT_FONT_SCALE) {
                fValue = TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            if (fValue < GroundOverlayOptions.NO_DIMENSION) {
                fValue = GroundOverlayOptions.NO_DIMENSION;
            }
            this.buffer[j] = (short) ((int) (32767.0f * fValue));
            i++;
            j++;
        }
        int writtenSamples = this.track.write(this.buffer, 0, numSamples);
        while (writtenSamples != numSamples) {
            writtenSamples += this.track.write(this.buffer, writtenSamples, numSamples - writtenSamples);
        }
    }

    public int getLatency() {
        return this.latency;
    }

    public void setVolume(float volume) {
        this.track.setStereoVolume(volume, volume);
    }
}
