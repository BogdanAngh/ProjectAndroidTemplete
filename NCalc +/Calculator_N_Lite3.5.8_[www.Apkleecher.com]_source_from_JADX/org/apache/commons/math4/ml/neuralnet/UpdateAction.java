package org.apache.commons.math4.ml.neuralnet;

public interface UpdateAction {
    void update(Network network, double[] dArr);
}
