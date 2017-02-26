package com.example.duy.calculator.item_math_type;

import android.util.Log;
import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;
import com.example.duy.calculator.math_eval.FormatExpression;
import edu.jas.ps.UnivPowerSeriesRing;

public class PrimitiveItem implements ExprInput {
    private static final String TAG = "PrimitiveItem";
    private String input;
    private String var;

    public PrimitiveItem(String input, String var) {
        this.var = UnivPowerSeriesRing.DEFAULT_NAME;
        this.input = FormatExpression.appendParenthesis(input);
        this.var = var;
    }

    public PrimitiveItem(String input) {
        this.var = UnivPowerSeriesRing.DEFAULT_NAME;
        this.input = FormatExpression.appendParenthesis(input);
    }

    public boolean isError(BigEvaluator evaluator) {
        return evaluator.isSyntaxError(this.input);
    }

    public String getInput() {
        String res = "Integrate(" + this.input + "," + this.var + Constants.RIGHT_PAREN;
        Log.d(TAG, "getInput: res");
        return res;
    }
}
