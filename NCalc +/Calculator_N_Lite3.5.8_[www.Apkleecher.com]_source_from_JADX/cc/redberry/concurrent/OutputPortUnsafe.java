package cc.redberry.concurrent;

import java.util.Iterator;

public interface OutputPortUnsafe<T> {

    public static final class PortIterable<T> implements Iterable<T> {
        private final OutputPortUnsafe<T> opu;

        public PortIterable(OutputPortUnsafe<T> opu) {
            this.opu = opu;
        }

        public Iterator<T> iterator() {
            return new PortIterator(this.opu);
        }
    }

    public static final class PortIterator<T> implements Iterator<T> {
        private T next;
        private final OutputPortUnsafe<T> opu;

        public PortIterator(OutputPortUnsafe<T> opu) {
            this.opu = opu;
        }

        public boolean hasNext() {
            Object take = this.opu.take();
            this.next = take;
            return take != null;
        }

        public T next() {
            return this.next;
        }

        public void remove() {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }

    public static final class Singleton<T> implements OutputPortUnsafe<T> {
        private T element;

        public Singleton(T element) {
            this.element = element;
        }

        public T take() {
            T newElement = this.element;
            this.element = null;
            return newElement;
        }
    }

    T take();
}
