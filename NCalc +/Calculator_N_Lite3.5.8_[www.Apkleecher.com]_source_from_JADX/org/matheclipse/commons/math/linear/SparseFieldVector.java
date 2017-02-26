package org.matheclipse.commons.math.linear;

import java.io.Serializable;
import org.apache.commons.math4.Field;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathUtils;
import org.matheclipse.commons.math.linear.OpenIntToIExpr.Iterator;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

public class SparseFieldVector implements FieldVector, Serializable {
    private static final long serialVersionUID = 7841233292190413362L;
    private final OpenIntToIExpr entries;
    private final int virtualSize;

    public SparseFieldVector(int dimension) {
        this.virtualSize = dimension;
        this.entries = new OpenIntToIExpr();
    }

    protected SparseFieldVector(SparseFieldVector v, int resize) {
        this.virtualSize = v.getDimension() + resize;
        this.entries = new OpenIntToIExpr(v.entries);
    }

    public SparseFieldVector(Field<IExpr> field, int dimension, int expectedSize) {
        this.virtualSize = dimension;
        this.entries = new OpenIntToIExpr(expectedSize);
    }

    public SparseFieldVector(Field<IExpr> field, IExpr[] values) throws NullArgumentException {
        MathUtils.checkNotNull(values);
        this.virtualSize = values.length;
        this.entries = new OpenIntToIExpr();
        for (int key = 0; key < values.length; key++) {
            this.entries.put(key, values[key]);
        }
    }

    public SparseFieldVector(SparseFieldVector v) {
        this.virtualSize = v.getDimension();
        this.entries = new OpenIntToIExpr(v.getEntries());
    }

    private OpenIntToIExpr getEntries() {
        return this.entries;
    }

