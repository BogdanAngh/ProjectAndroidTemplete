package org.apache.commons.math4.ml.clustering;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.exception.MathIllegalArgumentException;
import org.apache.commons.math4.exception.MathIllegalStateException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.ml.distance.EuclideanDistance;
import org.apache.commons.math4.random.JDKRandomGenerator;
import org.apache.commons.math4.random.RandomGenerator;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class FuzzyKMeansClusterer<T extends Clusterable> extends Clusterer<T> {
    private static final double DEFAULT_EPSILON = 0.001d;
    private List<CentroidCluster<T>> clusters;
    private final double epsilon;
    private final double fuzziness;
    private final int k;
    private final int maxIterations;
    private double[][] membershipMatrix;
    private List<T> points;
    private final RandomGenerator random;

    public FuzzyKMeansClusterer(int k, double fuzziness) throws NumberIsTooSmallException {
        this(k, fuzziness, -1, new EuclideanDistance());
    }

    public FuzzyKMeansClusterer(int k, double fuzziness, int maxIterations, DistanceMeasure measure) throws NumberIsTooSmallException {
        this(k, fuzziness, maxIterations, measure, DEFAULT_EPSILON, new JDKRandomGenerator());
    }

    public FuzzyKMeansClusterer(int k, double fuzziness, int maxIterations, DistanceMeasure measure, double epsilon, RandomGenerator random) throws NumberIsTooSmallException {
        super(measure);
        if (fuzziness <= 1.0d) {
            throw new NumberIsTooSmallException(Double.valueOf(fuzziness), Double.valueOf(1.0d), false);
        }
        this.k = k;
        this.fuzziness = fuzziness;
        this.maxIterations = maxIterations;
        this.epsilon = epsilon;
        this.random = random;
        this.membershipMatrix = null;
        this.points = null;
        this.clusters = null;
    }

    public int getK() {
        return this.k;
    }

    public double getFuzziness() {
        return this.fuzziness;
    }

    public int getMaxIterations() {
        return this.maxIterations;
    }

    public double getEpsilon() {
        return this.epsilon;
    }

    public RandomGenerator getRandomGenerator() {
        return this.random;
    }

    public RealMatrix getMembershipMatrix() {
        if (this.membershipMatrix != null) {
            return MatrixUtils.createRealMatrix(this.membershipMatrix);
        }
        throw new MathIllegalStateException();
    }

    public List<T> getDataPoints() {
        return this.points;
    }

    public List<CentroidCluster<T>> getClusters() {
        return this.clusters;
    }

    public double getObjectiveFunctionValue() {
        if (this.points == null || this.clusters == null) {
            throw new MathIllegalStateException();
        }
        int i = 0;
        double objFunction = 0.0d;
        for (Clusterable point : this.points) {
            int j = 0;
            for (CentroidCluster<T> cluster : this.clusters) {
                double dist = distance(point, cluster.getCenter());
                objFunction += (dist * dist) * FastMath.pow(this.membershipMatrix[i][j], this.fuzziness);
                j++;
            }
            i++;
        }
        return objFunction;
    }

    public List<CentroidCluster<T>> cluster(Collection<T> dataPoints) throws MathIllegalArgumentException {
        MathUtils.checkNotNull(dataPoints);
        int size = dataPoints.size();
        if (size < this.k) {
            throw new NumberIsTooSmallException(Integer.valueOf(size), Integer.valueOf(this.k), false);
        }
        this.points = Collections.unmodifiableList(new ArrayList(dataPoints));
        this.clusters = new ArrayList();
        this.membershipMatrix = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, this.k});
        double[][] oldMatrix = (double[][]) Array.newInstance(Double.TYPE, new int[]{size, this.k});
        if (size == 0) {
            return this.clusters;
        }
        initializeMembershipMatrix();
        int pointDimension = ((Clusterable) this.points.get(0)).getPoint().length;
        for (int i = 0; i < this.k; i++) {
            this.clusters.add(new CentroidCluster(new DoublePoint(new double[pointDimension])));
        }
        int iteration = 0;
        int max = this.maxIterations < 0 ? BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT : this.maxIterations;
        do {
            saveMembershipMatrix(oldMatrix);
            updateClusterCenters();
            updateMembershipMatrix();
            if (calculateMaxMembershipChange(oldMatrix) <= this.epsilon) {
                break;
            }
            iteration++;
        } while (iteration < max);
        return this.clusters;
    }

    private void updateClusterCenters() {
        int j = 0;
        List<CentroidCluster<T>> newClusters = new ArrayList(this.k);
        for (CentroidCluster<T> cluster : this.clusters) {
            int i = 0;
            double[] arr = new double[cluster.getCenter().getPoint().length];
            double sum = 0.0d;
            for (Clusterable point : this.points) {
                double[][] dArr = this.membershipMatrix;
                double u = FastMath.pow(r0[i][j], this.fuzziness);
                double[] pointArr = point.getPoint();
                int idx = 0;
                while (true) {
                    int length = arr.length;
                    if (idx >= r0) {
                        break;
                    }
                    arr[idx] = arr[idx] + (pointArr[idx] * u);
                    idx++;
                }
                sum += u;
                i++;
            }
            MathArrays.scaleInPlace(1.0d / sum, arr);
            newClusters.add(new CentroidCluster(new DoublePoint(arr)));
            j++;
        }
        this.clusters.clear();
        this.clusters = newClusters;
    }

    private void updateMembershipMatrix() {
        for (int i = 0; i < this.points.size(); i++) {
            Clusterable point = (Clusterable) this.points.get(i);
            double maxMembership = Double.MIN_VALUE;
            int newCluster = -1;
            for (int j = 0; j < this.clusters.size(); j++) {
                double membership;
                double sum = 0.0d;
                double distA = FastMath.abs(distance(point, ((CentroidCluster) this.clusters.get(j)).getCenter()));
                if (distA != 0.0d) {
                    for (CentroidCluster<T> c : this.clusters) {
                        double distB = FastMath.abs(distance(point, c.getCenter()));
                        if (distB == 0.0d) {
                            sum = Double.POSITIVE_INFINITY;
                            break;
                        } else {
                            sum += FastMath.pow(distA / distB, 2.0d / (this.fuzziness - 1.0d));
                        }
                    }
                }
                if (sum == 0.0d) {
                    membership = 1.0d;
                } else if (sum == Double.POSITIVE_INFINITY) {
                    membership = 0.0d;
                } else {
                    membership = 1.0d / sum;
                }
                this.membershipMatrix[i][j] = membership;
                if (this.membershipMatrix[i][j] > maxMembership) {
                    maxMembership = this.membershipMatrix[i][j];
                    newCluster = j;
                }
            }
            ((CentroidCluster) this.clusters.get(newCluster)).addPoint(point);
        }
    }

    private void initializeMembershipMatrix() {
        for (int i = 0; i < this.points.size(); i++) {
            for (int j = 0; j < this.k; j++) {
                this.membershipMatrix[i][j] = this.random.nextDouble();
            }
            this.membershipMatrix[i] = MathArrays.normalizeArray(this.membershipMatrix[i], 1.0d);
        }
    }

    private double calculateMaxMembershipChange(double[][] matrix) {
        double maxMembership = 0.0d;
        for (int i = 0; i < this.points.size(); i++) {
            for (int j = 0; j < this.clusters.size(); j++) {
                maxMembership = FastMath.max(FastMath.abs(this.membershipMatrix[i][j] - matrix[i][j]), maxMembership);
            }
        }
        return maxMembership;
    }

    private void saveMembershipMatrix(double[][] matrix) {
        for (int i = 0; i < this.points.size(); i++) {
            System.arraycopy(this.membershipMatrix[i], 0, matrix[i], 0, this.clusters.size());
        }
    }
}
