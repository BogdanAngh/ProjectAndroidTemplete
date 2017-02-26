package org.apache.commons.math4.optim.linear;

import org.apache.commons.math4.random.ValueServer;
import org.matheclipse.core.interfaces.IExpr;

public enum Relationship {
    EQ("="),
    LEQ("<="),
    GEQ(">=");
    
    private final String stringValue;

    private Relationship(String stringValue) {
        this.stringValue = stringValue;
    }

    public String toString() {
        return this.stringValue;
    }

    public Relationship oppositeRelationship() {
        switch ($SWITCH_TABLE$org$apache$commons$math4$optim$linear$Relationship()[ordinal()]) {
            case IExpr.DOUBLEID /*2*/:
                return GEQ;
            case ValueServer.EXPONENTIAL_MODE /*3*/:
                return LEQ;
            default:
                return EQ;
        }
    }
}
