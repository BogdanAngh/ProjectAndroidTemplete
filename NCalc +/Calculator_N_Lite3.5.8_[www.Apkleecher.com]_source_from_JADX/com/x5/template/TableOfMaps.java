package com.x5.template;

import com.x5.util.ObjectDataMap;
import com.x5.util.TableData;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class TableOfMaps implements TableData {
    int cursor;
    private List<Map<String, Object>> data;

    public TableOfMaps(List list) {
        this.cursor = -1;
        this.data = list;
    }

    public String[] getColumnLabels() {
        return null;
    }

    public void setColumnLabels(String[] labels) {
    }

    public String[] getRow() {
        return null;
    }

    public boolean hasNext() {
        if (this.data == null || this.data.size() <= this.cursor + 1) {
            return false;
        }
        return true;
    }

    public Map<String, Object> nextRecord() {
        this.cursor++;
        if (this.data == null || this.cursor >= this.data.size()) {
            return null;
        }
        return (Map) this.data.get(this.cursor);
    }

    public void reset() {
        this.cursor = -1;
    }

    static TableData boxObjectArray(Object[] dataStore) {
        if (dataStore == null || dataStore.length < 1) {
            return null;
        }
        List<Map> boxedObjects = new ArrayList();
        for (Object objectDataMap : dataStore) {
            boxedObjects.add(new ObjectDataMap(objectDataMap));
        }
        return new TableOfMaps(boxedObjects);
    }

    static TableData boxObjectList(List dataStore) {
        if (dataStore == null || dataStore.size() < 1) {
            return null;
        }
        return boxIterator(dataStore.iterator());
    }

    static TableData boxEnumeration(Enumeration dataStore) {
        if (dataStore == null || !dataStore.hasMoreElements()) {
            return null;
        }
        List<Map> boxedObjects = new ArrayList();
        while (dataStore.hasMoreElements()) {
            boxedObjects.add(new ObjectDataMap(dataStore.nextElement()));
        }
        return new TableOfMaps(boxedObjects);
    }

    static TableData boxCollection(Collection collection) {
        if (collection == null || collection.size() < 1) {
            return null;
        }
        return boxIterator(collection.iterator());
    }

    static TableData boxIterator(Iterator i) {
        if (i == null || !i.hasNext()) {
            return null;
        }
        List<Map> boxedObjects = new ArrayList();
        while (i.hasNext()) {
            boxedObjects.add(new ObjectDataMap(i.next()));
        }
        return new TableOfMaps(boxedObjects);
    }
}
