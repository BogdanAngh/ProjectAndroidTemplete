package org.apache.commons.math4.optim.nonlinear.scalar.noderiv;

import java.util.Comparator;
import org.apache.commons.math4.analysis.MultivariateFunction;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.optim.ConvergenceChecker;
import org.apache.commons.math4.optim.OptimizationData;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.optim.SimpleValueChecker;
import org.apache.commons.math4.optim.nonlinear.scalar.GoalType;
import org.apache.commons.math4.optim.nonlinear.scalar.MultivariateOptimizer;

public class SimplexOptimizer extends MultivariateOptimizer {
    private AbstractSimplex simplex;

    class 1 implements MultivariateFunction {
        1() {
        }

        public double value(double[] point) {
            return SimplexOptimizer.this.computeObjectiveValue(point);
        }
    }

    class 2 implements Comparator<PointValuePair> {
        private final /* synthetic */ boolean val$isMinim;

        2(boolean z) {
            this.val$isMinim = z;
        }

        public int compare(PointValuePair o1, PointValuePair o2) {
            double v1 = ((Double) o1.getValue()).doubleValue();
            double v2 = ((Double) o2.getValue()).doubleValue();
            return this.val$isMinim ? Double.compare(v1, v2) : Double.compare(v2, v1);
        }
    }

    public SimplexOptimizer(ConvergenceChecker<PointValuePair> checker) {
        super(checker);
    }

    public SimplexOptimizer(double rel, double abs) {
        this(new SimpleValueChecker(rel, abs));
    }

    public PointValuePair optimize(OptimizationData... optData) {
        return super.optimize(optData);
    }

    protected PointValuePair doOptimize() {
        boolean isMinim;
        checkParameters();
        MultivariateFunction evalFunc = new 1();
        if (getGoalType() == GoalType.MINIMIZE) {
            isMinim = true;
        } else {
            isMinim = false;
        }
        Comparator<PointValuePair> comparator = new 2(isMinim);
        this.simplex.build(getStartPoint());
        this.simplex.evaluate(evalFunc, comparator);
        PointValuePair[] previous = null;
        ConvergenceChecker<PointValuePair> checker = getConvergenceChecker();
        while (true) {
            if (getIterations() > 0) {
                boolean converged = true;
                int i = 0;
                while (i < this.simplex.getSize()) {
                    PointValuePair prev = previous[i];
                    if (converged && checker.converged(0, prev, this.simplex.getPoint(i))) {
                        converged = true;
                    } else {
                        converged = false;
                    }
                    i++;
                }
                if (converged) {
                    return this.simplex.getPoint(0);
                }
            }
            previous = this.simplex.getPoints();
            this.simplex.iterate(evalFunc, comparator);
            incrementIterationCount();
        }
    }

    protected void parseOptimizationData(OptimizationData... optData) {
        super.parseOptimizationData(optData);
        for (OptimizationData data : optData) {
            if (data instanceof AbstractSimplex) {
                this.simplex = (AbstractSimplex) data;
                return;
            }
        }
    }

    private void checkParameters() {
        if (this.simplex == null) {
            throw new NullArgumentException();
        } else if (getLowerBound() != null || getUpperBound() != null) {
            throw new MathUnsupportedOperationException(LocalizedFormats.CONSTRAINT, new Object[0]);
        }
    }
}
