package org.apache.commons.math4.linear;

import java.util.Iterator;
import java.util.NoSuchElementException;
import org.apache.commons.math4.analysis.FunctionUtils;
import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.function.Add;
import org.apache.commons.math4.analysis.function.Divide;
import org.apache.commons.math4.analysis.function.Multiply;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;

public abstract class RealVector {

    protected class Entry {
        private int index;

        public Entry() {
            setIndex(0);
        }

        public double getValue() {
            return RealVector.this.getEntry(getIndex());
        }

        public void setValue(double value) {
            RealVector.this.setEntry(getIndex(), value);
        }

        public int getIndex() {
            return this.index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

    class 1 implements Iterator<Entry> {
        private Entry e;
        private int i;
        private final /* synthetic */ int val$dim;

        1(int i) {
            this.val$dim = i;
            this.i = 0;
            this.e = new Entry();
        }

        public boolean hasNext() {
            return this.i < this.val$dim;
        }

        public Entry next() {
            if (this.i < this.val$dim) {
                Entry entry = this.e;
                int i = this.i;
                this.i = i + 1;
                entry.setIndex(i);
                return this.e;
            }
            throw new NoSuchElementException();
        }

        public void remove() throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }
    }

    class 2 extends RealVector {
        private final /* synthetic */ RealVector val$v;

        class 1 implements Iterator<Entry> {
            private final UnmodifiableEntry e;
            private final /* synthetic */ Iterator val$i;

            1(Iterator it, RealVector realVector) {
                this.val$i = it;
                this.e = new UnmodifiableEntry(realVector);
            }

            public boolean hasNext() {
                return this.val$i.hasNext();
            }

            public Entry next() {
                this.e.setIndex(((Entry) this.val$i.next()).getIndex());
                return this.e;
            }

            public void remove() throws MathUnsupportedOperationException {
                throw new MathUnsupportedOperationException();
            }
        }

        class 2 implements Iterator<Entry> {
            private final UnmodifiableEntry e;
            private final /* synthetic */ Iterator val$i;

            2(Iterator it, RealVector realVector) {
                this.val$i = it;
                this.e = new UnmodifiableEntry(realVector);
            }

            public boolean hasNext() {
                return this.val$i.hasNext();
            }

            public Entry next() {
                this.e.setIndex(((Entry) this.val$i.next()).getIndex());
                return this.e;
            }

            public void remove() throws MathUnsupportedOperationException {
                throw new MathUnsupportedOperationException();
            }
        }

        class UnmodifiableEntry extends Entry {
            private final /* synthetic */ RealVector val$v;

            UnmodifiableEntry(RealVector realVector) {
                this.val$v = realVector;
                super();
            }

            public double getValue() {
                return this.val$v.getEntry(getIndex());
            }

            public void setValue(double value) throws MathUnsupportedOperationException {
                throw new MathUnsupportedOperationException();
            }
        }

        2(RealVector realVector) {
            this.val$v = realVector;
        }

        public RealVector mapToSelf(UnivariateFunction function) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealVector map(UnivariateFunction function) {
            return this.val$v.map(function);
        }

        public Iterator<Entry> iterator() {
            return new 1(this.val$v.iterator(), this.val$v);
        }

        public Iterator<Entry> sparseIterator() {
            return new 2(this.val$v.sparseIterator(), this.val$v);
        }

        public RealVector copy() {
            return this.val$v.copy();
        }

        public RealVector add(RealVector w) throws DimensionMismatchException {
            return this.val$v.add(w);
        }

        public RealVector subtract(RealVector w) throws DimensionMismatchException {
            return this.val$v.subtract(w);
        }

        public RealVector mapAdd(double d) {
            return this.val$v.mapAdd(d);
        }

        public RealVector mapAddToSelf(double d) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealVector mapSubtract(double d) {
            return this.val$v.mapSubtract(d);
        }

        public RealVector mapSubtractToSelf(double d) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealVector mapMultiply(double d) {
            return this.val$v.mapMultiply(d);
        }

        public RealVector mapMultiplyToSelf(double d) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealVector mapDivide(double d) {
            return this.val$v.mapDivide(d);
        }

        public RealVector mapDivideToSelf(double d) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealVector ebeMultiply(RealVector w) throws DimensionMismatchException {
            return this.val$v.ebeMultiply(w);
        }

