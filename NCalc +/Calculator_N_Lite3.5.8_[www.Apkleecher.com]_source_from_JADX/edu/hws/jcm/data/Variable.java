package edu.hws.jcm.data;

public class Variable extends Constant {
    public Variable() {
        super(0.0d);
    }

    public Variable(String name) {
        super(name, 0.0d);
    }

    public Variable(String name, double value) {
        super(name, value);
    }

    public void setVal(double value) {
        this.value = value;
    }

    public Expression derivative(Variable wrt) {
        return new Constant(wrt == this ? 1.0d : 0.0d);
    }

    public void compileDerivative(ExpressionProgram prog, int myIndex, ExpressionProgram deriv, Variable wrt) {
        deriv.addConstant(wrt == this ? 1.0d : 0.0d);
    }

    public boolean dependsOn(Variable x) {
        return this == x;
    }

    public String toString() {
        String s = getName();
        return s == null ? "(unnamed variable)" : s;
    }
}
