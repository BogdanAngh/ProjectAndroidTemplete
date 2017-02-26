package edu.hws.jcm.data;

public class Constant implements Expression, ExpressionCommand, MathObject {
    private String name;
    protected double value;

    public Constant(double value) {
        this.value = value;
    }

    public Constant(String name, double value) {
        setName(name);
        this.value = value;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getVal() {
        return this.value;
    }

    public double getValueWithCases(Cases cases) {
        return this.value;
    }

    public Expression derivative(Variable wrt) {
        return new Constant(0.0d);
    }

    public String toString() {
        if (this.name == null) {
            return NumUtils.realToString(this.value);
        }
        return this.name;
    }

    public void apply(StackOfDouble stack, Cases cases) {
        stack.push(getVal());
    }

    public void compileDerivative(ExpressionProgram prog, int myIndex, ExpressionProgram deriv, Variable wrt) {
        deriv.addConstant(0.0d);
    }

    public int extent(ExpressionProgram prog, int myIndex) {
        return 1;
    }

    public boolean dependsOn(Variable x) {
        return false;
    }

    public void appendOutputString(ExpressionProgram prog, int myIndex, StringBuffer buffer) {
        buffer.append(toString());
    }
}
