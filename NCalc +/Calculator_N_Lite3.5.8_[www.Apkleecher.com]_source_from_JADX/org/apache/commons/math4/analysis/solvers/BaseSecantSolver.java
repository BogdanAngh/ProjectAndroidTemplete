package org.apache.commons.math4.analysis.solvers;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.exception.ConvergenceException;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.FastMath;
import org.matheclipse.core.interfaces.IExpr;

public abstract class BaseSecantSolver extends AbstractUnivariateSolver implements BracketedUnivariateSolver<UnivariateFunction> {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution = null;
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$BaseSecantSolver$Method = null;
    protected static final double DEFAULT_ABSOLUTE_ACCURACY = 1.0E-6d;
    private AllowedSolution allowed;
    private final Method method;

    protected enum Method {
        REGULA_FALSI,
        ILLINOIS,
        PEGASUS
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution;
        if (iArr == null) {
            iArr = new int[AllowedSolution.values().length];
            try {
                iArr[AllowedSolution.ABOVE_SIDE.ordinal()] = 5;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[AllowedSolution.ANY_SIDE.ordinal()] = 1;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[AllowedSolution.BELOW_SIDE.ordinal()] = 4;
            } catch (NoSuchFieldError e3) {
            }
            try {
                iArr[AllowedSolution.LEFT_SIDE.ordinal()] = 2;
            } catch (NoSuchFieldError e4) {
            }
            try {
                iArr[AllowedSolution.RIGHT_SIDE.ordinal()] = 3;
            } catch (NoSuchFieldError e5) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution = iArr;
        }
        return iArr;
    }

    static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$BaseSecantSolver$Method() {
        int[] iArr = $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$BaseSecantSolver$Method;
        if (iArr == null) {
            iArr = new int[Method.values().length];
            try {
                iArr[Method.ILLINOIS.ordinal()] = 2;
            } catch (NoSuchFieldError e) {
            }
            try {
                iArr[Method.PEGASUS.ordinal()] = 3;
            } catch (NoSuchFieldError e2) {
            }
            try {
                iArr[Method.REGULA_FALSI.ordinal()] = 1;
            } catch (NoSuchFieldError e3) {
            }
            $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$BaseSecantSolver$Method = iArr;
        }
        return iArr;
    }

    protected BaseSecantSolver(double absoluteAccuracy, Method method) {
        super(absoluteAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    protected BaseSecantSolver(double relativeAccuracy, double absoluteAccuracy, Method method) {
        super(relativeAccuracy, absoluteAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    protected BaseSecantSolver(double relativeAccuracy, double absoluteAccuracy, double functionValueAccuracy, Method method) {
        super(relativeAccuracy, absoluteAccuracy, functionValueAccuracy);
        this.allowed = AllowedSolution.ANY_SIDE;
        this.method = method;
    }

    public double solve(int maxEval, UnivariateFunction f, double min, double max, AllowedSolution allowedSolution) {
        return solve(maxEval, f, min, max, min + (0.5d * (max - min)), allowedSolution);
    }

    public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue, AllowedSolution allowedSolution) {
        this.allowed = allowedSolution;
        return super.solve(maxEval, f, min, max, startValue);
    }

    public double solve(int maxEval, UnivariateFunction f, double min, double max, double startValue) {
        return solve(maxEval, f, min, max, startValue, AllowedSolution.ANY_SIDE);
    }

    protected final double doSolve() throws ConvergenceException {
        double x0 = getMin();
        double x1 = getMax();
        double f0 = computeObjectiveValue(x0);
        double f1 = computeObjectiveValue(x1);
        if (f0 == 0.0d) {
            return x0;
        }
        if (f1 == 0.0d) {
            return x1;
        }
        verifyBracketing(x0, x1);
        double ftol = getFunctionValueAccuracy();
        double atol = getAbsoluteAccuracy();
        double rtol = getRelativeAccuracy();
        boolean inverted = false;
        do {
            double x = x1 - (((x1 - x0) * f1) / (f1 - f0));
            double fx = computeObjectiveValue(x);
            if (fx == 0.0d) {
                return x;
            }
            if (f1 * fx < 0.0d) {
                x0 = x1;
                f0 = f1;
                if (inverted) {
                    inverted = false;
                } else {
                    inverted = true;
                }
            } else {
                switch ($SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$BaseSecantSolver$Method()[this.method.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        if (x == x1) {
                            throw new ConvergenceException();
                        }
                        break;
                    case IExpr.DOUBLEID /*2*/:
                        f0 *= 0.5d;
                        break;
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        f0 *= f1 / (f1 + fx);
                        break;
                    default:
                        throw new MathInternalError();
                }
            }
            x1 = x;
            f1 = fx;
            if (FastMath.abs(f1) <= ftol) {
                switch ($SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution()[this.allowed.ordinal()]) {
                    case ValueServer.REPLAY_MODE /*1*/:
                        return x1;
                    case IExpr.DOUBLEID /*2*/:
                        if (inverted) {
                            return x1;
                        }
                        break;
                    case ValueServer.EXPONENTIAL_MODE /*3*/:
                        if (!inverted) {
                            return x1;
                        }
                        break;
                    case IExpr.DOUBLECOMPLEXID /*4*/:
                        if (f1 <= 0.0d) {
                            return x1;
                        }
                        break;
                    case ValueServer.CONSTANT_MODE /*5*/:
                        if (f1 >= 0.0d) {
                            return x1;
                        }
                        break;
                    default:
                        throw new MathInternalError();
                }
            }
        } while (FastMath.abs(x1 - x0) >= FastMath.max(FastMath.abs(x1) * rtol, atol));
        switch ($SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution()[this.allowed.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                return x1;
            case IExpr.DOUBLEID /*2*/:
                return inverted ? x1 : x0;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return inverted ? x0 : x1;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                return f1 <= 0.0d ? x1 : x0;
            case ValueServer.CONSTANT_MODE /*5*/:
                return f1 >= 0.0d ? x1 : x0;
            default:
                throw new MathInternalError();
        }
    }
}
