package org.apache.commons.math4.linear;

import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.MathArrays;

public class FieldLUDecomposition<T extends FieldElement<T>> {
    private FieldMatrix<T> cachedL;
    private FieldMatrix<T> cachedP;
    private FieldMatrix<T> cachedU;
    private boolean even;
    private final Field<T> field;
    private T[][] lu;
    private int[] pivot;
    private boolean singular;

    private static class Solver<T extends FieldElement<T>> implements FieldDecompositionSolver<T> {
        private final Field<T> field;
        private final T[][] lu;
        private final int[] pivot;
        private final boolean singular;

        private Solver(Field<T> field, T[][] lu, int[] pivot, boolean singular) {
            this.field = field;
            this.lu = lu;
            this.pivot = pivot;
            this.singular = singular;
        }

        public boolean isNonSingular() {
            return !this.singular;
        }

        public FieldVector<T> solve(FieldVector<T> b) {
            try {
                return solve((ArrayFieldVector) b);
            } catch (ClassCastException e) {
                int m = this.pivot.length;
                if (b.getDimension() != m) {
                    throw new DimensionMismatchException(b.getDimension(), m);
                } else if (this.singular) {
                    throw new SingularMatrixException();
                } else {
                    int col;
                    T bpCol;
                    int i;
                    FieldElement[] bp = (FieldElement[]) MathArrays.buildArray(this.field, m);
                    for (int row = 0; row < m; row++) {
                        bp[row] = b.getEntry(this.pivot[row]);
                    }
                    for (col = 0; col < m; col++) {
                        bpCol = bp[col];
                        for (i = col + 1; i < m; i++) {
                            bp[i] = (FieldElement) bp[i].subtract((FieldElement) bpCol.multiply(this.lu[i][col]));
                        }
                    }
                    for (col = m - 1; col >= 0; col--) {
                        bp[col] = (FieldElement) bp[col].divide(this.lu[col][col]);
                        bpCol = bp[col];
                        for (i = 0; i < col; i++) {
                            bp[i] = (FieldElement) bp[i].subtract((FieldElement) bpCol.multiply(this.lu[i][col]));
                        }
                    }
                    return new ArrayFieldVector(this.field, bp, false);
                }
            }
        }

        public ArrayFieldVector<T> solve(ArrayFieldVector<T> b) {
            int m = this.pivot.length;
            int length = b.getDimension();
            if (length != m) {
                throw new DimensionMismatchException(length, m);
            } else if (this.singular) {
                throw new SingularMatrixException();
            } else {
                int col;
                T bpCol;
                int i;
                FieldElement[] bp = (FieldElement[]) MathArrays.buildArray(this.field, m);
                for (int row = 0; row < m; row++) {
                    bp[row] = b.getEntry(this.pivot[row]);
                }
                for (col = 0; col < m; col++) {
                    bpCol = bp[col];
                    for (i = col + 1; i < m; i++) {
                        bp[i] = (FieldElement) bp[i].subtract((FieldElement) bpCol.multiply(this.lu[i][col]));
                    }
                }
                for (col = m - 1; col >= 0; col--) {
                    bp[col] = (FieldElement) bp[col].divide(this.lu[col][col]);
                    bpCol = bp[col];
                    for (i = 0; i < col; i++) {
                        bp[i] = (FieldElement) bp[i].subtract((FieldElement) bpCol.multiply(this.lu[i][col]));
                    }
                }
                return new ArrayFieldVector(bp, false);
            }
        }

        public FieldMatrix<T> solve(FieldMatrix<T> b) {
            int m = this.pivot.length;
            if (b.getRowDimension() != m) {
                throw new DimensionMismatchException(b.getRowDimension(), m);
            } else if (this.singular) {
                throw new SingularMatrixException();
            } else {
                int col;
                FieldElement[] bpCol;
                int i;
                FieldElement[] bpI;
                Object luICol;
                int j;
                int nColB = b.getColumnDimension();
                FieldElement[][] bp = (FieldElement[][]) MathArrays.buildArray(this.field, m, nColB);
                for (int row = 0; row < m; row++) {
                    FieldElement[] bpRow = bp[row];
                    int pRow = this.pivot[row];
                    for (col = 0; col < nColB; col++) {
                        bpRow[col] = b.getEntry(pRow, col);
                    }
                }
                for (col = 0; col < m; col++) {
                    bpCol = bp[col];
                    for (i = col + 1; i < m; i++) {
                        bpI = bp[i];
                        luICol = this.lu[i][col];
                        for (j = 0; j < nColB; j++) {
                            bpI[j] = (FieldElement) bpI[j].subtract((FieldElement) bpCol[j].multiply(luICol));
                        }
                    }
                }
                for (col = m - 1; col >= 0; col--) {
                    bpCol = bp[col];
                    T luDiag = this.lu[col][col];
                    for (j = 0; j < nColB; j++) {
                        bpCol[j] = (FieldElement) bpCol[j].divide(luDiag);
                    }
                    for (i = 0; i < col; i++) {
                        bpI = bp[i];
                        luICol = this.lu[i][col];
                        for (j = 0; j < nColB; j++) {
                            bpI[j] = (FieldElement) bpI[j].subtract((FieldElement) bpCol[j].multiply(luICol));
                        }
                    }
                }
                return new Array2DRowFieldMatrix(this.field, bp, false);
            }
        }

