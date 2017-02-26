package org.apache.commons.math4.ml.neuralnet;

import java.io.ObjectInputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import org.apache.commons.math4.exception.DimensionMismatchException;
import org.apache.commons.math4.exception.MathIllegalStateException;

public class Network implements Iterable<Neuron>, Serializable {
    private static final long serialVersionUID = 20130207;
    private final int featureSize;
    private final ConcurrentHashMap<Long, Set<Long>> linkMap;
    private final ConcurrentHashMap<Long, Neuron> neuronMap;
    private final AtomicLong nextId;

    public static class NeuronIdentifierComparator implements Comparator<Neuron>, Serializable {
        private static final long serialVersionUID = 20130207;

        public int compare(Neuron a, Neuron b) {
            long aId = a.getIdentifier();
            long bId = b.getIdentifier();
            if (aId < bId) {
                return -1;
            }
            return aId > bId ? 1 : 0;
        }
    }

    private static class SerializationProxy implements Serializable {
        private static final long serialVersionUID = 20130207;
        private final int featureSize;
        private final long[][] neighbourIdList;
        private final Neuron[] neuronList;
        private final long nextId;

        SerializationProxy(long nextId, int featureSize, Neuron[] neuronList, long[][] neighbourIdList) {
            this.nextId = nextId;
            this.featureSize = featureSize;
            this.neuronList = neuronList;
            this.neighbourIdList = neighbourIdList;
        }

        private Object readResolve() {
            return new Network(this.nextId, this.featureSize, this.neuronList, this.neighbourIdList);
        }
    }

    Network(long nextId, int featureSize, Neuron[] neuronList, long[][] neighbourIdList) {
        this.neuronMap = new ConcurrentHashMap();
        this.linkMap = new ConcurrentHashMap();
        if (numNeurons != neighbourIdList.length) {
            throw new MathIllegalStateException();
        }
        int i;
        for (Neuron n : neuronList) {
            long id = n.getIdentifier();
            if (id >= nextId) {
                throw new MathIllegalStateException();
            }
            this.neuronMap.put(Long.valueOf(id), n);
            this.linkMap.put(Long.valueOf(id), new HashSet());
        }
        for (i = 0; i < numNeurons; i++) {
            Set<Long> aLinks = (Set) this.linkMap.get(Long.valueOf(neuronList[i].getIdentifier()));
            for (long valueOf : neighbourIdList[i]) {
                Long bId = Long.valueOf(valueOf);
                if (this.neuronMap.get(bId) == null) {
                    throw new MathIllegalStateException();
                }
                addLinkToLinkSet(aLinks, bId.longValue());
            }
        }
        this.nextId = new AtomicLong(nextId);
        this.featureSize = featureSize;
    }

    public Network(long initialIdentifier, int featureSize) {
        this.neuronMap = new ConcurrentHashMap();
        this.linkMap = new ConcurrentHashMap();
        this.nextId = new AtomicLong(initialIdentifier);
        this.featureSize = featureSize;
    }

    public Iterator<Neuron> iterator() {
        return this.neuronMap.values().iterator();
    }

    public Collection<Neuron> getNeurons(Comparator<Neuron> comparator) {
        List<Neuron> neurons = new ArrayList();
        neurons.addAll(this.neuronMap.values());
        Collections.sort(neurons, comparator);
        return neurons;
    }

    public long createNeuron(double[] features) {
        if (features.length != this.featureSize) {
            throw new DimensionMismatchException(features.length, this.featureSize);
        }
        long id = createNextId().longValue();
        this.neuronMap.put(Long.valueOf(id), new Neuron(id, features));
        this.linkMap.put(Long.valueOf(id), new HashSet());
        return id;
    }

    public void deleteNeuron(Neuron neuron) {
        for (Neuron n : getNeighbours(neuron)) {
            deleteLink(n, neuron);
        }
        this.neuronMap.remove(Long.valueOf(neuron.getIdentifier()));
    }

