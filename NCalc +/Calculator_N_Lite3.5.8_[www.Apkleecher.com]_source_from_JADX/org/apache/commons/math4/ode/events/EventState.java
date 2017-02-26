package org.apache.commons.math4.ode.events;

import org.apache.commons.math4.analysis.UnivariateFunction;
import org.apache.commons.math4.analysis.solvers.AllowedSolution;
import org.apache.commons.math4.analysis.solvers.BracketedUnivariateSolver;
import org.apache.commons.math4.analysis.solvers.PegasusSolver;
import org.apache.commons.math4.analysis.solvers.UnivariateSolver;
import org.apache.commons.math4.analysis.solvers.UnivariateSolverUtils;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.ExpandableStatefulODE;
import org.apache.commons.math4.ode.events.EventHandler.Action;
import org.apache.commons.math4.ode.sampling.StepInterpolator;
import org.apache.commons.math4.util.FastMath;

public class EventState {
    private final double convergence;
    private ExpandableStatefulODE expandable;
    private boolean forward;
    private double g0;
    private boolean g0Positive;
    private final EventHandler handler;
    private boolean increasing;
    private final double maxCheckInterval;
    private final int maxIterationCount;
    private Action nextAction;
    private boolean pendingEvent;
    private double pendingEventTime;
    private double previousEventTime;
    private final UnivariateSolver solver;
    private double t0;

    class 1 implements UnivariateFunction {
        private final /* synthetic */ StepInterpolator val$interpolator;

        1(StepInterpolator stepInterpolator) {
            this.val$interpolator = stepInterpolator;
        }

        public double value(double t) throws LocalMaxCountExceededException {
            try {
                this.val$interpolator.setInterpolatedTime(t);
                return EventState.this.handler.g(t, EventState.this.getCompleteState(this.val$interpolator));
            } catch (MaxCountExceededException mcee) {
                throw new LocalMaxCountExceededException(mcee);
            }
        }
    }

    private static class LocalMaxCountExceededException extends RuntimeException {
        private static final long serialVersionUID = 20120901;
        private final MaxCountExceededException wrapped;

        public LocalMaxCountExceededException(MaxCountExceededException exception) {
            this.wrapped = exception;
        }

        public MaxCountExceededException getException() {
            return this.wrapped;
        }
    }

    public EventState(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
        this.handler = handler;
        this.maxCheckInterval = maxCheckInterval;
        this.convergence = FastMath.abs(convergence);
        this.maxIterationCount = maxIterationCount;
        this.solver = solver;
        this.expandable = null;
        this.t0 = Double.NaN;
        this.g0 = Double.NaN;
        this.g0Positive = true;
        this.pendingEvent = false;
        this.pendingEventTime = Double.NaN;
        this.previousEventTime = Double.NaN;
        this.increasing = true;
        this.nextAction = Action.CONTINUE;
    }

    public EventHandler getEventHandler() {
        return this.handler;
    }

    public void setExpandable(ExpandableStatefulODE expandable) {
        this.expandable = expandable;
    }

    public double getMaxCheckInterval() {
        return this.maxCheckInterval;
    }

    public double getConvergence() {
        return this.convergence;
    }

    public int getMaxIterationCount() {
        return this.maxIterationCount;
    }

    public void reinitializeBegin(StepInterpolator interpolator) throws MaxCountExceededException {
        this.t0 = interpolator.getPreviousTime();
        interpolator.setInterpolatedTime(this.t0);
        this.g0 = this.handler.g(this.t0, getCompleteState(interpolator));
        if (this.g0 == 0.0d) {
            double tStart = this.t0 + (0.5d * FastMath.max(this.solver.getAbsoluteAccuracy(), FastMath.abs(this.solver.getRelativeAccuracy() * this.t0)));
            interpolator.setInterpolatedTime(tStart);
            this.g0 = this.handler.g(tStart, getCompleteState(interpolator));
        }
        this.g0Positive = this.g0 >= 0.0d;
    }

