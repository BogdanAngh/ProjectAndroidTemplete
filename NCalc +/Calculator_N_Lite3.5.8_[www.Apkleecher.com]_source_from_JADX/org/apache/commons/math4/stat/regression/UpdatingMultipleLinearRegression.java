package org.apache.commons.math4.stat.regression;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NoDataException;

public interface UpdatingMultipleLinearRegression {
    void addObservation(double[] dArr, double d) throws ModelSpecificationException;

    void addObservations(double[][] dArr, double[] dArr2) throws ModelSpecificationException;

    void clear();

    long getN();

    boolean hasIntercept();

    RegressionResults regress() throws ModelSpecificationException, NoDataException;

    RegressionResults regress(int[] iArr) throws ModelSpecificationException, MathIllegalArgumentException;
}