    public int getFeaturesSize() {
        return this.featureSize;
    }

    public void addLink(Neuron a, Neuron b) {
        long aId = a.getIdentifier();
        long bId = b.getIdentifier();
        if (a != getNeuron(aId)) {
            throw new NoSuchElementException(Long.toString(aId));
        } else if (b != getNeuron(bId)) {
            throw new NoSuchElementException(Long.toString(bId));
        } else {
            addLinkToLinkSet((Set) this.linkMap.get(Long.valueOf(aId)), bId);
        }
    }

    private void addLinkToLinkSet(Set<Long> linkSet, long id) {
        linkSet.add(Long.valueOf(id));
    }

    public void deleteLink(Neuron a, Neuron b) {
        long aId = a.getIdentifier();
        long bId = b.getIdentifier();
        if (a != getNeuron(aId)) {
            throw new NoSuchElementException(Long.toString(aId));
        } else if (b != getNeuron(bId)) {
            throw new NoSuchElementException(Long.toString(bId));
        } else {
            deleteLinkFromLinkSet((Set) this.linkMap.get(Long.valueOf(aId)), bId);
        }
    }

    private void deleteLinkFromLinkSet(Set<Long> linkSet, long id) {
        linkSet.remove(Long.valueOf(id));
    }

    public Neuron getNeuron(long id) {
        Neuron n = (Neuron) this.neuronMap.get(Long.valueOf(id));
        if (n != null) {
            return n;
        }
        throw new NoSuchElementException(Long.toString(id));
    }

    public Collection<Neuron> getNeighbours(Iterable<Neuron> neurons) {
        return getNeighbours((Iterable) neurons, null);
    }

    public Collection<Neuron> getNeighbours(Iterable<Neuron> neurons, Iterable<Neuron> exclude) {
        Set<Long> idList = new HashSet();
        for (Neuron n : neurons) {
            idList.addAll((Collection) this.linkMap.get(Long.valueOf(n.getIdentifier())));
        }
        if (exclude != null) {
            for (Neuron n2 : exclude) {
                idList.remove(Long.valueOf(n2.getIdentifier()));
            }
        }
        List<Neuron> neuronList = new ArrayList();
        for (Long id : idList) {
            neuronList.add(getNeuron(id.longValue()));
        }
        return neuronList;
    }

    public Collection<Neuron> getNeighbours(Neuron neuron) {
        return getNeighbours(neuron, null);
    }

    public Collection<Neuron> getNeighbours(Neuron neuron, Iterable<Neuron> exclude) {
        Set<Long> idList = (Set) this.linkMap.get(Long.valueOf(neuron.getIdentifier()));
        if (exclude != null) {
            for (Neuron n : exclude) {
                idList.remove(Long.valueOf(n.getIdentifier()));
            }
        }
        List<Neuron> neuronList = new ArrayList();
        for (Long id : idList) {
            neuronList.add(getNeuron(id.longValue()));
        }
        return neuronList;
    }

    private Long createNextId() {
        return Long.valueOf(this.nextId.getAndIncrement());
    }

    private void readObject(ObjectInputStream in) {
        throw new IllegalStateException();
    }

    private Object writeReplace() {
        Neuron[] neuronList = (Neuron[]) this.neuronMap.values().toArray(new Neuron[0]);
        long[][] neighbourIdList = new long[neuronList.length][];
        for (int i = 0; i < neuronList.length; i++) {
            Collection<Neuron> neighbours = getNeighbours(neuronList[i]);
            long[] neighboursId = new long[neighbours.size()];
            int count = 0;
            for (Neuron n : neighbours) {
                neighboursId[count] = n.getIdentifier();
                count++;
            }
            neighbourIdList[i] = neighboursId;
        }
        return new SerializationProxy(this.nextId.get(), this.featureSize, neuronList, neighbourIdList);
    }
}
