package com.example.duy.calculator.utils;

import android.widget.TextView;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.FormatExpression;
import com.example.duy.calculator.math_eval.Solver;
import io.github.kexanie.library.BuildConfig;

public class TextUtil {
    public static String getCleanText(TextView textView, Solver solver) {
        return removeFormatting(solver, textView.getText().toString());
    }

    public static String formatText(String input, FormatExpression formatExpression, Solver solver) {
        if (solver != null) {
            input = formatExpression.addComas(solver, input, -1);
        }
        return formatExpression.insertSupScripts(input);
    }

    protected static String removeFormatting(Solver solver, String input) {
        input = input.replace(Constants.POWER_PLACEHOLDER, Constants.POWER_UNICODE);
        if (solver != null) {
            return input.replace(String.valueOf(solver.getBaseModule().getSeparator()), BuildConfig.FLAVOR);
        }
        return input;
    }

    public static int countOccurrences(String haystack, char needle) {
        int count = 0;
        for (int i = 0; i < haystack.length(); i++) {
            if (haystack.charAt(i) == needle) {
                count++;
            }
        }
        return count;
    }
}
