package org.apache.commons.math4.ml.clustering.evaluation;

import java.util.List;
import org.apache.commons.math4.ml.clustering.CentroidCluster;
import org.apache.commons.math4.ml.clustering.Cluster;
import org.apache.commons.math4.ml.clustering.Clusterable;
import org.apache.commons.math4.ml.clustering.DoublePoint;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.ml.distance.EuclideanDistance;

public abstract class ClusterEvaluator<T extends Clusterable> {
    private final DistanceMeasure measure;

    public abstract double score(List<? extends Cluster<T>> list);

    public ClusterEvaluator() {
        this(new EuclideanDistance());
    }

    public ClusterEvaluator(DistanceMeasure measure) {
        this.measure = measure;
    }

    public boolean isBetterScore(double score1, double score2) {
        return score1 < score2;
    }

    protected double distance(Clusterable p1, Clusterable p2) {
        return this.measure.compute(p1.getPoint(), p2.getPoint());
    }

    protected Clusterable centroidOf(Cluster<T> cluster) {
        List<T> points = cluster.getPoints();
        if (points.isEmpty()) {
            return null;
        }
        if (cluster instanceof CentroidCluster) {
            return ((CentroidCluster) cluster).getCenter();
        }
        double[] centroid = new double[((Clusterable) points.get(0)).getPoint().length];
        for (T p : points) {
            int i;
            double[] point = p.getPoint();
            for (i = 0; i < centroid.length; i++) {
                centroid[i] = centroid[i] + point[i];
            }
        }
        for (i = 0; i < centroid.length; i++) {
            centroid[i] = centroid[i] / ((double) points.size());
        }
        return new DoublePoint(centroid);
    }
}
