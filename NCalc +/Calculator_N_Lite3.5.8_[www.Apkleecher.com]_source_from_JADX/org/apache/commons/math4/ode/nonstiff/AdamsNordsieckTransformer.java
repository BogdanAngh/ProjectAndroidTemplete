package org.apache.commons.math4.ode.nonstiff;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.apache.commons.math4.FieldElement;
import org.apache.commons.math4.fraction.BigFraction;
import org.apache.commons.math4.linear.Array2DRowFieldMatrix;
import org.apache.commons.math4.linear.Array2DRowRealMatrix;
import org.apache.commons.math4.linear.ArrayFieldVector;
import org.apache.commons.math4.linear.FieldDecompositionSolver;
import org.apache.commons.math4.linear.FieldLUDecomposition;
import org.apache.commons.math4.linear.FieldMatrix;
import org.apache.commons.math4.linear.MatrixUtils;
import org.apache.commons.math4.linear.QRDecomposition;

public class AdamsNordsieckTransformer {
    private static final Map<Integer, AdamsNordsieckTransformer> CACHE;
    private final double[] c1;
    private final Array2DRowRealMatrix update;

    static {
        CACHE = new HashMap();
    }

    private AdamsNordsieckTransformer(int nSteps) {
        int i;
        FieldMatrix<BigFraction> bigP = buildP(nSteps);
        FieldDecompositionSolver<BigFraction> pSolver = new FieldLUDecomposition(bigP).getSolver();
        FieldElement[] u = new BigFraction[nSteps];
        Arrays.fill(u, BigFraction.ONE);
        BigFraction[] bigC1 = (BigFraction[]) pSolver.solve(new ArrayFieldVector(u, false)).toArray();
        FieldElement[][] shiftedP = (BigFraction[][]) bigP.getData();
        for (i = shiftedP.length - 1; i > 0; i--) {
            shiftedP[i] = shiftedP[i - 1];
        }
        shiftedP[0] = new BigFraction[nSteps];
        Arrays.fill(shiftedP[0], BigFraction.ZERO);
        this.update = MatrixUtils.bigFractionMatrixToRealMatrix(pSolver.solve(new Array2DRowFieldMatrix(shiftedP, false)));
        this.c1 = new double[nSteps];
        for (i = 0; i < nSteps; i++) {
            this.c1[i] = bigC1[i].doubleValue();
        }
    }

    public static AdamsNordsieckTransformer getInstance(int nSteps) {
        AdamsNordsieckTransformer t;
        synchronized (CACHE) {
            t = (AdamsNordsieckTransformer) CACHE.get(Integer.valueOf(nSteps));
            if (t == null) {
                t = new AdamsNordsieckTransformer(nSteps);
                CACHE.put(Integer.valueOf(nSteps), t);
            }
        }
        return t;
    }

    public int getNSteps() {
        return this.c1.length;
    }

    private FieldMatrix<BigFraction> buildP(int nSteps) {
        FieldElement[][] pData = (BigFraction[][]) Array.newInstance(BigFraction.class, new int[]{nSteps, nSteps});
        for (int i = 0; i < pData.length; i++) {
            BigFraction[] pI = pData[i];
            int factor = -(i + 1);
            int aj = factor;
            for (int j = 0; j < pI.length; j++) {
                pI[j] = new BigFraction((j + 2) * aj);
                aj *= factor;
            }
        }
        return new Array2DRowFieldMatrix(pData, false);
    }

    public Array2DRowRealMatrix initializeHighOrderDerivatives(double h, double[] t, double[][] y, double[][] yDot) {
        int length = y.length;
        int[] iArr = new int[]{(r0 - 1) * 2, this.c1.length};
        double[][] a = (double[][]) Array.newInstance(Double.TYPE, iArr);
        length = y.length;
        iArr = new int[]{(r0 - 1) * 2, y[0].length};
        double[][] b = (double[][]) Array.newInstance(Double.TYPE, iArr);
        double[] y0 = y[0];
        double[] yDot0 = yDot[0];
        int i = 1;
        while (true) {
            length = y.length;
            if (i >= r0) {
                return new Array2DRowRealMatrix(new QRDecomposition(new Array2DRowRealMatrix(a, false)).getSolver().solve(new Array2DRowRealMatrix(b, false)).getData(), false);
            }
            double di = t[i] - t[0];
            double ratio = di / h;
            double dikM1Ohk = 1.0d / h;
            double[] aI = a[(i * 2) - 2];
            double[] aDotI = a[(i * 2) - 1];
            int j = 0;
            while (true) {
                length = aI.length;
                if (j >= r0) {
                    break;
                }
                dikM1Ohk *= ratio;
                aI[j] = di * dikM1Ohk;
                aDotI[j] = ((double) (j + 2)) * dikM1Ohk;
                j++;
            }
            double[] yI = y[i];
            double[] yDotI = yDot[i];
            double[] bI = b[(i * 2) - 2];
            double[] bDotI = b[(i * 2) - 1];
            j = 0;
            while (true) {
                length = yI.length;
                if (j >= r0) {
                    break;
                }
                bI[j] = (yI[j] - y0[j]) - (yDot0[j] * di);
                bDotI[j] = yDotI[j] - yDot0[j];
                j++;
            }
            i++;
        }
    }

    public Array2DRowRealMatrix updateHighOrderDerivativesPhase1(Array2DRowRealMatrix highOrder) {
        return this.update.multiply(highOrder);
    }

    public void updateHighOrderDerivativesPhase2(double[] start, double[] end, Array2DRowRealMatrix highOrder) {
        double[][] data = highOrder.getDataRef();
        for (int i = 0; i < data.length; i++) {
            double[] dataI = data[i];
            double c1I = this.c1[i];
            for (int j = 0; j < dataI.length; j++) {
                dataI[j] = dataI[j] + ((start[j] - end[j]) * c1I);
            }
        }
    }
}
