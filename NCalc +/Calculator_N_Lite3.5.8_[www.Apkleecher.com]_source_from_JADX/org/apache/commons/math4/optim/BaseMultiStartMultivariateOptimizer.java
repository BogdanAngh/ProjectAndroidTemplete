package org.apache.commons.math4.optim;

import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.TooManyEvaluationsException;
import org.apache.commons.math4.random.RandomVectorGenerator;

public abstract class BaseMultiStartMultivariateOptimizer<PAIR> extends BaseMultivariateOptimizer<PAIR> {
    private RandomVectorGenerator generator;
    private int initialGuessIndex;
    private int maxEvalIndex;
    private OptimizationData[] optimData;
    private final BaseMultivariateOptimizer<PAIR> optimizer;
    private int starts;
    private int totalEvaluations;

    protected abstract void clear();

    public abstract PAIR[] getOptima();

    protected abstract void store(PAIR pair);

    public BaseMultiStartMultivariateOptimizer(BaseMultivariateOptimizer<PAIR> optimizer, int starts, RandomVectorGenerator generator) {
        super(optimizer.getConvergenceChecker());
        this.maxEvalIndex = -1;
        this.initialGuessIndex = -1;
        if (starts < 1) {
            throw new NotStrictlyPositiveException(Integer.valueOf(starts));
        }
        this.optimizer = optimizer;
        this.starts = starts;
        this.generator = generator;
    }

    public int getEvaluations() {
        return this.totalEvaluations;
    }

    public PAIR optimize(OptimizationData... optData) {
        this.optimData = optData;
        return super.optimize(optData);
    }

    protected PAIR doOptimize() {
        int i;
        for (i = 0; i < this.optimData.length; i++) {
            if (this.optimData[i] instanceof MaxEval) {
                this.optimData[i] = null;
                this.maxEvalIndex = i;
            }
            if (this.optimData[i] instanceof InitialGuess) {
                this.optimData[i] = null;
                this.initialGuessIndex = i;
            }
        }
        if (this.maxEvalIndex == -1) {
            throw new MathIllegalStateException();
        } else if (this.initialGuessIndex == -1) {
            throw new MathIllegalStateException();
        } else {
            RuntimeException lastException = null;
            this.totalEvaluations = 0;
            clear();
            int maxEval = getMaxEvaluations();
            double[] min = getLowerBound();
            double[] max = getUpperBound();
            double[] startPoint = getStartPoint();
            for (i = 0; i < this.starts; i++) {
                try {
                    this.optimData[this.maxEvalIndex] = new MaxEval(maxEval - this.totalEvaluations);
                    double[] s = null;
                    if (i == 0) {
                        s = startPoint;
                    } else {
                        int attempts = 0;
                        while (s == null) {
                            int attempts2 = attempts + 1;
                            if (attempts >= getMaxEvaluations()) {
                                throw new TooManyEvaluationsException(Integer.valueOf(getMaxEvaluations()));
                            }
                            s = this.generator.nextVector();
                            int k = 0;
                            while (s != null && k < s.length) {
                                if ((min != null && s[k] < min[k]) || (max != null && s[k] > max[k])) {
                                    s = null;
                                }
                                k++;
                            }
                            attempts = attempts2;
                        }
                    }
                    this.optimData[this.initialGuessIndex] = new InitialGuess(s);
                    store(this.optimizer.optimize(this.optimData));
                } catch (RuntimeException mue) {
                    lastException = mue;
                }
                this.totalEvaluations += this.optimizer.getEvaluations();
            }
            Object[] optima = getOptima();
            if (optima.length != 0) {
                return optima[0];
            }
            throw lastException;
        }
    }
}