        public FieldMatrix<T> getInverse() {
            int m = this.pivot.length;
            FieldElement one = (FieldElement) this.field.getOne();
            FieldMatrix identity = new Array2DRowFieldMatrix(this.field, m, m);
            for (int i = 0; i < m; i++) {
                identity.setEntry(i, i, one);
            }
            return solve(identity);
        }
    }

    public FieldLUDecomposition(FieldMatrix<T> matrix) {
        if (matrix.isSquare()) {
            int row;
            int m = matrix.getColumnDimension();
            this.field = matrix.getField();
            this.lu = matrix.getData();
            this.pivot = new int[m];
            this.cachedL = null;
            this.cachedU = null;
            this.cachedP = null;
            for (row = 0; row < m; row++) {
                this.pivot[row] = row;
            }
            this.even = true;
            this.singular = false;
            for (int col = 0; col < m; col++) {
                FieldElement[] luRow;
                T sum;
                int i;
                FieldElement sum2 = (FieldElement) this.field.getZero();
                for (row = 0; row < col; row++) {
                    luRow = this.lu[row];
                    sum = luRow[col];
                    for (i = 0; i < row; i++) {
                        sum2 = (FieldElement) sum.subtract((FieldElement) luRow[i].multiply(this.lu[i][col]));
                    }
                    luRow[col] = sum;
                }
                int nonZero = col;
                for (row = col; row < m; row++) {
                    luRow = this.lu[row];
                    sum = luRow[col];
                    for (i = 0; i < col; i++) {
                        sum2 = (FieldElement) sum.subtract((FieldElement) luRow[i].multiply(this.lu[i][col]));
                    }
                    luRow[col] = sum;
                    if (this.lu[nonZero][col].equals(this.field.getZero())) {
                        nonZero++;
                    }
                }
                if (nonZero >= m) {
                    this.singular = true;
                    return;
                }
                if (nonZero != col) {
                    boolean z;
                    FieldElement tmp = (FieldElement) this.field.getZero();
                    for (i = 0; i < m; i++) {
                        T tmp2 = this.lu[nonZero][i];
                        this.lu[nonZero][i] = this.lu[col][i];
                        this.lu[col][i] = tmp2;
                    }
                    int temp = this.pivot[nonZero];
                    this.pivot[nonZero] = this.pivot[col];
                    this.pivot[col] = temp;
                    if (this.even) {
                        z = false;
                    } else {
                        z = true;
                    }
                    this.even = z;
                }
                T luDiag = this.lu[col][col];
                for (row = col + 1; row < m; row++) {
                    luRow = this.lu[row];
                    luRow[col] = (FieldElement) luRow[col].divide(luDiag);
                }
            }
            return;
        }
        throw new NonSquareMatrixException(matrix.getRowDimension(), matrix.getColumnDimension());
    }

    public FieldMatrix<T> getL() {
        if (this.cachedL == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedL = new Array2DRowFieldMatrix(this.field, m, m);
            for (int i = 0; i < m; i++) {
                FieldElement[] luI = this.lu[i];
                for (int j = 0; j < i; j++) {
                    this.cachedL.setEntry(i, j, luI[j]);
                }
                this.cachedL.setEntry(i, i, (FieldElement) this.field.getOne());
            }
        }
        return this.cachedL;
    }

    public FieldMatrix<T> getU() {
        if (this.cachedU == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedU = new Array2DRowFieldMatrix(this.field, m, m);
            for (int i = 0; i < m; i++) {
                FieldElement[] luI = this.lu[i];
                for (int j = i; j < m; j++) {
                    this.cachedU.setEntry(i, j, luI[j]);
                }
            }
        }
        return this.cachedU;
    }

    public FieldMatrix<T> getP() {
        if (this.cachedP == null && !this.singular) {
            int m = this.pivot.length;
            this.cachedP = new Array2DRowFieldMatrix(this.field, m, m);
            for (int i = 0; i < m; i++) {
                this.cachedP.setEntry(i, this.pivot[i], (FieldElement) this.field.getOne());
            }
        }
        return this.cachedP;
    }

    public int[] getPivot() {
        return (int[]) this.pivot.clone();
    }

    public T getDeterminant() {
        if (this.singular) {
            return (FieldElement) this.field.getZero();
        }
        int m = this.pivot.length;
        T determinant = this.even ? (FieldElement) this.field.getOne() : (FieldElement) ((FieldElement) this.field.getZero()).subtract((FieldElement) this.field.getOne());
        for (int i = 0; i < m; i++) {
            FieldElement determinant2 = (FieldElement) determinant.multiply(this.lu[i][i]);
        }
        return determinant;
    }

    public FieldDecompositionSolver<T> getSolver() {
        return new Solver(this.lu, this.pivot, this.singular, null);
    }
}
