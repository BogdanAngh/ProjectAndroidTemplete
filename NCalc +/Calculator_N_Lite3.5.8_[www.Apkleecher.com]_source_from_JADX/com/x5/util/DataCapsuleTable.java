package com.x5.util;

import java.util.HashMap;
import java.util.Map;

public class DataCapsuleTable implements TableData {
    private String[] columnLabels;
    private Map<String, Object> currentRecord;
    private int cursor;
    private DataCapsule[] records;

    public static DataCapsuleTable extractData(Object[] objArray) {
        if (objArray == null) {
            return null;
        }
        int capsuleCount = 0;
        DataCapsule[] dataCapsules = new DataCapsule[objArray.length];
        for (int i = 0; i < objArray.length; i++) {
            Object o = objArray[i];
            if (o != null && (o instanceof DataCapsule)) {
                dataCapsules[i] = (DataCapsule) o;
                capsuleCount++;
            }
        }
        if (capsuleCount != 0) {
            return new DataCapsuleTable(dataCapsules);
        }
        return null;
    }

    public DataCapsuleTable(DataCapsule[] dataCapsules) {
        this.cursor = -1;
        this.records = dataCapsules;
    }

    public String[] getColumnLabels() {
        if (this.columnLabels == null) {
            return getReader().getColumnLabels();
        }
        return this.columnLabels;
    }

    public Object[] getRowRaw() {
        if (this.cursor < 0) {
            this.cursor = 0;
        }
        if (this.records == null || this.records.length <= this.cursor) {
            return null;
        }
        return getReader().extractData(this.records[this.cursor]);
    }

    private DataCapsuleReader getReader() {
        int readerIndex = this.cursor;
        if (readerIndex < 0) {
            readerIndex = 0;
        }
        if (this.records == null || this.records.length <= readerIndex) {
            return null;
        }
        return DataCapsuleReader.getReader(this.records[readerIndex]);
    }

    public String[] getRow() {
        Object[] rawRow = getRowRaw();
        String[] row = new String[rawRow.length];
        for (int i = 0; i < rawRow.length; i++) {
            Object x = rawRow[i];
            if (x == null) {
                row[i] = null;
            } else if (x instanceof String) {
                row[i] = (String) x;
            } else {
                row[i] = x.toString();
            }
        }
        return row;
    }

    public boolean hasNext() {
        if (this.records == null || this.records.length <= this.cursor + 1) {
            return false;
        }
        return true;
    }

    public Map<String, Object> nextRecord() {
        this.cursor++;
        String[] values = getRow();
        if (values == null) {
            return null;
        }
        if (this.currentRecord == null) {
            this.currentRecord = new HashMap();
        } else {
            this.currentRecord.clear();
        }
        String[] labels = getColumnLabels();
        for (int i = 0; i < labels.length; i++) {
            this.currentRecord.put(labels[i], values[i]);
        }
        return this.currentRecord;
    }

    public void setColumnLabels(String[] labels) {
        this.columnLabels = labels;
    }

    public void reset() {
        this.cursor = -1;
    }
}
