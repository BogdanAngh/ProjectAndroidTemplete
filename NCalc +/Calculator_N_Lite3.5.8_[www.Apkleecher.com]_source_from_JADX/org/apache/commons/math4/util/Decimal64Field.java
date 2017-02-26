package org.apache.commons.math4.util;

import org.apache.commons.math4.Field;
import org.apache.commons.math4.FieldElement;

public class Decimal64Field implements Field<Decimal64> {
    private static final Decimal64Field INSTANCE;

    static {
        INSTANCE = new Decimal64Field();
    }

    private Decimal64Field() {
    }

    public static final Decimal64Field getInstance() {
        return INSTANCE;
    }

    public Decimal64 getZero() {
        return Decimal64.ZERO;
    }

    public Decimal64 getOne() {
        return Decimal64.ONE;
    }

    public Class<? extends FieldElement<Decimal64>> getRuntimeClass() {
        return Decimal64.class;
    }
}
