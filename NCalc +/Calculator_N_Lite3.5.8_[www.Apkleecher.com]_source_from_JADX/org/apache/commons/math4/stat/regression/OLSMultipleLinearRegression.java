package org.apache.commons.math4.stat.regression;

import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.LUDecomposition;
import org.apache.commons.math4.linear.QRDecomposition;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.stat.StatUtils;
import org.apache.commons.math4.stat.descriptive.moment.SecondMoment;

public class OLSMultipleLinearRegression extends AbstractMultipleLinearRegression {
    private QRDecomposition qr;
    private final double threshold;

    public OLSMultipleLinearRegression() {
        this(0.0d);
    }

    public OLSMultipleLinearRegression(double threshold) {
        this.qr = null;
        this.threshold = threshold;
    }

    public void newSampleData(double[] y, double[][] x) throws MathIllegalArgumentException {
        validateSampleData(x, y);
        newYSampleData(y);
        newXSampleData(x);
    }

    public void newSampleData(double[] data, int nobs, int nvars) {
        super.newSampleData(data, nobs, nvars);
        this.qr = new QRDecomposition(getX(), this.threshold);
    }

    public RealMatrix calculateHat() {
        RealMatrix Q = this.qr.getQ();
        int p = this.qr.getR().getColumnDimension();
        int n = Q.getColumnDimension();
        Array2DRowRealMatrix augI = new Array2DRowRealMatrix(n, n);
        double[][] augIData = augI.getDataRef();
        int i = 0;
        while (i < n) {
            for (int j = 0; j < n; j++) {
                if (i != j || i >= p) {
                    augIData[i][j] = 0.0d;
                } else {
                    augIData[i][j] = 1.0d;
                }
            }
            i++;
        }
        return Q.multiply(augI).multiply(Q.transpose());
    }

    public double calculateTotalSumOfSquares() {
        if (isNoIntercept()) {
            return StatUtils.sumSq(getY().toArray());
        }
        return new SecondMoment().evaluate(getY().toArray());
    }

    public double calculateResidualSumOfSquares() {
        RealVector residuals = calculateResiduals();
        return residuals.dotProduct(residuals);
    }

    public double calculateRSquared() {
        return 1.0d - (calculateResidualSumOfSquares() / calculateTotalSumOfSquares());
    }

    public double calculateAdjustedRSquared() {
        double n = (double) getX().getRowDimension();
        if (isNoIntercept()) {
            return 1.0d - ((1.0d - calculateRSquared()) * (n / (n - ((double) getX().getColumnDimension()))));
        }
        return 1.0d - ((calculateResidualSumOfSquares() * (n - 1.0d)) / (calculateTotalSumOfSquares() * (n - ((double) getX().getColumnDimension()))));
    }

    protected void newXSampleData(double[][] x) {
        super.newXSampleData(x);
        this.qr = new QRDecomposition(getX(), this.threshold);
    }

    protected RealVector calculateBeta() {
        return this.qr.getSolver().solve(getY());
    }

    protected RealMatrix calculateBetaVariance() {
        int p = getX().getColumnDimension();
        RealMatrix Rinv = new LUDecomposition(this.qr.getR().getSubMatrix(0, p - 1, 0, p - 1)).getSolver().getInverse();
        return Rinv.multiply(Rinv.transpose());
    }
}
