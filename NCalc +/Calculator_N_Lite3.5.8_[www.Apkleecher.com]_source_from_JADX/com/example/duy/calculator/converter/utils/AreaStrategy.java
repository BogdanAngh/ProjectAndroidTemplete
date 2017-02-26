package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;
import org.apache.commons.math4.optim.nonlinear.scalar.noderiv.BOBYQAOptimizer;

public class AreaStrategy implements Strategy {
    private Context context;

    public AreaStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(this.context.getResources().getString(R.string.areaunitsqkm))) {
            return 2.5899752356d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqkm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmiles))) {
            return 0.3861006769d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(this.context.getResources().getString(R.string.areaunitsqm))) {
            return 2589975.2356d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmiles))) {
            return input / 2589975.2356d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(this.context.getResources().getString(R.string.areaunitsqcm))) {
            return (160934.0d * input) * 160934.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqcm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmiles))) {
            return input / 1.2994858E8d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmm))) {
            return (1609340.0d * input) * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmiles))) {
            return input / 1.09956112E8d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmiles)) && to.equals(this.context.getResources().getString(R.string.areaunitsqyard))) {
            return (1760.0d * input) * 1760.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqyard)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmiles))) {
            return input / 3097600.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqkm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqm))) {
            return (1000.0d * input) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqkm))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqkm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqcm))) {
            return (100000.0d * input) * 100000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqcm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqkm))) {
            return input / 1.410065408E9d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqkm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmm))) {
            return (1000000.0d * input) * 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqkm))) {
            return input / -7.27379968E8d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqkm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqyard))) {
            return 1195990.04993689d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqyard)) && to.equals(this.context.getResources().getString(R.string.areaunitsqkm))) {
            return input / 1195990.04993689d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqcm))) {
            return (100.0d * input) * 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqcm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqm))) {
            return input / 10000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmm))) {
            return (1000.0d * input) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqm))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqyard))) {
            return 1.1959828321d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqyard)) && to.equals(this.context.getResources().getString(R.string.areaunitsqm))) {
            return input / 1.1959828321d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqcm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmm))) {
            return (BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS * input) * BOBYQAOptimizer.DEFAULT_INITIAL_RADIUS;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqcm))) {
            return input / 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqcm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqyard))) {
            return 1.196836E-4d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqyard)) && to.equals(this.context.getResources().getString(R.string.areaunitsqcm))) {
            return input / 1.196836E-4d;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqmm)) && to.equals(this.context.getResources().getString(R.string.areaunitsqyard))) {
            return 1.1968359999999999E-6d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.areaunitsqyard)) && to.equals(this.context.getResources().getString(R.string.areaunitsqmm))) {
            return input / 1.1968359999999999E-6d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.areaunitsqm);
    }
}
