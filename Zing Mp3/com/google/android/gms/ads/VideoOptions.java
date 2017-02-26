package com.google.android.gms.ads;

import com.google.android.gms.internal.zzji;

@zzji
public final class VideoOptions {
    private final boolean zzakr;

    public static final class Builder {
        private boolean zzakr;

        public Builder() {
            this.zzakr = false;
        }

        public VideoOptions build() {
            return new VideoOptions();
        }

        public Builder setStartMuted(boolean z) {
            this.zzakr = z;
            return this;
        }
    }

    private VideoOptions(Builder builder) {
        this.zzakr = builder.zzakr;
    }

    public boolean getStartMuted() {
        return this.zzakr;
    }
}
