package org.apache.commons.math4.optim.nonlinear.scalar;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.optim.BaseMultiStartMultivariateOptimizer;
import org.apache.commons.math4.optim.PointValuePair;
import org.apache.commons.math4.random.RandomVectorGenerator;

public class MultiStartMultivariateOptimizer extends BaseMultiStartMultivariateOptimizer<PointValuePair> {
    private final List<PointValuePair> optima;
    private final MultivariateOptimizer optimizer;

    class 1 implements Comparator<PointValuePair> {
        1() {
        }

        public int compare(PointValuePair o1, PointValuePair o2) {
            if (o1 == null) {
                return o2 == null ? 0 : 1;
            } else {
                if (o2 == null) {
                    return -1;
                }
                double v1 = ((Double) o1.getValue()).doubleValue();
                double v2 = ((Double) o2.getValue()).doubleValue();
                return MultiStartMultivariateOptimizer.this.optimizer.getGoalType() == GoalType.MINIMIZE ? Double.compare(v1, v2) : Double.compare(v2, v1);
            }
        }
    }

    public MultiStartMultivariateOptimizer(MultivariateOptimizer optimizer, int starts, RandomVectorGenerator generator) throws NullArgumentException, NotStrictlyPositiveException {
        super(optimizer, starts, generator);
        this.optima = new ArrayList();
        this.optimizer = optimizer;
    }

    public PointValuePair[] getOptima() {
        Collections.sort(this.optima, getPairComparator());
        return (PointValuePair[]) this.optima.toArray(new PointValuePair[0]);
    }

    protected void store(PointValuePair optimum) {
        this.optima.add(optimum);
    }

    protected void clear() {
        this.optima.clear();
    }

    private Comparator<PointValuePair> getPairComparator() {
        return new 1();
    }
}
