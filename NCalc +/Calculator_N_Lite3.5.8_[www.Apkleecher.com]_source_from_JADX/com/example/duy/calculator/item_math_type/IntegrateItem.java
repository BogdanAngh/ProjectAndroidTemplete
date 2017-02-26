package com.example.duy.calculator.item_math_type;

import com.example.duy.calculator.math_eval.BigEvaluator;
import com.example.duy.calculator.math_eval.Constants;
import edu.jas.ps.UnivPowerSeriesRing;
import org.apache.commons.math4.geometry.VectorFormat;

public class IntegrateItem implements ExprInput {
    private String from;
    private String input;
    private String to;
    private final String var;

    public IntegrateItem(String input, String f, String t) {
        this.var = UnivPowerSeriesRing.DEFAULT_NAME;
        this.from = f;
        this.to = t;
        this.input = input;
    }

    public String getVar() {
        return UnivPowerSeriesRing.DEFAULT_NAME;
    }

    public String getFrom() {
        return this.from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return this.to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public boolean isError(BigEvaluator evaluator) {
        return false;
    }

    public String toString() {
        return "Integrate " + this.input + Constants.FROM + " " + this.from + " " + Constants.TO + " " + this.to;
    }

    public String getInput() {
        return "Integrate(" + this.input + ",{" + UnivPowerSeriesRing.DEFAULT_NAME + "," + this.from + "," + this.to + VectorFormat.DEFAULT_SUFFIX + Constants.RIGHT_PAREN;
    }

    public void setInput(String input) {
        this.input = input;
    }
}
