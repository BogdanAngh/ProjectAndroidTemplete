package org.apache.commons.math4.optim.nonlinear.scalar.noderiv;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.apache.commons.math4.analysis.interpolation.LoessInterpolator;
import org.apache.commons.math4.distribution.AbstractRealDistribution;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.EigenDecomposition;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.OpenMapRealVector;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.optim.nonlinear.scalar.MultivariateOptimizer;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;

public class CMAESOptimizer extends MultivariateOptimizer {
    private RealMatrix B;
    private RealMatrix BD;
    private RealMatrix C;
    private RealMatrix D;
    private double cc;
    private double ccov1;
    private double ccov1Sep;
    private double ccovmu;
    private double ccovmuSep;
    private final int checkFeasableCount;
    private double chiN;
    private double cs;
    private double damps;
    private RealMatrix diagC;
    private RealMatrix diagD;
    private int diagonalOnly;
    private int dimension;
    private double[] fitnessHistory;
    private final boolean generateStatistics;
    private int historySize;
    private double[] inputSigma;
    private final boolean isActiveCMA;
    private boolean isMinimize;
    private int iterations;
    private int lambda;
    private double logMu2;
    private final int maxIterations;
    private int mu;
    private double mueff;
    private double normps;
    private RealMatrix pc;
    private RealMatrix ps;
    private final RandomGenerator random;
    private double sigma;
    private final List<RealMatrix> statisticsDHistory;
    private final List<Double> statisticsFitnessHistory;
    private final List<RealMatrix> statisticsMeanHistory;
    private final List<Double> statisticsSigmaHistory;
    private final double stopFitness;
    private double stopTolFun;
    private double stopTolHistFun;
    private double stopTolUpX;
    private double stopTolX;
    private RealMatrix weights;
    private RealMatrix xmean;

    private static class DoubleIndex implements Comparable<DoubleIndex> {
        private final int index;
        private final double value;

        DoubleIndex(double value, int index) {
            this.value = value;
            this.index = index;
        }

        public int compareTo(DoubleIndex o) {
            return Double.compare(this.value, o.value);
        }

        public boolean equals(Object other) {
            if (this == other) {
                return true;
            }
            if (!(other instanceof DoubleIndex)) {
                return false;
            }
            if (Double.compare(this.value, ((DoubleIndex) other).value) != 0) {
                return false;
            }
            return true;
        }

        public int hashCode() {
            long bits = Double.doubleToLongBits(this.value);
            return (int) (((1438542 ^ (bits >>> 32)) ^ bits) & -1);
        }
    }

    private class FitnessFunction {
        private final boolean isRepairMode;

        public FitnessFunction() {
            this.isRepairMode = true;
        }

        public ValuePenaltyPair value(double[] point) {
            double value;
            double penalty = 0.0d;
            if (this.isRepairMode) {
                double[] repaired = repair(point);
                value = CMAESOptimizer.this.computeObjectiveValue(repaired);
                penalty = penalty(point, repaired);
            } else {
                value = CMAESOptimizer.this.computeObjectiveValue(point);
            }
            if (!CMAESOptimizer.this.isMinimize) {
                value = -value;
            }
            if (!CMAESOptimizer.this.isMinimize) {
                penalty = -penalty;
            }
            return new ValuePenaltyPair(value, penalty);
        }

        public boolean isFeasible(double[] x) {
            double[] lB = CMAESOptimizer.this.getLowerBound();
            double[] uB = CMAESOptimizer.this.getUpperBound();
            int i = 0;
            while (i < x.length) {
                if (x[i] < lB[i] || x[i] > uB[i]) {
                    return false;
                }
                i++;
            }
            return true;
        }

        private double[] repair(double[] x) {
            double[] lB = CMAESOptimizer.this.getLowerBound();
            double[] uB = CMAESOptimizer.this.getUpperBound();
            double[] repaired = new double[x.length];
            for (int i = 0; i < x.length; i++) {
                if (x[i] < lB[i]) {
                    repaired[i] = lB[i];
                } else if (x[i] > uB[i]) {
                    repaired[i] = uB[i];
                } else {
                    repaired[i] = x[i];
                }
            }
            return repaired;
        }

        private double penalty(double[] x, double[] repaired) {
            double penalty = 0.0d;
            for (int i = 0; i < x.length; i++) {
                penalty += FastMath.abs(x[i] - repaired[i]);
            }
            return CMAESOptimizer.this.isMinimize ? penalty : -penalty;
        }
    }

