package com.example.duy.calculator.converter.utils;

import android.content.Context;
import com.example.duy.calculator.R;

public class PowerStrategy implements Strategy {
    private Context context;

    public String getUnitDefault() {
        return this.context.getResources().getString(R.string.powerunithorsepower);
    }

    public PowerStrategy(Context context) {
        this.context = context;
    }

    public double Convert(String from, String to, double input) {
        if (from.equals(this.context.getResources().getString(R.string.powerunitwatts)) && to.equals(this.context.getResources().getString(R.string.powerunithorsepower))) {
            return 0.00134d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunithorsepower)) && to.equals(this.context.getResources().getString(R.string.powerunitwatts))) {
            return 745.7d * input;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunitwatts)) && to.equals(this.context.getResources().getString(R.string.powerunitkilowatts))) {
            return input / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunitkilowatts)) && to.equals(this.context.getResources().getString(R.string.powerunitwatts))) {
            return input * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunitkilowatts)) && to.equals(this.context.getResources().getString(R.string.powerunithorsepower))) {
            return input * 1.34102d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunithorsepower)) && to.equals(this.context.getResources().getString(R.string.powerunitkilowatts))) {
            return input * 0.7457d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunithorsepower)) && to.equals(this.context.getResources().getString(R.string.powerunitmegawatt))) {
            return ((input * 745.69d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunitmegawatt)) && to.equals(this.context.getResources().getString(R.string.powerunithorsepower))) {
            return ((input / 745.69d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunithorsepower)) && to.equals(this.context.getResources().getString(R.string.powerunitgigawatt))) {
            return (((input * 745.69d) / 1000.0d) / 1000.0d) / 1000.0d;
        }
        if (from.equals(this.context.getResources().getString(R.string.powerunitgigawatt)) && to.equals(this.context.getResources().getString(R.string.powerunithorsepower))) {
            return (((input / 745.69d) * 1000.0d) * 1000.0d) * 1000.0d;
        }
        if (from.equals(to)) {
            return input;
        }
        return 0.0d;
    }
}
