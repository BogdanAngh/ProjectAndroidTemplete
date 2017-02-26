package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class VolumeStrategy implements Strategy {
    private Context context;

    public VolumeStrategy(Context context) {
        this.context = context;
    }

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.volumeunitcubiccm);
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.volumeunitlitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitmillilitres))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitmillilitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitlitres))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitlitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicm))) {
            return input * 0.001d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(this.context.getResources().getString(R.string.volumeunitlitres))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitlitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubiccm))) {
            return 1000.0d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(this.context.getResources().getString(R.string.volumeunitlitres))) {
            return 0.001d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitlitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicmm))) {
            return input * 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(this.context.getResources().getString(R.string.volumeunitlitres))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitlitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet))) {
            return input * 0.03531d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(this.context.getResources().getString(R.string.volumeunitlitres))) {
            return input * 28.31685d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubiccm))) {
            return ((100.0d * input) * 100.0d) * 100.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicm))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicmm))) {
            return ((input * 1000.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicm))) {
            return input / 1.0E9d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(this.context.getResources().getString(R.string.volumeunitmillilitres))) {
            return input * 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitmillilitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicm))) {
            return input / 1000000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet))) {
            return input * 35.31467d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicm))) {
            return input / 35.31467d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicmm))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubiccm))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(this.context.getResources().getString(R.string.volumeunitmillilitres))) {
            return input;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitmillilitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubiccm))) {
            return input;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubiccm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet))) {
            return input / 28316.84659d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubiccm))) {
            return input * 28316.84659d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(this.context.getResources().getString(R.string.volumeunitmillilitres))) {
            return input * 0.001d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitmillilitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicmm))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicmm)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet))) {
            return input / 2.8316846592E7d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicmm))) {
            return input * 2.8316846592E7d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet)) && to.equals(this.context.getResources().getString(R.string.volumeunitmillilitres))) {
            return input * 28316.84659d;
        }
        if (from.equals(this.context.getResources().getString(R.string.volumeunitmillilitres)) && to.equals(this.context.getResources().getString(R.string.volumeunitcubicfeet))) {
            return input / 28316.84659d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }
}
