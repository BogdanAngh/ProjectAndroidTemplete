package edu.hws.jcm.functions;

import edu.hws.jcm.data.Cases;
import edu.hws.jcm.data.Expression;
import edu.hws.jcm.data.Function;
import edu.hws.jcm.data.MathObject;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.StackOfDouble;
import edu.hws.jcm.data.Variable;
import edu.jas.ps.UnivPowerSeriesRing;

public class ExpressionFunction extends FunctionParserExtension {
    private Expression definition;
    private Variable[] params;

    public ExpressionFunction(String name, String def) {
        this(name, new String[]{UnivPowerSeriesRing.DEFAULT_NAME}, def, null);
    }

    public ExpressionFunction(String name, String[] paramNames, String def, Parser parser) {
        setName(name);
        if (paramNames == null) {
            this.params = new Variable[0];
        } else {
            this.params = new Variable[paramNames.length];
            for (int i = 0; i < paramNames.length; i++) {
                this.params[i] = new Variable(paramNames[i]);
            }
        }
        redefine(def, parser);
        if (parser != null && name != null) {
            parser.add(this);
        }
    }

    public ExpressionFunction(String name, Variable[] params, Expression definition) {
        setName(name);
        if (params == null) {
            params = new Variable[0];
        }
        this.params = params;
        this.definition = definition;
    }

    private ExpressionFunction() {
    }

    public void redefine(String def) {
        redefine(def, null);
    }

    public void redefine(String def, Parser parser) {
        if (parser == null) {
            parser = new Parser();
        } else {
            parser = new Parser(parser);
        }
        for (MathObject add : this.params) {
            parser.add(add);
        }
        this.definition = parser.parse(def);
    }

    public String getDefinitionString() {
        return this.definition.toString();
    }

    public String toString() {
        StringBuffer b = new StringBuffer();
        b.append(this.name == null ? "unnamed function of (" : "function " + this.name + "(");
        for (int i = 0; i < this.params.length; i++) {
            b.append(this.params[i].getName());
            if (i < this.params.length - 1) {
                b.append(",");
            }
        }
        b.append(") given by ");
        b.append(this.definition.toString());
        return b.toString();
    }

    public int getArity() {
        return this.params.length;
    }

    public double getVal(double[] arguments) {
        return getValueWithCases(arguments, null);
    }

    public double getValueWithCases(double[] arguments, Cases cases) {
        double valueWithCases;
        synchronized (this.params) {
            if (arguments == null) {
                if (this.params.length > 0) {
                    throw new IllegalArgumentException("Internal Error:  Number of arguments provided to function does not match its arity.");
                }
                valueWithCases = this.definition.getValueWithCases(cases);
            } else if (arguments.length != this.params.length) {
                throw new IllegalArgumentException("Internal Error:  Number of arguments provided to function does not match its arity.");
            } else {
                for (int i = 0; i < this.params.length; i++) {
                    this.params[i].setVal(arguments[i]);
                }
                valueWithCases = this.definition.getValueWithCases(cases);
            }
        }
        return valueWithCases;
    }

    public Function derivative(int wrt) {
        if (wrt <= 0 || wrt > getArity()) {
            throw new IllegalArgumentException("Internal Error:  Attempt to take the derivative of a function of " + getArity() + " variables with respect to argument number " + wrt + ".");
        }
        ExpressionFunction deriv = new ExpressionFunction();
        if (this.name != null) {
            if (getArity() == 1) {
                deriv.setName(getName() + "'");
            } else {
                deriv.setName("D" + wrt + "[" + getName() + "]");
            }
        }
        deriv.params = this.params;
        deriv.definition = this.definition.derivative(this.params[wrt - 1]);
        return deriv;
    }

    public Function derivative(Variable x) {
        ExpressionFunction deriv = new ExpressionFunction();
        if (this.name != null) {
            deriv.setName("D" + x.getName() + "[" + getName() + "]");
        }
        deriv.params = this.params;
        deriv.definition = this.definition.derivative(x);
        return deriv;
    }

    public boolean dependsOn(Variable x) {
        return this.definition.dependsOn(x);
    }

    public void apply(StackOfDouble stack, Cases cases) {
        for (int i = getArity() - 1; i >= 0; i--) {
            this.params[i].setVal(stack.pop());
        }
        stack.push(this.definition.getValueWithCases(cases));
    }
}
