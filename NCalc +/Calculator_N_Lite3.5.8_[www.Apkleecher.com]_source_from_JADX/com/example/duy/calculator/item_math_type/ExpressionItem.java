package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.FormatExpression;

public class ExpressionItem implements ExprInput {
    private String expr;

    public ExpressionItem(String expr) {
        this.expr = FormatExpression.appendParenthesis(expr);
    }

    public String getExpr() {
        return this.expr;
    }

    public void setExpr(String expr) {
        this.expr = expr;
    }

    public boolean isError(BigEvaluator evaluator) {
        return evaluator.isSyntaxError(this.expr);
    }

    public String getInput() {
        return toString();
    }

    public String toString() {
        return this.expr;
    }
}
