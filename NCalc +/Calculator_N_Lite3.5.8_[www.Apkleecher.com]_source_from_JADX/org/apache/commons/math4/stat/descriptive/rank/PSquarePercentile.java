package org.apache.commons.math4.stat.descriptive.rank;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.apache.commons.math4.analysis.interpolation.LinearInterpolator;
import org.apache.commons.math4.analysis.interpolation.NevilleInterpolator;
import org.apache.commons.math4.analysis.interpolation.UnivariateInterpolator;
import org.apache.commons.math4.exception.InsufficientDataException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.Localizable;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.stat.descriptive.AbstractStorelessUnivariateStatistic;
import org.apache.commons.math4.stat.descriptive.StorelessUnivariateStatistic;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;
import org.apache.commons.math4.util.Precision;

public class PSquarePercentile extends AbstractStorelessUnivariateStatistic implements StorelessUnivariateStatistic, Serializable {
    private static final DecimalFormat DECIMAL_FORMAT;
    private static final double DEFAULT_QUANTILE_DESIRED = 50.0d;
    private static final int PSQUARE_CONSTANT = 5;
    private static final long serialVersionUID = 20150412;
    private long countOfObservations;
    private final List<Double> initialFive;
    private transient double lastObservation;
    private PSquareMarkers markers;
    private double pValue;
    private final double quantile;

    private static class FixedCapacityList<E> extends ArrayList<E> implements Serializable {
        private static final long serialVersionUID = 2283952083075725479L;
        private final int capacity;

        public FixedCapacityList(int fixedCapacity) {
            super(fixedCapacity);
            this.capacity = fixedCapacity;
        }

        public boolean add(E e) {
            return size() < this.capacity ? super.add(e) : false;
        }

        public boolean addAll(Collection<? extends E> collection) {
            boolean isCollectionLess;
            if (collection == null || collection.size() + size() > this.capacity) {
                isCollectionLess = false;
            } else {
                isCollectionLess = true;
            }
            if (isCollectionLess) {
                return super.addAll(collection);
            }
            return false;
        }
    }

    private static class Marker implements Serializable, Cloneable {
        private static final long serialVersionUID = -3575879478288538431L;
        private double desiredMarkerIncrement;
        private double desiredMarkerPosition;
        private int index;
        private double intMarkerPosition;
        private transient UnivariateInterpolator linear;
        private double markerHeight;
        private transient Marker next;
        private final UnivariateInterpolator nonLinear;
        private transient Marker previous;

        private Marker() {
            this.nonLinear = new NevilleInterpolator();
            this.linear = new LinearInterpolator();
            this.previous = this;
            this.next = this;
        }

        private Marker(double heightOfMarker, double makerPositionDesired, double markerPositionIncrement, double markerPositionNumber) {
            this();
            this.markerHeight = heightOfMarker;
            this.desiredMarkerPosition = makerPositionDesired;
            this.desiredMarkerIncrement = markerPositionIncrement;
            this.intMarkerPosition = markerPositionNumber;
        }

        private Marker previous(Marker previousMarker) {
            MathUtils.checkNotNull(previousMarker);
            this.previous = previousMarker;
            return this;
        }

        private Marker next(Marker nextMarker) {
            MathUtils.checkNotNull(nextMarker);
            this.next = nextMarker;
            return this;
        }

        private Marker index(int indexOfMarker) {
            this.index = indexOfMarker;
            return this;
        }

        private void updateDesiredPosition() {
            this.desiredMarkerPosition += this.desiredMarkerIncrement;
        }

        private void incrementPosition(int d) {
            this.intMarkerPosition += (double) d;
        }

        private double difference() {
            return this.desiredMarkerPosition - this.intMarkerPosition;
        }

        private double estimate() {
            double di = difference();
            boolean isNextHigher = this.next.intMarkerPosition - this.intMarkerPosition > 1.0d;
            boolean isPreviousLower = this.previous.intMarkerPosition - this.intMarkerPosition < -1.0d;
            if ((di >= 1.0d && isNextHigher) || (di <= -1.0d && isPreviousLower)) {
                int d = di >= 0.0d ? 1 : -1;
                xval = new double[3];
                xval[0] = this.previous.intMarkerPosition;
                xval[1] = this.intMarkerPosition;
                xval[2] = this.next.intMarkerPosition;
                yval = new double[3];
                yval[0] = this.previous.markerHeight;
                yval[1] = this.markerHeight;
                yval[2] = this.next.markerHeight;
                double xD = this.intMarkerPosition + ((double) d);
                this.markerHeight = this.nonLinear.interpolate(xval, yval).value(xD);
                if (isEstimateBad(yval, this.markerHeight)) {
                    int delta = xD - xval[1] > 0.0d ? 1 : -1;
                    double[] xBad = new double[]{xval[1], xval[delta + 1]};
                    MathArrays.sortInPlace(xBad, new double[][]{new double[]{yval[1], yval[delta + 1]}});
                    this.markerHeight = this.linear.interpolate(xBad, yBad).value(xD);
                }
                incrementPosition(d);
            }
            return this.markerHeight;
        }

