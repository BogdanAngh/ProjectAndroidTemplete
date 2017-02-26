package org.apache.commons.math4.genetics;

import java.util.concurrent.TimeUnit;
import org.apache.commons.math4.exception.NumberIsTooSmallException;

public class FixedElapsedTime implements StoppingCondition {
    private long endTime;
    private final long maxTimePeriod;

    public FixedElapsedTime(long maxTime) throws NumberIsTooSmallException {
        this(maxTime, TimeUnit.SECONDS);
    }

    public FixedElapsedTime(long maxTime, TimeUnit unit) throws NumberIsTooSmallException {
        this.endTime = -1;
        if (maxTime < 0) {
            throw new NumberIsTooSmallException(Long.valueOf(maxTime), Integer.valueOf(0), true);
        }
        this.maxTimePeriod = unit.toNanos(maxTime);
    }

    public boolean isSatisfied(Population population) {
        if (this.endTime < 0) {
            this.endTime = System.nanoTime() + this.maxTimePeriod;
        }
        return System.nanoTime() >= this.endTime;
    }
}
