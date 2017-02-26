package com.example.duy.calculator;

import android.support.multidex.MultiDexApplication;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Tokenizer;

public class CalcApplication extends MultiDexApplication {
    public BigEvaluator mSolver;
    public Tokenizer mTokenizer;

    public void onCreate() {
        super.onCreate();
        settingCrashHandle();
        this.mTokenizer = new Tokenizer(this);
        this.mSolver = new BigEvaluator(this.mTokenizer);
    }

    private void settingCrashHandle() {
    }

    public BigEvaluator getEvaluator() {
        return this.mSolver;
    }

    public void setSolver(BigEvaluator mSolver) {
        this.mSolver = mSolver;
    }

    public Tokenizer getTokenizer() {
        return this.mTokenizer;
    }

    public void setmTokenizer(Tokenizer mTokenizer) {
        this.mTokenizer = mTokenizer;
    }
}
