package edu.hws.jcm.data;

import com.example.duy.calculator.math_eval.Constants;

public class ConditionalExpression implements ExpressionCommand {
    private ExpressionProgram falseCase;
    private ExpressionProgram trueCase;

    public ConditionalExpression(ExpressionProgram trueCase, ExpressionProgram falseCase) {
        this.trueCase = trueCase;
        this.falseCase = falseCase;
    }

    public void apply(StackOfDouble stack, Cases cases) {
        double test = stack.pop();
        if (cases != null) {
            cases.addCase((int) test);
        }
        if (test != 0.0d) {
            stack.push(this.trueCase.getValueWithCases(cases));
        } else if (this.falseCase != null) {
            stack.push(this.falseCase.getValueWithCases(cases));
        } else {
            stack.push(Double.NaN);
        }
    }

    public void compileDerivative(ExpressionProgram prog, int myIndex, ExpressionProgram deriv, Variable wrt) {
        prog.copyExpression(myIndex - 1, deriv);
        deriv.addCommandObject(new ConditionalExpression((ExpressionProgram) this.trueCase.derivative(wrt), this.falseCase == null ? null : (ExpressionProgram) this.falseCase.derivative(wrt)));
    }

    public int extent(ExpressionProgram prog, int myIndex) {
        return prog.extent(myIndex - 1) + 1;
    }

    public boolean dependsOn(Variable x) {
        return this.trueCase.dependsOn(x) || (this.falseCase != null && this.falseCase.dependsOn(x));
    }

    public void appendOutputString(ExpressionProgram prog, int myIndex, StringBuffer buffer) {
        buffer.append(Constants.LEFT_PAREN);
        prog.appendOutputString(myIndex - 1, buffer);
        buffer.append(") ? (");
        buffer.append(this.trueCase.toString());
        buffer.append(Constants.RIGHT_PAREN);
        if (this.falseCase != null) {
            buffer.append(" : (");
            buffer.append(this.falseCase.toString());
            buffer.append(Constants.RIGHT_PAREN);
        }
    }
}
