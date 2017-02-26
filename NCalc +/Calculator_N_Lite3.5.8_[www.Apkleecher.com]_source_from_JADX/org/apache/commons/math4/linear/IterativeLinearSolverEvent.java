package org.apache.commons.math4.linear;

import org.apache.commons.math4.exception.MathUnsupportedOperationException;
import org.apache.commons.math4.util.IterationEvent;

public abstract class IterativeLinearSolverEvent extends IterationEvent {
    private static final long serialVersionUID = 20120129;

    public abstract double getNormOfResidual();

    public abstract RealVector getRightHandSideVector();

    public abstract RealVector getSolution();

    public IterativeLinearSolverEvent(Object source, int iterations) {
        super(source, iterations);
    }

    public RealVector getResidual() {
        throw new MathUnsupportedOperationException();
    }

    public boolean providesResidual() {
        return false;
    }
}
