package org.apache.commons.math4.analysis.integration;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;

public interface UnivariateIntegrator {
    double getAbsoluteAccuracy();

    int getEvaluations();

    int getIterations();

    int getMaximalIterationCount();

    int getMinimalIterationCount();

    double getRelativeAccuracy();

    double integrate(int i, UnivariateFunction univariateFunction, double d, double d2) throws TooManyEvaluationsException, MaxCountExceededException, MathIllegalArgumentException, NullArgumentException;
}
