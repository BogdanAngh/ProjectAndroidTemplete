package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.util.ExceptionContext;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.IterationManager;
import org.apache.commons.math4.util.MathUtils;

public class SymmLQ extends PreconditionedIterativeLinearSolver {
    private static final String OPERATOR = "operator";
    private static final String THRESHOLD = "threshold";
    private static final String VECTOR = "vector";
    private static final String VECTOR1 = "vector1";
    private static final String VECTOR2 = "vector2";
    private final boolean check;
    private final double delta;

    private static class State {
        static final double CBRT_MACH_PREC;
        static final double MACH_PREC;
        private final RealLinearOperator a;
        private final RealVector b;
        private boolean bIsNull;
        private double beta;
        private double beta1;
        private double bstep;
        private double cgnorm;
        private final boolean check;
        private double dbar;
        private final double delta;
        private double gammaZeta;
        private double gbar;
        private double gmax;
        private double gmin;
        private final boolean goodb;
        private boolean hasConverged;
        private double lqnorm;
        private final RealLinearOperator m;
        private final RealVector mb;
        private double minusEpsZeta;
        private double oldb;
        private RealVector r1;
        private RealVector r2;
        private double rnorm;
        private final double shift;
        private double snprod;
        private double tnorm;
        private RealVector wbar;
        private final RealVector xL;
        private RealVector y;
        private double ynorm2;

        static {
            MACH_PREC = FastMath.ulp(1.0d);
            CBRT_MACH_PREC = FastMath.cbrt(MACH_PREC);
        }

        public State(RealLinearOperator a, RealLinearOperator m, RealVector b, boolean goodb, double shift, double delta, boolean check) {
            this.a = a;
            this.m = m;
            this.b = b;
            this.xL = new ArrayRealVector(b.getDimension());
            this.goodb = goodb;
            this.shift = shift;
            if (m != null) {
                b = m.operate(b);
            }
            this.mb = b;
            this.hasConverged = false;
            this.check = check;
            this.delta = delta;
        }

        private static void checkSymmetry(RealLinearOperator l, RealVector x, RealVector y, RealVector z) throws NonSelfAdjointOperatorException {
            double s = y.dotProduct(y);
            double epsa = (MACH_PREC + s) * CBRT_MACH_PREC;
            if (FastMath.abs(s - x.dotProduct(z)) > epsa) {
                NonSelfAdjointOperatorException e = new NonSelfAdjointOperatorException();
                ExceptionContext context = e.getContext();
                context.setValue(SymmLQ.OPERATOR, l);
                context.setValue(SymmLQ.VECTOR1, x);
                context.setValue(SymmLQ.VECTOR2, y);
                context.setValue(SymmLQ.THRESHOLD, Double.valueOf(epsa));
                throw e;
            }
        }

        private static void throwNPDLOException(RealLinearOperator l, RealVector v) throws NonPositiveDefiniteOperatorException {
            NonPositiveDefiniteOperatorException e = new NonPositiveDefiniteOperatorException();
            ExceptionContext context = e.getContext();
            context.setValue(SymmLQ.OPERATOR, l);
            context.setValue(SymmLQ.VECTOR, v);
            throw e;
        }

        private static void daxpy(double a, RealVector x, RealVector y) {
            int n = x.getDimension();
            for (int i = 0; i < n; i++) {
                y.setEntry(i, (x.getEntry(i) * a) + y.getEntry(i));
            }
        }

        private static void daxpbypz(double a, RealVector x, double b, RealVector y, RealVector z) {
            int n = z.getDimension();
            for (int i = 0; i < n; i++) {
                z.setEntry(i, ((x.getEntry(i) * a) + (y.getEntry(i) * b)) + z.getEntry(i));
            }
        }

        void refineSolution(RealVector x) {
            int n = this.xL.getDimension();
            double d;
            double step;
            int i;
            if (this.lqnorm >= this.cgnorm) {
                double diag;
                double anorm = FastMath.sqrt(this.tnorm);
                if (this.gbar == 0.0d) {
                    diag = anorm * MACH_PREC;
                } else {
                    diag = this.gbar;
                }
                double zbar = this.gammaZeta / diag;
                d = this.bstep;
                double d2 = this.snprod;
                step = (r0 + (r0 * zbar)) / this.beta1;
                if (this.goodb) {
                    for (i = 0; i < n; i++) {
                        x.setEntry(i, ((zbar * this.wbar.getEntry(i)) + this.xL.getEntry(i)) + (step * this.mb.getEntry(i)));
                    }
                    return;
                }
                for (i = 0; i < n; i++) {
                    x.setEntry(i, (zbar * this.wbar.getEntry(i)) + this.xL.getEntry(i));
                }
            } else if (this.goodb) {
                step = this.bstep / this.beta1;
                for (i = 0; i < n; i++) {
                    double bi = this.mb.getEntry(i);
                    d = step * bi;
                    x.setEntry(i, d + this.xL.getEntry(i));
                }
            } else {
                x.setSubVector(0, this.xL);
            }
        }

