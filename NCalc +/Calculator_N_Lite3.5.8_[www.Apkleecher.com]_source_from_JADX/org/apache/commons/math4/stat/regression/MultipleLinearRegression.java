package org.apache.commons.math4.stat.regression;

public interface MultipleLinearRegression {
    double estimateRegressandVariance();

    double[] estimateRegressionParameters();

    double[] estimateRegressionParametersStandardErrors();

    double[][] estimateRegressionParametersVariance();

    double[] estimateResiduals();
}
