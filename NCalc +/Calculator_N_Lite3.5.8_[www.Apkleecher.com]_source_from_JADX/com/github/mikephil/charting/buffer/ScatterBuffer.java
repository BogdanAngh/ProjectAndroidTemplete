package com.github.mikephil.charting.buffer;

import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;

public class ScatterBuffer extends AbstractBuffer<IScatterDataSet> {
    public ScatterBuffer(int size) {
        super(size);
    }

    protected void addForm(float x, float y) {
        float[] fArr = this.buffer;
        int i = this.index;
        this.index = i + 1;
        fArr[i] = x;
        fArr = this.buffer;
        i = this.index;
        this.index = i + 1;
        fArr[i] = y;
    }

    public void feed(IScatterDataSet data) {
        float size = ((float) data.getEntryCount()) * this.phaseX;
        for (int i = 0; ((float) i) < size; i++) {
            Entry e = data.getEntryForIndex(i);
            addForm(e.getX(), e.getY() * this.phaseY);
        }
        reset();
    }
}
