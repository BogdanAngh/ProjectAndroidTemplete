package com.github.mikephil.charting.data;

import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;

public class PieData extends ChartData<IPieDataSet> {
    public PieData(IPieDataSet dataSet) {
        super(dataSet);
    }

    public void setDataSet(IPieDataSet dataSet) {
        this.mDataSets.clear();
        this.mDataSets.add(dataSet);
        init();
    }

    public IPieDataSet getDataSet() {
        return (IPieDataSet) this.mDataSets.get(0);
    }

    public IPieDataSet getDataSetByIndex(int index) {
        return index == 0 ? getDataSet() : null;
    }

    public IPieDataSet getDataSetByLabel(String label, boolean ignorecase) {
        if (!ignorecase) {
            return label.equals(((IPieDataSet) this.mDataSets.get(0)).getLabel()) ? (IPieDataSet) this.mDataSets.get(0) : null;
        } else {
            if (label.equalsIgnoreCase(((IPieDataSet) this.mDataSets.get(0)).getLabel())) {
                return (IPieDataSet) this.mDataSets.get(0);
            }
            return null;
        }
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        return getDataSet().getEntryForIndex((int) highlight.getX());
    }

    public float getYValueSum() {
        float sum = 0.0f;
        for (int i = 0; i < getDataSet().getEntryCount(); i++) {
            sum += ((PieEntry) getDataSet().getEntryForIndex(i)).getY();
        }
        return sum;
    }
}
