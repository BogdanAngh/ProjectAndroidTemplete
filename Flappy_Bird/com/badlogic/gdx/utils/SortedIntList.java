package com.badlogic.gdx.utils;

public class SortedIntList<E> implements Iterable<Node<E>> {
    Node<E> first;
    private Iterator iterator;
    private NodePool<E> nodePool;
    int size;

    class Iterator implements java.util.Iterator<Node<E>> {
        private Node<E> position;
        private Node<E> previousPosition;

        Iterator() {
        }

        public boolean hasNext() {
            return this.position != null;
        }

        public Node<E> next() {
            this.previousPosition = this.position;
            this.position = this.position.f87n;
            return this.previousPosition;
        }

        public void remove() {
            if (this.previousPosition != null) {
                if (this.previousPosition == SortedIntList.this.first) {
                    SortedIntList.this.first = this.position;
                } else {
                    this.previousPosition.f88p.f87n = this.position;
                    if (this.position != null) {
                        this.position.f88p = this.previousPosition.f88p;
                    }
                }
                SortedIntList sortedIntList = SortedIntList.this;
                sortedIntList.size--;
            }
        }

        public Iterator reset() {
            this.position = SortedIntList.this.first;
            this.previousPosition = null;
            return this;
        }
    }

    public static class Node<E> {
        public int index;
        protected Node<E> f87n;
        protected Node<E> f88p;
        public E value;
    }

    static class NodePool<E> extends Pool<Node<E>> {
        NodePool() {
        }

        protected Node<E> newObject() {
            return new Node();
        }

        public Node<E> obtain(Node<E> p, Node<E> n, E value, int index) {
            Node<E> newNode = (Node) super.obtain();
            newNode.f88p = p;
            newNode.f87n = n;
            newNode.value = value;
            newNode.index = index;
            return newNode;
        }
    }

    public SortedIntList() {
        this.nodePool = new NodePool();
        this.size = 0;
    }

    public E insert(int index, E value) {
        if (this.first != null) {
            Node<E> c = this.first;
            while (c.f87n != null && c.f87n.index <= index) {
                c = c.f87n;
            }
            if (index > c.index) {
                c.f87n = this.nodePool.obtain(c, c.f87n, value, index);
                if (c.f87n.f87n != null) {
                    c.f87n.f87n.f88p = c.f87n;
                }
                this.size++;
            } else if (index < c.index) {
                Node<E> newFirst = this.nodePool.obtain(null, this.first, value, index);
                this.first.f88p = newFirst;
                this.first = newFirst;
                this.size++;
            } else {
                c.value = value;
            }
        } else {
            this.first = this.nodePool.obtain(null, null, value, index);
            this.size++;
        }
        return null;
    }

    public E get(int index) {
        if (this.first == null) {
            return null;
        }
        Node<E> c = this.first;
        while (c.f87n != null && c.index < index) {
            c = c.f87n;
        }
        if (c.index == index) {
            return c.value;
        }
        return null;
    }

    public void clear() {
        while (this.first != null) {
            this.nodePool.free(this.first);
            this.first = this.first.f87n;
        }
        this.size = 0;
    }

    public int size() {
        return this.size;
    }

    public java.util.Iterator<Node<E>> iterator() {
        if (this.iterator == null) {
            this.iterator = new Iterator();
        }
        return this.iterator.reset();
    }
}
