package com.example.duy.calculator.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import io.github.kexanie.library.BuildConfig;

public class Settings {
    public static final String BASE = "BASE";
    public static final String CMPLX_ON = "COMPLEX";
    public static final String INPUT_BASE = "INPUT_BASE";
    public static final String INPUT_MATH = "inp_math";
    public static final String INPUT_STATISTIC = "INPUT_STATISTIC";
    public static final String IS_DEGREE = "IS_DEGREE";
    public static final String IS_FRACTION = "IS_FRACTION";
    private static final String IS_UPDATE = "IS_UPDATE_34";
    public static final String NEW_UPDATE = "new_update310";
    public static final String RESULT_BASE = "RESULT_BASE";
    public static final String RESULT_MATH = "res_math";
    public static final String USE_RADIANS = "USE_RADIANS";
    public static final String preName = "app_data";
    public static final long serialVersionUID = 310;
    private Editor editor;
    private SharedPreferences sharedPreferences;

    public Settings(Context context) {
        this.sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        this.editor = this.sharedPreferences.edit();
    }

    public static boolean useRadians(Context context) {
        return new Settings(context).getBoolean(USE_RADIANS);
    }

    public static boolean useComplex(Context context) {
        return new Settings(context).getBoolean(CMPLX_ON);
    }

    public static boolean useLightTheme(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean("THEME_STYLE", false);
    }

    public static void put(Context context, String key, boolean value) {
        new Settings(context).put(key, value);
    }

    public static void put(Context context, String key, String value) {
        new Settings(context).put(key, value);
    }

    public static String getString(Context context, String key) {
        return new Settings(context).getString(key);
    }

    public static boolean useFraction(Context context) {
        return new Settings(context).getBoolean(IS_FRACTION);
    }

    public static void setFraction(Context context, boolean b) {
        new Settings(context).put(IS_FRACTION, b);
    }

    public static boolean isUpdate(Context context) {
        return !new Settings(context).getBoolean(IS_UPDATE);
    }

    public static void setUpdate(Context abstractAppCompatActivity, boolean b) {
        new Settings(abstractAppCompatActivity).put(IS_UPDATE, b);
    }

    public void put(String key, String value) {
        this.editor.putString(key, value);
        this.editor.apply();
    }

    public void put(String key, int value) {
        this.editor.putInt(key, value);
        this.editor.apply();
    }

    public void put(String key, boolean value) {
        this.editor.putBoolean(key, value);
        this.editor.apply();
    }

    public void put(String key, long value) {
        this.editor.putLong(key, value);
        this.editor.apply();
    }

    public void put(String key, float value) {
        this.editor.putFloat(key, value);
        this.editor.apply();
    }

    public int getInt(String key) {
        return this.sharedPreferences.getInt(key, -1);
    }

    public long getLong(String key) {
        return this.sharedPreferences.getLong(key, -1);
    }

    public String getString(String key) {
        return this.sharedPreferences.getString(key, BuildConfig.FLAVOR);
    }

    public boolean getBoolean(String key) {
        return this.sharedPreferences.getBoolean(key, false);
    }

    public boolean isNewUpdate() {
        return this.sharedPreferences.getBoolean(NEW_UPDATE, false);
    }

    public void setVar(String name, String value) {
        this.editor.putString("var_" + name, value);
    }
}
