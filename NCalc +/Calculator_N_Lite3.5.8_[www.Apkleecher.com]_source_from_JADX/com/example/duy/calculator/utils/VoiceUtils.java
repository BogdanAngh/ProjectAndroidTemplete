package com.example.duy.calculator.utils;

import com.example.duy.calculator.math_eval.Constants;
import java.util.ArrayList;

public class VoiceUtils {
    private static ArrayList<ItemReplace> mReplace;

    public static class ItemReplace {
        private String math;
        private String text;

        public ItemReplace(String text, String math) {
            this.text = text;
            this.math = math;
        }

        public String getText() {
            return this.text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMath() {
            return this.math;
        }

        public void setMath(String math) {
            this.math = math;
        }
    }

    static {
        mReplace = new ArrayList();
    }

    public static void init() {
        mReplace.add(new ItemReplace("kh\u00f4ng", Constants.ZERO));
        mReplace.add(new ItemReplace("m\u1ed9t", "1"));
        mReplace.add(new ItemReplace("hai", "2"));
        mReplace.add(new ItemReplace("ba", "3"));
        mReplace.add(new ItemReplace("b\u1ed1n", "4"));
        mReplace.add(new ItemReplace("n\u0103m", "5"));
        mReplace.add(new ItemReplace("l\u0103m", "5"));
        mReplace.add(new ItemReplace("s\u00e1u", "6"));
        mReplace.add(new ItemReplace("b\u1ea3y", "7"));
        mReplace.add(new ItemReplace("b\u1ea9y", "7"));
        mReplace.add(new ItemReplace("t\u00e1m", "8"));
        mReplace.add(new ItemReplace("ch\u00edn", "9"));
        mReplace.add(new ItemReplace("ch\u00ednh", "9"));
        mReplace.add(new ItemReplace("m\u01b0\u1eddi", "10"));
        mReplace.add(new ItemReplace("c\u1ed9ng", "+"));
        mReplace.add(new ItemReplace("tr\u1eeb", "-"));
        mReplace.add(new ItemReplace("ch\u1eeb", "-"));
        mReplace.add(new ItemReplace("nh\u00e2n", "*"));
        mReplace.add(new ItemReplace("chia", "/"));
        mReplace.add(new ItemReplace("m\u0169", "^"));
        mReplace.add(new ItemReplace("m\u1ee7", "^"));
        mReplace.add(new ItemReplace("giai th\u1eeba", "!"));
    }

    public static String replace(String res) {
        init();
        for (int i = 0; i < mReplace.size(); i++) {
            res = res.replace(((ItemReplace) mReplace.get(i)).getText(), ((ItemReplace) mReplace.get(i)).getMath());
        }
        return res;
    }
}
