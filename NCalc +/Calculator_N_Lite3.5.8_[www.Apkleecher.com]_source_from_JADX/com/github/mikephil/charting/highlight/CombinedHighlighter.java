package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.ChartData;
import com.github.mikephil.charting.data.DataSet.Rounding;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.List;

public class CombinedHighlighter extends ChartHighlighter<CombinedDataProvider> implements Highlighter {
    protected BarHighlighter barHighlighter;

    public CombinedHighlighter(CombinedDataProvider chart, BarDataProvider barChart) {
        super(chart);
        this.barHighlighter = barChart.getBarData() == null ? null : new BarHighlighter(barChart);
    }

    protected List<Highlight> getHighlightsAtXPos(float xVal, float x, float y) {
        this.mHighlightBuffer.clear();
        List<BarLineScatterCandleBubbleData> dataObjects = ((CombinedDataProvider) this.mChart).getCombinedData().getAllData();
        for (int i = 0; i < dataObjects.size(); i++) {
            ChartData dataObject = (ChartData) dataObjects.get(i);
            if (this.barHighlighter == null || !(dataObject instanceof BarData)) {
                int dataSetCount = dataObject.getDataSetCount();
                for (int j = 0; j < dataSetCount; j++) {
                    IDataSet dataSet = ((BarLineScatterCandleBubbleData) dataObjects.get(i)).getDataSetByIndex(j);
                    if (dataSet.isHighlightEnabled()) {
                        Highlight s1 = buildHighlight(dataSet, j, xVal, Rounding.CLOSEST);
                        s1.setDataIndex(i);
                        this.mHighlightBuffer.add(s1);
                    }
                }
            } else {
                Highlight high = this.barHighlighter.getHighlight(x, y);
                if (high != null) {
                    high.setDataIndex(i);
                    this.mHighlightBuffer.add(high);
                }
            }
        }
        return this.mHighlightBuffer;
    }
}
