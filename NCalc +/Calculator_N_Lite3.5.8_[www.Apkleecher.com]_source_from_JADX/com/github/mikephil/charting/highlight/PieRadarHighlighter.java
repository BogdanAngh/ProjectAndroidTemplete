package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.charts.PieRadarChartBase;
import java.util.ArrayList;
import java.util.List;

public abstract class PieRadarHighlighter<T extends PieRadarChartBase> implements Highlighter {
    protected T mChart;
    protected List<Highlight> mHighlightBuffer;

    protected abstract Highlight getClosestHighlight(int i, float f, float f2);

    public PieRadarHighlighter(T chart) {
        this.mHighlightBuffer = new ArrayList();
        this.mChart = chart;
    }

    public Highlight getHighlight(float x, float y) {
        if (this.mChart.distanceToCenter(x, y) > this.mChart.getRadius()) {
            return null;
        }
        float angle = this.mChart.getAngleForPoint(x, y);
        if (this.mChart instanceof PieChart) {
            angle /= this.mChart.getAnimator().getPhaseY();
        }
        int index = this.mChart.getIndexForAngle(angle);
        if (index < 0 || index >= this.mChart.getData().getMaxEntryCountSet().getEntryCount()) {
            return null;
        }
        return getClosestHighlight(index, x, y);
    }
}
