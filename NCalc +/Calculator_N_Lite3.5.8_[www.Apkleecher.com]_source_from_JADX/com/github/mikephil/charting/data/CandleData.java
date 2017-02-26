package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ICandleDataSet;
import java.util.List;

public class CandleData extends BarLineScatterCandleBubbleData<ICandleDataSet> {
    public CandleData(List<ICandleDataSet> dataSets) {
        super((List) dataSets);
    }

    public CandleData(ICandleDataSet... dataSets) {
        super((IBarLineScatterCandleBubbleDataSet[]) dataSets);
    }
}
