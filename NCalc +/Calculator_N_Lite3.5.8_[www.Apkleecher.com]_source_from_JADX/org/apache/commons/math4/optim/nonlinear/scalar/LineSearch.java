package org.apache.commons.math4.optim.nonlinear.scalar;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.optim.MaxEval;
import org.apache.commons.math4.optim.univariate.BracketFinder;
import org.apache.commons.math4.optim.univariate.BrentOptimizer;
import org.apache.commons.math4.optim.univariate.SearchInterval;
import org.apache.commons.math4.optim.univariate.SimpleUnivariateValueChecker;
import org.apache.commons.math4.optim.univariate.UnivariateObjectiveFunction;
import org.apache.commons.math4.optim.univariate.UnivariateOptimizer;
import org.apache.commons.math4.optim.univariate.UnivariatePointValuePair;

public class LineSearch {
    private static final double ABS_TOL_UNUSED = Double.MIN_VALUE;
    private static final double REL_TOL_UNUSED = 1.0E-15d;
    private final BracketFinder bracket;
    private final double initialBracketingRange;
    private final UnivariateOptimizer lineOptimizer;
    private final MultivariateOptimizer mainOptimizer;

    class 1 implements UnivariateFunction {
        private final /* synthetic */ double[] val$direction;
        private final /* synthetic */ int val$n;
        private final /* synthetic */ double[] val$startPoint;

        1(int i, double[] dArr, double[] dArr2) {
            this.val$n = i;
            this.val$startPoint = dArr;
            this.val$direction = dArr2;
        }

        public double value(double alpha) {
            double[] x = new double[this.val$n];
            for (int i = 0; i < this.val$n; i++) {
                x[i] = this.val$startPoint[i] + (this.val$direction[i] * alpha);
            }
            return LineSearch.this.mainOptimizer.computeObjectiveValue(x);
        }
    }

    public LineSearch(MultivariateOptimizer optimizer, double relativeTolerance, double absoluteTolerance, double initialBracketingRange) {
        this.bracket = new BracketFinder();
        this.mainOptimizer = optimizer;
        this.lineOptimizer = new BrentOptimizer(REL_TOL_UNUSED, ABS_TOL_UNUSED, new SimpleUnivariateValueChecker(relativeTolerance, absoluteTolerance));
        this.initialBracketingRange = initialBracketingRange;
    }

    public UnivariatePointValuePair search(double[] startPoint, double[] direction) {
        this.bracket.search(new 1(startPoint.length, startPoint, direction), this.mainOptimizer.getGoalType(), 0.0d, this.initialBracketingRange);
        return this.lineOptimizer.optimize(new MaxEval(BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT), new UnivariateObjectiveFunction(f), goal, new SearchInterval(this.bracket.getLo(), this.bracket.getHi(), this.bracket.getMid()));
    }
}
