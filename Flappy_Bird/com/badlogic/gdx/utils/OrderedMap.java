package com.badlogic.gdx.utils;

import com.badlogic.gdx.utils.ObjectMap.Entries;
import com.badlogic.gdx.utils.ObjectMap.Entry;
import com.badlogic.gdx.utils.ObjectMap.Keys;
import com.badlogic.gdx.utils.ObjectMap.Values;

public class OrderedMap<K, V> extends ObjectMap<K, V> {
    final Array<K> keys;

    /* renamed from: com.badlogic.gdx.utils.OrderedMap.1 */
    class C05901 extends Entries {
        C05901(ObjectMap x0) {
            super(x0);
        }

        void advance() {
            this.nextIndex++;
            this.hasNext = this.nextIndex < this.map.size;
        }

        public Entry next() {
            this.entry.key = OrderedMap.this.keys.get(this.nextIndex);
            this.entry.value = this.map.get(this.entry.key);
            advance();
            return this.entry;
        }

        public void remove() {
            this.map.remove(this.entry.key);
        }
    }

    /* renamed from: com.badlogic.gdx.utils.OrderedMap.2 */
    class C05912 extends Keys {
        C05912(ObjectMap x0) {
            super(x0);
        }

        void advance() {
            this.nextIndex++;
            this.hasNext = this.nextIndex < this.map.size;
        }

        public K next() {
            K key = OrderedMap.this.keys.get(this.nextIndex);
            advance();
            return key;
        }

        public void remove() {
            this.map.remove(OrderedMap.this.keys.get(this.nextIndex - 1));
        }
    }

    /* renamed from: com.badlogic.gdx.utils.OrderedMap.3 */
    class C05923 extends Values {
        C05923(ObjectMap x0) {
            super(x0);
        }

        void advance() {
            this.nextIndex++;
            this.hasNext = this.nextIndex < this.map.size;
        }

        public V next() {
            V value = this.map.get(OrderedMap.this.keys.get(this.nextIndex));
            advance();
            return value;
        }

        public void remove() {
            this.map.remove(OrderedMap.this.keys.get(this.nextIndex - 1));
        }
    }

    public OrderedMap() {
        this.keys = new Array();
    }

    public OrderedMap(int initialCapacity) {
        super(initialCapacity);
        this.keys = new Array(initialCapacity);
    }

    public OrderedMap(int initialCapacity, float loadFactor) {
        super(initialCapacity, loadFactor);
        this.keys = new Array(initialCapacity);
    }

    public V put(K key, V value) {
        if (!containsKey(key)) {
            this.keys.add(key);
        }
        return super.put(key, value);
    }

    public V remove(K key) {
        this.keys.removeValue(key, false);
        return super.remove(key);
    }

    public void clear(int maximumCapacity) {
        this.keys.clear();
        super.clear(maximumCapacity);
    }

    public void clear() {
        this.keys.clear();
        super.clear();
    }

    public Array<K> orderedKeys() {
        return this.keys;
    }

    public Entries<K, V> entries() {
        return new C05901(this);
    }

    public Keys<K> keys() {
        return new C05912(this);
    }

    public Values<V> values() {
        return new C05923(this);
    }

    public String toString() {
        if (this.size == 0) {
            return "{}";
        }
        StringBuilder buffer = new StringBuilder(32);
        buffer.append('{');
        Array<K> keys = this.keys;
        int n = keys.size;
        for (int i = 0; i < n; i++) {
            Object key = keys.get(i);
            if (i > 0) {
                buffer.append(", ");
            }
            buffer.append(key);
            buffer.append('=');
            buffer.append(get(key));
        }
        buffer.append('}');
        return buffer.toString();
    }
}
