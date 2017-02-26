package com.github.mikephil.charting.data;

import android.graphics.Typeface;
import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.List;

public abstract class ChartData<T extends IDataSet<? extends Entry>> {
    protected List<T> mDataSets;
    protected float mLeftAxisMax;
    protected float mLeftAxisMin;
    protected float mRightAxisMax;
    protected float mRightAxisMin;
    protected float mXMax;
    protected float mXMin;
    protected float mYMax;
    protected float mYMin;

    public ChartData() {
        this.mYMax = -3.4028235E38f;
        this.mYMin = AutoScrollHelper.NO_MAX;
        this.mXMax = -3.4028235E38f;
        this.mXMin = AutoScrollHelper.NO_MAX;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = AutoScrollHelper.NO_MAX;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = AutoScrollHelper.NO_MAX;
        this.mDataSets = new ArrayList();
    }

    public ChartData(T... dataSets) {
        this.mYMax = -3.4028235E38f;
        this.mYMin = AutoScrollHelper.NO_MAX;
        this.mXMax = -3.4028235E38f;
        this.mXMin = AutoScrollHelper.NO_MAX;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = AutoScrollHelper.NO_MAX;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = AutoScrollHelper.NO_MAX;
        this.mDataSets = arrayToList(dataSets);
        init();
    }

    private List<T> arrayToList(T[] array) {
        List<T> list = new ArrayList();
        for (T set : array) {
            list.add(set);
        }
        return list;
    }

    public ChartData(List<T> sets) {
        this.mYMax = -3.4028235E38f;
        this.mYMin = AutoScrollHelper.NO_MAX;
        this.mXMax = -3.4028235E38f;
        this.mXMin = AutoScrollHelper.NO_MAX;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = AutoScrollHelper.NO_MAX;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = AutoScrollHelper.NO_MAX;
        this.mDataSets = sets;
        init();
    }

    protected void init() {
        calcMinMax();
    }

    public void notifyDataChanged() {
        init();
    }

    public void calcMinMax() {
        if (this.mDataSets != null) {
            int i;
            IDataSet dataSet;
            this.mYMax = -3.4028235E38f;
            this.mYMin = AutoScrollHelper.NO_MAX;
            this.mXMax = -3.4028235E38f;
            this.mXMin = AutoScrollHelper.NO_MAX;
            for (i = 0; i < this.mDataSets.size(); i++) {
                calcMinMax((IDataSet) this.mDataSets.get(i));
            }
            this.mLeftAxisMax = -3.4028235E38f;
            this.mLeftAxisMin = AutoScrollHelper.NO_MAX;
            this.mRightAxisMax = -3.4028235E38f;
            this.mRightAxisMin = AutoScrollHelper.NO_MAX;
            T firstLeft = getFirstLeft(this.mDataSets);
            if (firstLeft != null) {
                this.mLeftAxisMax = firstLeft.getYMax();
                this.mLeftAxisMin = firstLeft.getYMin();
                for (i = 0; i < this.mDataSets.size(); i++) {
                    dataSet = (IDataSet) this.mDataSets.get(i);
                    if (dataSet.getAxisDependency() == AxisDependency.LEFT) {
                        if (dataSet.getYMin() < this.mLeftAxisMin) {
                            this.mLeftAxisMin = dataSet.getYMin();
                        }
                        if (dataSet.getYMax() > this.mLeftAxisMax) {
                            this.mLeftAxisMax = dataSet.getYMax();
                        }
                    }
                }
            }
            T firstRight = getFirstRight(this.mDataSets);
            if (firstRight != null) {
                this.mRightAxisMax = firstRight.getYMax();
                this.mRightAxisMin = firstRight.getYMin();
                for (i = 0; i < this.mDataSets.size(); i++) {
                    dataSet = (IDataSet) this.mDataSets.get(i);
                    if (dataSet.getAxisDependency() == AxisDependency.RIGHT) {
                        if (dataSet.getYMin() < this.mRightAxisMin) {
                            this.mRightAxisMin = dataSet.getYMin();
                        }
                        if (dataSet.getYMax() > this.mRightAxisMax) {
                            this.mRightAxisMax = dataSet.getYMax();
                        }
                    }
                }
            }
        }
    }

    public int getDataSetCount() {
        if (this.mDataSets == null) {
            return 0;
        }
        return this.mDataSets.size();
    }

    public float getYMin() {
        return this.mYMin;
    }

    public float getYMin(AxisDependency axis) {
        if (axis == AxisDependency.LEFT) {
            if (this.mLeftAxisMin == AutoScrollHelper.NO_MAX) {
                return this.mRightAxisMin;
            }
            return this.mLeftAxisMin;
        } else if (this.mRightAxisMin == AutoScrollHelper.NO_MAX) {
            return this.mLeftAxisMin;
        } else {
            return this.mRightAxisMin;
        }
    }

