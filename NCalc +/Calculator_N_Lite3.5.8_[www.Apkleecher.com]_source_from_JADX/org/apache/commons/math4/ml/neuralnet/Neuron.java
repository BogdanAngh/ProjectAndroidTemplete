package org.apache.commons.math4.ml.neuralnet;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicReference;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.util.Precision;

public class Neuron implements Serializable {
    private static final long serialVersionUID = 20130207;
    private final AtomicReference<double[]> features;
    private final long identifier;
    private final int size;

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 20130207;
        private final double[] features;
        private final long identifier;

        SerializationProxy(long identifier, double[] features) {
            this.identifier = identifier;
            this.features = features;
        }

        private Object readResolve() {
            return new Neuron(this.identifier, this.features);
        }
    }

    Neuron(long identifier, double[] features) {
        this.identifier = identifier;
        this.size = features.length;
        this.features = new AtomicReference((double[]) features.clone());
    }

    public long getIdentifier() {
        return this.identifier;
    }

    public int getSize() {
        return this.size;
    }

    public double[] getFeatures() {
        return (double[]) ((double[]) this.features.get()).clone();
    }

    public boolean compareAndSetFeatures(double[] expect, double[] update) {
        if (update.length != this.size) {
            throw new DimensionMismatchException(update.length, this.size);
        }
        double[] current = (double[]) this.features.get();
        if (containSameValues(current, expect)) {
            return this.features.compareAndSet(current, (double[]) update.clone());
        } else {
            return false;
        }
    }

    private boolean containSameValues(double[] current, double[] expect) {
        if (expect.length != this.size) {
            throw new DimensionMismatchException(expect.length, this.size);
        }
        for (int i = 0; i < this.size; i++) {
            if (!Precision.equals(current[i], expect[i])) {
                return false;
            }
        }
        return true;
    }

    private void readObject(ObjectInputStream in) {
        throw new IllegalStateException();
    }

    private Object writeReplace() {
        return new SerializationProxy(this.identifier, (double[]) this.features.get());
    }
}
