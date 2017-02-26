package com.x5.template.filters;

import com.example.duy.calculator.math_eval.Constants;
import com.x5.template.Chunk;

public class QuickCalcFilter extends BasicFilter implements ChunkFilter {
    public String transformText(Chunk chunk, String text, FilterArgs args) {
        return applyQuickCalc(text, args.getUnparsedArgs());
    }

    public String getFilterName() {
        return "qcalc";
    }

    private static String applyQuickCalc(String text, String calc) {
        if (text == null) {
            return null;
        }
        if (calc == null) {
            return text;
        }
        try {
            char op;
            if (text.indexOf(".") > 0 || calc.indexOf(".") > 0) {
                double x = Double.parseDouble(text);
                op = calc.charAt(0);
                double y = Double.parseDouble(calc.substring(1));
                double z = x;
                if (op == Constants.MINUS_UNICODE) {
                    z = x - y;
                }
                if (op == Constants.PLUS_UNICODE) {
                    z = x + y;
                }
                if (op == Constants.MUL_UNICODE) {
                    z = x * y;
                }
                if (op == Constants.DIV_UNICODE) {
                    z = x / y;
                }
                if (op == '%') {
                    z = x % y;
                }
                return Double.toString(z);
            }
            long x2 = Long.parseLong(text);
            op = calc.charAt(0);
            long y2 = Long.parseLong(calc.substring(1));
            long z2 = x2;
            if (op == Constants.MINUS_UNICODE) {
                z2 = x2 - y2;
            }
            if (op == Constants.PLUS_UNICODE) {
                z2 = x2 + y2;
            }
            if (op == Constants.MUL_UNICODE) {
                z2 = x2 * y2;
            }
            if (op == Constants.DIV_UNICODE) {
                z2 = x2 / y2;
            }
            if (op == '%') {
                z2 = x2 % y2;
            }
            if (op == Constants.POWER_UNICODE) {
                z2 = Math.round(Math.pow((double) x2, (double) y2));
            }
            return Long.toString(z2);
        } catch (NumberFormatException e) {
            return text;
        }
    }
}
