package edu.hws.jcm.data;

public class SimpleFunction implements Function {
    private Expression e;
    private String name;
    private double[] save;
    private Variable[] v;

    public SimpleFunction(Expression e, Variable v) {
        this(e, new Variable[]{v});
    }

    public SimpleFunction(Expression e, Variable[] v) {
        this.e = e;
        this.v = v;
        this.save = new double[v.length];
    }

    public int getArity() {
        return this.v.length;
    }

    public double getVal(double[] arguments) {
        return getValueWithCases(arguments, null);
    }

    public double getValueWithCases(double[] arguments, Cases cases) {
        int i;
        for (i = 0; i < this.v.length; i++) {
            this.save[i] = this.v[i].getVal();
            this.v[i].setVal(arguments[i]);
        }
        double val = this.e.getValueWithCases(cases);
        for (i = 0; i < this.v.length; i++) {
            this.v[i].setVal(this.save[i]);
        }
        return val;
    }

    public Function derivative(int wrt) {
        if (wrt >= 1 && wrt <= this.v.length) {
            return new SimpleFunction(this.e.derivative(this.v[wrt - 1]), this.v);
        }
        throw new IllegalArgumentException("Internal Error.  Function does not have an argument number  " + wrt);
    }

    public Function derivative(Variable x) {
        for (Variable variable : this.v) {
            if (x == variable) {
                return new SimpleFunction(new Constant(0.0d), this.v);
            }
        }
        return new SimpleFunction(this.e.derivative(x), this.v);
    }

    public boolean dependsOn(Variable x) {
        for (Variable variable : this.v) {
            if (x == variable) {
                return false;
            }
        }
        return this.e.dependsOn(x);
    }
}
