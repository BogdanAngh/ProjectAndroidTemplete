package org.apache.commons.math4.ml.clustering;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.ml.distance.EuclideanDistance;
import org.apache.commons.math4.random.JDKRandomGenerator;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.stat.descriptive.moment.Variance;
import org.apache.commons.math4.util.MathUtils;
import org.matheclipse.core.interfaces.IExpr;

public class KMeansPlusPlusClusterer<T extends Clusterable> extends Clusterer<T> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ml$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy;
    private final EmptyClusterStrategy emptyStrategy;
    private final int k;
    private final int maxIterations;
    private final RandomGenerator random;

    public enum EmptyClusterStrategy {
        LARGEST_VARIANCE,
        LARGEST_POINTS_NUMBER,
        FARTHEST_POINT,
        ERROR
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$ml$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$ml$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy;
        if (iArr == null) {
            iArr = new int[EmptyClusterStrategy.values().length];
            try {
                iArr[EmptyClusterStrategy.ERROR.ordinal()] = 4;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[EmptyClusterStrategy.FARTHEST_POINT.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[EmptyClusterStrategy.LARGEST_POINTS_NUMBER.ordinal()] = 2;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[EmptyClusterStrategy.LARGEST_VARIANCE.ordinal()] = 1;
            } catch (NoSuchFieldError e4) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$ml$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy = iArr;
        }
        return iArr;
    }

    public KMeansPlusPlusClusterer(int k) {
        this(k, -1);
    }

    public KMeansPlusPlusClusterer(int k, int maxIterations) {
        this(k, maxIterations, new EuclideanDistance());
    }

    public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure) {
        this(k, maxIterations, measure, new JDKRandomGenerator());
    }

    public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random) {
        this(k, maxIterations, measure, random, EmptyClusterStrategy.LARGEST_VARIANCE);
    }

    public KMeansPlusPlusClusterer(int k, int maxIterations, DistanceMeasure measure, RandomGenerator random, EmptyClusterStrategy emptyStrategy) {
        super(measure);
        this.k = k;
        this.maxIterations = maxIterations;
        this.random = random;
        this.emptyStrategy = emptyStrategy;
    }

    public int getK() {
        return this.k;
    }

    public int getMaxIterations() {
        return this.maxIterations;
    }

    public RandomGenerator getRandomGenerator() {
        return this.random;
    }

    public EmptyClusterStrategy getEmptyClusterStrategy() {
        return this.emptyStrategy;
    }

    public List<CentroidCluster<T>> cluster(Collection<T> points) throws MathIllegalArgumentException, ConvergenceException {
        MathUtils.checkNotNull(points);
        if (points.size() < this.k) {
            throw new NumberIsTooSmallException(Integer.valueOf(points.size()), Integer.valueOf(this.k), false);
        }
        List<CentroidCluster<T>> clusters = chooseInitialCenters(points);
        int[] assignments = new int[points.size()];
        assignPointsToClusters(clusters, points, assignments);
        int max = this.maxIterations < 0 ? BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT : this.maxIterations;
        for (int count = 0; count < max; count++) {
            boolean emptyCluster = false;
            List<CentroidCluster<T>> newClusters = new ArrayList();
            for (CentroidCluster<T> cluster : clusters) {
                Clusterable newCenter;
                if (cluster.getPoints().isEmpty()) {
                    switch ($SWITCH_TABLE$org$apache$commons$math4$ml$clustering$KMeansPlusPlusClusterer$EmptyClusterStrategy()[this.emptyStrategy.ordinal()]) {
                        case ValueServer.REPLAY_MODE /*1*/:
                            newCenter = getPointFromLargestVarianceCluster(clusters);
                            break;
                        case IExpr.DOUBLEID /*2*/:
                            newCenter = getPointFromLargestNumberCluster(clusters);
                            break;
                        case ValueServer.EXPONENTIAL_MODE /*3*/:
                            newCenter = getFarthestPoint(clusters);
                            break;
                        default:
                            throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
                    }
                    emptyCluster = true;
                } else {
                    newCenter = centroidOf(cluster.getPoints(), cluster.getCenter().getPoint().length);
                }
                newClusters.add(new CentroidCluster(newCenter));
            }
            clusters = newClusters;
            if (assignPointsToClusters(newClusters, points, assignments) == 0 && !emptyCluster) {
                return clusters;
            }
        }
        return clusters;
    }

    private int assignPointsToClusters(List<CentroidCluster<T>> clusters, Collection<T> points, int[] assignments) {
        int assignedDifferently = 0;
        int pointIndex = 0;
        for (T p : points) {
            int clusterIndex = getNearestCluster(clusters, p);
            if (clusterIndex != assignments[pointIndex]) {
                assignedDifferently++;
            }
            ((CentroidCluster) clusters.get(clusterIndex)).addPoint(p);
            int pointIndex2 = pointIndex + 1;
            assignments[pointIndex] = clusterIndex;
            pointIndex = pointIndex2;
        }
        return assignedDifferently;
    }

    private List<CentroidCluster<T>> chooseInitialCenters(Collection<T> points) {
        int i;
        List<T> pointList = Collections.unmodifiableList(new ArrayList(points));
        int numPoints = pointList.size();
        boolean[] taken = new boolean[numPoints];
        List<CentroidCluster<T>> resultSet = new ArrayList();
        int firstPointIndex = this.random.nextInt(numPoints);
        Clusterable firstPoint = (Clusterable) pointList.get(firstPointIndex);
        resultSet.add(new CentroidCluster(firstPoint));
        taken[firstPointIndex] = true;
        double[] minDistSquared = new double[numPoints];
        for (i = 0; i < numPoints; i++) {
            if (i != firstPointIndex) {
                double d = distance(firstPoint, (Clusterable) pointList.get(i));
                minDistSquared[i] = d * d;
            }
        }
        while (resultSet.size() < this.k) {
            double distSqSum = 0.0d;
            for (i = 0; i < numPoints; i++) {
                if (!taken[i]) {
                    distSqSum += minDistSquared[i];
                }
            }
            double r = this.random.nextDouble() * distSqSum;
            int nextPointIndex = -1;
            double sum = 0.0d;
            for (i = 0; i < numPoints; i++) {
                if (!taken[i]) {
                    sum += minDistSquared[i];
                    if (sum >= r) {
                        nextPointIndex = i;
                        break;
                    }
                }
            }
            if (nextPointIndex == -1) {
                for (i = numPoints - 1; i >= 0; i--) {
                    if (!taken[i]) {
                        nextPointIndex = i;
                        break;
                    }
                }
            }
            if (nextPointIndex < 0) {
                break;
            }
            Clusterable p = (Clusterable) pointList.get(nextPointIndex);
            resultSet.add(new CentroidCluster(p));
            taken[nextPointIndex] = true;
            if (resultSet.size() < this.k) {
                for (int j = 0; j < numPoints; j++) {
                    if (!taken[j]) {
                        d = distance(p, (Clusterable) pointList.get(j));
                        double d2 = d * d;
                        if (d2 < minDistSquared[j]) {
                            minDistSquared[j] = d2;
                        }
                    }
                }
            }
        }
        return resultSet;
    }

    private T getPointFromLargestVarianceCluster(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
        double maxVariance = Double.NEGATIVE_INFINITY;
        Cluster<T> selected = null;
        for (Cluster cluster : clusters) {
            if (!cluster.getPoints().isEmpty()) {
                Clusterable center = cluster.getCenter();
                Variance stat = new Variance();
                for (Clusterable point : cluster.getPoints()) {
                    stat.increment(distance(point, center));
                }
                double variance = stat.getResult();
                if (variance > maxVariance) {
                    maxVariance = variance;
                    selected = cluster;
                }
            }
        }
        if (selected == null) {
            throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
        }
        List<T> selectedPoints = selected.getPoints();
        return (Clusterable) selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
    }

    private T getPointFromLargestNumberCluster(Collection<? extends Cluster<T>> clusters) throws ConvergenceException {
        int maxNumber = 0;
        Cluster<T> selected = null;
        for (Cluster<T> cluster : clusters) {
            int number = cluster.getPoints().size();
            if (number > maxNumber) {
                maxNumber = number;
                selected = cluster;
            }
        }
        if (selected == null) {
            throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
        }
        List<T> selectedPoints = selected.getPoints();
        return (Clusterable) selectedPoints.remove(this.random.nextInt(selectedPoints.size()));
    }

    private T getFarthestPoint(Collection<CentroidCluster<T>> clusters) throws ConvergenceException {
        double maxDistance = Double.NEGATIVE_INFINITY;
        Cluster<T> selectedCluster = null;
        int selectedPoint = -1;
        for (Cluster cluster : clusters) {
            Clusterable center = cluster.getCenter();
            List<T> points = cluster.getPoints();
            for (int i = 0; i < points.size(); i++) {
                double distance = distance((Clusterable) points.get(i), center);
                if (distance > maxDistance) {
                    maxDistance = distance;
                    selectedCluster = cluster;
                    selectedPoint = i;
                }
            }
        }
        if (selectedCluster != null) {
            return (Clusterable) selectedCluster.getPoints().remove(selectedPoint);
        }
        throw new ConvergenceException(LocalizedFormats.EMPTY_CLUSTER_IN_K_MEANS, new Object[0]);
    }

    private int getNearestCluster(Collection<CentroidCluster<T>> clusters, T point) {
        double minDistance = Double.MAX_VALUE;
        int clusterIndex = 0;
        int minCluster = 0;
        for (CentroidCluster<T> c : clusters) {
            double distance = distance(point, c.getCenter());
            if (distance < minDistance) {
                minDistance = distance;
                minCluster = clusterIndex;
            }
            clusterIndex++;
        }
        return minCluster;
    }

    private Clusterable centroidOf(Collection<T> points, int dimension) {
        int i;
        double[] centroid = new double[dimension];
        for (T p : points) {
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