    public float getYMax() {
        return this.mYMax;
    }

    public float getYMax(AxisDependency axis) {
        if (axis == AxisDependency.LEFT) {
            if (this.mLeftAxisMax == -3.4028235E38f) {
                return this.mRightAxisMax;
            }
            return this.mLeftAxisMax;
        } else if (this.mRightAxisMax == -3.4028235E38f) {
            return this.mLeftAxisMax;
        } else {
            return this.mRightAxisMax;
        }
    }

    public float getXMin() {
        return this.mXMin;
    }

    public float getXMax() {
        return this.mXMax;
    }

    public List<T> getDataSets() {
        return this.mDataSets;
    }

    protected int getDataSetIndexByLabel(List<T> dataSets, String label, boolean ignorecase) {
        int i;
        if (ignorecase) {
            for (i = 0; i < dataSets.size(); i++) {
                if (label.equalsIgnoreCase(((IDataSet) dataSets.get(i)).getLabel())) {
                    return i;
                }
            }
        } else {
            for (i = 0; i < dataSets.size(); i++) {
                if (label.equals(((IDataSet) dataSets.get(i)).getLabel())) {
                    return i;
                }
            }
        }
        return -1;
    }

    public String[] getDataSetLabels() {
        String[] types = new String[this.mDataSets.size()];
        for (int i = 0; i < this.mDataSets.size(); i++) {
            types[i] = ((IDataSet) this.mDataSets.get(i)).getLabel();
        }
        return types;
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        if (highlight.getDataSetIndex() >= this.mDataSets.size()) {
            return null;
        }
        return ((IDataSet) this.mDataSets.get(highlight.getDataSetIndex())).getEntryForXPos(highlight.getX());
    }

    public T getDataSetByLabel(String label, boolean ignorecase) {
        int index = getDataSetIndexByLabel(this.mDataSets, label, ignorecase);
        if (index < 0 || index >= this.mDataSets.size()) {
            return null;
        }
        return (IDataSet) this.mDataSets.get(index);
    }

    public T getDataSetByIndex(int index) {
        if (this.mDataSets == null || index < 0 || index >= this.mDataSets.size()) {
            return null;
        }
        return (IDataSet) this.mDataSets.get(index);
    }

    public void addDataSet(T d) {
        if (d != null) {
            calcMinMax(d);
            this.mDataSets.add(d);
        }
    }

    public boolean removeDataSet(T d) {
        if (d == null) {
            return false;
        }
        boolean removed = this.mDataSets.remove(d);
        if (!removed) {
            return removed;
        }
        calcMinMax();
        return removed;
    }

    public boolean removeDataSet(int index) {
        if (index >= this.mDataSets.size() || index < 0) {
            return false;
        }
        return removeDataSet((IDataSet) this.mDataSets.get(index));
    }

    public void addEntry(Entry e, int dataSetIndex) {
        if (this.mDataSets.size() <= dataSetIndex || dataSetIndex < 0) {
            Log.e("addEntry", "Cannot add Entry because dataSetIndex too high or too low.");
            return;
        }
        IDataSet set = (IDataSet) this.mDataSets.get(dataSetIndex);
        if (set.addEntry(e)) {
            calcMinMax(e, set.getAxisDependency());
        }
    }

    protected void calcMinMax(Entry e, AxisDependency axis) {
        if (this.mYMax < e.getY()) {
            this.mYMax = e.getY();
        }
        if (this.mYMin > e.getY()) {
            this.mYMin = e.getY();
        }
        if (this.mXMax < e.getX()) {
            this.mXMax = e.getX();
        }
        if (this.mXMin > e.getX()) {
            this.mXMin = e.getX();
        }
        if (axis == AxisDependency.LEFT) {
            if (this.mLeftAxisMax < e.getY()) {
                this.mLeftAxisMax = e.getY();
            }
            if (this.mLeftAxisMin > e.getY()) {
                this.mLeftAxisMin = e.getY();
                return;
            }
            return;
        }
        if (this.mRightAxisMax < e.getY()) {
            this.mRightAxisMax = e.getY();
        }
        if (this.mRightAxisMin > e.getY()) {
            this.mRightAxisMin = e.getY();
        }
    }

    protected void calcMinMax(T d) {
        if (this.mYMax < d.getYMax()) {
            this.mYMax = d.getYMax();
        }
        if (this.mYMin > d.getYMin()) {
            this.mYMin = d.getYMin();
        }
        if (this.mXMax < d.getXMax()) {
            this.mXMax = d.getXMax();
        }
        if (this.mXMin > d.getXMin()) {
            this.mXMin = d.getXMin();
        }
        if (d.getAxisDependency() == AxisDependency.LEFT) {
            if (this.mLeftAxisMax < d.getYMax()) {
                this.mLeftAxisMax = d.getYMax();
            }
            if (this.mLeftAxisMin > d.getYMin()) {
                this.mLeftAxisMin = d.getYMin();
                return;
            }
            return;
        }
        if (this.mRightAxisMax < d.getYMax()) {
            this.mRightAxisMax = d.getYMax();
        }
        if (this.mRightAxisMin > d.getYMin()) {
            this.mRightAxisMin = d.getYMin();
        }
    }

