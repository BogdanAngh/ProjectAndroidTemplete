package org.apache.commons.math4.ode;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;
import org.apache.commons.math4.analysis.integration.BaseAbstractUnivariateIntegrator;
import org.apache.commons.math4.analysis.solvers.BracketingNthOrderBrentSolver;
import org.apache.commons.math4.analysis.solvers.UnivariateSolver;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MaxCountExceededException;
import org.apache.commons.math4.exception.NoBracketingException;
import org.apache.commons.math4.exception.NumberIsTooSmallException;
import org.apache.commons.math4.exception.util.LocalizedFormats;
import org.apache.commons.math4.ode.events.EventHandler;
import org.apache.commons.math4.ode.events.EventState;
import org.apache.commons.math4.ode.sampling.AbstractStepInterpolator;
import org.apache.commons.math4.ode.sampling.StepHandler;
import org.apache.commons.math4.util.FastMath;
import org.apache.commons.math4.util.Incrementor;
import org.apache.commons.math4.util.Precision;

public abstract class AbstractIntegrator implements FirstOrderIntegrator {
    private Incrementor evaluations;
    private Collection<EventState> eventsStates;
    private transient ExpandableStatefulODE expandable;
    protected boolean isLastStep;
    private final String name;
    protected boolean resetOccurred;
    private boolean statesInitialized;
    protected Collection<StepHandler> stepHandlers;
    protected double stepSize;
    protected double stepStart;

    class 1 implements Comparator<EventState> {
        private final /* synthetic */ int val$orderingSign;

        1(int i) {
            this.val$orderingSign = i;
        }

        public int compare(EventState es0, EventState es1) {
            return this.val$orderingSign * Double.compare(es0.getEventTime(), es1.getEventTime());
        }
    }

    public abstract void integrate(ExpandableStatefulODE expandableStatefulODE, double d) throws NumberIsTooSmallException, DimensionMismatchException, MaxCountExceededException, NoBracketingException;

    public AbstractIntegrator(String name) {
        this.name = name;
        this.stepHandlers = new ArrayList();
        this.stepStart = Double.NaN;
        this.stepSize = Double.NaN;
        this.eventsStates = new ArrayList();
        this.statesInitialized = false;
        this.evaluations = new Incrementor();
        setMaxEvaluations(-1);
        this.evaluations.resetCount();
    }

    protected AbstractIntegrator() {
        this(null);
    }

    public String getName() {
        return this.name;
    }

    public void addStepHandler(StepHandler handler) {
        this.stepHandlers.add(handler);
    }

    public Collection<StepHandler> getStepHandlers() {
        return Collections.unmodifiableCollection(this.stepHandlers);
    }

    public void clearStepHandlers() {
        this.stepHandlers.clear();
    }

