package com.example.duy.calculator;

import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import com.example.duy.calculator.define.VariableEntry;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Tokenizer;
import java.util.ArrayList;

public abstract class AbstractCalculator extends AbstractNavDrawer implements OnSharedPreferenceChangeListener, Calculator {
    private boolean debug;
    public BigEvaluator mEvaluator;
    public Tokenizer mTokenizer;

    public abstract String getTextClean();

    public abstract void insertOperator(String str);

    public abstract void insertText(String str);

    public abstract void setTextDisplay(String str);

    public AbstractCalculator() {
        this.debug = false;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalcApplication application = (CalcApplication) getApplicationContext();
        this.mEvaluator = application.getEvaluator();
        this.mTokenizer = application.getTokenizer();
        ArrayList<VariableEntry> var = this.database.getAllVariable();
        for (int index = 0; index < var.size(); index++) {
            this.mEvaluator.define(((VariableEntry) var.get(index)).getName(), Double.parseDouble(((VariableEntry) var.get(index)).getValue()));
        }
    }
}
