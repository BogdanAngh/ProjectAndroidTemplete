package org.apache.commons.math4.ml.neuralnet.sofm;

import java.util.HashSet;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.math4.analysis.function.Gaussian;
import org.apache.commons.math4.linear.ArrayRealVector;
import org.apache.commons.math4.linear.RealVector;
import org.apache.commons.math4.ml.distance.DistanceMeasure;
import org.apache.commons.math4.ml.neuralnet.MapUtils;
import org.apache.commons.math4.ml.neuralnet.Network;
import org.apache.commons.math4.ml.neuralnet.Neuron;
import org.apache.commons.math4.ml.neuralnet.UpdateAction;

public class KohonenUpdateAction implements UpdateAction {
    private final DistanceMeasure distance;
    private final LearningFactorFunction learningFactor;
    private final NeighbourhoodSizeFunction neighbourhoodSize;
    private final AtomicLong numberOfCalls;

    public KohonenUpdateAction(DistanceMeasure distance, LearningFactorFunction learningFactor, NeighbourhoodSizeFunction neighbourhoodSize) {
        this.numberOfCalls = new AtomicLong(-1);
        this.distance = distance;
        this.learningFactor = learningFactor;
        this.neighbourhoodSize = neighbourhoodSize;
    }

    public void update(Network net, double[] features) {
        long numCalls = this.numberOfCalls.incrementAndGet();
        double currentLearning = this.learningFactor.value(numCalls);
        Neuron best = findAndUpdateBestNeuron(net, features, currentLearning);
        int currentNeighbourhood = this.neighbourhoodSize.value(numCalls);
        Gaussian neighbourhoodDecay = new Gaussian(currentLearning, 0.0d, 1.0d / ((double) currentNeighbourhood));
        if (currentNeighbourhood > 0) {
            Iterable neighbours = new HashSet();
            neighbours.add(best);
            Iterable exclude = new HashSet();
            exclude.add(best);
            int radius = 1;
            do {
                Iterable<Neuron> neighbours2 = net.getNeighbours(neighbours, exclude);
                for (Neuron n : neighbours2) {
                    updateNeighbouringNeuron(n, features, neighbourhoodDecay.value((double) radius));
                }
                exclude.addAll(neighbours2);
                radius++;
            } while (radius <= currentNeighbourhood);
        }
    }

    public long getNumberOfCalls() {
        return this.numberOfCalls.get();
    }

    private void updateNeighbouringNeuron(Neuron n, double[] features, double learningRate) {
        double[] expect;
        do {
            expect = n.getFeatures();
        } while (!n.compareAndSetFeatures(expect, computeFeatures(expect, features, learningRate)));
    }

    private Neuron findAndUpdateBestNeuron(Network net, double[] features, double learningRate) {
        Neuron best;
        double[] expect;
        do {
            best = MapUtils.findBest(features, net, this.distance);
            expect = best.getFeatures();
        } while (!best.compareAndSetFeatures(expect, computeFeatures(expect, features, learningRate)));
        return best;
    }

    private double[] computeFeatures(double[] current, double[] sample, double learningRate) {
        RealVector c = new ArrayRealVector(current, false);
        return new ArrayRealVector(sample, false).subtract(c).mapMultiplyToSelf(learningRate).add(c).toArray();
    }
}
