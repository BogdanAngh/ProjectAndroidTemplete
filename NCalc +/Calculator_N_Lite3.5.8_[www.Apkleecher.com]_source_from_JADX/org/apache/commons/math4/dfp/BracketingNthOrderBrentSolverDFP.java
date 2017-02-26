package org.apache.commons.math4.dfp;

import org.apache.commons.math4.analysis.solvers.AllowedSolution;
import org.apache.commons.math4.exception.MathInternalError;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NullArgumentException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.random.ValueServer;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.MathUtils;
import org.matheclipse.core.interfaces.IExpr;

public class BracketingNthOrderBrentSolverDFP {
    private static /* synthetic */ int[] $SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution = null;
    private static final int MAXIMAL_AGING = 2;
    private final Dfp absoluteAccuracy;
    private final Incrementor evaluations;
    private final Dfp functionValueAccuracy;
    private final int maximalOrder;
    private final Dfp relativeAccuracy;

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
                iArr[AllowedSolution.LEFT_SIDE.ordinal()] = MAXIMAL_AGING;
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

    public BracketingNthOrderBrentSolverDFP(Dfp relativeAccuracy, Dfp absoluteAccuracy, Dfp functionValueAccuracy, int maximalOrder) throws NumberIsTooSmallException {
        this.evaluations = new Incrementor();
        if (maximalOrder < MAXIMAL_AGING) {
            throw new NumberIsTooSmallException(Integer.valueOf(maximalOrder), Integer.valueOf(MAXIMAL_AGING), true);
        }
        this.maximalOrder = maximalOrder;
        this.absoluteAccuracy = absoluteAccuracy;
        this.relativeAccuracy = relativeAccuracy;
        this.functionValueAccuracy = functionValueAccuracy;
    }

    public int getMaximalOrder() {
        return this.maximalOrder;
    }

