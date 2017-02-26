package com.github.mikephil.charting.formatter;

import com.example.duy.calculator.math_eval.Constants;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.formatter.FormattedStringCache.PrimFloat;
import java.text.DecimalFormat;

public class DefaultAxisValueFormatter implements AxisValueFormatter {
    protected int digits;
    protected PrimFloat mFormattedStringCache;

    public DefaultAxisValueFormatter(int digits) {
        this.digits = 0;
        this.digits = digits;
        StringBuffer b = new StringBuffer();
        for (int i = 0; i < digits; i++) {
            if (i == 0) {
                b.append(".");
            }
            b.append(Constants.ZERO);
        }
        this.mFormattedStringCache = new PrimFloat(new DecimalFormat("###,###,###,##0" + b.toString()));
    }

    public String getFormattedValue(float value, AxisBase axis) {
        return this.mFormattedStringCache.getFormattedValue(value);
    }

    public int getDecimalDigits() {
        return this.digits;
    }
}