    public boolean removeEntry(Entry e, int dataSetIndex) {
        boolean z = false;
        if (e != null && dataSetIndex < this.mDataSets.size()) {
            IDataSet set = (IDataSet) this.mDataSets.get(dataSetIndex);
            if (set != null) {
                z = set.removeEntry(e);
                if (z) {
                    calcMinMax();
                }
            }
        }
        return z;
    }

    public boolean removeEntry(float xPos, int dataSetIndex) {
        if (dataSetIndex >= this.mDataSets.size()) {
            return false;
        }
        Entry e = ((IDataSet) this.mDataSets.get(dataSetIndex)).getEntryForXPos(xPos);
        if (e != null) {
            return removeEntry(e, dataSetIndex);
        }
        return false;
    }

    public T getDataSetForEntry(Entry e) {
        if (e == null) {
            return null;
        }
        for (int i = 0; i < this.mDataSets.size(); i++) {
            IDataSet set = (IDataSet) this.mDataSets.get(i);
            for (int j = 0; j < set.getEntryCount(); j++) {
                if (e.equalTo(set.getEntryForXPos(e.getX()))) {
                    return set;
                }
            }
        }
        return null;
    }

    public int[] getColors() {
        if (this.mDataSets == null) {
            return null;
        }
        int i;
        int clrcnt = 0;
        for (i = 0; i < this.mDataSets.size(); i++) {
            clrcnt += ((IDataSet) this.mDataSets.get(i)).getColors().size();
        }
        int[] colors = new int[clrcnt];
        int cnt = 0;
        for (i = 0; i < this.mDataSets.size(); i++) {
            for (Integer clr : ((IDataSet) this.mDataSets.get(i)).getColors()) {
                colors[cnt] = clr.intValue();
                cnt++;
            }
        }
        return colors;
    }

    public int getIndexOfDataSet(T dataSet) {
        return this.mDataSets.indexOf(dataSet);
    }

    protected T getFirstLeft(List<T> sets) {
        for (int i = 0; i < sets.size(); i++) {
            IDataSet dataSet = (IDataSet) sets.get(i);
            if (dataSet.getAxisDependency() == AxisDependency.LEFT) {
                return dataSet;
            }
        }
        return null;
    }

    public T getFirstRight(List<T> sets) {
        for (int i = 0; i < sets.size(); i++) {
            IDataSet dataSet = (IDataSet) sets.get(i);
            if (dataSet.getAxisDependency() == AxisDependency.RIGHT) {
                return dataSet;
            }
        }
        return null;
    }

    public void setValueFormatter(ValueFormatter f) {
        if (f != null) {
            for (int i = 0; i < this.mDataSets.size(); i++) {
                ((IDataSet) this.mDataSets.get(i)).setValueFormatter(f);
            }
        }
    }

    public void setValueTextColor(int color) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setValueTextColor(color);
        }
    }

    public void setValueTextColors(List<Integer> colors) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setValueTextColors(colors);
        }
    }

    public void setValueTypeface(Typeface tf) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setValueTypeface(tf);
        }
    }

    public void setValueTextSize(float size) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setValueTextSize(size);
        }
    }

    public void setDrawValues(boolean enabled) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setDrawValues(enabled);
        }
    }

    public void setHighlightEnabled(boolean enabled) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IDataSet) this.mDataSets.get(i)).setHighlightEnabled(enabled);
        }
    }

    public boolean isHighlightEnabled() {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            if (!((IDataSet) this.mDataSets.get(i)).isHighlightEnabled()) {
                return false;
            }
        }
        return true;
    }

    public void clearValues() {
        if (this.mDataSets != null) {
            this.mDataSets.clear();
        }
        notifyDataChanged();
    }

    public boolean contains(T dataSet) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            if (((IDataSet) this.mDataSets.get(i)).equals(dataSet)) {
                return true;
            }
        }
        return false;
    }

    public int getEntryCount() {
        int count = 0;
        for (int i = 0; i < this.mDataSets.size(); i++) {
            count += ((IDataSet) this.mDataSets.get(i)).getEntryCount();
        }
        return count;
    }

    public T getMaxEntryCountSet() {
        if (this.mDataSets == null || this.mDataSets.isEmpty()) {
            return null;
        }
        T max = (IDataSet) this.mDataSets.get(0);
        for (int i = 0; i < this.mDataSets.size(); i++) {
            T set = (IDataSet) this.mDataSets.get(i);
            if (set.getEntryCount() > max.getEntryCount()) {
                max = set;
            }
        }
        return max;
    }
}
