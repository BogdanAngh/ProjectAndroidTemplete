package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;

public interface ExprInput {
    String getInput();

    boolean isError(BigEvaluator bigEvaluator);
}