    public static class PopulationSize implements OptimizationData {
        private final int lambda;

        public PopulationSize(int size) throws NotStrictlyPositiveException {
            if (size <= 0) {
                throw new NotStrictlyPositiveException(Integer.valueOf(size));
            }
            this.lambda = size;
        }

        public int getPopulationSize() {
            return this.lambda;
        }
    }

    public static class Sigma implements OptimizationData {
        private final double[] sigma;

        public Sigma(double[] s) throws NotPositiveException {
            for (int i = 0; i < s.length; i++) {
                if (s[i] < 0.0d) {
                    throw new NotPositiveException(Double.valueOf(s[i]));
                }
            }
            this.sigma = (double[]) s.clone();
        }

        public double[] getSigma() {
            return (double[]) this.sigma.clone();
        }
    }

    private static class ValuePenaltyPair {
        private double penalty;
        private double value;

        public ValuePenaltyPair(double value, double penalty) {
            this.value = value;
            this.penalty = penalty;
        }
    }

    public CMAESOptimizer(int maxIterations, double stopFitness, boolean isActiveCMA, int diagonalOnly, int checkFeasableCount, RandomGenerator random, boolean generateStatistics, ConvergenceChecker<PointValuePair> checker) {
        super(checker);
        this.isMinimize = true;
        this.statisticsSigmaHistory = new ArrayList();
        this.statisticsMeanHistory = new ArrayList();
        this.statisticsFitnessHistory = new ArrayList();
        this.statisticsDHistory = new ArrayList();
        this.maxIterations = maxIterations;
        this.stopFitness = stopFitness;
        this.isActiveCMA = isActiveCMA;
        this.diagonalOnly = diagonalOnly;
        this.checkFeasableCount = checkFeasableCount;
        this.random = random;
        this.generateStatistics = generateStatistics;
    }

    public List<Double> getStatisticsSigmaHistory() {
        return this.statisticsSigmaHistory;
    }

    public List<RealMatrix> getStatisticsMeanHistory() {
        return this.statisticsMeanHistory;
    }

    public List<Double> getStatisticsFitnessHistory() {
        return this.statisticsFitnessHistory;
    }

    public List<RealMatrix> getStatisticsDHistory() {
        return this.statisticsDHistory;
    }

    public PointValuePair optimize(OptimizationData... optData) throws TooManyEvaluationsException, DimensionMismatchException {
        return super.optimize(optData);
    }

