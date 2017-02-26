package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class EnergyStrategy implements Strategy {
    private final Context context;

    public EnergyStrategy(Context context) {
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.energyunitcalories);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.energyunitcalories)) && to.equals(this.context.getResources().getString(R.string.energyunitkilocalories))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.energyunitkilocalories)) && to.equals(this.context.getResources().getString(R.string.energyunitcalories))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.energyunitcalories)) && to.equals(this.context.getResources().getString(R.string.energyunitjoules))) {
            return input * 4.1868d;
        }
        if (from.equals(this.context.getResources().getString(R.string.energyunitjoules)) && to.equals(this.context.getResources().getString(R.string.energyunitcalories))) {
            return input * 0.23885d;
        }
        if (from.equals(this.context.getResources().getString(R.string.energyunitkilocalories)) && to.equals(this.context.getResources().getString(R.string.energyunitjoules))) {
            return input * 4186.8d;
        }
        if (from.equals(this.context.getResources().getString(R.string.energyunitjoules)) && to.equals(this.context.getResources().getString(R.string.energyunitkilocalories))) {
            return input / 4186.8d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }
}
