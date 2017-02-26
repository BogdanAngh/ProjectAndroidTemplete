package org.apache.commons.math4.ml.clustering.evaluation;

import java.util.List;
import org.apache.commons.math4.ml.clustering.Cluster;
import org.apache.commons.math4.ml.clustering.Clusterable;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.stat.descriptive.moment.Variance;

public class SumOfClusterVariances<T extends Clusterable> extends ClusterEvaluator<T> {
    public SumOfClusterVariances(DistanceMeasure measure) {
        super(measure);
    }

    public double score(List<? extends Cluster<T>> clusters) {
        double varianceSum = 0.0d;
        for (Cluster<T> cluster : clusters) {
            if (!cluster.getPoints().isEmpty()) {
                Clusterable center = centroidOf(cluster);
                Variance stat = new Variance();
                for (Clusterable point : cluster.getPoints()) {
                    stat.increment(distance(point, center));
                }
                varianceSum += stat.getResult();
            }
        }
        return varianceSum;
    }
}
