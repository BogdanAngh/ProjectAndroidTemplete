package com.github.mikephil.charting.components;

import android.graphics.DashPathEffect;
import android.util.Log;
import com.github.mikephil.charting.formatter.AxisValueFormatter;
import com.github.mikephil.charting.formatter.DefaultAxisValueFormatter;
import com.github.mikephil.charting.utils.Utils;
import io.github.kexanie.library.BuildConfig;
import java.util.ArrayList;
import java.util.List;

public abstract class AxisBase extends ComponentBase {
    private int mAxisLineColor;
    private float mAxisLineWidth;
    public float mAxisMaximum;
    public float mAxisMinimum;
    public float mAxisRange;
    protected AxisValueFormatter mAxisValueFormatter;
    protected boolean mCenterAxisLabels;
    public float[] mCenteredEntries;
    protected boolean mCustomAxisMax;
    protected boolean mCustomAxisMin;
    public int mDecimals;
    protected boolean mDrawAxisLine;
    protected boolean mDrawGridLines;
    protected boolean mDrawLabels;
    protected boolean mDrawLimitLineBehindData;
    public float[] mEntries;
    public int mEntryCount;
    protected boolean mForceLabels;
    protected float mGranularity;
    protected boolean mGranularityEnabled;
    private int mGridColor;
    private DashPathEffect mGridDashPathEffect;
    private float mGridLineWidth;
    private int mLabelCount;
    protected List<LimitLine> mLimitLines;

    public AxisBase() {
        this.mGridColor = -7829368;
        this.mGridLineWidth = 1.0f;
        this.mAxisLineColor = -7829368;
        this.mAxisLineWidth = 1.0f;
        this.mEntries = new float[0];
        this.mCenteredEntries = new float[0];
        this.mLabelCount = 6;
        this.mGranularity = 1.0f;
        this.mGranularityEnabled = false;
        this.mForceLabels = false;
        this.mDrawGridLines = true;
        this.mDrawAxisLine = true;
        this.mDrawLabels = true;
        this.mCenterAxisLabels = false;
        this.mGridDashPathEffect = null;
        this.mDrawLimitLineBehindData = false;
        this.mCustomAxisMin = false;
        this.mCustomAxisMax = false;
        this.mAxisMaximum = 0.0f;
        this.mAxisMinimum = 0.0f;
        this.mAxisRange = 0.0f;
        this.mTextSize = Utils.convertDpToPixel(10.0f);
        this.mXOffset = Utils.convertDpToPixel(5.0f);
        this.mYOffset = Utils.convertDpToPixel(5.0f);
        this.mLimitLines = new ArrayList();
    }

    public void setDrawGridLines(boolean enabled) {
        this.mDrawGridLines = enabled;
    }

    public boolean isDrawGridLinesEnabled() {
        return this.mDrawGridLines;
    }

    public void setDrawAxisLine(boolean enabled) {
        this.mDrawAxisLine = enabled;
    }

    public boolean isDrawAxisLineEnabled() {
        return this.mDrawAxisLine;
    }

    public void setCenterAxisLabels(boolean enabled) {
        this.mCenterAxisLabels = enabled;
    }

    public boolean isCenterAxisLabelsEnabled() {
        return this.mCenterAxisLabels && this.mEntryCount > 1;
    }

    public void setGridColor(int color) {
        this.mGridColor = color;
    }

    public int getGridColor() {
        return this.mGridColor;
    }

    public void setAxisLineWidth(float width) {
        this.mAxisLineWidth = Utils.convertDpToPixel(width);
    }

    public float getAxisLineWidth() {
        return this.mAxisLineWidth;
    }

    public void setGridLineWidth(float width) {
        this.mGridLineWidth = Utils.convertDpToPixel(width);
    }

    public float getGridLineWidth() {
        return this.mGridLineWidth;
    }

    public void setAxisLineColor(int color) {
        this.mAxisLineColor = color;
    }

    public int getAxisLineColor() {
        return this.mAxisLineColor;
    }

    public void setDrawLabels(boolean enabled) {
        this.mDrawLabels = enabled;
    }

    public boolean isDrawLabelsEnabled() {
        return this.mDrawLabels;
    }

    public void setLabelCount(int count) {
        if (count > 25) {
            count = 25;
        }
        if (count < 2) {
            count = 2;
        }
        this.mLabelCount = count;
        this.mForceLabels = false;
    }

    public void setLabelCount(int count, boolean force) {
        setLabelCount(count);
        this.mForceLabels = force;
    }

