package com.example.duy.calculator.math_eval;

import android.content.Context;
import com.example.duy.calculator.R;
import java.util.LinkedList;
import java.util.List;

public class Tokenizer {
    private String TAG;
    private Context context;
    boolean debug;
    private final Context mContext;
    private final List<Localizer> mReplacements;

    private class Localizer {
        String english;
        String local;

        Localizer(String english, String local) {
            this.english = english;
            this.local = local;
        }
    }

    public Tokenizer(Context context) {
        this.debug = false;
        this.TAG = Tokenizer.class.getName();
        this.mContext = context;
        this.mReplacements = new LinkedList();
        this.context = context;
    }

    private void generateReplacements(Context context) {
        this.mReplacements.clear();
        this.mReplacements.add(new Localizer("/", context.getString(R.string.div)));
        this.mReplacements.add(new Localizer("*", context.getString(R.string.mul)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus2)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus3)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus4)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus5)));
        this.mReplacements.add(new Localizer("-", context.getString(R.string.minus)));
        this.mReplacements.add(new Localizer("cbrt", context.getString(R.string.op_cbrt)));
        this.mReplacements.add(new Localizer("sin", context.getString(R.string.sin)));
        this.mReplacements.add(new Localizer("cos", context.getString(R.string.cos)));
        this.mReplacements.add(new Localizer("tan", context.getString(R.string.tan)));
        this.mReplacements.add(new Localizer("asin", context.getString(R.string.arcsin)));
        this.mReplacements.add(new Localizer("acos", context.getString(R.string.arccos)));
        this.mReplacements.add(new Localizer("atan", context.getString(R.string.arctan)));
        this.mReplacements.add(new Localizer("asinh", context.getString(R.string.asinh)));
        this.mReplacements.add(new Localizer("acosh", context.getString(R.string.acosh)));
        this.mReplacements.add(new Localizer("atanh", context.getString(R.string.atanh)));
        this.mReplacements.add(new Localizer("ln", context.getString(R.string.fun_ln)));
        this.mReplacements.add(new Localizer("log", context.getString(R.string.lg)));
        this.mReplacements.add(new Localizer("exp", context.getString(R.string.exp)));
        this.mReplacements.add(new Localizer("infinity", Character.toString(Constants.INFINITY_UNICODE)));
        this.mReplacements.add(new Localizer("pi", context.getString(R.string.pi_symbol)));
        this.mReplacements.add(new Localizer("sqrt", context.getString(R.string.sqrt_sym)));
        this.mReplacements.add(new Localizer("degree", context.getString(R.string.degree_sym)));
        this.mReplacements.add(new Localizer("ceiling", context.getString(R.string.ceil)));
        this.mReplacements.add(new Localizer("floor", context.getString(R.string.floor)));
        this.mReplacements.add(new Localizer("Arcsinh", context.getString(R.string.asinh)));
        this.mReplacements.add(new Localizer("Arccosh", context.getString(R.string.acosh)));
        this.mReplacements.add(new Localizer("Arctanh", context.getString(R.string.atanh)));
        this.mReplacements.add(new Localizer("Arccos", context.getString(R.string.arccos)));
        this.mReplacements.add(new Localizer("Arctan", context.getString(R.string.arctan)));
        this.mReplacements.add(new Localizer("Arcsin", context.getString(R.string.arcsin)));
        this.mReplacements.add(new Localizer("Cbrt", context.getString(R.string.op_cbrt)));
        this.mReplacements.add(new Localizer("Sin", context.getString(R.string.sin)));
        this.mReplacements.add(new Localizer("Cos", context.getString(R.string.cos)));
        this.mReplacements.add(new Localizer("Tan", context.getString(R.string.tan)));
        this.mReplacements.add(new Localizer("Ln", context.getString(R.string.fun_ln)));
        this.mReplacements.add(new Localizer("Log", context.getString(R.string.lg)));
        this.mReplacements.add(new Localizer("Exp", context.getString(R.string.exp)));
        this.mReplacements.add(new Localizer(Constants.INFINITY, Character.toString(Constants.INFINITY_UNICODE)));
        this.mReplacements.add(new Localizer("Pi", context.getString(R.string.pi_symbol)));
        this.mReplacements.add(new Localizer("Sqrt", context.getString(R.string.sqrt_sym)));
        this.mReplacements.add(new Localizer("Degree", context.getString(R.string.degree_sym)));
        this.mReplacements.add(new Localizer("Floor", context.getString(R.string.floor)));
        this.mReplacements.add(new Localizer("<=", context.getString(R.string.leq)));
        this.mReplacements.add(new Localizer(">=", context.getString(R.string.geq)));
        this.mReplacements.add(new Localizer("!=", context.getString(R.string.neq)));
    }

    public String getNormalExpression(String expr) {
        generateReplacements(this.mContext);
        for (Localizer replacement : this.mReplacements) {
            expr = expr.replace(replacement.local, replacement.english);
        }
        return expr;
    }

    public String getLocalizedExpression(String expr) {
        generateReplacements(this.mContext);
        for (Localizer replacement : this.mReplacements) {
            expr = expr.replace(replacement.english, replacement.local);
        }
        return expr;
    }

    public Context getContext() {
        return this.context;
    }
}