    private double[] getCompleteState(StepInterpolator interpolator) {
        double[] complete = new double[this.expandable.getTotalDimension()];
        this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), complete);
        EquationsMapper[] secondaryMappers = this.expandable.getSecondaryMappers();
        int length = secondaryMappers.length;
        int i = 0;
        int index = 0;
        while (i < length) {
            int index2 = index + 1;
            secondaryMappers[i].insertEquationData(interpolator.getInterpolatedSecondaryState(index), complete);
            i++;
            index = index2;
        }
        return complete;
    }

    public boolean evaluateStep(StepInterpolator interpolator) throws MaxCountExceededException, NoBracketingException {
        try {
            this.forward = interpolator.isForward();
            double dt = interpolator.getCurrentTime() - this.t0;
            if (FastMath.abs(dt) < this.convergence) {
                return false;
            }
            int n = FastMath.max(1, (int) FastMath.ceil(FastMath.abs(dt) / this.maxCheckInterval));
            double h = dt / ((double) n);
            UnivariateFunction f = new 1(interpolator);
            double ta = this.t0;
            double ga = this.g0;
            int i = 0;
            while (i < n) {
                double tb = this.t0 + (((double) (i + 1)) * h);
                interpolator.setInterpolatedTime(tb);
                double gb = this.handler.g(tb, getCompleteState(interpolator));
                if (((gb >= 0.0d ? 1 : 0) ^ this.g0Positive) != 0) {
                    double root;
                    this.increasing = gb >= ga;
                    BracketedUnivariateSolver<UnivariateFunction> bracketing;
                    if (this.solver instanceof BracketedUnivariateSolver) {
                        bracketing = this.solver;
                        if (this.forward) {
                            root = bracketing.solve(this.maxIterationCount, f, ta, tb, AllowedSolution.RIGHT_SIDE);
                        } else {
                            root = bracketing.solve(this.maxIterationCount, f, tb, ta, AllowedSolution.LEFT_SIDE);
                        }
                    } else {
                        double baseRoot;
                        if (this.forward) {
                            baseRoot = this.solver.solve(this.maxIterationCount, f, ta, tb);
                        } else {
                            baseRoot = this.solver.solve(this.maxIterationCount, f, tb, ta);
                        }
                        int remainingEval = this.maxIterationCount - this.solver.getEvaluations();
                        bracketing = new PegasusSolver(this.solver.getRelativeAccuracy(), this.solver.getAbsoluteAccuracy());
                        if (this.forward) {
                            root = UnivariateSolverUtils.forceSide(remainingEval, f, bracketing, baseRoot, ta, tb, AllowedSolution.RIGHT_SIDE);
                        } else {
                            root = UnivariateSolverUtils.forceSide(remainingEval, f, bracketing, baseRoot, tb, ta, AllowedSolution.LEFT_SIDE);
                        }
                    }
                    if (!Double.isNaN(this.previousEventTime)) {
                        if (FastMath.abs(root - ta) <= this.convergence) {
                            if (FastMath.abs(root - this.previousEventTime) <= this.convergence) {
                                do {
                                    ta = this.forward ? ta + this.convergence : ta - this.convergence;
                                    ga = f.value(ta);
                                    if (((ga >= 0.0d ? 1 : 0) ^ this.g0Positive) == 0) {
                                        break;
                                    }
                                } while (((ta >= tb ? 1 : 0) ^ this.forward) != 0);
                                i--;
                            }
                        }
                    }
                    if (!Double.isNaN(this.previousEventTime)) {
                        if (FastMath.abs(this.previousEventTime - root) <= this.convergence) {
                            ta = tb;
                            ga = gb;
                        }
                    }
                    this.pendingEventTime = root;
                    this.pendingEvent = true;
                    return true;
                }
                ta = tb;
                ga = gb;
                i++;
            }
            this.pendingEvent = false;
            this.pendingEventTime = Double.NaN;
            return false;
        } catch (LocalMaxCountExceededException lmcee) {
            throw lmcee.getException();
        }
    }

    public double getEventTime() {
        if (this.pendingEvent) {
            return this.pendingEventTime;
        }
        return this.forward ? Double.POSITIVE_INFINITY : Double.NEGATIVE_INFINITY;
    }

    public void stepAccepted(double t, double[] y) {
        boolean z = true;
        boolean z2 = false;
        this.t0 = t;
        this.g0 = this.handler.g(t, y);
        if (!this.pendingEvent || FastMath.abs(this.pendingEventTime - t) > this.convergence) {
            if (this.g0 < 0.0d) {
                z = false;
            }
            this.g0Positive = z;
            this.nextAction = Action.CONTINUE;
            return;
        }
        this.previousEventTime = t;
        this.g0Positive = this.increasing;
        EventHandler eventHandler = this.handler;
        if ((this.increasing ^ this.forward) == 0) {
            z2 = true;
        }
        this.nextAction = eventHandler.eventOccurred(t, y, z2);
    }

    public boolean stop() {
        return this.nextAction == Action.STOP;
    }

    public boolean reset(double t, double[] y) {
        if (!this.pendingEvent || FastMath.abs(this.pendingEventTime - t) > this.convergence) {
            return false;
        }
        if (this.nextAction == Action.RESET_STATE) {
            this.handler.resetState(t, y);
        }
        this.pendingEvent = false;
        this.pendingEventTime = Double.NaN;
        if (this.nextAction == Action.RESET_STATE || this.nextAction == Action.RESET_DERIVATIVES) {
            return true;
        }
        return false;
    }
}
