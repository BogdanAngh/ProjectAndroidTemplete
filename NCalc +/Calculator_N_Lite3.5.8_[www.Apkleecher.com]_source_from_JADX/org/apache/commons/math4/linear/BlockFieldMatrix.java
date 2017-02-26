package org.apache.commons.math4.linear;

import java.io.Serializable;
import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathArrays;
import org.apache.commons.math4.util.MathUtils;

public class BlockFieldMatrix<T extends FieldElement<T>> extends AbstractFieldMatrix<T> implements Serializable {
    public static final int BLOCK_SIZE = 36;
    private static final long serialVersionUID = -4602336630143123183L;
    private final int blockColumns;
    private final int blockRows;
    private final T[][] blocks;
    private final int columns;
    private final int rows;

    public BlockFieldMatrix(Field<T> field, int rows, int columns) throws NotStrictlyPositiveException {
        super(field, rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blocks = createBlocksLayout(field, rows, columns);
    }

    public BlockFieldMatrix(T[][] rawData) throws DimensionMismatchException {
        this(rawData.length, rawData[0].length, toBlocksLayout(rawData), false);
    }

    public BlockFieldMatrix(int rows, int columns, T[][] blockData, boolean copyArray) throws DimensionMismatchException, NotStrictlyPositiveException {
        super(AbstractFieldMatrix.extractField((FieldElement[][]) blockData), rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        if (copyArray) {
            this.blocks = (FieldElement[][]) MathArrays.buildArray(getField(), this.blockRows * this.blockColumns, -1);
        } else {
            this.blocks = blockData;
        }
        int index = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = blockHeight(iBlock);
            int jBlock = 0;
            while (jBlock < this.blockColumns) {
                if (blockData[index].length != blockWidth(jBlock) * iHeight) {
                    throw new DimensionMismatchException(blockData[index].length, blockWidth(jBlock) * iHeight);
                }
                if (copyArray) {
                    this.blocks[index] = (FieldElement[]) blockData[index].clone();
                }
                jBlock++;
                index++;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static <T extends org.apache.commons.math4.FieldElement<T>> T[][] toBlocksLayout(T[][] r24) throws org.apache.commons.math4.exception.DimensionMismatchException {
        /*
        r0 = r24;
        r0 = r0.length;
        r21 = r0;
        r22 = 0;
        r22 = r24[r22];
        r0 = r22;
        r7 = r0.length;
        r22 = r21 + 36;
        r22 = r22 + -1;
        r5 = r22 / 36;
        r22 = r7 + 36;
        r22 = r22 + -1;
        r3 = r22 / 36;
        r9 = 0;
    L_0x0019:
        r0 = r24;
        r0 = r0.length;
        r22 = r0;
        r0 = r22;
        if (r9 < r0) goto L_0x0039;
    L_0x0022:
        r8 = org.apache.commons.math4.linear.AbstractFieldMatrix.extractField(r24);
        r22 = r5 * r3;
        r23 = -1;
        r0 = r22;
        r1 = r23;
        r6 = org.apache.commons.math4.util.MathArrays.buildArray(r8, r0, r1);
        r6 = (org.apache.commons.math4.FieldElement[][]) r6;
        r4 = 0;
        r10 = 0;
    L_0x0036:
        if (r10 < r5) goto L_0x004b;
    L_0x0038:
        return r6;
    L_0x0039:
        r22 = r24[r9];
        r0 = r22;
        r15 = r0.length;
        if (r15 == r7) goto L_0x0048;
    L_0x0040:
        r22 = new org.apache.commons.math4.exception.DimensionMismatchException;
        r0 = r22;
        r0.<init>(r7, r15);
        throw r22;
    L_0x0048:
        r9 = r9 + 1;
        goto L_0x0019;
    L_0x004b:
        r18 = r10 * 36;
        r22 = r18 + 36;
        r0 = r22;
        r1 = r21;
        r17 = org.apache.commons.math4.util.FastMath.min(r0, r1);
        r11 = r17 - r18;
        r13 = 0;
    L_0x005a:
        if (r13 < r3) goto L_0x005f;
    L_0x005c:
        r10 = r10 + 1;
        goto L_0x0036;
    L_0x005f:
        r20 = r13 * 36;
        r22 = r20 + 36;
        r0 = r22;
        r19 = org.apache.commons.math4.util.FastMath.min(r0, r7);
        r14 = r19 - r20;
        r22 = r11 * r14;
        r0 = r22;
        r2 = org.apache.commons.math4.util.MathArrays.buildArray(r8, r0);
        r2 = (org.apache.commons.math4.FieldElement[]) r2;
        r6[r4] = r2;
        r12 = 0;
        r16 = r18;
    L_0x007a:
        r0 = r16;
        r1 = r17;
        if (r0 < r1) goto L_0x0085;
    L_0x0080:
        r4 = r4 + 1;
        r13 = r13 + 1;
        goto L_0x005a;
    L_0x0085:
        r22 = r24[r16];
        r0 = r22;
        r1 = r20;
        java.lang.System.arraycopy(r0, r1, r2, r12, r14);
        r12 = r12 + r14;
        r16 = r16 + 1;
        goto L_0x007a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.linear.BlockFieldMatrix.toBlocksLayout(org.apache.commons.math4.FieldElement[][]):T[][]");
    }

    public static <T extends FieldElement<T>> T[][] createBlocksLayout(Field<T> field, int rows, int columns) {
        int blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        int blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        FieldElement[][] blocks = (FieldElement[][]) MathArrays.buildArray(field, blockRows * blockColumns, -1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int iHeight = FastMath.min(pStart + BLOCK_SIZE, rows) - pStart;
            for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                blocks[blockIndex] = (FieldElement[]) MathArrays.buildArray(field, iHeight * (FastMath.min(qStart + BLOCK_SIZE, columns) - qStart));
                blockIndex++;
            }
        }
        return blocks;
    }

    public FieldMatrix<T> createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
        return new BlockFieldMatrix(getField(), rowDimension, columnDimension);
    }

    public FieldMatrix<T> copy() {
        BlockFieldMatrix<T> copied = new BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int i = 0; i < this.blocks.length; i++) {
            System.arraycopy(this.blocks[i], 0, copied.blocks[i], 0, this.blocks[i].length);
        }
        return copied;
    }

    public FieldMatrix<T> add(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
        FieldMatrix<T> add;
        try {
            add = add((BlockFieldMatrix) m);
        } catch (ClassCastException e) {
            checkAdditionCompatible(m);
            add = new BlockFieldMatrix(getField(), this.rows, this.columns);
            int blockIndex = 0;
            int iBlock = 0;
            while (true) {
                int i = add.blockRows;
                if (iBlock >= r0) {
                    break;
                }
                int jBlock = 0;
                while (true) {
                    i = add.blockColumns;
                    if (jBlock >= r0) {
                        break;
                    }
                    FieldElement[] outBlock = add.blocks[blockIndex];
                    FieldElement[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * BLOCK_SIZE;
                    int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = (FieldElement) tBlock[k].add(m.getEntry(p, q));
                            k++;
                        }
                    }
                    blockIndex++;
                    jBlock++;
                }
                iBlock++;
            }
        }
        return add;
    }

    public BlockFieldMatrix<T> add(BlockFieldMatrix<T> m) throws MatrixDimensionMismatchException {
        checkAdditionCompatible(m);
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            FieldElement[] outBlock = out.blocks[blockIndex];
            FieldElement[] tBlock = this.blocks[blockIndex];
            FieldElement[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = (FieldElement) tBlock[k].add(mBlock[k]);
            }
        }
        return out;
    }

    public FieldMatrix<T> subtract(FieldMatrix<T> m) throws MatrixDimensionMismatchException {
        FieldMatrix<T> subtract;
        try {
            subtract = subtract((BlockFieldMatrix) m);
        } catch (ClassCastException e) {
            checkSubtractionCompatible(m);
            subtract = new BlockFieldMatrix(getField(), this.rows, this.columns);
            int blockIndex = 0;
            int iBlock = 0;
            while (true) {
                int i = subtract.blockRows;
                if (iBlock >= r0) {
                    break;
                }
                int jBlock = 0;
                while (true) {
                    i = subtract.blockColumns;
                    if (jBlock >= r0) {
                        break;
                    }
                    FieldElement[] outBlock = subtract.blocks[blockIndex];
                    FieldElement[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * BLOCK_SIZE;
                    int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = (FieldElement) tBlock[k].subtract(m.getEntry(p, q));
                            k++;
                        }
                    }
                    blockIndex++;
                    jBlock++;
                }
                iBlock++;
            }
        }
        return subtract;
    }

    public BlockFieldMatrix<T> subtract(BlockFieldMatrix<T> m) throws MatrixDimensionMismatchException {
        checkSubtractionCompatible(m);
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            FieldElement[] outBlock = out.blocks[blockIndex];
            FieldElement[] tBlock = this.blocks[blockIndex];
            FieldElement[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = (FieldElement) tBlock[k].subtract(mBlock[k]);
            }
        }
        return out;
    }

    public FieldMatrix<T> scalarAdd(T d) {
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            FieldElement[] outBlock = out.blocks[blockIndex];
            FieldElement[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = (FieldElement) tBlock[k].add(d);
            }
        }
        return out;
    }

    public FieldMatrix<T> scalarMultiply(T d) {
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            FieldElement[] outBlock = out.blocks[blockIndex];
            FieldElement[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = (FieldElement) tBlock[k].multiply((Object) d);
            }
        }
        return out;
    }

    public FieldMatrix<T> multiply(FieldMatrix<T> m) throws DimensionMismatchException {
        FieldMatrix<T> multiply;
        try {
            multiply = multiply((BlockFieldMatrix) m);
        } catch (ClassCastException e) {
            checkMultiplicationCompatible(m);
            multiply = new BlockFieldMatrix(getField(), this.rows, m.getColumnDimension());
            T zero = (FieldElement) getField().getZero();
            int blockIndex = 0;
            int iBlock = 0;
            while (true) {
                int i = multiply.blockRows;
                if (iBlock >= r0) {
                    break;
                }
                int pStart = iBlock * BLOCK_SIZE;
                int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                int jBlock = 0;
                while (true) {
                    i = multiply.blockColumns;
                    if (jBlock >= r0) {
                        break;
                    }
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, m.getColumnDimension());
                    FieldElement[] outBlock = multiply.blocks[blockIndex];
                    int kBlock = 0;
                    while (true) {
                        i = this.blockColumns;
                        if (kBlock >= r0) {
                            break;
                        }
                        int kWidth = blockWidth(kBlock);
                        FieldElement[] tBlock = this.blocks[(this.blockColumns * iBlock) + kBlock];
                        int rStart = kBlock * BLOCK_SIZE;
                        int k = 0;
                        for (int p = pStart; p < pEnd; p++) {
                            int lStart = (p - pStart) * kWidth;
                            int lEnd = lStart + kWidth;
                            for (int q = qStart; q < qEnd; q++) {
                                T sum = zero;
                                int r = rStart;
                                for (int l = lStart; l < lEnd; l++) {
                                    FieldElement sum2 = (FieldElement) sum.add((FieldElement) tBlock[l].multiply(m.getEntry(r, q)));
                                    r++;
                                }
                                outBlock[k] = (FieldElement) outBlock[k].add(sum);
                                k++;
                            }
                        }
                        kBlock++;
                    }
                    blockIndex++;
                    jBlock++;
                }
                iBlock++;
            }
        }
        return multiply;
    }

    public BlockFieldMatrix<T> multiply(BlockFieldMatrix<T> m) throws DimensionMismatchException {
        checkMultiplicationCompatible(m);
        BlockFieldMatrix<T> blockFieldMatrix = new BlockFieldMatrix(getField(), this.rows, m.columns);
        T zero = (FieldElement) getField().getZero();
        int blockIndex = 0;
        int iBlock = 0;
        while (true) {
            int i = blockFieldMatrix.blockRows;
            if (iBlock >= r0) {
                return blockFieldMatrix;
            }
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            int jBlock = 0;
            while (true) {
                i = blockFieldMatrix.blockColumns;
                if (jBlock >= r0) {
                    break;
                }
                int jWidth = blockFieldMatrix.blockWidth(jBlock);
                int jWidth2 = jWidth + jWidth;
                int jWidth3 = jWidth2 + jWidth;
                int jWidth4 = jWidth3 + jWidth;
                FieldElement[] outBlock = blockFieldMatrix.blocks[blockIndex];
                int kBlock = 0;
                while (true) {
                    i = this.blockColumns;
                    if (kBlock >= r0) {
                        break;
                    }
                    int kWidth = blockWidth(kBlock);
                    FieldElement[] tBlock = this.blocks[(this.blockColumns * iBlock) + kBlock];
                    FieldElement[] mBlock = m.blocks[(m.blockColumns * kBlock) + jBlock];
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        int lStart = (p - pStart) * kWidth;
                        int lEnd = lStart + kWidth;
                        for (int nStart = 0; nStart < jWidth; nStart++) {
                            T sum = zero;
                            int l = lStart;
                            int n = nStart;
                            while (l < lEnd - 3) {
                                FieldElement sum2 = (FieldElement) ((FieldElement) ((FieldElement) ((FieldElement) sum.add((FieldElement) tBlock[l].multiply(mBlock[n]))).add((FieldElement) tBlock[l + 1].multiply(mBlock[n + jWidth]))).add((FieldElement) tBlock[l + 2].multiply(mBlock[n + jWidth2]))).add((FieldElement) tBlock[l + 3].multiply(mBlock[n + jWidth3]));
                                l += 4;
                                n += jWidth4;
                            }
                            for (int l2 = l; l2 < lEnd; l2++) {
                                sum2 = (FieldElement) sum.add((FieldElement) tBlock[l2].multiply(mBlock[n]));
                                n += jWidth;
                            }
                            outBlock[k] = (FieldElement) outBlock[k].add(sum);
                            k++;
                        }
                    }
                    kBlock++;
                }
                blockIndex++;
                jBlock++;
            }
            iBlock++;
        }
    }

    public T[][] getData() {
        FieldElement[][] data = (FieldElement[][]) MathArrays.buildArray(getField(), getRowDimension(), getColumnDimension());
        int lastColumns = this.columns - ((this.blockColumns - 1) * BLOCK_SIZE);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            int regularPos = 0;
            int lastPos = 0;
            for (int p = pStart; p < pEnd; p++) {
                FieldElement[] dataP = data[p];
                int blockIndex = iBlock * this.blockColumns;
                int dataPos = 0;
                int jBlock = 0;
                while (jBlock < this.blockColumns - 1) {
                    int blockIndex2 = blockIndex + 1;
                    System.arraycopy(this.blocks[blockIndex], regularPos, dataP, dataPos, BLOCK_SIZE);
                    dataPos += BLOCK_SIZE;
                    jBlock++;
                    blockIndex = blockIndex2;
                }
                System.arraycopy(this.blocks[blockIndex], lastPos, dataP, dataPos, lastColumns);
                regularPos += BLOCK_SIZE;
                lastPos += lastColumns;
            }
        }
        return data;
    }

    public FieldMatrix<T> getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        BlockFieldMatrix<T> blockFieldMatrix = new BlockFieldMatrix(getField(), (endRow - startRow) + 1, (endColumn - startColumn) + 1);
        int rowsShift = startRow % BLOCK_SIZE;
        int blockStartColumn = startColumn / BLOCK_SIZE;
        int columnsShift = startColumn % BLOCK_SIZE;
        int pBlock = startRow / BLOCK_SIZE;
        for (int iBlock = 0; iBlock < blockFieldMatrix.blockRows; iBlock++) {
            int iHeight = blockFieldMatrix.blockHeight(iBlock);
            int qBlock = blockStartColumn;
            for (int jBlock = 0; jBlock < blockFieldMatrix.blockColumns; jBlock++) {
                int jWidth = blockFieldMatrix.blockWidth(jBlock);
                FieldElement[] outBlock = blockFieldMatrix.blocks[(blockFieldMatrix.blockColumns * iBlock) + jBlock];
                int index = (this.blockColumns * pBlock) + qBlock;
                int width = blockWidth(qBlock);
                int heightExcess = (iHeight + rowsShift) - 36;
                int widthExcess = (jWidth + columnsShift) - 36;
                int width2;
                if (heightExcess > 0) {
                    if (widthExcess > 0) {
                        width2 = blockWidth(qBlock + 1);
                        copyBlockPart(this.blocks[index], width, rowsShift, BLOCK_SIZE, columnsShift, BLOCK_SIZE, outBlock, jWidth, 0, 0);
                        copyBlockPart(this.blocks[index + 1], width2, rowsShift, BLOCK_SIZE, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
                        copyBlockPart(this.blocks[this.blockColumns + index], width, 0, heightExcess, columnsShift, BLOCK_SIZE, outBlock, jWidth, iHeight - heightExcess, 0);
                        copyBlockPart(this.blocks[(this.blockColumns + index) + 1], width2, 0, heightExcess, 0, widthExcess, outBlock, jWidth, iHeight - heightExcess, jWidth - widthExcess);
                    } else {
                        copyBlockPart(this.blocks[index], width, rowsShift, BLOCK_SIZE, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
                        copyBlockPart(this.blocks[this.blockColumns + index], width, 0, heightExcess, columnsShift, jWidth + columnsShift, outBlock, jWidth, iHeight - heightExcess, 0);
                    }
                } else if (widthExcess > 0) {
                    width2 = blockWidth(qBlock + 1);
                    copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, BLOCK_SIZE, outBlock, jWidth, 0, 0);
                    copyBlockPart(this.blocks[index + 1], width2, rowsShift, iHeight + rowsShift, 0, widthExcess, outBlock, jWidth, 0, jWidth - widthExcess);
                } else {
                    copyBlockPart(this.blocks[index], width, rowsShift, iHeight + rowsShift, columnsShift, jWidth + columnsShift, outBlock, jWidth, 0, 0);
                }
                qBlock++;
            }
            pBlock++;
        }
        return blockFieldMatrix;
    }

    private void copyBlockPart(T[] srcBlock, int srcWidth, int srcStartRow, int srcEndRow, int srcStartColumn, int srcEndColumn, T[] dstBlock, int dstWidth, int dstStartRow, int dstStartColumn) {
        int length = srcEndColumn - srcStartColumn;
        int srcPos = (srcStartRow * srcWidth) + srcStartColumn;
        int dstPos = (dstStartRow * dstWidth) + dstStartColumn;
        for (int srcRow = srcStartRow; srcRow < srcEndRow; srcRow++) {
            System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
            srcPos += srcWidth;
            dstPos += dstWidth;
        }
    }

    public void setSubMatrix(T[][] subMatrix, int row, int column) throws DimensionMismatchException, OutOfRangeException, NoDataException, NullArgumentException {
        MathUtils.checkNotNull(subMatrix);
        int refLength = subMatrix[0].length;
        if (refLength == 0) {
            throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }
        int endRow = (subMatrix.length + row) - 1;
        int endColumn = (column + refLength) - 1;
        checkSubMatrixIndex(row, endRow, column, endColumn);
        for (FieldElement[] subRow : subMatrix) {
            int length = subRow.length;
            if (r0 != refLength) {
                throw new DimensionMismatchException(refLength, subRow.length);
            }
        }
        int blockEndRow = (endRow + BLOCK_SIZE) / BLOCK_SIZE;
        int blockStartColumn = column / BLOCK_SIZE;
        int blockEndColumn = (endColumn + BLOCK_SIZE) / BLOCK_SIZE;
        for (int iBlock = row / BLOCK_SIZE; iBlock < blockEndRow; iBlock++) {
            int iHeight = blockHeight(iBlock);
            int firstRow = iBlock * BLOCK_SIZE;
            int iStart = FastMath.max(row, firstRow);
            int iEnd = FastMath.min(endRow + 1, firstRow + iHeight);
            for (int jBlock = blockStartColumn; jBlock < blockEndColumn; jBlock++) {
                int jWidth = blockWidth(jBlock);
                int firstColumn = jBlock * BLOCK_SIZE;
                int jStart = FastMath.max(column, firstColumn);
                int jLength = FastMath.min(endColumn + 1, firstColumn + jWidth) - jStart;
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (int i = iStart; i < iEnd; i++) {
                    System.arraycopy(subMatrix[i - row], jStart - column, block, ((i - firstRow) * jWidth) + (jStart - firstColumn), jLength);
                }
            }
        }
    }

    public FieldMatrix<T> getRowMatrix(int row) throws OutOfRangeException {
        checkRowIndex(row);
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), 1, this.columns);
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outBlockIndex = 0;
        int outIndex = 0;
        FieldElement[] outBlock = out.blocks[0];
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int available = outBlock.length - outIndex;
            if (jWidth > available) {
                System.arraycopy(block, iRow * jWidth, outBlock, outIndex, available);
                outBlockIndex++;
                outBlock = out.blocks[outBlockIndex];
                System.arraycopy(block, iRow * jWidth, outBlock, 0, jWidth - available);
                outIndex = jWidth - available;
            } else {
                System.arraycopy(block, iRow * jWidth, outBlock, outIndex, jWidth);
                outIndex += jWidth;
            }
        }
        return out;
    }

    public void setRowMatrix(int row, FieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
        try {
            setRowMatrix(row, (BlockFieldMatrix) matrix);
        } catch (ClassCastException e) {
            super.setRowMatrix(row, matrix);
        }
    }

    public void setRowMatrix(int row, BlockFieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        if (matrix.getRowDimension() == 1 && matrix.getColumnDimension() == nCols) {
            int iBlock = row / BLOCK_SIZE;
            int iRow = row - (iBlock * BLOCK_SIZE);
            int mBlockIndex = 0;
            int mIndex = 0;
            FieldElement[] mBlock = matrix.blocks[0];
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int jWidth = blockWidth(jBlock);
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int available = mBlock.length - mIndex;
                if (jWidth > available) {
                    System.arraycopy(mBlock, mIndex, block, iRow * jWidth, available);
                    mBlockIndex++;
                    mBlock = matrix.blocks[mBlockIndex];
                    System.arraycopy(mBlock, 0, block, iRow * jWidth, jWidth - available);
                    mIndex = jWidth - available;
                } else {
                    System.arraycopy(mBlock, mIndex, block, iRow * jWidth, jWidth);
                    mIndex += jWidth;
                }
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), 1, nCols);
    }

    public FieldMatrix<T> getColumnMatrix(int column) throws OutOfRangeException {
        checkColumnIndex(column);
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), this.rows, 1);
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outBlockIndex = 0;
        int outIndex = 0;
        FieldElement[] outBlock = out.blocks[0];
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = blockHeight(iBlock);
            FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            while (i < iHeight) {
                if (outIndex >= outBlock.length) {
                    outBlockIndex++;
                    outBlock = out.blocks[outBlockIndex];
                    outIndex = 0;
                }
                int outIndex2 = outIndex + 1;
                outBlock[outIndex] = block[(i * jWidth) + jColumn];
                i++;
                outIndex = outIndex2;
            }
        }
        return out;
    }

    public void setColumnMatrix(int column, FieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
        try {
            setColumnMatrix(column, (BlockFieldMatrix) matrix);
        } catch (ClassCastException e) {
            super.setColumnMatrix(column, matrix);
        }
    }

    void setColumnMatrix(int column, BlockFieldMatrix<T> matrix) throws MatrixDimensionMismatchException, OutOfRangeException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        if (matrix.getRowDimension() == nRows && matrix.getColumnDimension() == 1) {
            int jBlock = column / BLOCK_SIZE;
            int jColumn = column - (jBlock * BLOCK_SIZE);
            int jWidth = blockWidth(jBlock);
            int mBlockIndex = 0;
            int mIndex = 0;
            FieldElement[] mBlock = matrix.blocks[0];
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int iHeight = blockHeight(iBlock);
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int i = 0;
                while (i < iHeight) {
                    if (mIndex >= mBlock.length) {
                        mBlockIndex++;
                        mBlock = matrix.blocks[mBlockIndex];
                        mIndex = 0;
                    }
                    int mIndex2 = mIndex + 1;
                    block[(i * jWidth) + jColumn] = mBlock[mIndex];
                    i++;
                    mIndex = mIndex2;
                }
            }
            return;
        }
        throw new MatrixDimensionMismatchException(matrix.getRowDimension(), matrix.getColumnDimension(), nRows, 1);
    }

    public FieldVector<T> getRowVector(int row) throws OutOfRangeException {
        checkRowIndex(row);
        FieldElement[] outData = (FieldElement[]) MathArrays.buildArray(getField(), this.columns);
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            System.arraycopy(this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, outData, outIndex, jWidth);
            outIndex += jWidth;
        }
        return new ArrayFieldVector(getField(), outData, false);
    }

    public void setRowVector(int row, FieldVector<T> vector) throws MatrixDimensionMismatchException, OutOfRangeException {
        try {
            setRow(row, ((ArrayFieldVector) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setRowVector(row, vector);
        }
    }

    public FieldVector<T> getColumnVector(int column) throws OutOfRangeException {
        checkColumnIndex(column);
        FieldElement[] outData = (FieldElement[]) MathArrays.buildArray(getField(), this.rows);
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        int iBlock = 0;
        while (iBlock < this.blockRows) {
            int iHeight = blockHeight(iBlock);
            FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            int outIndex2 = outIndex;
            while (i < iHeight) {
                outIndex = outIndex2 + 1;
                outData[outIndex2] = block[(i * jWidth) + jColumn];
                i++;
                outIndex2 = outIndex;
            }
            iBlock++;
            outIndex = outIndex2;
        }
        return new ArrayFieldVector(getField(), outData, false);
    }

    public void setColumnVector(int column, FieldVector<T> vector) throws OutOfRangeException, MatrixDimensionMismatchException {
        try {
            setColumn(column, ((ArrayFieldVector) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setColumnVector(column, vector);
        }
    }

    public T[] getRow(int row) throws OutOfRangeException {
        checkRowIndex(row);
        FieldElement[] out = (FieldElement[]) MathArrays.buildArray(getField(), this.columns);
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            System.arraycopy(this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, out, outIndex, jWidth);
            outIndex += jWidth;
        }
        return out;
    }

    public void setRow(int row, T[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
        checkRowIndex(row);
        int nCols = getColumnDimension();
        if (array.length != nCols) {
            throw new MatrixDimensionMismatchException(1, array.length, 1, nCols);
        }
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            System.arraycopy(array, outIndex, this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, jWidth);
            outIndex += jWidth;
        }
    }

    public T[] getColumn(int column) throws OutOfRangeException {
        checkColumnIndex(column);
        FieldElement[] out = (FieldElement[]) MathArrays.buildArray(getField(), this.rows);
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        int iBlock = 0;
        while (iBlock < this.blockRows) {
            int iHeight = blockHeight(iBlock);
            FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            int outIndex2 = outIndex;
            while (i < iHeight) {
                outIndex = outIndex2 + 1;
                out[outIndex2] = block[(i * jWidth) + jColumn];
                i++;
                outIndex2 = outIndex;
            }
            iBlock++;
            outIndex = outIndex2;
        }
        return out;
    }

    public void setColumn(int column, T[] array) throws MatrixDimensionMismatchException, OutOfRangeException {
        checkColumnIndex(column);
        int nRows = getRowDimension();
        if (array.length != nRows) {
            throw new MatrixDimensionMismatchException(array.length, 1, nRows, 1);
        }
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        int iBlock = 0;
        while (iBlock < this.blockRows) {
            int iHeight = blockHeight(iBlock);
            FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
            int i = 0;
            int outIndex2 = outIndex;
            while (i < iHeight) {
                outIndex = outIndex2 + 1;
                block[(i * jWidth) + jColumn] = array[outIndex2];
                i++;
                outIndex2 = outIndex;
            }
            iBlock++;
            outIndex = outIndex2;
        }
    }

    public T getEntry(int row, int column) throws OutOfRangeException {
        checkRowIndex(row);
        checkColumnIndex(column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        return this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE))];
    }

    public void setEntry(int row, int column, T value) throws OutOfRangeException {
        checkRowIndex(row);
        checkColumnIndex(column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE))] = value;
    }

    public void addToEntry(int row, int column, T increment) throws OutOfRangeException {
        checkRowIndex(row);
        checkColumnIndex(column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        int k = ((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE));
        FieldElement[] blockIJ = this.blocks[(this.blockColumns * iBlock) + jBlock];
        blockIJ[k] = (FieldElement) blockIJ[k].add(increment);
    }

    public void multiplyEntry(int row, int column, T factor) throws OutOfRangeException {
        checkRowIndex(row);
        checkColumnIndex(column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        int k = ((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE));
        FieldElement[] blockIJ = this.blocks[(this.blockColumns * iBlock) + jBlock];
        blockIJ[k] = (FieldElement) blockIJ[k].multiply((Object) factor);
    }

    public FieldMatrix<T> transpose() {
        int nRows = getRowDimension();
        int nCols = getColumnDimension();
        BlockFieldMatrix<T> out = new BlockFieldMatrix(getField(), nCols, nRows);
        int blockIndex = 0;
        int iBlock = 0;
        while (true) {
            int i = this.blockColumns;
            if (iBlock >= r0) {
                return out;
            }
            int jBlock = 0;
            while (true) {
                i = this.blockRows;
                if (jBlock >= r0) {
                    break;
                }
                FieldElement[] outBlock = out.blocks[blockIndex];
                FieldElement[] tBlock = this.blocks[(this.blockColumns * jBlock) + iBlock];
                int pStart = iBlock * BLOCK_SIZE;
                int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.columns);
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.rows);
                int k = 0;
                for (int p = pStart; p < pEnd; p++) {
                    int lInc = pEnd - pStart;
                    int l = p - pStart;
                    for (int q = qStart; q < qEnd; q++) {
                        outBlock[k] = tBlock[l];
                        k++;
                        l += lInc;
                    }
                }
                blockIndex++;
                jBlock++;
            }
            iBlock++;
        }
    }

    public int getRowDimension() {
        return this.rows;
    }

    public int getColumnDimension() {
        return this.columns;
    }

    public T[] operate(T[] v) throws DimensionMismatchException {
        if (v.length != this.columns) {
            throw new DimensionMismatchException(v.length, this.columns);
        }
        FieldElement[] out = (FieldElement[]) MathArrays.buildArray(getField(), this.rows);
        T zero = (FieldElement) getField().getZero();
        int iBlock = 0;
        while (true) {
            int i = this.blockRows;
            if (iBlock >= r0) {
                return out;
            }
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            int jBlock = 0;
            while (true) {
                i = this.blockColumns;
                if (jBlock >= r0) {
                    break;
                }
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                int k = 0;
                int p = pStart;
                while (p < pEnd) {
                    T sum = zero;
                    int q = qStart;
                    while (q < qEnd - 3) {
                        FieldElement sum2 = (FieldElement) ((FieldElement) ((FieldElement) ((FieldElement) sum.add((FieldElement) block[k].multiply(v[q]))).add((FieldElement) block[k + 1].multiply(v[q + 1]))).add((FieldElement) block[k + 2].multiply(v[q + 2]))).add((FieldElement) block[k + 3].multiply(v[q + 3]));
                        k += 4;
                        q += 4;
                    }
                    int q2 = q;
                    int k2 = k;
                    while (q2 < qEnd) {
                        sum2 = (FieldElement) sum.add((FieldElement) block[k2].multiply(v[q2]));
                        q2++;
                        k2++;
                    }
                    out[p] = (FieldElement) out[p].add(sum);
                    p++;
                    k = k2;
                }
                jBlock++;
            }
            iBlock++;
        }
    }

    public T[] preMultiply(T[] v) throws DimensionMismatchException {
        if (v.length != this.rows) {
            throw new DimensionMismatchException(v.length, this.rows);
        }
        FieldElement[] out = (FieldElement[]) MathArrays.buildArray(getField(), this.columns);
        T zero = (FieldElement) getField().getZero();
        int jBlock = 0;
        while (true) {
            int i = this.blockColumns;
            if (jBlock >= r0) {
                return out;
            }
            int jWidth = blockWidth(jBlock);
            int jWidth2 = jWidth + jWidth;
            int jWidth3 = jWidth2 + jWidth;
            int jWidth4 = jWidth3 + jWidth;
            int qStart = jBlock * BLOCK_SIZE;
            int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
            int iBlock = 0;
            while (true) {
                i = this.blockRows;
                if (iBlock >= r0) {
                    break;
                }
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int pStart = iBlock * BLOCK_SIZE;
                int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                for (int q = qStart; q < qEnd; q++) {
                    int k = q - qStart;
                    T sum = zero;
                    int p = pStart;
                    while (p < pEnd - 3) {
                        FieldElement sum2 = (FieldElement) ((FieldElement) ((FieldElement) ((FieldElement) sum.add((FieldElement) block[k].multiply(v[p]))).add((FieldElement) block[k + jWidth].multiply(v[p + 1]))).add((FieldElement) block[k + jWidth2].multiply(v[p + 2]))).add((FieldElement) block[k + jWidth3].multiply(v[p + 3]));
                        k += jWidth4;
                        p += 4;
                    }
                    for (int p2 = p; p2 < pEnd; p2++) {
                        sum2 = (FieldElement) sum.add((FieldElement) block[k].multiply(v[p2]));
                        k += jWidth;
                    }
                    out[q] = (FieldElement) out[q].add(sum);
                }
                iBlock++;
            }
            jBlock++;
        }
    }

    public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (p - pStart) * jWidth;
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public T walkInRowOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / BLOCK_SIZE; iBlock < (endRow / BLOCK_SIZE) + 1; iBlock++) {
            int p0 = iBlock * BLOCK_SIZE;
            int pStart = FastMath.max(startRow, p0);
            int pEnd = FastMath.min((iBlock + 1) * BLOCK_SIZE, endRow + 1);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = startColumn / BLOCK_SIZE; jBlock < (endColumn / BLOCK_SIZE) + 1; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int q0 = jBlock * BLOCK_SIZE;
                    int qStart = FastMath.max(startColumn, q0);
                    int qEnd = FastMath.min((jBlock + 1) * BLOCK_SIZE, endColumn + 1);
                    FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public T walkInRowOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / BLOCK_SIZE; iBlock < (endRow / BLOCK_SIZE) + 1; iBlock++) {
            int p0 = iBlock * BLOCK_SIZE;
            int pStart = FastMath.max(startRow, p0);
            int pEnd = FastMath.min((iBlock + 1) * BLOCK_SIZE, endRow + 1);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = startColumn / BLOCK_SIZE; jBlock < (endColumn / BLOCK_SIZE) + 1; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int q0 = jBlock * BLOCK_SIZE;
                    int qStart = FastMath.max(startColumn, q0);
                    int qEnd = FastMath.min((jBlock + 1) * BLOCK_SIZE, endColumn + 1);
                    FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                FieldElement[] block = this.blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; p++) {
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
                blockIndex++;
            }
        }
        return visitor.end();
    }

    public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                FieldElement[] block = this.blocks[blockIndex];
                int k = 0;
                for (int p = pStart; p < pEnd; p++) {
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
                blockIndex++;
            }
        }
        return visitor.end();
    }

    public T walkInOptimizedOrder(FieldMatrixChangingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / BLOCK_SIZE; iBlock < (endRow / BLOCK_SIZE) + 1; iBlock++) {
            int p0 = iBlock * BLOCK_SIZE;
            int pStart = FastMath.max(startRow, p0);
            int pEnd = FastMath.min((iBlock + 1) * BLOCK_SIZE, endRow + 1);
            for (int jBlock = startColumn / BLOCK_SIZE; jBlock < (endColumn / BLOCK_SIZE) + 1; jBlock++) {
                int jWidth = blockWidth(jBlock);
                int q0 = jBlock * BLOCK_SIZE;
                int qStart = FastMath.max(startColumn, q0);
                int qEnd = FastMath.min((jBlock + 1) * BLOCK_SIZE, endColumn + 1);
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (int p = pStart; p < pEnd; p++) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; q++) {
                        block[k] = visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    public T walkInOptimizedOrder(FieldMatrixPreservingVisitor<T> visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        checkSubMatrixIndex(startRow, endRow, startColumn, endColumn);
        visitor.start(this.rows, this.columns, startRow, endRow, startColumn, endColumn);
        for (int iBlock = startRow / BLOCK_SIZE; iBlock < (endRow / BLOCK_SIZE) + 1; iBlock++) {
            int p0 = iBlock * BLOCK_SIZE;
            int pStart = FastMath.max(startRow, p0);
            int pEnd = FastMath.min((iBlock + 1) * BLOCK_SIZE, endRow + 1);
            for (int jBlock = startColumn / BLOCK_SIZE; jBlock < (endColumn / BLOCK_SIZE) + 1; jBlock++) {
                int jWidth = blockWidth(jBlock);
                int q0 = jBlock * BLOCK_SIZE;
                int qStart = FastMath.max(startColumn, q0);
                int qEnd = FastMath.min((jBlock + 1) * BLOCK_SIZE, endColumn + 1);
                FieldElement[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (int p = pStart; p < pEnd; p++) {
                    int k = (((p - p0) * jWidth) + qStart) - q0;
                    for (int q = qStart; q < qEnd; q++) {
                        visitor.visit(p, q, block[k]);
                        k++;
                    }
                }
            }
        }
        return visitor.end();
    }

    private int blockHeight(int blockRow) {
        return blockRow == this.blockRows + -1 ? this.rows - (blockRow * BLOCK_SIZE) : BLOCK_SIZE;
    }

    private int blockWidth(int blockColumn) {
        return blockColumn == this.blockColumns + -1 ? this.columns - (blockColumn * BLOCK_SIZE) : BLOCK_SIZE;
    }
}
