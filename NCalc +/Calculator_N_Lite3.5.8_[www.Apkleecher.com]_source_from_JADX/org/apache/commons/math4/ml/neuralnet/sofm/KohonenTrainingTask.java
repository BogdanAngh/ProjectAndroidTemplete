package org.apache.commons.math4.ml.neuralnet.sofm;

import java.util.Iterator;
import org.apache.commons.math4.ml.neuralnet.Network;

public class KohonenTrainingTask implements Runnable {
    private final Iterator<double[]> featuresIterator;
    private final Network net;
    private final KohonenUpdateAction updateAction;

    public KohonenTrainingTask(Network net, Iterator<double[]> featuresIterator, KohonenUpdateAction updateAction) {
        this.net = net;
        this.featuresIterator = featuresIterator;
        this.updateAction = updateAction;
    }

    public void run() {
        while (this.featuresIterator.hasNext()) {
            this.updateAction.update(this.net, (double[]) this.featuresIterator.next());
        }
    }
}
