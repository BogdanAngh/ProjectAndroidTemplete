package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;

public class FactorExpressionItem implements ExprInput {
    private String input;

    public FactorExpressionItem(String input) {
        this.input = input;
    }

    public boolean isError(BigEvaluator evaluator) {
        return false;
    }

    public String getInput() {
        return "Factor(" + this.input + Constants.RIGHT_PAREN;
    }

    public String toString() {
        return this.input;
    }
}
