package org.matheclipse.commons.math.linear;

import java.util.ArrayList;
import java.util.Iterator;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NotPositiveException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.geometry.VectorFormat;
import org.apache.commons.math4.linear.MatrixDimensionMismatchException;
import org.apache.commons.math4.linear.NonSquareMatrixException;
import org.matheclipse.core.expression.F;
import org.matheclipse.core.interfaces.IExpr;

public abstract class AbstractFieldMatrix implements FieldMatrix {
    public abstract void addToEntry(int i, int i2, IExpr iExpr) throws OutOfRangeException;

    public abstract FieldMatrix copy();

    public abstract FieldMatrix createMatrix(int i, int i2) throws NotStrictlyPositiveException;

    public abstract int getColumnDimension();

    public abstract IExpr getEntry(int i, int i2) throws OutOfRangeException;

    public abstract int getRowDimension();

    public abstract void multiplyEntry(int i, int i2, IExpr iExpr) throws OutOfRangeException;

    public abstract void setEntry(int i, int i2, IExpr iExpr) throws OutOfRangeException;

    protected AbstractFieldMatrix() {
    }

    protected AbstractFieldMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
        if (rowDimension <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, Integer.valueOf(rowDimension));
        } else if (columnDimension <= 0) {
            throw new NotStrictlyPositiveException(LocalizedFormats.DIMENSION, Integer.valueOf(columnDimension));
        }
    }

    @Deprecated
    protected static IExpr[][] buildArray(int rows, int columns) {
        return MathArrays.buildArray(rows, columns);
    }

    @Deprecated
    protected static IExpr[] buildArray(int length) {
        return MathArrays.buildArray(length);
    }

    public FieldMatrix add(FieldMatrix m) throws MatrixDimensionMismatchException {
        checkAdditionCompatible(m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        FieldMatrix out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col).plus(m.getEntry(row, col)));
            }
        }
        return out;
    }

    public FieldMatrix subtract(FieldMatrix m) throws MatrixDimensionMismatchException {
        checkSubtractionCompatible(m);
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        FieldMatrix out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, (IExpr) getEntry(row, col).subtract(m.getEntry(row, col)));
            }
        }
        return out;
    }

    public FieldMatrix scalarAdd(IExpr d) {
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        FieldMatrix out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col).plus(d));
            }
        }
        return out;
    }

    public FieldMatrix scalarMultiply(IExpr d) {
        int rowCount = getRowDimension();
        int columnCount = getColumnDimension();
        FieldMatrix out = createMatrix(rowCount, columnCount);
        for (int row = 0; row < rowCount; row++) {
            for (int col = 0; col < columnCount; col++) {
                out.setEntry(row, col, getEntry(row, col).times(d));
            }
        }
        return out;
    }

    public FieldMatrix multiply(FieldMatrix m) throws DimensionMismatchException {
        checkMultiplicationCompatible(m);
        int nRows = getRowDimension();
        int nCols = m.getColumnDimension();
        int nSum = getColumnDimension();
        FieldMatrix out = createMatrix(nRows, nCols);
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                IExpr sum = F.C0;
                for (int i = 0; i < nSum; i++) {
                    sum = sum.plus(getEntry(row, i).times(m.getEntry(i, col)));
                }
                out.setEntry(row, col, sum);
            }
        }
        return out;
    }

    public FieldMatrix preMultiply(FieldMatrix m) throws DimensionMismatchException {
        return m.multiply(this);
    }

    public FieldMatrix power(int p) throws NonSquareMatrixException, NotPositiveException {
        if (p < 0) {
            throw new NotPositiveException(Integer.valueOf(p));
        } else if (!isSquare()) {
            throw new NonSquareMatrixException(getRowDimension(), getColumnDimension());
        } else if (p == 0) {
            return MatrixUtils.createFieldIdentityMatrix(getRowDimension());
        } else {
            if (p == 1) {
                return copy();
            }
            int i;
            char[] binaryRepresentation = Integer.toBinaryString(p - 1).toCharArray();
            ArrayList<Integer> nonZeroPositions = new ArrayList();
            for (i = 0; i < binaryRepresentation.length; i++) {
                if (binaryRepresentation[i] == '1') {
                    nonZeroPositions.add(Integer.valueOf((binaryRepresentation.length - i) - 1));
                }
            }
            ArrayList<FieldMatrix> results = new ArrayList(binaryRepresentation.length);
            results.add(0, copy());
            for (i = 1; i < binaryRepresentation.length; i++) {
                FieldMatrix s = (FieldMatrix) results.get(i - 1);
                results.add(i, s.multiply(s));
            }
            FieldMatrix result = copy();
            Iterator i$ = nonZeroPositions.iterator();
            while (i$.hasNext()) {
                result = result.multiply((FieldMatrix) results.get(((Integer) i$.next()).intValue()));
            }
            return result;
        }
    }

    public IExpr[][] getData() {
        IExpr[][] data = MathArrays.buildArray(getRowDimension(), getColumnDimension());
        for (int i = 0; i < data.length; i++) {
            IExpr[] dataI = data[i];
            for (int j = 0; j < dataI.length; j++) {
                dataI[j] = getEntry(i, j);
            }
        }
        return data;
    }

    public FieldMatrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        FieldMatrix subMatrix = createMatrix((endRow - startRow) + 1, (endColumn - startColumn) + 1);
        for (int i = startRow; i <= endRow; i++) {
            for (int j = startColumn; j <= endColumn; j++) {
                subMatrix.setEntry(i - startRow, j - startColumn, getEntry(i, j));
            }
        }
        return subMatrix;
    }

    public FieldMatrix getSubMatrix(int[] selectedRows, int[] selectedColumns) throws NoDataException, NullArgumentException, OutOfRangeException {
        checkSubMatrixIndex(selectedRows, selectedColumns);
        FieldMatrix subMatrix = createMatrix(selectedRows.length, selectedColumns.length);
        subMatrix.walkInOptimizedOrder(new 1(this, F.C0, selectedRows, selectedColumns));
        return subMatrix;
    }

    public void copySubMatrix(int startRow, int endRow, int startColumn, int endColumn, IExpr[][] destination) throws MatrixDimensionMismatchException, NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        int rowsCount = (endRow + 1) - startRow;
        int columnsCount = (endColumn + 1) - startColumn;
        if (destination.length < rowsCount || destination[0].length < columnsCount) {
            throw new MatrixDimensionMismatchException(destination.length, destination[0].length, rowsCount, columnsCount);
        }
        walkInOptimizedOrder(new 2(this, F.C0, destination), startRow, endRow, startColumn, endColumn);
    }

    public void copySubMatrix(int[] selectedRows, int[] selectedColumns, IExpr[][] destination) throws MatrixDimensionMismatchException, NoDataException, NullArgumentException, OutOfRangeException {
        checkSubMatrixIndex(selectedRows, selectedColumns);
        if (destination.length < selectedRows.length || destination[0].length < selectedColumns.length) {
            throw new MatrixDimensionMismatchException(destination.length, destination[0].length, selectedRows.length, selectedColumns.length);
        }
        for (int i = 0; i < selectedRows.length; i++) {
            IExpr[] destinationI = destination[i];
            for (int j = 0; j < selectedColumns.length; j++) {
                destinationI[j] = getEntry(selectedRows[i], selectedColumns[j]);
            }
        }
    }

    public void setSubMatrix(IExpr[][] subMatrix, int row, int column) throws DimensionMismatchException, OutOfRangeException, NoDataException, NullArgumentException {
        if (subMatrix == null) {
            throw new NullArgumentException();
        }
        int nRows = subMatrix.length;
        if (nRows == 0) {
            throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_ROW);
        }
        int nCols = subMatrix[0].length;
        if (nCols == 0) {
            throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }
        for (int r = 1; r < nRows; r++) {
            if (subMatrix[r].length != nCols) {
                throw new DimensionMismatchException(nCols, subMatrix[r].length);
            }
        }
        checkRowIndex(row);
        checkColumnIndex(column);
        checkRowIndex((nRows + row) - 1);
        checkColumnIndex((nCols + column) - 1);
        for (int i = 0; i < nRows; i++) {
            for (int j = 0; j < nCols; j++) {
                setEntry(row + i, column + j, subMatrix[i][j]);
            }
        }
    }

    public FieldMatrix getRowMatrix(int row) throws OutOfRangeException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        FieldMatrix out = createMatrix(1, nCols);
        for (int i = 0; i < nCols; i++) {
            out.setEntry(0, i, getEntry(row, i));
        }
        return out;
    }

    public void setRowMatrix(int row, FieldMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        if (matrix.getRowDimension() == 1 && matrix.getColumnDimension() == nCols) {
            for (int i = 0; i < nCols; i++) {
                setEntry(row, i, matrix.getEntry(0, i));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
    }

    public FieldMatrix getColumnMatrix(int column) throws OutOfRangeException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        FieldMatrix out = createMatrix(nRows, 1);
        for (int i = 0; i < nRows; i++) {
            out.setEntry(i, 0, getEntry(i, column));
        }
        return out;
    }

    public void setColumnMatrix(int column, FieldMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        if (matrix.getRowDimension() == nRows && matrix.getColumnDimension() == 1) {
            for (int i = 0; i < nRows; i++) {
                setEntry(i, column, matrix.getEntry(i, 0));
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
    }

    public FieldVector getRowVector(int row) throws OutOfRangeException {
        return new ArrayFieldVector(getRow(row), false);
    }

    public void setRowVector(int row, FieldVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        if (vector.getDimension() != nCols) {
            throw new MatrixDimensionMismatchException(1, vector.getDimension(), 1, nCols);
        }
        for (int i = 0; i < nCols; i++) {
            setEntry(row, i, vector.getEntry(i));
        }
    }

    public FieldVector getColumnVector(int column) throws OutOfRangeException {
        return new ArrayFieldVector(getColumn(column), false);
    }

    public void setColumnVector(int column, FieldVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        if (vector.getDimension() != nRows) {
            throw new MatrixDimensionMismatchException(vector.getDimension(), 1, nRows, 1);
        }
        for (int i = 0; i < nRows; i++) {
            setEntry(i, column, vector.getEntry(i));
        }
    }

    public IExpr[] getRow(int row) throws OutOfRangeException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        IExpr[] out = MathArrays.buildArray(nCols);
        for (int i = 0; i < nCols; i++) {
            out[i] = getEntry(row, i);
        }
        return out;
    }

    public void setRow(int row, IExpr[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        if (array.length != nCols) {
            throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
        }
        for (int i = 0; i < nCols; i++) {
            setEntry(row, i, array[i]);
        }
    }

    public IExpr[] getColumn(int column) throws OutOfRangeException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        IExpr[] out = MathArrays.buildArray(nRows);
        for (int i = 0; i < nRows; i++) {
            out[i] = getEntry(i, column);
        }
        return out;
    }

    public void setColumn(int column, IExpr[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        if (array.length != nRows) {
            throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
        }
        for (int i = 0; i < nRows; i++) {
            setEntry(i, column, array[i]);
        }
    }

    public FieldMatrix transpose() {
        FieldMatrix out = createMatrix(getColumnDimension(), getRowDimension());
        walkInOptimizedOrder(new 3(this, F.C0, out));
        return out;
    }

    public boolean isSquare() {
        return getColumnDimension() == getRowDimension();
    }

    public IExpr getTrace() throws NonSquareMatrixException {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (nRows != nCols) {
            throw new NonSquareMatrixException(nRows, nCols);
        }
        IExpr trace = F.C0;
        for (int i = 0; i < nRows; i++) {
            trace = trace.plus(getEntry(i, i));
        }
        return trace;
    }

    public IExpr[] operate(IExpr[] v) throws DimensionMismatchException {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length != nCols) {
            throw new DimensionMismatchException(v.length, nCols);
        }
        IExpr[] out = MathArrays.buildArray(nRows);
        for (int row = 0; row < nRows; row++) {
            IExpr sum = F.C0;
            for (int i = 0; i < nCols; i++) {
                sum = sum.plus(getEntry(row, i).times(v[i]));
            }
            out[row] = sum;
        }
        return out;
    }

    public FieldVector operate(FieldVector v) throws DimensionMismatchException {
        try {
            return new ArrayFieldVector(operate(((ArrayFieldVector) v).getDataRef()), false);
        } catch (ClassCastException e) {
            int nRows = getRowDimension();
            int nCols = getColumnDimension();
            if (v.getDimension() != nCols) {
                throw new DimensionMismatchException(v.getDimension(), nCols);
            }
            IExpr[] out = MathArrays.buildArray(nRows);
            for (int row = 0; row < nRows; row++) {
                IExpr sum = F.C0;
                for (int i = 0; i < nCols; i++) {
                    sum = sum.plus(getEntry(row, i).times(v.getEntry(i)));
                }
                out[row] = sum;
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public IExpr[] preMultiply(IExpr[] v) throws DimensionMismatchException {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (v.length != nRows) {
            throw new DimensionMismatchException(v.length, nRows);
        }
        IExpr[] out = MathArrays.buildArray(nCols);
        for (int col = 0; col < nCols; col++) {
            IExpr sum = F.C0;
            for (int i = 0; i < nRows; i++) {
                sum = sum.plus(getEntry(i, col).times(v[i]));
            }
            out[col] = sum;
        }
        return out;
    }

    public FieldVector preMultiply(FieldVector v) throws DimensionMismatchException {
        try {
            return new ArrayFieldVector(preMultiply(((ArrayFieldVector) v).getDataRef()), false);
        } catch (ClassCastException e) {
            int nRows = getRowDimension();
            int nCols = getColumnDimension();
            if (v.getDimension() != nRows) {
                throw new DimensionMismatchException(v.getDimension(), nRows);
            }
            IExpr[] out = MathArrays.buildArray(nCols);
            for (int col = 0; col < nCols; col++) {
                IExpr sum = F.C0;
                for (int i = 0; i < nRows; i++) {
                    sum = sum.plus(getEntry(i, col).times(v.getEntry(i)));
                }
                out[col] = sum;
            }
            return new ArrayFieldVector(out, false);
        }
    }

    public IExpr walkInRowOrder(FieldMatrixChangingVisitor visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                setEntry(row, column, visitor.visit(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public IExpr walkInRowOrder(FieldMatrixPreservingVisitor visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int row = 0; row < rows; row++) {
            for (int column = 0; column < columns; column++) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public IExpr walkInRowOrder(FieldMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                setEntry(row, column, visitor.visit(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public IExpr walkInRowOrder(FieldMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int row = startRow; row <= endRow; row++) {
            for (int column = startColumn; column <= endColumn; column++) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public IExpr walkInColumnOrder(FieldMatrixChangingVisitor visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                setEntry(row, column, visitor.visit(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public IExpr walkInColumnOrder(FieldMatrixPreservingVisitor visitor) {
        int rows = getRowDimension();
        int columns = getColumnDimension();
        visitor.start(rows, columns, 0, rows - 1, 0, columns - 1);
        for (int column = 0; column < columns; column++) {
            for (int row = 0; row < rows; row++) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public IExpr walkInColumnOrder(FieldMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; column++) {
            for (int row = startRow; row <= endRow; row++) {
                setEntry(row, column, visitor.visit(row, column, getEntry(row, column)));
            }
        }
        return visitor.end();
    }

    public IExpr walkInColumnOrder(FieldMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(getRowDimension(), getColumnDimension(), startRow, endRow, startColumn, endColumn);
        for (int column = startColumn; column <= endColumn; column++) {
            for (int row = startRow; row <= endRow; row++) {
                visitor.visit(row, column, getEntry(row, column));
            }
        }
        return visitor.end();
    }

    public IExpr walkInOptimizedOrder(FieldMatrixChangingVisitor visitor) {
        return walkInRowOrder(visitor);
    }

    public IExpr walkInOptimizedOrder(FieldMatrixPreservingVisitor visitor) {
        return walkInRowOrder(visitor);
    }

    public IExpr walkInOptimizedOrder(FieldMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    public IExpr walkInOptimizedOrder(FieldMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        return walkInRowOrder(visitor, startRow, endRow, startColumn, endColumn);
    }

    public String toString() {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        StringBuffer res = new StringBuffer();
        String fullClassName = getClass().getName();
        res.append(fullClassName.substring(fullClassName.lastIndexOf(46) + 1)).append(VectorFormat.DEFAULT_PREFIX);
        for (int i = 0; i < nRows; i++) {
            if (i > 0) {
                res.append(",");
            }
            res.append(VectorFormat.DEFAULT_PREFIX);
            for (int j = 0; j < nCols; j++) {
                if (j > 0) {
                    res.append(",");
                }
                res.append(getEntry(i, j));
            }
            res.append(VectorFormat.DEFAULT_SUFFIX);
        }
        res.append(VectorFormat.DEFAULT_SUFFIX);
        return res.toString();
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FieldMatrix)) {
            return false;
        }
        FieldMatrix m = (FieldMatrix) object;
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        if (m.getColumnDimension() != nCols || m.getRowDimension() != nRows) {
            return false;
        }
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                if (!getEntry(row, col).equals(m.getEntry(row, col))) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        int ret = ((9999422 + nRows) * 31) + nCols;
        for (int row = 0; row < nRows; row++) {
            for (int col = 0; col < nCols; col++) {
                ret = (ret * 31) + ((((row + 1) * 11) + ((col + 1) * 17)) * getEntry(row, col).hashCode());
            }
        }
        return ret;
    }

    protected void checkRowIndex(int row) throws OutOfRangeException {
        if (row < 0 || row >= getRowDimension()) {
            throw new OutOfRangeException(LocalizedFormats.ROW_INDEX, Integer.valueOf(row), Integer.valueOf(0), Integer.valueOf(getRowDimension() - 1));
        }
    }

    protected void checkColumnIndex(int column) throws OutOfRangeException {
        if (column < 0 || column >= getColumnDimension()) {
            throw new OutOfRangeException(LocalizedFormats.COLUMN_INDEX, Integer.valueOf(column), Integer.valueOf(0), Integer.valueOf(getColumnDimension() - 1));
        }
    }

    protected void checkSubMatrixIndex(int startRow, int endRow, int startColumn, int endColumn) throws NumberIsTooSmallException, OutOfRangeException {
        checkRowIndex(startRow);
        checkRowIndex(endRow);
        if (endRow < startRow) {
            throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_ROW_AFTER_FINAL_ROW, Integer.valueOf(endRow), Integer.valueOf(startRow), true);
        }
        checkColumnIndex(startColumn);
        checkColumnIndex(endColumn);
        if (endColumn < startColumn) {
            throw new NumberIsTooSmallException(LocalizedFormats.INITIAL_COLUMN_AFTER_FINAL_COLUMN, Integer.valueOf(endColumn), Integer.valueOf(startColumn), true);
        }
    }

    protected void checkSubMatrixIndex(int[] selectedRows, int[] selectedColumns) throws NoDataException, NullArgumentException, OutOfRangeException {
        if (selectedRows == null || selectedColumns == null) {
            throw new NullArgumentException();
        } else if (selectedRows.length == 0 || selectedColumns.length == 0) {
            throw new NoDataException();
        } else {
            for (int row : selectedRows) {
                checkRowIndex(row);
            }
            for (int column : selectedColumns) {
                checkColumnIndex(column);
            }
        }
    }

    protected void checkAdditionCompatible(FieldMatrix m) throws MatrixDimensionMismatchException {
        if (getRowDimension() != m.getRowDimension() || getColumnDimension() != m.getColumnDimension()) {
            throw new MatrixDimensionMismatchException(m.getRowDimension(), m.getColumnDimension(), getRowDimension(), getColumnDimension());
        }
    }

    protected void checkSubtractionCompatible(FieldMatrix m) throws MatrixDimensionMismatchException {
        if (getRowDimension() != m.getRowDimension() || getColumnDimension() != m.getColumnDimension()) {
            throw new MatrixDimensionMismatchException(m.getRowDimension(), m.getColumnDimension(), getRowDimension(), getColumnDimension());
        }
    }

    protected void checkMultiplicationCompatible(FieldMatrix m) throws DimensionMismatchException {
        if (getColumnDimension() != m.getRowDimension()) {
            throw new DimensionMismatchException(m.getRowDimension(), getColumnDimension());
        }
    }
}
