package com.github.mikephil.charting.formatter;

import com.example.duy.calculator.math_eval.Constants;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.FormattedStringCache.Generic;
import com.github.mikephil.charting.utils.ViewPortHandler;
import java.text.DecimalFormat;

public class DefaultValueFormatter implements ValueFormatter {
    protected int mDecimalDigits;
    protected Generic<Integer, Float> mFormattedStringCache;

    public DefaultValueFormatter(int digits) {
        setup(digits);
    }

    public void setup(int digits) {
        this.mDecimalDigits = digits;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0) {
                b.append(".");
            }
            b.append(Constants.ZERO);
        }
        this.mFormattedStringCache = new Generic(new DecimalFormat("###,###,###,##0" + b.toString()));
    }

    public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
        return this.mFormattedStringCache.getFormattedValue(Float.valueOf(value), Integer.valueOf(dataSetIndex));
    }

    public int getDecimalDigits() {
        return this.mDecimalDigits;
    }
}
