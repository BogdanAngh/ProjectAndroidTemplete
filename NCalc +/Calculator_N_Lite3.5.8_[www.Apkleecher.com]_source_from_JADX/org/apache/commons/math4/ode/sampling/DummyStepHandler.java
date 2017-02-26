package org.apache.commons.math4.ode.sampling;

public class DummyStepHandler implements StepHandler {

    private static class LazyHolder {
        private static final DummyStepHandler INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new DummyStepHandler();
        }
    }

    private DummyStepHandler() {
    }

    public static DummyStepHandler getInstance() {
        return LazyHolder.INSTANCE;
    }

    public void init(double t0, double[] y0, double t) {
    }

    public void handleStep(StepInterpolator interpolator, boolean isLast) {
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