    protected PointValuePair doOptimize() {
        this.isMinimize = getGoalType().equals(GoalType.MINIMIZE);
        FitnessFunction fitnessFunction = new FitnessFunction();
        double[] guess = getStartPoint();
        this.dimension = guess.length;
        initializeCMA(guess);
        this.iterations = 0;
        ValuePenaltyPair valuePenalty = fitnessFunction.value(guess);
        double bestValue = valuePenalty.value + valuePenalty.penalty;
        push(this.fitnessHistory, bestValue);
        PointValuePair pointValuePair = new PointValuePair(getStartPoint(), this.isMinimize ? bestValue : -bestValue);
        PointValuePair lastResult = null;
        this.iterations = 1;
        loop0:
        while (true) {
            int i = this.iterations;
            int i2 = this.maxIterations;
            if (i > r0) {
                break;
            }
            int i3;
            incrementIterationCount();
            RealMatrix arz = randn1(this.dimension, this.lambda);
            RealMatrix arx = zeros(this.dimension, this.lambda);
            double[] fitness = new double[this.lambda];
            ValuePenaltyPair[] valuePenaltyPairs = new ValuePenaltyPair[this.lambda];
            int k = 0;
            while (k < this.lambda) {
                RealMatrix arxk = null;
                for (i3 = 0; i3 < this.checkFeasableCount + 1; i3++) {
                    if (this.diagonalOnly <= 0) {
                        arxk = this.xmean.add(this.BD.multiply(arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
                    } else {
                        arxk = this.xmean.add(times(this.diagD, arz.getColumnMatrix(k)).scalarMultiply(this.sigma));
                    }
                    if (i3 >= this.checkFeasableCount) {
                        break;
                    }
                    if (fitnessFunction.isFeasible(arxk.getColumn(0))) {
                        break;
                    }
                    arz.setColumn(k, randn(this.dimension));
                }
                copyColumn(arxk, 0, arx, k);
                try {
                    valuePenaltyPairs[k] = fitnessFunction.value(arx.getColumn(k));
                    k++;
                } catch (TooManyEvaluationsException e) {
                }
            }
            double valueRange = valueRange(valuePenaltyPairs);
            for (int iValue = 0; iValue < valuePenaltyPairs.length; iValue++) {
                fitness[iValue] = valuePenaltyPairs[iValue].value + (valuePenaltyPairs[iValue].penalty * valueRange);
            }
            int[] arindex = sortedIndices(fitness);
            RealMatrix xold = this.xmean;
            RealMatrix bestArx = selectColumns(arx, MathArrays.copyOf(arindex, this.mu));
            this.xmean = bestArx.multiply(this.weights);
            RealMatrix bestArz = selectColumns(arz, MathArrays.copyOf(arindex, this.mu));
            boolean hsig = updateEvolutionPaths(bestArz.multiply(this.weights), xold);
            if (this.diagonalOnly <= 0) {
                updateCovariance(hsig, bestArx, arz, arindex, xold);
            } else {
                updateCovarianceDiagonalOnly(hsig, bestArz);
            }
            this.sigma *= FastMath.exp(FastMath.min(1.0d, (((this.normps / this.chiN) - 1.0d) * this.cs) / this.damps));
            double bestFitness = fitness[arindex[0]];
            double worstFitness = fitness[arindex[arindex.length - 1]];
            if (bestValue > bestFitness) {
                bestValue = bestFitness;
                lastResult = optimum;
                pointValuePair = new PointValuePair(fitnessFunction.repair(bestArx.getColumn(0)), this.isMinimize ? bestFitness : -bestFitness);
                if (!(getConvergenceChecker() == null || lastResult == null)) {
                    if (getConvergenceChecker().converged(this.iterations, pointValuePair, lastResult)) {
                        break;
                    }
                }
            }
            if (this.stopFitness != 0.0d) {
                if (bestFitness < (this.isMinimize ? this.stopFitness : -this.stopFitness)) {
                    break;
                }
            }
            double[] sqrtDiagC = sqrt(this.diagC).getColumn(0);
            double[] pcCol = this.pc.getColumn(0);
            i3 = 0;
            while (i3 < this.dimension) {
                if (this.sigma * FastMath.max(FastMath.abs(pcCol[i3]), sqrtDiagC[i3]) <= this.stopTolX) {
                    if (i3 >= this.dimension - 1) {
                        break loop0;
                    }
                    i3++;
                } else {
                    break;
                }
            }
            for (i3 = 0; i3 < this.dimension; i3++) {
                if (this.sigma * sqrtDiagC[i3] > this.stopTolUpX) {
                    break loop0;
                }
            }
            double historyBest = min(this.fitnessHistory);
            double historyWorst = max(this.fitnessHistory);
            if (this.iterations > 2) {
                if (FastMath.max(historyWorst, worstFitness) - FastMath.min(historyBest, bestFitness) < this.stopTolFun) {
                    break;
                }
            }
            i = this.iterations;
            i2 = this.fitnessHistory.length;
            if (i > r0) {
                if (historyWorst - historyBest < this.stopTolHistFun) {
                    break;
                }
            }
            if (max(this.diagD) / min(this.diagD) > 1.0E7d) {
                break;
            }
            if (getConvergenceChecker() != null) {
                PointValuePair current = new PointValuePair(bestArx.getColumn(0), this.isMinimize ? bestFitness : -bestFitness);
                if (lastResult != null) {
                    if (getConvergenceChecker().converged(this.iterations, current, lastResult)) {
                        break;
                    }
                }
                lastResult = current;
            }
            if (bestValue == fitness[arindex[(int) (0.1d + (((double) this.lambda) / 4.0d))]]) {
                this.sigma *= FastMath.exp(0.2d + (this.cs / this.damps));
            }
            if (this.iterations > 2 && FastMath.max(historyWorst, bestFitness) - FastMath.min(historyBest, bestFitness) == 0.0d) {
                this.sigma *= FastMath.exp(0.2d + (this.cs / this.damps));
            }
            push(this.fitnessHistory, bestFitness);
            if (this.generateStatistics) {
                this.statisticsSigmaHistory.add(Double.valueOf(this.sigma));
                this.statisticsFitnessHistory.add(Double.valueOf(bestFitness));
                this.statisticsMeanHistory.add(this.xmean.transpose());
                this.statisticsDHistory.add(this.diagD.transpose().scalarMultiply(100000.0d));
            }
            this.iterations++;
        }
        return optimum;
    }

    protected void parseOptimizationData(OptimizationData... optData) {
        super.parseOptimizationData(optData);
        for (OptimizationData data : optData) {
            if (data instanceof Sigma) {
                this.inputSigma = ((Sigma) data).getSigma();
            } else if (data instanceof PopulationSize) {
                this.lambda = ((PopulationSize) data).getPopulationSize();
            }
        }
        checkParameters();
    }

    private void checkParameters() {
        double[] init = getStartPoint();
        double[] lB = getLowerBound();
        double[] uB = getUpperBound();
        if (this.inputSigma == null) {
            return;
        }
        if (this.inputSigma.length != init.length) {
            throw new DimensionMismatchException(this.inputSigma.length, init.length);
        }
        for (int i = 0; i < init.length; i++) {
            if (this.inputSigma[i] > uB[i] - lB[i]) {
                throw new OutOfRangeException(Double.valueOf(this.inputSigma[i]), Integer.valueOf(0), Double.valueOf(uB[i] - lB[i]));
            }
        }
    }

    private void initializeCMA(double[] guess) {
        if (this.lambda <= 0) {
            throw new NotStrictlyPositiveException(Integer.valueOf(this.lambda));
        }
        int i;
        double[][] sigmaArray = (double[][]) Array.newInstance(Double.TYPE, new int[]{guess.length, 1});
        for (i = 0; i < guess.length; i++) {
            sigmaArray[i][0] = this.inputSigma[i];
        }
        RealMatrix insigma = new Array2DRowRealMatrix(sigmaArray, false);
        this.sigma = max(insigma);
        this.stopTolUpX = 1000.0d * max(insigma);
        this.stopTolX = 1.0E-11d * max(insigma);
        this.stopTolFun = OpenMapRealVector.DEFAULT_ZERO_TOLERANCE;
        this.stopTolHistFun = 1.0E-13d;
        this.mu = this.lambda / 2;
        this.logMu2 = FastMath.log(((double) this.mu) + 0.5d);
        this.weights = log(sequence(1.0d, (double) this.mu, 1.0d)).scalarMultiply(-1.0d).scalarAdd(this.logMu2);
        double sumw = 0.0d;
        double sumwq = 0.0d;
        for (i = 0; i < this.mu; i++) {
            double w = this.weights.getEntry(i, 0);
            sumw += w;
            sumwq += w * w;
        }
        this.weights = this.weights.scalarMultiply(1.0d / sumw);
        this.mueff = (sumw * sumw) / sumwq;
        double d = (double) this.dimension;
        this.cc = (4.0d + (this.mueff / ((double) this.dimension))) / (((double) (this.dimension + 4)) + ((2.0d * this.mueff) / r0));
        this.cs = (this.mueff + 2.0d) / ((((double) this.dimension) + this.mueff) + 3.0d);
        double d2 = (double) (this.dimension + 1);
        this.damps = ((1.0d + (2.0d * FastMath.max(0.0d, FastMath.sqrt((this.mueff - 1.0d) / r0) - 1.0d))) * FastMath.max((double) LoessInterpolator.DEFAULT_BANDWIDTH, 1.0d - (((double) this.dimension) / (AbstractRealDistribution.SOLVER_DEFAULT_ABSOLUTE_ACCURACY + ((double) this.maxIterations))))) + this.cs;
        this.ccov1 = 2.0d / (((((double) this.dimension) + 1.3d) * (((double) this.dimension) + 1.3d)) + this.mueff);
        this.ccovmu = FastMath.min(1.0d - this.ccov1, (2.0d * ((this.mueff - 2.0d) + (1.0d / this.mueff))) / (((double) ((this.dimension + 2) * (this.dimension + 2))) + this.mueff));
        this.ccov1Sep = FastMath.min(1.0d, (this.ccov1 * (((double) this.dimension) + 1.5d)) / 3.0d);
        this.ccovmuSep = FastMath.min(1.0d - this.ccov1, (this.ccovmu * (((double) this.dimension) + 1.5d)) / 3.0d);
        d2 = (double) this.dimension;
        this.chiN = FastMath.sqrt((double) this.dimension) * ((1.0d - (1.0d / (4.0d * ((double) this.dimension)))) + (1.0d / ((21.0d * ((double) this.dimension)) * r0)));
        this.xmean = MatrixUtils.createColumnRealMatrix(guess);
        this.diagD = insigma.scalarMultiply(1.0d / this.sigma);
        this.diagC = square(this.diagD);
        this.pc = zeros(this.dimension, 1);
        this.ps = zeros(this.dimension, 1);
        this.normps = this.ps.getFrobeniusNorm();
        this.B = eye(this.dimension, this.dimension);
        this.D = ones(this.dimension, 1);
        this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
        this.C = this.B.multiply(diag(square(this.D)).multiply(this.B.transpose()));
        this.historySize = ((int) (((double) (this.dimension * 30)) / ((double) this.lambda))) + 10;
        this.fitnessHistory = new double[this.historySize];
        for (i = 0; i < this.historySize; i++) {
            this.fitnessHistory[i] = Double.MAX_VALUE;
        }
    }

    private boolean updateEvolutionPaths(RealMatrix zmean, RealMatrix xold) {
        this.ps = this.ps.scalarMultiply(1.0d - this.cs).add(this.B.multiply(zmean).scalarMultiply(FastMath.sqrt((this.cs * (2.0d - this.cs)) * this.mueff)));
        this.normps = this.ps.getFrobeniusNorm();
        boolean hsig = (this.normps / FastMath.sqrt(1.0d - FastMath.pow(1.0d - this.cs, this.iterations * 2))) / this.chiN < 1.4d + (2.0d / (((double) this.dimension) + 1.0d));
        this.pc = this.pc.scalarMultiply(1.0d - this.cc);
        if (hsig) {
            this.pc = this.pc.add(this.xmean.subtract(xold).scalarMultiply(FastMath.sqrt((this.cc * (2.0d - this.cc)) * this.mueff) / this.sigma));
        }
        return hsig;
    }

    private void updateCovarianceDiagonalOnly(boolean hsig, RealMatrix bestArz) {
        this.diagC = this.diagC.scalarMultiply((hsig ? 0.0d : (this.ccov1Sep * this.cc) * (2.0d - this.cc)) + ((1.0d - this.ccov1Sep) - this.ccovmuSep)).add(square(this.pc).scalarMultiply(this.ccov1Sep)).add(times(this.diagC, square(bestArz).multiply(this.weights)).scalarMultiply(this.ccovmuSep));
        this.diagD = sqrt(this.diagC);
        if (this.diagonalOnly > 1 && this.iterations > this.diagonalOnly) {
            this.diagonalOnly = 0;
            this.B = eye(this.dimension, this.dimension);
            this.BD = diag(this.diagD);
            this.C = diag(this.diagC);
        }
    }

    private void updateCovariance(boolean hsig, RealMatrix bestArx, RealMatrix arz, int[] arindex, RealMatrix xold) {
        double negccov = 0.0d;
        if (this.ccov1 + this.ccovmu > 0.0d) {
            double oldFac;
            RealMatrix arpos = bestArx.subtract(repmat(xold, 1, this.mu)).scalarMultiply(1.0d / this.sigma);
            RealMatrix realMatrix = this.pc;
            RealMatrix realMatrix2 = this.pc;
            RealMatrix roneu = r0.multiply(r0.transpose()).scalarMultiply(this.ccov1);
            if (hsig) {
                oldFac = 0.0d;
            } else {
                oldFac = (this.ccov1 * this.cc) * (2.0d - this.cc);
            }
            double d = this.ccov1;
            oldFac += (1.0d - r0) - this.ccovmu;
            if (this.isActiveCMA) {
                d = this.ccovmu;
                negccov = (((1.0d - r0) * 0.25d) * this.mueff) / (FastMath.pow((double) (this.dimension + 2), 1.5d) + (2.0d * this.mueff));
                RealMatrix arzneg = selectColumns(arz, MathArrays.copyOf(reverse(arindex), this.mu));
                RealMatrix arnorms = sqrt(sumRows(square(arzneg)));
                int[] idxnorms = sortedIndices(arnorms.getRow(0));
                RealMatrix arnormsInv = selectColumns(divide(selectColumns(arnorms, reverse(idxnorms)), selectColumns(arnorms, idxnorms)), inverse(idxnorms));
                double negcovMax = 0.33999999999999997d / square(arnormsInv).multiply(this.weights).getEntry(0, 0);
                if (negccov > negcovMax) {
                    negccov = negcovMax;
                }
                arzneg = times(arzneg, repmat(arnormsInv, this.dimension, 1));
                RealMatrix artmp = this.BD.multiply(arzneg);
                RealMatrix Cneg = artmp.multiply(diag(this.weights)).multiply(artmp.transpose());
                oldFac += 0.5d * negccov;
                this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu + (0.5d * negccov)).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose()))).subtract(Cneg.scalarMultiply(negccov));
            } else {
                this.C = this.C.scalarMultiply(oldFac).add(roneu).add(arpos.scalarMultiply(this.ccovmu).multiply(times(repmat(this.weights, 1, this.dimension), arpos.transpose())));
            }
        }
        updateBD(negccov);
    }

    private void updateBD(double negccov) {
        if ((this.ccov1 + this.ccovmu) + negccov > 0.0d && (((((double) this.iterations) % 1.0d) / ((this.ccov1 + this.ccovmu) + negccov)) / ((double) this.dimension)) / BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS < 1.0d) {
            double tfac;
            this.C = triu(this.C, 0).add(triu(this.C, 1).transpose());
            EigenDecomposition eig = new EigenDecomposition(this.C);
            this.B = eig.getV();
            this.D = eig.getD();
            this.diagD = diag(this.D);
            if (min(this.diagD) <= 0.0d) {
                for (int i = 0; i < this.dimension; i++) {
                    if (this.diagD.getEntry(i, 0) < 0.0d) {
                        this.diagD.setEntry(i, 0, 0.0d);
                    }
                }
                tfac = max(this.diagD) / 1.0E14d;
                this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
                this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
            }
            if (max(this.diagD) > 1.0E14d * min(this.diagD)) {
                tfac = (max(this.diagD) / 1.0E14d) - min(this.diagD);
                this.C = this.C.add(eye(this.dimension, this.dimension).scalarMultiply(tfac));
                this.diagD = this.diagD.add(ones(this.dimension, 1).scalarMultiply(tfac));
            }
            this.diagC = diag(this.C);
            this.diagD = sqrt(this.diagD);
            this.BD = times(this.B, repmat(this.diagD.transpose(), this.dimension, 1));
        }
    }

    private static void push(double[] vals, double val) {
        for (int i = vals.length - 1; i > 0; i--) {
            vals[i] = vals[i - 1];
        }
        vals[0] = val;
    }

    private int[] sortedIndices(double[] doubles) {
        int i;
        DoubleIndex[] dis = new DoubleIndex[doubles.length];
        for (i = 0; i < doubles.length; i++) {
            dis[i] = new DoubleIndex(doubles[i], i);
        }
        Arrays.sort(dis);
        int[] indices = new int[doubles.length];
        for (i = 0; i < doubles.length; i++) {
            indices[i] = dis[i].index;
        }
        return indices;
    }

    private double valueRange(ValuePenaltyPair[] vpPairs) {
        double max = Double.NEGATIVE_INFINITY;
        double min = Double.MAX_VALUE;
        for (ValuePenaltyPair vpPair : vpPairs) {
            if (vpPair.value > max) {
                max = vpPair.value;
            }
            if (vpPair.value < min) {
                min = vpPair.value;
            }
        }
        return max - min;
    }

    private static RealMatrix log(RealMatrix m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                d[r][c] = FastMath.log(m.getEntry(r, c));
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix sqrt(RealMatrix m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                d[r][c] = FastMath.sqrt(m.getEntry(r, c));
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix square(RealMatrix m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                double e = m.getEntry(r, c);
                d[r][c] = e * e;
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix times(RealMatrix m, RealMatrix n) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                d[r][c] = m.getEntry(r, c) * n.getEntry(r, c);
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix divide(RealMatrix m, RealMatrix n) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                d[r][c] = m.getEntry(r, c) / n.getEntry(r, c);
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix selectColumns(RealMatrix m, int[] cols) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), cols.length});
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < cols.length; c++) {
                d[r][c] = m.getEntry(r, cols[c]);
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix triu(RealMatrix m, int k) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getColumnDimension()});
        int r = 0;
        while (r < m.getRowDimension()) {
            int c = 0;
            while (c < m.getColumnDimension()) {
                d[r][c] = r <= c - k ? m.getEntry(r, c) : 0.0d;
                c++;
            }
            r++;
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix sumRows(RealMatrix m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{1, m.getColumnDimension()});
        for (int c = 0; c < m.getColumnDimension(); c++) {
            double sum = 0.0d;
            for (int r = 0; r < m.getRowDimension(); r++) {
                sum += m.getEntry(r, c);
            }
            d[0][c] = sum;
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix diag(RealMatrix m) {
        double[][] d;
        int i;
        if (m.getColumnDimension() == 1) {
            d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), m.getRowDimension()});
            for (i = 0; i < m.getRowDimension(); i++) {
                d[i][i] = m.getEntry(i, 0);
            }
            return new Array2DRowRealMatrix(d, false);
        }
        d = (double[][]) Array.newInstance(Double.TYPE, new int[]{m.getRowDimension(), 1});
        for (i = 0; i < m.getColumnDimension(); i++) {
            d[i][0] = m.getEntry(i, i);
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static void copyColumn(RealMatrix m1, int col1, RealMatrix m2, int col2) {
        for (int i = 0; i < m1.getRowDimension(); i++) {
            m2.setEntry(i, col2, m1.getEntry(i, col1));
        }
    }

    private static RealMatrix ones(int n, int m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, m});
        for (int r = 0; r < n; r++) {
            Arrays.fill(d[r], 1.0d);
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix eye(int n, int m) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{n, m});
        for (int r = 0; r < n; r++) {
            if (r < m) {
                d[r][r] = 1.0d;
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix zeros(int n, int m) {
        return new Array2DRowRealMatrix(n, m);
    }

    private static RealMatrix repmat(RealMatrix mat, int n, int m) {
        int rd = mat.getRowDimension();
        int cd = mat.getColumnDimension();
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{n * rd, m * cd});
        for (int r = 0; r < n * rd; r++) {
            for (int c = 0; c < m * cd; c++) {
                d[r][c] = mat.getEntry(r % rd, c % cd);
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static RealMatrix sequence(double start, double end, double step) {
        int size = (int) (((end - start) / step) + 1.0d);
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, 1});
        double value = start;
        for (int r = 0; r < size; r++) {
            d[r][0] = value;
            value += step;
        }
        return new Array2DRowRealMatrix(d, false);
    }

    private static double max(RealMatrix m) {
        double max = -1.7976931348623157E308d;
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                double e = m.getEntry(r, c);
                if (max < e) {
                    max = e;
                }
            }
        }
        return max;
    }

    private static double min(RealMatrix m) {
        double min = Double.MAX_VALUE;
        for (int r = 0; r < m.getRowDimension(); r++) {
            for (int c = 0; c < m.getColumnDimension(); c++) {
                double e = m.getEntry(r, c);
                if (min > e) {
                    min = e;
                }
            }
        }
        return min;
    }

    private static double max(double[] m) {
        double max = -1.7976931348623157E308d;
        for (int r = 0; r < m.length; r++) {
            if (max < m[r]) {
                max = m[r];
            }
        }
        return max;
    }

    private static double min(double[] m) {
        double min = Double.MAX_VALUE;
        for (int r = 0; r < m.length; r++) {
            if (min > m[r]) {
                min = m[r];
            }
        }
        return min;
    }

    private static int[] inverse(int[] indices) {
        int[] inverse = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            inverse[indices[i]] = i;
        }
        return inverse;
    }

    private static int[] reverse(int[] indices) {
        int[] reverse = new int[indices.length];
        for (int i = 0; i < indices.length; i++) {
            reverse[i] = indices[(indices.length - i) - 1];
        }
        return reverse;
    }

    private double[] randn(int size) {
        double[] randn = new double[size];
        for (int i = 0; i < size; i++) {
            randn[i] = this.random.nextGaussian();
        }
        return randn;
    }

    private RealMatrix randn1(int size, int popSize) {
        double[][] d = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, popSize});
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < popSize; c++) {
                d[r][c] = this.random.nextGaussian();
            }
        }
        return new Array2DRowRealMatrix(d, false);
    }
}
