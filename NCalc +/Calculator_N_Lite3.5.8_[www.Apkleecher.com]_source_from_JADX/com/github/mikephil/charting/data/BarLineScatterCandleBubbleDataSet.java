package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.support.v4.view.MotionEventCompat;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public abstract class BarLineScatterCandleBubbleDataSet<T extends Entry> extends DataSet<T> implements IBarLineScatterCandleBubbleDataSet<T> {
    protected int mHighLightColor;

    public BarLineScatterCandleBubbleDataSet(List<T> yVals, String label) {
        super(yVals, label);
        this.mHighLightColor = Color.rgb(MotionEventCompat.ACTION_MASK, 187, 115);
    }

    public void setHighLightColor(int color) {
        this.mHighLightColor = color;
    }

    public int getHighLightColor() {
        return this.mHighLightColor;
    }
}