        void init() {
            RealVector copy;
            this.xL.set(0.0d);
            this.r1 = this.b.copy();
            if (this.m == null) {
                copy = this.b.copy();
            } else {
                copy = this.m.operate(this.r1);
            }
            this.y = copy;
            if (this.m != null && this.check) {
                checkSymmetry(this.m, this.r1, this.y, this.m.operate(this.y));
            }
            this.beta1 = this.r1.dotProduct(this.y);
            if (this.beta1 < 0.0d) {
                throwNPDLOException(this.m, this.y);
            }
            if (this.beta1 == 0.0d) {
                this.bIsNull = true;
                return;
            }
            this.bIsNull = false;
            this.beta1 = FastMath.sqrt(this.beta1);
            RealVector v = this.y.mapMultiply(1.0d / this.beta1);
            this.y = this.a.operate(v);
            if (this.check) {
                checkSymmetry(this.a, v, this.y, this.a.operate(this.y));
            }
            daxpy(-this.shift, v, this.y);
            double alpha = v.dotProduct(this.y);
            daxpy((-alpha) / this.beta1, this.r1, this.y);
            daxpy((-v.dotProduct(this.y)) / v.dotProduct(v), v, this.y);
            this.r2 = this.y.copy();
            if (this.m != null) {
                this.y = this.m.operate(this.r2);
            }
            this.oldb = this.beta1;
            this.beta = this.r2.dotProduct(this.y);
            if (this.beta < 0.0d) {
                throwNPDLOException(this.m, this.y);
            }
            this.beta = FastMath.sqrt(this.beta);
            this.cgnorm = this.beta1;
            this.gbar = alpha;
            this.dbar = this.beta;
            this.gammaZeta = this.beta1;
            this.minusEpsZeta = 0.0d;
            this.bstep = 0.0d;
            this.snprod = 1.0d;
            this.tnorm = (alpha * alpha) + (this.beta * this.beta);
            this.ynorm2 = 0.0d;
            this.gmax = FastMath.abs(alpha) + MACH_PREC;
            this.gmin = this.gmax;
            if (this.goodb) {
                this.wbar = new ArrayRealVector(this.a.getRowDimension());
                this.wbar.set(0.0d);
            } else {
                this.wbar = v;
            }
            updateNorms();
        }

        void update() {
            RealVector v = this.y.mapMultiply(1.0d / this.beta);
            this.y = this.a.operate(v);
            daxpbypz(-this.shift, v, (-this.beta) / this.oldb, this.r1, this.y);
            double alpha = v.dotProduct(this.y);
            daxpy((-alpha) / this.beta, this.r2, this.y);
            this.r1 = this.r2;
            this.r2 = this.y;
            if (this.m != null) {
                this.y = this.m.operate(this.r2);
            }
            this.oldb = this.beta;
            this.beta = this.r2.dotProduct(this.y);
            if (this.beta < 0.0d) {
                throwNPDLOException(this.m, this.y);
            }
            this.beta = FastMath.sqrt(this.beta);
            this.tnorm += ((alpha * alpha) + (this.oldb * this.oldb)) + (this.beta * this.beta);
            double gamma = FastMath.sqrt((this.gbar * this.gbar) + (this.oldb * this.oldb));
            double c = this.gbar / gamma;
            double s = this.oldb / gamma;
            double deltak = (this.dbar * c) + (s * alpha);
            this.gbar = (this.dbar * s) - (c * alpha);
            double eps = s * this.beta;
            this.dbar = (-c) * this.beta;
            double zeta = this.gammaZeta / gamma;
            double zetaC = zeta * c;
            double zetaS = zeta * s;
            int n = this.xL.getDimension();
            for (int i = 0; i < n; i++) {
                double xi = this.xL.getEntry(i);
                double vi = v.getEntry(i);
                double wi = this.wbar.getEntry(i);
                this.xL.setEntry(i, ((wi * zetaC) + xi) + (vi * zetaS));
                this.wbar.setEntry(i, (wi * s) - (vi * c));
            }
            this.bstep += (this.snprod * c) * zeta;
            this.snprod *= s;
            this.gmax = FastMath.max(this.gmax, gamma);
            this.gmin = FastMath.min(this.gmin, gamma);
            this.ynorm2 += zeta * zeta;
            this.gammaZeta = this.minusEpsZeta - (deltak * zeta);
            this.minusEpsZeta = (-eps) * zeta;
            updateNorms();
        }

