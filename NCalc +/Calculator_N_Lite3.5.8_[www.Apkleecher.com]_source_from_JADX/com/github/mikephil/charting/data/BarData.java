package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import java.util.List;

public class BarData extends BarLineScatterCandleBubbleData<IBarDataSet> {
    private float mBarWidth;

    public BarData() {
        this.mBarWidth = 1.0f;
    }

    public BarData(IBarDataSet... dataSets) {
        super((IBarLineScatterCandleBubbleDataSet[]) dataSets);
        this.mBarWidth = 1.0f;
    }

    public BarData(List<IBarDataSet> dataSets) {
        super((List) dataSets);
        this.mBarWidth = 1.0f;
    }

    public void setBarWidth(float mBarWidth) {
        this.mBarWidth = mBarWidth;
    }

    public float getBarWidth() {
        return this.mBarWidth;
    }

    public void groupBars(float fromX, float groupSpace, float barSpace) {
        if (this.mDataSets.size() <= 1) {
            throw new RuntimeException("BarData needs to hold at least 2 BarDataSets to allow grouping.");
        }
        int maxEntryCount = ((IBarDataSet) getMaxEntryCountSet()).getEntryCount();
        float groupSpaceWidthHalf = groupSpace / 2.0f;
        float barSpaceHalf = barSpace / 2.0f;
        float barWidthHalf = this.mBarWidth / 2.0f;
        float interval = getGroupWidth(groupSpace, barSpace);
        for (int i = 0; i < maxEntryCount; i++) {
            float start = fromX;
            fromX += groupSpaceWidthHalf;
            int setCountJ = this.mDataSets.size();
            for (int j = 0; j < setCountJ; j++) {
                IBarDataSet set = (IBarDataSet) this.mDataSets.get(j);
                fromX = (fromX + barSpaceHalf) + barWidthHalf;
                if (i < set.getEntryCount()) {
                    BarEntry entry = (BarEntry) set.getEntryForIndex(i);
                    if (entry != null) {
                        entry.setX(fromX);
                    }
                }
                fromX = (fromX + barWidthHalf) + barSpaceHalf;
            }
            fromX += groupSpaceWidthHalf;
            float diff = interval - (fromX - start);
            if (diff > 0.0f || diff < 0.0f) {
                fromX += diff;
            }
        }
        notifyDataChanged();
    }

    public float getGroupWidth(float groupSpace, float barSpace) {
        return (((float) this.mDataSets.size()) * (this.mBarWidth + barSpace)) + groupSpace;
    }
}
