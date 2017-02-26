package com.example.duy.calculator.utils;

import android.content.Context;
import android.content.res.Resources;
import com.example.duy.calculator.R;

public class ThemeUtil {
    public static final String NULL = "";
    public static final int THEME_NOT_FOUND = -1;
    private final int mBlueTheme;
    private final int mBrownTheme;
    private final int mDarkTheme;
    private final int mDayNightTheme;
    private final int mDeepPurpleTheme;
    private final int mLightDarkActionBarTheme;
    private final int mLightTheme;
    private final int mPurpleTheme;
    private final int mRedTheme;
    private Resources mResources;
    private final int mTealTheme;
    private final int mYellowTheme;

    public ThemeUtil(Context applicationContext) {
        this.mResources = applicationContext.getResources();
        this.mBlueTheme = R.style.AppTheme_Blue_NoActionBar;
        this.mRedTheme = R.style.AppTheme_Red_NoActionBar;
        this.mPurpleTheme = R.style.AppTheme_Purple_NoActionBar;
        this.mDeepPurpleTheme = R.style.AppTheme_DeepPurple_NoActionBar;
        this.mTealTheme = R.style.AppTheme_Teal_NoActionBar;
        this.mYellowTheme = R.style.AppTheme_Yellow_NoActionBar;
        this.mLightTheme = R.style.AppTheme_Light_NoActionBar;
        this.mDarkTheme = R.style.AppTheme_NoActionBar;
        this.mLightDarkActionBarTheme = R.style.AppTheme_Light_DarkActionBar_NoActionBar;
        this.mBrownTheme = R.style.AppTheme_Brown_NoActionBar;
        this.mDayNightTheme = R.style.AppTheme_DayNight;
    }

    public int getTheme(String name) {
        name = name.trim();
        if (name.equals(NULL)) {
            return THEME_NOT_FOUND;
        }
        if (name.equals(this.mResources.getString(R.string.theme_light))) {
            return this.mLightTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_brown))) {
            return this.mBrownTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_blue))) {
            return this.mBlueTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_red))) {
            return this.mRedTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_purple))) {
            return this.mPurpleTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_deep_purple))) {
            return this.mDeepPurpleTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_teal))) {
            return this.mTealTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_yellow))) {
            return this.mYellowTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_dark))) {
            return this.mDarkTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_light_dark_action_bar))) {
            return this.mLightDarkActionBarTheme;
        }
        if (name.equals(this.mResources.getString(R.string.theme_day_night))) {
            return this.mDayNightTheme;
        }
        return this.mLightTheme;
    }
}
