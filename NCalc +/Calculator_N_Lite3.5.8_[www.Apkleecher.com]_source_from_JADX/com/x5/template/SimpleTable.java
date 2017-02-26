package com.x5.template;

import com.x5.util.TableData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

public class SimpleTable implements TableData, Map<String, Object> {
    public static final String ANON_ARRAY_LABEL = "_anonymous_";
    private Map<String, Integer> columnIndex;
    private int cursor;
    private String[] labels;
    private List<String[]> records;
    private int size;

    public SimpleTable(String[] columnLabels, Vector<String[]> tableRows) {
        this.cursor = -1;
        this.size = 0;
        this.labels = columnLabels;
        this.records = new ArrayList(tableRows);
    }

    public SimpleTable(String[] columnLabels, ArrayList<String[]> tableRows) {
        this.cursor = -1;
        this.size = 0;
        this.labels = columnLabels;
        this.records = tableRows;
    }

    public SimpleTable(String[] columnLabels, String[][] tableRows) {
        this.cursor = -1;
        this.size = 0;
        this.labels = columnLabels;
        this.records = new ArrayList();
        if (tableRows != null) {
            for (Object add : tableRows) {
                this.records.add(add);
            }
        }
    }

    public SimpleTable(String[] data) {
        this.cursor = -1;
        this.size = 0;
        if (data != null) {
            this.labels = new String[]{ANON_ARRAY_LABEL};
            this.records = new ArrayList();
            for (int i = 0; i < data.length; i++) {
                this.records.add(new String[]{data[i]});
            }
        }
    }

    public SimpleTable(List list) {
        this.cursor = -1;
        this.size = 0;
        if (list != null) {
            this.labels = new String[]{ANON_ARRAY_LABEL};
            this.records = new ArrayList();
            for (int i = 0; i < list.size(); i++) {
                this.records.add(new String[]{list.get(i).toString()});
            }
        }
    }

    public String[] getColumnLabels() {
        return this.labels;
    }

    public void setColumnLabels(String[] labels) {
        this.labels = labels;
    }

    public String[] getRow() {
        int i = 0;
        if (this.cursor < 0) {
            this.cursor = 0;
            if (this.records != null) {
                i = this.records.size();
            }
            this.size = i;
        }
        if (this.size > this.cursor) {
            return (String[]) this.records.get(this.cursor);
        }
        return null;
    }

    public boolean hasNext() {
        if (this.size > this.cursor + 1) {
            return true;
        }
        if (this.size != 0) {
            return false;
        }
        this.size = this.records == null ? 0 : this.records.size();
        if (this.size > this.cursor + 1) {
            return true;
        }
        return false;
    }

    public Map<String, Object> nextRecord() {
        this.cursor++;
        if (this.size > this.cursor) {
            return this;
        }
        if (this.size != 0) {
            return null;
        }
        this.size = this.records == null ? 0 : this.records.size();
        if (this.size <= this.cursor) {
            return null;
        }
        return this;
    }

    public void reset() {
        this.cursor = -1;
    }

    public int size() {
        return this.labels == null ? 0 : this.labels.length;
    }

    public boolean isEmpty() {
        return this.labels == null;
    }

    public boolean containsKey(Object key) {
        if (this.columnIndex == null) {
            indexColumns();
        }
        if (this.columnIndex == null) {
            return false;
        }
        return this.columnIndex.containsKey(key);
    }

    private void indexColumns() {
        if (this.labels != null) {
            this.columnIndex = new HashMap(this.labels.length);
            for (int i = 0; i < this.labels.length; i++) {
                this.columnIndex.put(this.labels[i], Integer.valueOf(i));
            }
        }
    }

    public boolean containsValue(Object value) {
        String[] record = getRow();
        if (record == null) {
            return false;
        }
        for (Object equals : record) {
            if (value.equals(equals)) {
                return true;
            }
        }
        return false;
    }

    public Object get(Object key) {
        if (this.labels == null) {
            return null;
        }
        if (this.columnIndex == null) {
            indexColumns();
        }
        if (this.columnIndex == null || !this.columnIndex.containsKey(key)) {
            return null;
        }
        try {
            return getRow()[((Integer) this.columnIndex.get(key)).intValue()];
        } catch (ArrayIndexOutOfBoundsException e) {
            return null;
        }
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
        if (this.labels == null) {
            return null;
        }
        if (this.columnIndex == null) {
            indexColumns();
        }
        if (this.columnIndex != null) {
            return this.columnIndex.keySet();
        }
        return null;
    }

    public Collection<Object> values() {
        String[] record = getRow();
        if (record == null) {
            return null;
        }
        Collection<Object> list = new ArrayList();
        for (Object add : record) {
            list.add(add);
        }
        return list;
    }

    public Set<Entry<String, Object>> entrySet() {
        return null;
    }
}
