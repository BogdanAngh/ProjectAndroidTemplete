package com.x5.template;

import com.x5.util.TableData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.SortedMap;

public class ObjectTable implements TableData, Map<String, Object> {
    public static final String KEY = "key";
    private static final String[] LABELS;
    public static final String VALUE = "value";
    private Object currentKey;
    private Iterator i;
    private Map obj;

    static {
        LABELS = new String[]{KEY, VALUE};
    }

    public ObjectTable(Map map) {
        this.i = null;
        this.currentKey = null;
        this.obj = map;
    }

    public String[] getColumnLabels() {
        return LABELS;
    }

    public void setColumnLabels(String[] labels) {
    }

    public String[] getRow() {
        if (this.currentKey == null) {
            return null;
        }
        return new String[]{this.currentKey.toString(), this.obj.get(this.currentKey).toString()};
    }

    public boolean hasNext() {
        if (this.obj == null) {
            return false;
        }
        if (this.i == null) {
            this.i = getOrderedKeys().iterator();
        }
        return this.i.hasNext();
    }

    public Map<String, Object> nextRecord() {
        if (hasNext()) {
            this.currentKey = this.i.next();
            return this;
        }
        this.currentKey = null;
        return null;
    }

    private List getOrderedKeys() {
        List keys = new ArrayList(this.obj.keySet());
        if (!((this.obj instanceof LinkedHashMap) || (this.obj instanceof SortedMap))) {
            Collections.sort(keys);
        }
        return keys;
    }

    public void reset() {
        this.i = getOrderedKeys().iterator();
    }

    public int size() {
        if (hasNext()) {
            return LABELS.length;
        }
        return 0;
    }

    public boolean isEmpty() {
        if (this.currentKey == null) {
            return true;
        }
        return false;
    }

    public boolean containsKey(Object key) {
        if (key == null || (!key.equals(LABELS[0]) && !key.equals(LABELS[1]))) {
            return false;
        }
        return true;
    }

    public boolean containsValue(Object value) {
        if (value != null && value.equals(this.obj.get(this.currentKey))) {
            return true;
        }
        return false;
    }

    public Object get(Object key) {
        if (key == null || this.currentKey == null) {
            return null;
        }
        if (key.equals(KEY)) {
            return this.currentKey;
        }
        if (key.equals(VALUE)) {
            return this.obj.get(this.currentKey);
        }
        return null;
    }

    public Object put(String key, Object value) {
        return null;
    }

    public Object remove(Object key) {
        return null;
    }

    public void putAll(Map<? extends String, ? extends Object> map) {
    }

    public void clear() {
    }

    public Set<String> keySet() {
        HashSet<String> keys = new HashSet();
        keys.add(KEY);
        keys.add(VALUE);
        return keys;
    }

    public Collection<Object> values() {
        if (this.currentKey == null) {
            return null;
        }
        HashSet<Object> values = new HashSet();
        values.add(this.obj.get(this.currentKey));
        return values;
    }

    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
