package com.badlogic.gdx.math;

import com.google.android.gms.cast.TextTrackStyle;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import java.util.Random;

public class MathUtils {
    private static final int ATAN2_BITS = 7;
    private static final int ATAN2_BITS2 = 14;
    private static final int ATAN2_COUNT = 16384;
    static final int ATAN2_DIM;
    private static final int ATAN2_MASK = 16383;
    private static final double BIG_ENOUGH_CEIL = 16384.999999999996d;
    private static final double BIG_ENOUGH_FLOOR = 16384.0d;
    private static final int BIG_ENOUGH_INT = 16384;
    private static final double BIG_ENOUGH_ROUND = 16384.5d;
    private static final double CEIL = 0.9999999d;
    private static final float INV_ATAN2_DIM_MINUS_1;
    public static final float PI = 3.1415927f;
    public static final float PI2 = 6.2831855f;
    private static final int SIN_BITS = 14;
    private static final int SIN_COUNT = 16384;
    private static final int SIN_MASK = 16383;
    private static final float degFull = 360.0f;
    public static final float degRad = 0.017453292f;
    private static final float degToIndex = 45.511112f;
    public static final float degreesToRadians = 0.017453292f;
    public static final float nanoToSec = 1.0E-9f;
    public static final float radDeg = 57.295776f;
    private static final float radFull = 6.2831855f;
    private static final float radToIndex = 2607.5945f;
    public static final float radiansToDegrees = 57.295776f;
    public static Random random;

    private static class Atan2 {
        static final float[] table;

        private Atan2() {
        }

        static {
            table = new float[MathUtils.SIN_COUNT];
            for (int i = MathUtils.ATAN2_DIM; i < MathUtils.ATAN2_DIM; i++) {
                for (int j = MathUtils.ATAN2_DIM; j < MathUtils.ATAN2_DIM; j++) {
                    float y0 = ((float) j) / ((float) MathUtils.ATAN2_DIM);
                    table[(MathUtils.ATAN2_DIM * j) + i] = (float) Math.atan2((double) y0, (double) (((float) i) / ((float) MathUtils.ATAN2_DIM)));
                }
            }
        }
    }

    private static class Sin {
        static final float[] table;

        private Sin() {
        }

        static {
            int i;
            table = new float[MathUtils.SIN_COUNT];
            for (i = MathUtils.ATAN2_DIM; i < MathUtils.SIN_COUNT; i++) {
                table[i] = (float) Math.sin((double) (((((float) i) + 0.5f) / 16384.0f) * MathUtils.radFull));
            }
            for (i = MathUtils.ATAN2_DIM; i < 360; i += 90) {
                table[((int) (((float) i) * MathUtils.degToIndex)) & MathUtils.SIN_MASK] = (float) Math.sin((double) (((float) i) * MathUtils.degreesToRadians));
            }
        }
    }

    public static final float sin(float radians) {
        return Sin.table[((int) (radToIndex * radians)) & SIN_MASK];
    }

    public static final float cos(float radians) {
        return Sin.table[((int) ((1.5707964f + radians) * radToIndex)) & SIN_MASK];
    }

    public static final float sinDeg(float degrees) {
        return Sin.table[((int) (degToIndex * degrees)) & SIN_MASK];
    }

    public static final float cosDeg(float degrees) {
        return Sin.table[((int) ((90.0f + degrees) * degToIndex)) & SIN_MASK];
    }

    static {
        ATAN2_DIM = (int) Math.sqrt(BIG_ENOUGH_FLOOR);
        INV_ATAN2_DIM_MINUS_1 = TextTrackStyle.DEFAULT_FONT_SCALE / ((float) (ATAN2_DIM - 1));
        random = new Random();
    }

    public static final float atan2(float y, float x) {
        float mul;
        float add;
        float f;
        if (x < INV_ATAN2_DIM_MINUS_1) {
            if (y < INV_ATAN2_DIM_MINUS_1) {
                y = -y;
                mul = TextTrackStyle.DEFAULT_FONT_SCALE;
            } else {
                mul = GroundOverlayOptions.NO_DIMENSION;
            }
            x = -x;
            add = -3.1415927f;
        } else {
            if (y < INV_ATAN2_DIM_MINUS_1) {
                y = -y;
                mul = GroundOverlayOptions.NO_DIMENSION;
            } else {
                mul = TextTrackStyle.DEFAULT_FONT_SCALE;
            }
            add = INV_ATAN2_DIM_MINUS_1;
        }
        if (x < y) {
            f = y;
        } else {
            f = x;
        }
        float invDiv = TextTrackStyle.DEFAULT_FONT_SCALE / (f * INV_ATAN2_DIM_MINUS_1);
        if (invDiv == Float.POSITIVE_INFINITY) {
            return (((float) Math.atan2((double) y, (double) x)) + add) * mul;
        }
        return (Atan2.table[(ATAN2_DIM * ((int) (y * invDiv))) + ((int) (x * invDiv))] + add) * mul;
    }

    public static final int random(int range) {
        return random.nextInt(range + 1);
    }

    public static final int random(int start, int end) {
        return random.nextInt((end - start) + 1) + start;
    }

    public static final boolean randomBoolean() {
        return random.nextBoolean();
    }

    public static final boolean randomBoolean(float chance) {
        return random() < chance;
    }

    public static final float random() {
        return random.nextFloat();
    }

    public static final float random(float range) {
        return random.nextFloat() * range;
    }

    public static final float random(float start, float end) {
        return (random.nextFloat() * (end - start)) + start;
    }

    public static int nextPowerOfTwo(int value) {
        if (value == 0) {
            return 1;
        }
        value--;
        value |= value >> 1;
        value |= value >> 2;
        value |= value >> 4;
        value |= value >> 8;
        return (value | (value >> 16)) + 1;
    }

    public static boolean isPowerOfTwo(int value) {
        return value != 0 && ((value - 1) & value) == 0;
    }

    public static int clamp(int value, int min, int max) {
        if (value < min) {
            return min;
        }
        return value > max ? max : value;
    }

    public static short clamp(short value, short min, short max) {
        if (value < min) {
            return min;
        }
        return value > max ? max : value;
    }

    public static float clamp(float value, float min, float max) {
        if (value < min) {
            return min;
        }
        return value > max ? max : value;
    }

    public static int floor(float x) {
        return ((int) (((double) x) + BIG_ENOUGH_FLOOR)) - 16384;
    }

    public static int floorPositive(float x) {
        return (int) x;
    }

    public static int ceil(float x) {
        return ((int) (((double) x) + BIG_ENOUGH_CEIL)) - 16384;
    }

    public static int ceilPositive(float x) {
        return (int) (((double) x) + CEIL);
    }

    public static int round(float x) {
        return ((int) (((double) x) + BIG_ENOUGH_ROUND)) - 16384;
    }

    public static int roundPositive(float x) {
        return (int) (0.5f + x);
    }
}
