package com.github.mikephil.charting.formatter;

import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.FormattedStringCache.Generic;
import com.github.mikephil.charting.formatter.FormattedStringCache.PrimFloat;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class PercentFormatter implements ValueFormatter, AxisValueFormatter {
    protected Generic<Integer, Float> mFormattedStringCache;
    protected PrimFloat mFormattedStringCacheAxis;

    public PercentFormatter() {
        this.mFormattedStringCache = new Generic(new DecimalFormat("###,###,##0.0"));
        this.mFormattedStringCacheAxis = new PrimFloat(new DecimalFormat("###,###,##0.0"));
    }

    public PercentFormatter(DecimalFormat format) {
        this.mFormattedStringCache = new Generic(format);
        this.mFormattedStringCacheAxis = new PrimFloat(format);
    }

    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return this.mFormattedStringCache.getFormattedValue(Float.valueOf(value), Integer.valueOf(dataSetIndex)) + " %";
    }

    public String getFormattedValue(float value, AxisBase axis) {
        return this.mFormattedStringCacheAxis.getFormattedValue(value) + " %";
    }

    public int getDecimalDigits() {
        return 1;
    }
}
