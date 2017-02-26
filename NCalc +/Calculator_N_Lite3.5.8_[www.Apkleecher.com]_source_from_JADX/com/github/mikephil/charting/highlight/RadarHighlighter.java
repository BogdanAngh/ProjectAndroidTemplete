package com.github.mikephil.charting.highlight;

import android.support.v4.widget.AutoScrollHelper;
import com.github.mikephil.charting.charts.RadarChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.RadarData;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.MPPointF;
import com.github.mikephil.charting.utils.Utils;
import java.util.List;

public class RadarHighlighter extends PieRadarHighlighter<RadarChart> {
    public RadarHighlighter(RadarChart chart) {
        super(chart);
    }

    protected Highlight getClosestHighlight(int index, float x, float y) {
        List<Highlight> highlights = getHighlightsAtIndex(index);
        float distanceToCenter = ((RadarChart) this.mChart).distanceToCenter(x, y) / ((RadarChart) this.mChart).getFactor();
        Highlight closest = null;
        float distance = AutoScrollHelper.NO_MAX;
        for (int i = 0; i < highlights.size(); i++) {
            Highlight high = (Highlight) highlights.get(i);
            float cdistance = Math.abs(high.getY() - distanceToCenter);
            if (cdistance < distance) {
                closest = high;
                distance = cdistance;
            }
        }
        return closest;
    }

    protected List<Highlight> getHighlightsAtIndex(int index) {
        this.mHighlightBuffer.clear();
        float phaseX = ((RadarChart) this.mChart).getAnimator().getPhaseX();
        float phaseY = ((RadarChart) this.mChart).getAnimator().getPhaseY();
        float sliceangle = ((RadarChart) this.mChart).getSliceAngle();
        float factor = ((RadarChart) this.mChart).getFactor();
        MPPointF pOut = MPPointF.getInstance(0.0f, 0.0f);
        for (int i = 0; i < ((RadarData) ((RadarChart) this.mChart).getData()).getDataSetCount(); i++) {
            IDataSet<?> dataSet = ((RadarData) ((RadarChart) this.mChart).getData()).getDataSetByIndex(i);
            Entry entry = dataSet.getEntryForIndex(index);
            float y = entry.getY() - ((RadarChart) this.mChart).getYChartMin();
            Utils.getPosition(((RadarChart) this.mChart).getCenterOffsets(), (y * factor) * phaseY, ((RadarChart) this.mChart).getRotationAngle() + ((((float) index) * sliceangle) * phaseX), pOut);
            List list = this.mHighlightBuffer;
            r16.add(new Highlight((float) index, entry.getY(), pOut.x, pOut.y, i, dataSet.getAxisDependency()));
        }
        return this.mHighlightBuffer;
    }
}
