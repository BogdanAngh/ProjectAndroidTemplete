package org.matheclipse.core.builtin.function;

import com.google.common.base.Predicate;
import org.matheclipse.core.eval.EvalEngine;
import org.matheclipse.core.eval.exception.Validate;
import org.matheclipse.core.eval.interfaces.AbstractCoreFunctionEvaluator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IAST;
import org.matheclipse.core.interfaces.IExpr;
import org.matheclipse.core.interfaces.INumber;
import org.matheclipse.core.interfaces.ISignedNumber;
import org.matheclipse.core.interfaces.ISymbol;
import org.matheclipse.parser.client.SyntaxError;

public class NumericQ extends AbstractCoreFunctionEvaluator implements Predicate<IExpr> {
    public boolean apply(IExpr arg) {
        return arg.isNumericFunction();
    }

    public IExpr evaluate(IAST ast, EvalEngine engine) {
        Validate.checkSize(ast, 2);
        return F.bool(apply(F.eval(ast.arg1())));
    }

    public void setUp(ISymbol symbol) throws SyntaxError {
    }

    @Deprecated
    public static ISignedNumber getSignedNumberNumericQ(IExpr arg1) {
        if (arg1.isSignedNumber()) {
            return (ISignedNumber) arg1;
        }
        if (arg1.isNumber()) {
            return null;
        }
        if (arg1.isNumericFunction()) {
            IExpr result = F.evaln(arg1);
            if (result.isSignedNumber()) {
                return (ISignedNumber) result;
            }
        }
        return null;
    }

    @Deprecated
    public static INumber getNumberNumericQ(IExpr arg1) {
        if (arg1.isNumber()) {
            return (INumber) arg1;
        }
        if (arg1.isNumericFunction()) {
            IExpr result = F.evaln(arg1);
            if (result.isNumber()) {
                return (INumber) result;
            }
        }
        return null;
    }
}
