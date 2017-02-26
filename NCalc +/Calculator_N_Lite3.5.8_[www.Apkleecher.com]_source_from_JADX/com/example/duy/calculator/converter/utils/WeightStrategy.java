package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class WeightStrategy implements Strategy {
    private Context context;

    public WeightStrategy(Context context) {
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.weightunitkg);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.weightunitkg)) && to.equals(this.context.getResources().getString(R.string.weightunitgm))) {
            return 1000.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitgm)) && to.equals(this.context.getResources().getString(R.string.weightunitkg))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitkg)) && to.equals(this.context.getResources().getString(R.string.weightunitlb))) {
            return 2.2046d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitlb)) && to.equals(this.context.getResources().getString(R.string.weightunitkg))) {
            return 0.454d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitkg)) && to.equals(this.context.getResources().getString(R.string.weightunitounce))) {
            return input * 35.27396d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitounce)) && to.equals(this.context.getResources().getString(R.string.weightunitkg))) {
            return input * 0.02835d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitkg)) && to.equals(this.context.getResources().getString(R.string.weightunitmg))) {
            return input * 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitmg)) && to.equals(this.context.getResources().getString(R.string.weightunitkg))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitgm)) && to.equals(this.context.getResources().getString(R.string.weightunitlb))) {
            return 0.0022d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitlb)) && to.equals(this.context.getResources().getString(R.string.weightunitgm))) {
            return 453.6d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitgm)) && to.equals(this.context.getResources().getString(R.string.weightunitmg))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitmg)) && to.equals(this.context.getResources().getString(R.string.weightunitgm))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitgm)) && to.equals(this.context.getResources().getString(R.string.weightunitounce))) {
            return input * 0.03527d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitounce)) && to.equals(this.context.getResources().getString(R.string.weightunitgm))) {
            return input * 28.34952d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitlb)) && to.equals(this.context.getResources().getString(R.string.weightunitmg))) {
            return input * 453592.37d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitmg)) && to.equals(this.context.getResources().getString(R.string.weightunitlb))) {
            return input / 453592.37d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitounce)) && to.equals(this.context.getResources().getString(R.string.weightunitmg))) {
            return input * 28349.52313d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitmg)) && to.equals(this.context.getResources().getString(R.string.weightunitounce))) {
            return input / 28349.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitlb)) && to.equals(this.context.getResources().getString(R.string.weightunitounce))) {
            return 16.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.weightunitounce)) && to.equals(this.context.getResources().getString(R.string.weightunitlb))) {
            return input / 16.0d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }
}
