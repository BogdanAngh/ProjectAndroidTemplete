package org.apache.commons.math4.fraction;

import java.io.Serializable;
import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;

public class BigFractionField implements Field<BigFraction>, Serializable {
    private static final long serialVersionUID = -1699294557189741703L;

    private static class LazyHolder {
        private static final BigFractionField INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new BigFractionField();
        }
    }

    private BigFractionField() {
    }

    public static BigFractionField getInstance() {
        return LazyHolder.INSTANCE;
    }

    public BigFraction getOne() {
        return BigFraction.ONE;
    }

    public BigFraction getZero() {
        return BigFraction.ZERO;
    }

    public Class<? extends FieldElement<BigFraction>> getRuntimeClass() {
        return BigFraction.class;
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
