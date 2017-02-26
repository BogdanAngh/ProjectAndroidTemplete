package edu.hws.jcm.functions;

import com.example.duy.calculator.math_eval.Constants;
import edu.hws.jcm.data.Cases;
import edu.hws.jcm.data.ExpressionCommand;
import edu.hws.jcm.data.ExpressionProgram;
import edu.hws.jcm.data.Function;
import edu.hws.jcm.data.ParseError;
import edu.hws.jcm.data.Parser;
import edu.hws.jcm.data.ParserContext;
import edu.hws.jcm.data.ParserExtension;
import edu.hws.jcm.data.StackOfDouble;
import edu.hws.jcm.data.Variable;
import org.apache.commons.math4.geometry.VectorFormat;
import org.matheclipse.core.interfaces.IExpr;

public abstract class FunctionParserExtension implements Function, ParserExtension, ExpressionCommand {
    protected String name;
    private boolean parensCanBeOptional;

    public void setParensCanBeOptional(boolean b) {
        this.parensCanBeOptional = b;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void doParse(Parser parser, ParserContext context) {
        int tok = context.next();
        String open = context.tokenString;
        if (tok == 4 && (open.equals("(") || ((open.equals("[") && (context.options & 8) != 0) || (open.equals(VectorFormat.DEFAULT_PREFIX) && (context.options & 16) != 0)))) {
            String close = open.equals("(") ? ")" : open.equals("[") ? "]" : VectorFormat.DEFAULT_SUFFIX;
            for (int i = 0; i < getArity(); i++) {
                if (parser.parseExpression(context)) {
                    throw new ParseError("An argument of a function cannot be a logical-valued expression.", context);
                }
                tok = context.next();
                if (i == getArity() - 1) {
                    if (tok == 4 && context.tokenString.equals(",")) {
                        throw new ParseError("Too many parameters for function \"" + getName() + "\".", context);
                    } else if (tok != 4 || !context.tokenString.equals(close)) {
                        throw new ParseError("Expected a \"" + close + "\" at the end of the paramter list for function \"" + getName() + "\".", context);
                    }
                } else if (tok != 4 || !context.tokenString.equals(",")) {
                    throw new ParseError("Exprected a comma followed by another argument for function \"" + getName() + "\".", context);
                }
            }
        } else if (getArity() != 1 || (context.options & IExpr.PATTERNID) == 0 || !this.parensCanBeOptional) {
            throw new ParseError("Parentheses required around parameter list of function \"" + getName() + "\".", context);
        } else if (parser.parseTerm(context)) {
            throw new ParseError("The argument of a function must be a numerical expression.", context);
        }
        context.prog.addCommandObject(this);
    }

    public void apply(StackOfDouble stack, Cases cases) {
        double[] d = new double[getArity()];
        for (int i = getArity() - 1; i >= 0; i--) {
            d[i] = stack.pop();
        }
        stack.push(getVal(d));
    }

    public void compileDerivative(ExpressionProgram prog, int myIndex, ExpressionProgram deriv, Variable wrt) {
        int i;
        int[] opIndex = new int[getArity()];
        int size = 1;
        for (i = 0; i < getArity(); i++) {
            opIndex[(getArity() - i) - 1] = myIndex - size;
            if (i < getArity() - 1) {
                size += prog.extent(myIndex - size);
            }
        }
        boolean output = false;
        if (dependsOn(wrt)) {
            output = true;
            for (int copyExpression : opIndex) {
                prog.copyExpression(copyExpression, deriv);
            }
            deriv.addCommandObject((FunctionParserExtension) derivative(wrt));
        }
        for (i = 0; i < getArity(); i++) {
            if (prog.dependsOn(opIndex[i], wrt)) {
                for (int copyExpression2 : opIndex) {
                    prog.copyExpression(copyExpression2, deriv);
                }
                deriv.addCommandObject((FunctionParserExtension) derivative(i + 1));
                prog.compileDerivative(opIndex[i], deriv, wrt);
                deriv.addCommand(-3);
                if (output) {
                    deriv.addCommand(-1);
                }
                output = true;
            }
        }
        if (!output) {
            prog.addConstant(0.0d);
        }
    }

    public int extent(ExpressionProgram prog, int myIndex) {
        int size = 1;
        for (int i = 0; i < getArity(); i++) {
            size += prog.extent(myIndex - size);
        }
        return size;
    }

    public void appendOutputString(ExpressionProgram prog, int myIndex, StringBuffer buffer) {
        int i;
        int[] opIndex = new int[getArity()];
        int size = 1;
        for (i = 0; i < getArity(); i++) {
            opIndex[(getArity() - i) - 1] = myIndex - size;
            if (i < getArity() - 1) {
                size += prog.extent(myIndex - size);
            }
        }
        String name = getName();
        if (name == null) {
            name = "(unnamed function)";
        }
        buffer.append(name);
        buffer.append(Constants.LEFT_PAREN);
        for (i = 0; i < getArity(); i++) {
            prog.appendOutputString(opIndex[i], buffer);
            if (i < getArity() - 1) {
                buffer.append(", ");
            }
        }
        buffer.append(Constants.RIGHT_PAREN);
    }
}
