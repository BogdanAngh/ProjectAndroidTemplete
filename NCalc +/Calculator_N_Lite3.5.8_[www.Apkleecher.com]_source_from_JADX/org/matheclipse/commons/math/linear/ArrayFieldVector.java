package org.matheclipse.commons.math.linear;

import java.io.Serializable;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathArithmeticException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooLargeException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.ZeroException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.MathUtils;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

public class ArrayFieldVector implements FieldVector, Serializable {
    private static final long serialVersionUID = 7648186910365927050L;
    private IExpr[] data;

    public ArrayFieldVector() {
        this(0);
    }

    public ArrayFieldVector(int size) {
        this.data = MathArrays.buildArray(size);
    }

    public ArrayFieldVector(int size, IExpr preset) {
        this(size);
        Arrays.fill(this.data, preset);
    }

    public ArrayFieldVector(IExpr[] d) throws NullArgumentException, ZeroException {
        MathUtils.checkNotNull(d);
        try {
            this.data = (IExpr[]) d.clone();
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
        }
    }

    public ArrayFieldVector(IExpr[] d, boolean copyArray) throws NullArgumentException, ZeroException {
        MathUtils.checkNotNull(d);
        if (d.length == 0) {
            throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
        }
        IExpr[] iExprArr;
        if (copyArray) {
            iExprArr = (IExpr[]) d.clone();
        } else {
            iExprArr = d;
        }
        this.data = iExprArr;
    }

    public ArrayFieldVector(IExpr[] d, int pos, int size) throws NullArgumentException, NumberIsTooLargeException {
        MathUtils.checkNotNull(d);
        if (d.length < pos + size) {
            throw new NumberIsTooLargeException(Integer.valueOf(pos + size), Integer.valueOf(d.length), true);
        }
        this.data = MathArrays.buildArray(size);
        System.arraycopy(d, pos, this.data, 0, size);
    }

