package org.apache.commons.math4.ml.neuralnet;

import java.lang.reflect.Array;
import java.util.Collection;
import java.util.HashMap;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.ml.neuralnet.twod.NeuronSquareMesh2D;
import org.apache.commons.math4.util.Pair;

public class MapUtils {
    private MapUtils() {
    }

    public static Neuron findBest(double[] features, Iterable<Neuron> neurons, DistanceMeasure distance) {
        Neuron best = null;
        double min = Double.POSITIVE_INFINITY;
        for (Neuron n : neurons) {
            double d = distance.compute(n.getFeatures(), features);
            if (d < min) {
                min = d;
                best = n;
            }
        }
        return best;
    }

    public static Pair<Neuron, Neuron> findBestAndSecondBest(double[] features, Iterable<Neuron> neurons, DistanceMeasure distance) {
        Neuron[] best = new Neuron[2];
        double[] min = new double[]{Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY};
        for (Neuron n : neurons) {
            double d = distance.compute(n.getFeatures(), features);
            if (d < min[0]) {
                min[1] = min[0];
                best[1] = best[0];
                min[0] = d;
                best[0] = n;
            } else if (d < min[1]) {
                min[1] = d;
                best[1] = n;
            }
        }
        return new Pair(best[0], best[1]);
    }

    public static double[][] computeU(NeuronSquareMesh2D map, DistanceMeasure distance) {
        int numRows = map.getNumberOfRows();
        int numCols = map.getNumberOfColumns();
        int[] iArr = new int[]{numRows, numCols};
        double[][] uMatrix = (double[][]) Array.newInstance(Double.TYPE, iArr);
        Network net = map.getNetwork();
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                Neuron neuron = map.getNeuron(i, j);
                Collection<Neuron> neighbours = net.getNeighbours(neuron);
                double[] features = neuron.getFeatures();
                double d = 0.0d;
                int count = 0;
                for (Neuron n : neighbours) {
                    count++;
                    d += distance.compute(features, n.getFeatures());
                }
                uMatrix[i][j] = d / ((double) count);
            }
        }
        return uMatrix;
    }

    public static int[][] computeHitHistogram(Iterable<double[]> data, NeuronSquareMesh2D map, DistanceMeasure distance) {
        HashMap<Neuron, Integer> hit = new HashMap();
        Network net = map.getNetwork();
        for (double[] f : data) {
            Neuron best = findBest(f, net, distance);
            Integer count = (Integer) hit.get(best);
            if (count == null) {
                hit.put(best, Integer.valueOf(1));
            } else {
                hit.put(best, Integer.valueOf(count.intValue() + 1));
            }
        }
        int numRows = map.getNumberOfRows();
        int numCols = map.getNumberOfColumns();
        int[][] histo = (int[][]) Array.newInstance(Integer.TYPE, new int[]{numRows, numCols});
        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                count = (Integer) hit.get(map.getNeuron(i, j));
                if (count == null) {
                    histo[i][j] = 0;
                } else {
                    histo[i][j] = count.intValue();
                }
            }
        }
        return histo;
    }

    public static double computeQuantizationError(Iterable<double[]> data, Iterable<Neuron> neurons, DistanceMeasure distance) {
        double d = 0.0d;
        int count = 0;
        for (double[] f : data) {
            count++;
            d += distance.compute(f, findBest(f, neurons, distance).getFeatures());
        }
        if (count != 0) {
            return d / ((double) count);
        }
        throw new NoDataException();
    }

    public static double computeTopographicError(Iterable<double[]> data, Network net, DistanceMeasure distance) {
        int notAdjacentCount = 0;
        int count = 0;
        for (double[] f : data) {
            count++;
            Pair<Neuron, Neuron> p = findBestAndSecondBest(f, net, distance);
            if (!net.getNeighbours((Neuron) p.getFirst()).contains(p.getSecond())) {
                notAdjacentCount++;
            }
        }
        if (count != 0) {
            return ((double) notAdjacentCount) / ((double) count);
        }
        throw new NoDataException();
    }
}
