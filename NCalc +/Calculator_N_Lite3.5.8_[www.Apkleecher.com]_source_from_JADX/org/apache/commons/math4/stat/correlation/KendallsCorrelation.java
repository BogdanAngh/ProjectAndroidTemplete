package org.apache.commons.math4.stat.correlation;

import java.util.Arrays;
import java.util.Comparator;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.linear.BlockRealMatrix;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.RealMatrix;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Pair;

public class KendallsCorrelation {
    private final RealMatrix correlationMatrix;

    class 1 implements Comparator<Pair<Double, Double>> {
        1() {
        }

        public int compare(Pair<Double, Double> pair1, Pair<Double, Double> pair2) {
            int compareFirst = ((Double) pair1.getFirst()).compareTo((Double) pair2.getFirst());
            return compareFirst != 0 ? compareFirst : ((Double) pair1.getSecond()).compareTo((Double) pair2.getSecond());
        }
    }

    public KendallsCorrelation() {
        this.correlationMatrix = null;
    }

    public KendallsCorrelation(double[][] data) {
        this(MatrixUtils.createRealMatrix(data));
    }

    public KendallsCorrelation(RealMatrix matrix) {
        this.correlationMatrix = computeCorrelationMatrix(matrix);
    }

    public RealMatrix getCorrelationMatrix() {
        return this.correlationMatrix;
    }

    public RealMatrix computeCorrelationMatrix(RealMatrix matrix) {
        int nVars = matrix.getColumnDimension();
        RealMatrix outMatrix = new BlockRealMatrix(nVars, nVars);
        for (int i = 0; i < nVars; i++) {
            for (int j = 0; j < i; j++) {
                double corr = correlation(matrix.getColumn(i), matrix.getColumn(j));
                outMatrix.setEntry(i, j, corr);
                outMatrix.setEntry(j, i, corr);
            }
            outMatrix.setEntry(i, i, 1.0d);
        }
        return outMatrix;
    }

    public RealMatrix computeCorrelationMatrix(double[][] matrix) {
        return computeCorrelationMatrix(new BlockRealMatrix(matrix));
    }

    public double correlation(double[] xArray, double[] yArray) throws DimensionMismatchException {
        if (xArray.length != yArray.length) {
            throw new DimensionMismatchException(xArray.length, yArray.length);
        }
        int i;
        int n = xArray.length;
        long numPairs = sum((long) (n - 1));
        Pair[] pairs = new Pair[n];
        for (i = 0; i < n; i++) {
            pairs[i] = new Pair(Double.valueOf(xArray[i]), Double.valueOf(yArray[i]));
        }
        Arrays.sort(pairs, new 1());
        long tiedXPairs = 0;
        long tiedXYPairs = 0;
        long consecutiveXTies = 1;
        long consecutiveXYTies = 1;
        Pair<Double, Double> prev = pairs[0];
        for (i = 1; i < n; i++) {
            Pair<Double, Double> curr = pairs[i];
            if (((Double) curr.getFirst()).equals(prev.getFirst())) {
                consecutiveXTies++;
                if (((Double) curr.getSecond()).equals(prev.getSecond())) {
                    consecutiveXYTies++;
                } else {
                    tiedXYPairs += sum(consecutiveXYTies - 1);
                    consecutiveXYTies = 1;
                }
            } else {
                tiedXPairs += sum(consecutiveXTies - 1);
                consecutiveXTies = 1;
                tiedXYPairs += sum(consecutiveXYTies - 1);
                consecutiveXYTies = 1;
            }
            prev = curr;
        }
        tiedXPairs += sum(consecutiveXTies - 1);
        tiedXYPairs += sum(consecutiveXYTies - 1);
        int swaps = 0;
        Pair[] pairsDestination = new Pair[n];
        for (int segmentSize = 1; segmentSize < n; segmentSize <<= 1) {
            for (int offset = 0; offset < n; offset += segmentSize * 2) {
                i = offset;
                int iEnd = FastMath.min(i + segmentSize, n);
                int j = iEnd;
                int jEnd = FastMath.min(j + segmentSize, n);
                int copyLocation = offset;
                while (true) {
                    if (i >= iEnd && j >= jEnd) {
                        break;
                    }
                    if (i >= iEnd) {
                        pairsDestination[copyLocation] = pairs[j];
                        j++;
                    } else if (j >= jEnd) {
                        pairsDestination[copyLocation] = pairs[i];
                        i++;
                    } else if (((Double) pairs[i].getSecond()).compareTo((Double) pairs[j].getSecond()) <= 0) {
                        pairsDestination[copyLocation] = pairs[i];
                        i++;
                    } else {
                        pairsDestination[copyLocation] = pairs[j];
                        j++;
                        swaps += iEnd - i;
                    }
                    copyLocation++;
                }
            }
            Pair[] pairsTemp = pairs;
            pairs = pairsDestination;
            pairsDestination = pairsTemp;
        }
        long tiedYPairs = 0;
        long consecutiveYTies = 1;
        prev = pairs[0];
        for (i = 1; i < n; i++) {
            curr = pairs[i];
            if (((Double) curr.getSecond()).equals(prev.getSecond())) {
                consecutiveYTies++;
            } else {
                tiedYPairs += sum(consecutiveYTies - 1);
                consecutiveYTies = 1;
            }
            prev = curr;
        }
        tiedYPairs += sum(consecutiveYTies - 1);
        long concordantMinusDiscordant = (((numPairs - tiedXPairs) - tiedYPairs) + tiedXYPairs) - ((long) (swaps * 2));
        return ((double) concordantMinusDiscordant) / FastMath.sqrt(((double) (numPairs - tiedXPairs)) * ((double) (numPairs - tiedYPairs)));
    }

    private static long sum(long n) {
        return ((1 + n) * n) / 2;
    }
}
