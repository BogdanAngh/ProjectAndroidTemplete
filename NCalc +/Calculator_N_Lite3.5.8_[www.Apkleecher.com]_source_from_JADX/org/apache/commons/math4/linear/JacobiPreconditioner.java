package org.apache.commons.math4.linear;

import org.apache.commons.math4.analysis.function.Sqrt;
import org.apache.commons.math4.util.MathArrays;

public class JacobiPreconditioner extends RealLinearOperator {
    private final ArrayRealVector diag;

    class 1 extends RealLinearOperator {
        private final /* synthetic */ RealVector val$sqrtDiag;

        1(RealVector realVector) {
            this.val$sqrtDiag = realVector;
        }

        public RealVector operate(RealVector x) {
            return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), this.val$sqrtDiag.toArray()), false);
        }

        public int getRowDimension() {
            return this.val$sqrtDiag.getDimension();
        }

        public int getColumnDimension() {
            return this.val$sqrtDiag.getDimension();
        }
    }

    public JacobiPreconditioner(double[] diag, boolean deep) {
        this.diag = new ArrayRealVector(diag, deep);
    }

    public static JacobiPreconditioner create(RealLinearOperator a) throws NonSquareOperatorException {
        int n = a.getColumnDimension();
        if (a.getRowDimension() != n) {
            throw new NonSquareOperatorException(a.getRowDimension(), n);
        }
        double[] diag = new double[n];
        int i;
        if (a instanceof AbstractRealMatrix) {
            AbstractRealMatrix m = (AbstractRealMatrix) a;
            for (i = 0; i < n; i++) {
                diag[i] = m.getEntry(i, i);
            }
        } else {
            ArrayRealVector x = new ArrayRealVector(n);
            for (i = 0; i < n; i++) {
                x.set(0.0d);
                x.setEntry(i, 1.0d);
                diag[i] = a.operate(x).getEntry(i);
            }
        }
        return new JacobiPreconditioner(diag, false);
    }

    public int getColumnDimension() {
        return this.diag.getDimension();
    }

    public int getRowDimension() {
        return this.diag.getDimension();
    }

    public RealVector operate(RealVector x) {
        return new ArrayRealVector(MathArrays.ebeDivide(x.toArray(), this.diag.toArray()), false);
    }

    public RealLinearOperator sqrt() {
        return new 1(this.diag.map(new Sqrt()));
    }
}
