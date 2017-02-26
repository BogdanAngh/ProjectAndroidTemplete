package org.apache.commons.math4.distribution;

import com.example.duy.calculator.geom2d.util.Angle2D;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.EigenDecomposition;
import org.apache.commons.math4.linear.NonPositiveDefiniteMatrixException;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.linear.SingularMatrixException;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.Well19937c;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class MultivariateNormalDistribution extends AbstractMultivariateRealDistribution {
    private final RealMatrix covarianceMatrix;
    private final double covarianceMatrixDeterminant;
    private final RealMatrix covarianceMatrixInverse;
    private final double[] means;
    private final RealMatrix samplingMatrix;

    public MultivariateNormalDistribution(double[] means, double[][] covariances) throws SingularMatrixException, DimensionMismatchException, NonPositiveDefiniteMatrixException {
        this(new Well19937c(), means, covariances);
    }

    public MultivariateNormalDistribution(RandomGenerator rng, double[] means, double[][] covariances) throws SingularMatrixException, DimensionMismatchException, NonPositiveDefiniteMatrixException {
        super(rng, means.length);
        int dim = means.length;
        if (covariances.length != dim) {
            throw new DimensionMismatchException(covariances.length, dim);
        }
        int i;
        for (i = 0; i < dim; i++) {
            if (dim != covariances[i].length) {
                throw new DimensionMismatchException(covariances[i].length, dim);
            }
        }
        this.means = MathArrays.copyOf(means);
        this.covarianceMatrix = new Array2DRowRealMatrix(covariances);
        EigenDecomposition covMatDec = new EigenDecomposition(this.covarianceMatrix);
        this.covarianceMatrixInverse = covMatDec.getSolver().getInverse();
        this.covarianceMatrixDeterminant = covMatDec.getDeterminant();
        double[] covMatEigenvalues = covMatDec.getRealEigenvalues();
        for (i = 0; i < covMatEigenvalues.length; i++) {
            if (covMatEigenvalues[i] < 0.0d) {
                throw new NonPositiveDefiniteMatrixException(covMatEigenvalues[i], i, 0.0d);
            }
        }
        Array2DRowRealMatrix covMatEigenvectors = new Array2DRowRealMatrix(dim, dim);
        for (int v = 0; v < dim; v++) {
            covMatEigenvectors.setColumn(v, covMatDec.getEigenvector(v).toArray());
        }
        RealMatrix tmpMatrix = covMatEigenvectors.transpose();
        for (int row = 0; row < dim; row++) {
            double factor = FastMath.sqrt(covMatEigenvalues[row]);
            for (int col = 0; col < dim; col++) {
                tmpMatrix.multiplyEntry(row, col, factor);
            }
        }
        this.samplingMatrix = covMatEigenvectors.multiply(tmpMatrix);
    }

    public double[] getMeans() {
        return MathArrays.copyOf(this.means);
    }

    public RealMatrix getCovariances() {
        return this.covarianceMatrix.copy();
    }

    public double density(double[] vals) throws DimensionMismatchException {
        int dim = getDimension();
        if (vals.length == dim) {
            return (FastMath.pow((double) Angle2D.M_2PI, ((double) dim) * -0.5d) * FastMath.pow(this.covarianceMatrixDeterminant, -0.5d)) * getExponentTerm(vals);
        }
        throw new DimensionMismatchException(vals.length, dim);
    }

    public double[] getStandardDeviations() {
        int dim = getDimension();
        double[] std = new double[dim];
        double[][] s = this.covarianceMatrix.getData();
        for (int i = 0; i < dim; i++) {
            std[i] = FastMath.sqrt(s[i][i]);
        }
        return std;
    }

    public double[] sample() {
        int i;
        int dim = getDimension();
        double[] normalVals = new double[dim];
        for (i = 0; i < dim; i++) {
            normalVals[i] = this.random.nextGaussian();
        }
        double[] vals = this.samplingMatrix.operate(normalVals);
        for (i = 0; i < dim; i++) {
            vals[i] = vals[i] + this.means[i];
        }
        return vals;
    }

    private double getExponentTerm(double[] values) {
        int i;
        double[] centered = new double[values.length];
        for (i = 0; i < centered.length; i++) {
            centered[i] = values[i] - getMeans()[i];
        }
        double[] preMultiplied = this.covarianceMatrixInverse.preMultiply(centered);
        double sum = 0.0d;
        for (i = 0; i < preMultiplied.length; i++) {
            sum += preMultiplied[i] * centered[i];
        }
        return FastMath.exp(-0.5d * sum);
    }
}
