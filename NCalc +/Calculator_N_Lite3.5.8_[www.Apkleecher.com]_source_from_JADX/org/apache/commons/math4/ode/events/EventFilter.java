package org.apache.commons.math4.ode.events;

import java.util.Arrays;
import org.apache.commons.math4.ode.events.EventHandler.Action;

public class EventFilter implements EventHandler {
    private static final int HISTORY_SIZE = 100;
    private double extremeT;
    private final FilterType filter;
    private boolean forward;
    private final EventHandler rawHandler;
    private final Transformer[] transformers;
    private final double[] updates;

    public EventFilter(EventHandler rawHandler, FilterType filter) {
        this.rawHandler = rawHandler;
        this.filter = filter;
        this.transformers = new Transformer[HISTORY_SIZE];
        this.updates = new double[HISTORY_SIZE];
    }

    public void init(double t0, double[] y0, double t) {
        this.rawHandler.init(t0, y0, t);
        this.forward = t >= t0;
        this.extremeT = this.forward ? Double.NEGATIVE_INFINITY : Double.POSITIVE_INFINITY;
        Arrays.fill(this.transformers, Transformer.UNINITIALIZED);
        Arrays.fill(this.updates, this.extremeT);
    }

    public double g(double t, double[] y) {
        double rawG = this.rawHandler.g(t, y);
        Transformer previous;
        Transformer next;
        int i;
        if (this.forward) {
            int last = this.transformers.length - 1;
            if (this.extremeT < t) {
                previous = this.transformers[last];
                next = this.filter.selectTransformer(previous, rawG, this.forward);
                if (next != previous) {
                    System.arraycopy(this.updates, 1, this.updates, 0, last);
                    System.arraycopy(this.transformers, 1, this.transformers, 0, last);
                    this.updates[last] = this.extremeT;
                    this.transformers[last] = next;
                }
                this.extremeT = t;
                return next.transformed(rawG);
            }
            for (i = last; i > 0; i--) {
                if (this.updates[i] <= t) {
                    return this.transformers[i].transformed(rawG);
                }
            }
            return this.transformers[0].transformed(rawG);
        } else if (t < this.extremeT) {
            previous = this.transformers[0];
            next = this.filter.selectTransformer(previous, rawG, this.forward);
            if (next != previous) {
                System.arraycopy(this.updates, 0, this.updates, 1, this.updates.length - 1);
                System.arraycopy(this.transformers, 0, this.transformers, 1, this.transformers.length - 1);
                this.updates[0] = this.extremeT;
                this.transformers[0] = next;
            }
            this.extremeT = t;
            return next.transformed(rawG);
        } else {
            for (i = 0; i < this.updates.length - 1; i++) {
                if (t <= this.updates[i]) {
                    return this.transformers[i].transformed(rawG);
                }
            }
            return this.transformers[this.updates.length - 1].transformed(rawG);
        }
    }

    public Action eventOccurred(double t, double[] y, boolean increasing) {
        return this.rawHandler.eventOccurred(t, y, this.filter.getTriggeredIncreasing());
    }

    public void resetState(double t, double[] y) {
        this.rawHandler.resetState(t, y);
    }
}
