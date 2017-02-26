package com.github.mikephil.charting.data;

import android.support.v4.widget.AutoScrollHelper;
import android.util.Log;
import com.github.mikephil.charting.charts.Chart;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.ArrayList;
import java.util.List;

public class CombinedData extends BarLineScatterCandleBubbleData<IBarLineScatterCandleBubbleDataSet<? extends Entry>> {
    private BarData mBarData;
    private BubbleData mBubbleData;
    private CandleData mCandleData;
    private LineData mLineData;
    private ScatterData mScatterData;

    public void setData(LineData data) {
        this.mLineData = data;
        init();
    }

    public void setData(BarData data) {
        this.mBarData = data;
        init();
    }

    public void setData(ScatterData data) {
        this.mScatterData = data;
        init();
    }

    public void setData(CandleData data) {
        this.mCandleData = data;
        init();
    }

    public void setData(BubbleData data) {
        this.mBubbleData = data;
        init();
    }

    public void calcMinMax() {
        if (this.mDataSets == null) {
            this.mDataSets = new ArrayList();
        }
        this.mDataSets.clear();
        this.mYMax = -3.4028235E38f;
        this.mYMin = AutoScrollHelper.NO_MAX;
        this.mXMax = -3.4028235E38f;
        this.mXMin = AutoScrollHelper.NO_MAX;
        this.mLeftAxisMax = -3.4028235E38f;
        this.mLeftAxisMin = AutoScrollHelper.NO_MAX;
        this.mRightAxisMax = -3.4028235E38f;
        this.mRightAxisMin = AutoScrollHelper.NO_MAX;
        for (ChartData data : getAllData()) {
            data.calcMinMax();
            this.mDataSets.addAll(data.getDataSets());
            if (data.getYMax() > this.mYMax) {
                this.mYMax = data.getYMax();
            }
            if (data.getYMin() < this.mYMin) {
                this.mYMin = data.getYMin();
            }
            if (data.getXMax() > this.mXMax) {
                this.mXMax = data.getXMax();
            }
            if (data.getXMin() < this.mXMin) {
                this.mXMin = data.getXMin();
            }
            if (data.mLeftAxisMax > this.mLeftAxisMax) {
                this.mLeftAxisMax = data.mLeftAxisMax;
            }
            if (data.mLeftAxisMin < this.mLeftAxisMin) {
                this.mLeftAxisMin = data.mLeftAxisMin;
            }
            if (data.mRightAxisMax > this.mRightAxisMax) {
                this.mRightAxisMax = data.mRightAxisMax;
            }
            if (data.mRightAxisMin < this.mRightAxisMin) {
                this.mRightAxisMin = data.mRightAxisMin;
            }
        }
    }

    public BubbleData getBubbleData() {
        return this.mBubbleData;
    }

    public LineData getLineData() {
        return this.mLineData;
    }

    public BarData getBarData() {
        return this.mBarData;
    }

    public ScatterData getScatterData() {
        return this.mScatterData;
    }

    public CandleData getCandleData() {
        return this.mCandleData;
    }

    public List<BarLineScatterCandleBubbleData> getAllData() {
        List<BarLineScatterCandleBubbleData> data = new ArrayList();
        if (this.mLineData != null) {
            data.add(this.mLineData);
        }
        if (this.mBarData != null) {
            data.add(this.mBarData);
        }
        if (this.mScatterData != null) {
            data.add(this.mScatterData);
        }
        if (this.mCandleData != null) {
            data.add(this.mCandleData);
        }
        if (this.mBubbleData != null) {
            data.add(this.mBubbleData);
        }
        return data;
    }

    public BarLineScatterCandleBubbleData getDataByIndex(int index) {
        return (BarLineScatterCandleBubbleData) getAllData().get(index);
    }

    public void notifyDataChanged() {
        if (this.mLineData != null) {
            this.mLineData.notifyDataChanged();
        }
        if (this.mBarData != null) {
            this.mBarData.notifyDataChanged();
        }
        if (this.mCandleData != null) {
            this.mCandleData.notifyDataChanged();
        }
        if (this.mScatterData != null) {
            this.mScatterData.notifyDataChanged();
        }
        if (this.mBubbleData != null) {
            this.mBubbleData.notifyDataChanged();
        }
        init();
    }

    public Entry getEntryForHighlight(Highlight highlight) {
        List<BarLineScatterCandleBubbleData> dataObjects = getAllData();
        if (highlight.getDataIndex() >= dataObjects.size()) {
            return null;
        }
        ChartData data = (ChartData) dataObjects.get(highlight.getDataIndex());
        if (highlight.getDataSetIndex() >= data.getDataSetCount()) {
            return null;
        }
        for (Entry entry : data.getDataSetByIndex(highlight.getDataSetIndex()).getEntriesForXPos(highlight.getX())) {
            if (entry.getY() == highlight.getY()) {
                return entry;
            }
            if (Float.isNaN(highlight.getY())) {
                return entry;
            }
        }
        return null;
    }

    public int getDataIndex(ChartData data) {
        return getAllData().indexOf(data);
    }

    public boolean removeDataSet(IBarLineScatterCandleBubbleDataSet<? extends Entry> d) {
        boolean success = false;
        for (ChartData data : getAllData()) {
            success = data.removeDataSet((IDataSet) d);
            if (success) {
                break;
            }
        }
        return success;
    }

    @Deprecated
    public boolean removeDataSet(int index) {
        Log.e(Chart.LOG_TAG, "removeDataSet(int index) not supported for CombinedData");
        return false;
    }

    @Deprecated
    public boolean removeEntry(Entry e, int dataSetIndex) {
        Log.e(Chart.LOG_TAG, "removeEntry(...) not supported for CombinedData");
        return false;
    }

    @Deprecated
    public boolean removeEntry(float xPos, int dataSetIndex) {
        Log.e(Chart.LOG_TAG, "removeEntry(...) not supported for CombinedData");
        return false;
    }
}