    public ArrayFieldVector(FieldVector v) throws NullArgumentException {
        MathUtils.checkNotNull(v);
        this.data = MathArrays.buildArray(v.getDimension());
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = v.getEntry(i);
        }
    }

    public ArrayFieldVector(ArrayFieldVector v) throws NullArgumentException {
        MathUtils.checkNotNull(v);
        this.data = (IExpr[]) v.data.clone();
    }

    public ArrayFieldVector(ArrayFieldVector v, boolean deep) throws NullArgumentException {
        MathUtils.checkNotNull(v);
        this.data = deep ? (IExpr[]) v.data.clone() : v.data;
    }

    @Deprecated
    public ArrayFieldVector(ArrayFieldVector v1, ArrayFieldVector v2) throws NullArgumentException {
        this((FieldVector) v1, (FieldVector) v2);
    }

    public ArrayFieldVector(FieldVector v1, FieldVector v2) throws NullArgumentException {
        MathUtils.checkNotNull(v1);
        MathUtils.checkNotNull(v2);
        IExpr[] v1Data = v1 instanceof ArrayFieldVector ? ((ArrayFieldVector) v1).data : v1.toArray();
        IExpr[] v2Data = v2 instanceof ArrayFieldVector ? ((ArrayFieldVector) v2).data : v2.toArray();
        this.data = MathArrays.buildArray(v1Data.length + v2Data.length);
        System.arraycopy(v1Data, 0, this.data, 0, v1Data.length);
        System.arraycopy(v2Data, 0, this.data, v1Data.length, v2Data.length);
    }

    @Deprecated
    public ArrayFieldVector(ArrayFieldVector v1, IExpr[] v2) throws NullArgumentException {
        this((FieldVector) v1, v2);
    }

    public ArrayFieldVector(FieldVector v1, IExpr[] v2) throws NullArgumentException {
        MathUtils.checkNotNull(v1);
        MathUtils.checkNotNull(v2);
        IExpr[] v1Data = v1 instanceof ArrayFieldVector ? ((ArrayFieldVector) v1).data : v1.toArray();
        this.data = MathArrays.buildArray(v1Data.length + v2.length);
        System.arraycopy(v1Data, 0, this.data, 0, v1Data.length);
        System.arraycopy(v2, 0, this.data, v1Data.length, v2.length);
    }

    @Deprecated
    public ArrayFieldVector(IExpr[] v1, ArrayFieldVector v2) throws NullArgumentException {
        this(v1, (FieldVector) v2);
    }

    public ArrayFieldVector(IExpr[] v1, FieldVector v2) throws NullArgumentException {
        MathUtils.checkNotNull(v1);
        MathUtils.checkNotNull(v2);
        IExpr[] v2Data = v2 instanceof ArrayFieldVector ? ((ArrayFieldVector) v2).data : v2.toArray();
        this.data = MathArrays.buildArray(v1.length + v2Data.length);
        System.arraycopy(v1, 0, this.data, 0, v1.length);
        System.arraycopy(v2Data, 0, this.data, v1.length, v2Data.length);
    }

    public ArrayFieldVector(IExpr[] v1, IExpr[] v2) throws NullArgumentException, ZeroException {
        MathUtils.checkNotNull(v1);
        MathUtils.checkNotNull(v2);
        if (v1.length + v2.length == 0) {
            throw new ZeroException(LocalizedFormats.VECTOR_MUST_HAVE_AT_LEAST_ONE_ELEMENT, new Object[0]);
        }
        this.data = MathArrays.buildArray(v1.length + v2.length);
        System.arraycopy(v1, 0, this.data, 0, v1.length);
        System.arraycopy(v2, 0, this.data, v1.length, v2.length);
    }

    public FieldVector copy() {
        return new ArrayFieldVector(this, true);
    }

    public FieldVector add(FieldVector v) throws DimensionMismatchException {
        try {
            return add((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            checkVectorDimensions(v);
            IExpr[] out = MathArrays.buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                out[i] = this.data[i].plus(v.getEntry(i));
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public ArrayFieldVector add(ArrayFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.data.length);
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = this.data[i].plus(v.data[i]);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector subtract(FieldVector v) throws DimensionMismatchException {
        try {
            return subtract((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            checkVectorDimensions(v);
            IExpr[] out = MathArrays.buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                out[i] = (IExpr) this.data[i].subtract(v.getEntry(i));
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public ArrayFieldVector subtract(ArrayFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.data.length);
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = (IExpr) this.data[i].subtract(v.data[i]);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapAdd(IExpr d) throws NullArgumentException {
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = this.data[i].plus(d);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapAddToSelf(IExpr d) throws NullArgumentException {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i].plus(d);
        }
        return this;
    }

    public FieldVector mapSubtract(IExpr d) throws NullArgumentException {
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = (IExpr) this.data[i].subtract(d);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapSubtractToSelf(IExpr d) throws NullArgumentException {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = (IExpr) this.data[i].subtract(d);
        }
        return this;
    }

    public FieldVector mapMultiply(IExpr d) throws NullArgumentException {
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = this.data[i].times(d);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapMultiplyToSelf(IExpr d) throws NullArgumentException {
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i].times(d);
        }
        return this;
    }

    public FieldVector mapDivide(IExpr d) throws NullArgumentException, MathArithmeticException {
        MathUtils.checkNotNull(d);
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = this.data[i].divide(d);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapDivideToSelf(IExpr d) throws NullArgumentException, MathArithmeticException {
        MathUtils.checkNotNull(d);
        for (int i = 0; i < this.data.length; i++) {
            this.data[i] = this.data[i].divide(d);
        }
        return this;
    }

    public FieldVector mapInv() throws MathArithmeticException {
        IExpr[] out = MathArrays.buildArray(this.data.length);
        IExpr one = F.C1;
        int i = 0;
        while (i < this.data.length) {
            try {
                out[i] = one.divide(this.data[i]);
                i++;
            } catch (MathArithmeticException e) {
                throw new MathArithmeticException(LocalizedFormats.INDEX, Integer.valueOf(i));
            }
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector mapInvToSelf() throws MathArithmeticException {
        IExpr one = F.C1;
        int i = 0;
        while (i < this.data.length) {
            try {
                this.data[i] = one.divide(this.data[i]);
                i++;
            } catch (MathArithmeticException e) {
                throw new MathArithmeticException(LocalizedFormats.INDEX, Integer.valueOf(i));
            }
        }
        return this;
    }

    public FieldVector ebeMultiply(FieldVector v) throws DimensionMismatchException {
        try {
            return ebeMultiply((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            checkVectorDimensions(v);
            IExpr[] out = MathArrays.buildArray(this.data.length);
            for (int i = 0; i < this.data.length; i++) {
                out[i] = this.data[i].times(v.getEntry(i));
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public ArrayFieldVector ebeMultiply(ArrayFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.data.length);
        IExpr[] out = MathArrays.buildArray(this.data.length);
        for (int i = 0; i < this.data.length; i++) {
            out[i] = this.data[i].times(v.data[i]);
        }
        return new ArrayFieldVector(out, false);
    }

    public FieldVector ebeDivide(FieldVector v) throws DimensionMismatchException, MathArithmeticException {
        try {
            return ebeDivide((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            checkVectorDimensions(v);
            IExpr[] out = MathArrays.buildArray(this.data.length);
            int i = 0;
            while (i < this.data.length) {
                try {
                    out[i] = this.data[i].divide(v.getEntry(i));
                    i++;
                } catch (MathArithmeticException e2) {
                    throw new MathArithmeticException(LocalizedFormats.INDEX, Integer.valueOf(i));
                }
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public ArrayFieldVector ebeDivide(ArrayFieldVector v) throws DimensionMismatchException, MathArithmeticException {
        checkVectorDimensions(v.data.length);
        IExpr[] out = MathArrays.buildArray(this.data.length);
        int i = 0;
        while (i < this.data.length) {
            try {
                out[i] = this.data[i].divide(v.data[i]);
                i++;
            } catch (MathArithmeticException e) {
                throw new MathArithmeticException(LocalizedFormats.INDEX, Integer.valueOf(i));
            }
        }
        return new ArrayFieldVector(out, false);
    }

    public IExpr[] getData() {
        return (IExpr[]) this.data.clone();
    }

    public IExpr[] getDataRef() {
        return this.data;
    }

    public IExpr dotProduct(FieldVector v) throws DimensionMismatchException {
        IExpr dotProduct;
        try {
            dotProduct = dotProduct((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            checkVectorDimensions(v);
            dotProduct = F.C0;
            for (int i = 0; i < this.data.length; i++) {
                dotProduct = dotProduct.plus(this.data[i].times(v.getEntry(i)));
            }
        }
        return dotProduct;
    }

    public IExpr dotProduct(ArrayFieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.data.length);
        IExpr dot = F.C0;
        for (int i = 0; i < this.data.length; i++) {
            dot = dot.plus(this.data[i].times(v.data[i]));
        }
        return dot;
    }

    public FieldVector projection(FieldVector v) throws DimensionMismatchException, MathArithmeticException {
        return v.mapMultiply(dotProduct(v).divide(v.dotProduct(v)));
    }

    public ArrayFieldVector projection(ArrayFieldVector v) throws DimensionMismatchException, MathArithmeticException {
        return (ArrayFieldVector) v.mapMultiply(dotProduct(v).divide(v.dotProduct(v)));
    }

    public FieldMatrix outerProduct(FieldVector v) {
        FieldMatrix outerProduct;
        try {
            outerProduct = outerProduct((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            int m = this.data.length;
            int n = v.getDimension();
            outerProduct = new Array2DRowFieldMatrix(m, n);
            for (int i = 0; i < m; i++) {
                for (int j = 0; j < n; j++) {
                    outerProduct.setEntry(i, j, this.data[i].times(v.getEntry(j)));
                }
            }
        }
        return outerProduct;
    }

    public FieldMatrix outerProduct(ArrayFieldVector v) {
        int m = this.data.length;
        int n = v.data.length;
        FieldMatrix out = new Array2DRowFieldMatrix(m, n);
        for (int i = 0; i < m; i++) {
            for (int j = 0; j < n; j++) {
                out.setEntry(i, j, this.data[i].times(v.data[j]));
            }
        }
        return out;
    }

    public IExpr getEntry(int index) {
        return this.data[index];
    }

    public int getDimension() {
        return this.data.length;
    }

    public FieldVector append(FieldVector v) {
        try {
            return append((ArrayFieldVector) v);
        } catch (ClassCastException e) {
            return new ArrayFieldVector(this, new ArrayFieldVector(v));
        }
    }

    public ArrayFieldVector append(ArrayFieldVector v) {
        return new ArrayFieldVector(this, v);
    }

    public FieldVector append(IExpr in) {
        IExpr[] out = MathArrays.buildArray(this.data.length + 1);
        System.arraycopy(this.data, 0, out, 0, this.data.length);
        out[this.data.length] = in;
        return new ArrayFieldVector(out, false);
    }

    public FieldVector getSubVector(int index, int n) throws OutOfRangeException, NotPositiveException {
        if (n < 0) {
            throw new NotPositiveException(LocalizedFormats.NUMBER_OF_ELEMENTS_SHOULD_BE_POSITIVE, Integer.valueOf(n));
        }
        ArrayFieldVector out = new ArrayFieldVector(n);
        try {
            System.arraycopy(this.data, index, out.data, 0, n);
        } catch (IndexOutOfBoundsException e) {
            checkIndex(index);
            checkIndex((index + n) - 1);
        }
        return out;
    }

    public void setEntry(int index, IExpr value) {
        try {
            this.data[index] = value;
        } catch (IndexOutOfBoundsException e) {
            checkIndex(index);
        }
    }

    public void setSubVector(int index, FieldVector v) throws OutOfRangeException {
        try {
            set(index, (ArrayFieldVector) v);
        } catch (ClassCastException e) {
            int i = index;
            while (i < v.getDimension() + index) {
                try {
                    this.data[i] = v.getEntry(i - index);
                    i++;
                } catch (IndexOutOfBoundsException e2) {
                    checkIndex(index);
                    checkIndex((v.getDimension() + index) - 1);
                    return;
                }
            }
        }
    }

    public void set(int index, ArrayFieldVector v) throws OutOfRangeException {
        try {
            System.arraycopy(v.data, 0, this.data, index, v.data.length);
        } catch (IndexOutOfBoundsException e) {
            checkIndex(index);
            checkIndex((v.data.length + index) - 1);
        }
    }

    public void set(IExpr value) {
        Arrays.fill(this.data, value);
    }

    public IExpr[] toArray() {
        return (IExpr[]) this.data.clone();
    }

    protected void checkVectorDimensions(FieldVector v) throws DimensionMismatchException {
        checkVectorDimensions(v.getDimension());
    }

    protected void checkVectorDimensions(int n) throws DimensionMismatchException {
        if (this.data.length != n) {
            throw new DimensionMismatchException(this.data.length, n);
        }
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

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null) {
            return false;
        }
        try {
            FieldVector rhs = (FieldVector) other;
            if (this.data.length != rhs.getDimension()) {
                return false;
            }
            for (int i = 0; i < this.data.length; i++) {
                if (!this.data[i].equals(rhs.getEntry(i))) {
                    return false;
                }
            }
            return true;
        } catch (ClassCastException e) {
            return false;
        }
    }

    public int hashCode() {
        int h = 3542;
        for (IExpr a : this.data) {
            h ^= a.hashCode();
        }
        return h;
    }

    private void checkIndex(int index) throws OutOfRangeException {
        if (index < 0 || index >= getDimension()) {
            throw new OutOfRangeException(LocalizedFormats.INDEX, Integer.valueOf(index), Integer.valueOf(0), Integer.valueOf(getDimension() - 1));
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
}
