package com.google.common.collect;

import com.google.common.annotations.GwtCompatible;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

@GwtCompatible
final class WellBehavedMap<K, V> extends ForwardingMap<K, V> {
    private final Map<K, V> delegate;
    private Set<Entry<K, V>> entrySet;

    private final class EntrySet extends EntrySet<K, V> {

        class 1 extends TransformedIterator<K, Entry<K, V>> {

            class 1 extends AbstractMapEntry<K, V> {
                final /* synthetic */ Object val$key;

                1(Object obj) {
                    this.val$key = obj;
                }

                public K getKey() {
                    return this.val$key;
                }

                public V getValue() {
                    return WellBehavedMap.this.get(this.val$key);
                }

                public V setValue(V value) {
                    return WellBehavedMap.this.put(this.val$key, value);
                }
            }

            1(Iterator x0) {
                super(x0);
            }

            Entry<K, V> transform(K key) {
                return new 1(key);
            }
        }

        private EntrySet() {
        }

        Map<K, V> map() {
            return WellBehavedMap.this;
        }

        public Iterator<Entry<K, V>> iterator() {
            return new 1(WellBehavedMap.this.keySet().iterator());
        }
    }

    private WellBehavedMap(Map<K, V> delegate) {
        this.delegate = delegate;
    }

    static <K, V> WellBehavedMap<K, V> wrap(Map<K, V> delegate) {
        return new WellBehavedMap(delegate);
    }

    protected Map<K, V> delegate() {
        return this.delegate;
    }

    public Set<Entry<K, V>> entrySet() {
        Set<Entry<K, V>> es = this.entrySet;
        if (es != null) {
            return es;
        }
        es = new EntrySet();
        this.entrySet = es;
        return es;
    }
}
