package com.example.duy.calculator.utils;

import android.graphics.Color;
import java.util.Random;

public class ColorUtil {
    public static int BLACK;
    public static int BLUE;
    public static int BLUE_GREY;
    public static int BROW;
    public static int[] COLOR;
    public static int CYAN;
    public static int DEEP_PURPLE;
    public static int GREEN;
    public static int GREY;
    public static int INDIGO;
    public static int LIGHT_GREEN;
    public static int LIME;
    public static int ORANGE;
    public static int PINK;
    public static int PURPLE;
    public static int RED;
    public static int TEAL;
    public static int YELLOW;

    static {
        RED = Color.parseColor("#FF5252");
        PINK = Color.parseColor("#E91E63");
        PURPLE = Color.parseColor("#9C27B0");
        DEEP_PURPLE = Color.parseColor("#7C4DFF");
        INDIGO = Color.parseColor("#3F51B5");
        BLUE = Color.parseColor("#448AFF");
        CYAN = Color.parseColor("#03A9F4");
        TEAL = Color.parseColor("#009688");
        GREEN = Color.parseColor("#009688");
        LIGHT_GREEN = Color.parseColor("#8BC34A");
        LIME = Color.parseColor("#CDDC39");
        YELLOW = Color.parseColor("#FFEB3B");
        ORANGE = Color.parseColor("#FFEB3B");
        BROW = Color.parseColor("#795548");
        GREY = Color.parseColor("#9E9E9E");
        BLUE_GREY = Color.parseColor("#9E9E9E");
        BLACK = Color.parseColor("#000000");
        COLOR = new int[]{RED, PINK, PURPLE, DEEP_PURPLE, INDIGO, BLUE, CYAN, TEAL, GREEN, LIGHT_GREEN, LIME, YELLOW, ORANGE, BROW, GREY, BLUE_GREY};
    }

    public int getColorRandom() {
        return COLOR[new Random(new Random().nextLong()).nextInt(COLOR.length - 1)];
    }
}