        public RealVector ebeDivide(RealVector w) throws DimensionMismatchException {
            return this.val$v.ebeDivide(w);
        }

        public double dotProduct(RealVector w) throws DimensionMismatchException {
            return this.val$v.dotProduct(w);
        }

        public double cosine(RealVector w) throws DimensionMismatchException, MathArithmeticException {
            return this.val$v.cosine(w);
        }

        public double getNorm() {
            return this.val$v.getNorm();
        }

        public double getL1Norm() {
            return this.val$v.getL1Norm();
        }

        public double getLInfNorm() {
            return this.val$v.getLInfNorm();
        }

        public double getDistance(RealVector w) throws DimensionMismatchException {
            return this.val$v.getDistance(w);
        }

        public double getL1Distance(RealVector w) throws DimensionMismatchException {
            return this.val$v.getL1Distance(w);
        }

        public double getLInfDistance(RealVector w) throws DimensionMismatchException {
            return this.val$v.getLInfDistance(w);
        }

        public RealVector unitVector() throws MathArithmeticException {
            return this.val$v.unitVector();
        }

        public void unitize() throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public RealMatrix outerProduct(RealVector w) {
            return this.val$v.outerProduct(w);
        }

        public double getEntry(int index) throws OutOfRangeException {
            return this.val$v.getEntry(index);
        }

        public void setEntry(int index, double value) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public void addToEntry(int index, double value) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public int getDimension() {
            return this.val$v.getDimension();
        }

        public RealVector append(RealVector w) {
            return this.val$v.append(w);
        }

        public RealVector append(double d) {
            return this.val$v.append(d);
        }

        public RealVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
            return this.val$v.getSubVector(index, n);
        }

        public void setSubVector(int index, RealVector w) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public void set(double value) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }

        public double[] toArray() {
            return this.val$v.toArray();
        }

        public boolean isNaN() {
            return this.val$v.isNaN();
        }

        public boolean isInfinite() {
            return this.val$v.isInfinite();
        }

