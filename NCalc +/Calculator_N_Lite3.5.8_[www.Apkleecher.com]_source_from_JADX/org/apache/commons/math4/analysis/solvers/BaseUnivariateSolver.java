package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;

public interface BaseUnivariateSolver<FUNC extends UnivariateFunction> {
    double getAbsoluteAccuracy();

    int getEvaluations();

    double getFunctionValueAccuracy();

    int getMaxEvaluations();

    double getRelativeAccuracy();

    double solve(int i, FUNC func, double d);

    double solve(int i, FUNC func, double d, double d2) throws MathIllegalArgumentException, TooManyEvaluationsException;

    double solve(int i, FUNC func, double d, double d2, double d3) throws MathIllegalArgumentException, TooManyEvaluationsException;
}
