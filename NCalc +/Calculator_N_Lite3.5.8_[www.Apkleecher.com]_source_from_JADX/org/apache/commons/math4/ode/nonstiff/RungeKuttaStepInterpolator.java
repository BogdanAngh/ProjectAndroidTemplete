package org.apache.commons.math4.ode.nonstiff;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import org.apache.commons.math4.ode.AbstractIntegrator;
import org.apache.commons.math4.ode.EquationsMapper;
import org.apache.commons.math4.ode.sampling.AbstractStepInterpolator;

abstract class RungeKuttaStepInterpolator extends AbstractStepInterpolator {
    protected AbstractIntegrator integrator;
    protected double[] previousState;
    protected double[][] yDotK;

    protected RungeKuttaStepInterpolator() {
        this.previousState = null;
        this.yDotK = null;
        this.integrator = null;
    }

    public RungeKuttaStepInterpolator(RungeKuttaStepInterpolator interpolator) {
        super(interpolator);
        if (interpolator.currentState != null) {
            this.previousState = (double[]) interpolator.previousState.clone();
            this.yDotK = new double[interpolator.yDotK.length][];
            for (int k = 0; k < interpolator.yDotK.length; k++) {
                this.yDotK[k] = (double[]) interpolator.yDotK[k].clone();
            }
        } else {
            this.previousState = null;
            this.yDotK = null;
        }
        this.integrator = null;
    }

    public void reinitialize(AbstractIntegrator rkIntegrator, double[] y, double[][] yDotArray, boolean forward, EquationsMapper primaryMapper, EquationsMapper[] secondaryMappers) {
        reinitialize(y, forward, primaryMapper, secondaryMappers);
        this.previousState = null;
        this.yDotK = yDotArray;
        this.integrator = rkIntegrator;
    }

    public void shift() {
        this.previousState = (double[]) this.currentState.clone();
        super.shift();
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        int i;
        writeBaseExternal(out);
        int n = this.currentState == null ? -1 : this.currentState.length;
        for (i = 0; i < n; i++) {
            out.writeDouble(this.previousState[i]);
        }
        int kMax = this.yDotK == null ? -1 : this.yDotK.length;
        out.writeInt(kMax);
        for (int k = 0; k < kMax; k++) {
            for (i = 0; i < n; i++) {
                out.writeDouble(this.yDotK[k][i]);
            }
        }
    }

    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        int i;
        double t = readBaseExternal(in);
        int n = this.currentState == null ? -1 : this.currentState.length;
        if (n < 0) {
            this.previousState = null;
        } else {
            this.previousState = new double[n];
            for (i = 0; i < n; i++) {
                this.previousState[i] = in.readDouble();
            }
        }
        int kMax = in.readInt();
        this.yDotK = kMax < 0 ? null : new double[kMax][];
        for (int k = 0; k < kMax; k++) {
            this.yDotK[k] = n < 0 ? null : new double[n];
            for (i = 0; i < n; i++) {
                this.yDotK[k][i] = in.readDouble();
            }
        }
        this.integrator = null;
        if (this.currentState != null) {
            setInterpolatedTime(t);
        } else {
            this.interpolatedTime = t;
        }
    }
}
