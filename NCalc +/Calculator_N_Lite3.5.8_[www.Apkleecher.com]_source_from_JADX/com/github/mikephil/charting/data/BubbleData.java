package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IBubbleDataSet;
import java.util.List;

public class BubbleData extends BarLineScatterCandleBubbleData<IBubbleDataSet> {
    public BubbleData(IBubbleDataSet... dataSets) {
        super((IBarLineScatterCandleBubbleDataSet[]) dataSets);
    }

    public BubbleData(List<IBubbleDataSet> dataSets) {
        super((List) dataSets);
    }

    public void setHighlightCircleWidth(float width) {
        for (int i = 0; i < this.mDataSets.size(); i++) {
            ((IBubbleDataSet) this.mDataSets.get(i)).setHighlightCircleWidth(width);
        }
    }
}
