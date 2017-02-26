package com.github.mikephil.charting.data;

import android.support.v4.widget.AutoScrollHelper;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public class BubbleDataSet extends BarLineScatterCandleBubbleDataSet<BubbleEntry> implements IBubbleDataSet {
    private float mHighlightCircleWidth;
    protected float mMaxSize;
    protected boolean mNormalizeSize;

    public BubbleDataSet(List<BubbleEntry> yVals, String label) {
        super(yVals, label);
        this.mNormalizeSize = true;
        this.mHighlightCircleWidth = 2.5f;
    }

    public void setHighlightCircleWidth(float width) {
        this.mHighlightCircleWidth = Utils.convertDpToPixel(width);
    }

    public float getHighlightCircleWidth() {
        return this.mHighlightCircleWidth;
    }

    public void calcMinMax() {
        if (this.mValues != null && !this.mValues.isEmpty()) {
            this.mYMax = -3.4028235E38f;
            this.mYMin = AutoScrollHelper.NO_MAX;
            this.mXMax = -3.4028235E38f;
            this.mXMin = AutoScrollHelper.NO_MAX;
            for (BubbleEntry e : this.mValues) {
                calcMinMax(e);
                float size = e.getSize();
                if (size > this.mMaxSize) {
                    this.mMaxSize = size;
                }
            }
        }
    }

    public DataSet<BubbleEntry> copy() {
        List<BubbleEntry> yVals = new ArrayList();
        for (int i = 0; i < this.mValues.size(); i++) {
            yVals.add(((BubbleEntry) this.mValues.get(i)).copy());
        }
        BubbleDataSet copied = new BubbleDataSet(yVals, getLabel());
        copied.mColors = this.mColors;
        copied.mHighLightColor = this.mHighLightColor;
        return copied;
    }

    public float getMaxSize() {
        return this.mMaxSize;
    }

    public boolean isNormalizeSizeEnabled() {
        return this.mNormalizeSize;
    }

    public void setNormalizeSizeEnabled(boolean normalizeSize) {
        this.mNormalizeSize = normalizeSize;
    }
}
