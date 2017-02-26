package org.apache.commons.math4.optim.univariate;

import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.optim.MaxEval;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.random.RandomGenerator;

public class MultiStartUnivariateOptimizer extends UnivariateOptimizer {
    private final RandomGenerator generator;
    private int maxEvalIndex;
    private OptimizationData[] optimData;
    private UnivariatePointValuePair[] optima;
    private final UnivariateOptimizer optimizer;
    private int searchIntervalIndex;
    private final int starts;
    private int totalEvaluations;

    class 1 implements Comparator<UnivariatePointValuePair> {
        private final /* synthetic */ GoalType val$goal;

        1(GoalType goalType) {
            this.val$goal = goalType;
        }

        public int compare(UnivariatePointValuePair o1, UnivariatePointValuePair o2) {
            if (o1 == null) {
                return o2 == null ? 0 : 1;
            } else {
                if (o2 == null) {
                    return -1;
                }
                double v1 = o1.getValue();
                double v2 = o2.getValue();
                return this.val$goal == GoalType.MINIMIZE ? Double.compare(v1, v2) : Double.compare(v2, v1);
            }
        }
    }

    public MultiStartUnivariateOptimizer(UnivariateOptimizer optimizer, int starts, RandomGenerator generator) {
        super(optimizer.getConvergenceChecker());
        this.maxEvalIndex = -1;
        this.searchIntervalIndex = -1;
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

    public UnivariatePointValuePair[] getOptima() {
        if (this.optima != null) {
            return (UnivariatePointValuePair[]) this.optima.clone();
        }
        throw new MathIllegalStateException(LocalizedFormats.NO_OPTIMUM_COMPUTED_YET, new Object[0]);
    }

    public UnivariatePointValuePair optimize(OptimizationData... optData) {
        this.optimData = optData;
        return super.optimize(optData);
    }

    protected UnivariatePointValuePair doOptimize() {
        int i;
        for (i = 0; i < this.optimData.length; i++) {
            if (this.optimData[i] instanceof MaxEval) {
                this.optimData[i] = null;
                this.maxEvalIndex = i;
            } else if (this.optimData[i] instanceof SearchInterval) {
                this.optimData[i] = null;
                this.searchIntervalIndex = i;
            }
        }
        if (this.maxEvalIndex == -1) {
            throw new MathIllegalStateException();
        } else if (this.searchIntervalIndex == -1) {
            throw new MathIllegalStateException();
        } else {
            RuntimeException lastException = null;
            this.optima = new UnivariatePointValuePair[this.starts];
            this.totalEvaluations = 0;
            int maxEval = getMaxEvaluations();
            double min = getMin();
            double max = getMax();
            double startValue = getStartValue();
            for (i = 0; i < this.starts; i++) {
                try {
                    double s;
                    this.optimData[this.maxEvalIndex] = new MaxEval(maxEval - this.totalEvaluations);
                    if (i == 0) {
                        s = startValue;
                    } else {
                        s = min + (this.generator.nextDouble() * (max - min));
                    }
                    this.optimData[this.searchIntervalIndex] = new SearchInterval(min, max, s);
                    this.optima[i] = this.optimizer.optimize(this.optimData);
                } catch (RuntimeException mue) {
                    lastException = mue;
                    this.optima[i] = null;
                }
                this.totalEvaluations += this.optimizer.getEvaluations();
            }
            sortPairs(getGoalType());
            if (this.optima[0] != null) {
                return this.optima[0];
            }
            throw lastException;
        }
    }

    private void sortPairs(GoalType goal) {
        Arrays.sort(this.optima, new 1(goal));
    }
}
