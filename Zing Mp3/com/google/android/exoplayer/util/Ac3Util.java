package com.google.android.exoplayer.util;

import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import com.google.android.exoplayer.MediaFormat;
import com.google.android.exoplayer.extractor.ts.PsExtractor;
import com.google.android.gms.ads.AdRequest;
import com.mp3download.zingmp3.C1569R;
import java.nio.ByteBuffer;

public final class Ac3Util {
    private static final int AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT = 1536;
    private static final int AUDIO_SAMPLES_PER_AUDIO_BLOCK = 256;
    private static final int[] BITRATE_BY_HALF_FRMSIZECOD;
    private static final int[] BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD;
    private static final int[] CHANNEL_COUNT_BY_ACMOD;
    private static final int[] SAMPLE_RATE_BY_FSCOD;
    private static final int[] SAMPLE_RATE_BY_FSCOD2;
    private static final int[] SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1;

    static {
        BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD = new int[]{1, 2, 3, 6};
        SAMPLE_RATE_BY_FSCOD = new int[]{48000, 44100, 32000};
        SAMPLE_RATE_BY_FSCOD2 = new int[]{24000, 22050, 16000};
        CHANNEL_COUNT_BY_ACMOD = new int[]{2, 1, 2, 3, 3, 4, 4, 5};
        BITRATE_BY_HALF_FRMSIZECOD = new int[]{32, 40, 48, 56, 64, 80, 96, C1569R.styleable.AppCompatTheme_spinnerStyle, AccessibilityNodeInfoCompat.ACTION_CLEAR_ACCESSIBILITY_FOCUS, 160, PsExtractor.AUDIO_STREAM, PsExtractor.VIDEO_STREAM, AUDIO_SAMPLES_PER_AUDIO_BLOCK, 320, 384, 448, AdRequest.MAX_CONTENT_URL_LENGTH, 576, 640};
        SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1 = new int[]{69, 87, C1569R.styleable.AppCompatTheme_checkboxStyle, 121, 139, 174, 208, 243, 278, 348, 417, 487, 557, 696, 835, 975, 1114, 1253, 1393};
    }

    public static MediaFormat parseAc3AnnexFFormat(ParsableByteArray data, String trackId, long durationUs, String language) {
        int sampleRate = SAMPLE_RATE_BY_FSCOD[(data.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6];
        int nextByte = data.readUnsignedByte();
        int channelCount = CHANNEL_COUNT_BY_ACMOD[(nextByte & 56) >> 3];
        if ((nextByte & 4) != 0) {
            channelCount++;
        }
        return MediaFormat.createAudioFormat(trackId, MimeTypes.AUDIO_AC3, -1, -1, durationUs, channelCount, sampleRate, null, language);
    }

    public static MediaFormat parseEAc3AnnexFFormat(ParsableByteArray data, String trackId, long durationUs, String language) {
        data.skipBytes(2);
        int sampleRate = SAMPLE_RATE_BY_FSCOD[(data.readUnsignedByte() & PsExtractor.AUDIO_STREAM) >> 6];
        int nextByte = data.readUnsignedByte();
        int channelCount = CHANNEL_COUNT_BY_ACMOD[(nextByte & 14) >> 1];
        if ((nextByte & 1) != 0) {
            channelCount++;
        }
        return MediaFormat.createAudioFormat(trackId, MimeTypes.AUDIO_E_AC3, -1, -1, durationUs, channelCount, sampleRate, null, language);
    }

    public static MediaFormat parseAc3SyncframeFormat(ParsableBitArray data, String trackId, long durationUs, String language) {
        data.skipBits(32);
        int fscod = data.readBits(2);
        data.skipBits(14);
        int acmod = data.readBits(3);
        if (!((acmod & 1) == 0 || acmod == 1)) {
            data.skipBits(2);
        }
        if ((acmod & 4) != 0) {
            data.skipBits(2);
        }
        if (acmod == 2) {
            data.skipBits(2);
        }
        return MediaFormat.createAudioFormat(trackId, MimeTypes.AUDIO_AC3, -1, -1, durationUs, CHANNEL_COUNT_BY_ACMOD[acmod] + (data.readBit() ? 1 : 0), SAMPLE_RATE_BY_FSCOD[fscod], null, language);
    }

    public static MediaFormat parseEac3SyncframeFormat(ParsableBitArray data, String trackId, long durationUs, String language) {
        int sampleRate;
        int i;
        data.skipBits(32);
        int fscod = data.readBits(2);
        if (fscod == 3) {
            sampleRate = SAMPLE_RATE_BY_FSCOD2[data.readBits(2)];
        } else {
            data.skipBits(2);
            sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
        }
        int acmod = data.readBits(3);
        boolean lfeon = data.readBit();
        String str = MimeTypes.AUDIO_E_AC3;
        int i2 = CHANNEL_COUNT_BY_ACMOD[acmod];
        if (lfeon) {
            i = 1;
        } else {
            i = 0;
        }
        return MediaFormat.createAudioFormat(trackId, str, -1, -1, durationUs, i2 + i, sampleRate, null, language);
    }

    public static int parseAc3SyncframeSize(byte[] data) {
        return getAc3SyncframeSize((data[4] & PsExtractor.AUDIO_STREAM) >> 6, data[4] & 63);
    }

    public static int parseEAc3SyncframeSize(byte[] data) {
        return ((((data[2] & 7) << 8) + (data[3] & NalUnitUtil.EXTENDED_SAR)) + 1) * 2;
    }

    public static int getAc3SyncframeAudioSampleCount() {
        return AC3_SYNCFRAME_AUDIO_SAMPLE_COUNT;
    }

    public static int parseEAc3SyncframeAudioSampleCount(byte[] data) {
        return (((data[4] & PsExtractor.AUDIO_STREAM) >> 6) == 3 ? 6 : BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(data[4] & 48) >> 4]) * AUDIO_SAMPLES_PER_AUDIO_BLOCK;
    }

    public static int parseEAc3SyncframeAudioSampleCount(ByteBuffer buffer) {
        return (((buffer.get(buffer.position() + 4) & PsExtractor.AUDIO_STREAM) >> 6) == 3 ? 6 : BLOCKS_PER_SYNCFRAME_BY_NUMBLKSCOD[(buffer.get(buffer.position() + 4) & 48) >> 4]) * AUDIO_SAMPLES_PER_AUDIO_BLOCK;
    }

    private static int getAc3SyncframeSize(int fscod, int frmsizecod) {
        int sampleRate = SAMPLE_RATE_BY_FSCOD[fscod];
        if (sampleRate == 44100) {
            return (SYNCFRAME_SIZE_WORDS_BY_HALF_FRMSIZECOD_44_1[frmsizecod / 2] + (frmsizecod % 2)) * 2;
        }
        int bitrate = BITRATE_BY_HALF_FRMSIZECOD[frmsizecod / 2];
        if (sampleRate == 32000) {
            return bitrate * 6;
        }
        return bitrate * 4;
    }

    private Ac3Util() {
    }
}
