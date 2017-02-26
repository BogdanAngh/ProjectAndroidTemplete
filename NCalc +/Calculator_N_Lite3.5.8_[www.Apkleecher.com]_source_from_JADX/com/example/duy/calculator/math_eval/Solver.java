package com.example.duy.calculator.math_eval;

import android.util.Log;
import io.github.kexanie.library.BuildConfig;
import org.javia.arity.Symbols;
import org.javia.arity.SyntaxException;

public class Solver {
    public static final long serialVersionUID = 4;
    private static final Symbols symbols;
    private String TAG;
    private BaseModule mBaseModule;

    static {
        symbols = new Symbols();
    }

    public Solver() {
        this.TAG = Solver.class.getName();
        this.mBaseModule = new BaseModule(this);
    }

    public static boolean equal(String a, String b) {
        return clean(a).equals(clean(b));
    }

    public static String clean(String equation) {
        return equation.replace(Constants.MINUS_UNICODE, Constants.MINUS_UNICODE).replace(Constants.DIV_UNICODE, Constants.DIV_UNICODE).replace(Constants.MUL_UNICODE, Constants.MUL_UNICODE).replace(Constants.INFINITY, Character.toString(Constants.INFINITY_UNICODE));
    }

    public static boolean isOperator(char c) {
        if ("+-/*^".indexOf(c) != -1) {
            return true;
        }
        return false;
    }

    public static boolean isOperator(String c) {
        return isOperator(c.charAt(0));
    }

    public static boolean isNegative(String number) {
        return number.startsWith(String.valueOf(Constants.MINUS_UNICODE)) || number.startsWith("-");
    }

    public static boolean isDigit(char number) {
        return String.valueOf(number).matches(Constants.REGEX_NUMBER);
    }

    public static boolean isComplex(String value) {
        return value.contains("i") || value.contains("I");
    }

    public String evaluate(String input, BigEvaluator evaluator) throws SyntaxException {
        if (input.trim().isEmpty()) {
            return BuildConfig.FLAVOR;
        }
        String decimalInput = convertToDecimal(input);
        String str = BuildConfig.FLAVOR;
        try {
            str = evaluator.getResult(decimalInput);
            if (str.toLowerCase().equals(Constants.TRUE) || str.toLowerCase().equals(Constants.FALSE)) {
                return str;
            }
            str = clean(this.mBaseModule.changeBase(str, Base.DECIMAL, this.mBaseModule.getBase()));
            Log.d(this.TAG, "solver: " + str);
            return str;
        } catch (Exception e) {
            e.printStackTrace();
            throw new SyntaxException();
        }
    }

    public double eval(String input) throws SyntaxException {
        return symbols.eval(input);
    }

    public void define(String var, double val) {
        try {
            symbols.define(var, val);
        } catch (Exception e) {
        }
    }

    public String convertToDecimal(String input) throws SyntaxException {
        return this.mBaseModule.changeBase(input, this.mBaseModule.getBase(), Base.DECIMAL);
    }

    public Base getBase() {
        return this.mBaseModule.getBase();
    }

    public void setBase(Base base) {
        this.mBaseModule.setBase(base);
    }

    public BaseModule getBaseModule() {
        return this.mBaseModule;
    }
}
