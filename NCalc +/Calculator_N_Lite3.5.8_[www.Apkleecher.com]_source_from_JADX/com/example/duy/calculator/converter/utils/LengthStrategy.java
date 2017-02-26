package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public class LengthStrategy implements Strategy {
    private Context context;

    public LengthStrategy(Context context) {
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.lengthunitm);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return 0.62137d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return 1.60934d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return 1609.34d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return input / 1609.34d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return 160934.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return input / 160934.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return input * 1609340.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return input / 1609340.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return 63360.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return input / 63360.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmile)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return 5280.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitmile))) {
            return input / 5280.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return 0.001d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return 100000.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return input / 100000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return 1000000.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return input * 3280.84d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return input / 3280.84d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitkm)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return input * 39370.1d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitkm))) {
            return input / 39370.1d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return 100.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return 1000.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return (100.0d * input) / 2.54d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return (2.54d * input) / 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return input * 3.28084d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 3.28084d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return input / BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return 2.54d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return input / 2.54d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitcm)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return input * 0.03281d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitcm))) {
            return input * 30.48d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return 0.00328d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return input * 304.8d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmm)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return input / 25.4d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitmm))) {
            return input * 25.4d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitfeet)) && to.equals(this.context.getResources().getString(R.string.lengthunitinch))) {
            return 12.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitinch)) && to.equals(this.context.getResources().getString(R.string.lengthunitfeet))) {
            return input / 12.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitnm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 1.0E10d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitnm))) {
            return input * 1.0E10d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitmicrom)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 1.0E7d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitmicrom))) {
            return input * 1.0E7d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitpm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / 1.0E13d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitpm))) {
            return input * 1.0E13d;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitdm)) && to.equals(this.context.getResources().getString(R.string.lengthunitm))) {
            return input / BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
        }
        if (from.equals(this.context.getResources().getString(R.string.lengthunitm)) && to.equals(this.context.getResources().getString(R.string.lengthunitdm))) {
            return input * BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }
}
