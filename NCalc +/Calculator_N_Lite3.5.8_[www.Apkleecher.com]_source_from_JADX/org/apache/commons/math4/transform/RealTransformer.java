package org.apache.commons.math4.transform;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NonMonotonicSequenceException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;

public interface RealTransformer {
    double[] transform(UnivariateFunction univariateFunction, double d, double d2, int i, TransformType transformType) throws NonMonotonicSequenceException, NotStrictlyPositiveException, MathIllegalArgumentException;

    double[] transform(double[] dArr, TransformType transformType) throws MathIllegalArgumentException;
}
