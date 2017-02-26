package org.apache.commons.math4.linear;

public interface RealVectorChangingVisitor {
    double end();

    void start(int i, int i2, int i3);

    double visit(int i, double d);
}