        private boolean isEstimateBad(double[] y, double yD) {
            return yD <= y[0] || yD >= y[2];
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof Marker)) {
                return false;
            }
            boolean result;
            Marker that = (Marker) o;
            if ((Double.compare(this.markerHeight, that.markerHeight) == 0) && Double.compare(this.intMarkerPosition, that.intMarkerPosition) == 0) {
                result = true;
            } else {
                result = false;
            }
            if (result && Double.compare(this.desiredMarkerPosition, that.desiredMarkerPosition) == 0) {
                result = true;
            } else {
                result = false;
            }
            if (result && Double.compare(this.desiredMarkerIncrement, that.desiredMarkerIncrement) == 0) {
                result = true;
            } else {
                result = false;
            }
            if (result && this.next.index == that.next.index) {
                result = true;
            } else {
                result = false;
            }
            if (result && this.previous.index == that.previous.index) {
                result = true;
            } else {
                result = false;
            }
            return result;
        }

        public int hashCode() {
            return Arrays.hashCode(new double[]{this.markerHeight, this.intMarkerPosition, this.desiredMarkerIncrement, this.desiredMarkerPosition, (double) this.previous.index, (double) this.next.index});
        }

        private void readObject(ObjectInputStream anInstream) throws ClassNotFoundException, IOException {
            anInstream.defaultReadObject();
            this.next = this;
            this.previous = this;
            this.linear = new LinearInterpolator();
        }

        public Object clone() {
            return new Marker(this.markerHeight, this.desiredMarkerPosition, this.desiredMarkerIncrement, this.intMarkerPosition);
        }

        public String toString() {
            return String.format("index=%.0f,n=%.0f,np=%.2f,q=%.2f,dn=%.2f,prev=%d,next=%d", new Object[]{Double.valueOf((double) this.index), Double.valueOf(Precision.round(this.intMarkerPosition, 0)), Double.valueOf(Precision.round(this.desiredMarkerPosition, 2)), Double.valueOf(Precision.round(this.markerHeight, 2)), Double.valueOf(Precision.round(this.desiredMarkerIncrement, 2)), Integer.valueOf(this.previous.index), Integer.valueOf(this.next.index)});
        }
    }

    protected interface PSquareMarkers extends Cloneable {
        Object clone();

        double estimate(int i);

        double getPercentileValue();

        double height(int i);

        double processDataPoint(double d);
    }

    private static class Markers implements PSquareMarkers, Serializable {
        private static final int HIGH = 4;
        private static final int LOW = 2;
        private static final long serialVersionUID = 1;
        private transient int k;
        private final Marker[] markerArray;

        private Markers(Marker[] theMarkerArray) {
            this.k = -1;
            MathUtils.checkNotNull(theMarkerArray);
            this.markerArray = theMarkerArray;
            for (int i = 1; i < PSquarePercentile.PSQUARE_CONSTANT; i++) {
                this.markerArray[i].previous(this.markerArray[i - 1]).next(this.markerArray[i + 1]).index(i);
            }
            this.markerArray[0].previous(this.markerArray[0]).next(this.markerArray[1]).index(0);
            this.markerArray[PSquarePercentile.PSQUARE_CONSTANT].previous(this.markerArray[HIGH]).next(this.markerArray[PSquarePercentile.PSQUARE_CONSTANT]).index(PSquarePercentile.PSQUARE_CONSTANT);
        }

        private Markers(List<Double> initialFive, double p) {
            this(createMarkerArray(initialFive, p));
        }

        private static Marker[] createMarkerArray(List<Double> initialFive, double p) {
            int countObserved = initialFive == null ? -1 : initialFive.size();
            if (countObserved < PSquarePercentile.PSQUARE_CONSTANT) {
                Localizable localizable = LocalizedFormats.INSUFFICIENT_OBSERVED_POINTS_IN_SAMPLE;
                Object[] objArr = new Object[LOW];
                objArr[0] = Integer.valueOf(countObserved);
                objArr[1] = Integer.valueOf(PSquarePercentile.PSQUARE_CONSTANT);
                throw new InsufficientDataException(localizable, objArr);
            }
            Collections.sort(initialFive);
            return new Marker[]{new Marker(), new Marker(1.0d, 0.0d, 1.0d, null), new Marker(1.0d + (2.0d * p), p / 2.0d, 2.0d, null), new Marker(1.0d + (4.0d * p), p, 3.0d, null), new Marker(3.0d + (2.0d * p), (1.0d + p) / 2.0d, 4.0d, null), new Marker(5.0d, 1.0d, 5.0d, null)};
        }

        public int hashCode() {
            return Arrays.deepHashCode(this.markerArray);
        }

        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || !(o instanceof Markers)) {
                return false;
            }
            return Arrays.deepEquals(this.markerArray, ((Markers) o).markerArray);
        }

        public double processDataPoint(double inputDataPoint) {
            incrementPositions(1, findCellAndUpdateMinMax(inputDataPoint) + 1, PSquarePercentile.PSQUARE_CONSTANT);
            updateDesiredPositions();
            adjustHeightsOfMarkers();
            return getPercentileValue();
        }

        public double getPercentileValue() {
            return height(3);
        }

        private int findCellAndUpdateMinMax(double observation) {
            this.k = -1;
            if (observation < height(1)) {
                this.markerArray[1].markerHeight = observation;
                this.k = 1;
            } else if (observation < height(LOW)) {
                this.k = 1;
            } else if (observation < height(3)) {
                this.k = LOW;
            } else if (observation < height(HIGH)) {
                this.k = 3;
            } else if (observation <= height(PSquarePercentile.PSQUARE_CONSTANT)) {
                this.k = HIGH;
            } else {
                this.markerArray[PSquarePercentile.PSQUARE_CONSTANT].markerHeight = observation;
                this.k = HIGH;
            }
            return this.k;
        }

        private void adjustHeightsOfMarkers() {
            for (int i = LOW; i <= HIGH; i++) {
                estimate(i);
            }
        }

        public double estimate(int index) {
            if (index >= LOW && index <= HIGH) {
                return this.markerArray[index].estimate();
            }
            throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(LOW), Integer.valueOf(HIGH));
        }

        private void incrementPositions(int d, int startIndex, int endIndex) {
            for (int i = startIndex; i <= endIndex; i++) {
                this.markerArray[i].incrementPosition(d);
            }
        }

        private void updateDesiredPositions() {
            for (int i = 1; i < this.markerArray.length; i++) {
                this.markerArray[i].updateDesiredPosition();
            }
        }

        private void readObject(ObjectInputStream anInputStream) throws ClassNotFoundException, IOException {
            anInputStream.defaultReadObject();
            for (int i = 1; i < PSquarePercentile.PSQUARE_CONSTANT; i++) {
                this.markerArray[i].previous(this.markerArray[i - 1]).next(this.markerArray[i + 1]).index(i);
            }
            this.markerArray[0].previous(this.markerArray[0]).next(this.markerArray[1]).index(0);
            this.markerArray[PSquarePercentile.PSQUARE_CONSTANT].previous(this.markerArray[HIGH]).next(this.markerArray[PSquarePercentile.PSQUARE_CONSTANT]).index(PSquarePercentile.PSQUARE_CONSTANT);
        }

        public double height(int markerIndex) {
            if (markerIndex < this.markerArray.length && markerIndex > 0) {
                return this.markerArray[markerIndex].markerHeight;
            }
            throw new OutOfRangeException(Integer.valueOf(markerIndex), Integer.valueOf(1), Integer.valueOf(this.markerArray.length));
        }

        public Object clone() {
            return new Markers(new Marker[]{new Marker(), (Marker) this.markerArray[1].clone(), (Marker) this.markerArray[LOW].clone(), (Marker) this.markerArray[3].clone(), (Marker) this.markerArray[HIGH].clone(), (Marker) this.markerArray[PSquarePercentile.PSQUARE_CONSTANT].clone()});
        }

        public String toString() {
            Object[] objArr = new Object[PSquarePercentile.PSQUARE_CONSTANT];
            objArr[0] = this.markerArray[1].toString();
            objArr[1] = this.markerArray[LOW].toString();
            objArr[LOW] = this.markerArray[3].toString();
            objArr[3] = this.markerArray[HIGH].toString();
            objArr[HIGH] = this.markerArray[PSquarePercentile.PSQUARE_CONSTANT].toString();
            return String.format("m1=[%s],m2=[%s],m3=[%s],m4=[%s],m5=[%s]", objArr);
        }
    }

    static {
        DECIMAL_FORMAT = new DecimalFormat("00.00");
    }

    public PSquarePercentile(double p) {
        this.initialFive = new FixedCapacityList(PSQUARE_CONSTANT);
        this.markers = null;
        this.pValue = Double.NaN;
        if (p > 100.0d || p < 0.0d) {
            throw new OutOfRangeException(LocalizedFormats.OUT_OF_RANGE, Double.valueOf(p), Integer.valueOf(0), Integer.valueOf(100));
        }
        this.quantile = p / 100.0d;
    }

    PSquarePercentile() {
        this(DEFAULT_QUANTILE_DESIRED);
    }

    public int hashCode() {
        double result = getResult();
        if (Double.isNaN(result)) {
            result = 37.0d;
        }
        double markersHash = (double) (this.markers == null ? 0 : this.markers.hashCode());
        return Arrays.hashCode(new double[]{result, this.quantile, markersHash, (double) this.countOfObservations});
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || !(o instanceof PSquarePercentile)) {
            return false;
        }
        boolean isNotNull;
        boolean isNull;
        boolean result;
        PSquarePercentile that = (PSquarePercentile) o;
        if (this.markers == null || that.markers == null) {
            isNotNull = false;
        } else {
            isNotNull = true;
        }
        if (this.markers == null && that.markers == null) {
            isNull = true;
        } else {
            isNull = false;
        }
        if (isNotNull) {
            result = this.markers.equals(that.markers);
        } else {
            result = isNull;
        }
        if (result && getN() == that.getN()) {
            result = true;
        } else {
            result = false;
        }
        return result;
    }

    public void increment(double observation) {
        this.countOfObservations++;
        this.lastObservation = observation;
        if (this.markers == null) {
            if (this.initialFive.add(Double.valueOf(observation))) {
                Collections.sort(this.initialFive);
                this.pValue = ((Double) this.initialFive.get((int) (this.quantile * ((double) (this.initialFive.size() - 1))))).doubleValue();
                return;
            }
            this.markers = newMarkers(this.initialFive, this.quantile);
        }
        this.pValue = this.markers.processDataPoint(observation);
    }

    public String toString() {
        if (this.markers == null) {
            return String.format("obs=%s pValue=%s", new Object[]{DECIMAL_FORMAT.format(this.lastObservation), DECIMAL_FORMAT.format(this.pValue)});
        }
        return String.format("obs=%s markers=%s", new Object[]{DECIMAL_FORMAT.format(this.lastObservation), this.markers.toString()});
    }

    public long getN() {
        return this.countOfObservations;
    }

    public StorelessUnivariateStatistic copy() {
        PSquarePercentile copy = new PSquarePercentile(100.0d * this.quantile);
        if (this.markers != null) {
            copy.markers = (PSquareMarkers) this.markers.clone();
        }
        copy.countOfObservations = this.countOfObservations;
        copy.pValue = this.pValue;
        copy.initialFive.clear();
        copy.initialFive.addAll(this.initialFive);
        return copy;
    }

    public double quantile() {
        return this.quantile;
    }

    public void clear() {
        this.markers = null;
        this.initialFive.clear();
        this.countOfObservations = 0;
        this.pValue = Double.NaN;
    }

    public double getResult() {
        if (Double.compare(this.quantile, 1.0d) == 0) {
            this.pValue = maximum();
        } else if (Double.compare(this.quantile, 0.0d) == 0) {
            this.pValue = minimum();
        }
        return this.pValue;
    }

    private double maximum() {
        if (this.markers != null) {
            return this.markers.height(PSQUARE_CONSTANT);
        }
        if (this.initialFive.isEmpty()) {
            return Double.NaN;
        }
        return ((Double) this.initialFive.get(this.initialFive.size() - 1)).doubleValue();
    }

    private double minimum() {
        if (this.markers != null) {
            return this.markers.height(1);
        }
        if (this.initialFive.isEmpty()) {
            return Double.NaN;
        }
        return ((Double) this.initialFive.get(0)).doubleValue();
    }

    public static PSquareMarkers newMarkers(List<Double> initialFive, double p) {
        return new Markers(p, null);
    }
}
