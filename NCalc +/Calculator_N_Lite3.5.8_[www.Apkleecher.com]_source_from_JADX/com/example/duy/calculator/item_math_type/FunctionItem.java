package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.FormatExpression;
import io.github.kexanie.library.BuildConfig;

public class FunctionItem implements ExprInput {
    private String leftExpr;
    private String rightExpr;

    public FunctionItem(String rightExpr) {
        this.leftExpr = Constants.Y;
        this.rightExpr = BuildConfig.FLAVOR;
        this.rightExpr = FormatExpression.appendParenthesis(rightExpr);
    }

    public FunctionItem(String leftExpr, String rightExpr) {
        this.leftExpr = Constants.Y;
        this.rightExpr = BuildConfig.FLAVOR;
        this.leftExpr = FormatExpression.appendParenthesis(leftExpr);
        this.rightExpr = FormatExpression.appendParenthesis(rightExpr);
    }

    public String getLeftExpr() {
        return this.leftExpr;
    }

    public void setLeftExpr(String leftExpr) {
        this.leftExpr = leftExpr;
    }

    public String getRightExpr() {
        return this.rightExpr;
    }

    public void setRightExpr(String rightExpr) {
        this.rightExpr = rightExpr;
    }

    public boolean isError(BigEvaluator evaluator) {
        if (evaluator.isSyntaxError(this.leftExpr)) {
            return true;
        }
        return evaluator.isSyntaxError(this.rightExpr);
    }

    public String getInput() {
        return toString();
    }

    public String toString() {
        return this.leftExpr + Character.toString(Constants.EQUAL_UNICODE) + this.rightExpr;
    }
}
