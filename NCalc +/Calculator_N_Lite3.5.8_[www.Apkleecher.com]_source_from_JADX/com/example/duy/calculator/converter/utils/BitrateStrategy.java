package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;
import org.apache.commons.math4.random.EmpiricalDistribution;
import org.matheclipse.core.interfaces.IExpr;

public class BitrateStrategy implements Strategy {
    final int BIT_RATE_1;
    final int BIT_RATE_2;
    private final Context context;

    public BitrateStrategy(Context context) {
        this.BIT_RATE_1 = EmpiricalDistribution.DEFAULT_BIN_COUNT;
        this.BIT_RATE_2 = IExpr.ASTID;
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.bitrateunitbytes);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return input;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitkilobytes))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitmegabytes))) {
            return (input / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitgigabytes))) {
            return ((input / 1000.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitterabytes))) {
            return (((input / 1000.0d) / 1000.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbits))) {
            return input * 8.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitkilobits))) {
            return (input * 8.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitmegabits))) {
            return ((input * 8.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitgigabits))) {
            return ((input * 8.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitterabits))) {
            return ((input * 8.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitkilobytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitmegabytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return (input * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitgigabytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return ((input * 1000.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitterabytes)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return (((input * 1000.0d) * 1000.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitbits)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return input / 8.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitmegabits)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return ((input / 8.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitkilobits)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return (input / 8.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitgigabits)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return (((input / 8.0d) * 1000.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.bitrateunitterabits)) && to.equals(this.context.getResources().getString(R.string.bitrateunitbytes))) {
            return ((((input / 8.0d) * 1000.0d) * 1000.0d) * 1000.0d) * 1000.0d;
        }
        return 0.0d;
    }
}
