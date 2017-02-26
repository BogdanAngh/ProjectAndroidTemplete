package com.example.duy.calculator.graph;

import edu.hws.jcm.data.Constant;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.Variable;
import edu.hws.jcm.functions.ExpressionFunction;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public class MathGraph {
    public static boolean isValid(String fun, String[] var) {
        Parser parParser = new Parser(1598);
        for (String v : var) {
            parParser.add(new Variable(v));
        }
        setUpParser(parParser);
        try {
            parParser.parse(fun);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public static boolean[] isValid(String[] fun, String[] var) {
        Parser parParser = new Parser(1598);
        for (String v : var) {
            parParser.add(new Variable(v));
        }
        setUpParser(parParser);
        boolean[] b = new boolean[fun.length];
        for (int i = 0; i < fun.length; i++) {
            try {
                parParser.parse(fun[i]);
                b[i] = true;
            } catch (Exception e) {
                b[i] = false;
            }
        }
        return b;
    }

    public static void setUpParser(Parser p) {
        p.add(new ExpressionFunction("sinh", "(e^x-e^(-x))/2"));
        p.add(new ExpressionFunction("cosh", "(e^x+e^(-x))/2"));
        p.add(new ExpressionFunction("tanh", "(e^x-e^(-x))/(e^x+e^(-x))"));
        p.add(new ExpressionFunction("coth", "(e^x+e^(-x))/(e^x-e^(-x))"));
        p.add(new ExpressionFunction("csch", "1/(e^x-e^(-x))"));
        p.add(new ExpressionFunction("sech", "1/(e^x+e^(-x))"));
        p.add(new ExpressionFunction("arcsinh", "ln(x+sqrt(x^2+1))"));
        p.add(new ExpressionFunction("arccosh", "ln(x+sqrt(x^2-1))"));
        p.add(new ExpressionFunction("arctanh", "ln((1+x)/(ln(1-x)))/2"));
        p.add(new ExpressionFunction("arccsch", "ln(sqrt(1+x^(-2))+1/x)"));
        p.add(new ExpressionFunction("arcsech", "ln(sqrt(x^(-2)-1)+1/x)"));
        p.add(new ExpressionFunction("arccoth", "ln((x+1)/(x-1))/2"));
        p.add(new ExpressionFunction("arccot", "pi/2-arctan(x)"));
        p.add(new ExpressionFunction("arcsec", "arccos(1/x)"));
        p.add(new ExpressionFunction("arccsc", "arcsin(1/x)"));
        p.add(new ExpressionFunction("sign", "x/abs(x)"));
        p.add(new Constant("gol", 1.618033988749895d));
        p.add(new Constant("cc", 2.99792458E8d));
        p.add(new Constant("gr", 6.6742867d * Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, -11.0d)));
        p.add(new Constant("h", 6.6260689633d * Math.pow(BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS, -34.0d)));
        p.add(new Constant("graphView", 9.80665d));
    }
}
