package edu.hws.jcm.data;

import java.io.Serializable;

public interface ExpressionCommand extends Serializable {
    void appendOutputString(ExpressionProgram expressionProgram, int i, StringBuffer stringBuffer);

    void apply(StackOfDouble stackOfDouble, Cases cases);

    void compileDerivative(ExpressionProgram expressionProgram, int i, ExpressionProgram expressionProgram2, Variable variable);

    boolean dependsOn(Variable variable);

    int extent(ExpressionProgram expressionProgram, int i);
}