    public FieldVector add(SparseFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
        SparseFieldVector res = (SparseFieldVector) copy();
        Iterator iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            IExpr value = iter.value();
            if (this.entries.containsKey(key)) {
                res.setEntry(key, this.entries.get(key).plus(value));
            } else {
                res.setEntry(key, value);
            }
        }
        return res;
    }

    public FieldVector append(SparseFieldVector v) {
        SparseFieldVector res = new SparseFieldVector(this, v.getDimension());
        Iterator iter = v.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key() + this.virtualSize, iter.value());
        }
        return res;
    }

    public FieldVector append(FieldVector v) {
        if (v instanceof SparseFieldVector) {
            return append((SparseFieldVector) v);
        }
        int n = v.getDimension();
        FieldVector res = new SparseFieldVector(this, n);
        for (int i = 0; i < n; i++) {
            res.setEntry(this.virtualSize + i, v.getEntry(i));
        }
        return res;
    }

    public FieldVector append(IExpr d) throws NullArgumentException {
        MathUtils.checkNotNull(d);
        FieldVector res = new SparseFieldVector(this, 1);
        res.setEntry(this.virtualSize, d);
        return res;
    }

    public FieldVector copy() {
        return new SparseFieldVector(this);
    }

    public IExpr dotProduct(FieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
        IExpr res = F.C0;
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res = res.plus(v.getEntry(iter.key()).times(iter.value()));
        }
        return res;
    }

    public FieldVector ebeDivide(FieldVector v) throws DimensionMismatchException, MathArithmeticException {
        checkVectorDimensions(v.getDimension());
        SparseFieldVector res = new SparseFieldVector(this);
        Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().divide(v.getEntry(iter.key())));
        }
        return res;
    }

    public FieldVector ebeMultiply(FieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
        SparseFieldVector res = new SparseFieldVector(this);
        Iterator iter = res.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res.setEntry(iter.key(), iter.value().times(v.getEntry(iter.key())));
        }
        return res;
    }

    @Deprecated
    public IExpr[] getData() {
        return toArray();
    }

    public int getDimension() {
        return this.virtualSize;
    }

    public IExpr getEntry(int index) throws OutOfRangeException {
        checkIndex(index);
        return this.entries.get(index);
    }

    public Field<IExpr> getField() {
        return null;
    }

    public FieldVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
        if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
        }
        checkIndex(index);
        checkIndex((index + n) - 1);
        SparseFieldVector res = new SparseFieldVector(n);
        int end = index + n;
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (key >= index && key < end) {
                res.setEntry(key - index, iter.value());
            }
        }
        return res;
    }

    public FieldVector mapAdd(IExpr d) throws NullArgumentException {
        return copy().mapAddToSelf(d);
    }

    public FieldVector mapAddToSelf(IExpr d) throws NullArgumentException {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, getEntry(i).plus(d));
        }
        return this;
    }

    public FieldVector mapDivide(IExpr d) throws NullArgumentException, MathArithmeticException {
        return copy().mapDivideToSelf(d);
    }

    public FieldVector mapDivideToSelf(IExpr d) throws NullArgumentException, MathArithmeticException {
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.entries.put(iter.key(), iter.value().divide(d));
        }
        return this;
    }

    public FieldVector mapInv() throws MathArithmeticException {
        return copy().mapInvToSelf();
    }

    public FieldVector mapInvToSelf() throws MathArithmeticException {
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, F.C1.divide(getEntry(i)));
        }
        return this;
    }

    public FieldVector mapMultiply(IExpr d) throws NullArgumentException {
        return copy().mapMultiplyToSelf(d);
    }

    public FieldVector mapMultiplyToSelf(IExpr d) throws NullArgumentException {
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            this.entries.put(iter.key(), iter.value().times(d));
        }
        return this;
    }

    public FieldVector mapSubtract(IExpr d) throws NullArgumentException {
        return copy().mapSubtractToSelf(d);
    }

    public FieldVector mapSubtractToSelf(IExpr d) throws NullArgumentException {
        return mapAddToSelf(F.C0.subtract(d));
    }

    public FieldMatrix outerProduct(SparseFieldVector v) {
        SparseFieldMatrix res = new SparseFieldMatrix(this.virtualSize, v.getDimension());
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            Iterator iter2 = v.entries.iterator();
            while (iter2.hasNext()) {
                iter2.advance();
                res.setEntry(iter.key(), iter2.key(), iter.value().times(iter2.value()));
            }
        }
        return res;
    }

    public FieldMatrix outerProduct(FieldVector v) {
        if (v instanceof SparseFieldVector) {
            return outerProduct((SparseFieldVector) v);
        }
        int n = v.getDimension();
        FieldMatrix res = new SparseFieldMatrix(this.virtualSize, n);
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            int row = iter.key();
            IExpr value = iter.value();
            for (int col = 0; col < n; col++) {
                res.setEntry(row, col, value.times(v.getEntry(col)));
            }
        }
        return res;
    }

    public FieldVector projection(FieldVector v) throws DimensionMismatchException, MathArithmeticException {
        checkVectorDimensions(v.getDimension());
        return v.mapMultiply(dotProduct(v).divide(v.dotProduct(v)));
    }

    public void set(IExpr value) {
        MathUtils.checkNotNull(value);
        for (int i = 0; i < this.virtualSize; i++) {
            setEntry(i, value);
        }
    }

    public void setEntry(int index, IExpr value) throws NullArgumentException, OutOfRangeException {
        MathUtils.checkNotNull(value);
        checkIndex(index);
        this.entries.put(index, value);
    }

    public void setSubVector(int index, FieldVector v) throws OutOfRangeException {
        checkIndex(index);
        checkIndex((v.getDimension() + index) - 1);
        int n = v.getDimension();
        for (int i = 0; i < n; i++) {
            setEntry(i + index, v.getEntry(i));
        }
    }

    public SparseFieldVector subtract(SparseFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
        SparseFieldVector res = (SparseFieldVector) copy();
        Iterator iter = v.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            int key = iter.key();
            if (this.entries.containsKey(key)) {
                res.setEntry(key, (IExpr) this.entries.get(key).subtract(iter.value()));
            } else {
                res.setEntry(key, F.C0.subtract(iter.value()));
            }
        }
        return res;
    }

    public FieldVector subtract(FieldVector v) throws DimensionMismatchException {
        if (v instanceof SparseFieldVector) {
            return subtract((SparseFieldVector) v);
        }
        int n = v.getDimension();
        checkVectorDimensions(n);
        FieldVector res = new SparseFieldVector(this);
        for (int i = 0; i < n; i++) {
            if (this.entries.containsKey(i)) {
                res.setEntry(i, (IExpr) this.entries.get(i).subtract(v.getEntry(i)));
            } else {
                res.setEntry(i, F.C0.subtract(v.getEntry(i)));
            }
        }
        return res;
    }

    public IExpr[] toArray() {
        IExpr[] res = MathArrays.buildArray(this.virtualSize);
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            res[iter.key()] = iter.value();
        }
        return res;
    }

    private void checkIndex(int index) throws OutOfRangeException {
        if (index < 0 || index >= getDimension()) {
            throw new OutOfRangeException(Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
        }
    }

    private void checkIndices(int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        int dim = getDimension();
        if (start < 0 || start >= dim) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(start), Integer.valueOf(0), Integer.valueOf(dim - 1));
        } else if (end < 0 || end >= dim) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(end), Integer.valueOf(0), Integer.valueOf(dim - 1));
        } else if (end < start) {
            throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(end), Integer.valueOf(start), false);
        }
    }

    protected void checkVectorDimensions(int n) throws DimensionMismatchException {
        if (getDimension() != n) {
            throw new DimensionMismatchException(getDimension(), n);
        }
    }

    public FieldVector add(FieldVector v) throws DimensionMismatchException {
        if (v instanceof SparseFieldVector) {
            return add((SparseFieldVector) v);
        }
        int n = v.getDimension();
        checkVectorDimensions(n);
        FieldVector res = new SparseFieldVector(getDimension());
        for (int i = 0; i < n; i++) {
            res.setEntry(i, v.getEntry(i).plus(getEntry(i)));
        }
        return res;
    }

    public IExpr walkInDefaultOrder(FieldVectorPreservingVisitor visitor) {
        int dim = getDimension();
        visitor.start(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            visitor.visit(i, getEntry(i));
        }
        return visitor.end();
    }

    public IExpr walkInDefaultOrder(FieldVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        checkIndices(start, end);
        visitor.start(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            visitor.visit(i, getEntry(i));
        }
        return visitor.end();
    }

    public IExpr walkInOptimizedOrder(FieldVectorPreservingVisitor visitor) {
        return walkInDefaultOrder(visitor);
    }

    public IExpr walkInOptimizedOrder(FieldVectorPreservingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInDefaultOrder(visitor, start, end);
    }

    public IExpr walkInDefaultOrder(FieldVectorChangingVisitor visitor) {
        int dim = getDimension();
        visitor.start(dim, 0, dim - 1);
        for (int i = 0; i < dim; i++) {
            setEntry(i, visitor.visit(i, getEntry(i)));
        }
        return visitor.end();
    }

    public IExpr walkInDefaultOrder(FieldVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        checkIndices(start, end);
        visitor.start(getDimension(), start, end);
        for (int i = start; i <= end; i++) {
            setEntry(i, visitor.visit(i, getEntry(i)));
        }
        return visitor.end();
    }

    public IExpr walkInOptimizedOrder(FieldVectorChangingVisitor visitor) {
        return walkInDefaultOrder(visitor);
    }

    public IExpr walkInOptimizedOrder(FieldVectorChangingVisitor visitor, int start, int end) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInDefaultOrder(visitor, start, end);
    }

    public int hashCode() {
        int result = this.virtualSize + 31;
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            result = (result * 31) + iter.value().hashCode();
        }
        return result;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SparseFieldVector)) {
            return false;
        }
        SparseFieldVector other = (SparseFieldVector) obj;
        if (this.virtualSize != other.virtualSize) {
            return false;
        }
        Iterator iter = this.entries.iterator();
        while (iter.hasNext()) {
            iter.advance();
            if (!other.getEntry(iter.key()).equals(iter.value())) {
                return false;
            }
        }
        iter = other.getEntries().iterator();
        while (iter.hasNext()) {
            iter.advance();
            if (!iter.value().equals(getEntry(iter.key()))) {
                return false;
            }
        }
        return true;
    }
}
