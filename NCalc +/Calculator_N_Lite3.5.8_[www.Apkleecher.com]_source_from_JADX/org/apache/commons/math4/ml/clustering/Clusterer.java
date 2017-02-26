package org.apache.commons.math4.ml.clustering;

import java.util.Collection;
import java.util.List;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.ml.distance.DistanceMeasure;

public abstract class Clusterer<T extends Clusterable> {
    private DistanceMeasure measure;

    public abstract List<? extends Cluster<T>> cluster(Collection<T> collection) throws MathIllegalArgumentException, ConvergenceException;

    protected Clusterer(DistanceMeasure measure) {
        this.measure = measure;
    }

    public DistanceMeasure getDistanceMeasure() {
        return this.measure;
    }

    protected double distance(Clusterable p1, Clusterable p2) {
        return this.measure.compute(p1.getPoint(), p2.getPoint());
    }
}
