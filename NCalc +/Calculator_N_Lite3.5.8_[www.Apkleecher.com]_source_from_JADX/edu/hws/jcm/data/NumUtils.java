package edu.hws.jcm.data;

import com.getkeepsafe.taptargetview.R;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public class NumUtils {
    public static double stringToReal(String s) {
        try {
            return new Double(s).doubleValue();
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }

    public static String realToString(double x) {
        return realToString(x, 10);
    }

    public static String realToString(double x, int width) {
        width = Math.min(25, Math.max(6, width));
        if (Double.isNaN(x)) {
            return "undefined";
        }
        if (!Double.isInfinite(x)) {
            String s = String.valueOf(x);
            if (Math.rint(x) == x && Math.abs(x) < 5.0E15d && s.length() <= width + 2) {
                return String.valueOf((long) x);
            }
            if (s.length() <= width) {
                return s;
            }
            boolean neg = false;
            if (x < 0.0d) {
                neg = true;
                x = -x;
                width--;
                s = String.valueOf(x);
            }
            long maxForNonExp = 5 * ((long) Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) (width - 2)));
            if (x >= 5.0E-4d && x <= ((double) maxForNonExp) && s.indexOf(69) == -1 && s.indexOf(R.styleable.AppCompatTheme_autoCompleteTextViewStyle) == -1) {
                s = trimZeros(round(s, width));
            } else if (x > 1.0d) {
                power = (long) Math.floor(Math.log(x) / Math.log(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS));
                exp = "E" + power;
                s = trimZeros(round(String.valueOf(x / Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) power)), width - exp.length())) + exp;
            } else {
                power = (long) Math.ceil((-Math.log(x)) / Math.log(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS));
                exp = "E-" + power;
                s = trimZeros(round(String.valueOf(x * Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, (double) power)), width - exp.length())) + exp;
            }
            if (neg) {
                return "-" + s;
            }
            return s;
        } else if (x < 0.0d) {
            return "-INF";
        } else {
            return "INF";
        }
    }

    private static String trimZeros(String num) {
        if (num.indexOf(46) < 0 || num.charAt(num.length() - 1) != '0') {
            return num;
        }
        int i = num.length() - 1;
        while (num.charAt(i) == '0') {
            i--;
        }
        if (num.charAt(i) == '.') {
            return num.substring(0, i);
        }
        return num.substring(0, i + 1);
    }

    private static String round(String num, int length) {
        if (num.indexOf(46) < 0 || num.length() <= length) {
            return num;
        }
        if (num.charAt(length) < '5' || num.charAt(length) == '.') {
            return num.substring(0, length);
        }
        char[] temp = new char[(length + 1)];
        int ct = length;
        boolean rounding = true;
        for (int i = length - 1; i >= 0; i--) {
            temp[ct] = num.charAt(i);
            if (rounding && temp[ct] != '.') {
                if (temp[ct] < '9') {
                    temp[ct] = (char) (temp[ct] + 1);
                    rounding = false;
                } else {
                    temp[ct] = '0';
                }
            }
            ct--;
        }
        if (rounding) {
            temp[ct] = '1';
            ct--;
        }
        return new String(temp, ct + 1, length - ct);
    }
}