    public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount) {
        addEventHandler(handler, maxCheckInterval, convergence, maxIterationCount, new BracketingNthOrderBrentSolver(convergence, 5));
    }

    public void addEventHandler(EventHandler handler, double maxCheckInterval, double convergence, int maxIterationCount, UnivariateSolver solver) {
        this.eventsStates.add(new EventState(handler, maxCheckInterval, convergence, maxIterationCount, solver));
    }

    public Collection<EventHandler> getEventHandlers() {
        List<EventHandler> list = new ArrayList(this.eventsStates.size());
        for (EventState state : this.eventsStates) {
            list.add(state.getEventHandler());
        }
        return Collections.unmodifiableCollection(list);
    }

    public void clearEventHandlers() {
        this.eventsStates.clear();
    }

    public double getCurrentStepStart() {
        return this.stepStart;
    }

    public double getCurrentSignedStepsize() {
        return this.stepSize;
    }

    public void setMaxEvaluations(int maxEvaluations) {
        Incrementor incrementor = this.evaluations;
        if (maxEvaluations < 0) {
            maxEvaluations = BaseAbstractUnivariateIntegrator.DEFAULT_MAX_ITERATIONS_COUNT;
        }
        incrementor.setMaximalCount(maxEvaluations);
    }

    public int getMaxEvaluations() {
        return this.evaluations.getMaximalCount();
    }

    public int getEvaluations() {
        return this.evaluations.getCount();
    }

    protected void initIntegration(double t0, double[] y0, double t) {
        this.evaluations.resetCount();
        for (EventState state : this.eventsStates) {
            state.setExpandable(this.expandable);
            state.getEventHandler().init(t0, y0, t);
        }
        for (StepHandler handler : this.stepHandlers) {
            handler.init(t0, y0, t);
        }
        setStateInitialized(false);
    }

    protected void setEquations(ExpandableStatefulODE equations) {
        this.expandable = equations;
    }

    protected ExpandableStatefulODE getExpandable() {
        return this.expandable;
    }

    protected Incrementor getEvaluationsCounter() {
        return this.evaluations;
    }

    public double integrate(FirstOrderDifferentialEquations equations, double t0, double[] y0, double t, double[] y) throws DimensionMismatchException, NumberIsTooSmallException, MaxCountExceededException, NoBracketingException {
        if (y0.length != equations.getDimension()) {
            throw new DimensionMismatchException(y0.length, equations.getDimension());
        } else if (y.length != equations.getDimension()) {
            throw new DimensionMismatchException(y.length, equations.getDimension());
        } else {
            ExpandableStatefulODE expandableODE = new ExpandableStatefulODE(equations);
            expandableODE.setTime(t0);
            expandableODE.setPrimaryState(y0);
            integrate(expandableODE, t);
            System.arraycopy(expandableODE.getPrimaryState(), 0, y, 0, y.length);
            return expandableODE.getTime();
        }
    }

    public void computeDerivatives(double t, double[] y, double[] yDot) throws MaxCountExceededException, DimensionMismatchException {
        this.evaluations.incrementCount();
        this.expandable.computeDerivatives(t, y, yDot);
    }

    protected void setStateInitialized(boolean stateInitialized) {
        this.statesInitialized = stateInitialized;
    }

    protected double acceptStep(AbstractStepInterpolator interpolator, double[] y, double[] yDot, double tEnd) throws MaxCountExceededException, DimensionMismatchException, NoBracketingException {
        EquationsMapper[] secondaryMappers;
        int length;
        int i;
        int i2;
        boolean z;
        double previousT = interpolator.getGlobalPreviousTime();
        double currentT = interpolator.getGlobalCurrentTime();
        if (!this.statesInitialized) {
            for (EventState reinitializeBegin : this.eventsStates) {
                reinitializeBegin.reinitializeBegin(interpolator);
            }
            this.statesInitialized = true;
        }
        SortedSet<EventState> treeSet = new TreeSet(new 1(interpolator.isForward() ? 1 : -1));
        for (EventState state : this.eventsStates) {
            if (state.evaluateStep(interpolator)) {
                treeSet.add(state);
            }
        }
        while (!treeSet.isEmpty()) {
            Iterator<EventState> iterator = treeSet.iterator();
            EventState currentEvent = (EventState) iterator.next();
            iterator.remove();
            double eventT = currentEvent.getEventTime();
            interpolator.setSoftPreviousTime(previousT);
            interpolator.setSoftCurrentTime(eventT);
            interpolator.setInterpolatedTime(eventT);
            double[] eventYComplete = new double[y.length];
            this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), eventYComplete);
            secondaryMappers = this.expandable.getSecondaryMappers();
            length = secondaryMappers.length;
            i = 0;
            i2 = 0;
            while (i < length) {
                int index = i2 + 1;
                secondaryMappers[i].insertEquationData(interpolator.getInterpolatedSecondaryState(i2), eventYComplete);
                i++;
                i2 = index;
            }
            for (EventState state2 : this.eventsStates) {
                state2.stepAccepted(eventT, eventYComplete);
                z = this.isLastStep || state2.stop();
                this.isLastStep = z;
            }
            for (StepHandler handler : this.stepHandlers) {
                handler.handleStep(interpolator, this.isLastStep);
            }
            if (this.isLastStep) {
                System.arraycopy(eventYComplete, 0, y, 0, y.length);
                return eventT;
            }
            boolean needReset = false;
            for (EventState state22 : this.eventsStates) {
                needReset = needReset || state22.reset(eventT, eventYComplete);
            }
            if (needReset) {
                interpolator.setInterpolatedTime(eventT);
                System.arraycopy(eventYComplete, 0, y, 0, y.length);
                computeDerivatives(eventT, y, yDot);
                this.resetOccurred = true;
                return eventT;
            }
            previousT = eventT;
            interpolator.setSoftPreviousTime(eventT);
            interpolator.setSoftCurrentTime(currentT);
            if (currentEvent.evaluateStep(interpolator)) {
                treeSet.add(currentEvent);
            }
        }
        interpolator.setInterpolatedTime(currentT);
        double[] currentY = new double[y.length];
        this.expandable.getPrimaryMapper().insertEquationData(interpolator.getInterpolatedState(), currentY);
        secondaryMappers = this.expandable.getSecondaryMappers();
        length = secondaryMappers.length;
        i = 0;
        i2 = 0;
        while (i < length) {
            index = i2 + 1;
            secondaryMappers[i].insertEquationData(interpolator.getInterpolatedSecondaryState(i2), currentY);
            i++;
            i2 = index;
        }
        for (EventState state222 : this.eventsStates) {
            state222.stepAccepted(currentT, currentY);
            z = this.isLastStep || state222.stop();
            this.isLastStep = z;
        }
        z = this.isLastStep || Precision.equals(currentT, tEnd, 1);
        this.isLastStep = z;
        for (StepHandler handler2 : this.stepHandlers) {
            handler2.handleStep(interpolator, this.isLastStep);
        }
        return currentT;
    }

    protected void sanityChecks(ExpandableStatefulODE equations, double t) throws NumberIsTooSmallException, DimensionMismatchException {
        double threshold = 1000.0d * FastMath.ulp(FastMath.max(FastMath.abs(equations.getTime()), FastMath.abs(t)));
        double dt = FastMath.abs(equations.getTime() - t);
        if (dt <= threshold) {
            throw new NumberIsTooSmallException(LocalizedFormats.TOO_SMALL_INTEGRATION_INTERVAL, Double.valueOf(dt), Double.valueOf(threshold), false);
        }
    }
}
