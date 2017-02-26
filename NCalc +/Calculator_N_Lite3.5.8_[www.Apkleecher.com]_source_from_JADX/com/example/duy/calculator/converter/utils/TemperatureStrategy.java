package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class TemperatureStrategy implements Strategy {
    private Context context;

    public TemperatureStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitc)) && to.equals(this.context.getResources().getString(R.string.temperatureunitf))) {
            return ((9.0d * input) / 5.0d) + 32.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitf)) && to.equals(this.context.getResources().getString(R.string.temperatureunitc))) {
            return ((input - 32.0d) * 5.0d) / 9.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitc)) && to.equals(this.context.getResources().getString(R.string.temperatureunitk))) {
            return input + 273.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitk)) && to.equals(this.context.getResources().getString(R.string.temperatureunitc))) {
            return input - 273.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitc)) && to.equals(this.context.getResources().getString(R.string.temperatureunitn))) {
            return (33.0d * input) / 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.temperatureunitn)) && to.equals(this.context.getResources().getString(R.string.temperatureunitc))) {
            return (100.0d * input) / 33.0d;
        }
        return !from.equals(to) ? 0.0d : input;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.temperatureunitc);
    }
}