    public int getMaxEvaluations() {
        return this.evaluations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    public Dfp getAbsoluteAccuracy() {
        return this.absoluteAccuracy;
    }

    public Dfp getRelativeAccuracy() {
        return this.relativeAccuracy;
    }

    public Dfp getFunctionValueAccuracy() {
        return this.functionValueAccuracy;
    }

    public Dfp solve(int maxEval, UnivariateDfpFunction f, Dfp min, Dfp max, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
        return solve(maxEval, f, min, max, min.add(max).divide((int) MAXIMAL_AGING), allowedSolution);
    }

    public Dfp solve(int maxEval, UnivariateDfpFunction f, Dfp min, Dfp max, Dfp startValue, AllowedSolution allowedSolution) throws NullArgumentException, NoBracketingException {
        MathUtils.checkNotNull(f);
        this.evaluations.setMaximalCount(maxEval);
        this.evaluations.resetCount();
        Dfp zero = startValue.getZero();
        Dfp nan = zero.newInstance((byte) 1, (byte) 3);
        Object x = new Dfp[(this.maximalOrder + 1)];
        Object y = new Dfp[(this.maximalOrder + 1)];
        x[0] = min;
        x[1] = startValue;
        x[MAXIMAL_AGING] = max;
        this.evaluations.incrementCount();
        y[1] = f.value(x[1]);
        if (y[1].isZero()) {
            return x[1];
        }
        this.evaluations.incrementCount();
        y[0] = f.value(x[0]);
        if (y[0].isZero()) {
            return x[0];
        }
        int nbPoints;
        int signChangeIndex;
        if (y[0].multiply(y[1]).negativeOrNull()) {
            nbPoints = MAXIMAL_AGING;
            signChangeIndex = 1;
        } else {
            this.evaluations.incrementCount();
            y[MAXIMAL_AGING] = f.value(x[MAXIMAL_AGING]);
            if (y[MAXIMAL_AGING].isZero()) {
                return x[MAXIMAL_AGING];
            }
            if (y[1].multiply(y[MAXIMAL_AGING]).negativeOrNull()) {
                nbPoints = 3;
                signChangeIndex = MAXIMAL_AGING;
            } else {
                throw new NoBracketingException(x[0].toDouble(), x[MAXIMAL_AGING].toDouble(), y[0].toDouble(), y[MAXIMAL_AGING].toDouble());
            }
        }
        Dfp[] tmpX = new Dfp[x.length];
        Dfp xA = x[signChangeIndex - 1];
        Dfp yA = y[signChangeIndex - 1];
        Dfp absXA = xA.abs();
        Dfp absYA = yA.abs();
        int agingA = 0;
        Dfp xB = x[signChangeIndex];
        Dfp yB = y[signChangeIndex];
        Dfp absXB = xB.abs();
        Dfp absYB = yB.abs();
        int agingB = 0;
        while (true) {
            Dfp maxX;
            Dfp maxY;
            if (absXA.lessThan(absXB)) {
                maxX = absXB;
            } else {
                maxX = absXA;
            }
            if (absYA.lessThan(absYB)) {
                maxY = absYB;
            } else {
                maxY = absYA;
            }
            if (xB.subtract(xA).subtract(this.absoluteAccuracy.add(this.relativeAccuracy.multiply(maxX))).negativeOrNull()) {
                break;
            }
            if (maxY.lessThan(this.functionValueAccuracy)) {
                break;
            }
            Dfp targetY;
            Dfp nextX;
            if (agingA >= MAXIMAL_AGING) {
                targetY = yB.divide(16).negate();
            } else if (agingB >= MAXIMAL_AGING) {
                targetY = yA.divide(16).negate();
            } else {
                targetY = zero;
            }
            int start = 0;
            int end = nbPoints;
            do {
                System.arraycopy(x, start, tmpX, start, end - start);
                nextX = guessX(targetY, tmpX, y, start, end);
                if (!(nextX.greaterThan(xA) && nextX.lessThan(xB))) {
                    if (signChangeIndex - start >= end - signChangeIndex) {
                        start++;
                    } else {
                        end--;
                    }
                    nextX = nan;
                }
                if (!nextX.isNaN()) {
                    break;
                }
            } while (end - start > 1);
            if (nextX.isNaN()) {
                nextX = xA.add(xB.subtract(xA).divide((int) MAXIMAL_AGING));
                start = signChangeIndex - 1;
                end = signChangeIndex;
            }
            this.evaluations.incrementCount();
            Dfp nextY = f.value(nextX);
            if (nextY.isZero()) {
                return nextX;
            }
            if (nbPoints > MAXIMAL_AGING && end - start != nbPoints) {
                nbPoints = end - start;
                System.arraycopy(x, start, x, 0, nbPoints);
                System.arraycopy(y, start, y, 0, nbPoints);
                signChangeIndex -= start;
            } else if (nbPoints == x.length) {
                nbPoints--;
                if (signChangeIndex >= (x.length + 1) / MAXIMAL_AGING) {
                    System.arraycopy(x, 1, x, 0, nbPoints);
                    System.arraycopy(y, 1, y, 0, nbPoints);
                    signChangeIndex--;
                }
            }
            System.arraycopy(x, signChangeIndex, x, signChangeIndex + 1, nbPoints - signChangeIndex);
            x[signChangeIndex] = nextX;
            System.arraycopy(y, signChangeIndex, y, signChangeIndex + 1, nbPoints - signChangeIndex);
            y[signChangeIndex] = nextY;
            nbPoints++;
            if (nextY.multiply(yA).negativeOrNull()) {
                xB = nextX;
                yB = nextY;
                absYB = yB.abs();
                agingA++;
                agingB = 0;
            } else {
                xA = nextX;
                yA = nextY;
                absYA = yA.abs();
                agingA = 0;
                agingB++;
                signChangeIndex++;
            }
        }
        switch ($SWITCH_TABLE$org$apache$commons$math4$analysis$solvers$AllowedSolution()[allowedSolution.ordinal()]) {
            case ValueServer.REPLAY_MODE /*1*/:
                if (absYA.lessThan(absYB)) {
                    return xA;
                }
                return xB;
            case MAXIMAL_AGING /*2*/:
                return xA;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return xB;
            case IExpr.DOUBLECOMPLEXID /*4*/:
                if (yA.lessThan(zero)) {
                    return xA;
                }
                return xB;
            case ValueServer.CONSTANT_MODE /*5*/:
                if (!yA.lessThan(zero)) {
                    xB = xA;
                }
                return xB;
            default:
                throw new MathInternalError(null);
        }
    }

    private Dfp guessX(Dfp targetY, Dfp[] x, Dfp[] y, int start, int end) {
        int j;
        for (int i = start; i < end - 1; i++) {
            int delta = (i + 1) - start;
            for (j = end - 1; j > i; j--) {
                x[j] = x[j].subtract(x[j - 1]).divide(y[j].subtract(y[j - delta]));
            }
        }
        Dfp x0 = targetY.getZero();
        for (j = end - 1; j >= start; j--) {
            x0 = x[j].add(x0.multiply(targetY.subtract(y[j])));
        }
        return x0;
    }
}
