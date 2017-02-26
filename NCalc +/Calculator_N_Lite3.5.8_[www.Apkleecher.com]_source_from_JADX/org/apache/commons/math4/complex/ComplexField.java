package org.apache.commons.math4.complex;

import java.io.Serializable;
import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;

public class ComplexField implements Field<Complex>, Serializable {
    private static final long serialVersionUID = -6130362688700788798L;

    private static class LazyHolder {
        private static final ComplexField INSTANCE;

        private LazyHolder() {
        }

        static {
            INSTANCE = new ComplexField();
        }
    }

    private ComplexField() {
    }

    public static ComplexField getInstance() {
        return LazyHolder.INSTANCE;
    }

    public Complex getOne() {
        return Complex.ONE;
    }

    public Complex getZero() {
        return Complex.ZERO;
    }

    public Class<? extends FieldElement<Complex>> getRuntimeClass() {
        return Complex.class;
    }

    private Object readResolve() {
        return LazyHolder.INSTANCE;
    }
}
