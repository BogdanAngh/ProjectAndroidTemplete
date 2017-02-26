package com.github.mikephil.charting.data;

import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import java.util.List;

public class LineData extends BarLineScatterCandleBubbleData<ILineDataSet> {
    public LineData(ILineDataSet... dataSets) {
        super((IBarLineScatterCandleBubbleDataSet[]) dataSets);
    }

    public LineData(List<ILineDataSet> dataSets) {
        super((List) dataSets);
    }
}
