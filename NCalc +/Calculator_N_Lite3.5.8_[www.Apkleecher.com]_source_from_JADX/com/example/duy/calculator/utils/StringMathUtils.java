package com.example.duy.calculator.utils;

import com.example.duy.calculator.math_eval.Constants;
import com.github.clans.fab.R;
import io.github.kexanie.library.BuildConfig;

public class StringMathUtils {
    public static String optimizeMath(String s) {
        String math = BuildConfig.FLAVOR;
        s = s.replace('{', Constants.LEFT_PAREN).replace('}', Constants.RIGHT_PAREN).replace('[', Constants.LEFT_PAREN).replace(']', Constants.RIGHT_PAREN).replace(':', Constants.DIV_UNICODE);
        for (int i = 0; i < s.length(); i++) {
            if (isAccept(s.charAt(i))) {
                math = math + s.charAt(i);
            }
        }
        return math;
    }

    private static boolean isAccept(char c) {
        if (c >= '0' && c <= '9') {
            return true;
        }
        switch (c) {
            case R.styleable.FloatingActionMenu_menu_colorRipple /*33*/:
            case R.styleable.FloatingActionMenu_menu_backgroundColor /*35*/:
            case R.styleable.FloatingActionMenu_menu_fab_show_animation /*37*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearanceLargePopupMenu /*40*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearanceSmallPopupMenu /*41*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_textAppearancePopupMenuHeader /*42*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dialogTheme /*43*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_listDividerAlertDialog /*45*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_actionDropDownStyle /*46*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_dropdownListPreferredItemHeight /*47*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_toolbarNavigationButtonStyle /*60*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_popupMenuStyle /*61*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_popupWindowStyle /*62*/:
            case com.getkeepsafe.taptargetview.R.styleable.AppCompatTheme_alertDialogButtonGroupStyle /*94*/:
                return true;
            default:
                return false;
        }
    }
}
