package com.example.duy.calculator.math_eval;

import io.github.kexanie.library.BuildConfig;
import io.github.kexanie.library.R;
import java.util.ArrayList;
import java.util.Locale;
import java.util.regex.Pattern;
import org.apache.commons.math4.random.ValueServer;
import org.javia.arity.SyntaxException;
import org.matheclipse.core.interfaces.IExpr;

public class BaseModule extends Module {
    private static final int PRECISION = 8;
    public static final char SELECTION_HANDLE = '\u2620';
    private static final String TAG;
    private final String REGEX_NOT_NUMBER;
    private final String REGEX_NUMBER;
    private Base mBase;
    private OnBaseChangeListener mBaseChangeListener;

    static /* synthetic */ class 1 {
        static final /* synthetic */ int[] $SwitchMap$com$example$duy$calculator$math_eval$Base;

        static {
            $SwitchMap$com$example$duy$calculator$math_eval$Base = new int[Base.values().length];
            try {
                $SwitchMap$com$example$duy$calculator$math_eval$Base[Base.BINARY.ordinal()] = 1;
            } catch (NoSuchFieldError e) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$math_eval$Base[Base.DECIMAL.ordinal()] = 2;
            } catch (NoSuchFieldError e2) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$math_eval$Base[Base.HEXADECIMAL.ordinal()] = 3;
            } catch (NoSuchFieldError e3) {
            }
            try {
                $SwitchMap$com$example$duy$calculator$math_eval$Base[Base.OCTAL.ordinal()] = 4;
            } catch (NoSuchFieldError e4) {
            }
        }
    }

    public interface OnBaseChangeListener {
        void onBaseChange(Base base);
    }

    static {
        TAG = BaseModule.class.getName();
    }

    public BaseModule(Solver solver) {
        super(solver);
        this.mBase = Base.DECIMAL;
        this.REGEX_NUMBER = Constants.REGEX_NUMBER.substring(0, Constants.REGEX_NUMBER.length() - 1) + SELECTION_HANDLE + "]";
        this.REGEX_NOT_NUMBER = Constants.REGEX_NOT_NUMBER.substring(0, Constants.REGEX_NOT_NUMBER.length() - 1) + SELECTION_HANDLE + "]";
    }

    public Base getBase() {
        return this.mBase;
    }

    public void setBase(Base base) {
        this.mBase = base;
        if (this.mBaseChangeListener != null) {
            this.mBaseChangeListener.onBaseChange(this.mBase);
        }
    }

    public String setBase(String input, Base base) throws SyntaxException {
        String text = changeBase(input, this.mBase, base);
        setBase(base);
        return text;
    }

    String changeBase(String text, Base base) throws SyntaxException {
        return changeBase(text, Base.DECIMAL, base);
    }

    String changeBase(String originalText, Base oldBase, Base newBase) throws SyntaxException {
        if (oldBase.equals(newBase) || originalText.isEmpty() || originalText.matches(this.REGEX_NOT_NUMBER)) {
            return originalText;
        }
        int i;
        String[] operations = originalText.split(this.REGEX_NUMBER);
        String[] numbers = originalText.split(this.REGEX_NOT_NUMBER);
        String[] translatedNumbers = new String[numbers.length];
        for (i = 0; i < numbers.length; i++) {
            if (!numbers[i].isEmpty()) {
                switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[oldBase.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[newBase.ordinal()]) {
                            case ValueServer.REPLAY_MODE /*1*/:
                                continue;
                            case IExpr.DOUBLEID /*2*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, 10);
                                    break;
                                } catch (NumberFormatException e) {
                                    throw new SyntaxException();
                                }
                            case ValueServer.EXPONENTIAL_MODE /*3*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, 16);
                                    break;
                                } catch (NumberFormatException e2) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLECOMPLEXID /*4*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 2, PRECISION);
                                    break;
                                } catch (NumberFormatException e3) {
                                    throw new SyntaxException();
                                }
                            default:
                                break;
                        }
                    case IExpr.DOUBLEID /*2*/:
                        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[newBase.ordinal()]) {
                            case ValueServer.REPLAY_MODE /*1*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 10, 2);
                                    break;
                                } catch (NumberFormatException e4) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLEID /*2*/:
                                continue;
                            case ValueServer.EXPONENTIAL_MODE /*3*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 10, 16);
                                    break;
                                } catch (NumberFormatException e5) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLECOMPLEXID /*4*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 10, PRECISION);
                                    break;
                                } catch (NumberFormatException e6) {
                                    throw new SyntaxException();
                                }
                            default:
                                break;
                        }
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[newBase.ordinal()]) {
                            case ValueServer.REPLAY_MODE /*1*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 16, 2);
                                    break;
                                } catch (NumberFormatException e7) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLEID /*2*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 16, 10);
                                    break;
                                } catch (NumberFormatException e8) {
                                    throw new SyntaxException();
                                }
                            case ValueServer.EXPONENTIAL_MODE /*3*/:
                                continue;
                            case IExpr.DOUBLECOMPLEXID /*4*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], 16, PRECISION);
                                    break;
                                } catch (NumberFormatException e9) {
                                    throw new SyntaxException();
                                }
                            default:
                                break;
                        }
                    case IExpr.DOUBLECOMPLEXID /*4*/:
                        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[newBase.ordinal()]) {
                            case ValueServer.REPLAY_MODE /*1*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], PRECISION, 2);
                                    break;
                                } catch (NumberFormatException e10) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLEID /*2*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], PRECISION, 10);
                                    break;
                                } catch (NumberFormatException e11) {
                                    throw new SyntaxException();
                                }
                            case ValueServer.EXPONENTIAL_MODE /*3*/:
                                try {
                                    translatedNumbers[i] = newBase(numbers[i], PRECISION, 16);
                                    break;
                                } catch (NumberFormatException e12) {
                                    throw new SyntaxException();
                                }
                            case IExpr.DOUBLECOMPLEXID /*4*/:
                                continue;
                            default:
                                break;
                        }
                    default:
                        continue;
                }
            }
        }
        String text = BuildConfig.FLAVOR;
        Object[] o = removeWhitespace(operations);
        Object[] n = removeWhitespace(translatedNumbers);
        if (originalText.substring(0, 1).matches(this.REGEX_NUMBER)) {
            i = 0;
            while (i < o.length && i < n.length) {
                text = (text + n[i]) + o[i];
                i++;
            }
        } else {
            i = 0;
            while (i < o.length && i < n.length) {
                text = (text + o[i]) + n[i];
                i++;
            }
        }
        if (o.length > n.length) {
            return text + o[o.length - 1];
        }
        if (n.length > o.length) {
            return text + n[n.length - 1];
        }
        return text;
    }

    public String newBase(String originalNumber, int originalBase, int base) throws SyntaxException {
        String[] split = originalNumber.split(Pattern.quote(getDecimalPoint() + BuildConfig.FLAVOR));
        if (split.length == 0) {
            split = new String[]{Constants.ZERO};
        }
        if (split[0].isEmpty()) {
            split[0] = Constants.ZERO;
        }
        if (originalBase != 10) {
            split[0] = Long.toString(Long.parseLong(split[0], originalBase));
        }
        String wholeNumber = BuildConfig.FLAVOR;
        switch (base) {
            case IExpr.DOUBLEID /*2*/:
                wholeNumber = Long.toBinaryString(Long.parseLong(split[0]));
                break;
            case PRECISION /*8*/:
                wholeNumber = Long.toOctalString(Long.parseLong(split[0]));
                break;
            case R.styleable.SwitchCompat_switchMinWidth /*10*/:
                wholeNumber = split[0];
                break;
            case IExpr.FRACTIONID /*16*/:
                wholeNumber = Long.toHexString(Long.parseLong(split[0]));
                break;
        }
        if (split.length == 1) {
            return wholeNumber.toUpperCase(Locale.US);
        }
        double decimal;
        if (split[1].length() > 13) {
            split[1] = split[1].substring(0, 13);
        }
        if (originalBase != 10) {
            decimal = getSolver().eval(Long.toString(Long.parseLong(split[1], originalBase)) + "/" + originalBase + "^" + split[1].length());
        } else {
            decimal = Double.parseDouble("0." + split[1]);
        }
        if (decimal == 0.0d) {
            return wholeNumber.toUpperCase(Locale.US);
        }
        String decimalNumber = BuildConfig.FLAVOR;
        int i = 0;
        while (decimal != 0.0d && i <= PRECISION) {
            decimal *= (double) base;
            int id = (int) Math.floor(decimal);
            decimal -= (double) id;
            decimalNumber = decimalNumber + Integer.toHexString(id);
            i++;
        }
        return (wholeNumber + getDecimalPoint() + decimalNumber).toUpperCase(Locale.US);
    }

    private Object[] removeWhitespace(String[] strings) {
        ArrayList<String> formatted = new ArrayList(strings.length);
        for (String s : strings) {
            if (!(s == null || s.isEmpty())) {
                formatted.add(s);
            }
        }
        return formatted.toArray();
    }

    public String groupSentence(String originalText, int selectionHandle) {
        if (originalText.isEmpty() || originalText.matches(this.REGEX_NOT_NUMBER)) {
            return originalText;
        }
        int i;
        if (selectionHandle >= 0 && selectionHandle <= originalText.length()) {
            originalText = originalText.substring(0, selectionHandle) + SELECTION_HANDLE + originalText.substring(selectionHandle);
        }
        String[] operations = originalText.split(this.REGEX_NUMBER);
        String[] numbers = originalText.split(this.REGEX_NOT_NUMBER);
        String[] translatedNumbers = new String[numbers.length];
        for (i = 0; i < numbers.length; i++) {
            if (!numbers[i].isEmpty()) {
                translatedNumbers[i] = groupDigits(numbers[i], this.mBase);
            }
        }
        String text = BuildConfig.FLAVOR;
        Object[] o = removeWhitespace(operations);
        Object[] n = removeWhitespace(translatedNumbers);
        if (originalText.substring(0, 1).matches(this.REGEX_NUMBER)) {
            i = 0;
            while (i < o.length && i < n.length) {
                text = (text + n[i]) + o[i];
                i++;
            }
        } else {
            i = 0;
            while (i < o.length && i < n.length) {
                text = (text + o[i]) + n[i];
                i++;
            }
        }
        if (o.length > n.length) {
            return text + o[o.length - 1];
        }
        if (n.length > o.length) {
            return text + n[n.length - 1];
        }
        return text;
    }

    public String groupDigits(String number, Base base) {
        String sign = BuildConfig.FLAVOR;
        if (Solver.isNegative(number)) {
            sign = String.valueOf(Constants.MINUS_UNICODE);
            number = number.substring(1);
        }
        String wholeNumber = number;
        String remainder = BuildConfig.FLAVOR;
        if (number.contains(getDecimalPoint() + BuildConfig.FLAVOR)) {
            if (number.startsWith(getDecimalPoint() + BuildConfig.FLAVOR)) {
                wholeNumber = BuildConfig.FLAVOR;
                remainder = number;
            } else {
                String[] temp = number.split(Pattern.quote(getDecimalPoint() + BuildConfig.FLAVOR));
                wholeNumber = temp[0];
                remainder = getDecimalPoint() + (temp.length == 1 ? BuildConfig.FLAVOR : temp[1]);
            }
        }
        return sign + group(wholeNumber, getSeparatorDistance(base), getSeparator(base)) + remainder;
    }

    private String group(String wholeNumber, int spacing, char separator) {
        StringBuilder sb = new StringBuilder();
        int digitsSeen = 0;
        int i = wholeNumber.length() - 1;
        while (i >= 0) {
            char curChar = wholeNumber.charAt(i);
            sb.insert(0, curChar);
            if (!(curChar == SELECTION_HANDLE || i == 0 || (i == 1 && wholeNumber.charAt(0) == SELECTION_HANDLE))) {
                digitsSeen++;
                if (digitsSeen > 0 && digitsSeen % spacing == 0) {
                    sb.insert(0, separator);
                }
            }
            i--;
        }
        return sb.toString();
    }

    public char getSeparator(Base base) {
        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[base.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return getBinSeparator();
            case IExpr.DOUBLEID /*2*/:
                return getDecSeparator();
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return getHexSeparator();
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return getOctSeparator();
            default:
                return '\u0000';
        }
    }

    public char getSeparator() {
        return getSeparator(this.mBase);
    }

    private int getSeparatorDistance(Base base) {
        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[base.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return getBinSeparatorDistance();
            case IExpr.DOUBLEID /*2*/:
                return getDecSeparatorDistance();
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return getHexSeparatorDistance();
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return getOctSeparatorDistance();
            default:
                return -1;
        }
    }

    public OnBaseChangeListener getOnBaseChangeListener() {
        return this.mBaseChangeListener;
    }

    public void setOnBaseChangeListener(OnBaseChangeListener l) {
        this.mBaseChangeListener = l;
    }

    public int getBaseNumber(Base base) {
        switch (1.$SwitchMap$com$example$duy$calculator$math_eval$Base[base.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return 2;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return 16;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return PRECISION;
            default:
                return 10;
        }
    }
}