    public boolean isForceLabelsEnabled() {
        return this.mForceLabels;
    }

    public int getLabelCount() {
        return this.mLabelCount;
    }

    public boolean isGranularityEnabled() {
        return this.mGranularityEnabled;
    }

    public void setGranularityEnabled(boolean enabled) {
        this.mGranularityEnabled = enabled;
    }

    public float getGranularity() {
        return this.mGranularity;
    }

    public void setGranularity(float granularity) {
        this.mGranularity = granularity;
        this.mGranularityEnabled = true;
    }

    public void addLimitLine(LimitLine l) {
        this.mLimitLines.add(l);
        if (this.mLimitLines.size() > 6) {
            Log.e("MPAndroiChart", "Warning! You have more than 6 LimitLines on your axis, do you really want that?");
        }
    }

    public void removeLimitLine(LimitLine l) {
        this.mLimitLines.remove(l);
    }

    public void removeAllLimitLines() {
        this.mLimitLines.clear();
    }

    public List<LimitLine> getLimitLines() {
        return this.mLimitLines;
    }

    public void setDrawLimitLinesBehindData(boolean enabled) {
        this.mDrawLimitLineBehindData = enabled;
    }

    public boolean isDrawLimitLinesBehindDataEnabled() {
        return this.mDrawLimitLineBehindData;
    }

    public String getLongestLabel() {
        String longest = BuildConfig.FLAVOR;
        for (int i = 0; i < this.mEntries.length; i++) {
            String text = getFormattedLabel(i);
            if (text != null && longest.length() < text.length()) {
                longest = text;
            }
        }
        return longest;
    }

    public String getFormattedLabel(int index) {
        if (index < 0 || index >= this.mEntries.length) {
            return BuildConfig.FLAVOR;
        }
        return getValueFormatter().getFormattedValue(this.mEntries[index], this);
    }

    public void setValueFormatter(AxisValueFormatter f) {
        if (f == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        } else {
            this.mAxisValueFormatter = f;
        }
    }

    public AxisValueFormatter getValueFormatter() {
        if (this.mAxisValueFormatter == null) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        } else if (this.mAxisValueFormatter.getDecimalDigits() != this.mDecimals && (this.mAxisValueFormatter instanceof DefaultAxisValueFormatter)) {
            this.mAxisValueFormatter = new DefaultAxisValueFormatter(this.mDecimals);
        }
        return this.mAxisValueFormatter;
    }

    public void enableGridDashedLine(float lineLength, float spaceLength, float phase) {
        this.mGridDashPathEffect = new DashPathEffect(new float[]{lineLength, spaceLength}, phase);
    }

    public void disableGridDashedLine() {
        this.mGridDashPathEffect = null;
    }

    public boolean isGridDashedLineEnabled() {
        return this.mGridDashPathEffect != null;
    }

    public DashPathEffect getGridDashPathEffect() {
        return this.mGridDashPathEffect;
    }

    public float getAxisMaximum() {
        return this.mAxisMaximum;
    }

    public float getAxisMinimum() {
        return this.mAxisMinimum;
    }

    public void resetAxisMaxValue() {
        this.mCustomAxisMax = false;
    }

    public boolean isAxisMaxCustom() {
        return this.mCustomAxisMax;
    }

    public void resetAxisMinValue() {
        this.mCustomAxisMin = false;
    }

    public boolean isAxisMinCustom() {
        return this.mCustomAxisMin;
    }

    public void setAxisMinValue(float min) {
        this.mCustomAxisMin = true;
        this.mAxisMinimum = min;
        this.mAxisRange = Math.abs(this.mAxisMaximum - min);
    }

    public void setAxisMaxValue(float max) {
        this.mCustomAxisMax = true;
        this.mAxisMaximum = max;
        this.mAxisRange = Math.abs(max - this.mAxisMinimum);
    }

    public void calculate(float dataMin, float dataMax) {
        float min;
        float max;
        if (this.mCustomAxisMin) {
            min = this.mAxisMinimum;
        } else {
            min = dataMin;
        }
        if (this.mCustomAxisMax) {
            max = this.mAxisMaximum;
        } else {
            max = dataMax;
        }
        if (Math.abs(max - min) == 0.0f) {
            max += 1.0f;
            min -= 1.0f;
        }
        this.mAxisMinimum = min;
        this.mAxisMaximum = max;
        this.mAxisRange = Math.abs(max - min);
    }
}
