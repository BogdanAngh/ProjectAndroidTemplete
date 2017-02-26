package org.apache.commons.math4.linear;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.Arrays;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.NoDataException;
import org.apache.commons.math4.exception.NotStrictlyPositiveException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.OutOfRangeException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.MathUtils;

public class BlockRealMatrix extends AbstractRealMatrix implements Serializable {
    public static final int BLOCK_SIZE = 52;
    private static final long serialVersionUID = 4991895511313664478L;
    private final int blockColumns;
    private final int blockRows;
    private final double[][] blocks;
    private final int columns;
    private final int rows;

    public BlockRealMatrix(int rows, int columns) throws NotStrictlyPositiveException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blocks = createBlocksLayout(rows, columns);
    }

    public BlockRealMatrix(double[][] rawData) throws DimensionMismatchException, NotStrictlyPositiveException {
        this(rawData.length, rawData[0].length, toBlocksLayout(rawData), false);
    }

    public BlockRealMatrix(int rows, int columns, double[][] blockData, boolean copyArray) throws DimensionMismatchException, NotStrictlyPositiveException {
        super(rows, columns);
        this.rows = rows;
        this.columns = columns;
        this.blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        this.blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        if (copyArray) {
            this.blocks = new double[(this.blockRows * this.blockColumns)][];
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
                    this.blocks[index] = (double[]) blockData[index].clone();
                }
                jBlock++;
                index++;
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static double[][] toBlocksLayout(double[][] r22) throws org.apache.commons.math4.exception.DimensionMismatchException {
        /*
        r0 = r22;
        r0 = r0.length;
        r20 = r0;
        r21 = 0;
        r21 = r22[r21];
        r0 = r21;
        r7 = r0.length;
        r21 = r20 + 52;
        r21 = r21 + -1;
        r5 = r21 / 52;
        r21 = r7 + 52;
        r21 = r21 + -1;
        r3 = r21 / 52;
        r8 = 0;
    L_0x0019:
        r0 = r22;
        r0 = r0.length;
        r21 = r0;
        r0 = r21;
        if (r8 < r0) goto L_0x002d;
    L_0x0022:
        r21 = r5 * r3;
        r0 = r21;
        r6 = new double[r0][];
        r4 = 0;
        r9 = 0;
    L_0x002a:
        if (r9 < r5) goto L_0x003f;
    L_0x002c:
        return r6;
    L_0x002d:
        r21 = r22[r8];
        r0 = r21;
        r14 = r0.length;
        if (r14 == r7) goto L_0x003c;
    L_0x0034:
        r21 = new org.apache.commons.math4.exception.DimensionMismatchException;
        r0 = r21;
        r0.<init>(r7, r14);
        throw r21;
    L_0x003c:
        r8 = r8 + 1;
        goto L_0x0019;
    L_0x003f:
        r17 = r9 * 52;
        r21 = r17 + 52;
        r0 = r21;
        r1 = r20;
        r16 = org.apache.commons.math4.util.FastMath.min(r0, r1);
        r10 = r16 - r17;
        r12 = 0;
    L_0x004e:
        if (r12 < r3) goto L_0x0053;
    L_0x0050:
        r9 = r9 + 1;
        goto L_0x002a;
    L_0x0053:
        r19 = r12 * 52;
        r21 = r19 + 52;
        r0 = r21;
        r18 = org.apache.commons.math4.util.FastMath.min(r0, r7);
        r13 = r18 - r19;
        r21 = r10 * r13;
        r0 = r21;
        r2 = new double[r0];
        r6[r4] = r2;
        r11 = 0;
        r15 = r17;
    L_0x006a:
        r0 = r16;
        if (r15 < r0) goto L_0x0073;
    L_0x006e:
        r4 = r4 + 1;
        r12 = r12 + 1;
        goto L_0x004e;
    L_0x0073:
        r21 = r22[r15];
        r0 = r21;
        r1 = r19;
        java.lang.System.arraycopy(r0, r1, r2, r11, r13);
        r11 = r11 + r13;
        r15 = r15 + 1;
        goto L_0x006a;
        */
        throw new UnsupportedOperationException("Method not decompiled: org.apache.commons.math4.linear.BlockRealMatrix.toBlocksLayout(double[][]):double[][]");
    }

    public static double[][] createBlocksLayout(int rows, int columns) {
        int blockRows = ((rows + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        int blockColumns = ((columns + BLOCK_SIZE) - 1) / BLOCK_SIZE;
        double[][] blocks = new double[(blockRows * blockColumns)][];
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int iHeight = FastMath.min(pStart + BLOCK_SIZE, rows) - pStart;
            for (int jBlock = 0; jBlock < blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                blocks[blockIndex] = new double[(iHeight * (FastMath.min(qStart + BLOCK_SIZE, columns) - qStart))];
                blockIndex++;
            }
        }
        return blocks;
    }

    public BlockRealMatrix createMatrix(int rowDimension, int columnDimension) throws NotStrictlyPositiveException {
        return new BlockRealMatrix(rowDimension, columnDimension);
    }

    public BlockRealMatrix copy() {
        BlockRealMatrix copied = new BlockRealMatrix(this.rows, this.columns);
        for (int i = 0; i < this.blocks.length; i++) {
            System.arraycopy(this.blocks[i], 0, copied.blocks[i], 0, this.blocks[i].length);
        }
        return copied;
    }

    public BlockRealMatrix add(RealMatrix m) throws MatrixDimensionMismatchException {
        BlockRealMatrix add;
        try {
            add = add((BlockRealMatrix) m);
        } catch (ClassCastException e) {
            MatrixUtils.checkAdditionCompatible(this, m);
            add = new BlockRealMatrix(this.rows, this.columns);
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
                    double[] outBlock = add.blocks[blockIndex];
                    double[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * BLOCK_SIZE;
                    int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = tBlock[k] + m.getEntry(p, q);
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

    public BlockRealMatrix add(BlockRealMatrix m) throws MatrixDimensionMismatchException {
        MatrixUtils.checkAdditionCompatible(this, m);
        BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            double[] outBlock = out.blocks[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] + mBlock[k];
            }
        }
        return out;
    }

    public BlockRealMatrix subtract(RealMatrix m) throws MatrixDimensionMismatchException {
        BlockRealMatrix subtract;
        try {
            subtract = subtract((BlockRealMatrix) m);
        } catch (ClassCastException e) {
            MatrixUtils.checkSubtractionCompatible(this, m);
            subtract = new BlockRealMatrix(this.rows, this.columns);
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
                    double[] outBlock = subtract.blocks[blockIndex];
                    double[] tBlock = this.blocks[blockIndex];
                    int pStart = iBlock * BLOCK_SIZE;
                    int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        for (int q = qStart; q < qEnd; q++) {
                            outBlock[k] = tBlock[k] - m.getEntry(p, q);
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

    public BlockRealMatrix subtract(BlockRealMatrix m) throws MatrixDimensionMismatchException {
        MatrixUtils.checkSubtractionCompatible(this, m);
        BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            double[] outBlock = out.blocks[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            double[] mBlock = m.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] - mBlock[k];
            }
        }
        return out;
    }

    public BlockRealMatrix scalarAdd(double d) {
        BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            double[] outBlock = out.blocks[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] + d;
            }
        }
        return out;
    }

    public RealMatrix scalarMultiply(double d) {
        BlockRealMatrix out = new BlockRealMatrix(this.rows, this.columns);
        for (int blockIndex = 0; blockIndex < out.blocks.length; blockIndex++) {
            double[] outBlock = out.blocks[blockIndex];
            double[] tBlock = this.blocks[blockIndex];
            for (int k = 0; k < outBlock.length; k++) {
                outBlock[k] = tBlock[k] * d;
            }
        }
        return out;
    }

    public BlockRealMatrix multiply(RealMatrix m) throws DimensionMismatchException {
        BlockRealMatrix multiply;
        try {
            multiply = multiply((BlockRealMatrix) m);
        } catch (ClassCastException e) {
            MatrixUtils.checkMultiplicationCompatible(this, m);
            multiply = new BlockRealMatrix(this.rows, m.getColumnDimension());
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
                    double[] outBlock = multiply.blocks[blockIndex];
                    int kBlock = 0;
                    while (true) {
                        i = this.blockColumns;
                        if (kBlock >= r0) {
                            break;
                        }
                        int kWidth = blockWidth(kBlock);
                        double[] tBlock = this.blocks[(this.blockColumns * iBlock) + kBlock];
                        int rStart = kBlock * BLOCK_SIZE;
                        int k = 0;
                        for (int p = pStart; p < pEnd; p++) {
                            int lStart = (p - pStart) * kWidth;
                            int lEnd = lStart + kWidth;
                            for (int q = qStart; q < qEnd; q++) {
                                double sum = 0.0d;
                                int r = rStart;
                                for (int l = lStart; l < lEnd; l++) {
                                    sum += tBlock[l] * m.getEntry(r, q);
                                    r++;
                                }
                                outBlock[k] = outBlock[k] + sum;
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

    public BlockRealMatrix multiply(BlockRealMatrix m) throws DimensionMismatchException {
        MatrixUtils.checkMultiplicationCompatible(this, m);
        BlockRealMatrix blockRealMatrix = new BlockRealMatrix(this.rows, m.columns);
        int blockIndex = 0;
        int iBlock = 0;
        while (true) {
            int i = blockRealMatrix.blockRows;
            if (iBlock >= r0) {
                return blockRealMatrix;
            }
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            int jBlock = 0;
            while (true) {
                i = blockRealMatrix.blockColumns;
                if (jBlock >= r0) {
                    break;
                }
                int jWidth = blockRealMatrix.blockWidth(jBlock);
                int jWidth2 = jWidth + jWidth;
                int jWidth3 = jWidth2 + jWidth;
                int jWidth4 = jWidth3 + jWidth;
                double[] outBlock = blockRealMatrix.blocks[blockIndex];
                int kBlock = 0;
                while (true) {
                    i = this.blockColumns;
                    if (kBlock >= r0) {
                        break;
                    }
                    int kWidth = blockWidth(kBlock);
                    double[] tBlock = this.blocks[(this.blockColumns * iBlock) + kBlock];
                    double[] mBlock = m.blocks[(m.blockColumns * kBlock) + jBlock];
                    int k = 0;
                    for (int p = pStart; p < pEnd; p++) {
                        int lStart = (p - pStart) * kWidth;
                        int lEnd = lStart + kWidth;
                        for (int nStart = 0; nStart < jWidth; nStart++) {
                            double sum = 0.0d;
                            int l = lStart;
                            int n = nStart;
                            while (l < lEnd - 3) {
                                sum += (((tBlock[l] * mBlock[n]) + (tBlock[l + 1] * mBlock[n + jWidth])) + (tBlock[l + 2] * mBlock[n + jWidth2])) + (tBlock[l + 3] * mBlock[n + jWidth3]);
                                l += 4;
                                n += jWidth4;
                            }
                            for (int l2 = l; l2 < lEnd; l2++) {
                                sum += tBlock[l2] * mBlock[n];
                                n += jWidth;
                            }
                            outBlock[k] = outBlock[k] + sum;
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

    public double[][] getData() {
        double[][] data = (double[][]) Array.newInstance(Double.TYPE, new int[]{getRowDimension(), getColumnDimension()});
        int lastColumns = this.columns - ((this.blockColumns - 1) * BLOCK_SIZE);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            int regularPos = 0;
            int lastPos = 0;
            for (int p = pStart; p < pEnd; p++) {
                double[] dataP = data[p];
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

    public double getNorm() {
        double[] colSums = new double[BLOCK_SIZE];
        double maxColSum = 0.0d;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            Arrays.fill(colSums, 0, jWidth, 0.0d);
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int j;
                int iHeight = blockHeight(iBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (j = 0; j < jWidth; j++) {
                    double sum = 0.0d;
                    for (int i = 0; i < iHeight; i++) {
                        sum += FastMath.abs(block[(i * jWidth) + j]);
                    }
                    colSums[j] = colSums[j] + sum;
                }
            }
            for (j = 0; j < jWidth; j++) {
                maxColSum = FastMath.max(maxColSum, colSums[j]);
            }
        }
        return maxColSum;
    }

    public double getFrobeniusNorm() {
        double sum2 = 0.0d;
        for (double[] dArr : this.blocks) {
            for (double entry : this.blocks[blockIndex]) {
                sum2 += entry * entry;
            }
        }
        return FastMath.sqrt(sum2);
    }

    public BlockRealMatrix getSubMatrix(int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
        BlockRealMatrix blockRealMatrix = new BlockRealMatrix((endRow - startRow) + 1, (endColumn - startColumn) + 1);
        int rowsShift = startRow % BLOCK_SIZE;
        int blockStartColumn = startColumn / BLOCK_SIZE;
        int columnsShift = startColumn % BLOCK_SIZE;
        int pBlock = startRow / BLOCK_SIZE;
        for (int iBlock = 0; iBlock < blockRealMatrix.blockRows; iBlock++) {
            int iHeight = blockRealMatrix.blockHeight(iBlock);
            int qBlock = blockStartColumn;
            for (int jBlock = 0; jBlock < blockRealMatrix.blockColumns; jBlock++) {
                int jWidth = blockRealMatrix.blockWidth(jBlock);
                double[] outBlock = blockRealMatrix.blocks[(blockRealMatrix.blockColumns * iBlock) + jBlock];
                int index = (this.blockColumns * pBlock) + qBlock;
                int width = blockWidth(qBlock);
                int heightExcess = (iHeight + rowsShift) - 52;
                int widthExcess = (jWidth + columnsShift) - 52;
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
        return blockRealMatrix;
    }

    private void copyBlockPart(double[] srcBlock, int srcWidth, int srcStartRow, int srcEndRow, int srcStartColumn, int srcEndColumn, double[] dstBlock, int dstWidth, int dstStartRow, int dstStartColumn) {
        int length = srcEndColumn - srcStartColumn;
        int srcPos = (srcStartRow * srcWidth) + srcStartColumn;
        int dstPos = (dstStartRow * dstWidth) + dstStartColumn;
        for (int srcRow = srcStartRow; srcRow < srcEndRow; srcRow++) {
            System.arraycopy(srcBlock, srcPos, dstBlock, dstPos, length);
            srcPos += srcWidth;
            dstPos += dstWidth;
        }
    }

    public void setSubMatrix(double[][] subMatrix, int row, int column) throws OutOfRangeException, NoDataException, NullArgumentException, DimensionMismatchException {
        MathUtils.checkNotNull(subMatrix);
        int refLength = subMatrix[0].length;
        if (refLength == 0) {
            throw new NoDataException(LocalizedFormats.AT_LEAST_ONE_COLUMN);
        }
        int endRow = (subMatrix.length + row) - 1;
        int endColumn = (column + refLength) - 1;
        MatrixUtils.checkSubMatrixIndex(this, row, endRow, column, endColumn);
        for (double[] subRow : subMatrix) {
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
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                for (int i = iStart; i < iEnd; i++) {
                    System.arraycopy(subMatrix[i - row], jStart - column, block, ((i - firstRow) * jWidth) + (jStart - firstColumn), jLength);
                }
            }
        }
    }

    public BlockRealMatrix getRowMatrix(int row) throws OutOfRangeException {
        MatrixUtils.checkRowIndex(this, row);
        BlockRealMatrix out = new BlockRealMatrix(1, this.columns);
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[0];
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public void setRowMatrix(int row, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        try {
            setRowMatrix(row, (BlockRealMatrix) matrix);
        } catch (ClassCastException e) {
            super.setRowMatrix(row, matrix);
        }
    }

    public void setRowMatrix(int row, BlockRealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        MatrixUtils.checkRowIndex(this, row);
        int nCols = getColumnDimension();
        if (matrix.getRowDimension() == 1 && matrix.getColumnDimension() == nCols) {
            int iBlock = row / BLOCK_SIZE;
            int iRow = row - (iBlock * BLOCK_SIZE);
            int mBlockIndex = 0;
            int mIndex = 0;
            double[] mBlock = matrix.blocks[0];
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int jWidth = blockWidth(jBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public BlockRealMatrix getColumnMatrix(int column) throws OutOfRangeException {
        MatrixUtils.checkColumnIndex(this, column);
        BlockRealMatrix out = new BlockRealMatrix(this.rows, 1);
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outBlockIndex = 0;
        int outIndex = 0;
        double[] outBlock = out.blocks[0];
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int iHeight = blockHeight(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public void setColumnMatrix(int column, RealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        try {
            setColumnMatrix(column, (BlockRealMatrix) matrix);
        } catch (ClassCastException e) {
            super.setColumnMatrix(column, matrix);
        }
    }

    void setColumnMatrix(int column, BlockRealMatrix matrix) throws OutOfRangeException, MatrixDimensionMismatchException {
        MatrixUtils.checkColumnIndex(this, column);
        int nRows = getRowDimension();
        if (matrix.getRowDimension() == nRows && matrix.getColumnDimension() == 1) {
            int jBlock = column / BLOCK_SIZE;
            int jColumn = column - (jBlock * BLOCK_SIZE);
            int jWidth = blockWidth(jBlock);
            int mBlockIndex = 0;
            int mIndex = 0;
            double[] mBlock = matrix.blocks[0];
            for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
                int iHeight = blockHeight(iBlock);
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public RealVector getRowVector(int row) throws OutOfRangeException {
        MatrixUtils.checkRowIndex(this, row);
        double[] outData = new double[this.columns];
        int iBlock = row / BLOCK_SIZE;
        int iRow = row - (iBlock * BLOCK_SIZE);
        int outIndex = 0;
        for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
            int jWidth = blockWidth(jBlock);
            System.arraycopy(this.blocks[(this.blockColumns * iBlock) + jBlock], iRow * jWidth, outData, outIndex, jWidth);
            outIndex += jWidth;
        }
        return new ArrayRealVector(outData, false);
    }

    public void setRowVector(int row, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
        try {
            setRow(row, ((ArrayRealVector) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setRowVector(row, vector);
        }
    }

    public RealVector getColumnVector(int column) throws OutOfRangeException {
        MatrixUtils.checkColumnIndex(this, column);
        double[] outData = new double[this.rows];
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        int iBlock = 0;
        while (iBlock < this.blockRows) {
            int iHeight = blockHeight(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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
        return new ArrayRealVector(outData, false);
    }

    public void setColumnVector(int column, RealVector vector) throws OutOfRangeException, MatrixDimensionMismatchException {
        try {
            setColumn(column, ((ArrayRealVector) vector).getDataRef());
        } catch (ClassCastException e) {
            super.setColumnVector(column, vector);
        }
    }

    public double[] getRow(int row) throws OutOfRangeException {
        MatrixUtils.checkRowIndex(this, row);
        double[] out = new double[this.columns];
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

    public void setRow(int row, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
        MatrixUtils.checkRowIndex(this, row);
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

    public double[] getColumn(int column) throws OutOfRangeException {
        MatrixUtils.checkColumnIndex(this, column);
        double[] out = new double[this.rows];
        int jBlock = column / BLOCK_SIZE;
        int jColumn = column - (jBlock * BLOCK_SIZE);
        int jWidth = blockWidth(jBlock);
        int outIndex = 0;
        int iBlock = 0;
        while (iBlock < this.blockRows) {
            int iHeight = blockHeight(iBlock);
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public void setColumn(int column, double[] array) throws OutOfRangeException, MatrixDimensionMismatchException {
        MatrixUtils.checkColumnIndex(this, column);
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
            double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double getEntry(int row, int column) throws OutOfRangeException {
        MatrixUtils.checkMatrixIndex(this, row, column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        return this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE))];
    }

    public void setEntry(int row, int column, double value) throws OutOfRangeException {
        MatrixUtils.checkMatrixIndex(this, row, column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        this.blocks[(this.blockColumns * iBlock) + jBlock][((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE))] = value;
    }

    public void addToEntry(int row, int column, double increment) throws OutOfRangeException {
        MatrixUtils.checkMatrixIndex(this, row, column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        int k = ((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE));
        double[] dArr = this.blocks[(this.blockColumns * iBlock) + jBlock];
        dArr[k] = dArr[k] + increment;
    }

    public void multiplyEntry(int row, int column, double factor) throws OutOfRangeException {
        MatrixUtils.checkMatrixIndex(this, row, column);
        int iBlock = row / BLOCK_SIZE;
        int jBlock = column / BLOCK_SIZE;
        int k = ((row - (iBlock * BLOCK_SIZE)) * blockWidth(jBlock)) + (column - (jBlock * BLOCK_SIZE));
        double[] dArr = this.blocks[(this.blockColumns * iBlock) + jBlock];
        dArr[k] = dArr[k] * factor;
    }

    public BlockRealMatrix transpose() {
        BlockRealMatrix out = new BlockRealMatrix(getColumnDimension(), getRowDimension());
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
                double[] outBlock = out.blocks[blockIndex];
                double[] tBlock = this.blocks[(this.blockColumns * jBlock) + iBlock];
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

    public double[] operate(double[] v) throws DimensionMismatchException {
        int length = v.length;
        int i = this.columns;
        if (length != r0) {
            throw new DimensionMismatchException(v.length, this.columns);
        }
        double[] out = new double[this.rows];
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                int k = 0;
                int p = pStart;
                while (p < pEnd) {
                    double sum = 0.0d;
                    int q = qStart;
                    while (q < qEnd - 3) {
                        sum += (((block[k] * v[q]) + (block[k + 1] * v[q + 1])) + (block[k + 2] * v[q + 2])) + (block[k + 3] * v[q + 3]);
                        k += 4;
                        q += 4;
                    }
                    int q2 = q;
                    int k2 = k;
                    while (q2 < qEnd) {
                        sum += block[k2] * v[q2];
                        q2++;
                        k2++;
                    }
                    out[p] = out[p] + sum;
                    p++;
                    k = k2;
                }
            }
        }
        return out;
    }

    public double[] preMultiply(double[] v) throws DimensionMismatchException {
        if (v.length != this.rows) {
            throw new DimensionMismatchException(v.length, this.rows);
        }
        double[] out = new double[this.columns];
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
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
                int pStart = iBlock * BLOCK_SIZE;
                int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
                for (int q = qStart; q < qEnd; q++) {
                    int k = q - qStart;
                    double sum = 0.0d;
                    int p = pStart;
                    while (p < pEnd - 3) {
                        sum += (((block[k] * v[p]) + (block[k + jWidth] * v[p + 1])) + (block[k + jWidth2] * v[p + 2])) + (block[k + jWidth3] * v[p + 3]);
                        k += jWidth4;
                        p += 4;
                    }
                    for (int p2 = p; p2 < pEnd; p2++) {
                        sum += block[k] * v[p2];
                        k += jWidth;
                    }
                    out[q] = out[q] + sum;
                }
                iBlock++;
            }
            jBlock++;
        }
    }

    public double walkInRowOrder(RealMatrixChangingVisitor visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double walkInRowOrder(RealMatrixPreservingVisitor visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int p = pStart; p < pEnd; p++) {
                for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                    int jWidth = blockWidth(jBlock);
                    int qStart = jBlock * BLOCK_SIZE;
                    int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double walkInRowOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
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
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double walkInRowOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
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
                    double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                double[] block = this.blocks[blockIndex];
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

    public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor) {
        visitor.start(this.rows, this.columns, 0, this.rows - 1, 0, this.columns - 1);
        int blockIndex = 0;
        for (int iBlock = 0; iBlock < this.blockRows; iBlock++) {
            int pStart = iBlock * BLOCK_SIZE;
            int pEnd = FastMath.min(pStart + BLOCK_SIZE, this.rows);
            for (int jBlock = 0; jBlock < this.blockColumns; jBlock++) {
                int qStart = jBlock * BLOCK_SIZE;
                int qEnd = FastMath.min(qStart + BLOCK_SIZE, this.columns);
                double[] block = this.blocks[blockIndex];
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

    public double walkInOptimizedOrder(RealMatrixChangingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
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
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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

    public double walkInOptimizedOrder(RealMatrixPreservingVisitor visitor, int startRow, int endRow, int startColumn, int endColumn) throws OutOfRangeException, NumberIsTooSmallException {
        MatrixUtils.checkSubMatrixIndex(this, startRow, endRow, startColumn, endColumn);
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
                double[] block = this.blocks[(this.blockColumns * iBlock) + jBlock];
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
