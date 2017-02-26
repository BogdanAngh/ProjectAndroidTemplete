package com.example.duy.calculator.math_eval;

import android.util.Log;
import org.javia.arity.SyntaxException;

public abstract class LogicEvaluator {
    public static final String ERROR_INDEX_STRING = "?";
    public static final int INPUT_EMPTY = 2;
    public static final int RESULT_ERROR = -1;
    public static final int RESULT_ERROR_WITH_INDEX = 3;
    public static final int RESULT_OK = 1;
    private String TAG;
    private final Solver mSolver;
    protected final Tokenizer mTokenizer;

    public interface EvaluateCallback {
        void onEvaluate(String str, String str2, int i);
    }

    public abstract BigEvaluator getEvaluator();

    public LogicEvaluator(Tokenizer tokenizer) {
        this.TAG = LogicEvaluator.class.getName();
        this.mSolver = new Solver();
        this.mTokenizer = tokenizer;
    }

    public Tokenizer getTokenizer() {
        return this.mTokenizer;
    }

    public void evaluateBase(CharSequence expr, EvaluateCallback callback) {
        evaluate(expr.toString(), callback);
    }

    private void evaluate(String expr, EvaluateCallback callback) {
        expr = this.mTokenizer.getNormalExpression(expr);
        Log.d(this.TAG, "evaluate: " + expr);
        try {
            String result = this.mTokenizer.getLocalizedExpression(this.mSolver.evaluate(expr, getEvaluator()));
            Log.d(this.TAG, "evaluate: result = " + result);
            callback.onEvaluate(expr, result, RESULT_OK);
        } catch (SyntaxException e) {
            callback.onEvaluate(expr, e.message + ";" + e.position, RESULT_ERROR_WITH_INDEX);
        } catch (Exception e2) {
        }
    }

    public void setBase(String expr, Base base, EvaluateCallback callback) {
        try {
            String result = this.mSolver.getBaseModule().setBase(expr, base);
            if (result.isEmpty()) {
                callback.onEvaluate(expr, result, INPUT_EMPTY);
            } else {
                callback.onEvaluate(expr, result, RESULT_OK);
            }
        } catch (SyntaxException e) {
        }
    }

    public Solver getSolver() {
        return this.mSolver;
    }
}