        private void updateNorms() {
            double diag;
            double acond;
            double anorm = FastMath.sqrt(this.tnorm);
            double ynorm = FastMath.sqrt(this.ynorm2);
            double epsa = anorm * MACH_PREC;
            double epsx = (anorm * ynorm) * MACH_PREC;
            double epsr = (anorm * ynorm) * this.delta;
            if (this.gbar == 0.0d) {
                diag = epsa;
            } else {
                diag = this.gbar;
            }
            this.lqnorm = FastMath.sqrt((this.gammaZeta * this.gammaZeta) + (this.minusEpsZeta * this.minusEpsZeta));
            double qrnorm = this.snprod * this.beta1;
            this.cgnorm = (this.beta * qrnorm) / FastMath.abs(diag);
            if (this.lqnorm <= this.cgnorm) {
                acond = this.gmax / this.gmin;
            } else {
                acond = this.gmax / FastMath.min(this.gmin, FastMath.abs(diag));
            }
            if (MACH_PREC * acond >= 0.1d) {
                throw new IllConditionedOperatorException(acond);
            }
            if (this.beta1 <= epsx) {
                throw new SingularOperatorException();
            }
            boolean z;
            this.rnorm = FastMath.min(this.cgnorm, this.lqnorm);
            if (this.cgnorm > epsx) {
                if (this.cgnorm > epsr) {
                    z = false;
                    this.hasConverged = z;
                }
            }
            z = true;
            this.hasConverged = z;
        }

        boolean hasConverged() {
            return this.hasConverged;
        }

        boolean bEqualsNullVector() {
            return this.bIsNull;
        }

        boolean betaEqualsZero() {
            return this.beta < MACH_PREC;
        }

        double getNormOfResidual() {
            return this.rnorm;
        }
    }

    public SymmLQ(int maxIterations, double delta, boolean check) {
        super(maxIterations);
        this.delta = delta;
        this.check = check;
    }

    public SymmLQ(IterationManager manager, double delta, boolean check) {
        super(manager);
        this.delta = delta;
        this.check = check;
    }

    public final boolean getCheck() {
        return this.check;
    }

    public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException {
        MathUtils.checkNotNull(a);
        return solveInPlace(a, m, b, new ArrayRealVector(a.getColumnDimension()), false, 0.0d);
    }

    public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, MaxCountExceededException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException {
        MathUtils.checkNotNull(a);
        return solveInPlace(a, m, b, new ArrayRealVector(a.getColumnDimension()), goodb, shift);
    }

    public RealVector solve(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        MathUtils.checkNotNull(x);
        return solveInPlace(a, m, b, x.copy(), false, 0.0d);
    }

    public RealVector solve(RealLinearOperator a, RealVector b) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        MathUtils.checkNotNull(a);
        RealVector x = new ArrayRealVector(a.getColumnDimension());
        x.set(0.0d);
        return solveInPlace(a, null, b, x, false, 0.0d);
    }

    public RealVector solve(RealLinearOperator a, RealVector b, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        MathUtils.checkNotNull(a);
        return solveInPlace(a, null, b, new ArrayRealVector(a.getColumnDimension()), goodb, shift);
    }

    public RealVector solve(RealLinearOperator a, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        MathUtils.checkNotNull(x);
        return solveInPlace(a, null, b, x.copy(), false, 0.0d);
    }

    public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        return solveInPlace(a, m, b, x, false, 0.0d);
    }

    public RealVector solveInPlace(RealLinearOperator a, RealLinearOperator m, RealVector b, RealVector x, boolean goodb, double shift) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, NonPositiveDefiniteOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        PreconditionedIterativeLinearSolver.checkParameters(a, m, b, x);
        IterationManager manager = getIterationManager();
        manager.resetIterationCount();
        manager.incrementIterationCount();
        State state = new State(a, m, b, goodb, shift, this.delta, this.check);
        state.init();
        state.refineSolution(x);
        IterativeLinearSolverEvent event = new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual());
        if (state.bEqualsNullVector()) {
            manager.fireTerminationEvent(event);
        } else {
            boolean earlyStop = state.betaEqualsZero() || state.hasConverged();
            manager.fireInitializationEvent(event);
            if (!earlyStop) {
                do {
                    manager.incrementIterationCount();
                    manager.fireIterationStartedEvent(new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual()));
                    state.update();
                    state.refineSolution(x);
                    manager.fireIterationPerformedEvent(new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual()));
                } while (!state.hasConverged());
            }
            manager.fireTerminationEvent(new DefaultIterativeLinearSolverEvent(this, manager.getIterations(), x, b, state.getNormOfResidual()));
        }
        return x;
    }

    public RealVector solveInPlace(RealLinearOperator a, RealVector b, RealVector x) throws NullArgumentException, NonSquareOperatorException, DimensionMismatchException, NonSelfAdjointOperatorException, IllConditionedOperatorException, MaxCountExceededException {
        return solveInPlace(a, null, b, x, false, 0.0d);
    }
}
