package com.badlogic.gdx.utils;

public class PooledLinkedList<T> {
    private Item<T> curr;
    private Item<T> head;
    private Item<T> iter;
    private final Pool<Item<T>> pool;
    private int size;
    private Item<T> tail;

    static final class Item<T> {
        public Item<T> next;
        public T payload;
        public Item<T> prev;

        Item() {
        }
    }

    /* renamed from: com.badlogic.gdx.utils.PooledLinkedList.1 */
    class C03811 extends Pool<Item<T>> {
        C03811(int x0, int x1) {
            super(x0, x1);
        }

        protected Item<T> newObject() {
            return new Item();
        }
    }

    public PooledLinkedList(int maxPoolSize) {
        this.size = 0;
        this.pool = new C03811(16, maxPoolSize);
    }

    public void add(T object) {
        Item<T> item = (Item) this.pool.obtain();
        item.payload = object;
        item.next = null;
        item.prev = null;
        if (this.head == null) {
            this.head = item;
            this.tail = item;
            this.size++;
            return;
        }
        item.prev = this.tail;
        this.tail.next = item;
        this.tail = item;
        this.size++;
    }

    public void iter() {
        this.iter = this.head;
    }

    public T next() {
        if (this.iter == null) {
            return null;
        }
        T payload = this.iter.payload;
        this.curr = this.iter;
        this.iter = this.iter.next;
        return payload;
    }

    public void remove() {
        if (this.curr != null) {
            this.size--;
            this.pool.free(this.curr);
            Item<T> c = this.curr;
            Item<T> n = this.curr.next;
            Item<T> p = this.curr.prev;
            this.curr = null;
            if (this.size == 0) {
                this.head = null;
                this.tail = null;
            } else if (c == this.head) {
                n.prev = null;
                this.head = n;
            } else if (c == this.tail) {
                p.next = null;
                this.tail = p;
            } else {
                p.next = n;
                n.prev = p;
            }
        }
    }

    public void clear() {
        iter();
        while (next() != null) {
            remove();
        }
    }
}
