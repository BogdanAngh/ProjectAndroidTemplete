package com.github.mikephil.charting.highlight;

import android.support.v4.widget.AutoScrollHelper;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.DataSet.Rounding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointD;
import java.util.ArrayList;
import java.util.List;

public class ChartHighlighter<T extends BarLineScatterCandleBubbleDataProvider> implements Highlighter {
    protected T mChart;
    protected List<Highlight> mHighlightBuffer;

    public ChartHighlighter(T chart) {
        this.mHighlightBuffer = new ArrayList();
        this.mChart = chart;
    }

    public Highlight getHighlight(float x, float y) {
        MPPointD pos = getValsForTouch(x, y);
        float xVal = (float) pos.x;
        MPPointD.recycleInstance(pos);
        return getHighlightForX(xVal, x, y);
    }

    protected MPPointD getValsForTouch(float x, float y) {
        return this.mChart.getTransformer(AxisDependency.LEFT).getValuesByTouchPoint(x, y);
    }

    protected Highlight getHighlightForX(float xVal, float x, float y) {
        List<Highlight> closestValues = getHighlightsAtXPos(xVal, x, y);
        if (closestValues.isEmpty()) {
            return null;
        }
        return getClosestHighlightByPixel(closestValues, x, y, getMinimumDistance(closestValues, y, AxisDependency.LEFT) < getMinimumDistance(closestValues, y, AxisDependency.RIGHT) ? AxisDependency.LEFT : AxisDependency.RIGHT, this.mChart.getMaxHighlightDistance());
    }

    protected float getMinimumDistance(List<Highlight> closestValues, float pos, AxisDependency axis) {
        float distance = AutoScrollHelper.NO_MAX;
        for (int i = 0; i < closestValues.size(); i++) {
            Highlight high = (Highlight) closestValues.get(i);
            if (high.getAxis() == axis) {
                float tempDistance = Math.abs(getHighlightPos(high) - pos);
                if (tempDistance < distance) {
                    distance = tempDistance;
                }
            }
        }
        return distance;
    }

    protected float getHighlightPos(Highlight h) {
        return h.getYPx();
    }

    protected List<Highlight> getHighlightsAtXPos(float xVal, float x, float y) {
        this.mHighlightBuffer.clear();
        BarLineScatterCandleBubbleData data = getData();
        if (data == null) {
            return this.mHighlightBuffer;
        }
        int dataSetCount = data.getDataSetCount();
        for (int i = 0; i < dataSetCount; i++) {
            IDataSet dataSet = data.getDataSetByIndex(i);
            if (dataSet.isHighlightEnabled()) {
                Highlight high = buildHighlight(dataSet, i, xVal, Rounding.CLOSEST);
                if (high != null) {
                    this.mHighlightBuffer.add(high);
                }
            }
        }
        return this.mHighlightBuffer;
    }

    protected Highlight buildHighlight(IDataSet set, int dataSetIndex, float xVal, Rounding rounding) {
        Entry e = set.getEntryForXPos(xVal, rounding);
        if (e == null) {
            return null;
        }
        MPPointD pixels = this.mChart.getTransformer(set.getAxisDependency()).getPixelsForValues(e.getX(), e.getY());
        return new Highlight(e.getX(), e.getY(), (float) pixels.x, (float) pixels.y, dataSetIndex, set.getAxisDependency());
    }

    public Highlight getClosestHighlightByPixel(List<Highlight> closestValues, float x, float y, AxisDependency axis, float minSelectionDistance) {
        Highlight closest = null;
        float distance = minSelectionDistance;
        for (int i = 0; i < closestValues.size(); i++) {
            Highlight high = (Highlight) closestValues.get(i);
            if (axis == null || high.getAxis() == axis) {
                float cDistance = getDistance(x, y, high.getXPx(), high.getYPx());
                if (cDistance < distance) {
                    closest = high;
                    distance = cDistance;
                }
            }
        }
        return closest;
    }

    protected float getDistance(float x1, float y1, float x2, float y2) {
        return (float) Math.hypot((double) (x1 - x2), (double) (y1 - y2));
    }

    protected BarLineScatterCandleBubbleData getData() {
        return this.mChart.getData();
    }
}
