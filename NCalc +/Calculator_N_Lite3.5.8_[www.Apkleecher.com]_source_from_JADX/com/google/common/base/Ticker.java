package com.google.common.base;

import com.google.common.annotations.Beta;
import com.google.common.annotations.GwtCompatible;

@GwtCompatible
@Beta
public abstract class Ticker {
    private static final Ticker SYSTEM_TICKER;

    static class 1 extends Ticker {
        1() {
        }

        public long read() {
            return Platform.systemNanoTime();
        }
    }

    public abstract long read();

    protected Ticker() {
    }

    public static Ticker systemTicker() {
        return SYSTEM_TICKER;
    }

    static {
        SYSTEM_TICKER = new 1();
    }
}
