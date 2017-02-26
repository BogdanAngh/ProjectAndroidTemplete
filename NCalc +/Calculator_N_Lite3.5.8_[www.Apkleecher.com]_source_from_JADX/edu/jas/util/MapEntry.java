package edu.jas.util;

import java.util.Map.Entry;

public class MapEntry<K, V> implements Entry<K, V> {
    final K key;
    final V value;

    public MapEntry(K k, V v) {
        this.key = k;
        this.value = v;
    }

    public K getKey() {
        return this.key;
    }

    public V getValue() {
        return this.value;
    }

    public V setValue(V v) {
        throw new UnsupportedOperationException("not implemented");
    }

    public boolean equals(Object b) {
        if (!(b instanceof Entry)) {
            return false;
        }
        Entry<K, V> me = (Entry) b;
        if (this.key.equals(me.getKey()) && this.value.equals(me.getValue())) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        return (this.key.hashCode() * 37) + this.value.hashCode();
    }
}