        public RealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
            return this.val$v.combine(a, b, y);
        }

        public RealVector combineToSelf(double a, double b, RealVector y) throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }
    }

    protected class SparseEntryIterator implements Iterator<Entry> {
        private Entry current;
        private final int dim;
        private Entry next;

        protected SparseEntryIterator() {
            this.dim = RealVector.this.getDimension();
            this.current = new Entry();
            this.next = new Entry();
            if (this.next.getValue() == 0.0d) {
                advance(this.next);
            }
        }

        protected void advance(Entry e) {
            if (e != null) {
                do {
                    e.setIndex(e.getIndex() + 1);
                    if (e.getIndex() >= this.dim) {
                        break;
                    }
                } while (e.getValue() == 0.0d);
                if (e.getIndex() >= this.dim) {
                    e.setIndex(-1);
                }
            }
        }

        public boolean hasNext() {
            return this.next.getIndex() >= 0;
        }

        public Entry next() {
            int index = this.next.getIndex();
            if (index < 0) {
                throw new NoSuchElementException();
            }
            this.current.setIndex(index);
            advance(this.next);
            return this.current;
        }

        public void remove() throws MathUnsupportedOperationException {
            throw new MathUnsupportedOperationException();
        }
    }

    public abstract RealVector append(double d);

    public abstract RealVector append(RealVector realVector);

    public abstract RealVector copy();

    public abstract RealVector ebeDivide(RealVector realVector) throws DimensionMismatchException;

    public abstract RealVector ebeMultiply(RealVector realVector) throws DimensionMismatchException;

    public abstract int getDimension();

    public abstract double getEntry(int i) throws OutOfRangeException;

    public abstract RealVector getSubVector(int i, int i2) throws NotPositiveException, OutOfRangeException;

    public abstract boolean isInfinite();

    public abstract boolean isNaN();

    public abstract void setEntry(int i, double d) throws OutOfRangeException;

    public abstract void setSubVector(int i, RealVector realVector) throws OutOfRangeException;

    public void addToEntry(int index, double increment) throws OutOfRangeException {
        setEntry(index, getEntry(index) + increment);
    }

    protected void checkVectorDimensions(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
    }

    protected void checkVectorDimensions(int n) throws DimensionMismatchException {
        int d = getDimension();
        if (d != n) {
            throw new DimensionMismatchException(d, n);
        }
    }

    protected void checkIndex(int index) throws OutOfRangeException {
        if (index < 0 || index >= getDimension()) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
        }
    }

    protected void checkIndices(int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        int dim = getDimension();
        if (start < 0 || start >= dim) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(dim - 1));
        } else if (end < 0 || end >= dim) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(end), Integer.valueOf(0), Integer.valueOf(dim - 1));
        } else if (end < start) {
            throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false);
        }
    }

    public RealVector add(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        RealVector result = v.copy();
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            int index = e.getIndex();
            result.setEntry(index, e.getValue() + result.getEntry(index));
        }
        return result;
    }

    public RealVector subtract(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        RealVector result = v.mapMultiply(-1.0d);
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            int index = e.getIndex();
            result.setEntry(index, e.getValue() + result.getEntry(index));
        }
        return result;
    }

    public RealVector mapAdd(double d) {
        return copy().mapAddToSelf(d);
    }

    public RealVector mapAddToSelf(double d) {
        if (d != 0.0d) {
            return mapToSelf(FunctionUtils.fix2ndArgument(new Add(), d));
        }
        return this;
    }

    public double dotProduct(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        double d = 0.0d;
        for (int i = 0; i < getDimension(); i++) {
            d += getEntry(i) * v.getEntry(i);
        }
        return d;
    }

    public double cosine(RealVector v) throws DimensionMismatchException, MathArithmeticException {
        double norm = getNorm();
        double vNorm = v.getNorm();
        if (norm != 0.0d && vNorm != 0.0d) {
            return dotProduct(v) / (norm * vNorm);
        }
        throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
    }

    public double getDistance(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        double d = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            double diff = e.getValue() - v.getEntry(e.getIndex());
            d += diff * diff;
        }
        return FastMath.sqrt(d);
    }

    public double getNorm() {
        double sum = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            double value = ((Entry) it.next()).getValue();
            sum += value * value;
        }
        return FastMath.sqrt(sum);
    }

    public double getL1Norm() {
        double norm = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            norm += FastMath.abs(((Entry) it.next()).getValue());
        }
        return norm;
    }

    public double getLInfNorm() {
        double norm = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            norm = FastMath.max(norm, FastMath.abs(((Entry) it.next()).getValue()));
        }
        return norm;
    }

    public double getL1Distance(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        double d = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            d += FastMath.abs(e.getValue() - v.getEntry(e.getIndex()));
        }
        return d;
    }

    public double getLInfDistance(RealVector v) throws DimensionMismatchException {
        checkVectorDimensions(v);
        double d = 0.0d;
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            d = FastMath.max(FastMath.abs(e.getValue() - v.getEntry(e.getIndex())), d);
        }
        return d;
    }

    public int getMinIndex() {
        int minIndex = -1;
        double minValue = Double.POSITIVE_INFINITY;
        Iterator<Entry> iterator = iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (entry.getValue() <= minValue) {
                minIndex = entry.getIndex();
                minValue = entry.getValue();
            }
        }
        return minIndex;
    }

    public double getMinValue() {
        int minIndex = getMinIndex();
        return minIndex < 0 ? Double.NaN : getEntry(minIndex);
    }

    public int getMaxIndex() {
        int maxIndex = -1;
        double maxValue = Double.NEGATIVE_INFINITY;
        Iterator<Entry> iterator = iterator();
        while (iterator.hasNext()) {
            Entry entry = (Entry) iterator.next();
            if (entry.getValue() >= maxValue) {
                maxIndex = entry.getIndex();
                maxValue = entry.getValue();
            }
        }
        return maxIndex;
    }

    public double getMaxValue() {
        int maxIndex = getMaxIndex();
        return maxIndex < 0 ? Double.NaN : getEntry(maxIndex);
    }

    public RealVector mapMultiply(double d) {
        return copy().mapMultiplyToSelf(d);
    }

    public RealVector mapMultiplyToSelf(double d) {
        return mapToSelf(FunctionUtils.fix2ndArgument(new Multiply(), d));
    }

    public RealVector mapSubtract(double d) {
        return copy().mapSubtractToSelf(d);
    }

    public RealVector mapSubtractToSelf(double d) {
        return mapAddToSelf(-d);
    }

    public RealVector mapDivide(double d) {
        return copy().mapDivideToSelf(d);
    }

    public RealVector mapDivideToSelf(double d) {
        return mapToSelf(FunctionUtils.fix2ndArgument(new Divide(), d));
    }

    public RealMatrix outerProduct(RealVector v) {
        RealMatrix product;
        int m = getDimension();
        int n = v.getDimension();
        if ((v instanceof SparseRealVector) || (this instanceof SparseRealVector)) {
            product = new OpenMapRealMatrix(m, n);
        } else {
            product = new Array2DRowRealMatrix(m, n);
        }
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                product.setEntry(i, j, getEntry(i) * v.getEntry(j));
            }
        }
        return product;
    }

    public RealVector projection(RealVector v) throws DimensionMismatchException, MathArithmeticException {
        if (v.dotProduct(v) != 0.0d) {
            return v.mapMultiply(dotProduct(v) / v.dotProduct(v));
        }
        throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
    }

    public void set(double value) {
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            ((Entry) it.next()).setValue(value);
        }
    }

    public double[] toArray() {
        int dim = getDimension();
        double[] values = new double[dim];
        for (int i = 0; i < dim; i++) {
            values[i] = getEntry(i);
        }
        return values;
    }

    public RealVector unitVector() throws MathArithmeticException {
        double norm = getNorm();
        if (norm != 0.0d) {
            return mapDivide(norm);
        }
        throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
    }

    public void unitize() throws MathArithmeticException {
        if (getNorm() == 0.0d) {
            throw new MathArithmeticException(LocalizedFormats.ZERO_NORM, new Object[0]);
        }
        mapDivideToSelf(getNorm());
    }

    public Iterator<Entry> sparseIterator() {
        return new SparseEntryIterator();
    }

    public Iterator<Entry> iterator() {
        return new 1(getDimension());
    }

    public RealVector map(UnivariateFunction function) {
        return copy().mapToSelf(function);
    }

    public RealVector mapToSelf(UnivariateFunction function) {
        Iterator<Entry> it = iterator();
        while (it.hasNext()) {
            Entry e = (Entry) it.next();
            e.setValue(function.value(e.getValue()));
        }
        return this;
    }

    public RealVector combine(double a, double b, RealVector y) throws DimensionMismatchException {
        return copy().combineToSelf(a, b, y);
    }

    public RealVector combineToSelf(double a, double b, RealVector y) throws DimensionMismatchException {
        checkVectorDimensions(y);
        for (int i = 0; i < getDimension(); i++) {
            setEntry(i, (a * getEntry(i)) + (b * y.getEntry(i)));
        }
        return this;
    }

    public double walkInDefaultOrder(RealVectorPreservingVisitor visitor) {
        int dim = getDimension();
        visitor.start(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            visitor.visit(i, getEntry(i));
        }
        return visitor.end();
    }

    public double walkInDefaultOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        checkIndices(start, end);
        visitor.start(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            visitor.visit(i, getEntry(i));
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor) {
        return walkInDefaultOrder(visitor);
    }

    public double walkInOptimizedOrder(RealVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInDefaultOrder(visitor, start, end);
    }

    public double walkInDefaultOrder(RealVectorChangingVisitor visitor) {
        int dim = getDimension();
        visitor.start(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            setEntry(i, visitor.visit(i, getEntry(i)));
        }
        return visitor.end();
    }

    public double walkInDefaultOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        checkIndices(start, end);
        visitor.start(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            setEntry(i, visitor.visit(i, getEntry(i)));
        }
        return visitor.end();
    }

    public double walkInOptimizedOrder(RealVectorChangingVisitor visitor) {
        return walkInDefaultOrder(visitor);
    }

    public double walkInOptimizedOrder(RealVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInDefaultOrder(visitor, start, end);
    }

    public boolean equals(Object other) throws MathUnsupportedOperationException {
        throw new MathUnsupportedOperationException();
    }

    public int hashCode() throws MathUnsupportedOperationException {
        throw new MathUnsupportedOperationException();
    }

    public static RealVector unmodifiableRealVector(RealVector v) {
        return new 2(v);
    }
}
