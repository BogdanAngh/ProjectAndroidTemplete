package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.DataSet.Rounding;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class BarLineScatterCandleBubbleRenderer extends DataRenderer {
    protected XBounds mXBounds;

    protected class XBounds {
        public int max;
        public int min;
        public int range;

        protected XBounds() {
        }

        public void set(BarLineScatterCandleBubbleDataProvider chart, IBarLineScatterCandleBubbleDataSet dataSet) {
            float phaseX = Math.max(0.0f, Math.min(1.0f, BarLineScatterCandleBubbleRenderer.this.mAnimator.getPhaseX()));
            float low = chart.getLowestVisibleX();
            float high = chart.getHighestVisibleX();
            Entry entryFrom = dataSet.getEntryForXPos(low, Rounding.DOWN);
            Entry entryTo = dataSet.getEntryForXPos(high, Rounding.UP);
            this.min = dataSet.getEntryIndex(entryFrom);
            this.max = dataSet.getEntryIndex(entryTo);
            this.range = (int) (((float) (this.max - this.min)) * phaseX);
        }
    }

    public BarLineScatterCandleBubbleRenderer(ChartAnimator animator, ViewPortHandler viewPortHandler) {
        super(animator, viewPortHandler);
        this.mXBounds = new XBounds();
    }

    protected boolean isInBoundsX(Entry e, IBarLineScatterCandleBubbleDataSet set) {
        if (e == null) {
            return false;
        }
        float entryIndex = (float) set.getEntryIndex(e);
        if (e == null || entryIndex >= ((float) set.getEntryCount()) * this.mAnimator.getPhaseX()) {
            return false;
        }
        return true;
    }
}
