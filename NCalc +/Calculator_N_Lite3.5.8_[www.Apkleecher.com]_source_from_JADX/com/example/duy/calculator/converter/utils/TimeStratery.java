package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class TimeStratery implements Strategy {
    private final int TIME_UNIT;
    private final Context context;

    public TimeStratery(Context context) {
        this.TIME_UNIT = 60;
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.timeunitsecond);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return input;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunitminutes))) {
            return input / 60.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunithours))) {
            return (input / 60.0d) / 60.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunitdays))) {
            return ((input / 60.0d) / 60.0d) / 24.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunitweeks))) {
            return (((input / 60.0d) / 60.0d) / 24.0d) / 7.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitsecond)) && to.equals(this.context.getResources().getString(R.string.timeunitmiliseconds))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitminutes)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return input * 60.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunithours)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return (60.0d * input) * 60.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitdays)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return ((60.0d * input) * 60.0d) * 24.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitweeks)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return (((60.0d * input) * 60.0d) * 24.0d) * 7.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.timeunitmiliseconds)) && to.equals(this.context.getResources().getString(R.string.timeunitsecond))) {
            return input / 1000.0d;
        }
        return 0.0d;
    }
}
