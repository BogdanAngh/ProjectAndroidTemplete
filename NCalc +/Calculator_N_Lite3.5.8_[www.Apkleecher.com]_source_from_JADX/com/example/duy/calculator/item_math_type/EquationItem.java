package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;

public class EquationItem extends FunctionItem {
    private String mInput;

    public EquationItem(String left) {
        super(left, Constants.ZERO);
    }

    public EquationItem(String left, String right) {
        super(left, right);
    }

    public boolean isError(BigEvaluator evaluator) {
        return super.isError(evaluator);
    }
}
